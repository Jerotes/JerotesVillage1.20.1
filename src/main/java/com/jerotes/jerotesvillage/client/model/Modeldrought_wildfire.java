package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DroughtWildfireAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtWildfireEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class Modeldrought_wildfire<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "drought_wildfire"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart body_1;
	private final ModelPart body_2;
	private final ModelPart body_3;
	private final ModelPart shield;
	private final ModelPart shield1;
	private final ModelPart shield1_in;
	private final ModelPart shield_three;
	private final ModelPart shield3;
	private final ModelPart shield3_in;
	private final ModelPart shield_two;
	private final ModelPart shield2;
	private final ModelPart shield2_in;
	private final ModelPart shield_one;
	private final ModelPart shield4;
	private final ModelPart shield4_in;

	public Modeldrought_wildfire(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.body_1 = this.body.getChild("body_1");
		this.body_2 = this.body.getChild("body_2");
		this.body_3 = this.body.getChild("body_3");
		this.shield = root.getChild("shield");
		this.shield1 = this.shield.getChild("shield1");
		this.shield1_in = this.shield1.getChild("shield1_in");
		this.shield_three = this.shield.getChild("shield_three");
		this.shield3 = this.shield_three.getChild("shield3");
		this.shield3_in = this.shield3.getChild("shield3_in");
		this.shield_two = this.shield_three.getChild("shield_two");
		this.shield2 = this.shield_two.getChild("shield2");
		this.shield2_in = this.shield2.getChild("shield2_in");
		this.shield_one = this.shield_two.getChild("shield_one");
		this.shield4 = this.shield_one.getChild("shield4");
		this.shield4_in = this.shield4.getChild("shield4_in");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 47).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(0, 55).addBox(-8.0F, -12.0F, 1.0F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.5F))
				.texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition body_3 = body.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition shield = partdefinition.addOrReplaceChild("shield", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition shield1 = shield.addOrReplaceChild("shield1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -9.0F));

		PartDefinition shield1_in = shield1.addOrReplaceChild("shield1_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 20).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 1.2544F, 0.0F));

		PartDefinition shield_three = shield.addOrReplaceChild("shield_three", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition shield3 = shield_three.addOrReplaceChild("shield3", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 9.0F));

		PartDefinition shield3_in = shield3.addOrReplaceChild("shield3_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 20).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition shield_two = shield_three.addOrReplaceChild("shield_two", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shield2 = shield_two.addOrReplaceChild("shield2", CubeListBuilder.create(), PartPose.offset(9.0F, -16.0F, 0.0F));

		PartDefinition shield2_in = shield2.addOrReplaceChild("shield2_in", CubeListBuilder.create().texOffs(32, 20).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(32, 0).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition shield_one = shield_two.addOrReplaceChild("shield_one", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shield4 = shield_one.addOrReplaceChild("shield4", CubeListBuilder.create(), PartPose.offset(-9.0F, -16.0F, 0.0F));

		PartDefinition shield4_in = shield4.addOrReplaceChild("shield4_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 20).addBox(-6.0F, -7.2544F, -0.5F, 12.0F, 18.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity instanceof DroughtWildfireEntity wildfire) {
			this.shield1.visible = wildfire.getShieldCount() >= 4;
			this.shield3.visible = wildfire.getShieldCount() >= 3;
			this.shield2.visible = wildfire.getShieldCount() >= 2;
			this.shield4.visible = wildfire.getShieldCount() >= 1;

			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.head.xRot = headPitch * ((float) Math.PI / 180F);

			this.animateWalk(DroughtWildfireAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
			if (wildfire.isAggressive()) {
				if (wildfire.getShieldCount() >= 4) {
					this.animateIdle2(wildfire, wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE2, ageInTicks);
				}
				else if (wildfire.getShieldCount() >= 3) {
					this.animateIdle2(wildfire, wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE3, ageInTicks);
				}
				else if (wildfire.getShieldCount() >= 2) {
					this.animateIdle2(wildfire, wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE4, ageInTicks);
				}
				else if (wildfire.getShieldCount() >= 1) {
					this.animateIdle2(wildfire, wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE5, ageInTicks);
				}
				else {
					this.animateIdle2(wildfire, wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE5, ageInTicks);
				}
			}
			else {
				this.animate(wildfire.idle1AnimationState, DroughtWildfireAnimation.IDLE1, ageInTicks);
			}
			//this.animate(wildfire.idle2AnimationState, DroughtWildfireAnimation.SCALE, ageInTicks);
			KeyframeAnimations.animate(this, DroughtWildfireAnimation.IDLEBASE, 0L, 1, new Vector3f());
			this.animate(wildfire.shootAnimationState, DroughtWildfireAnimation.SHOOT, ageInTicks);
			this.animate(wildfire.flameAnimationState, DroughtWildfireAnimation.WIND, ageInTicks);
			this.animate(wildfire.attack1AnimationState, DroughtWildfireAnimation.ATTACK1, ageInTicks);
			this.animate(wildfire.attack2AnimationState, DroughtWildfireAnimation.ATTACK2, ageInTicks);
			this.animate(wildfire.droughtBlazeAnimationState, DroughtWildfireAnimation.BLAZE, ageInTicks);
			this.animate(wildfire.deadAnimationState, DroughtWildfireAnimation.DEAD, ageInTicks);
		}
	}

	protected void animateIdle2(DroughtWildfireEntity wildfire, AnimationState animationState, AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation,
					(long) (Math.min(1, (wildfire.rollAnimProgress % 10) / 10f) * animation.lengthInSeconds() * 1000),
					Math.min(1, wildfire.idleAnimProgress / 10f),
					new Vector3f());
		});
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}