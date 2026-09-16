package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CyclonerRollLayer;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelcycloner;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.CyclonerEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class CyclonerRenderer extends MobRenderer<CyclonerEntity, Modelcycloner<CyclonerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/cycloner.png");
    private static final ResourceLocation DRAGONBORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/cycloner_dragonborn.png");
    private static final ResourceLocation ROLL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/cycloner_roll.png");
    public CyclonerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelcycloner(context.bakeLayer(Modelcycloner.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new CyclonerRollLayer(this, new Modelcycloner(context.bakeLayer(Modelcycloner.LAYER_LOCATION)), ROLL_LOCATION));
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(CyclonerEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(CyclonerEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake_2.png");
        }
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("Dragonborn".equals(string) || "Skyrim".equals(string) || "Dovahkiin".equals(string)) {
            return DRAGONBORN_LOCATION;
        }
        return LOCATION;
    }
}
