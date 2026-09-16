package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.NecromancyWarlockAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.NecromancyWarlockEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class Modelnecromancy_warlock<T extends NecromancyWarlockEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "necromancy_warlock"), "main");
	private final ModelPart head;
	private final ModelPart arms;

	public Modelnecromancy_warlock(ModelPart root) {
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition normal = head.addOrReplaceChild("normal", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
		PartDefinition nose = normal.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(96, 32).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(96, 36).addBox(-1.0F, 2.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(110, 32).addBox(-1.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(106, 32).addBox(0.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(102, 32).addBox(0.0F, 1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(114, 32).addBox(-1.0F, 1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-8.0F, -13.0F, 0.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 3.0F, -4.0F));
		hat.addOrReplaceChild("top", CubeListBuilder.create().texOffs(32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, 4.0F));
		hat.addOrReplaceChild("middle_left", CubeListBuilder.create().texOffs(99, 34).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-1.0F)), PartPose.offset(-0.5F, -10.0F, 4.0F));
		hat.addOrReplaceChild("middle_right", CubeListBuilder.create().texOffs(99, 34).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-1.0F)), PartPose.offset(-7.5F, -10.0F, 4.0F));
		hat.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(48, 32).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -9.0F, 4.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(64, 48).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.95F, -1.05F, -0.7505F, 0.0F, 0.0F));
		arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 48).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 21.05F, 1.05F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(NecromancyWarlockAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, NecromancyWarlockAnimation.IDLE, f3);
		this.animate(t.gearAnimationState, NecromancyWarlockAnimation.GEAR, f3);
		this.animate(t.screamAnimationState, NecromancyWarlockAnimation.SCREAM, f3);
		this.animate(t.whisperAnimationState, NecromancyWarlockAnimation.WHISPER, f3);
		this.animate(t.rainAnimationState, NecromancyWarlockAnimation.RAIN, f3);
		this.animate(t.fogAnimationState, NecromancyWarlockAnimation.FOG, f3);
		this.animate(t.summonAnimationState, NecromancyWarlockAnimation.SUMMON, f3);
		this.animate(t.deadAnimationState, NecromancyWarlockAnimation.DEAD, f3);

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