package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray;

import com.jerotes.jerotes.client.particle.BoltParticleOptions;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicSpray.BaseMagicSprayEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VirtualCaveSurgeSiphonEntity extends BaseMagicSprayEntity {
    public VirtualCaveSurgeSiphonEntity(EntityType<? extends VirtualCaveSurgeSiphonEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VirtualCaveSurgeSiphonEntity(EntityType<? extends VirtualCaveSurgeSiphonEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public VirtualCaveSurgeSiphonEntity(int spellLevelDamage, int spellLevelFireTime, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.VIRTUAL_CAVE_SURGE_SIPHON.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = spellLevelFireTime;
    }

    public void useMagicTo(LivingEntity livingEntity) {
        super.useMagicTo(livingEntity);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity2 = this.getOwner();
        if (entity2 instanceof LivingEntity livingEntity2) {
            double healthOld = livingEntity.getHealth();
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_NECROTIC, this, this.getOwner());
            if (livingEntity.hurt(damageSource,
                    Main.rollDice(4 + (spellLevelDamage - 3), 6, livingEntity2.getRandom()) / 30
            )) {
                double healthNew = livingEntity.getHealth();
                if ((float) (healthOld - healthNew)> 0) {
                    livingEntity2.heal((float) (healthOld - healthNew));
                }
            }
        }
    }

    @Override
    public void spawnConeParticles(int lifeTick) {
    }

    @Override
    public void spawnBoltParticles() {
        Entity owner = this.getOwner();
        if (owner == null) return;
        Vec3 casterPos = owner.position();
        Vec3 forward = owner.getViewVector(1.0f);
        if (forward.lengthSqr() < 1.0E-6) {
            forward = new Vec3(0, 0, 1);
        }
        forward = forward.normalize();
        Vec3 worldUp = new Vec3(0, 1, 0);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0E-6) {
            right = new Vec3(1, 0, 0);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        Vec3 origin = casterPos
                .add(0, owner.getBbHeight() * 0.7, 0)
                .add(forward.scale(0.4));
        float range = getMaxReach();
        float halfAngleRad = (float) Math.toRadians(
                getSpellAngle() * 0.5f
        );
        float baseRadius = range * (float) Math.tan(halfAngleRad);
        int totalParticles = getParticleCount() * 2;
        RandomSource random = this.random;
        for (int i = 0; i < totalParticles; i++) {
            float depth = 0.25f + random.nextFloat() * 0.75f;
            float actualDist = depth * range;
            float radiusAtDepth = baseRadius * depth;
            float angleRad = random.nextFloat() * Mth.TWO_PI;
            float distFromCenter =
                    radiusAtDepth * (float) Math.sqrt(random.nextFloat());
            Vec3 radialDir = right
                    .scale(Mth.cos(angleRad))
                    .add(up.scale(Mth.sin(angleRad)));
            Vec3 target = origin
                    .add(forward.scale(actualDist))
                    .add(radialDir.scale(distFromCenter));
            if (!Main.hasLineOfSightPos(target, owner)) {
                continue;
            }
            Vec3 delta = origin.subtract(target);
            float[] rgb = gradientColor(
                    beamLightI(),
                    beamLightII(),
                    beamLightIII(),
                    depth
            );
            float scale = 0.85f + depth * 0.35f;
            BoltParticleOptions options =
                    new BoltParticleOptions(
                            rgb[0],
                            rgb[1],
                            rgb[2],
                            scale,
                            (float) delta.x,
                            (float) delta.y,
                            (float) delta.z
                    );
            this.level().addParticle(
                    options,
                    target.x,
                    target.y,
                    target.z,
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_SURGE_SIPHON.get()) : itemStack;
    }
    public int beamLightI() {
        return 0x51606a;
    }
    public int beamLightII() {
        return 0xd3829f;
    }
    public int beamLightIII() {
        return 0x230b36;
    }
}
