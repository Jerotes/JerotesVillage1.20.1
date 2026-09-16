package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Other.SpellCloud.SpellCloudEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class SmokeMissileEntity extends MagicAboutEntity {
    public SmokeMissileEntity(EntityType<? extends SmokeMissileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SmokeMissileEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelHeight, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SMOKE_MISSILE.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
        this.spellLevelOtherEffectTime = spellLevelOtherEffectTime;
        this.spellLevelOtherEffectLevel = spellLevelOtherEffectLevel;
        this.spellLevelHeight = spellLevelHeight;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            Entity entity2 = this.getOwner();
            boolean bl = livingEntity.hurt(this.damageSources().indirectMagic(this, entity2), spellLevelDamage * 4);
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), this.getEffectSource());
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * spellLevelOtherEffectTime, spellLevelOtherEffectLevel - 1), this.getEffectSource());
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() == HitResult.Type.BLOCK && (this.level().getBlockState(BlockPos.containing(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z))).getBlock() instanceof LiquidBlock) {
            return;
        }
        if (!this.level().isClientSide) {
            this.Cloud();
            this.discard();
        }
    }
    @Override
    public void tick() {
        if (this.life >= this.getMaxLife()) {
            if (!this.level().isClientSide) {
                this.Cloud();
            }
        }
        super.tick();
        for (int i = 0; i < 5 + life; ++i) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * (life * 0.3)), this.getY() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * (life * 0.3)), this.getZ() + ((this.random.nextDouble() - 0.5d) * getBbWidth() * (life * 0.3)), 0.0d, 0.0d, 0.0d);
        }
    }

    @Override
    public int getMaxLife() {
        return 30;
    }

    public boolean Cloud() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0, 2.0, 4.0));
        for (int n = 0; n < spellLevelHeight; ++n) {
            SpellCloudEntity areaEffectCloud = new SpellCloudEntity(this.level(), this.getX(), this.getY() + n, this.getZ());
            Entity entity = this.getOwner();
            if (entity instanceof LivingEntity) {
                areaEffectCloud.setOwner((LivingEntity) entity);
            }
            areaEffectCloud.spellLevelDamage = this.spellLevelDamage;
            areaEffectCloud.setParticle(ParticleTypes.LARGE_SMOKE);
            areaEffectCloud.setParticleScaleMultiple(0.15f);
            areaEffectCloud.setRadius(1f);
            areaEffectCloud.setDuration(20 * spellLevelMainEffectTime);
            areaEffectCloud.setRadiusPerTick((6.0f - areaEffectCloud.getRadius()) / (float) areaEffectCloud.getDuration());
            areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1));
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.distanceToSqr(livingEntity);
                    if (!(d < 16.0)) continue;
                    areaEffectCloud.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    break;
                }
            }
            this.playSound(SoundEvents.SMOKER_SMOKE, 1.0f, 1.0f);
            this.level().addFreshEntity(areaEffectCloud);
        }
        return true;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.LARGE_SMOKE;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SMOKE_MISSILE.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
}
