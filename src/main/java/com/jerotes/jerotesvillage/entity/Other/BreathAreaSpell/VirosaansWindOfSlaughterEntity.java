package com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell;

import com.jerotes.jerotes.entity.Other.BreathAreaSpell.BaseBreathAreaEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.VirosaansWindOfSlaughterEffectEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;

public class VirosaansWindOfSlaughterEntity extends BaseBreathAreaEntity implements TraceableEntity, OwnableEntity, MagicAbout {

    public VirosaansWindOfSlaughterEntity(EntityType<? extends VirosaansWindOfSlaughterEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VirosaansWindOfSlaughterEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.VIROSAANS_WIND_OF_SLAUGHTER.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
    }

    public int getBaseSpellLevelDamage() {
        return 9;
    }
    public float getMaxReach() {
        return 60.0f;
    }
    public int getParticleCount() {
        return 240;
    }
    public float getSpellAngle() { return 22.5f; }
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
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this, livingEntity2);
        }
        ParticlesUse.sendBallParticles(livingEntity, JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get(), false, 3.0f, 0.1f);
        livingEntity.hurt(damageSource, 1);
        if (livingEntity.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())) {
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            return true;
        }
        livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get(),
                60 * (getSpellLevel() - 8) * 20, 0), this.getOwner() != null ? this.getOwner() : this);
        if (this.getOwner() != null && livingEntity.hasEffect(JerotesVillageMobEffects.VIROSAANS_WIND_OF_SLAUGHTER.get())) {
            if (this.level() instanceof ServerLevel serverLevel) {
                VirosaansWindOfSlaughterEffectEntity spell = new VirosaansWindOfSlaughterEffectEntity(serverLevel, this.getOwner());
                spell.setSpellLevelDamage(getSpellLevel());
                spell.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                spell.setOwnerUUID(this.getOwner().getUUID());
                spell.setTarget(livingEntity);
                spell.setSize(Main.mobWidth(livingEntity));
                spell.setTargetHeight(Main.mobHeight(livingEntity));
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

    public ParticleOptions getParticle() {
        return this.getRandom().nextBoolean() ? JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER.get() : JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_SMALL.get();
    }
    public int beamLightI() {
        return 0x384138;
    }
    public int beamLightII() {
        return 0x5e6b5e;
    }
    public int beamLightIII() {
        return 0x242924;
    }
}

