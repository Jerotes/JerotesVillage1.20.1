package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class OldSerpenperthMeteorEntity extends MagicAboutThrowEntity {
    public OldSerpenperthMeteorEntity(EntityType<? extends OldSerpenperthMeteorEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OldSerpenperthMeteorEntity(int spellLevelDamage, float spellLevelExplode, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.OLD_SERPENPERTH_METEOR.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelExplode = spellLevelExplode;
    }

    public OldSerpenperthMeteorEntity(int spellLevelDamage, float spellLevelExplode, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.OLD_SERPENPERTH_METEOR.get(), d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelExplode = spellLevelExplode;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this, this.getOwner());
            if (livingEntity.hurt(damageSource, Main.rollDice(6 + (getSpellLevel() - 5), 6, this.random))) {
                livingEntity.setSecondsOnFire(6 + getSpellLevel() * 3);
            }
            this.playSound(SoundEvents.STONE_BREAK, 10.0f, 1.0f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            if (this.level() instanceof ServerLevel _level && !JerotesVillageBlocks.DROUGHT_FIRE_STONE.get().defaultBlockState().isAir()) {
                for (int i = 0; i < 24; ++i) {
                    _level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.DROUGHT_FIRE_STONE.get().defaultBlockState()), this.getX(), this.getY(), this.getZ(), 15, 0.3, 0, 0.3, 0);
                }
            }
            DamageSource damageSources = this.damageSources().explosion(this, this.getOwner());
            this.level().explode(this, damageSources, null, this.getX(), this.getY(), this.getZ(), spellLevelExplode, true, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0f), this.getY(2.0D * this.random.nextDouble() - 1.0D), this.getRandomZ(1.0f), 0.0d, 0.0d, 0.0d);
        this.level().addParticle(ParticleTypes.LAVA, this.getRandomX(1.0f), this.getY(2.0D * this.random.nextDouble() - 1.0D), this.getRandomZ(1.0f), 0.0d, 0.0d, 0.0d);
    }

    @Override
    public int getMaxLife() {
        return 160;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.DROUGHT_FIRE_STONE.get();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return this.isAlive();
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }
}
