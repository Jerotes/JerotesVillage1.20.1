package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeltyrant_of_the_mountain_realm;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class TyrantOfTheMountainRealmArmorLayer<T extends TyrantOfTheMountainRealmEntity, M extends Modeltyrant_of_the_mountain_realm<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation CLOTHES_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_clothes.png");
    private static final ResourceLocation HELMET_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_helmet.png");
    private static final ResourceLocation CHESTPLATE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_chestplate.png");
    private static final ResourceLocation LEGGINGS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_leggings.png");
    private static final ResourceLocation BOOTS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_boots.png");
    private final M model;

    public TyrantOfTheMountainRealmArmorLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        //文武袖
        if (!t.isNoClothes()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(CLOTHES_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        //头盔
        if (t.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(HELMET_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        //胸甲
        if (t.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(CHESTPLATE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        //护腿
        if (t.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LEGGINGS_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        //靴子
        if (t.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BOOTS_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

