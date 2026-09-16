package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.IronTeethEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class Modeliron_teeth<T extends IronTeethEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "iron_teeth"), "main");
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modeliron_teeth(ModelPart root) {
		this.head = root.getChild("head");
		this.jaw = head.getChild("jaw");
		this.body = root.getChild("body");
		this.body2 = root.getChild("body2");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -11.0F, 8.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 4.0F));

		PartDefinition neck_r1 = head.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(26, 0).addBox(-2.0F, -5.0F, -4.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0F, 2.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition Jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -1.0F, -9.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(52, 1).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(40, 1).addBox(-2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition body2 = partdefinition.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(52, 20).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition body_r2 = body2.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(40, 20).addBox(-2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 9).mirror().addBox(-0.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 9).mirror().addBox(-0.9F, 8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(52, 9).mirror().addBox(-0.9F, 10.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 9).addBox(-1.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 9).addBox(-1.1F, 8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(52, 9).mirror().addBox(-1.1F, 10.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(-1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		float f6 = 0.4f * limbSwingAmount;
		this.left_leg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * f6;
		this.right_leg.xRot = Mth.cos(limbSwing * 0.6662f) * f6;

		int n = entity.getAttackTick();
		if (n > 7) {
			this.head.xRot = 1.5f * Mth.triangleWave((float) n, 15f);
			this.jaw.xRot = -2f * Mth.triangleWave((float) n, 15f);
			this.head.yScale = 1.5f;
			this.head.zScale = 2f;
		}
		else {
			this.head.yScale = 1f;
			this.head.zScale = 1f;
			this.head.xRot = headPitch * 0.0004f;
			this.jaw.xRot = 0f;
		}
		if (entity.isInSittingPose() && !entity.isWander()) {
			this.head.xRot = 0.5f;
		}
	}
}