package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.CarvedVillagerSpectralArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class VillagerMetalCrossbow extends ItemToolBaseCrossbow {

	public VillagerMetalCrossbow() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(450 * 3));
	}

	public int getEnchantmentValue() {
		return 17;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new CarvedVillagerSpectralArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}
	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.VILLAGER_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public float getShootingPower(ItemStack itemStack) {
		return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 2.0F : 3.5F;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 12;
	}
}

