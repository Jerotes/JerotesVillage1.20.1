package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.LakeLoachAnimation;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.LakeLoachEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modellake_loach<T extends LakeLoachEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "lake_loach"), "main");
	private final ModelPart root;
	private final ModelPart body;


	public Modellake_loach(ModelPart modelPart) {
		this.root = modelPart;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, -3.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(17, 11).mirror().addBox(-2.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.9829F, 2.0111F, -2.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(17, 11).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9829F, 2.0111F, -2.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(20, 1).addBox(-0.5F, -1.0F, 0.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 0.0F));

		PartDefinition right_fin = body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(22, 16).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition left_fin = body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(22, 16).mirror().addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 1.0F, 1.0F, -1.5708F, 0.0F, 0.7854F));

		PartDefinition body_middle = body.addOrReplaceChild("body_middle", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 7.0F));

		PartDefinition fin_back_1 = body_middle.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(10, 28).addBox(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 7.0F));

		PartDefinition body_back = body_middle.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 1.0F, 0.0F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 8.0F));

		PartDefinition fin_back_2 = body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(18, 29).addBox(0.0F, -0.5F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 5.5F));

		PartDefinition tail = body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 2.0F, 9.0F));

		PartDefinition right_fin_back = body_back.addOrReplaceChild("right_fin_back", CubeListBuilder.create().texOffs(22, 18).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.0F, 0.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition left_fin_back = body_back.addOrReplaceChild("left_fin_back", CubeListBuilder.create().texOffs(22, 18).mirror().addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 3.0F, 0.0F, -1.5708F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.body.yRot = f4 * ((float)Math.PI / 180F);
		this.body.xRot = f5 * ((float)Math.PI / 180F);
		if (t.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7) {
			this.body.xRot += -0.05f - 0.05f * Mth.cos(f3 * 0.3f);
		}
		this.animateWalk(LakeLoachAnimation.SWIM, f, f2, 1.5f, 2.0f);
	}
}

