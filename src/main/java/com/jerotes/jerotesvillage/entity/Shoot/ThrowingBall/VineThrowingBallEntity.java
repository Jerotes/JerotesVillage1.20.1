package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class VineThrowingBallEntity extends BaseThrowingBallEntity {
    public VineThrowingBallEntity(EntityType<? extends VineThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VineThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.VINE_THROWING_BALL.get(), livingEntity, level);
    }

    public VineThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.VINE_THROWING_BALL.get(), d, d2, d3, level);
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
        if (entity instanceof LivingEntity livingEntity && this.getOwner() != null && this.getOwner() instanceof LivingEntity owner && livingEntity.getHealth() <= owner.getHealth() * 5) {
            Main.addEffectMax(livingEntity, owner, JerotesMobEffects.ENSLAVEMENT.get(), 5, 0, false, true, 20);
        }
    }
    @Override
    public ParticleOptions tailParticles() {
        return ParticleTypes.CHERRY_LEAVES;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.VINE_THROWING_BALL.get();
    }
}
