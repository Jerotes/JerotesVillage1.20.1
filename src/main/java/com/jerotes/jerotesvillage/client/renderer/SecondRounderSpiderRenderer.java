package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SpiderRandomColorLayer;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSpiderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;

public class SecondRounderSpiderRenderer extends SpiderRenderer<SecondRounderSpiderEntity> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_spider.png");
    private static final ResourceLocation WICKED_RIDICULE_CALLS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wicked_ridicule_calls_second_rounder_spider_eye.png");
    private static final ResourceLocation PUSH_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_spider_push.png");

    public SecondRounderSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SPIDER);
        this.shadowRadius *= 1.2f;
        this.addLayer(new SpiderRandomColorLayer<>(this, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER))));
        this.addLayer(new WickedRidiculeCallsEyesLayer(this, WICKED_RIDICULE_CALLS_LOCATION));
    }

    @Override
    protected void scale(SecondRounderSpiderEntity spider, PoseStack poseStack, float f) {
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(SecondRounderSpiderEntity spider) {
        if (spider.getPushTick() > 0) {
            return PUSH_LOCATION;
        }
        return LOCATION;
    }
}