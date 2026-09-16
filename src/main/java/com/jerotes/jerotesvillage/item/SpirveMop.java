package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Vanishable;

public class SpirveMop extends ItemToolBaseBrush implements Vanishable {
	public SpirveMop() {
		super(new Properties().durability(32), 4.0f, 1.0f);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.STRING) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}

}
