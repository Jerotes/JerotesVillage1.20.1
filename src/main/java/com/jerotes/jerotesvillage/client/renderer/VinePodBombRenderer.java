package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvine_pod_bomb;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VinePodBombRenderer extends MobRenderer<VinePodBombEntity, Modelvine_pod_bomb<VinePodBombEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/vine_pod_bomb.png");

    public VinePodBombRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelvine_pod_bomb(context.bakeLayer(Modelvine_pod_bomb.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected void scale(VinePodBombEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(VinePodBombEntity cube) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(VinePodBombEntity cube) {
        return LOCATION;
    }
}
