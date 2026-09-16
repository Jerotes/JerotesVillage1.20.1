package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Part.DuneshocklerPart;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.*;

public class DuneshocklerEntity extends Monster implements JerotesEntity, LightningAbsorptionEntity, StopLook, SpellUseEntity, EliteEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState shoot1AnimationState = new AnimationState();
	public AnimationState shoot2AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_WALK = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_COOLDOWN = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_USE = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(DuneshocklerEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_12, false);

	public DuneshocklerEntity(EntityType<? extends DuneshocklerEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.xpReward = 100;
		this.moveControl = new GiantMoveControl(this);
		this.lookControl = new GiantLookControl(this, 1);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.3, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (DuneshocklerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.36);
		builder = builder.add(Attributes.MAX_HEALTH, 290);
		builder = builder.add(Attributes.ARMOR, 16);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 28);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.DUNESHOCKLER_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.DUNESHOCKLER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.DUNESHOCKLER_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	public int getMaxHeadYRot() {
		return 15;
	}
	@Override
	public int getMaxHeadXRot() {
		return 15;
	}
	@Override
	public int getHeadRotSpeed() {
		return 5;
	}
	@Override
	public void lookAt(Entity entity, float f, float f2) {
		if (!this.stopLookTime()) {
			super.lookAt(entity,f,f2);
		}
	}
	@Override
	public void setXRot(float f) {
		if (!this.stopLookTime()) {
			super.setXRot(f);
		}
	}
	@Override
	public void setYRot(float f) {
		if (!this.stopLookTime()) {
			super.setYRot(f);
		}
	}
	@Override
	public void setYBodyRot(float f) {
		if (!this.stopLookTime()) {
			super.setYBodyRot(f);
		}
	}
	@Override
	public boolean stopLookTime() {
		return this.getAttackTick() > 0;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 7f;
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
	public double LightningAbsorptionPercentage() {
		return 80;
	}
	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
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
		return aabb1.inflate(1.5d, 1.5d, 1.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().inflate(this.walkAnimation.speed() < 0.01 ? 0 : 1f,0,this.walkAnimation.speed() < 0.01 ? 0 : 1f).intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof DuneshocklerPart) {
			return false;
		}
		return super.canCollideWith(entity);
	}

	public int spellLevel = 4;
	public double blockDestroyTick;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getAttackWalk() {
		return this.getEntityData().get(ATTACK_WALK);
	}
	public void setAttackWalk(int n) {
		this.getEntityData().set(ATTACK_WALK, n);
	}
	public int getShootTick() {
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setShootTick(int n) {
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootCooldown() {
		return this.getEntityData().get(SHOOT_COOLDOWN);
	}
	public void setShootCooldown(int n) {
		this.getEntityData().set(SHOOT_COOLDOWN, n);
	}
	public int getShootUse() {
		return this.getEntityData().get(SHOOT_USE);
	}
	public void setShootUse(int n) {
		this.getEntityData().set(SHOOT_USE, n);
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else if (Objects.equals(animation, "attack4")){
			return 4;
		}
		else if (Objects.equals(animation, "shoot1")){
			return 5;
		}
		else if (Objects.equals(animation, "shoot2")){
			return 6;
		}
		else if (Objects.equals(animation, "dead")){
			return 7;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
		list.add(this.attack4AnimationState);
		list.add(this.shoot1AnimationState);
		list.add(this.shoot2AnimationState);
		list.add(this.deadAnimationState);
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
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public boolean specialAction() {
		return this.getShootTick() > 0;
	}
	@Nullable
	public UUID getChildId() {
		return this.entityData.get(CHILD_UUID).orElse(null);
	}
	public void setChildId(@Nullable UUID uniqueId) {
		this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
	}
	public Entity getChild() {
		UUID id = getChildId();
		if (id != null && !this.level().isClientSide) {
			return ((ServerLevel) level()).getEntity(id);
		}
		return null;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.getChildId() != null) {
			compoundTag.putUUID("ChildUUID", this.getChildId());
		}
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("AttackWalk", this.getAttackWalk());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("ShootCooldown", this.getShootCooldown());
		compoundTag.putInt("ShootUse", this.getShootUse());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		if (compoundTag.hasUUID("ChildUUID")) {
			this.setChildId(compoundTag.getUUID("ChildUUID"));
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setAttackWalk(compoundTag.getInt("AttackWalk"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setShootCooldown(compoundTag.getInt("ShootCooldown"));
		this.setShootUse(compoundTag.getInt("ShootUse"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(CHILD_UUID, Optional.empty());
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ATTACK_WALK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(SHOOT_COOLDOWN, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(SHOOT_USE, 0);
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
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.attack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack4AnimationState);
						break;
					case 5:
						this.shoot1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shoot1AnimationState);
						break;
					case 6:
						this.shoot2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shoot2AnimationState);
						break;
					case 7:
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
		this.setYBodyRot(this.getYRot());
		if (!this.level().isClientSide) {
			final Entity child = getChild();
			if (child == null) {
				LivingEntity partParent = this;
				final int segments = 7;
				for (int i = 0; i < segments; i++) {
					DuneshocklerPart part = new DuneshocklerPart(JerotesVillageEntityType.DUNESHOCKLER_PART.get(), partParent, this, i != 0 ? 2.1F : 2.2f, 180, 0F);
					if (this.level() instanceof ServerLevel serverLevel) {
						PlayerTeam teams = (PlayerTeam) this.getTeam();
						if (teams != null) {
							serverLevel.getScoreboard().addPlayerToTeam(part.getStringUUID(), teams);
						}
					}
					part.setParent(partParent);
					part.setTrueParent(this);
					part.setBodyIndex(i);
					if (partParent == this) {
						this.setChildId(part.getUUID());
					}
					part.setInitialPartPos(this);
					partParent = part;
					level().addFreshEntity(part);
				}
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
			this.bossEvent.update();
			if (OtherMainConfig.EliteBossBarOnlyCombat) {
				this.bossEvent.setVisible(this.getTarget() != null);
			}
		}
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(5.0f);
		}
		if (this.level().isClientSide()) {
			this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//视角摇晃
		if (this.walkAnimation.isMoving() && this.onGround() && this.getHealth() > this.getMaxHealth() / 4 && !this.isBaby()) {
			float f = (float) Math.cos(this.walkAnimation.position() * 0.8F - 1.5F);
			if (Math.abs(f) < 0.2) {
				List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
				for (LivingEntity shake : listShake) {
					if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
						continue;
					if (!shake.level().isClientSide()) {
						shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
					}
				}
			}
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		float breakLevel = 5f;
		double blockDestroyTickUse = 60;
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, breakLevel);
			if (blockDestroy) {
				this.blockDestroyTick = blockDestroyTickUse;
			}
			if (!this.level().isClientSide) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
		//攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
			this.setShootCooldown(Math.max(0, this.getShootCooldown() - 1));
			this.setShootTick(Math.max(0, this.getShootTick() - 1));
		}
		if (this.getAttackTick() == 15 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getTarget() != null && this.getAttackTick() <= -10) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
		}
		//行走攻击
		if (this.getAttackTick() > 0 || this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget())) {
			if (!this.level().isClientSide()) {
				this.setAttackWalk(Math.min(this.getAttackWalk() + 1, 40));
			}
			if (this.getAttackTick() > 0) {
				if (this.getAttackUse() == 4 || this.getAttackUse() == 3 || this.getAttackUse() == 1) {
					this.getNavigation().stop();
				}
				if (!this.level().isClientSide()) {
					this.setAttackWalk(Math.min(this.getAttackWalk() + 1, 40));
				}
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackWalk(Math.max(this.getAttackWalk() - 1, 0));
			}
		}
		//动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}

		//技能
		{
			if (this.getShootCooldown() <= 0 && this.spellNeed(0, 16, 1) && this.getTarget() != null) {
				if (!this.level().isClientSide()) {
					this.setShootTick(25);
					this.setShootCooldown(240);
					this.setAnimTick(25);
					boolean bl = this.getRandom().nextBoolean();
					this.setShootUse(bl ? 1 : 2);
					this.setAnimationState(bl ? "shoot1" : "shoot2");
				}
			}
			if (getShootUse() == 1) {
				if (this.getShootTick() == 12) {
					OtherSpellList.LightningSpit(this.getSpellLevel(), this, this.getTarget()).spellUse();
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_SHOOT, 5.0f, 1.0f);
					}
				}
			}
			if (getShootUse() == 2) {
				if (this.getShootTick() == 15) {
					OtherSpellList.LightningSpitAdd(this.getSpellLevel(), 3, this, this.getTarget()).spellUse();
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_SHOOT, 5.0f, 1.0f);
					}
				}
				if (this.getShootTick() == 13) {
					OtherSpellList.LightningSpitAdd(this.getSpellLevel(), 2, this, this.getTarget()).spellUse();
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_SHOOT, 5.0f, 1.0f);
					}
				}
				if (this.getShootTick() == 11) {
					OtherSpellList.LightningSpitAdd(this.getSpellLevel(), 1, this, this.getTarget()).spellUse();
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_SHOOT, 5.0f, 1.0f);
					}
				}
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= -10 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}


	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 30) {
				this.setAnimTick(30);
				this.setAttackTick(30);
				this.setAnimationState("attack1");
				this.setAttackUse(1);
			} else if (attackRandom > 20) {
				this.setAnimTick(30);
				this.setAttackTick(30);
				this.setAnimationState("attack2");
				this.setAttackUse(1);
			} else if (attackRandom > 10) {
				this.setAnimTick(30);
				this.setAttackTick(30);
				this.setAnimationState("attack3");
				this.setAttackUse(3);
			} else {
				this.setAnimTick(30);
				this.setAttackTick(30);
				this.setAnimationState("attack4");
				this.setAttackUse(2);
			}
		}
		this.getNavigation().stop();
		return true;
	}
	public boolean trueHurt() {
		//1-挥砍
		//2-践踏

		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		if (this.getAttackUse() == 2) {
			damageMulti = 1.5f;
			knockbackMulti = 1.5f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.25f;
		}

		if (!this.isSilent()) {
			if (this.getAttackUse() == 1 || this.getAttackUse() == 3) {
				this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_SLASH, 5.0f, 1.2f);
			}
			if (this.getAttackUse() == 2) {
				this.playSound(JerotesVillageSoundEvents.DUNESHOCKLER_TRAMPLE, 5.0f, 1.2f);
			}
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 128) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 2) continue;
			if (hurt instanceof DuneshocklerEntity duneshocklerEntity && duneshocklerEntity.getTarget() != this && this.getTarget() != duneshocklerEntity)
				continue;
			if (hurt instanceof DuneshocklerPart duneshocklerEntity && this.getTarget() != duneshocklerEntity)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl) {
				//效果
				if (this.getAttackUse() == 1 || this.getAttackUse() == 3 || this.getAttackUse() == 2 && Main.canSee(hurt, this)) {
					if (!hurt.level().isClientSide()) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
					}
				}
			}
		}
		//震动
		if (this.getAttackUse() == 2) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//下落方块
		{
			if (this.getAttackUse() == 2) {
				if (this.level() instanceof ServerLevel serverLevel) {
					AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this) / 2 + 6, 0, Main.mobWidth(this) / 2 + 6);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
						Main.spawnFallingBlockByPos(serverLevel, blockPos);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isLightningDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.IN_WALL)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.BLEEDING.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
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
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
