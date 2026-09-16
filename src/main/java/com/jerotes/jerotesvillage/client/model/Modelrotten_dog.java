package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.RottenDogAnimation;
import com.jerotes.jerotesvillage.entity.Animal.RottenDogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelrotten_dog<T extends RottenDogEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "rotten_dog"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_hind_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;

        public Modelrotten_dog(ModelPart root) {
        super(0.5f, 24.0f);
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_hind_leg = root.getChild("right_hind_leg");
        this.left_hind_leg = root.getChild("left_hind_leg");
        this.right_front_leg = root.getChild("right_front_leg");
        this.left_front_leg = root.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-0.5F, -0.02F, -6.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 13.5F, -8.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(22, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -4.0F, -0.5F, 0.3491F, 1.0036F, 0.3491F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(22, 15).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -4.0F, -0.5F, 0.3491F, -1.0036F, -0.3491F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.02F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(1.0F, 2.0F, -3.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 2.0F));

        PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(20, 0).addBox(-4.0F, -1.6531F, -3.6616F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3384F, -3.3469F, 1.5708F, 0.0F, 0.0F));

        PartDefinition mane_rotation_r1 = mane_rotation.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(40, 38).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.8469F, -0.3384F, 0.2618F, 0.0F, 0.0F));

        PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(42, 15).addBox(-3.0F, -1.0F, -2.25F, 6.0F, 7.0F, 5.0F, new CubeDeformation(-0.125F))
                .texOffs(40, 27).addBox(-3.0F, 5.0F, -3.25F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -1.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition tail = body_rotation.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 9.25F, 1.75F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(31, 15).addBox(-1.0F, -0.605F, -1.299F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 0.0F, 0.0F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(10, 31).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.125F)), PartPose.offset(-2.5F, 12.0F, 7.0F));

        PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 1.0F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(10, 31).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offset(2.5F, 12.0F, 7.0F));

        PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(8, 22).mirror().addBox(-1.0F, -0.5F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.5F, 1.0F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-0.75F, -0.5F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0125F)), PartPose.offset(-3.5F, 11.0F, -4.0F));

        PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create(), PartPose.offset(0.5F, 6.0F, 1.0F));

        PartDefinition right_front_foot_r1 = right_front_foot.addOrReplaceChild("right_front_foot_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.25F, -1.5F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0125F)).mirror(false), PartPose.offset(3.5F, 12.0F, -4.0F));

        PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create(), PartPose.offset(-0.5F, 5.0F, 1.0F));

        PartDefinition left_front_foot_r1 = left_front_foot.addOrReplaceChild("left_front_foot_r1", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
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
        if (t.isAggressive()) {
            this.animateWalk(RottenDogAnimation.RUSH, f, f2, 2.0f, 2.0f);
        }
        else {
            this.animateWalk(RottenDogAnimation.WALK, f, f2, 2.0f, 2.0f);
        }
        this.animate(((RottenDogEntity)t).idleAnimationState, RottenDogAnimation.IDLE, f3);
        this.animate(((RottenDogEntity)t).attackAnimationState, RottenDogAnimation.ATTACK, f3);
        this.animate(((RottenDogEntity)t).sitAnimationState, RottenDogAnimation.SIT, f3);
        this.animate(((RottenDogEntity)t).toSitAnimationState, RottenDogAnimation.TOSIT, f3);
        this.animate(((RottenDogEntity)t).stopSitAnimationState, RottenDogAnimation.STOPSIT, f3);
        this.animate(((RottenDogEntity)t).shakeAnimationState, RottenDogAnimation.SHAKE, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;

    public void setColor(float f, float f2, float f3) {
        this.r = f;
        this.g = f2;
        this.b = f3;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int n, int n2, float f, float f2, float f3, float f4) {
        super.renderToBuffer(poseStack, vertexConsumer, n, n2, this.r * f, this.g * f2, this.b * f3, f4);
    }

}

