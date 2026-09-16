package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public abstract class BaseBenthicFishEntity extends AbstractSchoolingFish implements JerotesEntity {
	public BaseBenthicFishEntity(EntityType<? extends BaseBenthicFishEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AvoidEntityGoal<GemstoneMalignasaurEntity>(this, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
	}

	@Override
	public int getAmbientSoundInterval() {
		return 12000;
	}
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}

	@Override
	public void aiStep() {
		super.aiStep();
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AirTick", this.airTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.airTick = compoundTag.getInt("AirTick");
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
}

