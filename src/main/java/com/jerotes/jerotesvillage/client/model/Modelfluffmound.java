package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FluffmoundAnimation;
import com.jerotes.jerotesvillage.entity.Animal.FluffmoundEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelfluffmound<T extends FluffmoundEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "fluffmound"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelfluffmound(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.45F, -21.6F));

		PartDefinition main_head = head.addOrReplaceChild("main_head", CubeListBuilder.create().texOffs(92, 83).addBox(-10.5F, -10.5F, -4.5F, 21.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.2F, -9.9F));

		PartDefinition head_r1 = main_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 127).addBox(-0.75F, -1.0F, 9.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 127).addBox(-0.75F, -1.0F, -10.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 127).addBox(-4.75F, -1.0F, 10.5F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 127).addBox(-4.75F, -1.0F, -11.5F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, -5.0F, -1.5708F, -0.9163F, 1.5708F));

		PartDefinition right_eyelid = main_head.addOrReplaceChild("right_eyelid", CubeListBuilder.create().texOffs(152, 94).addBox(-5.0F, -1.5F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, -5.0F, -5.5F));

		PartDefinition left_eyelid = main_head.addOrReplaceChild("left_eyelid", CubeListBuilder.create().texOffs(152, 94).mirror().addBox(-5.0F, -1.5F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, -5.0F, -5.5F));

		PartDefinition left_ear = main_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(7.5F, -10.5F, -0.5F));

		PartDefinition left_ear_r1 = left_ear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(152, 83).mirror().addBox(-4.5F, -3.0F, -2.5F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition right_ear = main_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-7.5F, -10.5F, -0.5F));

		PartDefinition right_ear_r1 = right_ear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(152, 83).addBox(-4.5F, -3.0F, -2.5F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition mouth = main_head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(48, 127).addBox(-6.0F, -4.5F, -2.5F, 12.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 127).addBox(-6.0F, -4.5F, -2.5F, 12.0F, 9.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 4.0F, -6.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(48, 141).addBox(-6.0F, -4.5F, -2.5F, 12.0F, 9.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -3.0F, 4.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 83).addBox(-15.5F, -14.5F, -4.5F, 31.0F, 29.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.2F, -2.9F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -17.5F, -24.0F, 40.0F, 35.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(0, 237).addBox(-9.0F, -19.5F, 3.0F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(0, 237).addBox(-9.0F, -19.5F, -22.0F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 2.5F));

		PartDefinition right_chest_r1 = body.addOrReplaceChild("right_chest_r1", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, 6.75F, -9.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_chest_r2 = body.addOrReplaceChild("right_chest_r2", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, 6.75F, 3.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_chest_r3 = body.addOrReplaceChild("right_chest_r3", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, 6.75F, 15.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition left_chest_r1 = body.addOrReplaceChild("left_chest_r1", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, 6.75F, -9.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_chest_r2 = body.addOrReplaceChild("left_chest_r2", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, 6.75F, 15.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_chest_r3 = body.addOrReplaceChild("left_chest_r3", CubeListBuilder.create().texOffs(68, 245).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, 6.75F, 3.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.75F, 22.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(96, 143).addBox(-6.0F, -4.5F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(140, 113).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0015F, 6.0F, 14.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(140, 113).mirror().addBox(-6.0F, 0.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0015F, 6.0F, 14.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(92, 113).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0015F, 6.0F, -13.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(92, 113).mirror().addBox(-6.0F, 0.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0015F, 6.0F, -13.5F));

		return LayerDefinition.create(meshdefinition, 256, 256);
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
		this.applyHeadRotation(t, f4, f5, f3);
		this.animateWalk(FluffmoundAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, FluffmoundAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, FluffmoundAnimation.ATTACK, f3);
		this.animate(t.sitAnimationState, FluffmoundAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, FluffmoundAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, FluffmoundAnimation.STOPSIT, f3);
		this.animate(t.hurtAnimationState, FluffmoundAnimation.HURT, f3);
		this.animate(t.hairlessAnimationState, FluffmoundAnimation.HAIRLESS, f3);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 45.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
