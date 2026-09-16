package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class RuinsMouseEntity extends BrightMouseEntity {
	private static final EntityDataAccessor<BlockPos> TEETH_BLOCK_POS = SynchedEntityData.defineId(RuinsMouseEntity.class, EntityDataSerializers.BLOCK_POS);
	public RuinsMouseEntity(EntityType type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.RUINS_MOUSE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.RUINS_MOUSE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.RUINS_MOUSE_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 0.1f;
	}

	@Nullable
	@Override
	public RuinsMouseEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.RUINS_MOUSE.get().create(serverLevel);
	}
	private static final EntityDataAccessor<Integer> TEETH_NEED_TICK = SynchedEntityData.defineId(RuinsMouseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TEETH_USE_TICK = SynchedEntityData.defineId(RuinsMouseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DUST_TICK = SynchedEntityData.defineId(RuinsMouseEntity.class, EntityDataSerializers.INT);

	public int getDustTick() {
		return this.getEntityData().get(DUST_TICK);
	}
	public void setDustTick(int n) {
		this.getEntityData().set(DUST_TICK, n);
	}
	public int getTeethNeedTick() {
		return this.getEntityData().get(TEETH_NEED_TICK);
	}
	public void setTeethNeedTick(int n) {
		this.getEntityData().set(TEETH_NEED_TICK, n);
	}
	public int getTeethUseTick() {
		return this.getEntityData().get(TEETH_USE_TICK);
	}
	public void setTeethUseTick(int n) {
		this.getEntityData().set(TEETH_USE_TICK, n);
	}
	public void setTeethBlockPos(BlockPos blockPos) {
		this.entityData.set(TEETH_BLOCK_POS, blockPos);
	}
	public BlockPos getTeethBlockPos() {
		return this.entityData.get(TEETH_BLOCK_POS);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("TeethNeedTick", this.getTeethNeedTick());
		compoundTag.putInt("TeethUseTick", this.getTeethUseTick());
		compoundTag.putInt("DustTick", this.getDustTick());
		compoundTag.putInt("TeethBlockPosX", this.getTeethBlockPos().getX());
		compoundTag.putInt("TeethBlockPosY", this.getTeethBlockPos().getY());
		compoundTag.putInt("TeethBlockPosZ", this.getTeethBlockPos().getZ());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setTeethNeedTick(compoundTag.getInt("TeethNeedTick"));
		this.setTeethUseTick(compoundTag.getInt("TeethUseTick"));
		this.setDustTick(compoundTag.getInt("DustTick"));
		int n = compoundTag.getInt("TeethBlockPosX");
		int n2 = compoundTag.getInt("TeethBlockPosY");
		int n3 = compoundTag.getInt("TeethBlockPosZ");
		this.setTeethBlockPos(new BlockPos(n, n2, n3));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(TEETH_BLOCK_POS, BlockPos.ZERO);
		this.getEntityData().define(TEETH_NEED_TICK, 20);
		this.getEntityData().define(TEETH_USE_TICK, 0);
		this.getEntityData().define(DUST_TICK, 1800);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isInWaterOrRain() || this.isInWaterOrBubble()) {
			if (this.getDustTick() > 0) {
				if (!this.level().isClientSide()) {
					this.setDustTick(this.getDustTick() - 120);
				}
			}
		}
		else if (this.level().getBlockState(getOnPos()).is(JerotesVillageBlocks.INDUSTRIAL_RESIDUAL_SOIL.get())) {
			if (this.getDustTick() < 2400) {
				if (!this.level().isClientSide()) {
					this.setDustTick(this.getDustTick() + 1);
				}
			}
		}

		if (this.getTeethUseTick() > 1) {
			if (!this.level().isClientSide()) {
				this.setTeethUseTick(this.getTeethUseTick() + 1);
			}
			if (this.getTeethBlockPos() != null) {
				if (this.getOnPos().distToCenterSqr(this.getTeethBlockPos().getCenter()) < 3) {
					if (this.level().getBlockState(this.getTeethBlockPos()).getDestroySpeed(this.level(), this.getTeethBlockPos()) < 10 && this.level().getBlockState(getTeethBlockPos()).getDestroySpeed(this.level(), getTeethBlockPos()) > 0) {
						this.level().destroyBlockProgress(this.getId(), this.getTeethBlockPos(), (80 - getTeethUseTick()) * 8 / 80);
					}
				}
				else {
					if (!this.level().isClientSide()) {
						this.setTeethUseTick(0);
					}
					this.playSound(JerotesVillageSoundEvents.RUINS_MOUSE_TEETH, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
					this.spawnAtLocation(Items.BONE_MEAL);
					this.gameEvent(GameEvent.ENTITY_PLACE);
					if (this.getTeethBlockPos() != null) {
						this.level().destroyBlockProgress(this.getId(), this.getTeethBlockPos(), -1);
					}
				}
			}
		}
		if (this.getTeethUseTick() == 1) {
			if (!this.level().isClientSide()) {
				this.setTeethUseTick(this.getTeethUseTick() - 1);
			}
			this.playSound(JerotesVillageSoundEvents.RUINS_MOUSE_TEETH, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
			this.spawnAtLocation(Items.BONE_MEAL);
			this.gameEvent(GameEvent.ENTITY_PLACE);
			if (this.getTeethBlockPos() != null) {
				this.level().destroyBlockProgress(this.getId(), this.getTeethBlockPos(), -1);
			}
		}

		if (this.getTeethNeedTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setTeethNeedTick(this.getDustTick() - 1);
			}
		}
		if (!this.level().isClientSide() && this.isAlive() && this.getTeethNeedTick() <= 0 &&
				(this.level().getBlockState(getOnPos()).getDestroySpeed(this.level(), getOnPos()) >= 1.5
				|| this.level().getBlockState(getOnPos()).getDestroySpeed(this.level(), getOnPos()) < 0) && !this.level().getBlockState(getOnPos()).isAir() && this.level().getFluidState(getOnPos()).isEmpty()) {
			if (!this.level().isClientSide()) {
				this.setTeethNeedTick(this.getRandom().nextInt(6000) + 6000);
				this.setTeethUseTick(90);
				this.setTeethBlockPos(this.getOnPos());
			}
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {

		if (!this.level().isClientSide()) {
			this.setTeethBlockPos(this.getOnPos());
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}




