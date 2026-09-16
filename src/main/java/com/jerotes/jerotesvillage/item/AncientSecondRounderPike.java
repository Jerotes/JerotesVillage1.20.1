package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import com.jerotes.jerotes.util.EntityAndItemFind;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class AncientSecondRounderPike extends ItemToolBasePike {
	public AncientSecondRounderPike() {
		super(new Tier() {
				  public int getUses() {
					  return 300;
				  }

				  public float getSpeed() {
					  return 6f;
				  }

				  public float getAttackDamageBonus() {
					  return EntityAndItemFind.getItemAttackDamageFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.PIKE);
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
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f,
				EntityAndItemFind.getItemAttackSpeedFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.PIKE) - 4f,
				(1.0f / EntityAndItemFind.getItemAttackSpeedFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.PIKE)),
				0.4f,
				0.9f, 1.1f, 4f,
				JerotesSoundEvents.SPEAR_ATTACK,
				JerotesSoundEvents.SPEAR_HIT,
				JerotesSoundEvents.SPEAR_WOOD_HIT,
				3.5f, 7.5f, 3.5f, 9.5f, 0.25f, 0.85f);
	}
}
