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

public class Modelmeror_pilum<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "meror_pilum"), "main");
	public final ModelPart body;

	public Modelmeror_pilum(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 2.25F, -0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 13).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -15.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(-0.2F))
				.texOffs(12, 3).addBox(-0.5F, -15.9F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 0).addBox(-1.0F, -3.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(12, 0).addBox(-1.0F, -3.45F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(12, 0).addBox(-1.0F, 1.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(12, 0).addBox(-1.0F, 1.45F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(4, 5).addBox(-0.5F, -8.1F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition tip_r1 = body.addOrReplaceChild("tip_r1", CubeListBuilder.create().texOffs(4, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition rod_r1 = body.addOrReplaceChild("rod_r1", CubeListBuilder.create().texOffs(4, 17).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.7F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition rod_r2 = body.addOrReplaceChild("rod_r2", CubeListBuilder.create().texOffs(4, 19).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
