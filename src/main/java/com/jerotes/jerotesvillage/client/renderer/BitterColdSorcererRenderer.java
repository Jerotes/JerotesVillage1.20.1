package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbitter_cold_sorcerer;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.BitterColdSorcererEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class BitterColdSorcererRenderer extends HumanoidMobRenderer<BitterColdSorcererEntity, Modelbitter_cold_sorcerer<BitterColdSorcererEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_sorcerer.png");
    private static final ResourceLocation HEOUPO_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_sorcerer_by_heoupo.png");
    public BitterColdSorcererRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelbitter_cold_sorcerer(context.bakeLayer(Modelbitter_cold_sorcerer.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(BitterColdSorcererEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(BitterColdSorcererEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake_2.png");
        }
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("Frozen".equals(string)) {
            return HEOUPO_LOCATION;
        }
        return LOCATION;
    }
}
