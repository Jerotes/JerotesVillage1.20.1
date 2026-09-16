package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FlowingDustAnimation;
import com.jerotes.jerotesvillage.entity.Monster.FlowingDustEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Modelflowing_dust<T extends FlowingDustEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "flowing_dust"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart eye;
	private final ModelPart up;
	private final ModelPart down;

	public Modelflowing_dust(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.body = root.getChild("body");
		this.eye = body.getChild("eye");
		this.up = body.getChild("up");
		this.down = body.getChild("down");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition eye = body.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -5.25F));

		PartDefinition up = body.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition down = body.addOrReplaceChild("down", CubeListBuilder.create().texOffs(0, 33).addBox(-6.0F, -1.5F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 3.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 48, 48);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.eye.yRot = netHeadYaw * 0.0040f;
		this.eye.xRot = headPitch * 0.0008f;
		this.animateWalk(FlowingDustAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
		this.animate(((FlowingDustEntity) entity).climbAnimationState, FlowingDustAnimation.CLIMB, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}