package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBallGlove;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class WoodenSlingshot extends ItemToolBaseThrowingBallGlove {
	public WoodenSlingshot() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(128), 1.4f);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(ItemTags.PLANKS) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
