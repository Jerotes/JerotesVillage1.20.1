package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.client.animation.SpearAnimations;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase;
import com.jerotes.jerotes.util.Ease;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.jerotes.jerotesvillage.entity.Neutral.SpearMachineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpearMachineItemInHandLayer<T extends SpearMachineEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public SpearMachineItemInHandLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
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
        poseStack.pushPose();
        this.renderArmWithItem(t, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, n);
        this.renderArmWithItem(t, itemStack2, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }

    protected void renderArmWithItem(SpearMachineEntity spearMachine, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        if (InventoryEntity.isSpear(itemStack)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            poseStack.translate(0f, 0.2f, 0f);
        }
        if (InventoryEntity.isPike(itemStack)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(17.5f));
        }
        poseStack.translate(0f, 0f, -0.08f);
        poseStack.scale(1.25f, 1.25f, 1.25f);
        spearInHandLayerSpear(this.getParentModel(), spearMachine, itemStack, itemDisplayContext, humanoidArm, poseStack, multiBufferSource, n);
        this.itemInHandRenderer.renderItem(spearMachine, itemStack, itemDisplayContext, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
    public static void spearInHandLayerSpear(EntityModel<?> entityModel, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (itemStack.getItem() instanceof ItemToolBaseSpearBase itemToolBaseSpearBase && !itemToolBaseSpearBase.otherAnimSpear()) {
            float f;
            if ((f = livingEntity.getTicksUsingItem()) != 0.0f) {
                if (humanoidArm == HumanoidArm.RIGHT &&
                        (livingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND && livingEntity.getMainArm() == HumanoidArm.RIGHT || livingEntity.getUsedItemHand() == InteractionHand.OFF_HAND && livingEntity.getMainArm() != HumanoidArm.RIGHT)
                ) {
                    SpearAnimations.thirdPersonUseItem(entityModel.attackTime, poseStack, f, humanoidArm, itemStack, livingEntity, Minecraft.getInstance().getPartialTick());
                }
                if (humanoidArm == HumanoidArm.LEFT &&
                        (livingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND && livingEntity.getMainArm() == HumanoidArm.LEFT || livingEntity.getUsedItemHand() == InteractionHand.OFF_HAND && livingEntity.getMainArm() != HumanoidArm.LEFT)
                ) {
                    SpearAnimations.thirdPersonUseItem(entityModel.attackTime, poseStack, f, humanoidArm, itemStack, livingEntity, Minecraft.getInstance().getPartialTick());
                }
            }
        }
    }
    public static float progress(float f, float f2, float f3) {
        return Mth.clamp(Mth.inverseLerp(f, f2, f3), 0.0F, 1.0F);
    }
}

