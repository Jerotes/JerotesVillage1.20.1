package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RustBeetleAnimation;
import com.jerotes.jerotesvillage.client.animation.SolishAnimation;
import com.jerotes.jerotesvillage.entity.Animal.Fish.SolishEntity;
import com.jerotes.jerotesvillage.entity.Animal.RustBeetleEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelsolish<T extends SolishEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "solish"), "main");
	private final ModelPart root;
	private final ModelPart body;


	public Modelsolish(ModelPart modelPart) {
		super(0.5f, 24.0f);
		this.root = modelPart;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -5.5F, -7.25F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 90).addBox(-5.0F, -5.5F, -7.25F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.5F))
				.texOffs(0, 0).addBox(-6.0F, -0.5F, -7.25F, 12.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(44, 49).addBox(-0.5F, -9.5F, -0.25F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, -4.25F));

		PartDefinition back_body = body.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(35, 27).addBox(-4.0F, -5.25F, 0.5F, 8.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(72, 49).addBox(-0.5F, 4.0F, 4.5F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(62, 21).addBox(-0.5F, -9.0F, 0.5F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 5.25F));

		PartDefinition tail = back_body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(44, 14).addBox(-3.0F, -3.75F, 0.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 55).addBox(-2.0F, -3.75F, 6.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 11.5F));

		PartDefinition tail_main = tail.addOrReplaceChild("tail_main", CubeListBuilder.create().texOffs(18, 57).addBox(-0.5F, -5.5F, -4.5F, 1.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(58, 49).addBox(-0.5F, 5.5F, -5.5F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-0.5F, -7.5F, -2.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 12.5F, -0.6545F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 38).addBox(-5.0F, -3.0F, -6.5F, 10.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 73).addBox(-5.0F, -3.0F, -6.5F, 10.0F, 10.0F, 7.0F, new CubeDeformation(0.25F))
				.texOffs(37, 0).addBox(-4.0F, -2.0F, -12.5F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(27, 67).addBox(-4.0F, -2.0F, -12.5F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(59, 0).addBox(-4.0F, 4.0F, -9.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.5F, -7.75F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(81, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -9.5F));

		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create(), PartPose.offset(-6.0F, 5.5F, -5.75F));

		PartDefinition right_fin_r1 = right_front_fin.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(26, 49).addBox(-0.5F, -2.5F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1243F, 0.3536F, 1.8412F, -0.8928F, -0.2236F, 0.442F));

		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create(), PartPose.offset(6.0F, 5.5F, -5.75F));

		PartDefinition left_fin_r1 = left_front_fin.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(26, 49).mirror().addBox(-0.5F, -2.5F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.1243F, 0.3536F, 1.8412F, -0.8928F, 0.2236F, -0.442F));

		PartDefinition right_hind_fin = body.addOrReplaceChild("right_hind_fin", CubeListBuilder.create(), PartPose.offset(-6.0F, 6.0F, 6.25F));

		PartDefinition right_fin_r2 = right_hind_fin.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(49, 59).addBox(-0.5F, -2.0F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8255F, 0.3536F, 1.2004F, -0.9593F, -0.062F, 0.6687F));

		PartDefinition left_hind_fin = body.addOrReplaceChild("left_hind_fin", CubeListBuilder.create(), PartPose.offset(6.0F, 6.0F, 6.25F));

		PartDefinition left_fin_r2 = left_hind_fin.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(49, 59).mirror().addBox(-0.5F, -2.0F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.8255F, 0.3536F, 1.2004F, -0.9593F, 0.062F, -0.6687F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.body.yRot = f4 * ((float)Math.PI / 180F);
		this.body.xRot = f5 * ((float)Math.PI / 180F);
		if (((Entity)t).getDeltaMovement().horizontalDistanceSqr() > 1.0E-7) {
			this.body.xRot += -0.05f - 0.05f * Mth.cos(f3 * 0.3f);
		}
		this.animate(((SolishEntity)t).idleAnimationState, RustBeetleAnimation.IDLE, f3);
		this.animateWalk(SolishAnimation.SWIM, f, f2, 1.5f, 2.0f);
		this.animate(((SolishEntity)t).attackAnimationState, SolishAnimation.ATTACK, f3);
	}
}

