package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;


public class Modelcelestial_coilvine_show<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "celestial_coilvine_show"), "main");
	private final ModelPart root;

	public Modelcelestial_coilvine_show(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition phantom = body.addOrReplaceChild("phantom", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition phantom_main = phantom.addOrReplaceChild("phantom_main", CubeListBuilder.create().texOffs(0, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(-1.5F))
				.texOffs(40, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -21.0445F, 0.0F));

		PartDefinition phantom_light = phantom_main.addOrReplaceChild("phantom_light", CubeListBuilder.create().texOffs(60, 251).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0923F, 11.0923F, -0.0877F, 0.0F, 0.0F));

		PartDefinition phantom_eye = phantom_main.addOrReplaceChild("phantom_eye", CubeListBuilder.create().texOffs(80, 201).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-2.5F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition phantom_eye_2 = phantom_eye.addOrReplaceChild("phantom_eye_2", CubeListBuilder.create().texOffs(80, 201).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-2.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition phantom_add = phantom.addOrReplaceChild("phantom_add", CubeListBuilder.create(), PartPose.offset(0.0F, -25.0F, 0.0F));

		PartDefinition phantom_add_1 = phantom_add.addOrReplaceChild("phantom_add_1", CubeListBuilder.create().texOffs(80, 221).addBox(-3.1667F, -3.0F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.1372F, 2.9353F, 0.0F, 0.0F, 0.0F, 0.0006F));

		PartDefinition phantom_add_1_r1 = phantom_add_1.addOrReplaceChild("phantom_add_1_r1", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.3333F, 20.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition phantom_add_1_r2 = phantom_add_1.addOrReplaceChild("phantom_add_1_r2", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3333F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition phantom_add_1_r3 = phantom_add_1.addOrReplaceChild("phantom_add_1_r3", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, -13.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition phantom_add_2 = phantom_add.addOrReplaceChild("phantom_add_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-13.9705F, -0.0647F, 0.0F, 0.0F, 0.0F, -0.0006F));

		PartDefinition phantom_add_1_r4 = phantom_add_2.addOrReplaceChild("phantom_add_1_r4", CubeListBuilder.create().texOffs(80, 221).addBox(-2.5F, -4.5F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition phantom_add_1_r5 = phantom_add_2.addOrReplaceChild("phantom_add_1_r5", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -10.0F, 0.0F, 3.1416F, 0.0F, 3.0543F));

		PartDefinition phantom_add_1_r6 = phantom_add_2.addOrReplaceChild("phantom_add_1_r6", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.5F, 23.5F, 0.0F, 0.0F, 3.1416F, -0.2618F));

		PartDefinition phantom_add_1_r7 = phantom_add_2.addOrReplaceChild("phantom_add_1_r7", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 14.5F, 0.0F, 0.0F, 3.1416F, 0.2618F));

		PartDefinition phantom_add_3 = phantom_add.addOrReplaceChild("phantom_add_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.0647F, 14.0295F, 0.0006F, 0.0F, 0.0F));

		PartDefinition phantom_add_1_r8 = phantom_add_3.addOrReplaceChild("phantom_add_1_r8", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.0F, 1.5F, -1.5708F, -1.4835F, 1.5708F));

		PartDefinition phantom_add_1_r9 = phantom_add_3.addOrReplaceChild("phantom_add_1_r9", CubeListBuilder.create().texOffs(80, 221).addBox(-2.5F, -4.5F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition phantom_add_1_r10 = phantom_add_3.addOrReplaceChild("phantom_add_1_r10", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 23.5F, 2.5F, 1.5708F, -1.8326F, -1.5708F));

		PartDefinition phantom_add_1_r11 = phantom_add_3.addOrReplaceChild("phantom_add_1_r11", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 14.5F, 2.5F, 1.5708F, -1.309F, -1.5708F));

		PartDefinition ribbon = phantom.addOrReplaceChild("ribbon", CubeListBuilder.create().texOffs(0, 265).addBox(-9.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.5F, 20.5F, 0.3043F, 0.0F, 0.0F));

		PartDefinition right_ribbon = ribbon.addOrReplaceChild("right_ribbon", CubeListBuilder.create().texOffs(0, 258).addBox(-18.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -0.8271F, 0.0F));

		PartDefinition right_ribbon2 = right_ribbon.addOrReplaceChild("right_ribbon2", CubeListBuilder.create().texOffs(36, 258).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, 0.0F, 0.0F, 0.1958F, -0.7203F, -0.2921F));

		PartDefinition right_ribbon3 = right_ribbon2.addOrReplaceChild("right_ribbon3", CubeListBuilder.create().texOffs(0, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.0F, -0.5255F, 0.0F));

		PartDefinition right_ribbon4 = right_ribbon3.addOrReplaceChild("right_ribbon4", CubeListBuilder.create().texOffs(20, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.449F, -1.3654F, -0.6874F));

		PartDefinition right_ribbon5 = right_ribbon4.addOrReplaceChild("right_ribbon5", CubeListBuilder.create().texOffs(0, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.8816F, -1.0782F, -0.9429F));

		PartDefinition right_ribbon6 = right_ribbon5.addOrReplaceChild("right_ribbon6", CubeListBuilder.create().texOffs(40, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.2333F, -0.3189F, -0.6485F));

		PartDefinition left_ribbon = ribbon.addOrReplaceChild("left_ribbon", CubeListBuilder.create().texOffs(0, 258).mirror().addBox(0.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 0.8271F, 0.0F));

		PartDefinition left_ribbon2 = left_ribbon.addOrReplaceChild("left_ribbon2", CubeListBuilder.create().texOffs(36, 258).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.0F, 0.0F, 0.0F, 0.1958F, 0.7203F, 0.2921F));

		PartDefinition left_ribbon3 = left_ribbon2.addOrReplaceChild("left_ribbon3", CubeListBuilder.create().texOffs(0, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.0F, 0.5255F, 0.0F));

		PartDefinition left_ribbon4 = left_ribbon3.addOrReplaceChild("left_ribbon4", CubeListBuilder.create().texOffs(20, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.449F, 1.3654F, 0.6874F));

		PartDefinition left_ribbon5 = left_ribbon4.addOrReplaceChild("left_ribbon5", CubeListBuilder.create().texOffs(0, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.8816F, 1.0782F, 0.9429F));

		PartDefinition left_ribbon6 = left_ribbon5.addOrReplaceChild("left_ribbon6", CubeListBuilder.create().texOffs(40, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.2333F, 0.3189F, 0.6485F));

		PartDefinition add = body.addOrReplaceChild("add", CubeListBuilder.create().texOffs(359, 391).addBox(-12.0F, -27.15F, -8.0F, 24.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.0223F, 0.0F));

		PartDefinition base_vine_add_r1 = add.addOrReplaceChild("base_vine_add_r1", CubeListBuilder.create().texOffs(128, 121).addBox(-7.5F, -4.217F, -34.6865F, 15.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -27.0F, 0.0F, 1.0036F, 3.1416F, 0.0F));

		PartDefinition base_vine_add_r2 = add.addOrReplaceChild("base_vine_add_r2", CubeListBuilder.create().texOffs(128, 121).addBox(-7.5F, -4.217F, -34.6865F, 15.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -27.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition base_vine_add_r3 = add.addOrReplaceChild("base_vine_add_r3", CubeListBuilder.create().texOffs(128, 121).addBox(-7.5F, -4.217F, -34.6865F, 15.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -27.0F, 0.0F, 0.0F, 1.5708F, -1.0036F));

		PartDefinition base_vine_add_r4 = add.addOrReplaceChild("base_vine_add_r4", CubeListBuilder.create().texOffs(128, 121).addBox(-7.5F, -4.217F, -34.6865F, 15.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -27.0F, 0.0F, 0.0F, -1.5708F, 1.0036F));

		PartDefinition base = body.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 121).addBox(-16.0F, -0.6857F, -16.0F, 32.0F, 1.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(64, 121).addBox(-16.0F, -0.7357F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(0, 72).addBox(-24.0F, 0.3143F, -24.0F, 48.0F, 1.0F, 48.0F, new CubeDeformation(0.01F))
				.texOffs(96, 72).addBox(-24.0F, 0.2643F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(0, 181).addBox(-5.0F, -7.7357F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.4143F, 0.0F));

		PartDefinition base_base = base.addOrReplaceChild("base_base", CubeListBuilder.create().texOffs(-72, 0).addBox(-36.0F, 0.05F, -36.0F, 72.0F, 0.0F, 72.0F, new CubeDeformation(0.0F))
				.texOffs(72, 0).addBox(-36.0F, 0.0F, -36.0F, 72.0F, 0.0F, 72.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.2643F, 0.0F));

		PartDefinition base_vine_add = base.addOrReplaceChild("base_vine_add", CubeListBuilder.create(), PartPose.offset(0.0F, -2.1357F, 0.0F));

		PartDefinition base_vine_add_r5 = base_vine_add.addOrReplaceChild("base_vine_add_r5", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 1.5708F, 3.0107F));

		PartDefinition base_vine_add_r6 = base_vine_add.addOrReplaceChild("base_vine_add_r6", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, -1.5708F, -3.0107F));

		PartDefinition base_vine_add_r7 = base_vine_add.addOrReplaceChild("base_vine_add_r7", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.1309F, 0.0F, -3.1416F));

		PartDefinition base_vine_add_r8 = base_vine_add.addOrReplaceChild("base_vine_add_r8", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, -3.0107F, 0.0F, 0.0F));

		PartDefinition base_vine_add_r9 = base_vine_add.addOrReplaceChild("base_vine_add_r9", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

		PartDefinition base_vine_add_r10 = base_vine_add.addOrReplaceChild("base_vine_add_r10", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

		PartDefinition base_vine_add_r11 = base_vine_add.addOrReplaceChild("base_vine_add_r11", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition base_vine_add_r12 = base_vine_add.addOrReplaceChild("base_vine_add_r12", CubeListBuilder.create().texOffs(22, 169).addBox(-24.5F, -0.3455F, -27.6444F, 49.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition base_flower = base.addOrReplaceChild("base_flower", CubeListBuilder.create().texOffs(-27, 154).addBox(-13.5F, 0.0F, -13.5F, 27.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.3357F, 0.0F));

		PartDefinition petal_1 = base_flower.addOrReplaceChild("petal_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.1202F, 0.0F, 0.0F, 0.0004F, 0.0F));

		PartDefinition petal_r1 = petal_1.addOrReplaceChild("petal_r1", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_r2 = petal_1.addOrReplaceChild("petal_r2", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		PartDefinition petal_r3 = petal_1.addOrReplaceChild("petal_r3", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_r4 = petal_1.addOrReplaceChild("petal_r4", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition petal_2 = base_flower.addOrReplaceChild("petal_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.1202F, 0.0F, 0.0F, -0.7858F, 0.0F));

		PartDefinition petal_r5 = petal_2.addOrReplaceChild("petal_r5", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_r6 = petal_2.addOrReplaceChild("petal_r6", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		PartDefinition petal_r7 = petal_2.addOrReplaceChild("petal_r7", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_r8 = petal_2.addOrReplaceChild("petal_r8", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}