package com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell;

import com.jerotes.jerotes.entity.Other.BreathAreaSpell.BaseBreathAreaEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;

public class OceanCurrentBreathAreaEntity extends BaseBreathAreaEntity implements TraceableEntity, OwnableEntity, MagicAbout {

    public OceanCurrentBreathAreaEntity(EntityType<? extends OceanCurrentBreathAreaEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OceanCurrentBreathAreaEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.OCEAN_CURRENT_BREATH_AREA.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
    }

    public DamageSource getDamageSource() {
        LivingEntity livingEntity2 = this.getOwner();
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this, livingEntity2);
        }
        return damageSource;
    }
    public float getMaxReach() {
        return 27.0f;
    }
    public int getParticleCount() {
        return 160;
    }
    public float getSpellAngle() { return 22.5f; }
    public boolean dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return false;
        }
        if (AttackFind.FindCanNotAttack(this.getOwner(), livingEntity))
            return false;
        //水类伤害
        float damageScale = 1;
        if (livingEntity.isSensitiveToWater()) {
            damageScale *= 3;
        }
        if (livingEntity.hasEffect(MobEffects.WATER_BREATHING) || livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
            damageScale /= 3;
        }
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MAGIC, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MAGIC, this, livingEntity2);
        }
        if (livingEntity2 != null && AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
            return false;
        }
        if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()) * damageScale);
            if (bl) {
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
        } else {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()) / 2 * damageScale);
            if (bl) {
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
        }
        return true;
    }

    public ParticleOptions getParticle() {
        return JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH.get();
    }
    public int beamLightI() {
        return 0x0056b2;
    }
    public int beamLightII() {
        return 0x488fdb;
    }
    public int beamLightIII() {
        return 0x003c72;
    }
}

