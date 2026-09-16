package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.UseThrowEntity;
import com.jerotes.jerotes.entity.Interface.UseThrownJavelinEntity;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedJavelinAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedThrowAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownOminousJavalinEntity;
import com.jerotes.jerotesvillage.entity.Interface.AlwaysShowArmIllagerEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
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
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.*;

public class JavelinThrowerEntity extends MeleeIllagerEntity implements AlwaysShowArmIllagerEntity, UseThrowEntity, RangedAttackMob, UseThrownJavelinEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState itemAttackAnimationState = new AnimationState();
    public AnimationState throw1AnimationState = new AnimationState();
    public AnimationState throw2AnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> JAVELIN_COUNT = SynchedEntityData.defineId(JavelinThrowerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LIMITED_JAVELIN = SynchedEntityData.defineId(JavelinThrowerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("0f16775e-c5ad-4472-98db-7aea68887638");

    public JavelinThrowerEntity(EntityType<? extends JavelinThrowerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        this.setCanPickUpLoot(false);
    }

    @Override
    public boolean UseThrownJavelinEntityStopUse() {
        return false;
    }
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 32);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new JerotesRangedJavelinAttackGoal<JavelinThrowerEntity>(this, 0.4, 60, 12.0F, this.meleeOrRangeDistance()){
            @Override
            public boolean canUse() {
                String string = ChatFormatting.stripFormatting(JavelinThrowerEntity.this.getName().getString());
                if (JavelinThrowerEntity.this.getJavalinCount() <= 0 && JavelinThrowerEntity.this.isLimitedJavelin() && !"Armored".equals(string)) {
                    return false;
                }
                return super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                String string = ChatFormatting.stripFormatting(JavelinThrowerEntity.this.getName().getString());
                if (JavelinThrowerEntity.this.getJavalinCount() <= 0 && JavelinThrowerEntity.this.isLimitedJavelin() && !"Armored".equals(string)) {
                    return false;
                }
                return super.canContinueToUse();
            }
        });
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
                if (JavelinThrowerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        if (InventoryEntity.isThrow(handItem)) {
            useThrowShoot(this, livingEntity);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
            }
            if (!this.level().isClientSide()) {
                this.setAnimTick(10);
                if (handItem == this.getMainHandItem()) {
                    this.setAnimationState("throw1");
                }
                else {
                    this.setAnimationState("throw2");
                }
            }
        }
        if (this.getJavalinCount() > 0 || !this.isLimitedJavelin() || "Armored".equals(string)) {
            if (InventoryEntity.isRangeJavelin(handItem)) {
                useTridentShoot(this, livingEntity);
                if ("Armored".equals(string)) {
                    this.getLookControl().setLookAt(livingEntity, 360f, 360f);
                    this.lookAt(livingEntity, 360.0f, 360.0f);

                    ThrownOminousJavalinEntity spear1 = new ThrownOminousJavalinEntity(this.level(), this, new ItemStack(JerotesVillageItems.OMINOUS_JAVELIN.get()));
                    ThrownOminousJavalinEntity spear2 = new ThrownOminousJavalinEntity(this.level(), this, new ItemStack(JerotesVillageItems.OMINOUS_JAVELIN.get()));
                    double d = livingEntity.getX() - this.getX();
                    double d2 = livingEntity.getY(0.3333333333333333) - spear1.getY();
                    double d3 = livingEntity.getZ() - this.getZ();
                    double d4 = Math.sqrt(d * d + d3 * d3);
                    spear1.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 4.0F);
                    spear2.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 4.0F);
                    this.level().addFreshEntity(spear1);
                    this.level().addFreshEntity(spear2);

                }
                if (!this.level().isClientSide()) {
                    this.setThrowTick(60);
                    this.setAnimTick(10);
                    if (handItem == this.getMainHandItem()) {
                        this.setAnimationState("throw1");
                    }
                    else {
                        this.setAnimationState("throw2");
                    }
                }
                if (!"Armored".equals(string)) {
                    if (!this.level().isClientSide()) {
                        this.setJavelinCount(this.getJavalinCount() - 1);
                    }
                }
            }
        }
        if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
    }

    @Override
    public boolean canUseRangeJavelin() {
        return true;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.JAVELIN_THROWER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.JAVELIN_THROWER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.JAVELIN_THROWER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.JAVELIN_THROWER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(JerotesVillageItems.OMINOUS_JAVELIN.get());
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

    public float thisTickRenderTime = 0;
    public float lastTickRenderTime = 6;
    public float attackAnimProgress = 0.0f;
    public int javalinCountMax = 5;
    public void setJavelinCount(int n){
        this.getEntityData().set(JAVELIN_COUNT, n);
    }
    public int getJavalinCount(){
        return this.getEntityData().get(JAVELIN_COUNT);
    }
    public void setLimitedJavelin(boolean bl){
        this.getEntityData().set(LIMITED_JAVELIN, bl);
    }
    public boolean isLimitedJavelin(){
        return this.getEntityData().get(LIMITED_JAVELIN);
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else if (Objects.equals(animation, "itemAttack")){
            return 2;
        }
        else if (Objects.equals(animation, "throw1")){
            return 3;
        }
        else if (Objects.equals(animation, "throw2")){
            return 4;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
        list.add(this.itemAttackAnimationState);
        list.add(this.throw1AnimationState);
        list.add(this.throw2AnimationState);
        return list;
    }
    //
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
        if (component != null && (component.getString().equals("Armored"))) {
            Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Armored armor", 8f, AttributeModifier.Operation.ADDITION));
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("JavalinCountMax", this.javalinCountMax);
        compoundTag.putInt("JavalinCount", this.getJavalinCount());
        compoundTag.putBoolean("LimitedJavelin", this.isLimitedJavelin());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.javalinCountMax = compoundTag.getInt("JavalinCountMax");
        this.setJavelinCount(compoundTag.getInt("JavalinCount"));
        this.setLimitedJavelin(compoundTag.getBoolean("LimitedJavelin"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(JAVELIN_COUNT, 5);
        this.getEntityData().define(LIMITED_JAVELIN, true);
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
                        this.throw1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.throw1AnimationState);
                        break;
                    case 4:
                        this.throw2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.throw2AnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            lastTickRenderTime = thisTickRenderTime;
            thisTickRenderTime = 0;
        }
    }
    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getTarget() == null) {
           if (!this.level().isClientSide()) {
                this.setThrowTick(60);
            }
        }
        if (!this.isAggressive() && this.getJavalinCount() < javalinCountMax && this.getRandom().nextInt(2400) == 1 && this.deathTime == 0) {
            if (!this.level().isClientSide()) {
                this.setJavelinCount(this.getJavalinCount() + 1);
            }
        }
        //拾取
        if (this.tickCount % 10 == 0) {
            List<BaseJavelinEntity> listSelfJavelin = this.level().getEntitiesOfClass(BaseJavelinEntity.class,
                    this.getBoundingBox().inflate(1.0, 1.0, 1.0));
            listSelfJavelin.removeIf(ominousJavalin -> ominousJavalin.getOwner() != this);
            double d = Double.MAX_VALUE;
            for (BaseJavelinEntity ominousJavalin : listSelfJavelin) {
                if (ominousJavalin == null) continue;
                if ((this.distanceToSqr(ominousJavalin)) > d) continue;
                if (!ominousJavalin.dealtDamage) continue;
                ominousJavalin.discard();
                if (!this.level().isClientSide()) {
                    if (this.getJavalinCount() < javalinCountMax) {
                        this.setJavelinCount(this.getJavalinCount() + 1);
                    }
                }
                if (!this.isSilent()) {
                    this.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.0f);
                }
            }
        }

        if (this.getAttackTick() < -8 && this.getThrowTick() > 50 && this.isAlive()) {
            this.idleAnimationState.startIfStopped((this.tickCount));
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!this.level().isClientSide()) {
            this.setAttackTick(0);
            if (this.getMainHandItem().isEmpty()) {
                this.setAnimTick(5);
                this.setAnimationState("attack");
            }
            else {
                this.setAnimTick(8);
                this.setAnimationState("itemAttack");
            }
        }
        this.idleAnimationState.stop();
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.JAVELIN_THROWER_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Armored".equals(string)) {
            if (damageSource.is(DamageTypeTags.IS_FALL))
                return true;
        }
        return super.isInvulnerableTo(damageSource);
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        if (mobSpawnType != MobSpawnType.CONVERSION) {
            this.setJavelinCount(this.javalinCountMax);
        }
        return spawnGroupData2;
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesVillageItems.OMINOUS_JAVELIN.get());
    }
}

