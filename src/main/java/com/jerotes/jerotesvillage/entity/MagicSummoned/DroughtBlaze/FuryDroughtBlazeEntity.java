package com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze;

import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesTameMobNonTameRandomTargetGoal;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class FuryDroughtBlazeEntity extends DroughtBlazeEntity implements Enemy {
	public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> true;

	public FuryDroughtBlazeEntity(EntityType<? extends FuryDroughtBlazeEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, SecondRounderEntity.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<HumanEntity>(this, HumanEntity.class, false, PREY_SELECTOR){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, Player.class, false, PREY_SELECTOR) {
			@Override
			public boolean canUse() {
				if (FuryDroughtBlazeEntity.this.level().getDifficulty() == Difficulty.PEACEFUL ||
						this.target != null && FuryDroughtBlazeEntity.this.distanceTo(this.target) > 15) {
					return false;
				}
				return super.canUse();
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, IronGolem.class, false, PREY_SELECTOR));
	}


	protected MagicSummonedEntity getTameEntity() {
		DroughtBlazeEntity droughtBlazeEntity = this.convertTo(JerotesVillageEntityType.DROUGHT_BLAZE.get(), true);
		ForgeEventFactory.onLivingConvert(this, droughtBlazeEntity);
		return droughtBlazeEntity;
	}
	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}
	@Override
	public boolean shouldDropExperience() {
		return true;
	}
	@Override
	protected boolean shouldDropLoot() {
		return true;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.isTame();
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return !this.isTame();
	}
}

