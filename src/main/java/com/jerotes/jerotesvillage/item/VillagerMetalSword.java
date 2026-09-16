package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class VillagerMetalSword extends ItemToolBaseSword {
	public VillagerMetalSword() {
		super(new Tier() {
			public int getUses() {
				return 450;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(10.5f, 1.1f, EntityAndItemFind.WeaponCategory.SWORD);
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 17;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get()));
			}
			  }, -1,
				EntityAndItemFind.getItemAttackSpeedFromDPS(10.5f, 1.1f, EntityAndItemFind.WeaponCategory.SWORD) - 4f,
				new Properties());
	}
}
