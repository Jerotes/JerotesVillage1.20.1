package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.BlueShimmerSquidEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelblue_shimmer_squid<T extends BlueShimmerSquidEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "blue_shimmer_squid"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle5;
	private final ModelPart tentacle6;
	private final ModelPart tentacle7;
	private final ModelPart tentacle8;
	private final ModelPart tentacle9;
	private final ModelPart tentacle10;

	public Modelblue_shimmer_squid(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.tentacle1 = body.getChild("tentacle1");
		this.tentacle2 = body.getChild("tentacle2");
		this.tentacle3 = body.getChild("tentacle3");
		this.tentacle4 = body.getChild("tentacle4");
		this.tentacle5 = body.getChild("tentacle5");
		this.tentacle6 = body.getChild("tentacle6");
		this.tentacle7 = body.getChild("tentacle7");
		this.tentacle8 = body.getChild("tentacle8");
		this.tentacle9 = body.getChild("tentacle9");
		this.tentacle10 = body.getChild("tentacle10");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.05F))
				.texOffs(36, 0).addBox(-3.2F, -2.0F, -3.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).mirror().addBox(1.2F, -2.0F, -3.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -13.0F, -4.0F, 10.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition tentacle1 = body.addOrReplaceChild("tentacle1", CubeListBuilder.create(), PartPose.offset(2.5F, 1.0F, 0.0F));

		PartDefinition tentacle1_r1 = tentacle1.addOrReplaceChild("tentacle1_r1", CubeListBuilder.create().texOffs(24, 21).addBox(-0.5F, -8.5F, -0.5F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition tentacle2 = body.addOrReplaceChild("tentacle2", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 1.5F));

		PartDefinition tentacle2_r1 = tentacle2.addOrReplaceChild("tentacle2_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tentacle3 = body.addOrReplaceChild("tentacle3", CubeListBuilder.create(), PartPose.offset(1.0F, 1.0F, -2.5F));

		PartDefinition tentacle3_r1 = tentacle3.addOrReplaceChild("tentacle3_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tentacle4 = body.addOrReplaceChild("tentacle4", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 1.5F));

		PartDefinition tentacle4_r1 = tentacle4.addOrReplaceChild("tentacle4_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tentacle5 = body.addOrReplaceChild("tentacle5", CubeListBuilder.create(), PartPose.offset(-2.5F, 1.0F, 0.0F));

		PartDefinition tentacle5_r1 = tentacle5.addOrReplaceChild("tentacle5_r1", CubeListBuilder.create().texOffs(24, 21).addBox(-0.5F, -8.5F, -0.5F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition tentacle6 = body.addOrReplaceChild("tentacle6", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, -1.5F));

		PartDefinition tentacle6_r1 = tentacle6.addOrReplaceChild("tentacle6_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -2.3562F, 0.0F));

		PartDefinition tentacle7 = body.addOrReplaceChild("tentacle7", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, -2.5F));

		PartDefinition tentacle7_r1 = tentacle7.addOrReplaceChild("tentacle7_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tentacle8 = body.addOrReplaceChild("tentacle8", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, -1.5F));

		PartDefinition tentacle8_r1 = tentacle8.addOrReplaceChild("tentacle8_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -4.5F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition tentacle9 = body.addOrReplaceChild("tentacle9", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, 2.5F));

		PartDefinition tentacle10 = body.addOrReplaceChild("tentacle10", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 2.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		tentacle3.xRot = -f3;
		tentacle3.zRot = -f3/4;
		tentacle7.xRot = -f3;
		tentacle7.zRot = f3/4;

		tentacle9.xRot = f3;
		tentacle9.zRot = -f3/4;
		tentacle10.xRot = f3;
		tentacle10.zRot = f3/4;

		tentacle1.zRot = -f3;
		tentacle5.zRot = f3;

		tentacle6.xRot = -f3/2;
		tentacle6.zRot = f3/2;

		tentacle8.xRot = -f3/2;
		tentacle8.zRot = -f3/2;

		tentacle2.xRot = f3/2;
		tentacle2.zRot = -f3/2;

		tentacle4.xRot = f3/2;
		tentacle4.zRot = f3/2;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
