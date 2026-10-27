package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicBeam.BaseMagicBeamEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SkylanceDragonBeamEntity extends BaseMagicBeamEntity {
    public SkylanceDragonBeamEntity(EntityType<? extends SkylanceDragonBeamEntity> entityType, Level level) {
        super(entityType, level);
    }
    public SkylanceDragonBeamEntity(EntityType<? extends SkylanceDragonBeamEntity> entityType, Level level, int spellLevelDamage) {
        super(entityType, level, spellLevelDamage);
    }

    public void tick() {
        super.tick();
        if (this.tickCount % 2 == 0) {
            if (!this.isSilent()) {
                this.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT, 2.2F, 10.0F);
            }
        }
    }
    public void customHurtMagic(Entity entity) {
        boolean bl = entity.hurt(AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_LIGHTNING, this, this.getOwner()),
                Main.rollDice(spellLevelDamage, 4, this.random) / 30 / 5 * (float) (MainConfig.BeamDamageMultipler));
        boolean bl2 = entity.hurt(AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_THUNDER, this, this.getOwner()),
                Main.rollDice(spellLevelDamage, 4, this.random) / 30 / 5 * (float) (MainConfig.BeamDamageMultipler));
        if (bl || bl2) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.level().isClientSide) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2, 0), this);
                }
                knockback(livingEntity, 0.5D, this.getX() - livingEntity.getX(), this.getZ() - livingEntity.getZ());
            }
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 24; ++i) {
                    serverLevel.sendParticles(JerotesParticleTypes.SHOCK.get(), entity.getRandomX(0.5), entity.getRandomY(), entity.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
    }
    public void knockback(LivingEntity livingEntity, double p_147241_, double p_147242_, double p_147243_) {
        if (!EntityAndItemFind.isNoSpecialKnockback(livingEntity.getType())) {
            p_147241_ *= (1.3D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) / 2;
            if (!(p_147241_ <= 0.0D)) {
                livingEntity.hasImpulse = true;
                Vec3 vec3 = livingEntity.getDeltaMovement();
                Vec3 vec31 = (new Vec3(p_147242_, 0.0D, p_147243_)).normalize().scale(p_147241_);
                livingEntity.setDeltaMovement(vec3.x / 2.0D - vec31.x, livingEntity.onGround() ? Math.min(0.4D, vec3.y / 2.0D + p_147241_) : vec3.y, vec3.z / 2.0D - vec31.z);
            }
        }
    }
    public int getMaxTick() {
        return 240;
    }

    public boolean isPiercing() {
        return true;
    }

    public double getMaxDistance() {
        return 60.0F;
    }


    public float getBeamScale() {
        return 3.0F * this.getBeamScaleAdd();
    }

    public ResourceLocation getBeamLocation() {
        return new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/skylance_dragon_beam.png");
    }
}
