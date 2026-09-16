package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ClothesLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelanimated_chain;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.AnimatedChainEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AnimatedChainRenderer extends MobRenderer<AnimatedChainEntity, Modelanimated_chain<AnimatedChainEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/animated_chain.png");
    private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
    public AnimatedChainRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelanimated_chain(context.bakeLayer(Modelanimated_chain.LAYER_LOCATION)), 0f);
        this.addLayer(new ClothesLayer(this, new Modelanimated_chain(context.bakeLayer(Modelanimated_chain.LAYER_LOCATION)), LOCATION));
    }

    @Override
    protected void scale(AnimatedChainEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(entity.getSize(), entity.getSize(), entity.getSize());
        super.scale(entity, poseStack, f);
    }

    @Override
    protected boolean isShaking(AnimatedChainEntity entity) {
        return entity.isAlive() && entity.getPrisoner() != null || super.isShaking(entity);
    }

    @Override
    protected float getFlipDegrees(AnimatedChainEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedChainEntity entity) {
        return NULL_LOCATION;
    }

}