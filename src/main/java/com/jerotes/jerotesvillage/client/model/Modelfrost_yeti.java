package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FrostYetiAnimation;
import com.jerotes.jerotesvillage.entity.Animal.SnowyApeEntity;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;


public class Modelfrost_yeti<T extends LivingEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "frost_yeti"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;

	private final float youngScaleFactor;
	private final float bodyYOffset;

	public Modelfrost_yeti(ModelPart root) {
		super(root);
		this.bodyYOffset = 0.5f;
		this.youngScaleFactor = 0.5f;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.right_arm = root.getChild("right_arm");
		this.right_hand = this.right_arm.getChild("right_hand");
		this.left_leg = root.getChild("left_leg");
		this.left_foot = this.left_leg.getChild("left_foot");
		this.right_leg = root.getChild("right_leg");
		this.right_foot = this.right_leg.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 24).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -4.0F, -5.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -9.6302F, -0.8218F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(34, 10).addBox(-5.0F, -6.0F, -1.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6302F, 0.8218F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -15.0F, 0.0F, 14.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6302F, 0.8218F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(8.0F, -9.0F, 0.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(42, 37).mirror().addBox(-2.4132F, -6.5076F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 32).mirror().addBox(-2.4132F, -6.5076F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.4132F, 6.5076F, -1.0F, 0.1745F, 0.0F, -0.1745F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(2.5F, 11.0F, -1.0F));

		PartDefinition left_hand_r1 = left_hand.addOrReplaceChild("left_hand_r1", CubeListBuilder.create().texOffs(42, 37).mirror().addBox(-2.5F, -6.5F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 51).mirror().addBox(-2.5F, -6.5F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-8.0F, -9.0F, 0.0F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(42, 37).addBox(-2.5868F, -6.5076F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(0, 32).addBox(-2.5868F, -6.5076F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.4132F, 6.5076F, -1.0F, 0.1745F, 0.0F, 0.1745F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-2.5F, 11.0F, -1.0F));

		PartDefinition right_hand_r1 = right_hand.addOrReplaceChild("right_hand_r1", CubeListBuilder.create().texOffs(42, 37).addBox(-2.5F, -6.5F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(0, 51).addBox(-2.5F, -6.5F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 6.0F, 0.0F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(22, 37).mirror().addBox(-2.5F, -5.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 4.5F, 0.5F, -0.3054F, 0.0F, -0.0873F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(22, 53).mirror().addBox(-2.5F, -0.5F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(40, 0).mirror().addBox(-2.5F, 5.5F, -2.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.5F, 7.5F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 6.0F, 0.0F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(22, 37).addBox(-2.5F, -5.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.5F, -0.3054F, 0.0F, 0.0873F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(22, 53).addBox(-2.5F, -0.5F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(40, 0).addBox(-2.5F, 5.5F, -2.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-0.5F, 7.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		if (t instanceof FrostYetiEntity frostYeti) {
			if (frostYeti.isYetiRush()) {
				this.animateWalk(FrostYetiAnimation.RUSH, f, f2, 1.5f, 2.0f);
			}
			else if (frostYeti.isAggressive()) {
				this.animateWalk(FrostYetiAnimation.RUN, f, f2, 1.5f, 2.0f);
			} else {
				this.animateWalk(FrostYetiAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			this.animate(frostYeti.attack1AnimationState, FrostYetiAnimation.ATTACK1, f3);
			this.animate(frostYeti.attack2AnimationState, FrostYetiAnimation.ATTACK2, f3);
			this.animate(frostYeti.attack3AnimationState, FrostYetiAnimation.ATTACK3, f3);
			if (!frostYeti.isLeftHanded()){
				this.animate(frostYeti.throwAnimationState, FrostYetiAnimation.THROW1, f3);
			}
			else {
				this.animate(frostYeti.throwAnimationState, FrostYetiAnimation.THROW2, f3);
			}
			this.animate(frostYeti.idleAnimationState, FrostYetiAnimation.IDLE, f3);
			this.animate(frostYeti.deterAnimationState, FrostYetiAnimation.DETER, f3);
			this.animate(frostYeti.deadAnimationState, FrostYetiAnimation.DEAD, f3);
		}
		if (t instanceof SnowyApeEntity snowyApe) {
			if (snowyApe.isBaby()) {
				this.head.xScale = 1.25f;
				this.head.yScale = 1.25f;
				this.head.zScale = 1.25f;
			}
			if (snowyApe.isVehicle()) {
				this.animateWalk(FrostYetiAnimation.RIDERUN, f, f2, 1.5f, 2.0f);
			} else if (snowyApe.isAggressive()) {
				this.animateWalk(FrostYetiAnimation.RUN, f, f2, 1.5f, 2.0f);
			} else {
				this.animateWalk(FrostYetiAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			this.animate(snowyApe.attack1AnimationState, FrostYetiAnimation.ATTACK1, f3);
			this.animate(snowyApe.attack2AnimationState, FrostYetiAnimation.ATTACK2, f3);
			this.animate(snowyApe.attack3AnimationState, FrostYetiAnimation.ATTACK3, f3);
			if (!snowyApe.isLeftHanded()){
				this.animate(snowyApe.throwAnimationState, FrostYetiAnimation.THROW3, f3);
			}
			else {
				this.animate(snowyApe.throwAnimationState, FrostYetiAnimation.THROW4, f3);
			}
			this.animate(snowyApe.idleAnimationState, FrostYetiAnimation.IDLE, f3);
			this.animate(snowyApe.sitAnimationState, FrostYetiAnimation.SIT, f3);
			this.animate(snowyApe.toSitAnimationState, FrostYetiAnimation.TOSIT, f3);
			this.animate(snowyApe.stopSitAnimationState, FrostYetiAnimation.STOPSIT, f3);
			//盾牌
			if (!snowyApe.isLeftHanded()) {
				if (this.attackTime <= 0.0F) {
					this.animate(snowyApe.shieldUseMainhandAnimationState, FrostYetiAnimation.RIGHTBLOCK, f3);
				}
				this.animate(snowyApe.shieldUseOffhandAnimationState, FrostYetiAnimation.LEFTBLOCK, f3);
			}
			else {
				if (this.attackTime <= 0.0F) {
					this.animate(snowyApe.shieldUseMainhandAnimationState, FrostYetiAnimation.LEFTBLOCK, f3);
				}
				this.animate(snowyApe.shieldUseOffhandAnimationState, FrostYetiAnimation.RIGHTBLOCK, f3);
			}
			//
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getHand(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getHand(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_hand;
		}
		return this.right_hand;
	}

	@Override
	public void renderToBuffer(PoseStack p_273029_, VertexConsumer p_272763_, int p_273665_, int p_272602_, float p_273190_, float p_273731_, float p_272609_, float p_273331_) {
		if (this.young) {
			p_273029_.pushPose();
			p_273029_.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
			p_273029_.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
			this.root().render(p_273029_, p_272763_, p_273665_, p_272602_, p_273190_, p_273731_, p_272609_, p_273331_);
			p_273029_.popPose();
		} else {
			this.root().render(p_273029_, p_272763_, p_273665_, p_272602_, p_273190_, p_273731_, p_272609_, p_273331_);
		}

	}
}