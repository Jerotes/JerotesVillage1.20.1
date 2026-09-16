package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.VariantZsieinAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelvariant_zsiein<T extends OldVariantZsieinEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "variant_zsiein"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart front_body;
	private final ModelPart back;
	private final ModelPart head;

	public Modelvariant_zsiein(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.front_body = this.body.getChild("front_body");
		this.back = this.front_body.getChild("back");
		this.head = this.back.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, -11.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.9397F, -0.0085F));

		PartDefinition front_body_r1 = front_body.addOrReplaceChild("front_body_r1", CubeListBuilder.create().texOffs(28, 236).addBox(-3.0F, -0.5F, -9.5F, 6.0F, 1.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.875F, 1.5F, -2.5744F, 0.0F, -3.1416F));

		PartDefinition front_body_r2 = front_body.addOrReplaceChild("front_body_r2", CubeListBuilder.create().texOffs(142, 248).addBox(-5.0F, -12.5F, -5.5F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(92, 243).addBox(-7.0F, -11.5F, -7.5F, 14.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 104).addBox(-10.0F, -9.5F, -13.5F, 20.0F, 19.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.125F, 1.5F, -0.5672F, 0.0F, 0.0F));

		PartDefinition right_middle_leg = front_body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offset(-11.0F, 9.9397F, 1.0085F));

		PartDefinition right_middle_leg_r1 = right_middle_leg.addOrReplaceChild("right_middle_leg_r1", CubeListBuilder.create().texOffs(158, 0).addBox(-2.5F, 8.0F, -4.5F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(200, 183).addBox(-2.5F, -8.0F, -4.5F, 5.0F, 16.0F, 9.0F, new CubeDeformation(0.125F))
				.texOffs(200, 183).addBox(-2.5F, -8.0F, -4.5F, 5.0F, 16.0F, 9.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.0F, 6.0F, 0.0F, -0.214F, 0.131F, 0.1298F));

		PartDefinition right_middle_leg_r2 = right_middle_leg.addOrReplaceChild("right_middle_leg_r2", CubeListBuilder.create().texOffs(154, 167).addBox(-1.5F, -8.0F, -3.5F, 3.0F, 16.0F, 7.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-0.0716F, -7.3402F, 0.8506F, 0.1613F, 0.101F, -0.0029F));

		PartDefinition right_middle_leg_1 = right_middle_leg.addOrReplaceChild("right_middle_leg_1", CubeListBuilder.create(), PartPose.offset(-3.0F, 13.0F, -1.0F));

		PartDefinition right_middle_leg_1_r1 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_1_r1", CubeListBuilder.create().texOffs(212, 208).addBox(-2.5F, -10.0F, -3.0F, 5.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.1304F, -0.0114F, 0.0865F));

		PartDefinition right_middle_leg_2 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_2", CubeListBuilder.create().texOffs(196, 234).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 14.0F, 1.0F));

		PartDefinition left_middle_leg = front_body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offset(11.0F, 9.9397F, 1.0085F));

		PartDefinition left_hind_leg_r1 = left_middle_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(158, 0).mirror().addBox(-2.5F, 8.0F, -4.5F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(200, 183).mirror().addBox(-2.5F, -8.0F, -4.5F, 5.0F, 16.0F, 9.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offsetAndRotation(1.0F, 6.0F, 0.0F, -0.214F, -0.131F, -0.1298F));

		PartDefinition left_hind_leg_r2 = left_middle_leg.addOrReplaceChild("left_hind_leg_r2", CubeListBuilder.create().texOffs(154, 167).mirror().addBox(-1.5F, -8.0F, -3.5F, 3.0F, 16.0F, 7.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offsetAndRotation(0.0716F, -7.3402F, 0.8506F, 0.1613F, -0.101F, 0.0029F));

		PartDefinition left_middle_leg_1 = left_middle_leg.addOrReplaceChild("left_middle_leg_1", CubeListBuilder.create(), PartPose.offset(3.0F, 13.0F, -1.0F));

		PartDefinition left_middle_leg_1_r1 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_1_r1", CubeListBuilder.create().texOffs(212, 208).mirror().addBox(-2.5F, -10.0F, -3.0F, 5.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.1304F, 0.0114F, -0.0865F));

		PartDefinition left_middle_leg_2 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_2", CubeListBuilder.create().texOffs(196, 234).mirror().addBox(-3.5F, -1.0F, -4.0F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 14.0F, 1.0F));

		PartDefinition back = front_body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(78, 58).addBox(-13.0F, -34.0584F, -6.4406F, 26.0F, 17.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(86, 128).addBox(-9.0F, -17.0584F, 2.5594F, 18.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(84, 29).addBox(-9.0F, -20.8084F, -3.4406F, 18.0F, 21.0F, 8.0F, new CubeDeformation(0.1F))
				.texOffs(78, 94).addBox(-11.0F, -9.8084F, -4.4406F, 22.0F, 6.0F, 10.0F, new CubeDeformation(0.1F))
				.texOffs(86, 110).addBox(-9.0F, 0.1916F, -3.4406F, 18.0F, 18.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 244).addBox(-2.0F, -36.0584F, -7.4406F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 244).addBox(-2.0F, -36.0584F, 2.5594F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.4981F, -11.5509F));

		PartDefinition neck_r1 = back.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 244).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0584F, 13.5594F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r2 = back.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(0, 244).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -21.0584F, 13.5594F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r3 = back.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(0, 244).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0584F, 13.5594F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r4 = back.addOrReplaceChild("neck_r4", CubeListBuilder.create().texOffs(84, 0).addBox(-5.0F, -2.0F, -6.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0584F, -0.4406F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head = back.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.8F, -11.9F, 12.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-7.0F, -4.8F, -12.9F, 14.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -32.2584F, -1.5406F));

		PartDefinition left_bottom_horn_r1 = head.addOrReplaceChild("left_bottom_horn_r1", CubeListBuilder.create().texOffs(0, 74).mirror().addBox(-1.0F, -5.5F, -2.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, -1.3F, -9.9F, -0.2956F, -0.0779F, -0.2502F));

		PartDefinition right_bottom_horn_r1 = head.addOrReplaceChild("right_bottom_horn_r1", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, -5.5F, -2.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -1.3F, -9.9F, -0.2956F, 0.0779F, 0.2502F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -9.0F, -1.5F, 6.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.7F, -13.4F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_tentacle = head.addOrReplaceChild("right_tentacle", CubeListBuilder.create(), PartPose.offset(-5.5679F, 5.2493F, -9.0631F));

		PartDefinition right_tentacle_r1 = right_tentacle.addOrReplaceChild("right_tentacle_r1", CubeListBuilder.create().texOffs(0, 59).addBox(-1.5F, -7.5F, -2.5F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5128F, 19.5726F, -0.6899F, -0.258F, -0.045F, 0.0058F));

		PartDefinition right_tentacle_r2 = right_tentacle.addOrReplaceChild("right_tentacle_r2", CubeListBuilder.create().texOffs(0, 39).addBox(-1.5F, -10.0F, -2.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9321F, 8.9507F, 0.6631F, 0.0F, 0.0F, 0.1745F));

		PartDefinition left_tentacle = head.addOrReplaceChild("left_tentacle", CubeListBuilder.create(), PartPose.offset(5.5679F, 5.2493F, -9.0631F));

		PartDefinition left_tentacle_r1 = left_tentacle.addOrReplaceChild("left_tentacle_r1", CubeListBuilder.create().texOffs(0, 59).mirror().addBox(-1.5F, -7.5F, -2.5F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5128F, 19.5726F, -0.6899F, -0.258F, 0.045F, -0.0058F));

		PartDefinition left_tentacle_r2 = left_tentacle.addOrReplaceChild("left_tentacle_r2", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-1.5F, -10.0F, -2.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.9321F, 8.9507F, 0.6631F, 0.0F, 0.0F, -0.1745F));

		PartDefinition right_teeth = head.addOrReplaceChild("right_teeth", CubeListBuilder.create(), PartPose.offset(-2.6451F, 9.1436F, -8.4F));

		PartDefinition right_teeth_r1 = right_teeth.addOrReplaceChild("right_teeth_r1", CubeListBuilder.create().texOffs(0, 89).addBox(-1.0F, -5.0F, -2.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_teeth = head.addOrReplaceChild("left_teeth", CubeListBuilder.create(), PartPose.offset(2.6451F, 9.1436F, -8.4F));

		PartDefinition left_teeth_r1 = left_teeth.addOrReplaceChild("left_teeth_r1", CubeListBuilder.create().texOffs(0, 89).mirror().addBox(-1.0F, -5.0F, -2.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition snoic = head.addOrReplaceChild("snoic", CubeListBuilder.create().texOffs(0, 172).addBox(-32.0F, -33.0F, -34.0F, 64.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.7F, 13.1F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, -4.9927F, -3.7221F));

		PartDefinition hair_r1 = hair.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(107, 12).addBox(-10.0F, 0.0F, -0.5F, 20.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.378F, -0.6864F, 0.7418F, 0.0F, 0.0F));

		PartDefinition hair_1 = hair.addOrReplaceChild("hair_1", CubeListBuilder.create(), PartPose.offset(0.0F, -6.7693F, 6.9787F));

		PartDefinition hair_1_r1 = hair_1.addOrReplaceChild("hair_1_r1", CubeListBuilder.create().texOffs(106, 0).addBox(-10.0F, 0.0F, 10.5F, 20.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.1472F, -7.6651F, 0.7418F, 0.0F, 0.0F));

		PartDefinition right_front_leg = back.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-9.5146F, -22.9034F, 3.5788F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(207, 14).addBox(-1.5F, -5.0F, -3.5F, 3.0F, 10.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-17.9774F, -12.8662F, -0.2112F, 0.0F, 0.0F, 0.0873F));

		PartDefinition right_front_leg_r2 = right_front_leg.addOrReplaceChild("right_front_leg_r2", CubeListBuilder.create().texOffs(216, 0).addBox(-4.5F, -0.5F, -5.5F, 9.0F, 1.0F, 11.0F, new CubeDeformation(0.0125F)), PartPose.offsetAndRotation(-16.1001F, -18.3732F, -0.6862F, 0.0715F, 0.05F, -0.6091F));

		PartDefinition right_front_leg_r3 = right_front_leg.addOrReplaceChild("right_front_leg_r3", CubeListBuilder.create().texOffs(188, 0).addBox(-2.5F, -10.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0125F)), PartPose.offsetAndRotation(-13.0279F, -17.4759F, -0.6077F, 0.0715F, 0.05F, -0.6091F));

		PartDefinition right_front_leg_r4 = right_front_leg.addOrReplaceChild("right_front_leg_r4", CubeListBuilder.create().texOffs(206, 57).addBox(-5.0F, -5.0F, -7.5F, 10.0F, 10.0F, 15.0F, new CubeDeformation(0.0125F)), PartPose.offsetAndRotation(-9.4224F, -11.4779F, -0.083F, 0.0715F, 0.05F, -0.6091F));

		PartDefinition right_front_leg_r5 = right_front_leg.addOrReplaceChild("right_front_leg_r5", CubeListBuilder.create().texOffs(175, 36).addBox(-4.0F, 5.0F, -6.5F, 8.0F, 8.0F, 13.0F, new CubeDeformation(0.1125F))
				.texOffs(217, 30).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 16.0F, 11.0F, new CubeDeformation(0.0125F)), PartPose.offsetAndRotation(-4.8338F, -4.9496F, 0.4882F, 0.0715F, 0.05F, -0.6091F));

		PartDefinition right_knife_wing = right_front_leg.addOrReplaceChild("right_knife_wing", CubeListBuilder.create(), PartPose.offset(-20.0279F, -29.4759F, -0.6077F));

		PartDefinition right_knife_wing_r1 = right_knife_wing.addOrReplaceChild("right_knife_wing_r1", CubeListBuilder.create().texOffs(0, 146).addBox(-40.5F, -18.0F, -0.5F, 50.0F, 13.0F, 0.0F, new CubeDeformation(0.0125F))
				.texOffs(65, 18).addBox(-18.5F, -18.0F, -1.5F, 22.0F, 8.0F, 3.0F, new CubeDeformation(0.0125F)), PartPose.offsetAndRotation(7.0F, 12.0F, 0.0F, 0.0715F, 0.05F, -0.6091F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create(), PartPose.offset(-14.6815F, -13.0377F, 0.7888F));

		PartDefinition right_front_leg_1_r1 = right_front_leg_1.addOrReplaceChild("right_front_leg_1_r1", CubeListBuilder.create().texOffs(192, 82).addBox(-1.0F, 1.5F, -8.0F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(192, 100).addBox(-2.0F, -0.5F, -7.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(194, 120).addBox(-4.0F, -3.5F, -6.0F, 8.0F, 13.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(226, 83).addBox(-3.0F, -17.5F, -5.0F, 6.0F, 35.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1961F, 15.7586F, 0.5F, 0.0F, 0.0F, 0.0873F));

		PartDefinition right_front_leg_2 = right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create(), PartPose.offset(-2.8962F, 32.2638F, -0.3349F));

		PartDefinition right_front_leg_2_r1 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r1", CubeListBuilder.create().texOffs(212, 144).addBox(-1.5F, -3.5F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4077F, -6.741F, 0.9242F, -0.0869F, -0.0076F, -0.0869F));

		PartDefinition right_front_leg_2_r2 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r2", CubeListBuilder.create().texOffs(232, 127).addBox(-2.5F, -6.5F, -3.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4077F, 4.259F, -0.0758F, -0.0843F, -0.0226F, -0.2608F));

		PartDefinition right_front_leg_2_r3 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r3", CubeListBuilder.create().texOffs(190, 159).addBox(-29.0F, -13.0F, -5.5F, 8.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.0923F, 11.7343F, -0.6423F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_front_leg_2_r4 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r4", CubeListBuilder.create().texOffs(172, 190).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8534F, 19.9711F, -3.8266F, -0.0807F, -0.0334F, -0.3914F));

		PartDefinition right_front_leg_2_r5 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r5", CubeListBuilder.create().texOffs(170, 203).addBox(-2.5F, -4.0F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2229F, 19.9399F, 0.7618F, -0.0852F, -0.0189F, -0.2174F));

		PartDefinition right_front_leg_2_r6 = right_front_leg_2.addOrReplaceChild("right_front_leg_2_r6", CubeListBuilder.create().texOffs(228, 147).addBox(-2.5F, -13.0F, -4.5F, 5.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5923F, 11.7343F, -0.6423F, -0.0865F, -0.0114F, -0.1304F));

		PartDefinition right_knife = right_front_leg_2.addOrReplaceChild("right_knife", CubeListBuilder.create(), PartPose.offset(2.5923F, 18.7343F, -0.6423F));

		PartDefinition right_knife_r1 = right_knife.addOrReplaceChild("right_knife_r1", CubeListBuilder.create().texOffs(0, 127).addBox(-1.5F, -1.0F, -4.5F, 0.0F, 13.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, -0.0865F, -0.0114F, -0.1304F));

		PartDefinition left_front_leg = back.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(9.5146F, -22.9034F, 3.5788F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(207, 14).mirror().addBox(-1.5F, -5.0F, -3.5F, 3.0F, 10.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(16.9774F, -12.8662F, -0.2112F, 0.0F, 0.0F, -0.0873F));

		PartDefinition left_front_leg_r2 = left_front_leg.addOrReplaceChild("left_front_leg_r2", CubeListBuilder.create().texOffs(216, 0).mirror().addBox(-4.5F, -0.5F, -5.5F, 9.0F, 1.0F, 11.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offsetAndRotation(16.1001F, -18.3732F, -0.6862F, 0.0715F, -0.05F, 0.6091F));

		PartDefinition left_front_leg_r3 = left_front_leg.addOrReplaceChild("left_front_leg_r3", CubeListBuilder.create().texOffs(188, 0).mirror().addBox(-3.5F, -10.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offsetAndRotation(13.0279F, -17.4759F, -0.6077F, 0.0715F, -0.05F, 0.6091F));

		PartDefinition left_front_leg_r4 = left_front_leg.addOrReplaceChild("left_front_leg_r4", CubeListBuilder.create().texOffs(206, 57).mirror().addBox(-5.0F, -5.0F, -7.5F, 10.0F, 10.0F, 15.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offsetAndRotation(9.4224F, -11.4779F, -0.083F, 0.0715F, -0.05F, 0.6091F));

		PartDefinition left_front_leg_r5 = left_front_leg.addOrReplaceChild("left_front_leg_r5", CubeListBuilder.create().texOffs(175, 36).mirror().addBox(-4.0F, 5.0F, -6.5F, 8.0F, 8.0F, 13.0F, new CubeDeformation(0.1125F)).mirror(false)
				.texOffs(217, 30).mirror().addBox(-3.0F, -3.0F, -5.5F, 6.0F, 16.0F, 11.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offsetAndRotation(4.8338F, -4.9496F, 0.4882F, 0.0715F, -0.05F, 0.6091F));

		PartDefinition left_knife_wing = left_front_leg.addOrReplaceChild("left_knife_wing", CubeListBuilder.create(), PartPose.offset(20.0279F, -29.4759F, -0.6077F));

		PartDefinition left_front_leg_r6 = left_knife_wing.addOrReplaceChild("left_front_leg_r6", CubeListBuilder.create().texOffs(0, 146).mirror().addBox(-9.5F, -18.0F, -0.5F, 50.0F, 13.0F, 0.0F, new CubeDeformation(0.0125F)).mirror(false)
				.texOffs(65, 18).mirror().addBox(-3.5F, -18.0F, -1.5F, 22.0F, 8.0F, 3.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 12.0F, 0.0F, 0.0715F, -0.05F, 0.6091F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create(), PartPose.offset(14.6815F, -13.0377F, 0.7888F));

		PartDefinition left_front_leg_1_r1 = left_front_leg_1.addOrReplaceChild("left_front_leg_1_r1", CubeListBuilder.create().texOffs(192, 82).mirror().addBox(-1.0F, 1.5F, -8.0F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(192, 100).mirror().addBox(-2.0F, -0.5F, -7.0F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(194, 120).mirror().addBox(-4.0F, -3.5F, -6.0F, 8.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(226, 83).mirror().addBox(-3.0F, -17.5F, -5.0F, 6.0F, 35.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1961F, 15.7586F, 0.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition left_front_leg_2 = left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create(), PartPose.offset(2.8962F, 32.2638F, -0.3349F));

		PartDefinition left_front_leg_2_r1 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r1", CubeListBuilder.create().texOffs(212, 144).mirror().addBox(-1.5F, -3.5F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.4077F, -6.741F, 0.9242F, -0.0869F, 0.0076F, 0.0869F));

		PartDefinition left_front_leg_2_r2 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r2", CubeListBuilder.create().texOffs(232, 127).mirror().addBox(-2.5F, -6.5F, -3.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.4077F, 4.259F, -0.0758F, -0.0843F, 0.0226F, 0.2608F));

		PartDefinition left_front_leg_2_r3 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r3", CubeListBuilder.create().texOffs(190, 159).mirror().addBox(21.0F, -13.0F, -5.5F, 8.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.0923F, 11.7343F, -0.6423F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg_2_r4 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r4", CubeListBuilder.create().texOffs(172, 190).mirror().addBox(-2.5F, -5.0F, -1.5F, 5.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.8534F, 19.9711F, -3.8266F, -0.0807F, 0.0334F, 0.3914F));

		PartDefinition left_front_leg_2_r5 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r5", CubeListBuilder.create().texOffs(170, 203).mirror().addBox(-2.5F, -4.0F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.2229F, 19.9399F, 0.7618F, -0.0852F, 0.0189F, 0.2174F));

		PartDefinition left_front_leg_2_r6 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r6", CubeListBuilder.create().texOffs(228, 147).mirror().addBox(-2.5F, -13.0F, -4.5F, 5.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5923F, 11.7343F, -0.6423F, -0.0865F, 0.0114F, 0.1304F));

		PartDefinition left_knife = left_front_leg_2.addOrReplaceChild("left_knife", CubeListBuilder.create(), PartPose.offset(-2.5923F, 18.7343F, -0.6423F));

		PartDefinition left_knife_r1 = left_knife.addOrReplaceChild("left_knife_r1", CubeListBuilder.create().texOffs(0, 127).mirror().addBox(1.5F, -1.0F, -4.5F, 0.0F, 13.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, -0.0865F, 0.0114F, 0.1304F));

		PartDefinition hind_body = body.addOrReplaceChild("hind_body", CubeListBuilder.create().texOffs(14, 72).addBox(-10.0F, -6.3639F, 0.8002F, 20.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3002F, 20.8639F));

		PartDefinition hind_body_r1 = hind_body.addOrReplaceChild("hind_body_r1", CubeListBuilder.create().texOffs(78, 238).addBox(-2.0F, -7.5F, -1.5F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.5222F, -3.6004F, 1.3526F, 0.0F, 0.0F));

		PartDefinition hind_body_r2 = hind_body.addOrReplaceChild("hind_body_r2", CubeListBuilder.create().texOffs(16, 40).addBox(-9.0F, -8.0F, -8.0F, 18.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.1361F, -4.6998F, -0.2182F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = hind_body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-10.6282F, 1.9809F, 6.4231F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(188, 193).addBox(-0.5F, -8.5F, -2.5F, 1.0F, 17.0F, 5.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-4.6282F, 7.2811F, 0.287F, 0.3927F, 0.0F, 0.2618F));

		PartDefinition right_hind_leg_r2 = right_hind_leg.addOrReplaceChild("right_hind_leg_r2", CubeListBuilder.create().texOffs(228, 182).addBox(-2.5F, -8.0F, -4.5F, 5.0F, 16.0F, 9.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.3718F, 6.7189F, -0.287F, 0.3927F, 0.0F, 0.2618F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-2.3718F, 12.7189F, 0.713F));

		PartDefinition right_hind_leg_1_r1 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_1_r1", CubeListBuilder.create().texOffs(234, 207).addBox(-15.5F, -10.0F, -3.0F, 5.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 7.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_hind_leg_2 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create().texOffs(226, 233).addBox(-3.5F, -1.5F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.5F, -1.0F));

		PartDefinition left_hind_leg = hind_body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(10.6282F, 1.9809F, 6.4231F));

		PartDefinition left_hind_leg_r3 = left_hind_leg.addOrReplaceChild("left_hind_leg_r3", CubeListBuilder.create().texOffs(188, 193).mirror().addBox(-0.5F, -8.5F, -2.5F, 1.0F, 17.0F, 5.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offsetAndRotation(4.6282F, 7.2811F, 0.287F, 0.3927F, 0.0F, -0.2618F));

		PartDefinition left_hind_leg_r4 = left_hind_leg.addOrReplaceChild("left_hind_leg_r4", CubeListBuilder.create().texOffs(228, 182).mirror().addBox(-2.5F, -8.0F, -4.5F, 5.0F, 16.0F, 9.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offsetAndRotation(1.3718F, 6.7189F, -0.287F, 0.3927F, 0.0F, -0.2618F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create(), PartPose.offset(2.3718F, 12.7189F, 0.713F));

		PartDefinition left_hind_leg_1_r1 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_1_r1", CubeListBuilder.create().texOffs(234, 207).mirror().addBox(10.5F, -10.0F, -3.0F, 5.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 7.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition left_hind_leg_2 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create().texOffs(226, 233).mirror().addBox(-3.5F, -1.5F, -4.0F, 7.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 14.5F, -1.0F));

		PartDefinition tail = hind_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -3.603F, 13.0874F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(180, 215).addBox(-4.0F, -3.0F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.2832F, -1.5879F, -0.9686F, 0.0F, 0.0F));

		PartDefinition right_tail = tail.addOrReplaceChild("right_tail", CubeListBuilder.create(), PartPose.offset(-0.75F, 5.6212F, -3.8055F));

		PartDefinition right_tail_r1 = right_tail.addOrReplaceChild("right_tail_r1", CubeListBuilder.create().texOffs(174, 165).addBox(-2.0275F, -10.0666F, 0.3182F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0581F, 10.9654F, -26.2717F, -0.822F, 0.1541F, 0.0833F));

		PartDefinition right_tail_r2 = right_tail.addOrReplaceChild("right_tail_r2", CubeListBuilder.create().texOffs(180, 241).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 3.0F, -13.0F, -0.9993F, 0.1137F, 0.0661F));

		PartDefinition right_tail_r3 = right_tail.addOrReplaceChild("right_tail_r3", CubeListBuilder.create().texOffs(180, 226).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.0F, -4.5F, -1.0893F, 0.0774F, 0.0404F));

		PartDefinition left_tail = tail.addOrReplaceChild("left_tail", CubeListBuilder.create(), PartPose.offset(4.75F, 5.6212F, -3.8055F));

		PartDefinition left_tail_r1 = left_tail.addOrReplaceChild("left_tail_r1", CubeListBuilder.create().texOffs(174, 165).mirror().addBox(-1.9725F, -10.0666F, 0.3182F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0581F, 10.9654F, -26.2717F, -0.822F, -0.1541F, -0.0833F));

		PartDefinition left_tail_r2 = left_tail.addOrReplaceChild("left_tail_r2", CubeListBuilder.create().texOffs(180, 241).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 3.0F, -13.0F, -0.9993F, -0.1137F, -0.0661F));

		PartDefinition left_tail_r3 = left_tail.addOrReplaceChild("left_tail_r3", CubeListBuilder.create().texOffs(180, 226).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -2.0F, -4.5F, -1.0893F, -0.0774F, -0.0404F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(VariantZsieinAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(((OldVariantZsieinEntity)t).idleAnimationState, VariantZsieinAnimation.IDLE, f3);
		this.animate(((OldVariantZsieinEntity)t).attack1AnimationState, VariantZsieinAnimation.ATTACK1, f3);
		this.animate(((OldVariantZsieinEntity)t).awayAnimationState, VariantZsieinAnimation.AWAY, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}