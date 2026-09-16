package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelflying_fireball<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "flying_fireball"), "main");
	private final ModelPart root;
	private final ModelPart main;

	public Modelflying_fireball(ModelPart modelPart) {
		this.root = modelPart;
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition base = main.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offset(4.0F, 3.0F, -4.0F));

		PartDefinition out = main.addOrReplaceChild("out", CubeListBuilder.create().texOffs(64, 0).addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.55F)), PartPose.offset(4.0F, 3.0F, -4.0F));

		PartDefinition fire_right = main.addOrReplaceChild("fire_right", CubeListBuilder.create().texOffs(608, -16).addBox(0.0F, 6.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(576, -16).addBox(0.0F, 5.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(544, -16).addBox(0.0F, 4.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(512, -16).addBox(0.0F, 3.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(480, -16).addBox(0.0F, 2.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(448, -16).addBox(0.0F, 1.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(416, -16).addBox(0.0F, 0.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(384, -16).addBox(0.0F, -0.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(352, -16).addBox(0.0F, -1.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(320, -16).addBox(0.0F, -2.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(288, -16).addBox(0.0F, -3.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(256, -16).addBox(0.0F, -4.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(224, -16).addBox(0.0F, -5.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(192, -16).addBox(0.0F, -6.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(160, -16).addBox(0.0F, -7.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(128, -16).addBox(0.0F, -8.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.0F, 16.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition fire_left = main.addOrReplaceChild("fire_left", CubeListBuilder.create().texOffs(608, -16).mirror().addBox(0.0F, 6.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(576, -16).mirror().addBox(0.0F, 5.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(544, -16).mirror().addBox(0.0F, 4.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(512, -16).mirror().addBox(0.0F, 3.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(480, -16).mirror().addBox(0.0F, 2.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(448, -16).mirror().addBox(0.0F, 1.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(416, -16).mirror().addBox(0.0F, 0.75F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(384, -16).mirror().addBox(0.0F, -0.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(352, -16).mirror().addBox(0.0F, -1.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(320, -16).mirror().addBox(0.0F, -2.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(288, -16).mirror().addBox(0.0F, -3.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(256, -16).mirror().addBox(0.0F, -4.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(224, -16).mirror().addBox(0.0F, -5.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(192, -16).mirror().addBox(0.0F, -6.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(160, -16).mirror().addBox(0.0F, -7.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(128, -16).mirror().addBox(0.0F, -8.25F, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -7.0F, 16.25F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 640, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.main.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.main.xRot = headPitch / (180F / (float) Math.PI);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
