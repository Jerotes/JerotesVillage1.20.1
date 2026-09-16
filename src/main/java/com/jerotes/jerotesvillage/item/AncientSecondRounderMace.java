package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.EntityAndItemFind;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class AncientSecondRounderMace extends ItemToolBaseHammer {
	public AncientSecondRounderMace() {
		super(new Tier() {
			public int getUses() {
				return 300;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return EntityAndItemFind.getItemAttackDamageFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.HAMMER);
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
				EntityAndItemFind.getItemAttackSpeedFromDPS(11.5f, 1.0f, EntityAndItemFind.WeaponCategory.HAMMER) - 4f,
				new Properties());
	}
}
