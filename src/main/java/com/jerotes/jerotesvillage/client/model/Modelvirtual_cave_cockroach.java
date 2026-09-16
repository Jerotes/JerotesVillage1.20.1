package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.VirtualCaveCockroachAnimation;
import com.jerotes.jerotesvillage.entity.Animal.VirtualCaveCockroachEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelvirtual_cave_cockroach<T extends VirtualCaveCockroachEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "virtual_cave_cockroach"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_elytra;
	private final ModelPart right_elytra;

	public Modelvirtual_cave_cockroach(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_elytra = body.getChild("left_elytra");
		this.right_elytra = body.getChild("right_elytra");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 109).addBox(-3.05F, -1.0F, -3.75F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 109).addBox(3.05F, -1.0F, -3.75F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -11.0F));

		PartDefinition tentacle = head.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(-24, 62).addBox(-8.0F, 0.0F, -23.25F, 16.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -3.5F));

		PartDefinition teeth = head.addOrReplaceChild("teeth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, -3.75F, 0.1309F, 0.0F, 0.0F));

		PartDefinition teeth_r1 = teeth.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.3F, -0.1745F, 0.0F, 0.0F));

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -2.8F, -6.5F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.3F))
				.texOffs(24, 10).addBox(-4.0F, -3.0F, -6.5F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -4.25F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 4).addBox(-4.0F, -3.0F, 0.5F, 8.0F, 6.0F, 20.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 16.5F, -4.5F));

		PartDefinition left_elytra = body.addOrReplaceChild("left_elytra", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-5.5F, 0.0F, -0.5F, 8.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -4.0F, 0.8F));

		PartDefinition right_elytra = body.addOrReplaceChild("right_elytra", CubeListBuilder.create().texOffs(0, 30).addBox(-3.5F, 0.0F, -0.5F, 8.0F, 8.0F, 24.0F, new CubeDeformation(-0.1F)), PartPose.offset(-2.0F, -4.0F, 0.8F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 18.6F, 0.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(0, 102).addBox(-6.0F, -4.5F, 0.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7428F, 2.5F, 4.6599F, 0.8727F, 0.4363F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 18.6F, 0.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(0, 102).mirror().addBox(-6.0F, -4.5F, 0.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.7428F, 2.5F, 4.6599F, 0.8727F, -0.4363F, 0.0F));

		PartDefinition right_middle_leg = partdefinition.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 17.7F, -5.0F));

		PartDefinition right_middle_leg_r1 = right_middle_leg.addOrReplaceChild("right_middle_leg_r1", CubeListBuilder.create().texOffs(0, 94).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 2.25F, 0.6127F, 0.0715F, 0.0501F));

		PartDefinition left_middle_leg = partdefinition.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 17.7F, -5.0F));

		PartDefinition left_middle_leg_r1 = left_middle_leg.addOrReplaceChild("left_middle_leg_r1", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 3.0F, 2.25F, 0.6127F, -0.0715F, -0.0501F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 17.3F, -9.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(0, 86).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1156F, 3.0F, 0.0009F, 0.2182F, -0.3054F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 17.3F, -9.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(0, 86).mirror().addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.1156F, 3.0F, 0.0009F, 0.2182F, 0.3054F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.2f;
			this.head.yScale = 1.2f;
			this.head.zScale = 1.2f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		if (t.isBaby()) {
			this.left_elytra.visible = false;
			this.right_elytra.visible = false;
		}
		else {
			this.left_elytra.visible = true;
			this.right_elytra.visible = true;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.getSizeLike() > 3) {
			this.animateWalk(VirtualCaveCockroachAnimation.WALKGIANT, f, f2, 2.0f, 2.0f);
		}
		else if (t.getSizeLike() > 1) {
			if (!(t.isFlying() || t.isNoGravity() && !t.onGround() && !t.isInSittingPose())) {
				this.animateWalk(VirtualCaveCockroachAnimation.WALKLARGE, f, f2, 2.0f, 2.0f);
			}
		}
		else {
			if (!(t.isFlying() || t.isNoGravity() && !t.onGround() && !t.isInSittingPose())) {
				this.animateWalk(VirtualCaveCockroachAnimation.WALK, f, f2, 2.0f, 2.0f);
			}
		}
		if (t.getSizeLike() > 3) {
			this.animate(t.idleAnimationState, VirtualCaveCockroachAnimation.IDLEGIANT, f3);
		}
		else if (t.getSizeLike() > 1) {
			if (t.isFlying() || t.isNoGravity() && !t.onGround() && !t.isInSittingPose()) {
				this.animate(t.idleAnimationState, VirtualCaveCockroachAnimation.FLYLARGE, f3);
			}
			else {
				this.animate(t.idleAnimationState, VirtualCaveCockroachAnimation.IDLELARGE, f3);
			}
		}
		else {
			if (t.isFlying() || t.isNoGravity() && !t.onGround() && !t.isInSittingPose()) {
				this.animate(t.idleAnimationState, VirtualCaveCockroachAnimation.FLY, f3);
			}
			else {
				this.animate(t.idleAnimationState, VirtualCaveCockroachAnimation.IDLE, f3);
			}
		}
		this.animate(t.attackAnimationState, VirtualCaveCockroachAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, VirtualCaveCockroachAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, VirtualCaveCockroachAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, VirtualCaveCockroachAnimation.STOPSIT, f3);
		this.animate(t.climbAnimationState, VirtualCaveCockroachAnimation.CLIMBING, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
