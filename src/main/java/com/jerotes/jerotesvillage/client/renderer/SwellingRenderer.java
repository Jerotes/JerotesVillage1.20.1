package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.client.model.Modelswelling;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SwellingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SwellingRenderer extends MobRenderer<SwellingEntity, Modelswelling<SwellingEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/swelling.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/swelling_glow.png");
    private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/swelling_wicked_ridicule_calls_eye.png");

    public SwellingRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelswelling<>(context.bakeLayer(Modelswelling.LAYER_LOCATION)), 0.45F);
        this.addLayer(new GlowOtherBodyLayer<>(this, new Modelswelling(context.bakeLayer(Modelswelling.LAYER_LOCATION)), GLOW_LOCATION));
        this.addLayer(new WickedRidiculeCallsEyesLayer<>(this, EYE_LOCATION));
    }

    @Override
    protected float getFlipDegrees(SwellingEntity entity) {
        return 0.0f;
    }

    @Override
    public void render(SwellingEntity swellingEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
        swellingEntity.thisTickRenderTime += 1;
        float smoothFactor = 1f / (swellingEntity.lastTickRenderTime + 1);
        swellingEntity.attackAnimProgress = Mth.lerp(smoothFactor, swellingEntity.attackAnimProgress, swellingEntity.getAttackAnim());
        super.render(swellingEntity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(SwellingEntity entity) {
        return LOCATION;
    }
}
