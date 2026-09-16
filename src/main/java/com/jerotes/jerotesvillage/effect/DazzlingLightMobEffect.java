package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectAllTick;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DazzlingLightMobEffect extends BaseMobEffectAllTick {
	public DazzlingLightMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 0xfff1b4);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.dazzling_light";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		if (!livingEntity.hasEffect(JerotesVillageMobEffects.FAIRNESS.get()) &&  !livingEntity.onGround() && !livingEntity.isInWater() && !livingEntity.noPhysics && livingEntity.getDeltaMovement().y > -1d) {
			livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, -0.5d, 0));
		}
		super.applyEffectTick(livingEntity, n);
	}
}

