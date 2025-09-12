package net.jdg.disruption.event_engine.impl;

public abstract class TimeEvent implements Event {

    private final long trigger_time;

    public TimeEvent(long trigger_time) {
        this.trigger_time = trigger_time;
    }

    @Override
    public EventCondition getCond() {
        return new EventCondition(t -> t.world().getTimeOfDay(0) == trigger_time);
    }
}
