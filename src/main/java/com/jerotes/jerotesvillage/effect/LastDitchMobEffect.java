package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class LastDitchMobEffect extends BaseMobEffect {
	public LastDitchMobEffect() {
		super(MobEffectCategory.NEUTRAL, -6160384);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.last_ditch";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
