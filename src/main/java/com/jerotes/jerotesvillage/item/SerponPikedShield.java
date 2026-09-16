package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseParryShield;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class SerponPikedShield extends ItemToolBaseParryShield {
	public SerponPikedShield() {
		super(new Item.Properties().stacksTo(1).durability(1600 * 3).fireResistant().rarity(Rarity.COMMON), 6f, 2.8f, 22, Ingredient.of(JerotesVillageItems.SERPON_ALLOY_INGOT.get())	, 35, 4, 1.3f, 5.5f, 1.1f);
	}
	public boolean isMeleeWeapon() {
		return true;
	}
}
