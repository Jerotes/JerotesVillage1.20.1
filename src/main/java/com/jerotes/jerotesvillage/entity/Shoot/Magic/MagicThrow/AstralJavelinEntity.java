package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.AstralJavelinPointEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class AstralJavelinEntity extends MagicAboutThrowEntity {
    private static final EntityDataAccessor<Optional<UUID>> DATA_TARGETPOINT_UUID =
            SynchedEntityData.defineId(AstralJavelinEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DATA_TARGETPOINT_ID =
            SynchedEntityData.defineId(AstralJavelinEntity.class, EntityDataSerializers.INT);

    public AstralJavelinEntity(EntityType<? extends AstralJavelinEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AstralJavelinEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ASTRAL_JAVELIN.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }

    public AstralJavelinEntity(int spellLevelDamage, int spellLevelFreezeTime, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ASTRAL_JAVELIN.get(), d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFreezeTime = spellLevelFreezeTime;
    }


    @Nullable
    public UUID getTargetPointUUID() {
        return this.getEntityData().get(DATA_TARGETPOINT_UUID).orElse((UUID)null);
    }
    public void setTargetPointUUID(@Nullable UUID uuid) {
        this.getEntityData().set(DATA_TARGETPOINT_UUID, Optional.ofNullable(uuid));
    }
    public int getTargetPointId() {
        return this.getEntityData().get(DATA_TARGETPOINT_ID);
    }
    public void setTargetPointId(int id) {
        this.getEntityData().set(DATA_TARGETPOINT_ID, id);
    }
    @Nullable
    public Entity getTargetPoint() {
        if (!this.level().isClientSide) {
            UUID uuid = this.getTargetPointUUID();
            return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
        } else {
            int id = this.getTargetPointId();
            return id <= -1 ? null : this.level().getEntity(id);
        }
    }
    public void setTargetPoint(@Nullable Entity entity) {
        if (entity != null) {
            this.setTargetPointUUID(entity.getUUID());
            if (!this.level().isClientSide) {
                this.setTargetPointId(entity.getId());
            }
        } else {
            this.setTargetPointUUID(null);
            this.setTargetPointId(-1);
        }
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("TargetPoint")) {
            this.setTargetPointUUID(compoundTag.getUUID("TargetPoint"));
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getTargetPointUUID() != null) {
            compoundTag.putUUID("TargetPoint", this.getTargetPointUUID());
        }
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_TARGETPOINT_UUID, Optional.empty());
        this.getEntityData().define(DATA_TARGETPOINT_ID, -1);
    }


    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel) {
            if (getOwner() instanceof LivingEntity livingEntity) {
                AstralJavelinPointEntity cloud = new AstralJavelinPointEntity(serverLevel, livingEntity);
                cloud.setSpellLevelDamage(getSpellLevel());
                cloud.setPos(this.getX(), this.getY(), this.getZ());
                cloud.setOwner(livingEntity);
                if (this.getTargetPoint() != null) {
                    cloud.setMain(this);
                    cloud.setTargetPoint(this.getTargetPoint());
                }
                this.setTargetPoint(cloud);
                serverLevel.addFreshEntity(cloud);
                serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(livingEntity));
            }
        }
    }



    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FORCE, this, this.getOwner());
            livingEntity.hurt(damageSource,
                    Main.rollDice(4 + (getSpellLevel() - 4), 10, livingEntity.getRandom())
            );
            this.playSound(SoundEvents.TRIDENT_HIT, 3.0f, 1.0f);
        }
    }
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        this.playSound(SoundEvents.TRIDENT_HIT_GROUND, 3.0f, 1.0f);
        this.discard();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            if (this.level() instanceof ServerLevel serverLevel) {
                ParticlesUse.ballRendererParticle(serverLevel,
                        this.getX(), this.getY(0.5f), this.getZ(),
                        beamLightIII(), beamLightII(),
                        0.1f, 0.2f, 0f,
                        0f, 0.5f, 0.25f,
                        6);
            }
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

    protected ParticleOptions getTrailParticle() {
        return JerotesParticleTypes.SMALL_GUST.get();
    }

    @Override
    protected float getGravity() {
        return 0.06F;
    }

    public int beamLightI() {
        return 0xbe528d;
    }
    public int beamLightII() {
        return 0xc98a70;
    }
    public int beamLightIII() {
        return 0x7d2578;
    }
}
