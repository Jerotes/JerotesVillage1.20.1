package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ScalyBearAnimation;
import com.jerotes.jerotesvillage.entity.Animal.ScalyBearEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelscaly_bear<T extends ScalyBearEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "scaly_bear"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelscaly_bear(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -6.0F, -6.0F, 9.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-2.5F, -1.0F, -9.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -13.0F));

		PartDefinition ear = head.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(26, 0).addBox(3.5F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 0).addBox(-5.5F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-7.0F, -0.5F, -7.25F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 81).addBox(-7.0F, 0.0F, -7.25F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 7.75F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(66, 0).addBox(-8.0F, -6.0F, -7.5F, 16.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(66, 65).addBox(-8.0F, -5.0F, -7.5F, 16.0F, 11.0F, 15.0F, new CubeDeformation(0.7F)), PartPose.offset(0.0F, -6.5F, 0.25F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(82, 27).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 9.0F, 8.0F, new CubeDeformation(0.05F)), PartPose.offset(-4.5F, 7.0F, 6.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(82, 44).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(82, 27).mirror().addBox(-2.0F, -3.0F, -2.0F, 4.0F, 9.0F, 8.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offset(4.5F, 7.0F, 6.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(82, 44).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(56, 27).addBox(-5.0F, -2.0F, -4.0F, 5.0F, 9.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(-3.5F, 6.0F, -6.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(56, 44).addBox(-5.0F, -2.0F, -4.0F, 5.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(56, 27).mirror().addBox(0.0F, -2.0F, -4.0F, 5.0F, 9.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(3.5F, 6.0F, -6.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(56, 44).mirror().addBox(0.0F, -2.0F, -4.0F, 5.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isBaby()) {
			this.head.xScale = 1.5f;
			this.head.yScale = 1.4f;
			this.head.zScale = 1.4f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()) {
			this.animateWalk(ScalyBearAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(ScalyBearAnimation.RUN, f, f2, 1.5f, 1.5f);
		}
		else {
			this.animateWalk(ScalyBearAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(((ScalyBearEntity)t).idleAnimationState, ScalyBearAnimation.IDLE, f3);
		this.animate(((ScalyBearEntity)t).attack1AnimationState, ScalyBearAnimation.ATTACK1, f3);
		this.animate(((ScalyBearEntity)t).attack2AnimationState, ScalyBearAnimation.ATTACK2, f3);
		this.animate(((ScalyBearEntity)t).attack3AnimationState, ScalyBearAnimation.ATTACK3, f3);
		this.animate(((ScalyBearEntity)t).attack4AnimationState, ScalyBearAnimation.ATTACK4, f3);
		this.animate(((ScalyBearEntity)t).sitAnimationState, ScalyBearAnimation.SIT, f3);
		this.animate(((ScalyBearEntity)t).toSitAnimationState, ScalyBearAnimation.TOSIT, f3);
		this.animate(((ScalyBearEntity)t).stopSitAnimationState, ScalyBearAnimation.STOPSIT, f3);
		this.animate(((ScalyBearEntity)t).danceAnimationState, ScalyBearAnimation.DANCE, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
