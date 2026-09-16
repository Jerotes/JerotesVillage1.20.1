package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modeltyrant_of_the_mountain_realm_show<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "tyrant_of_the_mountain_realm_show"), "main");
	private final ModelPart root;

	public Modeltyrant_of_the_mountain_realm_show(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -8.0F, -6.5F, 11.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(71, 0).addBox(-5.5F, -8.0F, -6.5F, 11.0F, 9.0F, 10.0F, new CubeDeformation(0.5F))
				.texOffs(113, 0).addBox(-1.5F, -11.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 1.0F));

		PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(42, 0).addBox(-5.5F, -0.5F, -1.5F, 11.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -5.0F));

		PartDefinition head_armor_hair = head.addOrReplaceChild("head_armor_hair", CubeListBuilder.create().texOffs(113, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -12.5F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, -3.1287F, -6.6213F));

		PartDefinition nose_r1 = nose.addOrReplaceChild("nose_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -4.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(6.0F, -4.0F, -2.0F));

		PartDefinition left_ear_r1 = left_ear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-6.0F, -4.0F, -2.0F));

		PartDefinition right_ear_r1 = right_ear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -15.6667F, -1.8333F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create().texOffs(0, 19).addBox(-9.0F, -11.3333F, -2.1667F, 18.0F, 4.0F, 9.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 32).addBox(-9.0F, -8.3333F, -3.1667F, 18.0F, 23.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 65).addBox(-9.0F, -0.2333F, -5.1667F, 18.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(71, 19).addBox(-9.0F, -11.3333F, -2.1667F, 18.0F, 4.0F, 9.0F, new CubeDeformation(0.05F))
				.texOffs(71, 32).addBox(-9.0F, -8.3333F, -3.1667F, 18.0F, 23.0F, 10.0F, new CubeDeformation(0.5F))
				.texOffs(127, 42).addBox(-9.0F, -1.1333F, -4.1667F, 18.0F, 1.0F, 1.0F, new CubeDeformation(0.49F))
				.texOffs(71, 65).addBox(-9.0F, 0.0067F, -5.1667F, 18.0F, 13.0F, 2.0F, new CubeDeformation(0.495F))
				.texOffs(147, 19).addBox(-9.0F, 6.6667F, -5.1667F, 18.0F, 10.0F, 12.0F, new CubeDeformation(0.55F))
				.texOffs(0, 220).addBox(-9.0F, -11.3333F, -5.1667F, 18.0F, 23.0F, 13.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition flower = up_body.addOrReplaceChild("flower", CubeListBuilder.create(), PartPose.offset(0.0F, -0.8333F, -5.4167F));

		PartDefinition flower_r1 = flower.addOrReplaceChild("flower_r1", CubeListBuilder.create().texOffs(127, 44).addBox(-3.5F, -3.0F, -1.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.75F, -0.0436F, 0.0F, 0.0F));

		PartDefinition back_banner = up_body.addOrReplaceChild("back_banner", CubeListBuilder.create().texOffs(222, 218).addBox(-5.0F, -3.3333F, 3.8333F, 10.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(226, 214).addBox(-5.0F, -3.3333F, 6.8333F, 10.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 3.0F));

		PartDefinition back_banner_1 = back_banner.addOrReplaceChild("back_banner_1", CubeListBuilder.create().texOffs(248, 214).addBox(-1.0F, -37.0F, -0.625F, 2.0F, 40.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(196, 166).addBox(0.0F, -35.0F, 0.375F, 0.0F, 18.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(244, 186).addBox(-1.5F, -35.25F, -1.125F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.25F))
				.texOffs(244, 181).addBox(-1.5F, -37.0F, -1.125F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(1.0F, 4.6667F, 6.4583F, -0.2166F, 0.2932F, 0.1145F));

		PartDefinition back_banner_r1 = back_banner_1.addOrReplaceChild("back_banner_r1", CubeListBuilder.create().texOffs(240, 233).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.0F, 0.375F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_2 = back_banner.addOrReplaceChild("back_banner_2", CubeListBuilder.create().texOffs(248, 214).addBox(-1.0F, -37.0F, -0.625F, 2.0F, 40.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(196, 166).addBox(0.0F, -35.0F, 0.375F, 0.0F, 18.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(244, 186).addBox(-1.5F, -35.25F, -1.125F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.25F))
				.texOffs(244, 181).addBox(-1.5F, -37.0F, -1.125F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-1.0F, 4.6667F, 6.4583F, -0.2166F, -0.2932F, -0.1145F));

		PartDefinition back_banner_r2 = back_banner_2.addOrReplaceChild("back_banner_r2", CubeListBuilder.create().texOffs(240, 233).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.0F, 0.375F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_3 = back_banner.addOrReplaceChild("back_banner_3", CubeListBuilder.create().texOffs(248, 214).addBox(-1.0F, -37.0F, -1.125F, 2.0F, 40.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(196, 166).addBox(0.0F, -35.0F, -0.125F, 0.0F, 18.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(244, 186).addBox(-1.5F, -35.25F, -1.625F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.25F))
				.texOffs(244, 181).addBox(-1.5F, -37.0F, -1.625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(2.0F, 4.6667F, 6.4583F, 0.0332F, 0.7746F, 0.4936F));

		PartDefinition back_banner_r3 = back_banner_3.addOrReplaceChild("back_banner_r3", CubeListBuilder.create().texOffs(240, 233).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.0F, -0.125F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_4 = back_banner.addOrReplaceChild("back_banner_4", CubeListBuilder.create().texOffs(248, 214).addBox(-1.0F, -37.0F, -1.125F, 2.0F, 40.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(196, 166).addBox(0.0F, -35.0F, -0.125F, 0.0F, 18.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(244, 186).addBox(-1.5F, -35.25F, -1.625F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.25F))
				.texOffs(244, 181).addBox(-1.5F, -37.0F, -1.625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-2.0F, 4.6667F, 6.4583F, 0.0332F, -0.7746F, -0.4936F));

		PartDefinition back_banner_r4 = back_banner_4.addOrReplaceChild("back_banner_r4", CubeListBuilder.create().texOffs(240, 233).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.0F, -0.125F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition dowm_body = body.addOrReplaceChild("dowm_body", CubeListBuilder.create().texOffs(0, 182).addBox(-9.0F, -1.25F, -2.0F, 18.0F, 17.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(147, 0).addBox(-9.0F, -1.25F, -2.0F, 18.0F, 17.0F, 10.0F, new CubeDeformation(0.55F)), PartPose.offset(0.0F, 15.9167F, -1.1667F));

		PartDefinition down_body_clothes_front = dowm_body.addOrReplaceChild("down_body_clothes_front", CubeListBuilder.create().texOffs(62, 217).addBox(-9.0F, 1.5F, -6.5F, 18.0F, 26.0F, 13.0F, new CubeDeformation(0.85F)), PartPose.offset(0.0F, -4.75F, 2.5F));

		PartDefinition down_body_clothes_hind = dowm_body.addOrReplaceChild("down_body_clothes_hind", CubeListBuilder.create().texOffs(86, 178).addBox(-9.0F, 1.5F, -6.5F, 18.0F, 26.0F, 13.0F, new CubeDeformation(0.825F)), PartPose.offset(0.0F, -4.75F, 2.5F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 97).mirror().addBox(-1.5F, -3.5F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 97).mirror().addBox(-1.5F, -3.5F, -4.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.75F)).mirror(false)
				.texOffs(124, 231).mirror().addBox(-1.5F, -4.0F, -4.5F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offsetAndRotation(10.5F, -21.5F, 0.5F, -0.1309F, 0.0F, -0.2618F));

		PartDefinition left_arm_armor_r1 = left_arm.addOrReplaceChild("left_arm_armor_r1", CubeListBuilder.create().texOffs(56, 111).mirror().addBox(-2.0F, -7.0F, -4.0F, 4.0F, 16.0F, 8.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.5F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-4.5F, 0.5F, -3.5F, 7.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 135).mirror().addBox(-4.5F, 2.5F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(158, 231).mirror().addBox(-5.5F, 0.0F, -4.75F, 9.0F, 16.0F, 9.0F, new CubeDeformation(0.8F)).mirror(false), PartPose.offsetAndRotation(4.0F, 11.0F, 0.25F, -0.4363F, 0.0F, 0.3491F));

		PartDefinition left_hand_armor_r1 = left_hand.addOrReplaceChild("left_hand_armor_r1", CubeListBuilder.create().texOffs(56, 151).mirror().addBox(-3.5F, 0.5F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offsetAndRotation(3.0F, 11.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition left_hand_vine = left_hand.addOrReplaceChild("left_hand_vine", CubeListBuilder.create().texOffs(208, 0).mirror().addBox(-6.0F, -4.5F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-2.6F)).mirror(false), PartPose.offset(-1.0F, 7.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 97).addBox(-5.5F, -3.5F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(56, 97).addBox(-5.5F, -3.5F, -4.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offsetAndRotation(-10.5F, -21.5F, 0.5F, 0.4974F, 0.4974F, 0.5236F));

		PartDefinition right_arm_armor_r1 = right_arm.addOrReplaceChild("right_arm_armor_r1", CubeListBuilder.create().texOffs(56, 111).addBox(-2.0F, -7.0F, -4.0F, 4.0F, 16.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-3.5F, 3.5F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 120).addBox(-2.5F, 0.5F, -3.5F, 7.0F, 15.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(56, 135).addBox(-2.5F, 2.5F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-4.0F, 11.0F, 0.25F, -0.3054F, 0.0F, -0.3491F));

		PartDefinition right_hand_armor_r1 = right_hand.addOrReplaceChild("right_hand_armor_r1", CubeListBuilder.create().texOffs(56, 151).addBox(0.5F, 0.5F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(-3.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition sword = right_hand.addOrReplaceChild("sword", CubeListBuilder.create(), PartPose.offset(1.0F, 12.0F, -4.0F));

		PartDefinition sword_main = sword.addOrReplaceChild("sword_main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, -5.5F, -9.35F, 2.3562F, 0.0F, 0.0F));

		PartDefinition main = sword_main.addOrReplaceChild("main", CubeListBuilder.create().texOffs(208, 76).addBox(-1.0F, -21.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -21.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -21.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -21.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -21.0F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -21.0F, 14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -21.0F, 15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -20.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -20.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -20.0F, 14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -20.0F, 15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -19.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -19.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -19.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -19.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -19.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -19.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -19.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -19.0F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -19.0F, 14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -18.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -18.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -18.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -18.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -18.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -18.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -18.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -18.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -17.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -17.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -17.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -17.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -17.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -17.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -17.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -17.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -16.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -16.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -16.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -16.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -16.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -16.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -16.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -16.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -15.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -15.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -15.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -15.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -15.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -15.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -15.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -15.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -14.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -14.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -14.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -14.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -14.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -14.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, -14.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -14.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -13.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -13.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -13.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -13.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -13.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -13.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -13.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -13.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -12.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -12.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -12.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -12.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -12.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -12.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -11.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -11.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -11.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -11.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -10.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -10.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -10.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -10.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -10.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -9.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -9.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -9.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -9.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -9.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -8.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 66).addBox(-1.0F, -8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -8.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -8.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -8.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -7.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -7.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -7.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -7.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -6.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -6.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -6.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 78).addBox(-1.0F, -6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -6.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -5.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -5.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -5.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 78).addBox(-1.0F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, -4.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 88).addBox(-1.0F, -4.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -4.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 78).addBox(-1.0F, -4.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -4.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -4.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, -3.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, -3.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 76).addBox(-1.0F, -3.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(212, 64).addBox(-1.0F, -3.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, -3.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 84).addBox(-1.0F, -3.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -3.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, -2.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, -2.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, -2.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 92).addBox(-1.0F, -2.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 78).addBox(-1.0F, -2.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 82).addBox(-1.0F, -2.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 72).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, -1.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, -1.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, -1.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 94).addBox(-1.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 0.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 0.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 0.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 0.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 66).addBox(-1.0F, 1.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, 1.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 1.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 1.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 1.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 66).addBox(-1.0F, 2.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, 2.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, 2.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, 2.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 2.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 2.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 2.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 2.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 3.0F, -15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 3.0F, -14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 66).addBox(-1.0F, 3.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, 3.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 68).addBox(-1.0F, 3.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 64).addBox(-1.0F, 3.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 4.0F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, 4.0F, -15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, 4.0F, -14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 4.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 64).addBox(-1.0F, 4.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 64).addBox(-1.0F, 4.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 80).addBox(-1.0F, 5.0F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 90).addBox(-1.0F, 5.0F, -15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 5.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 5.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 6.0F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 6.0F, -15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 6.0F, -14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 86).addBox(-1.0F, 6.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 74).addBox(-1.0F, 6.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(208, 74).addBox(-1.0F, 6.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.09F))
		.texOffs(208, 70).addBox(-1.0F, 6.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 7.0F, -15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 7.0F, -14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(208, 70).addBox(-1.0F, 7.0F, -13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring = sword_main.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(208, 82).addBox(0.0F, -14.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -13.7F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -13.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -14.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -14.1F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -13.7F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -14.1F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -13.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -14.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -14.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -14.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -15.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -15.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -14.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -14.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -15.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -14.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -15.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -13.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -12.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -12.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -12.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -12.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -12.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -12.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -13.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -12.9F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -12.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -13.7F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -13.7F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -14.1F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -14.1F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring2 = sword_main.addOrReplaceChild("ring2", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -10.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -10.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -10.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -10.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -10.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -10.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -10.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -11.7F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -11.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -12.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -12.1F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -11.7F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -12.1F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -11.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -12.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -11.7F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -11.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -13.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -13.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -12.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -12.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -13.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -13.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -12.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -12.1F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -10.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -12.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -12.9F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -11.7F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -12.1F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -11.3F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -12.5F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring3 = sword_main.addOrReplaceChild("ring3", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -9.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -9.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -9.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -9.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -9.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -9.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -9.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -10.7F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -10.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -11.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -11.1F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -10.7F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -11.1F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -10.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -11.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -10.7F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -10.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -12.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -12.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -11.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -11.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -12.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -12.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -11.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -11.1F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -9.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -11.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -11.9F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -10.7F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -11.1F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -10.3F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -11.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring4 = sword_main.addOrReplaceChild("ring4", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -7.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -7.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -7.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -7.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -7.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -7.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -7.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -8.7F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -8.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -9.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -9.1F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -8.7F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -9.1F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -8.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -9.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -8.7F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -8.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -10.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -10.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -9.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -9.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -10.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -10.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -9.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -9.1F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -7.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -9.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -9.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -8.7F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -9.1F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -8.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -9.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring5 = sword_main.addOrReplaceChild("ring5", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -6.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -6.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -6.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -6.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -6.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -6.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -6.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -7.7F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -7.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -8.1F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -7.7F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -8.1F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -7.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -7.7F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -7.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -9.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -9.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -8.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -8.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -9.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -9.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -8.1F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -6.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -8.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -8.9F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -7.7F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -8.1F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -7.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring6 = sword_main.addOrReplaceChild("ring6", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -4.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -4.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -4.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -5.7F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -5.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -6.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -6.1F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -5.7F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -6.1F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -5.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -6.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -5.7F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -5.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -7.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -7.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -6.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -6.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -7.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -7.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -6.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -6.1F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -4.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -6.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -6.9F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -5.7F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -6.1F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -5.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -6.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring7 = sword_main.addOrReplaceChild("ring7", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -3.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -3.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -3.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -4.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -4.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -5.1F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -4.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -5.1F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -4.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -4.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -4.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -6.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -6.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -5.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -5.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -6.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -6.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -5.1F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -3.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -5.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -5.9F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -4.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -5.1F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -4.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring8 = sword_main.addOrReplaceChild("ring8", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -1.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -1.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -1.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -1.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -1.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -1.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -1.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -2.7F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -2.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -3.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -3.1F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -2.7F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -3.1F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -2.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -3.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -2.7F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -2.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -4.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -4.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -3.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -3.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -4.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -4.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -3.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -3.1F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -1.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -3.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -3.9F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -2.7F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -3.1F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -2.3F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -3.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ring9 = sword_main.addOrReplaceChild("ring9", CubeListBuilder.create().texOffs(208, 70).addBox(-0.4F, -0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.8F, -0.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -0.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.2F, -0.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-1.6F, -0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -1.7F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -1.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -2.1F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.0F, -1.7F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.0F, -2.1F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-2.4F, -1.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-2.4F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -1.7F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -1.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -3.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -3.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.6F, -2.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-1.2F, -2.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -3.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -3.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -2.1F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(-0.4F, -0.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.8F, -2.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(-0.4F, -2.9F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.4F, -1.7F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.4F, -2.1F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 70).addBox(0.0F, -1.3F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(208, 82).addBox(0.0F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = sword_main.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 5.5169F, -13.5094F, -1.8762F, 0.0F, 0.0F));

		PartDefinition body_r1 = tail.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(208, 64).addBox(-0.5F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F))
				.texOffs(208, 64).addBox(-0.5F, 10.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 11.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 8.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 9.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 8.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 68).addBox(-0.5F, 9.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 7.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 6.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 68).addBox(-0.5F, 6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 68).addBox(-0.5F, 7.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 5.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 68).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 74).addBox(-0.5F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 68).addBox(-0.5F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(208, 64).addBox(-0.5F, 1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F))
				.texOffs(208, 64).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.4831F, 1.5094F, 0.3927F, 0.0F, 0.0F));

	//	PartDefinition sword_light = sword.addOrReplaceChild("sword_light", CubeListBuilder.create(), PartPose.offset(0.0F, 0.1F, -15.0F));

	//	PartDefinition sword_light_r1 = sword_light.addOrReplaceChild("sword_light_r1", CubeListBuilder.create().texOffs(144, 32).addBox(0.0F, -16.0F, -16.0F, 0.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.05F, 0.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 142).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 160).mirror().addBox(-3.0F, 0.0F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(1.25F)).mirror(false), PartPose.offsetAndRotation(4.25F, -1.0F, 0.0F, 0.0113F, -0.087F, -0.0431F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 162).mirror().addBox(-3.5F, -0.5F, -1.5F, 7.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 209).mirror().addBox(-3.5F, 9.5F, -2.5F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(56, 183).mirror().addBox(-3.5F, -0.5F, -1.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.55F)).mirror(false)
				.texOffs(56, 199).mirror().addBox(-3.5F, 8.5F, -2.5F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(0.0F, 12.5F, -2.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 142).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(56, 160).addBox(-4.0F, 0.0F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(-4.25F, -1.0F, 0.0F, 0.0113F, 0.087F, 0.0431F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(0, 162).addBox(-3.5F, -0.5F, -1.5F, 7.0F, 13.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 209).addBox(-3.5F, 9.5F, -2.5F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(56, 183).addBox(-3.5F, -0.5F, -1.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.55F))
				.texOffs(56, 199).addBox(-3.5F, 8.5F, -2.5F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 12.5F, -2.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}