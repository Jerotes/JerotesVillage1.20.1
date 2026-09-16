package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modeltorch_of_firm_ice<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "torch_of_firm_ice"), "main");
    private final ModelPart root;
    public final ModelPart medium;
    public final ModelPart head;

    public Modeltorch_of_firm_ice(ModelPart root) {
        this.root = root;
        this.medium = root.getChild("medium");
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 82).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition medium = partdefinition.addOrReplaceChild("medium", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, -2.0833F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(-2.0F, -14.0833F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-4.0F, -16.0833F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-3.0F, -18.0833F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-5.0F, -20.0833F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-4.0F, -24.0833F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0833F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition ice = head.addOrReplaceChild("ice", CubeListBuilder.create().texOffs(36, -12).addBox(4.0F, -32.0F, -6.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(36, -12).addBox(-4.0F, -32.0F, -6.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-6.0F, -32.0F, 4.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-6.0F, -32.0F, -4.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition ice_r1 = ice.addOrReplaceChild("ice_r1", CubeListBuilder.create().texOffs(36, -8).addBox(0.0F, -2.0F, -6.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(36, 4).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

