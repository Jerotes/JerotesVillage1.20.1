package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrust_beetle;
import com.jerotes.jerotesvillage.entity.Animal.RustBeetleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RustBeetleRenderer extends MobRenderer<RustBeetleEntity, Modelrust_beetle<RustBeetleEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rust_beetle.png");
    public RustBeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelrust_beetle(context.bakeLayer(Modelrust_beetle.LAYER_LOCATION)), 1.0f);
    }

    @Override
    protected float getFlipDegrees(RustBeetleEntity entity) {
        return 180.0f;
    }

    @Override
    protected void scale(RustBeetleEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.5f, 1.5f, 1.5f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(RustBeetleEntity entity) {
        return LOCATION;
    }
}

