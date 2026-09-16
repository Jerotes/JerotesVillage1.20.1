package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MerorGolemEntity extends MerorMachineEntity implements SpellUseEntity, EliteEntity, StopLook {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState sendAnimationState = new AnimationState();
	public AnimationState standAnimationState = new AnimationState();
	public AnimationState standStartAnimationState = new AnimationState();
	public AnimationState standStopAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(MerorGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SEND_TICK = SynchedEntityData.defineId(MerorGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SEND_USE_TICK = SynchedEntityData.defineId(MerorGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STAND_TICK = SynchedEntityData.defineId(MerorGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(MerorGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.NOTCHED_12, false);

	public MerorGolemEntity(EntityType<? extends MerorGolemEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(1.1f);
		this.xpReward = 100;
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
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

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 220);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(MerorGolemEntity.this, 1.25, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.getStandTick() > 0 && !this.isInSittingPose()) {
			return JerotesVillageSoundEvents.MEROR_GOLEM_AMBIENT;
		}
		return null;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_GOLEM_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_GOLEM_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEROR_GOLEM_WALK, 0.5f, 1.0f);
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
		return 2.95f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.random.nextInt(5) == 1;
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
		return aabb1.inflate(1.2d, 1.2d, 1.2d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
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
		return this.isTame() && this.isInSittingPose() && !this.isWander();
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
	public void setStandTick(int n){
		this.getEntityData().set(STAND_TICK, n);
	}
	public int getStandTick(){
		return this.getEntityData().get(STAND_TICK);
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
	private int sitTick = 0;
	public boolean isStand() {
		return this.getEntityData().get(STAND);
	}
	public void setStand(boolean bl) {
		this.getEntityData().set(STAND, bl);
	}
	public boolean specialAction() {
		return (this.getStandTick() < 0 || this.getStandTick() > 5980) && !this.isTame() || this.getSendUseTick() > 0;
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
		else if (Objects.equals(animation, "send")){
			return 3;
		}
		else if (Objects.equals(animation, "dead")){
			return 4;
		}
		else {
			return super.getAnimationState(animation);
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = super.getAllAnimations();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.sendAnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putBoolean("IsStand", this.isStand());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("StandTick", this.getStandTick());
		compoundTag.putInt("SendTick", this.getSendTick());
		compoundTag.putInt("SendUseTick", this.getSendUseTick());
		compoundTag.putInt("SitTick", this.sitTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		if (compoundTag.contains("IsStand")) {
			this.setStand(compoundTag.getBoolean("IsStand"));
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setStandTick(compoundTag.getInt("StandTick"));
		this.setSendTick(compoundTag.getInt("SendTick"));
		this.setSendUseTick(compoundTag.getInt("SendUseTick"));
		this.sitTick = compoundTag.getInt("SitTick");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(STAND_TICK, -10);
		this.getEntityData().define(SEND_TICK, 0);
		this.getEntityData().define(SEND_USE_TICK, 0);
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
						this.sendAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.sendAnimationState);
						break;
					case 4:
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
		if (this.isInWater()) {
			this.hasImpulse = true;
		}
		if (this.isAlive() && (!this.isTame() && this.getStandTick() > 40) || (this.isTame() && !this.isInSittingPose())) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		else {
			this.idleAnimationState.stop();
		}

		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-5, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 8 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.specialAction() || this.getAttackTick() > 0) {
			this.getNavigation().stop();
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
						this.heal(5.0f);
					}
				}
				//战斗状态
				if (this.isAggressive() || this.getSendUseTick() > 0 || this.getTarget() != null || this.getLastHurtByMob() != null) {
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
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_GOLEM_AMBIENT, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
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
		{
			{
				if (!this.specialAction() && !this.isNoAi() && this.getSendTick() >= 400 && this.getAttackTick() <= -5
						&& (this.getStandTick() > 20 || this.isTame())
						&& this.getTarget() != null && this.getTarget().isAlive() && this.getTarget() instanceof LivingEntity && this.distanceTo(this.getTarget()) < 16) {
					if (!this.level().isClientSide()) {
						this.setSendUseTick(90);
					}
				}
				if (this.getSendUseTick() == 90) {
					if (!this.level().isClientSide()) {
						this.setSendTick(0);
						this.setAnimTick(90);
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
				if ((this.getSendUseTick() == 75 || this.getSendUseTick() == 65 || this.getSendUseTick() == 55 || this.getSendUseTick() == 45 || this.getSendUseTick() == 35 || this.getSendUseTick() == 25) && this.isAlive()) {

					String string = ChatFormatting.stripFormatting(this.getName().getString());
					if (this.getRandom().nextFloat() <= 0.85f && !("Redstone Golem".equals(string) || "红石傀儡".equals(string))) {
						if (this.getTarget() != null) {
							OtherSpellFind.MerorTrap(this, this.getTarget(), this.getSpellLevel() * 2, this.getSpellLevel() * 4, 6, 2);
						} else {
							OtherSpellFind.MerorTrap(this, this, this.getSpellLevel() * 2, this.getSpellLevel() * 4, 6, 2);
						}
					}
					else if (this.getSendUseTick() == 65 || this.getSendUseTick() == 45 || this.getSendUseTick() == 25) {
						if (this.getTarget() != null) {
							OtherSpellFind.MerorMines(this, this.getTarget(), this.getSpellLevel(), this.getSpellLevel() * 2, 6, 2);
						} else {
							OtherSpellFind.MerorMines(this, this, this.getSpellLevel(), this.getSpellLevel() * 2, 6, 2);
						}
					}
					if (this.getSendUseTick() == 75) {
						if (!this.isSilent()) {
							this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_GOLEM_ROAR, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
						}
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setSendTick(Math.min(400, this.getSendTick() + 1));
				this.setSendUseTick(Math.max(0, this.getSendUseTick() - 1));
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction()) {
			return false;
		}
		if (this.getAttackTick() > -5) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(15);
			this.setAnimTick(15);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 15) {
				this.setAnimationState("attack1");
			} else {
				this.setAnimationState("attack2");
			}
		}
		return true;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.MEROR_GOLEM_ATTACK, 2.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(1.0f));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, 1.0f, 1.0f, false, 0f);
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
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
