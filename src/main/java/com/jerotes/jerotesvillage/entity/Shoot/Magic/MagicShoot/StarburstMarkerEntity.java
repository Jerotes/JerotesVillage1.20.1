package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class StarburstMarkerEntity extends BaseMagicBoltEntity {
    public static final EntityDataAccessor<Boolean> USEFUL = SynchedEntityData.defineId(StarburstMarkerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(StarburstMarkerEntity.class, EntityDataSerializers.INT);
    public StarburstMarkerEntity(EntityType<? extends StarburstMarkerEntity> entityType, Level level) {
        super(entityType, level);
    }

    public StarburstMarkerEntity(EntityType<? extends StarburstMarkerEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public StarburstMarkerEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.STARBURST_MARKER.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    public boolean isUseful() {
        return this.getEntityData().get(USEFUL);
    }
    public void setUseful(boolean bl) {
        this.getEntityData().set(USEFUL, bl);
    }
    public int getStartTick() {
        return this.getEntityData().get(START_TICK);
    }
    public void setStartTick(int n) {
        this.getEntityData().set(START_TICK, n);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsUseful", this.isUseful());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setUseful(compoundTag.getBoolean("IsUseful"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(USEFUL, false);
        this.getEntityData().define(START_TICK, 0);
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
        this.discard();
    }

    protected boolean dealDamageTo(LivingEntity livingEntity) {
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable()) {
            return false;
        }
        DamageSource damageSourceForce = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FORCE, this, this.getOwner());
        boolean bl2 = livingEntity.hurt(damageSourceForce,
                Main.rollDice(1, 8, livingEntity.getRandom())
        );
        if (bl2) {
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
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.getStartTick() > 0) {
            return;
        }
        if (!this.isUseful()) {
            this.setDeltaMovement(0, 0, 0);
            this.setUseful(true);
            this.xPower = 0;
            this.yPower = 0;
            this.zPower = 0;
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (this.getStartTick() > 0) {
            this.setStartTick(this.getStartTick() - 1);
        }
        if (this.getOwner() != null && this.distanceTo(this.getOwner()) > 90) {
            this.discard();
        }
    }

    @Override
    public int getMaxLife() {
        return 15 * 20;
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
        return 0xaa8168;
    }
    public int beamLightII() {
        return 0xfff580;
    }
    public int beamLightIII() {
        return 0x542e83;
    }
}
