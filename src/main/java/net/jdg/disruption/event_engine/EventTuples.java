package net.jdg.disruption.event_engine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EventTuples(Level world, @Nullable BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, @Nullable ServerPlayer playerEntity, List<Object> extra) {

    public static EventTuples stripExtra(EventTuples tuples) {
        return new EventTuples(tuples.world, tuples.pos, tuples.state, tuples.blockEntity, tuples.playerEntity, List.of());
    }

}
