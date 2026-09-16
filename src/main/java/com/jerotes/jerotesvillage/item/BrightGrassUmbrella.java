package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class BrightGrassUmbrella extends ItemToolBaseUmbrella {
	public BrightGrassUmbrella() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(120));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.BRIGHT_GRASS.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public float getVerticalSpeed() {
		return 0.3f;
	}
	@Override
	public float getHorizontalSpeed() {
		return 0.3f;
	}
	@Override
	public float getVerticalSpeedLiquid() {
		return 0.15f;
	}
	@Override
	public float getHorizontalSpeedLiquid() {
		return 0.15f;
	}
}
