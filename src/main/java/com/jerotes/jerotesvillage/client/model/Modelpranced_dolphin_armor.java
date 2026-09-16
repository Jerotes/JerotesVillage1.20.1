package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.PrancedDolphinEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpranced_dolphin_armor<T extends PrancedDolphinEntity> extends Modelpranced_dolphin<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "pranced_dolphin_armor"), "main");

	public Modelpranced_dolphin_armor(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.4F, -4.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(-0.85F)), PartPose.offset(0.0F, 24.0F, -3.0F));

		PartDefinition mane_armor_r1 = body.addOrReplaceChild("mane_armor_r1", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -7.5F, 1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition nose = body.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -1.2F, -1.1F, 4.0F, 5.0F, 5.0F, new CubeDeformation(2.1F)), PartPose.offset(0.0F, -4.0F, -3.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 14.0F));

		PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.0F));

		PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -1.0F, 1.0F, 0.6109F, 0.0F, -1.5708F));

		PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 0.0F, 1.0F, 0.6109F, 0.0F, 1.5708F));

		PartDefinition back_fin = body.addOrReplaceChild("back_fin", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
