package net.jdg.disruption.event_engine.impl;


import net.jdg.disruption.event_engine.EventTuples;

public class GenericEvent implements Event {
    @Override
    public void trigger(EventTuples tuples) {

    }

    @Override
    public EventCondition getCond() {
        return EventCondition.FALSE;
    }
}
