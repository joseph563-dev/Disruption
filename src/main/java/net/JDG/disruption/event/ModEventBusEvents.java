package net.JDG.disruption.event;

import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.ModEntities;
import net.JDG.disruption.entity.client.FakerModel;
import net.JDG.disruption.entity.client.MagnusModel;
import net.JDG.disruption.entity.custom.FakerEntity;
import net.JDG.disruption.entity.custom.MagnusEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


@EventBusSubscriber(modid = Disruption.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(FakerModel.LAYER_LOCATION, FakerModel::createBodyLayer);
            event.registerLayerDefinition(MagnusModel.LAYER_LOCATION, MagnusModel::createBodyLayer);

        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.FAKER.get(), FakerEntity.createAttributes().build());
            event.put(ModEntities.MAGNUS.get(), MagnusEntity.createAttributes().build());



        }

    }

