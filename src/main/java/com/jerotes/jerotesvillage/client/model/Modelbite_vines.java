package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelbite_vines<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "bite_vines"), "main");
    private final ModelPart root;
    private final ModelPart base;
    private final ModelPart upperJaw;
    private final ModelPart lowerJaw;

    public Modelbite_vines(ModelPart root) {
        this.root = root;
        this.base = root.getChild("base");
        this.upperJaw = root.getChild("upper_jaw");
        this.lowerJaw = root.getChild("lower_jaw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(-5.0F, 12.0F, -5.0F));

        PartDefinition base1 = base.addOrReplaceChild("base1", CubeListBuilder.create(), PartPose.offset(5.0F, 8.0F, 5.0F));

        PartDefinition base_r1 = base1.addOrReplaceChild("base_r1", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition base2 = base.addOrReplaceChild("base2", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 8.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition base_r2 = base2.addOrReplaceChild("base_r2", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition base3 = base.addOrReplaceChild("base3", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 8.0F, 5.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition base_r3 = base3.addOrReplaceChild("base_r3", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition base4 = base.addOrReplaceChild("base4", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 8.0F, 5.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition base_r4 = base4.addOrReplaceChild("base_r4", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition upper_jaw = partdefinition.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -5.0F, -2.0F, 7.0F, 19.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 19.0F, -4.0F, 3.1416F, 3.1416F, 0.0F));

        PartDefinition lower_jaw = partdefinition.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -5.0F, -2.0F, 7.0F, 19.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5F, 19.0F, 4.0F, 3.1416F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = f * 2.0f;
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        f6 = 1.0f - f6 * f6 * f6;
        float f7 = (f + Mth.sin(f * 2.7f)) * 0.6f * 12.0f;
        this.upperJaw.zRot = 3.1415927f * 2 - f6 * 0.35f * 3.1415927f;
        this.lowerJaw.zRot = 3.1415927f * 2 + f6 * 0.35f * 3.1415927f;
        this.lowerJaw.y = this.upperJaw.y = 24.0f - f7;
        this.base.y = this.upperJaw.y - 6;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

