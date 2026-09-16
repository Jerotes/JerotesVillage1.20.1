package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class IceRockThrowingBallEntity extends BaseThrowingBallEntity {
    public IceRockThrowingBallEntity(EntityType<? extends IceRockThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceRockThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ICE_ROCK_THROWING_BALL.get(), livingEntity, level);
    }

    public IceRockThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ICE_ROCK_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 3f;
    }
    @Override
    public float addDamage() {
        return 6f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }
    @Override
    public void aboutEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity && this.getOwner() != null && this.getOwner() instanceof LivingEntity owner) {
            if (!livingEntity.level().isClientSide) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, true), owner);
            }
        }
    }
    @Override
    public DamageSource damageSource(Entity entity) {
        return AttackFind.findDamageType(this, JerotesDamageTypes.FREEZE_SHOOT, this, entity);
    }
    @Override
    public float particleSpeed() {
        return 0;
    }
    @Override
    public ParticleOptions tailParticles() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.ICE_ROCK_THROWING_BALL.get();
    }
}
