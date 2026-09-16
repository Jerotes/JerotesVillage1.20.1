package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FirepowerPourerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FirepowerPourerEntity;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelfirepower_pourer<T extends FirepowerPourerEntity> extends HierarchicalModel<T> implements HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "firepower_pourer"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart top;
	private final ModelPart head;
	private final ModelPart top_head;

	public Modelfirepower_pourer(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.top = this.body.getChild("top");
		this.head = this.top.getChild("head");
		this.top_head = this.top.getChild("top_head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.875F, -1.5F));

		PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 40).addBox(-13.0F, -26.875F, -7.5F, 26.0F, 18.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 72).addBox(-11.0F, -9.875F, -5.5F, 22.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(64, 72).addBox(-10.0F, -5.875F, -4.5F, 20.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 72).addBox(-1.0F, -0.875F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.5F))
				.texOffs(0, 72).addBox(-4.0F, -0.875F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.6F))
				.texOffs(0, 72).addBox(2.0F, -0.875F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.6F))
				.texOffs(216, 45).addBox(-7.0F, -24.875F, 6.5F, 14.0F, 18.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 2.75F, 2.0F));

		PartDefinition top_head = top.addOrReplaceChild("top_head", CubeListBuilder.create(), PartPose.offset(0.0F, -21.875F, 2.0F));

		PartDefinition right_arm = top.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 86).addBox(-8.0F, -6.5F, -6.5F, 10.0F, 18.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(54, 75).addBox(-6.0F, -11.5F, 1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 75).addBox(-2.0F, -11.5F, 1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 75).addBox(-4.0F, -11.5F, -3.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 116).addBox(-9.0F, 3.5F, -3.5F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(14, 116).addBox(-10.0F, -0.5F, -4.5F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, -18.875F, 0.0F));

		PartDefinition right_joint_r1 = right_arm.addOrReplaceChild("right_joint_r1", CubeListBuilder.create().texOffs(32, 86).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 13.0F, -0.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(46, 88).addBox(-4.0F, -0.5F, -5.0F, 8.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(72, 85).addBox(-4.0F, 11.5F, -5.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.0F, 15.0F, -0.5F, -1.5272F, 0.0F, 0.0F));

		PartDefinition right_joint_r2 = right_hand.addOrReplaceChild("right_joint_r2", CubeListBuilder.create().texOffs(32, 86).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition chainsaw = right_hand.addOrReplaceChild("chainsaw", CubeListBuilder.create().texOffs(0, 210).addBox(-3.5F, -2.125F, -4.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 224).addBox(-1.5F, 2.875F, -2.5F, 3.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 232).addBox(-0.5F, 1.875F, -4.5F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 232).addBox(0.5F, 1.875F, -4.5F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.625F, 0.0F));

		PartDefinition left_arm = top.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 86).mirror().addBox(-2.0F, -6.5F, -6.5F, 10.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 75).mirror().addBox(2.0F, -11.5F, -3.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 75).mirror().addBox(4.0F, -11.5F, 1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 75).mirror().addBox(0.0F, -11.5F, 1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 116).mirror().addBox(8.0F, 3.5F, -3.5F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(14, 116).mirror().addBox(9.0F, -0.5F, -4.5F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(15.0F, -18.875F, 0.0F));

		PartDefinition left_joint_r1 = left_arm.addOrReplaceChild("left_joint_r1", CubeListBuilder.create().texOffs(32, 86).mirror().addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 13.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(46, 88).mirror().addBox(-4.0F, -0.5F, -5.0F, 8.0F, 12.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(72, 85).mirror().addBox(-4.0F, 11.5F, -5.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(2.0F, 15.0F, -0.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition left_joint_r2 = left_hand.addOrReplaceChild("left_joint_r2", CubeListBuilder.create().texOffs(32, 86).mirror().addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition gun = left_hand.addOrReplaceChild("gun", CubeListBuilder.create(), PartPose.offset(0.0F, 13.625F, 0.0F));

		PartDefinition gun_main = gun.addOrReplaceChild("gun_main", CubeListBuilder.create().texOffs(0, 176).mirror().addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 4.375F, 0.0F));

		PartDefinition gun_other = gun.addOrReplaceChild("gun_other", CubeListBuilder.create().texOffs(0, 194).mirror().addBox(-1.5F, -5.5F, -2.5F, 3.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.875F, 0.0F));

		PartDefinition gun1 = gun_other.addOrReplaceChild("gun1", CubeListBuilder.create().texOffs(22, 194).mirror().addBox(-2.5F, 0.9375F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(2.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(26, 196).mirror().addBox(3.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(4.5F, -1.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(-3.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(26, 196).mirror().addBox(-4.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(-5.5F, -1.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(16, 194).mirror().addBox(-1.0F, -9.0625F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 3.5625F, -3.0F));

		PartDefinition gun2 = gun_other.addOrReplaceChild("gun2", CubeListBuilder.create().texOffs(22, 194).mirror().addBox(-2.5F, 0.9375F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(2.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(26, 196).mirror().addBox(3.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(4.5F, -1.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(-3.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(26, 196).mirror().addBox(-4.5F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(22, 196).mirror().addBox(-5.5F, -1.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(16, 194).mirror().addBox(-1.0F, -9.0625F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 3.5625F, 3.0F));

		PartDefinition head = top.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.5F, -7.5F, 16.0F, 10.0F, 12.0F, new CubeDeformation(0.005F))
				.texOffs(0, 22).addBox(-9.0F, -11.5F, -8.0F, 18.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(44, 0).addBox(-2.0F, -3.5F, -10.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.875F, -2.5F));

		PartDefinition left_head = head.addOrReplaceChild("left_head", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-8.5F, -0.5F, -6.5F, 9.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.5F, -12.0F, -1.5F));

		PartDefinition right_head = head.addOrReplaceChild("right_head", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -0.5F, -6.5F, 9.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, -12.0F, -1.5F));

		PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(32, 116).addBox(-4.0F, 1.0F, -7.0F, 8.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(80, 54).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(128, 59).addBox(-10.0F, -1.0F, -10.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(-0.25F))
				.texOffs(128, 59).addBox(-4.0F, -1.0F, 1.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(-0.25F))
				.texOffs(128, 59).addBox(2.0F, -1.0F, -10.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(-0.25F))
				.texOffs(0, 72).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.6F))
				.texOffs(0, 72).addBox(-4.0F, -5.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.6F))
				.texOffs(0, 72).addBox(2.0F, -5.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 6.875F, 2.0F));

		PartDefinition left_leg = bottom.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 134).addBox(-2.0F, -1.0F, -6.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 162).addBox(3.0F, -2.0F, -7.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 3.0F, -6.0F, -0.1298F, -0.017F, -0.1298F));

		PartDefinition left_roll = left_leg.addOrReplaceChild("left_roll", CubeListBuilder.create().texOffs(16, 162).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 19.0F, -5.0F));

		PartDefinition right_leg = bottom.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 162).addBox(-7.0F, -2.0F, -7.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 134).addBox(-6.0F, -1.0F, -6.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 3.0F, -6.0F, -0.1298F, 0.017F, 0.1298F));

		PartDefinition right_roll = right_leg.addOrReplaceChild("right_roll", CubeListBuilder.create().texOffs(16, 162).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 19.0F, -5.0F));

		PartDefinition hind_leg = bottom.addOrReplaceChild("hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 5.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition hind_leg_r1 = hind_leg.addOrReplaceChild("hind_leg_r1", CubeListBuilder.create().texOffs(0, 162).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition hind_leg_r2 = hind_leg.addOrReplaceChild("hind_leg_r2", CubeListBuilder.create().texOffs(0, 134).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition hind_roll = hind_leg.addOrReplaceChild("hind_roll", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 5.0F));

		PartDefinition hind_roll_r1 = hind_roll.addOrReplaceChild("hind_roll_r1", CubeListBuilder.create().texOffs(16, 162).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public ModelPart getHead() {
		return this.top_head;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.animateWalk(FirepowerPourerAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(t.idleAnimationState, FirepowerPourerAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, FirepowerPourerAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, FirepowerPourerAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, FirepowerPourerAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, FirepowerPourerAnimation.ATTACK4, f3);
		this.animate(t.attack5AnimationState, FirepowerPourerAnimation.ATTACK5, f3);
		this.animate(t.break1AnimationState, FirepowerPourerAnimation.BREAK1, f3);
		this.animate(t.break2AnimationState, FirepowerPourerAnimation.BREAK2, f3);
		this.animate(t.shootAnimationState, FirepowerPourerAnimation.SHOOT, f3);
		this.animate(t.coolAnimationState, FirepowerPourerAnimation.COOL, f3);
		this.animate(t.deadAnimationState, FirepowerPourerAnimation.DEAD, f3);
	}
}