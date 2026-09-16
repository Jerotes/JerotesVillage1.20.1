package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.FireSpitterAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FireSpitterEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;


public class Modelfire_spitter<T extends FireSpitterEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "fire_spitter"), "main");
	private final ModelPart head;

	public Modelfire_spitter(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(FireSpitterAnimation.WALK, f, f2, 2.0f, 2.0f);
		if (!t.isLeftHanded()) {
			this.animate(t.drinkAnimationState, FireSpitterAnimation.DRINK1, f3);
			this.animate(t.spitteAnimationState, FireSpitterAnimation.SPITTE1, f3);
		}
		else {
			this.animate(t.drinkAnimationState, FireSpitterAnimation.DRINK2, f3);
			this.animate(t.spitteAnimationState, FireSpitterAnimation.SPITTE2, f3);
		}

		AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = t.getArmPose();
		boolean flag = abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSED && !(t instanceof AlwaysShowArmIllagerEntity);
		this.arms.visible = flag;
		this.leftArm.visible = !flag;
		this.rightArm.visible = !flag;
		if (!flag) {
			arms.xScale = 0;
			arms.yScale = 0;
			arms.zScale = 0;
		} else {
			leftArm.xScale = 0;
			leftArm.yScale = 0;
			leftArm.zScale = 0;
			rightArm.xScale = 0;
			rightArm.yScale = 0;
			rightArm.zScale = 0;
		}
		this.hatOld.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}
}