package com.jerotes.jerotesvillage.entity.Neutral;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.goal.FollowSerponCombatExecuterGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FlamoarEntity extends PathfinderMob implements NeutralMob, JerotesEntity, FactionEntity, FireAbsorptionEntity, CanBeSerponFactionEntity, EliteEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LADY = SynchedEntityData.defineId(FlamoarEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6, false);

	public FlamoarEntity(EntityType<? extends FlamoarEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 40;
		this.setMaxUpStep(2.1f);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(JerotesVillageItems.FLAMOAR_SCIMITAR.get()));
		this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(JerotesVillageItems.FLAMOAR_SCIMITAR.get()));
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.1f;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 0.1f;
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}


	@Override
	public String getFirstFactionTypeName() {
		if (this.isNotSerpon()) {
			return "";
		}
		return "serpon_combat_team";
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
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.FLYING_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 80);
		builder = builder.add(Attributes.ARMOR, 18);
		builder = builder.add(Attributes.ARMOR_TOUGHNESS, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(FlamoarEntity.this, 1.2, true));
		this.goalSelector.addGoal(2, new FollowSerponCombatExecuterGoal(this, 1.3f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.6));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, FlamoarEntity.class).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<FlamoarEntity>(this, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.FLAMOAR_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.FLAMOAR_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.FLAMOAR_DEATH;
	}
	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}
	@Override
	public void move(MoverType moverType, Vec3 vec3) {
		super.move(moverType, vec3);
		this.checkInsideBlocks();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean isOnFire() {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 2.9f;
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isSensitiveToWater() {
		return true;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public double FireAbsorptionPercentage() {
		return 60;
	}
	@Override
	public boolean isNotSerpon() {
		return false;
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public int getAttackAnim() {
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAttackAnim(int n) {
		this.getEntityData().set(ATTACK_ANIM, n);
	}
	public int getAttackTick() {
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setAttackTick(int n) {
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public boolean isLady() {
		return this.getEntityData().get(IS_LADY);
	}
	public void setLady(boolean bl) {
		this.getEntityData().set(IS_LADY, bl);
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
		else if (Objects.equals(animation, "attack4")){
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
		list.add(this.attack4AnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putBoolean("IsLady", this.isLady());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setLady(compoundTag.getBoolean("IsLady"));
		this.readPersistentAngerSaveData((Level)this.level(), compoundTag);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(IS_LADY, false);
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
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
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
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		Vec3 vec3 = this.getDeltaMovement();
		//上升
		if (this.getOnPos().getY() < this.level().getMinBuildHeight() && this.getTarget() == null || this.isInWallSelf()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3 - vec3.y) * 0.3, 0.0));
		}
		//靠近
		if (this.getTarget() != null && this.distanceTo(this.getTarget()) > 3 && this.getAttackTick() <= 0) {
			this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
			this.RushAttack();
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
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
		this.clearFire();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (this.level().isClientSide) {
			for (int i = 0; i < 3; ++i) {
				this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.4), this.getY() + 0.15 + (2.0D * this.random.nextDouble() - 1.0D) * 0.6, this.getRandomZ(0.4), 0.0, 0.0, 0.0);
			}
			for (int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0.0, 0.0, 0.0);
			}
		}
		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackUse() != 5 && this.getAttackTick() == 15 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackUse() == 5 && this.isAlive()) {
			if (this.getAttackTick() == 13) {
				this.trueHurt();
			}
		}
		if (this.getAttackTick() > 0){
			this.getNavigation().stop();
		}
		//战斗
		if (this.getTarget() != null && this.getAttackTick() <= 5) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 2, 0));
			}
		}
		if (this.getAttackTick() > 20){
			if (this.getTarget() != null) {
				this.lookAt(this.getTarget(), 360f, 360f);
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			}
		}
	}

	public boolean isInWallSelf() {
		float f = this.getBbWidth() * 0.8F;
		AABB aabb = AABB.ofSize(this.getEyePosition(), (double)f, 1.0E-6, (double)f);
		return BlockPos.betweenClosedStream(aabb).anyMatch((p_201942_) -> {
			BlockState blockstate = this.level().getBlockState(p_201942_);
			return !blockstate.isAir() && blockstate.isSuffocating(this.level(), p_201942_) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level(), p_201942_).move((double)p_201942_.getX(), (double)p_201942_.getY(), (double)p_201942_.getZ()), Shapes.create(aabb), BooleanOp.AND);
		});
	}

	public boolean RushAttack() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.015f;
		float f8 = f4 *= f7 / f6 * 2;
		this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAttackTick(25);
			int attackRandom = this.getRandom().nextInt(40);
			//1-重劈
			if (attackRandom > 30) {
				this.setAttackUse(1);
				this.setAnimationState("attack1");
			}
			//2-挥击
			else if (attackRandom > 20) {
				this.setAttackUse(2);
				this.setAnimationState("attack2");
			}
			else if (attackRandom > 10) {
				this.setAttackUse(2);
				this.setAnimationState("attack3");
			}
			//3-绞杀
			else {
				this.setAttackUse(3);
				this.setAnimationState("attack4");
			}
		}
		this.getNavigation().stop();
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,0,0));
		return true;
	}

	public boolean trueHurt() {
		//1-重劈
		//2-挥击
		//3-绞杀
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.FLAMOAR_ATTACK, 2.0f, 1.0f);
			if (this.getAttackUse() == 1) {
				this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 2) {
				this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 3) {
				this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 2.0f, 1.0f);
			}
		}
		//1-重劈
		//2-挥击
		//3-绞杀
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 1.5f;
		if (this.getAttackUse() == 1) {
			damageMulti = 1.25f;
			knockbackMulti = 1.25f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.25f;
			reach = 1.55f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 3 && this.getAttackUse() != 4 && this.getAttackUse() != 5) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl) {
				//着火
				hurt.setSecondsOnFire(16);
				//火焰粒子
				double d = -Mth.sin(this.getYRot() * 0.017453292f);
				double d2 = Mth.cos(this.getYRot() * 0.017453292f);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 15; ++i) {
						double xs = this.getX() + d + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - hurt.getX())/2;
						double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - hurt.getY())/2;
						double zs = this.getZ() + d2 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - hurt.getZ())/2;
						serverLevel.sendParticles(ParticleTypes.FLAME, xs, ys, zs, 0, d, 0.0, d2, 0.0);
					}
				}
			}
		}
		//位移
		{
			if (this.getAttackUse() == 2) {
				float f = this.getYRot();
				float f2 = this.getXRot();
				float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 0.5f;
				float f8 = f4 *= f7 / f6 * 2;
				if (!this.onGround()) {
					f8 = 0;
				}
				this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}


	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isFireDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.getEntity() instanceof LivingEntity livingEntity && livingEntity.isOnFire() && !livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE))
			return super.hurt(damageSource, amount/3);
		return super.hurt(damageSource, amount);
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
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			this.setLady(serverLevelAccessor.getRandom().nextFloat() > 0.8f);
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Nullable
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
}

