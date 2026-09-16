package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.TeleportStoneLayer;
import com.jerotes.jerotesvillage.client.model.Modelillager;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.TeleporterEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class TeleporterRenderer extends HumanoidMobRenderer<TeleporterEntity, Modelillager<TeleporterEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/teleporter.png");
    public TeleporterRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelillager(context.bakeLayer(Modelillager.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new TeleportStoneLayer(this, context.getItemInHandRenderer()));
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(TeleporterEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(TeleporterEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake.png");
        }
        return LOCATION;
    }
}
