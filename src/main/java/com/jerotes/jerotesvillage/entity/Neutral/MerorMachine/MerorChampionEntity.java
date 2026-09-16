package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Other.FallingBlock.BaseFallingBlock;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorReteEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.util.Other;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
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
import java.util.List;
import java.util.Objects;

public class MerorChampionEntity extends MerorMachineEntity implements BreakShieldEntity, UseThrownJavelinEntity, SpellUseEntity, StopLook, EliteEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState baseAnimationState = new AnimationState();
	public AnimationState chop1AnimationState = new AnimationState();
	public AnimationState chop2AnimationState = new AnimationState();
	public AnimationState slash1AnimationState = new AnimationState();
	public AnimationState slash2AnimationState = new AnimationState();
	public AnimationState slash3AnimationState = new AnimationState();
	public AnimationState stomp1AnimationState = new AnimationState();
	public AnimationState overloadAnimationState = new AnimationState();
	public AnimationState shockAnimationState = new AnimationState();
	public AnimationState throwAnimationState = new AnimationState();
	public AnimationState cycloneAnimationState = new AnimationState();
	public AnimationState hornAnimationState = new AnimationState();
	public AnimationState rushStartAnimationState = new AnimationState();
	public AnimationState rushStopAnimationState = new AnimationState();
	public AnimationState standAnimationState = new AnimationState();
	public AnimationState standStartAnimationState = new AnimationState();
	public AnimationState standStopAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ACTION_SURGE = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERLOAD_COOLDOWN = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOCK_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOCK_USE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RETE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RETE_USE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CYCLONE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CYCLONE_USE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_USE_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STAND_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PERFECT = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> FAULT_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FAULT_COOLDOWN_TICK = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<ItemStack> COATING = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Boolean> FIRST_COMBAT = SynchedEntityData.defineId(MerorChampionEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.NOTCHED_12, false);

	public MerorChampionEntity(EntityType<? extends MerorChampionEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(1.1f);
		this.xpReward = 120;
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 355);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 16);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(MerorChampionEntity.this, 1.25, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.getStandTick() > 0 && !this.isInSittingPose()) {
			return JerotesVillageSoundEvents.MEROR_CHAMPION_AMBIENT;
		}
		return null;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_CHAMPION_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_CHAMPION_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_WALK, 0.5f, 1.0f);
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
	public int getShieldBreakStrength() {
		return 100;
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
		if (this.getRushUseTick() <= 0 && this.getCycloneUseTick() <= 0) {
			super.push(0, 0, 0);
		}
		else {
			super.push(d, d2, d3);
		}
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 3.5f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.random.nextInt(5) == 1;
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
		return aabb1.inflate(1.2d, 1.2d, 1.2d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	private double blockDestroyTick;
	public int spellLevel = 4;
	public float rushFindXRot;
	public float rushFindYRot;
	@Override
	public int getSpellLevel() {
		return this.spellLevel + (this.isPerfect() ? 0 : -1);
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getActionSurge() {
		return this.getEntityData().get(ACTION_SURGE);
	}
	public void setActionSurge(int n) {
		this.getEntityData().set(ACTION_SURGE, n);
	}
	public int getOverloadCooldown() {
		return this.getEntityData().get(OVERLOAD_COOLDOWN);
	}
	public void setOverloadCooldown(int n) {
		this.getEntityData().set(OVERLOAD_COOLDOWN, n);
	}
	public void setStandTick(int n){
		this.getEntityData().set(STAND_TICK, n);
	}
	public int getStandTick(){
		return this.getEntityData().get(STAND_TICK);
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
	public void setReteTick(int n){
		this.getEntityData().set(RETE_TICK, n);
	}
	public int getReteTick(){
		return this.getEntityData().get(RETE_TICK);
	}
	public void setReteUseTick(int n){
		this.getEntityData().set(RETE_USE_TICK, n);
	}
	public int getReteUseTick(){
		return this.getEntityData().get(RETE_USE_TICK);
	}
	public void setCycloneTick(int n){
		this.getEntityData().set(CYCLONE_TICK, n);
	}
	public int getCycloneTick(){
		return this.getEntityData().get(CYCLONE_TICK);
	}
	public void setCycloneUseTick(int n){
		this.getEntityData().set(CYCLONE_USE_TICK, n);
	}
	public int getCycloneUseTick(){
		return this.getEntityData().get(CYCLONE_USE_TICK);
	}
	public void setRushTick(int n){
		this.getEntityData().set(RUSH_TICK, n);
	}
	public int getRushTick(){
		return this.getEntityData().get(RUSH_TICK);
	}
	public void setRushUseTick(int n){
		this.getEntityData().set(RUSH_USE_TICK, n);
	}
	public int getRushUseTick(){
		return this.getEntityData().get(RUSH_USE_TICK);
	}
	private int sitTick = 0;
	public boolean isStand() {
		return this.getEntityData().get(STAND);
	}
	public void setStand(boolean bl) {
		this.getEntityData().set(STAND, bl);
	}
	public boolean isPerfect() {
		return this.getEntityData().get(PERFECT);
	}
	public void setPerfect(boolean bl) {
		this.getEntityData().set(PERFECT, bl);
	}
	public boolean isFirstCombat() {
		return this.getEntityData().get(FIRST_COMBAT);
	}
	public void setFirstCombat(boolean bl) {
		this.getEntityData().set(FIRST_COMBAT, bl);
	}
	public void setFaultTick(int n){
		this.getEntityData().set(FAULT_TICK, n);
	}
	public int getFaultTick(){
		return this.getEntityData().get(FAULT_TICK);
	}
	public void setFaultCooldownTick(int n){
		this.getEntityData().set(FAULT_COOLDOWN_TICK, n);
	}
	public int getFaultCooldownTick(){
		return this.getEntityData().get(FAULT_COOLDOWN_TICK);
	}
	public void setCoating(ItemStack itemStack){
		if (itemStack == null)
			return;
		this.getEntityData().set(COATING, itemStack);
	}
	public ItemStack getCoating(){
		return this.getEntityData().get(COATING);
	}
	public boolean isFault() {
		return getFaultTick() > 0;
	}
	public boolean specialAction() {
		return (this.getStandTick() < 0 || this.getStandTick() > 5980) && !this.isTame() ||
				this.getShockUseTick() > 0 ||
				this.getReteUseTick() > 0 ||
				this.getCycloneUseTick() > 0 ||
				this.getRushUseTick() > 0 ||
				this.isFault();
	}
	public boolean specialActionCanMove() {
		return (this.getCycloneUseTick() > 0 || this.getRushUseTick() > 0) &&
				!this.isFault();
	}
	@Override
	public boolean stopLookTime() {
		return specialAction() && !specialActionCanMove() ||
				this.isFault() ||
				this.isTame() && this.isInSittingPose() && !this.isWander();
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "chop1")){
			return 1;
		}
		else if (Objects.equals(animation, "chop2")){
			return 2;
		}
		else if (Objects.equals(animation, "slash1")){
			return 3;
		}
		else if (Objects.equals(animation, "slash2")){
			return 4;
		}
		else if (Objects.equals(animation, "slash3")){
			return 5;
		}
		else if (Objects.equals(animation, "stomp1")){
			return 6;
		}
		else if (Objects.equals(animation, "overload")){
			return 7;
		}
		else if (Objects.equals(animation, "shock")){
			return 8;
		}
		else if (Objects.equals(animation, "throw")){
			return 9;
		}
		else if (Objects.equals(animation, "cyclone")){
			return 10;
		}
		else if (Objects.equals(animation, "horn")){
			return 11;
		}
		else if (Objects.equals(animation, "dead")){
			return 12;
		}
		else {
			return super.getAnimationState(animation);
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = super.getAllAnimations();
		list.add(this.chop1AnimationState);
		list.add(this.chop2AnimationState);
		list.add(this.slash1AnimationState);
		list.add(this.slash2AnimationState);
		list.add(this.slash3AnimationState);
		list.add(this.stomp1AnimationState);
		list.add(this.overloadAnimationState);
		list.add(this.shockAnimationState);
		list.add(this.throwAnimationState);
		list.add(this.cycloneAnimationState);
		list.add(this.hornAnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putBoolean("IsStand", this.isStand());
		compoundTag.putBoolean("IsPerfect", this.isPerfect());
		compoundTag.putBoolean("IsFirstCombat", this.isFirstCombat());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("ActionSurge", this.getActionSurge());
		compoundTag.putInt("OverloadCooldown", this.getOverloadCooldown());
		compoundTag.putInt("StandTick", this.getStandTick());
		compoundTag.putInt("ShockTick", this.getShockTick());
		compoundTag.putInt("ShockUseTick", this.getShockUseTick());
		compoundTag.putInt("ReteTick", this.getReteTick());
		compoundTag.putInt("ReteUseTick", this.getReteUseTick());
		compoundTag.putInt("CycloneTick", this.getCycloneTick());
		compoundTag.putInt("CycloneUseTick", this.getCycloneUseTick());
		compoundTag.putInt("RushTick", this.getRushTick());
		compoundTag.putInt("RushUseTick", this.getRushUseTick());
		compoundTag.putInt("FaultTick", this.getFaultTick());
		compoundTag.putInt("FaultCooldownTick", this.getFaultCooldownTick());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
		compoundTag.put("Coating", this.getCoating().save(new CompoundTag()));
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
		this.setPerfect(compoundTag.getBoolean("IsPerfect"));
		this.setFirstCombat(compoundTag.getBoolean("IsFirstCombat"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setActionSurge(compoundTag.getInt("ActionSurge"));
		this.setOverloadCooldown(compoundTag.getInt("OverloadCooldown"));
		this.setStandTick(compoundTag.getInt("StandTick"));
		this.setShockTick(compoundTag.getInt("ShockTick"));
		this.setShockUseTick(compoundTag.getInt("ShockUseTick"));
		this.setReteTick(compoundTag.getInt("ReteTick"));
		this.setReteUseTick(compoundTag.getInt("ReteUseTick"));
		this.setCycloneTick(compoundTag.getInt("CycloneTick"));
		this.setCycloneUseTick(compoundTag.getInt("CycloneUseTick"));
		this.setRushTick(compoundTag.getInt("RushTick"));
		this.setRushUseTick(compoundTag.getInt("RushUseTick"));
		this.setFaultTick(compoundTag.getInt("FaultTick"));
		this.setFaultCooldownTick(compoundTag.getInt("FaultCooldownTick"));
		this.sitTick = compoundTag.getInt("SitTick");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
		ItemStack itemstack = ItemStack.of(compoundTag.getCompound("Coating"));
		this.setCoating(itemstack);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ACTION_SURGE, 0);
		this.getEntityData().define(OVERLOAD_COOLDOWN, 0);
		this.getEntityData().define(STAND_TICK, -10);
		this.getEntityData().define(SHOCK_TICK, 400);
		this.getEntityData().define(SHOCK_USE_TICK, 0);
		this.getEntityData().define(RETE_TICK, 0);
		this.getEntityData().define(RETE_USE_TICK, 0);
		this.getEntityData().define(CYCLONE_TICK, 0);
		this.getEntityData().define(CYCLONE_USE_TICK, 0);
		this.getEntityData().define(RUSH_TICK, 0);
		this.getEntityData().define(RUSH_USE_TICK, 0);
		this.getEntityData().define(STAND, false);
		this.getEntityData().define(PERFECT, false);
		this.getEntityData().define(FAULT_TICK, 0);
		this.getEntityData().define(FAULT_COOLDOWN_TICK, 0);
		this.getEntityData().define(FIRST_COMBAT, true);
		this.getEntityData().define(COATING, Items.AIR.getDefaultInstance());
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
						this.chop1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.chop1AnimationState);
						break;
					case 2:
						this.chop2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.chop2AnimationState);
						break;
					case 3:
						this.slash1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash1AnimationState);
						break;
					case 4:
						this.slash2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash2AnimationState);
						break;
					case 5:
						this.slash3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash3AnimationState);
						break;
					case 6:
						this.stomp1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stomp1AnimationState);
						break;
					case 7:
						this.overloadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.overloadAnimationState);
						break;
					case 8:
						this.shockAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shockAnimationState);
						break;
					case 9:
						this.throwAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.throwAnimationState);
						break;
					case 10:
						this.cycloneAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.cycloneAnimationState);
						break;
					case 11:
						this.hornAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hornAnimationState);
						break;
					case 12:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getItem() instanceof BrushItem) {
			this.setCoating(Items.AIR.getDefaultInstance());
			player.swing(interactionHand);
		}
		else if (!itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/meror_metal"))) && !itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/refine_meror_metal")))) {
			if ((itemStack.is(ItemTags.TRIM_MATERIALS) ||
					itemStack.is(JerotesVillageItems.WILDERNESS_DIRT.get()) ||
					itemStack.is(Items.RED_MUSHROOM) ||
					itemStack.is(Items.BROWN_MUSHROOM) ||
					itemStack.is(Items.DANDELION) ||
					itemStack.is(Items.SUNFLOWER) ||
					itemStack.is(Items.PINK_TULIP) ||
					itemStack.is(Items.LEATHER)
			) && (!this.isTame() || this.getOwner() != null && player == this.getOwner())) {
				this.setCoating(itemStack);
				player.swing(interactionHand);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			}
		}
		return super.mobInteract(player, interactionHand);
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
		if (this.getTarget() != null && !this.isFault()) {
			this.getLookControl().setLookAt(this.getTarget(), 10.0f, 10.0f);
		}
		if (this.specialAction() && !this.specialActionCanMove() || this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		//基础动画
		this.baseAnimationState.startIfStopped(this.tickCount);
		//站立动画
		if (this.isAlive() && (!this.isTame() && this.getStandTick() > 40) || (this.isTame() && !this.isInSittingPose())) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		else {
			this.idleAnimationState.stop();
		}
		//清除动画
		if (!this.isFault()) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
			}
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//起身站立
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
				if (this.isAggressive() || this.getShockUseTick() > 0 || this.getTarget() != null || this.getLastHurtByMob() != null) {
					//休息取消
					if (this.getStandTick() <= 20) {
						if (!this.level().isClientSide()) {
							this.setStand(false);
							this.setStandTick(this.isFirstCombat() && !this.isTame() ? 6020 : 6000);
						}
					}
					else if (this.getStandTick() < 5980) {
						if (!this.level().isClientSide()) {
							this.setStandTick(5980);
						}
					}
				}
				//开始起身动画
				if (this.getStandTick() == 6000 && !this.isFirstCombat() || this.isFirstCombat() && this.getStandTick() == 6020) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
					this.standStopAnimationState.start(this.tickCount);
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_CHAMPION_AWAKE, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
				//停止起身动画
				if (this.getStandTick() == 5979) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
					if (!this.level().isClientSide()) {
						if (this.isFirstCombat()) {
							this.setFirstCombat(false);
						}
					}
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
			if (!this.level().isClientSide()) {
				if (!this.isFault()) {
					this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
				}
			}
			if (!this.isFault()) {
				if (this.getAttackUse() == 2) {
					if (this.getAttackTick() == 20 && this.isAlive()) {
						this.trueHurtFind();
					}
				} else if (this.getAttackUse() == 5) {
					if (this.getAttackTick() == 40 && this.isAlive()) {
						this.trueHurtFind();
					}
				} else {
					if (this.getAttackTick() == 10 && this.isAlive()) {
						this.trueHurtFind();
					}
				}
			}
			//故障
			if (this.getFaultCooldownTick() <= 0 && !this.isFault() && !this.isPerfect()) {
				if (this.getAttackTick() > 10 && this.getRandom().nextInt(10 * 20) == 1) {
					int randomTick = Main.randomReach(this.getRandom(), 2, 4) * 20;
					if (!this.level().isClientSide()) {
						this.setFaultTick(randomTick);
						this.setFaultCooldownTick(randomTick + 240);
					}
				}
			}
		}
		//过载
		{
			//静止
			if (!this.level().isClientSide()) {
				this.setFaultTick(Math.max(0, this.getFaultTick() - 1));
			}
			if (this.isFault()) {
				this.tickCount --;
				if (!this.level().isClientSide) {
					this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
					this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
					this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
				}
				this.setDeltaMovement(this.getDeltaMovement().multiply(0f, 1, 0f));
				this.getNavigation().stop();
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 15; ++i) {
						serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
								this.getRandomX(0.75f),
								this.getRandomY(),
								this.getRandomZ(0.75f), 0, 0.0, 0.0, 0.0, 0.0);
					}
				}
			}
		}
		//技能
		if (!this.isFault() && this.isAlive()) {
			//冠军威压
			{
				if (this.getShockTick() >= 480 && this.spellNeed(6, 16, 4) ) {
					if (!this.level().isClientSide()) {
						this.setShockUseTick(40);
					}
				}
				if (this.getShockUseTick() == 40) {
					if (!this.level().isClientSide()) {
						this.setShockTick(0);
						this.setAnimTick(40);
						this.setAnimationState("shock");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.CHAMPION_SHOCK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				if (this.getShockUseTick() == 30 && this.isAlive()) {
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_SHOCK, 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
				if (this.getShockUseTick() >= 10 && this.getShockUseTick() <= 30 && this.isAlive()) {
					//震动
					List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
					for (LivingEntity shake : listShake) {
						if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
							continue;
						if (!shake.level().isClientSide()) {
							shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 60, 0, false, false), this);
						}
					}
					//粒子
					if (this.level() instanceof ServerLevel serverLevel) {
						float yaw = this.yHeadRot * ((float) Math.PI / 180F);
						float pitch = this.getXRot() * ((float) Math.PI / 180F);
						Vec3 direction = new Vec3(
								-Math.sin(yaw) * Math.cos(pitch),
								-Math.sin(pitch),
								Math.cos(yaw) * Math.cos(pitch)
						).normalize();
						Vec3 center = this.position()
								.add(0, 3.35f, 0)
								.add(direction.scale(0.175f * (30 - (this.getShockUseTick() - 10))));
						float radius = 0.055f * (30 - (this.getShockUseTick() - 10));
						int particleCount = 20;
						Vec3 up = new Vec3(0, 1, 0);
						Vec3 right = direction.cross(up).normalize();
						for (int i = 0; i < particleCount; i++) {
							float angle = (float) (2 * Math.PI * i / particleCount);
							Vec3 offset = right.scale(radius * Math.cos(angle))
									.add(up.scale(radius * Math.sin(angle)));
							Vec3 particlePos = center.add(offset);
							serverLevel.sendParticles(
									JerotesVillageParticleTypes.MEROR_BULLET.get(),
									particlePos.x, particlePos.y, particlePos.z,
									1,
									0, 0, 0,
									0.015
							);
							serverLevel.sendParticles(
									ParticleTypes.CRIMSON_SPORE,
									particlePos.x, particlePos.y, particlePos.z,
									1,
									0, 0, 0,
									0.015
							);
						}
					}
					//下落方块
					if (this.getRushUseTick() % 3 == 0) {
						AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this) / 2 + 48, 0, Main.mobWidth(this) / 2 + 48);
						if (this.level() instanceof ServerLevel serverLevel) {
							for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
								Vec3 vec3 = blockPos.getCenter();
								//0~20
								int shocks = -(this.getShockUseTick() - 30);
								boolean bl = this.getPosition(0).distanceTo(vec3) > shocks * 0.8f &&
										vec3.distanceTo(this.getPosition(0)) <= shocks * 1f;
								if (!CelestialCoilvineEntity.canSeeAngleFour(this, vec3, 0, 120) ||
										!bl) continue;
								if (this.getRandom().nextFloat() > 0.25f) continue;
								Other.spawnEarthquakeFallingBlockByPosSameHeight(serverLevel, blockPos, this, 0.03f * shocks);
							}
						}
					}
					//击飞
					List<Entity> list = this.level().getEntitiesOfClass(Entity.class,
							this.getAttackBoundingBox().inflate(16.0, 16.0, 16.0));
					for (Entity hurt : list) {
						if (hurt == null) continue;
						if ((this.distanceTo(hurt)) > 128) continue;
						if (AttackFind.FindCanNotAttack(this, hurt) && (hurt instanceof LivingEntity || hurt instanceof TraceableEntity)) continue;
						if (!Main.canSee(hurt, this) && this.distanceTo(hurt) > 6) continue;
						if (!this.hasLineOfSight(hurt)) continue;
						if (hurt instanceof BaseFallingBlock) continue;
						if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
							double d = 0.0;
							if (hurt instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
								d = Math.max(livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
							}
							double d3 = Math.max(0, 1 - d) + 0.2f;
							if (Main.canSeeAngle(this, hurt.getEyePosition(), 120)) {
								d3 += 0.2f;
							}
							if (Main.canSeeAngle(this, hurt.getEyePosition(), 80)) {
								d3 += 0.2f;
							}
							if (Main.canSeeAngle(this, hurt.getEyePosition(), 30)) {
								d3 += 0.2f;
							}
							if (hurt instanceof Player player && player.isShiftKeyDown()) {
								d3 /= 8f;
							}
							float base = (0.15f + 1 / this.distanceTo(hurt) / 3f) * 0.05f;
							hurt.setOnGround(false);
							hurt.setDeltaMovement(hurt.getDeltaMovement().add(
									-(this.getX() - hurt.getX()) * d3 * base,
									-(this.getY() - hurt.getY()) * d3 * base + 0.125f * d3 * base,
									-(this.getZ() - hurt.getZ()) * d3 * base
							));
						}
					}
				}
				if (this.getShockUseTick() == 10 && this.isAlive()) {
					List<LivingEntity> listShock = this.level().getEntitiesOfClass(LivingEntity.class,
							this.getBoundingBox().inflate(32.0, 32.0, 32.0));
					for (LivingEntity shock : listShock) {
						if (shock == null || shock == this || (this.distanceToSqr(shock)) > Double.MAX_VALUE)
							continue;
						if (AttackFind.FindCanNotAttack(this, shock))
							continue;;
						if (!shock.level().isClientSide()) {
							shock.addEffect(new MobEffectInstance(JerotesMobEffects.ABACK.get(), getSpellLevel() * 60, getSpellLevel() / 2, false, false), this);
						}
					}
				}
			}
			//莫厄斗网
			{
				if (this.getReteTick() >= 480 &&
						(this.spellNeed(12, 32, 4) || this.spellNeed(3, 16, 4) && this.getTarget() != null && (this.getTarget().getY() - this.getY() > 3 || this.getTarget().getY() - this.getY() < 3))) {
					if (!this.level().isClientSide()) {
						this.setReteUseTick(40);
					}
				}
				if (this.getReteUseTick() == 40) {
					if (!this.level().isClientSide()) {
						this.setReteTick(0);
						this.setAnimTick(40);
						this.setAnimationState("throw");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_RETE, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_RETE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				if (this.getReteUseTick() >= 5) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				if (this.getReteUseTick() == 15 && this.isAlive()) {
					int count = 4;
					if (InventoryEntity.isRangeJavelin(this.getOffhandItem())) {
						useTridentShoot(this, this.getTarget(), 3f);
						count -= 1;
					}
					for (int i = 0; i < count; ++i) {
						MerorReteEntity javelin = new MerorReteEntity(this.level(), this, new ItemStack(JerotesVillageItems.MEROR_RETE.get()));
						javelin.shootFromRotation(this, this.getXRot(), this.getYRot(), 0.0f, 2.2f + count * 0.3f, count);
						if (!this.isSilent()) {
							this.playSound(SoundEvents.TRIDENT_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
						}
						this.level().addFreshEntity(javelin);
					}
				}
			}
			//冠军旋风
			{
				if (this.getCycloneTick() >= 600 && this.spellNeed(0, 16, 3)) {
					if (!this.level().isClientSide()) {
						this.setCycloneUseTick(100);
					}
				}
				if (this.getCycloneUseTick() == 100) {
					if (!this.level().isClientSide()) {
						this.setCycloneTick(0);
						this.setAnimTick(100);
						this.setAnimationState("cyclone");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_CYCLONE, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.CHAMPION_CYCLONE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				if (this.getCycloneUseTick() < 90 && this.getCycloneUseTick() >= 20) {
					//穿梭
					if (this.getTarget() != null) {
						this.cyclone();
					}
					//粒子
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 15; ++i) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), this.getRandomX(0.75), this.getRandomY(), this.getRandomZ(0.75), 0, 0.0, 0.0, 0.0, 0);
						}
						float f = (this.getCycloneUseTick() - 20f) / 70f * 80 * 10;
						float f2 = 5 - (this.getCycloneUseTick() - 20f) / 70f * 5;
						for (int i = 0; i < 2; ++i) {
							for (int i2 = 0; i2 < 10; ++i2) {
								for (int i3 = 0; i3 < 11; ++i3) {
									double angle = (Math.PI * 2 * (f + i3)) / 80;
									double radius = 1 + ( 0.75 * f2 + i2 / 8f * (3 + f2 * 0.15) ) * 0.5f;
									double offsetX = Math.cos(angle) * radius;
									double offsetZ = Math.sin(angle) * radius;
									serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_BULLET.get(),
											this.getX(), this.getY(i2 / 10f), this.getZ(),
											0, offsetX, 0.1f, offsetZ, 0.05 + i * 0.005f);
								}
							}
						}
					}
					//
					if (this.getCycloneTick() % 10 == 0 && this.getCycloneTick() % 5 == 0 || this.getCycloneUseTick() == 35) {
						this.setAttackUse(2);
						this.trueHurt(2, 1.25f, false, 0.25f, 0.5f, false);
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
			}
			//冠军冲锋
			{
				if (this.getRushTick() >= 640 && this.spellNeed(6, 24, 4)) {
					if (!this.level().isClientSide()) {
						this.setRushUseTick(140);
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
					}
				}
				if (this.getRushUseTick() == 140) {
					this.level().broadcastEntityEvent(this, (byte)101);
					if (!this.level().isClientSide()) {
						this.setRushTick(0);
						this.setAnimTick(140);
						this.setAnimationState("Rush");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_RUSH, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.CHAMPION_RUSH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
					}
				}
				if (this.getRushUseTick() < 140 && this.getRushUseTick() > 0) {
					if (this.getRushUseTick() <= 120) {
						this.RushAttack();
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 15; ++i) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_BULLET.get(),
										this.getRandomX(0.25) - this.getDeltaMovement().x, this.getRandomY() - 0.5f -  this.getDeltaMovement().y, this.getRandomZ(0.25) - this.getDeltaMovement().z, 0,
										this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z, -0.5f);
							}
						}
						//震动
						if (this.getRushUseTick() % 5 == 0) {
							this.getNavigation().stop();
							List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
							for (LivingEntity shake : listShake) {
								if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
									continue;
								if (!shake.level().isClientSide()) {
									shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
								}
							}
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
					}
					if (this.getRushUseTick() > 120 || this.getRushUseTick() % 40 == 0) {
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
						this.rushFindXRot = this.getXRot();
						this.rushFindYRot = this.getYRot();
					}
					if (this.getRushUseTick() == 20) {
						this.rushStopAnimationState.start(this.tickCount);
					}
					if (this.getRushUseTick() == 10) {
						this.setAttackUse(2);
						this.trueHurt(2, 1.25f, true, 1.5f, 1.5f, false);
					}
					if (this.getRushUseTick() == 1) {
						this.rushStartAnimationState.stop();
						this.rushStopAnimationState.stop();
					}
				}
			}
			//
			if (!this.level().isClientSide()) {
				//过载计算
				this.setFaultCooldownTick(Math.max(0, this.getFaultCooldownTick() - 1));
				//技能
				this.setOverloadCooldown(Math.max(0, this.getOverloadCooldown() - 1));
				this.setShockTick(Math.min(480, this.getShockTick() + 1));
				this.setShockUseTick(Math.max(0, this.getShockUseTick() - 1));
				this.setReteTick(Math.min(480, this.getReteTick() + 1));
				this.setReteUseTick(Math.max(0, this.getReteUseTick() - 1));
				this.setCycloneTick(Math.min(600, this.getCycloneTick() + 1));
				this.setCycloneUseTick(Math.max(0, this.getCycloneUseTick() - 1));
				this.setRushTick(Math.min(640, this.getRushTick() + 1));
				this.setRushUseTick(Math.max(0, this.getRushUseTick() - 1));
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}

	public boolean cyclone() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.25f;
		if (this.getCycloneUseTick() <= 30) {
			f7 = 0.1f;
		}
		if (this.getCycloneUseTick() > 80) {
			f7 = 0.07f;
		}
		float f8 = f4 *= f7 / f6 * 2;
		this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
		return true;
	}
	//冲刺
	public boolean RushAttack() {
		if (!this.level().isClientSide()) {
			this.setXRot(this.rushFindXRot);
			this.setYRot(this.rushFindYRot);
			this.setYBodyRot(this.rushFindYRot);
			this.setYHeadRot(this.rushFindYRot);
		}
		float f = this.rushFindYRot;
		float f2 = this.rushFindXRot;
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.2f;
		if (this.getRushUseTick() <= 20) {
			f7 = 0.1f;
		}
		if (this.getRushUseTick() > 100) {
			f7 = 0.07f;
		}
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
		boolean bl = false;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5f));
		for (LivingEntity attack : list) {
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			if (attack.isShiftKeyDown()) continue;
			AttackFind.attackBegin(this, attack);
			boolean bl2 = AttackFind.attackAfter(this, attack, 0.75f, 1.2f, false, 0);
			if (bl2) {
				bl = true;
			}
		}
		if (bl) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAnimationState("horn");
			}
		}
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isFault()) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (this.getAttackTick() > -5 && this.getActionSurge() <= 0) {
			return false;
		}
		if (!this.level().isClientSide()) {
			//动作如潮
			float actionRandom = this.getRandom().nextFloat();
			if (this.getActionSurge() <= 0) {
				if (actionRandom <= 0.25f) {
					this.setActionSurge(1);
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_ACTION, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else {
					this.setActionSurge(0);
				}
			}
			else if (this.getActionSurge() == 1) {
				if (actionRandom <= 0.75f) {
					this.setActionSurge(2);
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_ACTION, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else {
					this.setActionSurge(0);
				}
			}
			else if (this.getActionSurge() == 2) {
				if (actionRandom <= 0.25f) {
					this.setActionSurge(3);
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_ACTION, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else {
					this.setActionSurge(0);
				}
			}
			else if (this.getActionSurge() < 10) {
				if (actionRandom <= 0.1f) {
					this.setActionSurge(this.getActionSurge() + 1);
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_ACTION, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else {
					this.setActionSurge(0);
				}
			}
			else {
				this.setActionSurge(0);
			}
			//攻击判定
			int attackRandom = this.getRandom().nextInt(70);
			if (attackRandom > 60) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(1);
				this.setAnimationState("chop1");
			}
			else if (attackRandom > 50) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(1);
				this.setAnimationState("chop2");
			}
			else if (attackRandom > 45) {
				this.setAttackTick(30);
				this.setAnimTick(30);
				this.setAttackUse(2);
				this.setAnimationState("slash1");
			}
			else if (attackRandom > 35) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(3);
				this.setAnimationState("slash2");
			}
			else if (attackRandom > 25) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(3);
				this.setAnimationState("slash3");
			}
			else if (attackRandom > 10) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(4);
				this.setAnimationState("stomp1");
			}
			else if (this.getOverloadCooldown() <= 0) {
				this.setOverloadCooldown(this.isPerfect() ? 240 : 480);
				this.setAttackTick(50);
				this.setAnimTick(50);
				this.setAttackUse(5);
				this.setAnimationState("overload");
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.OVERLOAD_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
			}
			else {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAttackUse(3);
				this.setAnimationState("slash2");
			}
		}
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
			//后撤
			if (this.distanceTo(this.getTarget()) <= 2) {
				float f = this.getYHeadRot();
				float f2 = this.getXRot();
				float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = -0.5f;
				float f8 = 0;
				this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
			}
		}
		return true;
	}
	//选择普攻造成伤害类型
	public void trueHurtFind() {
		if (this.getAttackUse() == 1) {
			this.trueHurt(1, 1.15f, true, 1.25f, 1.0f, false);
		}
		else if (this.getAttackUse() == 2) {
			this.trueHurt(2, 1.25f, true, 1.5f, 1.5f, false);
		}
		else if (this.getAttackUse() == 3) {
			this.trueHurt(3, 1.25f, true, 1.0f, 1.0f, false);
		}
		else if (this.getAttackUse() == 4) {
			this.trueHurt(4, 0.75f, false, 0.75f, 2.0f, true);
		}
		else if (this.getAttackUse() == 5) {
			this.trueHurt(5, 1.5f, false, 2.5f, 2.0f, true);
		}
	}
	public boolean trueHurt(int type, float reach, boolean isOnlyFace, float damageMulti, float knockbackMulti, boolean fallBlock) {
		//1-劈砍
		//this.trueHurt(1, 1.15f, true, 1.25f, 1.0f, false)
		//2-挥砍-特殊
		//this.trueHurt(2, 1.25f, true, 1.25f, 1.5f, false)
		//3-挥砍
		//this.trueHurt(3, 1.25f, true, 1.1f, 1.0f, false)
		//4-震跺
		//this.trueHurt(4, 0.75f, false, 0.75f, 2.0f, true)
		//5-过载强击
		//this.trueHurt(5, 1.5f, false, 2.5f, 2.0f, true)
		if (this.isPerfect()) {
			damageMulti *= 1.2f;
		}
		if (!this.isSilent()) {
			if (type == 1) {
				this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_CHOP, 5.0f, 1.0f);
			}
			else if (type == 2) {
				this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_SLASH, 5.0f, 1.0f);
			}
			else if (type == 3) {
				this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_SLASH, 5.0f, 1.0f);
			}
			else if (type == 4) {
				this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_STOMP, 5.0f, 1.0f);
			}
			else if (type == 5) {
				this.playSound(JerotesVillageSoundEvents.MEROR_CHAMPION_OVERLOAD, 5.0f, 1.0f);
			}
		}
		float reachY = reach;
		if (fallBlock) {
			reachY /= 2;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach, reachY, reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceTo(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && isOnlyFace) continue;
			if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
				hurt.setDeltaMovement(hurt.getDeltaMovement().add((knockbackMulti / 8) * (0.7 + knockbackMulti / 3), (knockbackMulti / 8) * (0.7 + knockbackMulti / 3), (knockbackMulti / 8) * (0.7 + knockbackMulti / 3)));
			}
			AttackFind.attackBegin(this, hurt);
			float damages = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMulti;
			damages += Math.min(hurt.getMaxHealth()/18f, this.getHealth()/5f);
			if (fallBlock) {
				if (!hurt.level().isClientSide()) {
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
			boolean bl = AttackFind.attackAfter(this, hurt, 0, knockbackMulti, true, damages);
			if (bl) {
				if (fallBlock) {
					//击飞
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								(knockbackMulti / 3) * (0.7 + knockbackMulti / 3) * 0.25f,
								(knockbackMulti / 3) * (0.7 + knockbackMulti / 3) * 0.25f,
								(knockbackMulti / 3) * (0.7 + knockbackMulti / 3) * 0.25f));
					}
				}
			}
			//破盾
			if (hurt.getUseItem().getItem() instanceof ShieldItem) {
				if (hurt instanceof Player player) {
					player.disableShield(true);
				} else if (hurt instanceof UseShieldEntity useShieldEntity) {
					useShieldEntity.disableShield();
				}
			}
			if (!fallBlock) {
				//横扫粒子
				double d3 = -Mth.sin(this.getYRot() * 0.017453292f);
				double d4 = Mth.cos(this.getYRot() * 0.017453292f);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 15; ++i) {
						double xs = this.getX() + d3 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - hurt.getX()) / 2;
						double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - hurt.getY()) / 2;
						double zs = this.getZ() + d4 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - hurt.getZ()) / 2;
						serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, xs, ys, zs, 0, d3, 0.0, d4, 0.0);
					}
				}
			}
		}
		//震动
		if (fallBlock) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//粒子
		if (type == 5) {
			if (this.level() instanceof ServerLevel serverLevel) {
				float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
				float renderYawOffset = this.yBodyRot;
				Vec3 vec3 = new Vec3(this.xo + 2 * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo + 0.1f, this.zo + 2 * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
				{
					ParticleOptions particleOptions = JerotesVillageParticleTypes.MEROR_MONSTROSITY_ATTACK_IN.get();
					serverLevel.sendParticles(particleOptions,
							vec3.x,
							vec3.y,
							vec3.z,
							0, 0, 0, 0, 0);
				}
				{
					ParticleOptions particleOptions = JerotesVillageParticleTypes.MEROR_BULLET.get();
					double d = -Mth.sin(this.getYRot() * 0.017453292f);
					double d2 = Mth.cos(this.getYRot() * 0.017453292f);
					for (int i = 0; i < 160; i++) {
						double angle = (Math.PI * 2 * i) / 160;
						double radius = 4;
						double offsetX = Math.cos(angle) * radius;
						double offsetZ = Math.sin(angle) * radius;
						// 生成烟雾粒子
						serverLevel.sendParticles(particleOptions,
								vec3.x() + offsetX + d, vec3.y() + 0.2f, vec3.z() + offsetZ + d2,
								0, offsetX * 0.02, -0.04, offsetZ * 0.02, 1.0);
					}
					for (int i = 0; i < 100; i++) {
						double angle = (Math.PI * 2 * i) / 100;
						double radius = 3;
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
		//横扫效果
		Main.sweepAttack(this);
		//下落方块
		if (fallBlock) {
			AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + reach, 0, Main.mobWidth(this)/2 + reach);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					Main.spawnFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
		return true;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			this.addEffect(new MobEffectInstance(
					MobEffects.DAMAGE_BOOST,
					mobEffectInstance.getDuration(),
					mobEffectInstance.getAmplifier()
					,mobEffectInstance.isAmbient()
					,mobEffectInstance.isVisible()
					,mobEffectInstance.showIcon()));
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			this.addEffect(new MobEffectInstance(
					JerotesVillageMobEffects.ABUNDANT_COURAGE.get(),
					mobEffectInstance.getDuration(),
					mobEffectInstance.getAmplifier()
					,mobEffectInstance.isAmbient()
					,mobEffectInstance.isVisible()
					,mobEffectInstance.showIcon()));
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 101) {
			this.rushStartAnimationState.start(this.tickCount);
		}else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.MEROR_METAL_AXE.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.MEROR_RETE.get());
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount <= 4) {
			return false;
		}
		if (this.isPerfect())
			amount /= 2;
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
