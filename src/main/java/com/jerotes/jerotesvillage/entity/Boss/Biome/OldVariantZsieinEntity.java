package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.LoreUndyingEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ZsieinEntity;
import com.jerotes.jerotesvillage.entity.Part.VariantZsieinPart;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.goal.VariantZsieinHurtByTargetGoal;
import com.jerotes.jerotesvillage.goal.VariantZsieinMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.VariantZsieinNearestAttackablePlayerTargetGoal;
import com.jerotes.jerotesvillage.goal.VariantZsieinNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.navigation.LineNavigation;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.util.OtherEntityAndItemFind;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class OldVariantZsieinEntity extends ZsieinEntity implements RangedAttackMob, BossEntity, BreakShieldEntity, LoreUndyingEntity {
	public static final EntityDataAccessor<Integer> DATA_ZSIEIN_TARGET_ID = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> EXALTED_NORMAL = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> EXALTED_AWAY = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> SHOULD_EXALTED_AWAY = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SONIC = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(OldVariantZsieinEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState awayAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.WHITE, true, true);
	private VariantZsieinPart body;

	public OldVariantZsieinEntity(EntityType<? extends OldVariantZsieinEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(2.1f);
		this.xpReward = 1800;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		resetParts();
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
	}
//	//限伤
//	public boolean hasPercentageDamage() {
//		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
//			|| MainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
//	}
//	public float PercentageDamage(DamageSource damageSource) {
//		if (AttackFind.MagicResistance(damageSource)) {
//			return(float) MainConfig.VariantZsieinMagicAttackPercentage;
//		}
//		return (float) MainConfig.VariantZsieinAttackPercentage;
//	}
//	//百分比
//	public boolean hasDamageCap() {
//		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
//	}
//	public float DamageCap(DamageSource damageSource, Entity entity) {
//		return (float) MainConfig.VariantZsieinDamageCap;
//	}
//	//间隔
//	public boolean hasDamageCooldownTick() {
//		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || MainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
//	}
//public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
//	float base = 1.0F;
//	if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
//		base *= 0.5F;
//	}
//	return (float)OtherMainConfig.VariantZsieinDamageCooldownTick * base;
//}

	public boolean isExalted() {
		return true;
	}

	public OldVariantZsieinEntity(EntityType<? extends OldVariantZsieinEntity> entityType, Level level, double x, double y, double z) {
        this(entityType, level);
		this.moveToTrue(x, y, z);
	}

	public void resetParts() {
		removeParts();
		this.body = new VariantZsieinPart(this, 0.0f, 0, 0f, 4.5f, 5.5f, 1.0f);
		if (this.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (teams != null) {
				serverLevel.getScoreboard().addPlayerToTeam(body.getStringUUID(), teams);
			}
		}
		this.body.copyPosition(this);
		this.body.setParent(this);
	}
	public void updateParts() {
		Main.updatePart(body, this);
	}
	private void removeParts() {
		if (body != null) {
			body.remove(RemovalReason.DISCARDED);
			body = null;
		}
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
		this.bossEvent.setProgress(this.asHealth());
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.MAX_HEALTH, 730);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 30);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 35);
		builder = builder.add(Attributes.FOLLOW_RANGE, 2048);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new VariantZsieinMeleeAttackGoal(OldVariantZsieinEntity.this, 1.25, true));
		this.targetSelector.addGoal(1, new VariantZsieinHurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new VariantZsieinNearestAttackableTargetGoal<>(this, Mob.class, true));
		this.targetSelector.addGoal(2, new VariantZsieinNearestAttackablePlayerTargetGoal<>(this, Player.class, true));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.GENERIC_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		super.playStepSound(blockPos, blockState);
	}
	@Override
	protected float getSoundVolume() {
		return 15.0f;
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public boolean isOnFire() {
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
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof VariantZsieinPart) {
			return false;
		}
		return entity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(entity) && this.isAlive();
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 4.5f;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.random.nextInt(5) == 1;
	}
	public AABB getAttackBoundingBox() {
		double reach = 1.2d + WorldEvent.ZsieinAffinityGet(this.level()) / 1000d;
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
		return aabb1.inflate(reach, reach, reach);
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
	public boolean isNoAi() {
		return false;
	}
	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		return true;
	}
	@Override
	public boolean canAttack(LivingEntity livingEntity, TargetingConditions targetingConditions) {
		return targetingConditions.test(this, livingEntity);
	}
	@Override
	public boolean canAttackType(EntityType<?> entityType) {
		return true;
	}
	@Override
	public boolean isCustomNameVisible() {
		if (asHealth() <= 0.5) {
			return true;
		}
		return super.isCustomNameVisible();
	}
	@Override
	public Component getDisplayName() {
		if (asHealth() <= 0.5) {
            if (this.bossEvent != null) {
                this.bossEvent.setName(Component.literal("§kVariant Zsiein" + " ?§r"));
            }
            return Component.literal("§kVariant Zsiein" + " ?§r");
		}
		return super.getDisplayName();
	}
	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}
	@Override
	public boolean canChangeDimensions() {
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
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}
	@Override
	public boolean teleportTo(ServerLevel serverLevel, double d, double d2, double d3, Set<RelativeMovement> set, float f, float f2) {
		return false;
	}
	@Override
	public void teleportTo(double d, double d2, double d3) {
	}
	@Override
	public void moveTo(double d, double d2, double d3, float f, float f2) {
	}
	public void moveToTrue(double d, double d2, double d3) {
		super.teleportTo(d, d2, d3);
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		if (this.isInWater()) {
			return new AmphibiousPathNavigation(this, level);
		}
		return new LineNavigation(this, level);
	}
	protected float asHealth() {
		float maxHealth = this.getExaltedNormal();
		float health = this.getExaltedNormal() - this.getExaltedAway();
		return health / maxHealth;
	}
	public float getHealth() {
		return this.getExaltedNormal() - this.getExaltedAway();
	}
	public float getHealthOld() {
		return super.getHealth();
	}
	@Override
	public boolean isAlive() {
		return !this.shouldExaltedAway();
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	public float strength() {
		return (float) WorldEvent.ZsieinAffinityGet(this.level()) / 20f;
	}

	public float targetHealth = 0;
	public int attackTick = -10;
	private int tranTick;
	private int invulnerableTick = 30;
	private int blockDestroyTick;
	private int sonicTick = 0;
	@Nullable
	private LivingEntity bossZsieinTarget;
	@Nullable
	private UUID bossZsieinTargetUUID;
	@Nullable
	public LivingEntity getBossZsieinTarget() {
		if (!this.level().isClientSide){
			UUID uuid = this.bossZsieinTargetUUID;
			return uuid == null ? null : getLivingEntityByUUID(this.level(), uuid);
		} else {
			int id = this.getDataZsieinTargetId();
			return id <= -1 ? null : this.level().getEntity(this.getDataZsieinTargetId()) instanceof LivingEntity living && living != this ? living : null;
		}
	}
	public static LivingEntity getLivingEntityByUUID(Level level, UUID uuid) {
		return getLivingEntityByUUID(level.getServer(), uuid);
	}
	public static LivingEntity getLivingEntityByUUID(MinecraftServer server, UUID uuid){
		if (uuid != null && server != null) {
			for (ServerLevel world : server.getAllLevels()) {
				Entity entity = world.getEntity(uuid);
				if (entity instanceof LivingEntity livingEntity){
					return livingEntity;
				}
			}
		}
		return null;
	}
	public int getDataZsieinTargetId(){
		return this.entityData.get(DATA_ZSIEIN_TARGET_ID);
	}
	public void setDataZsieinTargetId(int id){
		this.entityData.set(DATA_ZSIEIN_TARGET_ID, id);
	}
	@Override
	public LivingEntity getTarget() {
		return this.getBossZsieinTarget();
	}
	public void setBossZsieinTarget(@Nullable LivingEntity livingEntity) {
		if (livingEntity == this) {
			return;
		}
		this.bossZsieinTarget = livingEntity;
		this.bossZsieinTargetUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	public int getInvulnerableTick() {
		return invulnerableTick;
	}
	public boolean isSonic() {
		return this.getEntityData().get(IS_SONIC);
	}
	public void setSonic(boolean bl) {
		this.getEntityData().set(IS_SONIC, bl);
	}
	public float getExaltedNormal() {
		return this.entityData.get(EXALTED_NORMAL);
	}
	public float getExaltedAway() {
		return this.entityData.get(EXALTED_AWAY);
	}
	public void setExaltedAway(float f) {
		this.entityData.set(EXALTED_AWAY, Mth.clamp(f, 0, this.getExaltedNormal()));
	}
	public void setExaltedNormal(float f) {
		this.entityData.set(EXALTED_NORMAL, f);
	}
	public boolean shouldExaltedAway() {
		return !this.getEntityData().get(SHOULD_EXALTED_AWAY);
	}
	public void setWantAway(boolean bl) {
		this.getEntityData().set(SHOULD_EXALTED_AWAY, !bl);
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
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
		compoundTag.putInt("InvulnerableTick", this.invulnerableTick);
		compoundTag.putInt("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putFloat("ExaltedNormal", this.getExaltedNormal());
		compoundTag.putFloat("ExaltedAway", this.getExaltedAway());
		compoundTag.putBoolean("ShouldExaltedAway", this.shouldExaltedAway());
		compoundTag.putBoolean("IsSonic", this.isSonic());
		compoundTag.putInt("AttackTick", this.attackTick);
		compoundTag.putInt("TranTick", this.tranTick);
		compoundTag.putInt("SonicTick", this.sonicTick);
		if (this.bossZsieinTargetUUID != null) {
			compoundTag.putUUID("BossZsieinTarget", this.bossZsieinTargetUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.invulnerableTick = compoundTag.getInt("InvulnerableTick");
		this.blockDestroyTick = compoundTag.getInt("BlockDestroyTick");
		if (compoundTag.contains("ExaltedNormal")) {
			this.setExaltedNormal(compoundTag.getFloat("ExaltedNormal"));
		}
		if (compoundTag.contains("ExaltedAway")) {
			this.setExaltedAway(compoundTag.getFloat("ExaltedAway"));
		}
		if (compoundTag.contains("ShouldExaltedAway")) {
			this.setWantAway(compoundTag.getBoolean("ShouldExaltedAway"));
		}
		if (compoundTag.contains("IsSonic")) {
			this.setSonic(compoundTag.getBoolean("IsSonic"));
		}
		this.attackTick = compoundTag.getInt("AttackTick");
		this.tranTick = compoundTag.getInt("TranTick");
		this.sonicTick = compoundTag.getInt("SonicTick");
		if (compoundTag.hasUUID("BossZsieinTarget")) {
			this.bossZsieinTargetUUID = compoundTag.getUUID("BossZsieinTarget");
		}
		this.bossEvent.setId(this.getUUID());
		resetParts();
	}
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(EXALTED_AWAY, 0.0F);
		this.getEntityData().define(EXALTED_NORMAL, 730.0F);
		this.getEntityData().define(SHOULD_EXALTED_AWAY, true);
		this.getEntityData().define(IS_SONIC, false);
		this.getEntityData().define(IS_LEGEND, true);
		this.getEntityData().define(DATA_ZSIEIN_TARGET_ID, -1);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (EXALTED_AWAY.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (EXALTED_NORMAL.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (SHOULD_EXALTED_AWAY.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (IS_SONIC.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")))) {
			this.tickCount += 100;
			this.setExaltedAway(this.getExaltedNormal());
			this.setWantAway(true);
			//this.aboutHeart = -2000f;

		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void baseTick() {
		if (this.getBossZsieinTarget() != null && this.getDataZsieinTargetId() == -1) {
			this.setDataZsieinTargetId(this.getBossZsieinTarget().getId());
		}
		//恶行掩盖
		List<PathfinderMob> list = this.level().getEntitiesOfClass(PathfinderMob.class, this.getBoundingBox().inflate(256, 256, 256));
		list.removeIf(pathfinderMob -> pathfinderMob.getTarget() != this);
		list.removeIf(OtherEntityAndItemFind::isZsieinVariantImmune);
		if (!list.isEmpty()) {
			double d = Double.MAX_VALUE;
			for (PathfinderMob mob : list) {
				if (mob == null || mob == this || (this.distanceToSqr(mob)) > d) continue;
				if (mob.getTarget() != this || OtherEntityAndItemFind.isZsieinVariantImmune(mob))
					continue;
				mob.setTarget(mob);
				mob.setLastHurtByMob(mob);
				mob.setAggressive(false);
			}
		}
		//真正死亡
		if (this.shouldExaltedAway()) {
			this.setExaltedAway(this.getExaltedNormal());
		}
		if (this.asHealth() <= 0 && this.tickCount > 100 && this.getExaltedAway() >= getExaltedNormal()) {
			this.setWantAway(true);
		}
		else {
			this.deathTime = 0;
		}
		//坠落
		if (this.getY() < -1000) {
			this.moveToTrue(this.getX(), 1000, this.getZ());
		}
		//高压反应
		float difference = this.getExaltedNormal() - this.getMaxHealth();
		if (difference > 0) {
			this.setExaltedNormal(this.getExaltedNormal() + difference);
			this.BossZsieinHeal(difference);
		}
		//血量挑整
		if (this.asHealth() > 1) {
			this.setExaltedAway(0);
		}
		this.setHealthSelf(getExaltedNormal() - getExaltedAway());
		Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(getExaltedNormal());
		super.baseTick();
	}

	@Override
	public void tick() {
		super.tick();
		updateParts();
		if (this.body == null || this.body.distanceTo(this) > 32) {
			resetParts();
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, Float.MAX_VALUE);
			if (blockDestroy) {
				this.blockDestroyTick = 4;
			}
			if (!this.level().isClientSide) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
		//音乐
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getBossZsieinTarget() instanceof Player player && player.isAlive()) {
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
		this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
		if (this.isInWater()) {
			this.hasImpulse = true;
		}
		if (asHealth() < 0.2 || this.getBossZsieinTarget() == null || !this.isAggressive()) {
			if ((this.random.nextInt(100) == 1 || (this.getBossZsieinTarget() == null || !this.isAggressive() && this.random.nextInt(20) == 1)) && this.deathTime == 0) {
				this.BossZsieinHeal(10.0f + this.strength());
			}
		}
		else{
			if (this.random.nextInt(200) == 1 && this.deathTime == 0) {
				this.BossZsieinHeal(5.0f + this.strength());
			}
		}
		//清除着火
		this.clearFire();
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//泽林免疫
		boolean isImmune = false;
		if (this.getBossZsieinTarget() != null) {
			isImmune = OtherEntityAndItemFind.isZsieinVariantImmune(this.getBossZsieinTarget());
		}
		//技能-牵扯
		if (this.getBossZsieinTarget() != null && !(this.getBossZsieinTarget() instanceof Player) && !(isImmune)) {
			if (this.getBossZsieinTarget().distanceTo(this) > 20 || (!this.getBossZsieinTarget().onGround() && !this.isWithinMeleeAttackRange(this.getBossZsieinTarget()))) {
				this.getBossZsieinTarget().setDeltaMovement( 0, 0, 0);
				this.getBossZsieinTarget().moveTo((this.getX() + this.getBossZsieinTarget().getX())/2, (this.getY() + this.getBossZsieinTarget().getY())/2, (this.getZ() + this.getBossZsieinTarget().getZ())/2);
			}
		}

		//技能-灾害凝视
		if (this.getBossZsieinTarget() != null && !(isImmune)) {
			if (this.getBossZsieinTarget().getHealth() > this.targetHealth && this.targetHealth > 0 && this.getBossZsieinTarget() != null) {
				this.getBossZsieinTarget().setHealth(this.targetHealth);
			}
			this.targetHealth = Objects.requireNonNull(this.getBossZsieinTarget()).getHealth();
		}
		else {
			this.targetHealth = 0;
		}

		//技能-供能转移
		float tranTick = 600 - this.strength() * 4;
		if (this.tranTick < tranTick) {
			this.tranTick += 1;
		}
		if (this.tranTick >= tranTick && this.getBossZsieinTarget() != null) {
			this.BossZsieinHeal(5.0f);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 16; ++i) {
					serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.7f), this.getRandomY(), this.getRandomZ(0.7f), 0, 0, 0.0, 0, 0.0);
				}
			}
		}

		//攻击
		if (this.attackTick > -10 && !this.shouldExaltedAway()) {
			this.attackTick -= 1;
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(1.5));
			for (LivingEntity attack : list) {
				if (attack == null) continue;
				if (!this.level().isClientSide) {
					attack.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2, 0));
				}
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 6; ++i) {
					if (!(this.getBlockStateOn().getBlock() instanceof AirBlock)) {
						serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1f) + ((this.random.nextFloat() * 4.5) * 2 - 4.5), this.getRandomY() + ((this.random.nextFloat() * 3) * 2 - 3), this.getRandomZ(1f) + ((this.random.nextFloat() * 4.5) * 2 - 4.5), 0, 0, 0.0, 0, 0.0);
					}
				}
				for (int i = 0; i < 64; ++i) {
					serverLevel.sendParticles(ParticleTypes.ENCHANT, this.getRandomX(1f) + ((this.random.nextFloat() * 4.5) * 2 - 4.5), this.getRandomY() + ((this.random.nextFloat() * 3) * 2 - 3), this.getRandomZ(1f) + ((this.random.nextFloat() * 4.5) * 2 - 4.5), 0, 0, 0.0, 0, 0.0);
				}
			}
		}
		if (this.attackTick == 0 && !(this.shouldExaltedAway())) {
			this.trueDoHurt();
		}

		//技能-泽林声波
		if (!this.shouldExaltedAway()) {
			if (this.getBossZsieinTarget() != null && this.getBossZsieinTarget().isAlive()) {
				if (this.sonicTick > 0) {
					this.sonicTick -= 1;
				}
				if (this.sonicTick <= 0) {
					if (!this.isWithinMeleeAttackRange(this.getBossZsieinTarget()) && this.random.nextFloat() > 0.98f) {
						this.sonicTick = -10;
					}
				}
			}
			if (this.sonicTick <= -10 && this.sonicTick > -30) {
				this.sonicTick -= 1;
				this.setSonic(true);
			}

			if (this.sonicTick <= -30) {
				if (this.getBossZsieinTarget() != null) {
					OtherSpellFind.ZsieinSonic(this, this.getBossZsieinTarget(), this.strength(), 0, 1, 0, false);
				} else {
					OtherSpellFind.ZsieinSonic(this, this,  this.strength(), 0, 1, 0, true);
				}
				this.sonicTick = 120;
				this.setSonic(false);
			}
		}
		//伤害间隔
		if (this.invulnerableTick < 30 + strength()) {
			this.invulnerableTick += 1;
		}
	}

	private void BossZsieinHeal(float f) {
		if (this.asHealth() >= 1) {
			return;
		}
		this.setExaltedAway(this.getExaltedAway() - f);
	}
	private void BossZsieinDamage(float f) {
		if (this.asHealth() <= 0) {
			return;
		}
		this.setExaltedAway(this.getExaltedAway() + f);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.attackTick <= -10) {
			this.attackTick = 20;
			this.tranTick = 0;
			this.level().broadcastEntityEvent(this, (byte)4);
			return true;
		}
		return false;
	}
	private boolean trueDoHurt() {
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.5, 3, 4.5));
		for (LivingEntity attack : list) {
			if (attack == null) continue;
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (attack instanceof Mob zsiein && OtherEntityFactionFind.isZsiein(zsiein.getType()) && zsiein.getTarget() != this)
				continue;
			if (this.level().getDifficulty() != Difficulty.HARD && creativePlayer(attack))
				continue;
			this.sweepAttacks(attack);
			this.doHurtAll(attack);
		}
		if (!this.isSilent()) {
			this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 10.0f, 1.0f);
		}
		return true;
	}

	private boolean doHurtAll(Entity entity) {
		if (this.level().getDifficulty() != Difficulty.HARD && creativePlayer(entity)) {
			return false;
		}
		float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)entity).getMobType());
		float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + EnchantmentHelper.getKnockbackBonus(this);
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			entity.setSecondsOnFire(i * 4);
		}
		if (entity instanceof LivingEntity livingEntity) {
			float health = livingEntity.getHealth();
			float maxHealth = livingEntity.getMaxHealth();
			//百分比大小
			float percentage;
			percentage = Math.max(this.strength()/2, 5);
			percentage = Math.min(percentage, 100);
			//此次伤害
			float trueDamage = f + (maxHealth * (percentage / 100));
			//攻击
			if ((entity instanceof OldSecondRounderGolemEntity || entity instanceof Player) && !WorldEvent.ZsieinExtremelyInfectedAbove(entity.level())) {
				entity.hurt(this.damageSources().mobAttack(this) , f);
			}
			else {
				entity.hurt(this.damageSources().mobAttack(this) , f * 3);
				//修改血量
				CompoundTag compoundTag = new CompoundTag();
				livingEntity.addAdditionalSaveData(compoundTag);
				if (livingEntity.getHealth() - trueDamage < 0) {
					if (livingEntity.getHealth() > 0) {
						setAllHealthTagsTo(compoundTag, 0);
					}
				} else {
					setAllHealthTagsTo(compoundTag, health - trueDamage);
				}
				livingEntity.readAdditionalSaveData(compoundTag);
			}
			if (livingEntity instanceof OldVariantZsieinEntity variantZsiein) {
				variantZsiein.BossZsieinDamage(f);
			}
			//技能-生命吸取
			this.BossZsieinHeal(trueDamage);
			if (f1 > 0.0F) {
				livingEntity.setDeltaMovement((livingEntity.getX() - this.getX())/2, (livingEntity.getY() - this.getY())/2, (livingEntity.getZ() - this.getZ())/2);
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}
		}
		if (entity instanceof Player player) {
			this.maybeDisableShield(player, player.getUseItem());
		}
		else if (!entity.level().isClientSide()) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_variant_zsiein_discard", Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_variant_zsiein_discard") + 200);
		}
		return true;
	}
	public void setAllHealthTagsTo(CompoundTag tag, float f) {
		for (String key : tag.getAllKeys()) {
			Tag value = tag.get(key);
			if (value instanceof CompoundTag) {
				setAllHealthTagsTo((CompoundTag) value, f);
			}
			else if (key.toLowerCase().equals("Health") || key.toLowerCase().contains("health")) {
				switch (Objects.requireNonNull(value).getId()) {
					case Tag.TAG_FLOAT:
						tag.putFloat(key, f);
						break;
					case Tag.TAG_INT:
						tag.putInt(key, (int) f);
						break;
					case Tag.TAG_DOUBLE:
						tag.putDouble(key, f);
						break;
				}
			}
		}
	}

	private void sweepAttacks(Entity entity) {
		if (entity.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 10; ++i) {
				serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(i * 0.1), entity.getZ(), 0, 0, 0.0, 0, 0.0);
			}
		}
	}

	private void maybeDisableShield(Player player, ItemStack itemStack) {
		if (!itemStack.isEmpty() && itemStack.getItem() instanceof ShieldItem shieldItem) {
			player.getCooldowns().addCooldown(shieldItem, 100);
			this.level().broadcastEntityEvent(player, (byte)30);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!WorldEvent.ZsieinExtremelyInfectedAbove(this.level())) {
			if (this.invulnerableTick < 30 + strength())
				return false;
			if (damageSource.getEntity() == null)
				return false;
			if (!(damageSource.getEntity() instanceof LivingEntity livingEntity) || livingEntity.getHealth() <= 0) {
				return false;
			}
			double distanceDamage = this.distanceToSqr(livingEntity) / 20;
			float damages = (float) (amount / distanceDamage);
			if (damages > amount) {
				damages = amount;
			}
			float damagesTrue = damages / 2.5f;
			if (damagesTrue < 2f) {
				return false;
			}
			float damageMax = (36.5f - Math.min((this.strength() / 3), 34.25f));
			if (damagesTrue > damageMax) {
				damagesTrue = damageMax;
			}
			boolean hurt = super.hurt(damageSource, damagesTrue);
			if (hurt) {
				return this.BossZsieinHurt(damageSource, damagesTrue);
			}
			return hurt;
		}
		return false;
	}

	public boolean BossZsieinHurt(DamageSource damageSource, float damages) {
		if (this.level().isClientSide) {
			return false;
		}
		if (this.invulnerableTick < 30 + strength())
			return false;
		this.BossZsieinDamage(damages);
		if (this.getBossZsieinTarget() == null && damageSource.getEntity() instanceof LivingEntity livingEntity) {
			if (!(this.level().getDifficulty() != Difficulty.HARD && creativePlayer(livingEntity))) {
				if (!this.level().isClientSide()) {
					this.setBossZsieinTarget(livingEntity);
					this.setDataZsieinTargetId(livingEntity.getId());
				}
			}
		}
		this.invulnerableTick = 0;
		return true;
	}

	public boolean creativePlayer(Entity entity) {
		return (entity instanceof Player player && (player.getAbilities().instabuild || player.isSpectator()));
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
	}

	public boolean isAffectedByPotions() {
		return false;
	}
	public boolean addEffect(MobEffectInstance p_182397_, @Nullable Entity p_182398_) {
		return false;
	}
	public boolean canBeAffected(MobEffectInstance p_31495_) {
		return false;
	}
	public Collection<MobEffectInstance> getActiveEffects() {
		return new ArrayList<>();
	}
	public Map<MobEffect, MobEffectInstance> getActiveEffectsMap() {
		return Maps.newHashMap();
	}
	public boolean hasEffect(MobEffect p_21024_) {
		return false;
	}
	@Nullable
	public MobEffectInstance getEffect(MobEffect p_21125_) {
		return null;
	}
	protected void onEffectUpdated(MobEffectInstance p_147192_, boolean p_147193_, @Nullable Entity p_147194_) {
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 4) {
			this.attackTick = 20;
			this.tranTick = 0;
			this.attack1AnimationState.start(this.tickCount);
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setExaltedAway(0);
		this.setExaltedNormal(720);
		this.setWantAway(false);
		this.setSonic(false);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	protected void dropAllDeathLoot(DamageSource damageSource) {
		Entity entity = damageSource.getEntity();
		int n = entity instanceof Player ? EnchantmentHelper.getMobLooting((LivingEntity)entity) : 0;
		if (this.shouldDropLoot() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.dropFromLootTable(damageSource, true);
			this.dropCustomDeathLoot(damageSource, n, true);
		}
		this.dropEquipment();
	}
	@Override
	protected void dropExperience() {
	}
	@Override
	public void tickDeath() {
		if (this.shouldExaltedAway() && this.asHealth() <= 0) {
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
			List<ServerPlayer> nearbyPlayers = level().getEntitiesOfClass(ServerPlayer.class, area);
			if (deathTime <= 0) {
				for (ServerPlayer serverPlayer : nearbyPlayers) {
					serverPlayer.awardStat(Stats.ENTITY_KILLED.get(this.getType()));
					Component subtitle = Component.translatable( "message.jerotesvillage.zsiein_away" ,this.getName().getString());
					serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§kNever Death§r").withStyle(ChatFormatting.BLUE)));
					serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(subtitle));
					serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(10, 60, 10));
				}
				this.awayAnimationState.start(this.tickCount);
			}

			++this.deathTime;
			this.idleAnimationState.stop();
			this.attack1AnimationState.stop();

			//掉落经验

			boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
			int n = 66666;
			if (this.level() instanceof ServerLevel serverLevel) {
				if (this.deathTime % 5 == 0 && bl) {
					float count = n * 0.025f;
					float countOfPlayer = count / nearbyPlayers.size();
					for (ServerPlayer serverPlayer : nearbyPlayers) {
						ExperienceOrb.award(serverLevel, serverPlayer.position().add(0,serverPlayer.getEyeHeight(),0).add((this.getRandom().nextDouble() - 0.5) * 1,(this.getRandom().nextDouble() - 0.5) * 1,(this.getRandom().nextDouble() - 0.5) * 1), Mth.floor(countOfPlayer));
						if (serverPlayer.getHealth() > 3 && (Main.getJerotesPersistentData(serverPlayer).getDouble("jerotesvillage_variant_zsiein_discard") <= 0) && !creativePlayer(serverPlayer)) {
							serverPlayer.hurt(serverPlayer.damageSources().genericKill(), 1);
						}
					}
				}
			}

			//掉落物物品
			if (this.deathTime >= 200) {
				//掉落经验
				if (this.level() instanceof ServerLevel serverLevel) {
					if (bl) {
						ExperienceOrb.award(serverLevel, this.position().add(0,3,0), 1800);
					}
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					if (serverLevel.getGameRules().getBoolean(GameRules.RULE_GLOBAL_SOUND_EVENTS)) {
						for (ServerPlayer serverPlayer : serverLevel.getServer().getPlayerList().getPlayers()) {
							serverPlayer.level().playLocalSound(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), JerotesSoundEvents.TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
						}
					}
				}
				for (int i = 0; i < 280; ++i) {
					double d = this.random.nextGaussian() * 0.02;
					double d2 = this.random.nextGaussian() * 0.02;
					double d3 = this.random.nextGaussian() * 0.02;
					this.level().addAlwaysVisibleParticle(ParticleTypes.PORTAL, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
					this.level().addAlwaysVisibleParticle(ParticleTypes.ASH, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
				}
				if (!this.level().isClientSide() && !this.isRemoved()) {
					this.setRemoved(RemovalReason.CHANGED_DIMENSION);
				}
			}
		}
	}

	public void setHealth(float f) {
	}
	private void setHealthSelf(float f) {
		super.setHealth(f);
	}

	@Override
	public void remove(RemovalReason removalReason) {
		if (this.shouldExaltedAway() && this.asHealth() <= 0) {
			removeParts();
			super.remove(removalReason);
		}
	}

	@Override
	public void onRemovedFromWorld() {
		if (this.shouldExaltedAway() && this.asHealth() <= 0) {
			super.onRemovedFromWorld();
		}
	}

	@Override
	public boolean isDeadOrDying() {
		if (this.shouldExaltedAway() && this.asHealth() <= 0) {
			return super.isDeadOrDying();
		}
		return false;
	}
}
