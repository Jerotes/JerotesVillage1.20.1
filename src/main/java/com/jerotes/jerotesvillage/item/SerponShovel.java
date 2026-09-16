package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SerponShovel extends ItemToolBaseShovel {
	public SerponShovel() {
		super(new Tier() {
			public int getUses() {
				return 1600;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 4.5f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 22;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.SERPON_ALLOY_INGOT.get()));
			}
		}, -1, 1.4f - 4f, new Properties().fireResistant());
	}
}
