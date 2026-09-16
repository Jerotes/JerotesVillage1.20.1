package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.AncientSecondRounderArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class AncientSecondRounderBow extends ItemToolBaseBow {
	public AncientSecondRounderBow() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(380 * 3));
	}

	public float getArrowSpeed(float f) {
		return f * 1.1f * 3.0F;
	}
	public int getEnchantmentValue() {
		return 15;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new AncientSecondRounderArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.IRON_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 17;
	}
}

