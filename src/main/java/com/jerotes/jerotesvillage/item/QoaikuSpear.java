package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class QoaikuSpear extends ItemToolBaseSpear {
	public QoaikuSpear() {
		super(new Tier() {
				  public int getUses() {
					  return 1300;
				  }

				  public float getSpeed() {
					  return 8.0f;
				  }

				  public float getAttackDamageBonus() {
					  return 4.5f;
				  }

				  public int getLevel() {
					  return 3;
				  }

				  public int getEnchantmentValue() {
					  return 23;
				  }

				  public Ingredient getRepairIngredient() {
					  return Ingredient.of(new ItemStack(JerotesVillageItems.QOAIKU_METAL_INGOT.get()));
				  }
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, (1.0f / 1.1f) - 4f,
				1.1f, 1.125f, 0.6f, 3.0f, 7.5f, 6.5f, 5.1f, 10.0f, 4.6f, false);
	}
}


