package net.jdg.disruption.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileNotFoundException;
import java.io.InputStream;


@Mixin(targets = "net/minecraft/client.gui/screens/LoadingOverlay$LogoTexture", priority = 1002)
@Debug(export = true)
public class LogoTextureMixin {

    //
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/SimpleTexture;<init>(Lnet/minecraft/resources/ResourceLocation;)V"), index = 2)
    private static ResourceLocation injected(int value) {
        return ResourceLocation.fromNamespaceAndPath("disruption", "textures/gui/old_mojang_logo.png");
    }

    @Inject(method = "getTextureImage(Lnet/minecraft/server/packs/resources/ResourceManager;)Lnet/minecraft/client/renderer/texture/SimpleTexture$TextureImage;",
            at = @At(shift = At.Shift.BEFORE,value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;toString()Ljava/lang/String;"),
            cancellable = true)
    private void changeLogo(ResourceManager resourceManager, CallbackInfoReturnable<SimpleTexture.TextureImage> cir, @Local LocalRef<IoSupplier<InputStream>> supplier) {
        supplier.set(() -> Minecraft.getInstance().getResourceManager().getResource(ResourceLocation.fromNamespaceAndPath("disruption", "textures/gui/old_mojang_logo.png")).get().open());
        cir.setReturnValue(new SimpleTexture.TextureImage(new FileNotFoundException(ResourceLocation.fromNamespaceAndPath("disruption", "textures/gui/old_mojang_logo.png").toString())));

    }
}
