package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BitterColdSorcererEntity extends SpellIllagerEntity implements SpellUseEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState shoot1AnimationState = new AnimationState();
    public AnimationState shoot2AnimationState = new AnimationState();
    public AnimationState ceremonyAnimationState = new AnimationState();

    public BitterColdSorcererEntity(EntityType<? extends BitterColdSorcererEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 30;
        this.setCanPickUpLoot(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 0);
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

        this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f) {
            @Override
            public void tick() {
                int spellLevels = BitterColdSorcererEntity.this.getSpellLevel();
                String string = ChatFormatting.stripFormatting(BitterColdSorcererEntity.this.getName().getString());
                if ("Frozen".equals(string)) {
                    spellLevels = BitterColdSorcererEntity.this.getSpellLevel() + 1;
                }
                this.spellLevel = spellLevels;
                super.tick();
            }
        });
        this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f) {
            @Override
            public void tick() {
                int spellLevels = BitterColdSorcererEntity.this.getSpellLevel();
                String string = ChatFormatting.stripFormatting(BitterColdSorcererEntity.this.getName().getString());
                if ("Frozen".equals(string)) {
                    spellLevels = BitterColdSorcererEntity.this.getSpellLevel() + 1;
                }
                this.spellLevel = spellLevels;
                super.tick();
            }
        });
        this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (BitterColdSorcererEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
    public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
        if (!this.level().isClientSide()) {
            this.setSpellTick(20);
        }
        if (string.equals("jerotesvillage_bitter_cold_frostbite")) {
            if (!this.level().isClientSide()) {
                this.setAnimTick(20);
                this.setAnimationState("shoot2");
            }
        }
        else {
            if (!this.level().isClientSide()) {
                this.setAnimTick(9);
                this.setAnimationState("shoot1");
            }
        }
    }
    @Override
    public List<SpellTypeInterface> SelfMainSpellList() {
        List<SpellTypeInterface> spellList = new ArrayList<>();
        if (!(this.getTarget() != null && this.distanceTo(this.getTarget()) > 8)) {
            //技能列表-苦寒霜噬术
            spellList.add(OtherSpellType.JEROTESVILLAGE_BITTER_COLD_FROSTBITE);
        }
        else {
            //技能列表-苦寒冰锥
            spellList.add(OtherSpellType.JEROTESVILLAGE_BITTER_COLD_ICE_SPIKE);
        }
        //技能列表-苦寒冰锥
        spellList.add(OtherSpellType.JEROTESVILLAGE_BITTER_COLD_ICE_SPIKE);
        return spellList;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.BITTER_COLD_SORCERER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.BITTER_COLD_SORCERER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.BITTER_COLD_SORCERER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.BITTER_COLD_SORCERER_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.BITTER_COLD_SORCERER_SHOOT_1;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.DISAPPEAR;
    }

    public int spellLevel = 3;
    public int altarSpellLevel = 4;
    @Override
    public int getSpellLevel() {
        //队伍
        List<BitterColdAltarEntity> listAltar = this.level().getEntitiesOfClass(BitterColdAltarEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
        listAltar.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        if (!listAltar.isEmpty()) {
            return this.altarSpellLevel;
        }
        return this.spellLevel;
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "shoot1")){
            return 1;
        }
        else if (Objects.equals(animation, "shoot2")){
            return 2;
        }
        else if (Objects.equals(animation, "ceremony")){
            return 3;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.shoot1AnimationState);
        list.add(this.shoot2AnimationState);
        list.add(this.ceremonyAnimationState);
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
                        this.shoot1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shoot1AnimationState);
                        break;
                    case 2:
                        this.shoot2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shoot2AnimationState);
                        break;
                    case 3:
                        this.ceremonyAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.ceremonyAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //队伍
        List<BitterColdAltarEntity> listAltar = this.level().getEntitiesOfClass(BitterColdAltarEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
        listAltar.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));

        if (!listAltar.isEmpty()) {
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
        }
        //苦寒祭坛
        else {
            if (this.getRandom().nextInt(6 * 20) == 1 && this.getSpellOneTick() <= 0 && this.getSpellTick() <= 0 && this.isAggressive()) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(40);
                    this.setSpellOneTick(600);
                    this.setAnimTick(40);
                    this.setAnimationState("ceremony");
                }
                OtherSpellList.BitterColdAltar(getSpellLevel(), this, this.getTarget() == null ? this : this.getTarget()).spellUse();
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BITTER_COLD_SORCERER_ALTAR, this.getSoundSource(), 5.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
                }
            }
        }
        //站立动画
        if (this.isAlive()) {
            this.idleAnimationState.startIfStopped((this.tickCount));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setCombatStyle(4);
        super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        return spawnGroupData;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Frozen".equals(string) && damageSource.is(DamageTypeTags.IS_FALL)) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Frozen".equals(string) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
            amount /= 4;
        }
        if (AttackFind.isColdDamage(damageSource))
            return super.hurt(damageSource, amount / 5);
        return super.hurt(damageSource, amount);
    }

    @Override
    public void tickDeath() {
        super.tickDeath();
        if (this.deathTime == 5) {
            for (int i = 0; i < 80; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double d2 = this.random.nextGaussian() * 0.02;
                double d3 = this.random.nextGaussian() * 0.02;
                this.level().addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
            }
        }
    }
}

