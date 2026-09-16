package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.GlowSparrowAnimation;
import com.jerotes.jerotesvillage.entity.Animal.GlowSparrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Modelglow_sparrow<T extends GlowSparrowEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "glow_sparrow"), "main");
	private final ModelPart root;
	private final ModelPart head;

    public Modelglow_sparrow(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-1.5F, -2.25F, -2.25F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
				.texOffs(16, 7).addBox(-0.5F, -1.0F, -3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 17.0F, -2.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, -2.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(2, 8).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 3.6F, 3.95F));

		PartDefinition body_r2 = tail.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(26, 2).addBox(-1.5F, 3.0F, 1.5F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6F, -2.95F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, 16.9F, -1.55F));

		PartDefinition left_wing_rotation = left_wing.addOrReplaceChild("left_wing_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.5F, 0.75F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_wing_rotation_r1 = left_wing_rotation.addOrReplaceChild("left_wing_rotation_r1", CubeListBuilder.create().texOffs(19, 8).mirror().addBox(-0.5F, -2.5F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.9F, -1.55F));

		PartDefinition right_wing_rotation = right_wing.addOrReplaceChild("right_wing_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.5F, 0.75F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_wing_rotation_r1 = right_wing_rotation.addOrReplaceChild("right_wing_rotation_r1", CubeListBuilder.create().texOffs(19, 8).addBox(-0.5F, -2.5F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(14, 18).addBox(-2.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 22.0F, -1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(14, 18).addBox(1.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 22.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}


	@Override
	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.3f;
			this.head.yScale = 1.3f;
			this.head.zScale = 1.3f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (!t.isFlying()) {
			this.animateWalk(GlowSparrowAnimation.WALK, f, f2, 1.5f, 2.0f);
		} else {
			this.animateWalk(GlowSparrowAnimation.FLY, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.sitAnimationState, GlowSparrowAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, GlowSparrowAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, GlowSparrowAnimation.STOPSIT, f3);
		this.animate(t.danceAnimationState, GlowSparrowAnimation.DANCE, f3);
	}

	public void renderOnShoulder(PoseStack poseStack, VertexConsumer vertexConsumer, int n, int n2, float f, float f2, float f3, float f4, int n3) {
		this.head.yRot = f3 * ((float)Math.PI / 180F);
		this.head.xRot = f4 * ((float)Math.PI / 180F);
		this.root.render(poseStack, vertexConsumer, n, n2);
	}

	@OnlyIn(Dist.CLIENT)
	public static enum State {
		FLYING,
		STANDING,
		SITTING,
		PARTY,
		ON_SHOULDER;
	}
}

