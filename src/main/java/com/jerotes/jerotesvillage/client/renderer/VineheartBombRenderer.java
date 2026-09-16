package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ClothesLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvineheart_bomb;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.VineheartBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VineheartBombRenderer extends MobRenderer<VineheartBombEntity, Modelvineheart_bomb<VineheartBombEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/vineheart_bomb.png");
     public VineheartBombRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelvineheart_bomb<>(context.bakeLayer(Modelvineheart_bomb.LAYER_LOCATION)), 0.4f);
         this.addLayer(new ClothesLayer<>(this, new Modelvineheart_bomb<>(context.bakeLayer(Modelvineheart_bomb.LAYER_LOCATION)), LOCATION));
    }

    @Override
    protected void scale(VineheartBombEntity entity, PoseStack poseStack, float f) {
        super.scale(entity, poseStack, f);
    }
    @Override
    protected float getFlipDegrees(VineheartBombEntity entity) {
        return 0.0f;
    }
    @Override
    public ResourceLocation getTextureLocation(VineheartBombEntity entity) {
        return LOCATION;
    }
}

