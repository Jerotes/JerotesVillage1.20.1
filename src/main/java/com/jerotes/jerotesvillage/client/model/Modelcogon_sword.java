package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CogonSwordAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.CogonSwordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelcogon_sword<T extends CogonSwordEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "cogon_sword"), "main");
	private final ModelPart root;
	public Modelcogon_sword(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition base_vine_add_r1 = body.addOrReplaceChild("base_vine_add_r1", CubeListBuilder.create().texOffs(14, 0).addBox(-5.5F, -4.217F, -22.6865F, 11.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 2.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition base_vine_add_r2 = body.addOrReplaceChild("base_vine_add_r2", CubeListBuilder.create().texOffs(14, 0).addBox(-5.5F, -4.217F, -22.6865F, 11.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -12.0F, 0.0F, 0.0F, 1.5708F, -1.0036F));

		PartDefinition base_vine_add_r3 = body.addOrReplaceChild("base_vine_add_r3", CubeListBuilder.create().texOffs(14, 0).addBox(-5.5F, -4.217F, -22.6865F, 11.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -12.0F, 0.0F, 0.0F, -1.5708F, 1.0036F));

		PartDefinition base_vine_add_r4 = body.addOrReplaceChild("base_vine_add_r4", CubeListBuilder.create().texOffs(14, 0).addBox(-5.5F, -4.217F, -22.6865F, 11.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -2.0F, 1.0036F, 3.1416F, 0.0F));

		PartDefinition sword = body.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(76, 13).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(76, 31).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(76, 0).addBox(-3.0F, -19.0F, -5.0F, 6.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 69).addBox(-1.0F, -81.0F, -2.5F, 2.0F, 62.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -81.0F, -3.5F, 1.0F, 62.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition sword_r1 = sword.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(76, 39).addBox(0.0F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.5F, -81.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, CogonSwordAnimation.START, f3);
	}
}