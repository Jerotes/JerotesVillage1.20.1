package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ScavengerAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ScavengerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelscavenger<T extends ScavengerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "scavenger"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelscavenger(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -11.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(26, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, -1.3F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -0.5F, 8.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.9F, -6.8F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_teeth = head.addOrReplaceChild("right_teeth", CubeListBuilder.create(), PartPose.offset(-2.6451F, -11.0564F, -21.5F));

		PartDefinition right_teeth_r1 = right_teeth.addOrReplaceChild("right_teeth_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 15.4F, -0.6109F, 0.0F, 0.0F));

		PartDefinition left_teeth = head.addOrReplaceChild("left_teeth", CubeListBuilder.create(), PartPose.offset(2.6451F, -11.0564F, -21.5F));

		PartDefinition left_teeth_r1 = left_teeth.addOrReplaceChild("left_teeth_r1", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-1.0F, -2.0F, -1.5F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 22.0F, 15.4F, -0.6109F, 0.0F, 0.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(0, 16).addBox(-7.0F, -1.75F, -6.0F, 14.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(40, 2).addBox(-1.0F, -5.75F, -6.0F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.25F, -4.5F));

		PartDefinition right_front_leg = front_body.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-7.0F, 0.25F, -2.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(77, 0).addBox(-3.0F, -7.0F, -2.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 13.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition left_front_leg_1_r1 = right_front_leg_1.addOrReplaceChild("left_front_leg_1_r1", CubeListBuilder.create().texOffs(92, 0).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition right_front_leg_2 = right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -11.0F, -0.25F));

		PartDefinition left_front_leg_2_r1 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r1", CubeListBuilder.create().texOffs(84, 18).addBox(-2.5F, -6.5F, -3.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0F, 16.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_front_leg_2_r2 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r2", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -14.5F, -3.0F, 4.0F, 29.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_front_leg = front_body.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(7.0F, 0.25F, -2.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(77, 0).mirror().addBox(0.0F, -7.0F, -2.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 13.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition left_front_leg_1_r2 = left_front_leg_1.addOrReplaceChild("left_front_leg_1_r2", CubeListBuilder.create().texOffs(92, 0).mirror().addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_front_leg_2 = left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -11.0F, -0.25F));

		PartDefinition left_front_leg_2_r3 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r3", CubeListBuilder.create().texOffs(84, 18).mirror().addBox(-2.5F, -6.5F, -3.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(4.0F, 16.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition left_front_leg_2_r4 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r4", CubeListBuilder.create().texOffs(108, 0).mirror().addBox(-2.0F, -14.5F, -3.0F, 4.0F, 29.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 12.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition right_middle_leg = front_body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, 1.25F, 3.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition right_middle_leg_r1 = right_middle_leg.addOrReplaceChild("right_middle_leg_r1", CubeListBuilder.create().texOffs(90, 38).addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.5524F, 6.2999F, -0.5F, 0.0F, 1.1781F, 0.1309F));

		PartDefinition right_middle_leg_1 = right_middle_leg.addOrReplaceChild("right_middle_leg_1", CubeListBuilder.create(), PartPose.offset(-3.0F, 12.0F, -0.5F));

		PartDefinition right_middle_leg_1_r1 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 38).addBox(-2.0F, -7.0F, -1.5F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.0F, -4.0F, 0.0F, 0.0F, -1.0036F, -0.3054F));

		PartDefinition right_middle_leg_2 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -10.0F, 0.25F));

		PartDefinition right_middle_leg_2_r1 = right_middle_leg_2.addOrReplaceChild("right_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 35).addBox(-1.0F, -14.5F, -2.0F, 3.0F, 28.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_middle_leg = front_body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, 1.25F, 3.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition left_middle_leg_r1 = left_middle_leg.addOrReplaceChild("left_middle_leg_r1", CubeListBuilder.create().texOffs(90, 38).mirror().addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.5524F, 6.2999F, -0.5F, 0.0F, -1.1781F, -0.1309F));

		PartDefinition left_middle_leg_1 = left_middle_leg.addOrReplaceChild("left_middle_leg_1", CubeListBuilder.create(), PartPose.offset(3.0F, 12.0F, -0.5F));

		PartDefinition left_middle_leg_1_r1 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 38).mirror().addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.0F, -4.0F, 0.5F, 0.0F, 1.0036F, 0.3054F));

		PartDefinition left_middle_leg_2 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -10.0F, 0.25F));

		PartDefinition left_middle_leg_2_r1 = left_middle_leg_2.addOrReplaceChild("left_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 35).mirror().addBox(-2.0F, -14.5F, -2.0F, 3.0F, 28.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 12.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition hind_body = body.addOrReplaceChild("hind_body", CubeListBuilder.create().texOffs(0, 46).addBox(-5.0F, -3.0F, 4.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.5F));

		PartDefinition hind_body_r1 = hind_body.addOrReplaceChild("hind_body_r1", CubeListBuilder.create().texOffs(29, 41).addBox(-4.0F, -3.5F, -6.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 5.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = hind_body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.5F, 3.5F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(62, 52).addBox(-1.0F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.9707F, 2.0356F, -2.0944F, -0.9599F, -0.5236F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-2.0F, 5.0F, 3.0F));

		PartDefinition right_hind_leg_1_r1 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_1_r1", CubeListBuilder.create().texOffs(74, 49).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -3.6636F, 3.4217F, -0.5672F, 0.0F, -0.7854F));

		PartDefinition right_hind_leg_2 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create(), PartPose.offset(-5.0F, -6.0F, 6.0F));

		PartDefinition right_hind_leg_2_r1 = right_hind_leg_2.addOrReplaceChild("right_hind_leg_2_r1", CubeListBuilder.create().texOffs(65, 15).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 10.5103F, 2.9137F, 0.1745F, -0.1047F, 0.0873F));

		PartDefinition left_hind_leg = hind_body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 2.5F, 3.5F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(62, 52).mirror().addBox(-2.0F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.9707F, 2.0356F, -2.0944F, 0.9599F, 0.5236F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create(), PartPose.offset(2.0F, 5.0F, 3.0F));

		PartDefinition left_hind_leg_1_r1 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_1_r1", CubeListBuilder.create().texOffs(74, 49).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -3.6636F, 3.4217F, -0.5672F, 0.0F, 0.7854F));

		PartDefinition left_hind_leg_2 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create(), PartPose.offset(5.0F, -6.0F, 6.0F));

		PartDefinition left_hind_leg_2_r1 = left_hind_leg_2.addOrReplaceChild("left_hind_leg_2_r1", CubeListBuilder.create().texOffs(65, 15).mirror().addBox(-2.0F, -14.0F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 10.5103F, 2.9137F, 0.1745F, 0.1047F, -0.0873F));

		PartDefinition tail = hind_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.3028F, 13.4513F, 2.7489F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(40, 18).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0743F, -0.3811F, -0.9686F, 0.0F, 0.0F));

		PartDefinition right_tail = tail.addOrReplaceChild("right_tail", CubeListBuilder.create(), PartPose.offset(-1.75F, 1.545F, -1.4228F));

		PartDefinition right_tail_r1 = right_tail.addOrReplaceChild("right_tail_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1716F, 11.4446F, -8.621F, -0.2114F, 0.0787F, 0.1565F));

		PartDefinition right_tail_r2 = right_tail.addOrReplaceChild("right_tail_r2", CubeListBuilder.create().texOffs(52, 34).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1329F, 4.1275F, -5.7516F, -0.6497F, 0.0842F, 0.1009F));

		PartDefinition right_tail_r3 = right_tail.addOrReplaceChild("right_tail_r3", CubeListBuilder.create().texOffs(52, 26).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7179F, 0.737F, -1.8292F, -1.0893F, 0.0774F, 0.0404F));

		PartDefinition left_tail = tail.addOrReplaceChild("left_tail", CubeListBuilder.create(), PartPose.offset(1.75F, 1.545F, -1.4228F));

		PartDefinition left_tail_r1 = left_tail.addOrReplaceChild("left_tail_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.1716F, 11.4446F, -8.621F, -0.2114F, -0.0787F, -0.1565F));

		PartDefinition left_tail_r2 = left_tail.addOrReplaceChild("left_tail_r2", CubeListBuilder.create().texOffs(52, 34).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1329F, 4.1275F, -5.7516F, -0.6497F, -0.0842F, -0.1009F));

		PartDefinition left_tail_r3 = left_tail.addOrReplaceChild("left_tail_r3", CubeListBuilder.create().texOffs(52, 26).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.7179F, 0.737F, -1.8292F, -1.0893F, -0.0774F, -0.0404F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(ScavengerAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, ScavengerAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, ScavengerAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, ScavengerAnimation.ATTACK2, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}