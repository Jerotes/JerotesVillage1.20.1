package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseAxe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class WonderlandDaggerAxe extends ItemToolBaseAxe {
	public WonderlandDaggerAxe() {
		super(new Tier() {
			public int getUses() {
				return 300;
			}

			public float getSpeed() {
				return 4.0F;
			}

			public float getAttackDamageBonus() {
				return 6f;
			}

			public int getLevel() {
				return 1;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
			}
		}, -1, 1.6f - 4f, new Properties().fireResistant());
	}
}
