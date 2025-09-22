package net.jdg.disruption.event_engine;


import net.jdg.disruption.Disruption;
import net.jdg.disruption.event_engine.event.PlayerJoinMessageEvent;
import net.jdg.disruption.event_engine.impl.*;
import net.jdg.disruption.util.ChatSequence;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EventBusSubscriber
public class EventEngine {
    private ArrayList<Event> events = new ArrayList<>();
    public static EventSuggestionProvider suggestionProvider;
    public static ArrayList<String> eventNames = new ArrayList<>();
    public static HashMap<String, ChatSequence> chatSequences = new HashMap<>();

    public EventEngine(IEventBus bus) {
        registerEvents();
    }


    public void registerEvents() {
        suggestionProvider = new EventSuggestionProvider(); // must go first
        registerEvent(new PlayerJoinMessageEvent());
    }


    public void registerEvent(Event event) {
        events.add(event);
        var name = event.getClass().getSimpleName();
        suggestionProvider.eventMap.put(name, event);
        eventNames.add(name);
    }

    public void triggerEvent(String eventName, EventTuples tuples) {
        var event = suggestionProvider.eventMap.get(eventName);
        event.trigger(tuples);
    }

    @SubscribeEvent
    public static void chatSent(ServerChatEvent serverChatEvent) {
        for (Event event : Disruption.eventEngine.events) {
            if (event instanceof OnChatSentEvent chatSentEvent) {
                var player = serverChatEvent.getPlayer();
                chatSentEvent.trigger(new EventTuples(player.level(), player.getOnPos(),null, null,player, List.of(serverChatEvent.getMessage())));
            }
        }
    }

    @SubscribeEvent
    public static void playerJoined(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        var player = playerLoggedInEvent.getEntity();
        for (Event event : Disruption.eventEngine.events) {
            if (event instanceof PlayerJoinsEvent blockBrokenEvent) {
                blockBrokenEvent.trigger(new EventTuples(player.level(), null, null, null, (ServerPlayer) player, List.of()));
            }
        }
    }
    @SubscribeEvent
    public static void useBlock(UseItemOnBlockEvent blockEvent) {
        var player = blockEvent.getPlayer();
        var world = blockEvent.getLevel();
        var hitResult = blockEvent.getUseOnContext();
        for (Event event : Disruption.eventEngine.events) {
            if (event instanceof BlockUseEvent blockBrokenEvent) {
                if (player == null || player.getServer() == null) {
                    return;
                }
                blockBrokenEvent.trigger(new EventTuples(world, hitResult.getClickedPos(), world.getBlockState(hitResult.getClickedPos()), world.getBlockEntity(hitResult.getClickedPos()), (ServerPlayer) player, List.of(hitResult, blockEvent.getHand())));
            }
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockDropsEvent blockDropsEvent) {
        var breaker = blockDropsEvent.getBreaker();
        var world = blockDropsEvent.getLevel();
        var blockPos = blockDropsEvent.getPos();
        var blockState = blockDropsEvent.getState();
        var blockEntity = blockDropsEvent.getBlockEntity();
        for (Event event : Disruption.eventEngine.events) {
            if (event instanceof BlockBrokenEvent blockBrokenEvent) {
                if (breaker instanceof ServerPlayer p) blockBrokenEvent.trigger(new EventTuples(world, blockPos, blockState, blockEntity, p, List.of()));
            }
        }
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Pre serverTickEvent) {
        for (ResourceKey<Level> dim : serverTickEvent.getServer().levelKeys()) {
            var serverWorld = serverTickEvent.getServer().getLevel(dim);
            if (serverWorld == null) return;
            for (ServerPlayer entity : serverWorld.getPlayers((a) -> true)) {
                for (ChatSequence sequence : chatSequences.values()) sequence.tick(entity);
                for (Event event : Disruption.eventEngine.events) {
                    if (event instanceof TimeEvent timeEvent) {
                        var tuples = new EventTuples(serverWorld, null, null, null, entity, List.of());
                        if (event.shouldRun(tuples)) {
                            timeEvent.trigger(tuples);
                        }
                    }
                    if (event instanceof RandomEvent randomEvent) {
                        var tuples = new EventTuples(serverWorld, null, null, null, entity, List.of());
                        if (event.shouldRun(tuples)) {
                            randomEvent.trigger(tuples);
                        }
                    }
                    if (event instanceof ChanceOverTimeEvent randomEvent) {
                        var tuples = new EventTuples(serverWorld, null, null, null, entity, List.of());
                        if (event.shouldRun(tuples)) {
                            for (Event event2 : Disruption.eventEngine.events) {
                                if (event2 instanceof ChanceOverTimeEvent randomEvent2) {
                                    randomEvent2.reset();
                                }
                            }
                            randomEvent.trigger(tuples);
                        }
                    }
                }
            }
        }
    }
}
