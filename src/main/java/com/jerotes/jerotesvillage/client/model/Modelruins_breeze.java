package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RuinsBreezeAnimation;
import com.jerotes.jerotesvillage.entity.Monster.RuinsBreeze;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelruins_breeze<T extends RuinsBreeze> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ruins_breeze"), "main");
	public static final ModelLayerLocation BREEZE_EYES_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ruins_breeze_eyes"), "main");
	public static final ModelLayerLocation BREEZE_WIND_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ruins_breeze_wind"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wind_body;
	private final ModelPart head;
    private final ModelPart wind_top;
	private final ModelPart wind_mid;
	private final ModelPart wind_bottom;
	private final ModelPart rods;

	public Modelruins_breeze(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root;
		this.body = root.getChild("body");
		this.rods = this.body.getChild("rods");
		this.head = this.body.getChild("head");
		this.wind_body = root.getChild("wind_body");
		this.wind_bottom = wind_body.getChild("wind_bottom");
		this.wind_mid = this.wind_bottom.getChild("wind_mid");
		this.wind_top = this.wind_mid.getChild("wind_top");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition rods = body.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
		rods.addOrReplaceChild("rod_1", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0f, 0.0f, -3.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(2.5981f, -3.0f, 1.5f, -2.7489f, -1.0472f, 3.1416f));
		rods.addOrReplaceChild("rod_2", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0f, 0.0f, -3.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5981f, -3.0f, 1.5f, -2.7489f, 1.0472f, 3.1416f));
		rods.addOrReplaceChild("rod_3", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0f, 0.0f, -3.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, -3.0f, 0.3927f, 0.0f, 0.0f));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 4.0f, 0.0f));
		PartDefinition wind_body = partDefinition.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition windBottom = wind_body.addOrReplaceChild("wind_bottom", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));
		PartDefinition windMid = windBottom.addOrReplaceChild("wind_mid", CubeListBuilder.create(), PartPose.offset(0.0f, -7.0f, 0.0f));
		PartDefinition wind_top = windMid.addOrReplaceChild("wind_top", CubeListBuilder.create(), PartPose.offset(0.0f, -6.0f, 0.0f));
		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	public static LayerDefinition createEyesLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition rods = body.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0f, 4.0f, 0.0f));
		head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(4, 24).addBox(-5.0f, -5.0f, -4.2f, 10.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(7, 16).addBox(-4.0f, -2.0f, -4.0f, 8.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition wind_body = partDefinition.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition windBottom = wind_body.addOrReplaceChild("wind_bottom", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));
		PartDefinition windMid = windBottom.addOrReplaceChild("wind_mid", CubeListBuilder.create(), PartPose.offset(0.0f, -7.0f, 0.0f));
		PartDefinition wind_top = windMid.addOrReplaceChild("wind_top", CubeListBuilder.create(), PartPose.offset(0.0f, -6.0f, 0.0f));
		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	public static LayerDefinition createWindBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition rods = body.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
		body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0f, 4.0f, 0.0f));
		PartDefinition wind_body = partDefinition.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition windBottom = wind_body.addOrReplaceChild("wind_bottom", CubeListBuilder.create().texOffs(1, 83).addBox(-2.5f, -7.0f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 24.0f, 0.0f));
		PartDefinition windMid = windBottom.addOrReplaceChild("wind_mid", CubeListBuilder.create().texOffs(74, 28).addBox(-6.0f, -6.0f, -6.0f, 12.0f, 6.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(78, 32).addBox(-4.0f, -6.0f, -4.0f, 8.0f, 6.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(49, 71).addBox(-2.5f, -6.0f, -2.5f, 5.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -7.0f, 0.0f));
		PartDefinition wind_top = windMid.addOrReplaceChild("wind_top", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0f, -8.0f, -9.0f, 18.0f, 8.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(6, 6).addBox(-6.0f, -8.0f, -6.0f, 12.0f, 8.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(105, 57).addBox(-2.5f, -8.0f, -2.5f, 5.0f, 8.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -6.0f, 0.0f));
		return LayerDefinition.create(meshDefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(RuinsBreezeAnimation.SLIDE, f, f2, 2.0f, 2.0f);
		float f6 = f3 * 3.1415927f * -0.1f;
		this.wind_top.x = Mth.cos(f6) * 1.0f * 0.6f;
		this.wind_top.z = Mth.sin(f6) * 1.0f * 0.6f;
		this.wind_mid.x = Mth.sin(f6) * 0.5f * 0.8f;
		this.wind_mid.z = Mth.cos(f6) * 0.8f;
		this.wind_bottom.x = Mth.cos(f6) * -0.25f * 1.0f;
		this.wind_bottom.z = Mth.sin(f6) * -0.25f * 1.0f;
		this.head.y = 4.0f + Mth.cos(f6) / 4.0f;
		this.rods.yRot = f3 * 3.1415927f * 0.1f;
		this.animate(t.shootAnimationState, RuinsBreezeAnimation.SHOOT, f3);
		this.animate(t.longJumpAnimationState, RuinsBreezeAnimation.JUMP, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public ModelPart windTop() {
		return this.wind_top;
	}

	public ModelPart windMiddle() {
		return this.wind_mid;
	}

	public ModelPart windBottom() {
		return this.wind_bottom;
	}
}