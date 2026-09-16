package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;

public class MerorWantedOrderMobEffect extends BaseMobEffectAllTick {
	public MerorWantedOrderMobEffect() {
		super(MobEffectCategory.HARMFUL, -7918531);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.meror_wanted_order";
	}
}
