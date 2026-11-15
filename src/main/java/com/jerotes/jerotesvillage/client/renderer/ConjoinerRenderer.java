package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.client.model.Modelconjoiner;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.ConjoinerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ConjoinerRenderer extends MobRenderer<ConjoinerEntity, Modelconjoiner<ConjoinerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/conjoiner.png");
    private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/conjoiner_wicked_ridicule_calls_eye.png");

    public ConjoinerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelconjoiner<>(context.bakeLayer(Modelconjoiner.LAYER_LOCATION)), 0.7F);
        this.addLayer(new WickedRidiculeCallsEyesLayer<>(this, EYE_LOCATION));
    }

    @Override
    public void render(ConjoinerEntity conjoinerEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
        conjoinerEntity.thisTickRenderTime += 1;
        float smoothFactor = 1f / (conjoinerEntity.lastTickRenderTime + 1);
        conjoinerEntity.attackAnimProgress = Mth.lerp(smoothFactor, conjoinerEntity.attackAnimProgress, conjoinerEntity.getAttackAnim());
        super.render(conjoinerEntity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(ConjoinerEntity entity) {
        return LOCATION;
    }
}
