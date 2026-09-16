package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ThornSpikeAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.ThornSpikeEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelthorn_spike<T extends ThornSpikeEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "thorn_spike"), "main");
	private final ModelPart root;
	public Modelthorn_spike(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(40, 1).addBox(-2.5F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.004F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r3 = body2.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r4 = body2.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.003F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r5 = body3.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r6 = body3.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(40, 1).addBox(-2.5F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r7 = body4.addOrReplaceChild("body_r7", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r8 = body4.addOrReplaceChild("body_r8", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body5 = body4.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(24, 2).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r9 = body5.addOrReplaceChild("body_r9", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r10 = body5.addOrReplaceChild("body_r10", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body6 = body5.addOrReplaceChild("body6", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r11 = body6.addOrReplaceChild("body_r11", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body_r12 = body6.addOrReplaceChild("body_r12", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, ThornSpikeAnimation.START, f3);
		this.animate(t.attackAnimationState, ThornSpikeAnimation.ATTACK, f3);
		this.animate(t.stopAnimationState, ThornSpikeAnimation.STOP, f3);
	}
}