package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.client.animation.SubmarinerAnimation;
import com.jerotes.jerotesvillage.client.animation.WildFinderAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.WildFinderEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelwild_finder<T extends WildFinderEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wild_finder"), "main");
	private final ModelPart head;
	private final ModelPart arms;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public Modelwild_finder(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.arms = body.getChild("arms");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
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

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.45F))
				.texOffs(52, 0).addBox(-6.0F, -6.1F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F))
				.texOffs(64, 54).addBox(-4.0F, 7.0F, -3.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.65F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arrow = body.addOrReplaceChild("arrow", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 7.5F, 4.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition front_r1 = arrow.addOrReplaceChild("front_r1", CubeListBuilder.create().texOffs(92, 44).addBox(-2.6081F, -8.2929F, -0.1F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 2.3F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition front_r2 = arrow.addOrReplaceChild("front_r2", CubeListBuilder.create().texOffs(92, 44).addBox(-1.3581F, -8.2929F, 0.15F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.5F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition front_r3 = arrow.addOrReplaceChild("front_r3", CubeListBuilder.create().texOffs(92, 44).addBox(-2.1081F, -8.2929F, 0.35F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9F, 2.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition body_r1 = arrow.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(92, 52).addBox(-1.5F, -5.5F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.95F, -1.05F, -0.7505F, 0.0F, 0.0F));

		PartDefinition left_shoulder = arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 21.05F, 1.05F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(80, 32).mirror().addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(80, 32).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (!t.isAggressive()) {
			this.animateWalk(WildFinderAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(WildFinderAnimation.ATTACKWALK, f, f2, 2.0f, 2.0f);
		}
		boolean bl = true;
		this.left_arm.visible = bl;
		this.right_arm.visible = bl;
		this.arms.visible = !bl;
		this.animate(t.idleAnimationState, WildFinderAnimation.IDLE, f3);
		if (!t.isLeftHanded()) {
			if (!t.getOffhandItem().isEmpty())
				this.animate(t.idleAnimationState, WildFinderAnimation.IDLE1, f3);
			this.animate(t.waveAnimationState, WildFinderAnimation.WAVE1, f3);
			this.animate(t.aimAttackAnimationState, WildFinderAnimation.AIM1, f3);
			this.animate(t.shootAnimationState, WildFinderAnimation.SHOOT1, f3);
		} else {
			if (!t.getOffhandItem().isEmpty())
				this.animate(t.idleAnimationState, WildFinderAnimation.IDLE2, f3);
			this.animate(t.waveAnimationState, WildFinderAnimation.WAVE2, f3);
			this.animate(t.aimAttackAnimationState, WildFinderAnimation.AIM2, f3);
			this.animate(t.shootAnimationState, WildFinderAnimation.SHOOT2, f3);
		}
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
}