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

public class Modelserpon_javelin<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_javelin"), "main");
	public final ModelPart body;

	public Modelserpon_javelin(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition head_r1 = body.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(4, 4).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rod_r1 = body.addOrReplaceChild("rod_r1", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.5429F, -9.9571F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rod_r2 = body.addOrReplaceChild("rod_r2", CubeListBuilder.create().texOffs(12, 4).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rod_r3 = body.addOrReplaceChild("rod_r3", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.5429F, -9.9571F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rod_r4 = body.addOrReplaceChild("rod_r4", CubeListBuilder.create().texOffs(12, 0).addBox(0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rod_r5 = body.addOrReplaceChild("rod_r5", CubeListBuilder.create().texOffs(4, 0).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -9.5F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
