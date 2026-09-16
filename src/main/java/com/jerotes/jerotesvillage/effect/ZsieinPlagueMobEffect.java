package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZsieinPlagueMobEffect extends BaseMobEffectTick {
	public ZsieinPlagueMobEffect() {
		super(MobEffectCategory.HARMFUL, -11713364);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.zsiein_plague";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
