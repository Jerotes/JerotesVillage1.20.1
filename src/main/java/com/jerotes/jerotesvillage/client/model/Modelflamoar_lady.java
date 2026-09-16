package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FlamoarLadyAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.FlamoarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;


public class Modelflamoar_lady<T extends FlamoarEntity> extends Modelflamoar<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "flamoar_lady"), "main");
	private final ModelPart root;
	private final ModelPart all_body;
	private final ModelPart leg;
	private final ModelPart leg2;
	private final ModelPart head;
	private final ModelPart face;
	private final ModelPart horn;
	private final ModelPart left_horn;
	private final ModelPart right_horn;
	private final ModelPart body;
	private final ModelPart cloak;
	private final ModelPart left_arm;
	private final ModelPart left_fire;
	private final ModelPart left_hand;
	private final ModelPart left_weapon;
	private final ModelPart right_arm;
	private final ModelPart right_fire;
	private final ModelPart right_hand;
	private final ModelPart right_weapon;

	public Modelflamoar_lady(ModelPart modelPart) {
        super(modelPart);
        this.root = modelPart;
		this.all_body = root.getChild("all_body");
		this.head = this.all_body.getChild("head");
		this.face = this.head.getChild("face");
		this.horn = this.head.getChild("horn");
		this.left_horn = this.horn.getChild("left_horn");
		this.right_horn = this.horn.getChild("right_horn");
		this.body = this.all_body.getChild("body");
		this.cloak = this.body.getChild("cloak");
		this.leg = this.body.getChild("leg");
		this.leg2 = this.leg.getChild("leg2");
		this.left_arm = this.all_body.getChild("left_arm");
		this.left_fire = this.left_arm.getChild("left_fire");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.left_weapon = this.left_hand.getChild("left_weapon");
		this.right_arm = this.all_body.getChild("right_arm");
		this.right_fire = this.right_arm.getChild("right_fire");
		this.right_hand = this.right_arm.getChild("right_hand");
		this.right_weapon = this.right_hand.getChild("right_weapon");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all_body = partdefinition.addOrReplaceChild("all_body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.25F));

		PartDefinition head = all_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.9375F, -3.185F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -6.9375F, -3.185F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(64, 0).addBox(-4.0F, -6.9375F, -3.185F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(50, 74).addBox(-5.0F, -8.1875F, -3.945F, 10.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0625F, -1.065F));

		PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(56, 58).addBox(-7.6F, -4.7F, -0.8901F, 16.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.4F, -5.2375F, -2.545F));

		PartDefinition horn_r1 = face.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(68, 58).addBox(0.0F, -5.5F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4F, 0.8F, -0.8901F, 0.0F, -0.5236F, 0.0F));

		PartDefinition horn_r2 = face.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(68, 58).addBox(0.0F, -5.5F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4F, -1.2F, 2.1099F, 0.0F, -0.6545F, 0.0F));

		PartDefinition horn_r3 = face.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0F, -5.5F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.6F, -1.2F, 2.1099F, 0.0F, 0.6545F, 0.0F));

		PartDefinition horn_r4 = face.addOrReplaceChild("horn_r4", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0F, -5.5F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.6F, 0.8F, -0.8901F, 0.0F, 0.5236F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, -7.9375F, -0.185F));

		PartDefinition left_horn = horn.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 1.7F, -4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, 0.7F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 2.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, 0.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, 0.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -0.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, -0.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -0.3F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -1.3F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -1.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -2.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, -1.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 0.7F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-0.5F, -2.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-0.5F, -3.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 0).addBox(-0.5F, -3.3F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -0.7F, 2.35F));

		PartDefinition right_horn = horn.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 1.7F, -4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, 0.7F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 1.7F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 2.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, 0.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, 0.7F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -0.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, -0.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -0.3F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -1.3F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -1.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(-0.5F, -2.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, -1.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-0.5F, 0.7F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-0.5F, -2.3F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-0.5F, -3.3F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 0).addBox(-0.5F, -3.3F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -0.7F, 2.35F));

		PartDefinition body = all_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(5, 18).addBox(-5.0F, 0.3F, -3.1F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(53, 19).addBox(-5.0F, 0.3F, -3.1F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.15F))
				.texOffs(5, 70).addBox(-5.0F, 0.3F, -3.6F, 10.0F, 8.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.3F, -0.15F));

		PartDefinition chest_r1 = body.addOrReplaceChild("chest_r1", CubeListBuilder.create().texOffs(8, 85).addBox(-4.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 3.8F, -3.0F, 0.829F, 0.0F, 0.0F));

		PartDefinition chest_r2 = body.addOrReplaceChild("chest_r2", CubeListBuilder.create().texOffs(26, 58).addBox(-4.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.15F))
				.texOffs(0, 58).addBox(-4.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.8F, -3.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cloak = body.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.offset(0.0F, 4.8F, 4.4F));

		PartDefinition cloak_r1 = cloak.addOrReplaceChild("cloak_r1", CubeListBuilder.create().texOffs(103, 87).addBox(-4.0F, -0.5F, 0.0F, 8.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(4, 32).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(49, 32).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 6.3F, 0.15F));

		PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0F, 0.25F, -3.75F, 10.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(34, 45).addBox(-5.0F, 0.25F, -3.75F, 10.0F, 6.0F, 7.0F, new CubeDeformation(0.15F))
				.texOffs(0, 58).addBox(-4.0F, 3.25F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(26, 58).addBox(-4.0F, 3.25F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 4.75F, 0.25F));

		PartDefinition left_arm = all_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(106, 29).mirror().addBox(0.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(110, 13).mirror().addBox(0.0F, 0.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(112, 0).mirror().addBox(-0.5F, -0.75F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.55F)).mirror(false), PartPose.offset(5.0F, -11.0F, -0.25F));

		PartDefinition left_fire = left_arm.addOrReplaceChild("left_fire", CubeListBuilder.create(), PartPose.offset(2.5F, -3.0F, -0.5F));

		PartDefinition fire_r1 = left_fire.addOrReplaceChild("fire_r1", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition fire_r2 = left_fire.addOrReplaceChild("fire_r2", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition fire_r3 = left_fire.addOrReplaceChild("fire_r3", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.0479F, -0.2129F, 0.2233F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(106, 70).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(107, 49).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(3.0F, 8.0F, -1.0F));

		PartDefinition left_weapon = left_hand.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.5F, 1.0F));

		PartDefinition right_arm = all_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(106, 29).addBox(-4.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(110, 13).addBox(-4.0F, 0.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.5F))
				.texOffs(112, 0).addBox(-3.5F, -0.75F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(-5.0F, -11.0F, -0.25F));

		PartDefinition right_fire = right_arm.addOrReplaceChild("right_fire", CubeListBuilder.create(), PartPose.offset(-2.5F, -3.0F, -0.5F));

		PartDefinition fire_r4 = right_fire.addOrReplaceChild("fire_r4", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.0479F, 0.2129F, -0.2233F));

		PartDefinition fire_r5 = right_fire.addOrReplaceChild("fire_r5", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition fire_r6 = right_fire.addOrReplaceChild("fire_r6", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(106, 70).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(107, 49).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(-3.0F, 8.0F, -1.0F));

		PartDefinition right_weapon = right_hand.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 7.5F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.animateWalk(FlamoarLadyAnimation.ATTACKWALK, f, f2 * Math.min(1, (t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animateWalk(FlamoarLadyAnimation.WALK, f, f2 * Math.min(1, (1 - t.attackAnimProgress / 10f)), 1.5f, 2.0f);
		this.animate(t.idleAnimationState, FlamoarLadyAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, FlamoarLadyAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, FlamoarLadyAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, FlamoarLadyAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, FlamoarLadyAnimation.ATTACK4, f3);
		this.animate(t.deadAnimationState, FlamoarLadyAnimation.DEATH, f3);
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.all_body.translateAndRotate(poseStack);
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getHand(humanoidArm).translateAndRotate(poseStack);
		this.getWeapon(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getHand(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_hand;
		}
		return this.right_hand;
	}
	protected ModelPart getWeapon(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_weapon;
		}
		return this.right_weapon;
	}
}