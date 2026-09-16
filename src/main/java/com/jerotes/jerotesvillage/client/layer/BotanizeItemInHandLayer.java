package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
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

public class BotanizeItemInHandLayer<T extends BotanizeEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public BotanizeItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack itemStack = t.getMainHandItem();
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        this.renderArmWithItem(t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void renderArmWithItem(BotanizeEntity bitterColdWind, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.translate(0f, 0.5f, 0f);
        poseStack.scale(1.0f, 1.0f, 1.0f);
        this.itemInHandRenderer.renderItem(bitterColdWind, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

