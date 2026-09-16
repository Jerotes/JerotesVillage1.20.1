package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class WickedRidiculeNightMobEffect extends BaseMobEffectTick {
	public WickedRidiculeNightMobEffect() {
		super(MobEffectCategory.HARMFUL, -9960960);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.wicked_ridicule_night";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
