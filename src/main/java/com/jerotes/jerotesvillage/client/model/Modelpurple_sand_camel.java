package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.PurpleSandCamelAnimation;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandCamelEntity;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelpurple_sand_camel<T extends PurpleSandCamelEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "purple_sand_camel"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public Modelpurple_sand_camel(ModelPart modelPart) {
        super(0.5f, 24.0f);
        this.root = modelPart;
        ModelPart modelPart2 = modelPart.getChild("body");
        this.head = modelPart2.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-7.5F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 10.5F));

        PartDefinition hump = body.addOrReplaceChild("hump", CubeListBuilder.create().texOffs(74, 0).addBox(-4.5F, -7.5F, -5.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(-0.5F))
                .texOffs(74, 0).addBox(-4.5F, -3.0F, 8.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 0).addBox(-4.5F, -3.0F, -5.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 0).addBox(-4.5F, -7.5F, 8.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -12.0F, -17.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(122, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 3.5F, 0.0873F, 0.0F, 0.0F));

        PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(74, 64).addBox(-4.5F, -19.5F, -22.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(-0.4F))
                .texOffs(74, 64).addBox(-4.5F, -15.0F, -8.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.1F))
                .texOffs(74, 64).addBox(-4.5F, -15.0F, -22.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.1F))
                .texOffs(74, 64).addBox(-4.5F, -19.5F, -8.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(-0.4F))
                .texOffs(92, 114).addBox(-3.5F, -22.0F, -22.5F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.1F))
                .texOffs(92, 114).addBox(-3.5F, -22.0F, -8.5F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.1F))
                .texOffs(0, 89).addBox(-7.5F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_front_leg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-2.6F, 2.0F, -2.0F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(4.9F, -3.0F, -20.5F));

        PartDefinition left_front_foot = left_front_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.6F, 0.0F, -2.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition right_front_leg = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-2.4F, 2.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(-4.9F, -3.0F, -20.0F));

        PartDefinition right_front_foot = right_front_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.4F, 0.0F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition left_hind_leg = body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(94, 16).mirror().addBox(-2.6F, 2.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(4.9F, -3.0F, 0.0F));

        PartDefinition left_hind_foot = left_hind_leg.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(58, 16).mirror().addBox(-2.6F, 0.0F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition right_hind_leg = body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(94, 16).addBox(-2.4F, 2.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(-4.9F, -3.0F, 0.0F));

        PartDefinition right_hind_foot = right_hind_leg.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(58, 16).addBox(-2.4F, 0.0F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(50, 0).addBox(-2.5F, -21.0F, -21.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(21, 0).addBox(-3.5F, -21.0F, -15.0F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(60, 24).addBox(-3.5F, -7.0F, -15.0F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -19.5F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(45, 0).addBox(-0.5F, 0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -21.0F, -9.5F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(67, 0).addBox(-2.5F, 0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -21.0F, -9.5F));

        PartDefinition bridle = head.addOrReplaceChild("bridle", CubeListBuilder.create().texOffs(21, 64).addBox(-3.5F, -21.0F, -15.0F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.1F))
                .texOffs(60, 87).addBox(-3.5F, -7.0F, -15.0F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.1F))
                .texOffs(50, 64).addBox(-2.5F, -21.0F, -21.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(74, 70).addBox(2.5F, -19.0F, -18.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mirror = bridle.addOrReplaceChild("mirror", CubeListBuilder.create().texOffs(74, 70).mirror().addBox(-3.5F, -42.0F, -27.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 23.0F, 9.0F));

        PartDefinition reins = head.addOrReplaceChild("reins", CubeListBuilder.create().texOffs(98, 42).mirror().addBox(3.51F, -18.0F, -17.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(98, 42).mirror().addBox(-3.51F, -18.0F, -17.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(84, 57).mirror().addBox(-3.5F, -18.0F, -2.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
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
        this.applyHeadRotation(t, f4, f5, f3);
        this.animateWalk(CamelAnimation.CAMEL_WALK, f, f2, 2.0f, 2.5f);
        this.animate(t.sitAnimationState, CamelAnimation.CAMEL_SIT, f3, 1.0f);
        this.animate(t.sitPoseAnimationState, CamelAnimation.CAMEL_SIT_POSE, f3, 1.0f);
        this.animate(t.sitUpAnimationState, CamelAnimation.CAMEL_STANDUP, f3, 1.0f);
        this.animate(t.idleAnimationState, CamelAnimation.CAMEL_IDLE, f3, 1.0f);
        this.animate(t.dashAnimationState, CamelAnimation.CAMEL_DASH, f3, 1.0f);
        this.animate(t.tameSitAnimationState, PurpleSandCamelAnimation.SIT, f3);
        this.animate(t.toSitAnimationState, PurpleSandCamelAnimation.TOSIT, f3);
        this.animate(t.stopSitAnimationState, PurpleSandCamelAnimation.STOPSIT, f3);
        this.animate(t.eatAnimationState, PurpleSandCamelAnimation.EAT, f3);
        this.animate(t.attack1AnimationState, PurpleSandCamelAnimation.ATTACK1, f3);
        this.animate(t.attack2AnimationState, PurpleSandCamelAnimation.ATTACK2, f3);
    }

    private void applyHeadRotation(T t, float f, float f2, float f3) {
        f = Mth.clamp(f, -30.0f, 30.0f);
        f2 = Mth.clamp(f2, -25.0f, 45.0f);
        if (t.getJumpCooldown() > 0) {
            float f4 = f3 - (float)t.tickCount;
            float f5 = 45.0f * ((float)t.getJumpCooldown() - f4) / 55.0f;
            f2 = Mth.clamp(f2 + f5, -25.0f, 70.0f);
        }
        this.head.yRot = f * ((float) Math.PI / 180F);
        this.head.xRot = f2 * ((float) Math.PI / 180F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

