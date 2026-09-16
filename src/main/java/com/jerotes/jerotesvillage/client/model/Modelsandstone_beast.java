package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SandstoneBeastAnimation;
import com.jerotes.jerotesvillage.entity.Animal.SandstoneBeastEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelsandstone_beast<T extends SandstoneBeastEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "sandstone_beast"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelsandstone_beast(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -10.0F, 14.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-6.0F, 0.0F, -9.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, -3.0F, -14.75F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(88, 83).addBox(-1.0F, -4.0F, -8.0F, 9.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 2.0F, -2.0F, -0.7854F, 0.829F, 1.5708F));

		PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offset(0.0F, -5.5833F, -2.3333F));

		PartDefinition horn_r1 = horns.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(106, 69).mirror().addBox(-4.5F, -4.5F, -1.0F, 9.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.1667F, -6.1667F, 0.2182F, -0.2182F, 0.7854F));

		PartDefinition horn_r2 = horns.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(104, 51).mirror().addBox(-1.0F, -7.0F, -2.0F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(104, 51).mirror().addBox(-11.0F, -7.0F, -2.0F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.5833F, -7.4167F, 0.829F, 0.0F, 0.0F));

		PartDefinition left_horn = horns.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(9.0F, -1.4167F, -1.6667F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(116, 51).mirror().addBox(-1.0F, -7.0F, -2.0F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 1.0472F, 0.0F, 0.4363F));

		PartDefinition right_horn = horns.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-9.0F, -1.4167F, -1.6667F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(116, 51).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 1.0472F, 0.0F, -0.4363F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -1.5F, -8.0F, 12.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.7273F, -7.6364F, -5.0F));

		PartDefinition stone_r1 = body.addOrReplaceChild("stone_r1", CubeListBuilder.create().texOffs(0, 120).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7273F, -2.8636F, 3.0F, 1.5708F, 0.0F, -0.7854F));

		PartDefinition stone_r2 = body.addOrReplaceChild("stone_r2", CubeListBuilder.create().texOffs(0, 112).addBox(-2.0F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.7273F, -2.8636F, -4.0F, 1.5708F, -0.48F, -1.0908F));

		PartDefinition stone_r3 = body.addOrReplaceChild("stone_r3", CubeListBuilder.create().texOffs(0, 120).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2727F, -2.8636F, 3.0F, 1.5708F, 0.0F, 0.7854F));

		PartDefinition stone_r4 = body.addOrReplaceChild("stone_r4", CubeListBuilder.create().texOffs(0, 102).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7273F, -3.8636F, 5.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition stone_r5 = body.addOrReplaceChild("stone_r5", CubeListBuilder.create().texOffs(0, 120).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7273F, -3.8636F, -4.0F, 1.5708F, 0.0F, 0.7854F));

		PartDefinition stone_r6 = body.addOrReplaceChild("stone_r6", CubeListBuilder.create().texOffs(16, 102).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.2727F, 4.1364F, -4.0F, 1.5708F, 0.0F, 0.7854F));

		PartDefinition stone_r7 = body.addOrReplaceChild("stone_r7", CubeListBuilder.create().texOffs(16, 102).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.7273F, 4.1364F, -4.0F, 1.5708F, 0.0F, -0.7854F));

		PartDefinition stone_r8 = body.addOrReplaceChild("stone_r8", CubeListBuilder.create().texOffs(0, 112).addBox(-8.0F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.2727F, -2.8636F, -4.0F, 1.5708F, 0.48F, 1.0908F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 73).addBox(-8.0F, -6.5F, -9.0F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7273F, 7.1364F, 13.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 37).addBox(-10.0F, -8.0F, -10.0F, 20.0F, 16.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7273F, 6.6364F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(92, 108).addBox(-7.0F, -7.5F, -8.5F, 14.0F, 16.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(-0.7273F, 6.6364F, -3.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.7273F, 1.6364F, 19.5F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(48, 73).addBox(-3.0F, 23.5F, 4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, -6.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(64, 73).addBox(-2.0F, 6.5F, 4.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.5F, -6.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_ball = tail.addOrReplaceChild("tail_ball", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 19.5F));

		PartDefinition tail_r3 = tail_ball.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(48, 80).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, -0.7854F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -2.5F, -2.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 2.5F, 12.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(99, 19).addBox(-3.0F, -0.5F, -2.2F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(96, 33).addBox(-4.0F, 6.5F, -3.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 9.0F, 2.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-6.0F, -2.5F, -2.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 2.5F, 12.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(99, 19).mirror().addBox(-3.0F, -0.5F, -2.2F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 33).mirror().addBox(-4.0F, 6.5F, -3.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 9.0F, 2.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 1.0F, -11.5F));

		PartDefinition stone_r9 = right_front_leg.addOrReplaceChild("stone_r9", CubeListBuilder.create().texOffs(16, 120).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 5.5F, 0.5F, 1.5708F, 0.0F, -0.7854F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(67, 20).addBox(-3.0F, -0.5F, -4.2F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(64, 34).addBox(-4.0F, 6.5F, -5.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 10.5F, 3.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-5.0F, -2.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 1.0F, -11.5F));

		PartDefinition stone_r10 = left_front_leg.addOrReplaceChild("stone_r10", CubeListBuilder.create().texOffs(16, 120).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.5F, 0.5F, 1.5708F, 0.0F, -0.7854F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(67, 20).mirror().addBox(-3.0F, -0.5F, -4.2F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 34).mirror().addBox(-4.0F, 6.5F, -5.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 10.5F, 3.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.3f;
			this.head.yScale = 1.3f;
			this.head.zScale = 1.3f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(SandstoneBeastAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, SandstoneBeastAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, SandstoneBeastAnimation.ATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
