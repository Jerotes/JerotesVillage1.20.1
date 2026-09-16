package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WhirlpoolEntity extends MagicAboutEntity {
    public WhirlpoolEntity(EntityType<? extends WhirlpoolEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WhirlpoolEntity(EntityType<? extends WhirlpoolEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public WhirlpoolEntity(int spellLevelDamage, int spellLevelMoveDistance, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.WHIRLPOOL.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMoveDistance = spellLevelMoveDistance;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            Entity entity2 = this.getOwner();
            //水类伤害
            float damageScale = 1;
            if (livingEntity.isSensitiveToWater()) {
                damageScale = 3f;
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MAGIC, this, this.getOwner());
            if (livingEntity.hasEffect(MobEffects.WATER_BREATHING) || livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                if (livingEntity.isInWater()) {
                    livingEntity.hurt(damageSource,
                            Main.rollDice(spellLevelDamage * 3, 4, this.random) / 3 * damageScale
                    );
                }
                else {
                    livingEntity.hurt(this.damageSources().indirectMagic(this, entity2),
                            Main.rollDice(spellLevelDamage * 3, 4, this.random) / 3 * damageScale
                    );
                }
            }
            else {
                if (livingEntity.isInWater()) {
                    livingEntity.hurt(damageSource,
                            Main.rollDice(spellLevelDamage * 3, 4, this.random) * damageScale
                    );
                } else {
                    livingEntity.hurt(this.damageSources().indirectMagic(this, entity2),
                            Main.rollDice(spellLevelDamage * 3, 4, this.random) * damageScale
                    );
                }
            }
            if (entity2 != null) {
                if ((Main.mobSizeSmall(entity2) || Main.mobSizeMedium(entity2) || Main.mobSizeLarge(entity2)) && !EntityAndItemFind.isNoSpecialKnockback(entity2.getType())) {
                    double d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) + spellLevelMoveDistance * 0.1;
                    double d3 = Math.max(spellLevelMoveDistance * 0.1, 1.0 + spellLevelMoveDistance * 0.1 - d);
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add((entity2.getX() - livingEntity.getX()) * 0.35 * d3, 0, 0));
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, (entity2.getY() - livingEntity.getY()) * 0.25 * d3, 0));
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 0, (entity2.getZ() - livingEntity.getZ()) * 0.35 * d3));
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(JerotesVillageParticleTypes.WHIRLPOOL.get(), this.getX(), (this.getY() + this.getEyeY())/2, this.getZ(), 0, 0, 0);
    }

    @Override
    public int getMaxLife() {
        return 80;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.BUBBLE_COLUMN_UP;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.WHIRLPOOL.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.85f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
}