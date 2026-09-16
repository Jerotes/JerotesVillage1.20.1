package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelvillager_witch_other<T extends Entity> extends VillagerModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "villager_witch_other"), "main");

	public Modelvillager_witch_other(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		hat.addOrReplaceChild("hat_rim", CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		body.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);

		addHead(meshdefinition, head);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static void addHead(MeshDefinition meshDefinition, PartDefinition head) {
		PartDefinition partdefinition = meshDefinition.getRoot();
		PartDefinition hat = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.05F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat_r1 = hat.addOrReplaceChild("hat_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-3.25F, -38.05F, -3.0F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9999F, 24.1769F, -1.9955F, -0.0524F, 0.0F, 0.0262F));

		PartDefinition hat_r2 = hat.addOrReplaceChild("hat_r2", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -42.05F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1866F, 23.7845F, -6.3849F, -0.157F, 0.0027F, 0.0785F));

		PartDefinition hat_r3 = hat.addOrReplaceChild("hat_r3", CubeListBuilder.create().texOffs(0, 31).addBox(0.25F, -44.05F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-7.4547F, 20.5976F, -15.173F, -0.3653F, 0.0191F, 0.1819F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -4.0F, 0.0F));
	}
}