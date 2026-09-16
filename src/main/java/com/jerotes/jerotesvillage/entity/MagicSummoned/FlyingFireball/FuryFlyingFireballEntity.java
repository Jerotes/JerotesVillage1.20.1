package com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball;

import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesTameMobNonTameRandomTargetGoal;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class FuryFlyingFireballEntity extends FlyingFireballEntity implements Enemy {
	public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> true;

	public FuryFlyingFireballEntity(EntityType<? extends FuryFlyingFireballEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, SecondRounderEntity.class, false, PREY_SELECTOR) {
			protected AABB getTargetSearchArea(double d) {
				return this.mob.getBoundingBox().inflate(d, d, d);
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.25;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, AbstractVillager.class, false, PREY_SELECTOR) {
			protected AABB getTargetSearchArea(double d) {
				return this.mob.getBoundingBox().inflate(d, d, d);
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.25;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<HumanEntity>(this, HumanEntity.class, false, PREY_SELECTOR) {
			protected AABB getTargetSearchArea(double d) {
				return this.mob.getBoundingBox().inflate(d, d, d);
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.25;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, Player.class, false, PREY_SELECTOR) {
			protected AABB getTargetSearchArea(double d) {
				return this.mob.getBoundingBox().inflate(d, d, d);
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.25;
			}
		});
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, IronGolem.class, false, PREY_SELECTOR) {
			protected AABB getTargetSearchArea(double d) {
				return this.mob.getBoundingBox().inflate(d, d, d);
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.25;
			}
		});
	}

	protected MagicSummonedEntity getTameEntity() {
		FlyingFireballEntity flyingFireballEntity = this.convertTo(JerotesVillageEntityType.FLYING_FIREBALL.get(), true);
		ForgeEventFactory.onLivingConvert(this, flyingFireballEntity);
		return flyingFireballEntity;
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
