package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BaseThrowingBallEntity extends ThrowableItemProjectile {

    public BaseThrowingBallEntity(EntityType<? extends BaseThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BaseThrowingBallEntity(EntityType<? extends BaseThrowingBallEntity> entityType, double d, double d2, double d3, Level level) {
        super(entityType, d, d2, d3, level);
    }

    public BaseThrowingBallEntity(EntityType<? extends BaseThrowingBallEntity> entityType, LivingEntity livingEntity, Level level) {
        super(entityType, livingEntity, level);
    }

    public float baseDamage() {
        return 2f;
    }
    public float addDamage() {
        return 4f;
    }
    public int maxBackCount() {
        return 0;
    }
    public void aboutEntity(Entity entity) {}
    public void aboutBreak(HitResult hitResult) {}
    public DamageSource damageSource(Entity entity) {
        return this.damageSources().thrown(this, entity);
    }
    public ParticleOptions tailParticles() {
        return ParticleTypes.CRIT;
    }
    public float particleSpeed() {
        return 1;
    }

    @Override
    public void handleEntityEvent(byte by) {
        if (by == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5) * 0.08, ((double) this.random.nextFloat() - 0.5) * 0.08, ((double) this.random.nextFloat() - 0.5) * 0.08);
            }
            if (this.getItem().getItem() instanceof FireworkRocketItem) {
                ItemStack itemStack = this.getItem();
                CompoundTag compoundTag = itemStack.isEmpty() ? null : itemStack.getTagElement("Fireworks");
                Vec3 vec3 = this.getDeltaMovement();
                this.level().createFireworks(this.getX(), this.getY(), this.getZ(), vec3.x, vec3.y, vec3.z, compoundTag);
            }
        }
    }

    public int backCount = 0;
    public double speedDamage = 0;
    public double deltaMoveDamage = 0;
    public double addMaxBackCount = 0;
    public boolean water = false;
    public double waterTick = 0;
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BackCount", this.backCount);
        compoundTag.putDouble("SpeedDamage", this.speedDamage);
        compoundTag.putDouble("DeltaMoveDamage", this.deltaMoveDamage);
        compoundTag.putDouble("AddMaxBackCount", this.addMaxBackCount);
        compoundTag.putBoolean("Water", this.water);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.backCount = compoundTag.getInt("BackCount");
        this.speedDamage = compoundTag.getDouble("SpeedDamage");
        this.deltaMoveDamage = compoundTag.getDouble("DeltaMoveDamage");
        this.addMaxBackCount = compoundTag.getDouble("AddMaxBackCount");
        this.water = compoundTag.getBoolean("Water");
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            if (this.backCount >= this.maxBackCount() + this.addMaxBackCount) {
                this.aboutBreak(hitResult);
                this.discard();
            }
            this.setDeltaMovement(this.getDeltaMovement().multiply(-0.05 - 0.005 * (backCount + 1), -1.2 + 0.05 * (backCount + 1), -0.05 - 0.005 * (backCount + 1)));
            this.backCount += 1;
        }
    }
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float f = (float) (this.addDamage() + (1 + this.baseDamage() / 10) * (deltaMoveDamage + this.speedDamage));
        boolean bl = entity.hurt(this.damageSource(this.getOwner()), f);
        if (bl) {
            this.aboutEntity(entity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (water) {
            Vec3 vec3 = this.getDeltaMovement();
            float f;
            if (this.isInWater()) {
                f = 1/0.8f * 0.99f;
            } else {
                f = 1;
            }
            this.setDeltaMovement(vec3.scale(f));
        }

        Vec3 vec3 = this.getDeltaMovement();
        double d5 = vec3.x * this.particleSpeed();
        double d6 = vec3.y * this.particleSpeed();
        double d1 = vec3.z * this.particleSpeed();
        if (this.tailParticles().getType() != ParticleTypes.ENTITY_EFFECT || !(this instanceof PotionThrowingBallEntity)) {
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(this.tailParticles(), this.getX() + d5 * (double) i / 4.0, this.getY() + d6 * (double) i / 4.0, this.getZ() + d1 * (double) i / 4.0, -d5, -d6 + 0.2 * this.particleSpeed(), -d1);
            }
        }
        if (water) {
            waterTick --;
            if (!this.level().isClientSide()) {
                if (this.isInFluidType() && this.waterTick <= 0) {
                    this.level().broadcastEntityEvent(this, (byte) 3);
                    if (this.backCount >= this.maxBackCount() + this.addMaxBackCount) {
                        this.discard();
                    }
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.99, -0.90, 0.99));
                    this.backCount += 1;
                    this.waterTick = 10;
                }
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.THROWING_STUBBORN_STONE.get();
    }
}
