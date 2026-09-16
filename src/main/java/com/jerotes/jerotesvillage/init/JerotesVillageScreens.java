package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.block.Other.VillagerMetalAnvil;
import com.jerotes.jerotesvillage.client.gui.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JerotesVillageScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(JerotesVillageMenus.CARVED_FACTION.get(), CarvedFactionScreen::new);
            MenuScreens.register(JerotesVillageMenus.TABLET_PRESS_MACHINE.get(), TabletPressMachineScreen::new);
            MenuScreens.register(JerotesVillageMenus.TORCH_OF_FIRM_ICE.get(), TorchOfFirmIceScreen::new);
            MenuScreens.register(JerotesVillageMenus.VILLAGER_METAL_ANVIL.get(), VillagerMetalAnvilScreen::new);
        });
    }
}
