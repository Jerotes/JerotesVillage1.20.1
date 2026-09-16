package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class CarvedHoundRenderer extends WolfRenderer {
    private static final ResourceLocation WOLF_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_hound.png");
    private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_hound_tame.png");
    private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_hound_angry.png");

    public CarvedHoundRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        if (wolf.isTame()) {
            return WOLF_TAME_LOCATION;
        }
        if (wolf.isAngry()) {
            return WOLF_ANGRY_LOCATION;
        }
        return WOLF_LOCATION;
    }
}



