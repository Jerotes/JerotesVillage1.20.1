package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class VirosaansWindOfSlaughterEffectEntity extends OtherSpellBaseEntity implements TraceableEntity, OwnableEntity, MagicAbout {
    public VirosaansWindOfSlaughterEffectEntity(EntityType<? extends VirosaansWindOfSlaughterEffectEntity> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
    }

    public VirosaansWindOfSlaughterEffectEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.VIROSAANS_WIND_OF_SLAUGHTER_EFFECT.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
        this.fixupDimensions();
    }
    public int getBaseSpellLevelDamage() {
        return 9;
    }
    public boolean scaleAboutBoundingBox() {
        return this.getLifeTick() > 20;
    }
    public boolean isInvulnerable() {
        return true;
    }
    public int getMaxStart() {
        return 10;
    }
    public int getMaxLife() {
        return 365 * 24 * 60 * 60 * 20 + 15;
    }
    public boolean moveToTarget() {
        return true;
    }
    public void moveToTargetTick() {
        Entity target = this.getTarget();
        if (target != null) {
            this.moveTo(this.getTarget().getX(), this.getTarget().getY(0.5), this.getTarget().getZ());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getLifeTick() <= this.getMaxLife()) {
            if (this.level() instanceof ServerLevel serverLevel && this.getTrailParticle() != null) {
                serverLevel.sendParticles(this.getTrailParticle(), this.getX() + ((this.random.nextDouble() - 0.5d)), this.getY() + ((this.random.nextDouble() - 0.5d) * getSize() * 1), this.getZ() + ((this.random.nextDouble() - 0.5d) * getSize() * 1), 0, 0, 0, 0, 0);
                Entity target = this.getTarget();
                if (target != null) {
                    serverLevel.sendParticles(JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get(),
                            this.getTarget().getRandomX(0.5),  this.getTarget().getRandomY(),  this.getTarget().getRandomZ(0.5), 1, 0, 0, 0, 0);
                }
            }
        }



        if (!this.level().isClientSide()) {
            if (getTarget() instanceof LivingEntity livingEntity && getTarget().isAlive()) {
                if (!livingEntity.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())) {
                    stop();
                }
            }
            else {
                stop();
            }
        }

        //传播
        if (getTarget() instanceof LivingEntity  && getTarget().isAlive()) {
            if (this.getLifeTick() % 120 == 0 && this.getLifeTick() != 0) {
                List<VirosaansWindOfSlaughterEffectEntity> listEffect = this.level().getEntitiesOfClass(VirosaansWindOfSlaughterEffectEntity.class, this.getBoundingBox().deflate(Main.mobWidth(this) / 2, Main.mobHeight(this) / 2, Main.mobWidth(this) / 2).inflate(6,6,6));
                listEffect.removeIf(effect -> effect.getTarget() != this.getTarget() || effect == this);
                for (VirosaansWindOfSlaughterEffectEntity effect : listEffect) {
                    if (effect.getId() < this.getId())
                        effect.discard();
                }

                    List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(6,6,6));
                list.removeIf(livingEntity -> livingEntity.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get()));
                for (LivingEntity entity : list) {
                    if (!AttackFind.FindCanNotAttack(this.getOwner(), entity) && Main.hasLineOfSightPos(entity.position(),this)) {
                        dealDamageTo(entity);
                    }
                }
                for (int i = 0; i < 5; ++i) {
                    ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get(), true, 6.0f, 0.08f);
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
        if (livingEntity2 != null && AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
            return false;
        }
        if (!( this.getTarget() instanceof LivingEntity effect && effect.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get()) && effect.getEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get()) != null))
            return false;
        ParticlesUse.sendBallParticles(livingEntity, JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get(), false, 3.0f, 0.1f);
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this, livingEntity2);
        }
        livingEntity.hurt(damageSource, 1);
        livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get(),
                Objects.requireNonNull(effect.getEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())).getDuration(), Objects.requireNonNull(effect.getEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())).getAmplifier()), this.getOwner() != null ? this.getOwner() : this);
        if (this.getOwner() != null && livingEntity.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())) {
            if (this.level() instanceof ServerLevel serverLevel) {
                VirosaansWindOfSlaughterEffectEntity spell = new VirosaansWindOfSlaughterEffectEntity(serverLevel, this.getOwner());
                spell.setSpellLevelDamage(getSpellLevel());
                spell.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                spell.setOwnerUUID(this.getOwner().getUUID());
                spell.setTarget(livingEntity);
                spell.setSize(Main.mobWidth(livingEntity));
                spell.setTargetHeight(Main.mobHeight(livingEntity));
                //
                spell.setLifeTick(this.getLifeTick());
                //
                serverLevel.addFreshEntity(spell);
            }
        }
        else {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this);
            if (livingEntity2 != null) {
                damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this, livingEntity2);
            }
            livingEntity.hurt(damageSource, livingEntity.getMaxHealth() * (1f/50f));
        }
        this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
        return true;
    }

    public void stop() {
        if (getTarget() instanceof LivingEntity livingEntity) {
            livingEntity.removeEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get());
        }
        this.discard();
    }

    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get();
    }

    protected float getInertia() {
        return 1.0f;
    }
    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
}
