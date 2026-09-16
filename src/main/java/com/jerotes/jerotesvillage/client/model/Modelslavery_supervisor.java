package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.client.animation.SlaverySupervisorAnimation;
import com.jerotes.jerotesvillage.client.animation.SubmarinerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SlaverySupervisorEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;


public class Modelslavery_supervisor<T extends SlaverySupervisorEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "slavery_supervisor"), "main");
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart hat_armor;
	private final ModelPart nose;
	private final ModelPart body;
	private final ModelPart body_armor;
	private final ModelPart body_legs_armor;
	private final ModelPart arms;
	private final ModelPart arms_armor;
	private final ModelPart left_shoulder;
	private final ModelPart left_shoulder_armor;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart left_arm_armor;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart right_arm_armor;
	private final ModelPart left_leg;
	private final ModelPart left_leg_armor;
	private final ModelPart left_foot_armor;
	private final ModelPart right_leg;
	private final ModelPart right_leg_armor;
	private final ModelPart right_foot_armor;


	public Modelslavery_supervisor(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.hat = this.head.getChild("hat");
		this.hat_armor = this.hat.getChild("hat_armor");
		this.nose = this.head.getChild("nose");
		this.body = root.getChild("body");
		this.body_armor = this.body.getChild("body_armor");
		this.body_legs_armor = this.body.getChild("body_legs_armor");
		this.arms = this.body.getChild("arms");
		this.arms_armor = this.arms.getChild("arms_armor");
		this.left_shoulder = this.arms.getChild("left_shoulder");
		this.left_shoulder_armor = this.left_shoulder.getChild("left_shoulder_armor");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.left_arm_armor = this.left_arm.getChild("left_arm_armor");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.right_arm_armor = this.right_arm.getChild("right_arm_armor");
		this.left_leg = root.getChild("left_leg");
		this.left_leg_armor = this.left_leg.getChild("left_leg_armor");
		this.left_foot_armor = this.left_leg.getChild("left_foot_armor");
		this.right_leg = root.getChild("right_leg");
		this.right_leg_armor = this.right_leg.getChild("right_leg_armor");
		this.right_foot_armor = this.right_leg.getChild("right_foot_armor");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat_armor = hat.addOrReplaceChild("hat_armor", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(96, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_armor = body.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(64, 38).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_legs_armor = body.addOrReplaceChild("body_legs_armor", CubeListBuilder.create().texOffs(100, 46).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.95F, -1.05F, -0.7505F, 0.0F, 0.0F));

		PartDefinition arms_armor = arms.addOrReplaceChild("arms_armor", CubeListBuilder.create().texOffs(64, 22).addBox(-8.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(92, 38).addBox(-4.0F, -19.05F, -3.05F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offset(0.0F, 21.05F, 1.05F));

		PartDefinition left_shoulder = arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 21.05F, 1.05F));

		PartDefinition left_shoulder_armor = left_shoulder.addOrReplaceChild("left_shoulder_armor", CubeListBuilder.create().texOffs(64, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, 0.0F));

		PartDefinition left_arm_armor = left_arm.addOrReplaceChild("left_arm_armor", CubeListBuilder.create().texOffs(64, 22).mirror().addBox(4.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(-5.0F, 22.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 9.0F, 0.0F));

		PartDefinition right_arm_armor = right_arm.addOrReplaceChild("right_arm_armor", CubeListBuilder.create().texOffs(64, 22).addBox(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(5.0F, 22.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_leg_armor = left_leg.addOrReplaceChild("left_leg_armor", CubeListBuilder.create().texOffs(80, 22).mirror().addBox(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition left_foot_armor = left_leg.addOrReplaceChild("left_foot_armor", CubeListBuilder.create().texOffs(96, 22).mirror().addBox(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_leg_armor = right_leg.addOrReplaceChild("right_leg_armor", CubeListBuilder.create().texOffs(80, 22).addBox(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_foot_armor = right_leg.addOrReplaceChild("right_foot_armor", CubeListBuilder.create().texOffs(96, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animate(t.idleAnimationState, SubmarinerAnimation.IDLE, f3);
		this.animateWalk(SlaverySupervisorAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.attack1AnimationState, SlaverySupervisorAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, SlaverySupervisorAnimation.ATTACK2, f3);
		this.animate(t.swordAttack1AnimationState, SlaverySupervisorAnimation.SWORDATTACK1, f3);
		this.animate(t.swordAttack2AnimationState, SlaverySupervisorAnimation.SWORDATTACK2, f3);
		this.animate(t.swordAttack3AnimationState, SlaverySupervisorAnimation.SWORDATTACK3, f3);
		this.animate(t.swordAttack4AnimationState, SlaverySupervisorAnimation.SWORDATTACK4, f3);
		this.animate(t.getAttackAnimationState, SlaverySupervisorAnimation.GET, f3);
		this.animate(t.setAttackAnimationState, SlaverySupervisorAnimation.SET, f3);
		this.animate(t.leadAnimationState, SlaverySupervisorAnimation.LEAD, f3);
		this.animate(t.deadAnimationState, SlaverySupervisorAnimation.DEAD, f3);
		//盾牌
		if (!t.isLeftHanded()) {
			if (this.attackTime <= 0.0F) {
				this.animate(t.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
			}
			this.animate(t.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
		}
		else {
			if (this.attackTime <= 0.0F) {
				this.animate(t.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
			}
			this.animate(t.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
		}
		//

		if (t.getItemBySlot(EquipmentSlot.HEAD).getItem() != JerotesVillageItems.SLAVERY_SUPERVISOR_HELMET.get()) {
			this.hat_armor.visible = false;
		}
		else {
			this.hat_armor.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.CHEST).getItem() != JerotesVillageItems.SLAVERY_SUPERVISOR_CHESTPLATE.get()) {
			this.body_armor.visible = false;
			this.left_arm_armor.visible = false;
			this.right_arm_armor.visible = false;
			this.arms_armor.visible = false;
			this.left_shoulder_armor.visible = false;
		}
		else {
			this.body_armor.visible = true;
			this.left_arm_armor.visible = true;
			this.right_arm_armor.visible = true;
			this.arms_armor.visible = true;
			this.left_shoulder_armor.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.LEGS).getItem() != JerotesVillageItems.SLAVERY_SUPERVISOR_LEGGINGS.get()) {
			this.body_legs_armor.visible = false;
			this.left_leg_armor.visible = false;
			this.right_leg_armor.visible = false;
		}
		else {
			this.body_legs_armor.visible = true;
			this.left_leg_armor.visible = true;
			this.right_leg_armor.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.FEET).getItem() != JerotesVillageItems.SLAVERY_SUPERVISOR_BOOTS.get()) {
			this.left_foot_armor.visible = false;
			this.right_foot_armor.visible = false;
		}
		else {
			this.left_foot_armor.visible = true;
			this.right_foot_armor.visible = true;
		}

		AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = t.getArmPose();

		boolean flag = abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSED && !(t instanceof AlwaysShowArmIllagerEntity);
		this.arms.visible = flag;
		this.leftArm.visible = !flag;
		this.rightArm.visible = !flag;

		if (!flag) {
			arms.xScale = 0;
			arms.yScale = 0;
			arms.zScale = 0;
		} else {
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
	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getWeapon(humanoidArm).translateAndRotate(poseStack);
	}
	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getWeapon(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_weapon;
		}
		return this.right_weapon;
	}
}