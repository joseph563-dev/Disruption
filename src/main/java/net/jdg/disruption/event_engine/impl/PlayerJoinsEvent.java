package net.jdg.disruption.event_engine.impl;


import net.jdg.disruption.event_engine.EventTuples;

public abstract class PlayerJoinsEvent implements Event {



    @Override
    public EventCondition getCond() {
        return EventCondition.FALSE;
    }
}
