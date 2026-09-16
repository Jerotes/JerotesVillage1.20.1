package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class QoaikuPike extends ItemToolBasePike {
	public QoaikuPike() {
		super(new Tier() {
				  public int getUses() {
					  return 1300;
				  }

				  public float getSpeed() {
					  return 8.0f;
				  }

				  public float getAttackDamageBonus() {
					  return EntityAndItemFind.getItemAttackDamageFromDPS(12.5f, 0.9f, EntityAndItemFind.WeaponCategory.PIKE);
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
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f,
				EntityAndItemFind.getItemAttackSpeedFromDPS(12.5f, 0.9f, EntityAndItemFind.WeaponCategory.PIKE) - 4f,
				(1.0f / EntityAndItemFind.getItemAttackSpeedFromDPS(12.5f, 0.9f, EntityAndItemFind.WeaponCategory.PIKE)),
				0.2f,
				0.9f, 1.1f, 4f,
				JerotesSoundEvents.SPEAR_ATTACK,
				JerotesSoundEvents.SPEAR_HIT,
				JerotesSoundEvents.SPEAR_HIT,
				3.5f, 7.5f, 3.5f, 9.5f, 0.25f, 0.85f);
	}
}