package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class IceRockThrowEntity extends MagicAboutThrowEntity {
    public IceRockThrowEntity(EntityType<? extends IceRockThrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceRockThrowEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ICE_ROCK_THROW.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }

    public IceRockThrowEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ICE_ROCK_THROW.get(), d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FREEZE_MAGIC, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource,
                    Main.rollDice(3 + (getSpellLevel() - 3), 10, livingEntity.getRandom())
            );
            DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner());
            livingEntity.hurt(damageSource2,
                    Main.rollDice(2, 10, livingEntity.getRandom())
            );
            if (bl) {
                if (livingEntity.getTicksFrozen() < 140 + 20 * spellLevelFreezeTime) {
                    livingEntity.setTicksFrozen(140 + 20 * spellLevelFreezeTime);
                }
            }
            this.playSound(SoundEvents.STONE_BREAK, 3.0f, 1.0f);
        }
    }
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        BlockPos center = blockHitResult.getBlockPos();
        Level level = this.level();
        Entity owner = this.getOwner();
        int radius = 2;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx*dx + dy*dy + dz*dz > radius*radius) continue;
                    BlockPos pos = center.offset(dx, dy, dz);
                    Main.freezeBlock(level, pos, owner);
                    Main.extinguishFire(level, pos, owner);
                }
            }
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            Main.spawnUnevenBlockByPos(serverLevel, blockHitResult.getBlockPos(), 2);
        }
        this.playSound(SoundEvents.STONE_BREAK, 3.0f, 1.0f);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level().isClientSide) {
            for (int i = 0; i < 24; ++i) {
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), (this.random.nextDouble() - 0.5d) * 0.03f, this.random.nextDouble() * 0.01f, (this.random.nextDouble() - 0.5d) * 0.03f);
            }
        }
        else {
            this.level().broadcastEntityEvent(this, (byte)3);
            if (this.level() instanceof ServerLevel _level && !JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState().isAir()) {
                for (int i = 0; i < 24; ++i) {
                    _level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), this.getX(), this.getY(), this.getZ(), 15, 0, 0, 0, 0);
                    _level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 15, (this.random.nextDouble() - 0.5d) * 0.03f, this.random.nextDouble() * 0.01f, (this.random.nextDouble() - 0.5d) * 0.03f, 0.3f);
                }
            }
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 240;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.ICE_ROCK.get();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return this.isAlive() && this.life > 10;
    }
    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive() && this.life > 10;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected float getGravity() {
        return 0.03F;
    }
}
