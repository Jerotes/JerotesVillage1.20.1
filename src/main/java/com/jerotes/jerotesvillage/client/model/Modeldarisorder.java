package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DarisorderAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Modeldarisorder<T extends DarisorderEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "darisorder"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart up_body;
	private final ModelPart head;

	public Modeldarisorder(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.body = root.getChild("body");
		this.up_body = body.getChild("up_body");
		this.head = up_body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, -2.0F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, -1.0F));

		PartDefinition up_body_r1 = up_body.addOrReplaceChild("up_body_r1", CubeListBuilder.create().texOffs(70, 145).addBox(-3.0F, -13.0F, 10.0F, 6.0F, 26.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 99).addBox(-16.0F, -13.0F, -10.0F, 32.0F, 26.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(17.9957F, -5.5653F, 1.5F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(0, 175).mirror().addBox(-4.5F, -15.0F, -6.5F, 10.0F, 30.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5043F, 9.0653F, 0.0F, 0.1745F, 0.0F, -0.1309F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(5.3897F, 22.1193F, 5.3473F));

		PartDefinition left_hand_r1 = left_hand.addOrReplaceChild("left_hand_r1", CubeListBuilder.create().texOffs(54, 244).mirror().addBox(-3.0F, -9.0F, 7.5F, 0.0F, 16.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 220).mirror().addBox(3.0F, -11.0F, 7.5F, 0.0F, 24.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 218).mirror().addBox(-6.0F, -13.0F, -7.5F, 12.0F, 26.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 11.0F, -5.0F, -0.1745F, 0.0F, 0.0436F));

		PartDefinition left_claw = left_hand.addOrReplaceChild("left_claw", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, -6.1F));

		PartDefinition left_claw_r1 = left_claw.addOrReplaceChild("left_claw_r1", CubeListBuilder.create().texOffs(0, 259).mirror().addBox(-7.0F, -5.0F, -7.5F, 14.0F, 11.0F, 15.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.3854F, 4.4459F, -1.7473F, -0.1745F, 0.0F, 0.0436F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-17.9957F, -5.5653F, 1.5F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(0, 175).addBox(-5.5F, -15.0F, -6.5F, 10.0F, 30.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5043F, 9.0653F, 0.0F, 0.1745F, 0.0F, 0.1309F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-5.3897F, 22.1193F, 5.3473F));

		PartDefinition right_hand_r1 = right_hand.addOrReplaceChild("right_hand_r1", CubeListBuilder.create().texOffs(54, 244).addBox(3.0F, -9.0F, 7.5F, 0.0F, 16.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(54, 220).addBox(-3.0F, -11.0F, 7.5F, 0.0F, 24.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 218).addBox(-6.0F, -13.0F, -7.5F, 12.0F, 26.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, -5.0F, -0.1745F, 0.0F, -0.0436F));

		PartDefinition right_claw = right_hand.addOrReplaceChild("right_claw", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, -6.1F));

		PartDefinition right_claw_r1 = right_claw.addOrReplaceChild("right_claw_r1", CubeListBuilder.create().texOffs(0, 259).addBox(-7.0F, -5.0F, -7.5F, 14.0F, 11.0F, 15.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.3854F, 4.4459F, -1.7473F, -0.1745F, 0.0F, -0.0436F));

		PartDefinition head = up_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.7001F, -9.5579F, 14.0F, 12.0F, 12.0F, new CubeDeformation(0.05F))
				.texOffs(16, 71).addBox(-1.0F, -0.7001F, -10.1579F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(52, 44).addBox(-11.0F, -1.7001F, -9.5579F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.025F))
				.texOffs(52, 44).mirror().addBox(7.0F, -1.7001F, -9.5579F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.025F)).mirror(false)
				.texOffs(0, 110).addBox(-13.4F, 2.2999F, -9.5579F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.02F))
				.texOffs(0, 110).mirror().addBox(10.4F, 2.2999F, -9.5579F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(0, 24).addBox(-6.5F, -0.7001F, -9.5579F, 13.0F, 11.0F, 12.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 47).addBox(-6.5F, -1.2001F, -9.9579F, 13.0F, 12.0F, 12.0F, new CubeDeformation(-0.6F))
				.texOffs(76, 17).addBox(-7.0F, 3.2999F, -9.5579F, 14.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.2999F, -11.4421F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 99).addBox(-1.0F, -2.5F, -6.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-11.249F, 0.6329F, -3.5579F, 0.0F, 0.0F, 0.5236F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 99).mirror().addBox(-1.0F, -2.5F, -6.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(11.249F, 0.6329F, -3.5579F, 0.0F, 0.0F, -0.5236F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 71).mirror().addBox(-2.5F, -5.0F, -6.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.4F, -0.0501F, 2.4421F, 0.0F, 0.0F, -0.5236F));

		PartDefinition head_r4 = head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 85).mirror().addBox(-2.5F, -5.0F, -6.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.4F, -3.0501F, -3.5579F, 0.0F, 0.0F, -0.5236F));

		PartDefinition head_r5 = head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 71).addBox(-2.5F, -5.0F, -6.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4F, -0.0501F, 2.4421F, 0.0F, 0.0F, 0.5236F));

		PartDefinition head_r6 = head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(0, 85).addBox(-2.5F, -5.0F, -6.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4F, -3.0501F, -3.5579F, 0.0F, 0.0F, 0.5236F));

		PartDefinition eye_r1 = head.addOrReplaceChild("eye_r1", CubeListBuilder.create().texOffs(16, 71).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(3.0F, -4.7001F, -9.1579F, 0.0F, 0.0F, -0.0873F));

		PartDefinition eye_r2 = head.addOrReplaceChild("eye_r2", CubeListBuilder.create().texOffs(16, 71).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-3.0F, -4.7001F, -9.1579F, 0.0F, 0.0F, 0.0873F));

		PartDefinition eye_r3 = head.addOrReplaceChild("eye_r3", CubeListBuilder.create().texOffs(16, 71).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(6.0F, -0.7001F, -9.1579F, 0.0F, 0.0F, -0.1745F));

		PartDefinition eye_r4 = head.addOrReplaceChild("eye_r4", CubeListBuilder.create().texOffs(16, 71).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-6.0F, -0.7001F, -9.1579F, 0.0F, 0.0F, 0.1745F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(76, 32).addBox(-7.0F, -2.6777F, -10.25F, 14.0F, 2.0F, 12.0F, new CubeDeformation(-0.025F))
				.texOffs(52, 0).addBox(-7.0F, -0.6777F, -10.25F, 14.0F, 5.0F, 12.0F, new CubeDeformation(0.05F))
				.texOffs(52, 28).addBox(-10.8F, 2.3223F, -10.25F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.025F))
				.texOffs(52, 28).mirror().addBox(6.8F, 2.3223F, -10.25F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offset(0.0F, 6.8776F, 0.6921F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(52, 17).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(51, 36).mirror().addBox(-2.5F, -3.0F, 3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.7F, 3.1062F, -7.25F, 0.0F, 0.0F, -0.8727F));

		PartDefinition jaw_r2 = jaw.addOrReplaceChild("jaw_r2", CubeListBuilder.create().texOffs(51, 36).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(52, 17).addBox(-2.5F, -3.0F, -9.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7F, 3.1062F, -1.25F, 0.0F, 0.0F, 0.8727F));

		PartDefinition tendril_1 = jaw.addOrReplaceChild("tendril_1", CubeListBuilder.create().texOffs(38, 77).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 4.3223F, -7.25F));

		PartDefinition tendril_1_add = tendril_1.addOrReplaceChild("tendril_1_add", CubeListBuilder.create().texOffs(46, 77).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition tendril_1_light = tendril_1_add.addOrReplaceChild("tendril_1_light", CubeListBuilder.create().texOffs(16, 71).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition tendril_2 = jaw.addOrReplaceChild("tendril_2", CubeListBuilder.create().texOffs(22, 77).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 4.3223F, -8.25F));

		PartDefinition tendril_2_add = tendril_2.addOrReplaceChild("tendril_2_add", CubeListBuilder.create().texOffs(30, 77).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition tendril_2_light = tendril_2_add.addOrReplaceChild("tendril_2_light", CubeListBuilder.create().texOffs(16, 71).addBox(-13.9F, 3.35F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(12.9F, 3.65F, 10.0F));

		PartDefinition tendril_3 = jaw.addOrReplaceChild("tendril_3", CubeListBuilder.create().texOffs(22, 77).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 4.3223F, -8.25F));

		PartDefinition tendril_3_add = tendril_3.addOrReplaceChild("tendril_3_add", CubeListBuilder.create().texOffs(30, 77).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition tendril_3_light = tendril_3_add.addOrReplaceChild("tendril_3_light", CubeListBuilder.create().texOffs(16, 71).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition tendril_4 = jaw.addOrReplaceChild("tendril_4", CubeListBuilder.create().texOffs(38, 77).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.5F, 4.3223F, -7.25F));

		PartDefinition tendril_4_add = tendril_4.addOrReplaceChild("tendril_4_add", CubeListBuilder.create().texOffs(46, 77).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition tendril_4_light = tendril_4_add.addOrReplaceChild("tendril_4_light", CubeListBuilder.create().texOffs(16, 71).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition down_body = body.addOrReplaceChild("down_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4995F, 1.5218F));

		PartDefinition up_body_r2 = down_body.addOrReplaceChild("up_body_r2", CubeListBuilder.create().texOffs(46, 204).addBox(-10.0F, -0.5086F, -0.3926F, 20.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.311F, -6.7513F, -0.0873F, 0.0F, 0.0F));

		PartDefinition up_body_r3 = down_body.addOrReplaceChild("up_body_r3", CubeListBuilder.create().texOffs(0, 145).addBox(-10.0F, -7.5F, -7.5F, 20.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail = down_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.5987F, 7.8076F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(20, 178).addBox(-10.0F, 0.5671F, -0.0124F, 20.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

		PartDefinition left_leg = down_body.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(5.0F, 8.5005F, -2.5218F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(0, 285).mirror().addBox(-5.5F, -9.5F, -6.5F, 11.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 4.0784F, 1.1117F, -0.1739F, -0.0151F, -0.0859F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(4.0F, 15.0F, -2.0F));

		PartDefinition left_foot_r1 = left_foot.addOrReplaceChild("left_foot_r1", CubeListBuilder.create().texOffs(0, 352).mirror().addBox(-4.5F, 6.5F, -6.5F, 9.0F, 6.0F, 14.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.6128F, 6.0595F, 4.2847F, 0.0F, -0.0873F, 0.0F));

		PartDefinition left_foot_r2 = left_foot.addOrReplaceChild("left_foot_r2", CubeListBuilder.create().texOffs(0, 319).mirror().addBox(-4.5F, -10.5F, -6.5F, 9.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6128F, 6.0595F, 4.2847F, 0.1752F, -0.0859F, -0.0152F));

		PartDefinition right_leg = down_body.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 8.5005F, -2.5218F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(0, 285).addBox(-5.5F, -9.5F, -6.5F, 11.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 4.0784F, 1.1117F, -0.1739F, 0.0151F, 0.0859F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(-4.0F, 15.0F, -2.0F));

		PartDefinition right_foot_r1 = right_foot.addOrReplaceChild("right_foot_r1", CubeListBuilder.create().texOffs(0, 352).addBox(-4.5F, 6.5F, -6.5F, 9.0F, 6.0F, 14.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.6128F, 6.0595F, 4.2847F, 0.0F, 0.0873F, 0.0F));

		PartDefinition right_foot_r2 = right_foot.addOrReplaceChild("right_foot_r2", CubeListBuilder.create().texOffs(0, 319).addBox(-4.5F, -10.5F, -6.5F, 9.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6128F, 6.0595F, 4.2847F, 0.1752F, 0.0859F, 0.0152F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		if (entity.isAggressive()) {
			this.animateWalk(DarisorderAnimation.RUN, limbSwing, limbSwingAmount, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(DarisorderAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
		}
		this.animate(entity.idleAnimationState, DarisorderAnimation.IDLE, ageInTicks);
		if (entity.isAggressive()) {
			this.animate(entity.idleAnimationState, DarisorderAnimation.MOUTH, ageInTicks);
		}
		this.animate(entity.startAnimationState, DarisorderAnimation.START, ageInTicks);
		this.animate(entity.attack1AnimationState, DarisorderAnimation.ATTACK1, ageInTicks);
		this.animate(entity.attack2AnimationState, DarisorderAnimation.ATTACK2, ageInTicks);
		this.animate(entity.attack3AnimationState, DarisorderAnimation.ATTACK3, ageInTicks);
		this.animate(entity.attack4AnimationState, DarisorderAnimation.ATTACK4, ageInTicks);
		this.animate(entity.deadAnimationState, DarisorderAnimation.DEATH, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}