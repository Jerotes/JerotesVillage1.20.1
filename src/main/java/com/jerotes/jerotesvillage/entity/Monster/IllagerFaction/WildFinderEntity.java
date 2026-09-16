package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Interface.UseBowEntity;
import com.jerotes.jerotes.goal.JerotesRangedBowAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.entity.Animal.WildernessWolfEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class WildFinderEntity extends MeleeIllagerEntity implements RangedAttackMob, SpellUseEntity, UseBowEntity {
    private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(WildFinderEntity.class, EntityDataSerializers.INT);
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState waveAnimationState = new AnimationState();
    public AnimationState aimAttackAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();

    public WildFinderEntity(EntityType<? extends WildFinderEntity> entityType, Level level) {
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
        this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<WildFinderEntity>(this, 0.5, 20, 15.0f){
            @Override
            public boolean canUse() {
                if (WildFinderEntity.this.getSpellTick() > 580) {
                    return false;
                }
                return super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                if (WildFinderEntity.this.getSpellTick() > 580) {
                    return false;
                }
                return super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(1, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (WildFinderEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        ItemStack handItem = this.getMainHandItem();
        if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
            handItem = this.getOffhandItem();
        }
        if (InventoryEntity.isBow(handItem)) {
            if ("Leader".equals(string)) {
                useBowShoot(this, livingEntity, f, Math.max(18, getBowLevel()), 20);
            }
            useBowShoot(this, livingEntity, f, getBowLevel(), 20);
        }
        if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
    }

    @Override
    public ItemStack getProjectile(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ProjectileWeaponItem projectileWeaponItem) {
            Predicate<ItemStack> predicate = projectileWeaponItem.getSupportedHeldProjectiles();
            ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            if (predicate.test(this.getMainHandItem())) {
                itemStack2 = this.getMainHandItem();
            }
            else if (predicate.test(this.getOffhandItem())) {
                itemStack2 = this.getOffhandItem();
            }
            else {
                for (int n = 0; n < this.inventoryCount(); ++n) {
                    ItemStack finds = this.mobInventory().getItem(n);
                    if (predicate.test(finds)) {
                        itemStack2 = finds;
                        break;
                    }
                }
            }
            return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
        return ProjectileUtil.getMobArrow(this, itemStack, f);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
        return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.WILD_FINDER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.WILD_FINDER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.WILD_FINDER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.WILD_FINDER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_BOW.get());
        Raid raid = this.getCurrentRaid();
        int n2 = 1;
        if (n > raid.getNumGroups(Difficulty.NORMAL)) {
            n2 = 2;
        }
        boolean bl3 = bl2 = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            HashMap hashMap = Maps.newHashMap();
            hashMap.put(Enchantments.INFINITY_ARROWS, n2);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if (this.getSpellTick() > 580) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    public int spellLevel = 2;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setBowLevel(int n){
        this.getEntityData().set(BOW_LEVEL, n);
    }
    public int getBowLevel(){
        return this.getEntityData().get(BOW_LEVEL);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "wave")){
            return 1;
        }
        else if (Objects.equals(animation, "shoot")){
            return 2;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.waveAnimationState);
        list.add(this.shootAnimationState);
        return list;
    }
    //
    @Override
    public boolean canUseBow() {
        return true;
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("BowLevel", this.getBowLevel());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setBowLevel(compoundTag.getInt("BowLevel"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BOW_LEVEL, 15);
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
                        this.waveAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.waveAnimationState);
                        break;
                    case 2:
                        this.shootAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shootAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAggressive() && InventoryEntity.isBow(this.getMainHandItem())) {
            this.aimAttackAnimationState.start(this.tickCount);
            if (this.getSpellTick() <= 580) {
                this.idleAnimationState.stop();
            }
        }
        else {
            this.aimAttackAnimationState.stop();
        }
        if (this.isUsingItem() && this.getUseItem().getItem() instanceof BowItem && this.getThrowTick() <= 0) {
            if (!this.level().isClientSide()) {
                this.setThrowTick(30);
                this.setAnimTick(30);
                this.setAnimationState("shoot");
            }
            this.idleAnimationState.stop();
        }
        //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Leader".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }
        //技能-荒野驱使
        List<WildernessWolfEntity> listWolf = this.level().getEntitiesOfClass(WildernessWolfEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        listWolf.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        List<WildFinderEntity> ListWildFinder = this.level().getEntitiesOfClass(WildFinderEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        ListWildFinder.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));

        if (listWolf.size() < ListWildFinder.size() * 8) {
            if (this.getRandom().nextInt(20) == 1 && this.getSpellTick() <= 0 && !this.isUsingItem() && this.getTarget() != null) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(600);
                    this.setAnimTick(20);
                    this.setAnimationState("wave");
                }
                OtherSpellFind.WildernessDriven(this, spellLevel + 1, (int) ((spellLevel + 1) * 1.5), 24);
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.WILD_FINDER_WAVE, this.getSoundSource(), 5.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
                }
            }
        }
        //站立动画
        if (!(this.isAggressive() && InventoryEntity.isBow(this.getMainHandItem())) || this.getSpellTick() > 580) {
            this.idleAnimationState.startIfStopped((this.tickCount));
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Leader".equals(string)) {
            if (damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
                    || damageSource.is(DamageTypes.CACTUS)
                    || damageSource.is(DamageTypes.THORNS)
                    || damageSource.is(DamageTypeTags.IS_FALL))
                return true;
        }
        return super.isInvulnerableTo(damageSource);
    }


    @Override
    public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Leader".equals(string)) {
            if (mobEffectInstance.getEffect() == JerotesMobEffects.ANESTHETIZED.get()) {
                return false;
            }
            if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
                this.addEffect(new MobEffectInstance(
                        JerotesVillageMobEffects.ABUNDANT_COURAGE.get(),
                        mobEffectInstance.getDuration(),
                        mobEffectInstance.getAmplifier()
                        ,mobEffectInstance.isAmbient()
                        ,mobEffectInstance.isVisible()
                        ,mobEffectInstance.showIcon()));
                return false;
            }
        }
        return super.canBeAffected(mobEffectInstance);
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_BOW.get());
    }
    @Override
    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_TORCH.get());
    }
}

