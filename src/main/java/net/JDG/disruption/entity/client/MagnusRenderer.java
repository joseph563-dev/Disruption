package net.JDG.disruption.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.custom.MagnusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MagnusRenderer extends MobRenderer<MagnusEntity, MagnusModel<MagnusEntity>> {
    public MagnusRenderer(EntityRendererProvider.Context context) {
        super(context, new MagnusModel<>(context.bakeLayer(MagnusModel.LAYER_LOCATION)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MagnusEntity etity) {
        return ResourceLocation.fromNamespaceAndPath(Disruption.MOD_ID, "textures/entity/magnus.png");
    }

    @Override
    public void render(MagnusEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
