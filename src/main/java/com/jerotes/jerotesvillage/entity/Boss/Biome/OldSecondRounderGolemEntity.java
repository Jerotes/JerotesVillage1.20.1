package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.google.common.collect.ImmutableList;
import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMachineEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.goal.SecondRounderGolemMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.SecondRounderGolemRangedAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.navigation.LineNavigation;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class OldSecondRounderGolemEntity extends MerorMachineEntity implements RangedAttackMob, BossEntity, LoreUndyingEntity, BreakShieldEntity, FireAbsorptionEntity, SpellUseEntity {
	private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("00a5ab13-dd26-489d-ac07-8f85efa400ee");
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("27bbfc47-7ced-43ee-88c7-4bca3ed4e02d");
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("fecce94e-9cb6-478e-92ef-a66823b14e53");
	private static final EntityDataAccessor<Float> BREATH_TICK = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ANGRY_TICK = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState standAnimationState = new AnimationState();
	public AnimationState standStartAnimationState = new AnimationState();
	public AnimationState standStopAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState blockAnimationState = new AnimationState();
	public AnimationState breathAnimationState = new AnimationState();
	public AnimationState swordAnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState attack6AnimationState = new AnimationState();
	public AnimationState angryAnimationState = new AnimationState();
	public AnimationState angrySword1AnimationState = new AnimationState();
	public AnimationState angrySword2AnimationState = new AnimationState();
	public AnimationState angrySword3AnimationState = new AnimationState();
	public AnimationState swordAttackAnimationState = new AnimationState();
	public AnimationState helpAnimationState = new AnimationState();
	public AnimationState cloakAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PINK, false);
	private static final EntityDataAccessor<Boolean> SWORD_USE = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CLOAK = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STAND = SynchedEntityData.defineId(OldSecondRounderGolemEntity.class, EntityDataSerializers.BOOLEAN);
	protected MoveControl moveControls = new GiantMoveControl(this);

	public OldSecondRounderGolemEntity(EntityType<? extends OldSecondRounderGolemEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(3.1f);
		this.xpReward = 1000;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(OtherMainConfig.SecondRounderGolemMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.SecondRounderGolemMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.SecondRounderGolemArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.SecondRounderGolemMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.SecondRounderGolemAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.SecondRounderGolemKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId()) || OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.SecondRounderGolemMagicAttackPercentage;
		}
		return (float) OtherMainConfig.SecondRounderGolemAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.SecondRounderGolemDamageCap;
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
		return (float)OtherMainConfig.SecondRounderGolemDamageCooldownTick * base;
	}

	public boolean shouldLegend() {
		return (MainConfig.HasPercentageDamage.contains(this.getEncodeId()) || OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId())) &&
				(MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()))  &&
				(MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId())) ;
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
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 436);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 36);
		builder = builder.add(Attributes.ARMOR, 20.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SecondRounderGolemMeleeAttackGoal(OldSecondRounderGolemEntity.this, 1.25, true));
		this.goalSelector.addGoal(1, new SecondRounderGolemRangedAttackGoal(this, 1.25, 40, 20.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (!this.specialAction() && this.attackTick <= 0) {
			//技能-毒性吐息
			this.setBreathTick(0);
			if (this.getHealth() / this.getMaxHealth() > 0.25f) {
				this.setAngryTick((float) (this.getAngryTick() - OtherMainConfig.SecondRounderGolemIntenseBombingCooldown / 5 * 20));
			}
			this.level().broadcastEntityEvent(this, (byte) 14);
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			//法术列表-魔法飞弹
			SpellList.PoisonBreath(spellLevel, this, livingEntity).spellUse(1);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.standTick > 0 && !this.isInSittingPose()) {
			return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_AMBIENT;
		}
		return null;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 3200;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_WALK, 0.15f, 1.0f);
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
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
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 4.8f;
	}
	public Crackiness getCrackiness() {
		return Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
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
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_MUSIC;
	}
	@Override
	public int getShieldBreakStrength() {
		return 2400;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		if (this.isInWater()) {
			return new AmphibiousPathNavigation(this, level);
		}
		return new LineNavigation(this, level);
	}
	@Override
	public double FireAbsorptionPercentage() {
		return OtherMainConfig.SecondRounderGolemFireAbsorptionPercentage;
	}

	public int spellLevel = OtherMainConfig.SecondRounderGolemSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int standTick = -10;
	private int attackTick;
	private int swordTick = -10;
	private int swordAttackTick;
	private double blockDestroyTick;
	private double healTick;
	public double cloakTick;
	private int sitTick = 0;
	private double helpTick = 0;
	public float getBreathTick() {
		return this.entityData.get(BREATH_TICK);
	}
	public void setBreathTick(float f) {
		this.entityData.set(BREATH_TICK, Mth.clamp(f, 0.0F, (float) OtherMainConfig.SecondRounderGolemBreathCooldown * 20));
	}
	public float getAngryTick() {
		return this.entityData.get(ANGRY_TICK);
	}
	public void setAngryTick(float f) {
		this.entityData.set(ANGRY_TICK, Mth.clamp(f, 0.0F, (float) OtherMainConfig.SecondRounderGolemIntenseBombingCooldown * 20));
	}
	public boolean isUseSword() {
		return this.getEntityData().get(SWORD_USE) && deathTime == 0;
	}
	public void setUseSword(boolean bl) {
		this.getEntityData().set(SWORD_USE, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			double d = OtherMainConfig.SecondRounderGolemMeleeDamageUseSword - OtherMainConfig.SecondRounderGolemMeleeDamage;
			double d2 = OtherMainConfig.SecondRounderGolemArmorUseSword - OtherMainConfig.SecondRounderGolemArmor;
			double d3 = OtherMainConfig.SecondRounderGolemMovementSpeedUseSword - OtherMainConfig.SecondRounderGolemMovementSpeed;
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Use sword attack damage", d, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Use sword armor", d2, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Use sword movement speed", d3, AttributeModifier.Operation.ADDITION));
		}
	}
	public boolean specialAction() {
		return (this.standTick < 0 || this.standTick > 780) && !this.isTame() || this.getBreathTick() < 20 && this.getBreathTick() > 0 || this.swordTick > 0 || this.swordAttackTick > 0 || this.cloakTick > OtherMainConfig.SecondRounderGolemPoisonFireCloakTime * 20 - 20;
	}
	public boolean isCloak() {
		return this.getEntityData().get(CLOAK);
	}
	public void setCloak(boolean bl) {
		this.getEntityData().set(CLOAK, bl);
	}
	public boolean isStand() {
		return this.getEntityData().get(STAND);
	}
	public void setStand(boolean bl) {
		this.getEntityData().set(STAND, bl);
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
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
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("StandTick", this.standTick);
		compoundTag.putBoolean("IsUseSword", this.isUseSword());
		compoundTag.putBoolean("IsCloak", this.isCloak());
		compoundTag.putBoolean("IsStand", this.isStand());
		compoundTag.putInt("AttackTick", this.attackTick);
		compoundTag.putFloat("BreathTick", this.getBreathTick());
		compoundTag.putFloat("AngryTick", this.getAngryTick());
		compoundTag.putInt("SwordTick", this.swordTick);
		compoundTag.putInt("SwordAttackTick", this.swordAttackTick);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putDouble("HealTick", this.healTick);
		compoundTag.putDouble("CloakTick", this.cloakTick);
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putDouble("HelpTick", this.helpTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.standTick = compoundTag.getInt("StandTick");
		if (compoundTag.contains("IsUseSword")) {
			this.setUseSword(compoundTag.getBoolean("IsUseSword"));
		}
		if (compoundTag.contains("IsCloak")) {
			this.setCloak(compoundTag.getBoolean("IsCloak"));
		}
		if (compoundTag.contains("IsStand")) {
			this.setStand(compoundTag.getBoolean("IsStand"));
		}
		this.attackTick = compoundTag.getInt("AttackTick");
		if (compoundTag.contains("BreathTick")) {
			this.setBreathTick(compoundTag.getFloat("BreathTick"));
		}
		if (compoundTag.contains("AngryTick")) {
			this.setAngryTick(compoundTag.getFloat("AngryTick"));
		}
		this.swordTick = compoundTag.getInt("SwordTick");
		this.swordAttackTick = compoundTag.getInt("SwordAttackTick");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.healTick = compoundTag.getDouble("HealTick");
		this.cloakTick = compoundTag.getDouble("CloakTick");
		this.sitTick = compoundTag.getInt("SitTick");
		this.helpTick = compoundTag.getDouble("HelpTick");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SWORD_USE, false);
		this.getEntityData().define(CLOAK, false);
		this.getEntityData().define(STAND, false);
		this.getEntityData().define(BREATH_TICK, 0f);
		this.getEntityData().define(ANGRY_TICK, 0f);
		this.getEntityData().define(IS_LEGEND, shouldLegend());
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
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

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.isInWater()) {
			this.hasImpulse = true;
		}
		if (this.isAlive() && (!this.isTame() && this.standTick > 0) || (this.isTame() && !this.isInSittingPose())) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		else {
			this.idleAnimationState.stop();
		}
		if (this.deathTime == 0) {
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
			} else {
				//降低计时
				if (this.standTick >= 0) {
					this.standTick -= 1;
				}
				//休息恢复
				if (this.standTick < 0) {
					if (this.random.nextInt(100) == 1 && this.deathTime == 0) {
						this.heal(5.0f);
					}
				}
				//战斗状态
				if (this.isAggressive() || this.getTarget() != null || this.getLastHurtByMob() != null) {
					//休息取消
					if (this.standTick <= 20) {
						this.standTick = 800;
						if (!this.level().isClientSide()) {
							this.setStand(false);
						}
					}
					else if (this.standTick < 780) {
						this.standTick = 780;
					}
				}
				//开始起身动画
				if (this.standTick == 800) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
					this.standStopAnimationState.start(this.tickCount);
					this.idleAnimationState.stop();
				}
				//停止起身动画
				if (this.standTick == 780) {
					this.standStartAnimationState.stop();
					this.standAnimationState.stop();
					this.standStopAnimationState.stop();
				}
				//开始屈身动画
				if (this.standTick == 20) {
					this.standStopAnimationState.stop();
					this.standAnimationState.stop();
					this.standStartAnimationState.start(this.tickCount);
				}
				//开始休息动画
				if (this.standTick <= 0) {
					this.standStartAnimationState.stop();
					this.standStopAnimationState.stop();
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

		//恢复生命
		if (this.healTick <= 0) {
			if (this.getHealth() / this.getMaxHealth() < 0.2) {
				if (this.random.nextInt(100) == 1 && this.deathTime == 0) {
					this.heal(20.0f);
				}
			} else {
				if (this.random.nextInt(200) == 1 && this.deathTime == 0) {
					this.heal(10.0f);
				}
			}
		}

		//技能-毒性吐息
		if (this.getBreathTick() < 20 * OtherMainConfig.SecondRounderGolemBreathCooldown) {
			this.setBreathTick(this.getBreathTick() + 1);
		}
		//近战攻击
		if (this.attackTick == 12 && this.deathTime == 0) {
			this.trueHurt();
		}
		if (this.attackTick > 0) {
			this.attackTick -= 1;
		}
		//技能-强硬摧毁
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, 60f);
			if (blockDestroy) {
				this.blockDestroyTick = OtherMainConfig.SecondRounderGolemBreakBlockCooldown * 20;
				if (!this.specialAction() && this.attackTick <= 0 && this.isAlive()) {
					this.trueHurt();
					this.level().broadcastEntityEvent(this, (byte) 12);
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_BLOCK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
			}
			if (!this.level().isClientSide) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
		//技能-烈性轰炸
		if (!this.specialAction() && this.attackTick <= 0) {
				if (this.getTarget() != null || this.isAggressive()) {
					if (this.getAngryTick() >= OtherMainConfig.SecondRounderGolemIntenseBombingCooldown * 20) {
						int spellLevel = this.getSpellLevel() - 1;
						if (this.level().getDifficulty() == Difficulty.NORMAL) {
							spellLevel = this.getSpellLevel();
						} else if (this.level().getDifficulty() == Difficulty.HARD) {
							spellLevel = this.getSpellLevel() + 1;
						}
						if (!this.isSilent()) {
							this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_ANGRY, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
						}
						OtherSpellFind.IntenseBombing(this, spellLevel, 16, 4);
						if (!this.level().isClientSide()) {
							this.setAngryTick(0);
						}
						this.level().broadcastEntityEvent(this, (byte) 15);
					}
					else if (this.getAngryTick() < OtherMainConfig.SecondRounderGolemIntenseBombingCooldown * 20) {
						this.setAngryTick(this.getAngryTick() + 1);
					}
				}
				else if (this.getAngryTick() > 0) {
					if (!this.level().isClientSide()) {
						this.setAngryTick(this.getAngryTick() - 1);
					}
				}
		}
		//技能-莫厄巨剑
		if (this.getHealth() < this.getMaxHealth() / 2 && this.swordTick < -1) {
			this.level().broadcastEntityEvent(this, (byte) 13);
			this.swordTick = 15;
		}
		if (this.swordTick > 0) {
			this.swordTick -= 1;
		}
		if (this.swordTick == 0) {
			if (!this.level().isClientSide) {
				this.setUseSword(true);
			}
			this.swordTick = -1;
		}
		if (this.swordAttackTick > 0) {
			this.swordAttackTick -= 1;
		}

		//技能-二轮横扫
		if ((this.swordAttackTick == 5 || this.swordAttackTick == 22) && this.deathTime == 0) {
			this.trueHurt();
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 48; ++i) {
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getRandomX(1.2f), this.getRandomY(), this.getRandomZ(1.2f), 5, 0, 0, 0, 0);
				}
			}
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_ATTACK_2, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}
		if (this.swordAttackTick == 1) {
			OtherSpellFind.SecondRounderSweep(this, this.getAttributeValue(Attributes.ATTACK_DAMAGE), 1);
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_SWORD_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}

		if (!this.specialAction() && this.attackTick <= 0) {
			//技能-毒火斗篷
			if (this.getTarget() != null && this.cloakTick <= 0 && this.getRandom().nextFloat() < (1f / 30f / 20f)) {
				this.cloakTick = OtherMainConfig.SecondRounderGolemPoisonFireCloakTime * 20;
				this.level().broadcastEntityEvent(this, (byte) 17);
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_CLOAK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
			}
			if (this.cloakTick > 0 && this.cloakTick % 5 == 0) {
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox());
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if ((this.distanceToSqr(hurt)) > 128) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FIRE_MAGIC, this);
					hurt.hurt(damageSource, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 4f);
					hurt.setSecondsOnFire(OtherMainConfig.SecondRounderGolemPoisonFireCloakFireTime);
					int spellLevel = OtherMainConfig.SecondRounderGolemPoisonFireCloakPotionLevel - 1;
					if (this.level().getDifficulty() == Difficulty.NORMAL) {
						spellLevel = OtherMainConfig.SecondRounderGolemPoisonFireCloakPotionLevel;
					} else if (this.level().getDifficulty() == Difficulty.HARD) {
						spellLevel = OtherMainConfig.SecondRounderGolemPoisonFireCloakPotionLevel + 1;
					}
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), spellLevel * 20, spellLevel));
					}
				}
			}
			//技能-I-贤者
			if (this.getTarget() != null && this.helpTick <= 0 && this.getRandom().nextFloat() < (1f / 6f / 20f)) {
				this.helpTick = OtherMainConfig.SecondRounderGolemSagesCooldown * 20;
				this.level().broadcastEntityEvent(this, (byte) 18);
				OtherSpellFind.I_Sages(this, this.getSpellLevel() * 3, 2, this.getSpellLevel() * 3, 2, 32);
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_HELP, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
			}
			if (this.helpTick > 0) {
				this.helpTick -= 1;
			}
		}

		if (this.cloakTick > 0) {
			this.cloakTick -= 1;
			if (!this.isCloak()) {
				if (!this.level().isClientSide) {
					this.setCloak(true);
				}
			}
		}
		else if (this.isCloak()) {
			if (!this.level().isClientSide) {
				this.setCloak(false);
			}
		}
		if (this.healTick > 0) {
			this.healTick -= 1;
		}
	}

	@Override
	protected void doPush(Entity entity) {
		if (!this.isNoAi() && !this.isTame() && !this.isAggressive() && entity instanceof LivingEntity livingEntity && this.canAttack(livingEntity) && this.canAttackType(livingEntity.getType()) && !this.isAlliedTo(livingEntity)) {
			this.setTarget((LivingEntity)entity);
		}
		super.doPush(entity);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction() || this.standTick > 780) {
			return false;
		}
		if (this.attackTick <= 0) {
			if (!this.level().isClientSide) {
				if (this.getHealth() / this.getMaxHealth() > (float) 1 / 3) {
					this.setAngryTick(0f);
				}
			}
			//技能-二轮横扫
			if (this.getRandom().nextFloat() > 0.90f && this.isUseSword()) {
				this.swordAttackTick = 35;
				this.level().broadcastEntityEvent(this, (byte) 16);
				return true;
			}
			else {
				//常规攻击
				this.attackTick = 20;
				this.level().broadcastEntityEvent(this, (byte) 4);
				return true;
			}
		}
		return false;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			if (!this.isUseSword()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_ATTACK_1, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			} else {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_ATTACK_2, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}
		int levels = 0;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			levels = 1;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			levels = 2;
		}
		int sword = 1;
		if (isUseSword()) {
			sword = 2;
		}

		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(levels * 2 * sword, levels * 2 * sword, levels * 2 * sword));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 128) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			float damage = 1.0f;
			if (hurt.getType() == JerotesVillageEntityType.VARIANT_ZSIEIN.get()) {
				damage = 5.0f;
			}
			AttackFind.attackBegin(this, hurt);
			boolean bl2;
			float damages = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage;
			//猛击
			if (!(this.isUseSword())) {
				float percentage = (float) OtherMainConfig.SecondRounderGolemAttackPercentage;
				bl2 = AttackFind.attackAfter(this, hurt, 0, 1.0f, true, damages);
			}
			//剑击
			else {
				if (Main.randomReach(this.getRandom(), 1, 4) == 1) {
					float percentage = (float) OtherMainConfig.SecondRounderGolemSwordAttackPercentage;
					bl2 = AttackFind.attackAfter(this, hurt, 0, 1.0f, true, damages);
				}
				//魔法
				else {
					float percentage = (float) OtherMainConfig.SecondRounderGolemSwordAttackPercentage;
					bl2 = AttackFind.attackAfterCustomDamage(this, hurt, this.damageSources().indirectMagic(this, this), 0, 1.0f, true, damages);
				}
				if (bl2) {
					float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + (float) EnchantmentHelper.getKnockbackBonus(this);
					if (f1 > 0.0F) {
						hurt.knockback((f1 * 0.5), Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
						this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
					}
				}
			}
			if (bl2) {
				//方块粒子
				BlockPos blockPos = hurt.getOnPos();
				BlockState blockState = hurt.level().getBlockState(blockPos);
				if (this.level() instanceof ServerLevel _level && !blockState.isAir()) {
					_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), hurt.getX(), hurt.getY(), hurt.getZ(), 15, 0.3, 0, 0.3, 0);
				}
				//莫厄粒子
				double d1 = -Mth.sin(this.getYRot() * 0.017453292f);
				double d2 = Mth.cos(this.getYRot() * 0.017453292f);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 15; ++i) {
						double xs = this.getX() + d1 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - hurt.getX())/2;
						double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - hurt.getY())/2;
						double zs = this.getZ() + d2 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - hurt.getZ())/2;
						serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, xs, ys, zs, 0, d1, 0.0, d2, 0.0);
					}
				}
				//额外击飞
				double d = Math.max(0, 1.0 - hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.04 * d, -(this.getY() - hurt.getY()) * 0.02 * d, -(this.getZ() - hurt.getZ()) * 0.04 * d));
				}
				if (this.random.nextInt(10) < levels * sword) {
					this.level().explode(this, hurt.getX(), hurt.getY(), hurt.getZ(), 0.2f, false, Level.ExplosionInteraction.NONE);
				}
				//盾牌击穿
				if (hurt instanceof Player player) {
					Item item0 = hurt.getItemInHand(InteractionHand.MAIN_HAND).getItem();
					Item item1 = hurt.getOffhandItem().getItem();
					if (item0 instanceof ShieldItem) {
						player.getCooldowns().addCooldown(item0, 2000);
					}
					if (item1 instanceof ShieldItem) {
						player.getCooldowns().addCooldown(item1, 2000);
					}
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.swordTick > 0)
			return true;
		if (damageSource.is(DamageTypeTags.IS_EXPLOSION))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		float n = 0.5f;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			n = 1f;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			n = 1.5f;
		}
		this.healTick = OtherMainConfig.SecondRounderGolemNotAttackedStartHealCooldown * 20;

		this.setAngryTick((float) (this.getAngryTick() + OtherMainConfig.SecondRounderGolemIntenseBombingCooldown / 20 * 20));
		float damages = (float) (amount / (OtherMainConfig.SecondRounderGolemNormalMerorToughnessNonMagical * n));
		if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this)) {
			damages = (float) (amount / (OtherMainConfig.SecondRounderGolemNormalMerorToughnessMagical * n));
		}
		if (this.isUseSword()){
			if (EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
				damages = amount * 0.5f;
		}
		else {
			if (EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
				damages = amount;
		}
		Crackiness crackiness = this.getCrackiness();
		boolean bl = super.hurt(damageSource, damages);
		if (bl && this.getCrackiness() != crackiness) {
			this.playSound(JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_DAMAGE, 1.0f, 1.0f);
		}
		return bl;
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		livingEntity.stopUsingItem();
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
			Item item0 = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
			Item item1 = player.getOffhandItem().getItem();
			if (item0 instanceof ShieldItem) {
				player.getCooldowns().addCooldown(item0, 2000);
			}
			if (item1 instanceof ShieldItem) {
				player.getCooldowns().addCooldown(item1, 2000);
			}
		}
		if (!livingEntity.level().isClientSide) {
			addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
		}
		AttackFind.attackAfterCustomDamage(this, livingEntity, this.damageSources().indirectMagic(this, this), 1.0f, 2.0f, false, 0);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ANESTHETIZED.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ANESTHETIZED_HOLD.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ENSLAVEMENT.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 4) {
			int attackRandom = this.random.nextInt(30);
			if (!specialAction() || this.standTick > 780){
				if (!isUseSword()) {
					if (attackRandom > 27) {
						this.attack1AnimationState.start(this.tickCount);
					} else if (attackRandom < 10) {
						this.attack2AnimationState.start(this.tickCount);
					} else {
						this.attack3AnimationState.start(this.tickCount);
					}
				}
				else {
					if (attackRandom > 20) {
						this.attack4AnimationState.start(this.tickCount);
					} else if (attackRandom < 15) {
						this.attack5AnimationState.start(this.tickCount);
					} else {
						this.attack6AnimationState.start(this.tickCount);
					}
				}
			}
		}
		else if (by == 12) {
			this.blockDestroyTick = OtherMainConfig.SecondRounderGolemBreakBlockCooldown * 20;
			if (!(this.swordTick > 0)) {
				this.blockAnimationState.start(this.tickCount);
			}
		}
		else if (by == 13) {
			this.swordTick = 15;
			this.swordAnimationState.start(this.tickCount);
		}
		else if (by == 14) {
			this.setBreathTick(0);
			if (!(this.swordTick > 0)) {
				this.attack1AnimationState.stop();
				this.attack2AnimationState.stop();
				this.attack3AnimationState.stop();
				this.blockAnimationState.stop();
				this.swordAnimationState.stop();
				this.attack4AnimationState.stop();
				this.attack5AnimationState.stop();
				this.attack6AnimationState.stop();
				this.angryAnimationState.stop();
				this.cloakAnimationState.stop();
				this.helpAnimationState.stop();
				this.breathAnimationState.start(this.tickCount);
			}
		}
		else if (by == 15) {
			if (!(this.swordTick > 0)) {
				this.attack1AnimationState.stop();
				this.attack2AnimationState.stop();
				this.attack3AnimationState.stop();
				this.blockAnimationState.stop();
				this.breathAnimationState.stop();
				this.swordAnimationState.stop();
				this.attack4AnimationState.stop();
				this.attack5AnimationState.stop();
				this.attack6AnimationState.stop();
				this.cloakAnimationState.stop();
				this.helpAnimationState.stop();
				if (!this.isUseSword()) {
					this.angryAnimationState.start(this.tickCount);
				}
				else {
					int attackRandom = this.random.nextInt(30);
					if (attackRandom > 20) {
						this.angrySword1AnimationState.start(this.tickCount);
					} else if (attackRandom < 10) {
						this.angrySword2AnimationState.start(this.tickCount);
					} else {
						this.angrySword3AnimationState.start(this.tickCount);
					}
				}
			}
		}
		else if (by == 16) {
			this.swordAttackTick = 35;
			this.swordAttackAnimationState.start(this.tickCount);
		}
		else if (by == 17) {
			this.cloakTick = OtherMainConfig.SecondRounderGolemPoisonFireCloakTime * 20;
			if (!(this.swordTick > 0)) {
				this.attack1AnimationState.stop();
				this.attack2AnimationState.stop();
				this.attack3AnimationState.stop();
				this.blockAnimationState.stop();
				this.breathAnimationState.stop();
				this.swordAnimationState.stop();
				this.attack4AnimationState.stop();
				this.attack5AnimationState.stop();
				this.attack6AnimationState.stop();
				this.angryAnimationState.stop();
				this.helpAnimationState.stop();
				this.cloakAnimationState.start(this.tickCount);
			}
			if (!this.isInvisible()) {
				this.level().addParticle(JerotesVillageParticleTypes.POISON_FIRE_CLOAK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0.0, 0.0, 0.0);
			}
		}
		else if (by == 18) {
			this.helpTick = OtherMainConfig.SecondRounderGolemSagesCooldown * 20;
			if (!(this.swordTick > 0)) {
				this.attack1AnimationState.stop();
				this.attack2AnimationState.stop();
				this.attack3AnimationState.stop();
				this.blockAnimationState.stop();
				this.breathAnimationState.stop();
				this.swordAnimationState.stop();
				this.attack4AnimationState.stop();
				this.attack5AnimationState.stop();
				this.attack6AnimationState.stop();
				this.angryAnimationState.stop();
				this.swordAttackAnimationState.stop();
				this.cloakAnimationState.stop();
				this.helpAnimationState.start(this.tickCount);
			}
		}
		else if (by == 67) {
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
			this.deadAnimationState.start(this.tickCount);
		}
		this.setBreathTick(0);
		++this.deathTime;
		this.idleAnimationState.stop();
		this.standAnimationState.stop();
		this.standStartAnimationState.stop();
		this.standStopAnimationState.stop();
		this.attack1AnimationState.stop();
		this.attack2AnimationState.stop();
		this.attack3AnimationState.stop();
		this.blockAnimationState.stop();
		this.breathAnimationState.stop();
		this.swordAnimationState.stop();
		this.attack4AnimationState.stop();
		this.attack5AnimationState.stop();
		this.attack6AnimationState.stop();
		this.angryAnimationState.stop();
		this.swordAttackAnimationState.stop();
		this.cloakAnimationState.stop();
		this.helpAnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}

	public static enum Crackiness {
		NONE(1.0F),
		LOW(0.75F),
		MEDIUM(0.5F),
		HIGH(0.25F);

		private static final List<Crackiness> BY_DAMAGE = Stream.of(values()).sorted(Comparator.comparingDouble((crackiness) -> {
			return (double)crackiness.fraction;
		})).collect(ImmutableList.toImmutableList());
		private final float fraction;

		private Crackiness(float f) {
			this.fraction = f;
		}

		public static Crackiness byFraction(float p_28902_) {
			for(Crackiness crackiness : BY_DAMAGE) {
				if (p_28902_ < crackiness.fraction) {
					return crackiness;
				}
			}

			return NONE;
		}
	}
}
