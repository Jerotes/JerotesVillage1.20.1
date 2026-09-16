package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBallGlove;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class RamblerLeatherGlove extends ItemToolBaseThrowingBallGlove {
	public RamblerLeatherGlove() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(1800), 1.25f);
	}

	@Override
	public int getEnchantmentValue() {
		return 12;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.RAMBLER_LEATHER.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
