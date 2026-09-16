package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SecondRounderGolemAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.SecondRounderGolemSwordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;


public class Modelsecond_rounder_golem<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "second_rounder_golem"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart right_hand;
	private final ModelPart left_hand;
	private final ModelPart sword;
	private final ModelPart swordback;
	private final ModelPart cloak;

	public Modelsecond_rounder_golem(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = root.getChild("right_arm");
		this.leftArm = root.getChild("left_arm");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.right_hand = rightArm.getChild("right_hand");
		this.left_hand = leftArm.getChild("left_hand");
		this.sword = right_hand.getChild("sword");
		this.swordback = body.getChild("swordback");
		this.cloak = root.getChild("cloak");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -7.4783F, -4.5283F, 12.0F, 7.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.5217F, -0.6717F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(56, 113).mirror().addBox(-2.0F, -4.0F, -3.5F, 4.0F, 8.0F, 7.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(5.5F, 15.5217F, 1.6717F, 0.0F, 0.0F, -0.2182F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(56, 113).addBox(-2.0F, -4.0F, -3.5F, 4.0F, 8.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-5.5F, 15.5217F, 1.6717F, 0.0F, 0.0F, 0.2182F));

		PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(0, 69).addBox(-5.5F, -3.5F, -3.0F, 11.0F, 7.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 9.8895F, 1.6659F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_r4 = body.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-4.5F, -3.0F, -4.0F, 9.0F, 6.0F, 8.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(9.7164F, -2.4546F, -0.5283F, 0.0873F, 0.0F, 0.2182F));

		PartDefinition body_r5 = body.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(0, 82).addBox(-4.5F, -3.0F, -4.0F, 9.0F, 6.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-9.7164F, -2.4546F, -0.5283F, 0.0873F, 0.0F, -0.2182F));

		PartDefinition body_r6 = body.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(34, 70).addBox(-9.0F, -6.0F, 0.0F, 18.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.912F, -4.7676F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_r7 = body.addOrReplaceChild("body_r7", CubeListBuilder.create().texOffs(0, 17).addBox(-9.0F, -6.0F, -5.5F, 18.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.3869F, 1.0284F, 0.1309F, 0.0F, 0.0F));

		PartDefinition body_armor = body.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(78, 117).addBox(-8.5F, -4.0F, -3.5F, 6.0F, 4.0F, 7.0F, new CubeDeformation(1.0F))
				.texOffs(78, 107).addBox(-8.5F, 0.0F, -3.5F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.75F))
				.texOffs(78, 98).addBox(-8.5F, 4.0F, -3.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offset(5.5F, 15.5217F, 1.6717F));

		PartDefinition swordback = body.addOrReplaceChild("swordback", CubeListBuilder.create().texOffs(112, 41).addBox(-3.4929F, -9.4429F, -0.5F, 7.0F, 36.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(104, 62).addBox(-0.9929F, -9.4429F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(108, 58).addBox(-0.4929F, 26.5571F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(104, 78).addBox(-4.4929F, -13.4429F, -1.5F, 9.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(120, 85).addBox(-1.4929F, -22.4429F, -0.5F, 3.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 88).addBox(-1.4929F, -19.4429F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(110, 85).addBox(-1.9929F, -24.4429F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(106, 85).addBox(-1.9929F, -25.6429F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(106, 85).addBox(1.0071F, -25.6429F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5026F, 4.5698F, 7.0205F, 0.1309F, 0.0F, -0.3491F));

		PartDefinition sword_r1 = swordback.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(106, 55).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0071F, 30.6571F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition sword_r2 = swordback.addOrReplaceChild("sword_r2", CubeListBuilder.create().texOffs(104, 49).mirror().addBox(-1.5F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.006F)).mirror(false), PartPose.offsetAndRotation(1.0071F, 28.0571F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition sword_r3 = swordback.addOrReplaceChild("sword_r3", CubeListBuilder.create().texOffs(104, 49).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.9929F, 28.0571F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition sword_r4 = swordback.addOrReplaceChild("sword_r4", CubeListBuilder.create().texOffs(98, 41).mirror().addBox(-1.0F, -9.5F, -0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(3.0071F, 0.0571F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition sword_r5 = swordback.addOrReplaceChild("sword_r5", CubeListBuilder.create().texOffs(98, 41).addBox(-1.0F, -9.5F, -0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-3.0929F, 0.0571F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 8).addBox(-6.0F, -8.0F, -4.75F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(-5.0F, -4.0F, -2.2F, 10.0F, 9.0F, 6.0F, new CubeDeformation(0.15F))
				.texOffs(32, 54).addBox(-5.0F, -9.0F, -2.2F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.12F)), PartPose.offset(0.0F, -18.0F, -2.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 116).addBox(-5.5F, -3.5F, -4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offset(-11.5F, -13.0F, 0.0F));

		PartDefinition right_shoulder_r1 = right_arm.addOrReplaceChild("right_shoulder_r1", CubeListBuilder.create().texOffs(0, 103).addBox(-4.0F, -3.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-1.2F, 4.0F, -0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(78, 17).addBox(-3.5F, -8.5F, -3.5F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -0.1F, 0.0F, 0.0F, 0.2618F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(102, 17).addBox(-3.0F, 2.1901F, -2.5863F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.2F))
				.texOffs(30, 111).addBox(-3.0F, -1.8099F, -2.5863F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.5F, 11.25F, -0.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition sword = right_hand.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(82, 41).addBox(-3.5F, 7.7375F, -0.5F, 7.0F, 36.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 62).addBox(-1.0F, 7.7375F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(78, 58).addBox(-0.5F, 43.7375F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 78).addBox(-4.5F, 3.7375F, -1.5F, 9.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(90, 85).addBox(-1.5F, -5.2625F, -0.5F, 3.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(82, 88).addBox(-1.5F, -2.2625F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(80, 85).addBox(-2.0F, -7.2625F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(76, 85).addBox(-2.0F, -8.4625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(76, 85).addBox(1.0F, -8.4625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5F, 16.0125F, 0.5F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition sword_r6 = sword.addOrReplaceChild("sword_r6", CubeListBuilder.create().texOffs(76, 55).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 47.8375F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition sword_r7 = sword.addOrReplaceChild("sword_r7", CubeListBuilder.create().texOffs(74, 49).mirror().addBox(-1.5F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.006F)).mirror(false), PartPose.offsetAndRotation(1.0F, 45.2375F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition sword_r8 = sword.addOrReplaceChild("sword_r8", CubeListBuilder.create().texOffs(74, 49).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-1.0F, 45.2375F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition sword_r9 = sword.addOrReplaceChild("sword_r9", CubeListBuilder.create().texOffs(68, 41).mirror().addBox(-1.0F, -9.5F, -0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(3.0F, 17.2375F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition sword_r10 = sword.addOrReplaceChild("sword_r10", CubeListBuilder.create().texOffs(68, 41).addBox(-1.0F, -9.5F, -0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-3.1F, 17.2375F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 116).mirror().addBox(-2.5F, -3.5F, -4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(11.5F, -13.0F, 0.0F));

		PartDefinition left_shoulder_r1 = left_arm.addOrReplaceChild("left_shoulder_r1", CubeListBuilder.create().texOffs(0, 103).mirror().addBox(-4.0F, -3.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(1.2F, 4.0F, -0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(78, 17).mirror().addBox(-1.5F, -8.5F, -3.5F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, -0.1F, 0.0F, 0.0F, -0.2618F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(102, 17).mirror().addBox(-3.0F, 2.1901F, -2.5863F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(30, 111).mirror().addBox(-3.0F, -1.8099F, -2.5863F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(2.5F, 11.25F, -0.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 4.0F, 0.0F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(84, 0).addBox(-3.0F, -6.0F, -2.5F, 6.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.0F, 0.5F, -0.0698F, 0.2269F, 0.0698F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 9.0F, 0.0F));

		PartDefinition right_feet_r1 = right_foot.addOrReplaceChild("right_feet_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5305F, 10.1F, -2.1914F, 0.0F, 0.1309F, 0.0F));

		PartDefinition right_feet_r2 = right_foot.addOrReplaceChild("right_feet_r2", CubeListBuilder.create().texOffs(54, 0).addBox(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.9305F, 9.8F, -1.7914F, 0.0F, 0.1309F, 0.0F));

		PartDefinition right_feet_r3 = right_foot.addOrReplaceChild("right_feet_r3", CubeListBuilder.create().texOffs(106, 0).addBox(-3.0F, -5.5F, -2.5F, 6.0F, 11.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-0.5F, 5.5F, 0.5F, 0.0F, 0.1309F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(5.0F, 4.0F, 0.0F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-3.0F, -6.0F, -2.5F, 6.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 3.0F, 0.5F, -0.0698F, -0.2269F, -0.0698F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition left_feet_r1 = left_foot.addOrReplaceChild("left_feet_r1", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-1.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5305F, 10.1F, -2.1914F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_feet_r2 = left_foot.addOrReplaceChild("left_feet_r2", CubeListBuilder.create().texOffs(54, 0).mirror().addBox(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.9305F, 9.8F, -1.7914F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_feet_r3 = left_foot.addOrReplaceChild("left_feet_r3", CubeListBuilder.create().texOffs(106, 0).mirror().addBox(-3.0F, -5.5F, -2.5F, 6.0F, 11.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(0.5F, 5.5F, 0.5F, 0.0F, -0.1309F, 0.0F));

		PartDefinition cloak = partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(0, 152).addBox(-12.0F, -12.0F, -12.0F, 24.0F, 24.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(128, 104).addBox(-16.0F, -8.0F, -16.0F, 32.0F, 16.0F, 32.0F, new CubeDeformation(1.0F))
				.texOffs(0, 200).addBox(-20.0F, -8.0F, -20.0F, 40.0F, 16.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof OldSecondRounderGolemEntity secondRounderGolem) {
			this.head.yRot = f4 * ((float) Math.PI / 180F);
			this.head.xRot = f5 * ((float) Math.PI / 180F);
			if (secondRounderGolem.isUseSword()) {
				this.animateWalk(SecondRounderGolemAnimation.RUN, f, f2, 1.5f, 2.0f);
			} else {
				this.animateWalk(SecondRounderGolemAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			this.animate(secondRounderGolem.idleAnimationState, SecondRounderGolemAnimation.IDLE, f3);
			this.animate(secondRounderGolem.standAnimationState, SecondRounderGolemAnimation.STAND, f3);
			this.animate(secondRounderGolem.standStartAnimationState, SecondRounderGolemAnimation.STANDSTART, f3);
			this.animate(secondRounderGolem.standStopAnimationState, SecondRounderGolemAnimation.STANDSTOP, f3);
			this.animate(secondRounderGolem.attack1AnimationState, SecondRounderGolemAnimation.ATTACK1, f3);
			this.animate(secondRounderGolem.attack2AnimationState, SecondRounderGolemAnimation.ATTACK2, f3);
			this.animate(secondRounderGolem.attack3AnimationState, SecondRounderGolemAnimation.ATTACK3, f3);
			this.animate(secondRounderGolem.blockAnimationState, SecondRounderGolemAnimation.BLOCK, f3);
			this.animate(secondRounderGolem.breathAnimationState, SecondRounderGolemAnimation.BREATH, f3);
			this.animate(secondRounderGolem.swordAnimationState, SecondRounderGolemAnimation.SWORD, f3);
			this.animate(secondRounderGolem.attack4AnimationState, SecondRounderGolemAnimation.ATTACK4, f3);
			this.animate(secondRounderGolem.attack5AnimationState, SecondRounderGolemAnimation.ATTACK5, f3);
			this.animate(secondRounderGolem.attack6AnimationState, SecondRounderGolemAnimation.ATTACK6, f3);
			this.animate(secondRounderGolem.angryAnimationState, SecondRounderGolemAnimation.ANGRY, f3);
			this.animate(secondRounderGolem.angrySword1AnimationState, SecondRounderGolemAnimation.ANGRYSWORD1, f3);
			this.animate(secondRounderGolem.angrySword2AnimationState, SecondRounderGolemAnimation.ANGRYSWORD2, f3);
			this.animate(secondRounderGolem.angrySword3AnimationState, SecondRounderGolemAnimation.ANGRYSWORD3, f3);
			this.animate(secondRounderGolem.swordAttackAnimationState, SecondRounderGolemAnimation.SWORDATTACK, f3);
			this.animate(secondRounderGolem.cloakAnimationState, SecondRounderGolemAnimation.CLOAK, f3);
			this.animate(secondRounderGolem.helpAnimationState, SecondRounderGolemAnimation.HELP, f3);
			this.animate(secondRounderGolem.deadAnimationState, SecondRounderGolemAnimation.DEAD, f3);
		}
		if (t instanceof SecondRounderGolemSwordEntity secondRounderGolemSword) {
			this.cloak.visible = false;
			this.head.visible = false;
			this.body.visible = false;
			this.leftLeg.visible = false;
			this.rightLeg.visible = false;
			this.animate(secondRounderGolemSword.swordAttackAnimationState, SecondRounderGolemAnimation.SWORDATTACK, f3);
		}
	}

}