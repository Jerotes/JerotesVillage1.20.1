package com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb;

import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VinePodBombEntity extends MagicSummonedEntity {
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> IS_BOMB = SynchedEntityData.defineId(VinePodBombEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(VinePodBombEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(VinePodBombEntity.class, EntityDataSerializers.INT);

	public VinePodBombEntity(EntityType<? extends VinePodBombEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 18);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2f);
		return builder;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "botanize_village";
	}

	@Override
	protected void registerGoals() {
		this.addGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));

		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, true) {
			public boolean canUse() {
				if (VinePodBombEntity.this.isBomb()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}
			public boolean canContinueToUse() {
				if (VinePodBombEntity.this.isBomb()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f) {
			public boolean canUse() {
				if (VinePodBombEntity.this.isBomb()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (VinePodBombEntity.this.isBomb()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});

		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GRASS_BREAK;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.GRASS_HIT;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.4f;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	public void setBomb(boolean bl){
		this.getEntityData().set(IS_BOMB, bl);
	}
	public boolean isBomb(){
		return this.getEntityData().get(IS_BOMB);
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
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
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
		compoundTag.putBoolean("IsBomb", this.isBomb());
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBomb(compoundTag.getBoolean("IsBomb"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(IS_BOMB, false);
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
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//
		//站立动画
		if (this.isAlive() && this.isBomb() && !this.onGround()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		else {
			this.idleAnimationState.stop();
		}
		//爆炸
		if (this.isBomb() && this.tickCount > 3 && this.onGround()) {
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MAGIC, this, this.getOwner());
			this.level().explode(this, damageSource, null, this.getX(), this.getY(), this.getZ(), 3.0f, false, Level.ExplosionInteraction.NONE);
			if (!this.level().isClientSide()) {
				this.setBomb(false);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_ATTACK.get(), this.getX(), this.getY() + 0.1f, this.getZ(), 0, 0, 0, 0, 0);
			}
		}
	}


	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
		return super.doHurtTarget(entity);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_EXPLOSION)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.getOwner() instanceof CelestialCoilvineEntity celestialCoilvineEntity) {
			if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource))
				amount *= 2f;
			if (damageSource.getDirectEntity() != null && damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.isOnFire() ||
					AttackFind.isFireDamage(damageSource))
				amount *= 1.5f;
			else if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
				ItemStack handItem = livingEntity.getMainHandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
					handItem = livingEntity.getOffhandItem();
				if (handItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
					amount *= 1.5f;
				}
			}
		}
		else {
			if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource))
				amount *= 1.25f;
		}
		boolean bl = super.hurt(damageSource, amount);
		return bl;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

}