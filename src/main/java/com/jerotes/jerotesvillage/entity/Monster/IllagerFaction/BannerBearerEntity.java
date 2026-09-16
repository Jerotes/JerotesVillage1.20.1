package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BannerBearerEntity extends MeleeIllagerEntity implements AlwaysShowArmIllagerEntity {
    private static final EntityDataAccessor<Integer> BANNER_TICK = SynchedEntityData.defineId(BannerBearerEntity.class, EntityDataSerializers.INT);
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState upAnimationState = new AnimationState();
    public AnimationState roundAnimationState = new AnimationState();

    public BannerBearerEntity(EntityType<? extends BannerBearerEntity> entityType, Level level) {
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
        builder = builder.add(Attributes.ARMOR, 5);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
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
        this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (BannerBearerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true));
        this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.BANNER_BEARER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.BANNER_BEARER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.BANNER_BEARER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.BANNER_BEARER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
    }
    @Override
    public MobType getMobType() {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Flag Zombie".equals(string)) {
            return MobType.UNDEAD;
        }
        return super.getMobType();
    }

    public void setBannerTick(int n){
        this.getEntityData().set(BANNER_TICK, n);
    }
    public int getBannerTick(){
        return this.getEntityData().get(BANNER_TICK);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "up")){
            return 2;
        }
        else if (Objects.equals(animation, "round")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.upAnimationState);
        list.add(this.roundAnimationState);
        return list;
    }
    //
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BannerTick", this.getBannerTick());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBannerTick(compoundTag.getInt("BannerTick"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BANNER_TICK, 0);
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
                        this.upAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.upAnimationState);
                        break;
                    case 3:
                        this.roundAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.roundAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.setBannerTick(Math.max(0, this.getBannerTick() - 1));
        }
        List<Mob> listRaider = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
        listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity.getTarget() == this || !((EntityFactionFind.isRaider(entity) && this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        listRaider.removeIf(entity -> entity.getTarget() != null);
        if ((this.getTarget() != null && !listRaider.isEmpty() && this.random.nextInt(60) == 1 && this.getBannerTick() <= 0)) {
            if (!this.level().isClientSide()) {
                this.setBannerTick(60);
                this.setAnimTick(25);
                if (this.getRandom().nextFloat() > 0.5f) {
                    this.setAnimationState("round");
                }
                else {
                    this.setAnimationState("up");
                }
            }
            OtherSpellFind.CombatInstruction(this, this.getTarget(), 32);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BANNER_BEARER_ROUND, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
            }
        }
        //站立动画
        if (this.isAlive()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!this.level().isClientSide()) {
            this.setAnimTick(10);
            this.setAnimationState("attack");
        }
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.BANNER_BEARER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }
}

