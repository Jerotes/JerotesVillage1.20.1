package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.client.particle.CylinderShockwaveParticleOptions;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
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
import java.util.Objects;

public class MockerEntity extends Monster implements JerotesEntity, WickedRidiculeCallsEntity, FactionEntity, SpellUseEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LAUGH_COOLDOWN = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LAUGH_TICK = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_COOLDOWN = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> CLIMB = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> CLIMB_ANIM = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(MockerEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState laughAnimationState = new AnimationState();
	public AnimationState jumpAnimationState = new AnimationState();

	public MockerEntity(EntityType<? extends MockerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1F);
		this.xpReward = 15;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 40.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
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
		return JerotesVillageSoundEvents.MOCKER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.MOCKER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MOCKER_DEATH;
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
		return 1.725F;
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
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 10;
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
	public boolean onClimbable() {
		return this.isClimbing();
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction() && !(this.getJumpTick() <= 40 && this.getJumpTick() >= 28)) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	public boolean specialAction() {
		return this.getLaughTick() > 0 || this.getJumpTick() > 0;
	}


	public float rushFindXRot;
	public float rushFindYRot;
	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float climbAnimProgress = 0.0f;
	public float attackAnimProgress = 0.0f;
	public int spellLevel = 1;
	public int wickedRidiculeNightNightSpellLevel = 2;
	@Override
	public int getSpellLevel() {
		if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).WickedRidiculeNight) {
			return Math.max(this.spellLevel, this.wickedRidiculeNightNightSpellLevel);
		}
		return this.spellLevel;
	}
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
	public void setLaughCooldown(int n) {
		this.getEntityData().set(LAUGH_COOLDOWN, n);
	}
	public int getLaughCooldown() {
		return this.getEntityData().get(LAUGH_COOLDOWN);
	}
	public void setLaughTick(int n) {
		this.getEntityData().set(LAUGH_TICK, n);
	}
	public int getLaughTick() {
		return this.getEntityData().get(LAUGH_TICK);
	}
	public void setJumpCooldown(int n) {
		this.getEntityData().set(JUMP_COOLDOWN, n);
	}
	public int getJumpCooldown() {
		return this.getEntityData().get(JUMP_COOLDOWN);
	}
	public void setJumpTick(int n) {
		this.getEntityData().set(JUMP_TICK, n);
	}
	public int getJumpTick() {
		return this.getEntityData().get(JUMP_TICK);
	}
	public boolean isClimbing() {
		return (this.entityData.get(CLIMB) & 1) != 0;
	}
	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(CLIMB);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(CLIMB, by);
	}
	public void setClimbAnim(int n){
		this.getEntityData().set(CLIMB_ANIM, n);
	}
	public int getClimbAnim(){
		return this.getEntityData().get(CLIMB_ANIM);
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
		if (Objects.equals(animation, "attack1")) {
			return 1;
		}
		else if (Objects.equals(animation, "attack2")) {
			return 2;
		}
		else if (Objects.equals(animation, "attack3")) {
			return 3;
		}
		else if (Objects.equals(animation, "attack4")) {
			return 4;
		}
		else if (Objects.equals(animation, "laugh")) {
			return 5;
		}
		else if (Objects.equals(animation, "jump")) {
			return 6;
		}
		return 0;
	}
	public List<AnimationState> getAllAnimations() {
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
		list.add(this.attack4AnimationState);
		list.add(this.laughAnimationState);
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
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("WickedRidiculeNightNightSpellLevel", this.wickedRidiculeNightNightSpellLevel);
		compoundTag.putInt("ClimbAnim", this.getClimbAnim());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("LaughCooldown", this.getLaughCooldown());
		compoundTag.putInt("LaughTick", this.getLaughTick());
		compoundTag.putInt("JumpCooldown", this.getJumpCooldown());
		compoundTag.putInt("JumpTick", this.getJumpTick());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.wickedRidiculeNightNightSpellLevel = compoundTag.getInt("WickedRidiculeNightNightSpellLevel");
		this.setClimbAnim(compoundTag.getInt("ClimbAnim"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setLaughCooldown(compoundTag.getInt("LaughCooldown"));
		this.setLaughTick(compoundTag.getInt("LaughTick"));
		this.setJumpCooldown(compoundTag.getInt("JumpCooldown"));
		this.setJumpTick(compoundTag.getInt("JumpTick"));
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
		this.getEntityData().define(LAUGH_COOLDOWN, 0);
		this.getEntityData().define(LAUGH_TICK, 0);
		this.getEntityData().define(JUMP_COOLDOWN, 0);
		this.getEntityData().define(JUMP_TICK, 0);
		this.getEntityData().define(CLIMB, (byte)0);
		this.getEntityData().define(CLIMB_ANIM, 0);
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
						this.attack1AnimationState.stop();
						this.attack1AnimationState.start(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.stop();
						this.attack2AnimationState.start(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.stop();
						this.attack3AnimationState.start(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.attack4AnimationState.stop();
						this.attack4AnimationState.start(this.tickCount);
						this.stopMostAnimation(this.attack4AnimationState);
						break;
					case 5:
						this.laughAnimationState.stop();
						this.laughAnimationState.start(this.tickCount);
						this.stopMostAnimation(this.laughAnimationState);
						break;
					case 6:
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
		if (!this.level().isClientSide()) {
			if (!(this.isVehicle() && this.getControllingPassenger() instanceof Player)) {
				this.setClimbing(this.horizontalCollision);
			}
		}
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
		if (this.onClimbable()) {
			if (!this.level().isClientSide()) {
				this.setClimbAnim(Math.min(this.getClimbAnim() + 1, 20));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setClimbAnim(Math.max(this.getClimbAnim() - 1, 0));
			}
		}
		//
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getJumpTick() > 10) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 20));
			}
		}
		else if (this.getAttackTick() > 0 || this.specialAction()) {
			if (this.getAttackAnim() > 10) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.max(this.getAttackAnim() - 1, 0));
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 1, 0));
			}
		}
		if (this.getAttackTick() == 15) {
			this.trueHurt();
		}
		//技能
		if (this.isAlive() && !this.isNoAi()) {
			//恶毒嘲笑
			if (this.getAttackTick() <= 0 &&
					!this.specialAction() && this.getLaughCooldown() <= 0 &&
					this.getTarget() != null && this.distanceTo(this.getTarget()) > 3 && SpellList.ViciousMockery(this.getSpellLevel(), this, this.getTarget()).canUse() && this.distanceTo(this.getTarget()) < 18 && this.getRandom().nextInt(2 * 20) == 1) {
				if (!this.level().isClientSide()) {
					this.setLaughTick(35);
					this.setLaughCooldown(240);
					this.setAnimTick(35);
					this.setAnimationState(0);
					this.setAnimationState("laugh");
				}
			}
			if (this.getLaughTick() == 20 && this.getTarget() != null && SpellList.ViciousMockery(this.getSpellLevel(), this, this.getTarget()).canUse()) {
				SpellList.ViciousMockery(this.getSpellLevel(), this, this.getTarget()).spellUse();
			}
			//跃击
			if (this.getAttackTick() <= 0 &&
					!this.specialAction() && this.getJumpCooldown() <= 0 &&
					this.getTarget() != null && this.distanceTo(this.getTarget()) > 6 && this.distanceTo(this.getTarget()) < 18) {
				if (!this.level().isClientSide()) {
					this.setJumpTick(60);
					this.setJumpCooldown(360);
					this.setAnimTick(60);
					this.setAnimationState(0);
					this.setAnimationState("jump");
				}
			}
			if (this.getJumpTick() == 60) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.MOCKER_JUMP, 2.0f, 1.0f);
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.MOCKER_JUMP_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
			if (this.getJumpTick() == 40 && this.getTarget() != null) {
				this.jumpToTarget(this.getTarget());
			}
			if (this.getJumpTick() > 50) {
				if (this.getTarget() != null) {
					this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				}
				this.rushFindXRot = this.getXRot();
				this.rushFindYRot = this.yHeadRot;
			}
			if (this.getJumpTick() <= 45 && this.getJumpTick() > 10) {
				if (!this.level().isClientSide()) {
					this.setXRot(this.rushFindXRot);
					this.setYRot(this.rushFindYRot);
					this.setYBodyRot(this.rushFindYRot);
					this.setYHeadRot(this.rushFindYRot);
				}
			}
			if (this.getJumpTick() == 28) {
				if (!this.level().isClientSide()) {
					this.setAttackUse(2);
				}
				this.trueHurt();
			}
		}
		if (!this.level().isClientSide()) {
			this.setLaughCooldown(Math.max(0, this.getLaughCooldown() - 1));
			this.setLaughTick(Math.max(0, this.getLaughTick() - 1));
			this.setJumpCooldown(Math.max(0, this.getJumpCooldown() - 1));
			this.setJumpTick(Math.max(0, this.getJumpTick() - 1));
		}
	}
    public boolean jumpToTarget(LivingEntity target) {
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
        if (len > 18.0) {
            dx = dx / len * 18.0;
            dz = dz / len * 18.0;
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
		if (this.specialAction()) {
			return false;
		}
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(40);
			if (attackRandom > 30) {
				this.setAttackTick(20);
				this.setAttackUse(1);
				this.setAnimTick(20);
				this.setAnimationState(0);
				this.setAnimationState("attack1");
			}
			else if (attackRandom > 20) {
				this.setAttackTick(20);
				this.setAttackUse(1);
				this.setAnimTick(20);
				this.setAnimationState(0);
				this.setAnimationState("attack2");
			}
			else if (attackRandom > 10) {
				this.setAttackTick(20);
				this.setAttackUse(1);
				this.setAnimTick(20);
				this.setAnimationState(0);
				this.setAnimationState("attack3");
			}
			else {
				this.setAttackTick(20);
				this.setAttackUse(1);
				this.setAnimTick(20);
				this.setAnimationState(0);
				this.setAnimationState("attack4");
			}
		}
		return true;
	}
	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(this.getAttackUse() != 2 ?
					JerotesVillageSoundEvents.MOCKER_ATTACK :
							JerotesVillageSoundEvents.MOCKER_CRIT,
					1.0f, 1.0f);
		}
		float damageMulti = 1.0F;
		float knockbackMulti = 1.0F;
		if (this.getAttackUse() == 2) {
			damageMulti = 1.5f;
			knockbackMulti = 1.5f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 2) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0);
			if (bl2) {
				if (!hurt.level().isClientSide) {
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 60, 0, false, false), this);
				}
			}
		}
		if (this.getAttackUse() == 2) {
			if (this.level() instanceof ServerLevel serverLevel) {
				float renderYawOffset = this.yBodyRot;
				float angleYaw = (90f) * ((float) Math.PI / 180.0F);
				Vec3 leftPos = new Vec3(this.xo + (2f) * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
				BlockPos blockPos = BlockPos.containing(leftPos).below();
				BlockParticleOption blockParticle = new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos));
				for (int i = 0; i < 20; i++) {
					double particleAngle = Math.PI * 2.0 * i / 20.0;
					serverLevel.sendParticles(blockParticle,
							leftPos.x + 1.0f * Math.cos(particleAngle),
							leftPos.y + 0.05,
							leftPos.z + 1.0f * Math.sin(particleAngle),
							1, 0.0, 0.0, 0.0, 0.0);
				}
				serverLevel.sendParticles(
						CylinderShockwaveParticleOptions.of(0.2f, 1.5f, 10, 0x6b1d00, 0xad3000),
						leftPos.x, leftPos.y + 0.05, leftPos.z, 1, 0, 0, 0, 0);
			}
		}
		else {
			Main.sweepAttack(this);
		}
		return true;
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
