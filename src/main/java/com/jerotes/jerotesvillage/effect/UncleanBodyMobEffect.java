package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class UncleanBodyMobEffect extends BaseMobEffectTick {
	public UncleanBodyMobEffect() {
		super(MobEffectCategory.HARMFUL, 10233904);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.unclean_body";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		int effectLevel = n + 1;
		if (livingEntity.getHealth() > 0f) {
			livingEntity.hurt(livingEntity.damageSources().magic(), effectLevel * 1.0f);
		}
	}
}
