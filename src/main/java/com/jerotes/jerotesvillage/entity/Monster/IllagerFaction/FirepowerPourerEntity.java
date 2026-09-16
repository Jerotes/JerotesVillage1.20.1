package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.OminouseBannerRaidForceEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Part.FirepowerPourerChainsawPart;
import com.jerotes.jerotesvillage.entity.Part.FirepowerPourerGunPart;
import com.jerotes.jerotesvillage.entity.Part.FirepowerPourerPart;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.VirtualCaveCrystalArrowEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FirepowerPourerEntity extends Raider implements EliteEntity, OminouseBannerRaidForceEntity, JerotesEntity, FactionEntity, BreakShieldEntity {
    private static final UUID CHAINSAW_ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("0a8dd507-ff80-455d-991c-346cd5d360c7");
    private static final EntityDataAccessor<Float> CHAINSAW_HEALTH = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> GUN_HEALTH = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COOL_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COOLING_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHOOT_USE_TICK = SynchedEntityData.defineId(FirepowerPourerEntity.class, EntityDataSerializers.INT);
    private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10, false);
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attack1AnimationState = new AnimationState();
    public AnimationState attack2AnimationState = new AnimationState();
    public AnimationState attack3AnimationState = new AnimationState();
    public AnimationState attack4AnimationState = new AnimationState();
    public AnimationState attack5AnimationState = new AnimationState();
    public AnimationState break1AnimationState = new AnimationState();
    public AnimationState break2AnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();
    public AnimationState coolAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    public final FirepowerPourerChainsawPart chainsaw;
    public final FirepowerPourerGunPart gun;
    public final FirepowerPourerPart[] allParts;

    public FirepowerPourerEntity(EntityType<? extends FirepowerPourerEntity> entityType, Level level) {
        super(entityType, level);
        setMaxUpStep(1.6f);
        this.xpReward = 80;
        this.setCanPickUpLoot(false);
        this.moveControl = new GiantMoveControl(this);
        this.chainsawAbout();
        chainsaw = new FirepowerPourerChainsawPart(this, "chainsaw", 1.2f, 1.2f);
        gun = new FirepowerPourerGunPart(this, "gun", 1.0f, 1.0f);
        allParts = new FirepowerPourerPart[]{chainsaw, gun};
    }

    @Override
    public String getFirstFactionTypeName() {
        return "ominous_banner_raid_force";
    }
    @Override
    public List<String> getFactionTypeUntilTame() {
        List<String> list = new ArrayList<>();
        list.add(getFirstFactionTypeName());
        list.add("raider");
        list.add("illager");
        return list;
    }
    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (allParts != null) {
            for (FirepowerPourerPart part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
        builder = builder.add(Attributes.MAX_HEALTH, 220);
        builder = builder.add(Attributes.ARMOR, 16);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.2);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0f));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, AbstractIllager.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
        this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (FirepowerPourerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
        return JerotesVillageSoundEvents.FIREPOWER_POURER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.FIREPOWER_POURER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.FIREPOWER_POURER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.FIREPOWER_POURER_CHEER;
    }
    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }
    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }
    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_WALK, 0.5f, 1);
    }
    @Override
    protected float getSoundVolume() {
        return 2.0f;
    }
    @Override
    public boolean canJoinRaid() {
        return true;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
    }
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get())
            return false;
        return super.canDrownInFluidType(type);
    }
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if (this.getCoolingTick() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0d, 1d, 0d));
        }
        else if (this.getShootUseTick() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
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
        return aabb1.inflate(1.25d, 1.25d, 1.25d);
    }
    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
        return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
    }
    public void chainsawAbout() {
        if (!(this.level()).isClientSide) {
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(CHAINSAW_ATTACK_DAMAGE_MODIFIER_UUID);
            if (this.getChainsawHealth() > 0) {
                Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(CHAINSAW_ATTACK_DAMAGE_MODIFIER_UUID, "Chainsaw attack damage", 8, AttributeModifier.Operation.ADDITION));
            }
        }
    }
    @Override
    public boolean canDisableShield() {
        return this.getChainsawHealth() > 0 || super.canDisableShield();
    }
    @Override
    public int getShieldBreakStrength() {
        if (this.getChainsawHealth() > 0) {
            return 100;
        }
        return 0;
    }
    @Override
    protected void blockedByShield(LivingEntity livingEntity) {
        if (this.getChainsawHealth() > 0) {
            if (livingEntity instanceof Player player) {
                player.disableShield(true);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 3.25f;
    }

    public float getChainsawHealth() {
        return this.entityData.get(CHAINSAW_HEALTH);
    }
    public void setChainsawHealth(float f) {
        this.entityData.set(CHAINSAW_HEALTH, Mth.clamp(f, 0.0F, 110f));
    }
    public float getGunHealth() {
        return this.entityData.get(GUN_HEALTH);
    }
    public void setGunHealth(float f) {
        this.entityData.set(GUN_HEALTH, Mth.clamp(f, 0.0F, 110f));
    }
    public void setAttackTick(int n){
        this.getEntityData().set(ATTACK_TICK, n);
    }
    public int getAttackTick(){
        return this.getEntityData().get(ATTACK_TICK);
    }
    public int getAttackUse() {
        return this.getEntityData().get(ATTACK_USE);
    }
    public void setAttackUse(int n) {
        this.getEntityData().set(ATTACK_USE, n);
    }
    public void setShootTick(int n){
        this.getEntityData().set(SHOOT_TICK, n);
    }
    public int getShootTick(){
        return this.getEntityData().get(SHOOT_TICK);
    }
    public void setShootUseTick(int n){
        this.getEntityData().set(SHOOT_USE_TICK, n);
    }
    public int getShootUseTick(){
        return this.getEntityData().get(SHOOT_USE_TICK);
    }
    public void setCoolTick(int n){
        this.getEntityData().set(COOL_TICK, n);
    }
    public int getCoolTick(){
        return this.getEntityData().get(COOL_TICK);
    }
    public void setCoolingTick(int n){
        this.getEntityData().set(COOLING_TICK, n);
    }
    public int getCoolingTick(){
        return this.getEntityData().get(COOLING_TICK);
    }
    //动画
    public void setAnimTick(int n){
        this.getEntityData().set(ANIM_TICK, n);
    }
    public int getAnimTick(){
        return this.getEntityData().get(ANIM_TICK);
    }
    public void setAnimationState(String input) {
        this.setAnimationState(this.getAnimationState(input));
    }
    public void setAnimationState(int id) {
        this.entityData.set(ANIM_STATE, id);
    }
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "attack1")){
            return 1;
        }
        else if (Objects.equals(animation, "attack2")){
            return 2;
        }
        else if (Objects.equals(animation, "attack3")){
            return 3;
        }
        else if (Objects.equals(animation, "attack4")){
            return 4;
        }
        else if (Objects.equals(animation, "attack5")){
            return 5;
        }
        else if (Objects.equals(animation, "break1")){
            return 6;
        }
        else if (Objects.equals(animation, "break2")){
            return 7;
        }
        else if (Objects.equals(animation, "shoot")){
            return 8;
        }
        else if (Objects.equals(animation, "cool")){
            return 9;
        }
        else if (Objects.equals(animation, "dead")){
            return 10;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attack1AnimationState);
        list.add(this.attack2AnimationState);
        list.add(this.attack3AnimationState);
        list.add(this.attack4AnimationState);
        list.add(this.attack5AnimationState);
        list.add(this.break1AnimationState);
        list.add(this.break2AnimationState);
        list.add(this.shootAnimationState);
        list.add(this.coolAnimationState);
        list.add(this.deadAnimationState);
        return list;
    }
    public void stopMostAnimation(AnimationState exception){
        for (AnimationState state : this.getAllAnimations()){
            if (state != exception) {
                state.stop();
            }
        }
    }
    public void stopAllAnimation(){
        for (AnimationState state : this.getAllAnimations()){
            state.stop();
        }
    }
    //
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AnimTick", this.getAnimTick());
        compoundTag.putInt("AttackTick", this.getAttackTick());
        compoundTag.putInt("AttackUse", this.getAttackUse());
        compoundTag.putInt("ShootTick", this.getShootTick());
        compoundTag.putInt("ShootUseTick", this.getShootUseTick());
        compoundTag.putInt("CoolTick", this.getCoolTick());
        compoundTag.putInt("CoolingTick", this.getCoolingTick());
        compoundTag.putFloat("ChainsawHealth", this.getChainsawHealth());
        compoundTag.putFloat("GunHealth", this.getGunHealth());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        this.setAnimTick(compoundTag.getInt("AnimTick"));
        this.setAttackTick(compoundTag.getInt("AttackTick"));
        this.setAttackUse(compoundTag.getInt("AttackUse"));
        this.setShootTick(compoundTag.getInt("ShootTick"));
        this.setShootUseTick(compoundTag.getInt("ShootUseTick"));
        this.setCoolTick(compoundTag.getInt("CoolTick"));
        this.setCoolingTick(compoundTag.getInt("CoolingTick"));
        this.setChainsawHealth(compoundTag.getFloat("ChainsawHealth"));
        this.setGunHealth(compoundTag.getFloat("GunHealth"));
        this.chainsawAbout();
        this.bossEvent.setId(this.getUUID());
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
        this.getEntityData().define(CHAINSAW_HEALTH, 110f);
        this.getEntityData().define(GUN_HEALTH, 110f);
        this.getEntityData().define(ATTACK_TICK, 0);
        this.getEntityData().define(ATTACK_USE, 0);
        this.getEntityData().define(SHOOT_TICK, 0);
        this.getEntityData().define(SHOOT_USE_TICK, 0);
        this.getEntityData().define(COOL_TICK, 0);
        this.getEntityData().define(COOLING_TICK, 0);
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
                        this.attack1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack1AnimationState);
                        break;
                    case 2:
                        this.attack2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack2AnimationState);
                        break;
                    case 3:
                        this.attack3AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack3AnimationState);
                        break;
                    case 4:
                        this.attack4AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack4AnimationState);
                        break;
                    case 5:
                        this.attack5AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attack5AnimationState);
                        break;
                    case 6:
                        this.break1AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.break1AnimationState);
                        break;
                    case 7:
                        this.break2AnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.break2AnimationState);
                        break;
                    case 8:
                        this.shootAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shootAnimationState);
                        break;
                    case 9:
                        this.coolAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.coolAnimationState);
                        break;
                    case 10:
                        this.deadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.deadAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(ItemTags.create(new ResourceLocation("forge:storage_blocks/iron")))) {
            if (this.getHealth() >= this.getMaxHealth()) {
                this.setChainsawHealth(110f);
                this.setGunHealth(110f);
                this.chainsawAbout();
                float f2 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
                this.playSound(JerotesSoundEvents.REPAIR_MOB, 1.0f, f2);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/iron")))) {
            this.repair(player, itemStack);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    public boolean repair(Player player, ItemStack itemStack) {
        this.heal(this.getMaxHealth() / 8);
        float f2 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
        this.playSound(JerotesSoundEvents.REPAIR_MOB, 1.0f, f2);
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return true;
    }

    public void handBreak1() {
        if (this.getCoolingTick() > 0 || this.getShootUseTick() > 0) {
            if (!this.level().isClientSide()) {
                this.setAnimTick(5);
                this.setAnimationState("break1");
            }
        }
    }
    public void handBreak2() {
        if (this.getCoolingTick() > 0 || this.getAttackTick() > -10) {
            if (!this.level().isClientSide()) {
                this.setAnimTick(5);
                this.setAnimationState("break2");
            }
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }
    @Override
    public PartEntity<?>[] getParts() {
        return allParts;
    }
    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        chainsaw.setId(1 + clientboundAddEntityPacket.getId());
        gun.setId(2 + clientboundAddEntityPacket.getId());
    }
    private void tickMultipart() {
        Vec3[] avector3d = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        if (this.getChainsawHealth() > 0) {
            this.chainsaw.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0f, 1.6f), 0, this.yBodyRot + 45f).add(center));
        }
        else {
            this.chainsaw.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0f, 0), 0, 0).add(center));
        }
        if (this.getGunHealth() > 0) {
            this.gun.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0f, 1.6f), 0, this.yBodyRot - 45f).add(center));
        }
        else {
            this.gun.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0f, 0), 0, 0).add(center));
        }
        for (int l = 0; l < this.allParts.length; ++l) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }
    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tick() {
        tickMultipart();
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
            this.bossEvent.update();
            if (OtherMainConfig.EliteBossBarOnlyCombat) {
                this.bossEvent.setVisible(this.getTarget() != null);
            }
        }
        if (this.random.nextInt	(900) == 1 && this.isAlive()) {
            this.heal(3.0f);
            if (!this.level().isClientSide()) {
                if (this.getChainsawHealth() > 0 && this.getChainsawHealth() < 110f) {
                    this.setChainsawHealth(this.getChainsawHealth() + 1);
                }
                if (this.getGunHealth() > 0 && this.getGunHealth() < 110f) {
                    this.setGunHealth(this.getGunHealth() + 1);
                }
            }
        }
        //清除动画
        if (!this.level().isClientSide()) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
        }
        if (this.getAnimTick() == 0) {
            if (!this.level().isClientSide()) {
                this.setAnimationState(0);
            }
        }
        //攻击
        if (!this.level().isClientSide()) {
            this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
        }
        if (this.getAttackUse() != 5) {
            if (this.getAttackUse() == 2 && this.getChainsawHealth() > 0) {
                if (this.getAttackTick() >= 12 && this.getAttackTick() <= 17 && this.isAlive()) {
                    this.trueHurt();
                }
            }
            else {
                if (this.getAttackTick() == 14 && this.isAlive()) {
                    this.trueHurt();
                }
            }
        }
        else {
            if ((this.getAttackTick() == 8 ||
                    this.getAttackTick() == 5 ||
                    this.getAttackTick() == 2) && this.isAlive()) {
                this.trueHurt();
            }
        }
        if (this.getAttackTick() > 0) {
            if (this.getTarget() != null && this.getAttackTick() > 18) {
                this.lookAt(this.getTarget(), 360f, 360f);
            }
            this.getNavigation().stop();
        }
        //反抗愤怒者
        if (this.getTarget() == null && this.getLastHurtByMob() instanceof Raider raider && raider.getTarget() == this) {
            this.setTarget(raider);
        }
        if (this.getTarget() == null || this.getTarget() != null && !this.getTarget().isAlive() && this.getTarget() instanceof Raider) {
            this.setAggressive(false);
            this.setTarget(null);
        }
        //站立动画
        if (this.isAlive()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
        //冷却
        if (this.getCoolingTick() <= 0) {
            if (!this.isAggressive()) {
                if (this.getCoolTick() > 0) {
                    if (!this.level().isClientSide()) {
                        this.setCoolTick(this.getCoolTick() - 1);
                    }
                }
            }
            else {
                if (!this.level().isClientSide()) {
                    this.setCoolTick(this.getCoolTick() + 1);
                }
            }
        }
        else if (this.getCoolTick() < 0) {
            if (!this.level().isClientSide()) {
                this.setCoolTick(0);
            }
        }
        if (this.getCoolTick() >= 640 && this.isAlive()) {
            if (!this.level().isClientSide()) {
                this.setCoolTick(0);
                this.setCoolingTick(60);
                this.setAnimTick(30);
                this.setAnimationState("cool");
            }
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_COOL, 5.0f, 1.0f);
            }
        }
        boolean hell = "Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher  ".equals(string) || "地狱行军者".equals(string);
        if (hell && this.tickCount % 10 == 0) {
            if (!this.level().isClientSide()) {
                this.addEffect(new MobEffectInstance(JerotesMobEffects.FIRE_ABSORPTION.get(), 200, 2, false, false), this);
            }
        }
        if (this.getCoolingTick() > 0) {
            if (!this.level().isClientSide()) {
                this.setCoolingTick(this.getCoolingTick() - 1);
            }
            this.getNavigation().stop();
            if (!this.level().isClientSide()) {
                this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
                this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
                this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
            }
            if (this.level().isClientSide()) {
                for (int i = 0; i < 8; ++i) {
                    if (hell) {
                        this.level().addParticle(ParticleTypes.FLASH, this.getRandomX(0.5), this.getEyeY(), this.getRandomZ(0.5), 0.0d, 0.0d, 0.0d);
                        this.level().addParticle(ParticleTypes.LAVA, this.getRandomX(0.5), this.getEyeY(), this.getRandomZ(0.5), 0.0d, 0.0d, 0.0d);
                    } else {
                        this.level().addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getEyeY(), this.getRandomZ(0.5), 0.0d, 0.0d, 0.0d);
                        this.level().addParticle(ParticleTypes.SPLASH, this.getRandomX(0.5), this.getEyeY(), this.getRandomZ(0.5), 0.0d, 0.0d, 0.0d);
                    }
                }
                for (int i = 0; i < 3; ++i) {
                    this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getEyeY(), this.getZ(), 0.0d, 0.15d, 0.0d);
                }
            }
        }
        else if (this.getCoolingTick() < 0) {
            if (!this.level().isClientSide()) {
                this.setCoolingTick(0);
            }
        }
        //射箭
        if (this.getShootUseTick() <= 0 && this.getShootTick() < 240 && this.getGunHealth() > 0) {
            if (!this.level().isClientSide()) {
                this.setShootTick(this.getShootTick() + 1);
            }
        }
        if (this.getShootTick() >= 240 && this.isAlive() && this.getTarget() != null && this.getTarget().isAlive() && this.getTarget().distanceTo(this) > 8 && this.getGunHealth() > 0) {
             if (!this.level().isClientSide()) {
                 this.setShootTick(0);
                 this.setShootUseTick(30);
                 this.setAnimTick(15);
                 this.setAnimationState("shoot");
            }
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_WANT_SHOOT, 5.0f, 1.0f);
            }
        }
        if (this.getShootUseTick() > 0 && this.getGunHealth() > 0 && this.isAlive()) {
            if (this.getTarget() != null && this.getShootUseTick() > 30) {
                this.lookAt(this.getTarget(), 360.0f, 360.0f);
            }
            if (!this.level().isClientSide()) {
                this.setShootUseTick(this.getShootUseTick() - 1);
            }
            this.getNavigation().stop();
            if (!this.level().isClientSide) {
                this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
                this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
            }
            if ((this.getShootUseTick() == 26 || this.getShootUseTick() == 24 || this.getShootUseTick() == 20 || this.getShootUseTick() == 18) && this.gun != null) {
                for (int i = 0; i < 3; ++i) {
                    if (this.getTarget() != null) {
                        this.gun.lookAt(EntityAnchorArgument.Anchor.EYES, this.getTarget().getPosition(0.5f));
                        this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
                        this.lookAt(this.getTarget(), 360.0f, 360.0f);
                    }

                    float angleYaw = (-15 + 90.0F) * ((float) Math.PI / 180.0F);
                    float offsetY = 2f;
                    float radius = 3f;
                    float renderYawOffset = this.getYRot();

                    AbstractArrow abstractArrow = new VirtualCaveCrystalArrowEntity(JerotesVillageEntityType.VIRTUAL_CAVE_CRYSTAL_ARROW.get(), this.level());
                    abstractArrow.shootFromRotation(this, this.gun.getXRot(), (this.gun.getYRot() - 3 + i * 3), 0.0f, 3f, 0);
                    abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().add(0,0.5,0));
                    abstractArrow.setPos(this.xo + radius * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
                            this.yo + offsetY,
                            this.zo + radius * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));

                    if (hell) {
                        abstractArrow.setSecondsOnFire(100);
                    }
                    abstractArrow.setOwner(this);
                    this.level().addFreshEntity(abstractArrow);
                }
                if (!this.isSilent()) {
                    this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_SHOOT, 5.0f, 1.0f);
                }
            }
        }
        if (this.getGunHealth() <= 0) {
            if (!this.level().isClientSide()) {
                this.setShootTick(0);
                this.setShootUseTick(0);
            }
        }
        if (hell) {
            this.clearFire();
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        boolean hell = "Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string);
        if (this.getCoolingTick() > 0 || this.getShootUseTick() > 0 || this.getAttackTick() > 0) {
            return false;
        }
        if (this.getAttackTick() > -10 && !hell) {
            return false;
        }
        this.chainsawAbout();
        if (!this.level().isClientSide()) {
            this.setAttackTick(20);
            float f = 0.1f;
             if (hell) {
                f = 0.4f;
            }

             if (this.getRandom().nextFloat() > f) {
                this.setAnimTick(10);
                int attackRandom = this.getRandom().nextInt(40);
                if (hell) {
                    if (attackRandom > 35) {
                        this.setAttackUse(1);
                        this.setAnimationState("attack1");
                    } else if (attackRandom > 10) {
                        this.setAttackUse(2);
                        this.setAnimationState("attack2");
                    } else if (attackRandom > 5) {
                        this.setAttackUse(3);
                        this.setAnimationState("attack3");
                    } else {
                        this.setAttackUse(4);
                        this.setAnimationState("attack4");
                    }
                }
                else {
                    if (attackRandom > 30) {
                        this.setAttackUse(1);
                        this.setAnimationState("attack1");
                    } else if (attackRandom > 20) {
                        this.setAttackUse(2);
                        this.setAnimationState("attack2");
                    } else if (attackRandom > 10) {
                        this.setAttackUse(3);
                        this.setAnimationState("attack3");
                    } else {
                        this.setAttackUse(4);
                        this.setAnimationState("attack4");
                    }
                }
            } else {
                this.setAttackUse(5);
                this.setAnimTick(20);
                this.setAnimationState("attack5");
            }
        }
        return true;
    }

    public boolean trueHurt() {
        boolean fire = false;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
            fire = true;
        }
        if (this.getCoolingTick() > 0 || this.getShootUseTick() > 0) {
            return false;
        }
        if (!this.isSilent()) {
            if (this.getChainsawHealth() > 0) {
                this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_ATTACK_2, 1.0f, 1.0f);
            }
            else {
                this.playSound(JerotesVillageSoundEvents.FIREPOWER_POURER_ATTACK_1, 1.0f, 1.0f);
            }
        }
        float reach = 0.5f;
        if (this.getAttackUse() == 5) {
            reach = 0.75f;
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            if (!Main.canSee(hurt, this) && this.getAttackUse() != 5) continue;
            AttackFind.attackBegin(this, hurt);
            boolean bl;
            float base = 1f;
            if (fire) {
                base = 1.25f;
            }
            if (this.getChainsawHealth() > 0 && (getAttackUse() == 2 || getAttackUse() == 5)) {
                DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
                bl =  AttackFind.attackAfterCustomDamage(this, hurt, damageSource, 0.4f * base, 0.1f, false, 0f);
            }
            else {
                bl = AttackFind.attackAfter(this, hurt, base, 1.0f, false, 0f);
            }
            if (bl) {
                if (this.getChainsawHealth() > 0) {
                    if (!hurt.level().isClientSide()) {
                        hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
                    }
                }
                if (fire) {
                    hurt.setSecondsOnFire(14);
                }
            }
        }
        //横扫效果
        Main.sweepAttack(this);
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
            ItemStack hand = this.getMainHandItem();
            hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FALL)
                || damageSource.is(DamageTypes.IN_WALL)
                || AttackFind.isWaterDamage(damageSource))
            return true;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
            if (AttackFind.isFireDamage(damageSource)) {
                return true;
            }
        }
        return super.isInvulnerableTo(damageSource);
    }
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        float damage = 1f;
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
            damage *= 0.75f;
        }
        this.chainsawAbout();
        if (this.getCoolingTick() > 0) {
            damage *= 1.5f;
        }
        if (damageSource.getEntity() instanceof LivingEntity livingEntity && EntityFactionFind.isHumanoid(livingEntity) && AttackFind.isMeleeDamage(damageSource)) {
            damage *= 0.5f;
        }
        return super.hurt(damageSource, amount * damage);
    }
    public boolean hurtByGunPart(DamageSource damageSource, float f) {
        boolean bl = this.hurt(damageSource, f * 0.1f);
        if (bl && this.getGunHealth() > 0 && damageSource.getEntity() != this) {
            if (!this.level().isClientSide()) {
                if (f > this.getGunHealth()) {
                    //掉落
                    ItemStack itemStack = new ItemStack(JerotesVillageItems.FIREPOWER_POURER_CROSSBOW.get());
                    itemStack.setDamageValue(itemStack.getMaxDamage() / 2 + 1);
                    ItemEntity itemEntity = this.spawnAtLocation(itemStack, 1);
                    if (itemEntity != null) {
                        itemEntity.setPos(this.getX(), this.getEyeY() + 0.2, this.getZ());
                        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
                    }
                    //粒子
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 8; ++i) {
                            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.IRON_BARS.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 15, 0.3, 0, 0.3, 0);
                        }
                    }
                    //动画
                    this.handBreak2();
                    if (this.getChainsawHealth() <= 0) {
                        if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
                            AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:disarmed");
                        }
                    }
                }
                this.setGunHealth(this.getGunHealth() - f);
            }
        }
        return bl;
    }
    public boolean hurtByChainsawPart(DamageSource damageSource, float f) {
        boolean bl = this.hurt(damageSource, f * 0.1f);
        if (bl && this.getChainsawHealth() > 0 && damageSource.getEntity() != this) {
            if (!this.level().isClientSide) {
                if (f > this.getChainsawHealth()) {
                    this.chainsawAbout();
                    //掉落
                    ItemStack itemStack = new ItemStack(JerotesVillageItems.FIREPOWER_POURER_CHAINSAW.get());
                    itemStack.setDamageValue(itemStack.getMaxDamage() / 2 + 1);
                    ItemEntity itemEntity = this.spawnAtLocation(itemStack, 1);
                    if (itemEntity != null) {
                        itemEntity.setPos(this.getX(), this.getEyeY() + 0.2, this.getZ());
                        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
                    }
                    //粒子
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 8; ++i) {
                            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.IRON_BARS.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 15, 0.3, 0, 0.3, 0);
                        }
                    }
                    //动画
                    this.handBreak1();
                    if (this.getGunHealth() <= 0) {
                        if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
                            AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:disarmed");
                        }
                    }
                }
                this.setChainsawHealth(this.getChainsawHealth() - f);
            }
        }
        return bl;
    }

    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            if (!this.level().isClientSide()) {
                this.setAnimTick(20);
                this.setAnimationState("dead");
            }
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }
}

