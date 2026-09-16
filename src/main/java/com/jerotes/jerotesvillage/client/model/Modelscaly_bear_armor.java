package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.ScalyBearAnimation;
import com.jerotes.jerotesvillage.entity.Animal.ScalyBearEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelscaly_bear_armor<T extends ScalyBearEntity> extends Modelscaly_bear<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "scaly_bear_armor"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public Modelscaly_bear_armor(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(78, 0).addBox(-5.5F, -6.5F, -7.0F, 11.0F, 11.0F, 9.0F, new CubeDeformation(-0.2F))
				.texOffs(47, 0).addBox(-3.5F, -1.5F, -8.0F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -13.0F));

		PartDefinition ear = head.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -3.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 27).addBox(-7.0F, -0.5F, -7.25F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 7.75F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition front_body = body.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -7.5F, 16.0F, 12.0F, 15.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -6.5F, 0.25F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(104, 20).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 9.0F, 8.0F, new CubeDeformation(0.35F)), PartPose.offset(-4.5F, 7.0F, 6.0F));

		PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(104, 37).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(104, 20).mirror().addBox(-2.0F, -3.0F, -2.0F, 4.0F, 9.0F, 8.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(4.5F, 7.0F, 6.0F));

		PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(104, 37).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(78, 20).addBox(-5.0F, -2.0F, -4.0F, 5.0F, 9.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(-3.5F, 6.0F, -6.0F));

		PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(78, 37).addBox(-5.0F, -2.0F, -4.0F, 5.0F, 12.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(78, 20).mirror().addBox(0.0F, -2.0F, -4.0F, 5.0F, 9.0F, 8.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(3.5F, 6.0F, -6.0F));

		PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(78, 37).mirror().addBox(0.0F, -2.0F, -4.0F, 5.0F, 12.0F, 8.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
