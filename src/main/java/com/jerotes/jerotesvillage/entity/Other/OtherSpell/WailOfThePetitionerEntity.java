package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;

public class WailOfThePetitionerEntity extends OtherSpellBaseEntity implements TraceableEntity, OwnableEntity {
    public static final double RANGE = 9;
    public static final int MAX_TARGETS = 10;
    public static final float INSTANT_DEATH_HP = 50.0F;
    public static final int DEAFEN_TICKS = 20 * 60 * 60;

    private boolean used;

    public WailOfThePetitionerEntity(EntityType<? extends WailOfThePetitionerEntity> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
    }

    public WailOfThePetitionerEntity(Level level, LivingEntity owner) {
        this(JerotesVillageEntityType.WAIL_OF_THE_PETITIONER.get(), level);
        this.setOwnerUUID(owner.getUUID());
        this.fixupDimensions();
    }

    public int getBaseSpellLevelDamage() {
        return 2;
    }
    public int getMaxStart() {
        return 10;
    }
    public int getMaxLife() {
        return 62;
    }
    @Override
    public void tick() {
        super.tick();
        if (getLifeTick() == 1) {
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 6; ++i) {
                    if (this.getOwner() != null) {
                     ParticlesUse.sendBallParticles(this.getOwner(), ParticleTypes.SOUL, false, 6.0f, 0.3f);
                    }
                }
            }
        }
        if (!this.used && !this.level().isClientSide() && this.getLifeTick() >= this.getMaxStart() && this.getOwner() != null) {
            this.used = true;
            this.wail();
            if (this.level() instanceof ServerLevel serverLevel) {
                ParticlesUse.sendBallParticles(this.getOwner(), ParticleTypes.SOUL, true, 6.0f, 0.28f);
            }
        }
    }

    private void wail() {
        if (this.level().isClientSide()) {
            return;
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 6; ++i) {
                if (this.getOwner() != null) {
                    RandomSource random = RandomSource.create();
                    double d = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    double d3 = random.nextGaussian() * 0.02;
                    float x = (float) this.getOwner().getX((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    float y = (float) this.getOwner().getY((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    float z = (float) this.getOwner().getZ((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    serverLevel.sendParticles(ParticleTypes.SOUL, x, y, z, 0, d, d2, d3, 0);
                    ParticlesUse.sendBallParticles(this.getOwner(), ParticleTypes.SOUL, true, 3.0f, 0.2f);
                    ParticlesUse.sendBallParticles(this.getOwner(), ParticleTypes.SOUL, true, 6.0f, 0.3f);
                }
            }
        }
        LivingEntity owner = this.getOwner();
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(RANGE));
        list.sort(Comparator.comparingDouble(this::distanceToSqr));
        int hit = 0;
        for (LivingEntity livingEntity : list) {
            if (hit >= MAX_TARGETS) {
                break;
            }
            if (livingEntity == owner || !livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity.isSpectator()) {
                continue;
            }
            if (livingEntity instanceof Player player && player.isCreative()) {
                continue;
            }
            if (livingEntity.distanceToSqr(this) > RANGE * RANGE) {
                continue;
            }
            if (owner != null && AttackFind.FindCanNotAttack(owner, livingEntity)) {
                continue;
            }
            if (livingEntity.hasEffect(JerotesMobEffects.DEAFENED.get()) || EntityAndItemFind.targetDeafnessTrue(livingEntity)) {
                continue;
            }
            ++hit;
            this.dealDamageTo(livingEntity, owner);
        }
    }

    public boolean dealDamageTo(LivingEntity target, LivingEntity owner) {
        if (this.level().isClientSide() || target.level().isClientSide()) {
            return false;
        }
        DamageSource damageSource;
        if (owner != null) {
            damageSource = AttackFind.findDamageType(target, JerotesDamageTypes.PSYCHIC, this, owner);
        } else {
            damageSource = AttackFind.findDamageType(target, JerotesDamageTypes.PSYCHIC, this);
        }
        boolean saving = EntityAndItemFind.savingSpellThrow(target);
        float damage = Main.rollDice(3 + (getSpellLevel() - 2), 8, this.getRandom());
        if (saving) {
            damage *= 0.5f;
        }
        target.invulnerableTime = 0;
        boolean hurt = target.hurt(damageSource, damage);
        return hurt;
    }
}
