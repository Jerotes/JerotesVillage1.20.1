package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.TanglerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.TanglerEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeMod;

public class Modeltangler<T extends TanglerEntity> extends Modelspecial_action<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "tangler"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public Modeltangler(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-1.0F, -2.6F, -3.6F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-1.0F, -1.0F, -3.7F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).mirror().addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 48).mirror().addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = f4 * ((float) Math.PI / 180F);
        this.head.xRot = f5 * ((float) Math.PI / 180F);
        if (t.isWantTangle()) {
            this.animateWalk(TanglerAnimation.SHIFT, f, f2, 1.5f, 2.0f);
        }
        else if (t.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
            this.animateWalk(TanglerAnimation.SWIM, f, f2, 1.5f, 2.0f);
        }
        else {
            this.animateWalk(TanglerAnimation.WALK, f, f2, 1.5f, 2.0f);
        }
        if (t.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
            this.animate(((TanglerEntity)t).idleAnimationState, TanglerAnimation.IDLE, f3);
        }
        else {
            this.animate(((TanglerEntity)t).idleAnimationState, TanglerAnimation.WATERIDLE, f3);
        }
        this.animate(((TanglerEntity)t).attackAnimationState, TanglerAnimation.ATTACK, f3);
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.left_sleeve.copyFrom(this.left_arm);
        this.right_sleeve.copyFrom(this.right_arm);
        this.left_pants.copyFrom(this.left_leg);
        this.right_pants.copyFrom(this.right_leg);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
