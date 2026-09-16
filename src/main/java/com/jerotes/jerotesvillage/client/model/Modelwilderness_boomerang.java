package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelwilderness_boomerang<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wilderness_boomerang"), "main");
	private final ModelPart root;
	private final ModelPart body;

	public Modelwilderness_boomerang(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

		PartDefinition rod2_r1 = body.addOrReplaceChild("rod2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition rod1_r1 = body.addOrReplaceChild("rod1_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (!entity.onGround() && entity.getDeltaMovement().x != 0 && entity.getDeltaMovement().y != 0 && entity.getDeltaMovement().z != 0) {
			this.body.xRot = -ageInTicks * 2f;
		}
	}

	public ModelPart getBody() {
		return this.body;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}