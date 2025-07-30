package net.JDG.disruption.entity;

import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.custom.FakerEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Disruption.MOD_ID);

    public static final Supplier<EntityType<FakerEntity>> FAKER =
            ENTITY_TYPES.register("faker", () -> EntityType.Builder.of(FakerEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 0.35f).build("faker"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
