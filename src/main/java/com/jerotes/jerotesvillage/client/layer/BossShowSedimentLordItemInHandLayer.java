package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.client.model.Modelsediment_lord;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BossShowSedimentLordItemInHandLayer<T extends BossShowEntity, M extends Modelsediment_lord<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public BossShowSedimentLordItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack itemStack = new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_GREATSWORD.get());
        poseStack.pushPose();
        this.renderArmWithItem((LivingEntity)t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
       poseStack.popPose();
    }

    protected void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

