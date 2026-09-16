package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelominous_gear<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "ominous_gear"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart top;
    private final ModelPart bottom;

    public Modelominous_gear(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.top = body.getChild("top");
        this.bottom = body.getChild("bottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(-16, 0).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(-16, 16).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.top.yRot = -f3 * 2f;
        this.bottom.yRot = f3 * 2f;
        this.bottom.xScale *= 0.75f;
        this.bottom.yScale *= 0.75f;
        this.bottom.zScale *= 0.75f;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

