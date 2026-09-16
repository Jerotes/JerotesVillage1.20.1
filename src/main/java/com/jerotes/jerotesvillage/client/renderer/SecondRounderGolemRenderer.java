package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelsecond_rounder_golem;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SecondRounderGolemRenderer extends MobRenderer<OldSecondRounderGolemEntity, Modelsecond_rounder_golem<OldSecondRounderGolemEntity>> {
    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem.png");
    private static final ResourceLocation SWORD_GOLEM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_sword.png");
    private static final ResourceLocation SWORD_BACK_GOLEM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_sword_back.png");
    private static final ResourceLocation SWORD_FIRE_CLOAK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_fire_cloak.png");
    private static final ResourceLocation SWORD_POISON_CLOAK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_poison_cloak.png");

    public SecondRounderGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), 1.7f);
        this.addLayer(new SecondRounderGolemCrackinessLayer(this));
        this.addLayer(new SecondRounderGolemBreathLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION))));
        this.addLayer(new SecondRounderGolemBackSwordLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), SWORD_BACK_GOLEM_LOCATION));
        this.addLayer(new SecondRounderGolemSwordLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), SWORD_GOLEM_LOCATION));
        this.addLayer(new SecondRounderGolemCloakLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), SWORD_FIRE_CLOAK_LOCATION));
        this.addLayer(new SecondRounderGolemCloakLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), SWORD_POISON_CLOAK_LOCATION));
    }

    @Override
    protected void scale(OldSecondRounderGolemEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.7f, 1.7f, 1.7f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(OldSecondRounderGolemEntity t) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(OldSecondRounderGolemEntity golem) {
        return GOLEM_LOCATION;
    }
}
