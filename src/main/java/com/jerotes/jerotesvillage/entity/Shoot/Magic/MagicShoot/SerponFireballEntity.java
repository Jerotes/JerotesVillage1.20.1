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

public class SerponFireballEntity extends MagicAboutEntity {
    public SerponFireballEntity(EntityType<? extends SerponFireballEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SerponFireballEntity(int spellLevelDamage, int spellLevelFireTime, float spellLevelExplode, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SERPON_FIREBALL.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = spellLevelFireTime;
        this.spellLevelExplode = spellLevelExplode;
    }
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FIRE_MAGIC, this, this.getOwner());
        if (!entity.hurt(damageSource,
                Main.rollDice(5 + (spellLevelDamage - 3), 8, random) + spellLevelDamage
        )) {
            entity.hurt(this.damageSources().explosion(this, this.getOwner()),
                    Main.rollDice(5 + (spellLevelDamage - 3), 8, random) + spellLevelDamage
            );
        }
        else {
            entity.setSecondsOnFire(spellLevelFireTime);
        }
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), spellLevelExplode, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        BlockPos center = blockHitResult.getBlockPos();
        Level level = this.level();
        Entity owner = this.getOwner();
        int radius = 2;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx*dx + dy*dy + dz*dz > radius*radius) continue;
                    BlockPos pos = center.offset(dx, dy, dz);
                    Main.igniteBlock(level, pos, owner);
                    Main.placeFire(level, pos, owner);
                }
            }
        }
    }

    public void lastBreak() {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), spellLevelExplode, Level.ExplosionInteraction.NONE);
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
        return ParticleTypes.FLAME;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SERPON_FIREBALL.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }

    public boolean isNormalLittleFireball() {
        return true;
    }

    public ResourceLocation getAsSpecialRendererTextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID,
                "textures/entity/projectiles/serpon_fireball.png");
    }
}
