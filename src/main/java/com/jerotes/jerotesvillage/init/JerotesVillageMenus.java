package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.inventory.CarvedFactionMenu;
import com.jerotes.jerotesvillage.inventory.TabletPressMachineMenu;
import com.jerotes.jerotesvillage.inventory.TorchOfFirmIceMenu;
import com.jerotes.jerotesvillage.inventory.VillagerMetalAnvilMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, JerotesVillage.MODID);
    public static final RegistryObject<MenuType<CarvedFactionMenu>> CARVED_FACTION = REGISTRY.register("carved_faction", () -> new MenuType<>(CarvedFactionMenu::new, FeatureFlags.VANILLA_SET));
    public static final RegistryObject<MenuType<TabletPressMachineMenu>> TABLET_PRESS_MACHINE = REGISTRY.register("tablet_press_machine", () -> new MenuType<>(TabletPressMachineMenu::new, FeatureFlags.VANILLA_SET));
    public static final RegistryObject<MenuType<TorchOfFirmIceMenu>> TORCH_OF_FIRM_ICE = REGISTRY.register("torch_of_firm_ice", () -> new MenuType<>(TorchOfFirmIceMenu::new, FeatureFlags.VANILLA_SET));

    public static final RegistryObject<MenuType<VillagerMetalAnvilMenu>> VILLAGER_METAL_ANVIL = REGISTRY.register("villager_metal_anvil", () -> new MenuType<>(VillagerMetalAnvilMenu::new, FeatureFlags.VANILLA_SET));
}