package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrimordierEntity extends Monster implements JerotesEntity, ColdAbsorptionEntity, StopLook, SpellUseEntity, BreakShieldEntity, EliteEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState attack6AnimationState = new AnimationState();
	public AnimationState attack7AnimationState = new AnimationState();
	public AnimationState attack8AnimationState = new AnimationState();
	public AnimationState shockAnimationState = new AnimationState();
	public AnimationState shockToCorrosiveAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOCK_TICK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOCK_USE_TICK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> SHOULD_SHOCK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SHOCK_JUMP = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_WALK = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_ANIM = SynchedEntityData.defineId(PrimordierEntity.class, EntityDataSerializers.INT);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_12, false);

	public PrimordierEntity(EntityType<? extends PrimordierEntity> type, Level world) {
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
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.6, true));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (PrimordierEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 320);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 25);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.PRIMORDIER_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.PRIMORDIER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.PRIMORDIER_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.PRIMORDIER_RUSH, 0.15f, 1.0f);
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public int getShieldBreakStrength() {
		return 100;
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
		return 4.5f;
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
	public double ColdAbsorptionPercentage() {
		return 80;
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction() && !this.onGround() || this.getAttackTick() > 0) {
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

	public int spellLevel = 3;
	public double blockDestroyTick;
	public float rushFindXRot;
	public float rushFindYRot;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setShockTick(int n){
		this.getEntityData().set(SHOCK_TICK, n);
	}
	public int getShockTick(){
		return this.getEntityData().get(SHOCK_TICK);
	}
	public void setShockUseTick(int n){
		this.getEntityData().set(SHOCK_USE_TICK, n);
	}
	public int getShockUseTick(){
		return this.getEntityData().get(SHOCK_USE_TICK);
	}
	public boolean isShouldShock() {
		return this.getEntityData().get(SHOULD_SHOCK);
	}
	public void setShouldShock(boolean bl) {
		this.getEntityData().set(SHOULD_SHOCK, bl);
	}
	public boolean isShockJump() {
		return this.getEntityData().get(SHOCK_JUMP);
	}
	public void setShockJump(boolean bl) {
		this.getEntityData().set(SHOCK_JUMP, bl);
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
	public int getJumpAnim() {
		return this.getEntityData().get(JUMP_ANIM);
	}
	public void setJumpAnim(int n) {
		this.getEntityData().set(JUMP_ANIM, n);
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
		else if (Objects.equals(animation, "attack5")){
			return 5;
		}
		else if (Objects.equals(animation, "attack6")){
			return 6;
		}
		else if (Objects.equals(animation, "attack7")){
			return 7;
		}
		else if (Objects.equals(animation, "attack8")){
			return 8;
		}
		else if (Objects.equals(animation, "shock")){
			return 9;
		}
		else if (Objects.equals(animation, "shockToCorrosive")){
			return 10;
		}
		else if (Objects.equals(animation, "dead")){
			return 11;
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
		list.add(this.attack5AnimationState);
		list.add(this.attack6AnimationState);
		list.add(this.attack7AnimationState);
		list.add(this.attack8AnimationState);
		list.add(this.shockAnimationState);
		list.add(this.shockToCorrosiveAnimationState);
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
		return this.getShockUseTick() > 0 || this.isShockJump();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("ShockTick", this.getShockTick());
		compoundTag.putInt("ShockUseTick", this.getShockUseTick());
		compoundTag.putBoolean("ShouldShock", this.isShouldShock());;
		compoundTag.putBoolean("ShockJump", this.isShockJump());;
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("AttackWalk", this.getAttackWalk());
		compoundTag.putInt("JumpAnim", this.getJumpAnim());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setShockTick(compoundTag.getInt("ShockTick"));
		this.setShockUseTick(compoundTag.getInt("ShockUseTick"));
		this.setShouldShock(compoundTag.getBoolean("ShouldShock"));
		this.setShockJump(compoundTag.getBoolean("ShockJump"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setAttackWalk(compoundTag.getInt("AttackWalk"));
		this.setJumpAnim(compoundTag.getInt("JumpAnim"));
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ATTACK_WALK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(SHOCK_TICK, 600);
		this.getEntityData().define(SHOCK_USE_TICK, 0);
		this.getEntityData().define(JUMP_ANIM, 0);
		this.getEntityData().define(SHOULD_SHOCK, false);
		this.getEntityData().define(SHOCK_JUMP, false);
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
						this.attack5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack5AnimationState);
						break;
					case 6:
						this.attack6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack6AnimationState);
						break;
					case 7:
						this.attack7AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack7AnimationState);
						break;
					case 8:
						this.attack8AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack8AnimationState);
						break;
					case 9:
						this.shockAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shockAnimationState);
						break;
					case 10:
						this.shockToCorrosiveAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shockToCorrosiveAnimationState);
						break;
					case 11:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
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
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
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
		}
		if (this.getAttackTick() == 20 && this.isAlive()) {
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
		//跳跃
		if (this.isShockJump() && !(this.getTarget() != null && this.getTarget().distanceToSqr(this) <= 16)) {
			if (!this.level().isClientSide()) {
				this.setJumpAnim(Math.min(this.getJumpAnim() + 1, 20));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setJumpAnim(Math.max(this.getJumpAnim() - 2, 0));
			}
		}

		//动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}

		//技能
		{
			//威慑
			if (this.isAlive()) {
				//判定
				if (this.isAggressive() && this.getAttackTick() <= -10 && !this.specialAction() && this.getShockTick() >= 600 && this.isShouldShock() && this.getTarget() != null && this.getTarget() instanceof LivingEntity) {
					if (!this.level().isClientSide()) {
						this.setShouldShock(false);
						this.setShockTick(0);
						this.setShockUseTick(120);
						this.setAnimTick(60);
						this.setAnimationState("shock");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.INTIMIDATION_STRIKE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				//可以威慑
				if (!isAggressive() && this.getTarget() == null) {
					if (!this.isShouldShock()) {
						if (!this.level().isClientSide()) {
							this.setShouldShock(true);
						}
					}
				}
				//蚀龙兽！！！
				if (this.getShockUseTick() == 95 && this.getTarget() instanceof CorrosiverEntity corrosiver) {
					if (!corrosiver.level().isClientSide()) {
						corrosiver.setAnimTick(20);
						corrosiver.setAnimationState("wind");
					}
				}
				if (this.getShockUseTick() == 85) {
					if (this.getTarget() instanceof CorrosiverEntity corrosiver) {
						if (!this.level().isClientSide()) {
							this.setShockUseTick(0);
							this.setAnimTick(15);
							this.setAnimationState("shockToCorrosive");
						}
						if (!corrosiver.isSilent()) {
							corrosiver.playSound(JerotesVillageSoundEvents.CORROSIVER_AMBIENT, 5.0f, 1.0f);
						}
					}
					else {
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.PRIMORDIER_SHOCK, 5.0f, 1.0f);
						}
					}
				}
				//吐气
				if (this.getShockUseTick() >= 75 && this.getShockUseTick() < 90) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
					float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
					float renderYawOffset = this.yBodyRot;
					Vec3 vec3 = new Vec3(this.xo + 2.25 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
							this.yo + 3.15f,
							this.zo + 2.25 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 70; i++) {
							double angle = (Math.PI * 2 * i) / 70;
							double offsetXAdd = Math.cos(angle);
							double offsetZAdd = Math.sin(angle);
							if (Main.canSeeAngle(this, new Vec3(this.getX() + offsetXAdd, 0, this.getZ() + offsetZAdd), 40)) {
								// 生成烟雾粒子
								serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x(), vec3.y(), vec3.z(), 0, offsetXAdd * 0.5f, -0.04, offsetZAdd * 0.5f, 1.0);
								serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x(), vec3.y(), vec3.z(), 0, offsetXAdd * 1.0f, -0.08, offsetZAdd * 1.0f, 1.0);
								serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x(), vec3.y(), vec3.z(), 0, offsetXAdd * 0.7f, -0.12, offsetZAdd * 0.7f, 1.0);
							}
						}
					}
					//冻伤
					List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(3));
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (!this.hasLineOfSight(hurt)) continue;
						if (!Main.canSee(hurt, this)) continue;
						if (hurt.getTicksFrozen() < 360) {
							hurt.setTicksFrozen(360);
						}
					}
					//视角摇晃
					List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
					for (LivingEntity shake : listShake) {
						if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
							continue;
						if (!shake.level().isClientSide()) {
							shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
						}
					}
				}
				//调整
				if (this.getShockUseTick() >= 75 && this.getTarget() != null) {
					this.getNavigation().setSpeedModifier(0.1f);
					this.rushFindXRot = this.getXRot();
					this.rushFindYRot = this.getYRot();
					this.lookAt(this.getTarget(), 360.0f, 360.0f);
				}
				//跳跃
				if (this.getShockUseTick() == 73 && this.getTarget() != null) {
					this.getNavigation().stop();
					if (!this.level().isClientSide()) {
						this.setShockJump(true);
						this.setAttackUse(3);
					}
					this.trueHurt();
					this.jumpToTarget(this.getTarget());
				}
				if (this.isShockJump() && this.getShockUseTick() < 60 && (this.onGround() || this.getShockUseTick() <= 0)) {
					if (this.onGround()) {
						if (!this.level().isClientSide()) {
							this.setAttackUse(3);
						}
						this.trueHurt();
					}
					if (!this.level().isClientSide()) {
						this.setShockUseTick(0);
						this.setShockJump(false);
					}
				}
				else {
					if (this.isShockJump() && this.getTarget() != null) {
						this.RushAttack();
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setShockTick(Math.min(600, this.getShockTick() + 1));
				this.setShockUseTick(Math.max(0, this.getShockUseTick() - 1));
			}
		}
	}

	public boolean jumpToTarget(LivingEntity target) {
		if (target != null) {
			float f = this.getYRot();
			float f2 = this.getXRot();
			float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f4 = -Mth.sin(f2 * 0.017453292f);
			float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
			float f7 = Math.min(this.distanceTo(target), 16);
			this.setOnGround(false);

			this.setDeltaMovement(
					f3 * (f7 / f6 * 1.35f),
					1.25f + Math.min(8, Math.max(0, target.getY() - this.getY())) * 0.2f,
					f5 * (f7 / f6 * 1.35f)
			);
			this.getNavigation().moveTo(target, 1.0f);
		}
		return true;
	}
	public boolean RushAttack() {
		if (!this.level().isClientSide()) {
			this.setXRot(this.rushFindXRot);
			this.setYRot(this.rushFindYRot);
			this.setYBodyRot(this.rushFindYRot);
			this.setYHeadRot(this.rushFindYRot);
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 5f;
		this.setOnGround(false);
		this.setDeltaMovement(this.getDeltaMovement().add(
				f3 * (f7 / f6 * 0.05f),
				0,
				f5 * (f7 / f6 * 0.05f)
		));
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isAggressive() && !this.specialAction() && this.getShockTick() >= 600 && this.isShouldShock() && this.getTarget() != null && this.getTarget() instanceof LivingEntity) {
			return false;
		}
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 35) {
				this.setAnimTick(60);
				this.setAttackTick(50);
				this.setAnimationState("attack1");
				this.setAttackUse(1);
			} else if (attackRandom > 30) {
				this.setAnimTick(40);
				this.setAttackTick(42);
				this.setAnimationState("attack2");
				this.setAttackUse(2);
			} else if (attackRandom > 25) {
				this.setAnimTick(40);
				this.setAttackTick(42);
				this.setAnimationState("attack3");
				this.setAttackUse(2);
			} else if (attackRandom > 20) {
				this.setAnimTick(50);
				this.setAttackTick(40);
				this.setAnimationState("attack4");
				this.setAttackUse(2);
			} else if (attackRandom > 15) {
				this.setAnimTick(50);
				this.setAttackTick(40);
				this.setAnimationState("attack5");
				this.setAttackUse(2);
			} else if (attackRandom > 10) {
				this.setAnimTick(60);
				this.setAttackTick(50);
				this.setAnimationState("attack6");
				this.setAttackUse(4);
			} else if (attackRandom > 5) {
				this.setAnimTick(40);
				this.setAttackTick(40);
				this.setAnimationState("attack7");
				this.setAttackUse(3);
			} else {
				this.setAnimTick(40);
				this.setAttackTick(40);
				this.setAnimationState("attack8");
				this.setAttackUse(3);
			}
		}
		this.getNavigation().stop();
		return true;
	}
	public boolean trueHurt() {
		//1-下砸
		//2-横扫
		//3-跃击
		//4-双手下砸

		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		if (this.getAttackUse() == 1) {
			damageMulti = 1.75f;
			reach = 0.75f;
		}
		if (this.getAttackUse() == 2) {
			knockbackMulti = 1.5f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.5f;
			knockbackMulti = 1.25f;
			reach = 1.25f;
		}
		if (this.getAttackUse() == 4) {
			damageMulti = 1.75f;
			knockbackMulti = 1.25f;
			reach = 0.75f;
		}

		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.PRIMORDIER_ATTACK, 5.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 128) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 1 && this.getAttackUse() != 3) continue;
			if (hurt instanceof PrimordierEntity primordierEntity && primordierEntity.getTarget() != this && this.getTarget() != primordierEntity)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl) {
				//效果
				if (hurt.getTicksFrozen() < 280) {
					hurt.setTicksFrozen(280);
				}
				if (!hurt.level().isClientSide()) {
					hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1), this);
					hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, 2), this);
				}
				//击飞
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.4;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-((this.getX() - hurt.getX()) * d3 / 2) * (0.7 + knockbackMulti / 3),
							-((this.getY() - hurt.getY()) * d3 / 2) * (0.7 + knockbackMulti / 3),
							-((this.getZ() - hurt.getZ()) * d3 / 2) * (0.7 + knockbackMulti / 3)
					));
					if (this.getAttackUse() == 2 && this.getAttackUse() == 4) {
						if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt))) {
							hurt.setDeltaMovement(hurt.getDeltaMovement().add(0f, (7 - this.distanceTo(hurt)) / 8, 0f));
						}
					}
				}
			}
		}
		//震动
		if (this.getAttackUse() == 1 || this.getAttackUse() == 3 || this.getAttackUse() == 4) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//粒子效果
		if (this.getAttackUse() == 1 || this.getAttackUse() == 3) {
			float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
			float renderYawOffset = this.yBodyRot;
			Vec3 vec3 = new Vec3(this.xo + 5 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					this.yo + 0.1f,
					this.zo + 5 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
			if (this.getAttackUse() == 3) {
				vec3 = this.getPosition(0.1f).add(0, 0.2f, 0);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 70; i++) {
					double angle = (Math.PI * 2 * i) / 70;
					double offsetXAdd = Math.cos(angle);
					double offsetZAdd = Math.sin(angle);
					// 生成烟雾粒子
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x() + offsetXAdd * 1.4f, vec3.y(), vec3.z() + offsetZAdd * 1.4f, 0, offsetXAdd * 1.4f, -0.04, offsetZAdd * 1.4f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x() + offsetXAdd * 1.0f, vec3.y(), vec3.z() + offsetZAdd * 1.0f, 0, offsetXAdd * 1.0f, -0.04, offsetZAdd * 1.0f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec3.x() + offsetXAdd * 0.7f, vec3.y(), vec3.z() + offsetZAdd * 0.7f, 0, offsetXAdd * 0.7f, -0.04, offsetZAdd * 0.7f, 1.0);
				}
			}
		}
		if (this.getAttackUse() == 4) {
			float angleYaw0 = (10 + 90.0F) * ((float) Math.PI / 180.0F);
			float angleYaw1 = (-10 + 90.0F) * ((float) Math.PI / 180.0F);
			float renderYawOffset = this.yBodyRot;
			Vec3 vec30 = new Vec3(this.xo + 4.75f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw0)),
					this.yo + 0.1f,
					this.zo + 5 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw0)));
			Vec3 vec31 = new Vec3(this.xo + 4.75f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw1)),
					this.yo + 0.1f,
					this.zo + 5 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw1)));
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 70; i++) {
					double angle = (Math.PI * 2 * i) / 70;
					double offsetXAdd = Math.cos(angle);
					double offsetZAdd = Math.sin(angle);
					// 生成烟雾粒子
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec30.x() + offsetXAdd * 1.4f, vec30.y(), vec30.z() + offsetZAdd * 1.4f, 0, offsetXAdd * 1.4f, -0.04, offsetZAdd * 1.4f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec30.x() + offsetXAdd * 1.0f, vec30.y(), vec30.z() + offsetZAdd * 1.0f, 0, offsetXAdd * 1.0f, -0.04, offsetZAdd * 1.0f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec30.x() + offsetXAdd * 0.7f, vec30.y(), vec30.z() + offsetZAdd * 0.7f, 0, offsetXAdd * 0.7f, -0.04, offsetZAdd * 0.7f, 1.0);

					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec31.x() + offsetXAdd * 1.4f, vec31.y(), vec31.z() + offsetZAdd * 1.4f, 0, offsetXAdd * 1.4f, -0.04, offsetZAdd * 1.4f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec31.x() + offsetXAdd * 1.0f, vec31.y(), vec31.z() + offsetZAdd * 1.0f, 0, offsetXAdd * 1.0f, -0.04, offsetZAdd * 1.0f, 1.0);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec31.x() + offsetXAdd * 0.7f, vec31.y(), vec31.z() + offsetZAdd * 0.7f, 0, offsetXAdd * 0.7f, -0.04, offsetZAdd * 0.7f, 1.0);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		//下落方块
		{
			if (this.getAttackUse() == 3) {
				if (this.level() instanceof ServerLevel serverLevel) {
					AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this) / 2 + 3, 0, Main.mobWidth(this) / 2 + 3);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
						Main.spawnFallingBlockByPos(serverLevel, blockPos);
					}
				}
			}
			if (this.getAttackUse() == 1) {
				float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
				float renderYawOffset = this.yBodyRot;
				Vec3 vec3 = new Vec3(this.xo + 5 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
						this.yo + 0.1f,
						this.zo + 5 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
				if (this.getAttackUse() == 3) {
					vec3 = this.getPosition(0.1f).add(0, 0.2f, 0);
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					AABB aabb = AABB.ofSize(new Vec3(vec3.x, vec3.y - 1, vec3.z), Main.mobWidth(this) / 2 + 3, 0, Main.mobWidth(this) / 2 + 3);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
						Main.spawnFallingBlockByPos(serverLevel, blockPos);
					}
				}
			}
			if (this.getAttackUse() == 4) {
				if (this.level() instanceof ServerLevel serverLevel) {
					AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this) / 2 + 12, 0, Main.mobWidth(this) / 2 + 12);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
						if (!Main.canSeeAngle(this, blockPos.getCenter(), 120))
							continue;
						Main.spawnFallingBlockByPos(serverLevel, blockPos);
					}
				}
			}
		}
		return true;
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 22;
	}
	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL) && this.isShockJump())
			return true;
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isColdDamage(damageSource)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isFireDamage(damageSource))
			return super.hurt(damageSource, amount * 2);
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
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
