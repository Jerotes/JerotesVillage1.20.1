package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PurpleSandGeckoAnimation;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandGeckoEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpurple_sand_gecko<T extends PurpleSandGeckoEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "purple_sand_gecko"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public Modelpurple_sand_gecko(ModelPart root) {
        super(0.5f, 24.0f);
        this.root = root;
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.125F))
                .texOffs(18, 11).addBox(-2.5F, -1.0F, -6.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.125F))
                .texOffs(44, 14).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 19.0F, -6.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 20).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 25).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 23).addBox(0.0F, -0.5F, 6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-2.5F, 19.0F, 3.0F));

        PartDefinition leg1_r1 = right_hind_leg.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -2.5F, -0.5F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition leg1_r2 = right_hind_leg.addOrReplaceChild("leg1_r2", CubeListBuilder.create().texOffs(32, 0).addBox(-3.5F, -1.8F, -5.3F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.5F, 4.5F, -3.0F, 0.0F, 1.5708F, 1.0036F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(2.5F, 19.0F, 3.0F));

        PartDefinition leg2_r1 = left_hind_leg.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -2.5F, -0.5F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition leg2_r2 = left_hind_leg.addOrReplaceChild("leg2_r2", CubeListBuilder.create().texOffs(32, 0).addBox(-3.5F, -1.8F, 5.3F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, 4.5F, -3.0F, 0.0F, 1.5708F, -1.0036F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-2.5F, 19.0F, -4.0F));

        PartDefinition leg3_r1 = right_front_leg.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -2.5F, -0.5F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition leg3_r2 = right_front_leg.addOrReplaceChild("leg3_r2", CubeListBuilder.create().texOffs(32, 0).addBox(3.5F, -1.8F, -5.3F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.5F, 4.5F, 4.0F, 0.0F, 1.5708F, 1.0036F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(2.5F, 19.0F, -4.0F));

        PartDefinition leg4_r1 = left_front_leg.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -2.5F, -0.5F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition leg4_r2 = left_front_leg.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(32, 0).addBox(3.5F, -1.8F, 5.3F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, 4.5F, 4.0F, 0.0F, 1.5708F, -1.0036F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (t.isBaby()) {
            this.head.xScale = 1.4f;
            this.head.yScale = 1.4f;
            this.head.zScale = 1.4f;
        }
        else {
            this.head.xScale = 1f;
            this.head.yScale = 1f;
            this.head.zScale = 1f;
        }
        this.head.yRot = f4 * ((float)Math.PI / 180F);
        this.head.xRot = f5 * ((float)Math.PI / 180F);
        this.animateWalk(PurpleSandGeckoAnimation.WALK, f, f2, 2.0f, 2.0f);
        this.animate(t.wallAnimationState, PurpleSandGeckoAnimation.WALL, f3);
        this.animate(t.hurtAnimationState, PurpleSandGeckoAnimation.HURT, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
