package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseFishingRod;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class QoaikuFishingRod extends ItemToolBaseFishingRod {
	public QoaikuFishingRod() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(1300), 3.5F, 2F);
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.QOAIKU_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}


	public int getEnchantmentValue() {
		return 23;
	}
}
