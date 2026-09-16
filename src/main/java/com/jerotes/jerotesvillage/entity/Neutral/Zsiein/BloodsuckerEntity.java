package com.jerotes.jerotesvillage.entity.Neutral.Zsiein;

import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.goal.BloodsuckerMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.goal.ZsieinFollowFlockLeaderGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BloodsuckerEntity extends ZsieinEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();

	public BloodsuckerEntity(EntityType<? extends BloodsuckerEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(2.5f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new BloodsuckerMeleeAttackGoal(this, 1.3, false));
		this.goalSelector.addGoal(3, new ZsieinRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(4, new ZsieinFollowFlockLeaderGoal(this));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BLOODSUCKER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BLOODSUCKER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BLOODSUCKER_DEATH;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.4f;
	}

	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		return list;
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.updateNoActionTime();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 2.5;
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(6);
			this.setAnimationState("attack");
		}
		double healthOldMain = 0;
		if (entity instanceof LivingEntity livingEntity) {
			healthOldMain = livingEntity.getHealth();
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.BLOODSUCKER_ATTACK, 1.0f, 1.0f);
			}
			if (entity instanceof LivingEntity livingEntity) {
				float base = (float) (0.25f + Math.min(WorldEvent.ZsieinAffinityGet(this.level()) / 1000f, 2f));
				double healthNewMain = livingEntity.getHealth();
				if ((float) (healthOldMain - healthNewMain) * base > 0) {
					this.heal((float) (healthOldMain - healthNewMain) * base);
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < WorldEvent.ZsieinAffinityGet(this.level()) / 20f; ++i) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN.get(), livingEntity.getRandomX(1f), livingEntity.getRandomY(), livingEntity.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
					}
				}
			}
		}
		return bl;
	}
}