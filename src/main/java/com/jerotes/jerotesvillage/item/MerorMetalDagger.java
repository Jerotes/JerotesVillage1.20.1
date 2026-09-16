package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MerorMetalDagger extends ItemToolBaseDagger {
	public MerorMetalDagger() {
		super(new Tier() {
			public int getUses() {
				return 5000;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(22.5f, 1.125f,
						EntityAndItemFind.WeaponCategory.DAGGER);
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
		}, -1, EntityAndItemFind.getItemAttackSpeedFromDPS(22.5f, 1.125f,
				EntityAndItemFind.WeaponCategory.DAGGER) - 4f, new Properties().fireResistant().rarity(Rarity.RARE));
	}
}
