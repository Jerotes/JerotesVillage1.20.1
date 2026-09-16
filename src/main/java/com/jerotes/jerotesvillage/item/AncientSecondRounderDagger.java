package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.EntityAndItemFind;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class AncientSecondRounderDagger extends ItemToolBaseDagger {
	public AncientSecondRounderDagger() {
		super(new Tier() {
			public int getUses() {
				return 300;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.DAGGER);
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
		}, -1,
				EntityAndItemFind.getItemAttackSpeedFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.DAGGER) - 4f,
				new Properties());
	}
}
