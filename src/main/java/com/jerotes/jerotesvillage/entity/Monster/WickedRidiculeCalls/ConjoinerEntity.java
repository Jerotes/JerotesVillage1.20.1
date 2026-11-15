package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;

public class ConjoinerEntity extends Monster implements JerotesEntity, WickedRidiculeCallsEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MAIN_ATTACK_TICK = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OFF_ATTACK_TICK = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(ConjoinerEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState attack6AnimationState = new AnimationState();
	public AnimationState attack7AnimationState = new AnimationState();
	public AnimationState attack8AnimationState = new AnimationState();

	public ConjoinerEntity(EntityType<? extends ConjoinerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1F);
		this.xpReward = 20;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.325);
		builder = builder.add(Attributes.MAX_HEALTH, 50.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new RestrictSunGoal(this));
		this.goalSelector.addGoal(0, new JerotesNoFireFleeSunGoal(this, 1.0));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.35, false));
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
		return JerotesVillageSoundEvents.CONJOINER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.CONJOINER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.CONJOINER_DEATH;
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
		return 2.45F;
	}
	public AABB getAttackBoundingBox() {
		AABB aabb = this.getBoundingBox();
		AABB aabb1 = aabb.inflate(Math.sqrt(2.04F) - 0.6F, 0.0, Math.sqrt(2.04F) - 0.6F);
		return aabb1.inflate(0.5, 0.5, 0.5);
	}
	@Override
	public boolean isWithinMeleeAttackRange(net.minecraft.world.entity.LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public int getMaxHeadYRot() {
		return 45;
	}
	@Override
	public int getMaxHeadXRot() {
		return 25;
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	public boolean specialAction() {
		return false;
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public void setAnimTick(int n) {
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick() {
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setMainAttackTick(int n) {
		this.getEntityData().set(MAIN_ATTACK_TICK, n);
	}
	public int getMainAttackTick() {
		return this.getEntityData().get(MAIN_ATTACK_TICK);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setOffAttackTick(int n) {
		this.getEntityData().set(OFF_ATTACK_TICK, n);
	}
	public int getOffAttackTick() {
		return this.getEntityData().get(OFF_ATTACK_TICK);
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
		return 0;
	}
	public List<AnimationState> getAllAnimations() {
		List<AnimationState> list = new ArrayList<>();
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
		compoundTag.putInt("MainAttackTick", this.getMainAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("OffAttackTick", this.getOffAttackTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setMainAttackTick(compoundTag.getInt("MainAttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setOffAttackTick(compoundTag.getInt("OffAttackTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(MAIN_ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(OFF_ATTACK_TICK, 0);
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
			this.setMainAttackTick(Math.max(0, this.getMainAttackTick() - 1));
			this.setOffAttackTick(Math.max(0, this.getOffAttackTick() - 1));
		}
		if (this.getMainAttackTick() > 0 || this.getOffAttackTick() > 0 || this.specialAction()) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 1, 0));
			}
		}
		//
		if (this.getMainAttackTick() == 15) {
			this.trueHurt(true);
		}
		if (this.getOffAttackTick() == 15) {
			this.trueHurt(false);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction()) {
			return false;
		}
		boolean bl = false;
		if (this.getMainAttackTick() <= 0) {
			if (!this.level().isClientSide()) {
				this.level().broadcastEntityEvent(this, (byte)(isLeftHanded() ? 102 : 101));
				this.setMainAttackTick(20);
			}
			bl = true;
		}
		if (this.getOffAttackTick() <= 0) {
			if (!this.level().isClientSide()) {
				this.level().broadcastEntityEvent(this, (byte)(!isLeftHanded() ? 102 : 101));
				this.setOffAttackTick(20);
			}
			bl = true;
		}
		return bl;
	}
	public boolean trueHurt(boolean hand) {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.CONJOINER_ATTACK, 1.0f, 1.0f);
		}
		float damageMulti = 1.0F;
		float knockbackMulti = 1.0F;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 2) continue;
			AttackFind.attackBegin(this, hurt);
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
			boolean bl2 = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, knockbackMulti, false, 0);
			if (bl2) {
			}
		}
		sweepAttack(this, this.isLeftHanded() == hand);
		return true;
	}
	public static void sweepAttack(Entity entity, boolean isLeft) {
		float yaw = (entity.getYRot() + (isLeft ? -45.0F : 45.0F)) * ((float)Math.PI / 180F);
		double d = (double)(-Mth.sin(yaw));
		double d2 = (double)Mth.cos(yaw);
		Level var6 = entity.level();
		if (var6 instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX() + d, entity.getY((double)0.5F), entity.getZ() + d2, 0, d, (double)0.0F, d2, (double)0.0F);
		}
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 101) {
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 30) {
				this.attack1AnimationState.start(this.tickCount);
			}
			else if (attackRandom > 20) {
				this.attack2AnimationState.start(this.tickCount);
			}
			else if (attackRandom > 10) {
				this.attack3AnimationState.start(this.tickCount);
			}
			else {
				this.attack4AnimationState.start(this.tickCount);
			}
		}
		else if (by == 102) {
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 30) {
				this.attack5AnimationState.start(this.tickCount);
			}
			else if (attackRandom > 20) {
				this.attack6AnimationState.start(this.tickCount);
			}
			else if (attackRandom > 10) {
				this.attack7AnimationState.start(this.tickCount);
			}
			else {
				this.attack8AnimationState.start(this.tickCount);
			}
		}
		else {
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
}
