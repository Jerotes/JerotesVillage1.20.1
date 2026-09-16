package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeljavelin_thrower;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.JavelinThrowerEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JavelinThrowerRenderer extends HumanoidMobRenderer<JavelinThrowerEntity, Modeljavelin_thrower<JavelinThrowerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/javelin_thrower.png");
    private static final ResourceLocation ILLAGER_WITHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/javelin_thrower_armored_by_illager_wither.png");
    public JavelinThrowerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modeljavelin_thrower(context.bakeLayer(Modeljavelin_thrower.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }
    @Override
    public void render(JavelinThrowerEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float attackAnim = t.getAttackAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }


    @Override
    protected void scale(JavelinThrowerEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(JavelinThrowerEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake.png");
        }
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("Armored".equals(string)) {
            return ILLAGER_WITHER_LOCATION;
        }
        return LOCATION;
    }
}
