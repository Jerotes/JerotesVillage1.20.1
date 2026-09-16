package com.jerotes.jerotesvillage.entity.Smart;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.goal.BulgeFloatGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.goal.SmartBulgeAttackGoal;
import com.jerotes.jerotesvillage.goal.SmartBulgeDodgeProjectileGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SmartBulgeEntity extends SmartEntity implements RangedAttackMob, JerotesEntity, FactionEntity, SpellUseEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SmartBulgeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SmartBulgeEntity.class, EntityDataSerializers.INT);

	public SmartBulgeEntity(EntityType<? extends SmartBulgeEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
		setMaxUpStep(0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
		} else {
			super.travel(vec3);
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new BulgeFloatGoal(this, 0.35f));
		this.goalSelector.addGoal(1, new SmartBulgeAttackGoal(this, 1.0, 60, 20.0f));
		this.goalSelector.addGoal(3, new SmartBulgeDodgeProjectileGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, true));
	}

	private float rotlerp(float p_21377_, float p_21378_, float p_21379_) {
		float f = Mth.wrapDegrees(p_21378_ - p_21377_);
		if (f > p_21379_) {
			f = p_21379_;
		}

		if (f < -p_21379_) {
			f = -p_21379_;
		}

		return p_21377_ + f;
	}
	public void lookJump(Entity p_21392_, float p_21393_, float p_21394_) {
		double d0 = p_21392_.getX() - this.getX();
		double d2 = p_21392_.getZ() - this.getZ();
		double d1;
		if (p_21392_ instanceof LivingEntity livingentity) {
			d1 = livingentity.getEyeY() - this.getEyeY();
		} else {
			d1 = (p_21392_.getBoundingBox().minY + p_21392_.getBoundingBox().maxY) / 2.0 - this.getEyeY();
		}

		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float ff = (float)(Mth.atan2(d2, d0) * 57.2957763671875) - 90.0F;
		float ff1 = (float)(-(Mth.atan2(d1, d3) * 57.2957763671875));
		float f1 = this.rotlerp(this.getXRot(), ff1, p_21394_);
		float f2 = this.rotlerp(this.getYRot(), ff, p_21393_);
		float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = -0.3f;
		this.setOnGround(false);
		this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, 0.3, f5 *= f7 / f6 * 2));
	}
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.distanceTo(livingEntity) < (Math.max(6, livingEntity.getBbWidth() / 1.5f))) {
			this.lookJump(livingEntity, 360f, 360f);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("shoot");
		}

		//法术列表-波涛射弹
		OtherSpellList.BulgeBullet(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24.0);
		return builder;
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BULGE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BULGE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BULGE_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}

	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
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
		if (Objects.equals(animation, "shoot")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.shootAnimationState);
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
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
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
						this.shootAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shootAnimationState);
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
	public int getAmbientSoundInterval() {
		return 480;
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.125f;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
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
		for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(6.0), e ->
				e != this && (e.swingTime == -1 || e.swinging) &&
						(e.distanceTo(e) <= 4 || e instanceof Mob mob && mob.isWithinMeleeAttackRange(this))
						&& this.onGround() && this.isAggressive() && this.getRandom().nextInt(20) == 1)) {
			Vec3 awayDir = this.position().subtract(livingEntity.position()).normalize().scale(0.5);
			this.setOnGround(false);
			this.setDeltaMovement(awayDir.x, 0.15, awayDir.z);
			break;
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
}

