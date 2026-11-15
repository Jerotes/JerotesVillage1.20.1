package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.client.animation.MockerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.MockerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

public class Modelmocker<T extends MockerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("jerotesvillage", "mocker"), "main");
	private final ModelPart rootPart;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart body4;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_hand;

	public Modelmocker(ModelPart root) {
		this.rootPart = root;
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.body1 = this.body.getChild("body1");
		this.body2 = this.body1.getChild("body2");
		this.body3 = this.body2.getChild("body3");
		this.body4 = this.body3.getChild("body4");
		this.left_arm = this.body.getChild("left_arm");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.right_arm = this.body.getChild("right_arm");
		this.right_hand = this.right_arm.getChild("right_hand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -8.0F, -4.0F, 11.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(38, 0).addBox(-5.0F, -1.0F, -4.0F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(66, 2).addBox(-5.0F, -1.0F, -4.0F, 10.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 15).addBox(-7.5F, -7.0F, -2.5F, 15.0F, 14.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 1.0F, 4.5F));

		PartDefinition back_r1 = body1.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(40, 28).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 28).addBox(-1.5F, -5.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 28).addBox(-1.5F, -9.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(40, 15).addBox(-6.0F, -0.5F, -3.75F, 12.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

		PartDefinition back_r2 = body2.addOrReplaceChild("back_r2", CubeListBuilder.create().texOffs(40, 31).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 31).addBox(-1.0F, -4.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 1.75F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(16, 34).addBox(-4.0F, -0.5F, -2.75F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.5F, -0.5F));

		PartDefinition back_r3 = body3.addOrReplaceChild("back_r3", CubeListBuilder.create().texOffs(40, 31).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 31).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 1.75F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(16, 46).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.5F, 0.0F));

		PartDefinition back_r4 = body4.addOrReplaceChild("back_r4", CubeListBuilder.create().texOffs(40, 31).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 31).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.5F, 1.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(0.0F, -2.0F, -3.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.5F, -3.0F, 3.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(40, 41).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(28, 46).mirror().addBox(1.0F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(28, 46).mirror().addBox(-0.5F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(28, 46).mirror().addBox(-2.0F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 15.0F, -1.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 39).addBox(-4.0F, -2.0F, -3.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, -3.0F, 3.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 46).addBox(-2.0F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 46).addBox(1.0F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 46).addBox(-0.5F, 16.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 15.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(MockerAnimation.WALK, limbSwing, limbSwingAmount * Math.min(1, 1 - entity.attackAnimProgress / 20f), 2.0f, 2.0f);
		this.animate(entity.idleAnimationState, MockerAnimation.IDLE, ageInTicks);
		this.animate(entity.idleAnimationState, MockerAnimation.IDLEHEAD, ageInTicks);
		this.animate(entity.idleAnimationState, MockerAnimation.IDLETAIL, ageInTicks);
		this.animateIdle2(entity,entity.idleAnimationState, MockerAnimation.WALK, ageInTicks);
        this.animate(entity.attack1AnimationState, MockerAnimation.ATTACK1, ageInTicks);
		this.animate(entity.attack2AnimationState, MockerAnimation.ATTACK2, ageInTicks);
		this.animate(entity.attack3AnimationState, MockerAnimation.ATTACK3, ageInTicks);
		this.animate(entity.attack4AnimationState, MockerAnimation.ATTACK4, ageInTicks);
		this.animate(entity.laughAnimationState, MockerAnimation.LAUGH, ageInTicks);
		this.animate(entity.jumpAnimationState, MockerAnimation.JUMP, ageInTicks);
		KeyframeAnimations.animate(this, MockerAnimation.CLIMB, 0L, entity.climbAnimProgress / 20f, new Vector3f());
	}
	protected void animateIdle2(T adventurerEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, adventurerEntity.climbAnimProgress / 20f),
					new Vector3f());
		});
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -30.0f, 30.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.rootPart;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
