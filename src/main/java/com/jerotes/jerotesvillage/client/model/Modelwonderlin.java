package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.WonderlinAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;

public class Modelwonderlin<T extends BaseWonderlinEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wonderlin"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelwonderlin(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -3.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 12).addBox(0.0F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 17).addBox(-3.0F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -0.5F, -1.5F, 5.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.5F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 12).addBox(0.0F, -0.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 12.5F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 21).addBox(-2.0F, -0.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 12.5F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, 19.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, 19.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
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
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (!t.isWonderlinRush()) {
			this.animateWalk(WonderlinAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.idleAnimationState, WonderlinAnimation.IDLE, f3);
		this.animate(t.kickAnimationState, WonderlinAnimation.KICK, f3);
		this.animate(t.pushAnimationState, WonderlinAnimation.PUSH, f3);
		this.animate(t.rushAnimationState, WonderlinAnimation.RUSH, f3);
		if (!t.isLeftHanded()) {
			this.animate(t.attackAnimationState, WonderlinAnimation.ATTACK1, f3);
		}
		else  {
			this.animate(t.attackAnimationState, WonderlinAnimation.ATTACK2, f3);
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
//				//主手矛
//				else if (InventoryEntity.isSpear(t, t.getItemInHand(InteractionHand.MAIN_HAND))) {
//					this.poseBlockingArm(mainHand, false);
//				}
//				//副手矛
//				else if (InventoryEntity.isSpear(t, t.getItemInHand(InteractionHand.OFF_HAND))) {
//					this.poseBlockingArm(offHand, false);
//				}
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

	@Override
	public ModelPart root() {
		return this.root;
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
