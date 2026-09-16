package com.jerotes.jerotesvillage.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import com.jerotes.jerotes.effect.*;

public class FragranceMobEffect extends BaseMobEffect {
	public FragranceMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -411771);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.fragrance";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
