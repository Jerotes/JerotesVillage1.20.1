package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WildernessGiantSkeletonEntity;
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

public class SedimentItemInHandLayer<T extends LivingEntity, M extends Modelspecial_action<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public SedimentItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        boolean $$10 = t.getMainArm() == HumanoidArm.RIGHT;
        ItemStack $$11 = $$10 ? t.getOffhandItem() : t.getMainHandItem();
        ItemStack $$12 = $$10 ? t.getMainHandItem() : t.getOffhandItem();
        if (!$$11.isEmpty() || !$$12.isEmpty()) {
            poseStack.pushPose();
            if (this.getParentModel().young) {
                float $$13 = 0.5F;
                poseStack.translate(0.0F, 0.75F, 0.0F);
                poseStack.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderArmWithItem(t, $$12, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
            this.renderArmWithItem(t, $$11, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
            poseStack.popPose();
        }
    }

    protected void renderArmWithItem(LivingEntity p_117185_, ItemStack p_117186_, ItemDisplayContext p_270970_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_) {
        if (!p_117186_.isEmpty()) {
            p_117189_.pushPose();
            ((ArmedModel)this.getParentModel()).translateToHand(p_117188_, p_117189_);
            p_117189_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            p_117189_.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean $$7 = p_117188_ == HumanoidArm.LEFT;
            p_117189_.translate(0, 0.125F, -0.125F);
            if (p_117185_ instanceof WildernessGiantSkeletonEntity) {
                p_117189_.translate((float)($$7 ? 0 : 0) / 16.0F, 0, 0);
            }
            Main.spearInHandLayer(this.getParentModel(), p_117185_, p_117186_, p_270970_, p_117188_, p_117189_, p_117190_, p_117191_);
            this.itemInHandRenderer.renderItem(p_117185_, p_117186_, p_270970_, $$7, p_117189_, p_117190_, p_117191_);
            p_117189_.popPose();
        }
    }
}

