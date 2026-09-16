package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import com.jerotes.jerotes.util.Main;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BrightLandHeartMobEffect extends BaseMobEffectTick {
	public BrightLandHeartMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -10671331);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.bright_land_heart";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		Main.getJerotesPersistentData(livingEntity).putDouble("jerotesvillage_bright_land_heart", 10);
	}
}
