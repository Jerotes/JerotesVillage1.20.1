package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MerorMinistrosityEntity extends MerorMachineEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();

	public MerorMinistrosityEntity(EntityType<? extends MerorMinistrosityEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 5;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
		this.goalSelector.addGoal(1, new JerotesAttackAvoidEntityGoal<LivingEntity>(this, LivingEntity.class, 8.0f, 0.6, 1.0));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.MEROR_MINISTROSITY_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_MINISTROSITY_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_MINISTROSITY_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEROR_MINISTROSITY_WALK, 0.15f, 1.0f);
	}
	@Override
	protected float getSoundVolume() {
		return 0.3f;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.55f;
	}

	private int sitTick = 0;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SitTick", this.sitTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.sitTick = compoundTag.getInt("SitTick");
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}

		if (this.isInSittingPose()) {
			this.idleAnimationState.stop();
		}
		else {
			this.sitAnimationState.stop();
		}
		if (this.isInSittingPose() && sitTick <= 0){
			this.sitTick = 40;
		}
		if (!this.isInSittingPose() && sitTick > 0){
			this.stopSitAnimationState.start(this.tickCount);
			this.sitTick = 0;
			this.sitAnimationState.stop();
			this.toSitAnimationState.stop();
		}

		if (this.sitTick == 40){
			this.toSitAnimationState.start(this.tickCount);
		}
		if (this.sitTick == 30){
			this.toSitAnimationState.stop();
			this.sitAnimationState.start(this.tickCount);
		}
		if (this.sitTick > 5){
			this.sitTick -= 1;
		}
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
	}
}
