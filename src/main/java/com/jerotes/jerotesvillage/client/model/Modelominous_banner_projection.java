package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.OminousBannerProjectionAnimation;
import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelominous_banner_projection<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ominous_banner_projection"), "main");
	private final ModelPart root;


	public Modelominous_banner_projection(ModelPart modelPart) {
		this.root = modelPart;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stand = partdefinition.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(16, 49).addBox(-8.0F, 30.5F, -9.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(64, 48).addBox(-4.0F, 29.5F, -5.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 1.0F));

		PartDefinition main_stand = stand.addOrReplaceChild("main_stand", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -89.5F, -2.0F, 4.0F, 91.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 66).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(32, 66).addBox(-2.0F, -68.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 30.0F, -1.0F));

		PartDefinition stand_r1 = main_stand.addOrReplaceChild("stand_r1", CubeListBuilder.create().texOffs(74, 40).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -89.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition top = main_stand.addOrReplaceChild("top", CubeListBuilder.create().texOffs(16, 0).addBox(-20.0F, -2.0F, -3.0F, 40.0F, 4.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(90, 8).addBox(-10.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(74, 8).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(74, 16).addBox(6.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -80.5F, 1.0F));

		PartDefinition top_r1 = top.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(74, 24).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-20.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition top_r2 = top.addOrReplaceChild("top_r2", CubeListBuilder.create().texOffs(74, 24).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(20.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone = top.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(-8.0F, 0.0F, -1.0F));

		PartDefinition right_slate = top.addOrReplaceChild("right_slate", CubeListBuilder.create().texOffs(58, 8).addBox(-3.5F, 0.0F, -2.0F, 7.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.5F, 2.0F, -2.0F));

		PartDefinition slate = top.addOrReplaceChild("slate", CubeListBuilder.create().texOffs(16, 8).addBox(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -2.0F));

		PartDefinition left_slate = top.addOrReplaceChild("left_slate", CubeListBuilder.create().texOffs(58, 8).mirror().addBox(-3.5F, 0.0F, -2.0F, 7.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(14.5F, 2.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (!(t instanceof BossShowEntity)) {
			this.animate(((OminousBannerProjectionEntity) t).idleAnimationState, OminousBannerProjectionAnimation.IDLE, f3);
			this.animate(((OminousBannerProjectionEntity) t).roundAnimationState, OminousBannerProjectionAnimation.ROUND, f3);
			this.animate(((OminousBannerProjectionEntity) t).deadAnimationState, OminousBannerProjectionAnimation.DEAD, f3);
		}
	}
}

