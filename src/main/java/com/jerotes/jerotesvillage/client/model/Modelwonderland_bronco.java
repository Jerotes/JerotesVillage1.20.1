package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.WonderlandBroncoAnimation;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandBroncoEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class Modelwonderland_bronco<T extends WonderlandBroncoEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wonderland_bronco"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;

	public Modelwonderland_bronco(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 10.0F, 6.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 5.0F));

		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, -9.0F, -15.0F));

		PartDefinition mane = neck.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.01F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -5.0F, -2.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(19, 16).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -0.01F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 16).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -0.01F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition headpiece = head.addOrReplaceChild("headpiece", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 6.0F, 0.1F));

		PartDefinition noseband = head.addOrReplaceChild("noseband", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition left_bit = head.addOrReplaceChild("left_bit", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition right_bit = head.addOrReplaceChild("right_bit", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition left_rein = head.addOrReplaceChild("left_rein", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 3.5F));

		PartDefinition left_rein_r1 = left_rein.addOrReplaceChild("left_rein_r1", CubeListBuilder.create().texOffs(32, 2).addBox(0.0F, -1.5F, -8.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1F, -1.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_rein = head.addOrReplaceChild("right_rein", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 3.5F));

		PartDefinition right_rein_r1 = right_rein.addOrReplaceChild("right_rein_r1", CubeListBuilder.create().texOffs(32, 2).addBox(0.0F, -1.5F, -8.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1F, -1.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, 0.0F, -1.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(4.0F, 9.0F, -9.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(64, 21).mirror().addBox(-3.0F, 0.0F, -1.9F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, 0.0F, -1.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(-4.0F, 9.0F, -9.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(64, 21).addBox(-1.0F, 0.0F, -1.9F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(4.0F, 9.0F, 8.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(64, 21).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(-4.0F, 9.0F, 8.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(64, 21).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.neck.xScale = 1.15f;
			this.neck.yScale = 1.15f;
			this.neck.zScale = 1.15f;
		}
		else {
			this.neck.xScale = 1f;
			this.neck.yScale = 1f;
			this.neck.zScale = 1f;
		}
		this.applyHeadRotation(t, f4, f5, f3);
		if (t.isSaddled() && t.isVehicle()) {
			this.animateWalk(WonderlandBroncoAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(WonderlandBroncoAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, WonderlandBroncoAnimation.IDLE, f3);
		this.animate(t.attackAnimationState, WonderlandBroncoAnimation.ATTACK, f3);
		this.animate(t.angry1AnimationState, WonderlandBroncoAnimation.ANGRY1, f3);
		this.animate(t.angry2AnimationState, WonderlandBroncoAnimation.ANGRY2, f3);
		this.animate(t.jumpAnimationState, WonderlandBroncoAnimation.JUMP, f3);
		this.animate(t.longJumpAnimationState, WonderlandBroncoAnimation.LONGJUMP, f3);
		this.animate(t.sitAnimationState, WonderlandBroncoAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, WonderlandBroncoAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, WonderlandBroncoAnimation.STOPSIT, f3);
		this.animate(t.eatAnimationState, WonderlandBroncoAnimation.EAT, f3);
		this.animate(t.eatGrassAnimationState, WonderlandBroncoAnimation.EATGRASS, f3);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -10.0f, 10.0f);
		f2 = Mth.clamp(f2, -20.0f, 20.0f);
		this.neck.yRot = f * ((float) Math.PI / 180F);
		this.neck.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}