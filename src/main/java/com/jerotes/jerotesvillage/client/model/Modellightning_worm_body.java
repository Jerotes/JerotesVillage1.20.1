package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modellightning_worm_body<T extends BasePartLivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "lightning_worm_body"), "main");
	private final ModelPart root;

	public Modellightning_worm_body(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 24).addBox(-4.5F, -4.0F, 5.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(26, 24).addBox(-4.5F, -4.0F, 5.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, -10.0F));

		PartDefinition body_right_wing = body.addOrReplaceChild("body_right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, -4.0F, 7.0F));

		PartDefinition right_wing_r3 = body_right_wing.addOrReplaceChild("right_wing_r3", CubeListBuilder.create().texOffs(37, 7).addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 9.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition right_wing_r4 = body_right_wing.addOrReplaceChild("right_wing_r4", CubeListBuilder.create().texOffs(37, 7).addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition body_left_wing = body.addOrReplaceChild("body_left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, -4.0F, 7.0F));

		PartDefinition left_wing_r3 = body_left_wing.addOrReplaceChild("left_wing_r3", CubeListBuilder.create().texOffs(37, 7).mirror().addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 9.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_wing_r4 = body_left_wing.addOrReplaceChild("left_wing_r4", CubeListBuilder.create().texOffs(37, 7).mirror().addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 0.0F, 3.0F, 0.0F, 0.0F, -0.7854F));

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