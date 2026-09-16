package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.VirtualCaveSpiderSetaeEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class VirtualCaveSpiderSetaeRenderer extends ArrowRenderer<VirtualCaveSpiderSetaeEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_spider_setae.png");

    public VirtualCaveSpiderSetaeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(VirtualCaveSpiderSetaeEntity arrow) {
        return ARROW_LOCATION;
    }
}

