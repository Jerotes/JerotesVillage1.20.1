package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class BitterCodEntity extends BaseBenthicFishEntity {
	public BitterCodEntity(EntityType<? extends BitterCodEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.BITTER_COD_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BITTER_COD_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BITTER_COD_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BITTER_COD_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.BITTER_COD_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.BITTER_COD_SWIM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
		return builder;
	}
}

