package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBrush;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpirveDuster extends ItemToolBaseBrush {
	public SpirveDuster() {
		super(new Properties().durability(32), 3.5f, 1.2f);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}


	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.FEATHER) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
