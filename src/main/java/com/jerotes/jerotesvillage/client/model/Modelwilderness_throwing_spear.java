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

public class Modelwilderness_throwing_spear<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wilderness_throwing_spear"), "main");
	private final ModelPart body;

	public Modelwilderness_throwing_spear(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -17.7F, -0.5F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 23).addBox(-0.5F, 13.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition spearhead_4_r1 = body.addOrReplaceChild("spearhead_4_r1", CubeListBuilder.create().texOffs(8, 6).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, -4.1897F, 0.041F, -0.3927F, 0.0F, 0.0F));

		PartDefinition spearhead_3_r1 = body.addOrReplaceChild("spearhead_3_r1", CubeListBuilder.create().texOffs(4, 6).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -4.1897F, 0.041F, 0.3927F, 0.0F, 0.0F));

		PartDefinition spearhead_2_r1 = body.addOrReplaceChild("spearhead_2_r1", CubeListBuilder.create().texOffs(8, 0).addBox(-0.5F, -2.5F, -0.9062F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, -14.8377F, 0.4662F, -0.3927F, 0.0F, 0.0F));

		PartDefinition spearhead_1_r1 = body.addOrReplaceChild("spearhead_1_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, -2.5F, -0.1012F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -14.8066F, -0.4588F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}