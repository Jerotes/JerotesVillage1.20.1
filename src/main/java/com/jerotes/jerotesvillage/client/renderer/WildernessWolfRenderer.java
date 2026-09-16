package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.WildernessWolfEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WildernessWolfRenderer extends WolfRenderer {
    private static final ResourceLocation WOLF_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf.png");
    private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_tame.png");
    private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_angry.png");
    private static final ResourceLocation WOLF_ILLAGER_FACTION_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_illager_faction.png");
    private static final ResourceLocation WOLF_ANGRY_ILLAGER_FACTION_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_angry_illager_faction.png");

    private static final ResourceLocation WOLF_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_snow.png");
    private static final ResourceLocation WOLF_TAME_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_tame_snow.png");
    private static final ResourceLocation WOLF_ANGRY_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_angry_snow.png");
    private static final ResourceLocation WOLF_ILLAGER_FACTION_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_illager_faction_snow.png");
    private static final ResourceLocation WOLF_ANGRY_ILLAGER_FACTION_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_wolf_angry_illager_faction_snow.png");

    public WildernessWolfRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.7f;
    }


    @Override
    protected void scale(Wolf entity, PoseStack poseStack, float f) {
        if (entity instanceof WildernessWolfEntity wildernessWolf && !wildernessWolf.isSnow()) {
            poseStack.scale(1.4f, 1.4f, 1.4f);
        }
        else {
            poseStack.scale(1.2f, 1.2f, 1.2f);
        }
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        if (wolf instanceof WildernessWolfEntity wildernessWolf) {
            if (wildernessWolf.isTame()) {
                if (wildernessWolf.isSnow())
                    return WOLF_TAME_SNOW_LOCATION;
                return WOLF_TAME_LOCATION;
            }
            if (wildernessWolf.isAngry()) {
                if (wildernessWolf.isIllagerFaction()) {
                    if (wildernessWolf.isSnow())
                        return WOLF_ANGRY_ILLAGER_FACTION_SNOW_LOCATION;
                    return WOLF_ANGRY_ILLAGER_FACTION_LOCATION;
                }
                if (wildernessWolf.isSnow())
                    return WOLF_ANGRY_SNOW_LOCATION;
                return WOLF_ANGRY_LOCATION;
            }
            if (wildernessWolf.isIllagerFaction()) {
                if (wildernessWolf.isSnow())
                    return WOLF_ILLAGER_FACTION_SNOW_LOCATION;
                return WOLF_ILLAGER_FACTION_LOCATION;
            }
            if (wildernessWolf.isSnow())
                return WOLF_SNOW_LOCATION;
            return WOLF_LOCATION;
        }
        return WOLF_LOCATION;
    }
}



