package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectTick;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;

public class WildernessGiantPoisonMobEffect extends BaseMobEffectTick {
	public WildernessGiantPoisonMobEffect() {
		super(MobEffectCategory.HARMFUL, -15507712);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.wilderness_giant_poison";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		int effectLevel = n + 1;
		float base = 1.0f;
		if (EntityFactionFind.isPlant(livingEntity)) {
			return;
		}
		if (livingEntity.getMobType() == MobType.UNDEAD) {
			base /= 2;
		}
		if (EntityFactionFind.isConstruct(livingEntity) || EntityFactionFind.isMachine(livingEntity)) {
			base /= 2;
		}
		if (EntityFactionFind.isOoze(livingEntity)) {
			base /= 2;
		}
		if (EntityFactionFind.isHumanoid(livingEntity)) {
			base *= 1.5f;
		}
		DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.POISON);
		if (EntityFactionFind.isConstruct(livingEntity) || EntityFactionFind.isMachine(livingEntity)) {
			damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.CORROSION);
		}
		if (livingEntity.getHealth() > 0f) {
			livingEntity.hurt(damageSource, effectLevel * base);
		}
	}
}
