package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.VirtualCaveSpiderAnimation;
import com.jerotes.jerotesvillage.entity.Animal.DownySpiderEntity;
import com.jerotes.jerotesvillage.entity.Monster.VirtualCaveSpiderEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelvirtual_cave_spider<T extends LivingEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "virtual_cave_spider"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart right_hind_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_middle_hind_leg;
	private final ModelPart left_middle_hind_leg;
	private final ModelPart right_middle_front_leg;
	private final ModelPart left_middle_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_front_leg;

	public Modelvirtual_cave_spider(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_middle_hind_leg = root.getChild("right_middle_hind_leg");
		this.left_middle_hind_leg = root.getChild("left_middle_hind_leg");
		this.right_middle_front_leg = root.getChild("right_middle_front_leg");
		this.left_middle_front_leg = root.getChild("left_middle_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_front_leg = root.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(66, 0).addBox(-4.5F, -3.0F, -13.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(66, 0).mirror().addBox(0.5F, -3.0F, -13.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -4.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0218F, -3.0005F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_pedipalp = head.addOrReplaceChild("right_pedipalp", CubeListBuilder.create(), PartPose.offset(-7.0F, 1.0F, -11.5F));

		PartDefinition right_pedipalp_r1 = right_pedipalp.addOrReplaceChild("right_pedipalp_r1", CubeListBuilder.create().texOffs(0, 48).addBox(-5.0F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.297F, -1.6843F, -0.5F, 0.0F, 0.0F, 1.8762F));

		PartDefinition right_pedipalp_1 = right_pedipalp.addOrReplaceChild("right_pedipalp_1", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -4.0F));

		PartDefinition right_pedipalp_1_r1 = right_pedipalp_1.addOrReplaceChild("right_pedipalp_1_r1", CubeListBuilder.create().texOffs(0, 56).addBox(-6.0F, -2.0F, -2.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4935F, 3.8013F, -0.4301F, 0.0F, 0.48F, 1.8762F));

		PartDefinition left_pedipalp = head.addOrReplaceChild("left_pedipalp", CubeListBuilder.create(), PartPose.offset(7.0F, 1.0F, -11.5F));

		PartDefinition left_pedipalp_r1 = left_pedipalp.addOrReplaceChild("left_pedipalp_r1", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-5.0F, -2.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.297F, -1.6843F, -0.5F, 0.0F, 0.0F, -1.8762F));

		PartDefinition left_pedipalp_down = left_pedipalp.addOrReplaceChild("left_pedipalp_down", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -4.0F));

		PartDefinition left_pedipalp_down_r1 = left_pedipalp_down.addOrReplaceChild("left_pedipalp_down_r1", CubeListBuilder.create().texOffs(0, 56).mirror().addBox(-6.0F, -2.0F, -2.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.4295F, 3.8535F, -0.4238F, 0.0F, -0.48F, -1.8762F));

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, 4.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-6.0F, -4.0F, -2.0F, 12.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 8.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5151F, 5.1056F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-8.0F, 16.0F, 4.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(32, 56).addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -5.0F, 2.5F, 0.0F, 0.4363F, 0.7854F));

		PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-9.0F, -9.5F, 8.0F));

		PartDefinition right_hind_leg_1_r1 = right_hind_leg_1.addOrReplaceChild("right_hind_leg_1_r1", CubeListBuilder.create().texOffs(72, 56).addBox(-12.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.5F, 4.5F, -2.4814F, -0.5567F, 1.9122F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(8.0F, 16.0F, 4.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(32, 56).mirror().addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -5.0F, 2.5F, 0.0F, -0.4363F, -0.7854F));

		PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create(), PartPose.offset(8.5F, -9.5F, 7.5F));

		PartDefinition left_hind_leg_1_r1 = left_hind_leg_1.addOrReplaceChild("left_hind_leg_1_r1", CubeListBuilder.create().texOffs(72, 56).mirror().addBox(-12.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 6.5F, 5.0F, -2.4814F, 0.5567F, -1.9122F));

		PartDefinition right_middle_hind_leg = partdefinition.addOrReplaceChild("right_middle_hind_leg", CubeListBuilder.create(), PartPose.offset(-8.0F, 16.0F, 0.0F));

		PartDefinition right_middle_hind_leg_r1 = right_middle_hind_leg.addOrReplaceChild("right_middle_hind_leg_r1", CubeListBuilder.create().texOffs(32, 56).addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6003F, -5.8074F, -0.9957F, 0.0F, -0.1309F, 0.7854F));

		PartDefinition right_middle_hind_leg_1 = right_middle_hind_leg.addOrReplaceChild("right_middle_hind_leg_1", CubeListBuilder.create(), PartPose.offset(-10.0F, -12.0F, -1.0F));

		PartDefinition right_middle_hind_leg_1_r1 = right_middle_hind_leg_1.addOrReplaceChild("right_middle_hind_leg_1_r1", CubeListBuilder.create().texOffs(76, 48).addBox(-11.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7579F, 9.971F, 1.4872F, -3.1203F, -0.243F, 1.8006F));

		PartDefinition left_middle_hind_leg = partdefinition.addOrReplaceChild("left_middle_hind_leg", CubeListBuilder.create(), PartPose.offset(8.0F, 16.0F, 0.0F));

		PartDefinition left_middle_hind_leg_r1 = left_middle_hind_leg.addOrReplaceChild("left_middle_hind_leg_r1", CubeListBuilder.create().texOffs(32, 56).mirror().addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.6003F, -5.8074F, -0.9957F, 0.0F, 0.1309F, -0.7854F));

		PartDefinition left_middle_hind_leg_1 = left_middle_hind_leg.addOrReplaceChild("left_middle_hind_leg_1", CubeListBuilder.create(), PartPose.offset(10.0F, -12.0F, -1.0F));

		PartDefinition left_middle_hind_leg_1_r1 = left_middle_hind_leg_1.addOrReplaceChild("left_middle_hind_leg_1_r1", CubeListBuilder.create().texOffs(76, 48).mirror().addBox(-11.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.7579F, 9.971F, 1.4872F, -3.1203F, 0.243F, -1.8006F));

		PartDefinition right_middle_front_leg = partdefinition.addOrReplaceChild("right_middle_front_leg", CubeListBuilder.create(), PartPose.offset(-8.0F, 16.0F, -4.0F));

		PartDefinition right_middle_front_leg_r1 = right_middle_front_leg.addOrReplaceChild("right_middle_front_leg_r1", CubeListBuilder.create().texOffs(32, 56).addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3572F, -4.5644F, -3.4833F, 0.0F, -0.4363F, 0.7854F));

		PartDefinition right_middle_front_leg_1 = right_middle_front_leg.addOrReplaceChild("right_middle_front_leg_1", CubeListBuilder.create(), PartPose.offset(-8.0F, -9.0F, -6.0F));

		PartDefinition right_middle_front_leg_1_r1 = right_middle_front_leg_1.addOrReplaceChild("right_middle_front_leg_1_r1", CubeListBuilder.create().texOffs(72, 56).addBox(-12.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0234F, 8.1882F, -5.4939F, 2.7566F, 0.3979F, 2.0871F));

		PartDefinition left_middle_front_leg = partdefinition.addOrReplaceChild("left_middle_front_leg", CubeListBuilder.create(), PartPose.offset(8.0F, 16.0F, -4.0F));

		PartDefinition left_middle_front_leg_r1 = left_middle_front_leg.addOrReplaceChild("left_middle_front_leg_r1", CubeListBuilder.create().texOffs(32, 56).mirror().addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.3572F, -4.5644F, -3.4833F, 0.0F, 0.4363F, -0.7854F));

		PartDefinition left_middle_front_leg_1 = left_middle_front_leg.addOrReplaceChild("left_middle_front_leg_1", CubeListBuilder.create(), PartPose.offset(8.0F, -9.0F, -6.0F));

		PartDefinition left_middle_front_leg_1_r1 = left_middle_front_leg_1.addOrReplaceChild("left_middle_front_leg_1_r1", CubeListBuilder.create().texOffs(72, 56).mirror().addBox(-12.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.889F, 8.2775F, -5.4279F, 2.7566F, -0.3979F, -2.0871F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-8.0F, 16.0F, -8.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(32, 56).addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, -3.0F, -0.0873F, -0.5934F, 0.7069F));

		PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create(), PartPose.offset(-6.0F, -8.0F, -8.0F));

		PartDefinition right_front_leg_1_r1 = right_front_leg_1.addOrReplaceChild("right_front_leg_1_r1", CubeListBuilder.create().texOffs(76, 40).addBox(-11.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4969F, 6.9398F, -6.6432F, 0.0F, 0.7418F, 1.8762F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(8.0F, 16.0F, -8.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(32, 56).mirror().addBox(-8.0F, -2.0F, -2.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -3.0F, -3.0F, -0.0873F, 0.5934F, -0.7069F));

		PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create(), PartPose.offset(6.0F, -8.0F, -8.0F));

		PartDefinition left_front_leg_1_r1 = left_front_leg_1.addOrReplaceChild("left_front_leg_1_r1", CubeListBuilder.create().texOffs(76, 40).mirror().addBox(-11.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.4969F, 6.9398F, -6.6432F, 0.0F, -0.7418F, -1.8762F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		if (t.isBaby()) {
			this.right_hind_leg.xScale = 0.6f;
			this.left_hind_leg.xScale = 0.6f;
			this.right_middle_hind_leg.xScale = 0.6f;
			this.left_middle_hind_leg.xScale = 0.6f;
			this.right_middle_front_leg.xScale = 0.6f;
			this.left_middle_front_leg.xScale = 0.6f;
			this.right_front_leg.xScale = 0.6f;
			this.left_front_leg.xScale = 0.6f;
			this.right_hind_leg.zScale = 0.6f;
			this.left_hind_leg.zScale = 0.6f;
			this.right_middle_hind_leg.zScale = 0.6f;
			this.left_middle_hind_leg.zScale = 0.6f;
			this.right_middle_front_leg.zScale = 0.6f;
			this.left_middle_front_leg.zScale = 0.6f;
			this.right_front_leg.zScale = 0.6f;
			this.left_front_leg.zScale = 0.6f;
		}
		else {
			this.right_hind_leg.xScale = 1f;
			this.left_hind_leg.xScale = 1f;
			this.right_middle_hind_leg.xScale = 1f;
			this.left_middle_hind_leg.xScale = 1f;
			this.right_middle_front_leg.xScale = 1f;
			this.left_middle_front_leg.xScale = 1f;
			this.right_front_leg.xScale = 1f;
			this.left_front_leg.xScale = 1f;
			this.right_hind_leg.zScale = 1f;
			this.left_hind_leg.zScale = 1f;
			this.right_middle_hind_leg.zScale = 1f;
			this.left_middle_hind_leg.zScale = 1f;
			this.right_middle_front_leg.zScale = 1f;
			this.left_middle_front_leg.zScale = 1f;
			this.right_front_leg.zScale = 1f;
			this.left_front_leg.zScale = 1f;
		}
		this.animateWalk(VirtualCaveSpiderAnimation.WALK, f, f2, 1.5f, 2.0f);
		if (t instanceof VirtualCaveSpiderEntity virtualCaveSpider) {
			this.animate(virtualCaveSpider.attackAnimationState, VirtualCaveSpiderAnimation.ATTACK, f3);
			this.animate(virtualCaveSpider.climbAnimationState, VirtualCaveSpiderAnimation.CLIMBING, f3);
		}
		if (t instanceof DownySpiderEntity downySpider) {
			this.animate(downySpider.attackAnimationState, VirtualCaveSpiderAnimation.ATTACK, f3);
			this.animate(downySpider.climbAnimationState, VirtualCaveSpiderAnimation.CLIMBING, f3);
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -10.0f, 10.0f);
		f2 = Mth.clamp(f2, -10.0f, 10.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
		this.body.yRot = f * ((float) Math.PI / 180F);
		this.body.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
