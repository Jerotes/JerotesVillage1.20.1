package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PrimordierAnimation;
import com.jerotes.jerotesvillage.entity.Monster.Elite.PrimordierEntity;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;


public class Modelprimordier<T extends PrimordierEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "primordier"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;

	public Modelprimordier(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.neck = body.getChild("neck");
		this.head = neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 6.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -33.8138F, -28.5811F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 53).addBox(-10.0F, -9.0F, -9.0F, 20.0F, 18.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(139, 177).addBox(-10.5F, -11.6485F, -16.6908F, 21.0F, 15.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(181, 177).addBox(-10.5F, 1.3515F, -16.6908F, 21.0F, 0.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(139, 212).addBox(-10.5F, -11.6485F, -19.6908F, 21.0F, 15.0F, 23.0F, new CubeDeformation(0.5F))
				.texOffs(48, 21).addBox(10.5F, -9.6485F, -8.6908F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 21).addBox(-15.5F, -9.6485F, -8.6908F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-10.5F, 3.2515F, -2.7908F, 21.0F, 6.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 8.8138F, -8.9189F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 21).addBox(-10.5F, -2.5F, -2.5F, 21.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.1485F, -17.1908F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-7.47F, -11.8537F, -4.5547F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(70, 12).addBox(2.0F, -6.0F, -4.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -14.3973F, 6.0573F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_horn_r2 = right_horn.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(94, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -7.0063F, 2.9958F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_horn_r3 = right_horn.addOrReplaceChild("right_horn_r3", CubeListBuilder.create().texOffs(94, 12).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(7.47F, -11.8537F, -4.5547F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(70, 0).mirror().addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(70, 12).mirror().addBox(-6.0F, -6.0F, -4.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -14.3973F, 6.0573F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_horn_r2 = left_horn.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(94, 0).mirror().addBox(-4.0F, -2.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -7.0063F, 2.9958F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_horn_r3 = left_horn.addOrReplaceChild("left_horn_r3", CubeListBuilder.create().texOffs(94, 12).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, 2.3515F, -13.6908F, 21.0F, 7.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(40, 39).addBox(-10.5F, 4.3515F, -13.6908F, 21.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(-1.5F, -12.0F, -10.0F));

		PartDefinition body1_r1 = body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(139, 64).addBox(-25.5F, -13.5F, -22.0F, 51.0F, 27.0F, 37.0F, new CubeDeformation(2.25F))
				.texOffs(139, 0).addBox(-25.5F, -13.5F, -22.0F, 51.0F, 27.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -9.5114F, -5.2722F, -1.0036F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.8558F, -7.2881F));

		PartDefinition body2_r1 = body2.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(139, 128).addBox(-16.5F, -12.0F, -10.5F, 33.0F, 28.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3986F, 1.9074F, -1.2654F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-25.5F, -30.0F, -21.0F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(336, 0).addBox(-6.0F, -9.0F, -7.5F, 12.0F, 18.0F, 15.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-3.3817F, 2.5654F, 0.2014F, 0.2132F, -0.0469F, 0.475F));

		PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(336, 33).addBox(-7.0F, -7.0F, -7.5F, 13.0F, 32.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8978F, 8.2235F, 1.5F, 0.2182F, 0.0F, 0.2618F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(336, 80).addBox(-7.2F, -1.7F, -10.3F, 14.0F, 27.0F, 17.0F, new CubeDeformation(0.5F))
				.texOffs(336, 124).addBox(-7.2F, 25.3F, -10.3F, 14.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 30.5F, 8.5F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(25.5F, -30.0F, -21.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(336, 0).mirror().addBox(-6.0F, -9.0F, -7.5F, 12.0F, 18.0F, 15.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.3817F, 2.5654F, 0.2014F, 0.2132F, 0.0469F, -0.475F));

		PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(336, 33).mirror().addBox(-6.0F, -7.0F, -7.5F, 13.0F, 32.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.8978F, 8.2235F, 1.5F, 0.2182F, 0.0F, -0.2618F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(336, 80).mirror().addBox(-4.2F, -1.7F, -10.3F, 14.0F, 27.0F, 17.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(336, 124).mirror().addBox(-4.2F, 25.3F, -10.3F, 14.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 30.5F, 8.5F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-13.5F, -9.0F, 1.0F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(410, 0).addBox(-7.5F, -13.5F, -7.0F, 15.0F, 27.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 4.861F, 1.7424F, -0.2618F, 0.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(410, 41).addBox(-7.0F, -2.5F, -6.0F, 14.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 14.7F, -1.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(13.5F, -9.0F, 1.0F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(410, 0).mirror().addBox(-7.5F, -13.5F, -7.0F, 15.0F, 27.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.8099F, 1.3542F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(410, 41).mirror().addBox(-7.0F, -2.5F, -6.0F, 14.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 14.7F, -1.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}
		private float rushArmWeight = 0f;
	private float targetRushArmWeight = 0f;

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		targetRushArmWeight = t.isAggressive() ? 1.0f : 0.0f;
		rushArmWeight += (targetRushArmWeight - rushArmWeight) * 0.1f;

		if (t.specialAction()) {
			this.animateWalk(PrimordierAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		else if (t.isAggressive()) {
			float walk = t.getAttackWalk() / 40f * 0.8f;
			this.animateWalk(PrimordierAnimation.RUSHARM, f, f2 * (1 - walk + 0.2f), 1.5f, 2.0f);
			this.animateWalk(PrimordierAnimation.RUSH, f, f2 * (0.2f + (1 - walk + 0.2f) * 0.8f), 1.5f, 2.0f);
		} else {
			this.animateWalk(PrimordierAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.attack1AnimationState, PrimordierAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, PrimordierAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, PrimordierAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, PrimordierAnimation.ATTACK4, f3);
		this.animate(t.attack5AnimationState, PrimordierAnimation.ATTACK5, f3);
		this.animate(t.attack6AnimationState, PrimordierAnimation.ATTACK6, f3);
		this.animate(t.attack7AnimationState, PrimordierAnimation.ATTACK7, f3);
		this.animate(t.attack8AnimationState, PrimordierAnimation.ATTACK8, f3);
		this.animate(t.idleAnimationState, PrimordierAnimation.IDLE, f3);
		KeyframeAnimations.animate(this, PrimordierAnimation.JUMP, 0L, t.isAlive() ? t.getJumpAnim() / 20f : 0, new Vector3f());
		this.animate(t.shockAnimationState, PrimordierAnimation.SHOCK, f3);
		this.animate(t.shockToCorrosiveAnimationState, PrimordierAnimation.SHOCKTOCORROSIVE, f3);
		this.animate(t.deadAnimationState, PrimordierAnimation.DEAD, f3);
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