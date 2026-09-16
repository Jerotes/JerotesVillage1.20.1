package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.animation.SpearAnimations;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import com.jerotes.jerotes.util.Ease;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.SpearMachineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class Modelspear_machine<T extends SpearMachineEntity> extends EntityModel<T> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "spear_machine"), "main");
	private final ModelPart wheel;
	private final ModelPart body;
	private final ModelPart spear;

	public Modelspear_machine(ModelPart root) {
		this.wheel = root.getChild("wheel");
		this.body = root.getChild("body");
		this.spear = root.getChild("spear");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wheel = partdefinition.addOrReplaceChild("wheel", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition side_wheel_2_r1 = wheel.addOrReplaceChild("side_wheel_2_r1", CubeListBuilder.create().texOffs(12, 29).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(0, 29).addBox(-11.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(0, 8).addBox(-6.5F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 0).addBox(-5.0F, 5.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 27).addBox(4.0F, 5.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 27).addBox(-7.0F, 5.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 4).addBox(3.0F, 4.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(26, 4).addBox(-4.0F, 4.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 12).addBox(-4.0F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 12).addBox(3.0F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition body_2_4_r1 = body.addOrReplaceChild("body_2_4_r1", CubeListBuilder.create().texOffs(26, 21).addBox(-1.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition body_2_3_r1 = body.addOrReplaceChild("body_2_3_r1", CubeListBuilder.create().texOffs(20, 21).addBox(0.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition spear = partdefinition.addOrReplaceChild("spear", CubeListBuilder.create().texOffs(24, 36).addBox(-1.0F, -0.5F, -22.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
				.texOffs(0, 18).addBox(-0.5F, -0.5F, -27.0F, 1.0F, 1.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.5F, 0.0F));

		PartDefinition tip_1_r1 = spear.addOrReplaceChild("tip_1_r1", CubeListBuilder.create().texOffs(16, 39).addBox(1.5944F, -0.5F, -15.0778F, 1.0F, 1.0F, 7.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.7521F, 0.0F, 0.1309F, 0.0F));

		PartDefinition tip_1_r2 = spear.addOrReplaceChild("tip_1_r2", CubeListBuilder.create().texOffs(0, 39).addBox(-2.5944F, -0.5F, -15.0778F, 1.0F, 1.0F, 7.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.7521F, 0.0F, -0.1309F, 0.0F));

		PartDefinition head_r1 = spear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(24, 30).addBox(-1.5F, -1.5F, 10.2521F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.7521F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 96, 64);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		wheel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		spear.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInSittingPose()){
			this.spear.yRot = 0f;
			this.spear.xRot = 55f;
		}
		else {
			this.spear.yRot = netHeadYaw / (180F / (float) Math.PI);
			this.spear.xRot = headPitch / (180F / (float) Math.PI);
		}
		this.wheel.xRot = limbSwing;

		if (entity.getMainHandItem().getItem() instanceof ItemToolBasePike) {
			if (!(this.attackTime <= 0.0F)) {
				animateAttack(this, this, entity);
			}
			float f = 1 - Math.min(25, entity.getTicksUsingItem()) / 25f;
			if (f > 0 && f <= 0.40) {
				float fs = f / 0.40f;
				this.spear.yRot += fs * 0.35f;
			}
			else if (f > 0.40 && f <= 0.5) {
				float fs = (f - 0.40f) / 0.10f;
				this.spear.yRot += 0.35f + fs * -0.55f;
			}
			else if (f > 0.5) {
				float fs = 1 - (f - 0.5f) / 0.5f;
				this.spear.yRot += fs * -0.2f;
			}
		}
	}

	public static <T extends Modelspear_machine<?>> void animateAttack(Modelspear_machine<?> humanoidModel, T t, LivingEntity livingEntity) {
		float f = livingEntity.getAttackAnim(Minecraft.getInstance().getPartialTick());
		float f2 = Ease.inOutSine(SpearAnimations.progress(f, 0.0F, 0.05F));
		float f3 = Ease.inQuad(SpearAnimations.progress(f, 0.05F, 0.2F));
		float f4 = Ease.inOutExpo(SpearAnimations.progress(f, 0.4F, 1.0F));
		ModelPart var10000 = humanoidModel.spear;
		var10000.yRot -= humanoidModel.body.yRot;
		var10000 = humanoidModel.spear;
		var10000.yRot -= humanoidModel.body.yRot;
		var10000 = humanoidModel.spear;
		var10000.xRot += (90.0F * f2 - 120.0F * f3 + 30.0F * f4) * 0.017453292F * 0.2f;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		return this.spear;
	}

	@Override
	public ModelPart getHead() {
		return this.spear;
	}
}

