package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Monster.BitterColdWindEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BitterColdWindItemInHandLayer<T extends BitterColdWindEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public BitterColdWindItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack itemStack;
        boolean bl = ((BitterColdWindEntity)t).getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemStack2 = bl ? ((BitterColdWindEntity)t).getOffhandItem() : ((BitterColdWindEntity)t).getMainHandItem();
        ItemStack itemStack3 = itemStack = bl ? ((BitterColdWindEntity)t).getMainHandItem() : ((BitterColdWindEntity)t).getOffhandItem();
        if (itemStack2.isEmpty() && itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        if (((EntityModel)this.getParentModel()).young) {
            float f7 = 0.5f;
            poseStack.translate(0.0f, 0.75f, 0.0f);
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        this.renderArmWithItem((BitterColdWindEntity)t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
        this.renderArmWithItem((BitterColdWindEntity)t, itemStack2, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void renderArmWithItem(BitterColdWindEntity bitterColdWind, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        boolean bl = humanoidArm == HumanoidArm.LEFT;
        poseStack.translate((float)(bl ? -3 : -2.7) / 16.0f, 0.4f, 0.5f);
        poseStack.scale(0.6f, 0.6f, 0.6f);
        Main.spearInHandLayer(this.getParentModel(), bitterColdWind, itemStack, itemDisplayContext, humanoidArm, poseStack, multiBufferSource, n);
        this.itemInHandRenderer.renderItem(bitterColdWind, itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

