package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class MerorReteEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MEROR_RETE.get());
	private static final EntityType<MerorReteEntity> type = JerotesVillageEntityType.MEROR_RETE.get();
	public MerorReteEntity(EntityType<? extends MerorReteEntity> entityType, Level level) {
		super(entityType, level, item, 6.0f);
		this.noCulling = true;
	}
	public MerorReteEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 6.0f);
		this.noCulling = true;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double d) {
		double d2 = this.getBoundingBox().getSize() * 4.0;
		if (Double.isNaN(d2)) {
			d2 = 4.0;
		}
		d2 *= 128.0;
		return d < d2 * d2;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		if (!this.level().isClientSide()) {
			livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 60, 3, false, false));
		}
	}

	@Override
	public boolean canHurtEnderman() {
		return true;
	}
	@Override
	public boolean mustLoyalty() {
		return true;
	}
	@Override
	public DamageSource getDamageSource(Entity entity) {
		return this.damageSources().thrown(this, this.getOwner() == null ? this : this.getOwner());
	}
	@Override
	protected SoundEvent getDefaultHitSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}
	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
	}
	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (entityHitResult.getEntity() instanceof Entity) {
			Entity target = entityHitResult.getEntity();
			if (entityHitResult.getEntity() instanceof PartEntity<?> partEntity && partEntity.getParent() != null) {
				target = partEntity.getParent();
			}
			if (!EntityAndItemFind.isNoSpecialKnockback(target.getType())) {
				if (!this.level().isClientSide()) {
					this.setGet(target);
				}
			}
		}
	}
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			this.level().addParticle(JerotesVillageParticleTypes.MEROR.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
		}
		if (this.getGet() != null && this.tickCount < 160) {
			this.getGet().moveTo(this.getX(), this.getY(), this.getZ());
		}
		if (this.getOwner() == null || !(this.getOwner() instanceof Player) && this.dealtDamage && this.getBoundingBox().intersects(this.getOwner().getBoundingBox())) {
			this.discard();
		}
	}

	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}

	@Nullable
	private Entity get;
	@Nullable
	private UUID getUUID;
	@Nullable
	public UUID getGetUUID() {
		return getUUID;
	}
	@Nullable
	public Entity getGet() {
		Entity entity;
		if (this.get == null && this.getUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.getUUID)) instanceof Entity) {
			this.get = entity;
		}
		return this.get;
	}
	public void setGet(@Nullable Entity livingEntity) {
		this.get = livingEntity;
		this.getUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.getUUID != null) {
			compoundTag.putUUID("Get", this.getUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID("Get")) {
			this.getUUID = compoundTag.getUUID("Get");
		}
	}
}
