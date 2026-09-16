package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TruesightLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SkinEntityBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhag;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.Hag.BaseHagEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class HagRenderer extends HumanoidMobRenderer<BaseHagEntity, Modelhag<BaseHagEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/hag.png");
    private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
    public HagRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelhag(context.bakeLayer(Modelhag.LAYER_LOCATION)), 0.5f);
        this.addLayer(new SkinEntityBodyLayer(this, new Modelhag(context.bakeLayer(Modelhag.LAYER_LOCATION)), "hag"));
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new TruesightLayer<>(this));
    }

    @Override
    protected void scale(BaseHagEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(BaseHagEntity t) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(BaseHagEntity witch) {
        return OtherMainConfig.RandomSkinMobHasUnderTexture ? LOCATION : NULL_LOCATION;
    }

}
