package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MerorBombEntity extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> OWNER_TYPE = SynchedEntityData.defineId(MerorBombEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MINE = SynchedEntityData.defineId(MerorBombEntity.class, EntityDataSerializers.BOOLEAN);
    public MerorBombEntity(EntityType<? extends MerorBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MerorBombEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.MEROR_BOMB.get(), livingEntity, level);
    }

    public MerorBombEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.MEROR_BOMB.get(), d, d2, d3, level);
    }

    public int boom = 0;
    public void setOwnerType(int n){
        this.getEntityData().set(OWNER_TYPE, n);
    }
    public int getOwnerType(){
        return this.getEntityData().get(OWNER_TYPE);
    }
    public void setMine(boolean bl){
        this.getEntityData().set(MINE, bl);
    }
    public boolean isMine(){
        return this.getEntityData().get(MINE);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Boom", this.boom);
        compoundTag.putBoolean("Mine", this.isMine());
        compoundTag.putInt("OwnerType", this.getOwnerType());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.boom = compoundTag.getInt("Boom");
        this.setMine(compoundTag.getBoolean("Mine"));
        this.setOwnerType(compoundTag.getInt("OwnerType"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(OWNER_TYPE, 0);
        this.getEntityData().define(MINE, false);
    }

    @Override
    public void handleEntityEvent(byte by) {
        if (by == 3) {
            ParticleOptions particleOptions = this.getParticle();
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            if (!isMine()) {
                this.boom += 120;
            }
        }
        this.setDeltaMovement(0d, 0d, 0d);
        this.moveTo(this.getPosition(0).add(0,-0.25,0));
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (isMine()) {
            this.setDeltaMovement(0d, 0d, 0d);
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5f, 2.5f, 2.5f));
        for (LivingEntity attack : list) {
            if (attack == null) continue;
            if (!(this.getOwner() instanceof LivingEntity livingEntityOwner)) continue;
            if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
            if (!Main.hasLineOfSightEntity(attack, this)) continue;
            if (AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
            if (OtherEntityFactionFind.isMerorMachine(attack.getType()))
                continue;
            this.boom += 30;
        }

        if (this.boom >= 120) {
            for (LivingEntity attack : list) {
                if (attack == null) continue;
                if (!(this.getOwner() instanceof LivingEntity livingEntityOwner)) continue;
                if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
                if (!Main.hasLineOfSightEntity(attack, this)) continue;
                if (AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
                if (OtherEntityFactionFind.isMerorMachine(attack.getType()))
                    continue;
                this.doHurtAll(attack);
            }
            if (!this.level().isClientSide()) {
                this.level().broadcastEntityEvent(this, (byte)3);
                float distance = (isMine() ? 3 : 4);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), (isMine() ? 2.5f : 5f), 1.0F);
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 20; ++i) {
                        RandomSource random = RandomSource.create();
                        double d = random.nextGaussian() * 0.02;
                        double d2 = random.nextGaussian() * 0.02;
                        double d3 = random.nextGaussian() * 0.02;
                        float x = (float) this.getX((2.0 * random.nextDouble() - 1.0) * ((distance/2 + random.nextDouble() * 2)));
                        float y = (float) this.getY((2.0 * random.nextDouble() - 1.0) * ((distance/3 + random.nextDouble() * 2)));
                        float z = (float) this.getZ((2.0 * random.nextDouble() - 1.0) * ((distance/2 + random.nextDouble() * 2)));
                        serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), x, y, z, 0, d, d2, d3, 0);
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 0, d, d2, d3, 0);
                    }
                }
                List<LivingEntity> lists = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(distance, distance, distance));
                for (LivingEntity hurt : lists) {
                    if (this.getOwner() instanceof LivingEntity living && AttackFind.FindCanNotAttack(living, hurt)) continue;
                    if (!Main.hasLineOfSightEntity(hurt, this)) continue;
                    DamageSource damageSources = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this, this.getOwner());
                    hurt.hurt(damageSources, (isMine() ? 25 : 35) / (Math.max(1, (this.distanceTo(hurt)))));

                    double d = 0.0;
                    if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                        d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
                    }
                    double d2 = Math.max(0, 1 - d) + 0.2f;
                    if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
                        hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.25 * (d2 + 0.3), -(this.getY() - hurt.getY()) * 0.25 * (d2 + 0.35) + 0.2f, -(this.getZ() - hurt.getZ()) * 0.25 * (d2 + 0.3)));
                    }
                }
                this.discard();
            }
        }
        else {
            this.boom += 1;
        }
    }

    private void doHurtAll(Entity entity) {
        if (this.level().isClientSide) {
            return;
        }
        Entity entity2 = this.getOwner();
        entity.hurt(this.damageSources().explosion(this, entity2), 18.0f);
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.MEROR_BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }
}
