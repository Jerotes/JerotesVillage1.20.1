package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RusherAnimation;
import com.jerotes.jerotesvillage.entity.Animal.RusherEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelrusher<T extends RusherEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rusher"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelrusher(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0054F, -13.4424F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5696F, -7.9519F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4946F, -0.0576F, 0.3054F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 15).addBox(-5.1667F, -15.3333F, -5.0F, 10.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 46).addBox(-4.6667F, -5.3333F, -4.5F, 9.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(64, 18).addBox(-4.6667F, -5.3333F, -4.0F, 9.0F, 7.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(64, 0).addBox(-5.1667F, -13.0F, -4.3333F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.1667F, 8.0F, 5.3333F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_r1 = body.addOrReplaceChild("saddle_r1", CubeListBuilder.create().texOffs(25, 35).addBox(-5.0F, -1.0F, -4.5F, 10.0F, 2.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-0.1667F, -4.5F, 3.6667F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r1 = body.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -2.9645F, -4.136F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1667F, -15.8797F, 4.1488F, -2.3562F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.1667F, 4.4226F, 2.3687F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(35, 46).addBox(-1.5F, -8.0F, 6.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4005F, -10.5824F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-5.0F, 8.0F, 7.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3741F, 2.5473F, 1.2328F, -0.1745F, 0.0175F, 0.0873F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.5F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-1.9045F, -4.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0955F, 3.204F, 1.3087F, 0.2094F, -0.0349F, 0.0087F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(5.0F, 8.0F, 7.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.3741F, 2.5473F, 1.2328F, -0.1745F, -0.0175F, -0.0873F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.5F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-2.0955F, -4.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0955F, 3.204F, 1.3087F, 0.2094F, 0.0349F, -0.0087F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-6.0F, 6.0F, -6.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-2.0F, -15.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 13.0F, 1.0F, 0.1309F, -0.0113F, 0.0873F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(48, 49).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(6.0F, 6.0F, -6.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-2.0F, -15.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 13.0F, 1.0F, 0.1309F, 0.0113F, -0.0873F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(48, 49).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.4f;
			this.head.yScale = 1.4f;
			this.head.zScale = 1.4f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if ((t.isSaddled() && t.isVehicle()) || t.getTarget() != null) {
			this.animateWalk(RusherAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(RusherAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.attackAnimationState, RusherAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, RusherAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, RusherAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, RusherAnimation.STOPSIT, f3);
		this.animate(t.jumpAnimationState, RusherAnimation.JUMP, f3);
		this.animate(t.longJumpAnimationState, RusherAnimation.LONGJUMP, f3);
		this.animate(t.eatAnimationState, RusherAnimation.EAT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}