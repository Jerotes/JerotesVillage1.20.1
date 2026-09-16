package com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell;

import com.jerotes.jerotes.entity.Other.ShootTargetSpell.AbstractFireballEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class TakeRootEntity extends AbstractFireballEntity {
    public TakeRootEntity(EntityType<? extends TakeRootEntity> entityType, Level level) {
        super(entityType, level);
    }

    public TakeRootEntity(Level level, LivingEntity owner) {
        this(JerotesVillageEntityType.TAKE_ROOT.get(), level);
        this.setOwner(owner);
    }

    @Override
    protected void performExplosion() {
        this.level().broadcastEntityEvent(this, (byte) 3);
        DamageSource damageSources = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this, this.getOwner());
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
                        Main.rollDice(8 + (getSpellLevel() - 5), 8, this.random)
                );
                if (bl) {
                    if (!EntityAndItemFind.savingSpellThrow(livingHurt)) {
                        if (!this.level().isClientSide()) {
                            livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 120, 0), this.getOwner());
                        }
                        if (!this.level().isClientSide()) {
                            livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 120, 0), this.getOwner());
                        }
                    }
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
        if (tick == 0) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GRASS_PLACE, this.getSoundSource(), 4.0f, 0.5f);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GRASS_PLACE, this.getSoundSource(), 4.0f, 2.5f);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, this.getSoundSource(), 4.0f, 0.5f);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, this.getSoundSource(), 4.0f, 2.5f);
        }
    }

    protected int trailMaxLife() {
        return 12;
    }

    protected int trailMaxPoints() {
        return 12;
    }

    @Override
    public int beamLightI() {
        return 0x65ae26;
    }
    @Override
    public int beamLightII() {
        return 0x8fe132;
    }
}
