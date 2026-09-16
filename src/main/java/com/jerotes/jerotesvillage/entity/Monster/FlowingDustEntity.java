package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class FlowingDustEntity extends Monster implements JerotesEntity {
	public AnimationState climbAnimationState = new AnimationState();
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(FlowingDustEntity.class, EntityDataSerializers.BYTE);

	public FlowingDustEntity(EntityType<? extends FlowingDustEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0.6f);
		this.xpReward = 5;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45);
		builder = builder.add(Attributes.MAX_HEALTH, 12);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(SoundEvents.SAND_STEP, 0.15f, 1.0f);
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SAND_FALL;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SAND_HIT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SAND_BREAK;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if (this.level().getBlockState(this.getOnPos().below()).isAir() && this.level().getBlockState(this.getOnPos().cross(new Vec3i(2,2,2))).isAir()) {
			return false;
		}
		return this.isAlive();
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}
	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, by);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_FLAGS_ID, (byte) 0);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.dustTick > 0){
			this.dustTick -= 1;
		}
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
		if (dustTick <= 0 && this.deathTime == 0 && this.onGround() && !this.onClimbable()  && !this.level().getBlockState(this.getOnPos()).isAir()) {
			if (!(this.level().isClientSide || this.xOld == this.getX() && this.zOld == this.getZ())) {
				double d = Math.abs(this.getX() - this.xOld);
				double d2 = Math.abs(this.getZ() - this.zOld);
				if (d >= 0.003 || d2 >= 0.003) {
					if (this.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.DEEP_DUST.get(), this.getX(), this.getY() + 0.01, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
					}
					this.dustTick = 5;
				}
			}
		}
	}
	private int dustTick;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("DustTick", this.dustTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.dustTick = compoundTag.getInt("DustTick");
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 20;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isFireDamage(damageSource))
			return super.hurt(damageSource, amount * 3);
		return super.hurt(damageSource, amount);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.4f;
	}

	public void aiStep() {
		super.aiStep();
		if (this.isClimbing()){
			this.climbAnimationState.start(this.tickCount);
		}
		else {
			this.climbAnimationState.stop();
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
	}
}

