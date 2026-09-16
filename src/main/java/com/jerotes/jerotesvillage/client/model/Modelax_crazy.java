package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.AxCrazyAnimation;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.AxCrazyEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;


public class Modelax_crazy<T extends AxCrazyEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ax_crazy"), "main");
	private final ModelPart head;
	private final ModelPart arms;

	public Modelax_crazy(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.arms = body.getChild("arms");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
		partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
				//肚腩
				.texOffs(64, 15).addBox(-4.0F, 5.0F, -4.0F, 8.0F, 7.0F, 2.0F, new CubeDeformation(-0.2F))
				//肚腩服装
				.texOffs(84, 15).addBox(-4.0F, 5.0F, -4.0F, 8.0F, 7.0F, 2.0F, new CubeDeformation(0.1F))
				//铐
				.texOffs(64, 0).addBox(-6.5F, -1.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition partdefinition3 = partdefinition2.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
		partdefinition3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		String string = ChatFormatting.stripFormatting(t.getName().getString());
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.getAngryTick() >= 120 && !("Sigma".equals(string) || "Bateman".equals(string))) {
			if (!t.isLeftHanded()) {
				this.animateWalk(AxCrazyAnimation.RUN1, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(AxCrazyAnimation.RUN2, f, f2, 2.0f, 2.0f);
			}
		}
		else if (!t.getMainHandItem().isEmpty() && !("Sigma".equals(string) || "Bateman".equals(string))) {
			this.animateWalk(AxCrazyAnimation.ITEMWALK, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(AxCrazyAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, AxCrazyAnimation.IDLE, f3);
		if (!t.isLeftHanded()) {
			this.animate(t.attackAnimationState, AxCrazyAnimation.ATTACK1, f3);
			this.animate(t.itemAttack1AnimationState, AxCrazyAnimation.ITEMATTACK1, f3);
			this.animate(t.itemAttack2AnimationState, AxCrazyAnimation.ITEMATTACK3, f3);
			this.animate(t.itemAttack3AnimationState, AxCrazyAnimation.ITEMATTACK5, f3);
		}
		else {
			this.animate(t.attackAnimationState, AxCrazyAnimation.ATTACK2, f3);
			this.animate(t.itemAttack1AnimationState, AxCrazyAnimation.ITEMATTACK2, f3);
			this.animate(t.itemAttack2AnimationState, AxCrazyAnimation.ITEMATTACK4, f3);
			this.animate(t.itemAttack3AnimationState, AxCrazyAnimation.ITEMATTACK5, f3);
		}
		this.animate(t.angryAnimationState, AxCrazyAnimation.ANGRY, f3);
		this.animate(t.deadAnimationState, AxCrazyAnimation.DEAD, f3);
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

		AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = t.getArmPose();

		boolean flag = abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSED && !(t instanceof AlwaysShowArmIllagerEntity) && !("Sigma".equals(string) || "Bateman".equals(string));
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