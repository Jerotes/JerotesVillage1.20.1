package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DustMothAnimation;
import com.jerotes.jerotesvillage.entity.Animal.DustMothEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modeldust_moth<T extends DustMothEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "dust_moth"), "main");
	private final ModelPart root;

	public Modeldust_moth(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(-2.5F, -1.8F, -3.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition left_antenna = body.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(18, -3).mirror().addBox(-0.0196F, -1.0F, -2.666F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.3366F, -1.0F, -5.083F, 0.0F, -1.0472F, 0.0F));

		PartDefinition right_antenna = body.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(18, -3).addBox(0.0196F, -1.0F, -2.666F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3366F, -1.0F, -5.083F, 0.0F, 1.0472F, 0.0F));

		PartDefinition front_legs = body.addOrReplaceChild("front_legs", CubeListBuilder.create(), PartPose.offset(1.5F, 3.0F, -2.0F));

		PartDefinition front_legs_r1 = front_legs.addOrReplaceChild("front_legs_r1", CubeListBuilder.create().texOffs(18, 2).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.0F, -1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition front_legs_r2 = front_legs.addOrReplaceChild("front_legs_r2", CubeListBuilder.create().texOffs(18, 2).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition middle_legs = body.addOrReplaceChild("middle_legs", CubeListBuilder.create(), PartPose.offset(1.5F, 3.0F, 0.0F));

		PartDefinition middle_legs_r1 = middle_legs.addOrReplaceChild("middle_legs_r1", CubeListBuilder.create().texOffs(18, 4).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition middle_legs_r2 = middle_legs.addOrReplaceChild("middle_legs_r2", CubeListBuilder.create().texOffs(18, 4).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.0F, -1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition back_legs = body.addOrReplaceChild("back_legs", CubeListBuilder.create(), PartPose.offset(1.5F, 3.0F, 2.0F));

		PartDefinition back_legs_r1 = back_legs.addOrReplaceChild("back_legs_r1", CubeListBuilder.create().texOffs(18, 6).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.0F, -1.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition back_legs_r2 = back_legs.addOrReplaceChild("back_legs_r2", CubeListBuilder.create().texOffs(18, 6).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 6).mirror().addBox(0.0F, 0.0F, -1.0F, 9.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(22, 0).mirror().addBox(0.0F, 0.1F, 2.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -1.0F, -3.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 6).addBox(-9.0F, 0.0F, -1.0F, 9.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(22, 0).addBox(-6.0F, 0.1F, 2.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, -3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (!t.isBaby()) {
			this.animateWalk(DustMothAnimation.FLY, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.idleAnimationState, DustMothAnimation.IDLE, f3);
		this.animate(t.dustAnimationState, DustMothAnimation.DUST, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}

