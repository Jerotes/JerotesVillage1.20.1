package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.collect.ImmutableList;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.entity.Ai.PurpleSandCamelAi;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.serialization.Dynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.camel.CamelAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.Team;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;

public class PurpleSandCamelEntity extends Camel implements NeutralMob, ControlVehicleEntity, ChangePoseAbout, HasHomePosEntity, PlayerRideableJumping, Saddleable, OwnableEntity, JerotesEntity, FactionEntity, TameMobEntity {
    protected Brain<?> makeBrain(Dynamic<?> p_311857_) {
        return this.brainProvider().makeBrain(p_311857_);
    }
    protected Brain.Provider<Camel> brainProvider() {
        return PurpleSandCamelAi.brainProvider();
    }
    public AnimationState attack1AnimationState = new AnimationState();
    public AnimationState attack2AnimationState = new AnimationState();
    public AnimationState tameSitAnimationState = new AnimationState();
    public AnimationState toSitAnimationState = new AnimationState();
    public AnimationState stopSitAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
    protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(PurpleSandCamelEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final Ingredient TEMPTATION_ITEM = Ingredient.of(Items.CACTUS, JerotesVillageItems.PINK_CACTUS.get());

    public PurpleSandCamelEntity(EntityType<? extends PurpleSandCamelEntity> entityType, Level level) {
        super(entityType, level);
        this.reassessTameGoals();
        setPersistenceRequired();
    }

    public boolean isFood(ItemStack itemStack) {
        return TEMPTATION_ITEM.test(itemStack) || super.isFood(itemStack);
    }

    @Override
    protected void randomizeAttributes(RandomSource randomSource) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
        builder = builder.add(Attributes.MAX_HEALTH, 55);
        builder = builder.add(Attributes.JUMP_STRENGTH, 0.45);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
        builder = builder.add(Attributes.FOLLOW_RANGE, 20);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
        return builder;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.2));
        this.goalSelector.addGoal(2, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
        this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
        this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
        this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, TEMPTATION_ITEM, false));
        this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        if (this.canPerformRearing()) {
            this.goalSelector.addGoal(9, new RandomStandGoal(this));
        }
        this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
        this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<PurpleSandCamelEntity>(this, false));
    }
    public boolean OwnerCanOrderAttack() {
        return !this.isBaby();
    }

    @Override
    public boolean isPushable() {
        return !this.isVehicle();
    }
    @Nullable
    @Override
    public PurpleSandCamelEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        UUID uUID;
        PurpleSandCamelEntity purpleSandCamelEntity = JerotesVillageEntityType.PURPLE_SAND_CAMEL.get().create(serverLevel);
        if (purpleSandCamelEntity != null && (uUID = this.getOwnerUUID()) != null) {
            purpleSandCamelEntity.setOwnerUUID(uUID);
            purpleSandCamelEntity.setTame(true);
        }
        return purpleSandCamelEntity;
    }
    @Override
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        }
        if (animal.getClass() != this.getClass()) {
            return false;
        }
        return this.isInLove() && animal.isInLove();
    }
    public float getScale() {
        return this.isBaby() ? 0.6F : 1.2F;
    }
    @Override
    public boolean canJump() {
        return this.isSaddled();
    }
    public void tame(Player player) {
        this.setTame(true);
        this.setOwnerUUID(player.getUUID());
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }
    }
    protected float getPassengersRidingOffsetY(EntityDimensions entityDimensions, float f) {
        return entityDimensions.height;
    }
    protected void reassessTameGoals() {
    }
    public boolean isCamelSitting() {
        if (this.isTame()) {
            return false;
        }
        return super.isCamelSitting();
    }
    public boolean isCamelVisuallySitting() {
        if (this.isTame()) {
            return false;
        }
        return super.isCamelVisuallySitting();
    }
    public boolean isTamed() {
        return this.getFlag(2);
    }
    public void openCustomInventoryScreen(Player player) {
        if (!(this.level()).isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTamed()) {
            player.openHorseInventory(this, this.inventory);
        }
    }
    protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
        int n = Math.max(this.getPassengers().indexOf(entity), 0);
        boolean bl = n == 0;
        float f2 = 0.5f;
        float f3 = (float)(this.isRemoved() ? 0.009999999776482582 : this.getBodyAnchorAnimationYOffset(bl, 0.0f, entityDimensions, f));
        if (this.getPassengers().size() > 1) {
            if (!bl) {
                f2 = -0.5f;
            }
            if (entity instanceof Animal) {
                f2 += 0.2f;
            }
        }
        return new Vector3f(0.0f, f3, f2 * f);
    }
    private double getBodyAnchorAnimationYOffset(boolean p_249228_, float p_251763_, EntityDimensions p_301064_, float p_299749_) {
        double d0 = (double)(p_301064_.height - 0.375F * p_299749_);
        float f = p_299749_ * 1.43F;
        float f1 = f - p_299749_ * 0.2F;
        float f2 = f - f1;
        boolean flag = this.isInPoseTransition();
        boolean flag1 = this.isCamelSitting();
        if (flag) {
            int i = flag1 ? 40 : 52;
            int j;
            float f3;
            if (flag1) {
                j = 28;
                f3 = p_249228_ ? 0.5F : 0.1F;
            } else {
                j = p_249228_ ? 24 : 32;
                f3 = p_249228_ ? 0.6F : 0.35F;
            }

            float f4 = Mth.clamp((float)this.getPoseTime() + p_251763_, 0.0F, (float)i);
            boolean flag2 = f4 < (float)j;
            float f5 = flag2 ? f4 / (float)j : (f4 - (float)j) / (float)(i - j);
            float f6 = f - f3 * f1;
            d0 += flag1 ? (double)Mth.lerp(f5, flag2 ? f : f6, flag2 ? f6 : f2) : (double)Mth.lerp(f5, flag2 ? f2 - f : f2 - f6, flag2 ? f2 - f6 : 0.0F);
        }

        if (flag1 && !flag) {
            d0 += (double)f2;
        }

        return d0;
    }
    @Override
    public boolean isManuallyControlCombatJerotes() {
        return this.getEntityData().get(MANUALLY_CONTROL_COMBAT);
    }
    public boolean isTrueManuallyControlCombatJerotes() {
        return this.getControllingPassenger() instanceof Player player && canBeControlJerotes(player) && isManuallyControlCombatJerotes();
    }
    @Override
    public void setManuallyControlCombatJerotes(boolean bl) {
        this.getEntityData().set(MANUALLY_CONTROL_COMBAT, bl);
        if (this.getControllingPassenger() instanceof Player player) {
            if (bl) {
                player.displayClientMessage(Component.translatable("message.jerotes.change_control_combat_type_0"), true);
            }
            else {
                player.displayClientMessage(Component.translatable("message.jerotes.change_control_combat_type_1"), true);
            }
        }
    }
    @Override
    public float getManuallyControlCombatCameraChangeJerotes() {
        return 1.0f;
    }
    @Override
    public boolean canBeControlJerotes(Player player) {
        return this.isSaddled() && !this.isBaby() && this.getOwner() != null && (player == this.getOwner() || AttackFind.FindCanNotAttack(this.getOwner(), player));
    }
    @Override
    protected void updateControlFlags() {
        boolean flag = !(this.getControllingPassenger() instanceof Mob);
        boolean flag1 = !(this.getVehicle() instanceof Boat);
        boolean controlStopTarget = isTrueManuallyControlCombatJerotes();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, flag);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag && flag1);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
        this.goalSelector.setControlFlag(Goal.Flag.TARGET, !controlStopTarget);
    }
    @Override
    public void pressMainJerotes(Player player) {
        AttackFind.individualAttackJerotes(this, 40);
        if (!this.isSilent()) {
            this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
        }
        if (!this.level().isClientSide()) {
            this.setAttackTick(20);
            this.setAnimTick(10);
            int attackRandom = this.getRandom().nextInt(30);
            if (attackRandom < 15) {
                this.setAnimationState("attack1");
            }
            else {
                this.setAnimationState("attack2");
            }
        }
    }
    @Override
    public void pressAddJerotes(Player player) {
        AttackFind.individualAttackJerotes(this, 40);
        if (!this.isSilent()) {
            this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
        }
        if (!this.level().isClientSide()) {
            this.setAttackTick(20);
            this.setAnimTick(10);
            int attackRandom = this.getRandom().nextInt(30);
            if (attackRandom < 15) {
                this.setAnimationState("attack1");
            }
            else {
                this.setAnimationState("attack2");
            }
        }
    }
    @Override
    public void individualAttackJerotes(LivingEntity livingEntity) {
        AttackFind.attackBegin(this, livingEntity);
        AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
    }
    @Override
    public boolean canPressMainJerotes() {
        return this.getAttackTick() <= 0;
    }
    @Override
    public boolean canPressAddJerotes() {
        return this.getAttackTick() <= 0;
    }

   private int sitTick = 0;
    private boolean orderedToSit;
    @Override
    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }
    @Override
    public boolean isInSittingPose() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }
    public boolean isOwnedBy(LivingEntity livingEntity) {
        return livingEntity == this.getOwner();
    }
    @Override
    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
    }
    public void setOrderedToSit(boolean bl) {
        this.orderedToSit = bl;
    }
    public void setTame(boolean bl) {
        byte by = this.entityData.get(DATA_FLAGS_ID);
        if (bl) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(by | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(by & 0xFFFFFFFB));
        }
        this.reassessTameGoals();
        this.setFlag(2, bl);
    }
    @Override
    public void setTamed(boolean bl) {
        this.setFlag(2, bl);
        this.setTame(bl);
    }
    @Override
    //1.20.4↑//
    //public PlayerTeam getTeam() {
    //1.20.1//
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.isTame() && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }
    @Override
    public void setInSittingPose(boolean bl) {
        byte by = this.entityData.get(DATA_FLAGS_ID);
        if (bl) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(by | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(by & -2));
        }
    }
    @Override
    public void setOwnerUUID(@Nullable UUID uUID) {
        this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uUID));
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
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attack1AnimationState);
        list.add(this.attack2AnimationState);
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
    public void setHomePos(BlockPos blockPos) {
        this.entityData.set(HOME_POS, blockPos);
    }
    public BlockPos getHomePos() {
        return this.entityData.get(HOME_POS);
    }
    public boolean isGoingHome() {
        return this.entityData.get(GOING_HOME);
    }
    public void setGoingHome(boolean bl) {
        this.entityData.set(GOING_HOME, bl);
    }
    @Nullable
    @Override
    public LivingEntity getOwner() {
        if (!this.level().isClientSide){
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : getLivingEntityByUUID(this.level(), uuid);
        } else {
            int id = this.getOwnerId();
            return id <= -1 ? null : this.level().getEntity(this.getOwnerId()) instanceof LivingEntity living && living != this ? living : null;
        }
    }
    public int getOwnerId(){
        return this.entityData.get(DATA_OWNER_ID);
    }
    public void setOwnerId(int id){
        this.entityData.set(DATA_OWNER_ID, id);
    }
    public static LivingEntity getLivingEntityByUUID(Level level, UUID uuid) {
        return getLivingEntityByUUID(level.getServer(), uuid);
    }
    public static LivingEntity getLivingEntityByUUID(MinecraftServer server, UUID uuid){
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity instanceof LivingEntity livingEntity){
                    return livingEntity;
                }
            }
        }
        return null;
    }
    public boolean isWander() {
        return this.getEntityData().get(IS_WANDER);
    }
    public void setWander(boolean bl) {
        this.getEntityData().set(IS_WANDER, bl);
    }
    public void setAttackTick(int n){
        this.getEntityData().set(ATTACK_TICK, n);
    }
    public int getAttackTick(){
        return this.getEntityData().get(ATTACK_TICK);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsWander", this.isWander());
        compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
        compoundTag.putInt("HomePosX", this.getHomePos().getX());
        compoundTag.putInt("HomePosY", this.getHomePos().getY());
        compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
        compoundTag.putInt("AnimTick", this.getAnimTick());
        compoundTag.putInt("AttackTick", this.getAttackTick());
        compoundTag.putInt("SitTick", this.sitTick);
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
        compoundTag.putBoolean("Sitting", this.orderedToSit);
        compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
        this.addPersistentAngerSaveData(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        UUID uUID;
        ItemStack itemStack;
        super.readAdditionalSaveData(compoundTag);
        this.setWander(compoundTag.getBoolean("IsWander"));
        this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
        int n = compoundTag.getInt("HomePosX");
        int n2 = compoundTag.getInt("HomePosY");
        int n3 = compoundTag.getInt("HomePosZ");
        this.setHomePos(new BlockPos(n, n2, n3));
        this.setAnimTick(compoundTag.getInt("AnimTick"));
        this.setAttackTick(compoundTag.getInt("AttackTick"));
        this.sitTick = compoundTag.getInt("SitTick");
        if (compoundTag.hasUUID("Owner")) {
            uUID = compoundTag.getUUID("Owner");
        } else {
            String string = compoundTag.getString("Owner");
            uUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setOwnerUUID(uUID);
                this.setTame(true);
            }
            catch (Throwable throwable) {
                this.setTame(false);
            }
        }
        this.orderedToSit = compoundTag.getBoolean("Sitting");
        this.setInSittingPose(this.orderedToSit);
        this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
        this.updateContainerEquipment();
        this.readPersistentAngerSaveData(this.level(), compoundTag);
    }
    @Override
    public boolean canChangeDimensions() {
        return super.canChangeDimensions() && this.getChangeType() != 3;
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_WANDER, false);
        this.getEntityData().define(DATA_OWNER_ID, -1);
        this.getEntityData().define(HOME_POS, BlockPos.ZERO);
        this.getEntityData().define(GOING_HOME, false);
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
        this.getEntityData().define(ATTACK_TICK, 0);
        this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
        this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
        this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
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
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }
    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getOwner() != null && this.getOwnerId() == -1) {
            this.setOwnerId(this.getOwner().getId());
        }
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
            this.setTarget(null);
        }
        //
        if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
            this.heal(1.0f);
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
        if (!this.level().isClientSide()) {
            this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
        }
        if (!this.isInSittingPose()) {
            this.tameSitAnimationState.stop();
        }
        if (this.isInSittingPose() && sitTick <= 0){
            this.sitTick = 40;
        }
        if (!this.isInSittingPose() && sitTick > 0){
            this.stopSitAnimationState.start(this.tickCount);
            this.sitTick = 0;
            this.tameSitAnimationState.stop();
            this.toSitAnimationState.stop();
        }

        if (this.sitTick == 40){
            this.toSitAnimationState.start(this.tickCount);
        }
        if (this.sitTick == 30){
            this.toSitAnimationState.stop();
            this.tameSitAnimationState.start(this.tickCount);
        }
        if (this.sitTick > 5){
            this.sitTick -= 1;
        }
        if (this.isCamelSitting() && this.isTame()) {
           this.standUp();
        }
    }

    //1.20.4↑//
//    @Override
//    public boolean canCamelChangePose() {
//        if (this.isTame()) {
//            return false;
//        }
//        return super.canCamelChangePose();
//    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();

        if (!itemStack.isEmpty() && !this.isFood(itemStack) && !this.isTamed()) {
            this.makeMad();
        }
        if (this.isTame()) {
            InteractionResult interactionResult;
            if (!player.isShiftKeyDown() && !this.isOrderedToSit()) {
                //喂食
                if (this.isFood(itemStack)) {
                    this.eatAnimationState.start(this.tickCount);
                    if (!this.isSilent()) {
                        this.playSound(getEatingSound(), 0.15f, 1.0f);
                    }
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(4f);
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                        this.gameEvent(GameEvent.EAT, this);
                        return InteractionResult.SUCCESS;
                    }
                    //常规繁殖
                    int i = this.getAge();
                    if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                        this.setInLove(player);
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                    }
                    if (this.isBaby()) {
                        this.usePlayerItem(player, interactionHand, itemStack);
                        this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                    }
                    return InteractionResult.SUCCESS;
                }
                return super.mobInteract(player, interactionHand);
            }
            if (!this.isVehicle() && player == this.getOwner()) {
                int pose = this.getChangeType() + 1;
                if (pose > 4) {
                    pose = 1;
                }
                this.setChangeType(pose, player);
            }
            return InteractionResult.SUCCESS;
        }
        if (!this.isFood(itemStack) || this.isAngry())
            return super.mobInteract(player, interactionHand);
        this.eatAnimationState.start(this.tickCount);
        if (!this.isSilent()) {
            this.playSound(getEatingSound(), 0.15f, 1.0f);
        }
        return this.fedFood(player, itemStack);
    }
    public int getChangeType() {
        return (this.isOrderedToSit() ? 1 : 2) + (this.isWander() ? 2 : 0);
    }
    public void setChangeType(int n) {
        n = Mth.clamp(n, 1, 4);
        switch (n) {
            case 1:
                this.setOrderedToSit(true);
                if (!this.level().isClientSide()) {
                    this.setWander(false);
                }
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                break;
            case 2:
                this.setOrderedToSit(false);
                if (!this.level().isClientSide()) {
                    this.setWander(false);
                }
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                break;
            case 3:
                this.setOrderedToSit(true);
                if (!this.level().isClientSide()) {
                    this.setWander(true);
                    this.setHomePos(this.blockPosition());
                }
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                break;
            case 4:
                this.setOrderedToSit(false);
                if (!this.level().isClientSide()) {
                    this.setWander(true);
                }
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                break;
        }
    }
    public void setChangeType(int n, Player player) {
        this.setChangeType(n);
        if (!this.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.translatable("talk.jerotes.pose_" + n, this.getDisplayName()).withStyle(ChatFormatting.WHITE));
        }
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        float f = player.isSprinting() && this.getJumpCooldown() == 0 ? 0.1f : 0.0f;
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) + f;
    }

    @Override
    protected void updateContainerEquipment() {
        if (this.level().isClientSide()) {
            return;
        }
        super.updateContainerEquipment();
    }
    @Override
    protected void spawnTamingParticles(boolean bl) {
        SimpleParticleType simpleParticleType = ParticleTypes.HEART;
        if (!bl) {
            simpleParticleType = ParticleTypes.SMOKE;
        }
        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            double d3 = this.random.nextGaussian() * 0.02;
            ((Level)this.level()).addParticle(simpleParticleType, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, d2, d3);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.isTrueManuallyControlCombatJerotes()) {
            return false;
        }
        if (this.getAttackTick() > 0) {
            return false;
        }
        if (!this.level().isClientSide()) {
            this.setAttackTick(20);
            this.setAnimTick(10);
            int attackRandom = this.getRandom().nextInt(30);
            if (attackRandom < 15) {
                this.setAnimationState("attack1");
            }
            else {
                this.setAnimationState("attack2");
            }
        }
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
            }
        }
        return bl;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        boolean bl = super.hurt(damageSource, amount);
        if (bl) {
            //取消坐下
            if (!this.level().isClientSide()) {
                if (this.getChangeType() == 1) {
                    if (this.getOwner() instanceof Player player) {
                        this.setChangeType(2, player);
                    }
                    else {
                        this.setChangeType(2);
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public void handleEntityEvent(byte by) {
        if (by == 7) {
            this.spawnTamingParticles(true);
        } else if (by == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(by);
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (entity == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        if (this.isOwnedBy(livingEntity)) {
            return false;
        }
        return super.canAttack(livingEntity);
    }

    @Override
    public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setHomePos(this.blockPosition());
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
            this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }
        super.die(damageSource);
    }

    @Nullable
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int n) {
        this.remainingPersistentAngerTime = n;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }
}

