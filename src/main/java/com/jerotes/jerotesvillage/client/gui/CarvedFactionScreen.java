package com.jerotes.jerotesvillage.client.gui;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.inventory.CarvedFactionMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarvedFactionScreen extends AbstractContainerScreen<CarvedFactionMenu> {
   private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/carved_faction.png");
   private final Level world;
   private final Player player;
   private final CarvedFactionMenu container;

   public CarvedFactionScreen(CarvedFactionMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.container = container;
      this.world = container.world;
      this.player = container.entity;
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      //铜刻佣兵团
      if (container.hasCopperCavedCompany()) {
         ResourceLocation CopperCavedCompany = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/faction/copper_carved_company.png");
         //头像
         guiGraphics.blit(CopperCavedCompany, this.leftPos + 12, this.topPos + 8, 0, 0, 16, 16, 16, 16);
         //名称
         int textX = this.leftPos + 11;
         int textY = this.topPos + 7;
         int textWidth = 69;
         int textHeight = 18;
         if (mouseX >= textX && mouseX <= textX + textWidth && mouseY >= textY && mouseY <= textY + textHeight) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("message.jerotesvillage.copper_carved_company").withStyle(ChatFormatting.GOLD));
            guiGraphics.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
         }
         //好感
         int CopperCavedCompanyInt = container.getCopperCavedCompanyInt();
         int CopperCavedCompanyColor = 16764928;
         if (CopperCavedCompanyInt > 500) {
            CopperCavedCompanyColor = 1965824;
         }
         else if (CopperCavedCompanyInt < -500) {
            CopperCavedCompanyColor = 8519680;
         }
         String CopperCavedCompanyString = String.valueOf(CopperCavedCompanyInt);
         if (CopperCavedCompanyInt >= 0) {
            CopperCavedCompanyString = "+" + CopperCavedCompanyInt;
         }
         guiGraphics.drawString(this.font, Component.literal(CopperCavedCompanyString), this.leftPos + 43, this.topPos + 12, CopperCavedCompanyColor, false);
      }
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1, 1, 1, 1);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      guiGraphics.blit(LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
      RenderSystem.disableBlend();
   }

   @Override
   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer();
         return true;
      }
      return super.keyPressed(key, b, c);
   }

   @Override
   public void containerTick() {
      super.containerTick();
   }

   @Override
   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }

   @Override
   public void init() {
      super.init();
   }
}
