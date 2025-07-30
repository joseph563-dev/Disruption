package net.JDG.disruption.event;

import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.ModEntities;
import net.JDG.disruption.entity.client.FakerModel;
import net.JDG.disruption.entity.custom.FakerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


@EventBusSubscriber(modid = Disruption.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(FakerModel.LAYER_LOCATION, FakerModel::createBodyLayer);

        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(ModEntities.FAKER.get(), FakerEntity.createAttributes().build());
        }
    }

