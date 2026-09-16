package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeltyrant_of_the_mountain_realm;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TyrantOfTheMountainRealmItemInHandLayer<T extends TyrantOfTheMountainRealmEntity, M extends Modeltyrant_of_the_mountain_realm<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation SWORD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_sword.png");
    private static final ResourceLocation SPEAR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_flag_spear.png");
    private final ItemInHandRenderer itemInHandRenderer;
    private final M model;

    public TyrantOfTheMountainRealmItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer, M m) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack mainHand = t.getMainHandItem();
        ItemStack offHand = t.getOffhandItem();
        poseStack.pushPose();
        //主手
        if (!(mainHand.getItem() == JerotesVillageItems.MOUNTAIN_REALM_NINE_RINGED_DAO.get())) {
            if (t.isUseSword() || (t.getChangeSword() > 5 && !t.isUseSword())) {
                this.sword(t, mainHand, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
            }
            else {
                //腰部
                this.bodySword(t, mainHand, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
            }
        }
        else {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(SWORD_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        //副手
        if (!(offHand.getItem() == JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get())) {
            if (t.getThrowSpearUseTick() > 0) {
                this.spear(t, offHand, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
            }
        }
        else {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(SPEAR_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        poseStack.popPose();
    }

    protected void sword(TyrantOfTheMountainRealmEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        poseStack.scale(1.3f, 1.3f, 1.3f);
        poseStack.translate(-0.025f, 0.16f, -0.06f);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void bodySword(TyrantOfTheMountainRealmEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.pushPose();
        (this.getParentModel()).translateToBodySword(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(27.5F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        poseStack.scale(1.3f, 1.3f, 1.3f);
        poseStack.translate(0.46f, -1.375f, -0.55f);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void spear(TyrantOfTheMountainRealmEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        poseStack.scale(1.3f, 1.3f, 1.3f);
        poseStack.translate(0f, 0.16f, 0f);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

