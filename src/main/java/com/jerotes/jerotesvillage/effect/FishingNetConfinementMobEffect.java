package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FishingNetConfinementMobEffect extends BaseMobEffectAllTick {
	public FishingNetConfinementMobEffect() {
		super(MobEffectCategory.HARMFUL, -3487818);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.fishing_net_confinement";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
