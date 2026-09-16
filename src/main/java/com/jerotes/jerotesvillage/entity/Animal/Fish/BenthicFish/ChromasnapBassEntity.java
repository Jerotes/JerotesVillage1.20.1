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


public class ChromasnapBassEntity extends BaseBenthicFishEntity {
	private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(ChromasnapBassEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLOR_1 = SynchedEntityData.defineId(ChromasnapBassEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLOR_2 = SynchedEntityData.defineId(ChromasnapBassEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLOR_3 = SynchedEntityData.defineId(ChromasnapBassEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLOR_4 = SynchedEntityData.defineId(ChromasnapBassEntity.class, EntityDataSerializers.INT);

	public ChromasnapBassEntity(EntityType<? extends ChromasnapBassEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.CHROMASNAP_BASS_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.CHROMASNAP_BASS_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.CHROMASNAP_BASS_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.CHROMASNAP_BASS_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.CHROMASNAP_BASS_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.CHROMASNAP_BASS_SWIM;
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
	public void setColor1(int n) {
		this.getEntityData().set(COLOR_1, n);
	}
	public int getColor1() {
		return this.getEntityData().get(COLOR_1);
	}
	public void setColor2(int n) {
		this.getEntityData().set(COLOR_2, n);
	}
	public int getColor2() {
		return this.getEntityData().get(COLOR_2);
	}
	public void setColor3(int n) {
		this.getEntityData().set(COLOR_3, n);
	}
	public int getColor3() {
		return this.getEntityData().get(COLOR_3);
	}
	public void setColor4(int n) {
		this.getEntityData().set(COLOR_4, n);
	}
	public int getColor4() {
		return this.getEntityData().get(COLOR_4);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("BaseColor", this.getBaseColor());
		compoundTag.putInt("Color1", this.getColor1());
		compoundTag.putInt("Color2", this.getColor2());
		compoundTag.putInt("Color3", this.getColor3());
		compoundTag.putInt("Color4", this.getColor4());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBaseColor(compoundTag.getInt("BaseColor"));
		this.setColor1(compoundTag.getInt("Color1"));
		this.setColor2(compoundTag.getInt("Color2"));
		this.setColor3(compoundTag.getInt("Color3"));
		this.setColor4(compoundTag.getInt("Color4"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(BASE_COLOR, 1);
		this.getEntityData().define(COLOR_1, 1);
		this.getEntityData().define(COLOR_2, 1);
		this.getEntityData().define(COLOR_3, 1);
		this.getEntityData().define(COLOR_4, 1);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			this.setBaseColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, 4));
			this.setColor1(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
			this.setColor2(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
			this.setColor3(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
			this.setColor4(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

