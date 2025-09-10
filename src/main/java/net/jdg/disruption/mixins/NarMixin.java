package net.jdg.disruption.mixins;

import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PrimaryLevelData.class, priority = 1001)
public class NarMixin {

    @Shadow private boolean confirmedExperimentalWarning;

    @Inject(method = "<init>*", at = @At(value = "TAIL"))
    private void PrimaryLevelData(CallbackInfo ci) {
        this.confirmedExperimentalWarning = true;
    }

    @Inject(method = "withConfirmedWarning(Z)Lnet/minecraft/world/level/storage/PrimaryLevelData;", at = @At(value = "RETURN"))
    private void withConfirmedWarning(boolean confirmedWarning, CallbackInfoReturnable<PrimaryLevelData> cir) {
        this.confirmedExperimentalWarning = true;
    }

    @Inject(method = "hasConfirmedExperimentalWarning()Z", at = @At(value = "HEAD"), cancellable = true)
    public void hasConfirmedExperimentalWarning(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}