package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DracognathusAnimation;
import com.jerotes.jerotesvillage.entity.Animal.DracognathusEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modeldracognathus<T extends DracognathusEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "dracognathus"), "main");
	private final ModelPart root;
	private final ModelPart head;
	public Modeldracognathus(ModelPart modelPart) {
		super(0.5f, 24.0f);
		this.root = modelPart;
		this.head = modelPart.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(35, 50).addBox(-2.5F, -3.8828F, -6.8969F, 5.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -12.5F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.49F, -4.8049F, -0.0565F, 0.6109F, 0.0F, 0.0F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(64, 15).addBox(0.0F, -2.161F, -0.2333F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9308F, 0.0881F, -1.8762F, 0.0F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(2.49F, -4.8049F, -0.0565F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(64, 15).mirror().addBox(0.0F, -2.161F, -0.2333F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.9308F, 0.0881F, -1.8762F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(65, 50).addBox(-2.5F, 0.1172F, -6.8969F, 5.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 6.2137F, -7.1104F));

		PartDefinition neck_hair_r1 = body.addOrReplaceChild("neck_hair_r1", CubeListBuilder.create().texOffs(64, -6).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.4651F, 0.25F, -0.1745F, 0.0F, 0.0F));

		PartDefinition neck_r1 = body.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(42, 0).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.9151F, -0.95F, -0.1745F, 0.0F, 0.0F));

		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(37, 35).addBox(-4.0F, -0.45F, -6.3F, 8.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(83, 48).addBox(-4.0F, -0.1F, -7.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(104, 38).addBox(3.0F, -1.45F, -5.3F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(83, 48).addBox(-4.0F, -1.45F, 5.7F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(104, 38).addBox(-4.0F, -1.45F, -5.3F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0137F, 8.9104F, -0.1309F, 0.0F, 0.0F));

		PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(0.0F, 2.1428F, 6.0395F));

		PartDefinition body1_hair_r1 = body1.addOrReplaceChild("body1_hair_r1", CubeListBuilder.create().texOffs(64, -7).addBox(0.0F, -0.5F, -5.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.2286F, 1.9813F, -0.1309F, 0.0F, 0.0F));

		PartDefinition body1_r1 = body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.5F, -5.0F, 9.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.2244F, -1.5465F, -0.1309F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 4.7033F, 9.6217F));

		PartDefinition body2_hair_r1 = body2.addOrReplaceChild("body2_hair_r1", CubeListBuilder.create().texOffs(64, -1).addBox(0.0F, -0.5F, -3.5F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4517F, 5.3834F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body2_r1 = body2.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -6.0F, -6.5F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.827F, 5.0806F, -0.2182F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.7669F, 19.1009F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail3_r1 = tail.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, -2.5F, -2.5F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5642F, 7.3988F, -0.5672F, 0.0F, 0.0F));

		PartDefinition tail2_hair_r1 = tail.addOrReplaceChild("tail2_hair_r1", CubeListBuilder.create().texOffs(64, 7).addBox(0.0F, -3.75F, -2.25F, 0.0F, 2.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 1.9642F, 5.4988F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail2_r1 = tail.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(16, 35).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.075F)), PartPose.offsetAndRotation(0.0F, 1.1694F, 5.1173F, -0.3927F, 0.0F, 0.0F));

		PartDefinition tail1_hair_r1 = tail.addOrReplaceChild("tail1_hair_r1", CubeListBuilder.create().texOffs(64, 3).addBox(0.0F, -1.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(0.0F, -2.5322F, 2.7596F, -0.3491F, 0.0F, 0.0F));

		PartDefinition tail1_r1 = tail.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(0, 35).addBox(-1.5F, -2.1022F, 2.2765F, 3.0F, 4.0F, 5.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -1.5366F, -2.8986F, -0.3491F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 10.0F, -4.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(1, 44).addBox(-1.0F, -3.0F, -2.5F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7159F, 1.7412F, -0.3F, 0.2618F, 0.0F, 0.2618F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(19, 41).addBox(-1.0F, -0.5F, -3.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 6.5F, 1.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 10.0F, -4.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(1, 44).mirror().addBox(-2.0F, -3.0F, -2.5F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7159F, 1.7412F, 0.5F, 0.1309F, 0.0F, -0.2618F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(19, 41).mirror().addBox(-1.0F, -0.5F, -3.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 6.5F, 1.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 13.0F, 8.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(34, 19).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.5F, -0.2F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5979F, 0.1059F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(34, 31).addBox(-1.5F, 1.5F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0979F, 0.3941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 13.0F, 8.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(34, 19).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 2.0F, -0.2F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(-0.5F, 4.5861F, 0.2141F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(34, 31).mirror().addBox(-1.5F, 1.5F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0861F, 0.2859F, 0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.3f;
			this.head.yScale = 1.3f;
			this.head.zScale = 1.3f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(DracognathusAnimation.RUSHRIDE, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(DracognathusAnimation.RUSH, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(DracognathusAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, DracognathusAnimation.IDLE, f3);
		this.animate(t.breathAnimationState, DracognathusAnimation.BREATH, f3);
		this.animate(t.attack1AnimationState, DracognathusAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, DracognathusAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, DracognathusAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, DracognathusAnimation.ATTACK4, f3);
		this.animate(t.sitAnimationState, DracognathusAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, DracognathusAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, DracognathusAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}