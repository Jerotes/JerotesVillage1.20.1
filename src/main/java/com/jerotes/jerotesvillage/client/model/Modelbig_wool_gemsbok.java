package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BigWoolGemsbokAnimation;
import com.jerotes.jerotesvillage.client.animation.WildernessBullAnimation;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelbig_wool_gemsbok<T extends BigWoolGemsbokEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "big_wool_gemsbok"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelbig_wool_gemsbok(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -4.75F, -3.0833F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(20, 0).addBox(-2.0F, -3.85F, -6.9833F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(93, 37).addBox(-2.5F, -3.85F, -4.9833F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(0, 60).addBox(0.0F, 1.25F, -3.0833F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(18, 69).addBox(-2.5F, -4.75F, -3.0833F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.4F))
				.texOffs(0, 75).addBox(-1.0F, 1.25F, -3.0833F, 2.0F, 6.0F, 9.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 3.75F, -15.3167F));

		PartDefinition ear_r1 = head.addOrReplaceChild("ear_r1", CubeListBuilder.create().texOffs(15, 0).mirror().addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -2.25F, 1.0167F, 0.0F, -0.8727F, 0.0F));

		PartDefinition ear_r2 = head.addOrReplaceChild("ear_r2", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.25F, 1.0167F, 0.0F, 0.8727F, 0.0F));

		PartDefinition head_wool_limit_r1 = head.addOrReplaceChild("head_wool_limit_r1", CubeListBuilder.create().texOffs(88, 113).addBox(-2.5F, -3.5F, -4.0F, 5.0F, 7.0F, 8.0F, new CubeDeformation(0.8F)), PartPose.offsetAndRotation(0.0F, -1.55F, -0.0833F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-2.49F, -9.2517F, 2.7458F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(20, 12).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 3.3876F, -1.4032F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_horn_r2 = right_horn.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(4, 27).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6938F, 0.7016F, -0.4287F, 0.4011F, -0.1766F));

		PartDefinition right_horn_r3 = right_horn.addOrReplaceChild("right_horn_r3", CubeListBuilder.create().texOffs(4, 27).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6938F, 0.7016F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(2.49F, -9.2517F, 2.7458F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(20, 12).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.3876F, -1.4032F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_horn_r2 = left_horn.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(4, 27).mirror().addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.6938F, 0.7016F, -0.4287F, -0.4011F, 0.1766F));

		PartDefinition left_horn_r3 = left_horn.addOrReplaceChild("left_horn_r3", CubeListBuilder.create().texOffs(4, 27).mirror().addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.6938F, 0.7016F, -0.3927F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 24).addBox(-5.5091F, -4.6364F, -9.8182F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(46, 24).addBox(-5.5091F, -4.5364F, -9.8182F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.3F))
				.texOffs(0, 102).addBox(-5.5091F, -6.0364F, -9.8182F, 11.0F, 14.0F, 12.0F, new CubeDeformation(0.4F))
				.texOffs(68, 68).addBox(-4.5091F, -4.6364F, -8.7182F, 9.0F, 14.0F, 21.0F, new CubeDeformation(2.0F))
				.texOffs(26, 75).addBox(-4.5091F, -8.6364F, -10.7182F, 9.0F, 12.0F, 12.0F, new CubeDeformation(1.0F))
				.texOffs(36, 0).addBox(-4.0091F, -6.1364F, -9.8182F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(84, 44).addBox(-5.4091F, -5.9364F, -6.4182F, 11.0F, 14.0F, 10.0F, new CubeDeformation(0.6F))
				.texOffs(0, 47).addBox(-4.5091F, -4.2364F, 1.5818F, 9.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(42, 47).addBox(-4.5091F, -4.2364F, 1.5818F, 9.0F, 10.0F, 12.0F, new CubeDeformation(0.4F))
				.texOffs(46, 103).addBox(-4.5091F, -4.2364F, 1.5818F, 9.0F, 13.0F, 12.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0091F, 4.6364F, -0.5818F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(50, 10).addBox(-4.0F, -1.5F, -3.5F, 8.0F, 3.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.0091F, -3.6364F, -0.6182F, -0.3491F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(80, 33).addBox(-4.98F, -8.8151F, 5.9829F, 10.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0291F, -1.9213F, -10.9011F));

		PartDefinition left_rein_r1 = neck.addOrReplaceChild("left_rein_r1", CubeListBuilder.create().texOffs(80, 17).mirror().addBox(0.0F, -1.0F, -8.5F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.72F, -4.117F, -0.8268F, 0.6149F, 0.1071F, 0.0754F));

		PartDefinition right_rein_r1 = neck.addOrReplaceChild("right_rein_r1", CubeListBuilder.create().texOffs(80, 17).addBox(0.0F, -1.0F, -8.5F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.78F, -4.117F, -0.8268F, 0.6149F, -0.1071F, -0.0754F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(26, 12).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.02F, 2.3441F, 0.6841F, -0.6109F, 0.0F, 0.0F));

		PartDefinition neck_r2 = neck.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.02F, -0.795F, -0.0134F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_chest = body.addOrReplaceChild("right_chest", CubeListBuilder.create().texOffs(73, 0).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5091F, 1.3636F, 6.5818F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_chest = body.addOrReplaceChild("left_chest", CubeListBuilder.create().texOffs(73, 0).mirror().addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.4909F, 1.3636F, 6.5818F, 0.0F, -1.5708F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.0091F, -2.7534F, 13.9112F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-0.5F, -5.5F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-3.25F, 5.9262F, 10.3738F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(98, 16).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 10.0F, 5.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.3264F, 0.3063F));

		PartDefinition right_hind_foot_wool_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_wool_r1", CubeListBuilder.create().texOffs(116, 43).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 4.7502F, -0.0109F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(116, 17).addBox(-1.5F, -5.5F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.2498F, 0.0109F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(3.25F, 5.9262F, 10.3738F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(98, 16).mirror().addBox(-2.0F, -5.0F, -2.5F, 4.0F, 10.0F, 5.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.3264F, 0.3063F));

		PartDefinition left_hind_foot_wool_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_wool_r1", CubeListBuilder.create().texOffs(116, 43).mirror().addBox(-1.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.7502F, -0.0109F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(116, 17).mirror().addBox(-1.5F, -5.5F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.2498F, 0.0109F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.8073F, -8.7401F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -5.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.4444F, 0.4165F));

		PartDefinition right_front_foot_wool_r1 = right_front_foot.addOrReplaceChild("right_front_foot_wool_r1", CubeListBuilder.create().texOffs(116, 31).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 6.2498F, -0.0109F, -0.0436F, 0.0F, 0.0F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(116, 0).addBox(-1.5F, -7.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.7502F, 0.0109F, -0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 3.8073F, -8.7401F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-2.0F, -5.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.4444F, 0.4165F));

		PartDefinition left_front_foot_wool_r1 = left_front_foot.addOrReplaceChild("left_front_foot_wool_r1", CubeListBuilder.create().texOffs(116, 31).mirror().addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.2498F, -0.0109F, -0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(116, 0).mirror().addBox(-1.5F, -7.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.7502F, 0.0109F, -0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
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
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isHorn() && t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(BigWoolGemsbokAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isHorn() && t.isAggressive()){
			this.animateWalk(BigWoolGemsbokAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(BigWoolGemsbokAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.attackAnimationState, BigWoolGemsbokAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, BigWoolGemsbokAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, BigWoolGemsbokAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, BigWoolGemsbokAnimation.STOPSIT, f3);
		this.animate(t.eatAnimationState, BigWoolGemsbokAnimation.EAT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}