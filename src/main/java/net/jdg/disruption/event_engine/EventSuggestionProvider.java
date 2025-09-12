package net.jdg.disruption.event_engine;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jdg.disruption.event_engine.impl.Event;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class EventSuggestionProvider implements SuggestionProvider<CommandSourceStack> {

    public HashMap<String, Event> eventMap = new HashMap<>();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        if (eventMap.isEmpty()) {
            return Suggestions.empty();
        }
        for (String event : eventMap.keySet()) {
            if (event != null && SharedSuggestionProvider.matchesSubStr(builder.getRemaining(), event)) {
                builder.suggest(event);
            }
        }
        return builder.buildFuture();
    }
}
