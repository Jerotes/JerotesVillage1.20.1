package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExecutionerEntity extends MeleeIllagerEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState itemAttack1AnimationState = new AnimationState();
    public AnimationState itemAttack2AnimationState = new AnimationState();

    public ExecutionerEntity(EntityType<? extends ExecutionerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 30;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
        builder = builder.add(Attributes.MAX_HEALTH, 48);
        builder = builder.add(Attributes.ARMOR, 5);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(ExecutionerEntity.this, 1.1, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (ExecutionerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.EXECUTIONER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.EXECUTIONER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.EXECUTIONER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.EXECUTIONER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        float f = this.getRandom().nextFloat();
        ItemStack itemStack = new ItemStack(JerotesVillageItems.EXECUTIONERS_AXE.get());
        if (f > 0.75) {
            itemStack = new ItemStack(JerotesVillageItems.EXECUTIONERS_HAMMER.get());
        }
        else if (f > 0.5) {
            itemStack = new ItemStack(JerotesVillageItems.EXECUTIONERS_HALBERD.get());
        }
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
            return aabb1.inflate(1.1d, 1.1d, 1.1d);
        return aabb1.inflate(0.5d, 0.5d, 0.5d);
    }
    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
        return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
    }

    public float thisTickRenderTime = 0;
    public float lastTickRenderTime = 6;
    public float attackAnimProgress = 0.0f;
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "itemAttack1")){
            return 2;
        }
        else if (Objects.equals(animation, "itemAttack2")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.itemAttack1AnimationState);
        list.add(this.itemAttack2AnimationState);
        return list;
    }
    //
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ANIM_STATE.equals(entityDataAccessor)) {
            if (this.level().isClientSide()) {
                switch (this.entityData.get(ANIM_STATE)){
                    case 0:
                        this.stopAllAnimation();
                        break;
                    case 1:
                        this.attackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attackAnimationState);
                        break;
                    case 2:
                        this.itemAttack1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack1AnimationState);
                        break;
                    case 3:
                        this.itemAttack2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack2AnimationState);
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
        if (this.getAttackTick() == 0 && this.isAlive()) {
            this.trueHurt();
        }
        if (this.getAttackTick() > 0) {
            if (this.getTarget() != null && this.getAttackTick() > 15) {
                this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
                this.lookAt(this.getTarget(), 360.0f, 360.0f);
            }
            this.getNavigation().stop();
        }
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.getAttackTick() <= this.getMinAttack()) {
            if (this.getMainHandItem().isEmpty()) {
                if (!this.level().isClientSide()) {
                    this.setAttackTick(0);
                    this.setAnimTick(5);
                    this.setAnimationState("attack");
                }
                return super.doHurtTarget(entity);
            }
            else {
                if (!this.level().isClientSide()) {
                    int attackRandom = this.getRandom().nextInt(30);
                    if (attackRandom < 25) {
                        this.setAttackTick(10);
                        this.setAnimTick(25);
                        this.setAnimationState("itemAttack1");
                    } else {
                        this.setAttackTick(10);
                        this.setAnimTick(30);
                        this.setAnimationState("itemAttack2");
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean trueHurt() {
        if (!this.isSilent()) {
            this.playSound(JerotesVillageSoundEvents.EXECUTIONER_ATTACK, 1.0f, 1.0f);
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            if (!Main.canSee(hurt, this)) continue;
            AttackFind.attackBegin(this, hurt);
            AttackFind.attackAfter(this, hurt, 1.1f, 1.0f, false, 0f);
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
    public ItemStack createSpawnWeapon(float weaponRandom) {
        if (weaponRandom > 0.75) {
            return new ItemStack(JerotesVillageItems.EXECUTIONERS_HAMMER.get());
        }
        else if (weaponRandom > 0.5) {
            return new ItemStack(JerotesVillageItems.EXECUTIONERS_HALBERD.get());
        }
        return new ItemStack(JerotesVillageItems.EXECUTIONERS_AXE.get());
    }
}

