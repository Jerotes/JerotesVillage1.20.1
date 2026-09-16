package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.CloudFurDeerAnimation;
import com.jerotes.jerotesvillage.entity.Animal.CloudFurDeerEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelcloud_fur_deer<T extends CloudFurDeerEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "cloud_fur_deer"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelcloud_fur_deer(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(34, 56).addBox(-2.5F, -14.5F, -9.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(96, 4).addBox(3.0F, -30.5F, -3.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(96, 4).mirror().addBox(-10.0F, -30.5F, -3.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(57, 45).addBox(-4.5F, -15.5F, -6.5F, 9.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(17, 0).addBox(4.0F, -17.0F, -4.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 0).mirror().addBox(-7.0F, -17.0F, -4.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, -6.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(29, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(82, 21).addBox(-6.5F, -4.0F, -5.0F, 13.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(122, 0).addBox(-1.5F, 0.0076F, 0.1743F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 1.0F, -1.4835F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(29, 29).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 10.0F, 6.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 10.0F, 6.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(29, 29).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 10.0F, -5.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 10.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
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
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(CloudFurDeerAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()){
			this.animateWalk(CloudFurDeerAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(CloudFurDeerAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.attackAnimationState, CloudFurDeerAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, CloudFurDeerAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, CloudFurDeerAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, CloudFurDeerAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}