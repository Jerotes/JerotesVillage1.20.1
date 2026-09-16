package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotesvillage.entity.Shoot.Other.MerorShockWaveEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MerorShockWaveRenderer<T extends MerorShockWaveEntity> extends EntityRenderer<T> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/item/magic_missile.png");
    private final boolean fullBright;

    public MerorShockWaveRenderer(EntityRendererProvider.Context context, float f, boolean bl) {
        super(context);
        this.fullBright = bl;
    }

    public MerorShockWaveRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0f, true);
    }

    @Override
    protected int getBlockLightLevel(T t, BlockPos blockPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(t, blockPos);
    }

    @Override
    public void render(T t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        super.render(t, f, f2, poseStack, multiBufferSource, n);
        t.TrailParticle(f2);
    }

    @Override
    public ResourceLocation getTextureLocation(MerorShockWaveEntity tex) {
        return TEXTURE_LOCATION;
    }
}

