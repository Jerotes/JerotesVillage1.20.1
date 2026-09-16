package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.control.NoRotationControl;
import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import com.jerotes.jerotesvillage.entity.Animal.WildernessWolfEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.ZombieKeeperEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.Other;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OminousBannerProjectionEntity extends Raider implements OminouseBannerRaidForceEntity, FactionEntity, BossEntity, JerotesEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(OminousBannerProjectionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(OminousBannerProjectionEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LAST_ROUND = SynchedEntityData.defineId(OminousBannerProjectionEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SUPPLEMENT = SynchedEntityData.defineId(OminousBannerProjectionEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(OminousBannerProjectionEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState roundAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, false);

	public OminousBannerProjectionEntity(EntityType entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.1f);
		xpReward = 300;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.OminousBannerProjectionMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.OminousBannerProjectionMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.OminousBannerProjectionArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.OminousBannerProjectionMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.OminousBannerProjectionAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.OminousBannerProjectionKnockbackResistance);
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
			return(float) OtherMainConfig.OminousBannerProjectionMagicAttackPercentage;
		}
		return (float) OtherMainConfig.OminousBannerProjectionAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.OminousBannerProjectionDamageCap;
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
		return (float)OtherMainConfig.OminousBannerProjectionDamageCooldownTick * base;
	}
	@Override
	public String getFirstFactionTypeName() {
		return "ominous_banner_raid_force";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("raider");
		list.add("illager");
		return list;
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
		if (this.isLastRound()) {
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
		else if (this.isSupplement()) {
			this.bossEvent.setProgress((float) this.supplementTick);
		}
		else {
			this.bossEvent.setProgress(Mth.clamp(this.getHealthOfLivingRaider() / this.summonMaxHealth, 0.0f, 1.0f));
		}
	}

	public float getHealthOfLivingRaider() {
		float f = (float) (this.summonAmount / this.summonAmountMax);
		List<Mob> listTeam = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listTeam.removeIf(entity -> entity instanceof OminousBannerProjectionEntity || !EntityFactionFind.isRaider(entity) || !(entity instanceof NeutralMob || entity instanceof Enemy) || entity instanceof WildernessWolfEntity);
		listTeam.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		for (Mob raider : listTeam) {
			f += raider.getHealth();
		}
		return f;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (OminousBannerProjectionEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}


	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 600;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_HURT;
	}
	@Override
	public SoundEvent getCelebrateSound() {
		return JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_CHEER;
	}
	@Override
	public void applyRaidBuffs(int p_37844_, boolean p_37845_) {
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_MUSIC;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
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
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(new Vec3(0, vec3.y, 0));
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new NoRotationControl(this);
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public boolean canBeSeenAsEnemy() {
		if (!this.isLastRound()) {
			return false;
		}
		return super.canBeSeenAsEnemy();
	}
	@Override
	public boolean canChangeDimensions() {
		return super.canChangeDimensions() && this.isLastRound();
	}

	//波次
	private int round = 1;
	private int maxRound = OtherMainConfig.OminousBannerProjectionMaxRound;
	//是否最后一波
	public boolean isLastRound() {
		return this.getEntityData().get(IS_LAST_ROUND);
	}
	public void setLastRound(boolean bl) {
		this.getEntityData().set(IS_LAST_ROUND, bl);
		this.noPhysics = false;
		if (bl) {
			this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}
	//是否间隔
	public boolean isSupplement() {
		return this.getEntityData().get(IS_SUPPLEMENT);
	}
	public void setSupplement(boolean bl) {
		this.getEntityData().set(IS_SUPPLEMENT, bl);
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
	//恢复时间
	public double supplementTick;
	//波次生物数量计算
	public double summonAmountMax = OtherMainConfig.OminousBannerProjectionRoundTimeMin * 20;
	public double summonAmountStopMax = OtherMainConfig.OminousBannerProjectionRoundTimeMax * 20;
	public double summonAmount = this.summonAmountMax;
	//此波次最大生命值
	public float summonMaxHealth = 1;
	//点数
	public double points = OtherMainConfig.OminousBannerProjectionPointsBase;
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
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
		if (Objects.equals(animation, "round")){
			return 1;
		}
		else if (Objects.equals(animation, "dead")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.roundAnimationState);
		list.add(this.deadAnimationState);
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
		compoundTag.putFloat("Faces", this.faces);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("Round", this.round);
		compoundTag.putInt("MaxRound", this.maxRound);
		compoundTag.putBoolean("IsLastRound", this.isLastRound());
		compoundTag.putBoolean("IsSupplement", this.isSupplement());
		compoundTag.putDouble("SupplementTick", this.supplementTick);
		compoundTag.putDouble("SummonAmount", this.summonAmount);
		compoundTag.putDouble("SummonAmountMax", this.summonAmountMax);
		compoundTag.putDouble("SummonAmountStopMax", this.summonAmountStopMax);
		compoundTag.putFloat("SummonMaxHealth", this.summonMaxHealth);
		compoundTag.putDouble("Points", this.points);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.faces = compoundTag.getFloat("Faces");
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.round = compoundTag.getInt("Round");
		this.maxRound = compoundTag.getInt("MaxRound");
		if (compoundTag.contains("IsLastRound")) {
			this.setLastRound(compoundTag.getBoolean("IsLastRound"));
		}
		if (compoundTag.contains("IsSupplement")) {
			this.setSupplement(compoundTag.getBoolean("IsSupplement"));
		}
		this.supplementTick = compoundTag.getDouble("SupplementTick");
		this.summonAmount = compoundTag.getDouble("SummonAmount");
		this.summonAmountMax = compoundTag.getDouble("SummonAmountMax");
		this.summonAmountStopMax = compoundTag.getDouble("SummonAmountStopMax");
		this.summonMaxHealth = compoundTag.getFloat("SummonMaxHealth");
		this.points = compoundTag.getDouble("Points");
		this.bossEvent.setId(this.getUUID());
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(IS_LAST_ROUND, false);
		this.getEntityData().define(IS_SUPPLEMENT, true);
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
						this.roundAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.roundAnimationState);
						break;
					case 2:
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
		List<Mob> listTeam = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listTeam.removeIf(entity -> entity instanceof OminousBannerProjectionEntity || !EntityFactionFind.isRaider(entity) || !(entity instanceof NeutralMob || entity instanceof Enemy) || entity instanceof WildernessWolfEntity);
		listTeam.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		//投影袭击
		if (!this.isLastRound() && this.deathTime == 0) {
			int time1 = (int) Math.max(((this.summonAmount) / 20), 0);
			int time2 = (int) ((this.summonAmount + (this.summonAmountStopMax - this.summonAmountMax)) / 20);
			this.bossEvent.setName(Component.translatable("entity.jerotesvillage.ominous_banner_raid").append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.ominous_banner_raid_round", String.valueOf(this.round))).append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.ominous_banner_raid_time", time1, time2)).append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.ominous_banner_raid_count", listTeam.size())));
			//准备累计
			if (this.supplementTick < 1) {
				this.supplementTick += 0.01;
				if (!this.level().isClientSide()) {
					this.setSupplement(true);
				}
				this.summonMaxHealth = 1;
				this.summonAmount = this.summonAmountMax;

				points = OtherMainConfig.OminousBannerProjectionPointsBase * Other.getRaidBossCount(this);
				for (int i = 1; i < this.round; ++i) {
					points = points * OtherMainConfig.OminousBannerProjectionPointsRoundGrowthMultiple * Other.getRaidBossCount(this);
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setSupplement(false);
				}
			}
			//生成数量
			if (!this.isSupplement()) {
				if (this.summonAmount > (this.summonAmountMax - this.summonAmountStopMax)) {
					this.summonAmount -= 1;
					if (this.summonAmount <= 0 && this.getHealthOfLivingRaider() <= 0) {
						this.round += 1;
						this.supplementTick = 0;
						if (!this.level().isClientSide()) {
							this.setSupplement(true);
						}
					}
				}
				//修改
				else {
					this.round += 1;
					this.supplementTick = 0;
					if (!this.level().isClientSide()) {
						this.setSupplement(true);
					}
				}
			}
			if (this.round > this.maxRound) {
				if (!this.level().isClientSide()) {
					this.setLastRound(true);
				}
				this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
				this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
			}
			//生成
			if (this.points > 0 && !this.isSupplement()) {
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_ROUND, this.getSoundSource(), 10.0f, 1.0f);
				}
				if (!this.level().isClientSide()) {
					this.setAnimTick(10);
					this.setAnimationState("round");
				}
				//最后一波
				if (this.round == this.maxRound) {
					for (int i = 0; i < 2; ++i) {
						//玩家数调整
						for (int counts = 0; counts < Other.getRaidBossCount(this); ++counts) {
							if (this.getTarget() != null && this.getTarget() instanceof ServerPlayer player && player.getAdvancements().getOrStartProgress(AdvancementEvent.AdvancementGet(player, "jerotesvillage:bastard_leader")).isDone()) {
								String string = "elite";
								if ((OtherMainConfig.OminousBannerProjectionAXCrazyCanAppear || this.getTarget() != null && this.getTarget().getMainHandItem().is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid_summon_special_elite_item"))))) {
									string = "elite_special";
								}
								List<EntityType<?>> entityTypes = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
										TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/"+ string))
								).stream().toList();
								int size = entityTypes.size();
								if (Main.randomReach(this.getRandom(), 0, size) == 0) {
									summonEntity(JerotesVillageEntityType.ADVENTURER.get(), 1, 16);
									this.points -= 60;
									break;
								}
								else {
									if ((OtherMainConfig.OminousBannerProjectionAXCrazyCanAppear || this.getTarget() != null && this.getTarget().getMainHandItem().is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid_summon_special_elite_item"))))) {
										EntityType<?> randomEntity = entityTypeFind("elite_special");
										if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
											summonEntity(randomEntity, 1, 16);
											this.points -= 60;
											break;
										}
									} else {
										EntityType<?> randomEntity = entityTypeFind("elite");
										if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
											summonEntity(randomEntity, 1, 16);
											this.points -= 60;
											break;
										}
									}
								}
							}
							else if ((OtherMainConfig.OminousBannerProjectionAXCrazyCanAppear || this.getTarget() != null && this.getTarget().getMainHandItem().is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid_summon_special_elite_item"))))) {
								EntityType<?> randomEntity = entityTypeFind("elite_special");
								if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
									summonEntity(randomEntity, 1, 16);
									this.points -= 60;
									break;
								}
							} else {
								EntityType<?> randomEntity = entityTypeFind("elite");
								if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
									summonEntity(randomEntity, 1, 16);
									this.points -= 60;
									break;
								}
							}
						}
					}
				}
				//巨怪
				for (int i = 1; i < maxSpawn(1); ++i) {
					if (this.points > 20 && this.getRandom().nextFloat() > 0.5f) {
						for (int i2 = 1; i2 < 5; ++i2) {
							EntityType<?> randomEntity = entityTypeFind("normal_xx");
							if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
								summonEntity(randomEntity, 1, 16);
								this.points -= 20;
								break;
							}
						}
					}
				}
				//执旗者
				//制图师
				//吹号者
				//传送师
				for (int i = 1; i < maxSpawn(1); ++i) {
					if (this.points > 15) {
						for (int i2 = 1; i2 < 5; ++i2) {
							EntityType<?> randomEntity = entityTypeFind("normal_xv");
							if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
								summonEntity(randomEntity, 1, 16);
								this.points -= 15;
								break;
							}
						}
					}
				}
				//苦寒术士
				//吐火者
				//灯术士
				//潜海者
				//野探客
				//紫沙女巫
				//行刑者
				//旋风者
				//养尸人
				//爆破者
				for (int i = 1; i < maxSpawn(3); ++i) {
					if (this.points > 12) {
						for (int i2 = 1; i2 < 5; ++i2) {
							EntityType<?> randomEntity = entityTypeFind("normal_xii");
							if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
								summonEntity(randomEntity, 1, 16);
								this.points -= 12;
								break;
							}
						}
					}
				}
				//探险者
				//掷枪者
				for (int i = 1; i < maxSpawn(5); ++i) {
					if (this.points > 8) {
						for (int i2 = 1; i2 < 5; ++i2) {
							EntityType<?> randomEntity = entityTypeFind("normal_viii");
							if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
								summonEntity(randomEntity, 1, 16);
								this.points -= 8;
								break;
							}
						}
					}
				}
				//背叛者
				for (int i = 1; i < maxSpawn(20); ++i) {
					if (this.points > 0) {
						for (int i2 = 1; i2 < 5; ++i2) {
							EntityType<?> randomEntity = entityTypeFind("normal_i");
							if (BiomeCanUse(randomEntity) && randomEntity != EntityType.ARROW) {
								summonEntity(randomEntity, 1, 16);
								this.points -= 1;
								break;
							}
						}
					}
				}
				if (this.round >= this.maxRound) {
					if (!this.level().isClientSide()) {
						this.setLastRound(true);
					}
					this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
					this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
				}
			}
		}

		this.setJumping(false);
		if (!this.level().isClientSide) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
	}

	//生成类型
	public EntityType entityTypeFind(String string) {
		List<EntityType<?>> entityTypes = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
				TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/"+ string))
		).stream().toList();
		if (!entityTypes.isEmpty()) {
			return entityTypes.get(level().random.nextInt(entityTypes.size()));
		}
		return EntityType.ARROW;
	}

	//群系
	public boolean BiomeCanUse(EntityType entityType) {
		if (OtherMainConfig.OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome) {
			return true;
		}

		Holder<Biome> biome = this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/i"))
		) && entityType.is(
						TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/i_biome"))
				)) {
			return false;
		}
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/ii"))
		) && entityType.is(
				TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/ii_biome"))
		)) {
			return false;
		}
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/iii"))
		) && entityType.is(
				TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/iii_biome"))
		)) {
			return false;
		}
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/iv"))
		) && entityType.is(
				TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/iv_biome"))
		)) {
			return false;
		}
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/v"))
		) && entityType.is(
				TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/v_biome"))
		)) {
			return false;
		}
		if (!biome.is(
				TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/vi"))
		) && entityType.is(
				TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(JerotesVillage.MODID, "ominous_banner_raid/vi_biome"))
		)) {
			return false;
		}
		return true;
	}

	//生成上限
	public double maxSpawn(double n) {
		for (int i = 1; i < this.round; ++i) {
			n = n * OtherMainConfig.OminousBannerProjectionPointsRoundGrowthMultiple * 0.8f * Other.getRaidBossCount(this);
		}
		return n;
	}

	public void summonEntity(EntityType entityType, int count, int summonDistance) {
		if (this.level() instanceof ServerLevel serverLevel) {
			if (entityType == null) {
				return;
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(this, summonDistance);
				if (summonPos.getY() < this.getY()) {
					summonPos = this.getOnPos().above();
				}
				Entity entity = entityType.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				PlayerTeam teams = (PlayerTeam) this.getTeam();
				if (entity != null) {
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(entity.getStringUUID(), teams);
					}
					if (entity instanceof ZombieKeeperEntity zombieKeeperEntity) {
						zombieKeeperEntity.summonZombie();
					}
					if (entity instanceof LivingEntity living) {
						if (entity instanceof CanBeIllagerFactionEntity canBeIllagerFactionEntity) {
							canBeIllagerFactionEntity.setIllagerFaction(true);
						}
						if (entity.getType() == JerotesVillageEntityType.GIANT_MONSTER.get() && entity instanceof GiantMonsterEntity giantMonsterEntity) {
							giantMonsterEntity.setIllagerFaction(true);
							//鞍
							giantMonsterEntity.equipSaddle(SoundSource.NEUTRAL);
							//盔甲
							giantMonsterEntity.equipArmor(null, new ItemStack(JerotesVillageItems.OMINOUS_GIANT_BEAST_ARMOR.get()));
						}
					}
					if (entity instanceof Mob mob) {
						if (this.getTarget() != null) {
							mob.setTarget(this.getTarget());
						}
						if (!mob.isInvisible()) {
							for (int i2 = 0; i2 < 32; ++i2) {
								serverLevel.sendParticles(ParticleTypes.PORTAL, mob.getRandomX(0.5), mob.getRandomY(), mob.getRandomZ(0.5), 0, mob.getRandom().nextGaussian(), 0.0, mob.getRandom().nextGaussian(), 0);
							}
						}
						this.summonMaxHealth += mob.getMaxHealth();
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.random.nextInt(240) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!this.isLastRound()) {
			if (amount < 8000) {
				if (damageSource.getEntity() instanceof Player) {
					this.summonAmount -= amount;
				}
				return false;
			}
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}

	public float faces;
	@Override
	public void stopRiding() {
		super.stopRiding();
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		float faces = serverLevelAccessor.getRandom().nextFloat();
		this.faces = faces;
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
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
			this.remove(RemovalReason.DISCARDED);
		}
	}
}




