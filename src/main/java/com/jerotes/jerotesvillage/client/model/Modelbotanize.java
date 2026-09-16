package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BotanizeAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class Modelbotanize<T extends BotanizeEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "botanize"), "main");
	private final ModelPart root;
	private final ModelPart head;


	public Modelbotanize(ModelPart modelPart) {
		this.root = modelPart;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.5F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rod_1 = body.addOrReplaceChild("rod_1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.0F, 1.5F));

		PartDefinition rod_2 = body.addOrReplaceChild("rod_2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.0F, -1.5F));

		PartDefinition rod_3 = body.addOrReplaceChild("rod_3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -10.0F, 1.5F));

		PartDefinition rod_4 = body.addOrReplaceChild("rod_4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -10.0F, -1.5F));

		PartDefinition rod_5 = body.addOrReplaceChild("rod_5", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition head_body = body.addOrReplaceChild("head_body", CubeListBuilder.create().texOffs(16, 16).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.getGiveTick() <= 0) {
			this.head.yRot = f4 * ((float) Math.PI / 180F);
			this.head.xRot = f5 * ((float) Math.PI / 180F);
		}
		this.animateWalk(BotanizeAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(t.idleAnimationState, BotanizeAnimation.IDLE, f3);
		this.animate(t.giveAnimationState, BotanizeAnimation.GIVE, f3);
		this.animate(t.tiltAnimationState, BotanizeAnimation.TILT, f3);
		this.animate(t.growAnimationState, BotanizeAnimation.GROW, f3);
		this.animate(t.shootAnimationState, BotanizeAnimation.SHOOT, f3);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		return this.head;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
	}
}

