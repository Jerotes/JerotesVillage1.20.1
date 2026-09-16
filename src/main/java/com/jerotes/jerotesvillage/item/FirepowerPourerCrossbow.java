package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.VirtualCaveCrystalArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class FirepowerPourerCrossbow extends ItemToolBaseCrossbow {

	public FirepowerPourerCrossbow() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(600 * 3));
	}

	public int getEnchantmentValue() {
		return 16;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new VirtualCaveCrystalArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}
	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.IRON_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public float getShootingPower(ItemStack itemStack) {
		return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 2.25F : 4F;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 14;
	}

	@Override
	public int getChargeDurations(ItemStack p_40940_) {
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, p_40940_);
		return i == 0 ? 5 : 5 - i;
	}
}

