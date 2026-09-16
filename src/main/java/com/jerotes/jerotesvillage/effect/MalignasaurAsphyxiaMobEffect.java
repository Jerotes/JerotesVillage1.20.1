package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import com.jerotes.jerotes.effect.*;

public class MalignasaurAsphyxiaMobEffect extends BaseMobEffectTick {
	public MalignasaurAsphyxiaMobEffect() {
		super(MobEffectCategory.HARMFUL, -13865258);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.malignasaur_asphyxia";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		int effectLevel = n + 1;
		if (livingEntity.getHealth() > 0f) {
			if (livingEntity.level() instanceof ServerLevel _level) {
				_level.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 1, 0, 0, 0, 0);
			}
			if (livingEntity.getMobType() != MobType.WATER && !livingEntity.hasEffect(MobEffects.WATER_BREATHING) && !livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
				livingEntity.hurt(livingEntity.damageSources().inWall(), effectLevel * 2.0f);
			}
		}
	}
}
