package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.GemstoneMalignasaurAnimation;
import com.jerotes.jerotesvillage.entity.Animal.MalignasaurEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class Modelgemstone_malignasaur<T extends LivingEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "gemstone_malignasaur"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart body_front;
	private final ModelPart body_hind_1;
	private final ModelPart body_hind_2;
	private final ModelPart body_hind_3;
	private final ModelPart tail;


	public Modelgemstone_malignasaur(ModelPart modelPart) {
		super(0.5f, 24.0f);
		this.root = modelPart;
		this.body = modelPart.getChild("body");
		this.body_front = body.getChild("body_front");
		this.head = body_front.getChild("head");
		this.body_hind_1 = body.getChild("body_hind_1");
		this.body_hind_2 = body_hind_1.getChild("body_hind_2");
		this.body_hind_3 = body_hind_2.getChild("body_hind_3");
		this.tail = body_hind_3.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(63, 427).addBox(0.0F, -13.75F, -15.0F, 0.0F, 4.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(63, 423).addBox(-4.0F, -12.75F, -15.0F, 0.0F, 4.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(63, 423).addBox(4.0F, -12.75F, -15.0F, 0.0F, 4.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -13.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(136, 328).addBox(0.0F, -12.5F, -17.5F, 0.0F, 13.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -2.0F, -2.5F, 0.0F, 0.0F, -0.4363F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(136, 328).mirror().addBox(0.0F, -12.5F, -17.5F, 0.0F, 13.0F, 35.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(11.0F, -2.0F, -2.5F, 0.0F, 0.0F, 0.4363F));

		PartDefinition main_body = body.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 191).addBox(-11.0F, -12.5F, -36.0F, 22.0F, 20.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 16.0F));

		PartDefinition saddle_1 = body.addOrReplaceChild("saddle_1", CubeListBuilder.create().texOffs(434, 492).addBox(-6.5F, -9.0F, 2.0F, 13.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(472, 497).addBox(4.5F, -9.0F, 8.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(478, 497).addBox(-6.5F, -9.0F, 8.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(484, 509).addBox(-4.5F, 3.0F, 8.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(484, 506).addBox(-4.5F, -9.0F, 8.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_r1 = saddle_1.addOrReplaceChild("saddle_r1", CubeListBuilder.create().texOffs(499, 503).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(495, 503).addBox(-8.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -11.8042F, 5.1308F, -0.6545F, 0.0F, 0.0F));

		PartDefinition saddle_r2 = saddle_1.addOrReplaceChild("saddle_r2", CubeListBuilder.create().texOffs(500, 500).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.6568F, -1.25F, -5.9569F, 0.0F, 0.0F, -1.5708F));

		PartDefinition saddle_r3 = saddle_1.addOrReplaceChild("saddle_r3", CubeListBuilder.create().texOffs(495, 503).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.6568F, -1.5F, -0.4569F, 0.0F, 0.0F, -1.5708F));

		PartDefinition saddle_r4 = saddle_1.addOrReplaceChild("saddle_r4", CubeListBuilder.create().texOffs(504, 503).addBox(-1.0F, -3.5F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8801F, -1.5F, 6.1737F, 0.6545F, 0.0F, -1.5708F));

		PartDefinition saddle_r5 = saddle_1.addOrReplaceChild("saddle_r5", CubeListBuilder.create().texOffs(500, 500).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.6568F, -1.25F, -5.9569F, 0.0F, 0.0F, -1.5708F));

		PartDefinition saddle_r6 = saddle_1.addOrReplaceChild("saddle_r6", CubeListBuilder.create().texOffs(495, 503).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.6568F, -1.5F, -0.4569F, 0.0F, 0.0F, -1.5708F));

		PartDefinition saddle_r7 = saddle_1.addOrReplaceChild("saddle_r7", CubeListBuilder.create().texOffs(504, 503).addBox(-1.0F, -3.5F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8801F, -1.5F, 6.1737F, -0.6545F, 0.0F, -1.5708F));

		PartDefinition saddle_2 = body.addOrReplaceChild("saddle_2", CubeListBuilder.create().texOffs(434, 492).addBox(-6.5F, -9.0F, 2.0F, 13.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(472, 497).addBox(4.5F, -9.0F, 8.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(478, 497).addBox(-6.5F, -9.0F, 8.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(484, 509).addBox(-4.5F, 3.0F, 8.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(484, 506).addBox(-4.5F, -9.0F, 8.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 9.9F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_r8 = saddle_2.addOrReplaceChild("saddle_r8", CubeListBuilder.create().texOffs(499, 503).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(495, 503).addBox(-12.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -11.8042F, 8.1308F, -1.5708F, 0.0F, 0.0F));

		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create(), PartPose.offset(-10.5F, 2.5F, -3.0F));

		PartDefinition right_front_fin_r1 = right_front_fin.addOrReplaceChild("right_front_fin_r1", CubeListBuilder.create().texOffs(166, 439).addBox(-12.0F, -8.0F, 0.0F, 24.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.9508F, 12.8769F, 9.2653F, -1.2985F, 0.4682F, -0.9347F));

		PartDefinition right_front_fin_r2 = right_front_fin.addOrReplaceChild("right_front_fin_r2", CubeListBuilder.create().texOffs(166, 429).addBox(-4.5F, -4.5F, -0.5F, 17.0F, 9.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-9.6223F, 11.1512F, 11.1999F, -1.2334F, 0.7599F, -0.8229F));

		PartDefinition right_front_fin_r3 = right_front_fin.addOrReplaceChild("right_front_fin_r3", CubeListBuilder.create().texOffs(166, 417).addBox(-23.5F, -5.5F, -0.5F, 23.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.5F, 0.5F, -1.309F, 0.384F, -0.9599F));

		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create(), PartPose.offset(10.5F, 2.5F, -3.0F));

		PartDefinition right_front_fin_r4 = left_front_fin.addOrReplaceChild("right_front_fin_r4", CubeListBuilder.create().texOffs(166, 439).mirror().addBox(-12.0F, -8.0F, 0.0F, 24.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.7408F, 12.7672F, 8.7412F, -1.3041F, -0.4261F, 0.9475F));

		PartDefinition right_front_fin_r5 = left_front_fin.addOrReplaceChild("right_front_fin_r5", CubeListBuilder.create().texOffs(166, 429).mirror().addBox(-13.5F, -5.5F, -0.5F, 17.0F, 9.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(10.1496F, 12.3555F, 10.6789F, -1.2463F, -0.7186F, 0.8421F));

		PartDefinition right_front_fin_r6 = left_front_fin.addOrReplaceChild("right_front_fin_r6", CubeListBuilder.create().texOffs(166, 417).mirror().addBox(0.5F, -5.5F, -0.5F, 23.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -1.5F, 0.5F, -1.309F, -0.384F, 0.9599F));

		PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(82, 405).addBox(-7.5F, -8.5F, -17.0F, 15.0F, 15.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(63, 429).addBox(0.0F, -12.5F, -20.0F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(63, 425).addBox(-4.0F, -11.5F, -20.0F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(63, 425).addBox(4.0F, -11.5F, -20.0F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -15.0F));

		PartDefinition body_front_r1 = body_front.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(114, 222).addBox(-6.5F, -2.5F, -20.0F, 13.0F, 3.0F, 17.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 9.0F, 3.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head = body_front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(202, 191).addBox(-7.0F, -4.0F, -22.0F, 14.0F, 4.0F, 25.0F, new CubeDeformation(0.1625F))
				.texOffs(0, 0).addBox(-7.0F, -4.9F, -23.0F, 14.0F, 1.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(290, 438).addBox(-6.5F, 0.0F, -13.0F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.1125F))
				.texOffs(206, 339).addBox(-5.5F, -7.0F, -22.0F, 11.0F, 3.0F, 25.0F, new CubeDeformation(0.05F))
				.texOffs(268, 240).addBox(-5.0F, -5.0F, -36.0F, 10.0F, 5.0F, 14.0F, new CubeDeformation(0.05F))
				.texOffs(274, 484).addBox(-5.0F, -0.5F, -21.0F, 10.0F, 5.0F, 9.0F, new CubeDeformation(-0.15F))
				.texOffs(462, 0).addBox(-5.0F, -0.5F, -35.8F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.05F))
				.texOffs(462, 34).addBox(-5.0F, -0.5F, -35.8F, 10.0F, 2.0F, 15.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, -1.0F, -8.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(188, 472).addBox(-4.0F, 12.25F, 51.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.5F, -88.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(40, 488).addBox(-0.5F, -2.5F, -7.0F, 2.0F, 5.0F, 14.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-4.9696F, -2.5276F, -29.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(40, 488).mirror().addBox(-2.5F, -2.75F, -7.0F, 2.0F, 5.0F, 14.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(6.0F, -2.5F, -29.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition head_r4 = head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 443).mirror().addBox(-1.0F, -2.0F, -12.5F, 2.0F, 4.0F, 25.0F, new CubeDeformation(-0.225F)).mirror(false), PartPose.offsetAndRotation(5.4F, -5.0F, -9.5F, 0.0F, 0.0F, -0.4363F));

		PartDefinition head_r5 = head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 443).addBox(-1.0F, -2.0F, -12.5F, 2.0F, 4.0F, 25.0F, new CubeDeformation(-0.225F)), PartPose.offsetAndRotation(-5.4F, -5.0F, -9.5F, 0.0F, 0.0F, 0.4363F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(266, 401).addBox(-5.5F, -1.5F, -10.0F, 11.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(462, 17).addBox(-5.0F, -3.25F, -24.0F, 10.0F, 2.0F, 15.0F, new CubeDeformation(-0.125F))
				.texOffs(278, 356).addBox(-5.0F, -1.5F, -23.7F, 10.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(140, 487).addBox(-5.0F, -5.0F, -6.25F, 10.0F, 5.0F, 9.0F, new CubeDeformation(-0.0175F)), PartPose.offset(0.0F, 1.5F, -12.0F));

		PartDefinition head_r6 = jaw.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(268, 220).addBox(-5.0F, 0.25F, -16.0F, 11.0F, 3.0F, 17.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-0.5F, 3.5F, 7.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(54, 470).addBox(-4.0F, 0.75F, 33.25F, 9.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.5F, -56.7F, -0.0436F, 0.0F, 0.0F));

		PartDefinition jaw_r2 = jaw.addOrReplaceChild("jaw_r2", CubeListBuilder.create().texOffs(164, 368).addBox(0.0F, 0.0F, -7.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 1.5F, -16.7F, 0.0F, 0.0F, 0.3491F));

		PartDefinition jaw_r3 = jaw.addOrReplaceChild("jaw_r3", CubeListBuilder.create().texOffs(164, 368).addBox(0.0F, 0.0F, -7.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 1.5F, -16.7F, 0.0F, 0.0F, -0.3491F));

		PartDefinition body_hind_1 = body.addOrReplaceChild("body_hind_1", CubeListBuilder.create().texOffs(0, 246).addBox(-8.5F, -6.5F, 0.0F, 17.0F, 17.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(63, 436).addBox(0.0F, -9.5F, 0.0F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 15.0F));

		PartDefinition back_r1 = body_hind_1.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(63, 432).addBox(0.0F, -2.0F, -0.5F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -6.5F, 0.5F, 0.0F, -0.0436F, 0.0F));

		PartDefinition back_r2 = body_hind_1.addOrReplaceChild("back_r2", CubeListBuilder.create().texOffs(63, 432).addBox(0.0F, -2.0F, -0.5F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -6.5F, 0.5F, 0.0F, 0.0436F, 0.0F));

		PartDefinition body_hind_1_r1 = body_hind_1.addOrReplaceChild("body_hind_1_r1", CubeListBuilder.create().texOffs(114, 191).addBox(-7.5F, -1.5F, 0.0F, 15.0F, 2.0F, 29.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0654F, 0.0F, 0.0F));

		PartDefinition add_r1 = body_hind_1.addOrReplaceChild("add_r1", CubeListBuilder.create().texOffs(180, 251).mirror().addBox(0.5F, 0.0F, -14.5F, 15.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 5.5F, 9.5F, 0.0F, 0.0F, 0.6109F));

		PartDefinition add_r2 = body_hind_1.addOrReplaceChild("add_r2", CubeListBuilder.create().texOffs(180, 251).addBox(-15.5F, 0.0F, -14.5F, 15.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 5.5F, 9.5F, 0.0F, 0.0F, -0.6109F));

		PartDefinition left_hind_fin = body_hind_1.addOrReplaceChild("left_hind_fin", CubeListBuilder.create(), PartPose.offset(7.5F, 5.5F, 21.0F));

		PartDefinition left_hind_fin_r1 = left_hind_fin.addOrReplaceChild("left_hind_fin_r1", CubeListBuilder.create().texOffs(214, 428).mirror().addBox(-9.5F, -5.0F, 0.0F, 24.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.2155F, 5.0124F, 4.6449F, -1.4835F, -0.6109F, 1.309F));

		PartDefinition left_hind_fin_r2 = left_hind_fin.addOrReplaceChild("left_hind_fin_r2", CubeListBuilder.create().texOffs(214, 417).mirror().addBox(-1.5F, -4.5F, -1.5F, 19.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -1.5F, 0.5F, -1.4835F, -0.6109F, 1.309F));

		PartDefinition right_hind_fin = body_hind_1.addOrReplaceChild("right_hind_fin", CubeListBuilder.create(), PartPose.offset(-7.5F, 5.5F, 21.0F));

		PartDefinition right_hind_fin_r1 = right_hind_fin.addOrReplaceChild("right_hind_fin_r1", CubeListBuilder.create().texOffs(214, 428).addBox(-14.5F, -5.0F, 0.0F, 24.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2155F, 5.0124F, 4.6449F, -1.4835F, 0.6109F, -1.309F));

		PartDefinition right_hind_fin_r2 = right_hind_fin.addOrReplaceChild("right_hind_fin_r2", CubeListBuilder.create().texOffs(214, 417).addBox(-17.5F, -4.5F, -1.5F, 19.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.5F, 0.5F, -1.4835F, 0.6109F, -1.309F));

		PartDefinition body_hind_2 = body_hind_1.addOrReplaceChild("body_hind_2", CubeListBuilder.create().texOffs(0, 292).addBox(-6.0F, -5.5F, 0.0F, 12.0F, 12.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(63, 436).addBox(0.0F, -8.5F, 0.0F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 29.0F));

		PartDefinition body_hind_2_r1 = body_hind_2.addOrReplaceChild("body_hind_2_r1", CubeListBuilder.create().texOffs(180, 280).addBox(-6.0F, -4.5F, 0.0F, 12.0F, 3.0F, 29.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_hind_2_r2 = body_hind_2.addOrReplaceChild("body_hind_2_r2", CubeListBuilder.create().texOffs(0, 381).mirror().addBox(0.0F, 0.0F, -14.5F, 12.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 5.5F, 14.5F, 0.0F, 0.0F, 0.7418F));

		PartDefinition body_hind_2_r3 = body_hind_2.addOrReplaceChild("body_hind_2_r3", CubeListBuilder.create().texOffs(0, 381).addBox(-12.0F, 0.0F, -14.5F, 12.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 5.5F, 14.5F, 0.0F, 0.0F, -0.7418F));

		PartDefinition body_hind_3 = body_hind_2.addOrReplaceChild("body_hind_3", CubeListBuilder.create().texOffs(92, 246).addBox(-4.5F, -4.5F, 0.0F, 9.0F, 9.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(63, 436).addBox(0.0F, -7.0F, 0.0F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 29.0F));

		PartDefinition body_hind_3_r1 = body_hind_3.addOrReplaceChild("body_hind_3_r1", CubeListBuilder.create().texOffs(92, 290).addBox(-4.5F, -0.5F, 0.0F, 9.0F, 3.0F, 35.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 5.0F, -0.3F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tail = body_hind_3.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(60, 413).addBox(-1.5F, -4.5F, 0.0F, 3.0F, 14.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(79, 327).addBox(0.0F, -14.5F, 0.0F, 0.0F, 37.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 35.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(72, 496).addBox(-2.0F, -3.75F, -6.25F, 4.0F, 5.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 18.5F, 11.5F, -0.7418F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(278, 374).addBox(-2.0F, -3.5F, -12.5F, 4.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 7.5F, -0.829F, 0.0F, 0.0F));

		PartDefinition tail_r3 = tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(234, 472).addBox(-1.5F, -2.0F, -9.5F, 3.0F, 4.0F, 17.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -7.5F, 7.5F, 0.6109F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}


	protected void animateIdle2(MalignasaurEntity malignasaur,
								AnimationState animationState, float p_233384_) {
		this.animateIdle2(malignasaur, animationState, p_233384_, 1.0F);
	}

	protected void animateIdle2(MalignasaurEntity malignasaur,
								AnimationState animationState, float ageInTicks, float speed) {
		AnimationDefinition animation1 = GemstoneMalignasaurAnimation.IDLE;
		AnimationDefinition animation2 = GemstoneMalignasaurAnimation.SWIM;
		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation1,
					animationState.getAccumulatedTime(),
					1 - malignasaur.waterAnimProgress / 40f,
					new Vector3f());
		});
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation2,
					animationState.getAccumulatedTime(),
					malignasaur.waterAnimProgress / 40f,
					new Vector3f());
		});
	}

	private float currentTailYaw = 0f;

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof MalignasaurEntity malignasaur) {
			this.applyHeadRotation(malignasaur, f4, f5, f3);
			//	this.setupBodyAnim(t);
			if (malignasaur.isBaby()) {
				this.head.xScale = 1.2f;
				this.head.yScale = 1.2f;
				this.head.zScale = 1.2f;
			} else {
				this.head.xScale = 1f;
				this.head.yScale = 1f;
				this.head.zScale = 1f;
			}


			float targetTailYaw = -f4 * 1.2f * ((float) Math.PI / 180F);
			currentTailYaw += (targetTailYaw - currentTailYaw) * 0.1f;
			body_hind_2.yRot += Mth.clamp(currentTailYaw * 0.6f, -1.0f, 1.0f);
			body_hind_3.yRot += Mth.clamp(currentTailYaw * 0.8f, -1.0f, 1.0f);
			tail.yRot += Mth.clamp(currentTailYaw, -1.0f, 1.0f);

			this.animateIdle2(malignasaur, malignasaur.landAnimationState, f3);
			if (malignasaur.isInWater() || (malignasaur instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.isInRain())) {
				this.animateWalk(GemstoneMalignasaurAnimation.SWIM, f, f2, 1.5f, 2.0f);
			} else {
				this.animateWalk(GemstoneMalignasaurAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			if (malignasaur instanceof GemstoneMalignasaurEntity) {
				KeyframeAnimations.animate(this, GemstoneMalignasaurAnimation.BOSS, 0L, 1F, new Vector3f());
			}
			this.animate(malignasaur.attack1AnimationState, GemstoneMalignasaurAnimation.ATTACK1, f3);
			this.animate(malignasaur.attack2AnimationState, GemstoneMalignasaurAnimation.ATTACK2, f3);
			this.animate(malignasaur.attack3AnimationState, GemstoneMalignasaurAnimation.ATTACK3, f3);
			this.animate(malignasaur.headAttack1AnimationState, GemstoneMalignasaurAnimation.HEADATTACK1, f3);
			this.animate(malignasaur.headAttack2AnimationState, GemstoneMalignasaurAnimation.HEADATTACK2, f3);
			this.animate(malignasaur.headAttack3AnimationState, GemstoneMalignasaurAnimation.HEADATTACK3, f3);
			this.animate(malignasaur.tailAttack1AnimationState, GemstoneMalignasaurAnimation.TAILATTACK1, f3);
			this.animate(malignasaur.tailAttack2AnimationState, GemstoneMalignasaurAnimation.TAILATTACK2, f3);
			this.animate(malignasaur.tailAttack3AnimationState, GemstoneMalignasaurAnimation.TAILATTACK3, f3);
			this.animate(malignasaur.breathAnimationState, GemstoneMalignasaurAnimation.BREATH, f3);
			this.animate(malignasaur.whirlpoolAnimationState, GemstoneMalignasaurAnimation.WHIRLPOOL, f3);
			this.animate(malignasaur.jumpAnimationState, GemstoneMalignasaurAnimation.JUMP, f3);
			this.animate(malignasaur.armorAnimationState, GemstoneMalignasaurAnimation.ARMOR, f3);
			this.animate(malignasaur.controlAnimationState, GemstoneMalignasaurAnimation.CONTROL, f3);
			this.animate(malignasaur.deterAnimationState, GemstoneMalignasaurAnimation.DETER, f3);
			this.animate(malignasaur.bombAnimationState, GemstoneMalignasaurAnimation.BOMB, f3);
			this.animate(malignasaur.sitAnimationState, GemstoneMalignasaurAnimation.SIT, f3);
			this.animate(malignasaur.toSitAnimationState, GemstoneMalignasaurAnimation.TOSIT, f3);
			this.animate(malignasaur.stopSitAnimationState, GemstoneMalignasaurAnimation.STOPSIT, f3);
			this.animate(malignasaur.deadAnimationState, GemstoneMalignasaurAnimation.DEAD, f3);
		}
		else if (t instanceof BossEntity) {
			KeyframeAnimations.animate(this, GemstoneMalignasaurAnimation.IDLE, 0L, 1, new Vector3f());
		}
	}

	private void applyHeadRotation(MalignasaurEntity t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F) * 0.5f;
		this.head.xRot = f2 * ((float) Math.PI / 180F) * (0.5f + 0.5f * (1 - t.waterAnimProgress / 40f));
		this.body_front.yRot = f * ((float) Math.PI / 180F) * 0.5f;
		this.body_front.xRot = f2 * ((float) Math.PI / 180F) * 0.5f;
		this.body.xRot = f2 * ((float) Math.PI / 180F) * 0.5f * (t.waterAnimProgress / 40f);
	}

//	public void setupBodyAnim(T t) {
//		if (t instanceof GemstoneMalignasaurEntity malignasaurEntity) {
//
//			float fs = Mth.lerp(0.9f, 0, malignasaurEntity.walkAnimation.speed()) * (malignasaurEntity.yBodyRotOldAbout / 360);
//            TickRateManager tickRateManager = null;
//            if (Minecraft.getInstance().level != null) {
//                tickRateManager = Minecraft.getInstance().level.tickRateManager();
//				float f11 = Minecraft.getInstance().isPaused() ? 0 : Minecraft.getInstance().getFrameTime();
//				float f22 = tickRateManager.runsNormally() ? f11 : 1.0f;
//				float tick = tickRateManager.isEntityFrozen(t) ? f22 : f11;
//
//				Object object;
//				float f4 = Mth.rotLerp(tick, malignasaurEntity.yBodyRotOOld, malignasaurEntity.yBodyRotOld);
//				float f5 = Mth.rotLerp(tick, t.yBodyRotO, t.yBodyRot);
//				float f6 = f5 - f4;
//				if (t.isPassenger() && (object = t.getVehicle()) instanceof LivingEntity) {
//					LivingEntity livingEntity = (LivingEntity)object;
//					f4 = Mth.rotLerp(tick, malignasaurEntity.yBodyRotOOld, malignasaurEntity.yBodyRotOld);
//					f6 = f5 - f4;
//					float f7 = Mth.wrapDegrees(f6);
//					if (f7 < -85.0f) {
//						f7 = -85.0f;
//					}
//					if (f7 >= 85.0f) {
//						f7 = 85.0f;
//					}
//					f4 = f5 - f7;
//					if (f7 * f7 > 2500.0f) {
//						f4 += f7 * 0.2f;
//					}
//					f6 = f5 - f4;
//				}
//				float f8 = Mth.lerp(tick, t.xRotO, t.getXRot());
//				if (LivingEntityRenderer.isEntityUpsideDown(t)) {
//					f8 *= -1.0f;
//					f6 *= -1.0f;
//				}
//
//				float f = f6;
//				float f2 = f8;
//
//				this.body_front.yRot = f * 0.008f * fs;
//				if (this.body_front.yRot > 0.4f) {
//					this.body_front.yRot = 0.4f;
//				}
//				if (this.body_front.yRot < -0.4f) {
//					this.body_front.yRot = -0.4f;
//				}
//				this.body_front.xRot = f2 * 0.003f;
//				if (this.body_front.xRot > 0.4f) {
//					this.body_front.xRot = 0.4f;
//				}
//				if (this.body_front.xRot < -0.4f) {
//					this.body_front.xRot = -0.4f;
//				}
//				this.body_hind_1.yRot = f * 0.008f * fs;
//				if (this.body_hind_1.yRot > 0.4f) {
//					this.body_hind_1.yRot = 0.4f;
//				}
//				if (this.body_hind_1.yRot < -0.4f) {
//					this.body_hind_1.yRot = -0.4f;
//				}
//				this.body_hind_2.yRot = f * 0.008f * fs;
//				if (this.body_hind_2.yRot > 0.4f) {
//					this.body_hind_2.yRot = 0.4f;
//				}
//				if (this.body_hind_2.yRot < -0.4f) {
//					this.body_hind_2.yRot = -0.4f;
//				}
//				this.body_hind_3.yRot = f * 0.008f * fs;
//				if (this.body_hind_3.yRot > 0.4f) {
//					this.body_hind_3.yRot = 0.4f;
//				}
//				if (this.body_hind_3.yRot < -0.4f) {
//					this.body_hind_3.yRot = -0.4f;
//				}
//				this.tail.yRot = f * 0.008f * fs;
//				if (this.tail.yRot > 0.4f) {
//					this.tail.yRot = 0.4f;
//				}
//				if (this.tail.yRot < -0.4f) {
//					this.tail.yRot = -0.4f;
//				}
//            }
//
//		}
//	}
//	public void setupBodyAnim(T t) {
//		if (t instanceof GemstoneMalignasaurEntity malignasaurEntity) {
//
//			float fs = Mth.lerp(0.9f, 0, malignasaurEntity.walkAnimation.speed()) * (malignasaurEntity.yBodyRotOldAbout / 360);
//			if (Minecraft.getInstance().level != null) {
//				float f11 = Minecraft.getInstance().isPaused() ? 0 : Minecraft.getInstance().getFrameTime();
//				float f22 = 1.0f;
//				float tick = f11;
//
//				Object object;
//				float f4 = Mth.rotLerp(tick, malignasaurEntity.yBodyRotOOld, malignasaurEntity.yBodyRotOld);
//				float f5 = Mth.rotLerp(tick, t.yBodyRotO, t.yBodyRot);
//				float f6 = f5 - f4;
//				if (t.isPassenger() && (object = t.getVehicle()) instanceof LivingEntity) {
//					LivingEntity livingEntity = (LivingEntity)object;
//					f4 = Mth.rotLerp(tick, malignasaurEntity.yBodyRotOOld, malignasaurEntity.yBodyRotOld);
//					f6 = f5 - f4;
//					float f7 = Mth.wrapDegrees(f6);
//					if (f7 < -85.0f) {
//						f7 = -85.0f;
//					}
//					if (f7 >= 85.0f) {
//						f7 = 85.0f;
//					}
//					f4 = f5 - f7;
//					if (f7 * f7 > 2500.0f) {
//						f4 += f7 * 0.2f;
//					}
//					f6 = f5 - f4;
//				}
//				float f8 = Mth.lerp(tick, t.xRotO, t.getXRot());
//				if (LivingEntityRenderer.isEntityUpsideDown(t)) {
//					f8 *= -1.0f;
//					f6 *= -1.0f;
//				}
//
//				float f = f6;
//				float f2 = f8;
//
//				this.body_front.yRot = f * 0.008f * fs;
//				if (this.body_front.yRot > 0.4f) {
//					this.body_front.yRot = 0.4f;
//				}
//				if (this.body_front.yRot < -0.4f) {
//					this.body_front.yRot = -0.4f;
//				}
//				this.body_front.xRot = f2 * 0.003f;
//				if (this.body_front.xRot > 0.4f) {
//					this.body_front.xRot = 0.4f;
//				}
//				if (this.body_front.xRot < -0.4f) {
//					this.body_front.xRot = -0.4f;
//				}
//				this.body_hind_1.yRot = f * 0.008f * fs;
//				if (this.body_hind_1.yRot > 0.4f) {
//					this.body_hind_1.yRot = 0.4f;
//				}
//				if (this.body_hind_1.yRot < -0.4f) {
//					this.body_hind_1.yRot = -0.4f;
//				}
//				this.body_hind_2.yRot = f * 0.008f * fs;
//				if (this.body_hind_2.yRot > 0.4f) {
//					this.body_hind_2.yRot = 0.4f;
//				}
//				if (this.body_hind_2.yRot < -0.4f) {
//					this.body_hind_2.yRot = -0.4f;
//				}
//				this.body_hind_3.yRot = f * 0.008f * fs;
//				if (this.body_hind_3.yRot > 0.4f) {
//					this.body_hind_3.yRot = 0.4f;
//				}
//				if (this.body_hind_3.yRot < -0.4f) {
//					this.body_hind_3.yRot = -0.4f;
//				}
//				this.tail.yRot = f * 0.008f * fs;
//				if (this.tail.yRot > 0.4f) {
//					this.tail.yRot = 0.4f;
//				}
//				if (this.tail.yRot < -0.4f) {
//					this.tail.yRot = -0.4f;
//				}
//			}
//
//		}
//	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}

