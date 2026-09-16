package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BitterColdIceSpikeEntity extends MagicAboutThrowEntity {
    public BitterColdIceSpikeEntity(EntityType<? extends BitterColdIceSpikeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BitterColdIceSpikeEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.BITTER_COLD_ICE_SPIKE.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }

    public BitterColdIceSpikeEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.BITTER_COLD_ICE_SPIKE.get(), d, d2, d3, level);
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
            livingEntity.hurt(damageSource,
                    Main.rollDice(3 + (getSpellLevel() - 2) * 2, 8, livingEntity.getRandom()) + 1
            );
            if (livingEntity.getTicksFrozen() < 140 + 20 * spellLevelFreezeTime) {
                livingEntity.setTicksFrozen(140 + 20 * spellLevelFreezeTime);
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
        return 0f;
    }
}
