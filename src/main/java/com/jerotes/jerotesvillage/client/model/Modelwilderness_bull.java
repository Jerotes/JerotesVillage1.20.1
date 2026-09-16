package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.WildernessBullAnimation;
import com.jerotes.jerotesvillage.entity.Animal.WildernessBullEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelwilderness_bull<T extends WildernessBullEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wilderness_bull"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelwilderness_bull(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();


		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 0).addBox(-6.0F, -5.975F, -4.525F, 12.0F, 12.0F, 9.0F, new CubeDeformation(0.05F))
				.texOffs(12, 24).addBox(5.5F, -7.5F, -6.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(10.5F, -13.5F, -6.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-14.0F, -7.5F, -6.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-14.0F, -13.5F, -6.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -18.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(54, 21).addBox(-3.0F, -6.0F, -4.5F, 6.0F, 12.0F, 9.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 3.5755F, -0.726F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-3.25F, -2.5F, -0.75F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 33).addBox(-19.25F, -2.5F, -0.75F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.25F, -1.8679F, -4.9553F, -0.1745F, 0.0F, 0.0F));

		PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(85, 52).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.4002F, -7.2772F, -0.5236F, 0.0F, 0.0F));

		PartDefinition head_r4 = head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -7.5F, 12.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(3, 60).addBox(-9.0F, -20.95F, -10.7F, 18.0F, 18.0F, 18.0F, new CubeDeformation(0.1F))
				.texOffs(3, 60).addBox(-9.0F, -21.1F, -7.55F, 18.0F, 18.0F, 18.0F, new CubeDeformation(-0.2F))
				.texOffs(120, 48).addBox(-7.5F, -10.5F, -13.5F, 15.0F, 27.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_armor_r1 = body.addOrReplaceChild("saddle_armor_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-8.5F, -3.0F, -8.5F, 17.0F, 6.0F, 17.0F, new CubeDeformation(-1.1F)), PartPose.offsetAndRotation(0.0F, 0.1674F, 8.3332F, -1.7017F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.125F, 16.2041F, 5.4693F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(144, 0).addBox(-1.625F, -0.875F, -10.375F, 3.0F, 3.0F, 21.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.125F, 2.4554F, -8.3312F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(114, 0).addBox(-3.0F, -3.45F, -4.55F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(-6.0F, -4.5F, 15.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(115, 22).addBox(-3.0F, -1.55F, -1.075F, 6.0F, 18.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 12.0F, -0.75F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(114, 0).mirror().addBox(-3.0F, -3.45F, -4.55F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(6.0F, -4.5F, 15.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(115, 22).mirror().addBox(-3.0F, -1.55F, -1.075F, 6.0F, 18.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 12.0F, -0.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(84, 0).addBox(-1.4F, -1.4F, -4.6F, 6.0F, 15.0F, 9.0F, new CubeDeformation(0.2F)), PartPose.offset(-9.0F, -9.0F, -10.5F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(84, 24).addBox(-3.0F, -1.25F, -3.75F, 6.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 14.25F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-4.5F, -1.4F, -4.6F, 6.0F, 15.0F, 9.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(9.0F, -9.0F, -10.5F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(84, 24).mirror().addBox(-3.0F, -1.25F, -3.75F, 6.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 14.25F, 0.0F));

		return LayerDefinition.create(meshdefinition, 192, 96);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.1f;
			this.head.yScale = 1.1f;
			this.head.zScale = 1.1f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = (float) (f5 * ((float) Math.PI / 180F) + t.getRushAttackDamageTick() * 0.003);
		if (t.isHorn() && t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(WildernessBullAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isHorn() && t.isAggressive()){
			this.animateWalk(WildernessBullAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(WildernessBullAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.attackAnimationState, WildernessBullAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, WildernessBullAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, WildernessBullAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, WildernessBullAnimation.STOPSIT, f3);
		this.animate(t.hoeAnimationState, WildernessBullAnimation.HOE, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}