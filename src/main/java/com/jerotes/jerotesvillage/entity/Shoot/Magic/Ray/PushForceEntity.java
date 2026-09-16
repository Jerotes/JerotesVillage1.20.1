package com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray;

import com.jerotes.jerotes.entity.Shoot.Magic.Ray.BaseRayEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PushForceEntity extends BaseRayEntity {
    public PushForceEntity(EntityType<? extends PushForceEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PushForceEntity(EntityType<? extends PushForceEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public PushForceEntity(int spellLevelDamage, float spellLevelXZPush, float spellLevelXZPushBase, float spellLevelYPush, float spellLevelYPushBase, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.PUSH_FORCE.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelXZPush = spellLevelXZPush;
        this.spellLevelXZPushBase = spellLevelXZPushBase;
        this.spellLevelYPush = spellLevelYPush;
        this.spellLevelYPushBase = spellLevelYPushBase;
        this.summonTod = d;
        this.summonTod2 = d2;
        this.summonTod3 = d3;
    }

    public boolean showHitParticle() {
        return false;
    }
    protected void hitEntity(Entity entity) {
        super.hitEntity(entity);
        if (!this.isUseful())
            return;
        this.setUseful(false);
        if (this.level().isClientSide) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            double resistance = 1.5;
            if (livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                resistance = 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            }
            if (livingEntity.isShiftKeyDown()) {
                resistance -= 0.3;
            }
            if (resistance < 0) {
                resistance = 0;
            }
            Entity entity2 = this.getOwner();
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FORCE, this, this.getOwner());
            DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.THUNDER, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource,
                    Main.rollDice(5 + (spellLevelDamage - 3), 6, livingEntity.getRandom())
            );
            boolean bl2 = livingEntity.hurt(damageSource2,
                    Main.rollDice(3 + (spellLevelDamage - 3), 6, livingEntity.getRandom()));
            if ((bl || bl2) && entity2 != null) {
                Vec3 vec3 =  new Vec3(entity2.getEyePosition().x, (entity2.getPosition(0).y + entity2.getEyePosition().y) / 2, entity2.getEyePosition().z);
                Vec3 vec32 = livingEntity.getEyePosition().subtract(vec3);
                Vec3 vec33 = vec32.normalize();
                double d = spellLevelYPush * resistance + spellLevelYPushBase;
                double d2 = spellLevelXZPush * resistance + spellLevelXZPushBase;
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(vec33.x() * d2, vec33.y() * d, vec33.z() * d2));
            }
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.isUseful())
            return;
        this.setUseful(false);
    }
    protected void afterHasLineOfSight() {
        super.afterHasLineOfSight();
        if (!this.isUseful())
            return;
        this.setUseful(false);
    }

    public BaseRayEntity getRay() {
        return new PushForceEntity(this.spellLevelDamage, this.spellLevelXZPush, this.spellLevelXZPushBase, this.spellLevelYPush, this.spellLevelYPushBase, this.level(), (LivingEntity) this.getOwner(), summonTod, summonTod2, summonTod3);
    }
    public boolean showParticle() {
        return true;
    }
    public boolean showBeam() {
        return false;
    }

    @Override
    public int getMaxLife() {
        return 40;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.PUSH_FORCE.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.OMINOUS_FORCE.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.85f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }

    public int beamLightI() {
        return 0xc26b4c;
    }
    public int beamLightII() {
        return 0xe3826c;
    }
}