package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.VineheartBombAnimation;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.VineheartBombEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelvineheart_bomb<T extends VineheartBombEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "vineheart_bomb"), "main");
	private final ModelPart root;
	public Modelvineheart_bomb(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(24, 63).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 37).addBox(-4.0F, 7.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-16.0F, 4.5F, -16.0F, 32.0F, 1.0F, 32.0F, new CubeDeformation(-2.25F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition body_r1 = bottom.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(8, 87).mirror().addBox(-5.0F, -18.0F, -5.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 87).addBox(-5.0F, -18.0F, -5.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 87).addBox(3.0F, -18.0F, -5.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 87).addBox(3.0F, -18.0F, -5.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(8, 87).mirror().addBox(3.0F, -18.0F, 3.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 87).addBox(3.0F, -18.0F, 3.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 87).addBox(-5.0F, -18.0F, 3.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(0, 87).addBox(-5.0F, -18.0F, 3.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(-6.0F, -20.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 51).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition body_r2 = top.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(16, 79).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(16, 69).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 79).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.9076F, -0.5411F, 0.576F));

		PartDefinition bomb = top.addOrReplaceChild("bomb", CubeListBuilder.create().texOffs(0, 63).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition body_r3 = bomb.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(40, 39).addBox(0.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition body_r4 = bomb.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(40, 51).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1005F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(t.startAnimationState, VineheartBombAnimation.START, f3);
	}
}