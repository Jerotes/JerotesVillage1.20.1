package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffect;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VirosaansWindOfSlaughterMobEffect extends BaseMobEffect {
	public VirosaansWindOfSlaughterMobEffect() {
		super(MobEffectCategory.HARMFUL, 0x7d8d7d);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.virosaans_wind_of_slaughter";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get(),
					livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 1, 0, 0, 0, 0);
		}
	}
}
