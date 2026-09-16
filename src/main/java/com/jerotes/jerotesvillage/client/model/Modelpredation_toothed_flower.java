package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PredationToothedFlowerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.PredationToothedFlowerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Modelpredation_toothed_flower<T extends PredationToothedFlowerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "predation_toothed_flower"), "main");
	private final ModelPart root;
	private final ModelPart moss;
	private final ModelPart body;
	private final ModelPart head;

	public Modelpredation_toothed_flower(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.moss = root.getChild("moss");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition moss = partdefinition.addOrReplaceChild("moss", CubeListBuilder.create().texOffs(50, 38).addBox(-13.0F, 0.0F, -13.0F, 26.0F, 0.0F, 26.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.5F, -7.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition body_3 = body.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition body_4 = body.addOrReplaceChild("body_4", CubeListBuilder.create().texOffs(32, 16).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(-0.125F)), PartPose.offset(0.0F, -29.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition skin_head = head.addOrReplaceChild("skin_head", CubeListBuilder.create().texOffs(80, 14).addBox(-6.0F, -5.0F, -13.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(104, 0).addBox(0.0F, -7.0F, -6.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 0).addBox(0.0F, -7.0F, -9.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 0).addBox(0.0F, -7.0F, -12.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 0).addBox(0.0F, -7.0F, -3.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = skin_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(94, 7).addBox(-1.0F, -0.5F, -5.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -2.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition skin_head_bottom = skin_head.addOrReplaceChild("skin_head_bottom", CubeListBuilder.create().texOffs(80, 64).addBox(-6.0F, -0.1F, -9.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(-0.005F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition normal_head = head.addOrReplaceChild("normal_head", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition throat = normal_head.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(0, 40).addBox(-7.5F, -8.0F, -0.75F, 15.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition mouth = normal_head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(34, 39).addBox(-7.5F, -8.0F, -5.75F, 15.0F, 15.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leave_1 = normal_head.addOrReplaceChild("leave_1", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -7.0F, 1.25F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F))
				.texOffs(68, 29).addBox(0.0F, -7.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -5.0F));

		PartDefinition leave_1_up = leave_1.addOrReplaceChild("leave_1_up", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -8.5F, 1.25F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(68, 29).addBox(0.0F, -5.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 0.0F));

		PartDefinition leave_2 = normal_head.addOrReplaceChild("leave_2", CubeListBuilder.create().texOffs(68, 29).addBox(0.0F, 2.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -5.0F));

		PartDefinition leave_r1 = leave_2.addOrReplaceChild("leave_r1", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -7.5F, 1.25F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leave_2_up = leave_2.addOrReplaceChild("leave_2_up", CubeListBuilder.create().texOffs(68, 29).addBox(0.0F, 2.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.5F, 0.0F));

		PartDefinition leave_r2 = leave_2_up.addOrReplaceChild("leave_r2", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 1.25F, 0.0F, 0.0F, -3.1416F));

		PartDefinition leave_3 = normal_head.addOrReplaceChild("leave_3", CubeListBuilder.create().texOffs(68, 29).addBox(5.5F, -0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, -0.5F, -5.0F));

		PartDefinition leave_r3 = leave_3.addOrReplaceChild("leave_r3", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-7.5F, 0.0F, 1.25F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leave_3_up = leave_3.addOrReplaceChild("leave_3_up", CubeListBuilder.create().texOffs(68, 29).addBox(5.0F, -0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 0.0F, 0.0F));

		PartDefinition leave_r4 = leave_3_up.addOrReplaceChild("leave_r4", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, 0.0F, 1.25F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leave_4 = normal_head.addOrReplaceChild("leave_4", CubeListBuilder.create().texOffs(68, 29).addBox(-5.5F, -0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, -0.5F, -5.0F));

		PartDefinition leave_r5 = leave_4.addOrReplaceChild("leave_r5", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(7.5F, 0.0F, 1.25F, 0.0F, 0.0F, -1.5708F));

		PartDefinition leave_4_up = leave_4.addOrReplaceChild("leave_4_up", CubeListBuilder.create().texOffs(68, 29).addBox(-5.0F, -0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 0.0F, 0.0F));

		PartDefinition leave_r6 = leave_4_up.addOrReplaceChild("leave_r6", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 1.25F, 0.0F, 0.0F, -1.5708F));

		PartDefinition leave_5 = normal_head.addOrReplaceChild("leave_5", CubeListBuilder.create().texOffs(68, 29).addBox(4.0F, -4.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -6.5F, -5.0F));

		PartDefinition leave_r7 = leave_5.addOrReplaceChild("leave_r7", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-6.0F, 6.0F, 1.25F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leave_5_up = leave_5.addOrReplaceChild("leave_5_up", CubeListBuilder.create().texOffs(68, 29).addBox(3.5F, -4.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -4.5F, 0.0F));

		PartDefinition leave_r8 = leave_5_up.addOrReplaceChild("leave_r8", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, 10.5F, 1.25F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leave_6 = normal_head.addOrReplaceChild("leave_6", CubeListBuilder.create().texOffs(68, 29).addBox(-4.0F, -4.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -6.5F, -5.0F));

		PartDefinition leave_r9 = leave_6.addOrReplaceChild("leave_r9", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(6.0F, 6.0F, 1.25F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leave_6_up = leave_6.addOrReplaceChild("leave_6_up", CubeListBuilder.create().texOffs(68, 29).addBox(-3.5F, -4.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -4.5F, 0.0F));

		PartDefinition leave_r10 = leave_6_up.addOrReplaceChild("leave_r10", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, 10.5F, 1.25F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leave_7 = normal_head.addOrReplaceChild("leave_7", CubeListBuilder.create().texOffs(68, 29).addBox(-4.0F, 0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 5.5F, -5.0F));

		PartDefinition leave_r11 = leave_7.addOrReplaceChild("leave_r11", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(6.0F, -6.0F, 1.25F, 0.0F, 0.0F, -2.3562F));

		PartDefinition leave_7_up = leave_7.addOrReplaceChild("leave_7_up", CubeListBuilder.create().texOffs(68, 29).addBox(-3.5F, 4.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 4.5F, 0.0F));

		PartDefinition leave_r12 = leave_7_up.addOrReplaceChild("leave_r12", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -10.5F, 1.25F, 0.0F, 0.0F, -2.3562F));

		PartDefinition leave_8 = normal_head.addOrReplaceChild("leave_8", CubeListBuilder.create().texOffs(68, 29).addBox(4.0F, 0.5F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 5.5F, -5.0F));

		PartDefinition leave_r13 = leave_8.addOrReplaceChild("leave_r13", CubeListBuilder.create().texOffs(56, 32).addBox(-3.0F, -14.5F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-6.0F, -6.0F, 1.25F, 0.0F, 0.0F, 2.3562F));

		PartDefinition leave_8_up = leave_8.addOrReplaceChild("leave_8_up", CubeListBuilder.create().texOffs(68, 29).addBox(4.5F, 4.0F, -1.75F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 4.5F, 0.0F));

		PartDefinition leave_r14 = leave_8_up.addOrReplaceChild("leave_r14", CubeListBuilder.create().texOffs(56, 22).addBox(-3.0F, -23.5F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, -10.5F, 1.25F, 0.0F, 0.0F, 2.3562F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.idleAnimationState, PredationToothedFlowerAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, PredationToothedFlowerAnimation.ATTACK, f3);
		this.animate(t.shootAnimationState, PredationToothedFlowerAnimation.SHOOT, f3);
		this.animate(t.hideAnimationState, PredationToothedFlowerAnimation.HIDE, f3);
		this.animate(t.unHideAnimationState, PredationToothedFlowerAnimation.UNHIDE, f3);;
		this.animate(t.inHideAnimationState, PredationToothedFlowerAnimation.INHIDE, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
