package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowSparrowOnShoulderLayer;
import com.jerotes.jerotesvillage.client.layer.SerponWingsLayer;
import com.jerotes.jerotesvillage.client.layer.VillagerOtherBodyLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//1.20.4↑//
//import net.minecraft.client.resources.PlayerSkin;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RendererEvent {
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {

        //1.20.4↑//
//        //荧光雀
//        addGlowSparrowOnShoulderLayer(event, PlayerSkin.Model.WIDE);
//        addGlowSparrowOnShoulderLayer(event, PlayerSkin.Model.SLIM);
//        //蛇龙凭证
//        addSerponWingsLayer(event, PlayerSkin.Model.WIDE);
//        addSerponWingsLayer(event, PlayerSkin.Model.SLIM);
//        //猪灵职业村民
//        LivingEntityRenderer<Villager, VillagerModel<Villager>> villager = event.getEntityRenderer(EntityType.VILLAGER);
//        if (villager != null) {
//            villager.addLayer(new VillagerOtherBodyLayer<>(villager, event.getContext()));
//        }
//        LivingEntityRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> zombieVillager = event.getEntityRenderer(EntityType.ZOMBIE_VILLAGER);
//        if (zombieVillager != null) {
//            zombieVillager.addLayer(new VillagerOtherBodyLayer<>(zombieVillager, event.getContext()));
//        }

        //1.20.1//
        //荧光雀
        addGlowSparrowOnShoulderLayer(event, "default");
        addGlowSparrowOnShoulderLayer(event,"slim");
        //蛇龙凭证
        addSerponWingsLayer(event, "default");
        addSerponWingsLayer(event,"slim");
        //猪灵职业村民
        LivingEntityRenderer<Villager, VillagerModel<Villager>> villager = event.getRenderer(EntityType.VILLAGER);
        if (villager != null) {
            villager.addLayer(new VillagerOtherBodyLayer<>(villager, event.getContext()));
        }
        LivingEntityRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> zombieVillager = event.getRenderer(EntityType.ZOMBIE_VILLAGER);
        if (zombieVillager != null) {
            zombieVillager.addLayer(new VillagerOtherBodyLayer<>(zombieVillager, event.getContext()));
        }
    }

    //1.20.4↑//
//    private static void addGlowSparrowOnShoulderLayer(EntityRenderersEvent.AddLayers event, PlayerSkin.Model skinType) {
//        LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getPlayerSkin(skinType);
//        if (renderer != null) {
//            renderer.addLayer(new GlowSparrowOnShoulderLayer<>(renderer, event.getEntityModels()));
//        }
//    }
//    private static void addSerponWingsLayer(EntityRenderersEvent.AddLayers event, PlayerSkin.Model skinType) {
//        LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getPlayerSkin(skinType);
//        if (renderer != null) {
//            renderer.addLayer(new SerponWingsLayer<>(renderer, event.getContext()));
//        }
//    }
    //1.20.1//
    private static void addGlowSparrowOnShoulderLayer(EntityRenderersEvent.AddLayers event, String string) {
        LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(string);
        if (renderer != null) {
            renderer.addLayer(new GlowSparrowOnShoulderLayer<>(renderer, event.getEntityModels()));
        }
    }
    private static void addSerponWingsLayer(EntityRenderersEvent.AddLayers event, String string) {
        LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(string);
        if (renderer != null) {
            renderer.addLayer(new SerponWingsLayer<>(renderer, event.getContext()));
        }
    }
}
