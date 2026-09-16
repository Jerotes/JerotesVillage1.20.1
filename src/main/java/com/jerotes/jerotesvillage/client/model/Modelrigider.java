package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RigiderAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.RigiderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelrigider<T extends RigiderEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rigider"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelrigider(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.5F, -15.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(31, 0).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, -1.3F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -0.5F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.9F, -6.8F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_teeth = head.addOrReplaceChild("right_teeth", CubeListBuilder.create(), PartPose.offset(-2.6451F, -11.0564F, -21.5F));

		PartDefinition right_teeth_r1 = right_teeth.addOrReplaceChild("right_teeth_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 21.0F, 15.4F, -1.5708F, -0.9599F, 1.5708F));

		PartDefinition left_teeth = head.addOrReplaceChild("left_teeth", CubeListBuilder.create(), PartPose.offset(2.6451F, -11.0564F, -21.5F));

		PartDefinition left_teeth_r1 = left_teeth.addOrReplaceChild("left_teeth_r1", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, -3.0F, -1.5F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 21.0F, 15.4F, -1.5708F, 0.9599F, -1.5708F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, -4.5F));

		PartDefinition front_body_r1 = front_body.addOrReplaceChild("front_body_r1", CubeListBuilder.create().texOffs(0, 73).addBox(-1.0F, -1.0F, -11.5F, 2.0F, 2.0F, 23.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -3.7389F, -2.1971F, 0.2519F, -0.2443F, 0.7543F));

		PartDefinition front_body_r2 = front_body.addOrReplaceChild("front_body_r2", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-2.0F, -2.0F, -9.5F, 4.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0944F, -2.1146F, -5.3425F, 0.3371F, 0.305F, -0.0776F));

		PartDefinition front_body_r3 = front_body.addOrReplaceChild("front_body_r3", CubeListBuilder.create().texOffs(104, 0).addBox(-2.0F, -2.0F, -9.5F, 4.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0944F, -2.1146F, -5.3425F, 0.3371F, -0.305F, 0.0776F));

		PartDefinition front_body_r4 = front_body.addOrReplaceChild("front_body_r4", CubeListBuilder.create().texOffs(128, 0).addBox(-5.8F, -5.5F, -11.5F, 12.0F, 4.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, -1.85F, -2.3F, 0.3491F, 0.0F, 0.0F));

		PartDefinition front_body_r5 = front_body.addOrReplaceChild("front_body_r5", CubeListBuilder.create().texOffs(0, 39).addBox(-8.8F, -4.5F, -12.5F, 18.0F, 9.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 0.55F, -1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition front_body_r6 = front_body.addOrReplaceChild("front_body_r6", CubeListBuilder.create().texOffs(36, 0).addBox(-11.0F, -8.0F, -12.0F, 22.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.25F, -0.1F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_front_leg = front_body.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-7.0F, -0.75F, -10.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(47, 109).addBox(-3.0F, -7.0F, -2.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 13.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition left_front_leg_1_r1 = right_front_leg_1.addOrReplaceChild("left_front_leg_1_r1", CubeListBuilder.create().texOffs(92, 64).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition right_front_leg_2 = right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -11.0F, -0.25F));

		PartDefinition left_front_leg_2_r1 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r1", CubeListBuilder.create().texOffs(84, 82).addBox(-2.5F, -13.5F, -3.5F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0F, 16.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_front_leg_2_r2 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r2", CubeListBuilder.create().texOffs(108, 64).addBox(-2.0F, -14.5F, -3.0F, 4.0F, 26.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_front_leg = front_body.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(7.0F, -0.75F, -10.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(47, 109).mirror().addBox(0.0F, -7.0F, -2.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 13.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition left_front_leg_1_r2 = left_front_leg_1.addOrReplaceChild("left_front_leg_1_r2", CubeListBuilder.create().texOffs(92, 64).mirror().addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_front_leg_2 = left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -11.0F, -0.25F));

		PartDefinition left_front_leg_2_r3 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r3", CubeListBuilder.create().texOffs(84, 82).mirror().addBox(-2.5F, -13.5F, -3.5F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(4.0F, 16.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition left_front_leg_2_r4 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r4", CubeListBuilder.create().texOffs(108, 64).mirror().addBox(-2.0F, -14.5F, -3.0F, 4.0F, 26.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 12.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition right_middle_leg = front_body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, 0.25F, -5.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition right_middle_leg_r1 = right_middle_leg.addOrReplaceChild("right_middle_leg_r1", CubeListBuilder.create().texOffs(90, 106).addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.5524F, 6.2999F, -0.5F, 0.0F, 1.1781F, 0.1309F));

		PartDefinition right_middle_leg_1 = right_middle_leg.addOrReplaceChild("right_middle_leg_1", CubeListBuilder.create(), PartPose.offset(-3.0F, 12.0F, -0.5F));

		PartDefinition right_middle_leg_1_r1 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 106).addBox(-2.0F, -7.0F, -1.5F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.0F, -4.0F, 0.0F, 0.0F, -1.0036F, -0.3054F));

		PartDefinition right_middle_leg_2 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -10.0F, 0.25F));

		PartDefinition right_middle_leg_2_r1 = right_middle_leg_2.addOrReplaceChild("right_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 99).addBox(-1.0F, -14.5F, -2.0F, 3.0F, 25.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_middle_leg = front_body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, 0.25F, -5.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition left_middle_leg_r1 = left_middle_leg.addOrReplaceChild("left_middle_leg_r1", CubeListBuilder.create().texOffs(90, 106).mirror().addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.5524F, 6.2999F, -0.5F, 0.0F, -1.1781F, -0.1309F));

		PartDefinition left_middle_leg_1 = left_middle_leg.addOrReplaceChild("left_middle_leg_1", CubeListBuilder.create(), PartPose.offset(3.0F, 12.0F, -0.5F));

		PartDefinition left_middle_leg_1_r1 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 106).mirror().addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.0F, -4.0F, 0.5F, 0.0F, 1.0036F, 0.3054F));

		PartDefinition left_middle_leg_2 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -10.0F, 0.25F));

		PartDefinition left_middle_leg_2_r1 = left_middle_leg_2.addOrReplaceChild("left_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 99).mirror().addBox(-2.0F, -14.5F, -2.0F, 3.0F, 25.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 12.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition hind_body = body.addOrReplaceChild("hind_body", CubeListBuilder.create().texOffs(61, 39).addBox(-9.0F, -3.0F, 4.0F, 18.0F, 12.0F, 9.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 3.0F, 1.5F));

		PartDefinition hind_body_r1 = hind_body.addOrReplaceChild("hind_body_r1", CubeListBuilder.create().texOffs(0, 114).addBox(-4.0F, -3.5F, -6.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 5.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = hind_body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.5F, 3.5F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(62, 116).mirror().addBox(-1.0F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.9707F, 2.0356F, -2.0944F, -0.9599F, -0.5236F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-2.0F, 5.0F, 3.0F));

		PartDefinition right_hind_leg_1_r1 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_1_r1", CubeListBuilder.create().texOffs(74, 113).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -3.6636F, 3.4217F, -0.5672F, 0.0F, -0.7854F));

		PartDefinition right_hind_leg_2 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -6.0F, 6.0F));

		PartDefinition right_hind_leg_2_r1 = right_hind_leg_2.addOrReplaceChild("right_hind_leg_2_r1", CubeListBuilder.create().texOffs(65, 79).mirror().addBox(-2.0F, -14.0F, -2.0F, 4.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 10.5103F, 2.9137F, 0.1745F, -0.1047F, 0.0873F));

		PartDefinition left_hind_leg = hind_body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 2.5F, 3.5F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(62, 116).addBox(-2.0F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.9707F, 2.0356F, -2.0944F, 0.9599F, 0.5236F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create(), PartPose.offset(2.0F, 5.0F, 3.0F));

		PartDefinition left_hind_leg_1_r1 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_1_r1", CubeListBuilder.create().texOffs(74, 113).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -3.6636F, 3.4217F, -0.5672F, 0.0F, 0.7854F));

		PartDefinition left_hind_leg_2 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -6.0F, 6.0F));

		PartDefinition left_hind_leg_2_r1 = left_hind_leg_2.addOrReplaceChild("left_hind_leg_2_r1", CubeListBuilder.create().texOffs(65, 79).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 10.5103F, 2.9137F, 0.1745F, 0.1047F, -0.0873F));

		PartDefinition tail = hind_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.3028F, 13.4513F, 2.7489F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(62, 107).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0743F, -0.3811F, -0.9686F, 0.0F, 0.0F));

		PartDefinition right_tail = tail.addOrReplaceChild("right_tail", CubeListBuilder.create(), PartPose.offset(-1.75F, 1.545F, -1.4228F));

		PartDefinition right_tail_r1 = right_tail.addOrReplaceChild("right_tail_r1", CubeListBuilder.create().texOffs(57, 103).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7499F, 9.0365F, -8.0981F, -0.2114F, 0.0787F, 0.1565F));

		PartDefinition right_tail_r2 = right_tail.addOrReplaceChild("right_tail_r2", CubeListBuilder.create().texOffs(57, 95).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1329F, 4.1275F, -5.7516F, -0.6497F, 0.0842F, 0.1009F));

		PartDefinition right_tail_r3 = right_tail.addOrReplaceChild("right_tail_r3", CubeListBuilder.create().texOffs(57, 87).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7179F, 0.737F, -1.8292F, -1.0893F, 0.0774F, 0.0404F));

		PartDefinition left_tail = tail.addOrReplaceChild("left_tail", CubeListBuilder.create(), PartPose.offset(1.75F, 1.545F, -1.4228F));

		PartDefinition left_tail_r1 = left_tail.addOrReplaceChild("left_tail_r1", CubeListBuilder.create().texOffs(57, 103).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7499F, 9.0365F, -8.0981F, -0.2114F, -0.0787F, -0.1565F));

		PartDefinition left_tail_r2 = left_tail.addOrReplaceChild("left_tail_r2", CubeListBuilder.create().texOffs(57, 95).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1329F, 4.1275F, -5.7516F, -0.6497F, -0.0842F, -0.1009F));

		PartDefinition left_tail_r3 = left_tail.addOrReplaceChild("left_tail_r3", CubeListBuilder.create().texOffs(57, 87).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.7179F, 0.737F, -1.8292F, -1.0893F, -0.0774F, -0.0404F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(RigiderAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, RigiderAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, RigiderAnimation.ATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}