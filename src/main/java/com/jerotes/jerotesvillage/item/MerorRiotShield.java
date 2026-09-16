package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class MerorRiotShield extends ItemToolBaseParryShield {
	public MerorRiotShield() {
		super(new Properties().stacksTo(1).durability(18800 * 3).fireResistant().rarity(Rarity.EPIC), 4f, 2.8f, 28, Ingredient.of(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()),
				20, 5, 1.5f, 8f, 1.2f);
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, level, entity, slot, selected);
		if (entity instanceof Player player) {
			if (selected) {
				if (!level.isClientSide) {
					if (player.getCooldowns().isOnCooldown(this)) {
						if (player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0) < 0.6 || player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0) < 0.9 && player.getRandom().nextFloat() < 0.03) {
							player.getCooldowns().removeCooldown(this);
						}
					}
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
