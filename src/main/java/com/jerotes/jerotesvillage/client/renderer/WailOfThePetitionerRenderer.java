package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.WailOfThePetitionerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WailOfThePetitionerRenderer extends EntityRenderer<WailOfThePetitionerEntity> {
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    public WailOfThePetitionerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WailOfThePetitionerEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(WailOfThePetitionerEntity entity) {
        return GLOW_LOCATION;
    }
}
