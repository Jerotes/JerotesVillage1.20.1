package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.Elite.LightningWormEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modellightning_worm<T extends LightningWormEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "lightning_worm"), "main");
	private final ModelPart root;

	public Modellightning_worm(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0F, -5.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-4.5F, -4.0F, -7.0F, 9.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-5.5F, -5.0F, -7.0F, 11.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).addBox(-5.5F, -5.0F, -5.25F, 11.0F, 11.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition right_wing = head.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, -4.0F, -3.0F));

		PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(37, 0).addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 9.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition right_wing_r2 = right_wing.addOrReplaceChild("right_wing_r2", CubeListBuilder.create().texOffs(37, 0).addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_wing = head.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, -4.0F, -3.0F));

		PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(37, 0).mirror().addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 9.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_wing_r2 = left_wing.addOrReplaceChild("left_wing_r2", CubeListBuilder.create().texOffs(37, 0).mirror().addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 0.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}