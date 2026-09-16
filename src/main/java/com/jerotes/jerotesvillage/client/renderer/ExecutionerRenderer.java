package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelexecutioner;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.ExecutionerEntity;
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

public class ExecutionerRenderer extends MobRenderer<ExecutionerEntity, Modelexecutioner<ExecutionerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/illager/executioner.png");
    public ExecutionerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelexecutioner(context.bakeLayer(Modelexecutioner.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new HumanoidArmorLayer(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.model.getHat().visible = true;
    }
    @Override
    public void render(ExecutionerEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float attackAnim = t.getAttackAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }


    protected void scale(ExecutionerEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1f, 1f, 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(ExecutionerEntity entity) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake.png");
        }
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("乐刑者".equals(string) || "Happiexecutioner".equals(string)) {
            return new ResourceLocation(JerotesVillage.MODID,
                    "textures/entity/illager/executioner_happiexecutioner_by_twiadvent.png");
        }
        return LOCATION;
    }
}
