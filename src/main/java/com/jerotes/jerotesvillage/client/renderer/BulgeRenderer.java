package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbulge;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;

public class BulgeRenderer extends MobRenderer<Mob, Modelbulge<Mob>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/bulge.png");
    private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/fury_bulge.png");
    public BulgeRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelbulge(context.bakeLayer(Modelbulge.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob entity) {
        if (entity instanceof Enemy) {
            return FURY_LOCATION;
        }
        return LOCATION;
    }
}

