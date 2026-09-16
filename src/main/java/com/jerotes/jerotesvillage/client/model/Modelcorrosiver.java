package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CorrosiverAnimation;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class Modelcorrosiver<T extends LivingEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "corrosiver"), "main");
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;

	public Modelcorrosiver(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -33.0F, 10.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 66).addBox(-5.0F, -10.7657F, -25.7939F, 10.0F, 10.0F, 20.0F, new CubeDeformation(0.005F))
				.texOffs(154, 62).addBox(-4.0F, -0.7657F, -24.7939F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -16.0F, -17.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition neck_hair_r1 = head.addOrReplaceChild("neck_hair_r1", CubeListBuilder.create().texOffs(140, -12).addBox(0.0F, -14.0F, -6.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(84, 0).addBox(-4.0F, -6.0F, -6.0F, 8.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5425F, -4.7207F, -0.5236F, 0.0F, 0.0F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.48F, -15.6028F, -12.5796F, 0.6227F, 0.0198F, -0.2531F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(148, 44).addBox(0.0F, -6.0F, -8.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(128, 44).addBox(0.0F, -2.0F, -4.0F, 2.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.9176F, -0.7568F, -1.7017F, 0.0F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(5.48F, -15.6028F, -12.5796F, 0.6227F, -0.0198F, 0.2531F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(148, 44).mirror().addBox(-2.0F, -6.0F, -8.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(128, 44).mirror().addBox(-2.0F, -2.0F, -4.0F, 2.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.9176F, -0.7568F, -1.7017F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(114, 62).addBox(-5.0F, 0.2343F, -15.7939F, 10.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(154, 62).addBox(-4.0F, -0.7657F, -14.2939F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(144, 24).addBox(0.0F, 2.2343F, -11.7939F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -10.0F));

		PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -6.0F));

		PartDefinition body1_hair_r1 = body1.addOrReplaceChild("body1_hair_r1", CubeListBuilder.create().texOffs(140, -14).addBox(0.0F, -16.0F, -14.0F, 0.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.5858F, -3.3825F, -0.7418F, 0.0F, 0.0F));

		PartDefinition body1_r1 = body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -9.0F, -10.0F, 22.0F, 18.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.6743F, -4.1814F, -0.7418F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(1.0F, 3.0F, 4.0F));

		PartDefinition body2_hair_r1 = body2.addOrReplaceChild("body2_hair_r1", CubeListBuilder.create().texOffs(140, 0).addBox(0.0F, -18.0F, -13.0F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.4879F, -1.816F, -0.829F, 0.0F, 0.0F));

		PartDefinition body2_r1 = body2.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-9.0F, -8.0F, -7.0F, 18.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6381F, -2.9204F, -0.829F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 8.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(140, 10).addBox(-0.005F, -14.005F, 3.505F, 0.0F, 4.0F, 10.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(1.0F, 5.4879F, -5.816F, -0.2618F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 68).addBox(-3.05F, -4.2545F, 4.6029F, 6.0F, 8.0F, 10.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.0F, -0.5121F, -5.816F, -0.2618F, 0.0F, 0.0F));

		PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset(0.962F, 2.2732F, 7.415F));

		PartDefinition tail_1_r1 = tail_1.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(32, 69).addBox(-3.075F, -4.575F, -8.425F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.038F, 1.4163F, 8.3637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_1_r2 = tail_1.addOrReplaceChild("tail_1_r2", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001F, -7.501F, -4.499F, 0.0F, 4.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.038F, 1.4163F, 5.3637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.3F, 8.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_1_r3 = tail_2.addOrReplaceChild("tail_1_r3", CubeListBuilder.create().texOffs(32, 69).addBox(-3.075F, -4.575F, -8.425F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.038F, 1.4163F, 8.3637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_1_r4 = tail_2.addOrReplaceChild("tail_1_r4", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001F, -7.501F, -4.499F, 0.0F, 4.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.038F, 1.4163F, 5.3637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.4F, 8.7F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_1_r5 = tail_3.addOrReplaceChild("tail_1_r5", CubeListBuilder.create().texOffs(140, -4).addBox(0.0F, -5.0F, -5.0F, 0.0F, 6.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.038F, 1.7163F, 8.6637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_1_r6 = tail_3.addOrReplaceChild("tail_1_r6", CubeListBuilder.create().texOffs(226, 0).addBox(-3.0F, -3.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(-0.075F)), PartPose.offsetAndRotation(-0.037F, 0.014F, 4.3738F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_1_r7 = tail_3.addOrReplaceChild("tail_1_r7", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001F, -7.501F, -4.499F, 0.0F, 4.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.038F, 1.4163F, 5.3637F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-14.0F, -12.865F, -15.0945F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(128, 40).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1004F, -5.834F, -1.5973F, -0.1309F, 0.0F, 0.2618F));

		PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(128, 0).addBox(-3.0F, -10.0F, 0.0F, 6.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6933F, 3.6706F, 1.0F, 0.2317F, 0.3405F, 0.3404F));

		PartDefinition right_arm_r3 = right_arm.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(184, 64).addBox(-4.0F, -10.0F, -5.0F, 8.0F, 10.0F, 10.0F, new CubeDeformation(0.7F))
				.texOffs(184, 34).addBox(-4.0F, -10.0F, -5.0F, 8.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0681F, 5.4824F, 1.0F, 0.2182F, 0.0F, 0.2618F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(220, 28).addBox(-4.6F, -3.6F, -5.4F, 8.0F, 26.0F, 10.0F, new CubeDeformation(0.4F)), PartPose.offset(-2.0F, 17.0F, 3.0F));

		PartDefinition right_claw = right_hand.addOrReplaceChild("right_claw", CubeListBuilder.create(), PartPose.offset(-3.0F, 22.0F, -1.4F));

		PartDefinition right_claw_r1 = right_claw.addOrReplaceChild("right_claw_r1", CubeListBuilder.create().texOffs(222, 66).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(16.0F, -12.865F, -15.0945F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(128, 40).mirror().addBox(-4.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.1004F, -5.834F, -1.5973F, -0.1309F, 0.0F, -0.2618F));

		PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(128, 0).mirror().addBox(-3.0F, -10.0F, 0.0F, 6.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.6933F, 3.6706F, 1.0F, 0.2317F, -0.3405F, -0.3404F));

		PartDefinition left_arm_r3 = left_arm.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(184, 64).mirror().addBox(-4.0F, -10.0F, -5.0F, 8.0F, 10.0F, 10.0F, new CubeDeformation(0.7F)).mirror(false)
				.texOffs(184, 34).mirror().addBox(-4.0F, -10.0F, -5.0F, 8.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.0681F, 5.4824F, 1.0F, 0.2182F, 0.0F, -0.2618F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(220, 28).mirror().addBox(-1.6F, -3.6F, -5.4F, 8.0F, 26.0F, 10.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(0.0F, 17.0F, 3.0F));

		PartDefinition left_claw = left_hand.addOrReplaceChild("left_claw", CubeListBuilder.create(), PartPose.offset(5.0F, 22.0F, -1.4F));

		PartDefinition left_claw_r1 = left_claw.addOrReplaceChild("left_claw_r1", CubeListBuilder.create().texOffs(222, 66).mirror().addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_leg = main.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-9.0F, -27.0F, 12.0F));

		PartDefinition right_hind_leg_r1 = right_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(84, 24).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 22.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(64, 58).addBox(-3.0F, -0.8958F, -5.2118F, 6.0F, 19.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 9.1958F, 0.2118F));

		PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(9.0F, -27.0F, 12.0F));

		PartDefinition left_hind_leg_r1 = left_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(84, 24).mirror().addBox(-4.0F, -12.0F, -4.0F, 8.0F, 22.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(64, 58).mirror().addBox(-3.0F, -0.8721F, -5.4282F, 6.0F, 19.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 9.1721F, 0.4282F));

		PartDefinition wind = partdefinition.addOrReplaceChild("wind", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top = wind.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 97).addBox(-26.0F, -5.25F, -26.0F, 52.0F, 16.0F, 52.0F, new CubeDeformation(2.75F))
				.texOffs(-1, 101).addBox(-24.0F, 8.0F, -24.0F, 48.0F, 16.0F, 48.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, -56.0F, 0.0F));

		PartDefinition middle = wind.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 172).addBox(-20.0F, -5.0F, -20.0F, 40.0F, 12.0F, 40.0F, new CubeDeformation(2.0F))
				.texOffs(0, 180).addBox(-16.0F, 5.0F, -16.0F, 32.0F, 12.0F, 32.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, -25.5F, 0.0F));

		PartDefinition bottom = wind.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 232).addBox(-37.0F, -8.0F, -37.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(2.0F))
				.texOffs(0, 232).addBox(-37.0F, -3.0F, -37.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(1.0F))
				.texOffs(0, 232).addBox(27.0F, -3.0F, -37.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(1.0F))
				.texOffs(0, 232).addBox(27.0F, -8.0F, -37.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(2.0F))
				.texOffs(0, 232).addBox(-37.0F, -8.0F, 27.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(2.0F))
				.texOffs(0, 232).addBox(-37.0F, -3.0F, 27.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(1.0F))
				.texOffs(0, 232).addBox(27.0F, -8.0F, 27.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(2.0F))
				.texOffs(0, 232).addBox(27.0F, -3.0F, 27.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof CorrosiverEntity corrosiverEntity) {
			float f6 = f3 * 3.1415927f * -0.1f;
			float f7 = f3 * 0.1f;
			this.head.yRot = f4 * ((float)Math.PI / 180F);
			this.head.xRot = f5 * ((float)Math.PI / 180F);

			KeyframeAnimations.animate(this, CorrosiverAnimation.WINDANIM, 0L, t.isAlive() ? Mth.clamp(1 - corrosiverEntity.windAnimProgress / 10f, 0, 1) : 0, new Vector3f());


			if (corrosiverEntity.isVehicle()) {
				this.animateWalk(CorrosiverAnimation.RUSH, f, f2, 1.0f, 2.0f);
			}
			else if (corrosiverEntity.isAggressive()) {
				this.animateWalk(CorrosiverAnimation.RUSH, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(CorrosiverAnimation.WALK, f, f2, 1.5f, 2.0f);
			}
			this.animate(corrosiverEntity.idleAnimationState, CorrosiverAnimation.IDLE, f3);
			this.animate(corrosiverEntity.idleWindAnimationState, CorrosiverAnimation.IDLEWIND, f3);
			this.animate(corrosiverEntity.attack1AnimationState, CorrosiverAnimation.ATTACK1, f3);
			this.animate(corrosiverEntity.attack2AnimationState, CorrosiverAnimation.ATTACK2, f3);
			this.animate(corrosiverEntity.attack3AnimationState, CorrosiverAnimation.ATTACK3, f3);
			this.animate(corrosiverEntity.attack4AnimationState, CorrosiverAnimation.ATTACK4, f3);
			this.animate(corrosiverEntity.attack5AnimationState, CorrosiverAnimation.ATTACK5, f3);
			this.animate(corrosiverEntity.attack6AnimationState, CorrosiverAnimation.ATTACK6, f3);
			this.animate(corrosiverEntity.attack7AnimationState, CorrosiverAnimation.ATTACK7, f3);
			this.animate(corrosiverEntity.breathAnimationState, CorrosiverAnimation.BREATH, f3);
			this.animate(corrosiverEntity.windAnimationState, CorrosiverAnimation.WIND, f3);
			this.animate(corrosiverEntity.snowAnimationState, CorrosiverAnimation.SNOW, f3);
			this.animate(corrosiverEntity.iceAnimationState, CorrosiverAnimation.ICE, f3);
			this.animate(corrosiverEntity.teleportAnimationState, CorrosiverAnimation.TELEPORT, f3);
			this.animate(corrosiverEntity.startAnimationState, CorrosiverAnimation.START, f3);
			this.animate(corrosiverEntity.deadAnimationState, CorrosiverAnimation.DEAD, f3);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}