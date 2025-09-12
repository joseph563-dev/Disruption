package net.jdg.disruption.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jdg.disruption.Disruption;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LevelRenderer.class, priority = 1001)
public class LevelRendererMixin {

    @Inject(method = "renderSky", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/blaze3d/vertex/Tesselator;begin(Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;)Lcom/mojang/blaze3d/vertex/BufferBuilder;", ordinal = 0))
    private void setSunProperties(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci, @Local(ordinal = 1) LocalFloatRef size) {
        size.set(Disruption.sunSize);
        RenderSystem.setShaderTexture(0, Disruption.sunTexture);
    }

    @Inject(method = "renderSky", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/blaze3d/vertex/Tesselator;begin(Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;)Lcom/mojang/blaze3d/vertex/BufferBuilder;", ordinal = 1))
    private void setMoonProperties(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci, @Local(ordinal = 1) LocalFloatRef size) {
        size.set(Disruption.moonSize);
        RenderSystem.setShaderTexture(0, Disruption.moonTexture);
    }
}