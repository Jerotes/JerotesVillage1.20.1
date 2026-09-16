package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownMountainRealmFlagSpearEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TyrantOfTheMountainRealmEntity extends BaseWonderlinEntity implements BossEntity, SpellUseEntity, BreakShieldEntity, LoreUndyingEntity {
	public AnimationState idleBaseAnimationState = new AnimationState();
	public AnimationState idle1AnimationState = new AnimationState();
	public AnimationState idle2AnimationState = new AnimationState();

	public AnimationState pullAnimationState = new AnimationState();
	public AnimationState pullAttackAnimationState = new AnimationState();
	public AnimationState backAnimationState = new AnimationState();

	public AnimationState cleave1AnimationState = new AnimationState();
	public AnimationState cleave2AnimationState = new AnimationState();
	public AnimationState cleave3AnimationState = new AnimationState();
	public AnimationState slash1AnimationState = new AnimationState();
	public AnimationState slash2AnimationState = new AnimationState();
	public AnimationState slash3AnimationState = new AnimationState();
	public AnimationState stomp1AnimationState = new AnimationState();
	public AnimationState stomp2AnimationState = new AnimationState();
	public AnimationState stomp3AnimationState = new AnimationState();
	public AnimationState fist1AnimationState = new AnimationState();
	public AnimationState fist2AnimationState = new AnimationState();
	public AnimationState fist3AnimationState = new AnimationState();
	public AnimationState shake1AnimationState = new AnimationState();
	public AnimationState shake2AnimationState = new AnimationState();
	public AnimationState shake3AnimationState = new AnimationState();

	public AnimationState block1AnimationState = new AnimationState();
	public AnimationState block2AnimationState = new AnimationState();
	public AnimationState block3AnimationState = new AnimationState();
	public AnimationState block4AnimationState = new AnimationState();
	public AnimationState block5AnimationState = new AnimationState();
	public AnimationState throwAnimationState = new AnimationState();
	public AnimationState wonderlinCallAnimationState = new AnimationState();
	public AnimationState wonderlinRushAnimationState = new AnimationState();
	public AnimationState goldAnimationState = new AnimationState();
	public AnimationState thornAnimationState = new AnimationState();
	public AnimationState grappleAnimationState = new AnimationState();
	public AnimationState rushAttackAnimationState = new AnimationState();
	public AnimationState shockAnimationState = new AnimationState();
	public AnimationState angryAttackAnimationState = new AnimationState();
	public AnimationState snapAttackAnimationState = new AnimationState();
	public AnimationState blockAttackAnimationState = new AnimationState();
	public AnimationState rushStartAnimationState = new AnimationState();
	public AnimationState rushStopAnimationState = new AnimationState();

	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, false);
	private static final EntityDataAccessor<Boolean> SWORD_USE = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> GOLDEN_BODY = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_CLOTHES = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_SWORD = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> USE_SPELL_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_BLOCK_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROW_SPEAR_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROW_SPEAR_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WONDERLIN_CALL_AND_RUSH_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WONDERLIN_CALL_AND_RUSH_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GOLDEN_BODY_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GOLDEN_BODY_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GOLDEN_BODY_HAVE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GOLDEN_BODY_MUST_STOP_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SUMMON_THORN_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SUMMON_THORN_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GRAPPLE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GRAPPLE_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOCK_ATTACK_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOCK_ATTACK_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> NEED_SHOCK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SHOCK_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SHOCK_COOLDOWN = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> SHOCK_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANGRY_ATTACK_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANGRY_ATTACK_USE_TICK = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(TyrantOfTheMountainRealmEntity.class, EntityDataSerializers.BOOLEAN);

	public TyrantOfTheMountainRealmEntity(EntityType<? extends TyrantOfTheMountainRealmEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.6f);
		xpReward = 700;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setCanPickUpLoot(false);
		this.moveControl = new GiantMoveControl(this);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0f;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 0f;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 0f;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 0f;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0f;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 0f;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmMaxHealth);
			this.setHealth(this.getMaxHealth());
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmMeleeDamage);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmArmor);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmMovementSpeed);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmAttackKnockback);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(OtherMainConfig.TyrantOfTheMountainRealmKnockbackResistance);
		}
		if (level.isClientSide) {
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
			return(float) OtherMainConfig.TyrantOfTheMountainRealmMagicAttackPercentage;
		}
		return (float) OtherMainConfig.TyrantOfTheMountainRealmAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.TyrantOfTheMountainRealmDamageCap;
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
		return (float)OtherMainConfig.TyrantOfTheMountainRealmDamageCooldownTick * base;
	}

	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 360);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_HURT;
	}
	@Override
	public boolean isLeftHanded() {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 4.3f;
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
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && (this.isInFluidType())) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.85));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(vec3);
		}
		if (this.specialAction() && this.getRushUseTick() <= 0 && this.getShockUseTick() <= 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	//
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		if (this.getRushUseTick() <= 0) {
			super.push(0, 0, 0);
		}
		else {
			super.push(d, d2, d3);
		}
	}
	@Override
	public boolean isBlocking() {
		if (this.getSpecialBlockTick() > 3) {
			return true;
		}
		return super.isBlocking();
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.getRandom().nextInt(5) == 1 && this.getRushTick() > 0;
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_MUSIC;
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public int getShieldBreakStrength() {
		return 600;
	}
	@Override
	public void pushEntities() {
		if (this.getRushUseTick() <= 0 || this.getRushUseTick() > 120) {
			super.pushEntities();
		}
	}

	public int spellLevel = OtherMainConfig.TyrantOfTheMountainRealmSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	private double blockDestroyTick;
	public int startTick;
	public int startAttackTick;
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setUseSpellTick(int n){
		this.getEntityData().set(USE_SPELL_TICK, n);
	}
	public int getUseSpellTick(){
		return this.getEntityData().get(USE_SPELL_TICK);
	}
	public void setSpecialBlockTick(int n){
		this.getEntityData().set(SPECIAL_BLOCK_TICK, n);
	}
	public int getSpecialBlockTick(){
		return this.getEntityData().get(SPECIAL_BLOCK_TICK);
	}
	//技能
	public void setThrowSpearTick(int n){
		this.getEntityData().set(THROW_SPEAR_TICK, n);
	}
	public int getThrowSpearTick(){
		return this.getEntityData().get(THROW_SPEAR_TICK);
	}
	public void setThrowSpearUseTick(int n){
		this.getEntityData().set(THROW_SPEAR_USE_TICK, n);
	}
	public int getThrowSpearUseTick(){
		return this.getEntityData().get(THROW_SPEAR_USE_TICK);
	}
	public void setWonderlinCallAndRushTick(int n){
		this.getEntityData().set(WONDERLIN_CALL_AND_RUSH_TICK, n);
	}
	public int getWonderlinCallAndRushTick(){
		return this.getEntityData().get(WONDERLIN_CALL_AND_RUSH_TICK);
	}
	public void setWonderlinCallAndRushUseTick(int n){
		this.getEntityData().set(WONDERLIN_CALL_AND_RUSH_USE_TICK, n);
	}
	public int getWonderlinCallAndRushUseTick(){
		return this.getEntityData().get(WONDERLIN_CALL_AND_RUSH_USE_TICK);
	}
	public void setGoldenBodyTick(int n){
		this.getEntityData().set(GOLDEN_BODY_TICK, n);
	}
	public int getGoldenBodyTick(){
		return this.getEntityData().get(GOLDEN_BODY_TICK);
	}
	public void setGoldenBodyUseTick(int n){
		this.getEntityData().set(GOLDEN_BODY_USE_TICK, n);
	}
	public int getGoldenBodyUseTick(){
		return this.getEntityData().get(GOLDEN_BODY_USE_TICK);
	}
	public void setGoldenBodyHaveTick(int n){
		this.getEntityData().set(GOLDEN_BODY_HAVE_TICK, n);
	}
	public int getGoldenBodyHaveTick(){
		return this.getEntityData().get(GOLDEN_BODY_HAVE_TICK);
	}
	public void setGoldenBodyMustStopTick(int n){
		this.getEntityData().set(GOLDEN_BODY_MUST_STOP_TICK, n);
	}
	public int getGoldenBodyMustStopTick(){
		return this.getEntityData().get(GOLDEN_BODY_MUST_STOP_TICK);
	}
	public void setSummonThornTick(int n){
		this.getEntityData().set(SUMMON_THORN_TICK, n);
	}
	public int getSummonThornTick(){
		return this.getEntityData().get(SUMMON_THORN_TICK);
	}
	public void setSummonThornUseTick(int n){
		this.getEntityData().set(SUMMON_THORN_USE_TICK, n);
	}
	public int getSummonThornUseTick(){
		return this.getEntityData().get(SUMMON_THORN_USE_TICK);
	}
	public void setGrappleTick(int n){
		this.getEntityData().set(GRAPPLE_TICK, n);
	}
	public int getGrappleTick(){
		return this.getEntityData().get(GRAPPLE_TICK);
	}
	public void setGrappleUseTick(int n){
		this.getEntityData().set(GRAPPLE_USE_TICK, n);
	}
	public int getGrappleUseTick(){
		return this.getEntityData().get(GRAPPLE_USE_TICK);
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
	public void setBlockAttackTick(int n){
		this.getEntityData().set(BLOCK_ATTACK_TICK, n);
	}
	public int getBlockAttackTick(){
		return this.getEntityData().get(BLOCK_ATTACK_TICK);
	}
	public void setBlockAttackUseTick(int n){
		this.getEntityData().set(BLOCK_ATTACK_USE_TICK, n);
	}
	public int getBlockAttackUseTick(){
		return this.getEntityData().get(BLOCK_ATTACK_USE_TICK);
	}
	public boolean isNeedShock() {
		return this.getEntityData().get(NEED_SHOCK);
	}
	public void setNeedShock(boolean bl) {
		this.getEntityData().set(NEED_SHOCK, bl);
	}
	public void setShockTick(int n){
		this.getEntityData().set(SHOCK_TICK, n);
	}
	public int getShockTick(){
		return this.getEntityData().get(SHOCK_TICK);
	}
	public void setShockCooldown(float f){
		this.getEntityData().set(SHOCK_COOLDOWN, f);
	}
	public float getShockCooldown(){
		return this.getEntityData().get(SHOCK_COOLDOWN);
	}
	public void setShockUseTick(int n){
		this.getEntityData().set(SHOCK_USE_TICK, n);
	}
	public int getShockUseTick(){
		return this.getEntityData().get(SHOCK_USE_TICK);
	}
	public void setAngryAttackTick(int n){
		this.getEntityData().set(ANGRY_ATTACK_TICK, n);
	}
	public int getAngryAttackTick(){
		return this.getEntityData().get(ANGRY_ATTACK_TICK);
	}
	public void setAngryAttackUseTick(int n){
		this.getEntityData().set(ANGRY_ATTACK_USE_TICK, n);
	}
	public int getAngryAttackUseTick(){
		return this.getEntityData().get(ANGRY_ATTACK_USE_TICK);
	}
	//
	public boolean specialAction() {
		return this.getChangeSword() > 0 ||
				this.getThrowSpearUseTick() > 0 ||
				this.getWonderlinCallAndRushUseTick() > 0 ||
				this.getGoldenBodyUseTick() > 0 ||
				this.getSummonThornUseTick() > 0 ||
				this.getGrappleUseTick() > 0 ||
				this.getRushUseTick() > 0 ||
				this.getBlockAttackUseTick() > 0 ||
				this.getShockUseTick() > 0 ||
				this.getAngryAttackUseTick() > 0 ||
				this.getSpecialBlockTick() > 0;
	}
	public float rushFindXRot;
	public float rushFindYRot;
	//
	@Nullable
	private LivingEntity grappleGet;
	@Nullable
	private UUID grappleGetUUID;
	@Nullable
	public UUID getGrappleGetUUID() {
		return grappleGetUUID;
	}
	@Nullable
	public LivingEntity getGrappleGet() {
		Entity entity;
		if (this.grappleGet == null && this.grappleGetUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.grappleGetUUID)) instanceof LivingEntity) {
			this.grappleGet = (LivingEntity)entity;
		}
		return this.grappleGet;
	}
	public void setGrappleGet(@Nullable LivingEntity livingEntity) {
		this.grappleGet = livingEntity;
		this.grappleGetUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	//
	public boolean isUseSword() {
		return this.getEntityData().get(SWORD_USE);
	}
	public void setUseSword(boolean bl) {
		this.getEntityData().set(SWORD_USE, bl);
	}
	public boolean isGoldenBody() {
		return this.getEntityData().get(GOLDEN_BODY);
	}
	public void setGoldenBody(boolean bl) {
		this.getEntityData().set(GOLDEN_BODY, bl);
	}
	public boolean isNoClothes() {
		return this.getEntityData().get(NO_CLOTHES);
	}
	public void setNoClothes(boolean bl) {
		this.getEntityData().set(NO_CLOTHES, bl);
	}
	public int getChangeSword() {
		return this.getEntityData().get(CHANGE_SWORD);
	}
	public void setChangeSword(int n) {
		this.getEntityData().set(CHANGE_SWORD, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
	}
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "pull")){
			return 1;
		}
		else if (Objects.equals(animation, "pullAttack")){
			return 2;
		}
		else if (Objects.equals(animation, "back")){
			return 3;
		}
		else if (Objects.equals(animation, "cleave1")){
			return 4;
		}
		else if (Objects.equals(animation, "cleave2")){
			return 5;
		}
		else if (Objects.equals(animation, "cleave3")){
			return 6;
		}
		else if (Objects.equals(animation, "slash1")){
			return 7;
		}
		else if (Objects.equals(animation, "slash2")){
			return 8;
		}
		else if (Objects.equals(animation, "slash3")){
			return 9;
		}
		else if (Objects.equals(animation, "stomp1")){
			return 10;
		}
		else if (Objects.equals(animation, "stomp2")){
			return 11;
		}
		else if (Objects.equals(animation, "stomp3")){
			return 12;
		}
		else if (Objects.equals(animation, "fist1")){
			return 13;
		}
		else if (Objects.equals(animation, "fist2")){
			return 14;
		}
		else if (Objects.equals(animation, "fist3")){
			return 15;
		}
		else if (Objects.equals(animation, "shake1")){
			return 16;
		}
		else if (Objects.equals(animation, "shake2")){
			return 17;
		}
		else if (Objects.equals(animation, "shake3")){
			return 18;
		}
		else if (Objects.equals(animation, "block1")){
			return 19;
		}
		else if (Objects.equals(animation, "block2")){
			return 20;
		}
		else if (Objects.equals(animation, "block3")){
			return 21;
		}
		else if (Objects.equals(animation, "block4")){
			return 22;
		}
		else if (Objects.equals(animation, "block5")){
			return 23;
		}
		else if (Objects.equals(animation, "throw")){
			return 24;
		}
		else if (Objects.equals(animation, "wonderlinCall")){
			return 25;
		}
		else if (Objects.equals(animation, "wonderlinRush")){
			return 26;
		}
		else if (Objects.equals(animation, "gold")){
			return 27;
		}
		else if (Objects.equals(animation, "thorn")){
			return 28;
		}
		else if (Objects.equals(animation, "grapple")){
			return 29;
		}
		else if (Objects.equals(animation, "rushAttack")){
			return 30;
		}
		else if (Objects.equals(animation, "shock")){
			return 31;
		}
		else if (Objects.equals(animation, "angryAttack")){
			return 32;
		}
		else if (Objects.equals(animation, "snapAttack")){
			return 33;
		}
		else if (Objects.equals(animation, "blockAttack")){
			return 34;
		}
		else if (Objects.equals(animation, "dead")){
			return 35;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.pullAnimationState);
		list.add(this.pullAttackAnimationState);
		list.add(this.backAnimationState);
		list.add(this.cleave1AnimationState);
		list.add(this.cleave2AnimationState);
		list.add(this.cleave3AnimationState);
		list.add(this.slash1AnimationState);
		list.add(this.slash2AnimationState);
		list.add(this.slash3AnimationState);
		list.add(this.stomp1AnimationState);
		list.add(this.stomp2AnimationState);
		list.add(this.stomp3AnimationState);
		list.add(this.fist1AnimationState);
		list.add(this.fist2AnimationState);
		list.add(this.fist3AnimationState);
		list.add(this.shake1AnimationState);
		list.add(this.shake2AnimationState);
		list.add(this.shake3AnimationState);
		list.add(this.block1AnimationState);
		list.add(this.block2AnimationState);
		list.add(this.block3AnimationState);
		list.add(this.block4AnimationState);
		list.add(this.block5AnimationState);
		list.add(this.throwAnimationState);
		list.add(this.wonderlinCallAnimationState);
		list.add(this.wonderlinRushAnimationState);
		list.add(this.goldAnimationState);
		list.add(this.thornAnimationState);
		list.add(this.grappleAnimationState);
		list.add(this.rushAttackAnimationState);
		list.add(this.shockAnimationState);
		list.add(this.angryAttackAnimationState);
		list.add(this.snapAttackAnimationState);
		list.add(this.blockAttackAnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.grappleGetUUID != null) {
			compoundTag.putUUID("GrappleGet", this.grappleGetUUID);
		}
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("StartTick", this.startTick);
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("SpecialBlockTick", this.getSpecialBlockTick());
		compoundTag.putInt("StartAttackTick", this.startAttackTick);
		compoundTag.putInt("SpellTick", this.getUseSpellTick());
		//技能
		compoundTag.putInt("ThrowSpearTick", this.getThrowSpearTick());
		compoundTag.putInt("ThrowSpearUseTick", this.getThrowSpearUseTick());
		compoundTag.putInt("WonderlinCallAndRushTick", this.getWonderlinCallAndRushTick());
		compoundTag.putInt("WonderlinCallAndRushUseTick", this.getWonderlinCallAndRushUseTick());
		compoundTag.putInt("GoldenBodyTick", this.getGoldenBodyTick());
		compoundTag.putInt("GoldenBodyUseTick", this.getGoldenBodyUseTick());
		compoundTag.putInt("GoldenBodyMustStopTick", this.getGoldenBodyMustStopTick());
		compoundTag.putInt("GoldenBodyHaveTick", this.getGoldenBodyHaveTick());
		compoundTag.putInt("SummonThornTick", this.getSummonThornTick());
		compoundTag.putInt("SummonThornUseTick", this.getSummonThornUseTick());
		compoundTag.putInt("GrappleTick", this.getGrappleTick());
		compoundTag.putInt("GrappleUseTick", this.getGrappleUseTick());
		compoundTag.putInt("RushTick", this.getRushTick());
		compoundTag.putInt("RushUseTick", this.getRushUseTick());
		compoundTag.putInt("BlockAttackTick", this.getBlockAttackTick());
		compoundTag.putInt("BlockAttackUseTick", this.getBlockAttackUseTick());
		compoundTag.putBoolean("IsNeedShock", this.isNeedShock());
		compoundTag.putInt("ShockTick", this.getShockTick());
		compoundTag.putFloat("ShockCooldown", this.getShockCooldown());
		compoundTag.putInt("ShockUseTick", this.getShockUseTick());
		compoundTag.putInt("AngryAttackTick", this.getAngryAttackTick());
		compoundTag.putInt("AngryAttackUseTick", this.getAngryAttackUseTick());
		//
		compoundTag.putInt("ChangeSword", this.getChangeSword());
		compoundTag.putBoolean("IsUseSword", this.isUseSword());
		compoundTag.putBoolean("IsGoldenBody", this.isGoldenBody());
		compoundTag.putBoolean("IsNoClothes", this.isNoClothes());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
		}
		if (compoundTag.hasUUID("GrappleGet")) {
			this.grappleGetUUID = compoundTag.getUUID("GrappleGet");
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.startTick = compoundTag.getInt("StartTick");
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setSpecialBlockTick(compoundTag.getInt("SpecialBlockTick"));
		this.startAttackTick = compoundTag.getInt("StartAttackTick");
		this.setUseSpellTick(compoundTag.getInt("SpellTick"));
		//技能
		this.setThrowSpearTick(compoundTag.getInt("ThrowSpearTick"));
		this.setThrowSpearUseTick(compoundTag.getInt("ThrowSpearUseTick"));
		this.setWonderlinCallAndRushTick(compoundTag.getInt("WonderlinCallAndRushTick"));
		this.setWonderlinCallAndRushUseTick(compoundTag.getInt("WonderlinCallAndRushUseTick"));
		this.setGoldenBodyTick(compoundTag.getInt("GoldenBodyTick"));
		this.setGoldenBodyUseTick(compoundTag.getInt("GoldenBodyUseTick"));
		this.setGoldenBodyHaveTick(compoundTag.getInt("GoldenBodyHaveTick"));
		this.setGoldenBodyMustStopTick(compoundTag.getInt("GoldenBodyMustStopTick"));
		this.setSummonThornTick(compoundTag.getInt("SummonThornTick"));
		this.setSummonThornUseTick(compoundTag.getInt("SummonThornUseTick"));
		this.setGrappleTick(compoundTag.getInt("GrappleTick"));
		this.setGrappleUseTick(compoundTag.getInt("GrappleUseTick"));
		this.setRushTick(compoundTag.getInt("RushTick"));
		this.setRushUseTick(compoundTag.getInt("RushUseTick"));
		this.setBlockAttackTick(compoundTag.getInt("BlockAttackTick"));
		this.setBlockAttackUseTick(compoundTag.getInt("BlockAttackUseTick"));
		this.setNeedShock(compoundTag.getBoolean("IsNeedShock"));
		this.setShockTick(compoundTag.getInt("ShockTick"));
		this.setShockCooldown(compoundTag.getFloat("ShockCooldown"));
		this.setShockUseTick(compoundTag.getInt("ShockUseTick"));
		this.setAngryAttackTick(compoundTag.getInt("AngryAttackTick"));
		this.setAngryAttackUseTick(compoundTag.getInt("AngryAttackUseTick"));
		//
		this.setChangeSword(compoundTag.getInt("ChangeSword"));
		if (compoundTag.contains("IsUseSword")) {
			this.setUseSword(compoundTag.getBoolean("IsUseSword"));
		}
		if (compoundTag.contains("IsGoldenBody")) {
			this.setGoldenBody(compoundTag.getBoolean("IsGoldenBody"));
		}
		if (compoundTag.contains("IsNoClothes")) {
			this.setNoClothes(compoundTag.getBoolean("IsNoClothes"));
		}
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SWORD_USE, false);
		this.getEntityData().define(GOLDEN_BODY, false);
		this.getEntityData().define(NO_CLOTHES, false);
		this.getEntityData().define(CHANGE_SWORD, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(USE_SPELL_TICK, 0);
		this.getEntityData().define(SPECIAL_BLOCK_TICK, 0);
		this.getEntityData().define(THROW_SPEAR_TICK, 0);
		this.getEntityData().define(THROW_SPEAR_USE_TICK, 0);
		this.getEntityData().define(WONDERLIN_CALL_AND_RUSH_TICK, 0);
		this.getEntityData().define(WONDERLIN_CALL_AND_RUSH_USE_TICK, 0);
		this.getEntityData().define(GOLDEN_BODY_TICK, 1200);
		this.getEntityData().define(GOLDEN_BODY_USE_TICK, 0);
		this.getEntityData().define(GOLDEN_BODY_HAVE_TICK, 0);
		this.getEntityData().define(GOLDEN_BODY_MUST_STOP_TICK, 0);
		this.getEntityData().define(SUMMON_THORN_TICK, 0);
		this.getEntityData().define(SUMMON_THORN_USE_TICK, 0);
		this.getEntityData().define(GRAPPLE_TICK, 0);
		this.getEntityData().define(GRAPPLE_USE_TICK, 0);
		this.getEntityData().define(RUSH_TICK, 0);
		this.getEntityData().define(RUSH_USE_TICK, 0);
		this.getEntityData().define(BLOCK_ATTACK_TICK, 0);
		this.getEntityData().define(BLOCK_ATTACK_USE_TICK, 0);
		this.getEntityData().define(NEED_SHOCK, false);
		this.getEntityData().define(SHOCK_TICK, 0);
		this.getEntityData().define(SHOCK_COOLDOWN, 0f);
		this.getEntityData().define(SHOCK_USE_TICK, 0);
		this.getEntityData().define(ANGRY_ATTACK_TICK, 0);
		this.getEntityData().define(ANGRY_ATTACK_USE_TICK, 0);
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
						this.pullAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pullAnimationState);
						break;
					case 2:
						this.pullAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pullAttackAnimationState);
						break;
					case 3:
						this.backAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.backAnimationState);
						break;
					case 4:
						this.cleave1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.cleave1AnimationState);
						break;
					case 5:
						this.cleave2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.cleave2AnimationState);
						break;
					case 6:
						this.cleave3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.cleave3AnimationState);
						break;
					case 7:
						this.slash1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash1AnimationState);
						break;
					case 8:
						this.slash2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash2AnimationState);
						break;
					case 9:
						this.slash3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.slash3AnimationState);
						break;
					case 10:
						this.stomp1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stomp1AnimationState);
						break;
					case 11:
						this.stomp2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stomp2AnimationState);
						break;
					case 12:
						this.stomp3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stomp3AnimationState);
						break;
					case 13:
						this.fist1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist1AnimationState);
						break;
					case 14:
						this.fist2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist2AnimationState);
						break;
					case 15:
						this.fist3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist3AnimationState);
						break;
					case 16:
						this.shake1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shake1AnimationState);
						break;
					case 17:
						this.shake2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shake2AnimationState);
						break;
					case 18:
						this.shake3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shake3AnimationState);
						break;
					case 19:
						this.block1AnimationState.startIfStopped(this.tickCount);
						break;
					case 20:
						this.block2AnimationState.startIfStopped(this.tickCount);
						break;
					case 21:
						this.block3AnimationState.startIfStopped(this.tickCount);
						break;
					case 22:
						this.block4AnimationState.startIfStopped(this.tickCount);
						break;
					case 23:
						this.block5AnimationState.startIfStopped(this.tickCount);
						break;
					case 24:
						this.throwAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.throwAnimationState);
						break;
					case 25:
						this.wonderlinCallAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.wonderlinCallAnimationState);
						break;
					case 26:
						this.wonderlinRushAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.wonderlinRushAnimationState);
						break;
					case 27:
						this.goldAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.goldAnimationState);
						break;
					case 28:
						this.thornAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.thornAnimationState);
						break;
					case 29:
						this.grappleAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.grappleAnimationState);
						break;
					case 30:
						this.rushAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.rushAttackAnimationState);
						break;
					case 31:
						this.shockAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shockAnimationState);
						break;
					case 32:
						this.angryAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.angryAttackAnimationState);
						break;
					case 33:
						this.snapAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.snapAttackAnimationState);
						break;
					case 34:
						this.blockAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.blockAttackAnimationState);
						break;
					case 35:
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
	}

	public static void useSelfTridentShoot(LivingEntity livingEntity, LivingEntity livingEntityTarget, float speed) {
		if (livingEntityTarget == null) {
			return;
		}
		ItemStack itemStackSpear = livingEntity.getOffhandItem();
		if (itemStackSpear.getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin() && jerotesItemThrownJavelinUse.JerotesThrownJavelinCanRange()) {
			int n3 = EnchantmentHelper.getRiptide(itemStackSpear);
			if (n3 > 0 && livingEntity.isInWaterOrRain() && jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinRush()) {
				float f1 = livingEntity.getYRot();
				float f2 = livingEntity.getXRot();
				float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 3.0f * ((1.0f + (float)n3) / 4.0f);
				livingEntity.push(f3 *= f7 / f6, f4 *= f7 / f6, f5 *= f7 / f6);
				if (livingEntity instanceof Player player) {
					player.startAutoSpinAttack(20);
				}
				if (livingEntity.onGround()) {
					float f8 = 1.1999999f;
					livingEntity.move(MoverType.SELF, new Vec3(0.0, 1.1999999284744263, 0.0));
				}
				SoundEvent soundEvent = n3 >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3 : (n3 == 2 ? SoundEvents.TRIDENT_RIPTIDE_2 : SoundEvents.TRIDENT_RIPTIDE_1);
				livingEntity.level().playSound(null, livingEntity, soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			else {
				if (jerotesItemThrownJavelinUse.JerotesNormalThrownJavelin()) {
					Projectile spear = jerotesItemThrownJavelinUse.JerotesThrownJavelin(livingEntity, itemStackSpear);
					if (spear == null) {
						return;
					}
					spear.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0f, speed, 0);
					if (!livingEntity.isSilent()) {
						livingEntity.playSound(SoundEvents.TRIDENT_THROW, 1.0f, 1.0f / (livingEntity.getRandom().nextFloat() * 0.4f + 0.8f));
					}
					livingEntity.level().addFreshEntity(spear);
				}
				else {
					jerotesItemThrownJavelinUse.JerotesNotNormalThrownJavelinUse();
				}
			}
		}
		if (itemStackSpear.getItem() instanceof TridentItem && itemStackSpear.getItem() == Items.TRIDENT) {
			int n3 = EnchantmentHelper.getRiptide(itemStackSpear);
			if (n3 > 0 && livingEntity.isInWaterOrRain()) {
				float f1 = livingEntity.getYRot();
				float f2 = livingEntity.getXRot();
				float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 3.0f * ((1.0f + (float)n3) / 4.0f);
				livingEntity.push(f3 *= f7 / f6, f4 *= f7 / f6, f5 *= f7 / f6);
				if (livingEntity instanceof Player player) {
					player.startAutoSpinAttack(20);
				}
				if (livingEntity.onGround()) {
					float f8 = 1.1999999f;
					livingEntity.move(MoverType.SELF, new Vec3(0.0, 1.1999999284744263, 0.0));
				}
				SoundEvent soundEvent = n3 >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3 : (n3 == 2 ? SoundEvents.TRIDENT_RIPTIDE_2 : SoundEvents.TRIDENT_RIPTIDE_1);
				livingEntity.level().playSound(null, livingEntity, soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			else {
				ThrownTrident spear = new ThrownTrident(livingEntity.level(), livingEntity, itemStackSpear);
				spear.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0f, speed, 0);
				if (!livingEntity.isSilent()) {
					livingEntity.playSound(SoundEvents.TRIDENT_THROW, 1.0f, 1.0f / (livingEntity.getRandom().nextFloat() * 0.4f + 0.8f));
				}
				livingEntity.level().addFreshEntity(spear);
			}
		}
	}
	public boolean isLandNavigatorType = true;

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.specialAction() && this.getRushUseTick() <= 0 && this.getShockUseTick() <= 0 && this.getGrappleUseTick() <= 0 || this.getAttackTick() > 15 && this.getRushUseTick() <= 0){
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 30.0f, 30.0f);
				this.lookAt(this.getTarget(), 30.0f, 30.0f);
			}
			this.getNavigation().stop();
		}
		if (this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}
		//破坏方块
		{
			if (this.blockDestroyTick > 0) {
				this.blockDestroyTick -= 1;
			}
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 10f);
				if (blockDestroy) {
					this.blockDestroyTick = OtherMainConfig.TyrantOfTheMountainRealmBreakBlockCooldown * 20;
				}
				if (!this.level().isClientSide) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
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
		//
		//站立动画
		{
			if (this.isAlive()) {
				this.idleBaseAnimationState.startIfStopped(this.tickCount);
			}
			if ((this.getChangeSword() <= 3 && this.isUseSword()) || (this.getChangeSword() > 5 && !this.isUseSword())) {
				this.idle1AnimationState.stop();
				if (this.isAlive()) {
					this.idle2AnimationState.start(this.tickCount);
				}
			}
			else {
				this.idle2AnimationState.stop();
				if (this.isAlive()) {
					this.idle1AnimationState.start(this.tickCount);
				}
			}
		}
		//拔刀
		{
			this.startAttackTick = Math.max(0, this.startAttackTick - 1);
			this.setChangeSword(Math.max(0, this.getChangeSword() - 1));
			if (this.getTarget() != null || this.isAggressive() || (this.specialAction() && this.getChangeSword() <= 0) || this.getAttackTick() > 0) {
				this.startAttackTick = 600;
			}
			if (!this.specialAction() && this.getChangeSword() <= 0) {
				if (this.startAttackTick <= 0 && this.isUseSword()) {
					if (!this.level().isClientSide()) {
						this.setUseSword(false);
						this.setChangeSword(20);
						this.setAnimTick(20);
						this.setAnimationState("back");
					}
				}
				if (this.startAttackTick > 0 && !this.isUseSword()) {
					if (!this.level().isClientSide()) {
						this.setUseSword(true);
						this.setChangeSword(20);
					}
					//拔刀
					if (this.getTarget() != null && this.getTarget().isAlive() && this.isWithinMeleeAttackRange(this.getTarget()) && this.getSensing().hasLineOfSight(this.getTarget()) && Main.canSee(this.getTarget(), this)) {
						//2-挥砍
						if (!this.level().isClientSide()) {
							this.setAttackTick(20);
							this.setAttackUse(2);
							this.setAnimTick(20);
							this.setAnimationState("pullAttack");
						}
					} else {
						if (!this.level().isClientSide()) {
							this.setAnimTick(20);
							this.setAnimationState("pull");
						}
					}
				}
			}
		}
		//近战攻击及格挡
		{
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-(int)( OtherMainConfig.TyrantOfTheMountainRealmMeleeAttackCooldown * 20), this.getAttackTick() - 1));
				this.setSpecialBlockTick(Math.max(0, this.getSpecialBlockTick() - 1));
			}
			if ((this.getAttackTick() > 10 || this.getAngryAttackUseTick() > 10) && this.isAlive() && !this.specialAction()) {
				this.trueHurtParticles();
			}
			if (this.getAttackTick() == 10 && this.isAlive()) {
				this.trueHurtFind();
			}
		}
		//技能
		{
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			List<TyrantOfTheMountainRealmEntity> listTyrant = this.level().getEntitiesOfClass(TyrantOfTheMountainRealmEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listTyrant.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<BaseWonderlinEntity> listWonderlin = this.level().getEntitiesOfClass(BaseWonderlinEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listWonderlin.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			//镇山旗矛
			{
				//开始
				if (this.getThrowSpearTick() == 320 && this.spellNeed(12, 24, 2)) {
					if (!this.level().isClientSide()) {
						this.setThrowSpearUseTick(45);
						this.setUseSpellTick(55);
					}
				}
				if (this.getThrowSpearUseTick() == 45 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setThrowSpearTick(0);
						this.setAnimTick(45);
						this.setAnimationState("throw");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.MOUNTAIN_REALM_FLAG_SPEAR_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_SHOOT, 5.0f, 1.0f);
					}
				}
				if (this.getThrowSpearUseTick() > 0 && this.isAlive()) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				//发射
				if (getThrowSpearUseTick() == 12 && this.isAlive()) {
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}

					int count = OtherMainConfig.TyrantOfTheMountainRealmFlagSpearCount;
					if (InventoryEntity.isRangeJavelin(this.getOffhandItem())) {
						useSelfTridentShoot(this, this.getTarget(), 3f);
						count -= 1;
					}

					for (int i = 0; i < count; ++i) {
						ThrownMountainRealmFlagSpearEntity spear = new ThrownMountainRealmFlagSpearEntity(this.level(), this, new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get()));
						spear.shootFromRotation(this, this.getXRot(), (this.getYRot() - 3 + i * 3), 0.0f, 3f, 0);
						if (!this.isSilent()) {
							this.playSound(SoundEvents.TRIDENT_THROW, 5.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
						}
						this.level().addFreshEntity(spear);
					}
				}
			}
			//奇灵呼唤&&奇灵冲撞
			{
				//奇灵召唤
				if (this.getWonderlinCallAndRushTick() == 800 && this.spellNeed(6, 18, 4) && listWonderlin.size() < listTyrant.size() * 12) {
					if (!this.level().isClientSide()) {
						this.setWonderlinCallAndRushUseTick(75);
						this.setUseSpellTick(85);
					}
				}
				if (this.getWonderlinCallAndRushUseTick() == 75 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setWonderlinCallAndRushTick(0);
						this.setAnimTick(40);
						this.setAnimationState("wonderlinCall");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_CALL, 5.0f, 1.0f);
					}
				}
				//召唤
				if (this.getWonderlinCallAndRushUseTick() == 60 && this.isAlive()) {
					OtherSpellFind.WonderlinCall(this, spellLevel, spellLevel, 4, 32);
				}
				//奇灵冲撞
				if (this.getWonderlinCallAndRushUseTick() == 35 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setAnimTick(35);
						this.setAnimationState("wonderlinRush");
					}

					if (this.level() instanceof ServerLevel serverLevel) {
						List<BaseWonderlinEntity> list = serverLevel.getEntitiesOfClass(BaseWonderlinEntity.class, this.getBoundingBox().inflate(12, 12, 12));
						for (BaseWonderlinEntity wonderlin : list) {
							if (wonderlin == null) continue;
							if (wonderlin == this) continue;
							if (wonderlin instanceof TyrantOfTheMountainRealmEntity) continue;
							if ((this.distanceTo(wonderlin)) > 24) continue;
							if ((wonderlin.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(wonderlin))
								continue;
							BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(this, 3);
							wonderlin.moveTo(summonPos.getCenter());
						}
					}
				}
				//冲撞
				if (this.getWonderlinCallAndRushUseTick() == 15 && this.isAlive()) {
					OtherSpellFind.WonderlinRush(this, 8);
					if (!this.level().isClientSide()) {
						this.setAttackTick(11);
						this.setAttackUse(2);
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_RUSH, 5.0f, 1.0f);
					}
				}
			}
			//金甲护身
			{
				//开始
				if (this.getGoldenBodyTick() == 1200 && this.spellNeed(0, 32, 1) && this.getGoldenBodyHaveTick() <= 0) {
					if (!this.level().isClientSide()) {
						this.setGoldenBodyUseTick(50);
						this.setUseSpellTick(60);
					}
				}
				if (this.getGoldenBodyUseTick() == 50 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setGoldenBodyTick(0);
						this.setAnimTick(50);
						this.setAnimationState("gold");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.GOLDEN_ARMOR_PROTECTION_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_GOLD, 5.0f, 1.0f);
					}
				}
				//金甲状态
				if (this.getGoldenBodyUseTick() == 20 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setGoldenBodyHaveTick(320);
						this.setGoldenBodyMustStopTick(2880);
					}
				}
				if (this.getGoldenBodyHaveTick() > 0) {
					this.setGoldenBodyTick(0);
				}
				//状态切换
				if (this.getGoldenBodyHaveTick() > 0 && !this.isGoldenBody()) {
					if (!this.level().isClientSide()) {
						this.setGoldenBody(true);
					}
				}
				if ((this.getGoldenBodyHaveTick() <= 0 || this.getGoldenBodyMustStopTick() <= 0) && this.isGoldenBody()) {
					if (!this.level().isClientSide()) {
						this.setGoldenBody(false);
					}
				}
			}
			//荆棘之刺
			{
				//开始
				if (this.getSummonThornTick() == 360 && this.spellNeed(0, 18, 2)) {
					if (!this.level().isClientSide()) {
						this.setSummonThornUseTick(40);
						this.setUseSpellTick(45);
					}
				}
				if (this.getSummonThornUseTick() == 40 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setSummonThornTick(0);
						this.setAnimTick(40);
						this.setAnimationState("thorn");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_THORN, 5.0f, 1.0f);
					}
				}
				//产生
				if (this.getSummonThornUseTick() == 25 && this.isAlive()) {
					if (this.getTarget() != null) {
						//法术列表-荆棘之刺
						OtherSpellList.ThornSpike(spellLevel, this, this.getTarget()).spellUse();
					}
					else {
						//法术列表-荆棘之刺
						OtherSpellList.ThornSpike(spellLevel, this, this).spellUse();
					}
				}
			}
			//擒抱抛投
			{
				//开始
				if (this.spellNeedGrapple()) {
					if (!this.level().isClientSide()) {
						this.setGrappleUseTick(60);
						this.setUseSpellTick(60);
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				if (this.getGrappleUseTick() == 60 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setGrappleTick(0);
						this.setAnimTick(60);
						this.setAnimationState("grapple");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_GRAPPLE, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.GRAPPLE_TOSS_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				//抓住
				if (this.getGrappleUseTick() == 48 && this.isAlive()) {
					if (this.getTarget() != null && this.isWithinGrappleAttackRange(this.getTarget(), 0.5f) && this.getSensing().hasLineOfSight(this.getTarget()) && Main.canSee(this.getTarget(), this)) {
						if (!this.level().isClientSide()) {
							this.setGrappleGet(this.getTarget());
						}
						if (!this.getTarget().level().isClientSide() && !(this.getTarget() instanceof Player) && this.getTarget() != null) {
							this.getTarget().addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 10, 0, false, false), this);
						}
					}
				}
				if (this.getGrappleGet() != null && this.isAlive()) {
					LivingEntity livingEntity = this.getGrappleGet();
					if (this.getGrappleUseTick() > 27 && this.getGrappleUseTick() < 48) this.grappleMove(livingEntity, 1.5f, 15f, 1 + (22 - (this.getGrappleUseTick() - 27)) * (1/8f));
					if (this.getGrappleUseTick() > 22 && this.getGrappleUseTick() <= 27) this.grappleMove(livingEntity, 2f, 135f, 3.5f);
					if (this.getGrappleUseTick() == 21) this.grappleMove(livingEntity, 1.5f, 45f, 3.5f);
					if (this.getGrappleUseTick() == 20) {
						this.grappleMove(livingEntity, 1f, 15, 4f);
						AttackFind.attackBegin(this, livingEntity);
						float damages = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.5f;
						damages += Math.min(livingEntity.getMaxHealth()/18f, this.getHealth()/5f);
						AttackFind.attackAfter(this, livingEntity, 0, 0, true, damages);
						if (!this.level().isClientSide()) {
							this.setAttackTick(11);
							this.setAttackUse(5);
						}
						if (this.getGrappleGet() != null && this.getGrappleGet().isAlive() && this.getGrappleGet().getHealth() > 0) {
							if (this.getGrappleGet() instanceof ServerPlayer player) {
								AdvancementEvent.AdvancementGive(player, "jerotesvillage:copper_head_iron_arms");
							}
						}
					}
					if (this.getGrappleUseTick() > 13 && this.getGrappleUseTick() < 20) this.grappleMove(livingEntity, 1.5f, -10 + (this.getGrappleUseTick() - 12) * 6, 4f);
					if (this.getGrappleUseTick() == 13) this.grappleMove(livingEntity, 1f, 45f, 4f);
					if (this.getGrappleUseTick() == 12) {
						float f = this.getYRot();
						float f2 = this.getXRot();
						float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
						float f4 = -Mth.sin(f2 * 0.017453292f);
						float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
						float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
						float f7 = 1.25f;
						livingEntity.setOnGround(false);
						livingEntity.setDeltaMovement(f3 *= f7 / f6 * 4, f4 *= 0.25f / f6 * 2 + 0.25f, f5 *= f7 / f6 * 4);
						livingEntity.fallDistance += 20;
					}
				}
				if (this.getGrappleUseTick() > 0 && this.getGrappleUseTick() < 30 && this.getGrappleGet() == null && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setGrappleGet(null);
						this.setGrappleUseTick(0);
						this.setAnimTick(0);
						this.setUseSpellTick(5);
						this.grappleAnimationState.stop();
					}
				}
				//放弃
				if (!this.isAlive() || (this.getGrappleUseTick() <= 0 && this.getGrappleGet() != null) || (this.getGrappleGet() != null && this.getGrappleGet() instanceof Player player && player.isShiftKeyDown())) {
					if (!this.level().isClientSide()) {
						this.setGrappleGet(null);
					}
				}
			}
			//冲锋斩
			{
				//开始
				if (this.getRushTick() == 800 && this.spellNeed(5, 24, 1)) {
					if (!this.level().isClientSide()) {
						this.setRushUseTick(140);
						this.setUseSpellTick(145);
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				if (this.getRushUseTick() == 140 && this.isAlive()) {
					this.rushStartAnimationState.start(this.tickCount);
					if (!this.level().isClientSide()) {
						this.setRushTick(0);
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_RUSH_ATTACK, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.CHARGE_CHOP_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
					if (this.getTarget() != null) {
						this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
						this.lookAt(this.getTarget(), 360.0f, 360.0f);
					}
				}
				//冲锋推动
				if (this.getRushUseTick() <= 120 && this.getRushUseTick() > 0 && this.isAlive()) {
					if (this.getRushUseTick() > 100) {
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
							this.lookAt(this.getTarget(), 360.0f, 360.0f);
						}
						this.rushFindXRot = this.getXRot();
						this.rushFindYRot = this.getYRot();
					}
					this.RushAttack();
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
				boolean must = this.getRushUseTick() == 20;
				boolean maybe = this.getRushUseTick() <= 60 && this.getRushUseTick() > 20;
				boolean canNotPush = this.getTarget() != null && (!(Main.mobSizeSmall(this.getTarget()) || Main.mobSizeMedium(this.getTarget()) || Main.mobSizeLarge(this.getTarget()))
						|| EntityAndItemFind.isNoSpecialKnockback(this.getTarget().getType()) || this.getTarget().isShiftKeyDown());
				boolean noTarget = this.getTarget() == null;
				boolean tooFar = this.getTarget() != null && this.distanceTo(this.getTarget()) > 24;
				boolean canAttack = this.getTarget() != null && this.isWithinGrappleAttackRange(this.getTarget(), 0.5f) && this.getSensing().hasLineOfSight(this.getTarget()) && Main.canSee(this.getTarget(), this);
				//大劈
				if (must && this.isAlive() || (maybe && this.isAlive() && (canNotPush || noTarget || tooFar || canAttack))) {
					if (!this.level().isClientSide()) {
						this.setAttackTick(20);
						this.setAttackUse(1);
						this.setRushUseTick(20);
						this.setAnimTick(20);
						this.setAnimationState("rushAttack");
					}
				}
				if (this.getRushUseTick() == 20 && this.isAlive()) {
					this.rushStopAnimationState.start(this.tickCount);
				}
				if (this.getRushUseTick() <= 0) {
					this.rushStartAnimationState.stop();
					this.rushStopAnimationState.stop();
				}
			}
			//震地踢击
			{
				//开始
				if (this.getBlockAttackTick() == 800 && this.spellNeed(0, 24, 2)) {
					if (!this.level().isClientSide()) {
						this.setBlockAttackUseTick(40);
						this.setUseSpellTick(45);
					}
				}
				if (this.getBlockAttackUseTick() == 40 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setBlockAttackTick(0);
						this.setAnimTick(40);
						this.setAnimationState("blockAttack");
					}
				}
				//产生
				if (getBlockAttackUseTick() == 25 && this.isAlive()) {
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_BLOCK_ATTACK, 5.0f, 1.0f);
					}
					OtherSpellFind.EarthquakeKick(this, spellLevel * 2, spellLevel, 30, 10, true);
				}
				if (getBlockAttackUseTick() == 15 && this.isAlive()) {
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_BLOCK_ATTACK, 5.0f, 1.0f);
					}
				}
			}
			//不屈意志
			{
				//判定
				if (this.getShockCooldown() >= (this.getHealth()/4) && this.getShockTick() >= 240 && this.getUseSpellTick() <= 0 && this.isUseSword() && !this.specialAction() && this.isAlive() && this.getAttackTick() <= 0) {
					if (!this.isNeedShock()) {
						if (!this.level().isClientSide()) {
							this.setNeedShock(true);
						}
					}
				}
				//开始
				if (this.getShockUseTick() == 40 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setUseSpellTick(40);
						this.setAnimTick(40);
						this.setAnimationState("shock");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_SHOCK, 5.0f, 1.0f);
					}
				}
				//效果
				if (this.getShockUseTick() == 20 && this.isAlive()) {
					if (this.getHealth() > this.getMaxHealth() / 2) {
						if (this.isNoClothes()) {
							if (!this.level().isClientSide()) {
								this.setNoClothes(false);
							}
						}
					}
					else {
						if (!this.isNoClothes()) {
							if (!this.level().isClientSide()) {
                                this.setNoClothes(true);
							}
						}
					}
					//法术列表-不屈意志
					OtherSpellList.IndomitableWill(spellLevel, this, this).spellUse();
				}
			}
			//盛怒连击
			{
				//开始
				if (this.getAngryAttackTick() == 800 && this.spellMeleeNeed(0.15f, 2)) {
					if (!this.level().isClientSide()) {
						this.setAngryAttackUseTick(70);
						this.setUseSpellTick(80);
					}
				}
				if (this.getAngryAttackUseTick() == 70 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setAngryAttackTick(0);
						this.setAnimTick(70);
						this.setAttackUse(2);
						this.setAttackTick(30);
						this.setAnimationState("angryAttack");
					}
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ANGRY_ATTACK, 5.0f, 1.0f);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.FURIOUS_ASSAULT_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				//第一刀后
				if (getAngryAttackUseTick() == 45 && this.isAlive()) {
					this.setAttackUse(2);
					this.setAttackTick(15);
				}
				//第二刀后
				if (getAngryAttackUseTick() == 35 && this.isAlive()) {
					this.setAttackUse(2);
					this.setAttackTick(15);
				}
				//第三刀后
				if (getAngryAttackUseTick() == 25 && this.isAlive()) {
					this.setAttackUse(2);
					this.setAttackTick(15);
				}
				//第四刀后
				if (getAngryAttackUseTick() == 15 && this.isAlive()) {
					this.setAttackUse(1);
					this.setAttackTick(15);
				}
			}
			if (!this.level().isClientSide()) {
				this.setUseSpellTick(Math.max(0, this.getUseSpellTick() - 1));
				this.setThrowSpearTick(Math.min(320, this.getThrowSpearTick() + 1));
				this.setThrowSpearUseTick(Math.max(0, this.getThrowSpearUseTick() - 1));
				this.setWonderlinCallAndRushTick(Math.min(800, this.getWonderlinCallAndRushTick() + 1));
				this.setWonderlinCallAndRushUseTick(Math.max(0, this.getWonderlinCallAndRushUseTick() - 1));
				this.setGoldenBodyTick(Math.min(1200, this.getGoldenBodyTick() + 1));
				this.setGoldenBodyUseTick(Math.max(0, this.getGoldenBodyUseTick() - 1));
				this.setGoldenBodyHaveTick(Math.max(0, this.getGoldenBodyHaveTick() - 1));
				this.setGoldenBodyMustStopTick(Math.max(0, this.getGoldenBodyMustStopTick() - 1));
				this.setSummonThornTick(Math.min(360, this.getSummonThornTick() + 1));
				this.setSummonThornUseTick(Math.max(0, this.getSummonThornUseTick() - 1));
				this.setGrappleTick(Math.min(360, this.getGrappleTick() + 1));
				this.setGrappleUseTick(Math.max(0, this.getGrappleUseTick() - 1));
				this.setRushTick(Math.min(800, this.getRushTick() + 1));
				this.setRushUseTick(Math.max(0, this.getRushUseTick() - 1));
				this.setBlockAttackTick(Math.min(800, this.getBlockAttackTick() + 1));
				this.setBlockAttackUseTick(Math.max(0, this.getBlockAttackUseTick() - 1));
				this.setShockTick(Math.min(240, this.getShockTick() + 1));
				this.setShockUseTick(Math.max(0, this.getShockUseTick() - 1));
				this.setAngryAttackTick(Math.min(800, this.getAngryAttackTick() + 1));
				this.setAngryAttackUseTick(Math.max(0, this.getAngryAttackUseTick() - 1));
			}

		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return this.getUseSpellTick() <= 0 && this.isUseSword() && !this.specialAction() && !this.isNoAi() && this.isAlive() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public boolean spellMeleeNeed(float distance, int time) {
		return this.getUseSpellTick() <= 0 && this.isUseSword() && !this.specialAction() && !this.isNoAi() && this.isAlive() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.isWithinGrappleAttackRange(this.getTarget(), distance)
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public boolean spellNeedGrapple() {
		return this.getUseSpellTick() <= 0 && this.isUseSword() && !this.specialAction() && !this.isNoAi() && this.isAlive() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.isWithinGrappleAttackRange(this.getTarget(), 0.25f)
				&& (Main.mobSizeSmall(this.getTarget()) || Main.mobSizeMedium(this.getTarget()) || Main.mobSizeLarge(this.getTarget()))
				&& !EntityAndItemFind.isNoSpecialKnockback(this.getTarget().getType())
				&& (this.getGrappleTick() == 360
				|| (this.getTarget().getHealth() < 40 && this.getTarget() instanceof Mob)) && this.getRandom().nextInt(20) == 1;
	}
	public boolean isWithinGrappleAttackRange(LivingEntity entity, float f) {
		return this.getAttackBoundingBox().inflate(f).intersects(entity.getBoundingBox());
	}
	//调整位置
	public void grappleMove(LivingEntity entity, float radius, float rot, float offsetY) {
		float angleYaw = (90.0F - rot) * ((float) Math.PI / 180.0F);
		double x = this.getX() + radius * Mth.cos((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
		double y = this.getY() + offsetY;
		double z = this.getZ() + radius * Mth.sin((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
		entity.teleportTo(x, y, z);
		entity.setDeltaMovement(0, 0, 0);
	}
	//选择普攻造成伤害类型
	public void trueHurtFind() {
		if (this.getAttackUse() == 1) {
			this.trueHurt(1, 1.25f, true, 1.6f, 1.0f, true, false);
		}
		else if (this.getAttackUse() == 2) {
			this.trueHurt(2, 1f, true, 1.4f, 1.5f, false, false);
		}
		else if (this.getAttackUse() == 3) {
			this.trueHurt(3, 0.75f, false, 1.2f, 1.0f, false, true);
		}
		else if (this.getAttackUse() == 4) {
			this.trueHurt(4, 1f, true, 1.2f, 2.0f, true, false);
		}
		else if (this.getAttackUse() == 5) {
			this.trueHurt(5, 1.75f, false, 1.0f, 2.0f, true, true);
		}
		else if (this.getAttackUse() == 6) {
			this.trueHurt(6, 3f, true, 2.0f, 3.0f, true, false);
		}
	}
	//选择普攻进行攻击类型
	public void doHurtTargetFind(){
		if (!this.level().isClientSide()) {
			float attackChoose = this.getRandom().nextFloat();
			int attackRandom = this.getRandom().nextInt(30);
			if (attackChoose > 0.9f) {
				this.setAttackTick(20);
				this.setAttackUse(1);
				this.setAnimTick(20);
				if (attackRandom > 20) {
					this.setAnimationState("cleave1");
				}
				else if (attackRandom > 10) {
					this.setAnimationState("cleave2");
				}
				else {
					this.setAnimationState("cleave3");
				}
			} else if (attackChoose > 0.55f) {
				this.setAttackTick(20);
				this.setAttackUse(2);
				this.setAnimTick(20);
				if (attackRandom > 20) {
					this.setAnimationState("slash1");
				}
				else if (attackRandom > 10) {
					this.setAnimationState("slash2");
				}
				else {
					this.setAnimationState("slash3");
				}
			} else if (attackChoose > 0.4f) {
				this.setAttackTick(20);
				this.setAttackUse(3);
				this.setAnimTick(20);
				if (attackRandom > 20) {
					this.setAnimationState("stomp1");
				}
				else if (attackRandom > 10) {
					this.setAnimationState("stomp2");
				}
				else {
					this.setAnimationState("stomp3");
				}
			} else if (attackChoose > 0.15f) {
				this.setAttackTick(20);
				this.setAttackUse(4);
				this.setAnimTick(20);
				if (attackRandom > 20) {
					this.setAnimationState("fist1");
				}
				else if (attackRandom > 10) {
					this.setAnimationState("fist2");
				}
				else {
					this.setAnimationState("fist3");
				}
			} else if (attackChoose > 0.035f) {
				this.setAttackTick(20);
				this.setAttackUse(5);
				this.setAnimTick(20);
				if (attackRandom > 20) {
					this.setAnimationState("shake1");
				}
				else if (attackRandom > 10) {
					this.setAnimationState("shake2");
				}
				else {
					this.setAnimationState("shake3");
				}
			}
			else {
				this.setAttackTick(20);
				this.setAttackUse(6);
				this.setAnimTick(20);
				this.setAnimationState("snapAttack");
			}
		}
	}
	//攻击范围粒子
	public void trueHurtParticles() {
		float normal = 1.5f;
		float normalY = 1.5f;
		boolean bl = false;
		if (this.getAttackUse() == 1) {
			normal += 1.25f;
			normalY += 1.25f;
		}
		else if (this.getAttackUse() == 2) {
			normal += 1f;
			normalY += 1f;
		}
		else if (this.getAttackUse() == 3) {
			normal += 0.75f;
			normalY += 0.325f;
			bl = true;
		}
		else if (this.getAttackUse() == 4) {
			normal += 1f;
			normalY += 1f;
		}
		else if (this.getAttackUse() == 5) {
			normal += 1.75f;
			normalY += 0.825f;
			bl = true;
		}
		else if (this.getAttackUse() == 6) {
			normal += 3f;
			normalY += 1.5f;
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 64; ++i) {
				double findX = this.getRandomX(1f) + ((this.random.nextFloat() * normal) * 2 - normal);
				double findY = this.getRandomY() + ((this.random.nextFloat() * normalY) * 2 - normalY);
				double findZ = this.getRandomZ(1f) + ((this.random.nextFloat() * normal) * 2 - normal);
				Vec3 vec3 = new Vec3(findX, findY, findZ);
				if (Main.canSee(vec3, this) || bl) {
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES,
							findX,
							findY,
							findZ,
							0, 0, 0.0, 0, 0.0);
				}
			}
		}
	}
	//冲刺
	public boolean RushAttack() {
		//技能-冲锋斩
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
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5f));
		for (LivingEntity attack : list) {
			if (!(Main.mobSizeSmall(attack) || Main.mobSizeMedium(attack) || Main.mobSizeLarge(attack)) || EntityAndItemFind.isNoSpecialKnockback(attack.getType())) continue;
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			if (attack instanceof Mob wonderlin && OtherEntityFactionFind.isWonderlin(wonderlin.getType()) && wonderlin.getTarget() != this && this.getTarget() != wonderlin)
				continue;
			float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
			double x = this.getX() + 1.5f * Mth.cos((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
			double y = this.getY() + 2;
			double z = this.getZ() + 1.5f * Mth.sin((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
			attack.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
			if (attack.isShiftKeyDown()) continue;
			AttackFind.attackBegin(this, attack);
			AttackFind.attackAfter(this, attack, 0.3f, 0.2f, false, 0);
			attack.teleportTo(x, y, z);
		}
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction() || !this.isUseSword()) {
			return false;
		}
		if (this.getAttackTick() <= -(int)( OtherMainConfig.TyrantOfTheMountainRealmMeleeAttackCooldown * 20)) {
			this.doHurtTargetFind();
			return true;
		}
		return false;
	}
	public void attackAnimation() {
	}

	public boolean trueHurt(int type, float reach, boolean isOnlyFace, float damageMulti, float knockbackMulti, boolean shieldBreak, boolean fallBlock) {
		//1-大劈
		//this.trueHurt(1, 1.25f, true, 1.6f, 1.0f, true, false)
		//2-挥砍
		//this.trueHurt(2, 1f, true, 1.4f, 1.5f, false, false)
		//3-重踏
		//this.trueHurt(3, 0.75f, false, 1.2f, 1.0f, false, true)
		//4-崩拳
		//this.trueHurt(4, 1f, true, 1.2f, 2.0f, true, false)
		//5-震跺
		//this.trueHurt(5, 1.75f, false, 1.0f, 2.0f, true, true)
		//6-拨弹
		//this.trueHurt(5, 1f, true, 2.0f, 3.0f, true, false)
		if (!this.isSilent()) {
			if (type == 1) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ATTACK_1, 5.0f, 1.0f);
			}
			else if (type == 2) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ATTACK_2, 5.0f, 1.0f);
			}
			else if (type == 3) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ATTACK_3, 5.0f, 1.0f);
			}
			else if (type == 4) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ATTACK_4, 5.0f, 1.0f);
			}
			else if (type == 5) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_ATTACK_5, 5.0f, 1.0f);
			}
			else if (type == 6) {
				this.playSound(JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_SNAP_ATTACK, 5.0f, 1.0f);
			}
		}
		if (type == 6) {
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.SNAP_PUSH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
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
			if (this.getAttackUse() == 6) {
				//弹指-震颤
				if (!hurt.level().isClientSide()) {
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 40, 0, false, false), this);
				}
			}
			if (!Main.canSee(hurt, this) && isOnlyFace) continue;
			if (!Main.canSeeAngle(this, hurt.getPosition(0.5f), 40) && this.getAttackUse() == 6) continue;
			if (this.getAttackUse() == 6) {
				//弹指-粒子
				if (this.level() instanceof ServerLevel serverLevel) {
					Vec3 vec3 = this.getEyePosition().add(0,-1,0);
					Vec3 vec32 = hurt.getEyePosition().subtract(vec3);
					Vec3 vec33 = vec32.normalize();
					for (int i = 1; i < Mth.floor(vec32.length()) * 3 + 8; ++i) {
						Vec3 vec34 = vec3.add(vec33.scale(i / 3f));
						serverLevel.sendParticles(ParticleTypes.FLASH, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
					}
					for (int i = 1; i < Mth.floor(vec32.length()) / 2 + 8; ++i) {
						Vec3 vec34 = vec3.add(vec33.scale(i * 2f));
						serverLevel.sendParticles(JerotesVillageParticleTypes.AIRWAVE.get(), vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
					}
				}
			}
			if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
				hurt.setDeltaMovement(hurt.getDeltaMovement().add((knockbackMulti / 3) * (0.7 + knockbackMulti / 3), (knockbackMulti / 3) * (0.7 + knockbackMulti / 3), (knockbackMulti / 3) * (0.7 + knockbackMulti / 3)));
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
						hurt.setDeltaMovement(hurt.getDeltaMovement().add((knockbackMulti / 3) * (0.7 + knockbackMulti / 3), (knockbackMulti / 3) * (0.7 + knockbackMulti / 3), (knockbackMulti / 3) * (0.7 + knockbackMulti / 3)));
					}
				}
				if (this.getAttackUse() == 6) {
					double d;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					} else {
						d = 0.0;
					}
					double d2 = Math.max(0.3, 1.3 - d);
					if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType()) && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d2 / 3) * (0.7 + knockbackMulti / 3),
								-(this.getY() - hurt.getY()) * d2 * (0.7 + knockbackMulti / 3),
								-((this.getZ() - hurt.getZ()) * d2 / 3) * (0.7 + knockbackMulti / 3)
						));
					}
					if (!this.level().isClientSide()) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), 120, 0), this);
						hurt.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0), this);
					}
				}
			}
			//破盾
			if (hurt.getUseItem().getItem() instanceof ShieldItem && shieldBreak) {
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
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.FLY_INTO_WALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
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
		float trueDamage = amount;
		if (AttackFind.isFireDamage(damageSource)
				|| AttackFind.isColdDamage(damageSource)
				|| AttackFind.isLightningDamage(damageSource)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
			trueDamage = trueDamage / 3;
		}
		//
		if ((this.isGoldenBody() || this.getGoldenBodyUseTick() > 0)) {
			trueDamage /= 3;
			if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this)) {
				trueDamage *= 0.75f;
			}
		}
		//伤害衰减
		trueDamage = this.hurtAbout(trueDamage);
		//延长金身
		if (this.getGoldenBodyHaveTick() > 0 && !this.isInvulnerableTo(damageSource) && !(damageSource.getEntity() instanceof CelestialCoilvineEntity) && !(damageSource.getEntity() instanceof OwnableEntity ownable && ownable.getOwner() instanceof CelestialCoilvineEntity)) {
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				if (!this.level().isClientSide()) {
					this.setGoldenBodyHaveTick(Math.min(320, (int) (this.getGoldenBodyHaveTick() + amount/3 + 1)));
				}
			}
			else if (this.level().getDifficulty() == Difficulty.HARD) {
				if (!this.level().isClientSide()) {
					this.setGoldenBodyHaveTick(Math.min(320, (int) (this.getGoldenBodyHaveTick() + amount + 1)));
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setGoldenBodyHaveTick(Math.min(320, (int) (this.getGoldenBodyHaveTick() + amount/5 + 1)));
				}
			}
		}

		if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && trueDamage <= this.getMaxHealth() * 5 && (this.isGoldenBody() || this.getGoldenBodyUseTick() > 0)) {
			return false;
		}
		//
		int spellLevel = this.getSpellLevel() - 1;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			spellLevel = this.getSpellLevel();
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			spellLevel = this.getSpellLevel() + 1;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(spellLevel * 4, spellLevel * 4, spellLevel * 4));
		list.removeIf(livingEntity -> AttackFind.FindCanNotAttack(this, livingEntity));
		list.removeIf(livingEntity ->  OtherEntityFactionFind.isWonderlin(livingEntity.getType()) && this.getTeam() == null && livingEntity.getTeam() == null);
		//
		if (!this.isNoAi() && this.isUseSword() && this.isAlive() && !this.isInvulnerableTo(damageSource)  && (isDamageSourceBlocks(damageSource) || this.isNeedShock() && !(this.getTarget() == null && list.isEmpty())) && !this.specialAction() && this.getAttackTick() <= 0) {
			if (this.getSpecialBlockTick() <= 0 && (this.getRandom().nextFloat() > 0.3 || this.isNeedShock() && !(this.getTarget() == null && list.isEmpty())) && trueDamage <= this.getMaxHealth() * 10) {
				if (!this.level().isClientSide()) {
					this.setSpecialBlockTick(8);
				}
				if (this.getRandom().nextFloat() <= 0.8 || !(damageSource.getEntity() != null && damageSource.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()
						&& this.isWithinMeleeAttackRange(livingEntity)
						&& this.getSensing().hasLineOfSight(livingEntity)
						&& Main.canSee(livingEntity, this))) {
					if (!this.level().isClientSide()) {
						//格挡
						int blockRandom = this.getRandom().nextInt(40);
						if (blockRandom > 30) {
							this.setAnimationState("block1");
						} else if (blockRandom > 20) {
							this.setAnimationState("block2");
						} else if (blockRandom > 10) {
							this.setAnimationState("block3");
						} else {
							this.setAnimationState("block4");
						}
					}
				}
				else {
					if (!this.level().isClientSide()) {
						this.setAttackTick(18);
						this.setAttackUse(2);
						this.setAnimTick(10);
						this.setAnimationState("block5");
					}
				}
				if (this.getOffhandItem().isEmpty() &&
						this.getMainHandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock()) {
					if (this.getMainHandItem().getItem() instanceof ItemSpecialEffect specialEffect) {
						specialEffect.blockUse(this, damageSource.getEntity(), damageSource);
					}
				}
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
				}
				if (this.isNeedShock() && !(this.getTarget() == null && list.isEmpty())) {
					if (!this.level().isClientSide()) {
						this.setShockCooldown(0);
						this.setShockUseTick(50);
						this.setNeedShock(false);
						this.setShockTick(0);
						return false;
					}
				}
				return false;
			}
		}
		if (!this.level().isClientSide()) {
			this.setShockCooldown(this.getShockCooldown() + (trueDamage / 2) + 0.001f);
		}
		return super.hurt(damageSource, trueDamage);
	}
	public float hurtAbout(float amount) {
		//伤害衰减
		if (amount < 0.5f) return 0;
		if (amount < this.getMaxHealth() / 45) return 0.5f;
		if (amount < this.getMaxHealth() / 18) return this.getMaxHealth() / 45;
		if (amount < this.getMaxHealth() / 8) return this.getMaxHealth() / 18;
		if (amount < this.getMaxHealth() / 5) return this.getMaxHealth() / 8;
		if (amount < this.getMaxHealth() / 3) return this.getMaxHealth() / 5;
		if (amount < this.getMaxHealth() / 2) return this.getMaxHealth() / 3;
		if (amount < this.getMaxHealth() / 1.5f) return this.getMaxHealth() / 2;
		return Math.max(amount * 0.9f, this.getMaxHealth() / 1.5f);
	}

	public boolean isDamageSourceBlocks(DamageSource damageSource) {
		Vec3 object;
		Entity entity = damageSource.getDirectEntity();
		boolean bl = entity instanceof AbstractArrow && ((AbstractArrow) (AbstractArrow) entity).getPierceLevel() > 0;
		if (!damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && !bl && (object = damageSource.getSourcePosition()) != null) {
			Vec3 vec3 = this.calculateViewVector(0.0f, this.getYHeadRot());
			Vec3 vec32 = object.vectorTo(this.position());
			vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
			return vec32.dot(vec3) < 0.0;
		}
		return false;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		//恢复
		if (entity instanceof LivingEntity livingEntity) {
			this.heal(Math.max(1, livingEntity.getMaxHealth() / 10));
		}
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
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_NINE_RINGED_DAO.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(5);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
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
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 20; ++i) {
				serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0, 0, 0);
			}
		}
		this.idleBaseAnimationState.stop();
		this.idle1AnimationState.stop();
		this.idle2AnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (this.level() instanceof ServerLevel serverLevel) {
				ArmorStand armorStand = EntityType.ARMOR_STAND.spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
				if (armorStand != null) {
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(armorStand.getStringUUID(), teams);
					}
					armorStand.setXRot(this.getXRot());
					armorStand.setYRot(this.getYRot());
					armorStand.setShowArms(true);
					armorStand.setHealth(1000);
					armorStand.setNoBasePlate(true);
					armorStand.setLeftArmPose(new Rotations(-90,-45,0));
					armorStand.setRightArmPose(new Rotations(-45,45,0));
					armorStand.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD));
					armorStand.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST));
					armorStand.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS));
					armorStand.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET));
					armorStand.setItemSlot(EquipmentSlot.MAINHAND, this.getItemBySlot(EquipmentSlot.MAINHAND));
					armorStand.setItemSlot(EquipmentSlot.OFFHAND, this.getItemBySlot(EquipmentSlot.OFFHAND));
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, armorStand.getRandomX(0.5), armorStand.getRandomY(), armorStand.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}
