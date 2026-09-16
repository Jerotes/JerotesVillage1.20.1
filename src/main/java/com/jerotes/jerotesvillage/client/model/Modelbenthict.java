package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BenthictAnimation;
import com.jerotes.jerotesvillage.entity.Animal.BenthictEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelbenthict<T extends BenthictEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "benthict"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart front_legs;
	private final ModelPart middle_front_legs;
	private final ModelPart middle_back_legs;
	private final ModelPart back_legs;

	public Modelbenthict(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.front_legs = root.getChild("front_legs");
		this.middle_front_legs = root.getChild("middle_front_legs");
		this.middle_back_legs = root.getChild("middle_back_legs");
		this.back_legs = root.getChild("back_legs");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 22.0F, -4.0F));

		PartDefinition antenna = head.addOrReplaceChild("antenna", CubeListBuilder.create().texOffs(-4, 16).addBox(-2.5F, 0.0F, -3.75F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.5F, 0.0F));

		PartDefinition front_legs = partdefinition.addOrReplaceChild("front_legs", CubeListBuilder.create().texOffs(0, 10).addBox(-4.5F, -0.5F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 22.5F, -3.0F));

		PartDefinition middle_front_legs = partdefinition.addOrReplaceChild("middle_front_legs", CubeListBuilder.create().texOffs(0, 10).addBox(-4.5F, -0.5F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 22.5F, -1.0F));

		PartDefinition middle_back_legs = partdefinition.addOrReplaceChild("middle_back_legs", CubeListBuilder.create().texOffs(0, 10).addBox(-4.5F, -0.5F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 22.5F, 1.0F));

		PartDefinition back_legs = partdefinition.addOrReplaceChild("back_legs", CubeListBuilder.create().texOffs(0, 10).addBox(-4.5F, -0.5F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 22.5F, 3.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isInWater()) {
			this.animateWalk(BenthictAnimation.SWIM, f, f2, 1.5f, 2.0f);
		} else {
			this.animateWalk(BenthictAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		this.animate(((BenthictEntity)t).idleAnimationState, BenthictAnimation.IDLE, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
