package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RedstoneGolemAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorGolemEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelredstone_golem<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "redstone_golem"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart up_body;
	private final ModelPart head;

	public Modelredstone_golem(ModelPart modelPart) {
		this.root = modelPart;
		this.body = modelPart.getChild("body");
		this.up_body = body.getChild("up_body");
		this.head = up_body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(120, 36).addBox(-11.0F, 14.0F, -7.0F, 22.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -33.0F, -10.0F, 40.0F, 32.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition head = up_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(124, 8).addBox(-8.0F, -6.0F, -12.0F, 16.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.75F, -10.0F));

		PartDefinition block = up_body.addOrReplaceChild("block", CubeListBuilder.create().texOffs(49, 90).addBox(-8.0F, -8.0F, -12.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.5F, 10.0F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 52).addBox(-14.0F, -4.0F, -6.0F, 14.0F, 24.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -27.0F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(3, 88).addBox(-5.5F, 0.0F, -10.0F, 11.0F, 22.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, 20.0F, 4.0F));

		PartDefinition right_finger1 = right_hand.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(97, 55).addBox(-1.5F, 0.0F, -6.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.0F, -2.5F));

		PartDefinition right_finger2 = right_hand.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(97, 55).addBox(-1.5F, 0.0F, -6.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.0F, 4.5F));

		PartDefinition right_finger3 = right_hand.addOrReplaceChild("right_finger3", CubeListBuilder.create().texOffs(81, 57).addBox(-1.5F, 0.0F, -6.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 22.0F, 2.5F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(3, 122).addBox(0.0F, -4.0F, -6.0F, 14.0F, 24.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -27.0F, 0.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(55, 124).addBox(-5.5F, 0.0F, -10.0F, 11.0F, 22.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(10.5F, 20.0F, 4.0F));

		PartDefinition left_finger1 = left_hand.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(97, 55).mirror().addBox(-1.5F, 0.0F, -6.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 22.0F, -2.5F));

		PartDefinition left_finger2 = left_hand.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(97, 55).mirror().addBox(-1.5F, 0.0F, -6.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 22.0F, 4.5F));

		PartDefinition left_finger3 = left_hand.addOrReplaceChild("left_finger3", CubeListBuilder.create().texOffs(81, 57).mirror().addBox(-1.5F, 0.0F, -6.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 22.0F, 2.5F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(113, 58).addBox(-6.0F, -2.0F, -10.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.5F, 6.0F, 4.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(161, 58).addBox(-6.0F, -2.0F, -10.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(14.5F, 6.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		if (t instanceof MerorGolemEntity merorGolemEntity) {
			this.animateWalk(RedstoneGolemAnimation.WALK, f, f2, 1.5f, 2.0f);
			this.animate(merorGolemEntity.attack1AnimationState, RedstoneGolemAnimation.ATTACK1, f3);
			this.animate(merorGolemEntity.attack2AnimationState, RedstoneGolemAnimation.ATTACK2, f3);
			this.animate(merorGolemEntity.idleAnimationState, RedstoneGolemAnimation.IDLE, f3);
			this.animate(merorGolemEntity.sendAnimationState, RedstoneGolemAnimation.SEND, f3);
			this.animate(merorGolemEntity.standAnimationState, RedstoneGolemAnimation.STAND, f3);
			this.animate(merorGolemEntity.standStartAnimationState, RedstoneGolemAnimation.STANDSTART, f3);
			this.animate(merorGolemEntity.standStopAnimationState, RedstoneGolemAnimation.STANDSTOP, f3);
			this.animate(merorGolemEntity.deadAnimationState, RedstoneGolemAnimation.DEAD, f3);
			this.animate(merorGolemEntity.pose1AnimationState, RedstoneGolemAnimation.POSE1, f3);
			this.animate(merorGolemEntity.pose2AnimationState, RedstoneGolemAnimation.POSE2, f3);
			this.animate(merorGolemEntity.pose3AnimationState, RedstoneGolemAnimation.POSE3, f3);
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}
}