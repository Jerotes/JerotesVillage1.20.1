package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SerponCombatExecuterAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class Modelserpon_combat_executer<T extends LivingEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_combat_executer"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	public final ModelPart body;
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

	public Modelserpon_combat_executer(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
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

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 1.0F));

		PartDefinition left_horn_r1 = horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(104, 65).mirror().addBox(0.0F, -6.0F, -4.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.75F, -1.0F, 3.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_horn_r1 = horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(104, 65).addBox(0.0F, -6.0F, -4.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -1.0F, 3.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(120, 81).addBox(0.0F, -4.0F, -1.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(120, 81).addBox(0.0F, 0.0F, -2.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.25F, 5.5F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -5.25F, -0.0873F, 0.0F, 0.0F));

		PartDefinition mouth_out_r1 = mouth.addOrReplaceChild("mouth_out_r1", CubeListBuilder.create().texOffs(91, 119).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1396F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(73, 119).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0785F, 0.0F, 0.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition main_body = body.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 57).mirror().addBox(1.3787F, -9.5F, -4.1213F, 0.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 3.0F, 4.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_wing_1 = left_wing.addOrReplaceChild("left_wing_1", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(0.0F, -10.0F, 0.0F, 0.0F, 31.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.3787F, -1.5F, 11.8787F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 57).addBox(-1.3787F, -9.5F, -4.1213F, 0.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 4.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_wing_1 = right_wing.addOrReplaceChild("right_wing_1", CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, -10.0F, 0.0F, 0.0F, 31.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3787F, -1.5F, 11.8787F, 0.0F, -0.5236F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(64, 50).addBox(-2.0F, -2.0F, -2.4F, 4.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(16, 48).addBox(-0.1F, 1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(-0.05F))
				.texOffs(0, 16).addBox(-3.9F, 1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(-0.05F))
				.texOffs(0, 48).addBox(0.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 32).addBox(-4.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(108, 91).addBox(-3.0F, 0.5F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(108, 101).addBox(-3.0F, -0.25F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(106, 111).addBox(-2.0F, 0.5F, -1.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition tail_5 = tail_4.addOrReplaceChild("tail_5", CubeListBuilder.create().texOffs(114, 120).addBox(-2.0F, 0.25F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_6 = tail_5.addOrReplaceChild("tail_6", CubeListBuilder.create().texOffs(120, 111).addBox(-1.0F, 0.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.75F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tail_7 = tail_6.addOrReplaceChild("tail_7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.75F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition tail_7_r1 = tail_7.addOrReplaceChild("tail_7_r1", CubeListBuilder.create().texOffs(106, 78).addBox(0.0F, -1.0F, -3.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition jacket = partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_armother_r1 = left_arm.addOrReplaceChild("left_armother_r1", CubeListBuilder.create().texOffs(106, 78).mirror().addBox(-0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, 0.0873F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_armother_r1 = right_arm.addOrReplaceChild("right_armother_r1", CubeListBuilder.create().texOffs(106, 78).mirror().addBox(0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, -0.0873F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_pants = partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_pants = partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof SerponCombatExecuterEntity serponCombatExecuterEntity) {
			this.head.yRot = f4 * ((float)Math.PI / 180F);
			this.head.xRot = f5 * ((float)Math.PI / 180F);
			if (!serponCombatExecuterEntity.isTrueCome()) {
				this.head.xScale -= this.head.xScale;
				this.head.yScale -= this.head.yScale;
				this.head.zScale -= this.head.zScale;
				this.body.xScale -= this.body.xScale;
				this.body.yScale -= this.body.yScale;
				this.body.zScale -= this.body.zScale;
				this.right_arm.xScale -= this.right_arm.xScale;
				this.right_arm.yScale -= this.right_arm.yScale;
				this.right_arm.zScale -= this.right_arm.zScale;
				this.left_arm.xScale -= this.left_arm.xScale;
				this.left_arm.yScale -= this.left_arm.yScale;
				this.left_arm.zScale -= this.left_arm.zScale;
				this.right_leg.xScale -= this.right_leg.xScale;
				this.right_leg.yScale -= this.right_leg.yScale;
				this.right_leg.zScale -= this.right_leg.zScale;
				this.left_leg.xScale -= this.left_leg.xScale;
				this.left_leg.yScale -= this.left_leg.yScale;
				this.left_leg.zScale -= this.left_leg.zScale;
			}
			if (serponCombatExecuterEntity.isAggressive() && !serponCombatExecuterEntity.getMainHandItem().isEmpty()) {
				this.animateWalk(SerponCombatExecuterAnimation.ATTACKWALK, f, f2, 1.5f, 2.0f);
			} else {
				this.animateWalk(SerponCombatExecuterAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			this.animate(serponCombatExecuterEntity.idleAnimationState, SerponCombatExecuterAnimation.IDLE, f3);
			this.animate(serponCombatExecuterEntity.attack1AnimationState, SerponCombatExecuterAnimation.ATTACK1, f3);
			this.animate(serponCombatExecuterEntity.attack2AnimationState, SerponCombatExecuterAnimation.ATTACK2, f3);
			this.animate(serponCombatExecuterEntity.swordAttack1AnimationState, SerponCombatExecuterAnimation.SWORDATTACK1, f3);
			this.animate(serponCombatExecuterEntity.swordAttack2AnimationState, SerponCombatExecuterAnimation.SWORDATTACK2, f3);
			this.animate(serponCombatExecuterEntity.swordAttack3AnimationState, SerponCombatExecuterAnimation.SWORDATTACK3, f3);
			this.animate(serponCombatExecuterEntity.swordAttack4AnimationState, SerponCombatExecuterAnimation.SWORDATTACK4, f3);
			this.animate(serponCombatExecuterEntity.block1AnimationState, SerponCombatExecuterAnimation.BLOCK1, f3);
			this.animate(serponCombatExecuterEntity.block2AnimationState, SerponCombatExecuterAnimation.BLOCK2, f3);
			this.animate(serponCombatExecuterEntity.block3AnimationState, SerponCombatExecuterAnimation.BLOCK3, f3);
			this.animate(serponCombatExecuterEntity.flameAttackAnimationState, SerponCombatExecuterAnimation.FLAMEATTACK, f3);
			this.animate(serponCombatExecuterEntity.windAttackAnimationState, SerponCombatExecuterAnimation.WINDATTACK, f3);
			this.animate(serponCombatExecuterEntity.hotWindSpellAnimationState, SerponCombatExecuterAnimation.HOTWINDSPELL, f3);
			this.animate(serponCombatExecuterEntity.pikeSpellAnimationState, SerponCombatExecuterAnimation.PIKESPELL, f3);
			this.animate(serponCombatExecuterEntity.meteorSpellAnimationState, SerponCombatExecuterAnimation.METEORSPELL, f3);
			this.animate(serponCombatExecuterEntity.pushAnimationState, SerponCombatExecuterAnimation.PUSH, f3);
			this.animate(serponCombatExecuterEntity.deadAnimationState, SerponCombatExecuterAnimation.DEAD, f3);
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