package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FireSecretorAnimation;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FireSecretorEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelfire_secretor<T extends FireSecretorEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "fire_secretor"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart leg_1;
	private final ModelPart leg_2;
	private final ModelPart leg_3;

	public Modelfire_secretor(ModelPart modelPart) {
		this.root = modelPart;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.leg_1 = root.getChild("leg_1");
		this.leg_2 = root.getChild("leg_2");
		this.leg_3 = root.getChild("leg_3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -5.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-4.0F, -8.0F, -5.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(56, 0).addBox(-4.0F, -14.0F, -5.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -5.0F, 1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -5.0F, -1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.175F))
				.texOffs(44, 39).addBox(-4.0F, -5.0F, -1.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.7F))
				.texOffs(0, 63).addBox(-6.0F, -5.0F, 4.0F, 12.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 39).mirror().addBox(-1.0F, -1.0F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 57).mirror().addBox(-1.0F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(5.0F, -4.0F, 1.0F));

		PartDefinition legt_hand = left_arm.addOrReplaceChild("legt_hand", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.5F, -2.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 21).mirror().addBox(-2.5F, 4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.0F, 8.0F, -1.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 39).addBox(-4.0F, -1.0F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(24, 57).addBox(-4.0F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, -4.0F, 1.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 48).addBox(-1.5F, -2.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(50, 21).addBox(-1.5F, 4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 8.0F, -1.0F));

		PartDefinition leg_1 = partdefinition.addOrReplaceChild("leg_1", CubeListBuilder.create().texOffs(32, 0).addBox(-4.1F, -3.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.15F)), PartPose.offset(0.1F, 10.0F, 1.0F));

		PartDefinition leg_2 = partdefinition.addOrReplaceChild("leg_2", CubeListBuilder.create().texOffs(27, 27).addBox(-4.6F, -3.0F, -2.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 16.0F, 1.0F));

		PartDefinition leg_3 = partdefinition.addOrReplaceChild("leg_3", CubeListBuilder.create().texOffs(26, 10).addBox(-5.1F, -3.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.1F, 22.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.animateWalk(FireSecretorAnimation.FLY, f, f2, 1.5f, 2.0f);
		this.animate(((FireSecretorEntity)t).fireAnimationState, FireSecretorAnimation.FIRE, f3);
		this.animate(((FireSecretorEntity)t).smokeAnimationState, FireSecretorAnimation.SMOKE, f3);
		this.animate(((FireSecretorEntity)t).idleAnimationState, FireSecretorAnimation.IDLE, f3);
		this.animate(((FireSecretorEntity)t).deadAnimationState, FireSecretorAnimation.DEAD, f3);
	}

}