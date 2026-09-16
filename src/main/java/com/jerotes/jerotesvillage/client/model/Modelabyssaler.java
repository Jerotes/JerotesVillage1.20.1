package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.AbyssalerAnimation;
import com.jerotes.jerotesvillage.entity.Animal.Fish.AbyssalerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelabyssaler<T extends AbyssalerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "abyssaler"), "main");
	private final ModelPart root;
	private final ModelPart body;


	public Modelabyssaler(ModelPart modelPart) {
		this.root = modelPart;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -2.0F, -2.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(24, 6).addBox(-1.0F, -1.5F, -4.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(22, 12).addBox(-1.4F, -0.5F, -4.65F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
				.texOffs(22, 12).addBox(-0.6F, -0.5F, -0.85F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
				.texOffs(22, 12).addBox(-1.4F, -0.5F, -0.85F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
				.texOffs(22, 12).addBox(-0.6F, -0.5F, -4.65F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.5F, 1.0F, 0.0F));

		PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -3.5F, 0.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition right_fin = body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.5F, 1.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition left_fin = body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 1.5F, 1.0F, -1.5708F, 0.0F, 0.7854F));

		PartDefinition fin_back_1 = body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 8.0F));

		PartDefinition body_back = body.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 19).addBox(-1.5F, -2.75F, 0.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition fin_back_2 = body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 7.0F));

		PartDefinition fin_back_3 = body_back.addOrReplaceChild("fin_back_3", CubeListBuilder.create().texOffs(14, -3).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 6.0F));

		PartDefinition tail = body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 13).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition right_fin_back = body_back.addOrReplaceChild("right_fin_back", CubeListBuilder.create().texOffs(8, 0).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.5F, 1.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition left_fin_back = body_back.addOrReplaceChild("left_fin_back", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 1.5F, 1.0F, -1.5708F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
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
		if (t.downFish()) {
			this.body.zRot = (t.getAirTick() - 150) * 0.026f;
		}
		this.animateWalk(AbyssalerAnimation.SWIM, f, f2, 1.5f, 2.0f);
		this.animate(((AbyssalerEntity)t).attackAnimationState, AbyssalerAnimation.ATTACK, f3);
	}
}

