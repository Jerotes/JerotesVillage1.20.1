package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.EyeDiscEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modeleye_disc<T extends EyeDiscEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "eye_disc"), "main");
	private final ModelPart body;
	private final ModelPart eye;

	public Modeleye_disc(ModelPart root) {
		this.body = root.getChild("body");
		this.eye = body.getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -9.0F, -0.5F, 18.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(-13.5F, -13.5F, 0.0F, 27.0F, 27.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition eye = body.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 19).addBox(-9.0F, -9.0F, 0.0F, 18.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0109F, 0.0F, -1.7498F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.body.xRot = headPitch / (180F / (float) Math.PI);
		this.eye.yRot = netHeadYaw / (180F / (float) Math.PI) * 0.1f;
		this.eye.xRot = headPitch / (180F / (float) Math.PI) * 0.1f;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}