package net.jdg.disruption.mixins;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldOpenFlows.class, priority = 1001)
public class NarNarMixin {

    @ModifyVariable(method = "openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V", at = @At(value = "STORE"), ordinal = 1)
    public boolean loadLevel_bl2(boolean isNotLifeCycleStable) {
        return isNotLifeCycleStable;
    }

    @Inject(method = "confirmWorldCreation", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;", remap = false), cancellable = true)
    private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, boolean skipWarnings, CallbackInfo ci) {
        loadWorld.run();
        ci.cancel();
    }
}