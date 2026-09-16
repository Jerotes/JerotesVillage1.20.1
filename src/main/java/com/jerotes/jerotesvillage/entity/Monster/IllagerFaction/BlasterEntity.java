package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.UseThrowEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedThrowAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BlasterEntity extends MeleeIllagerEntity implements AlwaysShowArmIllagerEntity, RangedAttackMob, UseThrowEntity {
    public AnimationState idle1AnimationState = new AnimationState();
    public AnimationState idle2AnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState throw1AnimationState = new AnimationState();
    public AnimationState throw2AnimationState = new AnimationState();

    public BlasterEntity(EntityType<? extends BlasterEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.36);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (BlasterEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        ItemStack handItem = this.getMainHandItem();
        if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
            handItem = this.getOffhandItem();
        }
        if (InventoryEntity.isThrow(handItem)) {
            useThrowShoot(this, livingEntity);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
            }
        }
        if (!this.isSilent()) {
            this.playSound(JerotesVillageSoundEvents.BLASTER_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        }
        if (!this.level().isClientSide()) {
            this.setThrowTick(80);
            this.setAnimTick(10);
            if (handItem == this.getMainHandItem()) {
                this.setAnimationState("throw1");
            }
            else {
                this.setAnimationState("throw2");
            }
        }
        this.idle1AnimationState.stop();
        this.idle2AnimationState.stop();
        if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
    }

    @Override
    public float meleeOrRangeDistance() {
        return 6.0f;
    }
    @Override
    public boolean canUseThrow() {
        return true;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.BLASTER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.BLASTER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.BLASTER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.BLASTER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_CUTLASS.get());
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

    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "throw1")){
            return 2;
        }
        else if (Objects.equals(animation, "throw2")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.throw1AnimationState);
        list.add(this.throw2AnimationState);
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
                        this.throw1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.throw1AnimationState);
                        break;
                    case 3:
                        this.throw2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.throw2AnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getTarget() == null) {
            if (!this.level().isClientSide()) {
                this.setThrowTick(80);
            }
        }
        if (this.isAlive() && getAttackTick() < 12 && getThrowTick() > 50) {
            if (InventoryEntity.isThrow(this.getMainHandItem())) {
                this.idle1AnimationState.startIfStopped(this.tickCount);
            }
            if (InventoryEntity.isThrow(this.getOffhandItem())) {
                this.idle2AnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!this.level().isClientSide()) {
            this.setThrowTick(0);
            this.setAnimTick(5);
            this.setAnimationState("attack");
        }
        this.idle1AnimationState.stop();
        this.idle2AnimationState.stop();
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.BLASTER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (damageSource.is(DamageTypeTags.IS_EXPLOSION))
            return super.hurt(damageSource, amount / 5);
        return super.hurt(damageSource, amount);
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_CUTLASS.get());
    }
    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_BOMB.get());
    }
}

