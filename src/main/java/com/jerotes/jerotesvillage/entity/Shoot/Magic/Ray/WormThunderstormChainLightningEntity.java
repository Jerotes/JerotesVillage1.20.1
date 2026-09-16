package com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray;

import com.jerotes.jerotes.entity.Shoot.Magic.Ray.BaseRayEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class WormThunderstormChainLightningEntity extends BaseRayEntity {
    private static final EntityDataAccessor<Optional<UUID>> DATA_TARGET_UUID = SynchedEntityData.defineId(WormThunderstormChainLightningEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(WormThunderstormChainLightningEntity.class, EntityDataSerializers.INT);

    public WormThunderstormChainLightningEntity(EntityType<? extends WormThunderstormChainLightningEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WormThunderstormChainLightningEntity(EntityType<? extends WormThunderstormChainLightningEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public WormThunderstormChainLightningEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.WORM_THUNDERSTORM_CHAIN_LIGHTNING.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.summonTod = d;
        this.summonTod2 = d2;
        this.summonTod3 = d3;
    }


    @Nullable
    public Entity getTarget() {
        if (!this.level().isClientSide) {
            UUID uuid = this.getTargetUUID();
            return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
        } else {
            int id = this.getTargetId();
            return id <= -1 ? null : this.level().getEntity(id);
        }
    }
    public void setTarget(@Nullable Entity entity) {
        if (entity != null) {
            this.setTargetUUID(entity.getUUID());
            if (!this.level().isClientSide) {
                this.setTargetId(entity.getId());
            }
        } else {
            this.setTargetUUID(null);
            this.setTargetId(-1);
        }
    }
    @Nullable
    public UUID getTargetUUID() {
        return this.getEntityData().get(DATA_TARGET_UUID).orElse(null);
    }
    public void setTargetUUID(@Nullable UUID uuid) {
        this.getEntityData().set(DATA_TARGET_UUID, Optional.ofNullable(uuid));
    }
    public int getTargetId() {
        return this.getEntityData().get(DATA_TARGET_ID);
    }
    public void setTargetId(int id) {
        this.getEntityData().set(DATA_TARGET_ID, id);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getTargetUUID() != null) {
            compoundTag.putUUID("Target", this.getTargetUUID());
        }
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("Target")) {
            this.setTargetUUID(compoundTag.getUUID("Target"));
        }
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_TARGET_UUID, Optional.empty());
        this.getEntityData().define(DATA_TARGET_ID, -1);
    }

    protected void hitEntity(Entity entity) {
        if (this.getTarget() != null && entity != this.getTarget())
            return;
        super.hitEntity(entity);
        if (!this.isUseful())
            return;
        this.setUseful(false);
        if (this.level().isClientSide) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            Entity owner = this.getOwner();
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_MAGIC, this, this.getOwner());
            if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
                livingEntity.hurt(damageSource,
                        Main.rollDice(8 + (spellLevelDamage - 3), 6, livingEntity.getRandom())
                );
            } else {
                livingEntity.hurt(damageSource,
                        Main.rollDice(8 + (spellLevelDamage - 3), 6, livingEntity.getRandom()) / 2f
                );
            }
            this.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT, 10.0f, 1.0f);
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
        return new WormThunderstormChainLightningEntity(this.spellLevelDamage, this.level(), (LivingEntity) this.getOwner(), summonTod, summonTod2, summonTod3);
    }

    @Override
    public void tick() {
        super.tick();
        //超过距离
        if (getLastHurt() != null && this.position().distanceTo(getLastHurt().position().add(0, Main.mobHeight(getLastHurt()) * 0.7f, 0)) > 9) {
            if (!this.level().isClientSide()) {
                this.setUseful(false);
            }
        }
    }

    @Override
    public int getMaxLife() {
        return 40;
    }
    public int getMaxReach() {
        return 60;
    }
    public boolean showHitParticle() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.WORM_THUNDERSTORM.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        if (this.isUseful()) {
            return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.WORM_THUNDERSTORM.get()) : itemStack;
        }
        return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }
    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
    public float beamScale() {
        return 0.3f;
    }

    public int beamLightI() {
        return 0x3296ff;
    }
    public int beamLightII() {
        return 0x8ac2ff;
    }
}
