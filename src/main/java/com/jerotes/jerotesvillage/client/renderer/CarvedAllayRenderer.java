package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.allay.Allay;

public class CarvedAllayRenderer extends AllayRenderer {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_allay.png");
    private static final ResourceLocation VEX_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_allay_vex.png");

    public CarvedAllayRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Allay allay) {
        if (allay instanceof CarvedAllayEntity carvedAllay && carvedAllay.isVexRace()) {
            return VEX_LOCATION;
        }
        return LOCATION;
    }
}
