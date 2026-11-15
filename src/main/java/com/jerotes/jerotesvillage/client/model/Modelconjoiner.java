package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.client.animation.ConjoinerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.ConjoinerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelconjoiner<T extends ConjoinerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("jerotesvillage", "conjoiner"), "main");
	private final ModelPart rootPart;
	private final ModelPart head;
	private final ModelPart left_head;
	private final ModelPart right_head;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;

	public Modelconjoiner(ModelPart root) {
		this.rootPart = root;
		this.head = root.getChild("head");
		this.left_head = this.head.getChild("left_head");
		this.right_head = this.head.getChild("right_head");
		this.body = root.getChild("body");
		this.body2 = this.body.getChild("body2");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition left_head = head.addOrReplaceChild("left_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -10.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.005F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition right_head = head.addOrReplaceChild("right_head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, -10.0F, -3.0F, 12.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(30, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 37).mirror().addBox(0.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, -9.0F, 0.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(52, 37).mirror().addBox(-2.0F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 9.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 37).addBox(-4.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -9.0F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(52, 37).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 9.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 51).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 6.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(50, 52).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 51).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 6.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(50, 52).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.rootPart;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.left_head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.left_head.xRot = headPitch * ((float) Math.PI / 180F);
		this.right_head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.right_head.xRot = headPitch * ((float) Math.PI / 180F);
		this.animateWalk(ConjoinerAnimation.RUN, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		this.animate(entity.idleAnimationState, ConjoinerAnimation.IDLE, ageInTicks);
		this.animate(entity.attack1AnimationState, ConjoinerAnimation.ATTACK1, ageInTicks);
		this.animate(entity.attack2AnimationState, ConjoinerAnimation.ATTACK2, ageInTicks);
		this.animate(entity.attack3AnimationState, ConjoinerAnimation.ATTACK3, ageInTicks);
		this.animate(entity.attack4AnimationState, ConjoinerAnimation.ATTACK4, ageInTicks);
		this.animate(entity.attack5AnimationState, ConjoinerAnimation.ATTACK5, ageInTicks);
		this.animate(entity.attack6AnimationState, ConjoinerAnimation.ATTACK6, ageInTicks);
		this.animate(entity.attack7AnimationState, ConjoinerAnimation.ATTACK7, ageInTicks);
		this.animate(entity.attack8AnimationState, ConjoinerAnimation.ATTACK8, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}