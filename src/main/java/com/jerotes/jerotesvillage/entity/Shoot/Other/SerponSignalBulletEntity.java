package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SerponSignalBulletEntity extends Fireball {
    public static final float SPLASH_RANGE = 4.0f;

    public SerponSignalBulletEntity(EntityType<? extends SerponSignalBulletEntity> entityType, Level level) {
        super(entityType, level);
    }


    public SerponSignalBulletEntity(EntityType<? extends SerponSignalBulletEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
        this(entityType, level);
        this.moveTo(d, d2, d3, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if (d7 != 0.0) {
            this.xPower = d4 / d7 * 0.1;
            this.yPower = d5 / d7 * 0.1;
            this.zPower = d6 / d7 * 0.1;
        }
    }

    public SerponSignalBulletEntity(Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SERPON_SIGNAL_BULLET.get(), livingEntity, d, d2, d3, level);
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (entity.hurt(this.damageSources().fireball(this, entity2), 7.0f)) {
            entity.setSecondsOnFire(8);
        }
        this.doEnchantDamageEffects((LivingEntity)entity2, entity);
        this.level().broadcastEntityEvent(this, (byte)3);
    }


    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        if ((this.level().getBlockState(BlockPos.containing(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z))).getBlock() == Blocks.WATER) {
            return;
        }
        BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        BlockPos blockPos2 = blockHitResult.getBlockPos();
        Main.igniteBlock(this.level(), blockPos, this.getOwner());
        Main.igniteBlock(this.level(), blockPos2, this.getOwner());
        Main.placeFire(this.level(), blockPos, this.getOwner());
        Main.placeFire(this.level(), blockPos2, this.getOwner());
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    public int life = 0;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life == 19 && this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 240; ++i) {
                serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * 30d), this.getY() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * 30d), this.getZ() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * 30d), 0, 0.0d, 0.0d, 0.0d, 0.0d);
            }
        }
        if (this.life >= 20) {
            this.discard();
        }
        else {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05, -this.getDeltaMovement().y * 0.5, this.random.nextGaussian() * 0.05);
            this.life += 1;
        }
        if (!this.isNoGravity()) {
            Vec3 vec33 = this.getDeltaMovement();
            this.setDeltaMovement(vec33.x, vec33.y + 0.15, vec33.z);
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SERPON_SIGNAL_BULLET.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.9f;
    }
}
