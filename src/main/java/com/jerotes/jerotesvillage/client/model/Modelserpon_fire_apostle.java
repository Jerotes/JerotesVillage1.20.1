package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SerponFireApostleAnimation;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponFireApostleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class Modelserpon_fire_apostle<T extends SerponFireApostleEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_fire_apostle"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	public final ModelPart body;
	public final ModelPart right_wing;
	public final ModelPart left_wing;
	private final ModelPart jacket;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart left_sleeve;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart right_sleeve;
	private final ModelPart left_leg;
	private final ModelPart left_pants;
	private final ModelPart right_leg;
	private final ModelPart right_pants;

	public Modelserpon_fire_apostle(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
		this.right_wing = body.getChild("right_wing");
		this.left_wing = body.getChild("left_wing");
		this.jacket = root.getChild("jacket");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.left_sleeve = root.getChild("left_sleeve");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -4.75F));

		PartDefinition mouth_out_r1 = mouth.addOrReplaceChild("mouth_out_r1", CubeListBuilder.create().texOffs(82, 21).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1396F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(64, 21).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0785F, 0.0F, 0.0F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(80, -4).addBox(0.0F, -31.25F, 4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(80, -4).addBox(0.0F, -27.25F, 3.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, 0.0F));

		PartDefinition left_horn_r1 = horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(64, -8).mirror().addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.75F, -39.5F, 4.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_horn_r1 = horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(64, -8).addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -39.5F, 4.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 48).addBox(0.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.05F))
				.texOffs(0, 16).addBox(-4.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.05F))
				.texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 48).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 12.0F, 1.0F));

		PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 0.5F));

		PartDefinition tail_1_r1 = tail_1.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(98, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 3.5F));

		PartDefinition tail_2_r1 = tail_2.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(98, 10).addBox(-2.0F, -2.5F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 3.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.25F, 5.25F));

		PartDefinition tail_3_r1 = tail_3.addOrReplaceChild("tail_3_r1", CubeListBuilder.create().texOffs(112, 10).addBox(-1.0F, 11.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -11.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(118, -5).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 4.25F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(64, -6).addBox(0.0F, -8.0F, -5.0F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 5.5F, 6.0F, 0.2182F, -0.4363F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(64, -6).mirror().addBox(0.0F, -8.0F, -5.0F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 5.5F, 6.0F, 0.2182F, 0.4363F, 0.0F));

		PartDefinition jacket = partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_armother_r1 = left_arm.addOrReplaceChild("left_armother_r1", CubeListBuilder.create().texOffs(84, -3).mirror().addBox(-0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, 0.0873F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_armother_r1 = right_arm.addOrReplaceChild("right_armother_r1", CubeListBuilder.create().texOffs(84, -3).mirror().addBox(0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, -0.0873F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_pants = partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -0.75F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, -0.25F));

		PartDefinition right_pants = partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);

		this.animateWalk(SerponFireApostleAnimation.ATTACKWALK, f, f2 * Math.min(1, (t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animateWalk(SerponFireApostleAnimation.WALK, f, f2 * Math.min(1, (1 - t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animate(t.idleAnimationState, SerponFireApostleAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, SerponFireApostleAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, SerponFireApostleAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, SerponFireApostleAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, SerponFireApostleAnimation.ATTACK4, f3);
		this.animate(t.attack5AnimationState, SerponFireApostleAnimation.ATTACK5, f3);
		this.animate(t.deadAnimationState, SerponFireApostleAnimation.DEAD, f3);
		if (t.isPrepare()) {
			this.left_wing.xScale = 1.2f;
			this.left_wing.yScale = 1.2f;
			this.left_wing.zScale = 1.2f;
			this.right_wing.xScale = 1.2f;
			this.right_wing.yScale = 1.2f;
			this.right_wing.zScale = 1.2f;
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