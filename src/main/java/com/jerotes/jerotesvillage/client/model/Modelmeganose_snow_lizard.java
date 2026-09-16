package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.MeganoseSnowLizardAnimation;
import com.jerotes.jerotesvillage.entity.Animal.MeganoseSnowLizardEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelmeganose_snow_lizard<T extends MeganoseSnowLizardEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "meganose_snow_lizard"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	public Modelmeganose_snow_lizard(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -2.5F, -13.0F, 15.0F, 15.0F, 27.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(-0.5F, -7.5F, -15.75F, 1.0F, 5.0F, 29.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 3.5F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(31, 42).addBox(-6.5F, -4.0F, -6.0F, 13.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -11.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 95).addBox(-2.5F, -4.0F, -12.75F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 76).addBox(-5.5F, -3.0F, -9.0F, 11.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(60, 65).addBox(-4.5F, -2.0F, -7.0F, 9.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(57, 0).addBox(-4.5F, -5.5F, 0.0F, 9.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 14.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(75, 33).addBox(-3.5F, -4.0F, 0.5F, 7.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 11.5F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(90, 14).addBox(-1.5F, -2.0F, 0.5F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 9.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-5.3F, 6.5F, 9.5F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-3.0F, -3.5F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.5672F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.offset(1.3F, 6.5F, 1.0F));

		PartDefinition right_hind_foot_r1 = right_hind_foot.addOrReplaceChild("right_hind_foot_r1", CubeListBuilder.create().texOffs(0, 93).addBox(-3.0F, -6.0F, -3.5F, 5.0F, 12.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-5.25F, 5.0F, -2.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(5.3F, 6.5F, 9.5F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-3.0F, -3.5F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, -0.5672F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.offset(-1.3F, 6.5F, 1.0F));

		PartDefinition left_hind_foot_r1 = left_hind_foot.addOrReplaceChild("left_hind_foot_r1", CubeListBuilder.create().texOffs(0, 93).mirror().addBox(-2.0F, -6.0F, -3.5F, 5.0F, 12.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(5.25F, 5.0F, -2.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(8.3F, 4.5F, -9.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(36, 76).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.1309F, 0.0F, -0.5672F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(-3.3F, 7.0F, 2.0F));

		PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(88, 50).addBox(-3.0F, -6.5F, -4.0F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 6.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(-8.3F, 4.5F, -9.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(36, 76).mirror().addBox(-3.0F, -1.5F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.5672F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(3.3F, 7.0F, 2.0F));

		PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(88, 50).mirror().addBox(-2.0F, -6.5F, -4.0F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 6.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
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
		this.animateWalk(MeganoseSnowLizardAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(((MeganoseSnowLizardEntity)t).idleAnimationState, MeganoseSnowLizardAnimation.IDLE, f3);
		this.animate(((MeganoseSnowLizardEntity)t).attack1AnimationState, MeganoseSnowLizardAnimation.ATTACK1, f3);
		this.animate(((MeganoseSnowLizardEntity)t).attack2AnimationState, MeganoseSnowLizardAnimation.ATTACK2, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}