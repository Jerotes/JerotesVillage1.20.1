package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.DracognathusEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modeldracognathus_armor<T extends DracognathusEntity> extends Modeldracognathus<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "dracognathus_armor"), "main");

	public Modeldracognathus_armor(ModelPart modelPart) {
		super(modelPart);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -3.5F, -5.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 8.0F, -12.5F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.49F, -4.8049F, -0.0565F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_ear_armor_r1 = right_horn.addOrReplaceChild("left_ear_armor_r1", CubeListBuilder.create().texOffs(19, 16).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.04F, 1.3049F, 1.0465F, -2.1817F, 0.0F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(2.49F, -4.8049F, -0.0565F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_ear_armor_r2 = left_horn.addOrReplaceChild("left_ear_armor_r2", CubeListBuilder.create().texOffs(19, 16).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.94F, 1.3049F, 1.0465F, -2.1817F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition mouth_armor_r1 = jaw.addOrReplaceChild("mouth_armor_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, -0.8F, -0.5F, 0.0F, 0.0F, 3.1416F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 6.2137F, -7.1104F));

		PartDefinition mane_armor_r1 = body.addOrReplaceChild("mane_armor_r1", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -1.2137F, 1.1104F, 1.4399F, 0.0F, 0.0F));

		PartDefinition body_armor_r1 = body.addOrReplaceChild("body_armor_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -5.0F, -11.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 4.8863F, 9.1104F, -0.1309F, 0.0F, 0.0F));

		PartDefinition neck_armor_r1 = body.addOrReplaceChild("neck_armor_r1", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -4.0F, -3.5F, 4.0F, 10.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.4019F, -0.4576F, 1.3963F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.7669F, 19.1009F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail_armor_r1 = tail.addOrReplaceChild("tail_armor_r1", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, -7.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 0.2315F, 2.2651F, -1.9199F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 10.0F, -4.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, -3.0F, -2.5F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.2159F, 1.7412F, -0.3F, 0.2618F, 0.0F, 0.2618F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(48, 21).addBox(-1.5F, -1.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.0F, 6.5F, 1.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 10.0F, -4.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, -3.0F, -2.5F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.2159F, 1.7412F, 0.5F, 0.1309F, 0.0F, -0.2618F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-1.5F, -1.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(1.0F, 6.5F, 1.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 13.0F, 8.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, -6.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.5F, 2.5F, -0.2F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5979F, 0.1059F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-1.5F, 0.5F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -2.0979F, 0.3941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 13.0F, 8.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 2.0F, -0.2F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(-0.5F, 4.5861F, 0.2141F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-1.5F, 0.5F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -2.0861F, 0.2859F, 0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}