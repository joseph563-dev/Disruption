package net.jdg.disruption.registries;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.jdg.disruption.Disruption;
import net.jdg.disruption.event_engine.EventEngine;
import net.jdg.disruption.event_engine.EventTuples;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.List;

import static net.minecraft.commands.Commands.*;

@EventBusSubscriber
public class JDGCommands {

    @SubscribeEvent
    public static void init(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        if (Disruption.IS_DEV) {
            event.getDispatcher().register(dispatcher.register(literal("itd")
                    .then(argument("event", StringArgumentType.string()).suggests((a, b) -> EventEngine.suggestionProvider.getSuggestions(a, b))
                            .executes(context -> {
                                final String value = StringArgumentType.getString(context, "event");
                                var hasEvent = EventEngine.suggestionProvider.eventMap.containsKey(value);
                                if (hasEvent) {
                                    context.getSource().sendSystemMessage(Component.literal("Attempting to run %s.".formatted(value)));
                                    Disruption.eventEngine.triggerEvent(value, new EventTuples(context.getSource().getLevel(), context.getSource().getEntity().getOnPos(), context.getSource().getLevel().getBlockState(context.getSource().getEntity().getOnPos()), context.getSource().getLevel().getBlockEntity(context.getSource().getEntity().getOnPos()), context.getSource().getPlayer(), List.of()));
                                } else {
                                    context.getSource().sendSystemMessage(Component.literal("Invalid Event: %s".formatted(value)));
                                }
                                return hasEvent ? 1 : 0;
                            }))).createBuilder());
            event.getDispatcher().register(dispatcher.register(literal("event")
                    .then(argument("event", StringArgumentType.string()).suggests(EventEngine.suggestionProvider)
                            .executes(context -> {
                                final String value = StringArgumentType.getString(context, "event");
                                var hasEvent = EventEngine.suggestionProvider.eventMap.containsKey(value);
                                if (hasEvent) {
                                    context.getSource().sendSystemMessage(Component.literal("Attempting to run %s.".formatted(value)));
                                    Disruption.eventEngine.triggerEvent(value, new EventTuples(context.getSource().getLevel(), context.getSource().getEntity().getOnPos(), context.getSource().getLevel().getBlockState(context.getSource().getEntity().getOnPos()), context.getSource().getLevel().getBlockEntity(context.getSource().getEntity().getOnPos()), context.getSource().getPlayer(), List.of()));
                                } else {
                                    context.getSource().sendSystemMessage(Component.literal("Invalid Event: %s".formatted(value)));
                                }
                                return hasEvent ? 1 : 0;
                            }))).createBuilder());
        }
    }




}
