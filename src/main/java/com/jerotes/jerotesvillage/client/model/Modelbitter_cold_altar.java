package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BitterColdAltarAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelbitter_cold_altar<T extends BitterColdAltarEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bitter_cold_altar"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart main;
	private final ModelPart chain1;
	private final ModelPart chain2;
	private final ModelPart chain3;
	private final ModelPart chain4;
	private final ModelPart stair;
	private final ModelPart middle;
	private final ModelPart bottom;

	public Modelbitter_cold_altar(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.main = this.body.getChild("main");
		this.chain1 = this.main.getChild("chain1");
		this.chain2 = this.main.getChild("chain2");
		this.chain3 = this.main.getChild("chain3");
		this.chain4 = this.main.getChild("chain4");
		this.stair = this.body.getChild("stair");
		this.middle = this.body.getChild("middle");
		this.bottom = this.body.getChild("bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, 32.3636F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, 16.3636F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(72, 8).addBox(-8.0F, -15.6364F, -16.0F, 16.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(72, 8).addBox(-8.0F, -15.6364F, 8.0F, 16.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(72, 0).addBox(8.0F, -15.6364F, -8.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(72, 0).addBox(-16.0F, -15.6364F, -8.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -23.6364F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -15.6364F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-8.0F, 0.3636F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-8.0F, 0.3636F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.25F))
				.texOffs(64, 32).addBox(-8.0F, -15.6364F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -52.3636F, 0.0F));

		PartDefinition chain1 = main.addOrReplaceChild("chain1", CubeListBuilder.create(), PartPose.offsetAndRotation(-20.5F, 21.3636F, 12.0F, 0.2182F, 0.0F, 0.5672F));

		PartDefinition body_r1 = chain1.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(64, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r2 = chain1.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(70, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition chain2 = main.addOrReplaceChild("chain2", CubeListBuilder.create(), PartPose.offsetAndRotation(-20.5F, 21.3636F, -12.0F, -0.2182F, 0.0F, 0.5672F));

		PartDefinition body_r3 = chain2.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(64, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r4 = chain2.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(70, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition chain3 = main.addOrReplaceChild("chain3", CubeListBuilder.create(), PartPose.offsetAndRotation(20.5F, 21.3636F, -12.0F, -0.2182F, 0.0F, -0.5672F));

		PartDefinition body_r5 = chain3.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(64, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r6 = chain3.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(70, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition chain4 = main.addOrReplaceChild("chain4", CubeListBuilder.create(), PartPose.offsetAndRotation(20.5F, 21.3636F, 12.0F, 0.2182F, 0.0F, -0.5672F));

		PartDefinition body_r7 = chain4.addOrReplaceChild("body_r7", CubeListBuilder.create().texOffs(64, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(64, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r8 = chain4.addOrReplaceChild("body_r8", CubeListBuilder.create().texOffs(70, 32).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 24.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 32).addBox(-1.5F, 8.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition stair = body.addOrReplaceChild("stair", CubeListBuilder.create().texOffs(64, 0).addBox(8.0F, -4.0F, -56.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-24.0F, -4.0F, -56.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-40.0F, -4.0F, -40.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(24.0F, -4.0F, -40.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-40.0F, -4.0F, 24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(24.0F, -4.0F, 24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -4.0F, -56.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(40.0F, -4.0F, -24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(40.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(40.0F, -4.0F, 8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(8.0F, -4.0F, 40.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -4.0F, 40.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-24.0F, -4.0F, 40.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-56.0F, -4.0F, 8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-56.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-56.0F, -4.0F, -24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition middle = body.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -8.0F, -40.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -8.0F, -40.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(8.0F, -8.0F, -40.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-24.0F, -8.0F, 24.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-40.0F, -8.0F, 8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(24.0F, -8.0F, -24.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(24.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(24.0F, -8.0F, 8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-40.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-40.0F, -8.0F, -24.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -8.0F, 24.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(8.0F, -8.0F, 24.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, -24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-24.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(8.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-24.0F, -4.0F, 8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(8.0F, -4.0F, 8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -4.0F, 8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(8.0F, -4.0F, -24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-24.0F, -4.0F, -24.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, BitterColdAltarAnimation.START, f3);
		this.animate(t.stopAnimationState, BitterColdAltarAnimation.STOP, f3);
	}
}