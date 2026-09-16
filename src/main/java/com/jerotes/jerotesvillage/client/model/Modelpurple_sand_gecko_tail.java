package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PurpleSandGeckoTailAnimation;
import com.jerotes.jerotesvillage.entity.Other.Other.PurpleSandGeckoTailEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpurple_sand_gecko_tail<T extends PurpleSandGeckoTailEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "purple_sand_gecko_tail"), "main");
    private final ModelPart root;

    public Modelpurple_sand_gecko_tail(ModelPart root) {
        super(0.5f, 24.0f);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 20).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 25).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 23).addBox(0.0F, -0.5F, 6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }


    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(t.heyAnimationState, PurpleSandGeckoTailAnimation.HEY, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
