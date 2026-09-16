package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.WildernessBullEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelwilderness_bull_armor<T extends WildernessBullEntity> extends Modelwilderness_bull<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wilderness_bull_armor"), "main");

	public Modelwilderness_bull_armor(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 4).addBox(10.5F, -12.5F, -6.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(42, 4).addBox(-14.0F, -12.5F, -6.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -6.0F, -18.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_armor_r1 = head.addOrReplaceChild("head_armor_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, 8.0F, -7.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(-0.55F))
				.texOffs(0, 0).addBox(-7.0F, -6.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0F, -18.45F, -3.35F, 10.0F, 14.0F, 10.0F, new CubeDeformation(3.85F))
				.texOffs(204, 0).addBox(-2.0F, -23.05F, 10.15F, 4.0F, 15.0F, 4.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -4.5F, 3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_armor_r1 = body.addOrReplaceChild("saddle_armor_r1", CubeListBuilder.create().texOffs(92, 28).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(2.65F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.3F, -1.7017F, 0.0F, 0.0F));

		PartDefinition hind_rotation_armor_r1 = body.addOrReplaceChild("hind_rotation_armor_r1", CubeListBuilder.create().texOffs(148, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(2.5F)), PartPose.offsetAndRotation(0.0F, 8.0F, -1.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition front_rotation_armor_r1 = body.addOrReplaceChild("front_rotation_armor_r1", CubeListBuilder.create().texOffs(92, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(3.5F)), PartPose.offsetAndRotation(0.0F, -11.5F, 0.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.125F, 16.2041F, 5.4693F));

		PartDefinition tail_armor_r1 = tail.addOrReplaceChild("tail_armor_r1", CubeListBuilder.create().texOffs(56, 28).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 18.0F, 8.0F, new CubeDeformation(-1.5F)), PartPose.offsetAndRotation(0.0F, 1.2467F, -1.721F, -1.2217F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(64, 69).addBox(-4.0F, -3.5F, -4.5F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(-6.0F, -4.5F, 15.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(96, 69).addBox(-4.0F, -2.8F, -2.5F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 12.0F, -0.75F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(64, 69).mirror().addBox(-4.0F, -3.5F, -4.5F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(6.0F, -4.5F, 15.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(96, 69).mirror().addBox(-4.0F, -2.8F, -2.5F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offset(0.0F, 12.0F, -0.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 69).addBox(-0.75F, -1.75F, -4.25F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.3F)), PartPose.offset(-9.0F, -9.0F, -10.5F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(32, 69).addBox(-3.5F, -3.25F, -3.8F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(1.5F, 14.25F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 69).mirror().addBox(-7.25F, -1.75F, -4.25F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.3F)).mirror(false), PartPose.offset(9.0F, -9.0F, -10.5F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(32, 69).mirror().addBox(-4.5F, -3.25F, -3.8F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-1.5F, 14.25F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}
}
