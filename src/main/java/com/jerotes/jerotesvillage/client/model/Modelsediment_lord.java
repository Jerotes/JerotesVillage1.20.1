package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SedimentLordAnimation;
import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;


public class Modelsediment_lord<T extends LivingEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "sediment_lord"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart jacket;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart left_sleeve;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart right_sleeve;
	private final ModelPart left_leg;
	private final ModelPart left_pants;
	private final ModelPart right_leg;
	private final ModelPart right_pants;

	public Modelsediment_lord(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.body = root.getChild("body");
		this.jacket = root.getChild("jacket");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.left_sleeve = root.getChild("left_sleeve");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.right_sleeve = root.getChild("right_sleeve");
		this.left_leg = root.getChild("left_leg");
		this.left_pants = root.getChild("left_pants");
		this.right_leg = root.getChild("right_leg");
		this.right_pants = root.getChild("right_pants");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket = partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_pants = partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_pants = partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t instanceof SedimentLordEntity sedimentLord) {
			this.head.yRot = f4 * ((float)Math.PI / 180F);
			this.head.xRot = f5 * ((float)Math.PI / 180F);
			float walk = sedimentLord.getAttackWalkAnim() / 10f;
			this.animateWalk(SedimentLordAnimation.ATTACKWALK, f, f2 * walk, 1.5f, 2.0f);
			this.animateWalk(SedimentLordAnimation.WALK, f, f2 * (1 - walk), 1.5f, 2.0f);
			this.animate(sedimentLord.startAnimationState, SedimentLordAnimation.START, f3);
			this.animate(sedimentLord.idleAnimationState, SedimentLordAnimation.IDLE, f3);
			this.animate(sedimentLord.attack1AnimationState, SedimentLordAnimation.ATTACK1, f3);
			this.animate(sedimentLord.attack2AnimationState, SedimentLordAnimation.ATTACK2, f3);
			this.animate(sedimentLord.swordAttack1AnimationState, SedimentLordAnimation.SWORDATTACK1, f3);
			this.animate(sedimentLord.swordAttack2AnimationState, SedimentLordAnimation.SWORDATTACK2, f3);
			this.animate(sedimentLord.swordAttack3AnimationState, SedimentLordAnimation.SWORDATTACK3, f3);
			this.animate(sedimentLord.swordAttack4AnimationState, SedimentLordAnimation.SWORDATTACK4, f3);
			this.animate(sedimentLord.leadAnimationState, SedimentLordAnimation.LEAD, f3);
			this.animate(sedimentLord.block1AnimationState, SedimentLordAnimation.BLOCK1, f3);
			this.animate(sedimentLord.block2AnimationState, SedimentLordAnimation.BLOCK2, f3);
			this.animate(sedimentLord.block3AnimationState, SedimentLordAnimation.BLOCK3, f3);
			this.animate(sedimentLord.shieldAttackAnimationState, SedimentLordAnimation.SHIELDATTACK, f3);
			this.animate(sedimentLord.dead1AnimationState, SedimentLordAnimation.DEAD1, f3);
			this.animate(sedimentLord.dead2AnimationState, SedimentLordAnimation.DEAD2, f3);
			this.animate(sedimentLord.pushingAttackAnimationState, SedimentLordAnimation.PUSHINGATTACK, f3);
			this.animate(sedimentLord.tripAttackAnimationState, SedimentLordAnimation.TRIPATTACK, f3);
			if (sedimentLord.getMainHandItem().getItem() instanceof TieredItem tieredItem && tieredItem.canDisableShield(sedimentLord.getMainHandItem(), new ItemStack(Items.SHIELD.asItem()), sedimentLord, sedimentLord)) {
				this.animate(sedimentLord.precisionAttackAnimationState, SedimentLordAnimation.PRECISIONATTACK2, f3);
			}
			else {
				this.animate(sedimentLord.precisionAttackAnimationState, SedimentLordAnimation.PRECISIONATTACK1, f3);
			}
			this.animate(sedimentLord.sweepingAttackAnimationState, SedimentLordAnimation.SWEEPINGATTACK, f3);
			this.animate(sedimentLord.menacingAttackAnimationState, SedimentLordAnimation.MENACINGATTACK, f3);
			this.animate(sedimentLord.maneuveringAttackAnimationState, SedimentLordAnimation.MANEUVERINGATTACK, f3);
			this.animate(sedimentLord.lungingAttackAnimationState, SedimentLordAnimation.LUNGINGATTACK, f3);
			this.animate(sedimentLord.goadingAttackAnimationState, SedimentLordAnimation.GOADINGATTACK, f3);
			this.animate(sedimentLord.feintingAttackAnimationState, SedimentLordAnimation.FEINTINGATTACK, f3);
			this.animate(sedimentLord.disarmingAttackAnimationState, SedimentLordAnimation.DISARMINGATTACK, f3);
			this.animate(sedimentLord.distractingStrikeAnimationState, SedimentLordAnimation.DISTRACTINGSTRIKE, f3);
		}
		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getWeapon(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getWeapon(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_weapon;
		}
		return this.right_weapon;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	public void setAllVisible(boolean bl) {
		this.head.visible = bl;
		this.hat.visible = bl;
		this.body.visible = bl;
		this.right_arm.visible = bl;
		this.left_arm.visible = bl;
		this.right_leg.visible = bl;
		this.left_leg.visible = bl;
	}
}