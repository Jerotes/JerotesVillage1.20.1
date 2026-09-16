package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class JerotesVillageStructures {
    public static final ResourceKey<Structure> WITCH_RESIDENCE = JerotesVillageStructures.createKey("witch_residence");

    private static ResourceKey<Structure> createKey(String string) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(string));
    }
}
