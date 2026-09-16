package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SerponAxe extends ItemToolBaseAxe {
	public SerponAxe() {
		super(new Tier() {
			public int getUses() {
				return 1600;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(12.5f, 1.2f, EntityAndItemFind.WeaponCategory.AXE);
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
			  }, -1, EntityAndItemFind.getItemAttackSpeedFromDPS(12.5f, 1.2f, EntityAndItemFind.WeaponCategory.AXE) - 4f,
				new Properties().fireResistant());
	}
}
