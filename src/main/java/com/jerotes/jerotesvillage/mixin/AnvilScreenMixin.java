package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.inventory.AnvilMenuInterface;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
    @Shadow
    private EditBox name;
    private static final ResourceLocation VILLAGER_METAL_ANVIL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/villager_metal_anvil.png");
    public AnvilScreenMixin(AnvilMenu anvilMenu, Inventory inventory, Component component, ResourceLocation resourceLocation) {
        super(anvilMenu, inventory, component, resourceLocation);
    }

    @Inject(method = "renderBg", at = @At("HEAD"), cancellable = true)
    protected void renderBg(GuiGraphics p_283345_, float p_283412_, int p_282871_, int p_281306_, CallbackInfo ci) {
        if (this.menu instanceof AnvilMenuInterface anvilMenuInterface && anvilMenuInterface.isVillagerMetal()) {
            p_283345_.blit(VILLAGER_METAL_ANVIL_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            this.renderErrorIcon(p_283345_, this.leftPos, this.topPos);
            p_283345_.blit(VILLAGER_METAL_ANVIL_LOCATION, this.leftPos + 59, this.topPos + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
            ci.cancel();
        }
    }

    @Inject(method = "renderFg", at = @At("HEAD"), cancellable = true)
    public void renderFg(GuiGraphics p_283449_, int p_283263_, int p_281526_, float p_282957_, CallbackInfo ci) {
        if (this.menu instanceof AnvilMenuInterface anvilMenuInterface && anvilMenuInterface.isVillagerMetal()) {
            this.name.render(p_283449_, p_283263_, p_281526_, p_282957_);
            ci.cancel();
        }
    }

    @Inject(method = "renderErrorIcon", at = @At("HEAD"), cancellable = true)
    protected void renderErrorIcon(GuiGraphics p_282905_, int p_283237_, int p_282237_, CallbackInfo ci) {
        if (this.menu instanceof AnvilMenuInterface anvilMenuInterface && anvilMenuInterface.isVillagerMetal()) {
            if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
                p_282905_.blit(VILLAGER_METAL_ANVIL_LOCATION, p_283237_ + 99, p_282237_ + 45, this.imageWidth, 0, 28, 21);
            }
            ci.cancel();
        }
    }
}