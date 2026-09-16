package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.TyrantOfTheMountainRealmAnimation;
import com.jerotes.jerotesvillage.client.animation.TyrantOfTheMountainRealmThreeAnimation;
import com.jerotes.jerotesvillage.client.animation.TyrantOfTheMountainRealmTwoAnimation;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class Modeltyrant_of_the_mountain_realm<T extends TyrantOfTheMountainRealmEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "tyrant_of_the_mountain_realm"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart up_body;
	private final ModelPart body_sword;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart left_hand_vine;
	private final ModelPart spear;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart sword;

	public Modeltyrant_of_the_mountain_realm(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.up_body = this.body.getChild("up_body");
		this.body_sword = this.up_body.getChild("body_sword");
		this.left_arm = root.getChild("left_arm");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.left_hand_vine = this.left_hand.getChild("left_hand_vine");
		this.spear = this.left_hand.getChild("spear");
		this.right_arm = root.getChild("right_arm");
		this.right_hand = this.right_arm.getChild("right_hand");
		this.sword = this.right_hand.getChild("sword");

	}

	public static LayerDefinition createBodyLayer() {
		return Modeltyrant_of_the_mountain_realm_add.createBodyLayer();
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		if (t.isUseSword() || (t.getChangeSword() > 5 && !t.isUseSword())) {
			this.sword.visible = true;
			this.body_sword.visible = false;
		}
		else {
			this.sword.visible = false;
			this.body_sword.visible = true;
		}
		if ((t.getChangeSword() <= 3 && t.isUseSword()) || (t.getChangeSword() > 5 && !t.isUseSword())) {
			if (t.isAlive() && t.getRushUseTick() > 20 && t.getRushUseTick() <= 120) {
				t.rushStartAnimationState.stop();
				t.rushStopAnimationState.stop();
				this.animateWalk(TyrantOfTheMountainRealmTwoAnimation.RUSH, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(TyrantOfTheMountainRealmAnimation.WALK2, f, f2, 2.0f, 2.0f);
			}
		} else {
			this.animateWalk(TyrantOfTheMountainRealmAnimation.WALK1, f, f2, 2.0f, 2.0f);
		}
        this.spear.visible = t.getThrowSpearUseTick() > 0;
		this.animate(t.idleBaseAnimationState, TyrantOfTheMountainRealmAnimation.IDLEBASE, f3);
		this.animate(t.idle1AnimationState, TyrantOfTheMountainRealmAnimation.IDLE1, f3);
		this.animate(t.idle2AnimationState, TyrantOfTheMountainRealmAnimation.IDLE2, f3);
		this.animate(t.pullAnimationState, TyrantOfTheMountainRealmAnimation.PULL, f3);
		this.animate(t.pullAttackAnimationState, TyrantOfTheMountainRealmAnimation.PULLATTACK, f3);
		this.animate(t.backAnimationState, TyrantOfTheMountainRealmAnimation.BACK, f3);
		this.animate(t.throwAnimationState, TyrantOfTheMountainRealmAnimation.SHOOT, f3);
		this.animate(t.block1AnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCK1, f3);
		this.animate(t.block2AnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCK2, f3);
		this.animate(t.block3AnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCK3, f3);
		this.animate(t.block4AnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCK4, f3);
		this.animate(t.block5AnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCK5, f3);
		this.animate(t.wonderlinCallAnimationState, TyrantOfTheMountainRealmAnimation.WONDERLIN, f3);
		this.animate(t.wonderlinRushAnimationState, TyrantOfTheMountainRealmAnimation.WONDERLINRUSH, f3);
		this.animate(t.goldAnimationState, TyrantOfTheMountainRealmAnimation.GOLDEN, f3);
		this.animate(t.thornAnimationState, TyrantOfTheMountainRealmTwoAnimation.THORN, f3);
		this.animate(t.grappleAnimationState, TyrantOfTheMountainRealmTwoAnimation.GRAPPLE, f3);
		this.animate(t.rushAttackAnimationState, TyrantOfTheMountainRealmTwoAnimation.RUSHATTACK, f3);
		this.animate(t.deadAnimationState, TyrantOfTheMountainRealmTwoAnimation.DEAD, f3);
		this.animate(t.rushStartAnimationState, TyrantOfTheMountainRealmTwoAnimation.RUSHSTART, f3);
		this.animate(t.rushStopAnimationState, TyrantOfTheMountainRealmTwoAnimation.RUSHSTOP, f3);
		this.animate(t.blockAttackAnimationState, TyrantOfTheMountainRealmTwoAnimation.BLOCKATTACK, f3);
		this.animate(t.shockAnimationState, TyrantOfTheMountainRealmTwoAnimation.SHOCK, f3);
		this.animate(t.angryAttackAnimationState, TyrantOfTheMountainRealmTwoAnimation.ANGRYATTACK, f3);
		this.animate(t.snapAttackAnimationState, TyrantOfTheMountainRealmTwoAnimation.SNAPATTACK, f3);

		this.animate(t.cleave1AnimationState, TyrantOfTheMountainRealmThreeAnimation.CLEAVE1, f3);
		this.animate(t.cleave2AnimationState, TyrantOfTheMountainRealmThreeAnimation.CLEAVE1, f3);
		this.animate(t.cleave3AnimationState, TyrantOfTheMountainRealmThreeAnimation.CLEAVE1, f3);
		this.animate(t.slash1AnimationState, TyrantOfTheMountainRealmThreeAnimation.SLASH1, f3);
		this.animate(t.slash2AnimationState, TyrantOfTheMountainRealmThreeAnimation.SLASH2, f3);
		this.animate(t.slash3AnimationState, TyrantOfTheMountainRealmThreeAnimation.SLASH3, f3);
		this.animate(t.stomp1AnimationState, TyrantOfTheMountainRealmThreeAnimation.STOMP1, f3);
		this.animate(t.stomp2AnimationState, TyrantOfTheMountainRealmThreeAnimation.STOMP1, f3);
		this.animate(t.stomp3AnimationState, TyrantOfTheMountainRealmThreeAnimation.STOMP1, f3);
		this.animate(t.fist1AnimationState, TyrantOfTheMountainRealmThreeAnimation.FIST1, f3);
		this.animate(t.fist2AnimationState, TyrantOfTheMountainRealmThreeAnimation.FIST2, f3);
		this.animate(t.fist3AnimationState, TyrantOfTheMountainRealmThreeAnimation.FIST3, f3);
		this.animate(t.shake1AnimationState, TyrantOfTheMountainRealmThreeAnimation.SHAKE1, f3);
		this.animate(t.shake2AnimationState, TyrantOfTheMountainRealmThreeAnimation.SHAKE1, f3);
		this.animate(t.shake3AnimationState, TyrantOfTheMountainRealmThreeAnimation.SHAKE1, f3);

//		this.animate(t.breathAnimationState, CorrosiverAnimation.BREATH, f3);
//		this.animate(t.windAnimationState, CorrosiverAnimation.WIND, f3);
//		this.animate(t.snowAnimationState, CorrosiverAnimation.SNOW, f3);
//		this.animate(t.deadAnimationState, CorrosiverAnimation.DEAD, f3);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f%360, -30.0f, 30.0f);
		f2 = Mth.clamp(f2%360, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getHand(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_hand;
		}
		return this.right_hand;
	}

	protected ModelPart getWeaponHand(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.spear;
		}
		return this.sword;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getHand(humanoidArm).translateAndRotate(poseStack);
		this.getWeaponHand(humanoidArm).translateAndRotate(poseStack);
	}
	public void translateToBodySword(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.body_sword.translateAndRotate(poseStack);
		this.up_body.translateAndRotate(poseStack);
		this.body_sword.translateAndRotate(poseStack);
	}
}