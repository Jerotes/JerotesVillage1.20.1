package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBrush;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public class SpirveBroom extends ItemToolBaseBrush {
	public SpirveBroom() {
		super(new Properties().durability(32), 4.0f, 1.0f);
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
		return itemStack2.is(ItemTags.PLANKS) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
