package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelhorned_armor extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "horned_armor"), "main");
	public final ModelPart head;

	public Modelhorned_armor(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F))
				.texOffs(0, 16).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
				.texOffs(32, 16).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.5F))
				.texOffs(56, 0).addBox(1.25F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(24, 0).addBox(2.8F, -1.5F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.5F))
				.texOffs(24, 16).addBox(2.8F, -2.5F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(56, 16).addBox(1.25F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -5.35F, -1.25F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(56, 0).mirror().addBox(-3.25F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(24, 0).mirror().addBox(-4.8F, -1.5F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 16).mirror().addBox(-3.25F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 16).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(24, 16).mirror().addBox(-4.8F, -2.5F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -5.35F, -1.25F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	public Modelhorned_armor getArmor(LivingEntity entity){
		return this;
	}
}