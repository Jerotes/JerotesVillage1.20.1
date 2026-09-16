package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelmountain_realm_armor extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "mountain_realm_armor"), "main");
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart left_arm;
	public final ModelPart right_arm;
	public final ModelPart left_leg;
	public final ModelPart right_leg;

	public Modelmountain_realm_armor(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F))
				.texOffs(64, 0).addBox(-1.5F, -11.3F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_armor_hair_r1 = head.addOrReplaceChild("head_armor_hair_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, 0, -1.5F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket = body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket_r1 = jacket.addOrReplaceChild("jacket_r1", CubeListBuilder.create().texOffs(64, 17).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.7F, -2.7F, -0.0175F, 0.0F, 0.0F));

		PartDefinition back_banner = jacket.addOrReplaceChild("back_banner", CubeListBuilder.create().texOffs(64, 49).addBox(-5.0F, -2.3333F, 3.8333F, 10.0F, 12.0F, 3.0F, new CubeDeformation(-1.0F))
				.texOffs(90, 60).addBox(-5.0F, -1.8333F, 6.8333F, 10.0F, 3.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.3333F, -2.3333F));

		PartDefinition back_banner_1 = back_banner.addOrReplaceChild("back_banner_1", CubeListBuilder.create().texOffs(120, 34).addBox(-1.0F, -25.0F, -0.625F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(68, 8).addBox(0.0F, -26.0F, 0.375F, 0.0F, 18.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(116, 18).addBox(-1.5F, -24.25F, -1.125F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.5F))
				.texOffs(116, 13).addBox(-1.5F, -25.0F, -1.125F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.245F)), PartPose.offsetAndRotation(1.0F, 4.6667F, 6.4583F, -0.2166F, 0.2932F, 0.1145F));

		PartDefinition back_banner_r1 = back_banner_1.addOrReplaceChild("back_banner_r1", CubeListBuilder.create().texOffs(112, 60).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, 0.375F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_2 = back_banner.addOrReplaceChild("back_banner_2", CubeListBuilder.create().texOffs(120, 34).addBox(-1.0F, -25.0F, -0.625F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(68, 8).addBox(0.0F, -26.0F, 0.375F, 0.0F, 18.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(116, 18).addBox(-1.5F, -24.25F, -1.125F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.5F))
				.texOffs(116, 13).addBox(-1.5F, -25.0F, -1.125F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.245F)), PartPose.offsetAndRotation(-1.0F, 4.6667F, 6.4583F, -0.2166F, -0.2932F, -0.1145F));

		PartDefinition back_banner_r2 = back_banner_2.addOrReplaceChild("back_banner_r2", CubeListBuilder.create().texOffs(112, 60).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, 0.375F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_3 = back_banner.addOrReplaceChild("back_banner_3", CubeListBuilder.create().texOffs(120, 34).addBox(-1.0F, -25.0F, -1.125F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(68, 8).addBox(0.0F, -26.0F, -0.125F, 0.0F, 18.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(116, 18).addBox(-1.5F, -24.25F, -1.625F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.5F))
				.texOffs(116, 13).addBox(-1.5F, -25.0F, -1.625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.245F)), PartPose.offsetAndRotation(2.0F, 4.6667F, 6.4583F, 0.0332F, 0.7746F, 0.4936F));

		PartDefinition back_banner_r3 = back_banner_3.addOrReplaceChild("back_banner_r3", CubeListBuilder.create().texOffs(112, 60).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -0.125F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition back_banner_4 = back_banner.addOrReplaceChild("back_banner_4", CubeListBuilder.create().texOffs(120, 34).addBox(-1.0F, -25.0F, -1.125F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(68, 8).addBox(0.0F, -26.0F, -0.125F, 0.0F, 18.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(116, 18).addBox(-1.5F, -24.25F, -1.625F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.5F))
				.texOffs(116, 13).addBox(-1.5F, -25.0F, -1.625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.245F)), PartPose.offsetAndRotation(-2.0F, 4.6667F, 6.4583F, 0.0332F, -0.7746F, -0.4936F));

		PartDefinition back_banner_r4 = back_banner_4.addOrReplaceChild("back_banner_r4", CubeListBuilder.create().texOffs(112, 60).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -0.125F, 1.5708F, -0.7854F, -1.5708F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.505F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition left_pants = left_leg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_pants = right_leg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.751F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	public Modelmountain_realm_armor getArmor(LivingEntity entity){
		return this;
	}
}