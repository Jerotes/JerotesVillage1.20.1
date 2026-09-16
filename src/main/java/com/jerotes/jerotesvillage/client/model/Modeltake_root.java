package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.TakeRootEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modeltake_root<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "take_root"), "main");
    private final ModelPart root;
    private final ModelPart main;

    public Modeltake_root(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 109.0F, 0.0F));

        PartDefinition spike1 = main.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -191.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition spike_r1 = spike1.addOrReplaceChild("spike_r1", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -96.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike2 = main.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -191.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition spike_r2 = spike2.addOrReplaceChild("spike_r2", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -96.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike3 = main.addOrReplaceChild("spike3", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -191.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition spike_r3 = spike3.addOrReplaceChild("spike_r3", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -96.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike4 = main.addOrReplaceChild("spike4", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -191.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition spike_r4 = spike4.addOrReplaceChild("spike_r4", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -96.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike5 = main.addOrReplaceChild("spike5", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -191.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition spike_r5 = spike5.addOrReplaceChild("spike_r5", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -96.0F, -9.0F, 0.0F, 192.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike6 = main.addOrReplaceChild("spike6", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -154.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition spike_r6 = spike6.addOrReplaceChild("spike_r6", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -59.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike7 = main.addOrReplaceChild("spike7", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -154.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition spike_r7 = spike7.addOrReplaceChild("spike_r7", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -59.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike8 = main.addOrReplaceChild("spike8", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -154.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition spike_r8 = spike8.addOrReplaceChild("spike_r8", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -59.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike9 = main.addOrReplaceChild("spike9", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -154.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition spike_r9 = spike9.addOrReplaceChild("spike_r9", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -59.0F, -9.0F, 0.0F, 155.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition main_2 = main.addOrReplaceChild("main_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -100.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition spike10 = main_2.addOrReplaceChild("spike10", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -157.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 95.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition spike_r10 = spike10.addOrReplaceChild("spike_r10", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -62.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike11 = main_2.addOrReplaceChild("spike11", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -157.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 95.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition spike_r11 = spike11.addOrReplaceChild("spike_r11", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -62.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike12 = main_2.addOrReplaceChild("spike12", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -157.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 95.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition spike_r12 = spike12.addOrReplaceChild("spike_r12", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -62.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike13 = main_2.addOrReplaceChild("spike13", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -157.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 95.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition spike_r13 = spike13.addOrReplaceChild("spike_r13", CubeListBuilder.create().texOffs(0, -18).addBox(0.0F, -62.0F, -9.0F, 0.0F, 158.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -95.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    public void scale(TakeRootEntity entity, float f) {
        float f2 = Mth.clamp(f/5,0,1);
        float f3 = Mth.clamp((-f + 40) / 5,0,1);
        this.main.yScale = f2 * f3;
        this.main.xScale = f2 * f3;
        this.main.zScale = f2 * f3;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

