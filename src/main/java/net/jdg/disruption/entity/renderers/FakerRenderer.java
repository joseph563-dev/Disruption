package net.jdg.disruption.entity.renderers;

import net.jdg.disruption.Disruption;
import net.jdg.disruption.entity.FakerEntity;
import net.jdg.disruption.entity.models.FakerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FakerRenderer extends MobRenderer<FakerEntity, FakerModel<FakerEntity>> {
    public FakerRenderer(EntityRendererProvider.Context context) {
        super(context, new FakerModel<>(context.bakeLayer(FakerModel.LAYER_LOCATION)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(FakerEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Disruption.MOD_ID, "textures/entity/faker.png");
    }
}