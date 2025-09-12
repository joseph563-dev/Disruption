package net.jdg.disruption.event_engine.impl;

public abstract class RandomEvent implements Event {

    protected final double chance;

    public RandomEvent(double chance) {
        this.chance = chance;
    }

    @Override
    public EventCondition getCond() {
        return new EventCondition((t) -> Math.random() < chance);
    }
    
}
