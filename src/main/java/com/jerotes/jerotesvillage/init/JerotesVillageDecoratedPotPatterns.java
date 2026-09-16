package com.jerotes.jerotesvillage.init;

import com.google.common.collect.ImmutableMap;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageDecoratedPotPatterns {

    public static final DeferredRegister<String> REGISTRY = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, JerotesVillage.MODID);

    public static final RegistryObject<String> LIGHTNING = REGISTRY.register("lightning_pottery_pattern", () -> JerotesVillage.MODID + ":lightning_pottery_pattern");

    public static void expandVanillaDefinitions() {
        ImmutableMap.Builder<Item, ResourceKey<String>> itemsToPot = new ImmutableMap.Builder<>();
        itemsToPot.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        itemsToPot.put(JerotesVillageItems.LIGHTNING_POTTERY_SHERD.get(), LIGHTNING.getKey());
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemsToPot.build();
    }
}
