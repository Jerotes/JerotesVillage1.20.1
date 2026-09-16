package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotesvillage.control.QoaikuMoveControl;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SubmarinerEntity extends MeleeIllagerEntity implements SpellUseEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState itemAttackAnimationState = new AnimationState();
    public AnimationState waveAnimationState = new AnimationState();
    public AnimationState waveInWaterAnimationState = new AnimationState();

    public SubmarinerEntity(EntityType<? extends SubmarinerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 25;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.36);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (SubmarinerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.SUBMARINER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.SUBMARINER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.SUBMARINER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.SUBMARINER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_DAGGER.get());
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
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }
    public boolean isLandNavigatorType = true;
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get())
            return false;
        return super.canDrownInFluidType(type);
    }
    @Override
    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.75));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
            if (this.getSpellTick() > 220) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
                if (this.getTarget() != null) {
                    this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
                    this.lookAt(this.getTarget(), 360.0f, 360.0f);
                }
            }
        } else {
            super.travel(vec3);
            if (this.getSpellTick() > 220) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
                if (this.getTarget() != null) {
                    this.getLookControl().setLookAt(this.getTarget(), 10.0f, 10.0f);
                    this.lookAt(this.getTarget(), 10.0f, 10.0f);
                }
            }
        }
    }

    public int spellLevel = 3;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "itemAttack")){
            return 2;
        }
        else if (Objects.equals(animation, "wave")){
            return 3;
        }
        else if (Objects.equals(animation, "waveInWater")){
            return 4;
        }
        else {
            return 0;
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevel", this.spellLevel);
        this.writeInventoryToTag(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.readInventoryFromTag(compoundTag);
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.itemAttackAnimationState);
        list.add(this.waveAnimationState);
        list.add(this.waveInWaterAnimationState);
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
                        this.itemAttackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.itemAttackAnimationState);
                        break;
                    case 3:
                        this.waveAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.waveAnimationState);
                        break;
                    case 4:
                        this.waveInWaterAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.waveInWaterAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //水陆切换
        if (this.isInWater() && this.isLandNavigatorType) {
            this.moveControl = new QoaikuMoveControl(this);
            this.navigation = new AmphibiousPathNavigation(this, level());
            this.isLandNavigatorType = false;
        }
        if (!this.isInWater() && !this.isLandNavigatorType) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigatorType = true;
        }
        //腾跃
        if (this.getNavigation().getPath() != null && !this.getNavigation().isDone()) {
            BlockState blockState = this.level().getBlockState(this.getNavigation().getPath().getTarget());
            if ((!blockState.getFluidState().isEmpty() && !blockState.isAir() || this.getTarget() != null && this.getTarget().onGround()) && this.isInWater() && this.horizontalCollision) {
                for (int n = 0; n < 18; ++n) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getRandomX(0.5), this.getY() + 0.5f, this.getRandomZ(0.5),
                                0, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.2, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.5);
                    }
                }
                this.setDeltaMovement(this.getDeltaMovement().add(0,0.25f,0));
            }
        }
        //
        //技能-宝石海浪
        if (this.getTarget() != null && this.hasLineOfSight(this.getTarget()) && this.getSpellTick() <= 0 && this.distanceTo(this.getTarget()) < 6) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(240);
                this.setAnimTick(20);
                if (this.isInWater()) {
                    this.setAnimationState("waveInWater");
                }
                else {
                    this.setAnimationState("wave");
                }
            }
        }
        if (this.getSpellTick() > 220) {
            if (this.isInWater()) {
                this.waveAnimationState.stop();
            }
            else {
                this.waveInWaterAnimationState.stop();
            }
        }
        if (this.getSpellTick() == 225) {
            //法术列表-宝石海浪
            OtherSpellList.GemstoneWaves(getSpellLevel(), this, this).spellUse();
        }

        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.getSpellTick() > 220) {
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
                this.playSound(JerotesVillageSoundEvents.SUBMARINER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_DAGGER.get());
    }
    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_DAGGER.get());
    }
}

