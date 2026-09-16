package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.HagAnimation;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.client.animation.LampWizardAnimation;
import com.jerotes.jerotesvillage.client.animation.TrumpeterAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.PurpleSandHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.BaseHagEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;


public class Modelhag<T extends LivingEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "hag"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modelhag(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -4.0F));

		PartDefinition mole = nose.addOrReplaceChild("mole", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 3.0F, -8.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -2.0F, 6.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat1 = hat.addOrReplaceChild("hat1", CubeListBuilder.create().texOffs(64, 46).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.75F, 0.0F));

		PartDefinition hat2 = hat1.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(100, 0).addBox(0.0F, 0.1047F, -1.9973F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -5.0F, -1.0F, -0.0524F, 0.0F, 0.0262F));

		PartDefinition hat3 = hat2.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(112, 11).addBox(0.0055F, 0.3127F, -1.9754F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.1047F, 0.0F, 0.0524F));

		PartDefinition hat4 = hat3.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(124, 19).addBox(0.0381F, 0.7144F, -1.8677F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, -0.2094F, 0.0F, 0.1047F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 4.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hair = body.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(36, 46).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 22).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof BaseHagEntity baseHagEntity) {
			this.head.yRot = f4 * ((float)Math.PI / 180F);
			this.head.xRot = f5 * ((float)Math.PI / 180F);
			if (baseHagEntity.isMelee()) {
				this.animateWalk(LampWizardAnimation.WALK, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(HagAnimation.WALK, f, f2, 2.0f, 2.0f);
			}
			if (baseHagEntity instanceof PurpleSandHagEntity) {
				this.animate(baseHagEntity.idleAnimationState, HagAnimation.BOSSIDLE, f3);
			}
			else {
				this.animate(baseHagEntity.idleAnimationState, HagAnimation.IDLE, f3);
			}
			if (baseHagEntity.isMelee()) {
				if (baseHagEntity.getMainHandItem().isEmpty()) {
					if (!baseHagEntity.isLeftHanded()) {
						this.animate(baseHagEntity.attack1AnimationState, HagAnimation.ATTACK1, f3);
						this.animate(baseHagEntity.attack2AnimationState, HagAnimation.ATTACK1, f3);
					} else {
						this.animate(baseHagEntity.attack1AnimationState, HagAnimation.ATTACK2, f3);
						this.animate(baseHagEntity.attack2AnimationState, HagAnimation.ATTACK2, f3);
					}
				}
				else {
					if (!baseHagEntity.isLeftHanded()) {
						this.animate(baseHagEntity.attack1AnimationState, TrumpeterAnimation.ITEMATTACK1, f3);
						this.animate(baseHagEntity.attack2AnimationState, TrumpeterAnimation.ITEMATTACK1, f3);
					} else {
						this.animate(baseHagEntity.attack1AnimationState, TrumpeterAnimation.ITEMATTACK2, f3);
						this.animate(baseHagEntity.attack2AnimationState, TrumpeterAnimation.ITEMATTACK2, f3);
					}
				}
			}
			else {
				this.animate(baseHagEntity.attack1AnimationState, HagAnimation.ATTACK1, f3);
				this.animate(baseHagEntity.attack2AnimationState, HagAnimation.ATTACK2, f3);
			}
			this.animate(baseHagEntity.spell1AnimationState, HagAnimation.SPELL1, f3);
			this.animate(baseHagEntity.spell2AnimationState, HagAnimation.SPELL2, f3);
			//盾牌
			if (!baseHagEntity.isLeftHanded()) {
				if (this.attackTime <= 0.0F) {
					this.animate(baseHagEntity.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
				}
				this.animate(baseHagEntity.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
			}
			else {
				if (this.attackTime <= 0.0F) {
					this.animate(baseHagEntity.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
				}
				this.animate(baseHagEntity.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
			}
			//
			this.hat.copyFrom(this.head);
			this.animate(baseHagEntity.deadAnimationState, HagAnimation.DEAD, f3);
			this.jacket.copyFrom(this.body);
			this.left_sleeve.copyFrom(this.left_arm);
			this.right_sleeve.copyFrom(this.right_arm);
			this.left_pants.copyFrom(this.left_leg);
			this.right_pants.copyFrom(this.right_leg);
		}
	}
}