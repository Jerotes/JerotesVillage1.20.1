package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DroughtFireballEntity extends MagicAboutEntity {
    public DroughtFireballEntity(EntityType<? extends DroughtFireballEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DroughtFireballEntity(int spellLevelDamage, int spellLevelFireTime, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.DROUGHT_FIREBALL.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = spellLevelFireTime;
    }
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FIRE_MAGIC, this, this.getOwner());
            if (!entity.hurt(damageSource, Main.rollDice(1, 8, livingEntity.getRandom()) + 1)) {
                entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), Main.rollDice(1, 8, livingEntity.getRandom()) + 1);
            }
            else {
                entity.setSecondsOnFire(spellLevelFireTime);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        BlockPos blockPos2 = blockHitResult.getBlockPos();
        Main.igniteBlock(this.level(), blockPos, this.getOwner());
        Main.igniteBlock(this.level(), blockPos2, this.getOwner());
        Main.placeFire(this.level(), blockPos, this.getOwner());
        Main.placeFire(this.level(), blockPos2, this.getOwner());
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        this.discard();
    }

    @Override
    public int getMaxLife() {
        return 90;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.DROUGHT_FIREBALL.get()) : itemStack;
    }

    public boolean isNormalLittleFireball() {
        return true;
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
                "textures/entity/projectiles/drought_fireball.png");
    }
}
