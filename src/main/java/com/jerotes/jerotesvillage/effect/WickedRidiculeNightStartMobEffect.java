package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class WickedRidiculeNightStartMobEffect extends BaseMobEffectTick {
	public WickedRidiculeNightStartMobEffect() {
		super(MobEffectCategory.HARMFUL, -9960960);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.wicked_ridicule_night_start";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);

		// 只在客户端运行时
		MobEffectInstance effectInstance = livingEntity.getEffect(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get());
		if (effectInstance != null) {
			if (livingEntity.level().isClientSide()) {
				WorldEvent.allChanged();
			}
		}
	}

}
