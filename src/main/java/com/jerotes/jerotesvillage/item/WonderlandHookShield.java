package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseParryShield;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class WonderlandHookShield extends ItemToolBaseParryShield {
	public WonderlandHookShield() {
		super(new Properties().stacksTo(1).durability(300 * 3).rarity(Rarity.COMMON), 5f, 1.2f, 15,
				Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 30, 3,
				1.05f, 5.0f, 1.2f);
	}
	public boolean isMeleeWeapon() {
		return true;
	}
}
