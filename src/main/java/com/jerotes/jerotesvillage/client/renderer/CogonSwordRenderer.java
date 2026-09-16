package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ClothesLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcogon_sword;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.CogonSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CogonSwordRenderer extends MobRenderer<CogonSwordEntity, Modelcogon_sword<CogonSwordEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cogon_sword.png");
     public CogonSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelcogon_sword<>(context.bakeLayer(Modelcogon_sword.LAYER_LOCATION)), 0f);
         this.addLayer(new ClothesLayer<>(this, new Modelcogon_sword(context.bakeLayer(Modelcogon_sword.LAYER_LOCATION)), LOCATION));
    }

    @Override
    protected void scale(CogonSwordEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.scale(entity, poseStack, f);
    }
    @Override
    protected float getFlipDegrees(CogonSwordEntity entity) {
        return 0.0f;
    }
    @Override
    public ResourceLocation getTextureLocation(CogonSwordEntity entity) {
        return LOCATION;
    }
}

