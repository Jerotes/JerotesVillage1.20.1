package com.jerotes.jerotesvillage.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import com.jerotes.jerotes.effect.*;

public class ThrowingStubbornStoneMobEffect extends BaseMobEffect {
	public ThrowingStubbornStoneMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -7173774);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.throwing_stubborn_stone";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
