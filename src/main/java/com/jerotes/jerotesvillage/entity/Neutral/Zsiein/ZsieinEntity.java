package com.jerotes.jerotesvillage.entity.Neutral.Zsiein;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMachineEntity;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class ZsieinEntity extends PathfinderMob implements NeutralMob, OwnableEntity, JerotesEntity, FactionEntity {
	private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("9795362f-1129-45a2-b767-392f870c8786");
	private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("99be7f89-39b9-48dc-be3b-4678bc3948ff");
	private static final UUID ATTACK_KNOCKBACK_MODIFIER_UUID = UUID.fromString("571c179d-b456-47cf-bec0-212bf584ae73");
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("753cf7d6-ba0a-4890-9206-5dd0e610d7bc");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("a935133e-e959-4060-817c-01ed0a3bcc35");
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("1065d154-2ee9-4446-978c-bcfee0d58edc");
	private static final UUID FOLLOW_RANGE_MODIFIER_UUID = UUID.fromString("54e2f555-545c-41be-bb8b-747bb42217f1");
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(ZsieinEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(ZsieinEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	protected static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ZsieinEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ZsieinEntity.class, EntityDataSerializers.INT);

	public ZsieinEntity(EntityType<? extends ZsieinEntity> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
		this.reassessTameGoals();
	}


	@Override
	public String getFirstFactionTypeName() {
		return "zsiein";
	}
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HighNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Mob>(this, Mob.class, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<MerorMachineEntity>(this, MerorMachineEntity.class, true, livingEntity -> livingEntity instanceof LivingEntity));

		this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<ZsieinEntity>(this, true));
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && (this.isInFluidType())) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		} else {
			super.travel(vec3);
		}
	}
	public void resetAbout(Level level) {
		if (WorldEvent.ZsieinLatentInfectedAbove(level)) {
			double affinity = WorldEvent.ZsieinAffinityGet(level);
			//移除
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).removeModifier(ATTACK_KNOCKBACK_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(FOLLOW_RANGE_MODIFIER_UUID);
			//基础
			double max_health = Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).getBaseValue();
			double attack_damage = Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).getBaseValue();
			double attack_knockback = Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).getBaseValue();
			double armor = Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).getBaseValue();
			double knockback_resistance = Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).getBaseValue();
			double movement_speed = Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue();
			double follow_range = Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).getBaseValue();
			//修改
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_UUID, "Zsiein max health", 0.001 * max_health * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Zsiein attack damage", 0.001 * attack_damage * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).addTransientModifier(new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER_UUID, "Zsiein attack knockback", 0.001 * attack_knockback * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Zsiein armor", 0.0005 * armor * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Zsiein knockback resistance", 0.001 * knockback_resistance * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Zsiein movement speed", 0.00025 * movement_speed * affinity, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(new AttributeModifier(FOLLOW_RANGE_MODIFIER_UUID, "Zsiein follow range", 0.003 * follow_range * affinity, AttributeModifier.Operation.ADDITION));
		}
		else {
			//移除
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).removeModifier(ATTACK_KNOCKBACK_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(FOLLOW_RANGE_MODIFIER_UUID);
		}
	}
	public AABB getAttackBoundingBox() {
		double reach = 0.5d + WorldEvent.ZsieinAffinityGet(this.level()) / 1000d;
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
	@Nullable
	public Entity changeDimension(ServerLevel serverLevel) {
		this.resetAbout(serverLevel);
		return super.changeDimension(serverLevel);
	}
	@Override
	public boolean canFreeze() {
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
			return false;
		}
		return super.canFreeze();
	}
	@Override
	public boolean isFreezing() {
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
			return false;
		}
		return super.isFreezing();
	}
	@Override
	public boolean isOnFire() {
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
			return false;
		}
		return super.isOnFire();
	}
	@Override
	public boolean isSensitiveToWater() {
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
			return false;
		}
		return super.isSensitiveToWater();
	}

	public void setOwnerUUID(@Nullable UUID uuid) {
		if (this instanceof OldVariantZsieinEntity) {
			return;
		}
		this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uuid));
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		if (this instanceof OldVariantZsieinEntity) {
			return null;
		}
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setTame(boolean bl) {
		if (this instanceof OldVariantZsieinEntity) {
			return;
		}
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
		}
		this.reassessTameGoals();
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
		return 0;
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
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
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.resetAbout(this.level());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	protected void spawnTamingParticles(boolean bl) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!bl) {
			particleoptions = ParticleTypes.SMOKE;
		}

		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;

	@Override
	public void aiStep() {
		super.aiStep();
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
			this.clearFire();
			//清除冻结
			if (this.getTicksFrozen() > 0) {
				this.setTicksFrozen(0);
			}
		}
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
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
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
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
		//
		this.followAiStep();
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.resetAbout(this.level());
		return super.doHurtTarget(entity);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isWitherDamage(damageSource)
				|| damageSource.is(DamageTypes.FALLING_BLOCK)
				|| damageSource.is(DamageTypes.FALLING_ANVIL)
				|| damageSource.is(DamageTypes.FALLING_STALACTITE)
				|| damageSource.is(JerotesDamageTypes.POISON)
				|| AttackFind.isPsychicDamage(damageSource)
				|| damageSource.is(JerotesDamageTypes.NECROTIC)
				|| damageSource.is(JerotesDamageTypes.RADIANT)
				|| AttackFind.isCorrosionDamage(damageSource))
			return true;
		//元素免疫
		if (WorldEvent.ZsieinModerateInfectedAbove(this.level()) && (AttackFind.isFireDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isLightningDamage(damageSource) || AttackFind.isWaterDamage(damageSource))) {
			return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		this.resetAbout(this.level());
		if (damageSource.getEntity() instanceof Mob zsiein && OtherEntityFactionFind.isZsiein(zsiein.getType()) && zsiein.getTarget() != this && this.getTarget() != zsiein)
			return false;

//		if (!this.level().isClientSide) {
//			this.setOrderedToSit(false);
//		}
		//反抗
		if (this.getTarget() == null && damageSource.getEntity() instanceof LivingEntity livingEntity) {
			if (!AttackFind.SameFactionAvoidDamage(this, livingEntity, false)) {
				this.setTarget(livingEntity);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 7) {
			this.spawnTamingParticles(true);
		} else if (by == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}


	@Override
	public void die(DamageSource damageSource) {
		net.minecraft.network.chat.Component deathMessage = this.getCombatTracker().getDeathMessage();
		super.die(damageSource);

		if (this.dead)
			if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
				this.getOwner().sendSystemMessage(deathMessage);
			}
	}
	
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.resetAbout(this.level());
		this.setHealth(this.getMaxHealth());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	private UUID persistentAngerTarget;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}


	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	//跟随
	@Nullable
	public ZsieinEntity leader;
	private int schoolSize = 1;

	public int getMaxSchoolSize() {
		return 256;
	}

	protected boolean canRandomSwim() {
		return !this.isFollower();
	}

	public boolean isFollower() {
		return this.leader != null && this.leader.isAlive();
	}

	public ZsieinEntity startFollowing(ZsieinEntity zsiein) {
		this.leader = zsiein;
		zsiein.addFollower();
		return zsiein;
	}

	public void stopFollowing() {
        if (this.leader != null) {
            this.leader.removeFollower();
        }
        this.leader = null;
	}

	private void addFollower() {
		++this.schoolSize;
	}

	private void removeFollower() {
		--this.schoolSize;
	}

	public boolean canBeFollowed() {
		return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
	}

	public void followAiStep() {
		List<?> list;
		if (this.hasFollowers() && this.level().random.nextInt(200) == 1 && (list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(64.0, 64.0, 64.0))).size() <= 1) {
			this.schoolSize = 1;
		}

		//1-泽林变体
		List<OldVariantZsieinEntity> listVariant = this.level().getEntitiesOfClass(OldVariantZsieinEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		//2-刚背体
		List<RigiderEntity> listRigider = this.level().getEntitiesOfClass(RigiderEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));

		//存在泽林变体
		if (!listVariant.isEmpty()) {
			//如果是跟随者
			if (this.isFollower()) {
				if (!(this.leader instanceof OldVariantZsieinEntity)) {
					for (OldVariantZsieinEntity variantZsiein : listVariant) {
						this.stopFollowing();
						this.leader = variantZsiein;
						variantZsiein.leader = null;
					}
				}
			}
			//如果不是跟随者
			else {
				if (!(this instanceof OldVariantZsieinEntity)) {
					for (OldVariantZsieinEntity variantZsiein : listVariant) {
						this.stopFollowing();
						this.leader = variantZsiein;
						variantZsiein.leader = null;
					}
					if (this.hasFollowers()) {
						this.schoolSize = 1;
					}
				}
			}
		}
		else {
			if (!listRigider.isEmpty()) {
				//如果是跟随者
				if (this.isFollower()) {
					if (!(this.leader instanceof RigiderEntity)) {
						for (RigiderEntity rigider : listRigider) {
							this.stopFollowing();
							this.leader = rigider;
							rigider.leader = null;
						}
					}
				}
				//如果不是跟随者
				else {
					if (!(this instanceof RigiderEntity)) {
						for (RigiderEntity rigider : listRigider) {
							this.stopFollowing();
							this.leader = rigider;
							rigider.leader = null;
						}
						if (this.hasFollowers()) {
							this.schoolSize = 1;
						}
					}
				}
			}
		}
	}

	public boolean hasFollowers() {
		return this.schoolSize > 1;
	}

	public boolean inRangeOfLeader() {
        if (this.leader != null) {
            return this.distanceToSqr(this.leader) <= 64.0;
        }
		return false;
    }

	public void pathToLeader() {
		if (this.isFollower()) {
            if (this.leader != null) {
                this.getNavigation().moveTo(this.leader, 1.0);
            }
        }
	}

	public void addFollowers(Stream<? extends ZsieinEntity> stream) {
		stream.limit(this.getMaxSchoolSize() - this.schoolSize).filter(zsiein -> zsiein != this).forEach(zsiein -> zsiein.startFollowing(this));
	}

	static class ZsieinRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
		private final ZsieinEntity fish;

		public ZsieinRandomStrollGoal(ZsieinEntity zsieinEntity, double d) {
			super(zsieinEntity, d);
			this.fish = zsieinEntity;
		}

		@Override
		public boolean canUse() {
			return this.fish.canRandomSwim() && super.canUse();
		}
	}
}