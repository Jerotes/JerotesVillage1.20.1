package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.MalialosaurAnimation;
import com.jerotes.jerotesvillage.entity.Animal.MalialosaurEntity;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class Modelmalialosaur<T extends MalialosaurEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "malialosaur"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart body_middle;
	private final ModelPart body_front;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart body_hind_1;
	private final ModelPart body_hind_2;
	private final ModelPart body_hind_3;
	private final ModelPart tail_1;
	private final ModelPart tail_2;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart right_hind_leg;
	private final ModelPart left_hind_leg;

	public Modelmalialosaur(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.body_middle = this.body.getChild("body_middle");
		this.body_front = this.body_middle.getChild("body_front");
		this.head = this.body_front.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.body_hind_1 = this.body.getChild("body_hind_1");
		this.body_hind_2 = this.body_hind_1.getChild("body_hind_2");
		this.body_hind_3 = this.body_hind_2.getChild("body_hind_3");
		this.tail_1 = this.body_hind_3.getChild("tail_1");
		this.tail_2 = this.tail_1.getChild("tail_2");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 3.75F, -3.0F));

		PartDefinition body_middle = body.addOrReplaceChild("body_middle", CubeListBuilder.create().texOffs(0, 48).addBox(-8.0F, -2.0F, -21.0F, 16.0F, 15.0F, 23.0F, new CubeDeformation(0.025F))
				.texOffs(186, 108).addBox(-8.0F, -2.0F, -17.0F, 16.0F, 15.0F, 19.0F, new CubeDeformation(0.525F)), PartPose.offset(0.0F, -0.25F, 0.0F));

		PartDefinition body_front = body_middle.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, -1.725F, -10.0F, 11.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.275F, -21.0F));

		PartDefinition head = body_front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 10.0F, 13.0F, new CubeDeformation(0.0125F))
				.texOffs(46, 0).addBox(-4.0F, -2.0F, -19.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(46, 18).addBox(-4.0F, 3.75F, -19.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.475F, -10.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(46, 29).addBox(-4.0F, 1.75F, -10.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(-0.125F))
				.texOffs(86, 0).addBox(-4.0F, 2.25F, -10.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.75F, -9.0F));

		PartDefinition body_hind_1 = body.addOrReplaceChild("body_hind_1", CubeListBuilder.create().texOffs(190, 142).addBox(-8.5F, -2.5F, 0.0F, 17.0F, 17.0F, 16.0F, new CubeDeformation(0.55F))
				.texOffs(0, 86).addBox(-8.5F, -2.5F, 0.0F, 17.0F, 17.0F, 19.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.25F, 0.0F));

		PartDefinition body_hind_2 = body_hind_1.addOrReplaceChild("body_hind_2", CubeListBuilder.create().texOffs(0, 122).addBox(-7.5F, -1.7F, -4.0F, 15.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.8F, 19.0F));

		PartDefinition body_hind_3 = body_hind_2.addOrReplaceChild("body_hind_3", CubeListBuilder.create().texOffs(172, 0).addBox(-5.5F, -2.5F, -5.0F, 11.0F, 11.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 12.0F));

		PartDefinition tail_1 = body_hind_3.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(194, 42).addBox(-4.0F, -2.5F, -2.0F, 8.0F, 9.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(188, 74).addBox(-3.0F, -2.5F, -2.0F, 6.0F, 6.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 21.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 219).mirror().addBox(-1.5F, -4.5F, -2.0F, 7.0F, 18.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(-11, 245).mirror().addBox(-1.5F, 13.4F, -5.0F, 7.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.5F, 10.5F, -20.5F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 219).addBox(-5.5F, -4.5F, -2.0F, 7.0F, 18.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(-11, 245).addBox(-5.5F, 13.4F, -5.0F, 7.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 10.5F, -20.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(30, 217).mirror().addBox(-2.0F, -4.5F, -7.0F, 7.0F, 18.0F, 9.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(18, 244).mirror().addBox(-2.0F, 13.4F, -10.0F, 7.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 10.5F, 25.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(30, 217).addBox(-5.0F, -4.5F, -7.0F, 7.0F, 18.0F, 9.0F, new CubeDeformation(0.01F))
				.texOffs(18, 244).addBox(-5.0F, 13.4F, -10.0F, 7.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 10.5F, 25.5F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	private float currentTailYaw = 0f;
	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.3f;
			this.head.yScale = 1.3f;
			this.head.zScale = 1.3f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}

		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F) * (0.5f + 0.5f * (1 - t.waterAnimProgress / 40f));

		if (!t.isInSittingPose()) {
			float targetTailYaw = -f4 * 1.2f * ((float) Math.PI / 180F);
			currentTailYaw += (targetTailYaw - currentTailYaw) * 0.1f;
		}
		else {
			currentTailYaw = 0;
		}
		body_hind_2.yRot += Mth.clamp(currentTailYaw * 0.2f, -1.0f, 1.0f);
		body_hind_3.yRot += Mth.clamp(currentTailYaw * 0.6f, -1.0f, 1.0f);
		tail_1.yRot += Mth.clamp(currentTailYaw * 0.3f, -1.0f, 1.0f);
		tail_2.yRot += Mth.clamp(currentTailYaw * 0.3f, -1.0f, 1.0f);

		KeyframeAnimations.animate(this, MalialosaurAnimation.LAND, 0L, (1 - t.waterAnimProgress / 40f), new Vector3f());
		KeyframeAnimations.animate(this, MalialosaurAnimation.WATER, 0L, (t.waterAnimProgress / 40f), new Vector3f());

		//水
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(MalialosaurAnimation.SWIMRIDE, f, f2 * t.waterAnimProgress / 40, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(MalialosaurAnimation.SWIM, f, f2 * t.waterAnimProgress / 40, 2.0f, 2.0f);
		}
		//陆地
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()){
			this.animateWalk(MalialosaurAnimation.RUSHRIDE, f, f2 * (1 - t.waterAnimProgress / 40f), 2.0f, 2.0f);
		}
		if (t.isAggressive()) {
			this.animateWalk(MalialosaurAnimation.RUSH, f, f2 * (1 - t.waterAnimProgress / 40f), 2.0f, 2.0f);
		}
		else {
			this.animateWalk(MalialosaurAnimation.WALK, f, f2 * (1 - t.waterAnimProgress / 40f), 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, MalialosaurAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, MalialosaurAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, MalialosaurAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, MalialosaurAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, MalialosaurAnimation.ATTACK4, f3);
		this.animate(t.sitAnimationState, MalialosaurAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, MalialosaurAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, MalialosaurAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}