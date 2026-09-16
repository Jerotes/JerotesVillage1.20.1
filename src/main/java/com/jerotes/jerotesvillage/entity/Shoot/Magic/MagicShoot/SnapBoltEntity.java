package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SnapBoltEntity extends BaseMagicBoltEntity {
    public SnapBoltEntity(EntityType<? extends SnapBoltEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SnapBoltEntity(EntityType<? extends SnapBoltEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public SnapBoltEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SNAP_BOLT.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
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
        DamageSource damageSourceRadiant = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_RADIANT, this, this.getOwner());
        DamageSource damageSourceForce = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FORCE, this, this.getOwner());
        boolean bl = livingEntity.hurt(damageSourceRadiant,
                Main.rollDice(3 + (spellLevelDamage - 4), 6, livingEntity.getRandom())
        );
        boolean bl2 = livingEntity.hurt(damageSourceForce,
                Main.rollDice(3, 6, livingEntity.getRandom())
        );
        if (bl || bl2) {
            ParticlesUse.sendBallParticles(livingEntity, JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), false, 2.0f, 0.1f);
        }
        this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
        return true;
    }

    @Override
    public void lastBreak() {
        if (!this.level().isClientSide) {
            ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), true, 3.0f, 0.14f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), true, 3.0f, 0.14f);
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 90;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get();
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
        return 0xd7f56e;
    }
    public int beamLightII() {
        return 0xf9fde7;
    }
    public int beamLightIII() {
        return 0xb7a11d;
    }

    public ResourceLocation TextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID,
                "textures/entity/projectiles/snap_bolt.png");
    }
}
