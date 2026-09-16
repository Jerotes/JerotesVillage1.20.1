package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelax_crazy;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.AxCrazyEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class AxCrazyRenderer extends HumanoidMobRenderer<AxCrazyEntity, Modelax_crazy<AxCrazyEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy.png");
    private static final ResourceLocation BATEMAN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_bateman.png");
    private static final ResourceLocation ILLAGER_WITHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_by_illager_wither.png");
    private static final ResourceLocation TWO_ILLAGER_WITHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_noble_by_illager_wither.png");
    public AxCrazyRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelax_crazy(context.bakeLayer(Modelax_crazy.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(AxCrazyEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(AxCrazyEntity entity) {
        return 0.0f;
    }

    @Override
    protected boolean isShaking(AxCrazyEntity entity) {
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        return entity.isAggressive() && !("Sigma".equals(string) || "Bateman".equals(string)) || super.isShaking(entity);
    }

    @Override
    public ResourceLocation getTextureLocation(AxCrazyEntity t) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake_2.png");
        }
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("Master".equals(string)) {
            return ILLAGER_WITHER_LOCATION;
        }
        if ("Noble".equals(string)) {
            return TWO_ILLAGER_WITHER_LOCATION;
        }
        if ("Sigma".equals(string) || "Bateman".equals(string)) {
            return BATEMAN_LOCATION;
        }
        return LOCATION;
    }

    @Override
    protected void setupRotations(AxCrazyEntity entity, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(entity, poseStack, f, f2, f3);
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("Sigma".equals(string) || "Bateman".equals(string)) {
            return;
        }
        if ((double)entity.walkAnimation.speed() < 0.01) {
            return;
        }
        float f5 = entity.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(2.5f * f6));
    }
}
