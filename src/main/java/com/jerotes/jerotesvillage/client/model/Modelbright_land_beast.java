package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BrightLandBeastAnimation;
import com.jerotes.jerotesvillage.entity.Animal.ArchnosedHornbeastEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelbright_land_beast<T extends LivingEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bright_land_beast"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart right_hind_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_front_leg;

	public Modelbright_land_beast(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_front_leg = root.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(308, 216).addBox(-18.0F, -13.0F, -9.0F, 36.0F, 32.0F, 18.0F, new CubeDeformation(-0.05F))
				.texOffs(384, 0).addBox(-18.0F, -13.0F, -37.0F, 36.0F, 30.0F, 28.0F, new CubeDeformation(-0.05F))
				.texOffs(434, 109).addBox(-18.0F, 7.0F, -38.0F, 36.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -74.0F, -67.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(432, 95).addBox(-17.0F, -8.25F, -1.0F, 34.0F, 8.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -11.5F, -36.5F, -0.8727F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 235).addBox(-12.0F, 1.25F, -7.5F, 24.0F, 12.0F, 22.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -15.2568F, -13.2819F, 0.6981F, 0.0F, 0.0F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(94, 233).addBox(-17.0F, -11.25F, -10.25F, 34.0F, 11.0F, 20.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 24.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(388, 58).addBox(-17.0F, -3.5F, -20.0F, 34.0F, 9.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, -17.0F));

		PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 2.0F, 1.9199F, 0.0F, 0.0F));

		PartDefinition right_horn = horns.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-21.0F, -1.4723F, -0.0351F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(72, 479).addBox(-3.5F, -12.0F, -4.5F, 7.0F, 24.0F, 9.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -70.6393F, -60.6938F, 2.7053F, 0.0F, 3.1416F));

		PartDefinition right_horn_r2 = right_horn.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(38, 462).addBox(-3.5F, -20.0F, -5.0F, 7.0F, 40.0F, 10.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -44.5694F, -44.3069F, 2.4871F, 0.0F, 3.1416F));

		PartDefinition right_horn_r3 = right_horn.addOrReplaceChild("right_horn_r3", CubeListBuilder.create().texOffs(0, 460).addBox(-3.5F, -20.0F, -6.0F, 7.0F, 40.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.9041F, -18.598F, 2.3126F, 0.0F, 3.1416F));

		PartDefinition right_horn_up_r1 = right_horn.addOrReplaceChild("right_horn_up_r1", CubeListBuilder.create().texOffs(104, 486).addBox(-3.5F, -6.0F, -7.0F, 7.0F, 12.0F, 14.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 1.6612F, 0.5712F, -2.3126F, 0.0F, 0.0F));

		PartDefinition left_horn = horns.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(21.0F, -1.4723F, -0.0351F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(72, 479).mirror().addBox(-3.5F, -12.0F, -4.5F, 7.0F, 24.0F, 9.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, -70.6393F, -60.6938F, 2.7053F, 0.0F, 3.1416F));

		PartDefinition left_horn_r2 = left_horn.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(38, 462).mirror().addBox(-3.5F, -20.0F, -5.0F, 7.0F, 40.0F, 10.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -44.5694F, -44.3069F, 2.4871F, 0.0F, 3.1416F));

		PartDefinition left_horn_r3 = left_horn.addOrReplaceChild("left_horn_r3", CubeListBuilder.create().texOffs(0, 460).mirror().addBox(-3.5F, -20.0F, -6.0F, 7.0F, 40.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -15.9041F, -18.598F, 2.3126F, 0.0F, -3.1416F));

		PartDefinition left_horn_up_r1 = left_horn.addOrReplaceChild("left_horn_up_r1", CubeListBuilder.create().texOffs(104, 486).mirror().addBox(-3.5F, -6.0F, -7.0F, 7.0F, 12.0F, 14.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.6612F, 0.5712F, -2.3126F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.3333F, -74.6015F, -15.0475F));

		PartDefinition neck_r1 = body.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 269).addBox(-9.0F, -14.75F, -13.5F, 20.0F, 6.0F, 33.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.6667F, -11.9268F, -36.6294F, 0.2618F, 0.0F, 0.0F));

		PartDefinition neck_r2 = body.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(202, 155).addBox(-18.0F, -10.75F, -16.0F, 36.0F, 21.0F, 32.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.3333F, 15.3313F, -34.1169F, -0.2618F, 0.0F, 0.0F));

		PartDefinition neck_r3 = body.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(206, 87).addBox(-18.0F, -30.0F, -14.5F, 36.0F, 36.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, 5.7998F, -33.5038F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(0, 105).addBox(-24.0F, -53.2806F, -23.1284F, 48.0F, 41.0F, 53.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, 9.4506F, 25.6225F, 1.4399F, 0.0F, 0.0F));

		PartDefinition rotation_r1 = rotation.addOrReplaceChild("rotation_r1", CubeListBuilder.create().texOffs(0, 357).addBox(-20.0F, -9.3319F, 6.1264F, 40.0F, 20.0F, 8.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 30.6903F, 5.2251F, -0.48F, 0.0F, 0.0F));

		PartDefinition rotation_r2 = rotation.addOrReplaceChild("rotation_r2", CubeListBuilder.create().texOffs(102, 199).addBox(-20.0F, -12.0F, -5.0F, 40.0F, 24.0F, 10.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, 29.8759F, -31.584F, 0.3054F, 0.0F, 0.0F));

		PartDefinition rotation_r3 = rotation.addOrReplaceChild("rotation_r3", CubeListBuilder.create().texOffs(206, 0).addBox(-20.0F, -21.0F, -22.5F, 40.0F, 42.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.4226F, -8.2153F, -0.0873F, 0.0F, 0.0F));

		PartDefinition rotation_r4 = rotation.addOrReplaceChild("rotation_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -15.6262F, -12.689F, 40.0F, 42.0F, 63.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -3.8591F, -24.4257F, -0.1309F, 0.0F, 0.0F));

		PartDefinition back_r1 = rotation.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(0, 385).addBox(-9.0F, -18.5944F, 24.4028F, 18.0F, 34.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.9875F, -2.6139F, -0.2618F, 0.0F, 0.0F));

		PartDefinition back_r2 = rotation.addOrReplaceChild("back_r2", CubeListBuilder.create().texOffs(0, 308).addBox(-9.0F, -14.0725F, 22.0644F, 18.0F, 40.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -40.2806F, 2.3716F, -0.0873F, 0.0F, 0.0F));

		PartDefinition rotation_r5 = rotation.addOrReplaceChild("rotation_r5", CubeListBuilder.create().texOffs(202, 208).addBox(-19.25F, -20.5F, -9.75F, 36.0F, 41.0F, 17.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, -32.2449F, -22.2136F, 0.0F, 0.2618F, 1.5708F));

		PartDefinition saddle = rotation.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.offset(0.0F, -32.7806F, 3.3716F));

		PartDefinition saddle_r1 = saddle.addOrReplaceChild("saddle_r1", CubeListBuilder.create().texOffs(48, 386).addBox(-21.0F, -19.5F, -10.5F, 42.0F, 41.0F, 21.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 43.3505F, 24.146F, 0.1309F, 0.0F, 0.0F));

		PartDefinition saddle_r2 = saddle.addOrReplaceChild("saddle_r2", CubeListBuilder.create().texOffs(374, 297).addBox(-24.0F, -20.5F, -10.5F, 48.0F, 41.0F, 21.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 2.6505F, 23.846F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail_1 = rotation.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 45.2194F, 7.3716F, -1.0036F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail_1.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 424).addBox(-3.0F, -25.8397F, 3.7845F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.4161F, -12.4104F, -0.2618F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.4161F, -7.4104F));

		PartDefinition tail_r2 = tail_2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(48, 448).addBox(-3.0F, 28.1603F, 3.7845F, 6.0F, 6.0F, 6.0F, new CubeDeformation(1.0F))
				.texOffs(24, 424).addBox(-3.0F, 4.1603F, 3.7845F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -5.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-22.0F, -62.0F, 37.5F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(218, 434).addBox(-7.0F, -21.0F, -15.0F, 20.0F, 48.0F, 30.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-1.0F, 10.7689F, -0.026F, -0.1289F, 0.0227F, 0.1731F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(414, 473).addBox(-10.0F, 41.9171F, -9.2292F, 20.0F, 10.0F, 29.0F, new CubeDeformation(0.1F)), PartPose.offset(-3.0F, 34.5F, -7.0F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(318, 432).addBox(-10.0F, -32.5F, -18.25F, 20.0F, 52.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 31.5F, 9.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(22.0F, -62.0F, 37.5F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(218, 434).mirror().addBox(-13.0F, -21.0F, -15.0F, 20.0F, 48.0F, 30.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(1.0F, 10.7689F, -0.026F, -0.1289F, -0.0227F, -0.1731F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(414, 473).mirror().addBox(-10.0F, 41.9171F, -9.2292F, 20.0F, 10.0F, 29.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(3.0F, 34.5F, -7.0F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(318, 432).mirror().addBox(-10.0F, -32.5F, -18.0F, 20.0F, 52.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 31.5F, 9.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(28.0F, -85.0F, -29.5F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(198, 338).addBox(-10.0F, -28.5F, -16.0F, 20.0F, 57.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 16.0436F, -0.9981F, 0.0865F, 0.0114F, -0.1304F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(406, 384).addBox(-8.6F, 55.4171F, -27.2292F, 20.0F, 10.0F, 33.0F, new CubeDeformation(0.1F)), PartPose.offset(2.0F, 44.0F, 7.5F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(302, 327).addBox(-10.0F, -34.0F, -16.0F, 20.0F, 68.0F, 32.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(1.4F, 30.1786F, -7.9677F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_foot_chest_left = left_front_foot.addOrReplaceChild("left_front_foot_chest_left", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-20.0F, -29.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 44.0F, -18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_front_foot_chest_right = left_front_foot.addOrReplaceChild("left_front_foot_chest_right", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-20.0F, -29.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.5F, 44.0F, -18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-28.0F, -85.0F, -29.5F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(198, 338).mirror().addBox(-10.0F, -28.5F, -16.0F, 20.0F, 57.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 16.0436F, -0.9981F, 0.0865F, -0.0114F, 0.1304F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(406, 384).mirror().addBox(-11.4F, 55.4171F, -27.2292F, 20.0F, 10.0F, 33.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-2.0F, 44.0F, 7.5F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(302, 327).mirror().addBox(-10.0F, -34.0F, -16.0F, 20.0F, 68.0F, 32.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-1.4F, 30.1786F, -7.9677F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_front_foot_chest_right = right_front_foot.addOrReplaceChild("right_front_foot_chest_right", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-20.0F, -29.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.5F, 44.0F, -18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition right_front_foot_chest_left = right_front_foot.addOrReplaceChild("right_front_foot_chest_left", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-20.0F, -29.0F, 0.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 44.0F, -18.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof ArchnosedHornbeastEntity brightLandBeast) {
			if (t.isBaby()) {
				this.head.xScale = 1.2f;
				this.head.yScale = 1.2f;
				this.head.zScale = 1.2f;
			}
			else {
				this.head.xScale = 1f;
				this.head.yScale = 1f;
				this.head.zScale = 1f;
			}
			this.applyHeadRotation(t, f4, f5, f3);
			if (brightLandBeast.getQuakeUseTick() > 0){
				this.animateWalk(BrightLandBeastAnimation.QUAKEWALK, f, f2, 2.0f, 2.0f);
			}
			else  {
				if (brightLandBeast.isVehicle()) {
					this.animateWalk(BrightLandBeastAnimation.WALK, f, f2, 2.0f, 2.0f);
				}
				else {
					if (brightLandBeast.isAggressive()) {
						this.animateWalk(BrightLandBeastAnimation.RUSH, f, f2 * brightLandBeast.getRushAnim() / 40f, brightLandBeast instanceof BrightLandBeastEntity ? 1.25f : 1.5f, brightLandBeast instanceof BrightLandBeastEntity ? 1.25f : 1.5f);
						this.animateWalk(BrightLandBeastAnimation.RUN, f, f2 * (1 - brightLandBeast.getRushAnim() / 40f), brightLandBeast instanceof BrightLandBeastEntity ? 1.25f : 1.5f, brightLandBeast instanceof BrightLandBeastEntity ? 1.25f : 1.5f);
					}
					else {
						this.animateWalk(BrightLandBeastAnimation.WALK, f, f2 * (1 - brightLandBeast.getRushAnim() / 40f), brightLandBeast instanceof BrightLandBeastEntity ? 1.5f : 2f, brightLandBeast instanceof BrightLandBeastEntity ? 1.5f : 2f);
					}
				}
			}
			this.animate(brightLandBeast.attack1AnimationState, BrightLandBeastAnimation.ATTACK1, f3);
			this.animate(brightLandBeast.attack2AnimationState, BrightLandBeastAnimation.ATTACK2, f3);
			this.animate(brightLandBeast.attack3AnimationState, BrightLandBeastAnimation.ATTACK3, f3);
			this.animate(brightLandBeast.attack4AnimationState, BrightLandBeastAnimation.ATTACK4, f3);
			this.animate(brightLandBeast.attack5AnimationState, BrightLandBeastAnimation.ATTACK5, f3);
			this.animate(brightLandBeast.attack6AnimationState, BrightLandBeastAnimation.ATTACK6, f3);
			this.animate(brightLandBeast.idleAnimationState, BrightLandBeastAnimation.IDLE, f3);
			this.animate(brightLandBeast.quakeStartAnimationState, BrightLandBeastAnimation.QUAKESTART, f3);
			this.animate(brightLandBeast.quakeAnimationState, BrightLandBeastAnimation.QUAKE, f3);
			this.animate(brightLandBeast.quakeUseAnimationState, BrightLandBeastAnimation.QUAKEUSE, f3);
			this.animate(brightLandBeast.deadAnimationState, BrightLandBeastAnimation.DEAD, f3);
			this.animate(brightLandBeast.sitAnimationState, BrightLandBeastAnimation.SIT, f3);
			this.animate(brightLandBeast.toSitAnimationState, BrightLandBeastAnimation.TOSIT, f3);
			this.animate(brightLandBeast.stopSitAnimationState, BrightLandBeastAnimation.STOPSIT, f3);
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}


	@Override
	public ModelPart root() {
		return this.root;
	}
}