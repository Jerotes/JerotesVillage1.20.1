	package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.SliderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

	public class Modelslider<T extends SliderEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "slider"), "main");
		private final ModelPart body;
		private final ModelPart body2;
		private final ModelPart body3;
		private final ModelPart body4;
		private final ModelPart body5;
		private final ModelPart body6;
		private final ModelPart body7;

	public Modelslider(ModelPart root) {
		this.body = root.getChild("body");
		this.body2 = root.getChild("body2");
		this.body3 = root.getChild("body3");
		this.body4 = root.getChild("body4");
		this.body5 = root.getChild("body5");
		this.body6 = body3.getChild("body6");
		this.body7 = body3.getChild("body7");
	}

		public static LayerDefinition createBodyLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

			PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(44, 40).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

			PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(44, 23).addBox(-3.0F, -5.0F, -1.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

			PartDefinition body2 = partdefinition.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(36, 0).addBox(-3.0F, -4.75F, -7.25F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 15.75F, 0.25F));

			PartDefinition body_r3 = body2.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(48, 34).addBox(-3.0F, -2.0F, 1.5F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
					.texOffs(0, 13).addBox(-4.0F, 0.0F, -6.5F, 8.0F, 2.0F, 9.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.75F, -0.75F, 0.3491F, 0.0F, 0.0F));

			PartDefinition body3 = partdefinition.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 2.0F, 11.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 22.0F, 0.0F));

			PartDefinition body6 = body3.addOrReplaceChild("body6", CubeListBuilder.create().texOffs(0, 34).addBox(-4.0F, -1.0F, 3.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

			PartDefinition body7 = body3.addOrReplaceChild("body7", CubeListBuilder.create().texOffs(0, 40).addBox(-3.0F, -1.0F, 3.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 2.0F, 2.0F));

			PartDefinition body4 = partdefinition.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(50, 17).addBox(-3.0F, -3.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(0, 24).addBox(-3.0F, -3.0F, -8.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(-4.0F, 20.0F, 1.0F));

			PartDefinition body5 = partdefinition.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(50, 11).addBox(-1.0F, -1.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(20, 24).addBox(0.0F, -1.0F, -8.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(4.0F, 18.0F, 1.0F));

			return LayerDefinition.create(meshdefinition, 64, 64);
		}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body3.xScale = 0.85f;
		this.body3.zScale = 1f + 0.2f * Mth.triangleWave(limbSwing, 15f);
		this.body6.z = 1f * Mth.cos(limbSwing * 0.6662f) * limbSwingAmount;
		this.body7.z = 3f * Mth.triangleWave(limbSwing, 15f);
		float f6 = 0.1f * limbSwingAmount;
		this.body2.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * f6;
		this.body4.xRot = Mth.cos(limbSwing * 0.6662f) * f6;
		this.body5.xRot = Mth.cos(limbSwing * 0.6662f) * f6;
		int n = entity.getAttackTick();
		if (n > 7) {
			this.body4.xRot = -1f * Mth.triangleWave((float) n, 15f);
			this.body5.xRot = -1f * Mth.triangleWave((float) n, 15f);
		}
		else {
			this.body4.xRot = Mth.cos(limbSwing * 0.6662f) * f6;
			this.body5.xRot = Mth.cos(limbSwing * 0.6662f) * f6;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
