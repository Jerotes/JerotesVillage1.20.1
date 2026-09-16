package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PredationToothedFlowerEntity extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, SpellUseEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState hideAnimationState = new AnimationState();
	public AnimationState unHideAnimationState = new AnimationState();
	public AnimationState inHideAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PredationToothedFlowerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PredationToothedFlowerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> UN_HIDE = SynchedEntityData.defineId(PredationToothedFlowerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> HIDE_TICK = SynchedEntityData.defineId(PredationToothedFlowerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(PredationToothedFlowerEntity.class, EntityDataSerializers.INT);

	public PredationToothedFlowerEntity(EntityType<? extends PredationToothedFlowerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0f);
		this.xpReward = 20;
		this.setJumping(false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(PredationToothedFlowerEntity.this, 1.25, true){
			@Override
			public boolean canContinueToUse() {
				return PredationToothedFlowerEntity.this.getShootTick() > 0 && super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 40, 20.0f){
			@Override
			public boolean canContinueToUse() {
				return (PredationToothedFlowerEntity.this.getTarget() != null && PredationToothedFlowerEntity.this.getShootTick() <= 0 && distanceToSqr(PredationToothedFlowerEntity.this.getTarget()) > 8) && super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		//捕食齿花喷吐
		if (this.hideAboutTick < 140) {
			this.hideAboutTick = 140;
		}
		if (this.getHideTick() > 0) {
			return;
		}
		if (!this.level().isClientSide()) {
			this.setShootTick(85);
			this.setAnimTick(10);
			this.setAnimationState("shoot");
		}
		//法术列表-捕食齿花喷吐
		OtherSpellList.PredationToothedFlowerShoot(this.getSpellLevel(), this, livingEntity).spellUse();

	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
		builder = builder.add(Attributes.JUMP_STRENGTH, 0.0);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.PREDATION_TOOTHED_FLOWER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.PREDATION_TOOTHED_FLOWER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.PREDATION_TOOTHED_FLOWER_DEATH;
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide()) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
		}
	}

	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setUnHide(boolean bl) {
		this.getEntityData().set(UN_HIDE, bl);
	}
	public boolean isUnHide() {
		return this.getEntityData().get(UN_HIDE);
	}
	public void setHideTick(int n) {
		this.getEntityData().set(HIDE_TICK, n);
	}
	public int getHideTick() {
		return this.getEntityData().get(HIDE_TICK);
	}
	public void setShootTick(int n) {
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootTick() {
		return this.getEntityData().get(SHOOT_TICK);
	}
	private int hideAboutTick;
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
		else if (Objects.equals(animation, "shoot")){
			return 2;
		}
		else if (Objects.equals(animation, "hide")){
			return 3;
		}
		else if (Objects.equals(animation, "unHide")){
			return 4;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		list.add(this.shootAnimationState);
		list.add(this.hideAnimationState);
		list.add(this.unHideAnimationState);
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
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putBoolean("IsUnHide", this.isUnHide());
		compoundTag.putInt("UnHideTick", this.getHideTick());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("HideAboutTick", this.hideAboutTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setUnHide(compoundTag.getBoolean("UnHide"));
		this.setHideTick(compoundTag.getInt("HideTick"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.hideAboutTick = compoundTag.getInt("HideAboutTick");
		this.setBoundingBox(this.makeBoundingBox());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(UN_HIDE, true);
		this.getEntityData().define(HIDE_TICK, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
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
					case 2:
						this.shootAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shootAnimationState);
						break;
					case 3:
						this.hideAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hideAnimationState);
						break;
					case 4:
						this.unHideAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.unHideAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
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
	protected AABB makeBoundingBox() {
		if (!this.isUnHide()) {
			return AABB.ofSize(new Vec3(this.getX(), this.getY() + 0.05, this.getZ()), 1, 0.1, 1);
		}
		return AABB.ofSize(new Vec3(this.getX(), this.getY()+1, this.getZ()), 1, 2, 1);
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

	@Override
	public void aiStep() {
		super.aiStep();
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
		//隐藏
		this.hideAboutTick = Math.max(0, this.hideAboutTick - 1);
		if (!this.level().isClientSide()) {
			this.setHideTick(Math.max(0, this.getHideTick() - 1));
			this.setShootTick(Math.max(0, this.getShootTick() - 1));
		}
		if (this.hideAboutTick <= 0 && this.isUnHide()) {
			if (!this.level().isClientSide()) {
				this.setUnHide(false);
				this.setHideTick(20);
				this.setAnimTick(21);
				this.setAnimationState("hide");
				this.setBoundingBox(this.makeBoundingBox());
			}
		}
		if (this.hideAboutTick > 0 && !this.isUnHide()) {
			if (!this.level().isClientSide()) {
				this.setUnHide(true);
				this.setHideTick(20);
				this.setAnimTick(21);
				this.setAnimationState("unHide");
				this.setBoundingBox(this.makeBoundingBox());
			}
		}

		if (this.isUnHide() && this.getHideTick() <= 0) {
			this.idleAnimationState.startIfStopped(this.tickCount);
			this.unHideAnimationState.stop();
			this.hideAnimationState.stop();
		}
		else {
			this.idleAnimationState.stop();
		}
		if (!this.isUnHide() && this.getHideTick() <= 0) {
			this.inHideAnimationState.startIfStopped(this.tickCount);
			this.unHideAnimationState.stop();
			this.hideAnimationState.stop();
		}
		else {
			this.inHideAnimationState.stop();
		}

		if (this.tickCount % 40 == 0) {
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.2, 0.2));
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == this) continue;
				if (find instanceof PredationToothedFlowerEntity) continue;
				if (find instanceof VirtualCaveSpiderEntity) continue;
				if (!(find instanceof Player) && !(find instanceof Mob)) continue;
				if ((this.distanceTo(find)) > 3) continue;
				if (this.getTarget() != null) continue;
				if (find.isAlliedTo(this)) continue;
				if (!this.canAttack(find) || !this.canAttackType(find.getType())) continue;
				if (find instanceof ServerPlayer player && (player.gameMode.getGameModeForPlayer() == GameType.CREATIVE || player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR))
					continue;
				if (find.isSpectator()) continue;
				if (find instanceof Enemy && this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(JerotesVillageBiomes.VIRTUAL_CAVE))
					continue;
				if (this.hideAboutTick < 40) {
					this.hideAboutTick = 40;
				}
				this.level().broadcastEntityEvent(this, (byte) 12);
				this.setTarget(find);
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.hideAboutTick < 140) {
			this.hideAboutTick = 140;
		}
		if (this.getHideTick() > 0) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.PREDATION_TOOTHED_FLOWER_ATTACK, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			if (entity instanceof LivingEntity livingEntity) {
				if (!entity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 40, 1), this);
				}
			}
		}
		return bl;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}


	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
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
		if (damageSource.is(DamageTypes.IN_FIRE))
			return super.hurt(damageSource, amount * 2);
		if (damageSource.is(DamageTypes.ON_FIRE))
			return super.hurt(damageSource, amount * 2);
		return super.hurt(damageSource, amount);
	}
}

