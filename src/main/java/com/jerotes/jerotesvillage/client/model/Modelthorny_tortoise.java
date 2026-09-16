package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ThornyTortoiseAnimation;
import com.jerotes.jerotesvillage.entity.Animal.ThornyTortoiseEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeMod;


public class Modelthorny_tortoise<T extends ThornyTortoiseEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "thorny_tortoise"), "main");
	private final ModelPart root;
	private final ModelPart neck;
	private final ModelPart head;
	public Modelthorny_tortoise(ModelPart modelPart) {
		super(0.25f, 72.0f);
		this.root = modelPart;
		this.neck = root.getChild("neck");
		this.head = this.neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -9.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -3.5F, -3.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.829F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, -6.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.25F, -1.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -0.25F, -4.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.75F, -2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(24, 0).addBox(-9.5F, -10.0F, -2.0F, 19.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(74, 21).addBox(-7.5F, -8.0F, 4.0F, 15.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 0).addBox(-7.5F, -10.0F, -4.0F, 15.0F, 19.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 26).addBox(-4.5F, -6.0F, 6.0F, 9.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(108, 14).addBox(-5.0F, -10.0F, 4.0F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(108, 14).addBox(5.0F, -10.0F, 4.0F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(108, 14).addBox(0.0F, -10.0F, 4.0F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(-6.5F, -7.0F, 3.0F, 13.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(38, 113).addBox(4.5F, -7.0F, 9.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 113).addBox(-6.5F, -7.0F, 9.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 125).addBox(-4.5F, 5.0F, 9.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 122).addBox(-4.5F, -7.0F, 9.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition saddle_r1 = body_rotation.addOrReplaceChild("saddle_r1", CubeListBuilder.create().texOffs(65, 119).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(61, 119).addBox(-8.0F, 0.0F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -9.8042F, 6.1308F, -0.6545F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 19).addBox(0.0F, -4.0F, -1.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 10.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body2_rotation = body2.addOrReplaceChild("body2_rotation", CubeListBuilder.create().texOffs(108, 0).addBox(-4.5F, -9.0F, -5.0F, 9.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(38, 26).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, 18.0F, 11.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(38, 26).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.5F, 18.0F, 11.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(37, 37).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 18.0F, -7.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(37, 37).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 18.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
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
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (t.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
			this.animateWalk(ThornyTortoiseAnimation.SWIM, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(ThornyTortoiseAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.hideAnimationState, ThornyTortoiseAnimation.HIDE, f3);
		this.animate(t.unHideAnimationState, ThornyTortoiseAnimation.UNHIDE, f3);
		this.animate(t.inHideAnimationState, ThornyTortoiseAnimation.INHIDE, f3);
		this.animate(t.attackAnimationState, ThornyTortoiseAnimation.ATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}