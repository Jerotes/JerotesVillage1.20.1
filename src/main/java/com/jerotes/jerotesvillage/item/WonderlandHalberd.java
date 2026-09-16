package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class WonderlandHalberd extends ItemToolBasePike {
	public WonderlandHalberd() {
		super(new Tier() {
			public int getUses() {
				return 300;
			}

			public float getSpeed() {
				return 4.0F;
			}

			public float getAttackDamageBonus() {
				return 9f;
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
		},  new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, 1.0f - 4f, 1.0f, 0.8f,
				0.9f, 1.1f, 1.5f,
				JerotesSoundEvents.SPEAR_ATTACK,
				JerotesSoundEvents.SPEAR_HIT,
				JerotesSoundEvents.SPEAR_HIT,
				2.0F, 4.5F, 2.0F, 6.5F, 0.125f, 0.5F);
	}
}
