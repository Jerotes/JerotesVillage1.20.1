package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

import java.util.List;

public class PotionThrowingBallEntity extends BaseThrowingBallEntity {
    public PotionThrowingBallEntity(EntityType<? extends PotionThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PotionThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.POTION_THROWING_BALL.get(), livingEntity, level);
    }

    public PotionThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.POTION_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 2f;
    }
    @Override
    public float addDamage() {
        return 4f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }
    @Override
    public void aboutEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!this.level().isClientSide) {
                List<MobEffectInstance> list = PotionUtils.getMobEffects(this.getItem());
                for (MobEffectInstance mobEffectInstance : list) {
                    if (mobEffectInstance.getEffect().isInstantenous()) {
                        mobEffectInstance.getEffect().applyInstantenousEffect(this, this.getOwner() != null ? this.getOwner() : this, livingEntity, mobEffectInstance.getAmplifier(), 0.5);
                        continue;
                    }
                    MobEffect mobEffect = new MobEffectInstance(mobEffectInstance).getEffect();
                    int amplifier = new MobEffectInstance(mobEffectInstance).getAmplifier();
                    int duration = new MobEffectInstance(mobEffectInstance).getDuration() / 10;
                    boolean bl = new MobEffectInstance(mobEffectInstance).isAmbient();
                    boolean bl2 = new MobEffectInstance(mobEffectInstance).isVisible();
                    if (this.getOwner() != null) {
                        livingEntity.addEffect(new MobEffectInstance(mobEffect, duration, amplifier, bl, bl2), this.getOwner());
                    }
                    else {
                        livingEntity.addEffect(new MobEffectInstance(mobEffect, duration, amplifier, bl, bl2));
                    }
                }
            }
        }
    }
    @Override
    public ParticleOptions tailParticles() {
        return ParticleTypes.ENTITY_EFFECT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tailParticles().getType() == ParticleTypes.ENTITY_EFFECT) {
            int n3 = PotionUtils.getColor(this.getItem());
            double mobEffectInstance = (float)(n3 >> 16 & 0xFF) / 255.0f;
            double d = (float)(n3 >> 8 & 0xFF) / 255.0f;
            double d2 = (float)(n3 & 0xFF) / 255.0f;
            float livingEntity = this.random.nextFloat() * 6.2831855f;
            float d7 = Mth.sqrt(this.random.nextFloat()) * 0.2f;
            double d3 = this.getX() + (double)(Mth.cos(livingEntity) * d7);
            double d4 = this.getY();
            double d5 = this.getZ() + (double)(Mth.sin(livingEntity) * d7);
            this.level().addAlwaysVisibleParticle(this.tailParticles(), d3, d4, d5, mobEffectInstance, d, d2);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.POTION_THROWING_BALL.get();
    }
}
