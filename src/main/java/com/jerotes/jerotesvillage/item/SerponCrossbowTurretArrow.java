package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseArrow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.SerponCrossbowTurretArrowEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SerponCrossbowTurretArrow extends ItemToolBaseArrow {
	public SerponCrossbowTurretArrow() {
		super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
		return new SerponCrossbowTurretArrowEntity(level, livingEntity, itemStack.copyWithCount(1));
	}

	@Override
	public float getBaseDamage() {
		return 7.0f;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}
