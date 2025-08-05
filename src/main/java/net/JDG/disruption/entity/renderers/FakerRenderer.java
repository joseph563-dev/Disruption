package net.JDG.disruption.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.FakerEntity;
import net.JDG.disruption.entity.models.FakerModel;
import net.minecraft.client.renderer.MultiBufferSource;
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

    @Override
    public void render(FakerEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
