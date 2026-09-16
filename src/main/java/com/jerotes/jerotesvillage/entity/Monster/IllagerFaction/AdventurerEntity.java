package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.OminousBombEntity;
import com.jerotes.jerotesvillage.goal.AdventurerMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdventurerEntity extends MeleeIllagerEntity implements EliteEntity, AlwaysShowArmIllagerEntity, StopLook {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attack1AnimationState = new AnimationState();
    public AnimationState attack2AnimationState = new AnimationState();
    public AnimationState swordAttack1AnimationState = new AnimationState();
    public AnimationState swordAttack2AnimationState = new AnimationState();
    public AnimationState swordAttack3AnimationState = new AnimationState();
    public AnimationState swordAttack4AnimationState = new AnimationState();
    public AnimationState swordAttack5AnimationState = new AnimationState();
    public AnimationState stabAnimationState = new AnimationState();
    public AnimationState blockAttackAnimationState = new AnimationState();
    public AnimationState lungingAttackAnimationState = new AnimationState();
    public AnimationState bombAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> SPECIAL_MELEE_CHANCE = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BLOCK_TICK = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BLOCK_ANIM = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOMB_COOLDOWN = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOMB_TICK = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(AdventurerEntity.class, EntityDataSerializers.INT);
    private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6, false);

    public AdventurerEntity(EntityType<? extends AdventurerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 80;
        this.setCanPickUpLoot(false);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())
                || "Commander".equals(string) || "指挥官".equals(string)) {
            this.bossEvent.addPlayer(serverPlayer);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())
                || "Commander".equals(string) || "指挥官".equals(string)) {
            this.bossEvent.removePlayer(serverPlayer);
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())
                || "Commander".equals(string) || "指挥官".equals(string)) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ADVENTURER_HELMET.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ADVENTURER_CHESTPLATE.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ADVENTURER_LEGGINGS.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ADVENTURER_BOOTS.get()), randomSource);
    }

    private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
        this.setItemSlot(equipmentSlot, itemStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
        builder = builder.add(Attributes.MAX_HEALTH, 140);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new AdventurerMeleeAttackGoal(this, 1.3, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (AdventurerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.ADVENTURER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.ADVENTURER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.ADVENTURER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.ADVENTURER_CHEER;
    }
    @Override
    public boolean isLeftHanded() {
        return false;
    }
    @Override
    protected float getSoundVolume() {
        return 2.0f;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_ADVENTURER_RAPIER.get());
        Raid raid = this.getCurrentRaid();
        int n2 = 1;
        if (n > raid.getNumGroups(Difficulty.NORMAL)) {
            n2 = 2;
        }
        boolean bl3 = bl2 = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            HashMap hashMap = Maps.newHashMap();
            hashMap.put(Enchantments.SHARPNESS, n2);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }
    public AABB getAttackBoundingBox() {
        Entity entity = this.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = this.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = this.getBoundingBox();
        }
        AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
        if (!this.getMainHandItem().isEmpty())
            return aabb1.inflate(0.85d, 0.85d, 0.85d);
        return aabb1.inflate(0.5d, 0.5d, 0.5d);
    }
    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
        return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
    }
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if (this.specialAction() && this.getBombTick() <= 10 || this.getAttackUse() == 2 && this.getAttackTick() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    @Override
    public void lookAt(Entity entity, float f, float f2) {
        if (!this.stopLookTime()) {
            super.lookAt(entity,f,f2);
        }
    }
    @Override
    public void setXRot(float f) {
        if (!this.stopLookTime()) {
            super.setXRot(f);
        }
    }
    @Override
    public void setYRot(float f) {
        if (!this.stopLookTime()) {
            super.setYRot(f);
        }
    }
    @Override
    public void setYBodyRot(float f) {
        if (!this.stopLookTime()) {
            super.setYBodyRot(f);
        }
    }
    @Override
    public boolean stopLookTime() {
        return this.getAttackTick() > 0 && this.getAttackTick() < 20;
    }

    public float thisTickRenderTime = 0;
    public float lastTickRenderTime = 6;
    public float blockAnimProgress = 0.0f;
    public boolean specialAction() {
        return this.getSpellTick() > 80 || this.getBlockTick() > 0 || this.getBombTick() > 0;
    }
    public void setSpecialMeleeChance(int n){
        this.getEntityData().set(SPECIAL_MELEE_CHANCE, n);
    }
    public int getSpecialMeleeChance(){
        return this.getEntityData().get(SPECIAL_MELEE_CHANCE);
    }
    public void setBlockAnim(int n){
        this.getEntityData().set(BLOCK_ANIM, n);
    }
    public int getBlockAnim(){
        return this.getEntityData().get(BLOCK_ANIM);
    }
    public void setBlockTick(int n){
        this.getEntityData().set(BLOCK_TICK, n);
    }
    public int getBlockTick(){
        return this.getEntityData().get(BLOCK_TICK);
    }
    public void setBombCooldown(int n){
        this.getEntityData().set(BOMB_COOLDOWN, n);
    }
    public int getBombCooldown(){
        return this.getEntityData().get(BOMB_COOLDOWN);
    }
    public void setBombTick(int n){
        this.getEntityData().set(BOMB_TICK, n);
    }
    public int getBombTick(){
        return this.getEntityData().get(BOMB_TICK);
    }
    public int getAttackUse() {
        return this.getEntityData().get(ATTACK_USE);
    }
    public void setAttackUse(int n) {
        this.getEntityData().set(ATTACK_USE, n);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack1")){
            return 1;
        }
        else if (Objects.equals(animation, "attack2")){
            return 2;
        }
        else if (Objects.equals(animation, "bomb")){
            return 3;
        }
        else if (Objects.equals(animation, "dead")){
            return 4;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attack1AnimationState);
        list.add(this.attack2AnimationState);
        list.add(this.bombAnimationState);
        list.add(this.deadAnimationState);
        return list;
    }
    //
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }
    public int getShieldLevel(){
        boolean special = false;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Commander".equals(string) || "指挥官".equals(string)) {
            special = true;
        }
        return special ? 5 : super.getShieldLevel();
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackUse", this.getAttackUse());
        compoundTag.putInt("SpecialMeleeChance", this.getSpecialMeleeChance());
        compoundTag.putInt("BlockTick", this.getBlockTick());
        compoundTag.putInt("BlockAnim", this.getBlockAnim());
        compoundTag.putInt("BombCooldown", this.getBombCooldown());
        compoundTag.putInt("BombTick", this.getBombTick());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        this.setAttackUse(compoundTag.getInt("AttackUse"));
        this.setSpecialMeleeChance(compoundTag.getInt("SpecialMeleeChance"));
        this.setBlockTick(compoundTag.getInt("BlockTick"));
        this.setBlockAnim(compoundTag.getInt("BlockAnim"));
        this.setBombCooldown(compoundTag.getInt("BombCooldown"));
        this.setBombTick(compoundTag.getInt("BombTick"));
        this.bossEvent.setId(this.getUUID());
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ATTACK_USE, 0);
        this.getEntityData().define(SPECIAL_MELEE_CHANCE, 0);
        this.getEntityData().define(BLOCK_TICK, 0);
        this.getEntityData().define(BLOCK_ANIM, 0);
        this.getEntityData().define(BOMB_COOLDOWN, 0);
        this.getEntityData().define(BOMB_TICK, 0);
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
                        this.attack1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack1AnimationState);
                        break;
                    case 2:
                        this.attack2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack2AnimationState);
                        break;
                    case 3:
                        this.bombAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.bombAnimationState);
                        break;
                    case 4:
                        this.deadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.deadAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            lastTickRenderTime = thisTickRenderTime;
            thisTickRenderTime = 0;
        }
    }
    @Override
    public void aiStep() {
        super.aiStep();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())
        || "Commander".equals(string) || "指挥官".equals(string)) {
            this.bossEvent.update();
            if (OtherMainConfig.EliteBossBarOnlyCombat) {
                this.bossEvent.setVisible(this.getTarget() != null);
            }
        }
        if (this.isAlive()) {
            if (this.getAttackUse() == 1) {
                if (this.getAttackTick() == 0) {
                    this.trueHurt();
                }
            }
            else if (this.getAttackUse() == 2) {
                if (this.getAttackTick() == 0 || this.getAttackTick() == 5 || this.getAttackTick() == 10 || this.getAttackTick() == 15 || this.getAttackTick() == 20) {
                    this.trueHurt();
                }
            }
            else if (this.getAttackUse() == 3 || this.getAttackUse() == 4) {
                if (this.getAttackTick() == 0) {
                    this.trueHurt();
                }
            }
        }
        if (!this.level().isClientSide()) {
            this.setBombTick(Math.max(0, this.getBombTick() - 1));
            this.setBombCooldown(Math.max(0, this.getBombCooldown() - 1));
            this.setBlockTick(Math.max(0, this.getBlockTick() - 1));
        }
        this.idleAnimationState.startIfStopped(this.tickCount);

        if (this.getTarget() != null && this.distanceTo(this.getTarget()) <= 7) {
            this.lookAt(this.getTarget(), 30.0f, 30.0f);
        }

        if (this.isAlive()) {
            //格挡
            {
                //格挡动作
                if (this.getBlockTick() > 0) {
                    if (!this.level().isClientSide()) {
                        this.setBlockAnim(Math.min(this.getBlockAnim() + 1, 10));
                    }
                }
                else {
                    if (!this.level().isClientSide()) {
                        this.setBlockAnim(Math.max(this.getBlockAnim() - 2, 0));
                    }
                }
                //距离过远取消格挡
                if (this.getBlockTick() > 20) {
                    if (!(this.getTarget() != null && this.getAttackBoundingBox().inflate(0.8f).intersects(this.getTarget().getBoundingBox()))) {
                        if (!this.level().isClientSide()) {
                            this.setBlockTick(Math.max(this.getBlockTick() - 20, 20));
                        }
                    }
                }
                //格挡挨打反击
            }
            boolean special = false;
            if ("La Verdadera Destreza".equals(string) || "Destreza".equals(string) || "Vaardigheid".equals(string) || "几何剑".equals(string) || "至高之术".equals(string)) {
                special = true;
            }
            if ("Commander".equals(string) || "指挥官".equals(string)) {
                special = true;
            }
            //突刺
            {
                if (spellNeed(3, 6, !special ? 6 : 2) && this.getTarget() != null && this.getSpecialMeleeChance() <= 0 && this.hasLineOfSight(this.getTarget()) && this.getAttackTick() <= 0) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_LUNGING,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (!this.level().isClientSide()) {
                        this.setAttackTick(10);
                        this.setSpecialMeleeChance(5);
                        this.setAnimTick(20);
                        this.setAttackUse(4);
                        this.level().broadcastEntityEvent(this, (byte) 104);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            if (!this.isInvisible()) {
                                serverLevel.sendParticles(JerotesVillageParticleTypes.RAPIER_LUNGING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                            }
                        }
                    }
                }
                if (this.getAttackTick() <= 2 && this.getAttackTick() > 0 && this.getAttackUse() == 4) {
                    this.RushAttack();
                }
                if (this.getAttackTick() > -10 && this.getAttackUse() == 7) {
                    //粒子
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 20; ++i) {
                            serverLevel.sendParticles(ParticleTypes.PORTAL,
                                    this.getRandomX(0.25) - this.getDeltaMovement().x, this.getRandomY() - 0.5f -  this.getDeltaMovement().y, this.getRandomZ(0.25) - this.getDeltaMovement().z, 0,
                                    this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z, -0.5f);
                        }
                    }
                }
            }
            //炸弹
            {
                if (spellNeed(3, 12, !special ? 4 : 1) && this.getTarget() != null && this.getBombCooldown() <= 0 && this.hasLineOfSight(this.getTarget()) && this.getAttackTick() <= 0) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_BOMB,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (!this.level().isClientSide()) {
                        this.setBombCooldown(240);
                        this.setBombTick(30);
                        this.setAnimTick(30);
                        this.setAnimationState("bomb");
                        if (this.level() instanceof ServerLevel serverLevel) {
                            if (!this.isInvisible()) {
                                serverLevel.sendParticles(JerotesVillageParticleTypes.OMINOUS_BOMB_TOSS_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                            }
                        }
                    }
                }
                if (this.getBombTick() <= 25 && this.getBombTick() > 10) {
                    this.BombRush();
                }
                if (this.getBombTick() == 10) {
                    if (this.getTarget() != null) {
                        this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
                        this.lookAt(this.getTarget(), 360.0f, 360.0f);
                    }
                    for (int i = 0; i < (special ? 5 : 3); ++i) {
                        OminousBombEntity spear = new OminousBombEntity(this.level(), this);
                        spear.shootFromRotation(this, this.getXRot(), (this.getYRot() - 3 + i * 3), 0.0f, 2f, 0);
                        if (!this.isSilent()) {
                            this.playSound(JerotesSoundEvents.ITEM_THROW, 2.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
                        }
                        this.level().addFreshEntity(spear);
                    }
                }
            }
        }
    }
    public boolean spellNeed(float min, float max, int time) {
        return !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= -10 && this.getTarget() != null && this.getTarget().isAlive()
                && this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
                && this.getRandom().nextInt(time * 20) == 1;
    }
    public boolean RushAttack() {
        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
            this.lookAt(this.getTarget(), 360.0f, 360.0f);
        }
        float f = this.getYRot();
        float f2 = this.getXRot();
        float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f4 = -Mth.sin(f2 * 0.017453292f);
        float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        float f7 = 0.25f;
        float f8 = f4 *= f7 / f6 * 2;
        if (this.getTarget() != null && ((int)this.getY() == (int)this.getTarget().getY()))
            f8 = 0;
        this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
        return true;
    }
    public boolean BombRush() {
        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
            this.lookAt(this.getTarget(), 360.0f, 360.0f);
        }
        float f = this.getYRot();
        float f2 = this.getXRot();
        float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f4 = -Mth.sin(f2 * 0.017453292f);
        float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        float f7 = 0.20f;
        if (this.getTarget() != null && this.distanceTo(this.getTarget()) > 6) {
            f7 = 0.15f;
        }
        float f8 = f4 *= f7 / f6 * 2;
        this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * -2, 0.015f, f5 *= f7 / f6 * -2));
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        int special = 0;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("La Verdadera Destreza".equals(string) || "Destreza".equals(string) || "Vaardigheid".equals(string) || "几何剑".equals(string) || "至高之术".equals(string)) {
            special = 1;
        }
        if ("Commander".equals(string) || "指挥官".equals(string)) {
            special = 2;
        }
        if (this.specialAction()) {
            return false;
        }
        if (this.getAttackTick() > 0 && getAttackUse() != 1) {
            return false;
        }
        int attackRandom = this.getRandom().nextInt(30);
        if (!this.level().isClientSide()) {
            if (this.getSpecialMeleeChance() > 0) {
                this.setSpecialMeleeChance(this.getSpecialMeleeChance() - 1);
            }
        }
        if (this.getMainHandItem().isEmpty()) {
            if (!this.level().isClientSide()) {
                this.setAttackTick(0);
                this.setAnimTick(5);
                this.setAttackUse(1);
                if (attackRandom < 20) {
                    this.setAnimationState("attack1");
                } else {
                    this.setAnimationState("attack2");
                }
            }
            return super.doHurtTarget(entity);
        }
        else {
            float attackUseFloat = 0.4f;
            boolean canNotSpecial = this.getSpecialMeleeChance() > 0
                    || this.getRandom().nextFloat() >= attackUseFloat;
            if (special >= 1) {
                canNotSpecial = this.getRandom().nextFloat() >= 0.8f;
            }
            //常规攻击
            if (canNotSpecial) {
                if (this.getSpecialMeleeChance() > 0) {
                    this.setSpecialMeleeChance(this.getSpecialMeleeChance() - 1);
                }
                if (!this.level().isClientSide()) {
                    this.setAttackTick(10);
                    this.setAnimTick(20);
                    this.setAttackUse(1);
                }
                this.level().broadcastEntityEvent(this, (byte) 101);
                return true;
            }
            else {
                if (!this.level().isClientSide()) {
                    this.setSpecialMeleeChance(special < 1 ? 3 : 0);
                }
                int specialRandom = this.getRandom().nextInt(40);
                //炸弹
                if (specialRandom > 30) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_BOMB,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (!this.level().isClientSide()) {
                        this.setBombCooldown(240);
                        this.setBombTick(30);
                        this.setAnimTick(30);
                        this.setAnimationState("bomb");
                        if (this.level() instanceof ServerLevel serverLevel) {
                            if (!this.isInvisible()) {
                                serverLevel.sendParticles(JerotesVillageParticleTypes.OMINOUS_BOMB_TOSS_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                            }
                        }
                    }
                }
                //连续刺击
               else if (specialRandom > 20) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_STAB,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.RAPIER_FLURRY_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                        }
                    }
                    if (!this.level().isClientSide()) {
                        this.setAttackTick(30);
                        this.setAnimTick(40);
                        this.setAttackUse(2);
                        this.level().broadcastEntityEvent(this, (byte) 102);
                    }
                }
                else if (specialRandom > 10) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_LUNGING,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.RAPIER_LUNGING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                        }
                    }
                    if (!this.level().isClientSide()) {
                        this.setAttackTick(10);
                        this.setSpecialMeleeChance(special < 1 ? 5 : 0);
                        this.setAnimTick(20);
                        this.setAttackUse(4);
                        this.level().broadcastEntityEvent(this, (byte) 104);
                    }
                }
                //格挡
                else {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_BLOCK,
                                this.getSoundSource(), 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.RAPIER_PARRY_RIPOSTE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                        }
                    }
                    if (!this.level().isClientSide()) {
                        this.setAttackUse(1);
                        this.setBlockTick(100);
                    }
                }
                return true;
            }
        }
    }

    @Override
    public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
        super.awardKillScore(entity, score, damageSource);
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Commander".equals(string) || "指挥官".equals(string)) {
            if (!this.level().isClientSide()) {
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 2, false, false), this);
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 0, false, false), this);
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.heal(livingEntity.getMaxHealth() / 20);
                for (MobEffectInstance mobEffectInstance : livingEntity.getActiveEffects()) {
                    if (!mobEffectInstance.getEffect().isBeneficial()) continue;
                    if (!this.level().isClientSide()) {
                        this.addEffect(mobEffectInstance, this);
                    }
                }
            }
        }
    }

    public boolean trueHurt() {
        int special = 0;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("La Verdadera Destreza".equals(string) || "Destreza".equals(string) || "Vaardigheid".equals(string) || "几何剑".equals(string) || "至高之术".equals(string)) {
            special = 1;
        }
        if ("Commander".equals(string) || "指挥官".equals(string)) {
            special = 2;
        }
        if (!this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ADVENTURER_ATTACK, this.getSoundSource(), 1.0f, 1.0f);
        }
        float damageMulti = 1f;
        float knockbackMulti = 1f;
        float reach = 0.5f;
        if (this.getAttackUse() == 2) {
            damageMulti = 0.85f;
            knockbackMulti = 0.01f;
            reach = 0.75f;
        }
        if (this.getAttackUse() == 3) {
            damageMulti = 1.85f;
            knockbackMulti = 3.0f;
            reach = 1.05f;
        }
        if (this.getAttackUse() == 4) {
            damageMulti = 1.65f;
            knockbackMulti = 1.5f;
            reach = 0.85f;
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            if (!Main.canSee(hurt, this)) continue;
            AttackFind.attackBegin(this, hurt);
            boolean bl2;
            if (this.getAttackUse() != 2) {
                bl2 = AttackFind.attackAfter(this, hurt, damageMulti + special * 0.5f, knockbackMulti, false, 0f);
            }
            else {
                DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
                bl2 = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, knockbackMulti, false, 0f);
            }
            if (bl2) {
                if (!(hurt.hasEffect(JerotesMobEffects.BLEEDING.get()))) {
                    if (!hurt.level().isClientSide) {
                        hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), hurt);
                    }
                }
                else {
                    int level = Objects.requireNonNull(hurt.getEffect(JerotesMobEffects.BLEEDING.get())).getAmplifier() + 1;
                    int time = Objects.requireNonNull(hurt.getEffect(JerotesMobEffects.BLEEDING.get())).getDuration();
                    int levelAdd = level;
                    int timeAdd = time + 5;
                    if (timeAdd > 120) {
                        levelAdd = level + 1;
                        timeAdd = 60;
                    }
                    if (levelAdd > (special > 0 ? 5 : 3)) {
                        levelAdd = special > 0 ? 5 : 3;
                    }
                    if (!hurt.level().isClientSide) {
                        hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), timeAdd, levelAdd, false, false), hurt);
                    }
                }
            }
        }
        //横扫效果
        Main.sweepAttack(this);
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
            ItemStack hand = this.getMainHandItem();
            hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        //随机移动
        if (this.getAttackUse() != 2) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.getRandom().nextFloat() - 0.5f) * 0.5f, 0, (this.getRandom().nextFloat() - 0.5f) * 0.5f));
        }
        else {
            this.setDeltaMovement(0,this.getDeltaMovement().y,0);
        }
        return true;
    }

    @Override
    protected int calculateFallDamage(float f, float f2) {
        return super.calculateFallDamage(f, f2) - 12;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (!this.isInvulnerableTo(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if ("Commander".equals(string) || "指挥官".equals(string)) {
                amount /= 1.5f;
            }
        }
        if (!this.isInvulnerableTo(damageSource) && !AttackFind.MagicResistance(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if ("La Verdadera Destreza".equals(string) || "Destreza".equals(string) || "Vaardigheid".equals(string) || "几何剑".equals(string) || "至高之术".equals(string)) {
                if (amount < this.getMaxHealth() && this.getRandom().nextFloat() > 0.75f) {
                    return false;
                }
            }
        }

        //格挡挨打反击
        if (this.getBlockTick() > 20) {
            if (!this.isInvulnerableTo(damageSource) && isDamageSourceBlocks(damageSource)) {
                if (!this.level().isClientSide()) {
                    this.setBlockTick(20);
                    this.setAttackTick(7);
                    this.setAnimTick(15);
                    this.setAttackUse(3);
                    this.level().broadcastEntityEvent(this, (byte) 103);
                }
                if (!this.level().isClientSide()) {
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.TWOHANDED_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
                    }
                }
                if (amount < this.getHealth() / 3) {
                    if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
                        ItemStack hand = this.getMainHandItem();
                        hand.hurtAndBreak((int) amount / 3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    }
                    return false;
                } else {
                    if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
                        ItemStack hand = this.getMainHandItem();
                        hand.hurtAndBreak((int) this.getHealth() / 3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    }
                    return super.hurt(damageSource, amount / 3);
                }
            }
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public void disableShield() {
        if (this.random.nextFloat() < 0.66) {
            if (this.shieldCoolDown < 100) {
                this.shieldCoolDown = 100;
            }
            this.stopUsingItem();
        }
    }

    @Override
    public void disableShieldTry() {
        if (this.random.nextFloat() < 0.05) {
            if (this.shieldCoolDown < 100) {
                this.shieldCoolDown = 100;
            }
            this.stopUsingItem();
        }
    }


    @Override
    public void handleEntityEvent(byte by) {
        if (by == 101) {
            int attackRandom = this.getRandom().nextInt(30);
            if (attackRandom > 25) {
                this.swordAttack1AnimationState.start(this.tickCount);
            } else if (attackRandom > 20) {
                this.swordAttack2AnimationState.start(this.tickCount);
            } else if (attackRandom > 10) {
                this.swordAttack3AnimationState.start(this.tickCount);
            } else if (attackRandom > 5) {
                this.swordAttack4AnimationState.start(this.tickCount);
            } else {
                this.swordAttack5AnimationState.start(this.tickCount);
            }
        }
        else if (by == 102) {
            this.swordAttack1AnimationState.stop();
            this.swordAttack2AnimationState.stop();
            this.swordAttack3AnimationState.stop();
            this.swordAttack4AnimationState.stop();
            this.swordAttack5AnimationState.stop();
            this.stabAnimationState.start(this.tickCount);
        }
        else if (by == 103) {
            this.swordAttack1AnimationState.stop();
            this.swordAttack2AnimationState.stop();
            this.swordAttack3AnimationState.stop();
            this.swordAttack4AnimationState.stop();
            this.swordAttack5AnimationState.stop();
            this.blockAttackAnimationState.start(this.tickCount);
        }
        else if (by == 104) {
            this.swordAttack1AnimationState.stop();
            this.swordAttack2AnimationState.stop();
            this.swordAttack3AnimationState.stop();
            this.swordAttack4AnimationState.stop();
            this.swordAttack5AnimationState.stop();
            this.lungingAttackAnimationState.start(this.tickCount);
        }
        else {
            super.handleEntityEvent(by);
        }
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setShieldLevel(2);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }
    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            this.swordAttack1AnimationState.stop();
            this.swordAttack2AnimationState.stop();
            this.swordAttack3AnimationState.stop();
            this.swordAttack4AnimationState.stop();
            this.swordAttack5AnimationState.stop();
            this.stabAnimationState.stop();
            this.blockAttackAnimationState.stop();
            this.lungingAttackAnimationState.stop();
            if (!this.level().isClientSide()) {
                this.setAnimTick(40);
                this.setAnimationState("dead");
            }
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_ADVENTURER_RAPIER.get());
    }
}

