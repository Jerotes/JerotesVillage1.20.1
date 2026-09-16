package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.recipes.HagReplacementRecipe;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, JerotesVillage.MODID);
    public static final RegistryObject<RecipeSerializer<TabletPressingRecipe>> TABLET_PRESSING =
            REGISTRY.register("tablet_pressing", TabletPressingRecipe.TabletPressingSerializer::new);
    public static final RegistryObject<RecipeSerializer<HagReplacementRecipe>> HAG_REPLACEMENT =
            REGISTRY.register("hag_replacement", HagReplacementRecipe.HagReplacementSerializer::new);
}