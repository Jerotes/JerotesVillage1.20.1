package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class PiglinResidentRenderer extends PiglinRenderer  {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/piglin_resident.png");
    private static final ResourceLocation PIGMAN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/piglin_resident_pigman.png");

    public PiglinResidentRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false);
    }

    @Override
    protected boolean isShaking(Mob mob) {
        String string = ChatFormatting.stripFormatting(mob.getName().getString());
        if ("Pigman".equals(string)) {
            return false;
        }
        return super.isShaking(mob);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob  mob) {
        String string = ChatFormatting.stripFormatting(mob.getName().getString());
        if ("Pigman".equals(string)) {
            return PIGMAN_LOCATION;
        }
        return LOCATION;
    }
}
