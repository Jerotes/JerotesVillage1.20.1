package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MerorMetalPike extends ItemToolBasePike {
	public MerorMetalPike() {
		super(new Tier() {
				  public int getUses() {
					  return 5000;
				  }

				  public float getSpeed() {
					  return 12f;
				  }

				  public float getAttackDamageBonus() {
					  return EntityAndItemFind.getItemAttackDamageFromDPS(22.5f, 1.125f, EntityAndItemFind.WeaponCategory.PIKE);
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
			  }, new Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE), -1f,
				EntityAndItemFind.getItemAttackSpeedFromDPS(22.5f, 1.125f, EntityAndItemFind.WeaponCategory.PIKE) - 4f,
				(1.0f / EntityAndItemFind.getItemAttackSpeedFromDPS(22.5f, 1.125f, EntityAndItemFind.WeaponCategory.PIKE)),
				0.2f,
				0.9f, 1.1f, 4f,
				JerotesSoundEvents.SPEAR_ATTACK,
				JerotesSoundEvents.SPEAR_HIT,
				JerotesSoundEvents.SPEAR_HIT,
				3.5f, 7.5f, 3.5f, 9.5f, 0.25f, 0.85f);
	}

	public int getBlockReduction() {
		return 75;
	}
}
