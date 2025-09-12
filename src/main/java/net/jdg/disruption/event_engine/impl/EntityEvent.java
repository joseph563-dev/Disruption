package net.jdg.disruption.event_engine.impl;

import net.jdg.disruption.event_engine.EventTuples;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;

public class EntityEvent extends ChanceOverTimeEvent {

    private final int min_required_block_space;
    private final double distance;
    private final EntityType<?> entityType;

    public EntityEvent(double start_chance, double increase_per_tick, EntityType<?> entityType, double distance, int min_required_block_space) {
        super(start_chance, increase_per_tick);
        this.distance = distance;
        this.min_required_block_space = min_required_block_space;
        this.entityType = entityType;
    }

    @Override
    public void trigger(EventTuples tuples) {
        var pos = getRandomPosition(tuples);
        if (pos == null) {
            for (int i = 0; i < 100; ++i) {
                var attempt_pos = getRandomPosition(tuples);
                if (attempt_pos != null) {
                    pos = attempt_pos;
                    break;
                }
            }
        }
        if (pos != null) {
            var entity = entityType.create(tuples.world());
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            tuples.world().addFreshEntity(entity);
        }
    }

    public BlockPos getRandomPosition(EventTuples tuples) {
        var players = tuples.world().getServer().getPlayerList().getPlayers();
        var player = players.get(Mth.nextInt(RandomSource.create(), 0, players.size() - 1));
        var sin = Math.sin(Mth.nextFloat(RandomSource.create(), -Mth.PI, Mth.PI)) * distance;
        var cos = Math.cos(Mth.nextFloat(RandomSource.create(), -Mth.PI, Mth.PI)) * distance;
        for (int i = 0; i < 10; ++i) {
           var ofs = new BlockPos((int) Math.round(sin), i, (int) Math.round(cos));
           var pos = player.getOnPos().offset(ofs);
           if (tuples.world().getBlockState(pos).isAir()) {
               for (int air_check = 0; air_check < min_required_block_space; air_check++) {
                   var pos2 = pos.offset(new Vec3i(0, air_check, 0));
                   if (!tuples.world().getBlockState(pos2).isAir()) {
                       return null;
                   }
               }
               return pos;
           }
        }
        return null;
    }

}
