package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BitterColdAltarOtherBodyLayer<T extends BitterColdAltarEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final M model;

    public BitterColdAltarOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (t.isInvisible()) {
            return;
        }
        if (t.getHealth() > t.getMaxHealth() * 0.95) {
            return;
        }
        ResourceLocation resourceLocation;
        if (t.getHealth() > t.getMaxHealth() * 0.9) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_0.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.8) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_1.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.7) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_2.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.6) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_3.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.5) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_4.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.4) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_5.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.3) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_6.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.2) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_7.png");
        }
        else if (t.getHealth() > t.getMaxHealth() * 0.1) {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_8.png");
        }
        else  {
            resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar_break_9.png");
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(resourceLocation));
        ((Model)this.model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

