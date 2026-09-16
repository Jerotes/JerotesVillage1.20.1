package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectAllTick;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class SerponCredentialMobEffect extends BaseMobEffectAllTick {
	public SerponCredentialMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -1930752);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.serpon_credential";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		if (n >= 1) {
			if (livingEntity instanceof Player player) {
				if (FMLEnvironment.dist == Dist.CLIENT) {
					player.getAbilities().flying = true;
				}
			}
		}
	}
}
