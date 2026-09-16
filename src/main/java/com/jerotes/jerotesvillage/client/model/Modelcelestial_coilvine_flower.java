package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CelestialCoilvineFlowerAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.CelestialCoilvineFlowerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelcelestial_coilvine_flower<T extends CelestialCoilvineFlowerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "celestial_coilvine_flower"), "main");
	private final ModelPart root;
	public Modelcelestial_coilvine_flower(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 44).addBox(-5.0F, 0.6F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(-27, 0).addBox(-13.5F, 0.0F, -13.5F, 27.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.25F, 0.0F));

		PartDefinition petal_1 = body.addOrReplaceChild("petal_1", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1202F, 0.0F));

		PartDefinition petal_1_1 = petal_1.addOrReplaceChild("petal_1_1", CubeListBuilder.create(), PartPose.offset(0.0F, 3.4202F, 4.0F));

		PartDefinition petal_r1 = petal_1_1.addOrReplaceChild("petal_r1", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition petal_1_2 = petal_1.addOrReplaceChild("petal_1_2", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.4202F, 0.0F));

		PartDefinition petal_r2 = petal_1_2.addOrReplaceChild("petal_r2", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_1_3 = petal_1.addOrReplaceChild("petal_1_3", CubeListBuilder.create(), PartPose.offset(0.0F, 3.4202F, -4.0F));

		PartDefinition petal_r3 = petal_1_3.addOrReplaceChild("petal_r3", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_1_4 = petal_1.addOrReplaceChild("petal_1_4", CubeListBuilder.create(), PartPose.offset(4.0F, 3.4202F, 0.0F));

		PartDefinition petal_r4 = petal_1_4.addOrReplaceChild("petal_r4", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		PartDefinition petal_2 = body.addOrReplaceChild("petal_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.1202F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition petal_2_1 = petal_2.addOrReplaceChild("petal_2_1", CubeListBuilder.create(), PartPose.offset(0.0F, 3.4202F, 4.0F));

		PartDefinition petal_r5 = petal_2_1.addOrReplaceChild("petal_r5", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition petal_2_2 = petal_2.addOrReplaceChild("petal_2_2", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.4202F, 0.0F));

		PartDefinition petal_r6 = petal_2_2.addOrReplaceChild("petal_r6", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.3491F));

		PartDefinition petal_2_3 = petal_2.addOrReplaceChild("petal_2_3", CubeListBuilder.create(), PartPose.offset(0.0F, 3.4202F, -4.0F));

		PartDefinition petal_r7 = petal_2_3.addOrReplaceChild("petal_r7", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.3491F, 3.1416F, 0.0F));

		PartDefinition petal_2_4 = petal_2.addOrReplaceChild("petal_2_4", CubeListBuilder.create(), PartPose.offset(4.0F, 3.4202F, 0.0F));

		PartDefinition petal_r8 = petal_2_4.addOrReplaceChild("petal_r8", CubeListBuilder.create().texOffs(-15, 32).addBox(-5.0F, -0.684F, 0.6206F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.3491F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, CelestialCoilvineFlowerAnimation.START, f3);
		this.animate(t.spellAnimationState, CelestialCoilvineFlowerAnimation.SPELL, f3);
		this.animate(t.stopAnimationState, CelestialCoilvineFlowerAnimation.STOP, f3);
	}
}