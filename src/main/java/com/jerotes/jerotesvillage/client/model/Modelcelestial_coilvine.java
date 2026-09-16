package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CelestialCoilvineAnimation;
import com.jerotes.jerotesvillage.client.animation.CelestialCoilvineClawAnimation;
import com.jerotes.jerotesvillage.client.animation.CelestialCoilvineEyeAnimation;
import com.jerotes.jerotesvillage.client.animation.CelestialCoilvineMouthAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class Modelcelestial_coilvine<T extends CelestialCoilvineEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "celestial_coilvine"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart phantom;
	private final ModelPart phantom_main;
	private final ModelPart phantom_light;
	private final ModelPart phantom_eye;
	private final ModelPart phantom_eye_2;
	private final ModelPart phantom_add;
	private final ModelPart phantom_add_1;
	private final ModelPart phantom_add_2;
	private final ModelPart phantom_add_3;
	private final ModelPart ribbon;
	private final ModelPart right_ribbon;
	private final ModelPart right_ribbon2;
	private final ModelPart right_ribbon3;
	private final ModelPart right_ribbon4;
	private final ModelPart right_ribbon5;
	private final ModelPart right_ribbon6;
	private final ModelPart left_ribbon;
	private final ModelPart left_ribbon2;
	private final ModelPart left_ribbon3;
	private final ModelPart left_ribbon4;
	private final ModelPart left_ribbon5;
	private final ModelPart left_ribbon6;
	private final ModelPart claw;
	private final ModelPart claw_main;
	private final ModelPart claw_stem_1;
	private final ModelPart claw_stem_2;
	private final ModelPart claw_stem_3;
	private final ModelPart claw_flower_base;
	private final ModelPart claw_flower;
	private final ModelPart claw_flower_top;
	private final ModelPart claw_flower_3_1;
	private final ModelPart claw_flower_3_2;
	private final ModelPart claw_flower_3_3;
	private final ModelPart claw_flower_2_1;
	private final ModelPart claw_flower_2_2;
	private final ModelPart claw_flower_2_3;
	private final ModelPart claw_flower_1_1;
	private final ModelPart claw_flower_1_2;
	private final ModelPart claw_flower_1_3;
	private final ModelPart claw_flower_bottom;
	private final ModelPart claw_flower_4_1;
	private final ModelPart claw_flower_4_2;
	private final ModelPart claw_flower_4_3;
	private final ModelPart claw_flower_5_1;
	private final ModelPart claw_flower_5_2;
	private final ModelPart claw_flower_5_3;
	private final ModelPart eye;
	private final ModelPart eye_body_up;
	private final ModelPart eye_main;
	private final ModelPart eye_main_add;
	private final ModelPart right_eye_main;
	private final ModelPart right_eye_main_2;
	private final ModelPart right_eye_main_3;
	private final ModelPart left_eye_main;
	private final ModelPart left_eye_main_2;
	private final ModelPart left_eye_main_3;
	private final ModelPart eye_body_down;
	private final ModelPart eye_eye;
	private final ModelPart mouth;
	private final ModelPart mouth_main;
	private final ModelPart mouth_main_right;
	private final ModelPart mouth_main_left;
	private final ModelPart mouth_stem_1;
	private final ModelPart mouth_stem_2;
	private final ModelPart mouth_stem_3;
	private final ModelPart mouth_flower_base;
	private final ModelPart mouth_flower;
	private final ModelPart mouth_flower_1_1;
	private final ModelPart mouth_flower_1_2;
	private final ModelPart mouth_flower_1_3;
	private final ModelPart mouth_flower_2_1;
	private final ModelPart mouth_flower_2_2;
	private final ModelPart mouth_flower_2_3;
	private final ModelPart mouth_flower_3_1;
	private final ModelPart mouth_flower_3_2;
	private final ModelPart mouth_flower_3_3;
	private final ModelPart mouth_flower_4_1;
	private final ModelPart mouth_flower_4_2;
	private final ModelPart mouth_flower_4_3;
	private final ModelPart add;
	private final ModelPart base;
	private final ModelPart base_base;
	private final ModelPart base_vine_add;
	private final ModelPart base_flower;
	private final ModelPart petal_1;
	private final ModelPart petal_2;

	public Modelcelestial_coilvine(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.phantom = this.body.getChild("phantom");
		this.phantom_main = this.phantom.getChild("phantom_main");
		this.phantom_light = this.phantom_main.getChild("phantom_light");
		this.phantom_eye = this.phantom_main.getChild("phantom_eye");
		this.phantom_eye_2 = this.phantom_eye.getChild("phantom_eye_2");
		this.phantom_add = this.phantom.getChild("phantom_add");
		this.phantom_add_1 = this.phantom_add.getChild("phantom_add_1");
		this.phantom_add_2 = this.phantom_add.getChild("phantom_add_2");
		this.phantom_add_3 = this.phantom_add.getChild("phantom_add_3");
		this.ribbon = this.phantom.getChild("ribbon");
		this.right_ribbon = this.ribbon.getChild("right_ribbon");
		this.right_ribbon2 = this.right_ribbon.getChild("right_ribbon2");
		this.right_ribbon3 = this.right_ribbon2.getChild("right_ribbon3");
		this.right_ribbon4 = this.right_ribbon3.getChild("right_ribbon4");
		this.right_ribbon5 = this.right_ribbon4.getChild("right_ribbon5");
		this.right_ribbon6 = this.right_ribbon5.getChild("right_ribbon6");
		this.left_ribbon = this.ribbon.getChild("left_ribbon");
		this.left_ribbon2 = this.left_ribbon.getChild("left_ribbon2");
		this.left_ribbon3 = this.left_ribbon2.getChild("left_ribbon3");
		this.left_ribbon4 = this.left_ribbon3.getChild("left_ribbon4");
		this.left_ribbon5 = this.left_ribbon4.getChild("left_ribbon5");
		this.left_ribbon6 = this.left_ribbon5.getChild("left_ribbon6");
		this.claw = this.body.getChild("claw");
		this.claw_main = this.claw.getChild("claw_main");
		this.claw_stem_1 = this.claw_main.getChild("claw_stem_1");
		this.claw_stem_2 = this.claw_stem_1.getChild("claw_stem_2");
		this.claw_stem_3 = this.claw_stem_2.getChild("claw_stem_3");
		this.claw_flower_base = this.claw_stem_3.getChild("claw_flower_base");
		this.claw_flower = this.claw_flower_base.getChild("claw_flower");
		this.claw_flower_top = this.claw_flower.getChild("claw_flower_top");
		this.claw_flower_3_1 = this.claw_flower_top.getChild("claw_flower_3_1");
		this.claw_flower_3_2 = this.claw_flower_3_1.getChild("claw_flower_3_2");
		this.claw_flower_3_3 = this.claw_flower_3_2.getChild("claw_flower_3_3");
		this.claw_flower_2_1 = this.claw_flower_top.getChild("claw_flower_2_1");
		this.claw_flower_2_2 = this.claw_flower_2_1.getChild("claw_flower_2_2");
		this.claw_flower_2_3 = this.claw_flower_2_2.getChild("claw_flower_2_3");
		this.claw_flower_1_1 = this.claw_flower_top.getChild("claw_flower_1_1");
		this.claw_flower_1_2 = this.claw_flower_1_1.getChild("claw_flower_1_2");
		this.claw_flower_1_3 = this.claw_flower_1_2.getChild("claw_flower_1_3");
		this.claw_flower_bottom = this.claw_flower.getChild("claw_flower_bottom");
		this.claw_flower_4_1 = this.claw_flower_bottom.getChild("claw_flower_4_1");
		this.claw_flower_4_2 = this.claw_flower_4_1.getChild("claw_flower_4_2");
		this.claw_flower_4_3 = this.claw_flower_4_2.getChild("claw_flower_4_3");
		this.claw_flower_5_1 = this.claw_flower_bottom.getChild("claw_flower_5_1");
		this.claw_flower_5_2 = this.claw_flower_5_1.getChild("claw_flower_5_2");
		this.claw_flower_5_3 = this.claw_flower_5_2.getChild("claw_flower_5_3");
		this.eye = this.body.getChild("eye");
		this.eye_body_up = this.eye.getChild("eye_body_up");
		this.eye_main = this.eye_body_up.getChild("eye_main");
		this.eye_main_add = this.eye_main.getChild("eye_main_add");
		this.right_eye_main = this.eye_main.getChild("right_eye_main");
		this.right_eye_main_2 = this.right_eye_main.getChild("right_eye_main_2");
		this.right_eye_main_3 = this.right_eye_main_2.getChild("right_eye_main_3");
		this.left_eye_main = this.eye_main.getChild("left_eye_main");
		this.left_eye_main_2 = this.left_eye_main.getChild("left_eye_main_2");
		this.left_eye_main_3 = this.left_eye_main_2.getChild("left_eye_main_3");
		this.eye_body_down = this.eye.getChild("eye_body_down");
		this.eye_eye = this.eye_body_down.getChild("eye_eye");
		this.mouth = this.body.getChild("mouth");
		this.mouth_main = this.mouth.getChild("mouth_main");
		this.mouth_main_right = this.mouth_main.getChild("mouth_main_right");
		this.mouth_main_left = this.mouth_main.getChild("mouth_main_left");
		this.mouth_stem_1 = this.mouth_main.getChild("mouth_stem_1");
		this.mouth_stem_2 = this.mouth_stem_1.getChild("mouth_stem_2");
		this.mouth_stem_3 = this.mouth_stem_2.getChild("mouth_stem_3");
		this.mouth_flower_base = this.mouth_stem_3.getChild("mouth_flower_base");
		this.mouth_flower = this.mouth_flower_base.getChild("mouth_flower");
		this.mouth_flower_1_1 = this.mouth_flower.getChild("mouth_flower_1_1");
		this.mouth_flower_1_2 = this.mouth_flower_1_1.getChild("mouth_flower_1_2");
		this.mouth_flower_1_3 = this.mouth_flower_1_2.getChild("mouth_flower_1_3");
		this.mouth_flower_2_1 = this.mouth_flower.getChild("mouth_flower_2_1");
		this.mouth_flower_2_2 = this.mouth_flower_2_1.getChild("mouth_flower_2_2");
		this.mouth_flower_2_3 = this.mouth_flower_2_2.getChild("mouth_flower_2_3");
		this.mouth_flower_3_1 = this.mouth_flower.getChild("mouth_flower_3_1");
		this.mouth_flower_3_2 = this.mouth_flower_3_1.getChild("mouth_flower_3_2");
		this.mouth_flower_3_3 = this.mouth_flower_3_2.getChild("mouth_flower_3_3");
		this.mouth_flower_4_1 = this.mouth_flower.getChild("mouth_flower_4_1");
		this.mouth_flower_4_2 = this.mouth_flower_4_1.getChild("mouth_flower_4_2");
		this.mouth_flower_4_3 = this.mouth_flower_4_2.getChild("mouth_flower_4_3");
		this.add = this.body.getChild("add");
		this.base = this.body.getChild("base");
		this.base_base = this.base.getChild("base_base");
		this.base_vine_add = this.base.getChild("base_vine_add");
		this.base_flower = this.base.getChild("base_flower");
		this.petal_1 = this.base_flower.getChild("petal_1");
		this.petal_2 = this.base_flower.getChild("petal_2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition phantom = body.addOrReplaceChild("phantom", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition phantom_main = phantom.addOrReplaceChild("phantom_main", CubeListBuilder.create().texOffs(0, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(-1.5F))
				.texOffs(40, 201).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 40.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -21.0F, 0.0F));

		PartDefinition phantom_light = phantom_main.addOrReplaceChild("phantom_light", CubeListBuilder.create().texOffs(60, 251).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, 10.0F));

		PartDefinition phantom_eye = phantom_main.addOrReplaceChild("phantom_eye", CubeListBuilder.create().texOffs(80, 201).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-2.5F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition phantom_eye_2 = phantom_eye.addOrReplaceChild("phantom_eye_2", CubeListBuilder.create().texOffs(80, 201).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-2.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition phantom_add = phantom.addOrReplaceChild("phantom_add", CubeListBuilder.create(), PartPose.offset(0.0F, -25.0F, 0.0F));

		PartDefinition phantom_add_1 = phantom_add.addOrReplaceChild("phantom_add_1", CubeListBuilder.create().texOffs(80, 221).addBox(-3.1667F, -3.0F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(14.1667F, 3.0F, 0.0F));

		PartDefinition phantom_add_1_r1 = phantom_add_1.addOrReplaceChild("phantom_add_1_r1", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.3333F, 20.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition phantom_add_1_r2 = phantom_add_1.addOrReplaceChild("phantom_add_1_r2", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3333F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition phantom_add_1_r3 = phantom_add_1.addOrReplaceChild("phantom_add_1_r3", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, -13.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition phantom_add_2 = phantom_add.addOrReplaceChild("phantom_add_2", CubeListBuilder.create(), PartPose.offset(-14.0F, 0.0F, 0.0F));

		PartDefinition phantom_add_1_r4 = phantom_add_2.addOrReplaceChild("phantom_add_1_r4", CubeListBuilder.create().texOffs(80, 221).addBox(-2.5F, -4.5F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition phantom_add_1_r5 = phantom_add_2.addOrReplaceChild("phantom_add_1_r5", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -10.0F, 0.0F, 3.1416F, 0.0F, 3.0543F));

		PartDefinition phantom_add_1_r6 = phantom_add_2.addOrReplaceChild("phantom_add_1_r6", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.5F, 23.5F, 0.0F, 0.0F, 3.1416F, -0.2618F));

		PartDefinition phantom_add_1_r7 = phantom_add_2.addOrReplaceChild("phantom_add_1_r7", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 14.5F, 0.0F, 0.0F, 3.1416F, 0.2618F));

		PartDefinition phantom_add_3 = phantom_add.addOrReplaceChild("phantom_add_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 14.0F));

		PartDefinition phantom_add_1_r8 = phantom_add_3.addOrReplaceChild("phantom_add_1_r8", CubeListBuilder.create().texOffs(132, 221).addBox(-1.5F, -10.0F, -4.0F, 3.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.0F, 1.5F, -1.5708F, -1.4835F, 1.5708F));

		PartDefinition phantom_add_1_r9 = phantom_add_3.addOrReplaceChild("phantom_add_1_r9", CubeListBuilder.create().texOffs(80, 221).addBox(-2.5F, -4.5F, -5.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition phantom_add_1_r10 = phantom_add_3.addOrReplaceChild("phantom_add_1_r10", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 23.5F, 2.5F, 1.5708F, -1.8326F, -1.5708F));

		PartDefinition phantom_add_1_r11 = phantom_add_3.addOrReplaceChild("phantom_add_1_r11", CubeListBuilder.create().texOffs(110, 221).addBox(-1.5F, -6.5F, -4.0F, 3.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 14.5F, 2.5F, 1.5708F, -1.309F, -1.5708F));

		PartDefinition ribbon = phantom.addOrReplaceChild("ribbon", CubeListBuilder.create().texOffs(0, 265).addBox(-9.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.5F, 20.5F));

		PartDefinition right_ribbon = ribbon.addOrReplaceChild("right_ribbon", CubeListBuilder.create().texOffs(0, 258).addBox(-18.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

		PartDefinition right_ribbon2 = right_ribbon.addOrReplaceChild("right_ribbon2", CubeListBuilder.create().texOffs(36, 258).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.0F, 0.0F, 0.0F));

		PartDefinition right_ribbon3 = right_ribbon2.addOrReplaceChild("right_ribbon3", CubeListBuilder.create().texOffs(0, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		PartDefinition right_ribbon4 = right_ribbon3.addOrReplaceChild("right_ribbon4", CubeListBuilder.create().texOffs(20, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		PartDefinition right_ribbon5 = right_ribbon4.addOrReplaceChild("right_ribbon5", CubeListBuilder.create().texOffs(0, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		PartDefinition right_ribbon6 = right_ribbon5.addOrReplaceChild("right_ribbon6", CubeListBuilder.create().texOffs(40, 251).addBox(-10.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon = ribbon.addOrReplaceChild("left_ribbon", CubeListBuilder.create().texOffs(0, 258).mirror().addBox(0.0F, -3.5F, 0.0F, 18.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon2 = left_ribbon.addOrReplaceChild("left_ribbon2", CubeListBuilder.create().texOffs(36, 258).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(18.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon3 = left_ribbon2.addOrReplaceChild("left_ribbon3", CubeListBuilder.create().texOffs(0, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon4 = left_ribbon3.addOrReplaceChild("left_ribbon4", CubeListBuilder.create().texOffs(20, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon5 = left_ribbon4.addOrReplaceChild("left_ribbon5", CubeListBuilder.create().texOffs(0, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition left_ribbon6 = left_ribbon5.addOrReplaceChild("left_ribbon6", CubeListBuilder.create().texOffs(40, 251).mirror().addBox(0.0F, -3.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition claw = body.addOrReplaceChild("claw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition claw_main = claw.addOrReplaceChild("claw_main", CubeListBuilder.create().texOffs(0, 440).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 0.0F));

		PartDefinition claw_main_r1 = claw_main.addOrReplaceChild("claw_main_r1", CubeListBuilder.create().texOffs(-24, 462).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.5708F, -1.0036F, -1.5708F));

		PartDefinition claw_main_r2 = claw_main.addOrReplaceChild("claw_main_r2", CubeListBuilder.create().texOffs(-24, 462).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.5708F, 1.0908F, -1.5708F));

		PartDefinition claw_main_r3 = claw_main.addOrReplaceChild("claw_main_r3", CubeListBuilder.create().texOffs(-24, 462).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -2.6616F));

		PartDefinition claw_main_r4 = claw_main.addOrReplaceChild("claw_main_r4", CubeListBuilder.create().texOffs(-24, 462).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition claw_stem_1 = claw_main.addOrReplaceChild("claw_stem_1", CubeListBuilder.create().texOffs(40, 444).addBox(-3.0F, -24.25F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.5F))
				.texOffs(64, 444).addBox(-3.0F, -24.25F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(1.25F)), PartPose.offset(0.0F, -0.75F, 0.0F));

		PartDefinition claw_stem_2 = claw_stem_1.addOrReplaceChild("claw_stem_2", CubeListBuilder.create().texOffs(40, 444).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(64, 444).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(1.0F))
				.texOffs(0, 486).addBox(-13.0F, -20.0F, 7.0F, 26.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.5F, 0.0F));

		PartDefinition claw_stem_3 = claw_stem_2.addOrReplaceChild("claw_stem_3", CubeListBuilder.create().texOffs(40, 444).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 444).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, -19.75F, 0.0F));

		PartDefinition claw_flower_base = claw_stem_3.addOrReplaceChild("claw_flower_base", CubeListBuilder.create(), PartPose.offset(0.0F, -20.5F, 0.0F));

		PartDefinition claw_flower = claw_flower_base.addOrReplaceChild("claw_flower", CubeListBuilder.create().texOffs(52, 474).addBox(-7.0F, 0.5F, -7.0F, 14.0F, 20.0F, 14.0F, new CubeDeformation(0.25F))
				.texOffs(52, 474).addBox(-7.0F, 0.5F, -7.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.75F))
				.texOffs(108, 488).addBox(-7.0F, -6.5F, -7.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition claw_flower_top = claw_flower.addOrReplaceChild("claw_flower_top", CubeListBuilder.create().texOffs(94, 459).addBox(-16.0F, 0.0F, -2.0F, 32.0F, 7.0F, 20.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, -3.75F, 0.0F));

		PartDefinition claw_flower_top_r1 = claw_flower_top.addOrReplaceChild("claw_flower_top_r1", CubeListBuilder.create().texOffs(144, 486).addBox(-13.5F, 0.0F, -13.0F, 27.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.5F, 7.0F, 13.0F, 0.0F, 0.2182F, -0.3054F));

		PartDefinition claw_flower_top_r2 = claw_flower_top.addOrReplaceChild("claw_flower_top_r2", CubeListBuilder.create().texOffs(144, 486).mirror().addBox(-13.5F, 0.0F, -13.0F, 27.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.5F, 7.0F, 13.0F, 0.0F, -0.2182F, 0.3054F));

		PartDefinition claw_flower_3_1 = claw_flower_top.addOrReplaceChild("claw_flower_3_1", CubeListBuilder.create().texOffs(252, 470).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(280, 492).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(312, 472).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(13.0F, 2.75F, 15.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition claw_flower_3_1_r1 = claw_flower_3_1.addOrReplaceChild("claw_flower_3_1_r1", CubeListBuilder.create().texOffs(232, 492).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition claw_flower_3_2 = claw_flower_3_1.addOrReplaceChild("claw_flower_3_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition claw_flower_3_2_r1 = claw_flower_3_2.addOrReplaceChild("claw_flower_3_2_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition claw_flower_3_2_r2 = claw_flower_3_2.addOrReplaceChild("claw_flower_3_2_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition claw_flower_3_2_r3 = claw_flower_3_2.addOrReplaceChild("claw_flower_3_2_r3", CubeListBuilder.create().texOffs(400, 472).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition claw_flower_3_2_r4 = claw_flower_3_2.addOrReplaceChild("claw_flower_3_2_r4", CubeListBuilder.create().texOffs(344, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_3_3 = claw_flower_3_2.addOrReplaceChild("claw_flower_3_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition claw_flower_3_3_r1 = claw_flower_3_3.addOrReplaceChild("claw_flower_3_3_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition claw_flower_3_3_r2 = claw_flower_3_3.addOrReplaceChild("claw_flower_3_3_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition claw_flower_3_3_r3 = claw_flower_3_3.addOrReplaceChild("claw_flower_3_3_r3", CubeListBuilder.create().texOffs(428, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_2_1 = claw_flower_top.addOrReplaceChild("claw_flower_2_1", CubeListBuilder.create().texOffs(252, 470).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(280, 492).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(312, 472).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-13.0F, 2.75F, 15.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition claw_flower_2_1_r1 = claw_flower_2_1.addOrReplaceChild("claw_flower_2_1_r1", CubeListBuilder.create().texOffs(232, 492).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition claw_flower_2_2 = claw_flower_2_1.addOrReplaceChild("claw_flower_2_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition claw_flower_2_2_r1 = claw_flower_2_2.addOrReplaceChild("claw_flower_2_2_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition claw_flower_2_2_r2 = claw_flower_2_2.addOrReplaceChild("claw_flower_2_2_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition claw_flower_2_2_r3 = claw_flower_2_2.addOrReplaceChild("claw_flower_2_2_r3", CubeListBuilder.create().texOffs(400, 472).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition claw_flower_2_2_r4 = claw_flower_2_2.addOrReplaceChild("claw_flower_2_2_r4", CubeListBuilder.create().texOffs(344, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_2_3 = claw_flower_2_2.addOrReplaceChild("claw_flower_2_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition claw_flower_2_3_r1 = claw_flower_2_3.addOrReplaceChild("claw_flower_2_3_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition claw_flower_2_3_r2 = claw_flower_2_3.addOrReplaceChild("claw_flower_2_3_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition claw_flower_2_3_r3 = claw_flower_2_3.addOrReplaceChild("claw_flower_2_3_r3", CubeListBuilder.create().texOffs(428, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_1_1 = claw_flower_top.addOrReplaceChild("claw_flower_1_1", CubeListBuilder.create().texOffs(252, 470).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(280, 492).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(312, 472).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.75F, 18.0F));

		PartDefinition claw_flower_1_1_r1 = claw_flower_1_1.addOrReplaceChild("claw_flower_1_1_r1", CubeListBuilder.create().texOffs(232, 492).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition claw_flower_1_2 = claw_flower_1_1.addOrReplaceChild("claw_flower_1_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition claw_flower_1_2_r1 = claw_flower_1_2.addOrReplaceChild("claw_flower_1_2_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition claw_flower_1_2_r2 = claw_flower_1_2.addOrReplaceChild("claw_flower_1_2_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition claw_flower_1_2_r3 = claw_flower_1_2.addOrReplaceChild("claw_flower_1_2_r3", CubeListBuilder.create().texOffs(400, 472).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition claw_flower_1_2_r4 = claw_flower_1_2.addOrReplaceChild("claw_flower_1_2_r4", CubeListBuilder.create().texOffs(344, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_1_3 = claw_flower_1_2.addOrReplaceChild("claw_flower_1_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition claw_flower_1_3_r1 = claw_flower_1_3.addOrReplaceChild("claw_flower_1_3_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition claw_flower_1_3_r2 = claw_flower_1_3.addOrReplaceChild("claw_flower_1_3_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition claw_flower_1_3_r3 = claw_flower_1_3.addOrReplaceChild("claw_flower_1_3_r3", CubeListBuilder.create().texOffs(428, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_bottom = claw_flower.addOrReplaceChild("claw_flower_bottom", CubeListBuilder.create().texOffs(178, 457).addBox(-12.0F, 0.0F, -13.0F, 24.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.75F, 0.0F));

		PartDefinition claw_flower_bottom_r1 = claw_flower_bottom.addOrReplaceChild("claw_flower_bottom_r1", CubeListBuilder.create().texOffs(99, 444).addBox(-9.5F, 0.0F, -5.5F, 19.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.5F, 7.0F, -11.5F, 0.0F, -0.3054F, -0.3054F));

		PartDefinition claw_flower_bottom_r2 = claw_flower_bottom.addOrReplaceChild("claw_flower_bottom_r2", CubeListBuilder.create().texOffs(99, 444).mirror().addBox(-9.5F, 0.0F, -5.5F, 19.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.5F, 7.0F, -11.5F, 0.0F, 0.3054F, 0.3054F));

		PartDefinition claw_flower_4_1 = claw_flower_bottom.addOrReplaceChild("claw_flower_4_1", CubeListBuilder.create().texOffs(252, 470).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(280, 492).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(312, 472).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(10.0F, 2.75F, -13.0F, 0.0F, 2.7925F, 0.0F));

		PartDefinition claw_flower_4_1_r1 = claw_flower_4_1.addOrReplaceChild("claw_flower_4_1_r1", CubeListBuilder.create().texOffs(232, 492).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition claw_flower_4_2 = claw_flower_4_1.addOrReplaceChild("claw_flower_4_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition claw_flower_4_2_r1 = claw_flower_4_2.addOrReplaceChild("claw_flower_4_2_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition claw_flower_4_2_r2 = claw_flower_4_2.addOrReplaceChild("claw_flower_4_2_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition claw_flower_4_2_r3 = claw_flower_4_2.addOrReplaceChild("claw_flower_4_2_r3", CubeListBuilder.create().texOffs(400, 472).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition claw_flower_4_2_r4 = claw_flower_4_2.addOrReplaceChild("claw_flower_4_2_r4", CubeListBuilder.create().texOffs(344, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_4_3 = claw_flower_4_2.addOrReplaceChild("claw_flower_4_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition claw_flower_4_3_r1 = claw_flower_4_3.addOrReplaceChild("claw_flower_4_3_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition claw_flower_4_3_r2 = claw_flower_4_3.addOrReplaceChild("claw_flower_4_3_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition claw_flower_4_3_r3 = claw_flower_4_3.addOrReplaceChild("claw_flower_4_3_r3", CubeListBuilder.create().texOffs(428, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_5_1 = claw_flower_bottom.addOrReplaceChild("claw_flower_5_1", CubeListBuilder.create().texOffs(252, 470).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(280, 492).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(312, 472).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.0F, 2.75F, -13.0F, 0.0F, -2.7925F, 0.0F));

		PartDefinition claw_flower_5_1_r1 = claw_flower_5_1.addOrReplaceChild("claw_flower_5_1_r1", CubeListBuilder.create().texOffs(232, 492).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition claw_flower_5_2 = claw_flower_5_1.addOrReplaceChild("claw_flower_5_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition claw_flower_5_2_r1 = claw_flower_5_2.addOrReplaceChild("claw_flower_5_2_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition claw_flower_5_2_r2 = claw_flower_5_2.addOrReplaceChild("claw_flower_5_2_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition claw_flower_5_2_r3 = claw_flower_5_2.addOrReplaceChild("claw_flower_5_2_r3", CubeListBuilder.create().texOffs(400, 472).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition claw_flower_5_2_r4 = claw_flower_5_2.addOrReplaceChild("claw_flower_5_2_r4", CubeListBuilder.create().texOffs(344, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition claw_flower_5_3 = claw_flower_5_2.addOrReplaceChild("claw_flower_5_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition claw_flower_5_3_r1 = claw_flower_5_3.addOrReplaceChild("claw_flower_5_3_r1", CubeListBuilder.create().texOffs(372, 472).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition claw_flower_5_3_r2 = claw_flower_5_3.addOrReplaceChild("claw_flower_5_3_r2", CubeListBuilder.create().texOffs(372, 472).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition claw_flower_5_3_r3 = claw_flower_5_3.addOrReplaceChild("claw_flower_5_3_r3", CubeListBuilder.create().texOffs(428, 472).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition eye = body.addOrReplaceChild("eye", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition eye_body_up = eye.addOrReplaceChild("eye_body_up", CubeListBuilder.create(), PartPose.offset(0.0F, -48.15F, 0.0F));

		PartDefinition eye_main_r1 = eye_body_up.addOrReplaceChild("eye_main_r1", CubeListBuilder.create().texOffs(286, 388).addBox(-17.5F, -3.5F, -9.5F, 35.0F, 7.0F, 19.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-25.5F, -5.35F, 0.0F, -0.219F, -0.3286F, -0.269F));

		PartDefinition eye_main_r2 = eye_body_up.addOrReplaceChild("eye_main_r2", CubeListBuilder.create().texOffs(286, 388).mirror().addBox(-17.5F, -3.5F, -9.5F, 35.0F, 7.0F, 19.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(25.5F, -5.35F, 0.0F, -0.219F, 0.3286F, 0.269F));

		PartDefinition eye_main_r3 = eye_body_up.addOrReplaceChild("eye_main_r3", CubeListBuilder.create().texOffs(286, 414).mirror().addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(19.5F, 13.15F, 0.0F, 0.0285F, 0.1278F, -0.7399F));

		PartDefinition eye_main_r4 = eye_body_up.addOrReplaceChild("eye_main_r4", CubeListBuilder.create().texOffs(286, 414).addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-19.5F, 13.15F, 0.0F, 0.0285F, -0.1278F, 0.7399F));

		PartDefinition eye_main = eye_body_up.addOrReplaceChild("eye_main", CubeListBuilder.create().texOffs(0, 380).addBox(-15.0F, -15.0F, -15.0F, 30.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition eye_main_add = eye_main.addOrReplaceChild("eye_main_add", CubeListBuilder.create().texOffs(174, 380).addBox(-15.0F, -15.0F, -1.0F, 30.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -15.5F));

		PartDefinition right_eye_main = eye_main.addOrReplaceChild("right_eye_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 15.0F));

		PartDefinition right_eye_main_2 = right_eye_main.addOrReplaceChild("right_eye_main_2", CubeListBuilder.create().texOffs(128, 380).mirror().addBox(0.0F, -15.0F, -30.0F, 8.0F, 30.0F, 30.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-15.0F, 0.0F, 0.0F));

		PartDefinition right_eye_main_3 = right_eye_main_2.addOrReplaceChild("right_eye_main_3", CubeListBuilder.create().texOffs(204, 380).mirror().addBox(-0.75F, -15.0F, -30.0F, 11.0F, 30.0F, 30.0F, new CubeDeformation(0.205F)).mirror(false), PartPose.offset(9.0F, 0.0F, 0.0F));

		PartDefinition left_eye_main = eye_main.addOrReplaceChild("left_eye_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 15.0F));

		PartDefinition left_eye_main_2 = left_eye_main.addOrReplaceChild("left_eye_main_2", CubeListBuilder.create().texOffs(128, 380).addBox(-8.0F, -15.0F, -30.0F, 8.0F, 30.0F, 30.0F, new CubeDeformation(0.25F)), PartPose.offset(15.0F, 0.0F, 0.0F));

		PartDefinition left_eye_main_3 = left_eye_main_2.addOrReplaceChild("left_eye_main_3", CubeListBuilder.create().texOffs(204, 380).addBox(-11.25F, -15.0F, -30.0F, 11.0F, 30.0F, 30.0F, new CubeDeformation(0.2F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

		PartDefinition eye_body_down = eye.addOrReplaceChild("eye_body_down", CubeListBuilder.create().texOffs(359, 391).addBox(-12.0F, 0.0417F, -8.0F, 24.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.1917F, 0.0F));

		PartDefinition eye_main_r5 = eye_body_down.addOrReplaceChild("eye_main_r5", CubeListBuilder.create().texOffs(286, 414).mirror().addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.5F, 7.1917F, 0.0F, 0.1163F, 0.0603F, -1.0873F));

		PartDefinition eye_main_r6 = eye_body_down.addOrReplaceChild("eye_main_r6", CubeListBuilder.create().texOffs(286, 414).mirror().addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.5F, -2.8083F, 0.0F, 0.0887F, -0.0964F, 0.2139F));

		PartDefinition eye_main_r7 = eye_body_down.addOrReplaceChild("eye_main_r7", CubeListBuilder.create().texOffs(286, 414).addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, 7.1917F, 0.0F, 0.1163F, -0.0603F, 1.0873F));

		PartDefinition eye_main_r8 = eye_body_down.addOrReplaceChild("eye_main_r8", CubeListBuilder.create().texOffs(286, 414).addBox(-23.5F, -4.0F, -9.0F, 47.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.5F, -2.8083F, 0.0F, 0.0887F, 0.0964F, -0.2139F));

		PartDefinition eye_eye = eye_body_down.addOrReplaceChild("eye_eye", CubeListBuilder.create().texOffs(256, 387).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, -8.8083F, 0.0F));

		PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 0.0F));

		PartDefinition mouth_main = mouth.addOrReplaceChild("mouth_main", CubeListBuilder.create().texOffs(0, 272).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition mouth_main_r1 = mouth_main.addOrReplaceChild("mouth_main_r1", CubeListBuilder.create().texOffs(-24, 294).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.5708F, -1.0036F, -1.5708F));

		PartDefinition mouth_main_r2 = mouth_main.addOrReplaceChild("mouth_main_r2", CubeListBuilder.create().texOffs(-24, 294).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.5708F, 1.0908F, -1.5708F));

		PartDefinition mouth_main_r3 = mouth_main.addOrReplaceChild("mouth_main_r3", CubeListBuilder.create().texOffs(-24, 294).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -2.6616F));

		PartDefinition mouth_main_r4 = mouth_main.addOrReplaceChild("mouth_main_r4", CubeListBuilder.create().texOffs(-24, 294).addBox(-2.0F, 0.0F, -12.0F, 20.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition mouth_main_right = mouth_main.addOrReplaceChild("mouth_main_right", CubeListBuilder.create().texOffs(94, 278).addBox(-5.0F, 6.0F, -5.0F, 10.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, 0.0F, 0.0F));

		PartDefinition mouth_main_right_r1 = mouth_main_right.addOrReplaceChild("mouth_main_right_r1", CubeListBuilder.create().texOffs(94, 278).addBox(-5.0F, -22.0F, -5.0F, 10.0F, 24.0F, 10.0F, new CubeDeformation(-2.5F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition mouth_main_right_r2 = mouth_main_right.addOrReplaceChild("mouth_main_right_r2", CubeListBuilder.create().texOffs(94, 278).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 32.0F, 10.0F, new CubeDeformation(-1.5F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition mouth_main_left = mouth_main.addOrReplaceChild("mouth_main_left", CubeListBuilder.create().texOffs(94, 278).mirror().addBox(-5.0F, 6.0F, -5.0F, 10.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(19.0F, 0.0F, 0.0F));

		PartDefinition mouth_main_left_r1 = mouth_main_left.addOrReplaceChild("mouth_main_left_r1", CubeListBuilder.create().texOffs(94, 278).mirror().addBox(-5.0F, -22.0F, -5.0F, 10.0F, 24.0F, 10.0F, new CubeDeformation(-2.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition mouth_main_left_r2 = mouth_main_left.addOrReplaceChild("mouth_main_left_r2", CubeListBuilder.create().texOffs(94, 278).mirror().addBox(-5.0F, -12.0F, -5.0F, 10.0F, 32.0F, 10.0F, new CubeDeformation(-1.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition mouth_stem_1 = mouth_main.addOrReplaceChild("mouth_stem_1", CubeListBuilder.create().texOffs(40, 276).addBox(-3.0F, -24.25F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.5F))
				.texOffs(64, 276).addBox(-3.0F, -24.25F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(1.25F)), PartPose.offset(0.0F, -0.75F, 0.0F));

		PartDefinition mouth_stem_2 = mouth_stem_1.addOrReplaceChild("mouth_stem_2", CubeListBuilder.create().texOffs(40, 276).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(64, 276).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(1.0F))
				.texOffs(0, 318).addBox(-13.0F, -20.0F, 7.0F, 26.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.5F, 0.0F));

		PartDefinition mouth_stem_3 = mouth_stem_2.addOrReplaceChild("mouth_stem_3", CubeListBuilder.create().texOffs(40, 276).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 276).addBox(-3.0F, -20.0F, -3.0F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, -19.75F, 0.0F));

		PartDefinition mouth_flower_base = mouth_stem_3.addOrReplaceChild("mouth_flower_base", CubeListBuilder.create(), PartPose.offset(0.0F, -20.5F, 0.0F));

		PartDefinition mouth_flower = mouth_flower_base.addOrReplaceChild("mouth_flower", CubeListBuilder.create().texOffs(52, 306).addBox(-7.0F, 0.5F, -7.0F, 14.0F, 20.0F, 14.0F, new CubeDeformation(0.25F))
				.texOffs(52, 306).addBox(-7.0F, 0.5F, -7.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.75F))
				.texOffs(108, 320).addBox(-7.0F, -6.5F, -7.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mouth_flower_1_1 = mouth_flower.addOrReplaceChild("mouth_flower_1_1", CubeListBuilder.create().texOffs(0, 338).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(28, 360).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(60, 340).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.0F, 7.0F));

		PartDefinition mouth_flower_1_1_r1 = mouth_flower_1_1.addOrReplaceChild("mouth_flower_1_1_r1", CubeListBuilder.create().texOffs(-20, 360).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition mouth_flower_1_2 = mouth_flower_1_1.addOrReplaceChild("mouth_flower_1_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition mouth_flower_1_2_r1 = mouth_flower_1_2.addOrReplaceChild("mouth_flower_1_2_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition mouth_flower_1_2_r2 = mouth_flower_1_2.addOrReplaceChild("mouth_flower_1_2_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition mouth_flower_1_2_r3 = mouth_flower_1_2.addOrReplaceChild("mouth_flower_1_2_r3", CubeListBuilder.create().texOffs(148, 340).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_1_2_r4 = mouth_flower_1_2.addOrReplaceChild("mouth_flower_1_2_r4", CubeListBuilder.create().texOffs(92, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_1_3 = mouth_flower_1_2.addOrReplaceChild("mouth_flower_1_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition mouth_flower_1_3_r1 = mouth_flower_1_3.addOrReplaceChild("mouth_flower_1_3_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition mouth_flower_1_3_r2 = mouth_flower_1_3.addOrReplaceChild("mouth_flower_1_3_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition mouth_flower_1_3_r3 = mouth_flower_1_3.addOrReplaceChild("mouth_flower_1_3_r3", CubeListBuilder.create().texOffs(176, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_2_1 = mouth_flower.addOrReplaceChild("mouth_flower_2_1", CubeListBuilder.create().texOffs(0, 338).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(28, 360).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(60, 340).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -1.0F, -7.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition mouth_flower_2_1_r1 = mouth_flower_2_1.addOrReplaceChild("mouth_flower_2_1_r1", CubeListBuilder.create().texOffs(-20, 360).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition mouth_flower_2_2 = mouth_flower_2_1.addOrReplaceChild("mouth_flower_2_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition mouth_flower_2_2_r1 = mouth_flower_2_2.addOrReplaceChild("mouth_flower_2_2_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition mouth_flower_2_2_r2 = mouth_flower_2_2.addOrReplaceChild("mouth_flower_2_2_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition mouth_flower_2_2_r3 = mouth_flower_2_2.addOrReplaceChild("mouth_flower_2_2_r3", CubeListBuilder.create().texOffs(148, 340).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_2_2_r4 = mouth_flower_2_2.addOrReplaceChild("mouth_flower_2_2_r4", CubeListBuilder.create().texOffs(92, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_2_3 = mouth_flower_2_2.addOrReplaceChild("mouth_flower_2_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition mouth_flower_2_3_r1 = mouth_flower_2_3.addOrReplaceChild("mouth_flower_2_3_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition mouth_flower_2_3_r2 = mouth_flower_2_3.addOrReplaceChild("mouth_flower_2_3_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition mouth_flower_2_3_r3 = mouth_flower_2_3.addOrReplaceChild("mouth_flower_2_3_r3", CubeListBuilder.create().texOffs(176, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_3_1 = mouth_flower.addOrReplaceChild("mouth_flower_3_1", CubeListBuilder.create().texOffs(0, 338).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(28, 360).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(60, 340).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(7.0F, -1.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition mouth_flower_3_1_r1 = mouth_flower_3_1.addOrReplaceChild("mouth_flower_3_1_r1", CubeListBuilder.create().texOffs(-20, 360).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition mouth_flower_3_2 = mouth_flower_3_1.addOrReplaceChild("mouth_flower_3_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition mouth_flower_3_2_r1 = mouth_flower_3_2.addOrReplaceChild("mouth_flower_3_2_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition mouth_flower_3_2_r2 = mouth_flower_3_2.addOrReplaceChild("mouth_flower_3_2_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition mouth_flower_3_2_r3 = mouth_flower_3_2.addOrReplaceChild("mouth_flower_3_2_r3", CubeListBuilder.create().texOffs(148, 340).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_3_2_r4 = mouth_flower_3_2.addOrReplaceChild("mouth_flower_3_2_r4", CubeListBuilder.create().texOffs(92, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_3_3 = mouth_flower_3_2.addOrReplaceChild("mouth_flower_3_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition mouth_flower_3_3_r1 = mouth_flower_3_3.addOrReplaceChild("mouth_flower_3_3_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition mouth_flower_3_3_r2 = mouth_flower_3_3.addOrReplaceChild("mouth_flower_3_3_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition mouth_flower_3_3_r3 = mouth_flower_3_3.addOrReplaceChild("mouth_flower_3_3_r3", CubeListBuilder.create().texOffs(176, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_4_1 = mouth_flower.addOrReplaceChild("mouth_flower_4_1", CubeListBuilder.create().texOffs(0, 338).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(28, 360).addBox(-1.0F, 1.5F, 3.0F, 2.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(60, 340).addBox(-7.0F, -4.5F, 0.0F, 14.0F, 6.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-7.0F, -1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition mouth_flower_4_1_r1 = mouth_flower_4_1.addOrReplaceChild("mouth_flower_4_1_r1", CubeListBuilder.create().texOffs(-20, 360).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition mouth_flower_4_2 = mouth_flower_4_1.addOrReplaceChild("mouth_flower_4_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition mouth_flower_4_2_r1 = mouth_flower_4_2.addOrReplaceChild("mouth_flower_4_2_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 4.4F, 14.0F, 0.0F, 0.0F, -2.7053F));

		PartDefinition mouth_flower_4_2_r2 = mouth_flower_4_2.addOrReplaceChild("mouth_flower_4_2_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 4.4F, 14.0F, 0.0F, 0.0F, 2.7053F));

		PartDefinition mouth_flower_4_2_r3 = mouth_flower_4_2.addOrReplaceChild("mouth_flower_4_2_r3", CubeListBuilder.create().texOffs(148, 340).addBox(-7.0F, 0.0F, -28.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 28.0F, 0.7854F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_4_2_r4 = mouth_flower_4_2.addOrReplaceChild("mouth_flower_4_2_r4", CubeListBuilder.create().texOffs(92, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -4.5F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition mouth_flower_4_3 = mouth_flower_4_2.addOrReplaceChild("mouth_flower_4_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 28.0F));

		PartDefinition mouth_flower_4_3_r1 = mouth_flower_4_3.addOrReplaceChild("mouth_flower_4_3_r1", CubeListBuilder.create().texOffs(120, 340).mirror().addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 2.9F, 14.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition mouth_flower_4_3_r2 = mouth_flower_4_3.addOrReplaceChild("mouth_flower_4_3_r2", CubeListBuilder.create().texOffs(120, 340).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 2.9F, 14.0F, 0.0F, 0.0F, 2.4871F));

		PartDefinition mouth_flower_4_3_r3 = mouth_flower_4_3.addOrReplaceChild("mouth_flower_4_3_r3", CubeListBuilder.create().texOffs(176, 340).addBox(-7.0F, -6.0F, -14.0F, 14.0F, 12.0F, 28.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 14.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition add = body.addOrReplaceChild("add", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

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

		PartDefinition petal_1 = base_flower.addOrReplaceChild("petal_1", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1202F, 0.0F));

		PartDefinition petal_r1 = petal_1.addOrReplaceChild("petal_r1", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_r2 = petal_1.addOrReplaceChild("petal_r2", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		PartDefinition petal_r3 = petal_1.addOrReplaceChild("petal_r3", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_r4 = petal_1.addOrReplaceChild("petal_r4", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition petal_2 = base_flower.addOrReplaceChild("petal_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.1202F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition petal_r5 = petal_2.addOrReplaceChild("petal_r5", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_r6 = petal_2.addOrReplaceChild("petal_r6", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		PartDefinition petal_r7 = petal_2.addOrReplaceChild("petal_r7", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_r8 = petal_2.addOrReplaceChild("petal_r8", CubeListBuilder.create().texOffs(39, 154).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.4202F, 0.0F, 0.3491F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T celestialCoilvineEntity, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.phantom.yRot = f4 * ((float) Math.PI / 180F);
		this.claw.yRot = f4 * ((float) Math.PI / 180F);
		this.eye.yRot = f4 * ((float) Math.PI / 180F);
		this.mouth_stem_1.yRot = f4 * ((float) Math.PI / 180F);

		this.phantom_eye.xRot = f5 * ((float) Math.PI / 180F);
		this.phantom_light.xRot = f5 * ((float) Math.PI / 180F);

		this.eye_main.xRot = f5 * ((float) Math.PI / 180F);
		this.eye_eye.xRot = f5 * ((float) Math.PI / 180F);

		this.mouth_flower.xRot = (Mth.clamp(f5, -65.0f, 65.0f) * ((float) Math.PI / 180F)) *
				(celestialCoilvineEntity.headXRotAnimProgress / 20f);

		if (celestialCoilvineEntity.getBodyTypeUseTick() <= 20) {
			this.phantom.visible = celestialCoilvineEntity.getBodyType() == 1;
			this.claw.visible = celestialCoilvineEntity.getBodyType() == 2;
			this.eye.visible = celestialCoilvineEntity.getBodyType() == 3;
			this.mouth.visible = celestialCoilvineEntity.getBodyType() == 4;
		}
		else {
			this.phantom.visible = celestialCoilvineEntity.getLastBodyType() == 1;
			this.claw.visible = celestialCoilvineEntity.getLastBodyType() == 2;
			this.eye.visible = celestialCoilvineEntity.getLastBodyType() == 3;
			this.mouth.visible = celestialCoilvineEntity.getLastBodyType() == 4;
		}
		this.animate(celestialCoilvineEntity.idleBaseAnimationState, CelestialCoilvineAnimation.IDLEBASE, f3);
		this.animate(celestialCoilvineEntity.rotAnimationState, CelestialCoilvineAnimation.ROT, f3);
		this.animate(celestialCoilvineEntity.attackAnimationState, CelestialCoilvineAnimation.ATTACK, f3);
		this.animate(celestialCoilvineEntity.startAnimationState, CelestialCoilvineAnimation.START, f3);
		this.animate(celestialCoilvineEntity.deadAnimationState, CelestialCoilvineAnimation.DEATH, f3);
		this.animate(celestialCoilvineEntity.hideAnimationState, CelestialCoilvineAnimation.HIDE, f3);
		this.animate(celestialCoilvineEntity.spell1AnimationState, CelestialCoilvineAnimation.SPELL1, f3);
		this.animate(celestialCoilvineEntity.spell2AnimationState, CelestialCoilvineAnimation.SPELL2, f3);
		this.animate(celestialCoilvineEntity.spell3AnimationState, CelestialCoilvineAnimation.SPELL3, f3);
		this.animate(celestialCoilvineEntity.spell4AnimationState, CelestialCoilvineAnimation.SPELL4, f3);
		this.animate(celestialCoilvineEntity.spell5AnimationState, CelestialCoilvineAnimation.SPELL5, f3);
		this.animate(celestialCoilvineEntity.spell6AnimationState, CelestialCoilvineAnimation.SPELL6, f3);
		this.animate(celestialCoilvineEntity.spell7AnimationState, CelestialCoilvineAnimation.SPELL7, f3);
		this.animate(celestialCoilvineEntity.change1AnimationState, CelestialCoilvineAnimation.CHANGE1, f3);
		this.animate(celestialCoilvineEntity.change2AnimationState, CelestialCoilvineAnimation.CHANGE2, f3);
		this.animate(celestialCoilvineEntity.change3AnimationState, CelestialCoilvineAnimation.CHANGE3, f3);
		this.animate(celestialCoilvineEntity.change4AnimationState, CelestialCoilvineAnimation.CHANGE4, f3);
		this.animate(celestialCoilvineEntity.claw1AnimationState, CelestialCoilvineClawAnimation.CLAW1, f3);
		this.animate(celestialCoilvineEntity.claw2AnimationState, CelestialCoilvineClawAnimation.CLAW2, f3);
		this.animate(celestialCoilvineEntity.claw3AnimationState, CelestialCoilvineClawAnimation.CLAW3, f3);
		this.animate(celestialCoilvineEntity.claw4AnimationState, CelestialCoilvineClawAnimation.CLAW4, f3);
		this.animate(celestialCoilvineEntity.claw5AnimationState, CelestialCoilvineClawAnimation.CLAW5, f3);
		this.animate(celestialCoilvineEntity.claw6AnimationState, CelestialCoilvineClawAnimation.CLAW6, f3);
		this.animate(celestialCoilvineEntity.claw7AnimationState, CelestialCoilvineClawAnimation.CLAW7, f3);
		this.animate(celestialCoilvineEntity.eye1AnimationState, CelestialCoilvineEyeAnimation.EYE1, f3);
		this.animate(celestialCoilvineEntity.eye2AnimationState, CelestialCoilvineEyeAnimation.EYE2, f3);
		this.animate(celestialCoilvineEntity.eye3AnimationState, CelestialCoilvineEyeAnimation.EYE3, f3);
		this.animate(celestialCoilvineEntity.eye4AnimationState, CelestialCoilvineEyeAnimation.EYE4, f3);
		this.animate(celestialCoilvineEntity.eye5AnimationState, CelestialCoilvineEyeAnimation.EYE5, f3);
		this.animate(celestialCoilvineEntity.eye6AnimationState, CelestialCoilvineEyeAnimation.EYE6, f3);
		this.animate(celestialCoilvineEntity.eye7AnimationState, CelestialCoilvineEyeAnimation.EYE7, f3);
		this.animate(celestialCoilvineEntity.mouth1AnimationState, CelestialCoilvineMouthAnimation.MOUTH1, f3);
		this.animate(celestialCoilvineEntity.mouth2AnimationState, CelestialCoilvineMouthAnimation.MOUTH2, f3);
		this.animate(celestialCoilvineEntity.mouth3AnimationState, CelestialCoilvineMouthAnimation.MOUTH3, f3);
		this.animate(celestialCoilvineEntity.mouth4AnimationState, CelestialCoilvineMouthAnimation.MOUTH4, f3);
		this.animate(celestialCoilvineEntity.mouth5AnimationState, CelestialCoilvineMouthAnimation.MOUTH5, f3);
		this.animate(celestialCoilvineEntity.mouth5AnimationState, CelestialCoilvineMouthAnimation.MOUTH5ADD, f3);
		this.animate(celestialCoilvineEntity.mouth6AnimationState, CelestialCoilvineMouthAnimation.MOUTH6, f3);
		this.animate(celestialCoilvineEntity.mouth7AnimationState, CelestialCoilvineMouthAnimation.MOUTH7, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}