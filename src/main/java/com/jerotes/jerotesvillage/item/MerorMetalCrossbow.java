package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorMetalArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class MerorMetalCrossbow extends ItemToolBaseCrossbow {

	public MerorMetalCrossbow() {
		super(new Properties().stacksTo(1).durability(5000 * 3).fireResistant().rarity(Rarity.RARE));
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new MerorMetalArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}
	public int getEnchantmentValue() {
		return 24;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.MEROR_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public float getShootingPower(ItemStack itemStack) {
		return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 2.5F : 4.5F;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 17;
	}
}

