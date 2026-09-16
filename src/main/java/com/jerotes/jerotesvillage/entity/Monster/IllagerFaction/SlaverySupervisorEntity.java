package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoalNotSwing;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.AnimatedChainEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotesvillage.init.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SlaverySupervisorEntity extends MeleeIllagerEntity implements EliteEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attack1AnimationState = new AnimationState();
    public AnimationState attack2AnimationState = new AnimationState();
    public AnimationState swordAttack1AnimationState = new AnimationState();
    public AnimationState swordAttack2AnimationState = new AnimationState();
    public AnimationState swordAttack3AnimationState = new AnimationState();
    public AnimationState swordAttack4AnimationState = new AnimationState();
    public AnimationState getAttackAnimationState = new AnimationState();
    public AnimationState setAttackAnimationState = new AnimationState();
    public AnimationState leadAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> SPECIAL_MELEE_CHANCE = SynchedEntityData.defineId(SlaverySupervisorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SET_ATTACK_TICK = SynchedEntityData.defineId(SlaverySupervisorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GET_ATTACK_TICK = SynchedEntityData.defineId(SlaverySupervisorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GET_ATTACK_COOLDOWN = SynchedEntityData.defineId(SlaverySupervisorEntity.class, EntityDataSerializers.INT);
    private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6, false);

    public SlaverySupervisorEntity(EntityType<? extends SlaverySupervisorEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 80;
        this.setCanPickUpLoot(false);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_HELMET.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_CHESTPLATE.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_LEGGINGS.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_BOOTS.get()), randomSource);
    }

    private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
        this.setItemSlot(equipmentSlot, itemStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
        builder = builder.add(Attributes.MAX_HEALTH, 80);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
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
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoalNotSwing(this, 1.1, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (SlaverySupervisorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_CHEER;
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
        ItemStack itemStack = new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_HAMMER_WHIP.get());
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
        if (this.specialAction()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    public boolean specialAction() {
        return this.getSpellTick() > 80 || this.getSetAttackTick() > 0 || this.getGetAttackTick() > 0;
    }
    public void setSpecialMeleeChance(int n){
        this.getEntityData().set(SPECIAL_MELEE_CHANCE, n);
    }
    public int getSpecialMeleeChance(){
        return this.getEntityData().get(SPECIAL_MELEE_CHANCE);
    }
    public void setGetAttackTick(int n){
        this.getEntityData().set(GET_ATTACK_TICK, n);
    }
    public int getGetAttackTick(){
        return this.getEntityData().get(GET_ATTACK_TICK);
    }
    public void setSetAttackTick(int n){
        this.getEntityData().set(SET_ATTACK_TICK, n);
    }
    public int getSetAttackTick(){
        return this.getEntityData().get(SET_ATTACK_TICK);
    }
    public void setGetAttackCooldown(int n){
        this.getEntityData().set(GET_ATTACK_COOLDOWN, n);
    }
    public int getGetAttackCooldown(){
        return this.getEntityData().get(GET_ATTACK_COOLDOWN);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack1")){
            return 1;
        }
        else if (Objects.equals(animation, "attack2")){
            return 2;
        }
        else if (Objects.equals(animation, "swordAttack1")){
            return 3;
        }
        else if (Objects.equals(animation, "swordAttack2")){
            return 4;
        }
        else if (Objects.equals(animation, "swordAttack3")){
            return 5;
        }
        else if (Objects.equals(animation, "swordAttack4")){
            return 6;
        }
        else if (Objects.equals(animation, "getAttack")){
            return 7;
        }
        else if (Objects.equals(animation, "setAttack")){
            return 8;
        }
        else if (Objects.equals(animation, "lead")){
            return 9;
        }
        else if (Objects.equals(animation, "dead")){
            return 10;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attack1AnimationState);
        list.add(this.attack2AnimationState);
        list.add(this.swordAttack1AnimationState);
        list.add(this.swordAttack2AnimationState);
        list.add(this.swordAttack3AnimationState);
        list.add(this.swordAttack4AnimationState);
        list.add(this.getAttackAnimationState);
        list.add(this.setAttackAnimationState);
        list.add(this.leadAnimationState);
        list.add(this.deadAnimationState);
        return list;
    }
    //
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpecialMeleeChance", this.getSpecialMeleeChance());
        compoundTag.putInt("SetAttackTick", this.getSetAttackTick());
        compoundTag.putInt("GetAttackTick", this.getGetAttackTick());
        compoundTag.putInt("GetAttackCooldown", this.getGetAttackCooldown());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        this.setSpecialMeleeChance(compoundTag.getInt("SpecialMeleeChance"));
        this.setSetAttackTick(compoundTag.getInt("SetAttackTick"));
        this.setGetAttackTick(compoundTag.getInt("GetAttackTick"));
        this.setGetAttackCooldown(compoundTag.getInt("GetAttackCooldown"));
        this.bossEvent.setId(this.getUUID());
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SPECIAL_MELEE_CHANCE, 0);
        this.getEntityData().define(SET_ATTACK_TICK, 0);
        this.getEntityData().define(GET_ATTACK_TICK, 0);
        this.getEntityData().define(GET_ATTACK_COOLDOWN, 0);
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
                        this.swordAttack1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.swordAttack1AnimationState);
                        break;
                    case 4:
                        this.swordAttack2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.swordAttack2AnimationState);
                        break;
                    case 5:
                        this.swordAttack3AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.swordAttack3AnimationState);
                        break;
                    case 6:
                        this.swordAttack4AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.swordAttack4AnimationState);
                        break;
                    case 7:
                        this.getAttackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.getAttackAnimationState);
                        break;
                    case 8:
                        this.setAttackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.setAttackAnimationState);
                        break;
                    case 9:
                        this.leadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.leadAnimationState);
                        break;
                    case 10:
                        this.deadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.deadAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
            this.bossEvent.update();
            if (OtherMainConfig.EliteBossBarOnlyCombat) {
                this.bossEvent.setVisible(this.getTarget() != null);
            }
        }
        //技能-执刑捆绑
        if (this.getSetAttackTick() == 19) {
            this.SetAttack();
        }
        //技能-缴械牵扯
        if (this.getGetAttackTick() == 17 && this.getTarget() != null) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_GET, 5.0f, 1.0f);
            }
            OtherSpellFind.DisarmDrag(this, this.getTarget(), 3, 0, 1, 0, false);
        }
        if (this.getGetAttackTick() <= 0 &&
                this.getItemInHand(InteractionHand.MAIN_HAND).getItem() == JerotesVillageItems.SLAVERY_SUPERVISOR_HAMMER_WHIP.get() &&
                this.getGetAttackCooldown() <= 0 &&
                !this.isNoAi() &&
                this.getTarget() != null &&
                (this.getRandom().nextInt(120) == 1 || "Noble".equals(string)) &&
                this.distanceTo(this.getTarget()) < 24 &&
                !this.specialAction() &&
                this.hasLineOfSight(this.getTarget())
        ) {
            if (!this.level().isClientSide()) {
                this.setGetAttackTick(25);
                this.setGetAttackCooldown(160);
                if ("Noble".equals(string)) {
                    this.setGetAttackCooldown(80);
                }
                this.setAnimTick(20);
                this.setAnimationState("getAttack");
            }
        }
        if (this.getAttackTick() == 0 && this.isAlive()) {
            this.trueHurt();
        }
        if (!this.level().isClientSide()) {
            this.setGetAttackTick(Math.max(0, this.getGetAttackTick() - 1));
            this.setGetAttackCooldown(Math.max(0, this.getGetAttackCooldown() - 1));
            this.setSetAttackTick(Math.max(0, this.getSetAttackTick() - 1));
        }
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.specialAction()) {
            return false;
        }
        float attackUseFloat = 0.2f;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Noble".equals(string)) {
            attackUseFloat = 0.6f;
        }
        boolean canNotSpecial = this.getSpecialMeleeChance() > 0
                || this.getRandom().nextFloat() >= attackUseFloat
                || this.getItemInHand(InteractionHand.MAIN_HAND).getItem() != JerotesVillageItems.SLAVERY_SUPERVISOR_HAMMER_WHIP.get()
                || entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(JerotesMobEffects.ENSLAVEMENT.get());
        //常规攻击
        if (canNotSpecial) {
            int attackRandom = this.getRandom().nextInt(30);
            if (!this.level().isClientSide()) {
                this.setGetAttackCooldown(160);
                if (this.getSpecialMeleeChance() > 0) {
                    this.setSpecialMeleeChance(this.getSpecialMeleeChance() - 1);
                }
            }
            if (this.getMainHandItem().isEmpty()) {
                if (!this.level().isClientSide()) {
                    this.setAttackTick(0);
                    this.setAnimTick(5);
                    if (attackRandom < 20) {
                        this.setAnimationState("attack1");
                    } else {
                        this.setAnimationState("attack2");
                    }
                }
                return super.doHurtTarget(entity);
            }
            else {
                this.setAttackTick(10);
                this.setAnimTick(20);
                if (attackRandom > 20) {
                    this.setAnimationState("swordAttack1");
                } else if (attackRandom > 10) {
                    this.setAnimationState("swordAttack2");
                } else if (attackRandom > 5) {
                    this.setAnimationState("swordAttack3");
                } else {
                    this.setAnimationState("swordAttack4");
                }
                return true;
            }
        }
        else {
            //技能-执刑捆绑
            if (!this.level().isClientSide()) {
                this.setSpecialMeleeChance(3);
                this.setSetAttackTick(25);
                this.setGetAttackCooldown(160);
                this.setAnimTick(20);
                this.setAnimationState("setAttack");
            }
            if (this.level() instanceof ServerLevel serverLevel) {
                if (!this.isInvisible()) {
                    serverLevel.sendParticles(JerotesVillageParticleTypes.EXECUTION_BINDING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                }
            }
            return true;
        }
    }

    public boolean trueHurt() {
        if (!this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_ATTACK, this.getSoundSource(), 1.0f, 1.0f);
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            if (!Main.canSee(hurt, this)) continue;
            AttackFind.attackBegin(this, hurt);
            AttackFind.attackAfter(this, hurt, 1.0f, 1.0f, false, 0f);
        }
        //横扫效果
        Main.sweepAttack(this);
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
            ItemStack hand = this.getMainHandItem();
            hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    public boolean SetAttack() {
        //技能-执刑捆绑
        PlayerTeam teams = (PlayerTeam) this.getTeam();
        if (!this.isSilent()) {
            this.playSound(JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_SET, 5.0f, 1.0f);
        }
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(2));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!Main.canSee(hurt, this)) continue;
            AttackFind.attackBegin(this, hurt);
            boolean bl = AttackFind.attackAfter(this, hurt, 1.5f, 2.0f, false, 0f);
            if (bl) {
                if (hurt.getHealth() > this.getHealth() * 4) continue;
                if (hurt.getHealth() > this.getHealth() * 2 && !"Noble".equals(string)) continue;
                if (!(Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))) continue;
                if (hurt instanceof AnimatedChainEntity) continue;
                if (this.level() instanceof ServerLevel serverLevel) {
                    AnimatedChainEntity chain = JerotesVillageEntityType.ANIMATED_CHAIN.get().spawn(serverLevel, BlockPos.containing(hurt.getX(), hurt.getY(), hurt.getZ()), MobSpawnType.MOB_SUMMONED);
                    if (chain != null) {
                        chain.setOwner(this);
                        chain.setPrisoner(hurt);
                        chain.setSize(Main.mobWidth(hurt));
                        if (teams != null) {
                            serverLevel.getScoreboard().addPlayerToTeam(chain.getStringUUID(), teams);
                        }
                        chain.setTarget(hurt);
                    }
                    serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
                }
            }
        }
        //横扫效果
        Main.sweepAttack(this);
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
            ItemStack hand = this.getMainHandItem();
            hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }


    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Noble".equals(string)) {
            amount *= 0.85f;
        }
        List<DefectorEntity> listDefector = this.level().getEntitiesOfClass(DefectorEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        listDefector.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        List<SlaverySupervisorEntity> ListSlaverySupervisor = this.level().getEntitiesOfClass(SlaverySupervisorEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        ListSlaverySupervisor.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        if (this.getTarget() != null && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount > 6 && this.getRandom().nextFloat() > 0.6f && this.getSpellTick() <= 0 && listDefector.size() <= ListSlaverySupervisor.size() * 16) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(100);
                this.setAnimTick(20);
                this.setAnimationState("lead");
            }
            OtherSpellFind.AtonementResistance(this);
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.SLAVERY_SUPERVISOR_LEAD, 5.0f, 1.0f);
            }
            return false;
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


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setShieldLevel(2);
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
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_HAMMER_WHIP.get());
    }
}

