package com.jerotes.jerotesvillage.inventory;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageMenus;
import com.jerotes.jerotesvillage.init.JerotesVillageRecipeType;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
//1.20.4↑
//import net.minecraft.world.item.crafting.RecipeHolder;
//1.20.1//
import net.minecraft.world.item.crafting.Recipe;

public class TabletPressMachineMenu extends RecipeBookMenu<Container> {
   private final Container container;
   private final ContainerData containerData;
   private final Slot ingredientSlot;
   public final Level level;
   private final RecipeType<? extends TabletPressingRecipe> recipeType;
   private final RecipeBookType recipeBookType;

   public TabletPressMachineMenu(int n, Inventory inventory) {
      this(n, inventory, new SimpleContainer(5), new SimpleContainerData(2));
   }

   public TabletPressMachineMenu(int n, Inventory inventory, Container container, ContainerData p_39096_) {
      super(JerotesVillageMenus.TABLET_PRESS_MACHINE.get(), n);
      checkContainerSize(container, 5);
      checkContainerDataCount(p_39096_, 2);
      this.container = container;
      this.containerData = p_39096_;
      this.level = inventory.player.level();
      this.recipeType = JerotesVillageRecipeType.TABLET_PRESSING.get();
      this.recipeBookType = JerotesVillage.TABLET_PRESS_MACHINE;
      this.addSlot(new TabletPressMachineMenu.PotionSlot(container, 0, 56, 58));
      this.addSlot(new TabletPressMachineMenu.PotionSlot(container, 1, 79, 58));
      this.addSlot(new TabletPressMachineMenu.PotionSlot(container, 2, 102, 58));
      this.ingredientSlot = this.addSlot(new TabletPressMachineMenu.PotionSlot(container, 3, 79, 17));
      this.addDataSlots(p_39096_);

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
      }

   }

   public boolean stillValid(Player p_39098_) {
      return this.container.stillValid(p_39098_);
   }

   protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
      if (p_38905_ == 5) return false;
      return super.moveItemStackTo(p_38904_, p_38905_, p_38906_, p_38907_);
   }
   public ItemStack quickMoveStack(Player player, int n) {
      ItemStack itemStack = ItemStack.EMPTY;
      Slot slot = this.slots.get(n);
      if (slot != null && slot.hasItem()) {
         ItemStack itemStack2 = slot.getItem();
         itemStack = itemStack2.copy();
         if (n >= 0 && n <= 2 || n == 3) {
            if (!this.moveItemStackTo(itemStack2, 4, 40, true)) {
               return ItemStack.EMPTY;
            }
            slot.onQuickCraft(itemStack2, itemStack);
         }
         else {
            if (this.ingredientSlot.mayPlace(itemStack2) && (!this.slots.get(3).hasItem()) || ItemStack.isSameItemSameTags(this.slots.get(3).getItem(), itemStack2)) {
               if (!this.moveItemStackTo(itemStack2, 3, 4, false)) {
                  if (!this.moveItemStackTo(itemStack2, 0, 3, false)) {
                     return ItemStack.EMPTY;
                  }
               }
            }
            else if (this.moveItemStackTo(itemStack2, 0, 3, false)) {
               if (this.moveItemStackTo(itemStack2, 0, 3, false)) {
                  if (this.moveItemStackTo(itemStack2, 0, 3, false)) {
                     return ItemStack.EMPTY;
                  }
               }
            }
            else if (n >= 4 && n < 31) {
               if (!this.moveItemStackTo(itemStack2, 31, 40, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (n >= 31 && n < 40) {
               if (!this.moveItemStackTo(itemStack2, 4, 31, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(itemStack2, 4, 40, false)) {
               return ItemStack.EMPTY;
            }
         }
         if (itemStack2.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
         if (itemStack2.getCount() == itemStack.getCount()) {
            return ItemStack.EMPTY;
         }
         slot.onTake(player, itemStack2);
      }

      return itemStack;
   }

   public int getMakingTicks() {
      return this.containerData.get(0);
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
      if (this.container instanceof StackedContentsCompatible) {
         ((StackedContentsCompatible)((Object)this.container)).fillStackedContents(stackedContents);
      }
   }

   @Override
   public void clearCraftingContent() {
      this.getSlot(0).set(ItemStack.EMPTY);
      this.getSlot(1).set(ItemStack.EMPTY);
      this.getSlot(2).set(ItemStack.EMPTY);
      this.getSlot(3).set(ItemStack.EMPTY);
   }

   //1.20.4↑//
//   @Override
//   public boolean recipeMatches(RecipeHolder<? extends Recipe<Container>> recipeHolder) {
//      return recipeHolder.value().matches(this.container, this.level);
//   }
   //1.20.1↑//
   @Override
   public boolean recipeMatches(Recipe<? super Container> recipeHolder) {
      return recipeHolder.matches(this.container, this.level);
   }

   @Override
   public int getResultSlotIndex() {
      return 4;
   }

   @Override
   public int getGridWidth() {
      return 1;
   }

   @Override
   public int getGridHeight() {
      return 1;
   }

   @Override
   public int getSize() {
      return 4;
   }

   @Override
   public RecipeBookType getRecipeBookType() {
      return this.recipeBookType;
   }

   @Override
   public boolean shouldMoveToInventory(int n) {
      return true;
   }

   static class PotionSlot extends Slot {
      public PotionSlot(Container container, int n, int n2, int n3) {
         super(container, n, n2, n3);
      }

      public boolean mayPlace(ItemStack itemStack) {
         return super.mayPlace(itemStack);
      }

      @Override
      public int getMaxStackSize(ItemStack stack) {
         return 64;
      }
      @Override
      public int getMaxStackSize() {
         return 64;
      }

      @Override
      public void onTake(Player player, ItemStack itemStack) {
         super.onTake(player, itemStack);
      }
   }
}
