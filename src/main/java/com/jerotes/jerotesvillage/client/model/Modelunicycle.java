	package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.UnicycleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

    public class Modelunicycle<T extends UnicycleEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "unicycle"), "main");
	private final ModelPart wheel;
	private final ModelPart body;

	public Modelunicycle(ModelPart root) {
		this.wheel = root.getChild("wheel");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wheel = partdefinition.addOrReplaceChild("wheel", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition wheel_r1 = wheel.addOrReplaceChild("wheel_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -13.0F, -1.5F, 2.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 30).addBox(-4.0F, -15.0F, -3.5F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-4.0F, -3.0F, -3.5F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(12, 0).addBox(-3.0F, 0.0F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, 1.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition side_4_r1 = body.addOrReplaceChild("side_4_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -3.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(5.5F, 4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition side_3_r1 = body.addOrReplaceChild("side_3_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.5F, -1.5708F, 1.0472F, -1.5708F));

		PartDefinition side_2_r1 = body.addOrReplaceChild("side_2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.0F, 5.5F, 1.5708F, 1.0472F, 1.5708F));

		PartDefinition support_2_r1 = body.addOrReplaceChild("support_2_r1", CubeListBuilder.create().texOffs(12, 0).addBox(-3.0F, -2.0F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		wheel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.wheel.xRot = limbSwing;

	}
}
