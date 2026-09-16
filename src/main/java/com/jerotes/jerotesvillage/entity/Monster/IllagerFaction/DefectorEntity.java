package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.goal.FollowSlaverySupervisorGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;

public class DefectorEntity extends MeleeIllagerEntity {
    private static final EntityDataAccessor<Boolean> MUST_ENEMY = SynchedEntityData.defineId(DefectorEntity.class, EntityDataSerializers.BOOLEAN);

    public DefectorEntity(EntityType<? extends DefectorEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.1, true){
            @Override
            public boolean canUse() {
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(2, new FollowSlaverySupervisorGoal(this, 1.1f));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class){
            @Override
            public boolean canUse() {
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
        }.setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (DefectorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true){
            @Override
            public boolean canUse() {
                if (DefectorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true){
            @Override
            public boolean canUse() {
                if (DefectorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true){
            @Override
            public boolean canUse() {
                if (DefectorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                 if (!DefectorEntity.this.isMustEnemy()) {
                    return false;
                }
                return super.canUse();
            }
        });
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.DEFECTOR_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.DEFECTOR_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.DEFECTOR_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.DEFECTOR_AMBIENT;
    }
    @Override
    public boolean canJoinRaid() {
        return this.isMustEnemy() && super.canJoinRaid();
    }
    @Override
    public boolean canBeLeader() {
        return false;
    }
    @Override
    public void setCelebrating(boolean p_37900_) {
    }
    @Override
    public boolean isCelebrating() {
        return false;
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
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.SPIRVE_STUB.get());
        Raid raid = this.getCurrentRaid();
        int n2 = 1;
        if (n > raid.getNumGroups(Difficulty.NORMAL)) {
            n2 = 2;
        }
        boolean bl3 = bl2 = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            HashMap hashMap = Maps.newHashMap();
            hashMap.put(Enchantments.MOB_LOOTING, n2);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }

    public boolean isMustEnemy() {
        return this.getEntityData().get(MUST_ENEMY);
    }
    public void setMustEnemy(boolean bl) {
        this.getEntityData().set(MUST_ENEMY, bl);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);;
        compoundTag.putBoolean("IsMustEnemy", this.isMustEnemy());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setMustEnemy(compoundTag.getBoolean("IsMustEnemy"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(MUST_ENEMY, false);
    }
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (MUST_ENEMY.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        //绿宝石
        if (item == Items.EMERALD) {
            //转化背叛者
            if (this.isAlive() && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, -500)) {
                if (player.level() instanceof ServerLevel serverLevel) {
                    player.swing(InteractionHand.MAIN_HAND);
                    Villager villager = this.convertTo(EntityType.VILLAGER, false);
                    for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                        ItemStack itemstack = this.getItemBySlot(equipmentslot);
                        if (!itemstack.isEmpty()) {
                            if (EnchantmentHelper.hasBindingCurse(itemstack)) {
                                if (villager != null) {
                                    villager.getSlot(equipmentslot.getIndex() + 300).set(itemstack);
                                }
                            } else {
                                this.spawnAtLocation(itemstack);
                            }
                        }
                    }
                    if (villager != null) {
                        villager.setDeltaMovement(0, 0, 0);
                        VillagerData villagerData = new VillagerData(VillagerType.PLAINS, JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get(), 2);
                        villager.setVillagerData(villagerData);
                        for (int i = 0; i < 60; ++i) {
                            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, villager.getRandomX(1.5), villager.getRandomY(), villager.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        if (!villager.isSilent()) {
                            serverLevel.playSound(null, villager.getX(), villager.getY(), villager.getZ(), JerotesSoundEvents.TELEPORT, villager.getSoundSource(), 5.0f, 1.0F);
                        }
                        net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, villager);
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                        RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 20);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //必须迎战
        List<Mob> listTeam = DefectorEntity.this.level().getEntitiesOfClass(Mob.class, DefectorEntity.this.getBoundingBox().inflate(16.0, 16.0, 16.0));
        listTeam.removeIf(entity -> !EntityFactionFind.isRaider(entity) || entity instanceof DefectorEntity);
        listTeam.removeIf(entity -> entity.isNoAi() || entity.hasEffect(JerotesMobEffects.PARALYZED.get()) || entity.hasEffect(JerotesMobEffects.ANESTHETIZED_HOLD.get()) || entity.hasEffect(JerotesMobEffects.ENSLAVEMENT.get()));
        if (!listTeam.isEmpty() || OtherMainConfig.DefectorMustAttack) {
            if (!this.level().isClientSide) {
                this.setMustEnemy(true);
            }
        }
        else {
            if (!this.level().isClientSide) {
                this.setMustEnemy(false);
            }
        }
        if (this.isMustEnemy()) {
            this.addParticlesAroundSelf(ParticleTypes.SPLASH);
        }
        //逃离袭击
        if (this.level() instanceof ServerLevel serverLevel && serverLevel.isRaided(this.getOnPos()) && !this.isMustEnemy()) {
            this.setTarget(null);
            this.setLastHurtByMob(null);
            Vec3 vec = LandRandomPos.getPosAway(this, 64, 48, this.position());
            if (vec != null) {
                this.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.3d);
            }
        }
    }

    public void addParticlesAroundSelf(ParticleOptions particleOptions) {
        double d = this.random.nextGaussian() * 0.02;
        double d2 = this.random.nextGaussian() * 0.02;
        double d3 = this.random.nextGaussian() * 0.02;
        this.level().addParticle(particleOptions, this.getRandomX(1.0), this.getEyeY(), this.getRandomZ(1.0), d, d2, d3);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.getRandom().nextFloat() > 0.75f) {
            if (this.level() instanceof ServerLevel serverLevel) {
                double d = this.getRandom().nextGaussian() * 0.02;
                double d2 = this.getRandom().nextGaussian() * 0.02;
                double d3 = this.getRandom().nextGaussian() * 0.02;
                for (int i = 0; i < 32; ++i) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH,  this.getRandomX(1.0), this.getEyeY(), this.getRandomZ(1.0), 0,  d, d2, d3, 1.0);
                }
            }
            return false;
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.SPIRVE_STUB.get());
    }
}

