package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ProsperousEnvoyAnimation;
import com.jerotes.jerotesvillage.entity.Boss.ProsperousEnvoyEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelprosperous_envoy<T extends LivingEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "prosperous_envoy"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart fan;
	private final ModelPart body_hind;
	private final ModelPart hind_fan;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart right_hind_leg;
	private final ModelPart right_hind_foot;
	private final ModelPart left_hind_leg;
	private final ModelPart left_hind_foot;
	private final ModelPart right_front_leg;
	private final ModelPart right_front_foot;
	private final ModelPart left_front_leg;
	private final ModelPart left_front_foot;


	public Modelprosperous_envoy(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.fan = this.body.getChild("fan");
		this.body_hind = this.body.getChild("body_hind");
		this.hind_fan = this.body_hind.getChild("hind_fan");
		this.tail = this.body_hind.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.right_hind_foot = this.right_hind_leg.getChild("right_hind_foot");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.left_hind_foot = this.left_hind_leg.getChild("left_hind_foot");
		this.right_front_leg = root.getChild("right_front_leg");
		this.right_front_foot = this.right_front_leg.getChild("right_front_foot");
		this.left_front_leg = root.getChild("left_front_leg");
		this.left_front_foot = this.left_front_leg.getChild("left_front_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -9.1509F, -26.9156F));

		PartDefinition neck_r1 = head.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-5.5F, -6.5F, -4.5F, 11.0F, 13.0F, 9.0F, new CubeDeformation(-0.26F)), PartPose.offsetAndRotation(0.0F, 6.1942F, -1.6482F, -0.7854F, 0.0F, 0.0F));

		PartDefinition neck_r2 = head.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(0, 26).addBox(-5.5F, -6.5F, -4.5F, 11.0F, 13.0F, 9.0F, new CubeDeformation(-0.26F)), PartPose.offsetAndRotation(0.0F, 0.1942F, -7.6482F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -5.5F, -7.5F, 11.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -12.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(48, 22).addBox(-10.0F, -19.2809F, -9.7937F, 20.0F, 19.0F, 23.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -13.2063F, -0.2809F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.45F))
				.texOffs(40, 46).addBox(-8.0F, -3.2063F, 1.7191F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-4.0F, 8.7937F, 1.7191F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).mirror().addBox(0.0F, 8.7937F, 1.7191F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(40, 46).mirror().addBox(4.0F, -3.2063F, 1.7191F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 38).addBox(-4.0F, -3.2063F, 0.7191F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(16, 20).addBox(-4.0F, -3.2063F, 0.7191F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-1.0F, -6.2063F, -2.2809F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.0F, -13.2063F, -0.2809F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(52, 0).addBox(-9.0F, -25.2809F, -4.2937F, 18.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2063F, -3.7191F, 1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r3 = body.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(0, 48).addBox(-6.5F, -6.0F, 0.5F, 13.0F, 14.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -27.9382F, 10.5873F, -2.3562F, 0.0F, 0.0F));

		PartDefinition fan = body.addOrReplaceChild("fan", CubeListBuilder.create(), PartPose.offset(0.0F, -12.7809F, 13.2063F));

		PartDefinition body_r1 = fan.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(78, 39).addBox(0.0F, -26.0F, -12.5F, 0.0F, 36.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.5F, -1.5708F, 0.0F, -3.1416F));

		PartDefinition body_hind = body.addOrReplaceChild("body_hind", CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.0F, -13.0F, 18.0F, 23.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.2809F, 4.2063F));

		PartDefinition hind_fan = body_hind.addOrReplaceChild("hind_fan", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, 8.0F));

		PartDefinition body_hind_r1 = hind_fan.addOrReplaceChild("body_hind_r1", CubeListBuilder.create().texOffs(128, 40).addBox(0.0F, -26.5F, -12.0F, 0.0F, 36.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 8.0F, -1.5708F, 0.0F, -3.1416F));

		PartDefinition tail = body_hind.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(120, 0).addBox(-5.5F, 2.5F, -5.9765F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.1564F, 0.2627F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(111, 23).addBox(-4.5F, -0.5F, -6.9765F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 3.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(147, 23).addBox(-3.5F, 0.0F, -3.9765F, 7.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.5F, 0.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(166, 0).addBox(-2.5F, 0.0F, -1.9765F, 5.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-10.0F, 4.0F, 9.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(194, 24).addBox(-3.5F, -6.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7634F, 3.9202F, 2.9355F, -0.1739F, 0.0151F, 0.0859F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(-1.0F, 10.5F, 0.0F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(226, 24).addBox(-4.0F, -6.5F, -4.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3373F, 4.7381F, 2.3002F, 0.1281F, 0.0582F, -0.013F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(10.0F, 4.0F, 9.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(194, 24).mirror().addBox(-3.5F, -6.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7634F, 3.9202F, 2.9355F, -0.1739F, -0.0151F, -0.0859F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(1.0F, 10.5F, 0.0F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(226, 24).mirror().addBox(-3.0F, -6.5F, -4.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.3373F, 4.7381F, 2.3002F, 0.1281F, -0.0582F, 0.013F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-11.0F, 0.0F, -22.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(190, 0).addBox(-3.5F, -7.0F, -5.0F, 7.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6661F, 5.2819F, 0.1429F, 0.1289F, -0.0227F, 0.1731F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(-4.0F, 11.0F, 3.0F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(224, 0).addBox(-4.0F, -7.5F, -4.5F, 7.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 6.1865F, -2.6828F, -0.1309F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(11.0F, 0.0F, -22.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(190, 0).mirror().addBox(-3.5F, -7.0F, -5.0F, 7.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.6661F, 5.526F, 0.2655F, 0.215F, 0.0376F, -0.1705F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(4.0F, 11.0F, 3.0F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(224, 0).mirror().addBox(-3.0F, -7.5F, -4.5F, 7.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 6.1865F, -2.6828F, -0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}


	private float currentTailYaw = 0f;
	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof ProsperousEnvoyEntity prosperousEnvoy) {
			this.head.yRot = f4 * ((float)Math.PI / 180F);
			this.head.xRot = f5 * ((float)Math.PI / 180F) / 2;
			float targetTailYaw = -f4 * 1.2f * ((float) Math.PI / 180F);
			currentTailYaw += (targetTailYaw - currentTailYaw) * 0.1f;
			tail.zRot -= Mth.clamp(currentTailYaw * 0.1f, -1.0f, 1.0f) * (prosperousEnvoy.getMoveAbout() / 20f);
			tail2.zRot -= Mth.clamp(currentTailYaw * 0.2f, -1.0f, 1.0f) * (prosperousEnvoy.getMoveAbout() / 20f);
			tail3.zRot -= Mth.clamp(currentTailYaw * 0.3f, -1.0f, 1.0f) * (prosperousEnvoy.getMoveAbout() / 20f);
			tail4.zRot -= Mth.clamp(currentTailYaw * 0.4f, -1.0f, 1.0f) * (prosperousEnvoy.getMoveAbout() / 20f);

			if (prosperousEnvoy.getRushTick() <= 0) {
				this.animateWalk(ProsperousEnvoyAnimation.WALK, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(ProsperousEnvoyAnimation.RUSH, f, f2, 2.0f, 2.0f);
			}
			this.animate(prosperousEnvoy.idleAnimationState, ProsperousEnvoyAnimation.IDLE, f3);
			this.animate(prosperousEnvoy.attack1AnimationState, ProsperousEnvoyAnimation.ATTACK1, f3);
			this.animate(prosperousEnvoy.attack2AnimationState, ProsperousEnvoyAnimation.ATTACK2, f3);
			this.animate(prosperousEnvoy.attack3AnimationState, ProsperousEnvoyAnimation.ATTACK3, f3);
			this.animate(prosperousEnvoy.trampleAnimationState, ProsperousEnvoyAnimation.ATTACK4, f3);
			this.animate(prosperousEnvoy.deadAnimationState, ProsperousEnvoyAnimation.DEAD, f3);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}