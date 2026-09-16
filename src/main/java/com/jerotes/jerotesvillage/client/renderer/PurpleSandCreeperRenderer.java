package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.PurpleSandCreeperColorLayer;
import com.jerotes.jerotesvillage.client.layer.PurpleSandCreeperPowerLayer;
import com.jerotes.jerotesvillage.entity.Monster.PurpleSandCreeperEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PurpleSandCreeperRenderer extends MobRenderer<PurpleSandCreeperEntity, CreeperModel<PurpleSandCreeperEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_creeper.png");

    public PurpleSandCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new CreeperModel(context.bakeLayer(ModelLayers.CREEPER)), 0.5f);
        this.addLayer(new PurpleSandCreeperColorLayer<>(this, new CreeperModel<>(context.bakeLayer(ModelLayers.CREEPER))));
        this.addLayer(new PurpleSandCreeperPowerLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(PurpleSandCreeperEntity entity, PoseStack poseStack, float f) {
        float f2 = entity.getSwelling(f);
        float f3 = 1.0f + Mth.sin(f2 * 100.0f) * f2 * 0.01f;
        f2 = Mth.clamp(f2, 0.0f, 1.0f);
        f2 *= f2;
        f2 *= f2;
        float f4 = (1.0f + f2 * 0.4f) * f3;
        float f5 = (1.0f + f2 * 0.1f) / f3;
        poseStack.scale(f4, f5, f4);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getWhiteOverlayProgress(PurpleSandCreeperEntity creeper, float f) {
        float f2 = creeper.getSwelling(f);
        if ((int)(f2 * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return Mth.clamp(f2, 0.5f, 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(PurpleSandCreeperEntity creeper) {
        return LOCATION;
    }
}

