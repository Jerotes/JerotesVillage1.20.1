package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class VineboltEntity extends MagicAboutThrowEntity implements MagicAbout {
	public static final EntityDataAccessor<Boolean> USEFUL = SynchedEntityData.defineId(VineboltEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TARGET_UUID = SynchedEntityData.defineId(VineboltEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(VineboltEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> DATA_RELATIVE_YAW = SynchedEntityData.defineId(VineboltEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> DATA_RELATIVE_PITCH = SynchedEntityData.defineId(VineboltEntity.class, EntityDataSerializers.FLOAT);

	private double xHitSpeed = 0;
	private double yHitSpeed = 0;
	private double zHitSpeed = 0;

	public VineboltEntity(EntityType<? extends VineboltEntity> entityType, Level level) {
		super(entityType, level);
	}

	public VineboltEntity(int spellLevelDamage, Level level, LivingEntity livingEntity) {
		super(JerotesVillageEntityType.PROJECTILE_VINEBOLT.get(), livingEntity, level);
		this.spellLevelDamage = spellLevelDamage;
	}

	public VineboltEntity(int spellLevelDamage, Level level, double d, double d2, double d3) {
		super(JerotesVillageEntityType.PROJECTILE_VINEBOLT.get(), d, d2, d3, level);
		this.spellLevelDamage = spellLevelDamage;
	}

	public boolean isUseful() {
		return this.getEntityData().get(USEFUL);
	}
	public void setUseful(boolean bl) {
		this.getEntityData().set(USEFUL, bl);
	}

	@Nullable
	public Entity getTarget() {
		if (!this.level().isClientSide) {
			UUID uuid = this.getTargetUUID();
			return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
		} else {
			int id = this.getTargetId();
			return id <= -1 ? null : this.level().getEntity(id);
		}
	}

	public void setTarget(@Nullable Entity entity) {
		if (entity != null) {
			this.setTargetUUID(entity.getUUID());
		} else {
			this.setTargetUUID(null);
		}
	}

	@Nullable
	public UUID getTargetUUID() {
		return this.getEntityData().get(DATA_TARGET_UUID).orElse(null);
	}

	public void setTargetUUID(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_TARGET_UUID, Optional.ofNullable(uuid));
	}

	public int getTargetId() {
		return this.getEntityData().get(DATA_TARGET_ID);
	}

	public void setTargetId(int id) {
		this.getEntityData().set(DATA_TARGET_ID, id);
	}

	public float getRelativeYaw() {
		return this.getEntityData().get(DATA_RELATIVE_YAW);
	}

	public float getRelativePitch() {
		return this.getEntityData().get(DATA_RELATIVE_PITCH);
	}

	public void setRelativeYaw(float yaw) {
		this.getEntityData().set(DATA_RELATIVE_YAW, yaw);
	}

	public void setRelativePitch(float pitch) {
		this.getEntityData().set(DATA_RELATIVE_PITCH, pitch);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsUseful", this.isUseful());
		if (this.getTargetUUID() != null) {
			compoundTag.putUUID("Target", this.getTargetUUID());
		}
		compoundTag.putFloat("RelativeYaw", this.getRelativeYaw());
		compoundTag.putFloat("RelativePitch", this.getRelativePitch());
		compoundTag.putDouble("XHitSpeed", this.xHitSpeed);
		compoundTag.putDouble("YHitSpeed", this.yHitSpeed);
		compoundTag.putDouble("ZHitSpeed", this.zHitSpeed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setUseful(compoundTag.getBoolean("IsUseful"));
		if (compoundTag.hasUUID("Target")) {
			this.setTargetUUID(compoundTag.getUUID("Target"));
		} else {
			this.setTargetUUID(null);
		}
		this.setRelativeYaw(compoundTag.getFloat("RelativeYaw"));
		this.setRelativePitch(compoundTag.getFloat("RelativePitch"));
		this.xHitSpeed = compoundTag.getDouble("XHitSpeed");
		this.yHitSpeed = compoundTag.getDouble("YHitSpeed");
		this.zHitSpeed = compoundTag.getDouble("ZHitSpeed");
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(USEFUL, false);
		this.getEntityData().define(DATA_TARGET_UUID, Optional.empty());
		this.getEntityData().define(DATA_TARGET_ID, -1);
		this.getEntityData().define(DATA_RELATIVE_YAW, 0f);
		this.getEntityData().define(DATA_RELATIVE_PITCH, 0f);
	}
	@Override
	public void tick() {
		Entity target = this.getTarget();
		if (target != null && this.getTargetId() == -1) {
			this.setTargetId(target.getId());
		}

		super.tick();
		if (this.isUseful()) {
			if (target != null) {
				this.setDeltaMovement(Vec3.ZERO);
				this.setNoGravity(true);
				double x = target.getX();
				double y = target.getY() + target.getBbHeight() / 2.0;
				double z = target.getZ();
				this.xo = x;
				this.yo = y;
				this.zo = z;
				this.setPos(x, y, z);
				this.setRot(this.getRelativeYaw(), this.getRelativePitch());
				this.xOld = target.xOld;
				this.yOld = target.yOld + target.getBbHeight() / 2.0;
				this.zOld = target.zOld;
				this.yRotO = this.getYRot();
				this.xRotO = this.getXRot();
				if (target instanceof LivingEntity livingTarget && !EntityAndItemFind.isNoSpecialKnockback(livingTarget.getType())) {
					livingTarget.setOnGround(false);
					livingTarget.hurtMarked = true;
					livingTarget.setDeltaMovement(
							-this.xHitSpeed / 9.0,
							-this.yHitSpeed / 9.0,
							-this.zHitSpeed / 9.0
					);
				}
				if (this.level().isClientSide) {
					this.level().addParticle(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(), x, y, z, 0.0, 0.0, 0.0);
				}
				if (this.life >= this.getMaxLife() - 5) {
					this.lastBreak();
					this.discard();
					return;
				}
			}
		}

		if (!this.level().isClientSide) {
			if (target != null && !target.isAlive()) {
				this.setTarget(null);
				this.setTargetId(-1);
				this.setUseful(false);
				this.lastBreak();
				this.discard();
				return;
			}
			if (this.getOwner() != null && this.distanceTo(this.getOwner()) > 60) {
				this.lastBreak();
				this.discard();
				return;
			}
		}
		if (this.level().isClientSide) {
			this.level().addParticle(
					JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
					this.getX(),
					this.getY(),
					this.getZ(),
					0.0,
					0.0,
					0.0
			);
		}
		if (this.life >= this.getMaxLife() - 5) {
			this.lastBreak();
			this.discard();
		}
	}
	@Override
	public void lastBreak() {
		if (this.level() instanceof ServerLevel serverLevel) {
			Vec3 pos = this.position();
			int count = 40;
			for (int i = 0; i < count; i++) {
				double dx = (random.nextDouble() - 0.5) * 2.0;
				double dy = (random.nextDouble() - 0.5) * 2.0;
				double dz = (random.nextDouble() - 0.5) * 2.0;
				serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
						pos.x, pos.y, pos.z,
						1, dx, dy, dz, 0.1);
			}
		}
	}

	@Override
	protected Item getDefaultItem() {
		return Items.AIR;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (this.isUseful()) {
			return;
		}
		if (this.level().isClientSide) {
			return;
		}
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof LivingEntity livingEntity) {
			DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.INDIRECT_MAGIC, this, this.getOwner());
			boolean bl = livingEntity.hurt(damageSource, Main.rollDice(4 + (getSpellLevel() - 5), 12, livingEntity.getRandom()));
			if (bl) {
				this.life = 20 * 54;
				this.setUseful(true);
				this.setTarget(livingEntity);
				this.setTargetId(livingEntity.getId());
				this.addHurtUUID(entity.getUUID());
				this.setRelativeYaw(this.getYRot());
				this.setRelativePitch(this.getXRot());
				Vec3 vel = this.getDeltaMovement();
				this.xHitSpeed = -vel.x * 2;
				this.yHitSpeed = -vel.y * 2;
				this.zHitSpeed = -vel.z * 2;
				this.setDeltaMovement(Vec3.ZERO);
				this.setNoGravity(true);
				this.setPos(livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() / 2.0, livingEntity.getZ());
				this.setRot(this.getRelativeYaw(), this.getRelativePitch());
			}
			else {
				this.lastBreak();
				this.discard();
			}
		}
	}

	@Override
	protected void onHit(@NotNull HitResult hitResult) {
		if (hitResult.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHitResult = (EntityHitResult) hitResult;
			Entity entity = entityHitResult.getEntity();
			if (entity instanceof LivingEntity && !this.isUseful()) {
				this.onHitEntity(entityHitResult);
				return;
			}
		}
		if (!this.level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 3);
		}
		this.lastBreak();
		this.discard();
	}

	@Override
	public int getMaxLife() {
		return 20 * 60 + 5;
	}

	@Override
	protected float getGravity() {
		return 0f;
	}

	public int getSpellLevel() {
		return this.spellLevelDamage;
	}
}