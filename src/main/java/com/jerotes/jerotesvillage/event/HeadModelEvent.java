package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class HeadModelEvent {
    @SubscribeEvent
    public static void onCreateSkullModel(EntityRenderersEvent.CreateSkullModels event) {
        registerSkullModelSafe(event, ABaseBlock.Types.SECOND_ROUNDER_ZOMBIE, ModelLayers.ZOMBIE_HEAD);
        registerSkullModelSafe(event, ABaseBlock.Types.SECOND_ROUNDER_SKELETON, ModelLayers.SKELETON_SKULL);
        registerSkullModelSafe(event, ABaseBlock.Types.RESURRECTER, ModelLayers.PLAYER_HEAD);
        registerSkullModelSafe(event, ABaseBlock.Types.PURPLE_SAND_CREEPER, ModelLayers.CREEPER_HEAD);
        registerSkullModelSafe(event, ABaseBlock.Types.SPIRVE, ModelLayers.PLAYER_HEAD);
        registerSkullModelSafe(event, ABaseBlock.Types.TANGLER, ModelLayers.PLAYER_HEAD);
        registerSkullModelSafe(event, ABaseBlock.Types.BLAMER, ModelLayers.PLAYER_HEAD);
    }

    private static void registerSkullModelSafe(EntityRenderersEvent.CreateSkullModels event, SkullBlock.Type skullType, ModelLayerLocation modelLayer) {
        try {
            ModelPart modelPart = event.getEntityModelSet().bakeLayer(modelLayer);
            if (modelPart == null) {
                JerotesVillage.LOGGER.error("Failed to bake model layer for skull type: {}", skullType);
                return;
            }
            event.registerSkullModel(skullType, new SkullModel(modelPart));
        } catch (Exception e) {
            JerotesVillage.LOGGER.error("Failed to register skull model for type: {}", skullType, e);
        }
    }
}