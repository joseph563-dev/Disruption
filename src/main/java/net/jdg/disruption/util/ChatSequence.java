package net.jdg.disruption.util;

import com.mojang.datafixers.util.Pair;
import net.jdg.disruption.event_engine.EventEngine;
import net.minecraft.network.chat.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;

public class ChatSequence {


    private final Pair<Component, Integer[]>[] sequence;

    private int timeLeft = 0;
    private final String id;
    private int step = 0;
    @SuppressWarnings("unchecked")
    private ChatSequence(ArrayList<Pair<Component, Integer[]>> sequence, String id, boolean ticksByDefault) {
        if (ticksByDefault) EventEngine.chatSequences.put(id, this);
        this.sequence = (Pair<Component, Integer[]>[]) sequence.toArray(new Pair<?>[]{});
        step = sequence.size();
        if (!sequence.isEmpty()) {
            timeLeft = sequence.getFirst().getSecond()[0] + Mth.nextInt(RandomSource.create(), -sequence.getFirst().getSecond()[1], sequence.getFirst().getSecond()[1]);
        }
        this.id = id;
    }

    public void tick(ServerPlayer player) {
        if (step < sequence.length) {
            if (timeLeft > 0) timeLeft -= 1;
            else {
                player.sendSystemMessage(sequence[step].getFirst());
                step += 1;
                timeLeft = sequence[step].getSecond()[0] + Mth.nextInt(RandomSource.create(), -sequence[step].getSecond()[1], sequence[step].getSecond()[1]);
            }
        }
    }



    public void start() {
        if (sequence.length > 0) {
            timeLeft = sequence[step].getSecond()[0] + Mth.nextInt(RandomSource.create(), -sequence[step].getSecond()[1], sequence[step].getSecond()[1]);
        }
        step = 0;
    }


    public static class ChatSequenceBuilder {

        private final ArrayList<Pair<Component, Integer[]>> sequence = new ArrayList<>();

        private ChatSequenceBuilder() {
        }

        public static ChatSequenceBuilder begin() {
            return new ChatSequenceBuilder();
        }

        public ChatSequenceBuilder add(String str, int delay, int delayVariability) {
            sequence.add(new Pair<>(Component.literal(str), new Integer[]{delay, delayVariability}));
            return this;
        }

        public ChatSequenceBuilder add(String str, int delay) {
            return add(str, delay, 0);
        }
        public ChatSequenceBuilder add(Component txt, int delay, int delayVariability) {
            sequence.add(new Pair<>(txt, new Integer[]{delay, delayVariability}));
            return this;
        }

        public ChatSequenceBuilder add(Component txt, int delay) {
            return add(txt, delay, 0);
        }

        public ChatSequence build() {
            return new ChatSequence(sequence, "",false);
        }

        public ChatSequence build(String globalId) {
            return new ChatSequence(sequence, globalId,true);
        }


    }

}
