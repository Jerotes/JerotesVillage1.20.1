package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RedstoneMonstrosityAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMonstrosityEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelredstone_monstrosity<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "redstone_monstrosity"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelredstone_monstrosity(ModelPart modelPart) {
		this.root = modelPart;
		this.body = modelPart.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 52).addBox(-37.6667F, -35.3333F, -18.3333F, 74.0F, 57.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(0, 139).addBox(-14.6667F, 21.6667F, -14.3333F, 28.0F, 11.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(98, 139).addBox(-14.6667F, -28.3333F, 10.6667F, 28.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.6667F, -33.6667F, 5.3333F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -11.75F, -15.25F, 28.0F, 31.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(196, 0).addBox(-24.0F, -9.75F, -10.25F, 11.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(292, 0).addBox(-33.0F, -24.75F, -10.25F, 9.0F, 28.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(244, 0).addBox(14.0F, -9.75F, -10.25F, 11.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(336, 0).addBox(25.0F, -24.75F, -10.25F, 9.0F, 28.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.6667F, -19.5833F, -20.3333F));

		PartDefinition eye = head.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.25F, 9.25F, -15.5F));

		PartDefinition eye2 = head.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 4).addBox(-3.0F, -1.5F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 5.75F, -15.5F));

		PartDefinition eye3 = head.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(12.25F, 9.25F, -15.5F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(98, 0).addBox(-14.0F, -5.5F, -15.25F, 28.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(77, 31).addBox(-14.0F, 0.5F, -15.25F, 28.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.75F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(303, 41).addBox(-1.25F, -27.0F, -10.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F))
				.texOffs(336, 91).addBox(-1.25F, -4.0F, -10.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(37.5833F, -23.3333F, -6.3333F));

		PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(380, 0).mirror().addBox(-11.0F, -2.0F, -5.0F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(16.75F, 19.0F, -0.5F));

		PartDefinition left_hand = left_arm2.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(284, 209).addBox(-15.5F, -2.0F, -10.5F, 29.0F, 20.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 22.0F, 0.0F));

		PartDefinition left_finger1 = left_hand.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(472, 0).addBox(-2.5F, 0.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 18.0F, -5.0F));

		PartDefinition left_finger2 = left_hand.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(472, 0).addBox(-2.5F, 0.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 18.0F, 4.0F));

		PartDefinition left_finger3 = left_hand.addOrReplaceChild("left_finger3", CubeListBuilder.create().texOffs(472, 0).mirror().addBox(-2.5F, 0.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 18.0F, 1.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(208, 41).addBox(-18.75F, -27.0F, -10.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F))
				.texOffs(208, 91).addBox(-35.75F, -4.0F, -10.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(-38.9167F, -23.3333F, -6.3333F));

		PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(380, 0).addBox(-11.0F, -2.0F, -5.0F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.75F, 19.0F, -0.5F));

		PartDefinition right_hand = right_arm2.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(172, 209).addBox(-13.5F, -2.0F, -10.5F, 29.0F, 20.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 22.0F, 0.0F));

		PartDefinition right_finger1 = right_hand.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(456, 0).addBox(-1.5F, 2.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 16.0F, -5.0F));

		PartDefinition right_finger2 = right_hand.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(456, 0).addBox(-1.5F, 2.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 16.0F, 4.0F));

		PartDefinition right_finger3 = right_hand.addOrReplaceChild("right_finger3", CubeListBuilder.create().texOffs(456, 0).mirror().addBox(-1.5F, 2.0F, 0.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 16.0F, 1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 206).addBox(-11.0F, -2.5F, -7.5F, 22.0F, 29.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -2.5F, -3.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(86, 206).addBox(-11.0F, -2.5F, -7.5F, 22.0F, 29.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -2.5F, -3.0F));

		return LayerDefinition.create(meshdefinition, 512, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		this.applyBodyRotation(t, f4, f5, f3);
		if (t instanceof MerorMonstrosityEntity merorMonstrosity) {
			this.animateWalk(RedstoneMonstrosityAnimation.WALK, f, f2, 1.5f, 2.0f);
			this.animate(merorMonstrosity.attack1AnimationState, RedstoneMonstrosityAnimation.ATTACK1, f3);
			this.animate(merorMonstrosity.attack2AnimationState, RedstoneMonstrosityAnimation.ATTACK2, f3);
			this.animate(merorMonstrosity.idleAnimationState, RedstoneMonstrosityAnimation.IDLE, f3);
			this.animate(merorMonstrosity.sendAnimationState, RedstoneMonstrosityAnimation.SEND, f3);
			this.animate(merorMonstrosity.spellAnimationState, RedstoneMonstrosityAnimation.SPELL, f3);
			this.animate(merorMonstrosity.standAnimationState, RedstoneMonstrosityAnimation.STAND, f3);
			this.animate(merorMonstrosity.standStartAnimationState, RedstoneMonstrosityAnimation.STANDSTART, f3);
			this.animate(merorMonstrosity.standStopAnimationState, RedstoneMonstrosityAnimation.STANDSTOP, f3);
			this.animate(merorMonstrosity.deadAnimationState, RedstoneMonstrosityAnimation.DEAD, f3);
			this.animate(merorMonstrosity.pose1AnimationState, RedstoneMonstrosityAnimation.POSE1, f3);
			this.animate(merorMonstrosity.pose2AnimationState, RedstoneMonstrosityAnimation.POSE2, f3);
			this.animate(merorMonstrosity.pose3AnimationState, RedstoneMonstrosityAnimation.POSE3, f3);
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}
	private void applyBodyRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -5.0f, 5.0f);
		f2 = Mth.clamp(f2, -5.0f, 5.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}
}