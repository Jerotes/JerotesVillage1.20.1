package com.jerotes.jerotesvillage.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBaseUpgradeSmithingTemplate extends Item {
	public ItemBaseUpgradeSmithingTemplate(Properties properties) {
		super(properties);
	}

	@Override
	public Component getName(ItemStack itemStack) {
		return Component.translatable("item.minecraft.smithing_template");
	}
}
