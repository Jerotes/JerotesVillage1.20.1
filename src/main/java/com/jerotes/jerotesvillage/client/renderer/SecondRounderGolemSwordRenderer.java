package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SecondRounderSwordPowerLayer;
import com.jerotes.jerotesvillage.client.model.Modelsecond_rounder_golem;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.SecondRounderGolemSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SecondRounderGolemSwordRenderer extends MobRenderer<SecondRounderGolemSwordEntity, Modelsecond_rounder_golem<SecondRounderGolemSwordEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_sword.png");
    public SecondRounderGolemSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION)), 0f);
        this.addLayer(new SecondRounderSwordPowerLayer(this, new Modelsecond_rounder_golem(context.bakeLayer(Modelsecond_rounder_golem.LAYER_LOCATION))));
    }

    @Override
    protected void scale(SecondRounderGolemSwordEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(2.2f, 2.2f, 2.2f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(SecondRounderGolemSwordEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(SecondRounderGolemSwordEntity entity) {
        return LOCATION;
    }

}