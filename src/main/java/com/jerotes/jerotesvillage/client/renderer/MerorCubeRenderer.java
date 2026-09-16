package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorCubeGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelredstone_cube;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorCubeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerorCubeRenderer extends MobRenderer<MerorCubeEntity, Modelredstone_cube<MerorCubeEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_cube.png");
    private static final ResourceLocation REDSTONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_cube_redstone_cube.png");

    public MerorCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelredstone_cube(context.bakeLayer(Modelredstone_cube.LAYER_LOCATION)), 0.5f);
        this.addLayer(new MerorCubeGlowLayer<>(this, new Modelredstone_cube(context.bakeLayer(Modelredstone_cube.LAYER_LOCATION))));
    }

    @Override
    protected void scale(MerorCubeEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(MerorCubeEntity cube) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(MerorCubeEntity cube) {
        String string = ChatFormatting.stripFormatting(cube.getName().getString());
        if ("Redstone Cube".equals(string) || "红石怪".equals(string)) {
            return REDSTONE_LOCATION;
        }
        return LOCATION;
    }
}
