package com.jerotes.jerotesvillage.entity.Shoot.Magic.Target;

import com.jerotes.jerotes.entity.Shoot.Magic.Target.BaseTargetEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DeadCenterEntity extends BaseTargetEntity {
    public DeadCenterEntity(EntityType<? extends DeadCenterEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DeadCenterEntity(EntityType<? extends DeadCenterEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
        this(entityType, level);
        this.moveTo(d, d2, d3, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if (d7 != 0.0) {
            this.xPower = d4 / d7 * 0.1;
            this.yPower = d5 / d7 * 0.1;
            this.zPower = d6 / d7 * 0.1;
        }
    }

    public DeadCenterEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.DEAD_CENTER.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    @Override
    public void useMagicTo(LivingEntity livingEntity) {
        super.useMagicTo(livingEntity);
        if (this.level().isClientSide) {
            return;
        }
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FORCE, this);
        if (this.getOwner() != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FORCE, this, this.getOwner());
        }
        livingEntity.hurt(damageSource,
                Main.rollDice(8 + (spellLevelDamage - 5), 10, livingEntity.getRandom())
        );
        this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 3.0f, 2.0f);
    }
    public void lastBreak() {
        this.playSound(SoundEvents.FIRE_EXTINGUISH, 3.0f, 2.0f);
    }

    @Override
    public int getUseLife() {
        return 10;
    }
    @Override
    public int getMaxLife() {
        return 20;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    protected float getLiquidInertia() {
        return 1.0f;
    }
    public int roundLightI() {
        return 0x65ae26;
    }
    public int roundLightII() {
        return 0xa2ff6f;
    }
}
