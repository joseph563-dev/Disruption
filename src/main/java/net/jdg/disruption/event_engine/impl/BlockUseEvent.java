package net.jdg.disruption.event_engine.impl;

public abstract class BlockUseEvent implements Event {

    protected boolean trigger = false;

    @Override
    public EventCondition getCond() {
        return EventCondition.FALSE;
    }
}
