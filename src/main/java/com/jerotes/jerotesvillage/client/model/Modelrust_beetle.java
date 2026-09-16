package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RustBeetleAnimation;
import com.jerotes.jerotesvillage.entity.Animal.RustBeetleEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelrust_beetle<T extends RustBeetleEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rust_beetle"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart tentacle;
	private final ModelPart teeth;
	private final ModelPart neck;
	private final ModelPart body;
	private final ModelPart left_elytra;
	private final ModelPart right_elytra;
	private final ModelPart right_hind_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_middle_leg;
	private final ModelPart left_middle_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_front_leg;

	public Modelrust_beetle(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
		this.tentacle = head.getChild("tentacle");
		this.teeth = head.getChild("teeth");
		this.neck = root.getChild("neck");
		this.body = root.getChild("body");
		this.left_elytra = body.getChild("left_elytra");
		this.right_elytra = body.getChild("right_elytra");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_middle_leg = root.getChild("right_middle_leg");
		this.left_middle_leg = root.getChild("left_middle_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_front_leg = root.getChild("left_front_leg");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -8.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-3.05F, -2.0F, -7.75F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).mirror().addBox(3.05F, -2.0F, -7.75F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, -4.0F));

		PartDefinition tentacle = head.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(31, 51).addBox(-5.0F, 0.0F, -15.25F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.5F));

		PartDefinition teeth = head.addOrReplaceChild("teeth", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -7.25F));

		PartDefinition teeth_r1 = teeth.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(18, 0).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -2.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 10).addBox(-4.0F, -3.0F, -7.5F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-3.0F, -2.0F, -1.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.75F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 18).addBox(-5.0F, -4.0F, -3.25F, 10.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.5F, 3.0F));

		PartDefinition left_elytra = body.addOrReplaceChild("left_elytra", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-2.5F, 0.0F, -0.25F, 5.0F, 8.0F, 15.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.5F, -4.0F, -3.0F));

		PartDefinition right_elytra = body.addOrReplaceChild("right_elytra", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, 0.0F, -0.25F, 5.0F, 8.0F, 15.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.5F, -4.0F, -3.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 17.0F, 2.0F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(40, 8).addBox(-11.0F, -2.0F, 1.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.75F, 0.0F, 0.4363F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 17.0F, 2.0F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(40, 8).mirror().addBox(-1.0F, -2.0F, 1.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.75F, 0.0F, -0.4363F, 0.0F));

		PartDefinition right_middle_leg = partdefinition.addOrReplaceChild("right_middle_leg", CubeListBuilder.create().texOffs(44, 0).addBox(-9.0F, -1.0F, -0.75F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 17.0F, -1.0F));

		PartDefinition left_middle_leg = partdefinition.addOrReplaceChild("left_middle_leg", CubeListBuilder.create().texOffs(44, 0).mirror().addBox(-1.0F, -1.0F, -0.75F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 17.0F, -1.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 17.0F, -4.0F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(44, 0).addBox(-9.0F, -1.0F, 1.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.75F, 0.0F, -0.3054F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 17.0F, -4.0F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(44, 0).mirror().addBox(-1.0F, -1.0F, 1.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.75F, 0.0F, 0.3054F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.animateWalk(RustBeetleAnimation.WALK, f, f2, 1.5f, 2.0f);
		this.animate(((RustBeetleEntity)t).idleAnimationState, RustBeetleAnimation.IDLE, f3);
		this.animate(((RustBeetleEntity)t).attackAnimationState, RustBeetleAnimation.ATTACK, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
