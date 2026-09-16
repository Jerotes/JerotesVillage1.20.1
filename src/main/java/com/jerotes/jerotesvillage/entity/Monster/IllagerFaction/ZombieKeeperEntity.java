package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotesvillage.entity.Monster.BoundZombieVillagerEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ZombieKeeperEntity extends MeleeIllagerEntity {
    public ZombieKeeperEntity(EntityType<? extends ZombieKeeperEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
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
                if (ZombieKeeperEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.ZOMBIE_KEEPER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.ZOMBIE_KEEPER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.ZOMBIE_KEEPER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.ZOMBIE_KEEPER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.SPIRVE_HORSEWHIP.get());
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
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.ZOMBIE_KEEPER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }

   public void summonZombie() {
       if (this.level() instanceof ServerLevel serverLevel) {
           PlayerTeam teams = (PlayerTeam) this.getTeam();
           for (int i = 0; i < 3; ++i) {
               BoundZombieVillagerEntity zombieVillager = JerotesVillageEntityType.BOUND_ZOMBIE_VILLAGER.get().spawn(serverLevel, this.getOnPos().above(), MobSpawnType.EVENT);
               if (zombieVillager != null) {
                   zombieVillager.setLeashedTo(this, true);
                   if (teams != null) {
                       serverLevel.getScoreboard().addPlayerToTeam(zombieVillager.getStringUUID(), teams);
                   }
               }
           }
       }
   }
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.MOB_SUMMONED) {
            PlayerTeam teams = (PlayerTeam) this.getTeam();
            for (int i = 0; i < 3; ++i) {
                BoundZombieVillagerEntity zombieVillager = JerotesVillageEntityType.BOUND_ZOMBIE_VILLAGER.get().spawn(serverLevelAccessor.getLevel(), this.getOnPos().above(), MobSpawnType.EVENT);
                if (zombieVillager != null) {
                    zombieVillager.setLeashedTo(this, true);
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (teams != null) {
                            serverLevel.getScoreboard().addPlayerToTeam(zombieVillager.getStringUUID(), teams);
                        }
                    }
                }
            }
        }
        return spawnGroupData2;
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.SPIRVE_HORSEWHIP.get());
    }
}

