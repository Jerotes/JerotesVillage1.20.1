package com.jerotes.jerotesvillage.effect;

import net.minecraft.world.effect.MobEffectCategory;
import com.jerotes.jerotes.effect.*;

public class BitterColdMobEffect extends BaseMobEffectTick {
	public BitterColdMobEffect() {
		super(MobEffectCategory.HARMFUL, -6973968);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.bitter_cold";
	}
}
