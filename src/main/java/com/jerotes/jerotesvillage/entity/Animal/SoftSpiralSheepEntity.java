package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.HornableEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class SoftSpiralSheepEntity extends Sheep implements JerotesEntity, HornableEntity {
	private static final EntityDataAccessor<Boolean> NO_HORN = SynchedEntityData.defineId(SoftSpiralSheepEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> NEED_RUSH = SynchedEntityData.defineId(SoftSpiralSheepEntity.class, EntityDataSerializers.INT);
	public SoftSpiralSheepEntity(EntityType<? extends SoftSpiralSheepEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 12.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		return builder;
	}

	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.15f * entityDimensions.height;
	}

	public boolean isNoHorn() {
		return this.getEntityData().get(NO_HORN);
	}
	public void setNoHorn(boolean bl) {
		this.getEntityData().set(NO_HORN, bl);
	}
	public boolean isNeedRush() {
		return this.getEntityData().get(NEED_RUSH) > 0;
	}
	public int getNeedRush() {
		return this.getEntityData().get(NEED_RUSH);
	}
	public void setNeedRush(int n) {
		this.getEntityData().set(NEED_RUSH, n);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsNoHorn", this.isNoHorn());
		compoundTag.putBoolean("IsNeedRush", this.isNeedRush());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("IsNoHorn")) {
			this.setNoHorn(compoundTag.getBoolean("IsNoHorn"));
		}
		if (compoundTag.contains("IsNeedRush")) {
			this.setNoHorn(compoundTag.getBoolean("IsNeedRush"));
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(NO_HORN, false);
		this.getEntityData().define(NEED_RUSH, 0);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		super.dropCustomDeathLoot(damageSource, n, bl);
		if (this.isHorn()) {
			this.spawnAtLocation(Items.GOAT_HORN, Main.randomReach(this.getRandom(), 0, 2));
		}
	}

	@Nullable
	@Override
	public SoftSpiralSheepEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		SoftSpiralSheepEntity sheep = JerotesVillageEntityType.SOFT_SPIRAL_SHEEP.get().create(serverLevel);
		if (sheep != null) {
			sheep.setNoHorn(this.isNoHorn());
		}
		return sheep;
	}
	@Override
	public void spawnChildFromBreeding(ServerLevel serverLevel, Animal animal) {
		if (animal instanceof SoftSpiralSheepEntity sheep && sheep.isHorn() != this.isHorn()) {
			for (int i = 0; i < Main.randomReach(this.getRandom(), 1, 3); ++i) {
				AgeableMob ageableMob = this.getBreedOffspring(serverLevel, animal);
				if (ageableMob == null) {
					return;
				}
				ageableMob.setBaby(true);
				ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
				this.finalizeSpawnChildFromBreeding(serverLevel, animal, ageableMob);
				serverLevel.addFreshEntityWithPassengers(ageableMob);
			}
		}
		else {
			super.spawnChildFromBreeding(serverLevel, animal);
		}
	}

	@Override
	public boolean isHorn() {
		return !this.isBaby() && !this.isNoHorn();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getLastHurtByMob() != null) {
			if (!this.level().isClientSide()) {
				this.setNeedRush(Math.min(this.getNeedRush() + 1, 20));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setNeedRush(Math.max(this.getNeedRush() - 1, 0));
			}
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
		return super.hurt(damageSource, amount);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setNoHorn(this.getRandom().nextBoolean());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}
