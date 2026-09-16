package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MalignasaurBombEntity extends MagicAboutEntity {
    public MalignasaurBombEntity(EntityType<? extends MalignasaurBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MalignasaurBombEntity(EntityType<? extends MalignasaurBombEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public MalignasaurBombEntity(int spellLevelDamage, float spellLevelExplode, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.MALIGNASAUR_BOMB.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelExplode = spellLevelExplode;
    }

    @Override
    public void tick() {
        if (this.life >= this.getMaxLife()) {
            this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 3.0f, 1.0f);
        }
        super.tick();
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2, 2, 2));
        for (LivingEntity attack : list) {
            if (attack == null) continue;
            if (!(this.getOwner() instanceof LivingEntity livingEntityOwner)) continue;
            if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
            if (!Main.hasLineOfSightEntity(attack, this)) continue;
            if (AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_DROWN, this, this.getOwner());
            attack.hurt(damageSource,
                    Main.rollDice(spellLevelDamage, 4, this.random)
            );
            this.explode();
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            this.discard();
        }
        if (this.tickCount % 20 == 0) {
            if (!this.isNoGravity()) {
                Vec3 vec33 = this.getDeltaMovement();
                this.setDeltaMovement(vec33.x, -0.15, vec33.z);
            }
        }
        if (this.tickCount % 20 == 10) {
            if (!this.isNoGravity()) {
                Vec3 vec33 = this.getDeltaMovement();
                this.setDeltaMovement(vec33.x, 0.15, vec33.z);
            }
        }
        if (level().isClientSide) {
            Vec3 currentPos = this.position().add(0.0D, 0.1875D, 0.0D);
            Vec3 prevPos = new Vec3(
                    this.xOld,
                    this.yOld,
                    this.zOld
            ).add(0.0D, 0.1875D, 0.0D);
            trailPoints.add(new TrailPoint(prevPos, currentPos, life));
            trailPoints.removeIf(p -> life - p.createdAt > trailMaxLife());
            while (trailPoints.size() > trailMaxPoints()) {
                trailPoints.remove(0);
            }
        }
    }
    private void explode() {
        if (!this.level().isClientSide) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 3.0F, 0.5F);
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 2; ++i) {
                    RandomSource random = RandomSource.create();
                    double d = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    double d3 = random.nextGaussian() * 0.02;
                    float x = (float) this.getX((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    float y = (float) this.getY((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    float z = (float) this.getZ((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
                    serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE_LAND.get(), x, y, z, 0, d, d2, d3, 0);
                    ParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.GEMSTONE_BUBBLE_LAND.get(), true, 2.0f, 0.5f);
                }
            }
        }
    }
    public void lastBreak() {
        this.explode();
    }

    public List<TrailPoint> getTrailPoints() {
        return trailPoints;
    }
    public static class TrailPoint {
        public final Vec3 prevPosition;
        public final Vec3 position;
        public final int createdAt;
        public TrailPoint(Vec3 prevPosition, Vec3 position, int tick) {
            this.prevPosition = prevPosition;
            this.position = position;
            this.createdAt = tick;
        }
        public Vec3 getPosition(float partialTick) {
            return prevPosition.lerp(position, partialTick);
        }
    }
    private final List<TrailPoint> trailPoints = new ArrayList<>();
    public int trailMaxLife() {
        return 6;
    }
    public int trailMaxPoints() {
        return 6;
    }

    @Override
    public int getMaxLife() {
        return 240;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.MALIGNASAUR_BOMB.get()) : itemStack;
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

