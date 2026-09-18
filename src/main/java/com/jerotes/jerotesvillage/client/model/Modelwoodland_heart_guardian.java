package com.jerotes.jerotesvillage.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.WoodlandHeartGuardianAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WoodlandHeartGuardianEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Vector3f;


public class Modelwoodland_heart_guardian<T extends WoodlandHeartGuardianEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "woodland_heart_guardian"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart left_slash;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart right_slash;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart slash;
	private final ModelPart slash_add;

	public Modelwoodland_heart_guardian(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.left_slash = this.left_weapon.getChild("left_slash");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.right_slash = this.right_weapon.getChild("right_slash");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.slash = root.getChild("slash");
		this.slash_add = root.getChild("slash_add");
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

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0F, -0.2182F, -0.3054F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(48, 24).mirror().addBox(-1.0F, 6.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false)
				.texOffs(48, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition left_slash = left_weapon.addOrReplaceChild("left_slash", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -6.6753F, -0.0355F, 0.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.3747F, -9.8823F, 1.789F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.15F))
				.texOffs(48, 24).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition right_slash = right_weapon.addOrReplaceChild("right_slash", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -6.6753F, -0.0355F, 0.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.3747F, -9.8823F, 1.789F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

		PartDefinition slash = partdefinition.addOrReplaceChild("slash", CubeListBuilder.create().texOffs(32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition slash_add = partdefinition.addOrReplaceChild("slash_add", CubeListBuilder.create().texOffs(32, 32).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	protected Iterable<ModelPart> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(this.slash, this.slash_add));
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.left_slash.visible = t.getOffhandItem().is(JerotesVillageItems.WILDERNESS_SWORD.get());
		this.right_slash.visible = t.getMainHandItem().is(JerotesVillageItems.WILDERNESS_SWORD.get());
		this.animateWalk(WoodlandHeartGuardianAnimation.RUN1, f, f2 * Math.min(1, (t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animateWalk(WoodlandHeartGuardianAnimation.WALK, f, f2 * Math.min(1, (1 - t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animate(t.idleAnimationState, WoodlandHeartGuardianAnimation.IDLE, f3);
		this.animate(t.left1AnimationState, WoodlandHeartGuardianAnimation.LEFT1, f3);
		this.animate(t.right1AnimationState, WoodlandHeartGuardianAnimation.RIGHT1, f3);
		this.animate(t.left2AnimationState, WoodlandHeartGuardianAnimation.LEFT2, f3);
		this.animate(t.right2AnimationState, WoodlandHeartGuardianAnimation.RIGHT2, f3);
		this.animate(t.left3AnimationState, WoodlandHeartGuardianAnimation.LEFT3, f3);
		this.animate(t.right3AnimationState, WoodlandHeartGuardianAnimation.RIGHT3, f3);
		this.animate(t.cutAnimationState, WoodlandHeartGuardianAnimation.CUT, f3);
		this.animate(t.rollAnimationState, WoodlandHeartGuardianAnimation.ROLL, f3);
		this.animate(t.deadAnimationState, WoodlandHeartGuardianAnimation.DEAD, f3);
		Modelspecial_action.animate(this, WoodlandHeartGuardianAnimation.ATTACKIDLE,
				0L, t.isAlive() ? Math.min(1, t.attackAnimProgress / 10f) : 0, new Vector3f());
		this.right_arm.xScale *= 1.001f;
		this.right_arm.zScale *= 1.001f;
		this.left_arm.xScale *= 1.001f;
		this.left_arm.zScale *= 1.001f;

//		this.slash.x *= 1.15f;
//		this.slash.z *= 1.15f;
//		this.slash_add.x *= 1.15f;
//		this.slash_add.z *= 1.15f;

		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
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