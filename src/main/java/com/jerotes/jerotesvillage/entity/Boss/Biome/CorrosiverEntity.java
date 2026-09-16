package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.DracognathusEntity;
import com.jerotes.jerotesvillage.entity.Monster.BitterColdWindEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.goal.SedimentLordMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CorrosiverEntity extends DracognathusEntity implements BeastToughEntity, BossEntity, LoreUndyingEntity, ColdAbsorptionEntity, SpellUseEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState idleWindAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState attack6AnimationState = new AnimationState();
	public AnimationState attack7AnimationState = new AnimationState();
	public AnimationState breathAnimationState = new AnimationState();
	public AnimationState windAnimationState = new AnimationState();
	public AnimationState snowAnimationState = new AnimationState();
	public AnimationState iceAnimationState = new AnimationState();
	public AnimationState teleportAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	public AnimationState startAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.WHITE, false);
	private static final EntityDataAccessor<Integer> BEAST_TOUGH_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> BEAST_TOUGH_DAMAGE = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_BEAST_TOUGH = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_COOLDOWN_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_BREAK_BEAST_TOUGH = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREATH_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREATH_USE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SNOW_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SNOW_USE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WINDCALLER_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WINDCALLER_USE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ICE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ICE_USE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TELEPORT_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TELEPORT_USE_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> WIND = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WIND_ANIM = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANGRY_AMIM = SynchedEntityData.defineId(CorrosiverEntity.class, EntityDataSerializers.INT);

	public CorrosiverEntity(EntityType<? extends CorrosiverEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.1f);
		this.xpReward = 400;
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.CorrosiverMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.CorrosiverMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.CorrosiverArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.CorrosiverMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.CorrosiverAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.CorrosiverKnockbackResistance);
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
			return(float) OtherMainConfig.CorrosiverMagicAttackPercentage;
		}
		return (float) OtherMainConfig.CorrosiverAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.CorrosiverDamageCap;
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
		return (float)OtherMainConfig.CorrosiverDamageCooldownTick * base;
	}

	@Override
	public int getExperienceReward() {
		return 400;
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
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SedimentLordMeleeAttackGoal(this, 1.3, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Animal.class, 6.0f));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Wolf>(this, Wolf.class, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.CORROSIVER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.CORROSIVER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.CORROSIVER_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.CORROSIVER_WALK, 0.15f, 1.0f);
	}
	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 3.185f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	protected float nextStep() {
		if (this.isVehicle())
			return (float)((int)this.moveDist + 2);
		return super.nextStep();
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.CORROSIVER_MUSIC;
	}
	@Override
	public boolean isOnFire() {
		if (this.isBeastTough()) {
			return false;
		}
		return super.isOnFire();
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")));
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
		float add = 1;
		if (this.isWind())
			add = 1.5f;
		return aabb1.inflate(add, add, add);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public CorrosiverEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		CorrosiverEntity dracognathusEntity = JerotesVillageEntityType.CORROSIVER.get().create(serverLevel);
		if (dracognathusEntity != null && (uUID = this.getOwnerUUID()) != null) {
			dracognathusEntity.setOwnerUUID(uUID);
			dracognathusEntity.setTame(true);
		}
		return dracognathusEntity;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7 && this.random.nextInt(5) == 0;
	}
	@Override
	public double ColdAbsorptionPercentage() {
		return OtherMainConfig.CorrosiverColdAbsorptionPercentage;
	}


	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float windAnimProgress = 0.0f;
	public float angryAnimProgress = 0.0f;
	public int spellLevel = OtherMainConfig.CorrosiverSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	private int healSelf = 30;
	private double blockDestroyTick;
	public int start = -101;
	private float health1 = 1;
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public void setBreathTick(int n){
		this.getEntityData().set(BREATH_TICK, n);
	}
	public int getBreathTick(){
		return this.getEntityData().get(BREATH_TICK);
	}
	public void setBreathUseTick(int n){
		this.getEntityData().set(BREATH_USE_TICK, n);
	}
	public int getBreathUseTick(){
		return this.getEntityData().get(BREATH_USE_TICK);
	}
	public void setSnowTick(int n){
		this.getEntityData().set(SNOW_TICK, n);
	}
	public int getSnowTick(){
		return this.getEntityData().get(SNOW_TICK);
	}
	public void setSnowUseTick(int n){
		this.getEntityData().set(SNOW_USE_TICK, n);
	}
	public int getSnowUseTick(){
		return this.getEntityData().get(SNOW_USE_TICK);
	}
	public void setWindcallerTick(int n){
		this.getEntityData().set(WINDCALLER_TICK, n);
	}
	public int getWindcallerTick(){
		return this.getEntityData().get(WINDCALLER_TICK);
	}
	public void setWindcallerUseTick(int n){
		this.getEntityData().set(WINDCALLER_USE_TICK, n);
	}
	public int getWindcallerUseTick(){
		return this.getEntityData().get(WINDCALLER_USE_TICK);
	}
	public void setTeleportTick(int n){
		this.getEntityData().set(TELEPORT_TICK, n);
	}
	public int getTeleportTick(){
		return this.getEntityData().get(TELEPORT_TICK);
	}
	public void setTeleportUseTick(int n){
		this.getEntityData().set(TELEPORT_USE_TICK, n);
	}
	public int getTeleportUseTick(){
		return this.getEntityData().get(TELEPORT_USE_TICK);
	}
	public void setIceTick(int n){
		this.getEntityData().set(ICE_TICK, n);
	}
	public int getIceTick(){
		return this.getEntityData().get(ICE_TICK);
	}
	public void setIceUseTick(int n){
		this.getEntityData().set(ICE_USE_TICK, n);
	}
	public int getIceUseTick(){
		return this.getEntityData().get(ICE_USE_TICK);
	}
	public boolean isWind() {
		return this.getEntityData().get(WIND) && this.isAlive();
	}
	public void setWind(boolean bl) {
		this.getEntityData().set(WIND, bl);
	}
	public boolean specialAction() {
		return this.getWindcallerUseTick() > 0 ||
				this.getIceUseTick() > 3 ||
				this.getSnowUseTick() > 0 ||
				this.getTeleportUseTick() > 0 ||
				this.getStartTick() > 0 ||
				this.getBreathTick() < 20;
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
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getWindAnim() {
		return this.getEntityData().get(WIND_ANIM);
	}
	public void setWindAnim(int n) {
		this.getEntityData().set(WIND_ANIM, n);
	}
	public int getAngryAnim() {
		return this.getEntityData().get(ANGRY_AMIM);
	}
	public void setAngryAnim(int n) {
		this.getEntityData().set(ANGRY_AMIM, n);
	}
	public int getStartTick() {
		return this.getEntityData().get(START_TICK);
	}
	public void setStartTick(int n) {
		this.getEntityData().set(START_TICK, n);
	}
	public void setBeastToughDamage(float f){
		this.getEntityData().set(BEAST_TOUGH_DAMAGE, f);
	}
	public float getBeastToughDamage(){
		return this.getEntityData().get(BEAST_TOUGH_DAMAGE);
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
		else if (Objects.equals(animation, "breath")){
			return 8;
		}
		else if (Objects.equals(animation, "wind")){
			return 9;
		}
		else if (Objects.equals(animation, "snow")){
			return 10;
		}
		else if (Objects.equals(animation, "ice")){
			return 11;
		}
		else if (Objects.equals(animation, "teleport")){
			return 12;
		}
		else if (Objects.equals(animation, "dead")){
			return 13;
		}
		else if (Objects.equals(animation, "start")){
			return 14;
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
		list.add(this.breathAnimationState);
		list.add(this.windAnimationState);
		list.add(this.snowAnimationState);
		list.add(this.iceAnimationState);
		list.add(this.teleportAnimationState);
		list.add(this.deadAnimationState);
		list.add(this.startAnimationState);
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
		return (float) OtherMainConfig.CorrosiverBreakToughHurtCooldownMultiple;
	}
	public int getBaseBeastToughTick() {
		return (int) (OtherMainConfig.CorrosiverToughHurtCooldown * 20);
	}
	public float getBreakBeastToughNeedBaseDamage() {
		return (int) (OtherMainConfig.CorrosiverBreakToughNeedBaseDamage);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);

		compoundTag.putInt("BeastToughTick", this.getBeastToughTick());
		compoundTag.putFloat("BeastToughDamage", this.getBeastToughDamage());
		compoundTag.putBoolean("IsBeastTough", this.isBeastTough());
		compoundTag.putInt("BreakBeastToughTick", this.getBreakBeastToughTick());
		compoundTag.putInt("BreakBeastToughCooldownTick", this.getBreakBeastToughCooldownTick());
		compoundTag.putBoolean("IsBreakBeastTough", this.isBreakBeastTough());

		compoundTag.putInt("StartTick", this.getStartTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("WindAnim", this.getWindAnim());
		compoundTag.putInt("AngryAnim", this.getAngryAnim());
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("WindcallerTick", this.getWindcallerTick());
		compoundTag.putInt("WindcallerUseTick", this.getWindcallerUseTick());
		compoundTag.putInt("IceTick", this.getIceTick());
		compoundTag.putInt("IceUseTick", this.getIceUseTick());
		compoundTag.putInt("TeleportTick", this.getTeleportTick());
		compoundTag.putInt("TeleportUseTick", this.getTeleportUseTick());
		compoundTag.putInt("SnowTick", this.getSnowTick());
		compoundTag.putInt("SnowUseTick", this.getSnowUseTick());
		compoundTag.putInt("BreathTick", this.getBreathTick());
		compoundTag.putInt("BreathUseTick", this.getBreathUseTick());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("Start", this.start);
		compoundTag.putFloat("Health1", this.health1);
		compoundTag.putInt("HealSelf", this.healSelf);
		compoundTag.putBoolean("IsWind", this.isWind());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");

		this.setBeastToughTick(compoundTag.getInt("BeastToughTick"));
		this.setBeastToughDamage(compoundTag.getFloat("BeastToughDamage"));
		this.setBeastTough(compoundTag.getBoolean("IsBeastTough"));
		this.setBreakBeastToughTick(compoundTag.getInt("BreakBeastToughTick"));
		this.setBreakBeastToughCooldownTick(compoundTag.getInt("BreakBeastToughCooldownTick"));
		this.setBreakBeastTough(compoundTag.getBoolean("IsBreakBeastTough"));

		this.setStartTick(compoundTag.getInt("StartTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setWindAnim(compoundTag.getInt("WindAnim"));
		this.setAngryAnim(compoundTag.getInt("AngryAnim"));
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setWindcallerTick(compoundTag.getInt("WindcallerTick"));
		this.setWindcallerUseTick(compoundTag.getInt("WindcallerUseTick"));
		this.setTeleportTick(compoundTag.getInt("TeleportTick"));
		this.setTeleportUseTick(compoundTag.getInt("TeleportUseTick"));
		this.setIceTick(compoundTag.getInt("IceTick"));
		this.setIceUseTick(compoundTag.getInt("IceUseTick"));
		this.setSnowTick(compoundTag.getInt("SnowTick"));
		this.setSnowUseTick(compoundTag.getInt("SnowUseTick"));
		this.setBreathTick(compoundTag.getInt("BreathTick"));
		this.setBreathUseTick(compoundTag.getInt("BreathUseTick"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.start = compoundTag.getInt("Start");
		this.health1 = compoundTag.getFloat("Health1");
		this.healSelf = compoundTag.getInt("HealSelf");
		if (compoundTag.contains("IsWind")) {
			this.setWind(compoundTag.getBoolean("IsWind"));
		}
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(BREATH_TICK, 0);
		this.getEntityData().define(BREATH_USE_TICK, 0);
		this.getEntityData().define(SNOW_TICK, 0);
		this.getEntityData().define(SNOW_USE_TICK, 0);
		this.getEntityData().define(WINDCALLER_TICK, 0);
		this.getEntityData().define(WINDCALLER_USE_TICK, 0);
		this.getEntityData().define(ICE_TICK, 0);
		this.getEntityData().define(ICE_USE_TICK, 0);
		this.getEntityData().define(TELEPORT_TICK, 0);
		this.getEntityData().define(TELEPORT_USE_TICK, 0);
		this.getEntityData().define(WIND, false);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(WIND_ANIM, 0);
		this.getEntityData().define(ANGRY_AMIM, 0);
		this.getEntityData().define(START_TICK, 0);

		this.getEntityData().define(BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BEAST_TOUGH_DAMAGE, 0f);
		this.getEntityData().define(IS_BEAST_TOUGH, false);
		this.getEntityData().define(BREAK_BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BREAK_BEAST_TOUGH_COOLDOWN_TICK, 0);
		this.getEntityData().define(IS_BREAK_BEAST_TOUGH, false);
		
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
						this.breathAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.breathAnimationState);
						break;
					case 9:
						this.windAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.windAnimationState);
						break;
					case 10:
						this.snowAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.snowAnimationState);
						break;
					case 11:
						this.iceAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.iceAnimationState);
						break;
					case 12:
						this.teleportAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.teleportAnimationState);
						break;
					case 13:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
					case 14:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")))) {
			if (!this.isTame()) {
				this.tame(player);
				this.setOrderedToSit(true);
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				this.level().broadcastEntityEvent(this, (byte) 7);
				return InteractionResult.SUCCESS;
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}
			this.setPersistenceRequired();
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
		if (!level().isClientSide() && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
		//技能-苦寒强韧
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
		if (this.specialAction() || this.getAttackTick() > 0){
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 10.0f, 10.0f);
				this.lookAt(this.getTarget(), 10.0f, 10.0f);
			}
			this.getNavigation().stop();
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
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (this.getRandom().nextInt(60) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}
		//
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
			this.navigation = new AmphibiousPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			this.moveControl = new MoveControl(this);
			this.navigation = new GroundPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//摧毁骑乘物
		Main.destroyRides(this);
		//
		//破坏方块
		{
			if (this.blockDestroyTick > 0) {
				this.blockDestroyTick -= 1;
			}
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = OtherMainConfig.CorrosiverBreakBlockCooldown * 20;
				}
				if (!this.level().isClientSide()) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
		//站立动画
		this.idleWindAnimationState.startIfStopped(this.tickCount);
		//出现召唤
		if (this.start > 0) {
			this.start -= 1;
		}
		else if (this.start == 0) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.CORROSIVER_AMBIENT, 10.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
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
		if (!this.level().isClientSide()) {
			this.setStartTick(Math.max(0, this.getStartTick() - 1));
		}
		//近战攻击
		if (this.getAttackTick() == 5 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 8) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360f, 360f);
			}
		}
		if (this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		//技能
		{
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			List<CorrosiverEntity> listCorrosiver = this.level().getEntitiesOfClass(CorrosiverEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listCorrosiver.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<BitterColdWindEntity> listWind = this.level().getEntitiesOfClass(BitterColdWindEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listWind.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			//蚀龙兽吐息
			{
				//判定
				if (this.getBreathTick() >= (int) (20 * OtherMainConfig.CorrosiverSpellCooldown) && this.spellNeed(0, 16, 1)) {
					if (!this.level().isClientSide()) {
						this.setSpellTick(25);
						this.setBreathUseTick(20);
					}
				}
				if (this.getBreathUseTick() == 20 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setBreathTick(0);
						this.setAnimTick(20);
						this.setAnimationState("breath");
					}
				}
				if (this.getBreathUseTick() == 10) {
					LivingEntity livingEntity = this;
					if (this.getTarget() != null) {
						livingEntity = this.getTarget();
					}
					//法术列表-蚀龙兽吐息
					OtherSpellList.CorrosiverBreath(spellLevel, this, livingEntity).spellUse(this.getTarget() instanceof Player ? 1 : Main.randomReach(this.getRandom(), 0, 1));
				}
			}
			//蚀龙兽吐息
			{
				//判定
				if (this.getIceTick() >= (int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 1.25f) && this.spellNeed(0, 14, 1)) {
					if (!this.level().isClientSide()) {
						this.setSpellTick(35);
						this.setIceUseTick(30);
					}
				}
				if (this.getIceUseTick() == 30 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setIceTick(0);
						this.setAnimTick(30);
						this.setAnimationState("ice");
					}
				}
				if (this.getIceUseTick() == 10) {
					LivingEntity livingEntity = this;
					if (this.getTarget() != null) {
						livingEntity = this.getTarget();
					}
					//法术列表-腐蚀冰锥
					OtherSpellList.CorrosiveIceSpike(spellLevel, this, livingEntity).spellUse();
				}
			}
			//苦寒唤风者
			{
				//判定
				if (this.getWindcallerTick() >= (int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 3) && this.spellNeed(0, 18, 4) && listWind.size() < listCorrosiver.size() * 16) {
					if (!this.level().isClientSide()) {
						this.setSpellTick(25);
						this.setWindcallerUseTick(20);
					}
				}
				if (this.getWindcallerUseTick() == 20 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setWindcallerTick(0);
						this.setAnimTick(20);
						this.setAnimationState("wind");
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CORROSIVER_WINDCALLER, this.getSoundSource(), 10.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
					}
				}
				//召唤
				if (this.getWindcallerUseTick() == 6 && this.isAlive()) {
					OtherSpellFind.BitterColdWindcaller(this, this.getTarget(), this.getSpellLevel(), this.getSpellLevel() + 2, 8, 32);
				}
			}
			//腐蚀雪云
			{
				//判定
				if (this.getSnowTick() >= (int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 2) && this.spellNeed(0, 12, 4)) {
					if (!this.level().isClientSide()) {
						this.setSpellTick(25);
						this.setSnowUseTick(20);
					}
				}
				if (this.getSnowUseTick() == 20 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setSnowTick(0);
						this.setAnimTick(20);
						this.setAnimationState("snow");
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CORROSIVER_SNOW, this.getSoundSource(), 10.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
					}
				}
				//召唤
				if (this.getSnowUseTick() == 10 && this.getTarget() != null && this.isAlive() && this.distanceTo(this.getTarget()) <= 32) {
					//法术列表-腐蚀雪云
					OtherSpellList.CorrosiveSnowCloud(this.getSpellLevel(), this, this.getTarget()).spellUse();
				}
			}
			//旋风裹身
			{
				//判定
				if (this.getTeleportTick() >= (int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 1.25f) && this.spellNeed(12, 64, 2)) {
					if (!this.level().isClientSide()) {
						this.setSpellTick(85);
						this.setTeleportUseTick(80);
					}
				}
				if (this.getTeleportUseTick() == 70 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setTeleportTick(0);
						this.setAnimTick(20);
						this.setAnimationState("teleport");
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CORROSIVER_TELEPORT, this.getSoundSource(), 10.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
					}
				}
				//传送
				if (this.getTeleportUseTick() == 60 && this.getTarget() != null && this.isAlive() && this.distanceTo(this.getTarget()) <= 128) {
					//旋风裹身
					OtherSpellFind.WhirlwindShroud(this, this, this.getTarget(), this.getTarget(), 8);
				}
			}
			if (!this.level().isClientSide()) {
				this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
				this.setWindcallerTick(Math.min((int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 3), this.getWindcallerTick() + 1));
				this.setWindcallerUseTick(Math.max(0, this.getWindcallerUseTick() - 1));
				this.setIceTick(Math.min((int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 1.25f), this.getIceTick() + 1));
				this.setIceUseTick(Math.max(0, this.getIceUseTick() - 1));
				this.setBreathTick(Math.min((int) (20 * OtherMainConfig.CorrosiverSpellCooldown), this.getBreathTick() + 1));
				this.setBreathUseTick(Math.max(0, this.getBreathUseTick() - 1));
				this.setSnowTick(Math.min((int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 2), this.getSnowTick() + 1));
				this.setSnowUseTick(Math.max(0, this.getSnowUseTick() - 1));
				this.setTeleportTick(Math.min((int) (20 * OtherMainConfig.CorrosiverSpellCooldown * 1.25f), this.getTeleportTick() + 1));
				this.setTeleportUseTick(Math.max(0, this.getTeleportUseTick() - 1));
			}
		}
		//粒子
		if (this.level().isClientSide()) {
			for (int i = 0; i < 3; ++i) {
				this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}
		//技能-苦寒旋风
		if (this.getHealth() > getMaxHealth() / 3 && this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(JerotesVillageBiomes.BITTER_COLD)) {
			if (this.isWind()) {
				if (!this.level().isClientSide()) {
					this.setWind(false);
				}
			}
		}
		else {
			if (!this.isWind() && this.isAlive()) {
				if (!this.level().isClientSide()) {
					this.setWind(true);
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.BITTER_COLD_CYCLONE_WIND_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CORROSIVER_WIND, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
			}
		}

		if (this.isWind() || this.getTeleportUseTick() > 0 || this.getStartTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setWindAnim(Math.min(10, this.getWindAnim() + 1));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setWindAnim(Math.max(0, this.getWindAnim() - 1));
			}
		}
		if (this.isAggressive()) {
			if (!this.level().isClientSide()) {
				this.setAngryAnim(Math.min(10, this.getAngryAnim() + 1));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAngryAnim(Math.max(0, this.getAngryAnim() - 1));
			}
		}


		if (this.isWind()) {
			Vec3 vec3 = this.getDeltaMovement();
			if (!this.onGround() && vec3.y < 0.0) {
				this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
			}
			if (healSelf > 0){
				healSelf -= 1;
			}
			else{
				healSelf = 30;
				this.heal((float) (getMaxHealth() / (OtherMainConfig.CorrosiverMaxHealth * 2)));
			}
			if (!this.level().isClientSide()) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
			}
			if (this.getTarget() != null && this.tickCount % 5 == 0 && this.getAttackTick() <= 0){
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().deflate(0.5f));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if ((this.distanceToSqr(hurt)) > 128) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (this.isAggressive() || (hurt instanceof Mob mob && mob.getTarget() == this) || hurt.getLastHurtMob() == this || hurt.getLastHurtByMob() == this) {
						if (!hurt.level().isClientSide()) {
							hurt.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 160, 1), this);
						}
					}
					double d;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					} else {
						d = 0.0;
					}
					double d2 = Math.max(0.3, 1.3 - d);
					if ((hurt.hasEffect(JerotesMobEffects.CORROSIVE.get()) || hurt instanceof Mob mob) && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * d2 / 3, -(this.getY() - hurt.getY()) * 1.5 * d2, -(this.getZ() - hurt.getZ()) * d2 / 3));
					}
				}
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return this.getSpellTick() <= 0 && !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= (0 - (int)(OtherMainConfig.CorrosiverMeleeAttackCooldown * 20)/2f) && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}


	@Override
	public void pressMainJerotes(Player player) {
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(25);
			this.setAttackTick(15);
			this.setAnimTick(15);
			if (attackRandom > 20) {
				this.setAttackUse(1);
				this.setAnimationState("attack1");
			} else if (attackRandom > 15) {
				this.setAttackUse(2);
				this.setAnimationState("attack2");
			} else if (attackRandom > 10) {
				this.setAttackUse(3);
				this.setAnimationState("attack3");
			} else if (attackRandom > 5) {
				this.setAttackUse(3);
				this.setAnimationState("attack4");
			} else {
				this.setAttackUse(4);
				this.setAnimationState("attack5");
			}
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(10);
			this.setAttackTick(15);
			this.setAnimTick(15);
			if (attackRandom > 5) {
				this.setAttackUse(4);
				this.setAnimationState("attack6");
			} else {
				this.setAttackUse(5);
				this.setAnimationState("attack7");
			}
		}
	}
	@Override
	public boolean canPressMainJerotes() {
		return this.getAttackTick() <= -(int)(OtherMainConfig.CorrosiverMeleeAttackCooldown * 20) && !this.specialAction();
	}
	@Override
	public boolean canPressAddJerotes() {
		return this.getAttackTick() <= -(int)(OtherMainConfig.CorrosiverMeleeAttackCooldown * 20) && !this.specialAction();
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction()) {
			return false;
		}
		if (this.getAttackTick() > -(int)(OtherMainConfig.CorrosiverMeleeAttackCooldown * 20)) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(35);
			this.setAttackTick(15);
			this.setAnimTick(20);
			if (attackRandom > 30) {
				this.setAttackUse(1);
				this.setAnimationState("attack1");
			} else if (attackRandom > 25) {
				this.setAttackUse(2);
				this.setAnimationState("attack2");
			} else if (attackRandom > 20) {
				this.setAttackUse(3);
				this.setAnimationState("attack3");
			} else if (attackRandom > 15) {
				this.setAttackUse(3);
				this.setAnimationState("attack4");
			} else if (attackRandom > 10) {
				this.setAttackUse(4);
				this.setAnimationState("attack5");
			} else if (attackRandom > 5) {
				this.setAttackUse(4);
				this.setAnimationState("attack6");
			} else {
				this.setAttackTick(13);
				this.setAttackUse(5);
				this.setAnimationState("attack7");
			}
		}
		return true;
	}
	public boolean trueHurt() {
		//1-猛砸
		//2-推离
		//3-挥拳
		//4.下砸
		//5-掀飞
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CORROSIVER_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}

		int levels = 0;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			levels = 1;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			levels = 2;
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		//1-猛砸
		if (this.getAttackUse() == 1) {
			damageMulti = 1.5f;
			knockbackMulti = 0.75f;
		}
		//2-推离
		if (this.getAttackUse() == 2) {
			damageMulti = 1.0f;
			knockbackMulti = 1.25f;
		}
		//3-挥拳
		if (this.getAttackUse() == 3) {
			damageMulti = 1.15f;
			knockbackMulti = 1.15f;
		}
		//4.下砸
		if (this.getAttackUse() == 4) {
			damageMulti = 1.25f;
			knockbackMulti = 1.05f;
		}
		//5-掀飞
		if (this.getAttackUse() == 5) {
			damageMulti = 1f;
			knockbackMulti = 2.0f;
		}
		if (this.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
			damageMulti += Math.max(0, Math.min(3, (float) ((float) (Math.min(6, 2 * this.fallDistance) + Math.min(8, this.fallDistance) + (1 + 0.5 * 0) * this.fallDistance) / this.getAttributeValue(Attributes.ATTACK_DAMAGE))));
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.75, 0.75, 0.75));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 4) continue;
			if (hurt instanceof BitterColdWindEntity bitterColdWindEntity && bitterColdWindEntity.getTarget() != this && this.getTarget() != bitterColdWindEntity)
				continue;
			if (hurt instanceof CorrosiverEntity corrosiverEntity && corrosiverEntity.getTarget() != this && this.getTarget() != corrosiverEntity)
				continue;
			if (hurt instanceof DracognathusEntity dracognathusEntity && dracognathusEntity.getTarget() != this && this.getTarget() != dracognathusEntity)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0);
			if (bl2) {
				double d = 0.0;
				if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
				}
				if (hurt.level() instanceof ServerLevel serverLevel) {
					Vec3 vec3 = hurt.position().subtract(this.position());
					BlockPos blockPos = hurt.getOnPos();
					Vec3 vec33 = blockPos.getCenter().add(0.0, 0.5, 0.0);
					int n = (int)(100.0 * d);
					serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos)), vec33.x, vec33.y, vec33.z, n, 0.3, 0.3, 0.3, 0.15);
					serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec33.x, this.getRandomY(), vec33.z, 16, 0.3, 0.3, 0.3, 0.15);
				}
				//击飞
				double d3 = Math.max(0, 1 - d) + knockbackMulti / 2;
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					if (!this.isWind()) {
						hurt.setOnGround(false);
						if (this.getAttackUse() == 5) {
							hurt.setDeltaMovement(hurt.getDeltaMovement().add(0, 2, 0));
						}
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-(this.getX() - hurt.getX()) * 1.4 * d3 * (0.7 + knockbackMulti / 3),
								-(this.getY() - hurt.getY()) * 2 * d3 * (0.7 + knockbackMulti / 3),
								-(this.getZ() - hurt.getZ()) * 1.4 * d3 * (0.7 + knockbackMulti / 3)));
					} else {
						hurt.setOnGround(false);
						if (this.getAttackUse() == 5) {
							hurt.setDeltaMovement(hurt.getDeltaMovement().add(0, 2, 0));
						}
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-(this.getX() - hurt.getX()) * 1.4 * (d3 * (0.7 + knockbackMulti / 3) + 0.3),
								-(this.getY() - hurt.getY()) * 2 * (d3 * (0.7 + knockbackMulti / 3) + 0.35),
								-(this.getZ() - hurt.getZ()) * 1.4 * (d3 * (0.7 + knockbackMulti / 3) + 0.3)));
					}
				}
				//效果
				if (hurt.getTicksFrozen() < (this.isWind() ? 420 : 280)) {
					hurt.setTicksFrozen(this.isWind() ? 420 : 280);
				}
				if (!hurt.level().isClientSide()) {
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), this.isWind() ? 400 : 200, levels), this);
					if (this.isWind()) {
						hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0), this);
						hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0), this);
					}
				}
			}
		}
		//粒子
		if (this.getAttackUse() == 1 || this.getAttackUse() == 4) {
			if (this.level() instanceof ServerLevel serverLevel) {
				Vec3 vec3 = this.getOnPos().getCenter().add(0.0, 0.5, 0.0);
				int n = (int) Math.min(300, 50.0f * this.getHealth());
				BlockState blockState = this.level().getBlockState(this.getOnPos());
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
				SoundEvent soundEvent = this.getAttackUse() == 1 ? JerotesVillageSoundEvents.MACE_SMASH_GROUND_HEAVY : JerotesVillageSoundEvents.MACE_SMASH_GROUND;
				if (!this.isSilent()) {
					serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(), soundEvent, SoundSource.NEUTRAL, 10.0f, 1.0f);
				}
				BlockPos blockPos = this.getOnPos();
				Vec3 vec33 = blockPos.getCenter().add(0.0, 0.5, 0.0);
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos)), vec33.x, vec33.y, vec33.z, 32, 0.3, 0.3, 0.3, 0.15);
				serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec33.x, this.getRandomY(), vec33.z, 32, 0.3, 0.3, 0.3, 0.15);

			}
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < 24; i++) {
					double angle = (Math.PI * 2 * i) / 24;
					double radius = Main.mobWidth(this) * 1.25f;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX() + offsetX, this.getY(0) + j * 0.25, this.getZ() + offsetZ, 0, 0.12, 0);
					double angle2 = (Math.PI * 2 * i) / 24;
					double radius2 = Main.mobWidth(this) * 2.75f;
					double offsetX2 = Math.cos(angle2) * radius2;
					double offsetZ2 = Math.sin(angle2) * radius2;
					this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX() + offsetX2, this.getY(0) + j * 0.15, this.getZ() + offsetZ2, 0, 0.12, 0);
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
		//横扫效果
		Main.sweepAttack(this);
		if (this.level() instanceof ServerLevel serverLevel && (this.getAttackUse() == 1 || this.getAttackUse() == 4)) {
			AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 2, 0, Main.mobWidth(this)/2 + 2);
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.start > -100){
			return true;
		}
		if (damageSource.is(DamageTypes.THORNS)
				|| AttackFind.isCorrosionDamage(damageSource)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.CACTUS)
				|| AttackFind.isColdDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL) && this.isWind()
				|| damageSource.is(DamageTypes.ON_FIRE) && this.isWind())
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		float damageMulti = 1;
		if (!isInvulnerableTo(damageSource)) {
			damageMulti = 5f/7f;
		}
		if (this.isWind()){
			if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this))
				amount *= 0.7f;
		}
		else if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this))
			amount *= 0.8f;


		if (AttackFind.isWaterDamage(damageSource))
			damageMulti *= 0.25f;
		if (AttackFind.isFireDamage(damageSource))
			damageMulti *= 0.25f;
		if (damageSource.is(DamageTypeTags.IS_EXPLOSION))
			damageMulti *= 0.25f;
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
		return super.hurt(damageSource, amount * damageMulti);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.CORROSIVE.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
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
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(40);
			this.setStartTick(40);
			this.setAnimationState("start");
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public boolean killedEntity(ServerLevel serverLevel, LivingEntity livingEntity) {
		boolean bl = super.killedEntity(serverLevel, livingEntity);
		if (!EntityAndItemFind.isLoreUndying(livingEntity)) {
			if (!EntityAndItemFind.isBoss(livingEntity.getType())) {
				if (this.level().isClientSide()) {
					int n = 24;
					if (Main.mobSizeMedium(livingEntity)) {
						n = 48;
					}
					if (Main.mobSizeLarge(livingEntity)) {
						n = 64;
					}
					if (Main.mobSizeGiant(livingEntity)) {
						n = 128;
					}
					for (int i = 0; i < n; ++i) {
						this.level().addParticle(ParticleTypes.SNOWFLAKE, livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 0.0, 0.0, 0.0);
					}
					for (int i = 0; i < n / 2; ++i) {
						this.level().addParticle(JerotesVillageParticleTypes.CORROSIVER_BREATH_FOG.get(), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 0.0, 0.0, 0.0);
					}
				}
				livingEntity.remove(RemovalReason.KILLED);
			}
		}
		return bl;
	}
}




