package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Part.DuneshocklerPart;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelduneshockler_body<T extends DuneshocklerPart> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "duneshockler_body"), "main");
	private final ModelPart root;

	public Modelduneshockler_body(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-12.2143F, -4.9258F, -12.5F, 25.0F, 25.0F, 25.0F, new CubeDeformation(0.25F))
				.texOffs(0, 100).addBox(-12.2143F, -4.9258F, -12.5F, 25.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(168, 0).addBox(-2.7143F, -8.9258F, -17.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(168, 13).addBox(-2.7143F, -8.9258F, -2.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(168, 26).addBox(-2.7143F, -8.9258F, 12.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(68, 150).addBox(-16.7143F, -7.9258F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(68, 164).addBox(12.2857F, -7.9258F, -2.5F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(100, 140).addBox(-1.7143F, -7.9258F, -15.0F, 4.0F, 7.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.2857F, -9.0742F, 0.0F));

		PartDefinition body_r1 = main.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 150).addBox(-2.0F, -3.5F, -15.0F, 4.0F, 7.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2857F, -3.4258F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition body_r2 = main.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(100, 70).addBox(0.0F, -7.0F, -17.0F, 0.0F, 36.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.2143F, 7.0742F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition body_r3 = main.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(100, 0).addBox(0.0F, -7.0F, -17.0F, 0.0F, 36.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.7857F, 7.0742F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition body_r4 = main.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(0, 50).addBox(-12.5F, -12.5F, -12.5F, 25.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2857F, 7.5742F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}