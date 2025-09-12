package net.jdg.disruption.event_engine.event;

import net.jdg.disruption.event_engine.EventTuples;
import net.jdg.disruption.event_engine.impl.PlayerJoinsEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class PlayerJoinMessageEvent extends PlayerJoinsEvent {
    @Override
    public void trigger(EventTuples tuples) {
        var player = tuples.playerEntity();
        if (player != null) player.sendSystemMessage(Component.literal(player.getName().getString() + " joined the game").withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
    }
}
