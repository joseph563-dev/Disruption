package net.jdg.disruption.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class CensorQuad {

    /**
     * @param box box to get the bounds of
     * @return X and Y coords of the rectangle with its size as Z and W
     */
    public static Vector4f getBoundingBoxProjectedRect(AABB box) {
        var client = Minecraft.getInstance();
        var width = client.getWindow().getGuiScaledWidth();
        var height = client.getWindow().getGuiScaledHeight();
        var cam = client.gameRenderer.getMainCamera();
        var verts = getBoxVerts(box);
        float minXPos = Float.MAX_VALUE, minYPos = Float.MAX_VALUE;
        float maxXPos = Float.MIN_VALUE, maxYPos = Float.MIN_VALUE;
        int failCount = 0;
        for (int i = 0; i < 8; ++i) {
            var vert = verts[i];
            var screenVert = new Vector4f(0,0,0,0);
            var plr = client.player;
            var world = client.level;
            if (plr != null && world != null) {
                var pPos = plr.getEyePosition();
                var ofs = new Vec3(new Vector3f(new Vector3f(vert).mul(1,-1,1)).add(pPos.toVector3f()));
                var raycast = world.clip(new ClipContext(pPos, ofs, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, plr));
                if (raycast.getType() != HitResult.Type.MISS) { // if it misses we know theres no obstruction for this vert
                    failCount += 1;
                }
            }
            var r = cam.rotation();
            var projMat = client.gameRenderer.getProjectionMatrix(client.options.fov().get());
            vert.rotate(new Quaternionf(r.x, -r.y, r.z, r.w));
            projMat.project(vert, new int[]{0,0, width, height}, screenVert);
            if (screenVert.z > 1) return new Vector4f(0);
            minXPos = Math.min(minXPos, screenVert.x);
            minYPos = Math.min(minYPos, screenVert.y);
            maxXPos = Math.max(maxXPos, screenVert.x);
            maxYPos = Math.max(maxYPos, screenVert.y);
        }
        if (failCount == 8) return new Vector4f(0,0,0,0);
        var ysize = Math.abs(maxYPos - minYPos);
        var xsize = Math.abs(maxXPos - minXPos);
        var pastVerticalPositive = minYPos >= height;
        var pastHorizontalPositive = minXPos >= width;
        var pastVerticalNegative = minYPos + ysize <= 0;
        var pastHorizontalNegative = minXPos + xsize <= 0;
        if (pastVerticalPositive || pastHorizontalPositive || pastHorizontalNegative || pastVerticalNegative) {
            return new Vector4f(0,0,0,0);
        }
        return new Vector4f(minXPos + xsize, minYPos + ysize, xsize + Math.min(0, minXPos), ysize + Math.min(0, minYPos));
    }

    private static Vector3f[] getBoxVerts(AABB box) {
        float x = (float) box.min(Direction.Axis.X);
        float y = (float) box.min(Direction.Axis.Y) * -1; // negation here somehow simplifies some of the other code idk
        float z = (float) box.min(Direction.Axis.Z);
        float x1 = (float) box.max(Direction.Axis.X);
        float y1 = (float) box.max(Direction.Axis.Y) * -1;
        float z1 = (float) box.max(Direction.Axis.Z);
        return new Vector3f[]{
                new Vector3f(x, y, z),
                new Vector3f(x1, y1, z1),
                new Vector3f(x1,y,z),
                new Vector3f(x,y1,z),
                new Vector3f(x,y,z1),
                new Vector3f(x1,y1,z),
                new Vector3f(x,y1,z1),
                new Vector3f(x1,y,z1)
        };
    }
    /**
     * @param context draw context provided by gui drawing
     * @param box make sure the box is not offset by an entity's position
     * @param snapSize adds to the minimum size of the quad
     * @param padding adds to the minimum size of the quad
     */
    public static void render(GuiGraphics context, AABB box, int snapSize, int padding) {
        var coords = getBoundingBoxProjectedRect(box);
        var w = (int) coords.z + padding + (snapSize / 2);
        var h = (int) coords.w + padding + (snapSize / 2);
        var x = step((int) coords.x - w ,snapSize) + (coords.x - w > 0 ? snapSize : 0);
        var y = step((int) coords.y - h, snapSize) + (coords.y - h > 0 ? snapSize : 0);
        if (x + w > 0 && y + h > 0) context.blit(ResourceLocation.parse("disruption:textures/gui/censor_quad.png"), x, y, 0,0, w, h, 16,16);
    }

    private static int step(int x, int by) {
        return x - (x % by);
    }
}
