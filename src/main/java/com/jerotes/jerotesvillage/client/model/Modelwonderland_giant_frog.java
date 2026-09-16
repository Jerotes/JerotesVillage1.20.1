package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.WonderlandGiantFrogAnimation;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogEntity;
import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelwonderland_giant_frog<T extends WonderlandGiantFrogEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wonderland_giant_frog"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart front_body;
	private final ModelPart head;

	public Modelwonderland_giant_frog(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.front_body = body.getChild("front_body");
		this.head = front_body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(6, 2).addBox(-9.0F, -3.225F, -3.0F, 18.0F, 6.0F, 18.0F, new CubeDeformation(1.0F))
				.texOffs(0, 26).addBox(-9.0F, -8.125F, -3.5F, 18.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.125F, -3.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(28, 60).addBox(-8.0F, 2.5F, -8.75F, 16.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.375F, -0.25F));

		PartDefinition croaking_body = front_body.addOrReplaceChild("croaking_body", CubeListBuilder.create().texOffs(56, 86).addBox(-7.0F, -2.0F, -3.0F, 14.0F, 4.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 6.15F, -3.65F));

		PartDefinition head = front_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 49).addBox(-8.0F, -2.25F, -8.75F, 16.0F, 5.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(2.0F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -3.25F, -3.25F));

		PartDefinition tongue = body.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(66, 0).addBox(-4.0F, 0.0F, -13.0F, 8.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.225F, 9.9F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-1.25F, -0.505F, -2.75F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(-8, 88).mirror().addBox(-5.25F, 5.395F, -4.75F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.75F, -1.62F, -5.75F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 64).addBox(-3.75F, -0.505F, -2.75F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(-8, 88).addBox(-2.75F, 5.395F, -4.75F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.75F, -1.62F, -5.75F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-4.0F, -0.505F, -2.5F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(-8, 80).mirror().addBox(-3.0F, 5.395F, -5.5F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(11.0F, 18.505F, 9.5F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, -0.505F, -2.5F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(-8, 80).addBox(-5.0F, 5.395F, -5.5F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, 18.505F, 9.5F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isInWaterOrBubble()) {
			this.animateWalk(FrogAnimation.FROG_SWIM, f, f2, 1.0f, 2.5f);
		} else {
			if (t.getDeltaMovement().x() != 0 && t.getDeltaMovement().z() != 0 || !t.isJumping()) {
				this.animateWalk(WonderlandGiantFrogAnimation.WALK, f, f2, 1.5f, 2.5f);
			}
		}
		this.animate(t.swimIdleAnimationState, FrogAnimation.FROG_IDLE_WATER, f3);
		this.animate(t.jumpAnimationState, WonderlandGiantFrogAnimation.JUMP, f3);
		this.animate(t.croakAnimationState, WonderlandGiantFrogAnimation.CROAK, f3);
		this.animate(t.tongueAnimationState, FrogAnimation.FROG_TONGUE, f3);
		if (t.isFakeBoss()) {
			this.animate(t.attackAnimationState, WonderlandGiantFrogAnimation.BOSSATTACK, f3);
		}
		else {
			this.animate(t.attackAnimationState, WonderlandGiantFrogAnimation.ATTACK, f3);
		}
		this.animate(t.eatAnimationState, WonderlandGiantFrogAnimation.EAT, f3);
		this.animate(t.sitAnimationState, WonderlandGiantFrogAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, WonderlandGiantFrogAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, WonderlandGiantFrogAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}