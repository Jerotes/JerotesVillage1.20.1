package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.AdventurerArmorLayer;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modeladventurer;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.AdventurerEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AdventurerRenderer extends MobRenderer<AdventurerEntity, Modeladventurer<AdventurerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/adventurer/adventurer.png");
    private static final ResourceLocation ARMOR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/adventurer/adventurer_armor.png");
    public AdventurerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modeladventurer(context.bakeLayer(Modeladventurer.LAYER_LOCATION)), 0.5f);
        this.addLayer(new AdventurerArmorLayer<>(this, new Modeladventurer(context.bakeLayer(Modeladventurer.LAYER_LOCATION)), ARMOR_LOCATION));
        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }
    @Override
    public void render(AdventurerEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float blockAnim = t.getBlockAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.blockAnimProgress = Mth.lerp(smoothFactor, t.blockAnimProgress, blockAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    protected void scale(AdventurerEntity entity, PoseStack poseStack, float f) {
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if (!("Commander".equals(string) || "指挥官".equals(string))) {
            poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        }
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(AdventurerEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(AdventurerEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/adventurer/ax_crazy_fake.png");
        }
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("La Verdadera Destreza".equals(string) || "Destreza".equals(string) || "Vaardigheid".equals(string) || "几何剑".equals(string) || "至高之术".equals(string)) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/adventurer/adventurer_ex_by_heoupo.png");
        }
        if ("Commander".equals(string) || "指挥官".equals(string)) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/adventurer/commander.png");
        }
        return LOCATION;
    }
}
