package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.SunCrabAnimation;
import com.jerotes.jerotesvillage.entity.Animal.SunCrabEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelsun_crab<T extends SunCrabEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "sun_crab"), "main");
    private final ModelPart root;
    private final ModelPart eye;
    private final ModelPart right_eye;
    private final ModelPart left_eye;


    public Modelsun_crab(ModelPart root) {
        super(0.5f, 24.0f);
        this.root = root;
        this.eye = root.getChild("eye");
        this.right_eye = eye.getChild("right_eye");
        this.left_eye = eye.getChild("left_eye");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition eye = partdefinition.addOrReplaceChild("eye", CubeListBuilder.create(), PartPose.offset(2.5F, 17.0868F, 0.0F));

        PartDefinition right_eye = eye.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.offset(0.0F, -0.2386F, 1.6226F));

        PartDefinition right_eye_r1 = right_eye.addOrReplaceChild("right_eye_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.7386F, 0.1302F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_eye_r2 = right_eye.addOrReplaceChild("right_eye_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.2614F, -0.1302F, -0.1719F, 0.0302F, 0.1719F));

        PartDefinition left_eye = eye.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.offset(0.0F, -0.2386F, -1.6226F));

        PartDefinition left_eye_r1 = left_eye.addOrReplaceChild("left_eye_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.7386F, -0.1302F, 0.1745F, 0.0F, 0.0F));

        PartDefinition left_eye_r2 = left_eye.addOrReplaceChild("left_eye_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.2614F, 0.1302F, 0.1719F, -0.0302F, 0.1719F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 20.0F, 0.0F));

        PartDefinition right_crab = partdefinition.addOrReplaceChild("right_crab", CubeListBuilder.create(), PartPose.offset(2.5F, 21.0F, -4.0F));

        PartDefinition right_crab_r1 = right_crab.addOrReplaceChild("right_crab_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, -2.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, -1.5F, -0.5236F, -0.7854F, 0.0F));

        PartDefinition right_crab2 = right_crab.addOrReplaceChild("right_crab2", CubeListBuilder.create(), PartPose.offset(2.5F, -2.0F, -2.0F));

        PartDefinition right_crab_r2 = right_crab2.addOrReplaceChild("right_crab_r2", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.5F, 0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 14).addBox(-1.0F, -4.5F, -3.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 1.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition right_crab3 = right_crab2.addOrReplaceChild("right_crab3", CubeListBuilder.create(), PartPose.offset(1.0F, 0.25F, 2.25F));

        PartDefinition right_crab_r3 = right_crab3.addOrReplaceChild("right_crab_r3", CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, -4.0F, 0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 2.75F, -1.25F, -0.1309F, 0.0F, 0.0F));

        PartDefinition left_crab = partdefinition.addOrReplaceChild("left_crab", CubeListBuilder.create(), PartPose.offset(2.5F, 21.0F, 4.0F));

        PartDefinition left_crab_r1 = left_crab.addOrReplaceChild("left_crab_r1", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-1.0F, -2.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 0.0F, 1.5F, 0.5236F, 0.7854F, 0.0F));

        PartDefinition left_crab2 = left_crab.addOrReplaceChild("left_crab2", CubeListBuilder.create(), PartPose.offset(2.5F, -2.0F, 2.0F));

        PartDefinition left_crab_r2 = left_crab2.addOrReplaceChild("left_crab_r2", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-1.0F, -1.5F, -4.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(0, 14).mirror().addBox(-1.0F, -4.5F, -1.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition left_crab3 = left_crab2.addOrReplaceChild("left_crab3", CubeListBuilder.create(), PartPose.offset(1.0F, 0.25F, -2.25F));

        PartDefinition left_crab_r3 = left_crab3.addOrReplaceChild("left_crab_r3", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-1.0F, -4.0F, -4.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.75F, 1.25F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(-1.5F, 21.6F, -4.0F));

        PartDefinition leg_r1 = leg1.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -2.3527F, -1.5773F, 2.1817F, 0.0F, 0.0F));

        PartDefinition leg1_1 = leg1.addOrReplaceChild("leg1_1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.1F, -3.75F));

        PartDefinition leg_r2 = leg1_1.addOrReplaceChild("leg_r2", CubeListBuilder.create().texOffs(0, 49).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2476F, -1.875F, -2.0944F, 0.0F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(-1.5F, 21.5F, 4.0F));

        PartDefinition leg_r3 = leg2.addOrReplaceChild("leg_r3", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.2527F, 1.5773F, 0.9599F, 0.0F, 0.0F));

        PartDefinition leg2_2 = leg2.addOrReplaceChild("leg2_2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 3.75F));

        PartDefinition leg_r4 = leg2_2.addOrReplaceChild("leg_r4", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.2476F, 1.875F, -1.0472F, 0.0F, 0.0F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(-0.5F, 21.5F, -4.0F));

        PartDefinition leg_r5 = leg3.addOrReplaceChild("leg_r5", CubeListBuilder.create().texOffs(0, 42).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -2.2527F, -1.5773F, 2.1817F, 0.0F, 0.0F));

        PartDefinition leg3_3 = leg3.addOrReplaceChild("leg3_3", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -3.75F));

        PartDefinition leg_r6 = leg3_3.addOrReplaceChild("leg_r6", CubeListBuilder.create().texOffs(0, 49).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2476F, -1.875F, -2.0944F, 0.0F, 0.0F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(-0.5F, 21.5F, 4.0F));

        PartDefinition leg_r7 = leg4.addOrReplaceChild("leg_r7", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.2527F, 1.5773F, 0.9599F, 0.0F, 0.0F));

        PartDefinition leg4_4 = leg4.addOrReplaceChild("leg4_4", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 3.75F));

        PartDefinition leg_r8 = leg4_4.addOrReplaceChild("leg_r8", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.2476F, 1.875F, -1.0472F, 0.0F, 0.0F));

        PartDefinition leg5 = partdefinition.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offset(0.5F, 21.5F, -4.0F));

        PartDefinition leg_r9 = leg5.addOrReplaceChild("leg_r9", CubeListBuilder.create().texOffs(0, 42).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -2.2527F, -1.5773F, 2.1817F, 0.0F, 0.0F));

        PartDefinition leg5_5 = leg5.addOrReplaceChild("leg5_5", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -3.75F));

        PartDefinition leg_r10 = leg5_5.addOrReplaceChild("leg_r10", CubeListBuilder.create().texOffs(0, 49).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2476F, -1.875F, -2.0944F, 0.0F, 0.0F));

        PartDefinition leg6 = partdefinition.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offset(0.5F, 21.5F, 4.0F));

        PartDefinition leg_r11 = leg6.addOrReplaceChild("leg_r11", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.2527F, 1.5773F, 0.9599F, 0.0F, 0.0F));

        PartDefinition leg6_6 = leg6.addOrReplaceChild("leg6_6", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 3.75F));

        PartDefinition leg_r12 = leg6_6.addOrReplaceChild("leg_r12", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.2476F, 1.875F, -1.0472F, 0.0F, 0.0F));

        PartDefinition leg7 = partdefinition.addOrReplaceChild("leg7", CubeListBuilder.create(), PartPose.offset(1.5F, 21.5F, -4.0F));

        PartDefinition leg_r13 = leg7.addOrReplaceChild("leg_r13", CubeListBuilder.create().texOffs(0, 42).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -2.2527F, -1.5773F, 2.1817F, 0.0F, 0.0F));

        PartDefinition leg7_7 = leg7.addOrReplaceChild("leg7_7", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -3.75F));

        PartDefinition leg_r14 = leg7_7.addOrReplaceChild("leg_r14", CubeListBuilder.create().texOffs(0, 49).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2476F, -1.875F, -2.0944F, 0.0F, 0.0F));

        PartDefinition leg8 = partdefinition.addOrReplaceChild("leg8", CubeListBuilder.create(), PartPose.offset(1.5F, 21.5F, 4.0F));

        PartDefinition leg_r15 = leg8.addOrReplaceChild("leg_r15", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.2527F, 1.5773F, 0.9599F, 0.0F, 0.0F));

        PartDefinition leg8_8 = leg8.addOrReplaceChild("leg8_8", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 3.75F));

        PartDefinition leg_r16 = leg8_8.addOrReplaceChild("leg_r16", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.2476F, 1.875F, -1.0472F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 64);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.right_eye.xRot = f5 * 0.017453292f;
        this.right_eye.yRot = f4 * 0.017453292f;
        this.left_eye.xRot = f5 * 0.017453292f;
        this.left_eye.yRot = f4 * 0.017453292f;
        this.animateWalk(SunCrabAnimation.WALK, f, f2, 2.0f, 2.0f);
        this.animate(((SunCrabEntity)t).idleAnimationState, SunCrabAnimation.IDLE, f3);
        this.animate(((SunCrabEntity)t).attackAnimationState, SunCrabAnimation.ATTACK, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

}
