package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class AncientSecondRounderWarspear extends ItemToolBaseSpear {
	public AncientSecondRounderWarspear() {
		super(new Tier() {
				  public int getUses() {
					  return 300;
				  }

				  public float getSpeed() {
					  return 6f;
				  }

				  public float getAttackDamageBonus() {
					  return 3.5f;
				  }

				  public int getLevel() {
					  return 2;
				  }

				  public int getEnchantmentValue() {
					  return 15;
				  }

				  public Ingredient getRepairIngredient() {
					  return Ingredient.of(new ItemStack(Items.IRON_INGOT));
				  }
			  }, new Item.Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, (1.0f / 0.95f) - 4f,
				0.95f, 1.0f, 0.6f, 4.5f, 10.0f, 9.0f, 5.1f, 13.75f, 4.6f);
	}
}
