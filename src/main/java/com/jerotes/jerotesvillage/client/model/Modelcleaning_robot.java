package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.CleaningRobotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelcleaning_robot<T extends CleaningRobotEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "cleaning_robot"), "main");
	private final ModelPart body;
	private final ModelPart brace;
	private final ModelPart bottom;
	private final ModelPart clean_1;
	private final ModelPart clean_2;
	private final ModelPart clean_3;
	private final ModelPart clean_4;
	private final ModelPart clean_5;
	private final ModelPart clean_6;
	private final ModelPart clean_7;
	private final ModelPart clean_8;
	private final ModelPart clean_9;


	public Modelcleaning_robot(ModelPart root) {
		this.body = root.getChild("body");
		this.brace = root.getChild("brace");
		this.bottom = root.getChild("bottom");
		this.clean_1 = root.getChild("clean_1");
		this.clean_2 = root.getChild("clean_2");
		this.clean_3 = root.getChild("clean_3");
		this.clean_4 = root.getChild("clean_4");
		this.clean_5 = root.getChild("clean_5");
		this.clean_6 = root.getChild("clean_6");
		this.clean_7 = root.getChild("clean_7");
		this.clean_8 = root.getChild("clean_8");
		this.clean_9 = root.getChild("clean_9");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.25F, 0.0F));

		PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(32, 26).addBox(-2.0F, -0.5F, -2.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -8.5F, 3.5F));

		PartDefinition brace = partdefinition.addOrReplaceChild("brace", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.25F, 0.0F));

		PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -0.7143F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(24, 20).addBox(-8.0F, -0.7143F, 8.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 20).addBox(-8.0F, -0.7143F, -9.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.2857F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition clean_west_r1 = bottom.addOrReplaceChild("clean_west_r1", CubeListBuilder.create().texOffs(24, 20).addBox(-8.0F, 0.0F, 8.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 20).addBox(-8.0F, 0.0F, -9.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7143F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition clean_1 = partdefinition.addOrReplaceChild("clean_1", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 24.0F, 7.5F));

		PartDefinition clean_2 = partdefinition.addOrReplaceChild("clean_2", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 24.0F, 7.5F));

		PartDefinition clean_3 = partdefinition.addOrReplaceChild("clean_3", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 24.0F, -7.5F));

		PartDefinition clean_4 = partdefinition.addOrReplaceChild("clean_4", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 24.0F, -7.5F));

		PartDefinition clean_5 = partdefinition.addOrReplaceChild("clean_5", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -7.5F));

		PartDefinition clean_6 = partdefinition.addOrReplaceChild("clean_6", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 24.0F, 0.0F));

		PartDefinition clean_7 = partdefinition.addOrReplaceChild("clean_7", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 7.5F));

		PartDefinition clean_8 = partdefinition.addOrReplaceChild("clean_8", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 24.0F, 0.0F));

		PartDefinition clean_9 = partdefinition.addOrReplaceChild("clean_9", CubeListBuilder.create().texOffs(47, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isOn()) {
			this.body.yRot = netHeadYaw * 0.017453292f;
			this.clean_1.yRot = ageInTicks;
			this.clean_2.yRot = ageInTicks;
			this.clean_3.yRot = ageInTicks;
			this.clean_4.yRot = ageInTicks;
			this.clean_5.yRot = ageInTicks;
			this.clean_6.yRot = ageInTicks;
			this.clean_7.yRot = ageInTicks;
			this.clean_8.yRot = ageInTicks;
			this.clean_9.yRot = ageInTicks;
		}
		else{
			this.body.yRot = 0;
			this.clean_1.yRot = 0;
			this.clean_2.yRot = 0;
			this.clean_3.yRot = 0;
			this.clean_4.yRot = 0;
			this.clean_5.yRot = 0;
			this.clean_6.yRot = 0;
			this.clean_7.yRot = 0;
			this.clean_8.yRot = 0;
			this.clean_9.yRot = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		brace.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		clean_9.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}