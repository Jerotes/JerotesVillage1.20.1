package com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge;

import com.jerotes.jerotes.entity.Interface.ConcentrationSpellEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.network.JerotesPlayerData;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.goal.BigWitchAttackGoal;
import com.jerotes.jerotesvillage.goal.BulgeFloatGoal;
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


public class SeawaveEntity extends BulgeEntity implements RangedAttackMob, ConcentrationSpellEntity, SpellUseEntity {
	private static final EntityDataAccessor<Integer> IDLE_ANIM = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ROLL_ANIM = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WIND_TICK = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BULGE_TICK = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BULGE_USE_TICK = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TRIDENT_COUNT = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TRIDENT_ADD_COOLDOWN = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> TRIDENT_HEALTH = SynchedEntityData.defineId(SeawaveEntity.class, EntityDataSerializers.FLOAT);
	public AnimationState idle1AnimationState = new AnimationState();
	public AnimationState idle2AnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState windAnimationState = new AnimationState();
	public AnimationState bulgeAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	public SeawaveEntity(EntityType<? extends SeawaveEntity> entityType, Level level) {
		super(entityType, level);
	}

	public int contractShouldBaseLevel() {
		return 6;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new BulgeFloatGoal(this, 0.15f));
		//this.goalSelector.addGoal(1, new JerotesAttackAvoidEntityGoal<>(this, LivingEntity.class, 3.0f, 0.8f, 1.0f));
		this.goalSelector.addGoal(1, new BigWitchAttackGoal(this, 0.8f, 80, 20.0f));
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.specialAction() || this.getAttackTick() > 0) {
			return;
		}
		if (this.distanceTo(livingEntity) > 8 || this.getRandom().nextFloat() > 0.5f || !this.hasLineOfSight(livingEntity)) {
			//波涛射弹
			if (!this.level().isClientSide()) {
				this.setAnimTick(50);
				this.setAnimationState("shoot");
				this.setShootTick(50);
				OtherSpellList.BulgeBullet(this.getSpellLevel(), this, livingEntity).spellUse();
			}
		}
		else {
			//宝石海浪
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("wind");
				this.setWindTick(30);
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 80);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SEAWAVE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SEAWAVE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SEAWAVE_DEATH;
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
		else if (Objects.equals(animation, "wind")){
			return 2;
		}
		else if (Objects.equals(animation, "bulge")){
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
		list.add(this.windAnimationState);
		list.add(this.bulgeAnimationState);
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	public int getTridentCount() {
		return this.getEntityData().get(TRIDENT_COUNT);
	}
	public void setTridentCount(int n) {
		this.getEntityData().set(TRIDENT_COUNT, n);
	}
	public int getTridentAddCooldown() {
		return this.getEntityData().get(TRIDENT_ADD_COOLDOWN);
	}
	public void setTridentAddCooldown(int n) {
		this.getEntityData().set(TRIDENT_ADD_COOLDOWN, n);
	}
	public float getTridentHealth() {
		return this.getEntityData().get(TRIDENT_HEALTH);
	}
	public void setTridentHealth(float f) {
		this.getEntityData().set(TRIDENT_HEALTH, f);
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
	public int getWindTick() {
		return this.getEntityData().get(WIND_TICK);
	}
	public void setWindTick(int n) {
		this.getEntityData().set(WIND_TICK, n);
	}
	public int getBulgeTick() {
		return this.getEntityData().get(BULGE_TICK);
	}
	public void setBulgeTick(int n) {
		this.getEntityData().set(BULGE_TICK, n);
	}
	public int getBulgeUseTick() {
		return this.getEntityData().get(BULGE_USE_TICK);
	}
	public void setBulgeUseTick(int n) {
		this.getEntityData().set(BULGE_USE_TICK, n);
	}
	public boolean specialAction() {
		return getShootTick() > 0 || getWindTick() > 0 || getBulgeUseTick() > 0;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("IdleAnim", this.getIdleAnim());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("WindTick", this.getWindTick());
		compoundTag.putInt("BulgeTick", this.getBulgeTick());
		compoundTag.putInt("BulgeUseTick", this.getBulgeUseTick());
		compoundTag.putInt("TridentCount", this.getTridentCount());
		compoundTag.putInt("TridentAddCooldown", this.getTridentAddCooldown());
		compoundTag.putFloat("TridentHealth", this.getTridentHealth());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setIdleAnim(compoundTag.getInt("IdleAnim"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setWindTick(compoundTag.getInt("WindTick"));
		this.setBulgeTick(compoundTag.getInt("BulgeTick"));
		this.setBulgeUseTick(compoundTag.getInt("BulgeUseTick"));
		this.setTridentCount(compoundTag.getInt("TridentCount"));
		this.setTridentAddCooldown(compoundTag.getInt("TridentAddCooldown"));
		this.setTridentHealth(compoundTag.getFloat("TridentHealth"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IDLE_ANIM, 0);
		this.getEntityData().define(ROLL_ANIM, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(WIND_TICK, 0);
		this.getEntityData().define(BULGE_TICK, 400);
		this.getEntityData().define(BULGE_USE_TICK, 0);
		this.getEntityData().define(TRIDENT_COUNT, 4);
		this.getEntityData().define(TRIDENT_ADD_COOLDOWN, 0);
		this.getEntityData().define(TRIDENT_HEALTH, 10f);
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
						this.windAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.windAnimationState);
						break;
					case 3:
						this.bulgeAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.bulgeAnimationState);
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
				Main.getJerotesPersistentData(this).putDouble("jerotesvillage_bulge_bullet", 0);
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
								serverLevel.sendParticles(ParticleTypes.BUBBLE_POP,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, 0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.BUBBLE,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, 0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.BUBBLE_POP,
										this.getX() + offsetX, this.getY() - 0.05f,
										this.getZ() + offsetZ, 0, 0, -0.5, 0, 1);
								serverLevel.sendParticles(ParticleTypes.BUBBLE,
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
						this.setTridentHealth(Math.min(10f, this.getTridentHealth() + 3.0f));
					}
				}
				//恢复数量
				if (this.getTridentCount() < 4 && this.getTridentAddCooldown() <= 0) {
					if (!this.level().isClientSide()) {
						this.setTridentHealth(10);
						this.setTridentCount(Math.min(4, this.getTridentCount() + 1));
						this.setTridentAddCooldown(120);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20; ++i) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 1, 0, -0.25, 0, 0.125);
						}
					}
					this.playSound(JerotesVillageSoundEvents.SEAWAVE_REGENERAT);
				}
				//降低数量
				if (this.getTridentCount() > 0 && this.getTridentHealth() <= 0) {
					if (!this.level().isClientSide()) {
						this.setTridentHealth(10);
						this.setTridentCount(Math.max(0, this.getTridentCount() - 1));
						this.setTridentAddCooldown(120);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20; ++i) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 1, 0, -0.25, 0, 0.125);
						}
					}
					this.playSound(JerotesVillageSoundEvents.SEAWAVE_BREAK);
				}
			}
			//波涛射弹
			//宝石海浪
			if (this.getWindTick() == 20) {
				if (this.getTarget() != null) {
					//法术列表-宝石海浪
					OtherSpellList.GemstoneWaves(getSpellLevel(), this, this).spellUse();
				}
			}
			//召唤波涛人
			List<BulgeEntity> listBulge = this.level().getEntitiesOfClass(BulgeEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listBulge.removeIf(entity -> this.getTarget() == entity || entity instanceof SeawaveEntity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<SeawaveEntity> ListSeawave = this.level().getEntitiesOfClass(SeawaveEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			ListSeawave.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			if (this.getBulgeTick() >= 600 && listBulge.size() <= ListSeawave.size() * 4 && this.isAggressive() && !this.specialAction() && this.getAttackTick() <= 0) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(25);
					this.setAnimationState("bulge");
					this.setBulgeTick(0);
					this.setBulgeUseTick(25);
				}
			}
			if (this.getBulgeUseTick() == 5) {
				//法术列表-召唤波涛人
				OtherSpellList.ConjureBulges(getSpellLevel(), this, this).spellUse();
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEAWAVE_BULGE, this.getSoundSource(), 10.0f, 1.0f);
				}
			}
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-60, this.getAttackTick() - 1));
				this.setTridentAddCooldown(Math.max(0, this.getTridentAddCooldown() - 1));
				this.setShootTick(Math.max(0, this.getShootTick() - 1));
				this.setWindTick(Math.max(0, this.getWindTick() - 1));
				this.setBulgeTick(Math.min(600, this.getBulgeTick() + 1));
				this.setBulgeUseTick(Math.max(0, this.getBulgeUseTick() - 1));
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
			if (attackRandom > 15 || this.getTridentCount() <= 0) {
				this.setAnimationState("attack2");
				this.setAttackUse(2);
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.SEAWAVE_SHOCKWAVE, 5.0f, 1.0f);
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
		//1-突刺
		//2-震地波
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		if (this.getAttackUse() == 2) {
			knockbackMulti = 1.5f;
			reach = 1.25f;
		}
		if (this.getAttackUse() == 1) {
			knockbackMulti = 1.25f;
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SEAWAVE_TRIDENT, 5.0f, 1.0f);
			}
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 128) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			//水中震地波无视可观
			if (!this.hasLineOfSight(hurt) && !(this.getAttackUse() == 2 && (this.isInWater() || this.level().getFluidState(this.getOnPos()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below().below().below()).is(Fluids.WATER)))) continue;
			if (hurt instanceof BulgeEntity bulgeEntity && bulgeEntity.getTarget() != this && this.getTarget() != bulgeEntity)
				continue;
			AttackFind.attackBegin(this, hurt);
			if (this.getAttackUse() == 2) {
				boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
				if (bl) {
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
				//水中击飞
				if (this.isInWater() || this.level().getFluidState(this.getOnPos()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below().below()).is(Fluids.WATER) || this.level().getFluidState(this.getOnPos().below().below().below().below()).is(Fluids.WATER)) {
					//击飞
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						double d = 0.0;
						if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = Math.max(0, 1 - d) + 0.2f;
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
				int count = 0;
				if (Main.canSeeAngle(this, hurt.getEyePosition(), 45)) {
					if (this.getTridentCount() >= 4) {
						count += 1;
					}
					if (this.getTridentCount() >= 3) {
						count += 1;
					}
				}
				if (CelestialCoilvineEntity.canSeeAngleFour(this, hurt.getEyePosition(), 45, 80)) {
					if (this.getTridentCount() >= 2) {
						count += 1;
					}
				}
				if (CelestialCoilvineEntity.canSeeAngleFour(this, hurt.getEyePosition(), -45, 80)) {
					if (this.getTridentCount() >= 1) {
						count += 1;
					}
				}
				for (int i = 0; i < count; ++i) {
					DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
					AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, knockbackMulti, false, 0f);
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
				serverLevel.sendParticles(JerotesVillageParticleTypes.SEAWAVE_SHOCKWAVE.get(), this.getX(), this.getY() + 0.1f, this.getZ(), 0, 0, 0, 0, 0);
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
		if (AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= this.getHealth() * 100 && this.getTridentCount() > 0) {
			if (!this.level().isClientSide()) {
				this.setTridentHealth(this.getTridentHealth() - amount);
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
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_SEAWAVE.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
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
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_SEAWAVE.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			ParticlesUse.summonParticle(serverLevel, this, this.getX(), this.getY(), this.getZ(),
					0x033177, 0x4ac8ff);
		}
		this.discard();
		return true;
	}
}

