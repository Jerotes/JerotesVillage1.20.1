package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.GiantMonsterAnimation;
import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelgiant_monster<T extends GiantMonsterEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "giant_monster"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelgiant_monster(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -7.5F, -9.0F, 16.0F, 15.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(116, 80).addBox(-8.0F, 6.5F, -9.0F, 16.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(128, 53).addBox(-8.0F, -7.5F, -9.0F, 16.0F, 15.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -2.5F, -17.0F));

		PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.267F, -13.2053F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_horn = horns.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-9.0F, -4.4107F, 9.9634F));

		PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(12, 42).addBox(-1.0F, -8.9378F, -0.4643F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 16.5258F, 1.7617F, 0.9599F, 0.0F, 0.0F));

		PartDefinition right_chain_r1 = right_horn.addOrReplaceChild("right_chain_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-1.0F, -3.6176F, -2.0305F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 4.9132F, -10.408F, 0.9599F, 0.0F, 0.0F));

		PartDefinition right_horn_r2 = right_horn.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(2, 42).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.4382F, -6.9044F, 0.9599F, 0.0F, 0.0F));

		PartDefinition right_horn_r3 = right_horn.addOrReplaceChild("right_horn_r3", CubeListBuilder.create().texOffs(28, 42).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1465F, -10.3442F, 0.9599F, 0.0F, 0.0F));

		PartDefinition right_horn_r4 = right_horn.addOrReplaceChild("right_horn_r4", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -11.9379F, 0.5357F, 2.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.2004F, -5.9523F, 0.9599F, 0.0F, 0.0F));

		PartDefinition left_horn = horns.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(9.0F, -4.4107F, 9.9634F));

		PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(12, 42).mirror().addBox(-1.0F, -3.5F, -3.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 11.3296F, -1.2383F, 0.9599F, 0.0F, 0.0F));

		PartDefinition left_chain_r1 = left_horn.addOrReplaceChild("left_chain_r1", CubeListBuilder.create().texOffs(128, 0).mirror().addBox(-1.0F, -3.6176F, -2.0305F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.9132F, -10.408F, 0.9599F, 0.0F, 0.0F));

		PartDefinition left_horn_r2 = left_horn.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(2, 42).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.4382F, -6.9044F, 0.9599F, 0.0F, 0.0F));

		PartDefinition left_horn_r3 = left_horn.addOrReplaceChild("left_horn_r3", CubeListBuilder.create().texOffs(28, 42).mirror().addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.1465F, -10.3442F, 0.9599F, 0.0F, 0.0F));

		PartDefinition left_horn_r4 = left_horn.addOrReplaceChild("left_horn_r4", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-1.0F, -6.5F, -2.0F, 2.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0042F, -8.9523F, 0.9599F, 0.0F, 0.0F));

		PartDefinition hang = horns.addOrReplaceChild("hang", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chain = hang.addOrReplaceChild("chain", CubeListBuilder.create(), PartPose.offset(0.0F, 3.4981F, 5.0911F));

		PartDefinition main_chain_r1 = chain.addOrReplaceChild("main_chain_r1", CubeListBuilder.create().texOffs(137, 0).addBox(-8.0F, -1.1176F, -1.5305F, 16.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.442F, -4.9085F, 0.9599F, 0.0F, 0.0F));

		PartDefinition main_chain_r2 = chain.addOrReplaceChild("main_chain_r2", CubeListBuilder.create().texOffs(137, 3).addBox(-8.0F, -0.0305F, -0.3824F, 16.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.442F, -4.9085F, 2.5307F, 0.0F, 0.0F));

		PartDefinition main_chain_r3 = chain.addOrReplaceChild("main_chain_r3", CubeListBuilder.create().texOffs(137, 0).addBox(-8.0F, 0.0F, -1.5F, 16.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.058F, 6.3415F, 0.1745F, 0.0F, 0.0F));

		PartDefinition main_chain_r4 = chain.addOrReplaceChild("main_chain_r4", CubeListBuilder.create().texOffs(137, 3).addBox(-8.0F, 0.0F, -1.5F, 16.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.058F, 6.3415F, 1.7453F, 0.0F, 0.0F));

		PartDefinition bell = hang.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(128, 11).addBox(-2.0F, -6.3391F, -1.8839F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(130, 22).addBox(-3.0F, -7.3391F, -2.8839F, 6.0F, 2.0F, 6.0F, new CubeDeformation(-0.5F))
				.texOffs(128, 20).addBox(-1.0F, -7.5891F, -0.8839F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(128, 23).addBox(-1.0F, -1.5892F, -0.8839F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(144, 17).addBox(-1.0F, -1.5892F, -0.8839F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(128, 11).addBox(-0.5F, -1.0892F, 0.6161F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(128, 11).addBox(-0.5F, -0.5892F, 0.0161F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
				.texOffs(128, 9).addBox(-0.5F, -0.5892F, -0.7839F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
				.texOffs(128, 9).addBox(-0.5F, -1.0892F, -1.3839F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.4545F, -1.2947F, 2.3562F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -0.5F, -11.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(66, 105).addBox(-8.0F, 2.5F, -11.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(116, 96).addBox(-8.0F, 0.5F, -11.0F, 16.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 4.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(64, 73).addBox(-7.0F, -4.0F, -4.0F, 14.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -16.0F));

		PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(0, 55).addBox(-8.0F, -21.25F, -9.75F, 16.0F, 16.0F, 20.0F, new CubeDeformation(0.05F))
				.texOffs(128, 30).addBox(-8.0F, -21.25F, -11.75F, 16.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 91).addBox(-7.0F, -5.25F, -9.75F, 14.0F, 13.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(180, 92).addBox(-9.0F, -21.25F, -9.95F, 18.0F, 16.0F, 20.0F, new CubeDeformation(1.1F)), PartPose.offsetAndRotation(0.0F, 2.25F, 4.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = rotation.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 8.25F, 6.25F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(52, 60).addBox(0.0F, -4.5F, -3.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(52, 66).addBox(-3.0F, -4.5F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.1375F, -10.8652F, -1.0908F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 59).addBox(-2.0F, -6.5F, -1.5F, 4.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, -4.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(94, 0).addBox(-4.0F, -5.0F, -4.5F, 8.0F, 22.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -8.0F, 14.5F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(94, 31).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(202, 24).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.26F)), PartPose.offset(-1.0F, 17.0F, -0.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(94, 0).mirror().addBox(-4.0F, -5.0F, -4.5F, 8.0F, 22.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -8.0F, 14.5F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(94, 31).mirror().addBox(-4.0F, -1.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(202, 24).mirror().addBox(-4.0F, -1.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(1.0F, 17.0F, -0.5F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(56, 0).addBox(-4.0F, -4.5F, -5.5F, 8.0F, 25.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, -8.5F, -8.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(164, 21).addBox(-4.0F, -1.5F, -5.5F, 8.0F, 17.0F, 11.0F, new CubeDeformation(0.26F))
				.texOffs(56, 36).addBox(-4.0F, -1.5F, -5.5F, 8.0F, 17.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.0F, 17.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-4.0F, -4.5F, -5.5F, 8.0F, 25.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -8.5F, -8.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(56, 36).mirror().addBox(-4.0F, -1.5F, -5.5F, 8.0F, 17.0F, 11.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(164, 21).mirror().addBox(-4.0F, -1.5F, -5.5F, 8.0F, 17.0F, 11.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(1.0F, 17.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.2f;
			this.head.yScale = 1.2f;
			this.head.zScale = 1.2f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()) {
			this.animateWalk(GiantMonsterAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(GiantMonsterAnimation.RUN, f, f2, 1.5f, 1.5f);
		}
		else {
			this.animateWalk(GiantMonsterAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(((GiantMonsterEntity)t).idleAnimationState, GiantMonsterAnimation.IDLE, f3);
		this.animate(((GiantMonsterEntity)t).attack1AnimationState, GiantMonsterAnimation.ATTACK1, f3);
		this.animate(((GiantMonsterEntity)t).attack2AnimationState, GiantMonsterAnimation.ATTACK2, f3);
		this.animate(((GiantMonsterEntity)t).attack3AnimationState, GiantMonsterAnimation.ATTACK3, f3);
		this.animate(((GiantMonsterEntity)t).sitAnimationState, GiantMonsterAnimation.SIT, f3);
		this.animate(((GiantMonsterEntity)t).toSitAnimationState, GiantMonsterAnimation.TOSIT, f3);
		this.animate(((GiantMonsterEntity)t).stopSitAnimationState, GiantMonsterAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
