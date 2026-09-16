package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.WhipTheTideVineEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WhipTheTideEntity extends MagicAboutThrowEntity {
    public WhipTheTideEntity(EntityType<? extends WhipTheTideEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WhipTheTideEntity(int spellLevelDamage, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.WHIP_THE_TIDE.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    public WhipTheTideEntity(int spellLevelDamage, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.WHIP_THE_TIDE.get(), d, d2, d3, level);
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
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner());
            livingEntity.hurt(damageSource,
                    Main.rollDice(2 + (this.spellLevelDamage - 5), 4, livingEntity.getRandom())
            );
            WhipTheTideVineEntity.placeVine(this.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    this.getYRot(), 5, this.getOwner(), this.getSpellLevel());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        WhipTheTideVineEntity.placeVine(this.level(), blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z,
                this.getYRot(), 5, this.getOwner(), this.getSpellLevel());
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY(), this.getZ(), 40, 0.35, 0.25, 0.35, 0.25);
            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_CHANGE.get(), this.getX(), this.getY(), this.getZ(), 6, 0.05, 0.025, 0.05, 0.025);
            this.playSound(SoundEvents.CHAIN_BREAK, 1.5f, 1.0f);
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 240;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return this.isAlive() && this.life > 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive() && this.life > 10;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SPLASH;
    }

    @Override
    protected float getGravity() {
        return 0.03F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
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

    public List<TrailPoint> getTrailPoints() {
        return trailPoints;
    }

    public boolean showBeam() {
        return true;
    }

    public int beamLightI() {
        return 0xa5c72f;
    }

    public int beamLightII() {
        return 0xd7f56e;
    }

    public int beamLightIII() {
        return 0x2a7d21;
    }

    public int trailMaxLife() {
        return 6;
    }

    public int trailMaxPoints() {
        return 6;
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
}
