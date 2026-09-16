package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BeachTerrorBirdAnimation;
import com.jerotes.jerotesvillage.entity.Animal.BeachTerrorBirdEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelbeach_terror_bird<T extends BeachTerrorBirdEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "beach_terror_bird"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;

	public Modelbeach_terror_bird(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 2.4707F, -3.3423F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -3.4707F, -5.6577F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(28, 24).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 51).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.25F))
				.texOffs(38, 13).addBox(-2.5F, -3.0F, -10.0F, 5.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(27, 27).addBox(-2.5F, -2.5F, -11.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -2.0F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(12, 51).addBox(-3.0F, 0.0F, 0.5F, 6.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 1.5F));

		PartDefinition bill = head.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition main_body = body.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.6577F, -3.5293F, 10.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = main_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 9.6985F, 1.0028F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(26, 58).addBox(-3.5F, 1.5115F, 5.7386F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(31, 41).addBox(-4.0F, -4.4885F, 3.7386F, 8.0F, 8.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 4.7575F, -7.6761F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(38, 0).addBox(-4.0F, -3.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5744F, 0.0F, 0.0F));

		PartDefinition left_wing = main_body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(5.5F, -4.4707F, 1.3423F));

		PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(10, 27).mirror().addBox(-0.5F, -3.0F, -8.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_wing = main_body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-5.5F, -4.4707F, 1.3423F));

		PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(10, 27).addBox(-0.5F, -3.0F, -8.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 4.0F, 0.0F));

		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(18, 41).mirror().addBox(1.25F, -10.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 16.0F, 0.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_leg_r2 = left_leg.addOrReplaceChild("left_leg_r2", CubeListBuilder.create().texOffs(54, 41).mirror().addBox(0.25F, -5.5F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 3.504F, -0.0913F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition left_foot_r1 = left_foot.addOrReplaceChild("left_foot_r1", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(1.25F, -6.0F, -1.0F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 5.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition left_foot2 = left_foot.addOrReplaceChild("left_foot2", CubeListBuilder.create().texOffs(46, 54).mirror().addBox(-1.75F, -1.1F, -6.0F, 4.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 4.0F, 0.0F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(18, 41).addBox(-3.25F, -10.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(2.0F, 16.0F, 0.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_leg_r2 = right_leg.addOrReplaceChild("right_leg_r2", CubeListBuilder.create().texOffs(54, 41).addBox(-4.25F, -5.5F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.504F, -0.0913F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition right_foot_r1 = right_foot.addOrReplaceChild("right_foot_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-3.25F, -6.0F, -1.0F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 5.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition right_foot2 = right_foot.addOrReplaceChild("right_foot2", CubeListBuilder.create().texOffs(46, 54).addBox(-2.25F, -1.1F, -6.0F, 4.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 72, 72);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.4f;
			this.head.yScale = 1.4f;
			this.head.zScale = 1.4f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isAggressive()) {
			this.animateWalk(BeachTerrorBirdAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(BeachTerrorBirdAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, BeachTerrorBirdAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, BeachTerrorBirdAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, BeachTerrorBirdAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, BeachTerrorBirdAnimation.ATTACK3, f3);
		this.animate(t.fallAnimationState, BeachTerrorBirdAnimation.FALL, f3);
		this.animate(t.toFallAnimationState, BeachTerrorBirdAnimation.TOFALL, f3);
		this.animate(t.stopFallAnimationState, BeachTerrorBirdAnimation.STOPFALL, f3);
		this.animate(t.sitAnimationState, BeachTerrorBirdAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, BeachTerrorBirdAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, BeachTerrorBirdAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
