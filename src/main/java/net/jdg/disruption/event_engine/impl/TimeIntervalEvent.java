package net.jdg.disruption.event_engine.impl;

public abstract class TimeIntervalEvent implements Event {

    private long delay = 0;
    private final long waitTime;


    public TimeIntervalEvent(long waitTime) {
        this.waitTime = waitTime - 1;
    }

    public void reset(){
        delay = waitTime;
    }

    @Override
    public EventCondition getCond() {
        return new EventCondition(t -> {
            if (delay > 0) {
                delay -= 1;
                return false;
            } else {
                delay = waitTime;
                return true;
            }
        });
    }
}
