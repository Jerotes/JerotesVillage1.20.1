package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class Modelbitter_cold_wind<T extends LivingEntity> extends EntityModel<T> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bitter_cold_wind"), "main");
	private final ModelPart top;
	private final ModelPart middle;
	private final ModelPart bottom;
	private final ModelPart eye;

	public Modelbitter_cold_wind(ModelPart root) {
		this.top = root.getChild("top");
		this.middle = root.getChild("middle");
		this.bottom = root.getChild("bottom");
		this.eye = root.getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -4.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(6, 6).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(105, 57).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(74, 28).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(78, 32).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(49, 71).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(1, 83).addBox(-2.5F, -3.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, 0.0F));

		PartDefinition eye = partdefinition.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(21, 123).addBox(-6.0F, -18.0F, -9.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 123).addBox(3.0F, -18.0F, -9.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		eye.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.eye.yRot = netHeadYaw * 0.004f;
		this.eye.xRot = headPitch * 0.0004f;
		float f6 = ageInTicks * 3.1415927f * -0.1f;
		this.top.x = Mth.cos(f6) * 1.0f * 0.6f;
		this.top.z = Mth.sin(f6) * 1.0f * 0.6f;
		this.middle.x = Mth.sin(f6) * 0.5f * 0.8f;
		this.middle.z = Mth.cos(f6) * 0.8f;
		this.bottom.x = Mth.cos(f6) * -0.25f * 1.0f;
		this.bottom.z = Mth.sin(f6) * -0.25f * 1.0f;
		this.top.yRot = -ageInTicks * 0.3f;
		this.middle.yRot = -ageInTicks * 0.25f;
		this.bottom.yRot = -ageInTicks * 0.2f;
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.top;
		}
		return this.middle;
	}

	@Override
	public ModelPart getHead() {
		return this.eye;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
	}
}