package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.List;
import java.util.Objects;

public class MerorCubeEntity extends MerorMachineEntity {

	public MerorCubeEntity(EntityType<? extends MerorCubeEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_CUBE_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_CUBE_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEROR_CUBE_WALK, 0.15f, 1.0f);
	}
	@Override
	public boolean isPushable() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Redstone Cube".equals(string) || "红石怪".equals(string)) {
			return super.isPushable();
		}
		return false;
	}
	@Override
	public boolean canBeCollidedWith() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Redstone Cube".equals(string) || "红石怪".equals(string)) {
			return super.canBeCollidedWith();
		}
		return this.isAlive();
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Redstone Cube".equals(string) || "红石怪".equals(string)) {
			return super.canCollideWith(entity);
		}
		return !(entity instanceof MerorMonstrosityEntity) && this.isAlive();
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.4f;
	}

	private int dustTick;
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else {
			return super.getAnimationState(animation);
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = super.getAllAnimations();
		list.add(this.attackAnimationState);
		return list;
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("DustTick", this.dustTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.dustTick = compoundTag.getInt("DustTick");
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
		if (this.dustTick > 0){
			this.dustTick -= 1;
		}
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Redstone Cube".equals(string) || "红石怪".equals(string)) {
			if (dustTick <= 0 && this.deathTime == 0 && this.onGround() && !this.onClimbable() && !this.level().getBlockState(this.getOnPos()).isAir()) {
				if (!(this.level().isClientSide || this.xOld == this.getX() && this.zOld == this.getZ())) {
					double d = Math.abs(this.getX() - this.xOld);
					double d2 = Math.abs(this.getZ() - this.zOld);
					if (d >= 0.003 || d2 >= 0.003) {
						if (this.level() instanceof ServerLevel serverLevel) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.REDSTONE_CUBE_DUST.get(), this.getX(), this.getY() + 0.01, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
						}
						this.dustTick = 5;
					}
				}
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.updateNoActionTime();
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (!("Redstone Cube".equals(string) || "红石怪".equals(string))) {
			if (this.level().isClientSide()) {
				this.level().addParticle(JerotesVillageParticleTypes.MEROR.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 2.5;
		}
	}
}