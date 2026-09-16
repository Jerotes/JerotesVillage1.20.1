package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;


public class BlueShimmerSquidEntity extends GlowSquid implements JerotesEntity, Bucketable {
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BlueShimmerSquidEntity.class, EntityDataSerializers.BOOLEAN);

	public BlueShimmerSquidEntity(EntityType<? extends BlueShimmerSquidEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 10.0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		return builder;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBucket();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromBucket() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FROM_BUCKET, false);
	}
	@Override
	public boolean fromBucket() {
		return this.getEntityData().get(FROM_BUCKET);
	}
	@Override
	public void setFromBucket(boolean bl) {
		this.entityData.set(FROM_BUCKET, bl);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("FromBucket", this.fromBucket());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFromBucket(compoundTag.getBoolean("FromBucket"));
	}
	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		Bucketable.saveDefaultDataToBucketTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.BLUE_SHIMMER_SQUID_BUCKET.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.GLOW_SQUID_AMBIENT;
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		return Bucketable.bucketMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
    }

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		if (super.hurt(damageSource, f) && this.getLastHurtByMob() != null) {
			if (!this.level().isClientSide) {
				this.spawnInkEffect();
			}
			return true;
		}
		return false;
	}

	private void spawnInkEffect() {
		AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
		areaEffectCloud.setOwner(this);
		areaEffectCloud.setParticle(ParticleTypes.GLOW_SQUID_INK);
		areaEffectCloud.setRadius(2f);
		areaEffectCloud.setDuration((int) (60));
		areaEffectCloud.setRadiusPerTick((2.0f - areaEffectCloud.getRadius()) / (float)areaEffectCloud.getDuration());
		areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 240, 0));
		areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.GLOWING, 240, 0));
		this.level().addFreshEntity(areaEffectCloud);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
		if (!list.isEmpty() && this.tickCount % 40 == 0) {
			list.removeIf(player -> Main.getTargetedEntity(player, 16) != this);
			for (Player players : list) {
				if (players == null) continue;
				if ((this.distanceTo(players)) > Double.MAX_VALUE) continue;
				if (!this.level().isClientSide) {
					players.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0), this);
					players.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 120, 0), this);
				}
			}
		}
	}
}

