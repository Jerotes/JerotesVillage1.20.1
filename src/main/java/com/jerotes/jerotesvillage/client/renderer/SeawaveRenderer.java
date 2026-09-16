package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelseawave;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Enemy;

public class SeawaveRenderer extends MobRenderer<SeawaveEntity, Modelseawave<SeawaveEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/seawave.png");
    private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/fury_seawave.png");
    public SeawaveRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelseawave(context.bakeLayer(Modelseawave.LAYER_LOCATION)), 0.6f);
    }
    @Override
    protected float getFlipDegrees(SeawaveEntity entity) {
        return 0f;
    }

    @Override
    protected void scale(SeawaveEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.25f, 1.25f, 1.25f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(SeawaveEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float idleAnim = t.getIdleAnim();
        float rollAnim = t.getRollAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.idleAnimProgress = Math.min(10, Mth.lerp(smoothFactor, t.idleAnimProgress, idleAnim));
        if (!t.specialAction() && t.getAttackTick() <= 0 && t.isAlive()) {
            t.rollAnimProgress = Mth.lerp(smoothFactor, t.rollAnimProgress, rollAnim);
        }
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(SeawaveEntity entity) {
        if (entity instanceof Enemy) {
            return FURY_LOCATION;
        }
        return LOCATION;
    }
}

