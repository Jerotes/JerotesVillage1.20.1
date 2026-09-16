package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.client.model.Modelillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class IllagerItemInHandLayer<T extends AbstractIllager, M extends Modelillager<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public IllagerItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack itemStack;
        boolean attack = t.isAggressive();
        if (!attack) {
            return;
        }
        boolean bl = ((LivingEntity)t).getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemStack2 = bl ? ((LivingEntity)t).getOffhandItem() : ((LivingEntity)t).getMainHandItem();
        ItemStack itemStack3 = itemStack = bl ? ((LivingEntity)t).getMainHandItem() : ((LivingEntity)t).getOffhandItem();
        if (itemStack2.isEmpty() && itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        if (this.getParentModel().young) {
            float f7 = 0.5f;
            poseStack.translate(0.0f, 0.75f, 0.0f);
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        this.renderArmWithItem((LivingEntity)t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
        this.renderArmWithItem((LivingEntity)t, itemStack2, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        boolean bl = humanoidArm == HumanoidArm.LEFT;
        poseStack.translate((float)(bl ? -1 : 1) / 16.0f, 0.125f, -0.625f);
        Main.spearInHandLayer(this.getParentModel(), livingEntity, itemStack, itemDisplayContext, humanoidArm, poseStack, multiBufferSource, n);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

