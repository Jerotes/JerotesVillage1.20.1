package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.Smart.SmartBulgeEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BulgeBulletEntity extends MagicAboutEntity {
    public BulgeBulletEntity(EntityType<? extends BulgeBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BulgeBulletEntity(EntityType<? extends BulgeBulletEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public BulgeBulletEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.BULGE_BULLET.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        boolean canNotHurt =
                (entity instanceof BulgeEntity && !(this.getOwner() != null && this.getOwner() instanceof Mob mob && mob.getTarget() == entity)) ||
                        (entity instanceof SmartBulgeEntity && !(this.getOwner() != null && this.getOwner() instanceof Mob mob && mob.getTarget() == entity));
        if (entity instanceof LivingEntity livingEntity && !(canNotHurt)) {
            //水类伤害
            float damageScale = 1;
            if (livingEntity.isSensitiveToWater()) {
                damageScale = 3f;
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MAGIC, this, this.getOwner());
            if (!entity.hurt(damageSource,
                    (Main.rollDice(1, 8, livingEntity.getRandom()) + 1) * damageScale
            ))
            {
                entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()),
                        (Main.rollDice(1, 8, livingEntity.getRandom()) + 1) * damageScale
                );
            }
            else {
                livingEntity.clearFire();
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hitResult).getEntity() instanceof BulgeEntity) {
            return;
        }
        if (!this.level().isClientSide) {
            this.discard();
        }
    }


    @Override
    public int getMaxLife() {
        return 90;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.BUBBLE;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.BULGE_BULLET.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }

    public ResourceLocation getAsSpecialRendererTextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID,
                "textures/entity/projectiles/bulge_bullet.png");
    }
}