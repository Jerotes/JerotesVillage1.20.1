package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BrightGrassCarpWalkerAnimation;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.BrightGrassCarpEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelbright_grass_carp_walker<T extends BrightGrassCarpEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bright_grass_carp_walker"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart body_front;
	private final ModelPart head;


	public Modelbright_grass_carp_walker(ModelPart modelPart) {
		this.root = modelPart;
		this.body = root.getChild("body");
		this.body_front = body.getChild("body_front");
		this.head = body_front.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.0F, -3.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition body_front_r1 = body_front.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -2.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.5F, -2.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head = body_front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -2.75F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(24, 7).addBox(-1.0F, 0.7F, -4.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -6.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition right_fin = body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.0F, -5.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition left_fin = body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 2.0F, -5.0F, -1.5708F, 0.0F, 0.7854F));

		PartDefinition fin_back_1 = body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(0, -2).addBox(0.0F, -2.5F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 1.0F));

		PartDefinition body_back = body.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 3.75F, -0.9599F, 0.0F, 0.0F));

		PartDefinition fin_back_2 = body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(14, -3).addBox(0.0F, -1.5F, -2.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 6.0F));

		PartDefinition tail = body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 7).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 7.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_fin_back = body_back.addOrReplaceChild("right_fin_back", CubeListBuilder.create().texOffs(8, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.8282F, 1.4078F, -0.9385F, 0.8743F, -1.0614F));

		PartDefinition left_fin_back = body_back.addOrReplaceChild("left_fin_back", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(0.0F, -2.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 1.8282F, 1.4078F, -0.9385F, -0.8743F, 1.0614F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(BrightGrassCarpWalkerAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(t.idleAnimationState, BrightGrassCarpWalkerAnimation.IDLE, f3);
	}
}

