package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.MerorChampionAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorChampionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class Modelmeror_champion<T extends MerorChampionEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "meror_champion"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart up_body;
	private final ModelPart neck;
	private final ModelPart hair;
	private final ModelPart head;
	private final ModelPart right_eye;
	private final ModelPart left_eye;
	private final ModelPart ring;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart axe;
	private final ModelPart axe_head;
	private final ModelPart axe_up1;
	private final ModelPart axe_up2;
	private final ModelPart net;
	private final ModelPart tail;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;

	public Modelmeror_champion(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.up_body = this.body.getChild("up_body");
		this.neck = this.up_body.getChild("neck");
		this.hair = this.neck.getChild("hair");
		this.head = this.neck.getChild("head");
		this.right_eye = this.head.getChild("right_eye");
		this.left_eye = this.head.getChild("left_eye");
		this.ring = this.head.getChild("ring");
		this.left_arm = this.up_body.getChild("left_arm");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.right_arm = this.up_body.getChild("right_arm");
		this.right_hand = this.right_arm.getChild("right_hand");
		this.axe = this.right_hand.getChild("axe");
		this.axe_head = this.axe.getChild("axe_head");
		this.axe_up1 = this.axe_head.getChild("axe_up1");
		this.axe_up2 = this.axe_head.getChild("axe_up2");
		this.net = this.left_hand.getChild("net");
		this.tail = this.body.getChild("tail");
		this.left_leg = this.body.getChild("left_leg");
		this.left_foot = this.left_leg.getChild("left_foot");
		this.right_leg = this.body.getChild("right_leg");
		this.right_foot = this.right_leg.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -21.4291F, -1.6334F));

		PartDefinition skirt_r1 = body.addOrReplaceChild("skirt_r1", CubeListBuilder.create().texOffs(0, 128).addBox(-9.0F, -5.0F, -5.5F, 18.0F, 14.0F, 10.0F, new CubeDeformation(0.75F))
				.texOffs(0, 109).addBox(-9.0F, -5.0F, -5.5F, 18.0F, 9.0F, 10.0F, new CubeDeformation(0.25F))
				.texOffs(0, 85).addBox(-9.0F, -5.0F, -5.5F, 18.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6984F, 4.8057F, -0.0873F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(100, 20).addBox(-3.0F, -5.0F, -3.5F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6984F, 4.8057F, 0.0436F, 0.0F, 0.0F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create(), PartPose.offset(0.0F, 7.9291F, 1.6334F));

		PartDefinition body_r2 = up_body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(54, 67).addBox(-7.0F, -4.0F, -3.0F, 14.0F, 8.0F, 11.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body_r3 = up_body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(0, 64).addBox(-7.0F, -6.0F, -3.0F, 14.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body_r4 = up_body.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(0, 37).addBox(-11.0F, -6.0F, -8.0F, 22.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5803F, -1.5964F, 0.3927F, 0.0F, 0.0F));

		PartDefinition neck = up_body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(-4.5F, -15.5F, -4.5F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(64, 23).addBox(-4.0F, -2.0F, -5.0F, 8.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -1.7868F, 0.9096F, -2.5307F, 0.0F, 0.0F));

		PartDefinition neck_r2 = neck.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, -5.0F, -5.0F, 7.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -2.6753F, -2.1929F, -0.2618F, 0.0F, 0.0F));

		PartDefinition neck_r3 = neck.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(34, 17).mirror().addBox(-2.0F, -5.0F, -6.0F, 4.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, -0.6545F, 0.4363F, -0.2618F));

		PartDefinition neck_r4 = neck.addOrReplaceChild("neck_r4", CubeListBuilder.create().texOffs(34, 17).addBox(-2.0F, -5.0F, -6.0F, 4.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, -0.4363F, 0.2618F));

		PartDefinition hair = neck.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(4.0F, -2.9253F, -5.6929F));

		PartDefinition hair_r1 = hair.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(133, -9).addBox(0.7146F, -3.5513F, -1.7765F, 0.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2696F, -2.1477F, 0.162F, -0.2234F, -0.3512F, 0.2443F));

		PartDefinition hair_r2 = hair.addOrReplaceChild("hair_r2", CubeListBuilder.create().texOffs(133, -9).addBox(-0.7146F, -3.5513F, -1.7766F, 0.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2459F, -2.1853F, -0.0511F, -0.2234F, 0.3512F, -0.2443F));

		PartDefinition hair_r3 = hair.addOrReplaceChild("hair_r3", CubeListBuilder.create().texOffs(108, 0).addBox(-3.5F, -5.6946F, -1.0608F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(4.5F, -3.0F, -4.5F));

		PartDefinition right_horn_r1 = head.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(184, 0).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(184, 0).mirror().addBox(9.0F, -2.5F, -1.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -9.5F, -7.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_horn_r2 = head.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(174, 0).addBox(-1.0F, -4.5F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.1F))
				.texOffs(174, 0).mirror().addBox(9.0F, -4.5F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -4.5F, -3.5F, 1.1345F, 0.0F, 0.0F));

		PartDefinition beard_r1 = head.addOrReplaceChild("beard_r1", CubeListBuilder.create().texOffs(94, 0).addBox(-3.0F, -2.5F, -4.0F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 6.5F, -6.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -8.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition helmet_r1 = head.addOrReplaceChild("helmet_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -4.0F));

		PartDefinition right_eye_r1 = right_eye.addOrReplaceChild("right_eye_r1", CubeListBuilder.create().texOffs(0, -2).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.01F, -0.7071F, -1.2247F, 0.2618F, 0.0F, 0.0F));

		PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -4.0F));

		PartDefinition left_eye_r1 = left_eye.addOrReplaceChild("left_eye_r1", CubeListBuilder.create().texOffs(0, -2).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.01F, -0.7071F, -1.2247F, 0.2618F, 0.0F, 0.0F));

		PartDefinition ring = head.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -0.0577F, -3.2668F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.3682F, -11.674F, 1.4399F, 0.0F, 0.0F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(11.0F, -15.5F, -3.5F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(172, 47).mirror().addBox(-0.5F, -5.5F, -5.0F, 1.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.5529F, 3.8129F, 2.2318F, 0.0F, -0.1745F, 1.3963F));

		PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(172, 47).mirror().addBox(-0.5F, -5.5F, -5.0F, 1.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.6971F, -0.6871F, 1.2318F, 0.1745F, 0.0F, -0.1745F));

		PartDefinition left_arm_r3 = left_arm.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(194, 0).mirror().addBox(-3.5F, -4.0F, -5.0F, 7.0F, 8.0F, 10.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(3.0139F, 0.2467F, 1.4923F, 0.1745F, 0.0F, -0.1745F));

		PartDefinition left_arm_r4 = left_arm.addOrReplaceChild("left_arm_r4", CubeListBuilder.create().texOffs(168, 18).mirror().addBox(-0.4132F, -6.5076F, -5.0F, 4.0F, 20.0F, 9.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(194, 18).mirror().addBox(-3.4132F, -6.5076F, -5.0F, 7.0F, 20.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(3.4132F, 3.5076F, 2.5F, 0.1745F, 0.0F, -0.1745F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(5.5F, 16.9224F, 3.2217F));

		PartDefinition left_hand_r1 = left_hand.addOrReplaceChild("left_hand_r1", CubeListBuilder.create().texOffs(170, 68).mirror().addBox(0.5F, -13.5F, -5.0F, 4.0F, 16.0F, 8.0F, new CubeDeformation(0.75F)).mirror(false)
				.texOffs(194, 47).mirror().addBox(-2.5F, -10.5F, -5.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 14.0776F, 0.7783F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_hand_r2 = left_hand.addOrReplaceChild("left_hand_r2", CubeListBuilder.create().texOffs(170, 92).mirror().addBox(0.5F, -13.5F, -6.0F, 6.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(194, 68).mirror().addBox(-1.75F, -14.5F, -5.0F, 7.0F, 21.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.75F, 13.0776F, 0.7783F, -0.0873F, 0.0F, 0.0F));

		PartDefinition net = left_hand.addOrReplaceChild("net", CubeListBuilder.create().texOffs(192, 128).addBox(-1.5F, 0.5776F, -2.2217F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(192, 128).addBox(-1.5F, 10.5776F, -2.2217F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.75F))
				.texOffs(212, 128).addBox(-1.5F, 20.5776F, -2.2217F, 4.0F, 16.0F, 6.0F, new CubeDeformation(1.75F)), PartPose.offset(-1.0F, 16.0F, -1.0F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-11.0F, -15.5F, -3.5F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(172, 47).addBox(-0.5F, -5.5F, -5.0F, 1.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-3.4922F, 3.1183F, 2.2318F, 0.0F, 0.1745F, -1.3963F));

		PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(172, 47).addBox(-0.5F, -5.5F, -5.0F, 1.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.7578F, -1.3817F, 1.2318F, 0.1745F, 0.0F, 0.1745F));

		PartDefinition right_arm_r3 = right_arm.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(194, 0).addBox(-3.5F, -4.0F, -5.0F, 7.0F, 8.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.9531F, -0.4479F, 1.4923F, 0.1745F, 0.0F, 0.1745F));

		PartDefinition right_arm_r4 = right_arm.addOrReplaceChild("right_arm_r4", CubeListBuilder.create().texOffs(168, 18).addBox(-3.5868F, -6.5076F, -5.0F, 4.0F, 20.0F, 9.0F, new CubeDeformation(0.5F))
				.texOffs(194, 18).addBox(-3.5868F, -6.5076F, -5.0F, 7.0F, 20.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.4132F, 3.5076F, 2.5F, 0.1745F, 0.0F, 0.1745F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-5.5F, 16.9224F, 3.2217F));

		PartDefinition right_hand_r1 = right_hand.addOrReplaceChild("right_hand_r1", CubeListBuilder.create().texOffs(170, 92).addBox(-6.5F, -13.5F, -6.0F, 6.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(194, 68).addBox(-5.25F, -14.5F, -5.0F, 7.0F, 21.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 13.0776F, 0.7783F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_hand_r2 = right_hand.addOrReplaceChild("right_hand_r2", CubeListBuilder.create().texOffs(170, 68).addBox(-4.5F, -13.5F, -5.0F, 4.0F, 16.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(194, 47).addBox(-4.5F, -10.5F, -5.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, 14.0776F, 0.7783F, -0.0873F, 0.0F, 0.0F));

		PartDefinition axe = right_hand.addOrReplaceChild("axe", CubeListBuilder.create().texOffs(220, 192).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 39.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(228, 203).addBox(-2.0F, 20.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(238, 230).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(238, 235).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(242, 192).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(202, 236).addBox(-1.5F, -22.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 17.0776F, -1.7217F, 1.4835F, 0.0F, 0.0F));

		PartDefinition body_r5 = axe.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(242, 197).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.7929F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition axe_head = axe.addOrReplaceChild("axe_head", CubeListBuilder.create().texOffs(192, 224).addBox(-2.0F, -25.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition axe_up1 = axe_head.addOrReplaceChild("axe_up1", CubeListBuilder.create().texOffs(228, 192).addBox(-2.0F, -2.5F, 2.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(218, 233).addBox(-1.0F, -2.5F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(214, 236).addBox(-0.5F, -2.5F, 7.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.5F, 0.0F));

		PartDefinition head_r2 = axe_up1.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(228, 220).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.026F)), PartPose.offsetAndRotation(-0.75F, 1.5F, 5.5F, 0.0F, 0.3927F, 0.0F));

		PartDefinition head_r3 = axe_up1.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(214, 236).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.5F, 8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition head_r4 = axe_up1.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(228, 220).addBox(0.0F, -4.0F, -1.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.75F, 1.5F, 5.5F, 0.0F, -0.3927F, 0.0F));

		PartDefinition axe_up2 = axe_head.addOrReplaceChild("axe_up2", CubeListBuilder.create().texOffs(228, 192).addBox(-2.0F, -2.5F, -5.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(218, 233).addBox(-1.0F, -2.5F, -7.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(214, 236).addBox(-0.5F, -2.5F, -8.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(192, 192).addBox(0.0F, -7.5F, -13.0F, 0.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.5F, 0.0F));

		PartDefinition head_r5 = axe_up2.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(228, 232).addBox(0.0F, -4.0F, -3.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.026F)), PartPose.offsetAndRotation(0.75F, 1.5F, -5.5F, 0.0F, 0.3927F, 0.0F));

		PartDefinition head_r6 = axe_up2.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(214, 236).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.5F, -8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition head_r7 = axe_up2.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(228, 232).addBox(-1.0F, -4.0F, -3.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-0.75F, 1.5F, -5.5F, 0.0F, -0.3927F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.6032F, 7.2754F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(54, 61).addBox(0.0F, -2.5F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.8115F, 4.7651F, 1.5708F, -1.309F, -1.5708F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(54, 61).addBox(0.0F, -2.5F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.8115F, 4.7651F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_r3 = tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(50, 64).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.8259F, 2.858F, 0.2618F, 0.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(5.564F, 14.6165F, 2.9368F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(228, 0).mirror().addBox(-2.5F, -7.5F, -2.5F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.064F, 6.3126F, -0.8034F, -0.3054F, 0.0F, -0.0873F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(224, 55).mirror().addBox(-2.5F, 11.624F, -3.3131F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.936F, 14.1885F, -0.7902F));

		PartDefinition left_foot_r1 = left_foot.addOrReplaceChild("left_foot_r1", CubeListBuilder.create().texOffs(224, 66).mirror().addBox(-2.5F, -4.5F, -2.5F, 5.0F, 11.0F, 3.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.4477F, 0.3033F, -0.2007F, 0.0F, 0.0F));

		PartDefinition left_foot_r2 = left_foot.addOrReplaceChild("left_foot_r2", CubeListBuilder.create().texOffs(226, 37).mirror().addBox(-2.5F, -6.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.1477F, 0.5033F, -0.2182F, 0.0F, 0.0F));

		PartDefinition left_foot_r3 = left_foot.addOrReplaceChild("left_foot_r3", CubeListBuilder.create().texOffs(226, 23).mirror().addBox(-2.5F, -9.5F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.124F, 1.2869F, 0.3054F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-5.564F, 14.6165F, 2.9368F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(228, 0).addBox(-4.5F, -7.5F, -2.5F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.064F, 6.3126F, -0.8034F, -0.3054F, 0.0F, 0.0873F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(224, 55).addBox(-2.5F, 11.624F, -3.3131F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(-0.936F, 14.1885F, -0.7902F));

		PartDefinition right_foot_r1 = right_foot.addOrReplaceChild("right_foot_r1", CubeListBuilder.create().texOffs(224, 66).addBox(-2.5F, -4.5F, -2.5F, 5.0F, 11.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 8.4477F, 0.3033F, -0.2007F, 0.0F, 0.0F));

		PartDefinition right_foot_r2 = right_foot.addOrReplaceChild("right_foot_r2", CubeListBuilder.create().texOffs(226, 37).addBox(-2.5F, -6.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1477F, 0.5033F, -0.2182F, 0.0F, 0.0F));

		PartDefinition right_foot_r3 = right_foot.addOrReplaceChild("right_foot_r3", CubeListBuilder.create().texOffs(226, 23).addBox(-2.5F, -9.5F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 6.124F, 1.2869F, 0.3054F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		if (t.isFault()) {
			return;
		}
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		this.net.visible = t.getReteUseTick() > 0 && t.getReteUseTick() <= 38;
		if (t.isAlive() && t.getRushUseTick() > 20 && t.getRushUseTick() <= 120) {
			this.animateWalk(MerorChampionAnimation.RUSH, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(MerorChampionAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.idleAnimationState, MerorChampionAnimation.IDLE, f3);
		this.animate(t.baseAnimationState, MerorChampionAnimation.BASE, f3);

		this.animate(t.chop1AnimationState, MerorChampionAnimation.CHOP1, f3);
		this.animate(t.chop2AnimationState, MerorChampionAnimation.CHOP2, f3);
		this.animate(t.slash1AnimationState, MerorChampionAnimation.SLASH1, f3);
		this.animate(t.slash2AnimationState, MerorChampionAnimation.SLASH2, f3);
		this.animate(t.slash3AnimationState, MerorChampionAnimation.SLASH3, f3);
		this.animate(t.stomp1AnimationState, MerorChampionAnimation.STOMP1, f3);
		this.animate(t.overloadAnimationState, MerorChampionAnimation.OVERLOAD, f3);
		if (!t.isTame() && t.isFirstCombat()) {
			this.animate(t.standStartAnimationState, MerorChampionAnimation.DEAD, f3);
			this.animate(t.standStopAnimationState, MerorChampionAnimation.STOPNOTACTIVE, f3);
			this.animate(t.standAnimationState, MerorChampionAnimation.NOTACTIVE, f3);
		}
		else {
			this.animate(t.standStartAnimationState, MerorChampionAnimation.TOSIT, f3);
			this.animate(t.standStopAnimationState, MerorChampionAnimation.STOPSIT, f3);
			this.animate(t.standAnimationState, MerorChampionAnimation.SIT, f3);
		}
		this.animate(t.shockAnimationState, MerorChampionAnimation.SHOCK, f3);
		this.animate(t.throwAnimationState, MerorChampionAnimation.THROW, f3);
		this.animate(t.cycloneAnimationState, MerorChampionAnimation.CYCLONE, f3);
		this.animate(t.hornAnimationState, MerorChampionAnimation.HORN, f3);
		this.animate(t.rushStartAnimationState, MerorChampionAnimation.RUSHSTART, f3);
		this.animate(t.rushStopAnimationState, MerorChampionAnimation.RUSHSTOP, f3);
		this.animate(t.deadAnimationState, MerorChampionAnimation.DEAD, f3);
		this.animate(t.pose1AnimationState, MerorChampionAnimation.POSE1, f3);
		this.animate(t.pose2AnimationState, MerorChampionAnimation.POSE2, f3);
		this.animate(t.pose3AnimationState, MerorChampionAnimation.POSE3, f3);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f%360, -30.0f, 30.0f);
		f2 = Mth.clamp(f2%360, -45.0f, 45.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getHand(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_hand;
		}
		return this.right_hand;
	}

	protected ModelPart getWeaponHand(HumanoidArm humanoidArm) {
		return this.axe;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getHand(humanoidArm).translateAndRotate(poseStack);
		this.getWeaponHand(humanoidArm).translateAndRotate(poseStack);
	}
}