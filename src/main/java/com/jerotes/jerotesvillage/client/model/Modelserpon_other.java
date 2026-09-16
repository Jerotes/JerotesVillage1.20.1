package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelserpon_other<T extends LivingEntity> extends Modelhumanoid<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_other"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart tail;
	private final ModelPart tail_1;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart tail_4;

	public Modelserpon_other(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_wing = body.getChild("left_wing");
		this.right_wing = body.getChild("right_wing");
		this.tail = body.getChild("tail");
		this.tail_1 = tail.getChild("tail_1");
		this.tail_2 = tail.getChild("tail_2");
		this.tail_3 = tail.getChild("tail_3");
		this.tail_4 = tail.getChild("tail_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition left_horn_r1 = horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(0, -8).mirror().addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.75F, -15.5F, 4.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_horn_r1 = horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(0, -8).addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -15.5F, 4.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(16, -4).addBox(0.0F, -31.25F, 4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, -4).addBox(0.0F, -27.25F, 3.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -5.25F));

		PartDefinition mouth_out_r1 = mouth.addOrReplaceChild("mouth_out_r1", CubeListBuilder.create().texOffs(18, 21).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1396F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0785F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(0, -6).mirror().addBox(0.1221F, -3.1272F, -1.5013F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 1.5F, 2.0F, 0.2182F, 0.4363F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(0, -6).addBox(-0.1221F, -3.1272F, -1.5013F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 1.5F, 2.0F, 0.2182F, -0.4363F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, 0.0F));

		PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_1_r1 = tail_1.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(34, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.5F, 2.5F, 1.2217F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_2_r1 = tail_2.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(34, 10).addBox(-2.0F, -2.5F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 7.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_3_r1 = tail_3.addOrReplaceChild("tail_3_r1", CubeListBuilder.create().texOffs(48, 10).addBox(-1.0F, 11.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -1.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_4 = tail.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(54, -5).addBox(0.0F, -8.0F, 13.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(20, -3).mirror().addBox(-0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.3F, 3.5F, 0.75F, 1.3526F, 0.0F, 0.0873F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(20, -3).mirror().addBox(0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.3F, 3.5F, 0.75F, 1.3526F, 0.0F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch);
		float f6 = 0.4f * limbSwingAmount + 0.3f;
		if (!entityIn.onGround()) {
			this.left_wing.yRot = Mth.cos((ageInTicks + limbSwing)/2.5f * 0.6662f + 3.1415927f) * f6;
			this.right_wing.yRot = Mth.cos((ageInTicks + limbSwing)/2.5f * 0.6662f) * f6;
		}
		this.tail_1.x = 2f * Mth.cos(limbSwing * 0.6662f + 0.2f) * limbSwingAmount;
		this.tail_2.x = 4f * Mth.cos(limbSwing * 0.6662f + 0.4f) * limbSwingAmount;
		this.tail_3.x = 7f * Mth.cos(limbSwing * 0.6662f + 0.6f) * limbSwingAmount;
		this.tail_4.x = 9f * Mth.cos(limbSwing * 0.6662f + 0.8f) * limbSwingAmount;
		if (entityIn instanceof SerponEntity serponEntity && serponEntity.isPrepare()) {
			this.left_wing.xScale = 1.2f;
			this.left_wing.yScale = 1.2f;
			this.left_wing.zScale = 1.2f;
			this.right_wing.xScale = 1.2f;
			this.right_wing.yScale = 1.2f;
			this.right_wing.zScale = 1.2f;
		}
	}
}