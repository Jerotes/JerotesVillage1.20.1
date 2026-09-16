package com.jerotes.jerotesvillage.entity.Neutral;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class UnicycleEntity extends PathfinderMob implements JerotesEntity, Saddleable, PlayerRideableJumping {
	private static final EntityDataAccessor<Integer> RUSH_TICK = SynchedEntityData.defineId(UnicycleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_STRENGTH = SynchedEntityData.defineId(UnicycleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_ANIM = SynchedEntityData.defineId(UnicycleEntity.class, EntityDataSerializers.INT);

	private boolean isJumping;
	private float playerJumpPendingScale;

	public UnicycleEntity(EntityType<? extends UnicycleEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		xpReward = 0;
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 5);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		return builder;
	}

	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.UNICYCLE_DEATH;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return JerotesVillageSoundEvents.UNICYCLE_DEATH;
	}

	@Override
	protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
		if (this.onGround() && this.playerJumpPendingScale == 0.0f) {
			return Vec3.ZERO;
		}
		float f = player.xxa * 0.5f;
		float f2 = player.zza;
		if (f2 <= 0.0f) {
			f2 *= 0.25f;
		}
		return new Vec3(f, 0.0, f2);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive();
	}

	@Override
	public void equipSaddle(@Nullable SoundSource p_21748_) {
	}

	@Override
	public boolean isSaddled() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(JerotesVillageSoundEvents.UNICYCLE_WALK, 0.15f, 1);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	protected void positionRider(Entity entity, MoveFunction moveFunction) {
		Vec3 vec3 = this.getPassengerRidingPosition(entity);
		moveFunction.accept(entity, vec3.x, vec3.y + getMyRidingOffset(this), vec3.z);
	}

	public float getMyRidingOffset(Entity entity) {
		return this.ridingOffset(entity) * this.getScale();
	}

	protected float ridingOffset(Entity entity) {
		return -0.6F;
	}

	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}

	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle() && this.getFirstPassenger() instanceof Player) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float forward = passenger.zza;
				float strafe = passenger.xxa * 0.5f;
				super.travel(new Vec3(strafe, 0, forward));
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			return;
		}
		super.travel(dir);
	}

	@Override
	protected void tickRidden(Player player, Vec3 vec3) {
		super.tickRidden(player, vec3);
		this.setRot(player.getYRot(), player.getXRot() * 0.5f);
		this.yBodyRot = this.yHeadRot = this.getYRot();
		this.yRotO = this.yHeadRot;
		if (this.isControlledByLocalInstance()) {
			if (this.onGround()) {
				this.setIsJumping(false);
				if (this.playerJumpPendingScale > 0.0f && !this.isJumping()) {
					this.executeRidersJump(this.playerJumpPendingScale, vec3);
				}
				this.playerJumpPendingScale = 0.0f;
			}
		}
	}

	@Override
	protected float getRiddenSpeed(Player player) {
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	}

	@Override
	public void onPlayerJump(int n) {
		if (!this.isSaddled()) {
			return;
		}
		if (n < 0) {
			n = 0;
		}
		this.playerJumpPendingScale = n >= 90 ? 1.0f : 0.4f + 0.4f * (float)n / 90.0f;
	}

	@Override
	public boolean canJump() {
		return this.isSaddled() && this.onGround();
	}

	@Override
	public int getJumpCooldown() {
		return 0;
	}

	@Override
	public void handleStartJump(int i) {
		if (i >= 30 && i <= 100) {
			this.playJumpSound();
		}
	}

	protected void playJumpSound() {
	}

	@Override
	public void handleStopJump() {
	}

	public boolean isJumping() {
		return this.isJumping;
	}

	public void setIsJumping(boolean bl) {
		this.isJumping = bl;
	}

	protected void executeRidersJump(float f, Vec3 vec3) {
		Vec3 currentMotion = this.getDeltaMovement();
		double currentSpeed = currentMotion.length();
		if (currentSpeed > 0.5) {
			return;
		}
		double d = 0.015f * (double) f * (double) this.getBlockJumpFactor();
		double d2 = d * 0.5 + (double) this.getJumpBoostPower();
		this.setDeltaMovement(currentMotion.x * (1 + f * 1), d2, currentMotion.z * (1 + f * 1));
		this.setIsJumping(true);
		this.hasImpulse = true;
		if (vec3.z > 0.0) {
			float f2 = Mth.sin(this.getYRot() * 0.017453292f);
			float f3 = Mth.cos(this.getYRot() * 0.017453292f);
			this.setDeltaMovement(
					-0.04f * f2 * f + currentMotion.x * (1 + f * 5),
					d2,
					0.04f * f3 * f + currentMotion.z * (1 + f * 5));
		}
	}

	public void setRushTick(int n){
		this.getEntityData().set(RUSH_TICK, n);
	}

	public int getRushTick(){
		return this.getEntityData().get(RUSH_TICK);
	}

	public void setRushAnim(int n){
		this.getEntityData().set(RUSH_ANIM, n);
	}

	public int getRushAnim(){
		return this.getEntityData().get(RUSH_ANIM);
	}

	public void setRushStrength(int n){
		this.getEntityData().set(RUSH_STRENGTH, n);
	}

	public int getRushStrength(){
		return this.getEntityData().get(RUSH_STRENGTH);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("RushTick", this.getRushTick());
		compoundTag.putInt("RushStrength", this.getRushStrength());
		compoundTag.putInt("RushAnim", this.getRushAnim());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setRushTick(compoundTag.getInt("RushTick"));
		this.setRushStrength(compoundTag.getInt("RushStrength"));
		this.setRushAnim(compoundTag.getInt("RushAnim"));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(RUSH_TICK, 0);
		this.getEntityData().define(RUSH_STRENGTH, 0);
		this.getEntityData().define(RUSH_ANIM, 0);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(1.0f);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (!player.isShiftKeyDown()) {
			if (!this.level().isClientSide) {
				player.startRiding(this);
			}
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, hand);
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity;
		if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}
}