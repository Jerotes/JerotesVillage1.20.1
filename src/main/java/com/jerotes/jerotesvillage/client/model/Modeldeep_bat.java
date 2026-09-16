package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DeepBatAnimation;
import com.jerotes.jerotesvillage.entity.Animal.DeepBatEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Modeldeep_bat extends AgeableHierarchicalModel<DeepBatEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "deep_bat"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWingTip;
    private final ModelPart leftWingTip;
    private final ModelPart feet;

    public Modeldeep_bat(ModelPart modelPart) {
        super(0.5f, 24.0f, RenderType::entityCutout);
        this.root = modelPart;
        this.body = modelPart.getChild("body");
        this.head = modelPart.getChild("head");
        this.rightWing = this.body.getChild("right_wing");
        this.rightWingTip = this.rightWing.getChild("right_wing_tip");
        this.leftWing = this.body.getChild("left_wing");
        this.leftWingTip = this.leftWing.getChild("left_wing_tip");
        this.feet = this.body.getChild("feet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, 0.0f, -1.0f, 3.0f, 5.0f, 2.0f), PartPose.offset(0.0f, 17.0f, 0.0f));
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-2.0f, -3.0f, -1.0f, 4.0f, 3.0f, 2.0f), PartPose.offset(0.0f, 17.0f, 0.0f));
        partDefinition3.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(1, 15).addBox(-2.5f, -4.0f, 0.0f, 3.0f, 5.0f, 0.0f), PartPose.offset(-1.5f, -2.0f, 0.0f));
        partDefinition3.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(8, 15).addBox(-0.1f, -3.0f, 0.0f, 3.0f, 5.0f, 0.0f), PartPose.offset(1.1f, -3.0f, 0.0f));
        PartDefinition partDefinition4 = partDefinition2.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(12, 0).addBox(-2.0f, -2.0f, 0.0f, 2.0f, 7.0f, 0.0f), PartPose.offset(-1.5f, 0.0f, 0.0f));
        partDefinition4.addOrReplaceChild("right_wing_tip", CubeListBuilder.create().texOffs(16, 0).addBox(-6.0f, -2.0f, 0.0f, 6.0f, 8.0f, 0.0f), PartPose.offset(-2.0f, 0.0f, 0.0f));
        PartDefinition partDefinition5 = partDefinition2.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(12, 7).addBox(0.0f, -2.0f, 0.0f, 2.0f, 7.0f, 0.0f), PartPose.offset(1.5f, 0.0f, 0.0f));
        partDefinition5.addOrReplaceChild("left_wing_tip", CubeListBuilder.create().texOffs(16, 8).addBox(0.0f, -2.0f, 0.0f, 6.0f, 8.0f, 0.0f), PartPose.offset(2.0f, 0.0f, 0.0f));
        partDefinition2.addOrReplaceChild("feet", CubeListBuilder.create().texOffs(16, 16).addBox(-1.5f, 0.0f, 0.0f, 3.0f, 2.0f, 0.0f), PartPose.offset(0.0f, 5.0f, 0.0f));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(DeepBatEntity bat, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (bat.isResting()) {
            this.applyHeadRotation(f4);
        }
        this.animate(bat.flyAnimationState, DeepBatAnimation.BAT_FLYING, f3, 1.0f);
        this.animate(bat.restAnimationState, DeepBatAnimation.BAT_RESTING, f3, 1.0f);
    }

    private void applyHeadRotation(float f) {
        this.head.yRot = f * ((float) Math.PI / 180F);
    }
}

