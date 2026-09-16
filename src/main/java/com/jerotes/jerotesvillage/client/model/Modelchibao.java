package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ChibaoAnimation;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;

public class Modelchibao<T extends ChibaoEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "chibao"), "main");
	public final ModelPart head;
	public final ModelPart hat;
	public final ModelPart body;
	public final ModelPart jacket;
	public final ModelPart left_arm;
	public final ModelPart left_sleeve;
	public final ModelPart right_arm;
	public final ModelPart right_sleeve;
	public final ModelPart left_leg;
	public final ModelPart left_pants;
	public final ModelPart right_leg;
	public final ModelPart right_pants;

	public Modelchibao(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
		this.jacket = root.getChild("jacket");
		this.left_arm = root.getChild("left_arm");
		this.left_sleeve = root.getChild("left_sleeve");
		this.right_arm = root.getChild("right_arm");
		this.right_sleeve = root.getChild("right_sleeve");
		this.left_leg = root.getChild("left_leg");
		this.left_pants = root.getChild("left_pants");
		this.right_leg = root.getChild("right_leg");
		this.right_pants = root.getChild("right_pants");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -9.0F, -5.0F, 12.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 65).addBox(-7.0F, -9.0F, -5.25F, 14.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 111).addBox(-6.0F, -9.0F, -5.0F, 12.0F, 9.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -1.5F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, -0.5F, -4.0F, 16.0F, 23.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 78).addBox(-8.0F, 5.5F, -4.0F, 16.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 48).addBox(-8.0F, 12.75F, -5.0F, 16.0F, 9.0F, 8.0F, new CubeDeformation(0.2F))
				.texOffs(0, 102).addBox(-8.0F, 12.75F, -5.0F, 16.0F, 9.0F, 8.0F, new CubeDeformation(0.45F))
				.texOffs(80, 97).addBox(-8.0F, 0.0F, -4.0F, 16.0F, 23.0F, 8.0F, new CubeDeformation(0.475F)), PartPose.offset(0.0F, -8.0F, 0.75F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(0.0F, -2.5F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 0).mirror().addBox(0.0F, -2.5F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(8.0F, -5.5F, 1.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(100, 22).mirror().addBox(-5.0F, 0.0F, -4.0F, 6.0F, 12.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(72, 22).mirror().addBox(-5.0F, 0.0F, -4.0F, 6.0F, 12.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(5.0F, 11.5F, 0.0F));

		PartDefinition left_weapon = left_hand.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(-2.0F, 10.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(100, 0).addBox(-6.0F, -2.5F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(72, 0).addBox(-6.0F, -2.5F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(-8.0F, -5.5F, 1.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(100, 22).addBox(-1.0F, 0.0F, -4.0F, 6.0F, 12.0F, 8.0F, new CubeDeformation(0.1F))
				.texOffs(72, 22).addBox(-1.0F, 0.0F, -4.0F, 6.0F, 12.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(-5.0F, 11.5F, 0.0F));

		PartDefinition right_weapon = right_hand.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(2.0F, 10.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(100, 42).mirror().addBox(-3.25F, -2.0F, -4.5F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(72, 42).mirror().addBox(-3.25F, -2.0F, -4.5F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(5.0F, 13.5F, 1.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(100, 57).mirror().addBox(-3.25F, -2.0F, -2.25F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 57).mirror().addBox(-3.25F, -2.0F, -2.25F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 5.0F, -2.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(100, 42).addBox(-2.75F, -2.0F, -4.5F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.1F))
				.texOffs(72, 42).addBox(-2.75F, -2.0F, -4.5F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(-5.0F, 13.5F, 1.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(100, 57).addBox(-2.75F, -2.0F, -2.25F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(72, 57).addBox(-2.75F, -2.0F, -2.25F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 5.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		ItemStack itemStack = t.getMainHandItem();
		ItemStack itemStacks = t.getOffhandItem();
		ModelPart mainHand;
		ModelPart offHand;
		if (!(t.isLeftHanded())) {
			mainHand = rightArm;
			offHand = leftArm;
		} else {
			mainHand = leftArm;
			offHand = rightArm;
		}
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (t.isBaby()) {
			this.head.y -= 3f;
		}
		if (t.isAggressive()) {
			this.animateWalk(ChibaoAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else{
			this.animateWalk(ChibaoAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		if (t.getUseItem().getItem() instanceof BowItem) {
			this.rightArm.yRot = 0f;
			this.leftArm.yRot = 0f;
			this.rightArm.xRot = 0f;
			this.leftArm.xRot = 0f;
		}
		this.animate(t.idleAnimationState, ChibaoAnimation.IDLE, f3);
		this.animate(t.sneakAnimationState, ChibaoAnimation.SHIFT, f3);
		if (!t.isLeftHanded()) {
			this.animate(t.attackAnimationState, ChibaoAnimation.ATTACK1, f3);
			this.animate(t.itemAttackAnimationState, ChibaoAnimation.ITEMATTACK1, f3);
			this.animate(t.throw1AnimationState, ChibaoAnimation.THROW1, f3);
			this.animate(t.throw2AnimationState, ChibaoAnimation.THROW2, f3);
		} else {
			this.animate(t.attackAnimationState, ChibaoAnimation.ATTACK2, f3);
			this.animate(t.itemAttackAnimationState, ChibaoAnimation.ITEMATTACK1, f3);
			this.animate(t.throw1AnimationState, ChibaoAnimation.THROW2, f3);
			this.animate(t.throw2AnimationState, ChibaoAnimation.THROW1, f3);
		}
		{
			ItemStack handItem = t.getMainHandItem();
			if ((t.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(t.getMainHandItem())) && !t.getOffhandItem().isEmpty()) {
				handItem = t.getOffhandItem();
			}
			if (t.isAggressive()) {
				if (itemStack.getItem() instanceof BowItem) {
					if (mainHand == rightArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
				else if (itemStacks.getItem() instanceof BowItem && handItem != t.getMainHandItem() && t.getUsedItemHand() == InteractionHand.OFF_HAND) {
					if (mainHand == leftArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
			}
			if (t.isAggressive()) {
				if (itemStack.getItem() instanceof CrossbowItem) {
					if (t.isChargingCrossbow()) {
						AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, !t.isLeftHanded());
					} else {
						AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, !t.isLeftHanded());
					}
				}
				else if (itemStacks.getItem() instanceof CrossbowItem && handItem != t.getMainHandItem()) {
					if (t.isChargingCrossbow()) {
						AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, t.isLeftHanded());
					} else {
						AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, t.isLeftHanded());
					}
				}
			}
			if (InventoryEntity.isRangeJavelin(itemStack) || itemStack.getItem() instanceof TridentItem) {
				if (t.isUsingItem() && t.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
					jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(mainHand, offHand, head, true);
				}
				//三叉戟
				else if (t.isUsingItem() && t.getUseItem().getItem() instanceof TridentItem) {
					mainHand.xRot = mainHand.xRot * 0.5f - 3.1415927f;
					mainHand.yRot = 0.0f;
				}
			}
			else if ((InventoryEntity.isRangeJavelin(itemStacks) || itemStacks.getItem() instanceof TridentItem) && handItem != t.getMainHandItem()) {
				if (t.isUsingItem() && t.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
					jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(offHand, mainHand, head, true);
				}
				//三叉戟
				else if (t.isUsingItem() && t.getUseItem().getItem() instanceof TridentItem) {
					offHand.xRot = offHand.xRot * 0.5f - 3.1415927f;
					offHand.yRot = 0.0f;
				}
			}
			if (t.isAggressive()) {
				if (t.shieldCanUse() && t.notBowCrossbow(t, InteractionHand.MAIN_HAND) && t.getOffhandItem().getItem() instanceof ShieldItem && t.isUsingItem() && t.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(offHand, false);
				}
				//主手盾牌
				else if (t.shieldCanUse() && t.notBowCrossbow(t, InteractionHand.OFF_HAND) && t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldItem && t.isUsingItem() && t.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(mainHand, false);
				}
				//主手双手武器
				else if (t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && t.getOffhandItem().isEmpty() && t.isUsingItem() && t.getUseItem().getItem() instanceof ItemTwoHanded && t.attackAnim <= 0.0F) {
					this.poseBlockingArm(mainHand, false);
				}
				//副手双手武器
				else if (t.getOffhandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && t.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && t.isUsingItem() && t.getUseItem().getItem() instanceof ItemTwoHanded && t.attackAnim <= 0.0F) {
					this.poseBlockingArm(offHand, false);
				}
			}
			specialAnim(t, this, f, f2, f3, f4, f5);
		}

		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}

	public void poseBlockingArm(ModelPart modelPart, boolean bl) {
		if (modelPart == leftArm) {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? -30.0f : 30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
		else {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? 30.0f : -30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
	}
}