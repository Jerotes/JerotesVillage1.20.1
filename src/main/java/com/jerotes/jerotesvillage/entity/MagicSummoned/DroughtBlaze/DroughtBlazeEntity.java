package com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze;

import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DroughtBlazeEntity extends MagicSummonedEntity implements JerotesEntity, RangedAttackMob, SpellUseEntity, FireAbsorptionEntity {
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(DroughtBlazeEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(DroughtBlazeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(DroughtBlazeEntity.class, EntityDataSerializers.INT);
	public DroughtBlazeEntity(EntityType<? extends DroughtBlazeEntity> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 25.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
		return builder;
	}

	protected void registerGoals() {
		super.registerGoals();
		if (!(this instanceof DroughtWildfireEntity)) {
			this.goalSelector.addGoal(1, new BlazeAttackGoal(this));
		}
		this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, HumanEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		//法术列表-旱火火球
		OtherSpellList.DroughtFireball(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLAZE_AMBIENT;
	}
	protected SoundEvent getHurtSound(DamageSource p_32235_) {
		return SoundEvents.BLAZE_HURT;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLAZE_DEATH;
	}
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public double FireAbsorptionPercentage() {
		return 60;
	}
	public boolean isSensitiveToWater() {
		return true;
	}

	private float allowedHeightOffset = 0.5F;
	private int nextHeightOffsetChangeTick;
	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	//动画
	public void setAnimTick(int n){
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick(){
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		return 0;
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			state.stop();
		}
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (!(this instanceof DroughtWildfireEntity)) {
			if (ANIM_STATE.equals(entityDataAccessor)) {
				if (this.level().isClientSide()) {
					switch (this.entityData.get(ANIM_STATE)) {
						case 0:
							this.stopAllAnimation();
							break;
					}
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	protected void customServerAiStep() {
		if (!(this instanceof DroughtWildfireEntity)) {
			--this.nextHeightOffsetChangeTick;
			if (this.nextHeightOffsetChangeTick <= 0) {
				this.nextHeightOffsetChangeTick = 100;
				this.allowedHeightOffset = (float) this.random.triangle(0.5D, 6.891D);
			}

			LivingEntity livingentity = this.getTarget();
			if (livingentity != null && livingentity.getEyeY() > this.getEyeY() + (double) this.allowedHeightOffset && this.canAttack(livingentity)) {
				Vec3 vec3 = this.getDeltaMovement();
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, ((double) 0.3F - vec3.y) * (double) 0.3F, 0.0D));
				this.hasImpulse = true;
			}
		}
		super.customServerAiStep();
	}
	public void aiStep() {
		if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
		}
		if (!(this instanceof DroughtWildfireEntity)) {
			if (this.level().isClientSide()) {
				if (this.random.nextInt(24) == 0 && !this.isSilent()) {
					this.level().playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
				}
				for (int i = 0; i < 2; ++i) {
					this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
				}
			}
		}
		super.aiStep();
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isFireDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	public boolean isOnFire() {
		return this.isCharged() && !(this instanceof DroughtWildfireEntity);
	}

	private boolean isCharged() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	void setCharged(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			by = (byte)(by | 1);
		} else {
			by &= -2;
		}
		this.entityData.set(DATA_FLAGS_ID, by);
	}

	static class BlazeAttackGoal extends Goal {
		private final DroughtBlazeEntity blaze;
		private int attackStep;
		private int attackTime;
		private int lastSeen;

		public BlazeAttackGoal(DroughtBlazeEntity p_32247_) {
			this.blaze = p_32247_;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity $$0 = this.blaze.getTarget();
			return $$0 != null && $$0.isAlive() && this.blaze.canAttack($$0);
		}

		public void start() {
			this.attackStep = 0;
		}

		public void stop() {
			this.blaze.setCharged(false);
			this.lastSeen = 0;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			--this.attackTime;
			LivingEntity target = this.blaze.getTarget();
			if (target != null) {
				boolean $$1 = this.blaze.getSensing().hasLineOfSight(target);
				if ($$1) {
					this.lastSeen = 0;
				} else {
					++this.lastSeen;
				}

				double distance = this.blaze.distanceToSqr(target);
				if (distance < 4.0) {
					if (!$$1) {
						return;
					}

					if (this.attackTime <= 0) {
						this.attackTime = 20;
						this.blaze.doHurtTarget(target);
					}

					this.blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
				}
				else if (distance < this.getFollowDistance() * this.getFollowDistance() && $$1) {
					if (this.attackTime <= 0) {
						++this.attackStep;
						if (this.attackStep == 1) {
							this.attackTime = 25;
							this.blaze.setCharged(true);
						} else if (this.attackStep <= 4) {
							this.attackTime = 6;
						} else {
							this.attackTime = 30;
							this.attackStep = 0;
							this.blaze.setCharged(false);
						}

						if (this.attackStep == 2) {
							double $$0 = this.blaze.distanceToSqr(target.getX(), target.getY(), target.getZ());
							float $$2 = (float)Math.sqrt($$0) / 20.0f;
							float $$3 = Mth.clamp($$2, 0.1F, 1.0F);
							this.blaze.performRangedAttack(target, $$3);
						}
					}

					this.blaze.getLookControl().setLookAt(target, 10.0F, 10.0F);
				}
				else if (this.lastSeen < 5) {
					this.blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
				}

				super.tick();
			}
		}

		private double getFollowDistance() {
			return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
		}
	}
}
