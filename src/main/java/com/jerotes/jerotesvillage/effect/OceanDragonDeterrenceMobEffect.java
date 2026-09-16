package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.*;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class OceanDragonDeterrenceMobEffect extends BaseMobEffectAllTick {
	public OceanDragonDeterrenceMobEffect() {
		super(MobEffectCategory.HARMFUL, -5576705);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.ocean_dragon_deterrence";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		if (livingEntity.onGround() || livingEntity.isInWater()) {
			livingEntity.makeStuckInBlock(livingEntity.getBlockStateOn(), new Vec3(0.25, 0.05, 0.25));
		}
		super.applyEffectTick(livingEntity, n);
	}
}
