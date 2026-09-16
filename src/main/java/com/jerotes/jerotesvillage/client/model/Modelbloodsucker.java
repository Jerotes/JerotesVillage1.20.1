package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.BloodsuckerAnimation;
import com.jerotes.jerotesvillage.client.animation.ScavengerAnimation;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.BloodsuckerEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ScavengerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class Modelbloodsucker<T extends BloodsuckerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bloodsucker"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelbloodsucker(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, -11.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, -5.0F, -3.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, -1.3F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.9F, -6.8F, -0.9163F, 0.0F, 0.0F));

		PartDefinition right_teeth = head.addOrReplaceChild("right_teeth", CubeListBuilder.create(), PartPose.offset(-2.6451F, 7.9436F, -11.5F));

		PartDefinition right_teeth_r1 = right_teeth.addOrReplaceChild("right_teeth_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.4F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_teeth = head.addOrReplaceChild("left_teeth", CubeListBuilder.create(), PartPose.offset(2.6451F, 7.9436F, -11.5F));

		PartDefinition left_teeth_r1 = left_teeth.addOrReplaceChild("left_teeth_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -2.0F, -1.5F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.4F, -0.0873F, 0.0F, 0.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.25F, -4.5F));

		PartDefinition front_body_r1 = front_body.addOrReplaceChild("front_body_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-7.0F, -8.0F, -6.0F, 14.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.25F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_front_leg = front_body.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, 4.25F, -4.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(100, 33).addBox(-1.5F, -10.5F, -2.0F, 3.0F, 21.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.4246F, -5.5112F, -0.4831F, 0.0797F, -0.138F, 2.5832F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create(), PartPose.offset(-8.7578F, -12.1059F, 0.6237F));

		PartDefinition left_front_leg_1_r1 = right_front_leg_1.addOrReplaceChild("left_front_leg_1_r1", CubeListBuilder.create().texOffs(98, 18).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -2.0F, 0.3442F, -0.0594F, -1.712F));

		PartDefinition right_front_leg_2 = right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create(), PartPose.offset(-9.3677F, 0.91F, -4.1358F));

		PartDefinition left_front_leg_2_r1 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r1", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -6.5F, -2.5F, 4.0F, 13.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-6.8995F, 17.678F, -2.9131F, -0.1636F, 0.0384F, 0.3288F));

		PartDefinition left_front_leg_2_r2 = right_front_leg_2.addOrReplaceChild("left_front_leg_2_r2", CubeListBuilder.create().texOffs(114, 0).addBox(-1.5F, -19.5F, -2.0F, 3.0F, 42.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6138F, 16.9243F, -2.8428F, -0.1636F, 0.0384F, 0.3288F));

		PartDefinition left_front_leg = front_body.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, 4.25F, -4.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(100, 33).mirror().addBox(-1.5F, -10.5F, -2.0F, 3.0F, 21.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(3.4246F, -5.5112F, -0.4831F, 0.0797F, 0.138F, -2.5832F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create(), PartPose.offset(8.7578F, -12.1059F, 0.6237F));

		PartDefinition left_front_leg_1_r2 = left_front_leg_1.addOrReplaceChild("left_front_leg_1_r2", CubeListBuilder.create().texOffs(98, 18).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 0.0F, -2.0F, 0.3442F, 0.0594F, 1.712F));

		PartDefinition left_front_leg_2 = left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create(), PartPose.offset(9.3677F, 0.91F, -4.1358F));

		PartDefinition left_front_leg_2_r3 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r3", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-2.0F, -6.5F, -2.5F, 4.0F, 13.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(6.8995F, 17.678F, -2.9131F, -0.1636F, -0.0384F, -0.3288F));

		PartDefinition left_front_leg_2_r4 = left_front_leg_2.addOrReplaceChild("left_front_leg_2_r4", CubeListBuilder.create().texOffs(114, 0).mirror().addBox(-1.5F, -19.5F, -2.0F, 3.0F, 42.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.6138F, 16.9243F, -2.8428F, -0.1636F, -0.0384F, -0.3288F));

		PartDefinition right_middle_leg = front_body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offset(-7.0F, 5.25F, 1.0F));

		PartDefinition right_middle_leg_r1 = right_middle_leg.addOrReplaceChild("right_middle_leg_r1", CubeListBuilder.create().texOffs(90, 57).addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-2.5524F, -5.7001F, -0.5F, -1.1416F, 1.5193F, 1.561F));

		PartDefinition right_middle_leg_1 = right_middle_leg.addOrReplaceChild("right_middle_leg_1", CubeListBuilder.create(), PartPose.offset(-6.0F, -12.0F, -0.5F));

		PartDefinition right_middle_leg_1_r1 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 58).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, 4.0F, 0.0F, 0.1307F, -1.467F, -2.5954F));

		PartDefinition right_middle_leg_2 = right_middle_leg_1.addOrReplaceChild("right_middle_leg_2", CubeListBuilder.create(), PartPose.offset(-7.0F, 10.0F, 0.25F));

		PartDefinition right_middle_leg_2_r1 = right_middle_leg_2.addOrReplaceChild("right_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 46).addBox(-1.0F, -14.5F, -2.0F, 3.0F, 28.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 12.5F, 0.25F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_middle_leg = front_body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offset(7.0F, 3.25F, 1.0F));

		PartDefinition left_middle_leg_r1 = left_middle_leg.addOrReplaceChild("left_middle_leg_r1", CubeListBuilder.create().texOffs(90, 57).mirror().addBox(-1.5F, -7.5F, -1.5F, 3.0F, 15.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(2.5524F, -3.7001F, -0.5F, -1.1416F, -1.5193F, -1.561F));

		PartDefinition left_middle_leg_1 = left_middle_leg.addOrReplaceChild("left_middle_leg_1", CubeListBuilder.create(), PartPose.offset(6.0F, -10.0F, -0.5F));

		PartDefinition left_middle_leg_1_r1 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_1_r1", CubeListBuilder.create().texOffs(102, 58).mirror().addBox(-2.0F, -7.0F, -2.0F, 4.0F, 14.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.0F, 4.0F, 0.0F, 0.1307F, 1.467F, 2.5954F));

		PartDefinition left_middle_leg_2 = left_middle_leg_1.addOrReplaceChild("left_middle_leg_2", CubeListBuilder.create(), PartPose.offset(7.0F, 10.0F, 0.25F));

		PartDefinition left_middle_leg_2_r1 = left_middle_leg_2.addOrReplaceChild("left_middle_leg_2_r1", CubeListBuilder.create().texOffs(116, 46).mirror().addBox(-2.0F, -14.5F, -2.0F, 3.0F, 28.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 12.5F, 0.25F, 0.0F, 0.0F, -0.1309F));

		PartDefinition hind_body = body.addOrReplaceChild("hind_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 1.5F));

		PartDefinition hind_body_r1 = hind_body.addOrReplaceChild("hind_body_r1", CubeListBuilder.create().texOffs(0, 45).addBox(-6.0F, -4.5F, -4.5F, 12.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0053F, -0.0019F, 9.5326F, 0.3924F, -0.0028F, 0.0114F));

		PartDefinition hind_body_r2 = hind_body.addOrReplaceChild("hind_body_r2", CubeListBuilder.create().texOffs(44, 18).addBox(-4.0F, -3.5F, -6.0F, 8.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 6.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = hind_body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.5F, 6.5F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(90, 78).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2516F, -1.1727F, 0.4452F, -2.9065F, -0.5692F, 2.1999F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-4.0F, -2.0F, 0.0F));

		PartDefinition right_hind_leg_1_r1 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_1_r1", CubeListBuilder.create().texOffs(102, 75).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.6636F, 3.4217F, -1.9199F, -1.0036F, 0.7854F));

		PartDefinition right_hind_leg_2 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create(), PartPose.offset(-7.0F, -5.0F, 6.0F));

		PartDefinition right_hind_leg_2_r1 = right_hind_leg_2.addOrReplaceChild("right_hind_leg_2_r1", CubeListBuilder.create().texOffs(112, 90).addBox(-2.0F, -20.0F, -2.0F, 4.0F, 34.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 16.5103F, 2.9137F, 0.1745F, -0.1047F, 0.0873F));

		PartDefinition left_hind_leg = hind_body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.5F, 2.5F, 6.5F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(90, 78).mirror().addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.2516F, -1.1727F, 0.4452F, -2.9065F, 0.5692F, -2.1999F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create(), PartPose.offset(4.0F, -2.0F, 0.0F));

		PartDefinition left_hind_leg_1_r1 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_1_r1", CubeListBuilder.create().texOffs(102, 75).mirror().addBox(-2.0F, -5.5F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -2.6636F, 3.4217F, -1.9199F, 1.0036F, -0.7854F));

		PartDefinition left_hind_leg_2 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create(), PartPose.offset(7.0F, -5.0F, 6.0F));

		PartDefinition left_hind_leg_2_r1 = left_hind_leg_2.addOrReplaceChild("left_hind_leg_2_r1", CubeListBuilder.create().texOffs(112, 90).mirror().addBox(-2.0F, -20.0F, -2.0F, 4.0F, 34.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 16.5103F, 2.9137F, 0.1745F, 0.1047F, -0.0873F));

		PartDefinition tail = hind_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.3028F, 20.4513F, -2.7925F, 0.0F, 0.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(42, 45).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0743F, -0.3811F, -0.9686F, 0.0F, 0.0F));

		PartDefinition right_tail = tail.addOrReplaceChild("right_tail", CubeListBuilder.create(), PartPose.offset(-1.9975F, 7.7841F, -6.9977F));

		PartDefinition right_tail_r1 = right_tail.addOrReplaceChild("right_tail_r1", CubeListBuilder.create().texOffs(0, 45).addBox(-0.8593F, -3.4288F, 3.8765F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9241F, 2.1196F, -7.355F, -0.2114F, 0.0787F, 0.1565F));

		PartDefinition right_tail_r2 = right_tail.addOrReplaceChild("right_tail_r2", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3804F, -2.1116F, -0.1768F, -0.6497F, 0.0842F, 0.1009F));

		PartDefinition right_tail_r3 = right_tail.addOrReplaceChild("right_tail_r3", CubeListBuilder.create().texOffs(42, 53).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9654F, -5.502F, 3.7457F, -1.0893F, 0.0774F, 0.0404F));

		PartDefinition left_tail = tail.addOrReplaceChild("left_tail", CubeListBuilder.create(), PartPose.offset(1.75F, 1.545F, -0.4228F));

		PartDefinition left_tail_r1 = left_tail.addOrReplaceChild("left_tail_r1", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5932F, 13.8527F, -10.1439F, -0.2114F, -0.0787F, -0.1565F));

		PartDefinition left_tail_r2 = left_tail.addOrReplaceChild("left_tail_r2", CubeListBuilder.create().texOffs(50, 53).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1329F, 4.1275F, -6.7516F, -0.6497F, -0.0842F, -0.1009F));

		PartDefinition left_tail_r3 = left_tail.addOrReplaceChild("left_tail_r3", CubeListBuilder.create().texOffs(42, 53).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.7179F, 0.737F, -2.8292F, -1.0893F, -0.0774F, -0.0404F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(BloodsuckerAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, BloodsuckerAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, BloodsuckerAnimation.ATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}