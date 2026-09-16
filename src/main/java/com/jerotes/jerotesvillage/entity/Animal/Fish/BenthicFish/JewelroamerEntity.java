package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;


public class JewelroamerEntity extends BaseBenthicFishEntity {
	private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(JewelroamerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_COLOR = SynchedEntityData.defineId(JewelroamerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FISH_TYPE = SynchedEntityData.defineId(JewelroamerEntity.class, EntityDataSerializers.INT);

	public JewelroamerEntity(EntityType<? extends JewelroamerEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.JEWELROAMER_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.JEWELROAMER_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.JEWELROAMER_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.JEWELROAMER_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.JEWELROAMER_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.JEWELROAMER_SWIM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
		return builder;
	}

	public void setBaseColor(int n) {
		this.getEntityData().set(BASE_COLOR, n);
	}
	public int getBaseColor() {
		return this.getEntityData().get(BASE_COLOR);
	}
	public void setAddColor(int n) {
		this.getEntityData().set(ADD_COLOR, n);
	}
	public int getAddColor() {
		return this.getEntityData().get(ADD_COLOR);
	}
	public void setColorType(int n) {
		this.getEntityData().set(FISH_TYPE, n);
	}
	public int getFishType() {
		return this.getEntityData().get(FISH_TYPE);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("BaseColor", this.getBaseColor());
		compoundTag.putInt("AddColor", this.getAddColor());
		compoundTag.putInt("FishType", this.getFishType());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBaseColor(compoundTag.getInt("BaseColor"));
		this.setAddColor(compoundTag.getInt("AddColor"));
		this.setColorType(compoundTag.getInt("FishType"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(BASE_COLOR, 1);
		this.getEntityData().define(ADD_COLOR, 1);
		this.getEntityData().define(FISH_TYPE, 1);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			this.setBaseColor(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
			this.setAddColor(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
			this.setColorType(Main.randomReach(serverLevelAccessor.getRandom(), 0, 7));
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

