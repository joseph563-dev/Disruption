package net.JDG.disruption.event;

import net.JDG.disruption.Disruption;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static java.awt.SystemColor.text;

@EventBusSubscriber(modid = Disruption.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

        @SubscribeEvent
        private static void onPLayerJoins (PlayerEvent.PlayerLoggedInEvent event)
        {

                        if (event.getEntity() instanceof Player player){
                                player.sendSystemMessage(Component.literal(player.getName().getString() + " joined the game").withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
                }


        }

}
