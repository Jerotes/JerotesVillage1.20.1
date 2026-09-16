package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Shoot.Arrow.CarvedVillagerSpectralArrowEntity;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class CarvedVillagerSpectralArrow extends ItemToolBaseArrow {
	public CarvedVillagerSpectralArrow() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
		return new CarvedVillagerSpectralArrowEntity(level, livingEntity, itemStack.copyWithCount(1));
	}

	@Override
	public float getBaseDamage() {
		return 2.0f;
	}
}
