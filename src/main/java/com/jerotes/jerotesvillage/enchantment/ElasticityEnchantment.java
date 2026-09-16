package com.jerotes.jerotesvillage.enchantment;

import com.jerotes.jerotesvillage.init.JerotesVillageEnchantments;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBallGlove;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;


public class ElasticityEnchantment extends Enchantment {
	public ElasticityEnchantment(EquipmentSlot... slots) {
		super(Rarity.COMMON, JerotesVillageEnchantments.THROWING_BALL_ABOUT, slots);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int getMinCost(int n) {
		return 1 + (n - 1) * 10;
	}

	@Override
	public int getMaxCost(int n) {
		return 50;
	}

	public boolean isTradeable() {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
		return itemstack.getItem() instanceof ItemToolBaseThrowingBall || itemstack.getItem() instanceof ItemToolBaseThrowingBallGlove;
	}
}
