package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SerponFireChargeEntity extends Fireball {

    public SerponFireChargeEntity(EntityType<? extends SerponFireChargeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SerponFireChargeEntity(Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SERPON_FIRE_CHARGE.get(), livingEntity, d, d2, d3, level);
    }

    private float hits = 0;

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() == this.getOwner()) {
            return;
        }
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }

        if (hits == 1) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (!entity.hurt(this.damageSources().fireball(this, entity2), 5.0f)) {
            entity.hurt(this.damageSources().thrown(this, entity2), 4.0f);
        } {
            entity.setSecondsOnFire(8);
        }
        this.doEnchantDamageEffects((LivingEntity)entity2, entity);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
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
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.hits = 1;
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
        if (this.hits > 0) {
            hits -= 0.5f;
        }
        else {
            hits = 0;
        }
        if (this.life >= 70) {
            this.discard();
        }
        else {
            this.life += 1;
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SERPON_FIRE_CHARGE.get()) : itemStack;
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
    protected float getInertia() {
        return 0.98f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.7f;
    }
}
