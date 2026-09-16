package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.ColdAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.SnowyApeEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.FrostYetiMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.FrostYetiRangedAttackGoal;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FrostYetiEntity extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, ColdAbsorptionEntity, SpellUseEntity, EliteEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState throwAnimationState = new AnimationState();
	public AnimationState deterAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("c237172f-7a44-460d-89a4-c9115ee0a48d");
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROW_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DETER_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUNAWAY_TICK = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> SHOULD_DETER = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NEED_RUSH = SynchedEntityData.defineId(FrostYetiEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_10, false);

	public FrostYetiEntity(EntityType<? extends FrostYetiEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.xpReward = 50;
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
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
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new FrostYetiMeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(1, new FrostYetiRangedAttackGoal(this, 1.25, 40, 20.0f));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (FrostYetiEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
			this.setThrowTick(100);
			this.setAnimTick(13);
			this.setAnimationState("throw");
		}
		//法术列表-冰岩投掷
		OtherSpellList.IceRockThrow(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 128);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.FROST_YETI_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.FROST_YETI_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.FROST_YETI_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		if (this.isYetiRush()) {
			this.playSound(JerotesVillageSoundEvents.FROST_YETI_RUSH, 0.15f, 1.0f);
		}
		else{
			super.playStepSound(blockPos, blockState);
		}
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
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 12;
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
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 2.95f;
	}
	@Override
	public double ColdAbsorptionPercentage() {
		return 80;
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
		return aabb1.inflate(0.75d, 0.75d, 0.75d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setThrowTick(int n){
		this.getEntityData().set(THROW_TICK, n);
	}
	public int getThrowTick(){
		return this.getEntityData().get(THROW_TICK);
	}
	public void setDeterTick(int n){
		this.getEntityData().set(DETER_TICK, n);
	}
	public int getDeterTick(){
		return this.getEntityData().get(DETER_TICK);
	}
	public void setJumpTick(int n){
		this.getEntityData().set(JUMP_TICK, n);
	}
	public int getJumpTick(){
		return this.getEntityData().get(JUMP_TICK);
	}
	public void setRunawayTick(int n){
		this.getEntityData().set(RUNAWAY_TICK, n);
	}
	public int getRunawayTick(){
		return this.getEntityData().get(RUNAWAY_TICK);
	}
	public boolean isYetiRush() {
		return this.getEntityData().get(NEED_RUSH) && this.isAlive();
	}
	public void setYetiRush(boolean bl) {
		this.getEntityData().set(NEED_RUSH, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Rush movement speed", 0.1, AttributeModifier.Operation.ADDITION));
			setMaxUpStep(3.1f);
		}
		else{
			setMaxUpStep(1.1f);
		}
	}
	public boolean isShouldDeter() {
		return this.getEntityData().get(SHOULD_DETER);
	}
	public void setShouldDeter(boolean bl) {
		this.getEntityData().set(SHOULD_DETER, bl);
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else if (Objects.equals(animation, "throw")){
			return 4;
		}
		else if (Objects.equals(animation, "deter")){
			return 5;
		}
		else if (Objects.equals(animation, "dead")){
			return 6;
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
		list.add(this.throwAnimationState);
		list.add(this.deterAnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putBoolean("IsYetiRush", this.isYetiRush());
		compoundTag.putInt("ThrowTick", this.getThrowTick());
		compoundTag.putInt("DeterTick", this.getDeterTick());
		compoundTag.putInt("JumpTick", this.getJumpTick());
		compoundTag.putBoolean("ShouldDeter", this.isShouldDeter());;
		compoundTag.putInt("RunAwayTick", this.getRunawayTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setYetiRush(compoundTag.getBoolean("IsYetiRush"));
		this.setThrowTick(compoundTag.getInt("ThrowTick"));
		this.setDeterTick(compoundTag.getInt("DeterTick"));
		this.setJumpTick(compoundTag.getInt("JumpTick"));
		this.setShouldDeter(compoundTag.getBoolean("ShouldDeter"));
		this.setRunawayTick(compoundTag.getInt("RunAwayTick"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(NEED_RUSH, false);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(THROW_TICK, 0);
		this.getEntityData().define(DETER_TICK, 0);
		this.getEntityData().define(JUMP_TICK, 0);
		this.getEntityData().define(RUNAWAY_TICK, 0);
		this.getEntityData().define(SHOULD_DETER, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (NEED_RUSH.equals(entityDataAccessor)) {
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
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.throwAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.throwAnimationState);
						break;
					case 5:
						this.deterAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deterAnimationState);
						break;
					case 6:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (!this.level().isClientSide()) {
			if (this.hasEffect(MobEffects.REGENERATION) && this.getRunawayTick() > 0) {
				if (item == JerotesVillageItems.BITTER_COLD_BELL.get()) {
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					this.spawnAtLocation(JerotesVillageItems.FROST_YETI_HAIR.get(), 1);
					SnowyApeEntity snowyApe = this.convertTo(JerotesVillageEntityType.SNOWY_APE.get(), true);
					net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, snowyApe);
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 32; ++i) {
							double d = this.random.nextGaussian() * 0.02;
							double d2 = this.random.nextGaussian() * 0.02;
							double d3 = this.random.nextGaussian() * 0.02;
							serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0, d, d2, d3, 0);
						}
					}
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
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
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
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 5 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 10) {
			if (this.getTarget() != null && this.getAttackTick() > 10) {
				this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
			}
		}
		if (this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		//逃离
		if (this.getRunawayTick() > 0) {
			this.setRunawayTick(this.getRunawayTick() - 1);
			if (this.onGround()) {
				Vec3 randomShake = new Vec3(this.getRandom().nextFloat() - 0.5F, 0, this.getRandom().nextFloat() - 0.5F).scale(0.1F);
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.7F, 1, 0.7F).add(randomShake));
			}
			Vec3 vec = LandRandomPos.getPosAway(this, 64, 24, this.position());
			if (vec != null) {
				this.getNavigation().moveTo(vec.x, vec.y, vec.z, 1d);
			}
		}
		if (!this.level().isClientSide()) {
			this.setThrowTick((Math.max(0, this.getThrowTick() - 1)));
			this.setDeterTick((Math.max(0, this.getDeterTick() - 1)));
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (!this.horizontalCollision && this.getJumpTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setJumpTick(this.getJumpTick() - 1);
			}
		}
		if (this.horizontalCollision) {
			if (!this.level().isClientSide()) {
				this.setJumpTick(this.getJumpTick() + 1);
			}
		}
		if (this.horizontalCollision && this.getJumpTick() < 40 && this.onGround()) {
			this.setDeltaMovement(0.1, 0.8, 0.1);
			if (!this.level().isClientSide()) {
				this.setJumpTick(0);
			}
		}

		if (this.getDeterTick() >= 360){
			this.attack1AnimationState.stop();
			this.attack2AnimationState.stop();
			this.attack3AnimationState.stop();
			this.getNavigation().stop();
		}

		//技能-雪怪恐吓
		if (this.isAggressive() && this.getRunawayTick() <= 0 && this.getDeterTick() <= 0 && this.isShouldDeter() && this.getTarget() != null && this.getTarget() instanceof LivingEntity) {
			if (!this.level().isClientSide()) {
				this.setShouldDeter(false);
				this.setDeterTick(400);
				this.setAnimTick(40);
				this.setAnimationState("deter");
			}
			OtherSpellFind.YetiDeter(this, this.getTarget(), this.getSpellLevel(), this.getSpellLevel(), 1, this.getSpellLevel() * 6);
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.FROST_YETI_DETER, this.getSoundSource(), 15.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}
		if (!isAggressive() && this.getTarget() == null) {
			if (!this.isShouldDeter()) {
				if (!this.level().isClientSide()) {
					this.setShouldDeter(true);
				}
			}
		}

		if (this.getRunawayTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setYetiRush(true);
			}
			if (this.getTarget() != null) {
				Vec3 vec = LandRandomPos.getPosAway(this, 64, 24, this.getTarget().position());
				if (vec != null) {
					this.getNavigation().moveTo(vec.x, vec.y, vec.z, 2d);
				}
			}
		}
		else if (this.getTarget() != null && this.getDeterTick() < 360) {
			if (this.distanceTo(this.getTarget()) < 24 && !this.horizontalCollision) {
				if (!this.level().isClientSide()) {
					this.setYetiRush(false);
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setYetiRush(true);
				}
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setYetiRush(false);
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (this.getDeterTick() < 360 && this.getRunawayTick() <= 0) {
			if (!this.level().isClientSide()) {
				this.setAttackTick(0);
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 20) {
					this.setAnimTick(12);
					this.setAnimationState("attack1");
				} else if (attackRandom > 10) {
					this.setAnimTick(15);
					this.setAttackTick(15);
					this.setAnimationState("attack2");
					return true;
				} else {
					this.setAnimTick(12);
					this.setAnimationState("attack3");
				}
			}

			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FREEZE_MELEE, this);
			if (Main.randomReach(this.getRandom(), 1, 3) == 1 && !(entity.isInvulnerableTo(damageSource))) {
				if (entity instanceof LivingEntity) {
					entity.setIsInPowderSnow(true);
					if (entity.getTicksFrozen() < 240) {
						entity.setTicksFrozen(240);
					}
				}
				AttackFind.attackBegin(this, entity);
				boolean bl = AttackFind.attackAfterCustomDamage(this, entity, damageSource, 1.0f, 1.0f, false, 0f);
				if (bl) {
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.FROST_YETI_ATTACK, 1.0f, 1.0f);
					}
					if (entity instanceof LivingEntity livingEntity) {
						if (livingEntity.hasEffect(MobEffects.WEAKNESS)) {
							int weak1 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration();
							int weak2 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getAmplifier();
							if (!this.level().isClientSide()) {
								this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), weak1, weak2), this);
							}
						}
					}
					//群攻
					List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(0.5, 0.2, 0.5));
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (!this.hasLineOfSight(hurt)) continue;
						if (hurt instanceof FrostYetiEntity frostYetiEntity && frostYetiEntity.getTarget() != this && this.getTarget() != frostYetiEntity)
							continue;
						AttackFind.attackBegin(this, hurt);
						boolean bl2 = AttackFind.attackAfterCustomDamage(this, entity, damageSource, 1.0f, 1.0f, false, 0f);
						if (bl2) {
							if (entity instanceof LivingEntity livingEntity) {
								if (livingEntity.hasEffect(MobEffects.WEAKNESS)) {
									int weak1 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration();
									int weak2 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getAmplifier();
									if (!this.level().isClientSide()) {
										this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), weak1, weak2), this);
									}
								}
							}
						}
					}
					//横扫效果
					Main.sweepAttack(this);
				}
				return bl;
			}
			else {
				boolean bl = super.doHurtTarget(entity);
				if (bl) {
					if (!this.isSilent()) {
						this.playSound(JerotesVillageSoundEvents.FROST_YETI_ATTACK, 1.0f, 1.0f);
					}
					if (entity instanceof LivingEntity livingEntity) {
						if (livingEntity.hasEffect(MobEffects.WEAKNESS)) {
							int weak1 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration();
							int weak2 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getAmplifier();
							if (!this.level().isClientSide()) {
								this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), weak1, weak2), this);
							}
						}
					}
					//群攻
					List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(0.5, 0.2, 0.5));
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (!this.hasLineOfSight(hurt)) continue;
						if (hurt instanceof FrostYetiEntity frostYetiEntity && frostYetiEntity.getTarget() != this && this.getTarget() != frostYetiEntity)
							continue;
						AttackFind.attackBegin(this, hurt);
						boolean bl2 = AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
						if (bl2) {
							if (entity instanceof LivingEntity livingEntity) {
								if (livingEntity.hasEffect(MobEffects.WEAKNESS)) {
									int weak1 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration();
									int weak2 = Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getAmplifier();
									if (!this.level().isClientSide()) {
										this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), weak1, weak2), this);
									}
								}
							}
						}
					}
					//横扫效果
					Main.sweepAttack(this);
				}
				return bl;
			}
		}
		return false;
	}
	public boolean trueHurt() {
		if (this.getDeterTick() >= 360 || this.getRunawayTick() > 0) {
			return false;
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.FROST_YETI_ATTACK, 2.0f, 1.0f);
		}
		float reach = 0.5f;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FREEZE_MELEE, this);
			boolean bl;
			if (!(hurt.isInvulnerableTo(damageSource))) {
				bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, 1.0f, 1.0f, false, 0f);
			}
			else {
				bl = AttackFind.attackAfter(this, hurt, 1.0f, 1.0f, false, 0f);
			}
			if (bl) {
				if (hurt.hasEffect(MobEffects.WEAKNESS)) {
					int weak1 = Objects.requireNonNull(hurt.getEffect(MobEffects.WEAKNESS)).getDuration();
					int weak2 = Objects.requireNonNull(hurt.getEffect(MobEffects.WEAKNESS)).getAmplifier();
					if (!this.level().isClientSide()) {
						this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), weak1, weak2), this);
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
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isColdDamage(damageSource)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!this.level().isClientSide()) {
			this.setRunawayTick(0);
		}
		if (damageSource.is(DamageTypeTags.IS_PROJECTILE))
			return super.hurt(damageSource, amount/2);
		if (AttackFind.isFireDamage(damageSource))
			return super.hurt(damageSource, amount * 2);
		return super.hurt(damageSource, amount);
	}

	@Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack, float f) {
		if (itemStack.isEmpty()) {
			return null;
		} else if (this.level().isClientSide()) {
			return null;
		} else {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY() + (double)f, this.getZ(), itemStack);
			itementity.setDefaultPickUpDelay();
			if (itementity.getItem().getItem() == JerotesVillageItems.FROST_YETI_HAIR.get()) {
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
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
