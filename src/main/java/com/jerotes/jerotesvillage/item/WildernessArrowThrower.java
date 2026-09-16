package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;

public class WildernessArrowThrower extends ItemToolBaseBow {
	public WildernessArrowThrower() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(400 * 3));
	}

	public int getEnchantmentValue() {
		return 12;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		Arrow arrow = new Arrow(livingEntity.level(), livingEntity
				//1.20.4↑//
				//, itemStack
		);
		arrow.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 120));
		return arrow;
	}
	public float getArrowSpeed(float f) {
		return f * 1.05f * 3.0F;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.SPEAR;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.WILDERNESS_WOOD_CORE.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 20;
	}
}

