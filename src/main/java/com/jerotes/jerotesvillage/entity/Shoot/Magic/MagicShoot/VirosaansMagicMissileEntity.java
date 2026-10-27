package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class VirosaansMagicMissileEntity extends BaseMagicBoltEntity {
    public VirosaansMagicMissileEntity(EntityType<? extends VirosaansMagicMissileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VirosaansMagicMissileEntity(EntityType<? extends VirosaansMagicMissileEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public VirosaansMagicMissileEntity(int spellLevelDamage, int spellSubcategory, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.VIROSAANS_MAGIC_MISSILE.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellSubcategory = spellSubcategory;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            this.dealDamageTo(livingEntity);
        }
    }

    protected boolean dealDamageTo(LivingEntity livingEntity) {
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable()) {
            return false;
        }
        DamageSource damageSourceRadiant = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_NECROTIC, this, this.getOwner());
        boolean bl = livingEntity.hurt(damageSourceRadiant, spellLevelDamage);
        if (bl) {
            if (this.level() instanceof ServerLevel serverLevel) {
                ParticlesUse.ballRendererParticle(serverLevel,
                        this.getX(), this.getY(0.5f), this.getZ(),
                        beamLightIII(), beamLightII(),
                        0.1f, 0.2f, 0f,
                        0f, 0.5f, 0.25f,
                        6);
            }
        }
        this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
        return true;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 40;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesParticleTypes.NULL.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    protected float getLiquidInertia() {
        return 1.0f;
    }

    public int beamLightI() {
        return 0x9a9d97;
    }
    public int beamLightII() {
        return 0xe0e0e0;
    }
    public int beamLightIII() {
        return 0x5e7052;
    }

    public ResourceLocation TextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID,
                "textures/entity/projectiles/virosaans_magic_missile.png");
    }
}
