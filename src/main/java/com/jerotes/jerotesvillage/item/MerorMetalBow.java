package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorMetalArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class MerorMetalBow extends ItemToolBaseBow {
	public MerorMetalBow() {
		super(new Properties().stacksTo(1).durability(5000 * 3).fireResistant().rarity(Rarity.RARE));
	}

	public int getEnchantmentValue() {
		return 24;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new MerorMetalArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}
	public float getArrowSpeed(float f) {
		return f * 1.4f * 3.0F;
	}
	public float getArrowInaccuracy() {
		return 0.5F;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.MEROR_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 25;
	}
}

