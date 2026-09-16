package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Part.ImprisonVinePart;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ImprisonVineEntity extends Mob implements JerotesEntity, TraceableEntity, OwnableEntity, MagicAbout {
    private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(ImprisonVineEntity.class, EntityDataSerializers.INT);
    public AnimationState startAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ImprisonVineEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ImprisonVineEntity.class, EntityDataSerializers.INT);
    private ImprisonVinePart part_1;
    private ImprisonVinePart part_2;
    
    public ImprisonVineEntity(EntityType<? extends ImprisonVineEntity> type, Level world) {
        super(type, world);
        resetParts();
    }

    public ImprisonVineEntity(Level p_36926_, double p_36927_, double p_36928_, double p_36929_, float p_36930_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.IMPRISON_VINE.get(), p_36926_);
        this.setOwner(p_36932_);
        this.setYRot(p_36930_ * 57.295776F);
        this.setPos(p_36927_, p_36928_, p_36929_);
    }

    @Override
    protected void registerGoals() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 40);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
        builder = builder.add(Attributes.FOLLOW_RANGE, 0);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
        return builder;
    }

    public void resetParts() {
        removeParts();
        if (this.getStartTick() > 20) {
            return;
        }
        this.part_1 = new ImprisonVinePart(this, 1.0f, -90, 0, 0.5f, 2f, 1.0f);
        if (this.level() instanceof ServerLevel serverLevel) {
            PlayerTeam teams = (PlayerTeam) this.getTeam();
            if (teams != null) {
                serverLevel.getScoreboard().addPlayerToTeam(part_1.getStringUUID(), teams);
            }
        }
        this.part_1.copyPosition(this);
        this.part_1.setParent(this);
        this.part_2 = new ImprisonVinePart(this, 1.0f, 90, 0, 0.5f, 2f, 1.0f);
        if (this.level() instanceof ServerLevel serverLevel) {
            PlayerTeam teams = (PlayerTeam) this.getTeam();
            if (teams != null) {
                serverLevel.getScoreboard().addPlayerToTeam(part_2.getStringUUID(), teams);
            }
        }
        this.part_2.copyPosition(this);
        this.part_2.setParent(this);
    }

    public void updateParts() {
        Main.updatePart(part_1, this);
        Main.updatePart(part_2, this);
    }

    private void removeParts() {
        if (part_1 != null) {
            part_1.remove(RemovalReason.DISCARDED);
            part_1 = null;
        }
        if (part_2 != null) {
            part_2.remove(RemovalReason.DISCARDED);
            part_2 = null;
        }
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        removeParts();
        super.remove(reason);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GRASS_BREAK;
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
        return 0.5f;
    }

    public int maxTick = 240;
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
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
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
        else if (Objects.equals(animation, "dead")){
            return 2;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.startAnimationState);
        list.add(this.deadAnimationState);
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
    //
    private int spellLevelDamage = 5;
    @Override
    public int getSpellLevel() {
        return this.spellLevelDamage;
    }
    public void setSpellLevelDamage(int n) {
        this.spellLevelDamage = n;
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
        resetParts();
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(START_TICK, 0);
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
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
                        this.deadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.deadAnimationState);
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
        updateParts();
        if (this.part_1 == null || this.part_2 == null || this.part_1.distanceTo(this) > 10 || this.part_2.distanceTo(this) > 10) {
            resetParts();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
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
            trueMaxTick = 240;
        }
        if (this.getStartTick() >= trueMaxTick) {
            this.hurt(this.damageSources().genericKill(), 40f);
        }
        if (this.getStartTick() == 20) {
            resetParts();
        }
        if (this.isAlive() && this.getStartTick() > 20) {
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.25f));
            list.removeIf(livingEntity -> AttackFind.FindCanNotAttack(this, livingEntity));
            list.removeIf(livingEntity -> this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), livingEntity));
            if (!list.isEmpty()) {
                for (LivingEntity hurt : list) {
                    if (hurt == null) continue;
                    if (hurt == this) continue;
                    if (hurt == this.getOwner()) continue;
                    if (hurt instanceof OwnableEntity ownable && ownable.getOwner() == this.getOwner()) continue;
                    if (hurt instanceof ImprisonVineEntity) continue;
                    if (AttackFind.FindCanNotAttack(this, hurt)) continue;
                    if (this.getOwner() != null	&& AttackFind.FindCanNotAttack(this.getOwner(), hurt)) continue;
                    if (!this.hasLineOfSight(hurt)) continue;
                    if (this.getOwner() instanceof CelestialCoilvineEntity mob) {
                        if (hurt instanceof Mob mob2 && mob.getTarget() != mob2 && mob2.getTarget() != mob && !(mob2.getTarget() != null && mob2.getTarget().isAlliedTo(mob))) {
                            return;
                        }
                        if (hurt instanceof Player player && mob.getTarget() != player) {
                            return;
                        }
                    }
                    if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
                        if (!hurt.level().isClientSide()) {
                            hurt.teleportTo(this.getX(), this.getY(0), this.getZ());
                            hurt.setDeltaMovement(0, 0, 0);
                        }
                    }
                }
            }
        }
        //近战攻击
        if (this.getStartTick() % 20 == 0 && this.getStartTick() > 20 && this.isAlive()) {
            List<LivingEntity> listAttack = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.25f));
            listAttack.removeIf(livingEntity -> AttackFind.FindCanNotAttack(this, livingEntity));
            listAttack.removeIf(livingEntity -> this.getOwner() != null	&& AttackFind.FindCanNotAttack(this.getOwner(), livingEntity));
            boolean bl = false;
            for (LivingEntity hurt : listAttack) {
                if (hurt == null) continue;
                if (hurt == this) continue;
                if (hurt == this.getOwner()) continue;
                if (hurt instanceof OwnableEntity ownable && ownable.getOwner() == this.getOwner()) continue;
                if (hurt instanceof ImprisonVineEntity) continue;
                if (AttackFind.FindCanNotAttack(this, hurt)) continue;
                if (this.getOwner() != null	&& AttackFind.FindCanNotAttack(this.getOwner(), hurt)) continue;
                if (!this.hasLineOfSight(hurt)) continue;
                if (this.getOwner() instanceof CelestialCoilvineEntity mob) {
                    if (hurt instanceof Mob mob2 && mob.getTarget() != mob2 && mob2.getTarget() != mob && !(mob2.getTarget() != null && mob2.getTarget().isAlliedTo(mob))) {
                        return;
                    }
                    if (hurt instanceof Player player && mob.getTarget() != player) {
                        return;
                    }
                }
                AttackFind.attackBegin(this, hurt);
                DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.CRAMMING, this, this.getOwner());
                AttackFind.attackAfterCustomDamageNoEnchantAbout(this, hurt, damageSource,0.2f * spellLevelDamage, 1.0f, false, 0f);
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
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        boolean special = false;
        if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource)) {
            amount *= 2f;
            special = true;
        }

        if (AttackFind.isFireDamage(damageSource) ||
                damageSource.getDirectEntity() != null && damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.isOnFire()) {
            amount *= 1.5f;
            special = true;
        }
        else if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
            ItemStack handItem = livingEntity.getMainHandItem();
            if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
                handItem = livingEntity.getOffhandItem();
            if (handItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
                amount *= 1.5f;
                special = true;
            }
        }
        if (!special) {
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.25f));
            if ((damageSource.getDirectEntity() instanceof LivingEntity livingEntity && list.contains(livingEntity)) ||
                    (damageSource.getEntity() instanceof LivingEntity livingEntity2 && (damageSource.getDirectEntity() == livingEntity2 || damageSource.getDirectEntity() == null) && list.contains(livingEntity2))) {
                amount *= 0.5f;
            }
        }
        return super.hurt(damageSource, amount);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (!this.level().isClientSide()) {
            this.setAnimTick(20);
            this.setAnimationState("start");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            if (!this.level().isClientSide()) {
                this.setAnimTick(20);
                this.setAnimationState("dead");
            }
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}