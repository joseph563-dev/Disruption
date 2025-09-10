package net.jdg.disruption.entity.renderers;

import net.jdg.disruption.Disruption;
import net.jdg.disruption.entity.MagnusEntity;
import net.jdg.disruption.entity.models.MagnusModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MagnusRenderer extends MobRenderer<MagnusEntity, MagnusModel<MagnusEntity>> {
    public MagnusRenderer(EntityRendererProvider.Context context) {
        super(context, new MagnusModel<>(context.bakeLayer(MagnusModel.LAYER_LOCATION)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MagnusEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Disruption.MOD_ID, "textures/entity/magnus.png");
    }
}
