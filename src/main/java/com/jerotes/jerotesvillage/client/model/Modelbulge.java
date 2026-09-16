package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BulgeAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.Smart.SmartBulgeEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelbulge<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bulge"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body_top;
	private final ModelPart body_bottom;

	public Modelbulge(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.head = root.getChild("head");
		this.body_top = root.getChild("body_top");
		this.body_bottom = root.getChild("body_bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition body_top = partdefinition.addOrReplaceChild("body_top", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition stick1 = body_top.addOrReplaceChild("stick1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, -6.0F));

		PartDefinition stick2 = body_top.addOrReplaceChild("stick2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -6.0F));

		PartDefinition stick3 = body_top.addOrReplaceChild("stick3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, 6.0F));

		PartDefinition stick4 = body_top.addOrReplaceChild("stick4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, 6.0F));

		PartDefinition body_bottom = partdefinition.addOrReplaceChild("body_bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition stick5 = body_bottom.addOrReplaceChild("stick5", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, -3.0F));

		PartDefinition stick6 = body_bottom.addOrReplaceChild("stick6", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -3.0F));

		PartDefinition stick7 = body_bottom.addOrReplaceChild("stick7", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 3.0F));

		PartDefinition stick8 = body_bottom.addOrReplaceChild("stick8", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity instanceof BulgeEntity bulge) {
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
			this.animateWalk(BulgeAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
			if (bulge.isAggressive()) {
				this.animate(bulge.idleAnimationState, BulgeAnimation.ATTACKIDLE, ageInTicks);
			} else {
				this.animate(bulge.idleAnimationState, BulgeAnimation.IDLE, ageInTicks);
			}
			this.animate(bulge.shootAnimationState, BulgeAnimation.SHOOT, ageInTicks);
		}
		if (entity instanceof SmartBulgeEntity bulge) {
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
			this.animateWalk(BulgeAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
			if (bulge.isAggressive()) {
				this.animate(bulge.idleAnimationState, BulgeAnimation.ATTACKIDLE, ageInTicks);
			} else {
				this.animate(bulge.idleAnimationState, BulgeAnimation.IDLE, ageInTicks);
			}
			this.animate(bulge.shootAnimationState, BulgeAnimation.SHOOT, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}