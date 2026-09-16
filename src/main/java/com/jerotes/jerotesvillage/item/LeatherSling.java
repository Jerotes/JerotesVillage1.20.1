package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBallGlove;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class LeatherSling extends ItemToolBaseThrowingBallGlove {
	public LeatherSling() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(64), 1.5f);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.LEATHER) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
