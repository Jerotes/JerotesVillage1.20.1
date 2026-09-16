package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.GavilerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.GavilerEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;


public class Modelgaviler<T extends GavilerEntity> extends Modelillager<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "gaviler"), "main");
	private final ModelPart head;
	private final ModelPart arms;

	public Modelgaviler(ModelPart root) {
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.3F))
				.texOffs(96, 0).addBox(-4.0F, 0.5F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(92, 48).addBox(-8.0F, -2.5F, -3.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(40, 38).addBox(-4.0F, 1.45F, -3.35F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 22).addBox(-8.0F, -2.55F, -3.35F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 0.3F, -0.7505F, 0.0F, 0.0F));

		PartDefinition left_shoulder = arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(92, 48).mirror().addBox(4.0F, -23.0F, -2.7F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.5F, -0.3F));

		PartDefinition left_shoulder_r1 = left_shoulder.addOrReplaceChild("left_shoulder_r1", CubeListBuilder.create().texOffs(92, 45).mirror().addBox(0.0F, -2.5F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.75F, -17.6F, 1.6F, 0.7418F, 0.0F, -0.0873F));

		PartDefinition book = body.addOrReplaceChild("book", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 8.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cover_left = book.addOrReplaceChild("cover_left", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 1.0F));

		PartDefinition cover_left_rotation_r1 = cover_left.addOrReplaceChild("cover_left_rotation_r1", CubeListBuilder.create().texOffs(80, 18).addBox(-3.0F, -5.0F, -0.01F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(114, 28).addBox(-3.0F, -5.0F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cover_right = book.addOrReplaceChild("cover_right", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 1.0F));

		PartDefinition cover_right_rotation_r1 = cover_right.addOrReplaceChild("cover_right_rotation_r1", CubeListBuilder.create().texOffs(64, 18).addBox(-3.0F, -5.0F, -0.01F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(98, 28).addBox(-3.0F, -5.0F, 0.0F, 6.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition book_spine = book.addOrReplaceChild("book_spine", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition spine_rotation_r1 = book_spine.addOrReplaceChild("spine_rotation_r1", CubeListBuilder.create().texOffs(110, 28).addBox(-1.0F, -5.0F, 0.01F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(76, 18).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition pages_left = book.addOrReplaceChild("pages_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.4F));

		PartDefinition page_left_rotation_r1 = pages_left.addOrReplaceChild("page_left_rotation_r1", CubeListBuilder.create().texOffs(76, 28).addBox(-2.5F, -4.0F, -0.5F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition pages_right = book.addOrReplaceChild("pages_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.4F));

		PartDefinition page_right_rotation_r1 = pages_right.addOrReplaceChild("page_right_rotation_r1", CubeListBuilder.create().texOffs(64, 28).addBox(-2.5F, -4.0F, -0.5F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, 3.1416F, -3.1416F));

		PartDefinition flipping_page_left = book.addOrReplaceChild("flipping_page_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.1F));

		PartDefinition flip_left_rotation_r1 = flipping_page_left.addOrReplaceChild("flip_left_rotation_r1", CubeListBuilder.create().texOffs(88, 28).addBox(-2.5F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition flipping_page_right = book.addOrReplaceChild("flipping_page_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.1F));

		PartDefinition flip_right_rotation_r1 = flipping_page_right.addOrReplaceChild("flip_right_rotation_r1", CubeListBuilder.create().texOffs(88, 28).addBox(-2.5F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, 3.1416F, -3.1416F));

		PartDefinition main_body = body.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(64, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(92, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(92, 45).mirror().addBox(0.0F, -2.5F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.5F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition ball = left_arm.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(108, 15).mirror().addBox(0.0F, -1.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 10.0F, 0.0F));

		PartDefinition ball_r1 = ball.addOrReplaceChild("ball_r1", CubeListBuilder.create().texOffs(108, 15).mirror().addBox(0.0F, -6.0F, -2.0F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition ball_main = ball.addOrReplaceChild("ball_main", CubeListBuilder.create().texOffs(92, 18).mirror().addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(92, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(GavilerAnimation.WALK, f, f2, 2.0f, 2.0f);
		this.animate(t.idleAnimationState, GavilerAnimation.IDLE, f3);
		this.animate(t.shootAnimationState, GavilerAnimation.SHOOT, f3);
		this.animate(t.upAnimationState, GavilerAnimation.UP, f3);
		this.animate(t.downAnimationState, GavilerAnimation.DOWN, f3);
		this.animate(t.deadAnimationState, GavilerAnimation.DEAD, f3);

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