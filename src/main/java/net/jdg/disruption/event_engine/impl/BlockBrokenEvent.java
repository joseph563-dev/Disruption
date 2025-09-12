package net.jdg.disruption.event_engine.impl;

import net.jdg.disruption.event_engine.EventTuples;

public abstract class BlockBrokenEvent implements Event {

    protected boolean trigger = false;

    @Override
    public boolean shouldRun(EventTuples tuples) {
        return false;
    }
}
