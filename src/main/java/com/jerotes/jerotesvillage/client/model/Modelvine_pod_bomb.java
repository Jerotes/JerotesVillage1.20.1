package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.VinePodBombAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelvine_pod_bomb<T extends VinePodBombEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "vine_pod_bomb"), "main");
	private final ModelPart root;
	private final ModelPart body;

	public Modelvine_pod_bomb(ModelPart modelPart) {
		this.root = modelPart;
		this.body = modelPart.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(VinePodBombAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(t.idleAnimationState, VinePodBombAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, VinePodBombAnimation.ATTACK, f3);
		if (t.isInSittingPose()) {
			this.body.y = 17.5f;
		}
	}

}