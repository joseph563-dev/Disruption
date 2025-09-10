package net.jdg.disruption.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class JDGEvents {

        @SubscribeEvent
        private static void onPLayerJoins (PlayerEvent.PlayerLoggedInEvent event) {
                if (event.getEntity() instanceof Player player) {
                        player.sendSystemMessage(Component.literal(player.getName().getString() + " joined the game").withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
                }
        }
}
