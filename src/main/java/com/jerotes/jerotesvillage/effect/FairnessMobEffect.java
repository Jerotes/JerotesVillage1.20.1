package com.jerotes.jerotesvillage.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import com.jerotes.jerotes.effect.*;

public class FairnessMobEffect extends BaseMobEffectTick {
	public FairnessMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 16741499);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.fairness";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
