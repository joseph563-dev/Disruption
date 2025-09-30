package net.jdg.disruption.util.world;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.function.Function;

public class DirectionCast {

    private final BlockPos origin;

    public DirectionCast(BlockPos origin) {
        this.origin = origin;
    }

    public record BlockData(BlockPos pos, BlockState state) {}

    public BlockData cast(Level world, Direction direction, int maxLength) {
        var i = 0;
        while (i < maxLength) {
            var ofs = origin.relative(direction, i);
            var state = world.getBlockState(ofs);
            if (state.isAir()) {
                return new BlockData(ofs, state);
            }
            i += 1;
        }
        var ofs = origin.relative(direction, i);
        var state = world.getBlockState(ofs);
        return new BlockData(ofs, state);
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    public static MultiCast multicast(Level world, Direction startDir, BlockPos origin, int maxLength) {
        return new MultiCast(world, maxLength, origin, startDir);
    }

    private static class MultiCast {
        private BlockData multicastData;
        private BlockPos origin;
        private final BlockPos originalOrigin;
        private final int maxLength;
        private ArrayList<BlockPos> visited = new ArrayList<>();

        private final Level world;

        public MultiCast(Level world, int maxLength, BlockPos origin, Direction startDir) {
            this.world = world;
            this.maxLength = maxLength;
            this.origin = origin;
            this.originalOrigin = origin;
            this.multicastData = cast(startDir);
        }

        /**
         * @param dirProvider function to provide the cast direction for each iteration
         * @param cond return true to retrieve the result
         * @param allowPreviouslyVisited if false, will stop iteration if a position is encountered more than once
         * @return block data at end of cast
         */
        public BlockData until(Function<BlockData, Direction> dirProvider, Function<BlockData, Boolean> cond, boolean allowPreviouslyVisited) {
            var data = multicastData;
            while (!cond.apply(cast(dirProvider.apply(data)))) {
                data = cast(dirProvider.apply(data));
                origin = data.pos();
                if (!allowPreviouslyVisited && visited.contains(data.pos())) break;
            }
            origin = originalOrigin;
            return data;
        }

        public MultiCast next(Function<BlockData, Direction> cond) {
            multicastData = cast(cond.apply(multicastData));
            origin = multicastData.pos();
            return this;
        }

        public MultiCast next(Direction dir) {
            multicastData = cast(dir);
            origin = multicastData.pos();
            return this;
        }

        public BlockData getMulticastData() {
            origin = originalOrigin;
            return multicastData;
        }

        public boolean isCurPosAlreadyVisited() {
            return !visited.contains(origin);
        }

        private BlockData cast(Direction direction) {
            var i = 0;
            while (i < maxLength) {
                var ofs = origin.relative(direction, i);
                var state = world.getBlockState(ofs);
                if (state.isAir()) {
                    return new BlockData(ofs, state);
                }
                i += 1;
            }
            var ofs = origin.relative(direction, i);
            var state = world.getBlockState(ofs);
            return new BlockData(ofs, state);
        }

    }

}
