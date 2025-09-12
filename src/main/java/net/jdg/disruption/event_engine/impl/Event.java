package net.jdg.disruption.event_engine.impl;

import net.jdg.disruption.event_engine.EventTuples;

import java.util.Arrays;
import java.util.function.Function;

public interface Event {

    void trigger(EventTuples tuples);

    default boolean shouldRun(EventTuples tuples) {
        return getCond().check(tuples);
    }


    EventCondition getCond();
    
    class EventCondition {
        public static final EventCondition TRUE = new EventCondition(t -> true);
        public static final EventCondition FALSE = new EventCondition(t -> false);
        private final Function<EventTuples, Boolean>[] conds;
        private final boolean or;

        @SafeVarargs
        public EventCondition(Function<EventTuples, Boolean>... conds) {
            this.conds = conds;
            this.or = false;
        }
        
        @SafeVarargs
        public EventCondition(boolean or, Function<EventTuples, Boolean>... conds) {
            this.conds = conds;
            this.or = or;
        }
        
        public EventCondition merge(EventCondition other) {
            var a = new java.util.ArrayList<>(Arrays.stream(conds).toList());
            a.addAll(Arrays.stream(other.conds).toList());
            //noinspection unchecked
            return new EventCondition(or, a.toArray(new Function[]{}));
        }
        
        public boolean check(EventTuples tuples) {
            for (Function<EventTuples, Boolean> cond : conds) {
                var result = cond.apply(tuples);
                if (or && result) return true;
                if (!or && !result) return false;
            }
            return true;
        }
    }
}
