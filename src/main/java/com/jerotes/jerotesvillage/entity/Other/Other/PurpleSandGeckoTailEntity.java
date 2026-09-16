package com.jerotes.jerotesvillage.entity.Other.Other;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandGeckoEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PurpleSandGeckoTailEntity extends Mob implements JerotesEntity {
	public AnimationState heyAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(PurpleSandGeckoTailEntity.class, EntityDataSerializers.BOOLEAN);
	public PurpleSandGeckoTailEntity(EntityType<? extends PurpleSandGeckoTailEntity> type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		return builder;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_BABY_ID, false);
	}

	@Override
	public boolean isBaby() {
		return this.getEntityData().get(DATA_BABY_ID);
	}

	@Override
	public void setBaby(boolean bl) {
		this.getEntityData().set(DATA_BABY_ID, bl);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_BABY_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	private int dieTick;

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsBaby", this.isBaby());
		compoundTag.putInt("HurtTick", this.dieTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBaby(compoundTag.getBoolean("IsBaby"));
		this.dieTick = compoundTag.getInt("HurtTick");
	}

	@Override
	public void tick() {
		super.tick();
		this.dieTick += 1;
		if (this.dieTick > 1200){
			this.hurt(this.damageSources().mobAttack(this), 10);
		}

		if (this.isAlive()) {
			this.heyAnimationState.startIfStopped(this.tickCount);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
	}

	@Override
	protected void doPush(Entity entity) {
		if (entity instanceof LivingEntity livingEntity && !(entity instanceof PurpleSandGeckoEntity) && !(entity instanceof PurpleSandGeckoTailEntity)) {
			this.hurt(this.damageSources().mobAttack(livingEntity), 10);
		}
		super.doPush(entity);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		this.hurt(this.damageSources().playerAttack(player), 10);
		return super.mobInteract(player, interactionHand);
	}

}




