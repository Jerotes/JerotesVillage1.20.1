package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class GlassThrowingBallEntity extends BaseThrowingBallEntity {
    public GlassThrowingBallEntity(EntityType<? extends GlassThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GlassThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.GLASS_THROWING_BALL.get(), livingEntity, level);
    }

    public GlassThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.GLASS_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 4f;
    }
    @Override
    public float addDamage() {
        return 8f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }
    @Override
    public void aboutEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!livingEntity.level().isClientSide) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this.getOwner());
            }
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 32; i++) {
                double dx = (this.level().random.nextDouble() - 0.5) * 2.0;
                double dy = (this.level().random.nextDouble() - 0.5) * 2.0;
                double dz = (this.level().random.nextDouble() - 0.5) * 2.0;
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(JerotesVillageItems.GLASS_THROWING_BALL.get())), entity.getX() + dx, entity.getY(0.1) + dy , entity.getZ() + dz, 0, dx, dy, dz, 0.0D);
            }
        }
        double distance = (deltaMoveDamage + this.speedDamage) / 4;
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(distance, distance, distance));
        for (LivingEntity attack : list) {
            if (attack == null) continue;
            if (attack == entity) continue;
            if (this.getOwner() instanceof LivingEntity livingEntityOwner && AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
            if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
            if (!Main.hasLineOfSightEntity(attack, this)) continue;
            float f = (float) (this.addDamage() + (1 + this.baseDamage() / 10) * (deltaMoveDamage + this.speedDamage));
            boolean bl = attack.hurt(this.damageSources().thrown(this, this.getOwner()), f / 3f);
            if (bl) {
                if (!attack.level().isClientSide) {
                    attack.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this.getOwner());
                }
            }
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 32; i++) {
                    double dx = (this.level().random.nextDouble() - 0.5) * 2.0;
                    double dy = (this.level().random.nextDouble() - 0.5) * 2.0;
                    double dz = (this.level().random.nextDouble() - 0.5) * 2.0;
                    serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(JerotesVillageItems.GLASS_THROWING_BALL.get())), attack.getX() + dx, attack.getY(0.1) + dy , attack.getZ() + dz, 0, dx, dy, dz, 0.0D);
                }
            }
        }
        if (!this.isSilent()) {
            this.playSound(SoundEvents.GLASS_BREAK, 3.0f, 1.0f);
        }
    }
    @Override
    public void aboutBreak(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            double distance = (deltaMoveDamage + this.speedDamage) / 4;
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(distance, distance, distance));
            for (LivingEntity attack : list) {
                if (attack == null) continue;
                if (this.getOwner() instanceof LivingEntity livingEntityOwner && AttackFind.FindCanNotAttack(livingEntityOwner, attack))
                    continue;
                if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
                if (!Main.hasLineOfSightEntity(attack, this)) continue;
                float f = (float) (this.addDamage() + (1 + this.baseDamage() / 10) * (deltaMoveDamage + this.speedDamage));
                boolean bl = attack.hurt(this.damageSources().thrown(this, this.getOwner()), f / 3f);
                if (bl) {
                    if (!attack.level().isClientSide) {
                        attack.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this.getOwner());
                    }
                }
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 32; i++) {
                        double dx = (this.level().random.nextDouble() - 0.5) * 2.0;
                        double dy = (this.level().random.nextDouble() - 0.5) * 2.0;
                        double dz = (this.level().random.nextDouble() - 0.5) * 2.0;
                        serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(JerotesVillageItems.GLASS_THROWING_BALL.get())), attack.getX() + dx, attack.getY(0.1) + dy , attack.getZ() + dz, 0, dx, dy, dz, 0.0D);
                    }
                }
            }
            if (!this.isSilent()) {
                this.playSound(SoundEvents.GLASS_BREAK, 3.0f, 1.0f);
            }
        }
    }
    @Override
    public ParticleOptions tailParticles() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.GLASS.defaultBlockState());
    }
    @Override
    public float particleSpeed() {
        return 0.4f;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.GLASS_THROWING_BALL.get();
    }
}
