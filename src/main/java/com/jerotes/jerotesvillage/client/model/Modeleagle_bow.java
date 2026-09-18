package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.EagleBowAnimation;
import com.jerotes.jerotesvillage.entity.Boss.EagleBowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Vector3f;


public class Modeleagle_bow<T extends EagleBowEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "eagle_bow"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modeleagle_bow(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.headwear = root.getChild("headwear");
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
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition headwear = partdefinition.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(8, 26).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0F, -0.2182F, -0.3054F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(48, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(56, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 16).addBox(-1.0F, 1.0F, -1.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(EagleBowAnimation.SHOOTWALK, f, f2 * Math.min(1, (t.shootAnimProgress / 10f)), 1.5f, 2.0f);
		this.animateWalk(EagleBowAnimation.ATTACKWALK, f, f2 * Math.min(1, (t.attackAnimProgress / 10f)) * Math.min(1, (1 - t.shootAnimProgress / 10f)), 1.5f, 2.0f);
		this.animateWalk(EagleBowAnimation.WALK, f, f2 * Math.min(1, (1 - t.attackAnimProgress / 10f)) * Math.min(1, (1 - t.shootAnimProgress / 10f)), 1.5f, 2.0f);
		this.animate(t.idleAnimationState, EagleBowAnimation.IDLE, f3);
		this.animateIdle2(t, t.idleAnimationState, EagleBowAnimation.IDLEBASE, f3);

		this.animate(t.attack1AnimationState, EagleBowAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, EagleBowAnimation.ATTACK2, f3);
		this.animate(t.meleeAttack1AnimationState, EagleBowAnimation.MELEEATTACK1, f3);
		this.animate(t.meleeAttack2AnimationState, EagleBowAnimation.MELEEATTACK2, f3);

		this.animate(t.shootSlow1AnimationState, EagleBowAnimation.SHOOTSLOW1, f3);
		this.animate(t.shootSlow2AnimationState, EagleBowAnimation.SHOOTSLOW2, f3);
		this.animate(t.shootSlow3AnimationState, EagleBowAnimation.SHOOTSLOW3, f3);
		this.animate(t.shootSlow4AnimationState, EagleBowAnimation.SHOOTSLOW4, f3);
		this.right_arm.xScale *= 1.001f;
		this.right_arm.zScale *= 1.001f;
		this.left_arm.xScale *= 1.001f;
		this.left_arm.zScale *= 1.001f;
		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}
	protected void animateIdle2(EagleBowEntity entity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			Modelspecial_action.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, 1 - entity.attackAnimProgress / 10f) * Math.min(1, 1 - entity.shootAnimProgress / 10f),
					new Vector3f());
		});
	}

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

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	public void setAllVisible(boolean bl) {
		this.head.visible = bl;
		this.hat.visible = bl;
		this.body.visible = bl;
		this.right_arm.visible = bl;
		this.left_arm.visible = bl;
		this.right_leg.visible = bl;
		this.left_leg.visible = bl;
	}
}