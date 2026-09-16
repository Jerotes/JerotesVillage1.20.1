package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RottenBrightMelonEntity extends BaseThrowingBallEntity {
    public RottenBrightMelonEntity(EntityType<? extends RottenBrightMelonEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RottenBrightMelonEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ROTTEN_BRIGHT_MELON.get(), livingEntity, level);
    }

    public RottenBrightMelonEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ROTTEN_BRIGHT_MELON.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 1f;
    }
    @Override
    public float addDamage() {
        return 2f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }
    @Override
    public void aboutEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity && this.getOwner() != null && this.getOwner() instanceof LivingEntity owner) {
            if (!livingEntity.level().isClientSide) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20, 0, false, true), owner);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20, 0, false, true), owner);
            }
        }
    }
    @Override
    public ParticleOptions tailParticles() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(JerotesVillageItems.BRIGHT_MELON_SEEDS.get()));
    }
    @Override
    public float particleSpeed() {
        return 0.25f;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.ROTTEN_BRIGHT_MELON.get();
    }
}
