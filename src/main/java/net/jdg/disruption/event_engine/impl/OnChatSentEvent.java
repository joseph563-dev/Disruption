package net.jdg.disruption.event_engine.impl;

import net.jdg.disruption.event_engine.EventTuples;
import net.minecraft.network.chat.Component;

public abstract class OnChatSentEvent implements Event{
    @Override // keep this final it will make things less confusing trust me
    public final void trigger(EventTuples tuples) {
        trigger(EventTuples.stripExtra(tuples), (Component) tuples.extra().getFirst());
    }

    public abstract void trigger(EventTuples tuples, Component message);

    @Override
    public EventCondition getCond() {
        return EventCondition.FALSE;
    }
}
