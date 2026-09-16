package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.BoundZombieVillagerEntity;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;

public class BoundZombieVillagerRenderer extends HumanoidMobRenderer<BoundZombieVillagerEntity, ZombieVillagerModel<BoundZombieVillagerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bound_zombie_villager.png");

    public BoundZombieVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new VillagerProfessionLayer<>(this, context.getResourceManager(), "zombie_villager"));
    }

    public ResourceLocation getTextureLocation(BoundZombieVillagerEntity p_116559_) {
        return LOCATION;
    }

    protected boolean isShaking(BoundZombieVillagerEntity zombieVillager) {
        return super.isShaking(zombieVillager) || zombieVillager.isConverting();
    }
}