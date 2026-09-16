package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RedstoneCubeAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorCubeEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelredstone_cube<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "redstone_cube"), "main");
	private final ModelPart root;
	private final ModelPart body;

	public Modelredstone_cube(ModelPart modelPart) {
		this.root = modelPart;
		this.body = modelPart.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(RedstoneCubeAnimation.WALK, f, f2, 1.5f, 2.0f);
		if (t instanceof MerorCubeEntity merorCube) {
			if (merorCube.isInSittingPose()) {
				this.body.y = 17.5f;
			}
			this.animate(merorCube.attackAnimationState, RedstoneCubeAnimation.ATTACK, f3);
			this.animate(merorCube.pose1AnimationState, RedstoneCubeAnimation.POSE1, f3);
			this.animate(merorCube.pose2AnimationState, RedstoneCubeAnimation.POSE2, f3);
			this.animate(merorCube.pose3AnimationState, RedstoneCubeAnimation.POSE3, f3);
		}
	}

}