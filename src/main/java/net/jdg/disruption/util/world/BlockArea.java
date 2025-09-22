package net.jdg.disruption.util.world;

import com.mojang.datafixers.util.Pair;
import net.jdg.disruption.util.misc.ArrayUtil;
import net.jdg.disruption.util.misc.LogicalOperator;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public enum BlockArea {
    cuboid,
    spheroid,
    octahedral, // aka manhatten distance
    cylinderX,
    cylinderY,
    cylinderZ;

    public BlockPos[] logicalCache(LogicalOperator type, Pair<BlockPos, BlockPos>... positions) {
        var list = new ArrayList<BlockPos[]>();
        for (Pair<BlockPos, BlockPos> pair : positions) {
            var min = pair.getFirst();
            var max = pair.getSecond();
            list.add(cache(min.getX(),min.getY(),min.getZ(),max.getX(),max.getY(),max.getZ()));
        }
        return ArrayUtil.logicalOp(type,list.toArray(new BlockPos[][]{}));
    }

    public BlockPos[] cache(BlockPos min, BlockPos max) {
        return cache(min.getX(),min.getY(),min.getZ(),max.getX(),max.getY(),max.getZ());
    }

    public BlockPos[] cache(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        var iter = new CuboidBlockIterator(minX, minY, minZ, maxX, maxY, maxZ);
        var arr = new BlockPos[Math.abs(maxX - minX) * Math.abs(maxY - minY) * Math.abs(maxZ - minZ)];
        var currentArrPos = 0;
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (inArea(pos, new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
                arr[currentArrPos] = pos;
                currentArrPos += 1;
            }
        }
        return ArrayUtil.resizeArray(arr, currentArrPos);
    }

    public void forEach(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Consumer<BlockPos> func) {
        var iter = new CuboidBlockIterator(minX, minY, minZ, maxX, maxY, maxZ);
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            func.accept(pos);
        }
    }

    public void forEach(BlockPos min, BlockPos max, Consumer<BlockPos> func) {
        forEach(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ(), func);
    }

    public void iterateWhile(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Function<BlockPos, Boolean> func) {
        var iter = new CuboidBlockIterator(minX, minY, minZ, maxX, maxY, maxZ);
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (inArea(pos, new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
                if (!func.apply(pos)) break;
            }
        }
    }
    public void iterateWhile(BlockPos min, BlockPos max, Function<BlockPos, Boolean> func) {
        iterateWhile(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ(), func);
    }
    public boolean inArea(BlockPos pos, BlockPos min, BlockPos max) {
        var pX = pos.getX();
        var pY = pos.getY();
        var pZ = pos.getZ();
        var miX = min.getX();
        var miY = min.getY();
        var miZ = min.getZ();
        var mxX = max.getX();
        var mxY = max.getY();
        var mxZ = max.getZ();
        var lX = Math.abs(mxX - miX);
        var lY = Math.abs(mxY - miY);
        var lZ = Math.abs(mxZ - miZ);
        double cX = (double) lX / 2 + miX;
        double cY = (double) lY / 2 + miY;
        double cZ = (double) lZ / 2 + miZ;
        switch (this) {
            case cuboid -> {
                return pX <= mxX && pY <= mxY && pZ <= mxZ && pX >= miX && pY >= miY && pZ >= miZ;
            }
            case spheroid -> {
                var a = Math.sqrt((pX - cX) * (pX - cX)) <= lX / 2d;
                var b = Math.sqrt((pY - cY) * (pY - cY)) <= lY / 2d;
                var c = Math.sqrt((pZ - cZ) * (pZ - cZ)) <= lZ / 2d;
                return a & b & c;
            }
            case octahedral -> {
                var a = Math.abs(pX - cX) <= lX;
                var b = Math.abs(pY - cY) <= lY;
                var c = Math.abs(pZ - cZ) <= lZ;
                return a & b & c;

            }
            case cylinderX -> {
                return pX <= mxX && pX >= miX && Math.sqrt((pY - cY) * (pY - cY)) <= lY / 2d && Math.sqrt((pZ - cZ) * (pZ - cZ)) <= lZ / 2d;
            }
            case cylinderY -> {
                return pY <= mxY && pY >= miY && Math.sqrt((pZ - cZ) * (pZ - cZ)) <= lZ / 2d && Math.sqrt((pX - cX) * (pX - cX)) <= lX / 2d;
            }
            case cylinderZ -> {
                return pZ >= miZ && pZ <= mxZ && Math.sqrt((pY - cY) * (pY - cY)) <= lY / 2d && Math.sqrt((pX - cX) * (pX - cX)) <= lX / 2d;
            }
        }
        return false;
    }

}
