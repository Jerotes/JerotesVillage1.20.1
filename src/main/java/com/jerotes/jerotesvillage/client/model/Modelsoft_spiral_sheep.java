package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SoftSpiralSheepAnimation;
import com.jerotes.jerotesvillage.entity.Animal.SoftSpiralSheepEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelsoft_spiral_sheep<T extends SoftSpiralSheepEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "soft_spiral_sheep"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelsoft_spiral_sheep(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -6.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(45, 53).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.2314F, -2.4217F, 0.6127F, -0.3644F, -0.2455F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(45, 53).mirror().addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -2.2314F, -2.4217F, 0.6127F, 0.3644F, 0.2455F));

		PartDefinition left_horn_r1 = head.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(52, 60).mirror().addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 57).mirror().addBox(1.5F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.7577F, -4.3656F, 0.3433F, -0.5672F, 0.0F, 0.7418F));

		PartDefinition left_horn_r2 = head.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(36, 52).mirror().addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.49F, -4.5F, -1.0F, -0.5672F, 0.0F, 0.7418F));

		PartDefinition right_horn_r1 = head.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(44, 57).addBox(-3.5F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(52, 60).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.7577F, -4.3656F, 0.3433F, -0.5672F, 0.0F, -0.7418F));

		PartDefinition right_horn_r2 = head.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(36, 52).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.49F, -4.5F, -1.0F, -0.5672F, 0.0F, -0.7418F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(38, 38).addBox(-1.0F, -6.0F, -2.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(40, 4).addBox(-2.5F, -7.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(38, 22).addBox(-2.5F, -7.0F, -6.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.5F, 4.0F, -3.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 2).addBox(-5.5F, -10.0F, -11.0F, 11.0F, 10.0F, 10.0F, new CubeDeformation(0.25F))
				.texOffs(0, 22).addBox(-5.0F, -10.0F, -12.0F, 10.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 49).addBox(-1.5F, 7.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 10.0F, 6.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-1.5F, 7.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 10.0F, 6.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 49).addBox(-1.5F, 7.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 10.0F, -5.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-1.5F, 7.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 10.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	private float headXRot;

	public void prepareMobModel(T p_103687_, float p_103688_, float p_103689_, float p_103690_) {
		super.prepareMobModel(p_103687_, p_103688_, p_103689_, p_103690_);
		this.head.y = 6.0F + p_103687_.getHeadEatPositionScale(p_103690_) * 9.0F;
		this.headXRot = p_103687_.getHeadEatAngleScale(p_103690_);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.3f;
			this.head.yScale = 1.3f;
			this.head.zScale = 1.3f;
		} else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		if (this.headXRot == t.getXRot() * 0.017453292f) {
			this.head.xRot = f5 * ((float) Math.PI / 180F);
		}
		else {
			this.head.xRot = this.headXRot;
		}
			this.animateWalk(SoftSpiralSheepAnimation.RUN, f, f2 * t.getNeedRush() / 20f, 2.0f, 2.0f);
			this.animateWalk(SoftSpiralSheepAnimation.WALK, f, f2 * (1f - t.getNeedRush() / 20f), 2.0f, 2.0f);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}