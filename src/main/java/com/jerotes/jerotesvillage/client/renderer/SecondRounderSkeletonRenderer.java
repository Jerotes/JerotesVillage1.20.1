package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSkeletonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SecondRounderSkeletonRenderer extends SkeletonRenderer {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_0.png");
    private static final ResourceLocation LOCATION_ABC = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_1.png");
    private static final ResourceLocation LOCATION_AB = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_2.png");
    private static final ResourceLocation LOCATION_BC = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_3.png");
    private static final ResourceLocation LOCATION_AC = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_4.png");
    private static final ResourceLocation LOCATION_A = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_5.png");
    private static final ResourceLocation LOCATION_B = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_6.png");
    private static final ResourceLocation LOCATION_C = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_7.png");
    private static final ResourceLocation WICKED_RIDICULE_CALLS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wicked_ridicule_calls_second_rounder_skeleton_eye.png");
    public SecondRounderSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
        this.addLayer(new WickedRidiculeCallsEyesLayer(this, WICKED_RIDICULE_CALLS_LOCATION));
    }

    @Override
    protected void scale(AbstractSkeleton entity, PoseStack poseStack, float f) {
        if (((SecondRounderSkeletonEntity)entity).isGiant()) {
            poseStack.scale(1.75f, 1.75f, 1.75f);
        }
        else {
            poseStack.scale(1.05f, 1.05f, 1.05f);
        }
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(AbstractSkeleton entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (((SecondRounderSkeletonEntity)entity).isGiant()) {
            this.shadowRadius = 0.90f;
        }
        else {
            this.shadowRadius = 0.53f;
        }
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
        if (((SecondRounderSkeletonEntity)entity).isSkinA() && ((SecondRounderSkeletonEntity)entity).isSkinB() && ((SecondRounderSkeletonEntity)entity).isSkinC()) {
            return LOCATION_ABC;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinA() && ((SecondRounderSkeletonEntity)entity).isSkinB()) {
            return LOCATION_AB;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinB() && ((SecondRounderSkeletonEntity)entity).isSkinC()) {
            return LOCATION_BC;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinA() && ((SecondRounderSkeletonEntity)entity).isSkinC()) {
            return LOCATION_AC;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinA()) {
            return LOCATION_A;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinB()) {
            return LOCATION_B;
        }
        if (((SecondRounderSkeletonEntity)entity).isSkinC()) {
            return LOCATION_C;
        }
        return LOCATION;
    }
}