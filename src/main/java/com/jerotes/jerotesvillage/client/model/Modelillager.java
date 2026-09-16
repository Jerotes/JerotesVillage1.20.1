package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import com.jerotes.jerotes.entity.Interface.*;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;


public class Modelillager<T extends AbstractIllager> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "illager"), "main");
	public final ModelPart head;
	public final ModelPart hatOld;
	public final ModelPart body;
	public final ModelPart arms;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	public final ModelPart rightArm;
	public final ModelPart leftArm;

	public Modelillager(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.hatOld = root.getChild("hat");
		this.hat.visible = false;
		this.body = root.getChild("body");
		this.arms = body.getChild("arms");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
		head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
		arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		super.setupAnim(t, f, f2, f3, f4, f5);

		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (this.riding) {
			this.rightArm.xRot = (-(float)Math.PI / 5F);
			this.rightArm.yRot = 0.0F;
			this.rightArm.zRot = 0.0F;
			this.leftArm.xRot = (-(float)Math.PI / 5F);
			this.leftArm.yRot = 0.0F;
			this.leftArm.zRot = 0.0F;
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = ((float)Math.PI / 10F);
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = (-(float)Math.PI / 10F);
			this.leftLeg.zRot = -0.07853982F;
		} else {
			this.rightArm.xRot = Mth.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f2 * 0.5F;
			this.rightArm.yRot = 0.0F;
			this.rightArm.zRot = 0.0F;
			this.leftArm.xRot = Mth.cos(f * 0.6662F) * 2.0F * f2 * 0.5F;
			this.leftArm.yRot = 0.0F;
			this.leftArm.zRot = 0.0F;
			this.rightLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * f2 * 0.5F;
			this.rightLeg.yRot = 0.0F;
			this.rightLeg.zRot = 0.0F;
			this.leftLeg.xRot = Mth.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f2 * 0.5F;
			this.leftLeg.yRot = 0.0F;
			this.leftLeg.zRot = 0.0F;
		}

		AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = t.getArmPose();
		if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.ATTACKING) {
			if (t.getMainHandItem().isEmpty()) {
				AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, f3);
			} else {
				AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, t, this.attackTime, f3);
			}
		} else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
			this.rightArm.z = 0.0F;
			this.rightArm.x = -5.0F;
			this.leftArm.z = 0.0F;
			this.leftArm.x = 5.0F;
			this.rightArm.xRot = Mth.cos(f3 * 0.6662F) * 0.25F;
			this.leftArm.xRot = Mth.cos(f3 * 0.6662F) * 0.25F;
			this.rightArm.zRot = 2.3561945F;
			this.leftArm.zRot = -2.3561945F;
			this.rightArm.yRot = 0.0F;
			this.leftArm.yRot = 0.0F;
		} 
		else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
			this.rightArm.yRot = -0.1F + this.head.yRot;
			this.rightArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
			this.leftArm.xRot = -0.9424779F + this.head.xRot;
			this.leftArm.yRot = this.head.yRot - 0.4F;
			this.leftArm.zRot = ((float)Math.PI / 2F);
		} 
		else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
			AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
		} 
		else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
			AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, true);
		} 
		else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CELEBRATING) {
			this.rightArm.z = 0.0F;
			this.rightArm.x = -5.0F;
			this.rightArm.xRot = Mth.cos(f3 * 0.6662F) * 0.05F;
			this.rightArm.zRot = 2.670354F;
			this.rightArm.yRot = 0.0F;
			this.leftArm.z = 0.0F;
			this.leftArm.x = 5.0F;
			this.leftArm.xRot = Mth.cos(f3 * 0.6662F) * 0.05F;
			this.leftArm.zRot = -2.3561945F;
			this.leftArm.yRot = 0.0F;
		}
		//
		ItemStack itemStack = t.getMainHandItem();
		ItemStack itemStacks = t.getOffhandItem();
		ModelPart mainHand;
		ModelPart offHand;
		if (!t.isLeftHanded()) {
			mainHand = rightArm;
			offHand = leftArm;
		}
		else {
			mainHand = leftArm;
			offHand = rightArm;
		}
		//潜行
		if (t.isShiftKeyDown()) {
			this.body.xRot = 0.5f;
			this.rightArm.xRot += 0.4f;
			this.leftArm.xRot += 0.4f;
			this.rightLeg.z = 4.0f;
			this.leftLeg.z = 4.0f;
			this.rightLeg.y = 12.2f;
			this.leftLeg.y = 12.2f;
			this.head.y = 4.2f;
			this.body.y = 3.2f;
			this.leftArm.y = 5.2f;
			this.rightArm.y = 5.2f;
		}
        if (t.isAggressive() && t instanceof UseShieldEntity useShield) {
			if (useShield.shieldCanUse() && useShield.notBowCrossbow(t, InteractionHand.MAIN_HAND) && t.getOffhandItem().getItem() instanceof ShieldItem && t.getUseItem().getItem() instanceof ShieldItem) {
				this.poseBlockingArm(offHand, false);
			}
			//主手盾牌
			else if (useShield.shieldCanUse() && useShield.notBowCrossbow(t, InteractionHand.OFF_HAND) && t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldItem && t.getUseItem().getItem() instanceof ShieldItem) {
				this.poseBlockingArm(mainHand, false);
			}
			//主手双手武器
			else if (t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && t.getOffhandItem().isEmpty() && t.getUseItem().getItem() instanceof ItemTwoHanded && t.attackAnim <= 0.0F) {
				this.poseBlockingArm(mainHand, false);
			}
			//副手双手武器
			else if (t.getOffhandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && t.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && t.getUseItem().getItem() instanceof ItemTwoHanded && t.attackAnim <= 0.0F) {
				this.poseBlockingArm(offHand, false);
			}
//			//主手矛
//			else if (t instanceof InventoryEntity inventoryEntity && InventoryEntity.isSpear(inventoryEntity, t.getItemInHand(InteractionHand.MAIN_HAND))) {
//				this.poseBlockingArm(mainHand, false);
//			}
//			//副手矛
//			else if (t instanceof InventoryEntity inventoryEntity && InventoryEntity.isSpear(inventoryEntity, t.getItemInHand(InteractionHand.OFF_HAND))) {
//				this.poseBlockingArm(offHand, false);
//			}
//			//主手矛
//			else if (InventoryEntity.isSpear(t.getItemInHand(InteractionHand.MAIN_HAND))) {
//				this.poseBlockingArm(mainHand, false);
//			}
//			//副手矛
//			else if (InventoryEntity.isSpear(t.getItemInHand(InteractionHand.OFF_HAND))) {
//				this.poseBlockingArm(offHand, false);
//			}
		}
		specialAnim(t, this, f, f2, f3, f4, f5);
		boolean flag = abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSED && !(t instanceof AlwaysShowArmIllagerEntity);
		this.arms.visible = flag;
		this.leftArm.visible = !flag;
		this.rightArm.visible = !flag;

		if (!flag) {
			arms.xScale = 0;
			arms.yScale = 0;
			arms.zScale = 0;
			leftArm.xScale = 1;
			leftArm.yScale = 1;
			leftArm.zScale = 1;
			rightArm.xScale = 1;
			rightArm.yScale = 1;
			rightArm.zScale = 1;
		} else {
			arms.xScale = 1;
			arms.yScale = 1;
			arms.zScale = 1;
			leftArm.xScale = 0;
			leftArm.yScale = 0;
			leftArm.zScale = 0;
			rightArm.xScale = 0;
			rightArm.yScale = 0;
			rightArm.zScale = 0;
		}
		this.hatOld.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}

	public boolean shouldTaczIdle() {
		return false;
	}


	public ModelPart getHat() {
		return this.hat;
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