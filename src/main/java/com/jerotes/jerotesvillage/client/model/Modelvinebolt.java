package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.VineboltEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelvinebolt<T extends VineboltEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "vinebolt"), "main");
	private final ModelPart root;
	public Modelvinebolt(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -13.0F, 2.0F, 2.0F, 29.0F, new CubeDeformation(0.01F))
				.texOffs(0, 31).addBox(-1.0F, -1.0F, -13.0F, 2.0F, 2.0F, 29.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 7.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(62, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -13.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.25F, 0.0F, -13.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, -13.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition body_r4 = body.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(0, 62).addBox(-3.0F, 0.0F, -5.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.0F, 0.0F, -2.8798F));

		PartDefinition body_r5 = body.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(0, 62).addBox(-3.0F, 0.0F, -5.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.0F, 0.0F, -0.2618F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}
}