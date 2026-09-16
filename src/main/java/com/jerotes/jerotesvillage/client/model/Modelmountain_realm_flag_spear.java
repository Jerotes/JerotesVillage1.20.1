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

public class Modelmountain_realm_flag_spear<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "mountain_realm_flag_spear"), "main");
	public final ModelPart body;

	public Modelmountain_realm_flag_spear(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 57.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-1.5F, -13.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.005F))
				.texOffs(38, 5).addBox(-1.5F, -11.25F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition head_r1 = body.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 59).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition banner = body.addOrReplaceChild("banner", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -9.0F, 0.0F, 5.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, -0.9163F, 0.0F));

		PartDefinition banner2 = banner.addOrReplaceChild("banner2", CubeListBuilder.create().texOffs(8, 18).addBox(0.0F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 0.0F, 0.0F, -1.789F, 0.0F));

		PartDefinition banner3 = banner2.addOrReplaceChild("banner3", CubeListBuilder.create().texOffs(8, 34).addBox(0.0F, -7.0F, 0.0F, 3.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition banner4 = banner3.addOrReplaceChild("banner4", CubeListBuilder.create().texOffs(8, 48).addBox(0.0F, -6.0F, 0.0F, 3.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition banner5 = banner4.addOrReplaceChild("banner5", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -5.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition banner6 = banner5.addOrReplaceChild("banner6", CubeListBuilder.create().texOffs(32, 10).addBox(0.0F, -4.0F, 0.0F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition banner7 = banner6.addOrReplaceChild("banner7", CubeListBuilder.create().texOffs(32, 18).addBox(0.0F, -3.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition banner8 = banner7.addOrReplaceChild("banner8", CubeListBuilder.create().texOffs(32, 24).addBox(0.0F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.5F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition banner9 = banner8.addOrReplaceChild("banner9", CubeListBuilder.create().texOffs(32, 29).addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 0.0F, 0.0F, -0.9163F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
