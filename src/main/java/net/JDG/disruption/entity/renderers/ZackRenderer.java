package net.JDG.disruption.entity.renderers;


import net.JDG.disruption.Disruption;
import net.JDG.disruption.entity.ZackEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ZackRenderer extends MobRenderer<ZackEntity, PlayerModel<ZackEntity>> {
    public ZackRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
    }


    @Override
    public ResourceLocation getTextureLocation(ZackEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Disruption.MOD_ID, "textures/entity/zack.png");
    }
}
