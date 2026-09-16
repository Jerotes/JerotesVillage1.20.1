package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ImprisonVineAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.ImprisonVineEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelimprison_vine<T extends ImprisonVineEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "imprison_vine"), "main");
	private final ModelPart root;
	public Modelimprison_vine(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition base = body.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition base1 = base.addOrReplaceChild("base1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition base_r1 = base1.addOrReplaceChild("base_r1", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition base2 = base.addOrReplaceChild("base2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition base_r2 = base2.addOrReplaceChild("base_r2", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition base3 = base.addOrReplaceChild("base3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition base_r3 = base3.addOrReplaceChild("base_r3", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition base4 = base.addOrReplaceChild("base4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition base_r4 = base4.addOrReplaceChild("base_r4", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition upper_jaw = body.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -32.0F, -12.0F, 16.0F, 40.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition lower_jaw = body.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 64).addBox(-4.5F, -32.0F, -12.0F, 16.0F, 40.0F, 24.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition base5 = body.addOrReplaceChild("base5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition base6 = base5.addOrReplaceChild("base6", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition base_r5 = base6.addOrReplaceChild("base_r5", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition base7 = base5.addOrReplaceChild("base7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition base_r6 = base7.addOrReplaceChild("base_r6", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition base8 = base5.addOrReplaceChild("base8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition base_r7 = base8.addOrReplaceChild("base_r7", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition base9 = base5.addOrReplaceChild("base9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition base_r8 = base9.addOrReplaceChild("base_r8", CubeListBuilder.create().texOffs(80, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 63.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, ImprisonVineAnimation.START, f3);
		this.animate(t.deadAnimationState, ImprisonVineAnimation.DEAD, f3);
	}
}