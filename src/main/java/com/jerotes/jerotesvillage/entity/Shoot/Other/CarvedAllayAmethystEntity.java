package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CarvedAllayAmethystEntity extends ThrowableItemProjectile {
    public CarvedAllayAmethystEntity(EntityType<? extends CarvedAllayAmethystEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CarvedAllayAmethystEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.CARVED_ALLAY_AMETHYST.get(), livingEntity, level);
    }

    public CarvedAllayAmethystEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.CARVED_ALLAY_AMETHYST.get(), d, d2, d3, level);
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
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(this.damageSources().thrown(this, this), 4);
            if (this.level() instanceof ServerLevel _level) {
                for (int i = 0; i < 24; ++i) {
                    _level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_CLUSTER.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 0, 0.3, 0, 0.3, 0);
                }
            }
            this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0f, 1.0f);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (this.level() instanceof ServerLevel _level) {
            for (int i = 0; i < 24; ++i) {
                _level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_CLUSTER.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 0, 0.3, 0, 0.3, 0);
            }
        }
        this.discard();
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FALLING_SPORE_BLOSSOM;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.CARVED_ALLAY_AMETHYST.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ParticleTypes.FALLING_SPORE_BLOSSOM : new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }
}