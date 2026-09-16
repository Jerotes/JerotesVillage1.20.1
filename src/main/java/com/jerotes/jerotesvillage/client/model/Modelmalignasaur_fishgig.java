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

public class Modelmalignasaur_fishgig<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "malignasaur_fishgig"), "main");
	public final ModelPart body;

	public Modelmalignasaur_fishgig(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 4).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 5.75F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 4).addBox(1.5F, -15.0F, -0.5F, 1.0F, 4.75F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 4).addBox(-2.5F, -15.0F, -0.5F, 1.0F, 4.75F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition fish_r1 = body.addOrReplaceChild("fish_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-6.0F, -6.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -0.25F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rod_r1 = body.addOrReplaceChild("rod_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-3.5F, 0.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.015F)), PartPose.offsetAndRotation(0.0F, -12.75F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
