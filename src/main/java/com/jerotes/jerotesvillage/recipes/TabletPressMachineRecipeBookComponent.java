package com.jerotes.jerotesvillage.recipes;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
//1.20.4↑//
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.minecraft.client.gui.components.WidgetSprites;
//1.20.1//
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TabletPressMachineRecipeBookComponent extends RecipeBookComponent {
    //1.20.4↑//
//   private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
//           new ResourceLocation(JerotesVillage.MODID, "recipe_book/tablet_press_machine_filter_enabled"),
//           new ResourceLocation(JerotesVillage.MODID, "recipe_book/tablet_press_machine_filter_disabled"),
//           new ResourceLocation(JerotesVillage.MODID, "recipe_book/tablet_press_machine_filter_enabled_highlighted"),
//           new ResourceLocation(JerotesVillage.MODID, "recipe_book/tablet_press_machine_filter_disabled_highlighted")
//   );
   protected static final ResourceLocation FILTER_SPRITESS = new ResourceLocation(JerotesVillage.MODID, "textures/gui/tablet_press_machine_recipe_book.png");

    @Override
    protected void initFilterButtonTextures() {
        //1.20.4↑//
        //this.filterButton.initTextureValues(FILTER_SPRITES);
        //1.20.1//
        this.filterButton.initTextureValues(152, 41, 28, 18, FILTER_SPRITESS);
    }

//    //1.20.4↑//
//   @Override
//   public void setupGhostRecipe(RecipeHolder<?> recipeHolder, List<Slot> slots) {
//      if (recipeHolder.value() instanceof TabletPressingRecipe recipe) {
//         ItemStack resultItem = recipe.getResultItem(this.minecraft.level.registryAccess());
//         this.ghostRecipe.setRecipe(recipeHolder);
//         //结果
//         this.ghostRecipe.addIngredient(Ingredient.of(resultItem), slots.get(1).x, slots.get(1).y);
//
//         NonNullList<Ingredient> ingredients = NonNullList.create();
//         ingredients.addAll(recipe.getBase()); //添加基础材料
//          for (Ingredient ingredient : ingredients) {
//              if (!ingredient.isEmpty()) {
//                  Slot slot = slots.get(0);
//                  this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
//              }
//          }
//         NonNullList<Ingredient> add = NonNullList.create();
//          add.addAll(recipe.getAddition()); //添加附加材料
//          for (Ingredient ingredient : add) {
//              if (!ingredient.isEmpty()) {
//                  Slot slot = slots.get(3);
//                  this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
//              }
//          }
//      }
//      else {
//         super.setupGhostRecipe(recipeHolder, slots);
//      }
//   }
    //1.20.1//
   @Override
   public void setupGhostRecipe(Recipe<?> recipeHolder, List<Slot> slots) {
      if (recipeHolder instanceof TabletPressingRecipe recipe) {

         ItemStack resultItem = recipe.getResultItem(this.minecraft.level.registryAccess());
         this.ghostRecipe.setRecipe(recipeHolder);
         this.ghostRecipe.addIngredient(Ingredient.of(resultItem), slots.get(1).x, slots.get(1).y);

         NonNullList<Ingredient> ingredients = NonNullList.create();
         ingredients.addAll(recipe.getBase()); //添加基础材料
         for (Ingredient ingredient : ingredients) {
            if (!ingredient.isEmpty()) {
               Slot slot = slots.get(0);
               this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            }
         }

         NonNullList<Ingredient> ingredientss = NonNullList.create();
         ingredientss.addAll(recipe.getAddition()); //添加附加材料
         for (Ingredient ingredient : ingredientss) {
            if (!ingredient.isEmpty()) {
               Slot slot = slots.get(3);
               this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            }
         }
      }
      else {
         super.setupGhostRecipe(recipeHolder, slots);
      }
   }

   private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.jerotesvillage.tablet_press_machine");

   @Override
   protected Component getRecipeFilterName() {
      return FILTER_NAME;
   }
}