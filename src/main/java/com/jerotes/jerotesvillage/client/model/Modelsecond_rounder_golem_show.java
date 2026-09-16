package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;


public class Modelsecond_rounder_golem_show<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID,
			"second_rounder_golem_show"), "main");
	private final ModelPart root;

	public Modelsecond_rounder_golem_show(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -49.0434F, -1.3435F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create().texOffs(1, 80).addBox(-11.75F, -36.7066F, -8.8065F, 24.0F, 14.0F, 14.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition body_r1 = up_body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(68, 164).addBox(-12.5F, -0.5F, -7.5F, 26.0F, 3.0F, 14.0F, new CubeDeformation(0.5F))
				.texOffs(0, 139).addBox(-10.5F, -6.5F, -6.5F, 22.0F, 14.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -2.221F, 3.3319F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_r2 = up_body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 165).mirror().addBox(-8.5F, -5.5F, -8.5F, 18.0F, 12.0F, 16.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(19.4329F, -26.9092F, 1.9435F, 0.0447F, 0.0094F, 0.218F));

		PartDefinition body_r3 = up_body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(0, 165).addBox(-8.5F, -5.5F, -7.5F, 18.0F, 12.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-19.4329F, -26.9092F, 0.9435F, 0.0447F, -0.0094F, -0.218F));

		PartDefinition body_r4 = up_body.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(68, 140).addBox(-18.0F, -12.0F, 0.0F, 36.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.176F, -9.5351F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_r5 = up_body.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(0, 34).addBox(-18.0F, -12.0F, -11.0F, 36.0F, 24.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.2262F, 2.0569F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head = up_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 18.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(96, 18).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.25F))
				.texOffs(64, 16).addBox(-12.0F, -16.0F, -8.5F, 24.0F, 18.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 109).addBox(-9.85F, -7.85F, -3.65F, 20.0F, 18.0F, 12.0F, new CubeDeformation(0.15F))
				.texOffs(64, 109).addBox(-9.88F, -18.08F, -3.62F, 20.0F, 10.0F, 12.0F, new CubeDeformation(0.11F)), PartPose.offset(0.0F, -32.9566F, -3.6565F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(256, 140).addBox(-9.0F, 2.0F, -7.5F, 16.0F, 12.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offset(-23.0F, -22.9566F, 1.3435F));

		PartDefinition right_shoulder_r1 = right_arm.addOrReplaceChild("right_shoulder_r1", CubeListBuilder.create().texOffs(256, 166).addBox(-8.0F, -5.0F, -6.0F, 16.0F, 10.0F, 14.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.9F, -1.9F, -0.6F, 0.0F, 0.0F, -0.3054F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(256, 0).addBox(-7.0F, -17.0F, -7.0F, 10.0F, 34.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.8F, 0.0F, 0.0F, 0.2618F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(304, 0).addBox(-5.6F, 4.5802F, -5.6726F, 12.0F, 34.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(316, 156).addBox(-5.6F, -3.1198F, -5.6726F, 12.0F, 20.0F, 14.0F, new CubeDeformation(0.5F))
				.texOffs(302, 128).addBox(-6.6F, -3.1198F, -6.6726F, 14.0F, 10.0F, 16.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(-5.0F, 22.5F, -1.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition sword = right_hand.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(164, 82).addBox(-7.0F, 15.475F, -1.0F, 14.0F, 72.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(148, 124).addBox(-2.0F, 15.475F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(156, 116).addBox(-1.0F, 87.475F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(148, 156).addBox(-9.0F, 7.475F, -3.0F, 18.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(180, 170).addBox(-3.0F, -10.525F, -1.0F, 6.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(164, 176).addBox(-2.9F, -4.425F, -1.1F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(160, 170).addBox(-3.9F, -14.425F, -1.1F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(152, 170).addBox(-3.9F, -16.825F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(152, 170).addBox(2.1F, -16.825F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.0F, 32.025F, 1.0F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition sword_r1 = sword.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(152, 110).addBox(-2.05F, -2.05F, -0.95F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 95.675F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition sword_r2 = sword.addOrReplaceChild("sword_r2", CubeListBuilder.create().texOffs(148, 98).mirror().addBox(-2.994F, -4.994F, -1.006F, 6.0F, 10.0F, 2.0F, new CubeDeformation(0.006F)).mirror(false), PartPose.offsetAndRotation(2.0F, 90.475F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition sword_r3 = sword.addOrReplaceChild("sword_r3", CubeListBuilder.create().texOffs(148, 98).addBox(-2.995F, -4.995F, -1.005F, 6.0F, 10.0F, 2.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-2.0F, 90.475F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition sword_r4 = sword.addOrReplaceChild("sword_r4", CubeListBuilder.create().texOffs(136, 82).mirror().addBox(-1.95F, -18.95F, -1.05F, 4.0F, 38.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(6.0F, 34.475F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition sword_r5 = sword.addOrReplaceChild("sword_r5", CubeListBuilder.create().texOffs(136, 82).addBox(-1.95F, -18.95F, -1.05F, 4.0F, 38.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-6.2F, 34.475F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(256, 140).mirror().addBox(-7.0F, 2.0F, -8.5F, 16.0F, 12.0F, 14.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(23.0F, -22.9566F, 1.3435F));

		PartDefinition left_shoulder_r1 = left_arm.addOrReplaceChild("left_shoulder_r1", CubeListBuilder.create().texOffs(256, 166).mirror().addBox(-8.0F, -5.0F, -7.0F, 16.0F, 10.0F, 14.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(3.1F, -1.9F, 0.4F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(256, 0).mirror().addBox(-3.0F, -17.0F, -7.0F, 10.0F, 34.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 10.0F, 0.8F, 0.0F, 0.0F, -0.2618F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(304, 0).mirror().addBox(-5.5F, 4.5802F, -5.8726F, 12.0F, 34.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(316, 156).mirror().addBox(-5.4F, -3.1198F, -5.6726F, 12.0F, 20.0F, 14.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(302, 128).mirror().addBox(-6.4F, -3.1198F, -6.6726F, 14.0F, 10.0F, 16.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(5.0F, 22.5F, -1.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition swordback = up_body.addOrReplaceChild("swordback", CubeListBuilder.create().texOffs(224, 82).addBox(-6.9857F, -18.8857F, -1.0F, 14.0F, 72.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(208, 124).addBox(-1.9857F, -18.8857F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(216, 116).addBox(-0.9857F, 53.1143F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(208, 156).addBox(-8.9857F, -26.8857F, -3.0F, 18.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(240, 170).addBox(-2.9857F, -44.8857F, -1.0F, 6.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(224, 176).addBox(-2.8857F, -38.7857F, -1.1F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(220, 170).addBox(-3.8857F, -48.7857F, -1.1F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(212, 170).addBox(-3.8857F, -51.1857F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(212, 170).addBox(2.1143F, -51.1857F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.0052F, -12.8605F, 14.0411F, 0.1309F, 0.0F, -0.3491F));

		PartDefinition sword_r6 = swordback.addOrReplaceChild("sword_r6", CubeListBuilder.create().texOffs(212, 110).addBox(-2.05F, -2.05F, -0.95F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0143F, 61.3143F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition sword_r7 = swordback.addOrReplaceChild("sword_r7", CubeListBuilder.create().texOffs(208, 98).mirror().addBox(-2.994F, -4.994F, -1.006F, 6.0F, 10.0F, 2.0F, new CubeDeformation(0.106F)).mirror(false), PartPose.offsetAndRotation(2.0143F, 56.1143F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition sword_r8 = swordback.addOrReplaceChild("sword_r8", CubeListBuilder.create().texOffs(208, 98).addBox(-2.995F, -4.995F, -1.005F, 6.0F, 10.0F, 2.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-1.9857F, 56.1143F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition sword_r9 = swordback.addOrReplaceChild("sword_r9", CubeListBuilder.create().texOffs(196, 82).mirror().addBox(-1.95F, -18.95F, -1.05F, 4.0F, 38.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(6.0143F, 0.1143F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition sword_r10 = swordback.addOrReplaceChild("sword_r10", CubeListBuilder.create().texOffs(196, 82).addBox(-1.95F, -18.95F, -1.05F, 4.0F, 38.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-6.1857F, 0.1143F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition down_body = body.addOrReplaceChild("down_body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition down_body_clothes_r1 = down_body.addOrReplaceChild("down_body_clothes_r1", CubeListBuilder.create().texOffs(256, 48).mirror().addBox(-10.0F, -29.5F, -7.0F, 20.0F, 59.0F, 14.0F, new CubeDeformation(0.9F)).mirror(false), PartPose.offsetAndRotation(8.4186F, 29.073F, 3.3435F, 0.0F, 0.0F, -0.1309F));

		PartDefinition down_body_clothes_r2 = down_body.addOrReplaceChild("down_body_clothes_r2", CubeListBuilder.create().texOffs(256, 48).addBox(-3.5F, -7.5F, -7.5F, 20.0F, 59.0F, 14.0F, new CubeDeformation(0.905F))
				.texOffs(368, 154).addBox(-3.5F, -7.5F, -7.5F, 8.0F, 22.0F, 14.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-12.0F, 6.5434F, 3.8435F, 0.0F, 0.0F, 0.1309F));

		PartDefinition down_body_r1 = down_body.addOrReplaceChild("down_body_r1", CubeListBuilder.create().texOffs(368, 154).mirror().addBox(-3.5F, -7.5F, -7.5F, 8.0F, 22.0F, 14.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offsetAndRotation(11.0F, 6.5434F, 3.8435F, 0.0F, 0.0F, -0.1309F));

		PartDefinition body_armor = down_body.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(412, 168).addBox(-6.0F, -0.6227F, -4.4214F, 12.0F, 8.0F, 14.0F, new CubeDeformation(1.0F))
				.texOffs(412, 148).addBox(-6.0F, 8.3773F, -4.9214F, 12.0F, 6.0F, 14.0F, new CubeDeformation(0.75F))
				.texOffs(412, 130).addBox(-6.0F, 15.3773F, -4.9214F, 12.0F, 4.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.3339F, 0.2649F));

		PartDefinition body_r6 = body_armor.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(464, 131).addBox(-9.0F, -20.0F, 0.0F, 18.0F, 59.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 18.3773F, -6.3214F, -0.0436F, 0.0F, 0.0F));

		PartDefinition right_leg = down_body.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-8.0F, 4.0434F, 1.3435F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(168, 0).addBox(-6.0F, -12.0F, -5.0F, 12.0F, 37.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 8.0F, 1.0F, -0.0698F, 0.2269F, 0.0698F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(-4.0F, 31.0F, 0.0F));

		PartDefinition right_feet_r1 = right_foot.addOrReplaceChild("right_feet_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-2.0F, -3.0F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 31.2F, -6.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition right_feet_r2 = right_foot.addOrReplaceChild("right_feet_r2", CubeListBuilder.create().texOffs(104, 0).addBox(-5.0F, -3.5F, -1.0F, 10.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.3542F, 30.35F, -6.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition right_feet_r3 = right_foot.addOrReplaceChild("right_feet_r3", CubeListBuilder.create().texOffs(212, 0).addBox(-6.0F, -17.0F, -4.5F, 12.0F, 34.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(1.0F, 16.5F, -0.5F, 0.0F, 0.1309F, 0.0F));

		PartDefinition right_feet_armor = right_foot.addOrReplaceChild("right_feet_armor", CubeListBuilder.create(), PartPose.offset(1.0F, 16.5F, -0.5F));

		PartDefinition right_feet_armor_r1 = right_feet_armor.addOrReplaceChild("right_feet_armor_r1", CubeListBuilder.create().texOffs(188, 50).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(1.7F)), PartPose.offsetAndRotation(-0.6958F, -3.0F, -2.4872F, 0.0F, -0.6545F, 0.0F));

		PartDefinition right_feet_armor_r2 = right_feet_armor.addOrReplaceChild("right_feet_armor_r2", CubeListBuilder.create().texOffs(212, 44).addBox(-16.8412F, -10.0F, -6.4273F, 12.0F, 20.0F, 10.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(11.0F, -3.0F, 0.4957F, 0.0F, 0.1309F, 0.0F));

		PartDefinition left_leg = down_body.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(10.0F, 4.0434F, 1.3435F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(168, 0).mirror().addBox(-6.0F, -12.0F, -5.0F, 12.0F, 37.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 8.0F, 1.0F, -0.0698F, -0.2269F, -0.0698F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(2.0F, 31.0F, 0.0F));

		PartDefinition left_feet_r1 = left_foot.addOrReplaceChild("left_feet_r1", CubeListBuilder.create().texOffs(128, 0).mirror().addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 31.2F, -6.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_feet_r2 = left_foot.addOrReplaceChild("left_feet_r2", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-4.75F, -3.75F, -1.25F, 10.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(104, 0).mirror().addBox(-4.75F, -3.75F, -1.25F, 10.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.1389F, 30.6F, -5.5829F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_feet_r3 = left_foot.addOrReplaceChild("left_feet_r3", CubeListBuilder.create().texOffs(212, 0).mirror().addBox(-6.0F, -17.0F, -4.5F, 12.0F, 34.0F, 10.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 16.5F, -0.5F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_feet_armor = left_foot.addOrReplaceChild("left_feet_armor", CubeListBuilder.create(), PartPose.offset(-1.0F, 16.5F, -0.5F));

		PartDefinition left_feet_armor_r1 = left_feet_armor.addOrReplaceChild("left_feet_armor_r1", CubeListBuilder.create().texOffs(188, 50).mirror().addBox(-3.0F, -9.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(1.7F)).mirror(false), PartPose.offsetAndRotation(0.6958F, -3.0F, -2.4872F, 0.0F, 0.6545F, 0.0F));

		PartDefinition left_feet_armor_r2 = left_feet_armor.addOrReplaceChild("left_feet_armor_r2", CubeListBuilder.create().texOffs(212, 44).mirror().addBox(4.8412F, -10.0F, -6.4273F, 12.0F, 20.0F, 10.0F, new CubeDeformation(1.5F)).mirror(false), PartPose.offsetAndRotation(-11.0F, -3.0F, 0.4957F, 0.0F, -0.1309F, 0.0F));

		PartDefinition cloak = body.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(0, 304).addBox(-24.0F, -24.0F, -24.0F, 48.0F, 48.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(256, 208).addBox(-33.0F, -17.0F, -33.0F, 66.0F, 34.0F, 66.0F, new CubeDeformation(1.0F))
				.texOffs(0, 400).addBox(-40.0F, -16.0F, -40.0F, 80.0F, 32.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0434F, 1.3435F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

}