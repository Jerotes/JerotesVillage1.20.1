package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.GiantMonsterAnimation;
import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelgiant_monster_armor<T extends GiantMonsterEntity> extends Modelgiant_monster<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "giant_monster_armor"), "main");

	public Modelgiant_monster_armor(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.5F, -9.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(1.6F)), PartPose.offset(0.0F, -2.5F, -17.0F));

		PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.267F, -13.2054F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_horn = horns.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-9.0F, -4.4107F, 9.9634F));

		PartDefinition right_horn_armor_r1 = right_horn.addOrReplaceChild("right_horn_armor_r1", CubeListBuilder.create().texOffs(42, 5).addBox(-2.0F, -3.5F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(1.35F)), PartPose.offsetAndRotation(0.6F, 11.3296F, -1.2383F, 1.5708F, -0.6109F, -1.5708F));

		PartDefinition left_horn = horns.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(9.0F, -4.4107F, 9.9634F));

		PartDefinition left_horn_armor_r1 = left_horn.addOrReplaceChild("left_horn_armor_r1", CubeListBuilder.create().texOffs(42, 5).mirror().addBox(-2.0F, -3.5F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(1.35F)).mirror(false), PartPose.offsetAndRotation(-0.6F, 11.3296F, -1.2383F, 1.5708F, 0.6109F, 1.5708F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, -1.6F, -11.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(1.55F)), PartPose.offset(0.0F, 8.0F, 2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 4.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -16.0F));

		PartDefinition neck_armor_r1 = neck.addOrReplaceChild("neck_armor_r1", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0F, -9.0F, -7.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(2.5F)), PartPose.offsetAndRotation(0.0F, 0.75F, 7.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(204, 0).addBox(-2.0F, -30.3F, 7.65F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.25F, 4.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_armor_r1 = rotation.addOrReplaceChild("saddle_armor_r1", CubeListBuilder.create().texOffs(92, 28).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(3.0F)), PartPose.offsetAndRotation(0.0F, -7.75F, 0.8F, -1.5708F, 0.0F, 0.0F));

		PartDefinition hind_rotation_armor_r1 = rotation.addOrReplaceChild("hind_rotation_armor_r1", CubeListBuilder.create().texOffs(148, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(2.75F)), PartPose.offsetAndRotation(0.0F, -0.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition front_rotation_armor_r1 = rotation.addOrReplaceChild("front_rotation_armor_r1", CubeListBuilder.create().texOffs(92, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(3.5F)), PartPose.offsetAndRotation(0.0F, -15.25F, 0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition tail = rotation.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 8.25F, 6.25F));

		PartDefinition tail_armor_r1 = tail.addOrReplaceChild("tail_armor_r1", CubeListBuilder.create().texOffs(56, 28).addBox(-3.0F, -14.0F, -4.0F, 6.0F, 28.0F, 8.0F, new CubeDeformation(-1.75F)), PartPose.offsetAndRotation(0.0F, 5.2936F, -7.96F, -1.5708F, -0.48F, 1.5708F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(64, 69).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offset(-8.0F, -8.0F, 14.5F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(96, 69).addBox(-3.75F, -3.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.0F, 17.0F, -0.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(64, 69).mirror().addBox(-4.0F, -5.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(8.0F, -8.0F, 14.5F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(96, 69).mirror().addBox(-4.25F, -3.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(1.0F, 17.0F, -0.5F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 69).addBox(-5.0F, -4.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.75F)), PartPose.offset(-10.0F, -8.5F, -8.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(32, 69).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.7F)), PartPose.offset(-1.0F, 17.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 69).mirror().addBox(-3.0F, -4.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.75F)).mirror(false), PartPose.offset(10.0F, -8.5F, -8.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(32, 69).mirror().addBox(-4.0F, -4.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.7F)).mirror(false), PartPose.offset(1.0F, 17.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}
}
