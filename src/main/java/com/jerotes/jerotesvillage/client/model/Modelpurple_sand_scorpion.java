package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PurpleSandScorpionAnimation;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandScorpionEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpurple_sand_scorpion<T extends PurpleSandScorpionEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "purple_sand_scorpion"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelpurple_sand_scorpion(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0F, -8.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.5F, -3.0F, -8.75F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(0.5F, -3.0F, -8.75F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 15.0F, -6.75F, 0.0436F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -4.0F, -4.5F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.01F))
				.texOffs(20, 16).addBox(-5.0F, 4.25F, -4.5F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 113).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(46, 126).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(68, 116).addBox(4.0F, -7.0F, -4.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(46, 126).addBox(-5.0F, -7.0F, 7.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(68, 116).addBox(-5.0F, -7.0F, -4.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -2.5F));

		PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -4.0F, -1.5F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition body_2 = body_1.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 33).addBox(-4.5F, -3.5F, 0.0F, 9.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 7.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_3 = body_2.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(0, 44).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 4.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail_1 = body_3.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition tail_5 = tail_4.addOrReplaceChild("tail_5", CubeListBuilder.create().texOffs(52, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition stab = tail_5.addOrReplaceChild("stab", CubeListBuilder.create().texOffs(72, 0).addBox(-1.5F, -0.6667F, -0.0833F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 0).addBox(-2.0F, -1.4167F, 0.9167F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(83, 0).addBox(-2.0F, -1.4167F, 6.9167F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5833F, 7.0833F, 1.7453F, 0.0F, 0.0F));

		PartDefinition left_pincher = partdefinition.addOrReplaceChild("left_pincher", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, 17.0F, -15.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_pincher_r1 = left_pincher.addOrReplaceChild("left_pincher_r1", CubeListBuilder.create().texOffs(114, 44).mirror().addBox(-1.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_pincher_1 = left_pincher.addOrReplaceChild("left_pincher_1", CubeListBuilder.create(), PartPose.offset(-1.5F, -1.0F, -2.0F));

		PartDefinition left_pincher_1_r1 = left_pincher_1.addOrReplaceChild("left_pincher_1_r1", CubeListBuilder.create().texOffs(112, 52).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition left_pincher_2 = left_pincher_1.addOrReplaceChild("left_pincher_2", CubeListBuilder.create(), PartPose.offset(-2.9132F, 0.0F, -1.5076F));

		PartDefinition left_pincher_2_r1 = left_pincher_2.addOrReplaceChild("left_pincher_2_r1", CubeListBuilder.create().texOffs(110, 60).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition left_pincher_3 = left_pincher_2.addOrReplaceChild("left_pincher_3", CubeListBuilder.create().texOffs(106, 69).mirror().addBox(-2.5F, -2.75F, -5.5F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(114, 82).mirror().addBox(-1.5F, -1.75F, -9.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.3368F, -1.25F, -2.9924F));

		PartDefinition left_pincher_4 = left_pincher_3.addOrReplaceChild("left_pincher_4", CubeListBuilder.create().texOffs(112, 88).mirror().addBox(-1.5F, -1.0F, -4.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.25F, -5.5F));

		PartDefinition right_pincher = partdefinition.addOrReplaceChild("right_pincher", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 17.0F, -15.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition right_pincher_r1 = right_pincher.addOrReplaceChild("right_pincher_r1", CubeListBuilder.create().texOffs(114, 44).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition right_pincher_1 = right_pincher.addOrReplaceChild("right_pincher_1", CubeListBuilder.create(), PartPose.offset(1.5F, -1.0F, -2.0F));

		PartDefinition right_pincher_1_r1 = right_pincher_1.addOrReplaceChild("right_pincher_1_r1", CubeListBuilder.create().texOffs(112, 52).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition right_pincher_2 = right_pincher_1.addOrReplaceChild("right_pincher_2", CubeListBuilder.create(), PartPose.offset(2.9132F, 0.0F, -1.5076F));

		PartDefinition right_pincher_2_r1 = right_pincher_2.addOrReplaceChild("right_pincher_2_r1", CubeListBuilder.create().texOffs(110, 60).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_pincher_3 = right_pincher_2.addOrReplaceChild("right_pincher_3", CubeListBuilder.create().texOffs(106, 69).addBox(-2.5F, -2.75F, -5.5F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(114, 82).addBox(-1.5F, -1.75F, -9.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.3368F, -1.25F, -2.9924F));

		PartDefinition right_pincher_4 = right_pincher_3.addOrReplaceChild("right_pincher_4", CubeListBuilder.create().texOffs(112, 88).addBox(-1.5F, -1.0F, -4.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, -5.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(100, 12).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 17.0F, -2.0F, 0.7854F, 0.6545F, 1.1781F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create().texOffs(108, 24).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.6581F));

		PartDefinition right_hind_leg_2 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create().texOffs(102, 40).addBox(-10.5F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(100, 12).mirror().addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 17.0F, -2.0F, 0.7854F, -0.6545F, -1.1781F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create().texOffs(108, 24).mirror().addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(11.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.6581F));

		PartDefinition left_hind_leg_2 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create().texOffs(102, 40).mirror().addBox(-0.5F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_middle_hind_leg = partdefinition.addOrReplaceChild("right_middle_hind_leg", CubeListBuilder.create().texOffs(104, 8).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 17.0F, -5.0F, 0.2618F, 0.3054F, 0.6981F));

		PartDefinition right_middle_hind_leg_1 = right_middle_hind_leg.addOrReplaceChild("right_middle_hind_leg_1", CubeListBuilder.create().texOffs(110, 20).addBox(-6.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-9.5F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_middle_hind_leg_2 = right_middle_hind_leg_1.addOrReplaceChild("right_middle_hind_leg_2", CubeListBuilder.create().texOffs(106, 36).addBox(-8.5F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_middle_hind_leg = partdefinition.addOrReplaceChild("left_middle_hind_leg", CubeListBuilder.create().texOffs(104, 8).mirror().addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 17.0F, -5.0F, 0.2618F, -0.3054F, -0.6981F));

		PartDefinition left_middle_hind_leg_1 = left_middle_hind_leg.addOrReplaceChild("left_middle_hind_leg_1", CubeListBuilder.create().texOffs(110, 20).mirror().addBox(-0.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(9.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_middle_hind_leg_2 = left_middle_hind_leg_1.addOrReplaceChild("left_middle_hind_leg_2", CubeListBuilder.create().texOffs(106, 36).mirror().addBox(-0.5F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_middle_front_leg = partdefinition.addOrReplaceChild("right_middle_front_leg", CubeListBuilder.create().texOffs(106, 4).addBox(-8.5F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 17.0F, -8.0F, 0.0F, 0.1309F, 0.4363F));

		PartDefinition right_middle_front_leg_1 = right_middle_front_leg.addOrReplaceChild("right_middle_front_leg_1", CubeListBuilder.create().texOffs(112, 16).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

		PartDefinition right_middle_front_leg_2 = right_middle_front_leg_1.addOrReplaceChild("right_middle_front_leg_2", CubeListBuilder.create().texOffs(108, 32).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_middle_front_leg = partdefinition.addOrReplaceChild("left_middle_front_leg", CubeListBuilder.create().texOffs(106, 4).mirror().addBox(-0.5F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 17.0F, -8.0F, 0.0F, -0.1309F, -0.4363F));

		PartDefinition left_middle_front_leg_1 = left_middle_front_leg.addOrReplaceChild("left_middle_front_leg_1", CubeListBuilder.create().texOffs(112, 16).mirror().addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(8.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.1781F));

		PartDefinition left_middle_front_leg_2 = left_middle_front_leg_1.addOrReplaceChild("left_middle_front_leg_2", CubeListBuilder.create().texOffs(108, 32).mirror().addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(108, 0).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 17.0F, -11.0F, -0.3491F, -0.2618F, 0.2618F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create().texOffs(112, 16).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition right_front_leg_2 = right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create().texOffs(110, 28).addBox(-6.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(108, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 17.0F, -11.0F, -0.3491F, 0.2618F, -0.2618F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create().texOffs(112, 16).mirror().addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_front_leg_2 = left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create().texOffs(110, 28).mirror().addBox(-0.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.animateWalk(PurpleSandScorpionAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(t.idleAnimationState, PurpleSandScorpionAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, PurpleSandScorpionAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, PurpleSandScorpionAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, PurpleSandScorpionAnimation.ATTACK3, f3);
		this.animate(t.stabAnimationState, PurpleSandScorpionAnimation.STAB, f3);
		this.animate(t.sitAnimationState, PurpleSandScorpionAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, PurpleSandScorpionAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, PurpleSandScorpionAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
