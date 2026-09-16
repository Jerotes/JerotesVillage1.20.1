package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectTick;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BitterColdProtectionMobEffect extends BaseMobEffectTick {
	public BitterColdProtectionMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 0x066b6b);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.bitter_cold_protection";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
