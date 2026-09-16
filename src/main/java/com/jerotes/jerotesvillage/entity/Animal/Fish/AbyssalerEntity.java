package com.jerotes.jerotesvillage.entity.Animal.Fish;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class AbyssalerEntity extends AbstractFish implements NeutralMob, JerotesEntity, FactionEntity {
	public AnimationState attackAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(AbyssalerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(AbyssalerEntity.class, EntityDataSerializers.INT);

	public AbyssalerEntity(EntityType<? extends AbyssalerEntity> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, false);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.ABYSSALER_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.ABYSSALER_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.ABYSSALER_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.ABYSSALER_HURT;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.ABYSSALER_SWIM;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.ABYSSALER_FLOP;
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (this.getHealth() < this.getMaxHealth()/2) {
			return Bucketable.bucketMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
		}
		return InteractionResult.sidedSuccess(((Level)this.level()).isClientSide);
    }

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 1.5);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<GemstoneMalignasaurEntity>(this, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<Pufferfish>(this, Pufferfish.class, 8.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(4, new AbyssalerSwimGoal(this));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this,1,40));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
		//this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<StarbriteEntity>(this, StarbriteEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Guardian>(this, Guardian.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<AbyssalerEntity>(this, false));
	}

	public boolean downFish() {
		return airTick >= 150;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(6);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.ABYSSALER_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
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
		if (!this.isInWater() && this.airTick < 200) {
			airTick += Math.min(200, airTick + 1);
		}
		if (this.isInWater() && this.airTick > 0) {
			airTick = Math.max(0, airTick - 5);
		}
	}

	private int airTick = 0;
	public int getAirTick() {
		return airTick;
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
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AirTick", this.airTick);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.airTick = compoundTag.getInt("AirTick");
		this.readPersistentAngerSaveData(this.level(), compoundTag);
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
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		} else {
			super.travel(vec3);
		}
	}

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}


	static class AbyssalerSwimGoal extends RandomSwimmingGoal {
		private final AbstractFish fish;

		public AbyssalerSwimGoal(AbstractFish abstractFish) {
			super(abstractFish, 1.0, 40);
			this.fish = abstractFish;
		}
	}
}

