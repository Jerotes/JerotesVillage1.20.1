package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.RusherEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelrusher_armor<T extends RusherEntity> extends Modelrusher<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rusher_armor"), "main");

	public Modelrusher_armor(ModelPart modelPart) {
		super(modelPart);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(19, 16).addBox(-4.55F, -2.0054F, 0.4324F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 16).addBox(2.55F, -2.0054F, 0.4324F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(3.1F, -1.5054F, -0.0576F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-3.1F, -1.5054F, -0.0576F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0054F, -13.4424F));

		PartDefinition right_bit_r1 = head.addOrReplaceChild("right_bit_r1", CubeListBuilder.create().texOffs(29, 5).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(29, 5).addBox(4.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.5F, 3.2446F, -6.8076F, 0.3054F, 0.0F, 0.0F));

		PartDefinition noseband_r1 = head.addOrReplaceChild("noseband_r1", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 2.2446F, -5.8076F, 0.3054F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = head.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 1.9946F, -5.0576F, 0.3054F, 0.0F, 0.0F));

		PartDefinition headpiece_r1 = head.addOrReplaceChild("headpiece_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.7446F, -1.4576F, 0.3054F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.4946F, -1.0576F, 0.3054F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 0).addBox(-5.1667F, -8.0F, -4.3333F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.1667F, 8.0F, 5.3333F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -5.0F, -11.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.1667F, -5.0F, -0.3333F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r1 = body.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -6.0F, -3.5F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.2167F, -14.0F, 3.1667F, -0.7854F, 0.0F, 0.0F));

		PartDefinition mane_r1 = body.addOrReplaceChild("mane_r1", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.1667F, -11.299F, 6.723F, -0.7854F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.1667F, 4.4226F, 2.3687F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, -7.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 6.5774F, -3.202F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-5.0F, 8.0F, 7.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5409F, 2.5641F, 1.1391F, -0.1745F, 0.0175F, 0.0873F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.5F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-1.9591F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0409F, 2.3141F, 1.1391F, 0.2094F, -0.0349F, -0.0087F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(5.0F, 8.0F, 7.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.5171F, 2.5413F, 1.2438F, -0.1745F, -0.0175F, -0.0873F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.5F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0171F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0171F, 2.2913F, 1.2438F, 0.2094F, 0.0349F, -0.0087F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-6.0F, 6.0F, -6.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(48, 21).addBox(1.0F, -11.0F, -10.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-4.0F, 7.5F, 9.25F, 0.1309F, -0.0113F, 0.0873F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(48, 21).addBox(1.0F, -11.0F, -10.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.0F, 10.75F, 8.5F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(6.0F, 6.0F, -6.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(1.0F, -11.0F, -10.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 7.5F, 9.25F, 0.1309F, 0.0113F, -0.0873F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(1.0F, -11.0F, -10.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 10.75F, 8.5F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}