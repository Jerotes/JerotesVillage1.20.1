package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CorrosiveIceSpikeEntity extends MagicAboutThrowEntity {
    public CorrosiveIceSpikeEntity(EntityType<? extends CorrosiveIceSpikeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CorrosiveIceSpikeEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.CORROSIVE_ICE_SPIKE.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }

    public CorrosiveIceSpikeEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.CORROSIVE_ICE_SPIKE.get(), d, d2, d3, level);
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
            DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.FREEZE_MAGIC, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource,
                    Main.rollDice(4 + (getSpellLevel() - 3), 4, livingEntity.getRandom())
            );
            if (livingEntity.getTicksFrozen() < 140 + 20 * spellLevelFreezeTime) {
                livingEntity.setTicksFrozen(140 + 20 * spellLevelFreezeTime);
            }
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1), this.getEffectSource());
            }
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
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 80;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.ICE_ROCK.get();
    }

    @Override
    protected float getGravity() {
        return 0.03F;
    }
}