package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.MerorLocatorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelmeror_locator<T extends MerorLocatorEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "meror_locator"), "main");
	private final ModelPart body;
	private final ModelPart fly_1;
	private final ModelPart fly_2;
	private final ModelPart fly_3;
	private final ModelPart fly_4;

	public Modelmeror_locator(ModelPart root) {
		this.body = root.getChild("body");
		this.fly_1 = root.getChild("fly_1");
		this.fly_2 = root.getChild("fly_2");
		this.fly_3 = root.getChild("fly_3");
		this.fly_4 = root.getChild("fly_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-2.5F, -4.5F, -2.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 12).addBox(1.5F, -4.5F, -2.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 12).addBox(1.5F, -4.5F, 1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 12).addBox(-2.5F, -4.5F, 1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 16).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition eye4_r1 = body.addOrReplaceChild("eye4_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition eye3_r1 = body.addOrReplaceChild("eye3_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition eye2_r1 = body.addOrReplaceChild("eye2_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition fly_1 = partdefinition.addOrReplaceChild("fly_1", CubeListBuilder.create().texOffs(-4, 8).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.75F, -2.0F));

		PartDefinition fly_2 = partdefinition.addOrReplaceChild("fly_2", CubeListBuilder.create().texOffs(-4, 8).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.75F, -2.0F));

		PartDefinition fly_3 = partdefinition.addOrReplaceChild("fly_3", CubeListBuilder.create().texOffs(-4, 8).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.75F, 2.0F));

		PartDefinition fly_4 = partdefinition.addOrReplaceChild("fly_4", CubeListBuilder.create().texOffs(-4, 8).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.75F, 2.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.yRot = netHeadYaw * 0.017453292f;
		if (!entity.onGround()) {
			this.fly_1.yRot = ageInTicks;
			this.fly_2.yRot = ageInTicks;
			this.fly_3.yRot = ageInTicks;
			this.fly_4.yRot = ageInTicks;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		fly_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		fly_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		fly_3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		fly_4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}