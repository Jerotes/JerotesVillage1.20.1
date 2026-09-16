package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelserpon_chariot<T extends SerponChariotEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_chariot"), "main");
	private final ModelPart main;
	private final ModelPart wheel;
	private final ModelPart right_wheel;
	private final ModelPart right_roll;
	private final ModelPart left_wheel;
	private final ModelPart left_roll;
	private final ModelPart leash;
	private final ModelPart leash_wheel;
	private final ModelPart leash_right_roll;
	private final ModelPart leash_left_roll;

	public Modelserpon_chariot(ModelPart root) {
		this.main = root.getChild("main");
		this.wheel = main.getChild("wheel");
		this.right_wheel = wheel.getChild("right_wheel");
		this.right_roll = right_wheel.getChild("right_roll");
		this.left_wheel = wheel.getChild("left_wheel");
		this.left_roll = left_wheel.getChild("left_roll");
		this.leash = main.getChild("leash");
		this.leash_wheel = leash.getChild("leash_wheel");
		this.leash_right_roll = leash_wheel.getChild("leash_right_roll");
		this.leash_left_roll = leash_wheel.getChild("leash_left_roll");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(1.0F, 21.0F, 0.0F));

		PartDefinition bottom = main.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -18.0F, 0.0F, 42.0F, 36.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(90, 0).addBox(-20.0F, -18.0F, -3.0F, 38.0F, 36.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(172, 0).addBox(-14.0F, -14.0F, 3.1F, 28.0F, 28.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, 2.0F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition chair = bottom.addOrReplaceChild("chair", CubeListBuilder.create().texOffs(54, 92).addBox(1.0F, -8.0F, 1.9F, 11.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.6F));

		PartDefinition chair_r1 = chair.addOrReplaceChild("chair_r1", CubeListBuilder.create().texOffs(54, 92).addBox(-5.5F, -8.0F, -3.0F, 11.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -9.0F, 4.9F, 0.0F, 0.0F, 1.5708F));

		PartDefinition chair_r2 = chair.addOrReplaceChild("chair_r2", CubeListBuilder.create().texOffs(54, 92).addBox(-5.5F, -8.0F, -3.0F, 11.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 9.0F, 4.9F, 0.0F, 0.0F, 1.5708F));

		PartDefinition right = main.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 74).addBox(-8.0F, -4.5F, -2.25F, 25.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 100).addBox(-8.0F, -4.5F, 0.75F, 25.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 74).addBox(-19.0F, -4.5F, -2.25F, 11.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.25F, -11.5F, 5.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_r1 = right.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 92).addBox(-12.5F, -3.0F, -1.0F, 25.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -5.5F, 0.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition left = main.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 74).addBox(-17.0F, -4.5F, -2.25F, 25.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 100).addBox(-17.0F, -4.5F, 0.75F, 25.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 74).addBox(8.0F, -4.5F, -2.25F, 11.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.25F, -11.5F, 5.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_r1 = left.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 92).addBox(-12.5F, -3.0F, -1.0F, 25.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -5.5F, 0.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition back = main.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(6.0F, -5.5F, -1.0F, 10.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 39).addBox(-16.0F, -5.5F, -1.0F, 10.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -5.5F, 21.0F));

		PartDefinition front = main.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 52).addBox(-18.0F, -2.0F, 0.0F, 36.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 65).addBox(-16.0F, -5.0F, -4.0F, 32.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.0F, -14.0F, 0.0F, -3.1416F, 0.0F));

		PartDefinition wheel = main.addOrReplaceChild("wheel", CubeListBuilder.create(), PartPose.offset(-1.0F, 3.0F, 0.0F));

		PartDefinition right_wheel = wheel.addOrReplaceChild("right_wheel", CubeListBuilder.create().texOffs(24, 39).addBox(-6.5F, -1.5F, -1.5F, 20.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(70, 39).addBox(-3.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(-29.5F, -2.5F, 4.5F));

		PartDefinition right_wheel_r1 = right_wheel.addOrReplaceChild("right_wheel_r1", CubeListBuilder.create().texOffs(-11, 140).addBox(-5.5F, 0.0F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.7854F, -3.1416F));

		PartDefinition right_roll = right_wheel.addOrReplaceChild("right_roll", CubeListBuilder.create().texOffs(82, 39).addBox(-4.5F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(76, 47).addBox(0.5F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition right_blade_r1 = right_roll.addOrReplaceChild("right_blade_r1", CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.7082F, -11.4127F, 0.4712F, 0.0F, 0.0F));

		PartDefinition right_blade_r2 = right_roll.addOrReplaceChild("right_blade_r2", CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 9.7082F, -7.0534F, 1.7279F, 0.0F, 0.0F));

		PartDefinition right_blade_r3 = right_roll.addOrReplaceChild("right_blade_r3", CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 9.7082F, 7.0534F, 2.9845F, 0.0F, 0.0F));

		PartDefinition right_blade_r4 = right_roll.addOrReplaceChild("right_blade_r4", CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.7082F, 11.4127F, -2.042F, 0.0F, 0.0F));

		PartDefinition right_blade_r5 = right_roll.addOrReplaceChild("right_blade_r5", CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -12.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition left_wheel = wheel.addOrReplaceChild("left_wheel", CubeListBuilder.create().texOffs(24, 39).mirror().addBox(-13.5F, -1.5F, -1.5F, 20.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(70, 39).mirror().addBox(0.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(29.5F, -2.5F, 4.5F));

		PartDefinition right_wheel_r2 = left_wheel.addOrReplaceChild("right_wheel_r2", CubeListBuilder.create().texOffs(-11, 140).mirror().addBox(-5.5F, 0.0F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, -0.7854F, -3.1416F));

		PartDefinition left_roll = left_wheel.addOrReplaceChild("left_roll", CubeListBuilder.create().texOffs(82, 39).mirror().addBox(-5.5F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(76, 47).mirror().addBox(-2.5F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

		PartDefinition left_blade_r1 = left_roll.addOrReplaceChild("left_blade_r1", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.7082F, -11.4127F, 0.4712F, 0.0F, 0.0F));

		PartDefinition left_blade_r2 = left_roll.addOrReplaceChild("left_blade_r2", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 9.7082F, -7.0534F, 1.7279F, 0.0F, 0.0F));

		PartDefinition left_blade_r3 = left_roll.addOrReplaceChild("left_blade_r3", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 9.7082F, 7.0534F, 2.9845F, 0.0F, 0.0F));

		PartDefinition left_blade_r4 = left_roll.addOrReplaceChild("left_blade_r4", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.7082F, 11.4127F, -2.042F, 0.0F, 0.0F));

		PartDefinition left_blade_r5 = left_roll.addOrReplaceChild("left_blade_r5", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -12.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition leash = main.addOrReplaceChild("leash", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.0F, -15.0F));

		PartDefinition left_leash_r1 = leash.addOrReplaceChild("left_leash_r1", CubeListBuilder.create().texOffs(180, 39).mirror().addBox(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(7.5F, -14.5F, -19.4F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition right_leash_r1 = leash.addOrReplaceChild("right_leash_r1", CubeListBuilder.create().texOffs(180, 39).addBox(-1.5F, -1.5F, -6.5F, 3.0F, 3.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-7.0F, -13.5F, -19.4F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition leash_r1 = leash.addOrReplaceChild("leash_r1", CubeListBuilder.create().texOffs(172, 21).addBox(-1.5F, -1.5F, -9.5F, 0.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -11.5F, -22.6F, -1.5708F, 1.309F, -1.5708F));

		PartDefinition leash_r2 = leash.addOrReplaceChild("leash_r2", CubeListBuilder.create().texOffs(172, 21).addBox(-1.5F, -1.5F, -9.5F, 0.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -22.6F, -1.5708F, 1.309F, -1.5708F));

		PartDefinition leash_r3 = leash.addOrReplaceChild("leash_r3", CubeListBuilder.create().texOffs(153, 39).addBox(-1.5F, -1.5F, -10.5F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -19.4F, 0.0F, 1.5708F, 0.0F));

		PartDefinition leash_r4 = leash.addOrReplaceChild("leash_r4", CubeListBuilder.create().texOffs(132, 39).addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 14.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -8.5F, -19.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition leash_r5 = leash.addOrReplaceChild("leash_r5", CubeListBuilder.create().texOffs(186, 28).addBox(-1.5F, -1.5F, 0.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -3.4F, -23.3F, 0.5672F, 0.0F, 0.0F));

		PartDefinition leash_r6 = leash.addOrReplaceChild("leash_r6", CubeListBuilder.create().texOffs(100, 39).addBox(-1.5F, -1.5F, 0.5F, 3.0F, 3.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 3.5F, -9.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition leash_r7 = leash.addOrReplaceChild("leash_r7", CubeListBuilder.create().texOffs(105, 39).addBox(-1.5F, -1.5F, -10.5F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, -10.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leash_r8 = leash.addOrReplaceChild("leash_r8", CubeListBuilder.create().texOffs(105, 39).addBox(-1.5F, -1.5F, -10.5F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, -6.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leash_wheel = leash.addOrReplaceChild("leash_wheel", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -22.9F));

		PartDefinition leash_wheel_r1 = leash_wheel.addOrReplaceChild("leash_wheel_r1", CubeListBuilder.create().texOffs(180, 28).addBox(-1.0F, -1.5F, -13.5F, 2.0F, 3.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition leash_right_roll = leash_wheel.addOrReplaceChild("leash_right_roll", CubeListBuilder.create().texOffs(76, 47).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 0.0F, -0.1F));

		PartDefinition right_blade_r6 = leash_right_roll.addOrReplaceChild("right_blade_r6", CubeListBuilder.create().texOffs(0, 108).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.7082F, -11.4127F, 0.4712F, 0.0F, 0.0F));

		PartDefinition right_blade_r7 = leash_right_roll.addOrReplaceChild("right_blade_r7", CubeListBuilder.create().texOffs(0, 108).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.7082F, -7.0534F, 1.7279F, 0.0F, 0.0F));

		PartDefinition right_blade_r8 = leash_right_roll.addOrReplaceChild("right_blade_r8", CubeListBuilder.create().texOffs(0, 108).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.7082F, 7.0534F, 2.9845F, 0.0F, 0.0F));

		PartDefinition right_blade_r9 = leash_right_roll.addOrReplaceChild("right_blade_r9", CubeListBuilder.create().texOffs(0, 108).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.7082F, 11.4127F, -2.042F, 0.0F, 0.0F));

		PartDefinition right_blade_r10 = leash_right_roll.addOrReplaceChild("right_blade_r10", CubeListBuilder.create().texOffs(0, 108).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition leash_left_roll = leash_wheel.addOrReplaceChild("leash_left_roll", CubeListBuilder.create().texOffs(76, 47).mirror().addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 0.0F, -0.1F));

		PartDefinition left_blade_r6 = leash_left_roll.addOrReplaceChild("left_blade_r6", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.7082F, -11.4127F, 0.4712F, 0.0F, 0.0F));

		PartDefinition left_blade_r7 = leash_left_roll.addOrReplaceChild("left_blade_r7", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.7082F, -7.0534F, 1.7279F, 0.0F, 0.0F));

		PartDefinition left_blade_r8 = leash_left_roll.addOrReplaceChild("left_blade_r8", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.7082F, 7.0534F, 2.9845F, 0.0F, 0.0F));

		PartDefinition left_blade_r9 = leash_left_roll.addOrReplaceChild("left_blade_r9", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.7082F, 11.4127F, -2.042F, 0.0F, 0.0F));

		PartDefinition left_blade_r10 = leash_left_roll.addOrReplaceChild("left_blade_r10", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.leash.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.leash.xRot = headPitch / (180F / (float) Math.PI);
		this.right_roll.xRot = limbSwing;
		this.left_roll.xRot = limbSwing;
		this.leash_left_roll.xRot = limbSwing;
		this.leash_right_roll.xRot = limbSwing;
	}
}

