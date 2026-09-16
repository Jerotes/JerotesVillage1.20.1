package com.jerotes.jerotesvillage.client.gui;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.recipes.TabletPressMachineRecipeBookComponent;
import com.jerotes.jerotesvillage.inventory.TabletPressMachineMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TabletPressMachineScreen extends AbstractContainerScreen<TabletPressMachineMenu> implements RecipeUpdateListener {
   private static final ResourceLocation PROGRESS_SPRITE = new ResourceLocation(JerotesVillage.MODID, "container/tablet_press_machine/tablet_press_machine_progress");
   private static final ResourceLocation PROGRESS_SPRITES = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/tablet_press_machine/tablet_press_machine_progress.png");
   private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/tablet_press_machine.png");

   private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");

   private final TabletPressMachineRecipeBookComponent recipeBookComponent = new TabletPressMachineRecipeBookComponent();
   private boolean widthTooNarrow;

   public TabletPressMachineScreen(TabletPressMachineMenu tabletPressMachineMenu, Inventory inventory, Component component) {
      super(tabletPressMachineMenu, inventory, component);
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.recipeBookComponent.tick();
   }

   protected void init() {
      super.init();
      this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
      this.widthTooNarrow = this.width < 379;
      this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookMenu)this.menu);
      this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);

      //1.20.4↑//
//      this.addRenderableWidget(new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, button -> {
//         this.recipeBookComponent.toggleVisibility();
//         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
//         button.setPosition(this.leftPos + 20, this.height / 2 - 49);
//      }));
      //1.20.1//
      this.addRenderableWidget(new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (button) -> {
         this.recipeBookComponent.toggleVisibility();
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         button.setPosition(this.leftPos + 20, this.height / 2 - 49);
      }));
   }

   public void render(GuiGraphics guiGraphics, int n, int n2, float f) {
      if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
         //1.20.4↑//
         //this.renderBackground(guiGraphics, n, n2, f);
         //1.20.1//
         this.renderBg(guiGraphics, f, n, n2);
         this.recipeBookComponent.render(guiGraphics, n, n2, f);
      } else {
         super.render(guiGraphics, n, n2, f);
         this.recipeBookComponent.render(guiGraphics, n, n2, f);
         this.recipeBookComponent.renderGhostRecipe(guiGraphics, this.leftPos, this.topPos, true, f);
      }
      this.renderTooltip(guiGraphics, n, n2);
      this.recipeBookComponent.renderTooltip(guiGraphics, this.leftPos, this.topPos, n, n2);
   }

   protected void renderBg(GuiGraphics guiGraphics, float f, int n, int n2) {
      int i = this.leftPos;
      int j = this.topPos;
      guiGraphics.blit(LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

      int i1 = this.menu.getMakingTicks();
      if (i1 > 0) {
         int j1 = (int)(32.0F * (1.0F - (float)i1 / 600.0F));
         if (j1 > 0) {
            //1.20.4↑//
            //guiGraphics.blitSprite(PROGRESS_SPRITE, 64, 32, 0, 0, i + 55, j + 26, 64, j1);
            //1.20.1//
            guiGraphics.blit(PROGRESS_SPRITES, i + 55, j + 26, 0, 0, 64, j1, 64, 32);
         }
      }
   }

   @Override
   public boolean mouseClicked(double d, double d2, int n) {
      if (this.recipeBookComponent.mouseClicked(d, d2, n)) {
         this.setFocused(this.recipeBookComponent);
         return true;
      }
      if (this.widthTooNarrow && this.recipeBookComponent.isVisible()) {
         return true;
      }
      return super.mouseClicked(d, d2, n);
   }

   @Override
   protected void slotClicked(Slot slot, int n, int n2, ClickType clickType) {
      super.slotClicked(slot, n, n2, clickType);
      this.recipeBookComponent.slotClicked(slot);
   }

   @Override
   public boolean keyPressed(int n, int n2, int n3) {
      if (this.recipeBookComponent.keyPressed(n, n2, n3)) {
         return false;
      }
      return super.keyPressed(n, n2, n3);
   }

   @Override
   protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
      boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + this.imageWidth) || d2 >= (double)(n2 + this.imageHeight);
      return this.recipeBookComponent.hasClickedOutside(d, d2, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, n3) && bl;
   }

   @Override
   public boolean charTyped(char c, int n) {
      if (this.recipeBookComponent.charTyped(c, n)) {
         return true;
      }
      return super.charTyped(c, n);
   }

   @Override
   public void recipesUpdated() {
      this.recipeBookComponent.recipesUpdated();
   }

   @Override
   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookComponent;
   }
}