package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class FirepowerPourerChainsaw extends ItemToolBaseChainsaw {
	public FirepowerPourerChainsaw() {
		super(new Tier() {
			public int getUses() {
				return 800;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return 8.5f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 16;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}
		}, -1, 1.0f - 4f, new Properties().rarity(Rarity.UNCOMMON));
	}
}
