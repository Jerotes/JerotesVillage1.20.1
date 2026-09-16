package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZsieinFogMobEffect extends BaseMobEffectTick {
	public ZsieinFogMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -13357460);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.zsiein_fog";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
	}
}
