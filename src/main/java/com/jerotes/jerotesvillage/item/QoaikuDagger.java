package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class QoaikuDagger extends ItemToolBaseDagger {
	public QoaikuDagger() {
		super(new Tier() {
			public int getUses() {
				return 1300;
			}

			public float getSpeed() {
				return 8.0f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(12.5f, 0.9f,
						EntityAndItemFind.WeaponCategory.DAGGER);
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
			  }, -1, EntityAndItemFind.getItemAttackSpeedFromDPS(12.5f, 0.9f,
						EntityAndItemFind.WeaponCategory.DAGGER) - 4f,
				new Properties());
	}
}
