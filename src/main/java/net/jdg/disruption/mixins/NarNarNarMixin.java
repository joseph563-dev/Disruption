package net.jdg.disruption.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public abstract class NarNarNarMixin extends Entity {

    public NarNarNarMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "getDropItem", at = @At("HEAD"), cancellable = true)
    private void replaceBoatDrops(CallbackInfoReturnable<Item> cir) {
        if (this.level().isClientSide) return;

        Level level = this.level();
        BlockPos pos = this.blockPosition();

        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                new ItemStack(Items.STICK, 2)));
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                new ItemStack(Items.OAK_PLANKS, 3)));

        cir.cancel();
    }
}