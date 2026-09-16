package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpirveScissors extends ItemToolBaseShears {
	public SpirveScissors() {
		super(new Properties().durability(250), 4.0f,1.3f);
	}

	@Override
	public int getEnchantmentValue() {
		return 14;
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.IRON_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
