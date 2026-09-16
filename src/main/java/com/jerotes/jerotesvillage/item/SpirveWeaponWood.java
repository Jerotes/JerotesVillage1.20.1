package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class SpirveWeaponWood extends ItemToolBaseSword {
	public SpirveWeaponWood() {
		super(new Tier() {
			public int getUses() {
				return 32;
			}

			public float getSpeed() {
				return 2f;
			}

			public float getAttackDamageBonus() {
				return 3f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(ItemTags.PLANKS);
			}
		}, -1, 1f - 4f, new Item.Properties());
	}
}
