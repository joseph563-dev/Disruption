package net.jdg.disruption.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {

    //


    @Inject(method = "registerTextures(Lnet/minecraft/client/Minecraft;)V", at = @At(value = "HEAD"), cancellable = true)
    private static void changeLogo(Minecraft minecraft, CallbackInfo ci) {
        minecraft.getTextureManager().register(ResourceLocation.fromNamespaceAndPath("disruption", "textures/gui/old_mojang_logo.png"), new LoadingOverlay.LogoTexture());
        ci.cancel();
    }

}
