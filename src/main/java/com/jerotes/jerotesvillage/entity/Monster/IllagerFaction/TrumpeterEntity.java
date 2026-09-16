package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotes.goal.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class TrumpeterEntity extends MeleeIllagerEntity implements SpellUseEntity {
    public AnimationState idleAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> BLOW_TICK = SynchedEntityData.defineId(TrumpeterEntity.class, EntityDataSerializers.INT);
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState itemAttackAnimationState = new AnimationState();
    public AnimationState blowAnimationState = new AnimationState();
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("5e71e582-5631-478e-bc04-ef5c1301b532");

    public TrumpeterEntity(EntityType<? extends TrumpeterEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 25;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
        builder = builder.add(Attributes.MAX_HEALTH, 32);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
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
                if (TrumpeterEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.TRUMPETER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.TRUMPETER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.TRUMPETER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.TRUMPETER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(Items.IRON_SWORD);
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
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if (this.getBlowTick() < 1) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    public int spellLevel = 1;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setBlowTick(int n){
        this.getEntityData().set(BLOW_TICK, n);
    }
    public int getBlowTick(){
        return this.getEntityData().get(BLOW_TICK);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "itemAttack")){
            return 2;
        }
        else if (Objects.equals(animation, "blow")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.itemAttackAnimationState);
        list.add(this.blowAnimationState);
        return list;
    }
    //
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
        if (component != null && (component.getString().equals("Elite"))) {
            Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Elite armor", 6f, AttributeModifier.Operation.ADDITION));
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("BlowTick", this.getBlowTick());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setBlowTick(compoundTag.getInt("BlowTick"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BLOW_TICK, 10);
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
                        this.attackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attackAnimationState);
                        break;
                    case 2:
                        this.itemAttackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttackAnimationState);
                        break;
                    case 3:
                        this.blowAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.blowAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Elite".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }
        if (this.getBlowTick() > 1) {
            if (!this.level().isClientSide()) {
                this.setBlowTick(this.getBlowTick() - 1);
            }
        }
        else if (this.getBlowTick() < 1 && this.getBlowTick() > -30) {
            if (!this.level().isClientSide()) {
                this.setBlowTick(this.getBlowTick() - 1);
            }
        }
        if (this.getBlowTick() == 1 && this.getOffhandItem().is(Items.GOAT_HORN) && this.getTarget() != null) {
            if (!this.level().isClientSide()) {
                this.setBlowTick(0);
            }
        }
        if (this.getBlowTick() == 0) {
            if (!this.level().isClientSide()) {
                this.setAnimTick(30);
                this.setAnimationState("blow");
            }
            this.startUsingItem(InteractionHand.OFF_HAND);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TRUMPETER_BLOW, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
            }
        }
        if (this.getBlowTick() <= -30) {
            if (!this.level().isClientSide()) {
                this.setBlowTick(360);
            }
            OtherSpellFind.OminousHorn(this, spellLevel * 30, spellLevel, spellLevel * 12, spellLevel + 2, 32);
        }
        if (this.getBlowTick() > 1 && this.useItem.is(Items.GOAT_HORN)) {
            this.stopUsingItem();
        }
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.getBlowTick() < 1) {
            return false;
        }
        if (!this.level().isClientSide()) {
            if (this.getMainHandItem().isEmpty()) {
                this.setAnimTick(5);
                this.setAnimationState("attack");
            }
            else {
                this.setAnimTick(10);
                this.setAnimationState("itemAttack");
            }
        }
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.TRUMPETER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(Items.IRON_SWORD);
    }
    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(Items.GOAT_HORN);
    }
}

