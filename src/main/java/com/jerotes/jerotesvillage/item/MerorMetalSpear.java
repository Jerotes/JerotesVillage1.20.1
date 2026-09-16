package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MerorMetalSpear extends ItemToolBaseSpear {
	public MerorMetalSpear() {
		super(new Tier() {
				  public int getUses() {
					  return 5000;
				  }

				  public float getSpeed() {
					  return 12f;
				  }

				  public float getAttackDamageBonus() {
					  return 7f;
				  }

				  public int getLevel() {
					  return 4;
				  }

				  public int getEnchantmentValue() {
					  return 24;
				  }

				  public Ingredient getRepairIngredient() {
					  return Ingredient.of(new ItemStack(JerotesVillageItems.MEROR_METAL_INGOT.get()));
				  }
			  }, new Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE), -1f, (1.0f / 1.05f) - 4f,
				1.05f, 1.6f, 0.4f, 4.5f, 10.0f, 9.0f, 5.1f, 13.75f, 4.6f);
	}
}


