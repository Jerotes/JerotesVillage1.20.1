package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwilderness_giant_skeleton;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WildernessGiantSkeletonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class WildernessGiantSkeletonRenderer extends HumanoidMobRenderer<WildernessGiantSkeletonEntity, Modelwilderness_giant_skeleton<WildernessGiantSkeletonEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_giant_skeleton.png");
    public WildernessGiantSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelwilderness_giant_skeleton(context.bakeLayer(Modelwilderness_giant_skeleton.LAYER_LOCATION)), 0.90f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(WildernessGiantSkeletonEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.75f, 1.75f, 1.75f);
        super.scale(entity, poseStack, f);
    }
    @Override
    public ResourceLocation getTextureLocation(WildernessGiantSkeletonEntity entity) {
        return LOCATION;
    }
}