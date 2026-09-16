package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireSpitterEntity extends SpellIllagerEntity implements RangedAttackMob, SpellUseEntity {
    public AnimationState drinkAnimationState = new AnimationState();
    public AnimationState spitteAnimationState = new AnimationState();

    public FireSpitterEntity(EntityType<? extends FireSpitterEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 25;
        this.setCanPickUpLoot(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 32);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 20, 8.0F));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (FireSpitterEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        if (this.hasEffect(MobEffects.FIRE_RESISTANCE) && this.getSpellOneTick() <= 0) {
            //技能-不祥火舌术
            if (!this.level().isClientSide()) {
                this.setSpellTick(60);
                this.setSpellOneTick(120);
                this.setAnimTick(60);
                this.setAnimationState("spitte");
            }
            //技能列表-不祥火舌术
            OtherSpellList.OminousFlames(this.getSpellLevel(), this, livingEntity).spellUse();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.FIRE_SPITTER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.FIRE_SPITTER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.FIRE_SPITTER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.FIRE_SPITTER_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.FIRE_SPITTER_SPITTE;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.WOLOLO;
    }
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if ((this.getSpellOneTick() > 0 && this.getTarget() != null && this.distanceTo(this.getTarget()) < 12) || this.getUseItem().getItem() instanceof PotionItem) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    public int spellLevel = 2;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "drink")){
            return 1;
        }
        else if (Objects.equals(animation, "spitte")){
            return 2;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.drinkAnimationState);
        list.add(this.spitteAnimationState);
        return list;
    }
    //
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevel", this.spellLevel);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevel = compoundTag.getInt("SpellLevel");
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
                        this.drinkAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.drinkAnimationState);
                        break;
                    case 2:
                        this.spitteAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.spitteAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isUsingItem() && (this.getTarget() != null || (this.getRandom().nextInt(30 * 20) == 1 && !this.isNoAi())) && !this.hasEffect(MobEffects.FIRE_RESISTANCE) && this.getOffhandItem().getItem() == Items.POTION && (PotionUtils.getPotion(this.getOffhandItem()) == Potions.LONG_FIRE_RESISTANCE || PotionUtils.getPotion(this.getOffhandItem()) == Potions.FIRE_RESISTANCE)) {
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.FIRE_SPITTER_DRINK, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
            }
            this.startUsingItem(InteractionHand.OFF_HAND);
            if (!this.level().isClientSide()) {
                this.setSpellTick(35);
                this.setSpellOneTick(35);
                this.setAnimTick(35);
                this.setAnimationState("drink");
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (AttackFind.isFireDamage(damageSource)) {
            if (!this.isUsingItem() && !this.hasEffect(MobEffects.FIRE_RESISTANCE) && this.getOffhandItem().getItem() == Items.POTION && (PotionUtils.getPotion(this.getOffhandItem()) == Potions.LONG_FIRE_RESISTANCE || PotionUtils.getPotion(this.getOffhandItem()) == Potions.FIRE_RESISTANCE)) {
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.FIRE_SPITTER_DRINK, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
                }
                this.startUsingItem(InteractionHand.OFF_HAND);
                if (!this.level().isClientSide()) {
                    this.setSpellTick(35);
                    this.setSpellOneTick(35);
                    this.setAnimTick(35);
                    this.setAnimationState("drink");
                }
            }
            return super.hurt(damageSource, amount / 5);
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        Potion potion = Potions.FIRE_RESISTANCE;
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }
}

