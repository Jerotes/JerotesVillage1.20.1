package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RedstoneMinistrosityAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMinistrosityEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelredstone_ministrosity<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "redstone_ministrosity"), "main");
	private final ModelPart root;
	public Modelredstone_ministrosity(ModelPart modelPart) {
		this.root = modelPart;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(43, 25).addBox(-3.5F, 3.0F, -3.5F, 7.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -5.5F, -6.5F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -3.5F, -6.6F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(1, 0).addBox(-3.5F, -1.5F, -6.6F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(1, 0).addBox(2.5F, -1.5F, -6.6F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 3.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 12).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(54, 21).addBox(-2.0F, 3.0F, -2.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 4.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 12).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(54, 21).addBox(-1.0F, 3.0F, -2.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 4.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(42, 0).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}


	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof MerorMinistrosityEntity merorMinistrosityEntity) {
			this.animateWalk(RedstoneMinistrosityAnimation.WALK, f, f2, 1.5f, 2.0f);
			this.animate(merorMinistrosityEntity.idleAnimationState, RedstoneMinistrosityAnimation.IDLE, f3);
			this.animate(merorMinistrosityEntity.sitAnimationState, RedstoneMinistrosityAnimation.SIT, f3);
			this.animate(merorMinistrosityEntity.toSitAnimationState, RedstoneMinistrosityAnimation.TOSIT, f3);
			this.animate(merorMinistrosityEntity.stopSitAnimationState, RedstoneMinistrosityAnimation.STOPSIT, f3);
			this.animate(merorMinistrosityEntity.pose1AnimationState, RedstoneMinistrosityAnimation.POSE1, f3);
			this.animate(merorMinistrosityEntity.pose2AnimationState, RedstoneMinistrosityAnimation.POSE2, f3);
			this.animate(merorMinistrosityEntity.pose3AnimationState, RedstoneMinistrosityAnimation.POSE3, f3);
		}
	}
}