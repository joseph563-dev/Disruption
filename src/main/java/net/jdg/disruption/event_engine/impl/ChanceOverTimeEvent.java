package net.jdg.disruption.event_engine.impl;

public abstract class ChanceOverTimeEvent implements Event {

    private double chance;
    private final double start_chance;
    private final double increase_per_tick;

    public ChanceOverTimeEvent(double start_chance, double increase_per_tick) {
        chance = start_chance;
        this.start_chance = start_chance;
        this.increase_per_tick = increase_per_tick;
    }

    public void reset() {
        chance = start_chance;
    }

    @Override
    public EventCondition getCond() {
        return new EventCondition((t) -> {
            if (Math.random() < chance) {
                return true;
            } else {
                chance += increase_per_tick;
                return false;
            }
        });
    }
}
