package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectSlowTick;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MerorStimulinEffect extends BaseMobEffectSlowTick {
	public MerorStimulinEffect() {
		super(MobEffectCategory.BENEFICIAL, -28257);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.meror_stimulin";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		int effectLevel = 1;
		if (livingEntity.getHealth() > livingEntity.getMaxHealth()/2) {
			livingEntity.heal( effectLevel * 0.25f);
		}
		if (livingEntity.getHealth() <= livingEntity.getMaxHealth()/2) {
			livingEntity.heal( effectLevel * 0.5f);
		}
	}
}
