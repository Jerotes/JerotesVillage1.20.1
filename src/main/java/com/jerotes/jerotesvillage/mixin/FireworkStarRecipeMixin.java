package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//1.20.1//
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Mixin(FireworkStarRecipe.class)
public abstract class FireworkStarRecipeMixin extends CustomRecipe {
    //1.20.4↑//
//    public FireworkStarRecipeMixin(CraftingBookCategory category) {
//        super(category);
//    }
    //1.20.1//
    public FireworkStarRecipeMixin(ResourceLocation resourceLocation, CraftingBookCategory category) {
        super(resourceLocation, category);
    }

    @Final
    @Shadow
    private static Map<Item, FireworkRocketItem.Shape> SHAPE_BY_ITEM;
    @Mutable
    @Final
    @Shadow
    private static Ingredient SHAPE_INGREDIENT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addToShapeIngredient(CallbackInfo ci) {
        SHAPE_INGREDIENT = Ingredient.of(
                Stream.concat(
                        Arrays.stream(SHAPE_INGREDIENT.getItems()),
                        Stream.of(
                                new ItemStack(JerotesVillageItems.SECOND_ROUNDER_ZOMBIE_HEAD.get()),
                                new ItemStack(JerotesVillageItems.SECOND_ROUNDER_SKELETON_SKULL.get()),
                                new ItemStack(JerotesVillageItems.RESURRECTER_HEAD.get()),
                                new ItemStack(JerotesVillageItems.PURPLE_SAND_CREEPER_HEAD.get()),
                                new ItemStack(JerotesVillageItems.SPIRVE_HEAD.get()),
                                new ItemStack(JerotesVillageItems.TANGLER_HEAD.get()),
                                new ItemStack(JerotesVillageItems.BLAMER_HEAD.get())

                        )
                ).toArray(ItemStack[]::new)
        );
        SHAPE_BY_ITEM.put(JerotesVillageItems.SECOND_ROUNDER_ZOMBIE_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.SECOND_ROUNDER_SKELETON_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.RESURRECTER_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.PURPLE_SAND_CREEPER_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.SPIRVE_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.TANGLER_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        SHAPE_BY_ITEM.put(JerotesVillageItems.BLAMER_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
    }
}