package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RustleFoxAnimation;
import com.jerotes.jerotesvillage.entity.Animal.RustleFoxEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelrustle_fox<T extends RustleFoxEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rustle_fox"), "main");
	private final ModelPart root;
	public final ModelPart head;


	public Modelrustle_fox(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 5).addBox(-3.0F, -2.0F, -5.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(6, 18).addBox(-1.0F, 2.0F, -7.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 14.5F, -4.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -5.5F, 0.0F, -1.3963F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, -5.5F, 0.0F, 1.4835F, 0.0F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(8, 1).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.0F, -3.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition head_r4 = head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(15, 1).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, -3.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 41).addBox(-5.0F, -7.0F, -4.5F, 10.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(13, 24).addBox(-1.001F, -3.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.5F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(4, 24).mirror().addBox(-0.999F, -3.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 17.5F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(13, 24).addBox(-1.001F, -3.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.5F, -2.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(4, 24).mirror().addBox(-0.999F, -3.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 17.5F, -2.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 9.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.4f;
			this.head.yScale = 1.4f;
			this.head.zScale = 1.4f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isAggressive()) {
			this.animateWalk(RustleFoxAnimation.RUSH, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(RustleFoxAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, RustleFoxAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, RustleFoxAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, RustleFoxAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, RustleFoxAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, RustleFoxAnimation.STOPSIT, f3);
		this.animate(t.sleepAnimationState, RustleFoxAnimation.SLEEP, f3);
		this.animate(t.jumpAnimationState, RustleFoxAnimation.JUMP, f3);
		this.animate(t.wantAttackAnimationState, RustleFoxAnimation.WANTATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}

