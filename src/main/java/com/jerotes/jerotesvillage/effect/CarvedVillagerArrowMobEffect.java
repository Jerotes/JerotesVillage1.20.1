package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectTick;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;

public class CarvedVillagerArrowMobEffect extends BaseMobEffectTick {
	public CarvedVillagerArrowMobEffect() {
		super(MobEffectCategory.NEUTRAL, -11763826);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.carved_villager_arrow";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		int effectLevel = n + 1;
		DamageSource damageSource = livingEntity.level().damageSources().cramming();
		if (livingEntity.getType() == EntityType.VILLAGER || livingEntity.getType() == EntityType.WANDERING_TRADER || EntityFactionFind.isCarved(livingEntity.getType())  || livingEntity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			livingEntity.heal(6 * effectLevel);
		}
		else if (livingEntity.getMobType() == MobType.UNDEAD) {
			livingEntity.hurt(damageSource, 5 * effectLevel);
		}
		else if (EntityFactionFind.isRaider(livingEntity) || livingEntity.getType() == EntityType.VEX) {
			livingEntity.hurt(damageSource, 7 * effectLevel);
		}
		else if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
			if (mob.getTarget().getType() == EntityType.VILLAGER || mob.getTarget().getType() == EntityType.WANDERING_TRADER || EntityFactionFind.isCarved(mob.getTarget().getType())  || mob.getTarget() instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500))
			{
				livingEntity.hurt(damageSource, 3 * effectLevel);
			}
		}
	}
}

