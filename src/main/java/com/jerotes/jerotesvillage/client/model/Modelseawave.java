package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SeawaveAnimation;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
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

public class Modelseawave<T extends LivingEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "seawave"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart body_1;
	private final ModelPart body_2;
	private final ModelPart body_3;
	private final ModelPart trident;
	private final ModelPart trident1;
	private final ModelPart trident1_in;
	private final ModelPart trident_three;
	private final ModelPart trident3;
	private final ModelPart trident3_in;
	private final ModelPart trident_two;
	private final ModelPart trident2;
	private final ModelPart trident2_in;
	private final ModelPart trident_one;
	private final ModelPart trident4;
	private final ModelPart trident4_in;

	public Modelseawave(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.body_1 = this.body.getChild("body_1");
		this.body_2 = this.body.getChild("body_2");
		this.body_3 = this.body.getChild("body_3");
		this.trident = root.getChild("trident");
		this.trident1 = this.trident.getChild("trident1");
		this.trident1_in = this.trident1.getChild("trident1_in");
		this.trident_three = this.trident.getChild("trident_three");
		this.trident3 = this.trident_three.getChild("trident3");
		this.trident3_in = this.trident3.getChild("trident3_in");
		this.trident_two = this.trident_three.getChild("trident_two");
		this.trident2 = this.trident_two.getChild("trident2");
		this.trident2_in = this.trident2.getChild("trident2_in");
		this.trident_one = this.trident_two.getChild("trident_one");
		this.trident4 = this.trident_one.getChild("trident4");
		this.trident4_in = this.trident4.getChild("trident4_in");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 47).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(0, 54).addBox(-6.0F, -9.0F, 1.0F, 12.0F, 9.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5F, 0.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5F, 0.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition body_3 = body.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5F, 0.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(16, 35).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition trident = partdefinition.addOrReplaceChild("trident", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition trident1 = trident.addOrReplaceChild("trident1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -9.0F));

		PartDefinition trident1_in = trident1.addOrReplaceChild("trident1_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 0).addBox(-1.0F, -17.2544F, -0.5F, 2.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 3).addBox(-2.0F, -6.2544F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 0.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 13.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 7.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(38, 0).addBox(-3.5F, -12.2544F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.05F))
				.texOffs(38, 12).addBox(-2.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.2544F, 0.0F));

		PartDefinition stick_r1 = trident1_in.addOrReplaceChild("stick_r1", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, 2.7925F));

		PartDefinition stick_r2 = trident1_in.addOrReplaceChild("stick_r2", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition stick_r3 = trident1_in.addOrReplaceChild("stick_r3", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, -2.7925F));

		PartDefinition stick_r4 = trident1_in.addOrReplaceChild("stick_r4", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition stick_r5 = trident1_in.addOrReplaceChild("stick_r5", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -17.2544F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition trident_three = trident.addOrReplaceChild("trident_three", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition trident3 = trident_three.addOrReplaceChild("trident3", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 9.0F));

		PartDefinition trident3_in = trident3.addOrReplaceChild("trident3_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 0).addBox(-1.0F, -17.2544F, -0.5F, 2.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-3.5F, -12.2544F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.05F))
				.texOffs(38, 12).addBox(-2.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 3).addBox(-2.0F, -6.2544F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 0.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 13.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 7.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition stick_r6 = trident3_in.addOrReplaceChild("stick_r6", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, -2.7925F));

		PartDefinition stick_r7 = trident3_in.addOrReplaceChild("stick_r7", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, 2.7925F));

		PartDefinition stick_r8 = trident3_in.addOrReplaceChild("stick_r8", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition stick_r9 = trident3_in.addOrReplaceChild("stick_r9", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition stick_r10 = trident3_in.addOrReplaceChild("stick_r10", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -17.2544F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition trident_two = trident_three.addOrReplaceChild("trident_two", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition trident2 = trident_two.addOrReplaceChild("trident2", CubeListBuilder.create(), PartPose.offset(9.0F, -16.0F, 0.0F));

		PartDefinition trident2_in = trident2.addOrReplaceChild("trident2_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 0).addBox(-1.0F, -17.2544F, -0.5F, 2.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 3).addBox(-2.0F, -6.2544F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 0.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 13.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 7.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(38, 0).addBox(-3.5F, -12.2544F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.05F))
				.texOffs(38, 12).addBox(-2.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition stick_r11 = trident2_in.addOrReplaceChild("stick_r11", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, 2.7925F));

		PartDefinition stick_r12 = trident2_in.addOrReplaceChild("stick_r12", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition stick_r13 = trident2_in.addOrReplaceChild("stick_r13", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, -2.7925F));

		PartDefinition stick_r14 = trident2_in.addOrReplaceChild("stick_r14", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition stick_r15 = trident2_in.addOrReplaceChild("stick_r15", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -17.2544F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition trident_one = trident_two.addOrReplaceChild("trident_one", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition trident4 = trident_one.addOrReplaceChild("trident4", CubeListBuilder.create(), PartPose.offset(-9.0F, -16.0F, 0.0F));

		PartDefinition trident4_in = trident4.addOrReplaceChild("trident4_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 0).addBox(-1.0F, -17.2544F, -0.5F, 2.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-3.5F, -12.2544F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.05F))
				.texOffs(38, 12).addBox(-2.5F, -10.2544F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 3).addBox(-2.0F, -6.2544F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 0.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 13.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(44, 3).addBox(-2.0F, 7.7456F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.2544F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition stick_r16 = trident4_in.addOrReplaceChild("stick_r16", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, -2.7925F));

		PartDefinition stick_r17 = trident4_in.addOrReplaceChild("stick_r17", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(4.6986F, -16.0441F, 0.0F, 0.0F, 0.0F, 2.7925F));

		PartDefinition stick_r18 = trident4_in.addOrReplaceChild("stick_r18", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition stick_r19 = trident4_in.addOrReplaceChild("stick_r19", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -11.7544F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition stick_r20 = trident4_in.addOrReplaceChild("stick_r20", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -17.2544F, 0.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity instanceof SeawaveEntity seawave) {
			this.trident1.visible = seawave.getTridentCount() >= 4;
			this.trident3.visible = seawave.getTridentCount() >= 3;
			this.trident2.visible = seawave.getTridentCount() >= 2;
			this.trident4.visible = seawave.getTridentCount() >= 1;

			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.head.xRot = headPitch * ((float) Math.PI / 180F);

			this.animateWalk(SeawaveAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.0f);
			if (seawave.isAggressive()) {
				if (seawave.getTridentCount() >= 4) {
					this.animateIdle2(seawave, seawave.idle1AnimationState, SeawaveAnimation.IDLE2, ageInTicks);
				}
				else if (seawave.getTridentCount() >= 3) {
					this.animateIdle2(seawave, seawave.idle1AnimationState, SeawaveAnimation.IDLE3, ageInTicks);
				}
				else if (seawave.getTridentCount() >= 2) {
					this.animateIdle2(seawave, seawave.idle1AnimationState, SeawaveAnimation.IDLE4, ageInTicks);
				}
				else if (seawave.getTridentCount() >= 1) {
					this.animateIdle2(seawave, seawave.idle1AnimationState, SeawaveAnimation.IDLE5, ageInTicks);
				}
				else {
					this.animateIdle2(seawave, seawave.idle1AnimationState, SeawaveAnimation.IDLE5, ageInTicks);
				}
			}
			else {
				this.animate(seawave.idle1AnimationState, SeawaveAnimation.IDLE1, ageInTicks);
			}
			//this.animate(seawave.idle2AnimationState, SeawaveAnimation.SCALE, ageInTicks);
			KeyframeAnimations.animate(this, SeawaveAnimation.IDLEBASE, 0L, Math.min(1, seawave.idleAnimProgress / 10f), new Vector3f());
			this.animate(seawave.shootAnimationState, SeawaveAnimation.SHOOT, ageInTicks);
			this.animate(seawave.windAnimationState, SeawaveAnimation.WIND, ageInTicks);
			this.animate(seawave.attack1AnimationState, SeawaveAnimation.ATTACK1, ageInTicks);
			this.animate(seawave.attack2AnimationState, SeawaveAnimation.ATTACK2, ageInTicks);
			this.animate(seawave.bulgeAnimationState, SeawaveAnimation.BULGE, ageInTicks);
			this.animate(seawave.deadAnimationState, SeawaveAnimation.DEAD, ageInTicks);
		}
	}

	protected void animateIdle2(SeawaveEntity seawave, AnimationState animationState, AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation,
					(long) (Math.min(1, (seawave.rollAnimProgress % 10) / 10f) * animation.lengthInSeconds() * 1000),
					Math.min(1, seawave.idleAnimProgress / 10f),
					new Vector3f());
		});
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}