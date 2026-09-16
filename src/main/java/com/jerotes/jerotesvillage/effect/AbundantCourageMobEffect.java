package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class AbundantCourageMobEffect extends BaseMobEffect {
	public AbundantCourageMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -2097142);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.abundant_courage";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
