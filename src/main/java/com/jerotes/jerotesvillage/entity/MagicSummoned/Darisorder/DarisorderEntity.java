package com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder;

import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.ConcentrationSpellEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.network.JerotesPlayerData;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Animal.GlowSparrowEntity;
import com.jerotes.jerotesvillage.goal.DarisorderAvoidGlowEntityGoal;
import com.jerotes.jerotesvillage.goal.DarisorderAvoidPlayerGoal;
import com.jerotes.jerotesvillage.goal.DarisorderMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.DarkNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class DarisorderEntity extends MagicSummonedEntity implements JerotesEntity, BreakShieldEntity, ConcentrationSpellEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(DarisorderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(DarisorderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(DarisorderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(DarisorderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> INSTABILITY = SynchedEntityData.defineId(DarisorderEntity.class, EntityDataSerializers.BOOLEAN);

	public DarisorderEntity(EntityType<? extends DarisorderEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.6f);
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
	}

	public int contractShouldBaseLevel() {
		return 8;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, GlowSparrowEntity.class, 8.0f, 1.5, 1.5));
		this.goalSelector.addGoal(0, new DarisorderAvoidPlayerGoal<>(this, Player.class, 8.0f, 1.5, 1.5));
		this.goalSelector.addGoal(1, new DarisorderAvoidGlowEntityGoal<>(this, LivingEntity.class, 8.0f, 1.5, 1.5));
		this.goalSelector.addGoal(2, new DarisorderMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(2, new JerotesNoFireFleeSunGoal(this, 1.0));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));


		if (!(this instanceof FuryDarisorderEntity)) {
			this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<Player>(this, Player.class, true));
			this.targetSelector.addGoal(3, new DarkNearestAttackableTargetGoal<Mob>(this, Mob.class, true));
		}
	}

	public boolean hasSunlightFear() {
		return true;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.MAX_HEALTH, 230);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
		return builder;
	}

	public static boolean checkMonsterSpawnRules(EntityType<? extends DarisorderEntity> p_219014_, ServerLevelAccessor p_219015_, MobSpawnType p_219016_, BlockPos p_219017_, RandomSource p_219018_) {
		return p_219015_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_219015_, p_219017_, p_219018_) && checkMobSpawnRules(p_219014_, p_219015_, p_219016_, p_219017_, p_219018_);
	}
	public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_219010_, BlockPos p_219011_, RandomSource p_219012_) {
		if (p_219010_.getBrightness(LightLayer.SKY, p_219011_) > p_219012_.nextInt(32)) {
			return false;
		} else {
			DimensionType dimensiontype = p_219010_.dimensionType();
			int i = dimensiontype.monsterSpawnBlockLightLimit();
			if (i < 15 && p_219010_.getBrightness(LightLayer.BLOCK, p_219011_) > i) {
				return false;
			} else {
				int j = p_219010_.getLevel().isThundering() ? p_219010_.getMaxLocalRawBrightness(p_219011_, 10) : p_219010_.getMaxLocalRawBrightness(p_219011_);
				return j <= dimensiontype.monsterSpawnLightTest().sample(p_219012_);
			}
		}
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.DARISORDER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.DARISORDER_DEATH;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
//	@Override
//	public boolean removeWhenFarAway(double d) {
//		return this.isInstability();
//	}
//	@Override
//	protected boolean shouldDespawnInPeaceful() {
//		return this.isInstability();
//	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public int getShieldBreakStrength() {
		return 100;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level){
			@Override
			protected PathFinder createPathFinder(int n) {
				this.nodeEvaluator = new WalkNodeEvaluator();
				this.nodeEvaluator.setCanPassDoors(true);
				return new PathFinder(this.nodeEvaluator, n){
					@Override
					protected float distance(Node node, Node node2) {
						return node.distanceToXZ(node2);
					}
				};
			}

		};
	}
	//
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
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
	public boolean canDisableShield() {
		return true;
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
		return aabb1.inflate(1.25d, 1.25d, 1.25d);
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 4f;
	}
	@Override
	public int getMaxFallDistance() {
		return super.getMaxFallDistance() * 64;
	}


	private double blockDestroyTick;
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public float lightLevel() {
		if (this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())) {
			return this.level().getMaxLocalRawBrightness(this.getOnPos().above()) / 15.0F;
		}
		return 0.0f;
	}
	public boolean isInstability() {
		return this.getEntityData().get(INSTABILITY);
	}
	public void setInstability(boolean bl) {
		this.getEntityData().set(INSTABILITY, bl);
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
		if (Objects.equals(animation, "start")){
			return 1;
		}
		else if (Objects.equals(animation, "attack1")){
			return 2;
		}
		else if (Objects.equals(animation, "attack2")){
			return 3;
		}
		else if (Objects.equals(animation, "attack3")){
			return 4;
		}
		else if (Objects.equals(animation, "attack4")){
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
		list.add(this.startAnimationState);
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
				if (state != this.startAnimationState) {
					state.stop();
				}
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			if (state != this.startAnimationState) {
				state.stop();
			}
		}
	}
	//

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putBoolean("IsInstability", this.isInstability());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setInstability(compoundTag.getBoolean("IsInstability"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(INSTABILITY, true);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (INSTABILITY.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 2:
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 3:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 4:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 5:
						this.attack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack4AnimationState);
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
	public boolean isEnderman() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Ender".equals(string) || "Enderman".equals(string) || "Darisender".equals(string)) {
			return true;
		}
		return false;
	}
	public boolean isSensitiveToWater() {
		return super.isSensitiveToWater() || isEnderman();
	}

	public void aiStep() {
		super.aiStep();
		if (this.getRandom().nextInt((int) (900 * (lightLevel() + 0.05))) == 1 && this.deathTime == 0) {
			this.heal((float) (3.0f / (lightLevel() + 0.25)));
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-(int)(5 + this.lightLevel() * 20), this.getAttackTick() - 1));
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		if (this.getAttackTick() > -(int)(5 + this.lightLevel() * 20)) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
				this.getNavigation().stop();
			}
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//见光死
		if (this.isInstability() && lightLevel() >= 0.4 && !isEnderman()) {
			if (this.getHealth() < 40 * lightLevel()) {
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 64; ++i) {
						double d = this.getRandom().nextGaussian() * 0.02;
						double d2 = this.getRandom().nextGaussian() * 0.02;
						double d3 = this.getRandom().nextGaussian() * 0.02;
						serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						if (this.getRandom().nextFloat() > 0.8f) {
							serverLevel.sendParticles(ParticleTypes.POOF, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						}
						if (this.getRandom().nextFloat() > 0.2f) {
							serverLevel.sendParticles(ParticleTypes.FLASH, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						}
					}
					this.remove(RemovalReason.KILLED);
				}
			}
			else {
				this.hurt(this.damageSources().genericKill(), 40f * lightLevel());
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 16; ++i) {
						double d = this.getRandom().nextGaussian() * 0.02;
						double d2 = this.getRandom().nextGaussian() * 0.02;
						double d3 = this.getRandom().nextGaussian() * 0.02;
						serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						if (this.getRandom().nextFloat() > 0.8f) {
							serverLevel.sendParticles(ParticleTypes.POOF, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						}
						if (this.getRandom().nextFloat() > 0.2f) {
							serverLevel.sendParticles(ParticleTypes.FLASH, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
						}
					}
				}
			}
		}
		if (this.hasEffect(MobEffects.GLOWING)) {
			this.hurt(this.damageSources().genericKill(), 5f);
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, 15f * lightLevel());
			if (blockDestroy) {
				this.blockDestroyTick = 40;
			}
			if (!this.level().isClientSide) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -(int)(5 + this.lightLevel() * 20)) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(0);
			this.setAnimTick(10);
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 30) {
				this.setAnimationState("attack1");
			} else if (attackRandom > 20) {
				this.setAnimationState("attack2");
			} else if (attackRandom > 10) {
				this.setAnimationState("attack3");
			} else {
				this.setAnimationState("attack4");
			}
		}
		float addBlind = entity instanceof LivingEntity livingEntity && EntityAndItemFind.targetBlindnessTrue(livingEntity) ? 1.5f : 1;
		AttackFind.attackBegin(this, entity);
//		if (entity instanceof LivingEntity livingEntity &&
//				this.getRandom().nextFloat() > 0.8f &&
//				this.getSpellTick() <= 0 &&
//				!(livingEntity.hasEffect(JerotesMobEffects.PHANTASMAL_KILLER.get()))) {
//			SpellList.PhantasmalKiller(4, this,livingEntity).spellUse();
//			if (!this.level().isClientSide()) {
//				this.setSpellTick(120);
//			}
//		}
		boolean bl = AttackFind.attackAfter(this, entity, 1.0f + (1-this.lightLevel()) * addBlind, 0.1f, false, 0f);;
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.DARISORDER_ATTACK, 1.0f, 1.0f);
			}
			double d = 0;
			if (entity instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
				d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			}
			double d3 = Math.max(0.25, 1.25 - d);
			if ((Main.mobSizeSmall(entity) || Main.mobSizeMedium(entity) || Main.mobSizeLarge(entity)) &&
					!EntityAndItemFind.isNoSpecialKnockback(entity.getType())) {
				entity.setOnGround(false);
				entity.setDeltaMovement(entity.getDeltaMovement().add((this.getX() - entity.getX()) * 0.3 * d3, 0, 0));
				entity.setDeltaMovement(entity.getDeltaMovement().add(0, (this.getY() - entity.getY()) * 0.225 * d3, 0));
				entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0, (this.getZ() - entity.getZ()) * 0.3 * d3));
				if (entity.distanceTo(this) <= 4) {
					entity.setDeltaMovement(0d, 0d, 0d);
				}
			}
		}
		//群攻
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.2, 1.4, 2.2));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (hurt instanceof DarisorderEntity darisorderEntity && darisorderEntity.getTarget() != this)
				continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, (1.0f + (1-this.lightLevel()) * addBlind) * 0.5f, 0.1f, false, 0f);
		}
		//横扫效果
		Main.sweepAttack(this);
		if (entity instanceof LivingEntity livingEntity) {
			BlockPos blockPos = this.getOnPos();
			for (int i = 0; i < 16; ++i) {
				BlockPos blockPos1 = Main.findSpawnPositionNearFillOnBlock(this.level(), entity.getOnPos(), 3);
				if (!Main.canSee(livingEntity, blockPos1.getCenter())) {
					blockPos = blockPos1;
				}
			}
			if (blockPos != this.getOnPos()) {
				this.moveTo(blockPos, this.getYRot(), (this.getXRot() + 180) % 360);
				this.playSound(JerotesSoundEvents.TELEPORT);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 64; ++i) {
						double d = this.random.nextGaussian() * 0.02;
						double d2 = this.random.nextGaussian() * 0.02;
						double d3 = this.random.nextGaussian() * 0.02;
						serverLevel.sendParticles(isEnderman() ? ParticleTypes.PORTAL : ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 1);
					}
				}
			}
		}
		return bl;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource)
				|| AttackFind.isNecroticDamage(damageSource)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isRadiantDamage(damageSource)) {
			amount *= 4;
			return super.hurt(damageSource, amount);
		}
		float trueDamage = amount;
		if (AttackFind.isFireDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isLightningDamage(damageSource)) {
			trueDamage = trueDamage / 10;
		}
		if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			trueDamage = amount * Math.max(0.65f, 1 - this.lightLevel() * 0.4f);
		}
		if (trueDamage > this.getMaxHealth() / 10 && trueDamage < this.getMaxHealth() * 3) {
			trueDamage = this.getMaxHealth() / 10;
		}
		return super.hurt(damageSource, trueDamage);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.DARKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.BLINDNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WITHER) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.BLEEDING.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.GLOWING) {
			this.addEffect(new MobEffectInstance(
					MobEffects.MOVEMENT_SLOWDOWN,
					mobEffectInstance.getDuration(),
					mobEffectInstance.getAmplifier()
					,mobEffectInstance.isAmbient()
					,mobEffectInstance.isVisible()
					,mobEffectInstance.showIcon()));
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType == MobSpawnType.CHUNK_GENERATION) {
			this.setInstability(true);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(30);
			this.setAnimationState("start");
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
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
		for (int i = 0; i < 16; ++i) {
			double d = this.random.nextGaussian() * 0.02;
			double d2 = this.random.nextGaussian() * 0.02;
			double d3 = this.random.nextGaussian() * 0.02;
			this.level().addParticle(isEnderman() ? ParticleTypes.PORTAL : ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
			if (this.getRandom().nextFloat() > 0.8f) {
				this.level().addParticle(isEnderman() ? ParticleTypes.PORTAL : ParticleTypes.POOF, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
			}
			if (this.getRandom().nextFloat() > 0.2f) {
				this.level().addParticle(isEnderman() ? ParticleTypes.PORTAL : ParticleTypes.FLASH, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
			}
		}
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.endConcentration();
			if (this.getLastDamageSource() != null && !this.getLastDamageSource().is(JerotesDamageTypes.MAGIC_EFFECT)) {
				this.level().broadcastEntityEvent(this, (byte) 60);
				this.remove(RemovalReason.KILLED);
			}
			else {
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}


	@Override
	public void remove(RemovalReason removalReason) {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CALL_THE_DARISORDER.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		super.remove(removalReason);
	}

	@Override
	public boolean endConcentration() {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CALL_THE_DARISORDER.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		if (this.isNotPersistent() && this.getLifeTick() > 0) {
			this.setTame(false);
			this.setOwnerId(-1);
			this.entityData.set(DATA_OWNER_UUID_ID, Optional.empty());
		}
		else {
			this.discard();
		}
		return true;
	}
}

