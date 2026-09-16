package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.BrightMouseEntity;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modellittle_velvet<T extends BrightMouseEntity> extends QuadrupedModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "little_velvet"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;

	public Modellittle_velvet(ModelPart root) {
		super(root, true, 16.0f, 2.25f, 2.4f, 2.0f, 24);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftFrontLeg = root.getChild("right_front_leg");
		this.rightFrontLeg = root.getChild("left_front_leg");
		this.leftHindLeg = root.getChild("right_hind_leg");
		this.rightHindLeg = root.getChild("left_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 23).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(0, 9).addBox(-1.5F, -0.02F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(-3.0F, -3.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 14).addBox(1.0F, -3.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -9.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(34, 11).addBox(-3.5F, -6.5F, -4.5F, 7.0F, 13.0F, 8.0F, new CubeDeformation(-0.15F))
				.texOffs(18, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 0).addBox(-1.0F, -0.75F, -1.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 2.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition front_left_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, 18.1F, -5.0F));

		PartDefinition front_right_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, 18.1F, -5.0F));

		PartDefinition back_left_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 24).addBox(0.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, 19.0F, 3.0F));

		PartDefinition back_right_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 24).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, 19.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		this.leftHindLeg.xRot = Mth.cos(f * 0.6662f) * f2;
		this.rightHindLeg.xRot = Mth.cos(f * 0.6662f + 3.1415927f + 0.3f) * f2;
		this.leftFrontLeg.xRot = Mth.cos(f * 0.6662f + 3.1415927f + 0.3f) * f2;
		this.rightFrontLeg.xRot = Mth.cos(f * 0.6662f) * f2;

	}

}
