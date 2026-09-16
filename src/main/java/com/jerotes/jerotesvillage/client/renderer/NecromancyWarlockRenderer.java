package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelnecromancy_warlock;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.NecromancyWarlockEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class NecromancyWarlockRenderer extends HumanoidMobRenderer<NecromancyWarlockEntity, Modelnecromancy_warlock<NecromancyWarlockEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/necromancy_warlock.png");
    private static final ResourceLocation ILLAGER_WITHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/necromancy_warlock_by_illager_wither.png");
    public NecromancyWarlockRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelnecromancy_warlock(context.bakeLayer(Modelnecromancy_warlock.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(NecromancyWarlockEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(NecromancyWarlockEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancyWarlockEntity t) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake_2.png");
        }
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("Master".equals(string)) {
            return ILLAGER_WITHER_LOCATION;
        }
        return LOCATION;
    }
}
