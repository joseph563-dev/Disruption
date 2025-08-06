package net.JDG.disruption.registries;

import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.ZackEntity;
import net.JDG.disruption.entity.renderers.ZackRenderer;
import net.JDG.disruption.entity.models.FakerModel;
import net.JDG.disruption.entity.renderers.FakerRenderer;
import net.JDG.disruption.entity.models.MagnusModel;
import net.JDG.disruption.entity.renderers.MagnusRenderer;
import net.JDG.disruption.entity.FakerEntity;
import net.JDG.disruption.entity.MagnusEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber
public class JDGEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Disruption.MOD_ID);

    public static final Supplier<EntityType<FakerEntity>> FAKER =
            ENTITY_TYPES.register("faker", () -> EntityType.Builder.of(FakerEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 2.8f).build("faker"));

    public static final Supplier<EntityType<MagnusEntity>> MAGNUS =
            ENTITY_TYPES.register("magnus", () -> EntityType.Builder.of(MagnusEntity::new, MobCategory.MONSTER)
                    .sized(32.75f, 61.4375f).build("magnus"));

    public static final Supplier<EntityType<ZackEntity>> ZACK =
            ENTITY_TYPES.register("zack", () -> EntityType.Builder.of(ZackEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 2f).build("zack"));


    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FakerModel.LAYER_LOCATION, FakerModel::createBodyLayer);
        event.registerLayerDefinition(MagnusModel.LAYER_LOCATION, MagnusModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(JDGEntities.FAKER.get(), FakerRenderer::new);
        event.registerEntityRenderer(JDGEntities.MAGNUS.get(), MagnusRenderer::new);
        event.registerEntityRenderer(JDGEntities.ZACK.get(), ZackRenderer::new);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JDGEntities.FAKER.get(), FakerEntity.createAttributes().build());
        event.put(JDGEntities.MAGNUS.get(), MagnusEntity.createAttributes().build());
        event.put(JDGEntities.ZACK.get(), ZackEntity.createAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
