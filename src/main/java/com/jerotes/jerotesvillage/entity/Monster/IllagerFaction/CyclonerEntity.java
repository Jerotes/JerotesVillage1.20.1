package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
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

public class CyclonerEntity extends MeleeIllagerEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState idle1AnimationState = new AnimationState();
    public AnimationState idle2AnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState rollAnimationState = new AnimationState();
    public AnimationState rollStartAnimationState = new AnimationState();
    public AnimationState rollStopAnimationState = new AnimationState();
    public AnimationState itemAttack1AnimationState = new AnimationState();
    public AnimationState itemAttack2AnimationState = new AnimationState();
    public AnimationState itemAttack3AnimationState = new AnimationState();
    public AnimationState itemAttack4AnimationState = new AnimationState();

    public CyclonerEntity(EntityType<? extends CyclonerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 25;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.HORNED_HELMET.get()), randomSource);
    }

    private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
        this.setItemSlot(equipmentSlot, itemStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
        builder = builder.add(Attributes.MAX_HEALTH, 32);
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
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, LivingEntity.class, 64.0f,0f,false){
            @Override
            public boolean canUse() {
                if (CyclonerEntity.this.getSpellTick() <= 0) {
                    return false;
                }
                if (this.lookAt != this.mob.getTarget()) {
                    return false;
                }
                return super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                if (CyclonerEntity.this.getSpellTick() <= 0) {
                    return false;
                }
                if (this.lookAt != this.mob.getTarget()) {
                    return false;
                }
                return super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(CyclonerEntity.this, 1.1, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (CyclonerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.CYCLONER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.CYCLONER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.CYCLONER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.CYCLONER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_MACE.get());
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
        this.setItemSlot(EquipmentSlot.OFFHAND, itemStack);
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
        return aabb1.inflate(0.5d, 0.5d, 0.5d);
    }
    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
        return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
    }

    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "roll")){
            return 2;
        }
        else if (Objects.equals(animation, "rollStart")){
            return 3;
        }
        else if (Objects.equals(animation, "rollStop")){
            return 4;
        }
        else if (Objects.equals(animation, "itemAttack1")) {
            return 5;
        }
        else if (Objects.equals(animation, "itemAttack2")) {
            return 6;
        }
        else if (Objects.equals(animation, "itemAttack3")) {
            return 7;
        }
        else if (Objects.equals(animation, "itemAttack4")) {
            return 8;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.rollAnimationState);
        list.add(this.rollStartAnimationState);
        list.add(this.rollStopAnimationState);
        list.add(this.itemAttack1AnimationState);
        list.add(this.itemAttack2AnimationState);
        list.add(this.itemAttack3AnimationState);
        list.add(this.itemAttack4AnimationState);
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
                        this.rollAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.rollAnimationState);
                        break;
                    case 3:
                        this.rollStartAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.rollStartAnimationState);
                        break;
                    case 4:
                        this.rollStopAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.rollStopAnimationState);
                        break;
                    case 5:
                        this.itemAttack1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack1AnimationState);
                        break;
                    case 6:
                        this.itemAttack2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack2AnimationState);
                        break;
                    case 7:
                        this.itemAttack3AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack3AnimationState);
                        break;
                    case 8:
                        this.itemAttack4AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttack4AnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //旋风
        if (this.getTarget() != null && this.isAggressive() && this.getSpellTick() <= -120 && this.getRandom().nextInt(60) == 1) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(120);
                if (this.level() instanceof ServerLevel serverLevel) {
                    if (!this.isInvisible()) {
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CYCLONER_CYCLONES_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                    }
                }
                if (!this.level().isClientSide()) {
                    this.setAnimTick(20);
                    this.setAnimationState("rollStart");
                }
            }
        }
        if (this.getSpellTick() > -120 && this.isAlive()) {
            //旋转状态
            if (this.getSpellTick() > 0) {
                if (this.getTarget() != null) {
                    if (this.getSpellTick() % 10 == 0 && this.onGround()) {
                        this.jumpToTarget(this.getTarget());
                    }
                }
                if (this.getSpellTick() % 2 == 0) {
                    if (!this.isSilent()) {
                        this.playSound(JerotesVillageSoundEvents.CYCLONER_ROLL, 1.0f, 1.0f);
                    }
                }
                //攻击
                if (this.getSpellTick() % 5 == 0) {
                    this.trueHurt();
                }
                if (this.getSpellTick() == 10 && this.getSpellTick() <= 100) {
                    this.rollStartAnimationState.stop();
                    if (!this.level().isClientSide()) {
                        this.setAnimTick(10);
                        this.setAnimationState("rollStop");
                    }
                }
                else if (this.getSpellTick() % 10 == 0 && this.getSpellTick() <= 100) {
                    this.rollStartAnimationState.stop();
                    if (!this.level().isClientSide()) {
                        this.setAnimTick(10);
                        this.setAnimationState("roll");
                    }
                }
            }
        }
        if (this.getSpellTick() <= 0 || !this.isAlive()) {
            this.rollAnimationState.stop();
        }
        //站立
        if (this.getSpellTick() <= 0 && this.isAlive()) {
            if (!this.isLeftHanded()) {
                if (!this.getMainHandItem().isEmpty()) {
                    this.idle1AnimationState.startIfStopped((this.tickCount));
                }
                if (!this.getOffhandItem().isEmpty()) {
                    this.idle2AnimationState.startIfStopped((this.tickCount));
                }
            }
            else {
                if (!this.getMainHandItem().isEmpty()) {
                    this.idle2AnimationState.startIfStopped((this.tickCount));
                }
                if (!this.getOffhandItem().isEmpty()) {
                    this.idle1AnimationState.startIfStopped((this.tickCount));
                }
            }
        }
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.getSpellTick() > 0)
            return false;
        this.attackAnimation();
        this.level().broadcastEntityEvent(this, (byte)4);
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.CYCLONER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }
    public void attackAnimation() {
        if (this.getMainHandItem().isEmpty()) {
            if (!this.level().isClientSide()) {
                this.setAnimTick(8);
                this.setAnimationState("attack");
            }
        }
        else {
            if (!this.level().isClientSide()) {
                int attackRandom = this.getRandom().nextInt(40);
                if (attackRandom < 10) {
                    this.setAnimTick(15);
                    this.setAnimationState("itemAttack1");
                } else if (attackRandom < 20) {
                    this.setAnimTick(15);
                    this.setAnimationState("itemAttack2");
                } else if (attackRandom < 30) {
                    this.setAnimTick(15);
                    this.setAnimationState("itemAttack3");
                } else {
                    this.setAnimTick(15);
                    this.setAnimationState("itemAttack4");
                }
            }
        }
    }

    public boolean trueHurt() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.25));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            AttackFind.attackBegin(this, hurt);
            AttackFind.attackAfter(this, hurt, 0.75f, 1.5f, false, 0f);
        }
        //横扫效果
        Main.sweepAttack(this);
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
            ItemStack hand = this.getMainHandItem();
            hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }


    public boolean jumpToTarget(LivingEntity target) {
        this.lookAt(target, 360.0f, 360.0f);
        final double smoothX = Mth.clamp(Math.abs(target.getX() - this.getX()), 0, 1);
        final double smoothZ = Mth.clamp(Math.abs(target.getZ() - this.getZ()), 0, 1);
        final double d0 = (target.getX() - this.getX()) * 0.3 * smoothX;
        final double d2 = (target.getZ() - this.getZ()) * 0.3 * smoothZ;
        final float up = 0.5f + this.getRandom().nextFloat() * 0.8f;
        this.setDeltaMovement(this.getDeltaMovement().add(d0 * 0.75, up, d2 * 0.75));
        this.getNavigation().moveTo(target, 1.0f);
        return true;
    }

    @Override
    protected int calculateFallDamage(float f, float f2) {
        return super.calculateFallDamage(f, f2) - 12;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FALL) && this.getSpellTick() > 0)
            return true;
        return super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (!this.level().isClientSide()) {
            this.setSpellTick(this.getSpellTick() - 40);
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_MACE.get());
    }
    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_MACE.get());
    }
}

