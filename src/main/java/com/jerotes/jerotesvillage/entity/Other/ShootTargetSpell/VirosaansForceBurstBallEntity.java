package com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell;

import com.jerotes.jerotes.entity.Other.ShootTargetSpell.AbstractFireballEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class VirosaansForceBurstBallEntity extends AbstractFireballEntity {
    public static final Vector3f COLOR_CORE_FLASH = new Vector3f(1.00f, 0.92f, 0.96f);
    public static final Vector3f COLOR_BRIGHT_PINK = new Vector3f(1.00f, 0.30f, 0.70f);
    public static final Vector3f COLOR_PINK_PURPLE = new Vector3f(0.72f, 0.30f, 0.85f);
    public static final Vector3f COLOR_PALE_VIOLET = new Vector3f(0.78f, 0.68f, 0.95f);
    public static final Vector3f COLOR_NEON_PINK = new Vector3f(1.00f, 0.15f, 0.80f);

    public VirosaansForceBurstBallEntity(EntityType<? extends VirosaansForceBurstBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VirosaansForceBurstBallEntity(Level level, LivingEntity owner) {
        this(JerotesVillageEntityType.VIROSAANS_FORCE_BURST_BALL.get(), level);
        this.setOwner(owner);
    }

    @Override
    protected void performExplosion() {
        this.level().broadcastEntityEvent(this, (byte) 3);
        DamageSource damageSources = AttackFind.findDamageType(this, JerotesDamageTypes.FORCE, this, this.getOwner());
        float distance = 6;

        java.util.List<Entity> lists = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(distance + 2, distance + 2, distance + 2));
        for (Entity hurt : lists) {
            if (hurt.distanceTo(this) > distance) continue;
            float strength = (24 - this.distanceTo(hurt)) / 2f;
            if (this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), hurt)) continue;

            float visibility = getSeenPercent(hurt, this);
            float damageMultiplier = Math.max(0.0f, visibility);
            if (damageMultiplier <= 0.01f) continue;

            if (hurt instanceof LivingEntity livingHurt) {
                boolean bl = hurt.hurt(damageSources,
                        Main.rollDice(12 + (getSpellLevel() - 7), 6, this.random)
                );
                if (bl) {
                    if (!EntityAndItemFind.savingSpellThrow(livingHurt)) {
                        if (!this.level().isClientSide()) {
                            livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.PRONE.get(), 120, 0), this.getOwner());
                        }
                    }
                }

                double knock;
                if (livingHurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                    knock = livingHurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                } else {
                    knock = 0.0;
                }
                double d2 = Math.max(0.2, 1.2 - knock);
                livingHurt.setDeltaMovement(livingHurt.getDeltaMovement().add(
                        -(this.getX() - livingHurt.getX()) * d2 / 3,
                        -(this.getY() - livingHurt.getY()) * d2 / 8,
                        -(this.getZ() - livingHurt.getZ()) * d2 / 3));
                if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
                    livingHurt.setDeltaMovement(livingHurt.getDeltaMovement().add(
                            -(this.getX() - hurt.getX()) * strength * 0.2F,
                            -(this.getY() - hurt.getY()) * strength * 0.1F,
                            -(this.getZ() - hurt.getZ()) * strength * 0.2F));
                }
            }
            if (hurt instanceof ItemEntity itemEntity && itemEntity.getItem().getRarity() == Rarity.COMMON) {
                itemEntity.setDeltaMovement(0, strength / 6, 0);
            }
            if (hurt instanceof LivingEntity livingHurt && !livingHurt.level().isClientSide) {
                livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 120, 0, false, false), this);
            }
        }
        this.setDeltaMovement(0, 0, 0);
    }

    @Override
    protected void onLifeBomb(int tick) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (tick == 8) {
            Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(this.position()), 4);
        }
        if (tick >= 1 && tick <= 12) {
            float t = tick / 20f;
            float ringRadiusBlocks = Mth.clamp(4f * (t / 0.6f), 0f, 4f);
            int sparkCount = 14;
            for (int j = 0; j < sparkCount; j++) {
                float angle = (float) (j / (double) sparkCount * 2 * Math.PI + tick * 0.35f);
                double x = this.getX() + ringRadiusBlocks * Math.cos(angle);
                double z = this.getZ() + ringRadiusBlocks * Math.sin(angle);
                serverLevel.sendParticles(
                        new DustParticleOptions(COLOR_NEON_PINK, 1.1f),
                        x, this.getY() + 0.25, z, 1, 0.05, 0.02, 0.05, 0.0
                );
            }
            if (tick % 2 == 0) {
                serverLevel.sendParticles(ParticleTypes.WITCH, this.getX(), this.getY() + 0.2, this.getZ(),
                        3, ringRadiusBlocks * 0.4, 0.1, ringRadiusBlocks * 0.4, 0.0);
            }
        }

        if (tick > 12 && tick < lifeBombDuration() && tick % 5 == 0) {
            for (int j = 0; j < 2; j++) {
                double ox = (this.random.nextDouble() - 0.5) * 4.0;
                double oz = (this.random.nextDouble() - 0.5) * 4.0;
                double oy = 0.3 + this.random.nextDouble() * 1.5;
                serverLevel.sendParticles(
                        new DustParticleOptions(COLOR_NEON_PINK, 0.7f),
                        this.getX() + ox, this.getY() + oy, this.getZ() + oz,
                        1, 0.02, 0.01, 0.02, 0.0
                );
            }
        }

        if (tick == 1) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 4.0f, 0.5f);
        }
        if (tick == 5) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.AMETHYST_BLOCK_CHIME, this.getSoundSource(), 8.0f, 1.6f);
        }
    }

    @Override
    protected int lifeBombOnsetTick() {
        return 1;
    }

    @Override
    protected int lifeBombDuration() {
        return 140;
    }

    @Override
    protected int trailMaxLife() {
        return 10;
    }

    @Override
    protected int trailMaxPoints() {
        return 10;
    }

    @Override
    public int beamLightI() {
        return 0xc04e72;
    }
    @Override
    public int beamLightII() {
        return 0xffced6;
    }
}