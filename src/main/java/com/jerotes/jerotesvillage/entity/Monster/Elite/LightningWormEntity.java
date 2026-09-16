package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.LightningAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesLeapAtTargetAndLookGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.LightningWormMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.LightningWormRangedAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
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
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class LightningWormEntity extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, LightningAbsorptionEntity, SpellUseEntity, EliteEntity {
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(LightningWormEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LIGHTNING_TICK = SynchedEntityData.defineId(LightningWormEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CHARGING_TICK = SynchedEntityData.defineId(LightningWormEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(LightningWormEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_10, false);

	public LightningWormEntity(EntityType<? extends LightningWormEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.6f);
		this.setNoGravity(true);
		this.xpReward = 50;
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}
	public boolean isJerotesFlyingMob() {
		return true;
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
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LightningWormMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(1, new LightningWormRangedAttackGoal(this, 1.25, 40, 20.0f));
		this.goalSelector.addGoal(2, new JerotesLeapAtTargetAndLookGoal(this, (float) 0.5));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (LightningWormEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (!this.level().isClientSide()) {
			this.setShootTick(240);
		}
		//法术列表-蠕虫雷暴
		OtherSpellList.WormThunderstorm(this.getSpellLevel(), this, this.getTarget()).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.FLYING_SPEED, 1.0);
		builder = builder.add(Attributes.MAX_HEALTH, 135);
		builder = builder.add(Attributes.ARMOR, 11);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 16);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.LIGHTNING_WORM_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.LIGHTNING_WORM_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.LIGHTNING_WORM_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public boolean isOnFire() {
		return false;
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
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}
	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		this.resetFallDistance();
	}
	@Override
	public double LightningAbsorptionPercentage() {
		return 80;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			final Entity child = getChild();
			if (child == null) {
				LivingEntity partParent = this;
				final int segments = 10;
				for (int i = 0; i < segments; i++) {
					BasePartLivingEntity part = new BasePartLivingEntity(JerotesVillageEntityType.LIGHTNING_WORM_PART.get(), partParent, 1.1F, 180, 0);
					if (this.level() instanceof ServerLevel serverLevel) {
						PlayerTeam teams = (PlayerTeam) this.getTeam();
						if (teams != null) {
							serverLevel.getScoreboard().addPlayerToTeam(part.getStringUUID(), teams);
						}
					}
					part.setParent(partParent);
					part.setBodyIndex(i);
					if (partParent == this) {
						this.setChildId(part.getUUID());
					}
					part.setInitialPartPos(this);
					partParent = part;
					level().addFreshEntity(part);
				}
			}
		}
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
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		this.setNoGravity(true);
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		this.clearFire();
		if (!this.level().isClientSide()) {
			this.setShootTick(Math.max(0, this.getShootTick() - 1));
			this.setLightningTick(Math.max(0, this.getLightningTick() - 1));
			this.setChargingTick(Math.max(0, this.getChargingTick() - 1));
		}
		if (this.level().isClientSide()) {
			this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
		}

		//技能-错乱放电
		if (this.getTarget() != null) {
			if (this.distanceTo(this.getTarget()) <= 32) {
				if (Main.randomReach(this.getRandom(), 1, 20) == 1 && this.getLightningTick() <= 0 && this.getChargingTick() <= 0) {
					if (!this.level().isClientSide()) {
						this.setChargingTick(40);
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.LIGHTNING_WORM_CHARGING, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
					if (this.level().isClientSide()) {
						for (int i = 0; i < 20; ++i) {
							this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getTarget().getRandomX(0.5), this.getTarget().getRandomY(), this.getTarget().getRandomZ(0.5), 0.0, 0.0, 0.0);
						}
					}
				}
			}
			if (this.getChargingTick() == 5) {
				//目标
				if (!this.level().isClientSide()) {
					this.setLightningTick(120);
				}
				//法术列表-错乱放电
				OtherSpellList.ChaoticDischarge(getSpellLevel(), this, this.getTarget()).spellUse();
			}
		}

		//避雷针
		if (this.getRandom().nextInt(200) == 1) {
			if (findLightningTargetAround(this.getOnPos()) != null) {

				LightningBolt entity;
				boolean bl = this.level().getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
				if ((entity = EntityType.LIGHTNING_BOLT.create(this.level())) != null) {
					entity.moveTo(Vec3.atBottomCenterOf(findLightningTargetAround(this.getOnPos())));
					entity.setVisualOnly(bl);
					this.level().addFreshEntity(entity);
				}


			}
		}
	}

	private Optional<BlockPos> findLightningRod(BlockPos blockPos2) {
		if (this.level() instanceof ServerLevel serverLevel) {
			PoiManager poiManager = serverLevel.getPoiManager();
			Optional<BlockPos> optional = poiManager.findClosest(holder -> holder.is(PoiTypes.LIGHTNING_ROD), blockPos -> blockPos.getY() == serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1, blockPos2, 128, PoiManager.Occupancy.ANY);
			return optional.map(blockPos -> blockPos.above(1));
		}
		return Optional.empty();
	}

	protected BlockPos findLightningTargetAround(BlockPos blockPos) {
		if (this.level() instanceof ServerLevel serverLevel) {
			BlockPos blockPos2 = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
			Optional<BlockPos> optional = this.findLightningRod(blockPos2);
			if (optional.isPresent() && blockPos.distToCenterSqr(blockPos2.getCenter()) < 32) {
				return optional.get();
			}
		}
		return null;
	}

	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setShootTick(int n){
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootTick(){
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setLightningTick(int n){
		this.getEntityData().set(LIGHTNING_TICK, n);
	}
	public int getLightningTick(){
		return this.getEntityData().get(LIGHTNING_TICK);
	}
	public void setChargingTick(int n){
		this.getEntityData().set(CHARGING_TICK, n);
	}
	public int getChargingTick(){
		return this.getEntityData().get(CHARGING_TICK);
	}
	@Override
	public void setCustomName(@javax.annotation.Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.getChildId() != null) {
			compoundTag.putUUID("ChildUUID", this.getChildId());
		}
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("LightningTick", this.getLightningTick());
		compoundTag.putInt("ChargingTick", this.getChargingTick());
		compoundTag.putInt("ShootTick", this.getShootTick());
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		if (compoundTag.hasUUID("ChildUUID")) {
			this.setChildId(compoundTag.getUUID("ChildUUID"));
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setLightningTick(compoundTag.getInt("LightningTick"));
		this.setChargingTick(compoundTag.getInt("ChargingTick"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.bossEvent.setId(this.getUUID());
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isLightningDamage(damageSource)
				|| damageSource.is(DamageTypes.ON_FIRE)
				|| damageSource.is(DamageTypes.IN_WALL)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	protected void doPush(Entity entity) {
		if (!(entity instanceof BasePartLivingEntity)) {
			super.doPush(entity);
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(CHILD_UUID, Optional.empty());
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(LIGHTNING_TICK, 0);
		this.getEntityData().define(CHARGING_TICK, 0);
	}

	@Nullable
	public UUID getChildId() {
		return this.entityData.get(CHILD_UUID).orElse(null);
	}

	public void setChildId(@Nullable UUID uniqueId) {
		this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
	}

	public Entity getChild() {
		UUID id = getChildId();
		if (id != null && !this.level().isClientSide) {
			return ((ServerLevel) level()).getEntity(id);
		}
		return null;
	}

	@Override
	public boolean doHurtTarget (Entity entity){
		if (Main.randomReach(this.getRandom(), 1, 3) == 1) {
			AttackFind.attackBegin(this, entity);
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_MELEE, this);
			boolean bl = AttackFind.attackAfterCustomDamage(this, entity, damageSource, 1.0f, 1.0f, false, 0f);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.LIGHTNING_WORM_ATTACK, 1.0f, 1.0f);
				}
			}
			return bl;
		}
		else{
			boolean bl = super.doHurtTarget(entity);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.LIGHTNING_WORM_ATTACK, 1.0f, 1.0f);
				}
			}
			return bl;
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@javax.annotation.Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack, float f) {
		if (itemStack.isEmpty()) {
			return null;
		} else if (this.level().isClientSide) {
			return null;
		} else {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY() + (double)f, this.getZ(), itemStack);
			itementity.setDefaultPickUpDelay();
			if (itementity.getItem().getItem() == JerotesVillageItems.LIGHTNING_WORM_CHITIN.get()) {
				itementity.setGlowingTag(true);
			}
			if (captureDrops() != null) captureDrops().add(itementity);
			else {
				this.level().addFreshEntity(itementity);
			}
			return itementity;
		}
	}

	@Override
	public void tickDeath () {
		if (deathTime <= 0) {
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}
}

