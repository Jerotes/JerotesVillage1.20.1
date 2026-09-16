package com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze;

import com.jerotes.jerotes.entity.Interface.ConcentrationSpellEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.network.JerotesPlayerData;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.BigWitchAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class DroughtWildfireEntity extends DroughtBlazeEntity implements RangedAttackMob, ConcentrationSpellEntity, FireAbsorptionEntity, SpellUseEntity {
	private static final EntityDataAccessor<Integer> IDLE_ANIM = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ROLL_ANIM = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FLAME_TICK = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DROUGHT_BLAZE_TICK = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DROUGHT_BLAZE_USE_TICK = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_COUNT = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_ADD_COOLDOWN = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SHIELD_HEALTH = SynchedEntityData.defineId(DroughtWildfireEntity.class, EntityDataSerializers.FLOAT);
	public AnimationState idle1AnimationState = new AnimationState();
	public AnimationState idle2AnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState flameAnimationState = new AnimationState();
	public AnimationState droughtBlazeAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	public DroughtWildfireEntity(EntityType<? extends DroughtWildfireEntity> entityType, Level level) {
		super(entityType, level);
	}

	public int contractShouldBaseLevel() {
		return 6;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new BigWitchAttackGoal(this, 0.8f, 80, 20.0f));
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.specialAction() || this.getAttackTick() > 0) {
			return;
		}
		if (this.distanceTo(livingEntity) > 8 || this.getRandom().nextFloat() > 0.5f || !this.hasLineOfSight(livingEntity)) {
			//旱火火球
			if (!this.level().isClientSide()) {
				this.setAnimTick(50);
				this.setAnimationState("shoot");
				this.setShootTick(50);
				OtherSpellList.DroughtFireball(this.getSpellLevel(), this, livingEntity).spellUse();
			}
		}
		else {
			//蛇龙火舌术
			if (!this.level().isClientSide()) {
				this.setAnimTick(60);
				this.setAnimationState("flame");
				this.setFlameTick(60);
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 120);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}
	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.DROUGHT_WILDFIRE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.DROUGHT_WILDFIRE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.DROUGHT_WILDFIRE_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 480;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.875f;
	}
	@Override
	public double FireAbsorptionPercentage() {
		return 80;
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction() || this.getAttackTick() > 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.005d, 1d, 0.005d));
		}
	}
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(1.25d, 1.25d, 1.25d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float idleAnimProgress = 0.0f;
	public float rollAnimProgress = 0.0f;
	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "shoot")){
			return 1;
		}
		else if (Objects.equals(animation, "flame")){
			return 2;
		}
		else if (Objects.equals(animation, "droughtBlaze")){
			return 3;
		}
		else if (Objects.equals(animation, "attack1")){
			return 4;
		}
		else if (Objects.equals(animation, "attack2")){
			return 5;
		}
		else if (Objects.equals(animation, "dead")){
			return 6;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.shootAnimationState);
		list.add(this.flameAnimationState);
		list.add(this.droughtBlazeAnimationState);
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	public int getShieldCount() {
		return this.getEntityData().get(SHIELD_COUNT);
	}
	public void setShieldCount(int n) {
		this.getEntityData().set(SHIELD_COUNT, n);
	}
	public int getShieldAddCooldown() {
		return this.getEntityData().get(SHIELD_ADD_COOLDOWN);
	}
	public void setShieldAddCooldown(int n) {
		this.getEntityData().set(SHIELD_ADD_COOLDOWN, n);
	}
	public float getShieldHealth() {
		return this.getEntityData().get(SHIELD_HEALTH);
	}
	public void setShieldHealth(float f) {
		this.getEntityData().set(SHIELD_HEALTH, f);
	}
	public int getIdleAnim() {
		return this.getEntityData().get(IDLE_ANIM);
	}
	public void setIdleAnim(int n) {
		this.getEntityData().set(IDLE_ANIM, n);
	}
	public int getRollAnim() {
		return this.getEntityData().get(ROLL_ANIM);
	}
	public void setRollAnim(int n) {
		this.getEntityData().set(ROLL_ANIM, n);
	}
	public int getAttackTick() {
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setAttackTick(int n) {
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getShootTick() {
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setShootTick(int n) {
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getFlameTick() {
		return this.getEntityData().get(FLAME_TICK);
	}
	public void setFlameTick(int n) {
		this.getEntityData().set(FLAME_TICK, n);
	}
	public int getDroughtBlazeTick() {
		return this.getEntityData().get(DROUGHT_BLAZE_TICK);
	}
	public void setDroughtBlazeTick(int n) {
		this.getEntityData().set(DROUGHT_BLAZE_TICK, n);
	}
	public int getDroughtBlazeUseTick() {
		return this.getEntityData().get(DROUGHT_BLAZE_USE_TICK);
	}
	public void setDroughtBlazeUseTick(int n) {
		this.getEntityData().set(DROUGHT_BLAZE_USE_TICK, n);
	}
	public boolean specialAction() {
		return getShootTick() > 0 || getFlameTick() > 0 || getDroughtBlazeUseTick() > 0;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("IdleAnim", this.getIdleAnim());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("FlameTick", this.getFlameTick());
		compoundTag.putInt("DroughtBlazeTick", this.getDroughtBlazeTick());
		compoundTag.putInt("DroughtBlazeUseTick", this.getDroughtBlazeUseTick());
		compoundTag.putInt("ShieldCount", this.getShieldCount());
		compoundTag.putInt("ShieldAddCooldown", this.getShieldAddCooldown());
		compoundTag.putFloat("ShieldHealth", this.getShieldHealth());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setIdleAnim(compoundTag.getInt("IdleAnim"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setFlameTick(compoundTag.getInt("FlameTick"));
		this.setDroughtBlazeTick(compoundTag.getInt("DroughtBlazeTick"));
		this.setDroughtBlazeUseTick(compoundTag.getInt("DroughtBlazeUseTick"));
		this.setShieldCount(compoundTag.getInt("ShieldCount"));
		this.setShieldAddCooldown(compoundTag.getInt("ShieldAddCooldown"));
		this.setShieldHealth(compoundTag.getFloat("ShieldHealth"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IDLE_ANIM, 0);
		this.getEntityData().define(ROLL_ANIM, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(FLAME_TICK, 0);
		this.getEntityData().define(DROUGHT_BLAZE_TICK, 400);
		this.getEntityData().define(DROUGHT_BLAZE_USE_TICK, 0);
		this.getEntityData().define(SHIELD_COUNT, 4);
		this.getEntityData().define(SHIELD_ADD_COOLDOWN, 0);
		this.getEntityData().define(SHIELD_HEALTH, 10f);
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
						this.shootAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shootAnimationState);
						break;
					case 2:
						this.flameAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.flameAnimationState);
						break;
					case 3:
						this.droughtBlazeAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.droughtBlazeAnimationState);
						break;
					case 4:
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 5:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 6:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		if (!this.isAggressive()) {
			if (this.getShootTick() > 0) {
				Main.getJerotesPersistentData(this).putDouble("jerotesvillage_drought_fireball", 0);
				if (!this.level().isClientSide()) {
					this.setShootTick(0);
				}
				this.shootAnimationState.stop();
			}
		}
		if (this.isAlive()) {
			this.idle1AnimationState.startIfStopped(this.tickCount);
			this.idle2AnimationState.startIfStopped(this.tickCount);
		}
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			this.lookAt(this.getTarget(), 360f, 360f);
		}

		if (!this.specialAction() && this.getAttackTick() <= 0 && this.isAlive()) {
			if (!this.level().isClientSide()) {
				this.setRollAnim(this.getRollAnim() + 1);
			}
		}
		else if (this.getIdleAnim() == 0) {
			if (!this.level().isClientSide()) {
				this.setRollAnim(0);
			}
		}

		if (!this.specialAction() && this.getAttackTick() <= 0 && this.isAlive()) {
			if (!this.level().isClientSide()) {
				this.setIdleAnim(Math.min(this.getIdleAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setIdleAnim(Math.max(this.getIdleAnim() - 1, 0));
			}
		}
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}

		if (this.isAlive()) {
			//近战
			{
				if (this.getAttackTick() <= -60 && this.isAlive() && this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget()) && this.getAttackBoundingBox().intersects(this.getTarget().getBoundingBox()) && this.getSensing().hasLineOfSight(this.getTarget())) {
					this.swing(InteractionHand.MAIN_HAND);
					this.doHurtTarget(this.getTarget());
				}
				if (this.getAttackTick() == 10 && this.isAlive()) {
					this.trueHurt();
				}
				if (this.isInWater() ||
						this.level().getFluidState(this.getOnPos()).is(Fluids.WATER) ||
						this.level().getFluidState(this.getOnPos().below()).is(Fluids.WATER) ||
						this.level().getFluidState(this.getOnPos().below().below()).is(Fluids.WATER) ||
						this.level().getFluidState(this.getOnPos().below().below().below()).is(Fluids.WATER) ||
						this.level().getFluidState(this.getOnPos().below().below().below().below()).is(Fluids.WATER)
				) {
					if (this.getAttackTick() > 5 && this.getAttackTick() <= 12 && this.getAttackUse() == 2 && this.isAlive()) {
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 24; i++) {
								double angle = (Math.PI * 2 * i) / 24;
								double radius = 3;
								double offsetX = Math.cos(angle) * radius;
								double offsetZ = Math.sin(angle) * radius;
								serverLevel.sendParticles(ParticleTypes.LAVA,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, 0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.LAVA,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, 0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.LAVA,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, -0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.LAVA,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, -0.5, 0, 1);
							}
						}
					}
				}
			}
			//三叉戟
			{
				//回血
				if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setShieldHealth(Math.min(10f, this.getShieldHealth() + 3.0f));
					}
				}
				//恢复数量
				if (this.getShieldCount() < 4 && this.getShieldAddCooldown() <= 0) {
					if (!this.level().isClientSide()) {
						this.setShieldHealth(10);
						this.setShieldCount(Math.min(4, this.getShieldCount() + 1));
						this.setShieldAddCooldown(120);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20; ++i) {
							serverLevel.sendParticles(ParticleTypes.LAVA, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 1, 0, -0.25, 0, 0.125);
						}
					}
					this.playSound(JerotesVillageSoundEvents.DROUGHT_WILDFIRE_REGENERAT);
				}
				//降低数量
				if (this.getShieldCount() > 0 && this.getShieldHealth() <= 0) {
					if (!this.level().isClientSide()) {
						this.setShieldHealth(10);
						this.setShieldCount(Math.max(0, this.getShieldCount() - 1));
						this.setShieldAddCooldown(120);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20; ++i) {
							serverLevel.sendParticles(ParticleTypes.LAVA, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 1, 0, -0.25, 0, 0.125);
						}
					}
					this.playSound(JerotesVillageSoundEvents.DROUGHT_WILDFIRE_BREAK);
				}
			}
			//旱火火球
			//蛇龙火舌术
			if (this.getFlameTick() == 50) {
				if (this.getTarget() != null) {
					//法术列表-蛇龙火舌术
					OtherSpellList.SerponFlames(getSpellLevel(), this, this).spellUse();
				}
			}
			//召唤旱火烈焰人
			List<DroughtBlazeEntity> listDroughtBlaze = this.level().getEntitiesOfClass(DroughtBlazeEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listDroughtBlaze.removeIf(entity -> this.getTarget() == entity || entity instanceof DroughtWildfireEntity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<DroughtWildfireEntity> ListDroughtWildfire = this.level().getEntitiesOfClass(DroughtWildfireEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			ListDroughtWildfire.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			if (this.getDroughtBlazeTick() >= 600 && listDroughtBlaze.size() <= ListDroughtWildfire.size() * 4 && this.isAggressive() && !this.specialAction() && this.getAttackTick() <= 0) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(25);
					this.setAnimationState("droughtBlaze");
					this.setDroughtBlazeTick(0);
					this.setDroughtBlazeUseTick(25);
				}
			}
			if (this.getDroughtBlazeUseTick() == 5) {
				//法术列表-召唤旱火烈焰人
				OtherSpellList.ConjureDroughtBlazes(getSpellLevel(), this, this).spellUse();
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.DROUGHT_WILDFIRE_DROUGHT_BLAZE, this.getSoundSource(), 10.0f, 1.0f);
				}
			}
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-60, this.getAttackTick() - 1));
				this.setShieldAddCooldown(Math.max(0, this.getShieldAddCooldown() - 1));
				this.setShootTick(Math.max(0, this.getShootTick() - 1));
				this.setFlameTick(Math.max(0, this.getFlameTick() - 1));
				this.setDroughtBlazeTick(Math.min(600, this.getDroughtBlazeTick() + 1));
				this.setDroughtBlazeUseTick(Math.max(0, this.getDroughtBlazeUseTick() - 1));
			}
		}
	}


	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction() || this.getAttackTick() > -60) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(30);
			this.setAnimTick(30);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 15 || this.getShieldCount() <= 0) {
				this.setAnimationState("attack2");
				this.setAttackUse(2);
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.DROUGHT_WILDFIRE_SHOCKWAVE, 5.0f, 1.0f);
				}
			}
			else {
				this.setAnimationState("attack1");
				this.setAttackUse(1);
			}
		}
		return true;
	}
	public boolean trueHurt() {
		//1-推击
		//2-震地波
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		if (this.getAttackUse() == 2) {
			knockbackMulti = 1.5f;
			reach = 1.25f;
		}
		if (this.getAttackUse() == 1) {
			damageMulti = 2.0f;
			knockbackMulti = 1.25f;
			reach = 1.25f;
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.DROUGHT_WILDFIRE_THRUST, 5.0f, 1.0f);
			}
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 128) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (hurt instanceof DroughtBlazeEntity droughtBlazeEntity && droughtBlazeEntity.getTarget() != this && this.getTarget() != droughtBlazeEntity)
				continue;
			AttackFind.attackBegin(this, hurt);
			if (this.getAttackUse() == 2) {
				boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
				if (bl) {
					hurt.setSecondsOnFire(12);
					//击飞
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						double d = 0.0;
						if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = Math.max(0, 1 - d) + 0.3f;
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d3 / 1.5f) * (0.7 + knockbackMulti / 3),
								-((this.getY() - hurt.getY()) * d3 / 1.5f) * (0.7 + knockbackMulti / 3),
								-((this.getZ() - hurt.getZ()) * d3 / 1.5f) * (0.7 + knockbackMulti / 3)
						));
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(0f, (7 - this.distanceTo(hurt)) / 10, 0f));
					}
				}
			}
			if (this.getAttackUse() == 1) {
				boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
				if (bl) {
					hurt.setSecondsOnFire(12);
				}
			}
		}
		if (this.getAttackUse() == 2) {
			//震动
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12.0, 12.0, 12.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
			//粒子效果
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.DROUGHT_WILDFIRE_SHOCKWAVE.get(), this.getX(), this.getY() + 0.1f, this.getZ(), 0, 0, 0, 0, 0);
			}
			//破船
			List<Boat> listBoat = this.level().getEntitiesOfClass(Boat.class, this.getAttackBoundingBox().inflate(reach));
			for (Boat hurt : listBoat) {
				if (hurt == null) continue;
				if ((this.distanceToSqr(hurt)) > 128) continue;
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				hurt.hurt(this.damageSources().mobAttack(this), 30);
			}
		}
		else {
			//横扫效果
			Main.sweepAttack(this);
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isFireDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= this.getHealth() * 100 && this.getShieldCount() > 0) {
			if (!this.level().isClientSide()) {
				this.setShieldHealth(this.getShieldHealth() - amount);
			}
			if (amount > 0) {
				this.playSound(JerotesSoundEvents.TWOHANDED_BLOCK);
			}
			amount = 0;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.endConcentration();
			if (this.getLastDamageSource() != null && !this.getLastDamageSource().is(JerotesDamageTypes.MAGIC_EFFECT)) {
				this.level().broadcastEntityEvent(this, (byte) 60);
				this.remove(RemovalReason.KILLED);
			}
			else {
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public void remove(RemovalReason removalReason) {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_DROUGHT_WILDFIRE.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		super.remove(removalReason);
	}

	@Override
	public boolean endConcentration() {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_DROUGHT_WILDFIRE.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		this.discard();
		return true;
	}
}

