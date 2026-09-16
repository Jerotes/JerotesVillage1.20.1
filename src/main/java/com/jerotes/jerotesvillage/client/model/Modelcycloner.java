package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CyclonerAnimation;
import com.jerotes.jerotesvillage.client.animation.SubmarinerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.CyclonerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;


public class Modelcycloner<T extends CyclonerEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "cycloner"), "main");
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modelcycloner(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.nose = this.head.getChild("nose");
		this.hat = this.head.getChild("hat");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
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

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.95F, -1.05F, -0.7505F, 0.0F, 0.0F));

		PartDefinition left_shoulder = arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 21.05F, 1.05F));

		PartDefinition wind = body.addOrReplaceChild("wind", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -15.5F, -8.0F, 16.0F, 31.0F, 16.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition wind2 = body.addOrReplaceChild("wind2", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -15.5F, -8.0F, 16.0F, 31.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition wind3 = body.addOrReplaceChild("wind3", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -15.5F, -8.0F, 16.0F, 31.0F, 16.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(64, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.animate(t.idleAnimationState, SubmarinerAnimation.IDLE, f3);
		this.animateWalk(CyclonerAnimation.WALK, f, f2, 2.0f, 2.0f);
		if (!t.isLeftHanded()) {
			this.animate(t.attackAnimationState, CyclonerAnimation.ATTACK1, f3);
			this.animate(t.itemAttack1AnimationState, CyclonerAnimation.ITEMATTACK1_RIGHT, f3);
			this.animate(t.itemAttack2AnimationState, CyclonerAnimation.ITEMATTACK2_RIGHT, f3);
			this.animate(t.itemAttack3AnimationState, CyclonerAnimation.ITEMATTACK3_RIGHT, f3);
			this.animate(t.itemAttack4AnimationState, CyclonerAnimation.ITEMATTACK4_RIGHT, f3);
		} else {
			this.animate(t.attackAnimationState, CyclonerAnimation.ATTACK2, f3);
			this.animate(t.itemAttack1AnimationState, CyclonerAnimation.ITEMATTACK1_LEFT, f3);
			this.animate(t.itemAttack2AnimationState, CyclonerAnimation.ITEMATTACK2_LEFT, f3);
			this.animate(t.itemAttack3AnimationState, CyclonerAnimation.ITEMATTACK3_LEFT, f3);
			this.animate(t.itemAttack4AnimationState, CyclonerAnimation.ITEMATTACK4_LEFT, f3);
		}
		this.animate(t.idle1AnimationState, CyclonerAnimation.ITEMIDLE1, f3);
		this.animate(t.idle2AnimationState, CyclonerAnimation.ITEMIDLE2, f3);
		this.animate(t.rollAnimationState, CyclonerAnimation.ROLL, f3);
		this.animate(t.rollStartAnimationState, CyclonerAnimation.ROLLSTART, f3);
		this.animate(t.rollStopAnimationState, CyclonerAnimation.ROLLSTOP, f3);
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
		boolean bl = true;
		this.left_arm.visible = bl;
		this.right_arm.visible = bl;
		this.arms.visible = !bl;
		if (t.deathTime == 0) {
			arms.xScale = 0;
			arms.yScale = 0;
			arms.zScale = 0;
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