package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.OminousBombFragmentEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class OminousBombEntity extends ThrowableItemProjectile {
    public OminousBombEntity(EntityType<? extends OminousBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OminousBombEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.OMINOUS_BOMB.get(), livingEntity, level);
    }

    public OminousBombEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.OMINOUS_BOMB.get(), d, d2, d3, level);
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
            this.boom += 30;
            this.setDeltaMovement(0d, 0d, 0d);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level().isClientSide) {
            this.boom += 30;
            this.setDeltaMovement(0d, 0d, 0d);
            this.setNoGravity(true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.boom >= 40) {

            if (!this.level().isClientSide()) {
                this.level().broadcastEntityEvent(this, (byte)3);
                float distance = 3f;
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 2.5f, 1.0F);
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 20; ++i) {
                        RandomSource random = RandomSource.create();
                        double d = random.nextGaussian() * 0.02;
                        double d2 = random.nextGaussian() * 0.02;
                        double d3 = random.nextGaussian() * 0.02;
                        float x = (float) this.getX((2.0 * random.nextDouble() - 1.0) * ((distance/2 + random.nextDouble() * 2)));
                        float y = (float) this.getY((2.0 * random.nextDouble() - 1.0) * ((distance/3 + random.nextDouble() * 2)));
                        float z = (float) this.getZ((2.0 * random.nextDouble() - 1.0) * ((distance/2 + random.nextDouble() * 2)));
                        serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(JerotesVillageItems.OMINOUS_BOMB.get())), x, y, z, 0, d, d2, d3, 0);
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 0, d, d2, d3, 0);
                    }
                }
                List<LivingEntity> lists = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(distance, distance, distance));
                for (LivingEntity hurt : lists) {
                    if (this.getOwner() instanceof LivingEntity living && AttackFind.FindCanNotAttack(living, hurt)) continue;
                    if (!Main.hasLineOfSightEntity(hurt, this)) continue;
                    DamageSource damageSources = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this, this.getOwner());
                    hurt.hurt(damageSources, 15 / (Math.max(1, (this.distanceTo(hurt)))));

                    double d = 0.0;
                    if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                        d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
                    }
                    double d2 = Math.max(0, 1 - d);
                    if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
                        hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.1 * (d2 + 0.3), -(this.getY() - hurt.getY()) * 0.1 * (d2 + 0.35), -(this.getZ() - hurt.getZ()) * 0.1 * (d2 + 0.3)));
                    }
                }
                for (int i = 0; i < 12; ++i) {
                    OminousBombFragmentEntity abstractArrow = new OminousBombFragmentEntity(JerotesVillageEntityType.OMINOUS_BOMB_FRAGMENT.get(), this.level());
                    abstractArrow.setPos(this.getX(), this.getY(), this.getZ());
                    abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().add((this.random.nextFloat() * 2 - 1)/4,0.75f, (this.random.nextFloat() * 2 - 1)/4));
                    if (this.getOwner() != null) {
                        abstractArrow.setOwner(this.getOwner());
                    }
                    this.level().addFreshEntity(abstractArrow);
                }
                this.discard();
            }
        }
        else {
            this.boom += 1;
        }
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.OMINOUS_BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }
}
