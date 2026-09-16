package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CelestialCoilvineFlowerEntity extends Mob implements JerotesEntity, TraceableEntity, OwnableEntity, MagicAbout {
    private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);
    public AnimationState startAnimationState = new AnimationState();
    public AnimationState spellAnimationState = new AnimationState();
    public AnimationState stopAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIGHT_LOCK_X = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> LIGHT_LOCK_Y = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> LIGHT_LOCK_Z = SynchedEntityData.defineId(CelestialCoilvineFlowerEntity.class, EntityDataSerializers.INT);

    public CelestialCoilvineFlowerEntity(EntityType<? extends CelestialCoilvineFlowerEntity> type, Level world) {
        super(type, world);
        this.noCulling = true;
    }

    public CelestialCoilvineFlowerEntity(Level p_36926_, double p_36927_, double p_36928_, double p_36929_, float p_36930_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.CELESTIAL_COILVINE_FLOWER.get(), p_36926_);
        this.setOwner(p_36932_);
        this.setYRot(p_36930_ * 57.295776F);
        this.setPos(p_36927_, p_36928_, p_36929_);
        this.noCulling = true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        double d2 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d2)) {
            d2 = 4.0;
        }
        d2 *= 128.0;
        return d < d2 * d2;
    }

    @Override
    protected void registerGoals() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 25);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 0);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
        return builder;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.GRASS_HIT;
    }
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
    @Override
    public boolean isPushedByFluid() {
        return false;
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public void pushEntities() {
    }
    @Override
    public void setDeltaMovement(Vec3 vec3) {
        super.setDeltaMovement(new Vec3(0, 0, 0));
    }
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get())
            return false;
        return super.canDrownInFluidType(type);
    }
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.75f;
    }

    private int spellLevelDamage = 4;
    @Override
    public int getSpellLevel() {
        return this.spellLevelDamage;
    }
    public void setSpellLevelDamage(int n) {
        this.spellLevelDamage = n;
    }
    public int maxTick = 480;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    @Nullable
    @Override
    public LivingEntity getOwner() {
        if (!this.level().isClientSide){
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : getLivingEntityByUUID(this.level(), uuid);
        } else {
            int id = this.getOwnerId();
            return id <= -1 ? null : this.level().getEntity(this.getOwnerId()) instanceof LivingEntity living && living != this ? living : null;
        }
    }
    public int getOwnerId(){
        return this.entityData.get(DATA_OWNER_ID);
    }
    public void setOwnerId(int id){
        this.entityData.set(DATA_OWNER_ID, id);
    }
    public static LivingEntity getLivingEntityByUUID(Level level, UUID uuid) {
        return getLivingEntityByUUID(level.getServer(), uuid);
    }

    public static LivingEntity getLivingEntityByUUID(MinecraftServer server, UUID uuid){
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity instanceof LivingEntity livingEntity){
                    return livingEntity;
                }
            }
        }
        return null;
    }
    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }
    //动画
    public void setAnimTick(int n){
        this.getEntityData().set(ANIM_TICK, n);
    }
    public int getAnimTick(){
        return this.getEntityData().get(ANIM_TICK);
    }
    public void setAnimationState(String input) {
        this.setAnimationState(this.getAnimationState(input));
    }
    public void setAnimationState(int id) {
        this.entityData.set(ANIM_STATE, id);
    }
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "start")){
            return 1;
        }
        else if (Objects.equals(animation, "spell")){
            return 2;
        }
        else if (Objects.equals(animation, "stop")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.startAnimationState);
        list.add(this.spellAnimationState);
        list.add(this.stopAnimationState);
        return list;
    }
    public void stopMostAnimation(AnimationState exception){
        for (AnimationState state : this.getAllAnimations()){
            if (state != exception) {
                state.stop();
            }
        }
    }
    public void stopAllAnimation(){
        for (AnimationState state : this.getAllAnimations()){
            state.stop();
        }
    }
    //
    public void setStartTick(int n){
        this.getEntityData().set(START_TICK, n);
    }
    public int getStartTick(){
        return this.getEntityData().get(START_TICK);
    }
    public void setLightLockX(int n){
        this.getEntityData().set(LIGHT_LOCK_X, n);
    }
    public int getLightLockX() {return this.getEntityData().get(LIGHT_LOCK_X);}
    public void setLightLockY(float f){
        this.getEntityData().set(LIGHT_LOCK_Y, f);
    }
    public float getLightLockY() {
        return this.getEntityData().get(LIGHT_LOCK_Y);
    }
    public void setLightLockZ(int n){
        this.getEntityData().set(LIGHT_LOCK_Z, n);
    }
    public int getLightLockZ() {
        return this.getEntityData().get(LIGHT_LOCK_Z);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
        compoundTag.putInt("StartTick", this.getStartTick());
        compoundTag.putInt("AnimTick", this.getAnimTick());
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
        compoundTag.putInt("MaxTick", this.maxTick);
        compoundTag.putInt("LightLockX", this.getLightLockX());
        compoundTag.putFloat("LightLockY", this.getLightLockY());
        compoundTag.putInt("LightLockZ", this.getLightLockZ());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
        this.setStartTick(compoundTag.getInt("StartTick"));
        this.setAnimTick(compoundTag.getInt("AnimTick"));
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
        this.maxTick = compoundTag.getInt("MaxTick");
        this.setLightLockX(compoundTag.getInt("LightLockX"));
        this.setLightLockY(compoundTag.getFloat("LightLockY"));
        this.setLightLockZ(compoundTag.getInt("LightLockZ"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_OWNER_ID, -1);
        this.getEntityData().define(START_TICK, 0);
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
        this.getEntityData().define(LIGHT_LOCK_X, 0);
        this.getEntityData().define(LIGHT_LOCK_Y, 0f);
        this.getEntityData().define(LIGHT_LOCK_Z, 0);
    }
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ANIM_STATE.equals(entityDataAccessor)) {
            if (this.level().isClientSide()) {
                switch (this.entityData.get(ANIM_STATE)){
                    case 0:
                        this.stopAllAnimation();
                        break;
                    case 1:
                        this.startAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.startAnimationState);
                        break;
                    case 2:
                        this.spellAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.spellAnimationState);
                        break;
                    case 3:
                        this.stopAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.stopAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.getOwner() != null) {
            LivingEntity livingEntity = this.getOwner();
            if (entity == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }
    @Override
    //1.20.4↑//
    //public PlayerTeam getTeam() {
    //1.20.1//
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }

    @Override
    public void tick() {
        super.tick();
        this.setJumping(false);
        if (!this.level().isClientSide) {
            this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
            this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getOwner() != null && this.getOwnerId() == -1) {
            this.setOwnerId(this.getOwner().getId());
        }
        if (!this.level().isClientSide()) {
            this.setStartTick(this.getStartTick() + 1);
        }
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        //
        //清除动画
        if (!this.level().isClientSide()) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
        }
        if (this.getAnimTick() == 0) {
            if (!this.level().isClientSide()) {
                this.setAnimationState(0);
            }
        }
        //最大生命
        int trueMaxTick = this.maxTick;
        if (this.maxTick == 0) {
            trueMaxTick = 30;
        }
        if (this.getStartTick() >= trueMaxTick) {
            this.hurt(this.damageSources().genericKill(), 200f);
        }
        //粒子
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(0.75f), this.getRandomY(), this.getRandomZ(0.75f), 0, 0, 0, 0, 0);
        }
        if (this.getStartTick() % 5 == 0) {
            if (this.getOwner() != null) {
                this.setLightLockX(this.getOwner().getOnPos().getX());
                this.setLightLockY(this.getOwner().getOnPos().getY() + (this.getOwner() instanceof CelestialCoilvineFlowerEntity celestialCoilvineFlowerEntity ? 3.45f : getOwner().getEyeHeight()));
                this.setLightLockZ(this.getOwner().getOnPos().getZ());
            }
            else {
                this.setLightLockX(this.getOnPos().getX());
                this.setLightLockY(this.getOnPos().getY());
                this.setLightLockZ(this.getOnPos().getZ());
            }
        }
        if (this.isAlive() && this.getStartTick() > 100 && this.getStartTick() % 80 == 0 && !this.isOnFire() && this.getOwner() != null && this.distanceTo(this.getOwner()) <= 128) {
            this.getOwner().clearFire();
            this.getOwner().heal(Mth.clamp(this.getOwner().getMaxHealth() / (1600f / spellLevelDamage), 0.4f * spellLevelDamage, 5 * spellLevelDamage));
            if (this.level() instanceof ServerLevel serverLevel) {
                Vec3 vec3 = this.getEyePosition();
                Vec3 vec32 = this.getOwner().getPosition(0).add(0,(this.getOwner() instanceof CelestialCoilvineFlowerEntity celestialCoilvineFlowerEntity ? 4f : getOwner().getEyeHeight()),0).subtract(vec3);
                Vec3 vec33 = vec32.normalize();
                for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
                    Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
                    serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                for (int i = 0; i < 24; i++) {
                    serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(0.75f), this.getRandomY(), this.getRandomZ(0.75f), 0, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getOwner().getRandomX(0.75f), this.getOwner().getRandomY(), this.getOwner().getRandomZ(0.75f), 0, 0, 0, 0, 0);
                }
            }
            if (!this.level().isClientSide()) {
                this.setAnimTick(10);
                this.setAnimationState("spell");
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypes.CACTUS)
                || damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
                || damageSource.is(DamageTypes.CRAMMING)
                || damageSource.is(DamageTypeTags.IS_FALL)
                || AttackFind.isWaterDamage(damageSource)
                || damageSource.is(DamageTypes.DRY_OUT)
                || damageSource.is(DamageTypes.IN_WALL))
            return true;
        return super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (isInvulnerableTo(damageSource))
            return super.isInvulnerableTo(damageSource);
        if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource))
            f *= 2f;
        if (damageSource.getDirectEntity() != null && damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.isOnFire() ||
                AttackFind.isFireDamage(damageSource))
            f *= 15f;
        else if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
            ItemStack handItem = livingEntity.getMainHandItem();
            if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
                handItem = livingEntity.getOffhandItem();
            if (handItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
                f *= 15f;
            }
        }
        return super.hurt(damageSource, f);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (!this.level().isClientSide()) {
            this.setAnimTick(100);
            this.setAnimationState("start");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            this.setAnimTick(20);
            this.setAnimationState("stop");
        }
        ++this.deathTime;
        this.startAnimationState.stop();
        this.spellAnimationState.stop();
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}