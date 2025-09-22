package net.jdg.disruption.util.world;

import net.jdg.disruption.util.misc.ArrayUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class RandomBlockPosSelector {
    private final Level world;

    private final BlockPos[] cached;

    public RandomBlockPosSelector(Level world, BlockPos min, BlockPos max, BlockArea area) {
        this.world = world;
        this.cached = area.cache(min, max);
    }

    public RandomBlockPosSelector(Level world, BlockPos[] positions) {
        this.world = world;
        this.cached = positions;
    }

    public BlockPos select() {
        return world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, ArrayUtil.pickRandom(cached));
    }
}
