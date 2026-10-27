package com.jerotes.jerotesvillage;

import com.jerotes.jerotes.spell.SpellRegistry;
import com.jerotes.jerotesvillage.client.CilentInit;
import com.jerotes.jerotesvillage.compat.tacz.MerorsResourceManager;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Other.WonderlinPainting.WonderlinPaintingVariants;
import com.jerotes.jerotesvillage.event.RendererEvent;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.network.OtherPacketHandler;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import com.jerotes.jerotesvillage.util.ViewerNameManager;
import com.jerotes.jerotesvillage.world.density.JerotesDensityFunctions;
import com.jerotes.jerotesvillage.world.features.DroughtFireLavaFractureFeature;
import com.jerotes.jerotesvillage.world.features.StructureFeature;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.slf4j.Logger;

@Mod(JerotesVillage.MODID)
public class JerotesVillage {
    public static final String MODID = "jerotesvillage";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final RecipeBookType TABLET_PRESS_MACHINE = RecipeBookType.create("TABLET_PRESS_MACHINE");

    public JerotesVillage() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        JerotesVillageItems.REGISTRY.register(modEventBus);
        JerotesVillageBlocks.REGISTRY.register(modEventBus);
        JerotesVillageBlockEntityType.REGISTRY.register(modEventBus);
        JerotesVillageEntityType.REGISTRY.register(modEventBus);
        JerotesVillageTabs.REGISTRY.register(modEventBus);
        JerotesVillageVillagerProfessions.PROFESSIONS.register(modEventBus);
        JerotesVillageMobEffects.REGISTRY.register(modEventBus);
        JerotesVillageParticleTypes.REGISTRY.register(modEventBus);
        JerotesVillagePotions.REGISTRY.register(modEventBus);
        JerotesVillageEnchantments.REGISTRY.register(modEventBus);
        JerotesVillageMenus.REGISTRY.register(modEventBus);
        JerotesVillageRecipeType.REGISTRY.register(modEventBus);
        JerotesVillageRecipeSerializers.REGISTRY.register(modEventBus);
        JerotesVillageDecoratedPotPatterns.REGISTRY.register(modEventBus);
        JerotesVillageBiomeSources.register(FMLJavaModLoadingContext.get().getModEventBus());
        JerotesVillageTreeDecorator.REGISTRY.register(modEventBus);
        StructureFeature.REGISTRY.register(modEventBus);
        DroughtFireLavaFractureFeature.REGISTRY.register(modEventBus);
        JerotesDensityFunctions.REGISTRY.register(modEventBus);
        WonderlinPaintingVariants.REGISTRY.register(modEventBus);
        modEventBus.addListener(this::initClient);
        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OtherMainConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, OtherMainConfig.CLIENT_SPEC);
        for (OtherSpellType type : OtherSpellType.values()) {
            SpellRegistry.register(type);
        }
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OtherPacketHandler::register);
        event.enqueueWork(JerotesVillageItems::setup);
        event.enqueueWork(JerotesVillagePotions::init);
        event.enqueueWork(JerotesVillageSpawnPlacements::init);
        event.enqueueWork(JerotesVillageRaider::addRaiders);
        event.enqueueWork(JerotesVillageDecoratedPotPatterns::expandVanillaDefinitions);
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("tacz")) {
                // 检查是否需要更新资源
                if (MerorsResourceManager.needsUpdate()) {
                    LOGGER.info("Updating MerorsEnergeticGun resources...");
                    MerorsResourceManager.initializeResources();
                } else {
                    LOGGER.info("MerorsEnergeticGun resources are up to date");
                }
            }
        });

    }
    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ViewerNameManager.getInstance());
    }

    private void initClient(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        event.enqueueWork(CilentInit::clientInit);
        event.enqueueWork(() -> {
            MinecraftForge.EVENT_BUS.register(new RendererEvent());
        });
    }

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping :
                event.getMappings(net.minecraft.core.registries.Registries.ITEM, MODID)) {
            ResourceLocation oldId = mapping.getKey();
//			if (oldId.equals(new ResourceLocation("yourmodid", "old_item"))) {
//				mapping.remap(JerotesVillageItems.NEW_ITEM.get());
//			}
        }
    }
}