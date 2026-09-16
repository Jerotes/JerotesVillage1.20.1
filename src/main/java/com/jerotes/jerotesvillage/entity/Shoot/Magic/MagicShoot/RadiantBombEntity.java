package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RadiantBombEntity extends BaseMagicBoltEntity {
    public RadiantBombEntity(EntityType<? extends RadiantBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RadiantBombEntity(EntityType<? extends RadiantBombEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public RadiantBombEntity(int spellLevelDamage, float spellLevelExplode, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.RADIANT_BOMB.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelExplode = spellLevelExplode;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource,
                    Main.rollDice(spellLevelDamage, 10, livingEntity.getRandom())
            );
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1), this.getEffectSource());
                if (this.getOwner() != null) {
                    String string = ChatFormatting.stripFormatting(this.getOwner().getName().getString());
                    if ("Soul Lampologer".equals(string) || "Soullampologer".equals(string) || "Lampologer".equals(string)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), this.getEffectSource());
                    }
                }
            }
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
      }
    }
    public void lastBreak() {
        if (!this.level().isClientSide) {
            for (int n = 0; n < 4; ++n) {
                ParticlesUse.sendBallParticles(this, JerotesParticleTypes.CURE_WOUNDS.get(), true, 3.0f, 0.3f);
                ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.RADIANT_BOMB.get(), true, 1.0f, 0.05f);
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this, this.getOwner());
            this.level().explode(this, damageSource, null, this.getX(), this.getY(), this.getZ(), spellLevelExplode, false, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            for (int n = 0; n < 4; ++n) {
                ParticlesUse.sendBallParticles(this, JerotesParticleTypes.CURE_WOUNDS.get(), true, 3.0f, 0.3f);
                ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.RADIANT_BOMB.get(), true, 1.0f, 0.05f);
            }
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this, this.getOwner());
            this.level().explode(this, damageSource, null, this.getX(), this.getY(), this.getZ(), spellLevelExplode, false, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 90;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.RADIANT_BOMB.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.RADIANT_BOMB.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }

    public int beamLightI() {
        return 0xffca52;
    }
    public int beamLightII() {
        return 0xf9ebca;
    }
    public int beamLightIII() {
        return 0xffb306;
    }
    public ResourceLocation TextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID,
                "textures/entity/projectiles/radiant_bomb.png");
    }
}
