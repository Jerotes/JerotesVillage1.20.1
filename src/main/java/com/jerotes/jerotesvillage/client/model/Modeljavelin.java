package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modeljavelin<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "javelin"), "main");
	public final ModelPart body;

	public Modeljavelin(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -24.0F, 7.5F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(0, 25).addBox(-8.5F, -11.0F, 7.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F))
				.texOffs(4, 25).addBox(-8.5F, -11.0F, 7.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F))
				.texOffs(4, 0).addBox(-9.5F, -32.0F, 8.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(4, -3).addBox(-8.0F, -32.0F, 6.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(4, 7).addBox(-8.5F, -26.0F, 7.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
				.texOffs(10, 0).addBox(-8.5F, -31.5F, 7.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
