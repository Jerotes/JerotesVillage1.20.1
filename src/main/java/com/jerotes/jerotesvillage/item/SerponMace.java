package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseHammer;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SerponMace extends ItemToolBaseHammer {
	public SerponMace() {
		super(new Tier() {
			public int getUses() {
				return 1600;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(12.5f, 1.2f, EntityAndItemFind.WeaponCategory.HAMMER);
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
			  }, -1, EntityAndItemFind.getItemAttackSpeedFromDPS(12.5f, 1.2f, EntityAndItemFind.WeaponCategory.HAMMER) - 4f,
				new Properties().fireResistant());
	}
}
