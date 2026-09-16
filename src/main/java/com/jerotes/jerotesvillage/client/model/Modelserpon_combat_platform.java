package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCombatPlatformEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelserpon_combat_platform<T extends SerponCombatPlatformEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_combat_platform"), "main");
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart propeller_1;
	private final ModelPart propeller_2;
	private final ModelPart propeller_3;
	private final ModelPart propeller_4;

	public Modelserpon_combat_platform(ModelPart root) {
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.propeller_1 = main.getChild("propeller_1");
		this.propeller_2 = main.getChild("propeller_2");
		this.propeller_3 = main.getChild("propeller_3");
		this.propeller_4 = main.getChild("propeller_4");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-40.0F, -40.0F, -5.6364F, 80.0F, 80.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 160).addBox(40.0F, -24.0F, -5.6364F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 160).addBox(-88.0F, -24.0F, -5.6364F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 160).addBox(-24.0F, -88.0F, -5.6364F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 160).addBox(-24.0F, 40.0F, -5.6364F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(112, 160).addBox(24.0F, 24.0F, -5.6364F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 0).addBox(24.0F, 24.0F, -14.1364F, 16.0F, 16.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(28.0F, 28.0F, 13.8636F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(28.0F, 28.0F, -18.1364F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 0).addBox(24.0F, -40.0F, -14.1364F, 16.0F, 16.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(28.0F, -36.0F, 13.8636F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(28.0F, -36.0F, -18.1364F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 0).addBox(-40.0F, 24.0F, -14.1364F, 16.0F, 16.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(-36.0F, 28.0F, 13.8636F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(-36.0F, 28.0F, -18.1364F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 0).addBox(-40.0F, -40.0F, -14.1364F, 16.0F, 16.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(-36.0F, -36.0F, 13.8636F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(176, 48).addBox(-36.0F, -36.0F, -18.1364F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 88).addBox(-32.0F, -32.0F, -11.6364F, 64.0F, 64.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.6364F, 0.0F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition side_r1 = body.addOrReplaceChild("side_r1", CubeListBuilder.create().texOffs(112, 160).addBox(-24.0F, -24.0F, -4.0F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-48.0F, 48.0F, -1.6364F, 0.0F, 0.0F, 1.5708F));

		PartDefinition side_r2 = body.addOrReplaceChild("side_r2", CubeListBuilder.create().texOffs(112, 160).addBox(-24.0F, -24.0F, -4.0F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-48.0F, -48.0F, -1.6364F, 0.0F, 0.0F, -3.1416F));

		PartDefinition side_r3 = body.addOrReplaceChild("side_r3", CubeListBuilder.create().texOffs(112, 160).addBox(-24.0F, -24.0F, -4.0F, 48.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(48.0F, -48.0F, -1.6364F, 0.0F, 0.0F, -1.5708F));

		PartDefinition propeller_1 = main.addOrReplaceChild("propeller_1", CubeListBuilder.create().texOffs(192, 48).addBox(-8.0F, 20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(192, 48).addBox(-8.0F, -20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -14.0F, 32.0F));

		PartDefinition propeller_2 = main.addOrReplaceChild("propeller_2", CubeListBuilder.create().texOffs(192, 48).addBox(-8.0F, 20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(192, 48).addBox(-8.0F, -20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -14.0F, 32.0F));

		PartDefinition propeller_3 = main.addOrReplaceChild("propeller_3", CubeListBuilder.create().texOffs(192, 48).addBox(-8.0F, 20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(192, 48).addBox(-8.0F, -20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -14.0F, -32.0F));

		PartDefinition propeller_4 = main.addOrReplaceChild("propeller_4", CubeListBuilder.create().texOffs(192, 48).addBox(-8.0F, 20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(192, 48).addBox(-8.0F, -20.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -14.0F, -32.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.main.x = Mth.cos(ageInTicks * 0.6662f) * 0.15f;
		this.main.y = Mth.cos(ageInTicks * 0.3331f) * 0.03f + 25f;
		this.main.z = Mth.cos(ageInTicks * 0.6662f * (2f/3f)) * 0.05f;
		this.propeller_1.yRot = limbSwing + ageInTicks;
		this.propeller_2.yRot = limbSwing + ageInTicks;
		this.propeller_3.yRot = limbSwing + ageInTicks;
		this.propeller_4.yRot = limbSwing + ageInTicks;
	}
}

