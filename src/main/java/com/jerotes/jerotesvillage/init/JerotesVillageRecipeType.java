package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.recipes.HagReplacementRecipe;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageRecipeType {
    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, JerotesVillage.MODID);

    public static final RegistryObject<RecipeType<TabletPressingRecipe>> TABLET_PRESSING = REGISTRY.register("tablet_pressing", () -> registerRecipeType("tablet_pressing"));
    public static final RegistryObject<RecipeType<HagReplacementRecipe>> HAG_REPLACEMENT = REGISTRY.register("hag_replacement", () -> registerRecipeType("hag_replacement"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        RecipeType<T> type = new RecipeType<>() {
            public String toString() {
                return JerotesVillage.MODID + ":" + identifier;
            }
        };
        System.out.println("Registered RecipeType: " + type);
        return type;
    }

}
