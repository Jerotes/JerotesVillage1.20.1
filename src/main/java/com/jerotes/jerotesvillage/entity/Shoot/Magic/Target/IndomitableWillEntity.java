package com.jerotes.jerotesvillage.entity.Shoot.Magic.Target;

import com.jerotes.jerotes.entity.Shoot.Magic.Target.BaseTargetEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IndomitableWillEntity extends BaseTargetEntity {
    public IndomitableWillEntity(EntityType<? extends IndomitableWillEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IndomitableWillEntity(EntityType<? extends IndomitableWillEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public IndomitableWillEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.INDOMITABLE_WILL.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    @Override
    public void useMagicTo(LivingEntity livingEntity) {
        super.useMagicTo(livingEntity);
        float f = livingEntity.getHealth();
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.PSYCHIC, this, this.getOwner());
        boolean bl = livingEntity.hurt(damageSource,
                Main.rollDice(10 + (spellLevelDamage - 6), 6, livingEntity.getRandom())
        );
        if (bl && this.getOwner() != null && this.getOwner() instanceof LivingEntity living) {
            float heal = (f - livingEntity.getHealth()) / 8f;
            living.heal(heal);
            for (int i = 0; i < 12; ++i) {
                if (this.level() instanceof ServerLevel serverLevel && this.getTrailParticle() != null) {
                    serverLevel.sendParticles(this.getTrailParticle(), living.getRandomX(0.5), living.getRandomY(), living.getRandomZ(0.5), 0, 0, 0, 0, 0);
                }
            }
        }
        this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
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
        return JerotesVillageParticleTypes.INDOMITABLE_WILL.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.INDOMITABLE_WILL.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
    public int roundLightI() {
        return 0x622929;
    }
    public int roundLightII() {
        return 0x784c4c;
    }
}
