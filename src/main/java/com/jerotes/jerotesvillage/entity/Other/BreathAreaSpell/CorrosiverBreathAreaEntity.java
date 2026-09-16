package com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell;

import com.jerotes.jerotes.entity.Other.BreathAreaSpell.BaseBreathAreaEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;

public class CorrosiverBreathAreaEntity extends BaseBreathAreaEntity implements TraceableEntity, OwnableEntity, MagicAbout {

    public CorrosiverBreathAreaEntity(EntityType<? extends CorrosiverBreathAreaEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CorrosiverBreathAreaEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.CORROSIVER_BREATH_AREA.get(), p_36926_);
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
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.CORROSION, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.CORROSION, this, livingEntity2);
        }
        if (livingEntity2 != null && AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
            return false;
        }
        if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()));
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(),
                        (6 + (getSpellLevel() - 1) * 3) * 20,
                        (int)Math.ceil((getSpellLevel()) / 2.0)), this.getOwner() != null ? this.getOwner() : this);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                        (6 + (getSpellLevel() - 1) * 3) * 20,
                        (int)Math.ceil((getSpellLevel()) / 2.0)), this.getOwner() != null ? this.getOwner() : this);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                        (6 + (getSpellLevel() - 1) * 3) * 20,
                        (int)Math.ceil((getSpellLevel()) / 2.0)), this.getOwner() != null ? this.getOwner() : this);
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
        } else {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()) / 2);
            if (bl) {
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
        }
        return true;
    }


    public ParticleOptions getParticle() {
        return JerotesVillageParticleTypes.CORROSIVER_BREATH.get();
    }
    public int beamLightI() {
        return 0x1fafc2;
    }
    public int beamLightII() {
        return 0x74e0ee;
    }
    public int beamLightIII() {
        return 0x15747c;
    }
}

