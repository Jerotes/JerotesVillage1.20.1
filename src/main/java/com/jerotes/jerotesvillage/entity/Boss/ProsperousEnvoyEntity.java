package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.BeastToughEntity;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.UseShieldEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.RamblerEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProsperousEnvoyEntity extends RamblerEntity implements BossEntity, BeastToughEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState trampleAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MOVE_ABOUT = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BEAST_TOUGH_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> BEAST_TOUGH_DAMAGE = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_BEAST_TOUGH = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_COOLDOWN_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_BREAK_BEAST_TOUGH = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> TRAMPLE_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_TICK = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(ProsperousEnvoyEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.GREEN, false);

	public ProsperousEnvoyEntity(EntityType entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.1f);
		xpReward = 300;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.ProsperousEnvoyMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.ProsperousEnvoyMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.ProsperousEnvoyArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.ProsperousEnvoyMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.ProsperousEnvoyAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.ProsperousEnvoyKnockbackResistance);
		}
		if (level.isClientSide()) {
			BossBarEvent.BOSSES.add(this);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.ProsperousEnvoyMagicAttackPercentage;
		}
		return (float) OtherMainConfig.ProsperousEnvoyAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.ProsperousEnvoyDamageCap;
	}
	//间隔
	public boolean hasDamageCooldownTick() {
		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
		float base = 1.0F;
		if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
			base *= 0.5F;
		}
		return (float)OtherMainConfig.ProsperousEnvoyDamageCooldownTick * base;
	}

	@Override
	public int getExperienceReward() {
		return 300;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (this.start > 0) {
			this.bossEvent.setProgress(health1 / this.getMaxHealth());
			health1 += (this.getHealth() - health1) / 60;
		}
		else{
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 225);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12.0);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.PROSPEROUS_ENVOY_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.PROSPEROUS_ENVOY_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.PROSPEROUS_ENVOY_HURT;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.PROSPEROUS_ENVOY_WALK, 0.15f, 1.0f);
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.getRandom().nextInt(5) == 1 && this.getRushTick() > 0;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.PROSPEROUS_ENVOY_MUSIC;
	}
	@Override
	public boolean isOnFire() {
		if (this.isBeastTough()) {
			return false;
		}
		return super.isOnFire();
	}
	@Override
	public int getMaxHeadYRot() {
		return 35;
	}
	@Override
	public int getMaxHeadXRot() {
		return 30;
	}
	@Nullable
	@Override
	public ProsperousEnvoyEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.PROSPEROUS_ENVOY.get().create(serverLevel);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 2.3f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isBaby() {
		return false;
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
		return aabb1.inflate(0.85d, 0.85d, 0.85d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	private double blockDestroyTick;
	public int start = -101;
	private float health1 = 1;
	public float rushFindXRot;
	public float rushFindYRot;
	public boolean specialAction() {
		return this.getTrampleTick() > 0 || this.getRushTick() > 0;
	}
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
	public void setMoveAbout(int n){
		this.getEntityData().set(MOVE_ABOUT, n);
	}
	public int getMoveAbout(){
		return this.getEntityData().get(MOVE_ABOUT);
	}
	public void setTrampleTick(int n){
		this.getEntityData().set(TRAMPLE_TICK, n);
	}
	public int getTrampleTick(){
		return this.getEntityData().get(TRAMPLE_TICK);
	}
	public void setRushTick(int n){
		this.getEntityData().set(RUSH_TICK, n);
	}
	public int getRushTick(){
		return this.getEntityData().get(RUSH_TICK);
	}
	public void setBeastToughDamage(float f){
		this.getEntityData().set(BEAST_TOUGH_DAMAGE, f);
	}
	public float getBeastToughDamage(){
		return this.getEntityData().get(BEAST_TOUGH_DAMAGE);
	}
	@Override
	public boolean isBeastTough() {
		return this.getEntityData().get(IS_BEAST_TOUGH) && this.isAlive() && !this.isBreakBeastTough();
	}
	public void setBeastTough(boolean bl) {
		this.getEntityData().set(IS_BEAST_TOUGH, bl);
	}
	public void setBeastToughTick(int n){
		this.getEntityData().set(BEAST_TOUGH_TICK, n);
	}
	public int getBeastToughTick(){
		return this.getEntityData().get(BEAST_TOUGH_TICK);
	}
	@Override
	public boolean isBreakBeastTough() {
		return this.getEntityData().get(IS_BREAK_BEAST_TOUGH);
	}
	public void setBreakBeastTough(boolean bl) {
		this.getEntityData().set(IS_BREAK_BEAST_TOUGH, bl);
	}
	public void setBreakBeastToughTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_TICK, n);
	}
	public int getBreakBeastToughTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_TICK);
	}
	public void setBreakBeastToughCooldownTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_COOLDOWN_TICK, n);
	}
	public int getBreakBeastToughCooldownTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_COOLDOWN_TICK);
	}
	//动画
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
		else if (Objects.equals(animation, "trample")){
			return 4;
		}
		else if (Objects.equals(animation, "dead")){
			return 5;
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
		list.add(this.trampleAnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
	}
	public float getBeastToughBreakHurtCooldownMultiple() {
		return (float) OtherMainConfig.ProsperousEnvoyBreakToughHurtCooldownMultiple;
	}
	public int getBaseBeastToughTick() {
		return (int) (OtherMainConfig.ProsperousEnvoyToughHurtCooldown * 20);
	}
	public float getBreakBeastToughNeedBaseDamage() {
		return (int) (OtherMainConfig.ProsperousEnvoyBreakToughNeedBaseDamage);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("AttackTick", this.getAttackTick());

		compoundTag.putInt("BeastToughTick", this.getBeastToughTick());
		compoundTag.putFloat("BeastToughDamage", this.getBeastToughDamage());
		compoundTag.putBoolean("IsBeastTough", this.isBeastTough());
		compoundTag.putInt("BreakBeastToughTick", this.getBreakBeastToughTick());
		compoundTag.putInt("BreakBeastToughCooldownTick", this.getBreakBeastToughCooldownTick());
		compoundTag.putBoolean("IsBreakBeastTough", this.isBreakBeastTough());

		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("Start", this.start);
		compoundTag.putFloat("Health1", this.health1);
		compoundTag.putInt("TrampleTick", this.getTrampleTick());
		compoundTag.putInt("RushTick", this.getRushTick());
		compoundTag.putInt("MoveAbout", this.getMoveAbout());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		
		this.setBeastToughTick(compoundTag.getInt("BeastToughTick"));
		this.setBeastToughDamage(compoundTag.getFloat("BeastToughDamage"));
		this.setBeastTough(compoundTag.getBoolean("IsBeastTough"));
		this.setBreakBeastToughTick(compoundTag.getInt("BreakBeastToughTick"));
		this.setBreakBeastToughCooldownTick(compoundTag.getInt("BreakBeastToughCooldownTick"));
		this.setBreakBeastTough(compoundTag.getBoolean("IsBreakBeastTough"));
		
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.start = compoundTag.getInt("Start");
		this.health1 = compoundTag.getFloat("Health1");
		this.bossEvent.setId(this.getUUID());
		this.setTrampleTick(compoundTag.getInt("TrampleTick"));
		this.setRushTick(compoundTag.getInt("RushTick"));
		this.setMoveAbout(compoundTag.getInt("MoveAbout"));
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
	}
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(TRAMPLE_TICK, 0);
		this.getEntityData().define(RUSH_TICK, 0);

		this.getEntityData().define(BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BEAST_TOUGH_DAMAGE, 0f);
		this.getEntityData().define(IS_BEAST_TOUGH, false);
		this.getEntityData().define(BREAK_BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BREAK_BEAST_TOUGH_COOLDOWN_TICK, 0);
		this.getEntityData().define(IS_BREAK_BEAST_TOUGH, false);
		
		this.getEntityData().define(MOVE_ABOUT, 0);
		this.getEntityData().define(IS_LEGEND, false);
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
						this.trampleAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.trampleAnimationState);
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
	public void tick() {
		super.tick();
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
		//技能-强韧
		if (!level().isClientSide()) {
			this.setBeastToughTick(Math.max(0, this.getBeastToughTick() - 1));
			this.setBreakBeastToughTick(Math.max(0, this.getBreakBeastToughTick() - 1));
			this.setBreakBeastToughCooldownTick(Math.max(0, this.getBreakBeastToughCooldownTick() - 1));
			this.setBeastTough(this.getBeastToughTick() > 0);
			this.setBreakBeastTough(this.getBreakBeastToughTick() > 0);
		}
		if (this.isBeastTough() && this.getRemainingFireTicks() > 0) {
			this.clearFire();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.getRandom().nextInt(240) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}
		if (this.walkAnimation.speed() >= 0.01) {
			if (!this.level().isClientSide()) {
				this.setMoveAbout(Math.min(20, this.getMoveAbout() + 1));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setMoveAbout(Math.max(-20, this.getMoveAbout() - 1));
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//出现召唤
		if (this.start > 0) {
			this.start -= 1;
		}
		else if (this.start == 0) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.PROSPEROUS_ENVOY_AMBIENT, 10.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
			}
			if (!this.level().isClientSide()) {
				this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 660, 1));
			}
			this.start = -1;
		}
		else if (this.start > -100) {
			this.start -= 1;
		}
		if (this.start <= -100) {
			this.start = -101;
		}
		//近战攻击
		if (this.getAttackTick() == 5 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 0) {
			if (this.getTarget() != null && this.getAttackTick() > 8) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
			}
			this.getNavigation().stop();
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-(int)( OtherMainConfig.ProsperousEnvoyMeleeAttackCooldown * 20), this.getAttackTick() - 1));
			this.setTrampleTick(Math.max(0, this.getTrampleTick() - 1));
		}
		//技能-繁兴震地
		if (this.getTrampleTick() > 0) {
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 6; ++i) {
					if (!(this.getBlockStateOn().getBlock() instanceof AirBlock)) {
						serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1f), this.getY(0.1f), this.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
					}
				}
			}
			if (this.getTrampleTick() == 25) {
				this.TrampleAttack();
			}
		}
		//技能-繁兴冲刺
		if (this.getRushTick() > -(int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20 * 5) && this.isAlive()) {
			if (!this.level().isClientSide()) {
				this.setRushTick(this.getRushTick() - 1);
			}
			if (this.getRushTick() > 0) {
				if (this.getRushTick() > (int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20 - 20)) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				else if (this.getRushTick() == (int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20 - 20)) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
					this.rushFindXRot = this.getXRot();
					this.rushFindYRot = this.getYHeadRot();
				}
				else {
					if (this.getRushTick() > (int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20 - 25)) {
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
							this.lookAt(this.getTarget(), 360.0f, 360.0f);
						}
						this.rushFindXRot = this.getXRot();
						this.rushFindYRot = this.getYHeadRot();
					}
					if (this.getRushTick() % 5 == 0) {
						if (!this.level().isClientSide()) {
							if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
								boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
								AABB aABB = this.getBoundingBox().inflate(1).move(0, 1, 0);
								for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
									BlockState blockState = this.level().getBlockState(blockPos);
									float block = blockState.getDestroySpeed(this.level(), blockPos);
									if (block >= 5f || block < 0f) continue;
									if (!ForgeEventFactory.onEntityDestroyBlock(this, blockPos, blockState)) continue;
									if (!ForgeHooks.canEntityDestroy(this.level(), blockPos, this)) continue;
									if ((blockState.is(BlockTags.REPLACEABLE_BY_TREES) || blockState.is(BlockTags.DIRT) || blockState.is(BlockTags.SCULK_REPLACEABLE) || blockState.is(BlockTags.STONE_ORE_REPLACEABLES)) && this.getRandom().nextFloat() > 0.05) {
										bl = this.level().destroyBlock(blockPos, false, this) || bl;
									} else {
										bl = this.level().destroyBlock(blockPos, true, this) || bl;
									}
								}
							}
						}
					}
					this.RushAttack();
					//震动
					{
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
							}
						}
					}
				}
			}
		}
		if (this.getRushTick() <= -(int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20) * 5 && this.getTarget() != null && !this.isNoAi() && this.getRushTick() <= 0 && !this.getAttackBoundingBox().intersects(this.getTarget().getBoundingBox())) {
			if (!this.level().isClientSide()) {
				this.setRushTick((int) (OtherMainConfig.ProsperousEnvoyProsperousRushTime * 20));
			}
			this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
			this.rushFindXRot = this.getXRot();
			this.rushFindYRot = this.getYHeadRot();
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.PROSPEROUS_RUSH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
		}
		//粒子
		List<RamblerEntity> listRambler = this.level().getEntitiesOfClass(RamblerEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		int strength = Math.min(30, listRambler.size());
		if (this.tickCount % 3 == 0) {
			if (this.level().isClientSide()) {
				for (int i = 0; i < strength; ++i) {
					this.level().addParticle(ParticleTypes.CHERRY_LEAVES, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
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
				this.blockDestroyTick = OtherMainConfig.ProsperousEnvoyBreakBlockCooldown * 20;
			}
			if (!this.level().isClientSide()) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction() || this.getAttackTick() > -(int)( OtherMainConfig.ProsperousEnvoyMeleeAttackCooldown * 20)) {
			return false;
		}
		if (this.getRandom().nextFloat() > OtherMainConfig.ProsperousEnvoyProsperousTrampleChance) {
			if (!this.level().isClientSide()) {
				this.setAttackTick(10);
				this.setAnimTick(10);
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 20) {
					this.setAnimationState("attack1");
				} else if (attackRandom < 10) {
					this.setAnimationState("attack2");
				} else {
					this.setAnimationState("attack3");
				}
			}
			return true;
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackTick(0);
				this.setTrampleTick(40);
				this.setAnimTick(20);
				this.setAnimationState("trample");
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.PROSPEROUS_TRAMPLE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
			return true;
		}
	}
	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.PROSPEROUS_ENVOY_ATTACK, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		List<RamblerEntity> listRambler = this.level().getEntitiesOfClass(RamblerEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		int strength = Math.min(30, listRambler.size());
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.25, 0.25, 0.25));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			if (hurt instanceof RamblerEntity rambler && rambler.getTarget() != this && this.getTarget() != rambler)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, 1.0f, 1.0f, false, 0);
			if (bl2) {
				//击飞
				if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					double d2 = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					double d3 = Math.max(strength / 60f, 1.0 + strength / 60f - d2);
					if (this.onGround() && hurt.onGround() && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt))) {
						hurt.setOnGround(false);
						hurt.push(-(this.getX() - hurt.getX()) * 0.8 * d3, (0.1 + strength * 0.01), -(this.getZ() - hurt.getZ()) * 0.8 * d3);
					}
				}
				if (!hurt.level().isClientSide && Math.random() < 0.28) {
					hurt.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 1));
					hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
				}
				//麻醉
				if (!hurt.level().isClientSide()) {
					float anesthetizedTicks = 0;
					double anesthetizedAdds = this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 6 * 20;
					if (anesthetizedAdds != 0) {
						if (hurt.level() instanceof ServerLevel serverLevel) {
							serverLevel.sendParticles(JerotesParticleTypes.ANESTHETIZED_VIII.get(), hurt.getRandomX(0.5), hurt.getRandomY(), hurt.getRandomZ(0.5), 3, 0, 0, 0, 0);
						}
					}
					if (hurt.hasEffect(JerotesMobEffects.ANESTHETIZED.get())) {
						anesthetizedTicks = hurt.getEffect(JerotesMobEffects.ANESTHETIZED.get()).getDuration();
					}
					if ((anesthetizedAdds != 0 || anesthetizedTicks != 0) && !EntityFactionFind.isMachine(hurt) && !EntityFactionFind.isConstruct(hurt)) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.ANESTHETIZED.get(), (int) (anesthetizedAdds + anesthetizedTicks), 0, false, false));
					}
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
	}
	public void attackAnimation() {
	}

	public boolean TrampleAttack() {
		List<RamblerEntity> listRambler = this.level().getEntitiesOfClass(RamblerEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		int strength = Math.min(30, listRambler.size());
		//技能-繁兴震地
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.PROSPEROUS_ENVOY_TRAMPLE, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity attack : list) {
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			if (attack instanceof RamblerEntity rambler && rambler.getTarget() != this && this.getTarget() != rambler)
				continue;
			//击飞
			if (attack.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
				double d2 = attack.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
				double d3 = Math.max(strength / 60f, 1.0 + strength / 60f - d2);
				if (this.onGround() && attack.onGround() && (Main.mobSizeSmall(attack) || Main.mobSizeMedium(attack))) {
					attack.setOnGround(false);
					attack.push(-(this.getX() - attack.getX()) * 0.8 * d3, (0.15 + strength * 0.01), -(this.getZ() - attack.getZ()) * 0.8 * d3);
				}
			}
			AttackFind.attackBegin(this, attack);
			boolean bl2 = AttackFind.attackAfter(this, attack, (float) (3f + (strength * 0.05)), 2.0f, false, 0f);
			if (bl2) {
				//二次击飞
				if (attack.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					double d2 = attack.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					double d3 = Math.max(strength / 60f, 1.0 + strength / 60f - d2);
					if (this.onGround() && attack.onGround() && (Main.mobSizeSmall(attack) || Main.mobSizeMedium(attack))) {
						attack.setOnGround(false);
						attack.push(-(this.getX() - attack.getX()) * 0.8 * d3, (0.2 + strength * 0.01), -(this.getZ() - attack.getZ()) * 0.8 * d3);
					}
					if (!attack.level().isClientSide && Math.random() < 0.28) {
						attack.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 1));
						attack.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
					}
				}
				//麻醉
				if (!attack.level().isClientSide()) {
					float anesthetizedTicks = 0;
					double anesthetizedAdds = this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 120 * 20;
					if (anesthetizedAdds != 0) {
						if (attack.level() instanceof ServerLevel serverLevel) {
							serverLevel.sendParticles(JerotesParticleTypes.ANESTHETIZED_VIII.get(), attack.getRandomX(0.5), attack.getRandomY(), attack.getRandomZ(0.5), 3, 0, 0, 0, 0);
						}
					}
					if (attack.hasEffect(JerotesMobEffects.ANESTHETIZED.get())) {
						anesthetizedTicks = attack.getEffect(JerotesMobEffects.ANESTHETIZED.get()).getDuration();
					}
					if (!attack.level().isClientSide && (anesthetizedAdds != 0 || anesthetizedTicks != 0) && !EntityFactionFind.isMachine(attack) && !EntityFactionFind.isConstruct(attack)) {
						attack.addEffect(new MobEffectInstance(JerotesMobEffects.ANESTHETIZED.get(), (int) (anesthetizedAdds + anesthetizedTicks), 0, false, false));
					}
				}
			}
			//破盾
			if (attack.getUseItem().getItem() instanceof ShieldItem) {
				if (attack instanceof Player player) {
					player.disableShield(true);
				} else if (attack instanceof UseShieldEntity useShieldEntity) {
					useShieldEntity.disableShield();
				}
			}
		}
		//震动
		{
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		//下落方块
		AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 1, 0, Main.mobWidth(this)/2 + 1);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		return true;
	}

	public boolean RushAttack() {
		//技能-繁茂冲刺
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
		float f7 = 0.3f;
		float f8 = f4 *= f7 / f6 * 2;
		if (!this.onGround()) {
			f8 = 0;
		}
		this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 6; ++i) {
				if (!(this.getBlockStateOn().getBlock() instanceof AirBlock)) {
					serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1f), this.getY(0.1f), this.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
				}
			}
		}
		//攻击
		if (this.getRushTick() % 5 == 0) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.PROSPEROUS_ENVOY_RUSH, 10.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
			}
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
			for (LivingEntity attack : list) {
				if (AttackFind.FindCanNotAttack(this, attack)) continue;
				if (!this.hasLineOfSight(attack)) continue;
				if (attack instanceof RamblerEntity rambler && rambler.getTarget() != this && this.getTarget() != rambler)
					continue;
				AttackFind.attackBegin(this, attack);
				boolean bl2 = AttackFind.attackAfter(this, attack, 1.5f, 2.0f, false, 0f);
				if (bl2) {
					if (!attack.level().isClientSide()) {
						//麻醉
						float anesthetizedTicks = 0;
						double anesthetizedAdds = this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 15 * 20;
						if (anesthetizedAdds != 0) {
							if (attack.level() instanceof ServerLevel serverLevel) {
								serverLevel.sendParticles(JerotesParticleTypes.ANESTHETIZED_VIII.get(), attack.getRandomX(0.5), attack.getRandomY(), attack.getRandomZ(0.5), 3, 0, 0, 0, 0);
							}
						}
						if (attack.hasEffect(JerotesMobEffects.ANESTHETIZED.get())) {
							anesthetizedTicks = attack.getEffect(JerotesMobEffects.ANESTHETIZED.get()).getDuration();
						}
						if (!attack.level().isClientSide && (anesthetizedAdds != 0 || anesthetizedTicks != 0) && !EntityFactionFind.isMachine(attack) && !EntityFactionFind.isConstruct(attack)) {
							attack.addEffect(new MobEffectInstance(JerotesMobEffects.ANESTHETIZED.get(), (int) (anesthetizedAdds + anesthetizedTicks), 0, false, false));
						}
					}
				}
				//横扫效果
				Main.sweepAttack(this);
			}
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.start > -100){
			return true;
		}
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		List<RamblerEntity> listRambler = this.level().getEntitiesOfClass(RamblerEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		int strength = Math.min(30, listRambler.size());
		
		//强韧期间
		if (this.isBeastTough() && !this.isBreakBeastTough()) {
			//破除强韧
			if (this.getBeastToughDamage() > this.getBreakBeastToughNeedBaseDamage() && this.getBreakBeastToughCooldownTick() <= 0 && amount > this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple() && damageSource.getEntity() != null && EntityAndItemFind.isIgnoreSpecialHurtCooldown(damageSource.getEntity().getType())) {
				if (!this.level().isClientSide()) {
					this.setBreakBeastToughTick(this.getBaseBreakBeastToughTick());
					this.setBreakBeastToughCooldownTick(this.getBaseBreakBeastToughCooldownTick());
					if (!this.level().isClientSide()) {
						this.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), this.getBaseBreakBeastToughHoldTick(), 0, false, false));
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_BREAK.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
			}
			//强韧抵挡
			else if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple()) {
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
				return false;
			}
			//其他情况无视强韧
		}
		//其他情况设置强韧上限
		if (!this.level().isClientSide()) {
			this.setBeastToughDamage(amount);
		}
		//设置强韧间隔
		double toughBase = this.getBaseBeastToughTick();
		float toughMultiple = 0.5f;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			toughMultiple = 1f;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			toughMultiple = 1.5f;
		}
		if (!this.level().isClientSide()) {
			this.setBeastToughTick((int) (toughBase * toughMultiple));
		}

		return super.hurt(damageSource, amount * (1 - strength * 0.01f));
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			//骨粉
			if (this.level() instanceof ServerLevel serverLevel) {
				if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
					AABB aABB = this.getBoundingBox().inflate(4).move(0, -2, 0);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
						if (BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), serverLevel, blockPos) || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), serverLevel, blockPos, null)) {
							if (!serverLevel.isClientSide()) {
								serverLevel.levelEvent(1505, blockPos, 0);
							}
						}
					}
				}
			}
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}