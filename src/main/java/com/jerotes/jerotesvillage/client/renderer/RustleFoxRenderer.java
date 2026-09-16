package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.RustleFoxHeldItemLayer;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelrustle_fox;
import com.jerotes.jerotesvillage.entity.Animal.RustleFoxEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RustleFoxRenderer extends MobRenderer<RustleFoxEntity, Modelrustle_fox<RustleFoxEntity>> {
    private static final ResourceLocation RED_FOX_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox.png");
    private static final ResourceLocation RED_FOX_SLEEP_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_sleep.png");
    private static final ResourceLocation SNOW_FOX_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_snow.png");
    private static final ResourceLocation SNOW_FOX_SLEEP_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_sleep_snow.png");
    private static final ResourceLocation PINK_FOX_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_pink.png");
    private static final ResourceLocation PINK_FOX_SLEEP_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_sleep_pink.png");
    private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rustle_fox_tame.png");

    public RustleFoxRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelrustle_fox(context.bakeLayer(Modelrustle_fox.LAYER_LOCATION)), 0.5f);
        this.addLayer(new TameLayer(this, new Modelrustle_fox(context.bakeLayer(Modelrustle_fox.LAYER_LOCATION)), TAME_LOCATION));
        this.addLayer(new RustleFoxHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    protected void setupRotations(RustleFoxEntity fox, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(fox, poseStack, f, f2, f3);
        if (fox.isPouncing() || fox.isFaceplanted()) {
            float f4 = -Mth.lerp(f3, fox.xRotO, fox.getXRot());
            poseStack.mulPose(Axis.XP.rotationDegrees(f4));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(RustleFoxEntity fox) {
        if (fox.getFoxType() == 2) {
            return fox.isSleeping() ? PINK_FOX_SLEEP_TEXTURE : PINK_FOX_TEXTURE;
        }
        if (fox.getFoxType() == 3) {
            return fox.isSleeping() ? SNOW_FOX_SLEEP_TEXTURE : SNOW_FOX_TEXTURE;
        }
        return fox.isSleeping() ? RED_FOX_SLEEP_TEXTURE : RED_FOX_TEXTURE;
    }
}



