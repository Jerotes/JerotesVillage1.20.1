package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class PurpleSandAlchemyBombEntity extends ThrowableItemProjectile {
    public PurpleSandAlchemyBombEntity(EntityType<? extends PurpleSandAlchemyBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PurpleSandAlchemyBombEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.PURPLE_SAND_ALCHEMY_BOMB.get(), livingEntity, level);
    }

    public PurpleSandAlchemyBombEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.PURPLE_SAND_ALCHEMY_BOMB.get(), d, d2, d3, level);
    }

    public int boom = 0;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.boom = compoundTag.getInt("Boom");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Boom", this.boom);
    }

    @Override
    public void handleEntityEvent(byte by) {
        if (by == 3) {
            ParticleOptions particleOptions = this.getParticle();
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level().isClientSide) {
            this.boom += 60;
        }
        this.setDeltaMovement(0d, 0d, 0d);
        this.moveTo(this.getPosition(0).lerp(hitResult.getEntity().getPosition(0), 0.5));
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level().isClientSide) {
            this.boom += 30;
        }
        this.setDeltaMovement(0d, 0d, 0d);
        this.moveTo(this.getPosition(0).add(0,0,0));
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().getBlockState(this.getOnPos()).isAir() || this.level().getBlockState(this.getOnPos().above()).isAir()) {
            this.setNoGravity(false);
        }
        if (this.boom >= 80) {
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte)3);
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0f, Level.ExplosionInteraction.NONE);
                this.discard();
            }
        }
        else {
            this.boom += 1;
        }
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.PURPLE_SAND_ALCHEMY_BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }
}
