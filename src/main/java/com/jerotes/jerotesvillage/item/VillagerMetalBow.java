package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.CarvedVillagerSpectralArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class VillagerMetalBow extends ItemToolBaseBow {
	public VillagerMetalBow() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(450 * 3));
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
	public float getArrowSpeed(float f) {
		return f * 1.2f * 3.0F;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.VILLAGER_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 18;
	}
}

