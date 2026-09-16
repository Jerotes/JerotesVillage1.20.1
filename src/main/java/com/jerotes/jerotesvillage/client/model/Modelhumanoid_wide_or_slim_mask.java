package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Other.ViewerEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelhumanoid_wide_or_slim_mask<T extends ViewerEntity> extends Modelhumanoid_wide_or_slim<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "humanoid_wide_or_slim_mask"), "main");

    public Modelhumanoid_wide_or_slim_mask(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        left_sleeve.addOrReplaceChild("left_sleeve_slim", CubeListBuilder.create(), PartPose.ZERO);
        left_sleeve.addOrReplaceChild("left_sleeve_wide", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        right_sleeve.addOrReplaceChild("right_sleeve_slim", CubeListBuilder.create(), PartPose.ZERO);
        right_sleeve.addOrReplaceChild("right_sleeve_wide", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition left_arm_wide = left_arm.addOrReplaceChild("left_arm_wide", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm_slim = left_arm.addOrReplaceChild("left_arm_slim", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition right_arm_wide = right_arm.addOrReplaceChild("right_arm_wide", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm_slim = right_arm.addOrReplaceChild("right_arm_slim", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat_r1 = hat.addOrReplaceChild("hat_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, -4.75F, -0.9F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }



}
