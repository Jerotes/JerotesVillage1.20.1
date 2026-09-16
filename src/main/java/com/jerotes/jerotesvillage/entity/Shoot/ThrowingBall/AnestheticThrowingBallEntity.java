package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotes.entity.Interface.AnesthetizedAttackEntity;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AnestheticThrowingBallEntity extends BaseThrowingBallEntity implements AnesthetizedAttackEntity {
    public AnestheticThrowingBallEntity(EntityType<? extends AnestheticThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AnestheticThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ANESTHETIC_THROWING_BALL.get(), livingEntity, level);
    }

    public AnestheticThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ANESTHETIC_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public int getAnesthetized() {
        return 60;
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
    public ParticleOptions tailParticles() {
        return JerotesParticleTypes.ANESTHETIZED_VIII.get();
    }
    @Override
    public float particleSpeed() {
        return 0;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.ANESTHETIC_THROWING_BALL.get();
    }
}
