package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MerorMonstrosityEntity extends MerorMachineEntity implements BreakShieldEntity, SpellUseEntity, StopLook, EliteEntity {
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState spellAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState sendAnimationState = new AnimationState();
	public AnimationState standAnimationState = new AnimationState();
	public AnimationState standStartAnimationState = new AnimationState();
	public AnimationState standStopAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_USE_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SEND_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SEND_USE_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STAND_TICK = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(MerorMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.NOTCHED_20, false);

	public MerorMonstrosityEntity(EntityType<? extends MerorMonstrosityEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(2.6f);
		this.xpReward = 120;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		this.lookControl = new GiantLookControl(this, 10);
		this.moveControl = new GiantMoveControl(this);
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

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 640);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 30);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(MerorMonstrosityEntity.this, 1.25, true));
		//this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}
	@Override
	protected SoundEvent getAmbientSound() {
		if (this.getStandTick() > 0 && !this.isInSittingPose()) {
			return JerotesVillageSoundEvents.MEROR_MONSTROSITY_AMBIENT;
		}
		return null;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_MONSTROSITY_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_MONSTROSITY_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEROR_MONSTROSITY_WALK, 1.0f, 1.0f);
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
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
	public int getShieldBreakStrength() {
		return 2400;
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 4.25f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.random.nextInt(5) == 1;
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
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
		return aabb1.inflate(1.75d, 1.75d, 1.75d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}

	public int spellLevel = 4;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setShootTick(int n){
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootTick(){
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setShootUseTick(int n){
		this.getEntityData().set(SHOOT_USE_TICK, n);
	}
	public int getShootUseTick(){
		return this.getEntityData().get(SHOOT_USE_TICK);
	}
	public void setSendTick(int n){
		this.getEntityData().set(SEND_TICK, n);
	}
	public int getSendTick(){
		return this.getEntityData().get(SEND_TICK);
	}
	public void setSendUseTick(int n){
		this.getEntityData().set(SEND_USE_TICK, n);
	}
	public int getSendUseTick(){
		return this.getEntityData().get(SEND_USE_TICK);
	}
	public void setStandTick(int n){
		this.getEntityData().set(STAND_TICK, n);
	}
	public int getStandTick(){
		return this.getEntityData().get(STAND_TICK);
	}
	private double blockDestroyTick;
	private int sitTick = 0;
	public boolean isStand() {
		return this.getEntityData().get(STAND);
	}
	public void setStand(boolean bl) {
		this.getEntityData().set(STAND, bl);
	}
	public boolean specialAction() {
		return (this.getStandTick() < 0 || this.getStandTick() > 5980) && !this.isTame() || this.getSendUseTick() > 0 || this.getShootUseTick() > 0;
	}
	@Override
	public boolean stopLookTime() {
		return specialAction() || this.getAttackTick() > 0 ||
				this.isTame() && this.isInSittingPose() && !this.isWander();
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "spell")){
			return 3;
		}
		else if (Objects.equals(animation, "send")){
			return 4;
		}
		else if (Objects.equals(animation, "dead")){
			return 5;
		}
		else {
			return super.getAnimationState(animation);
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = super.getAllAnimations();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.spellAnimationState);
		list.add(this.sendAnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("StandTick", this.getStandTick());
		compoundTag.putBoolean("IsStand", this.isStand());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("ShootUseTick", this.getShootUseTick());
		compoundTag.putInt("SendTick", this.getSendTick());
		compoundTag.putInt("SendUseTick", this.getSendUseTick());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("SitTick", this.sitTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setStandTick(compoundTag.getInt("StandTick"));
		if (compoundTag.contains("IsStand")) {
			this.setStand(compoundTag.getBoolean("IsStand"));
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setShootUseTick(compoundTag.getInt("ShootUseTick"));
		this.setSendTick(compoundTag.getInt("SendTick"));
		this.setSendUseTick(compoundTag.getInt("SendUseTick"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.sitTick = compoundTag.getInt("SitTick");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(SHOOT_USE_TICK, 0);
		this.getEntityData().define(SEND_TICK, 0);
		this.getEntityData().define(SEND_USE_TICK, 0);
		this.getEntityData().define(STAND_TICK, -30);
		this.getEntityData().define(STAND, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (STAND.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
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
						this.spellAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spellAnimationState);
						break;
					case 4:
						this.sendAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.sendAnimationState);
						break;
					case 5:
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
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
			this.bossEvent.update();
			if (OtherMainConfig.EliteBossBarOnlyCombat) {
				this.bossEvent.setVisible(this.getTarget() != null);
			}
			if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
				this.bossEvent.setColor(BossEvent.BossBarColor.RED);
			}
			else {
				this.bossEvent.setColor(BossEvent.BossBarColor.PINK);
			}
		}
		//注视
		if (this.getTarget() != null && !this.stopLookTime() && this.getAttackTick() <= 0) {
			if (this.distanceTo(this.getTarget()) > 12) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360f, 360f);
			} else {
				this.getLookControl().setLookAt(this.getTarget(), 30f, 30f);
				this.lookAt(this.getTarget(), 30f, 30f);
			}
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

		if (this.isInWater()) {
			this.hasImpulse = true;
		}

		if (this.isAlive() && (!this.isTame() && (this.getStandTick() <= 6000 && this.getStandTick() > 20) || (this.isTame() && !this.isInSittingPose()))) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		else {
			this.idleAnimationState.stop();
		}

		if (this.isAlive()) {
			if (this.isTame()) {
				if (this.isInSittingPose()) {
					this.idleAnimationState.stop();
				}
				else {
					this.standAnimationState.stop();
				}
				if (this.isInSittingPose() && sitTick <= 0){
					this.sitTick = 40;
				}
				if (!this.isInSittingPose() && sitTick > 0){
					this.standStopAnimationState.start(this.tickCount);
					this.sitTick = 0;
					this.standAnimationState.stop();
					this.standStartAnimationState.stop();
				}

				if (this.sitTick == 40){
					this.standStartAnimationState.start(this.tickCount);
				}
				if (this.sitTick == 20){
					this.standStartAnimationState.stop();
					this.standAnimationState.start(this.tickCount);
				}
				if (this.sitTick > 5){
					this.sitTick -= 1;
				}
			}
			else {
				//降低计时
				if (this.getStandTick() >= 0) {
					if (!this.level().isClientSide()) {
						this.setStandTick(this.getStandTick() - 1);
					}
				}
				//休息恢复
				else {
					if (this.getRandom().nextInt(100) == 1 && this.isAlive()) {
						this.heal(15.0f);
					}
				}
				//战斗状态
				if (this.isAggressive() || this.getSendUseTick() > 0 || this.getShootUseTick() > 0 || this.getTarget() != null || this.getLastHurtByMob() != null) {
					//休息取消
					if (this.getStandTick() <= 20) {
						if (!this.level().isClientSide()) {
							this.setStand(false);
							this.setStandTick(6000);
						}
					}
					else if (this.getStandTick() < 5980) {
						if (!this.level().isClientSide()) {
							this.setStandTick(5980);
						}
					}
				}
				//开始起身动画
				if (this.getStandTick() == 6000) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
					this.standStopAnimationState.start(this.tickCount);
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_MONSTROSITY_AWAKE, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
				//停止起身动画
				if (this.getStandTick() == 5979) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
				}
				//开始屈身动画
				if (this.getStandTick() == 21) {
					this.standAnimationState.stop();
					this.standStartAnimationState.start(this.tickCount);
				}
				//开始休息动画
				if (this.getStandTick() <= 0) {
					this.standStartAnimationState.stop();
					this.standAnimationState.start(this.tickCount);
					if (!this.isStand()) {
						if (!this.level().isClientSide()) {
							this.setStand(true);
						}
					}
				}
				else {
					this.standAnimationState.stop();
				}
			}
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, 5f);
			if (blockDestroy) {
				this.blockDestroyTick = 30;
			}
			if (!this.level().isClientSide()) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
		//近战攻击
		{
			if (this.getAttackTick() == 20 && this.isAlive()) {
				this.trueHurt();
			}
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-30, this.getAttackTick() - 1));
			}
			if (this.getAttackTick() > 40) {
				if (this.getTarget() != null && !this.stopLookTime()) {
					this.getLookControl().setLookAt(this.getTarget(), 120f, 120f);
					this.lookAt(this.getTarget(), 120.0f, 120.0f);
				}
				this.getNavigation().stop();
			}
			if (this.specialAction() || this.getAttackTick() > 0) {
				this.getNavigation().stop();
			}
			if ("Classic".equals(string) || "Old".equals(string) || "classic".equals(string) || "old".equals(string)) {
				if (this.getAttackTick() == 16) {
					double d = -Mth.sin(this.getYRot() * 0.017453292f);
					double d2 = Mth.cos(this.getYRot() * 0.017453292f);
					if (this.level() instanceof ServerLevel _level) {
						_level.sendParticles(JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK.get(), this.getX() + d, this.getY() + 0.1, this.getZ() + d2, 0, d, 0.0, d2, 0.0);
					}
				}
			}
		}

		List<MerorCubeEntity> listCube = this.level().getEntitiesOfClass(MerorCubeEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listCube.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
		List<MerorMonstrosityEntity> ListMonstrosity = this.level().getEntitiesOfClass(MerorMonstrosityEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		ListMonstrosity.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));

		//技能
		{
			//召唤莫厄立方
			{
				if (this.getAttackTick() <= -30 && this.getSendTick() >= 1200
						&& !this.specialAction() && !this.isNoAi()
						&& listCube.size() < ListMonstrosity.size() * 32
						&& this.getTarget() != null && this.getTarget().isAlive() && this.getTarget() instanceof LivingEntity && this.distanceTo(this.getTarget()) < 16
				) {
					if (!this.level().isClientSide()) {
						this.setSendUseTick(70);
					}
				}
				if (this.getSendUseTick() == 70) {
					if (!this.level().isClientSide()) {
						this.setSendTick(0);
						this.setAnimTick(70);
						this.setAnimationState("send");
					}
				}
				if (this.getSendUseTick() > 0) {
					this.getNavigation().stop();
					if (!this.level().isClientSide()) {
						this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
						this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
						this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
					}
				}
				if (this.getSendUseTick() == 50) {
					this.trueHurt(false);

					ParticleOptions particleOptions = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_IN.get();
					if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
						particleOptions = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_IN_REDSTONE_MONSTROSITY.get();
					}
					float angleYaw1 = (47.5f + 90.0F) * ((float) Math.PI / 180.0F);
					float angleYaw2 = (-47.5f + 90.0F) * ((float) Math.PI / 180.0F);
					float renderYawOffset = this.yBodyRot;
					if (this.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(particleOptions,
								this.xo + 3 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw1)),
								this.yo + 0.1f,
								this.zo + 3 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw1)),
								0, 0, 0, 0, 0);
						serverLevel.sendParticles(particleOptions,
								this.xo + 3 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw2)),
								this.yo + 0.1f,
								this.zo + 3 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw2)),
								0, 0, 0, 0, 0);
					}


					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_MONSTROSITY_ROAR, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
				if (this.getSendUseTick() == 25) {
					OtherSpellFind.ConjureMerorCube(this, this.getSpellLevel() * 2, this.getSpellLevel() * 3, 16);
				}
			}
			//莫厄炸弹
			{
				if (this.getAttackTick() <= -30 && this.getShootTick() >= 600
						&& !this.specialAction() && !this.isNoAi()
						&& this.getTarget() != null && this.getTarget().isAlive() && this.getTarget() instanceof LivingEntity && this.distanceTo(this.getTarget()) < 16) {
					if (!this.level().isClientSide()) {
						this.setShootUseTick(45);
					}
				}
				if (this.getShootUseTick() == 45) {
					if (!this.level().isClientSide()) {
						this.setShootTick(0);
						this.setAnimTick(45);
						this.setAnimationState("spell");
					}
				}
				if (this.getShootUseTick() > 0) {
					if (this.getTarget() != null && this.getShootUseTick() > 40) {
						this.getLookControl().setLookAt(this.getTarget(), 120f, 120f);
						this.lookAt(this.getTarget(), 120.0f, 120.0f);
					}
					this.getNavigation().stop();
				}
				if (this.getShootUseTick() == 15) {
					if (this.getTarget() != null) {
						OtherSpellFind.MerorBomb(this, this.getTarget(), 5, 6, 4, false);
					} else {
						OtherSpellFind.MerorBomb(this, this, 5, 6, 4, false);
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_MONSTROSITY_SHOOT, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setShootTick(Math.min(600, this.getShootTick() + 1));
				this.setShootUseTick(Math.max(0, this.getShootUseTick() - 1));
				this.setSendTick(Math.min(1200, this.getSendTick() + 1));
				this.setSendUseTick(Math.max(0, this.getSendUseTick() - 1));
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.specialAction() && this.getAttackTick() <= -30) {
			if (this.getRandom().nextFloat() > 0.75f) {
				if (!this.level().isClientSide()) {
					this.setAttackTick(30);
					this.setAnimTick(40);
					this.setAnimationState("attack1");
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAttackTick(35);
					this.setAnimTick(50);
					this.setAnimationState("attack2");
				}
			}
			return true;
		}
		return false;
	}

	public boolean trueHurt() {
		return trueHurt(true);
	}

	public boolean trueHurt(boolean useParticles) {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_MONSTROSITY_ATTACK, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(1.0));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, 1.35f, 1.0f, false, 0f);
			if (bl) {
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.5;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.8 * d3, -(this.getY() - hurt.getY()) * 0.7 * d3 + 0.1f, -(this.getZ() - hurt.getZ()) * 0.8 * d3));
				}
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt))) {
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(0f, (7 - this.distanceTo(hurt)) / 4, 0f));
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		//震动
		List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(24.0, 24.0, 24.0));
		for (LivingEntity shake : listShake) {
			if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
				continue;
			if (!shake.level().isClientSide()) {
				shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 40, 0, false, false), this);
			}
		}
		if (useParticles) {
			String string = ChatFormatting.stripFormatting(this.getName().getString());
			ParticleOptions particleOptions = JerotesVillageParticleTypes.MEROR_BULLET.get();
			ParticleOptions particleOptionsOut = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_OUT.get();
			ParticleOptions particleOptionsIn = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_IN.get();
			if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string) ) {
				particleOptions = ParticleTypes.CAMPFIRE_COSY_SMOKE;
				particleOptionsOut = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_OUT_REDSTONE_MONSTROSITY.get();
				particleOptionsIn = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_IN_REDSTONE_MONSTROSITY.get();
			}
			if (!("Classic".equals(string) || "Old".equals(string) || "classic".equals(string) || "old".equals(string))) {
				float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
				float renderYawOffset = this.yBodyRot;
				Vec3 vec3 = new Vec3(this.xo + 2 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo + 0.1f, this.zo + 2 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
				//范围粒子
				if (this.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(particleOptionsOut,
							vec3.x,
							vec3.y,
							vec3.z,
							0, 0, 0, 0, 0);
					serverLevel.sendParticles(particleOptionsIn,
							vec3.x,
							vec3.y,
							vec3.z,
							0, 0, 0, 0, 0);

					double d = -Mth.sin(this.getYRot() * 0.017453292f);
					double d2 = Mth.cos(this.getYRot() * 0.017453292f);
					for (int i = 0; i < 160; i++) {
						double angle = (Math.PI * 2 * i) / 160;
						double radius = 8;
						double offsetX = Math.cos(angle) * radius;
						double offsetZ = Math.sin(angle) * radius;
						// 生成烟雾粒子
						serverLevel.sendParticles(particleOptions,
								vec3.x() + offsetX + d, vec3.y() + 0.2f, vec3.z() + offsetZ + d2,
								0, offsetX * 0.02, -0.04, offsetZ * 0.02, 1.0);
					}
					for (int i = 0; i < 100; i++) {
						double angle = (Math.PI * 2 * i) / 100;
						double radius = 5;
						double offsetX = Math.cos(angle) * radius;
						double offsetZ = Math.sin(angle) * radius;
						// 生成烟雾粒子
						serverLevel.sendParticles(particleOptions,
								vec3.x() + offsetX + d, vec3.y() + 0.2f, vec3.z() + offsetZ + d2,
								0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount <= 4) {
			return false;
		}
		return super.hurt(damageSource, amount);
	}
	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
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
		this.standAnimationState.stop();
		this.standStopAnimationState.stop();
		this.standStartAnimationState.stop();
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
