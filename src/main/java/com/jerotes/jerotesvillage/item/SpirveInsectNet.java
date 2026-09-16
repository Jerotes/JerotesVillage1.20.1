package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class SpirveInsectNet extends ItemToolBaseInsectNet {
	public SpirveInsectNet() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(59), 2f, 1f);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.STRING) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}


	@Override
	public int getEnchantmentValue() {
		return 15;
	}
}
