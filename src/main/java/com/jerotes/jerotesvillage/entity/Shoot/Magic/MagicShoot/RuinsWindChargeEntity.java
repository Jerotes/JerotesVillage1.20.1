package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BreezeWindChargeEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class RuinsWindChargeEntity extends MagicAboutEntity implements ItemSupplier {
    public static final WindChargeExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new WindChargeExplosionDamageCalculator();

    public RuinsWindChargeEntity(EntityType<? extends RuinsWindChargeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RuinsWindChargeEntity(EntityType<? extends RuinsWindChargeEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public RuinsWindChargeEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.RUINS_WIND_CHARGE.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    @Override
    protected AABB makeBoundingBox() {
        float f = this.getType().getDimensions().width / 2.0f;
        float f2 = this.getType().getDimensions().height;
        float f3 = 0.15f;
        return new AABB(this.position().x - (double)f, this.position().y - 0.15000000596046448, this.position().z - (double)f, this.position().x + (double)f, this.position().y - 0.15000000596046448 + (double)f2, this.position().z + (double)f);
    }
    @Override
    public boolean canCollideWith(Entity entity) {
        if (entity instanceof RuinsWindChargeEntity) {
            return false;
        }
        return super.canCollideWith(entity);
    }
    @Override
    protected boolean canHitEntity(Entity entity) {
        if (entity instanceof RuinsWindChargeEntity) {
            return false;
        }
        if (entity.getType() == EntityType.END_CRYSTAL) {
            return false;
        }
        return super.canHitEntity(entity);
    }
    public static void explode(Entity entity, Vec3 vec3) {
        BreezeWindChargeEntity.explode(entity, vec3, 3);
    }

    public static float getSeenPercent(Vec3 vec3, Entity entity) {
        AABB aABB = entity.getBoundingBox();
        double d = 1.0 / ((aABB.maxX - aABB.minX) * 2.0 + 1.0);
        double d2 = 1.0 / ((aABB.maxY - aABB.minY) * 2.0 + 1.0);
        double d3 = 1.0 / ((aABB.maxZ - aABB.minZ) * 2.0 + 1.0);
        double d4 = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (d < 0.0 || d2 < 0.0 || d3 < 0.0) {
            return 0.0f;
        }
        int n = 0;
        int n2 = 0;
        for (double d6 = 0.0; d6 <= 1.0; d6 += d) {
            for (double d7 = 0.0; d7 <= 1.0; d7 += d2) {
                for (double d8 = 0.0; d8 <= 1.0; d8 += d3) {
                    double d9 = Mth.lerp(d6, aABB.minX, aABB.maxX);
                    double d10 = Mth.lerp(d7, aABB.minY, aABB.maxY);
                    double d11 = Mth.lerp(d8, aABB.minZ, aABB.maxZ);
                    Vec3 vec32 = new Vec3(d9 + d4, d10, d11 + d5);
                    if (entity.level().clip(new ClipContext(vec32, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                        ++n;
                    }
                    ++n2;
                }
            }
        }
        return (float)n / (float)n2;
    }


    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.0f;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        explode(this, this.position());
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.THUNDER, this, this.getOwner());
            if (!entity.hurt(damageSource,
                    Main.rollDice(1, 8, livingEntity.getRandom()) + 1
            )) {
                entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()),
                        Main.rollDice(1, 8, livingEntity.getRandom()) + 1
                );
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        Vec3i vec3i = blockHitResult.getDirection().getNormal();
        Vec3 vec3 = Vec3.atLowerCornerOf(vec3i).multiply(0.25, 0.25, 0.25);
        Vec3 vec32 = blockHitResult.getLocation().add(vec3);
        explode(this, vec32);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public int getMaxLife() {
        return 90;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesParticleTypes.NULL.get();
    }

    @Override
    protected ClipContext.Block getClipType() {
        return ClipContext.Block.OUTLINE;
    }
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }

    @Override
    public int getSpellLevel() {
        return spellLevelDamage;
    }

    public static final class WindChargeExplosionDamageCalculator
            extends ExplosionDamageCalculator {
        //1.20.4↑//
        //@Override
        public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
            return false;
        }
    }
}