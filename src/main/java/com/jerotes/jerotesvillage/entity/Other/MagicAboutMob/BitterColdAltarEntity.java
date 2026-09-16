package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.control.NoRotationControl;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.BitterColdSorcererEntity;
import com.jerotes.jerotesvillage.entity.Part.BitterColdAltarPart;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BitterColdAltarEntity extends Mob implements JerotesEntity, TraceableEntity, CanBeIllagerFactionEntity, OwnableEntity, MagicAbout {
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(BitterColdAltarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BitterColdAltarEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BitterColdAltarEntity.class, EntityDataSerializers.INT);
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState stopAnimationState = new AnimationState();
	private BitterColdAltarPart part_1;
	private BitterColdAltarPart part_2;


	public BitterColdAltarEntity(EntityType<? extends BitterColdAltarEntity> type, Level world) {
		super(type, world);
		this.startAnimationState.start(this.tickCount);
		resetParts();
	}
	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 50);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	public void resetParts() {
		removeParts();
		this.part_1 = new BitterColdAltarPart(this, 0.0f, 0, 1.5f, 1f, 3.5f, 1.0f);
		if (this.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (teams != null) {
				serverLevel.getScoreboard().addPlayerToTeam(part_1.getStringUUID(), teams);
			}
		}
		this.part_1.copyPosition(this);
		this.part_1.setParent(this);
		this.part_2 = new BitterColdAltarPart(this, 0.0f, 0, 0.5f, 5f, 1f, 1.0f);
		if (this.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (teams != null) {
				serverLevel.getScoreboard().addPlayerToTeam(part_2.getStringUUID(), teams);
			}
		}
		this.part_2.copyPosition(this);
		this.part_2.setParent(this);
	}

	public void updateParts() {
		Main.updatePart(part_1, this);
		Main.updatePart(part_2, this);
	}

	private void removeParts() {
		if (part_1 != null) {
			part_1.remove(RemovalReason.DISCARDED);
			part_1 = null;
		}
		if (part_2 != null) {
			part_2.remove(RemovalReason.DISCARDED);
			part_2 = null;
		}
	}

	@Override
	public void remove(@NotNull RemovalReason reason) {
		removeParts();
		super.remove(reason);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GLASS_BREAK;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.GLASS_HIT;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
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
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
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
	public boolean isIllagerFaction() {
		return this.getOwner() != null && (EntityFactionFind.isRaider(this.getOwner()));
	}

	@Override
	public void setIllagerFaction(boolean b) {
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;

	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	@Nullable
	@Override
	public LivingEntity getOwner() {
		Entity entity;
		if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
			this.owner = (LivingEntity)entity;
		}
		return this.owner;
	}
	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity;
		this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.getOwner() != null) {
			LivingEntity livingEntity = this.getOwner();
			if (entity == livingEntity) {
				return true;
			}
			if (livingEntity != null) {
				return livingEntity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
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
		else if (Objects.equals(animation, "stop")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.startAnimationState);
		list.add(this.stopAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != startAnimationState) {
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
	public void setStartTick(int n){
		this.getEntityData().set(START_TICK, n);
	}
	public int getStartTick(){
		return this.getEntityData().get(START_TICK);
	}
	//
	private int spellLevelDamage = 4;
	@Override
	public int getSpellLevel() {
		return this.spellLevelDamage;
	}
	public void setSpellLevelDamage(int n) {
		this.spellLevelDamage = n;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
		compoundTag.putFloat("Faces", this.faces);
		compoundTag.putInt("StartTick", this.getStartTick());
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
		this.faces = compoundTag.getFloat("Faces");
		this.setStartTick(compoundTag.getInt("StartTick"));
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		resetParts();
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
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(START_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
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
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 2:
						this.stopAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stopAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			this.goalSelector.setControlFlag(Goal.Flag.TARGET, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
		updateParts();
		if (this.part_1 == null || this.part_2 == null || this.part_1.distanceTo(this) > 10 || this.part_2.distanceTo(this) > 10) {
			resetParts();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		if (!this.level().isClientSide()) {
			this.setStartTick(this.getStartTick() + 1);
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
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//队伍
		if (this.getTarget() != null && this.getTarget().distanceTo(this) <= 24) {
			if (this.level() instanceof ServerLevel _level) {
				_level.sendParticles(ParticleTypes.SNOWFLAKE, this.getRandomX(0.5f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 3) + 1.5f, this.getRandomZ(0.5f), 15, 0.3, 0, 0.3, 0);
			}
		}

		//队伍
		List<BitterColdSorcererEntity> listSorcerer =
				this.level().getEntitiesOfClass(BitterColdSorcererEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
		listSorcerer.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
		if (listSorcerer.isEmpty()) {
			if (this.getStartTick() % 60 == 0) {
				this.hurt(this.damageSources().genericKill() ,5);
			}
		}

		if (this.getStartTick() % 120 == 0 && deathTime == 0) {
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BITTER_COLD_SORCERER_ALTAR_USE, this.getSoundSource(), 5.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12, 12, 12));
				if (!list.isEmpty()) {
					for (LivingEntity livingEntityDeter : list) {
						if (livingEntityDeter == null) continue;
						if ((this.distanceTo(livingEntityDeter)) > 24) continue;
						if (AttackFind.FindCanNotAttack(this, livingEntityDeter)) continue;
						if (this.getTeam() == null && livingEntityDeter.getTeam() == null && (livingEntityDeter instanceof Mob mob && mob.getTarget() != this && this.getTarget() != mob && EntityFactionFind.isRaider(mob) && this.isIllagerFaction()))
							continue;
						if (livingEntityDeter instanceof BitterColdAltarEntity || (livingEntityDeter instanceof OwnableEntity ownable && ownable.getOwner() == this.owner && (!(livingEntityDeter instanceof Mob mob) || mob.getTarget() != this) && this.getTarget() != ownable))
							continue;
						livingEntityDeter.setTicksFrozen(Math.max(livingEntityDeter.getTicksFrozen(), livingEntityDeter.getTicksFrozen() + 12 * 20));
						if (!livingEntityDeter.level().isClientSide) {
							livingEntityDeter.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 6 * 20, 0), this);
							livingEntityDeter.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 6 * 20, 0), this);
						}
					}
				}
				serverLevel.sendParticles(JerotesVillageParticleTypes.BITTER_COLD_ALTAR.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isColdDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || amount >= 300) {
			if (amount >= 5) {
				if (this.level() instanceof ServerLevel _level) {
					for (int i = 0; i < 24; ++i) {
						_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getRandomX(0.5f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 3) + 1.5f, this.getRandomZ(0.5f), 15, 0.3, 0, 0.3, 0);
					}
				}
			}
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isFireDamage(damageSource)) {
			if (amount >= 5) {
				if (this.level() instanceof ServerLevel _level) {
					for (int i = 0; i < 24; ++i) {
						_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getRandomX(0.5f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 3) + 1.5f, this.getRandomZ(0.5f), 15, 0.3, 0, 0.3, 0);
					}
				}
			}
			return super.hurt(damageSource, amount * 3);
		}
		if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
			ItemStack handItem = livingEntity.getMainHandItem();
			if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
				handItem = livingEntity.getOffhandItem();
			if (handItem.getItem() instanceof PickaxeItem pickaxeItem) {
				if (amount >= 5) {
					if (this.level() instanceof ServerLevel _level) {
						for (int i = 0; i < 24; ++i) {
							_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getRandomX(0.5f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 3) + 1.5f, this.getRandomZ(0.5f), 15, 0.3, 0, 0.3, 0);
						}
					}
				}
				int n = (int) pickaxeItem.getTier().getSpeed() + pickaxeItem.getEnchantmentLevel(handItem, Enchantments.BLOCK_EFFICIENCY);
				return super.hurt(damageSource, amount * n / 2);
			}
		}
		if (amount >= 5) {
			if (this.level() instanceof ServerLevel _level) {
				for (int i = 0; i < 24; ++i) {
					_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getRandomX(0.5f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 3) + 1.5f, this.getRandomZ(0.5f), 15, 0.3, 0, 0.3, 0);
				}
			}
			return super.hurt(damageSource, 5);
		}
		return false;
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
	public void tickDeath() {
		if(deathTime <= 0){
			this.setAnimTick(20);
			this.setAnimationState("stop");
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
