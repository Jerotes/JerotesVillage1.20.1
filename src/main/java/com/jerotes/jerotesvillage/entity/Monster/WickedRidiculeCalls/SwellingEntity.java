package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Other.SpellCloud.SpellCloudEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.SwellingStareMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SwellingEntity extends Monster implements JerotesEntity, WickedRidiculeCallsEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SwellingEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SwellingEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SwellingEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(SwellingEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(SwellingEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState jumpAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	public SwellingEntity(EntityType<? extends SwellingEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1F);
		this.xpReward = 5;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 12.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new RestrictSunGoal(this));
		this.goalSelector.addGoal(0, new JerotesNoFireFleeSunGoal(this, 1.0));
		this.goalSelector.addGoal(1, new SwellingStareMeleeAttackGoal(this, 1.35));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SWELLING_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SWELLING_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SWELLING_DEATH;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) {
			return false;
		}
		return super.canDrownInFluidType(type);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, net.minecraft.world.entity.EntityDimensions entityDimensions) {
		return 1.0F;
	}
	public AABB getAttackBoundingBox() {
		AABB aabb = this.getBoundingBox();
		AABB aabb1 = aabb.inflate(Math.sqrt(2.04F) - 0.6F, 0.0, Math.sqrt(2.04F) - 0.6F);
		return aabb1.inflate(5.5, 5.5, 5.5);
	}
	@Override
	public boolean isWithinMeleeAttackRange(net.minecraft.world.entity.LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 10;
	}
	@Override
	public int getMaxHeadYRot() {
		return 25;
	}
	@Override
	public int getMaxHeadXRot() {
		return 25;
	}

	public float rushFindXRot;
	public float rushFindYRot;
	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public void setAnimTick(int n) {
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick() {
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAttackTick(int n) {
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick() {
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackAnim(int n){
		this.getEntityData().set(ATTACK_ANIM, n);
	}
	public int getAttackAnim(){
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(0);
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "jump")) {
			return 1;
		}
		return 0;
	}
	public List<AnimationState> getAllAnimations() {
		List<AnimationState> list = new ArrayList<>();
		list.add(this.jumpAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception) {
		for (AnimationState state : this.getAllAnimations()) {
			if (state != exception) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation() {
		for (AnimationState state : this.getAllAnimations()) {
			state.stop();
		}
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)) {
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.jumpAnimationState.stop();
						this.jumpAnimationState.start(this.tickCount);
						this.stopMostAnimation(this.jumpAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void addInWickedRidiculeNight(boolean bl) {
		if (!this.level().isClientSide()) {
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DEEP_BLESSING.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 0), this);
		}
	}


	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getTarget() != null && (!this.getTarget().isAlive()
				|| this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-20, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() > 0 && !this.onGround()) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 2, 0));
			}
		}
		if (this.getAttackTick() > 0 && this.getAttackTick() < 58) {
			boolean bl = false;
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,
					this.getBoundingBox().inflate(0.5));
			for (LivingEntity hurt : list) {
				if (hurt == null) continue;
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				if (!this.hasLineOfSight(hurt)) continue;
				bl = true;
			}
			if (this.onGround() || bl) {
				this.trueHurt();
				if (!this.level().isClientSide()) {
					this.setAttackTick(0);
				}
			}
		}
	}
	public boolean jumpToTarget(Entity target) {
		if (!this.level().isClientSide()) {
			this.setXRot(this.rushFindXRot);
			this.setYRot(this.rushFindYRot);
			this.setYBodyRot(this.rushFindYRot);
			this.setYHeadRot(this.rushFindYRot);
		}
		this.lookAt(target, 360.0f, 360.0f);
		double dx = target.getX() - this.getX();
		double dz = target.getZ() - this.getZ();
		double len = Math.sqrt(dx * dx + dz * dz);
		if (len > 6.0) {
			dx = dx / len * 6.0;
			dz = dz / len * 6.0;
		}
		final double smoothX = Mth.clamp(Math.abs(dx), 0, 1);
		final double smoothZ = Mth.clamp(Math.abs(dz), 0, 1);
		final double d0 = dx * 0.3 * smoothX;
		final double d2 = dz * 0.3 * smoothZ;
		final float up = 0.25f + this.getRandom().nextFloat() * 0.3f;
		this.setDeltaMovement(this.getDeltaMovement().add(d0 * 0.75, up, d2 * 0.75));
		this.getNavigation().moveTo(target, 1.0f);
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -20) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(60);
			this.setAnimTick(10);
			this.setAnimationState(0);
			this.setAnimationState("jump");
		}
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
		}
		this.rushFindXRot = this.getXRot();
		this.rushFindYRot = this.yHeadRot;
		this.jumpToTarget(entity);
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SWELLING_JUMP, 1.0f, 1.0f);
		}
		return true;
	}
	public boolean trueHurt() {
		float damageMulti = 1.0F;
		float knockbackMulti = 0F;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0);
			if (bl2) {
				this.hurt(this.damageSources().mobAttack(hurt), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
			}
		}
		Main.sweepAttack(this);
		return true;
	}

	protected void blockedByShield(LivingEntity p_21246_) {
		super.blockedByShield(p_21246_);
		if (!this.level().isClientSide()) {
			this.setAttackTick(120);
		}
		this.knockback(2.5D, p_21246_.getX() - this.getX(), p_21246_.getZ() - this.getZ());
	}


	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			this.deadAnimationState.start(this.tickCount);
		}
		++this.deathTime;
		if (this.deathTime == 5) {
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.SWELLING_BOMB_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SWELLING_BOMB, 1.0f, 1.0f);
			}
			List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(23.0, 3.0, 3.0));
			for (Player hurt : list) {
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				double d = 0.0;
				if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
				}
				double d2 = Math.max(0, 1 - d) + 0.2f;
				if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType()) && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt))) {
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.26 * (d2 + 0.3), -(this.getY() - hurt.getY()) * 0.2 * (d2 + 0.35) + 0.2f, -(this.getZ() - hurt.getZ()) * 0.2 * (d2 + 0.3)));
				}
			}
		}
		if (!this.level().isClientSide()) {
			if (this.deathTime == 19) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 2.0F, 1.5F);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 10; ++i) {
						RandomSource random = RandomSource.create();
						double d = random.nextGaussian() * 0.02;
						double d2 = random.nextGaussian() * 0.02;
						double d3 = random.nextGaussian() * 0.02;
						float x = (float) this.getX((2.0 * random.nextDouble() - 1.0) * ((1.5 + random.nextDouble() * 2)));
						float y = (float) this.getY((2.0 * random.nextDouble() - 1.0) * ((0.75 + random.nextDouble() * 2)));
						float z = (float) this.getZ((2.0 * random.nextDouble() - 1.0) * ((1.5 + random.nextDouble() * 2)));
						serverLevel.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 0, d, d2, d3, 0);
					}
				}
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0, 3.0, 3.0));
				for (LivingEntity hurt : list) {
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					DamageSource damageSources = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this);
					hurt.hurt(damageSources, this.getMaxHealth() / (Math.max(1, (this.distanceTo(hurt)))));
				}
			}
		}
		if (this.deathTime == 3 && this.getTarget() != null) {
			this.rushFindXRot = this.getXRot();
			this.rushFindYRot = this.yHeadRot;
			this.jumpToTarget(this.getTarget());
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState(0);
				this.setAnimationState("jump");
			}
		}
		if (!this.level().isClientSide()) {
			if (this.deathTime == 19) {
				this.spawnLingeringCloud();
			}
		}
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		SpellCloudEntity areaEffectCloud = new SpellCloudEntity(this.level(), this.getX(), this.getY(), this.getZ());
		areaEffectCloud.setRadius(2.5f );
		areaEffectCloud.setRadiusOnUse(-0.5f);
		areaEffectCloud.setWaitTime(10);
		areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
		areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
		areaEffectCloud.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 60, 0, false, false));
		areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, false));
		if (!collection.isEmpty()) {
			for (MobEffectInstance mobEffectInstance : collection) {
				if (mobEffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
					areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
				}
			}
		}
		this.level().addFreshEntity(areaEffectCloud);
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
}
