package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SerponCrossbowTurretAnimation;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

public class Modelserpon_crossbow_turret<T extends SerponCrossbowTurretEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "serpon_crossbow_turret"), "main");
	private final ModelPart root;
	private final ModelPart base;
	private final ModelPart add;
	private final ModelPart crossbow;
	private final ModelPart shoot;
	private final ModelPart arrow;
	private final ModelPart winch;
	private final ModelPart winch_3;
	private final ModelPart winch_2;
	private final ModelPart winch_1;
	private final ModelPart left_1;
	private final ModelPart left_2;
	private final ModelPart left_3;
	private final ModelPart right_1;
	private final ModelPart right_2;
	private final ModelPart right_3;


	public Modelserpon_crossbow_turret(ModelPart root) {
		this.root = root;
		this.base = root.getChild("base");
		this.add = root.getChild("add");
		this.crossbow = this.add.getChild("crossbow");
		this.shoot = this.crossbow.getChild("shoot");
		this.arrow = this.shoot.getChild("arrow");
		this.winch = this.crossbow.getChild("winch");
		this.winch_3 = this.winch.getChild("winch_3");
		this.winch_2 = this.winch.getChild("winch_2");
		this.winch_1 = this.winch.getChild("winch_1");
		this.left_1 = this.crossbow.getChild("left_1");
		this.left_2 = this.left_1.getChild("left_2");
		this.left_3 = this.left_2.getChild("left_3");
		this.right_1 = this.crossbow.getChild("right_1");
		this.right_2 = this.right_1.getChild("right_2");
		this.right_3 = this.right_2.getChild("right_3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 1.6111F, -16.0F, 32.0F, 2.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(34, 34).addBox(-4.0F, -0.3889F, -15.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.3889F, 0.0F));

		PartDefinition base_r1 = base.addOrReplaceChild("base_r1", CubeListBuilder.create().texOffs(0, 96).addBox(-3.0F, -1.5F, -4.5F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, 0.1111F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition base_r2 = base.addOrReplaceChild("base_r2", CubeListBuilder.create().texOffs(0, 96).addBox(-3.0F, -1.5F, -4.5F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 0.1111F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition base_r3 = base.addOrReplaceChild("base_r3", CubeListBuilder.create().texOffs(52, 34).addBox(-3.0F, -10.0F, 6.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6111F, 9.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition base_r4 = base.addOrReplaceChild("base_r4", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -1.5F, -11.0F, 6.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1111F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition base_r5 = base.addOrReplaceChild("base_r5", CubeListBuilder.create().texOffs(34, 34).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6111F, 13.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition base_r6 = base.addOrReplaceChild("base_r6", CubeListBuilder.create().texOffs(34, 34).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 0.6111F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition base_r7 = base.addOrReplaceChild("base_r7", CubeListBuilder.create().texOffs(34, 34).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.6111F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition add = partdefinition.addOrReplaceChild("add", CubeListBuilder.create().texOffs(30, 96).addBox(-2.0F, -5.875F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(-2.5F, -6.875F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.875F, 0.0F));

		PartDefinition add_r1 = add.addOrReplaceChild("add_r1", CubeListBuilder.create().texOffs(0, 108).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -0.375F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition crossbow = add.addOrReplaceChild("crossbow", CubeListBuilder.create().texOffs(84, 232).addBox(-3.0F, -1.5F, -6.5F, 6.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 59).addBox(-4.0F, -6.5F, -16.0F, 8.0F, 5.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(32, 232).addBox(-4.0F, -7.5F, -16.0F, 3.0F, 1.0F, 23.0F, new CubeDeformation(0.0F))
				.texOffs(24, 250).addBox(-4.0F, -8.5F, -19.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 247).addBox(2.0F, -8.5F, -19.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(164, 253).addBox(-2.0F, -9.5F, -19.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 253).addBox(-2.0F, -8.5F, 5.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(32, 232).mirror().addBox(1.0F, -7.5F, -16.0F, 3.0F, 1.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(84, 248).addBox(-8.0F, -6.5F, -19.0F, 16.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(176, 252).addBox(-8.0F, -7.5F, -19.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(176, 252).mirror().addBox(1.0F, -7.5F, -19.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -7.375F, 0.0F));

		PartDefinition shoot = crossbow.addOrReplaceChild("shoot", CubeListBuilder.create().texOffs(24, 244).addBox(-1.0F, -0.25F, 0.3333F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 238).addBox(-1.0F, -1.25F, -1.6667F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 235).addBox(-2.0F, -1.0F, -1.1667F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.25F, -9.8333F));

		PartDefinition arrow = shoot.addOrReplaceChild("arrow", CubeListBuilder.create().texOffs(122, 235).addBox(-0.5F, -0.5F, -8.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, 1.3333F));

		PartDefinition arrow_r1 = arrow.addOrReplaceChild("arrow_r1", CubeListBuilder.create().texOffs(24, 253).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition winch = crossbow.addOrReplaceChild("winch", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 8.5F));

		PartDefinition winch_3 = winch.addOrReplaceChild("winch_3", CubeListBuilder.create(), PartPose.offset(-4.5F, 0.0F, 0.0F));

		PartDefinition winch_r1 = winch_3.addOrReplaceChild("winch_r1", CubeListBuilder.create().texOffs(0, 236).mirror().addBox(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(0, 246).mirror().addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 236).addBox(-9.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.1F))
				.texOffs(61, 245).addBox(-8.5F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 246).addBox(-9.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition winch_r2 = winch_3.addOrReplaceChild("winch_r2", CubeListBuilder.create().texOffs(0, 236).mirror().addBox(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.15F)).mirror(false)
				.texOffs(0, 236).addBox(-9.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition winch_2 = winch.addOrReplaceChild("winch_2", CubeListBuilder.create().texOffs(24, 215).addBox(-0.5F, -0.5F, -16.0F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition winch_1 = winch.addOrReplaceChild("winch_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition winch_1_r1 = winch_1.addOrReplaceChild("winch_1_r1", CubeListBuilder.create().texOffs(24, 242).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition right_1 = crossbow.addOrReplaceChild("right_1", CubeListBuilder.create().texOffs(196, 252).addBox(-7.0F, -0.5F, -1.5F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(61, 247).addBox(-1.0F, -1.5F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -7.0F, -17.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_2 = right_1.addOrReplaceChild("right_2", CubeListBuilder.create().texOffs(214, 252).addBox(-8.0F, -0.5F, -1.5F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(61, 247).addBox(-1.0F, -1.5F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition right_3 = right_2.addOrReplaceChild("right_3", CubeListBuilder.create().texOffs(0, 212).addBox(-0.5F, -0.75F, -0.75F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(61, 247).addBox(-1.0F, -1.5F, -2.75F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.25F, 0.0F, 1.1781F, 0.0F));

		PartDefinition left_1 = crossbow.addOrReplaceChild("left_1", CubeListBuilder.create().texOffs(196, 252).mirror().addBox(1.0F, -0.5F, -1.5F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(61, 247).mirror().addBox(-1.0F, -1.5F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, -7.0F, -17.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_2 = left_1.addOrReplaceChild("left_2", CubeListBuilder.create().texOffs(214, 252).mirror().addBox(1.0F, -0.5F, -1.5F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(61, 247).mirror().addBox(-1.0F, -1.5F, -2.5F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition left_3 = left_2.addOrReplaceChild("left_3", CubeListBuilder.create().texOffs(0, 212).mirror().addBox(-0.5F, -0.75F, -0.75F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(61, 247).mirror().addBox(-1.0F, -1.5F, -2.75F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 0.25F, 0.0F, -1.1781F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.add.yRot = f4 * ((float) Math.PI / 180F);
		this.crossbow.xRot = f5 * ((float) Math.PI / 180F);
		this.animate(t.fireAnimationState, SerponCrossbowTurretAnimation.FIRE, f3);
		animateIdle2(t,t.chargeAnimationState, SerponCrossbowTurretAnimation.CHARGE, f3);
	}


	protected void animateIdle2(SerponCrossbowTurretEntity serponCrossbowTurret, AnimationState p_233382_, AnimationDefinition p_233383_, float p_233384_) {
		this.animateIdle2(serponCrossbowTurret, p_233382_, p_233383_, p_233384_, 1.0F);
	}
	protected void animateIdle2(SerponCrossbowTurretEntity serponCrossbowTurret, AnimationState animationState, AnimationDefinition animation, float ageInTicks, float speed) {

		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((state) -> {
				KeyframeAnimations.animate(this, animation,
						(long) (Math.min(1, Math.min(serponCrossbowTurret.attackAnimProgress, 30) / 30f) * animation.lengthInSeconds() * 999),
						1.0f,
						new Vector3f());
		});
	}
}

