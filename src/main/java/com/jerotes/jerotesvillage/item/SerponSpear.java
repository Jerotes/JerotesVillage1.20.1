package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SerponSpear extends ItemToolBaseSpear {
	public SerponSpear() {
		super(new Tier() {
				  public int getUses() {
					  return 1600;
				  }

				  public float getSpeed() {
					  return 12f;
				  }

				  public float getAttackDamageBonus() {
					  return 3f;
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
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(), -1f, (1.0f / 0.65f) - 4f,
				0.65f, 0.95f, 0.6f, 2.5f, 8.0f, 6.75f, 5.1f, 11.25f, 4.6f);
	}
}


