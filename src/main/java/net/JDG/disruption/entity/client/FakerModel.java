package net.JDG.disruption.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.custom.FakerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FakerModel<T extends FakerEntity> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION =
                new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Disruption.MOD_ID, "faker"), "main");
        private final ModelPart Body;
        private final ModelPart head;

        public FakerModel(ModelPart root) {
            this.Body = root.getChild("Body");
            this.head = this.Body.getChild("head");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition left_leg = Body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -16.0F, -1.0F));

            PartDefinition right_leg = Body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(20, 16).addBox(-2.3261F, -0.0775F, 0.1877F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -16.0F, -1.0F));

            PartDefinition right_arm = Body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(4.0F, -29.0F, -1.0F));

            PartDefinition cube_r1 = right_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -16.0F, -1.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 14.0F, 1.0F, 0.0F, 0.0F, -0.1745F));

            PartDefinition left_arm = Body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(-4.0F, -29.0F, -1.0F));

            PartDefinition cube_r2 = left_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 16).addBox(-3.0F, -16.0F, -1.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 14.0F, 1.0F, 0.0F, 0.0F, 0.1745F));

            PartDefinition torso = Body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -2.0F, -1.0F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -30.0F, 0.0F));

            PartDefinition head = Body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -15.0F, 0.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -31.0F, -1.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(FakerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.applyHeadRotation(netHeadYaw, headPitch);

            this.animateWalk(FakerAnimations.WALK,limbSwing,limbSwingAmount, 2f, 2.5f);
            this.animate(entity.idleAnimationState, FakerAnimations.IDLE, ageInTicks, 1f);


        }

        private void applyHeadRotation(float headYaw, float headPitch){
            headYaw = Mth.clamp(headYaw, -30f, 30f);
            headPitch = Mth.clamp(headPitch, -25f, 45);

            this.head.yRot = headYaw * ((float)Math.PI / 180f);
            this.head.xRot = headPitch * ((float)Math.PI / 180f);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
            Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color );
        }

    @Override
    public ModelPart root() {
        return Body;
    }
}

