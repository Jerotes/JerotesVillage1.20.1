package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BotanizeBulletEntity extends MagicAboutEntity {
    public BotanizeBulletEntity(EntityType<? extends BotanizeBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BotanizeBulletEntity(EntityType<? extends BotanizeBulletEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public BotanizeBulletEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.BOTANIZE_BULLET.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity && !(entity instanceof BotanizeEntity)) {
            Entity entity2 = this.getOwner();
            DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.THORNS, this, this.getOwner());
            if (!entity.hurt(damageSource,
                    Main.rollDice(1, 8, livingEntity.getRandom()) + 1
            )) {
                entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()),
                        Main.rollDice(1, 8, livingEntity.getRandom()) + 1
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
        if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hitResult).getEntity() instanceof BotanizeEntity) {
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
        return ParticleTypes.CHERRY_LEAVES;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.BOTANIZE_BULLET.get()) : itemStack;
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
                "textures/entity/projectiles/botanize_bullet.png");
    }
}