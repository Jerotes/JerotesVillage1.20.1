package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandYellowDogEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WonderlandYellowDogRenderer extends WolfRenderer {
    private static final ResourceLocation BROWN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/brown.png");
    private static final ResourceLocation BROWN_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/brown_tame.png");
    private static final ResourceLocation BROWN_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/brown_angry.png");

    private static final ResourceLocation YELLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/yellow.png");
    private static final ResourceLocation YELLOW_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/yellow_tame.png");
    private static final ResourceLocation YELLOW_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/yellow_angry.png");

    private static final ResourceLocation BLACK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/black.png");
    private static final ResourceLocation BLACK_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/black_tame.png");
    private static final ResourceLocation BLACK_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/black_angry.png");

    private static final ResourceLocation RED_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/red.png");
    private static final ResourceLocation RED_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/red_tame.png");
    private static final ResourceLocation RED_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/red_angry.png");

    private static final ResourceLocation IRON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/iron.png");
    private static final ResourceLocation IRON_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/iron_tame.png");
    private static final ResourceLocation IRON_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/iron_angry.png");

    private static final ResourceLocation TIGER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/tiger.png");
    private static final ResourceLocation TIGER_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/tiger_tame.png");
    private static final ResourceLocation TIGER_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/tiger_angry.png");

    private static final ResourceLocation WHITE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/white.png");
    private static final ResourceLocation WHITE_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/white_tame.png");
    private static final ResourceLocation WHITE_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_yellow_dog/white_angry.png");

    public WonderlandYellowDogRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        if (wolf instanceof WonderlandYellowDogEntity wonderlandYellowDog) {
            if (wonderlandYellowDog.getDogColor() == 1) {
                if (wonderlandYellowDog.isTame()) {
                    return BROWN_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return BROWN_ANGRY_LOCATION;
                }
                return BROWN_LOCATION;
            } else if (wonderlandYellowDog.getDogColor() == 2) {
                if (wonderlandYellowDog.isTame()) {
                    return BLACK_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return BLACK_ANGRY_LOCATION;
                }
                return BLACK_LOCATION;
            } else if (wonderlandYellowDog.getDogColor() == 3) {
                if (wonderlandYellowDog.isTame()) {
                    return RED_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return RED_ANGRY_LOCATION;
                }
                return RED_LOCATION;
            } else if (wonderlandYellowDog.getDogColor() == 4) {
                if (wonderlandYellowDog.isTame()) {
                    return IRON_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return IRON_ANGRY_LOCATION;
                }
                return IRON_LOCATION;
            } else if (wonderlandYellowDog.getDogColor() == 5) {
                if (wonderlandYellowDog.isTame()) {
                    return TIGER_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return TIGER_ANGRY_LOCATION;
                }
                return TIGER_LOCATION;
            } else if (wonderlandYellowDog.getDogColor() == 6) {
                if (wonderlandYellowDog.isTame()) {
                    return WHITE_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return WHITE_ANGRY_LOCATION;
                }
                return WHITE_LOCATION;
            } else {
                if (wonderlandYellowDog.isTame()) {
                    return YELLOW_TAME_LOCATION;
                }
                if (wonderlandYellowDog.isAngry()) {
                    return YELLOW_ANGRY_LOCATION;
                }
                return YELLOW_LOCATION;
            }
        }
        if (wolf.isTame()) {
            return YELLOW_TAME_LOCATION;
        }
        if (wolf.isAngry()) {
            return YELLOW_ANGRY_LOCATION;
        }
        return YELLOW_LOCATION;
    }
}



