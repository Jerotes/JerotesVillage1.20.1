package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class OminousGearEntity extends Entity implements TraceableEntity, OwnableEntity, MagicAbout {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    public int lifeTicks = 120;
    private boolean clientSideAttackStarted;


    public OminousGearEntity(EntityType<? extends OminousGearEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OminousGearEntity(Level level, double d, double d2, double d3, float f, int n, LivingEntity livingEntity) {
        this(JerotesVillageEntityType.OMINOUS_GEAR.get(), level);
        this.warmupDelayTicks = n;
        this.setOwner(livingEntity);
        this.setYRot(f * 57.295776f);
        this.setPos(d, d2, d3);
    }

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }
    @Nullable
    @Override
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.getOwner() != null) {
            LivingEntity livingEntity = this.getOwner();
            if (entity == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }
    @Override
    //1.20.4↑//
    //public PlayerTeam getTeam() {
    //1.20.1//
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }

    //
    private int spellLevelDamage = 3;
    @Override
    public int getSpellLevel() {
        return this.spellLevelDamage;
    }
    public void setSpellLevelDamage(int n) {
        this.spellLevelDamage = n;
    }
    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
        compoundTag.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
        this.warmupDelayTicks = compoundTag.getInt("Warmup");
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        //常规
        if (this.level().isClientSide) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks % 2 == 0) {
                    for (int i = 0; i < 12; ++i) {
                        double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                        double d2 = this.getY() + 0.05 + this.random.nextDouble();
                        double d3 = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                        double d4 = (this.random.nextDouble() * 2.0 - 1.0) * 0.005;
                        double d5 = this.random.nextDouble() * 0.005;
                        double d6 = (this.random.nextDouble() * 2.0 - 1.0) * 0.005;
                        this.level().addParticle(ParticleTypes.CRIT, d, d2 + 0.2f, d3, d4, d5, d6);
                    }
                }
            }
        }
        else if (--this.warmupDelayTicks < 0) {
            if (this.lifeTicks % 2 == 0 && lifeTicks <= 110) {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2));
                for (LivingEntity livingEntity : list) {
                    this.dealDamageTo(livingEntity);
                }
            }
            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte)4);
                this.sentSpikeEvent = true;
            }
            if (--this.lifeTicks < 0) {
                this.discard();
            }
        }
    }

    private void dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return;
        }
        if (livingEntity2 == null) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BLEED);
            boolean bl = livingEntity.hurt(damageSource, 1.0f + 0.5f * spellLevelDamage);
            if (bl) {
                if (!livingEntity.level().isClientSide) {
                    livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
                }
            }
        }
        else {
            if (AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
                return;
            }
            if (livingEntity instanceof Mob mobTarget && EntityFactionFind.isRaider(mobTarget) && mobTarget.getTarget() != livingEntity2 && !(livingEntity2 instanceof Mob mob && mob.getTarget() == mobTarget)) {
                return;
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BLEED, livingEntity2);
            boolean bl = livingEntity.hurt(damageSource, 1.0f + 0.5f * spellLevelDamage);
            if (bl) {
                if (!livingEntity.level().isClientSide) {
                    livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), livingEntity2);
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte by) {
        super.handleEntityEvent(by);
        if (by == 4) {
            this.clientSideAttackStarted = true;
        }
    }

    public float getAnimationProgress(float f) {
        if (!this.clientSideAttackStarted) {
            return 0.0f;
        }
        int n = this.lifeTicks - 2;
        if (n <= 0) {
            return 1.0f;
        }
        return 1.0f - ((float)n - f) / 20.0f;
    }
}

