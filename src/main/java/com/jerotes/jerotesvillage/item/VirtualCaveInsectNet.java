package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class VirtualCaveInsectNet extends ItemToolBaseInsectNet {
	public VirtualCaveInsectNet() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(256), 2f, 1f);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesItems.HIGH_STRENGTH_STRING.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}
}
