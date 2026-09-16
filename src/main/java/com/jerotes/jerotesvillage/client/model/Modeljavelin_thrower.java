package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.client.animation.JavelinThrowerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.JavelinThrowerEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;


public class Modeljavelin_thrower<T extends JavelinThrowerEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "javelin_thrower"), "main");
	private final ModelPart head;
	private final ModelPart arms;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public Modeljavelin_thrower(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.arms = body.getChild("arms");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		if (!t.getMainHandItem().isEmpty()) {
			if (!t.isLeftHanded()) {
				this.animateWalk(JavelinThrowerAnimation.ITEMWALK1, f, f2, 2.0f, 2.0f);
			} else {
				this.animateWalk(JavelinThrowerAnimation.ITEMWALK2, f, f2, 2.0f, 2.0f);
			}
		} else {
			this.animateWalk(JavelinThrowerAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		boolean bl = true;
		this.left_arm.visible = bl;
		this.right_arm.visible = bl;
		this.arms.visible = !bl;
		if (!t.isLeftHanded()) {
			if (!t.getMainHandItem().isEmpty()) {
				this.animateIdle2(t, t.idleAnimationState, JavelinThrowerAnimation.JAVELINIDLE1, f3);
			}
			this.animate(t.attackAnimationState, JavelinThrowerAnimation.ATTACK1, f3);
			this.animate(t.itemAttackAnimationState, JavelinThrowerAnimation.JAVELINATTACK1, f3);
			this.animate(t.throw1AnimationState, JavelinThrowerAnimation.THROW1, f3);
			this.animate(t.throw2AnimationState, JavelinThrowerAnimation.THROW2, f3);
		} else {
			if (!t.getMainHandItem().isEmpty()) {
				this.animateIdle2(t, t.idleAnimationState, JavelinThrowerAnimation.JAVELINIDLE2, f3);
			}
			this.animate(t.attackAnimationState, JavelinThrowerAnimation.ATTACK2, f3);
			this.animate(t.itemAttackAnimationState, JavelinThrowerAnimation.JAVELINATTACK2, f3);
			this.animate(t.throw1AnimationState, JavelinThrowerAnimation.THROW2, f3);
			this.animate(t.throw2AnimationState, JavelinThrowerAnimation.THROW1, f3);
		}
		//盾牌
		if (!t.isLeftHanded()) {
			if (this.attackTime <= 0.0F) {
				this.animate(t.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
			}
			this.animate(t.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
		}
		else {
			if (this.attackTime <= 0.0F) {
				this.animate(t.shieldUseMainhandAnimationState, HumanoidAnimation.SHIELD_LEFT, f3);
			}
			this.animate(t.shieldUseOffhandAnimationState, HumanoidAnimation.SHIELD_RIGHT, f3);
		}
		//
		if (t.deathTime == 0) {
			arms.xScale = 0;
			arms.yScale = 0;
			arms.zScale = 0;
		}
		this.hatOld.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}
	protected void animateIdle2(JavelinThrowerEntity javelinThrowerEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			Modelspecial_action.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, 1 - javelinThrowerEntity.attackAnimProgress / 10f),
					new Vector3f());
		});
	}
}