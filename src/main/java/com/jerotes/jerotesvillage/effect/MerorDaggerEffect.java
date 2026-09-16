package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;

public class MerorDaggerEffect extends BaseMobEffect {
	public MerorDaggerEffect() {
		super(MobEffectCategory.BENEFICIAL, -141091);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.meror_dagger";
	}
}
