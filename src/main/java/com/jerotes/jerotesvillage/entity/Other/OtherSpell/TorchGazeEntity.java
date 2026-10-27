package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class TorchGazeEntity extends OtherSpellBaseEntity implements TraceableEntity, OwnableEntity, MagicAbout {
    private static final int WARN_START = 5;
    private static final int FALL_END   = 32;
    private static final int BURN_SOUND_INTERVAL = 12;

    public TorchGazeEntity(EntityType<? extends TorchGazeEntity> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public TorchGazeEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.TORCH_GAZE.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
        this.noCulling = true;
    }

    public int getBaseSpellLevelDamage() {
        return 5;
    }
    public int getMaxStart() {
        return 40;
    }
    public int getMaxLife() {
        return 60;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level() instanceof ServerLevel serverLevel) {
            int t = this.getLifeTick();
            if (t == WARN_START) {
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.BLAZE_SHOOT,
                        SoundSource.NEUTRAL, 1.3F, 0.7F);
            }
            if (t == FALL_END) {
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE,
                        SoundSource.NEUTRAL, 1.0F, 1.2F);
            }
            if (t > FALL_END && t < this.getMaxLife() && (t - FALL_END) % BURN_SOUND_INTERVAL == 0) {
                float pitch = 0.85F + this.getRandom().nextFloat() * 0.3F;
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.FIRE_AMBIENT,
                        SoundSource.NEUTRAL, 2F, pitch);
            }
            if (t >= 20 && t <= 30 && t % 5 == 0) {
                float pitch = 0.85F + this.getRandom().nextFloat() * 0.3F;
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.BLAZE_SHOOT,
                        SoundSource.NEUTRAL, 2F, pitch);
            }
        }
        //攻击
        if (this.getLifeTick() == 20) {
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(4f,8.5f,4f).move(0,9f,0));
            double radius = 2.25f;
            double radiusSq = radius * radius;
            list.removeIf(livingEntity -> livingEntity.distanceToSqr(this) > (radiusSq + ((Main.mobWidth(livingEntity) / 2) * (Main.mobWidth(livingEntity) / 2))));
            if (this.level() instanceof ServerLevel serverLevel) {
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (!AttackFind.FindCanNotAttack(this.getOwner(), entity)) {
                            this.dealDamageTo(livingEntity);
                        }
                    }
                }
            }
        }
    }

    public boolean dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return false;
        }
        if (AttackFind.FindCanNotAttack(this.getOwner(), livingEntity))
            return false;
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this);
        DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FIRE, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this, livingEntity2);
            damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FIRE, this, livingEntity2);
        }
        boolean bl = livingEntity.hurt(damageSource,
                Main.rollDice(4 + getSpellLevel() - 5, 6, getRandom())
        );
        boolean bl2 = livingEntity.hurt(damageSource2,
                Main.rollDice(4 + getSpellLevel() - 5, 6, getRandom())
        );
        if (bl) {
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 24; ++i) {
                    serverLevel.sendParticles(JerotesParticleTypes.CURE_WOUNDS.get(), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        if (bl2) {
            livingEntity.setSecondsOnFire(6 + getSpellLevel() * 3);
        }
        return true;
    }

    public boolean shouldRenderAtSqrDistance(double p_20869_) {
        double d0 = 64.0D * getViewScale();
        return p_20869_ < d0 * d0;
    }
}