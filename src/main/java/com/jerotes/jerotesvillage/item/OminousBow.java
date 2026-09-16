package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.AnestheticArrowEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class OminousBow extends ItemToolBaseBow {
	public OminousBow() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(1000 * 3));
	}

	public int getEnchantmentValue() {
		return 16;
	}

	public float getArrowSpeed(float f) {
		return f * 1.1f * 3.0F;
	}
	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public float customBaseShootArrowChance() {
		return 0.2f;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new AnestheticArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.IRON_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 18;
	}
}

