package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.client.model.Modelflamoar;
import com.jerotes.jerotesvillage.entity.Neutral.FlamoarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FlamoarItemInHandLayer<T extends FlamoarEntity, M extends Modelflamoar<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public FlamoarItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        ItemStack itemStack;
        boolean bl = t.getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemStack2 = bl ? t.getOffhandItem() : t.getMainHandItem();
        ItemStack itemStack3 = itemStack = bl ? t.getMainHandItem() : t.getOffhandItem();
        if (itemStack2.isEmpty() && itemStack.isEmpty()) {
            return;
        }
        if (t.isBaby()) {
            return;
        }
        poseStack.pushPose();
        this.renderArmWithItem(t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
        this.renderArmWithItem(t, itemStack2, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        this.getParentModel().translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        boolean bl = humanoidArm == HumanoidArm.LEFT;
        if (!(livingEntity instanceof FlamoarEntity flamoarEntity && flamoarEntity.isLady())) {
            poseStack.scale(1.25f, 1.25f, 1.25f);
            poseStack.translate(0, -0.125F, -0.2F);
        }
        Main.spearInHandLayer(this.getParentModel(), livingEntity, itemStack, itemDisplayContext, humanoidArm, poseStack, multiBufferSource, n);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

