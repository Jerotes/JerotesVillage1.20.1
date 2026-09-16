package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WhipTheTideVineEntity extends Entity implements TraceableEntity, OwnableEntity {
    private int warmupDelayTicks;
    private int spellLevelDamage = 5;
    private boolean sentSpikeEvent;
    private int lifeTicks = 30;
    private boolean clientSideAttackStarted;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public WhipTheTideVineEntity(EntityType<? extends WhipTheTideVineEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WhipTheTideVineEntity(Level level, double d, double d2, double d3, float f, int n, LivingEntity livingEntity) {
        this(JerotesVillageEntityType.WHIP_THE_TIDE_VINE.get(), level);
        this.warmupDelayTicks = n;
        if (livingEntity != null) {
            this.setOwner(livingEntity);
        }
        this.setYRot(f * 57.295776f);
        this.setPos(d, d2, d3);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel) this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity) entity;
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
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }

    public void setSpellLevelDamage(int n) {
        this.spellLevelDamage = n;
    }

    public int getSpellLevelDamage() {
        return this.spellLevelDamage;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.warmupDelayTicks = compoundTag.getInt("Warmup");
        this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Warmup", this.warmupDelayTicks);
        compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks == 18) {
                    for (int i = 0; i < 12; ++i) {
                        double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double) this.getBbWidth() * 0.5;
                        double d2 = this.getY() + 0.05 + this.random.nextDouble();
                        double d3 = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double) this.getBbWidth() * 0.5;
                        double d4 = (this.random.nextDouble() * 2.0 - 1.0) * 0.005;
                        double d5 = this.random.nextDouble() * 0.005;
                        double d6 = (this.random.nextDouble() * 2.0 - 1.0) * 0.005;
                        this.level().addParticle(JerotesVillageParticleTypes.CELESTIAL_COILVINE_CHANGE.get(), d, d2 + 1.0, d3, d4, d5, d6);
                    }
                }
            }
        } else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -8) {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2));
                for (LivingEntity livingEntity : list) {
                    this.dealDamageTo(livingEntity);
                }
            }
            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte) 4);
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
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
            livingEntity.hurt(damageSource, Main.rollDice(2, 4, this.random));
        } else {
            if (AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
                return;
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this, livingEntity2);
            livingEntity.hurt(damageSource, Main.rollDice(2, 4, this.random));
        }
    }

    @Override
    public void handleEntityEvent(byte by) {
        super.handleEntityEvent(by);
        if (by == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
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
        return 1.0f - ((float) n - f) / 20.0f;
    }

    public static boolean placeVine(Level level, double d, double d2, double d3, float f, int n, Entity entity, int spellLevelDamage) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        WhipTheTideVineEntity whipTheTideVine = new WhipTheTideVineEntity(serverLevel, d, d2, d3, f, n, entity instanceof LivingEntity livingEntity ? livingEntity : null);
        whipTheTideVine.setSpellLevelDamage(spellLevelDamage);
        if (entity instanceof LivingEntity livingEntity) {
            PlayerTeam teams = (PlayerTeam) livingEntity.getTeam();
            if (livingEntity.getTeam() != null) {
                serverLevel.getScoreboard().addPlayerToTeam(whipTheTideVine.getStringUUID(), teams);
            }
        }
        serverLevel.addFreshEntity(whipTheTideVine);
        return true;
    }
}
