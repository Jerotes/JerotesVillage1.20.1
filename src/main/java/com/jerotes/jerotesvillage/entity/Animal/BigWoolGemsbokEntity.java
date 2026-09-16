package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
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
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class BigWoolGemsbokEntity extends AbstractChestedHorse implements NeutralMob, ControlVehicleEntity, ChangePoseAbout, HasHomePosEntity, HornableEntity, PlayerRideableJumping, Saddleable, OwnableEntity, JerotesEntity, FactionEntity, TameMobEntity, Shearable, IForgeShearable {
    protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_STOP_HAIR = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> HAIR_TICK = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> HAIR_COUNT = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> IS_HAIR_CAN_MORE = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> DATA_WOOL_ID = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.INT);
    public static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (enumMap) -> {
        enumMap.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        enumMap.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        enumMap.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        enumMap.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        enumMap.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        enumMap.put(DyeColor.LIME, Blocks.LIME_WOOL);
        enumMap.put(DyeColor.PINK, Blocks.PINK_WOOL);
        enumMap.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        enumMap.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        enumMap.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        enumMap.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        enumMap.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        enumMap.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        enumMap.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        enumMap.put(DyeColor.RED, Blocks.RED_WOOL);
        enumMap.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BOOLEAN);
    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((p_29868_) -> {return p_29868_;}, BigWoolGemsbokEntity::createGemsbokColor)));
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState sitAnimationState = new AnimationState();
    public AnimationState toSitAnimationState = new AnimationState();
    public AnimationState stopSitAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
    private static final Ingredient FOOD_ITEMS = Ingredient.of(
            ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
    );
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(BigWoolGemsbokEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private static float[] createGemsbokColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        }
        else {
            float[] afloat = dyeColor.getTextureDiffuseColors();
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    public static float[] getColorArray(DyeColor dyeColor) {
        return COLORARRAY_BY_COLOR.get(dyeColor);
    }

    public BigWoolGemsbokEntity(EntityType<? extends BigWoolGemsbokEntity> entityType, Level level) {
        super(entityType, level);
        this.reassessTameGoals();
        setPersistenceRequired();
    }

    @Override
    protected void randomizeAttributes(RandomSource randomSource) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
        builder = builder.add(Attributes.MAX_HEALTH, 40);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        builder = builder.add(Attributes.JUMP_STRENGTH, 1.25);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
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
        this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        if (this.canPerformRearing()) {
            this.goalSelector.addGoal(9, new RandomStandGoal(this));
        }
        this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
        this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<BigWoolGemsbokEntity>(this, false));
    }
    public boolean OwnerCanOrderAttack() {
        return !this.isBaby();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_DEATH;
    }
    @Override
    protected SoundEvent getAngrySound() {
        return JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_ANGRY;
    }
    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_WALK, 0.15f, 1.0f);
    }
    @Nullable
    @Override
    protected SoundEvent getEatingSound() {
        return JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_EAT;
    }
    @Override
    protected void playJumpSound() {
        this.playSound(JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_JUMP, 0.4f, 1.0f);
    }
    @Override
    public boolean isFood(ItemStack itemStack) {
        return FOOD_ITEMS.test(itemStack);
    }
    @Override
    public boolean isHorn() {
        return !this.isBaby();
    }
    @Override
    protected int calculateFallDamage(float f, float f2) {
        return super.calculateFallDamage(f, f2) - 15;
    }
    @Override
    public boolean canFreeze() {
        return false;
    }
    @Override
    public boolean isFreezing() {
        return false;
    }
    @Override
    public boolean isPushable() {
        return !this.isVehicle();
    }
    //
    protected void positionRider(Entity entity, MoveFunction moveFunction) {
        Vec3 vec3 = this.getPassengerRidingPosition(entity);
        moveFunction.accept(entity, vec3.x, vec3.y + getMyRidingOffset(this), vec3.z);
    }
    public float getMyRidingOffset(Entity entity) {
        return this.ridingOffset(entity) * this.getScale();
    }
    protected float ridingOffset(Entity entity) {
        return -0.5F;
    }
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
    }
    protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
        return new Vector3f(0.0F, entityDimensions.height, 0.0F);
    }
    //
    @Nullable
    @Override
    public BigWoolGemsbokEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        UUID uUID;
        BigWoolGemsbokEntity bigWoolGemsbok = JerotesVillageEntityType.BIG_WOOL_GEMSBOK.get().create(serverLevel);
        if (bigWoolGemsbok != null && (uUID = this.getOwnerUUID()) != null) {
            bigWoolGemsbok.setOwnerUUID(uUID);
            bigWoolGemsbok.setTame(true);
            bigWoolGemsbok.setColor(this.getOffspringColor(this, (BigWoolGemsbokEntity)ageableMob));
            bigWoolGemsbok.setHairCanMore(true);
            if (this.getOwner() instanceof ServerPlayer serverPlayer) {
                AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:the_reinforced");
            }
        }
        return bigWoolGemsbok;
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
    private static CraftingContainer makeContainer(DyeColor dyeColor, DyeColor dyeColor1) {
        CraftingContainer craftingcontainer = new TransientCraftingContainer(new AbstractContainerMenu((MenuType)null, -1) {
            public ItemStack quickMoveStack(Player player, int n) {
                return ItemStack.EMPTY;
            }

            public boolean stillValid(Player player) {
                return false;
            }
        }, 2, 1);
        craftingcontainer.setItem(0, new ItemStack(DyeItem.byColor(dyeColor)));
        craftingcontainer.setItem(1, new ItemStack(DyeItem.byColor(dyeColor1)));
        return craftingcontainer;
    }
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        float height = 1.9f;
        if (this.isBaby()) {
            return height / 2;
        }
        return height;
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
            this.playSound(JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_ATTACK, 1.0f, 1.0f);
        }
        if (!this.level().isClientSide()) {
            this.setAnimTick(10);
            this.setAttackTick(20);
            this.setAnimationState("attack");
        }
    }
    @Override
    public void pressAddJerotes(Player player) {
        AttackFind.individualAttackJerotes(this, 40);
        if (!this.isSilent()) {
            this.playSound(JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_ATTACK, 1.0f, 1.0f);
        }
        if (!this.level().isClientSide()) {
            this.setAnimTick(10);
            this.setAttackTick(20);
            this.setAnimationState("attack");
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
    public void setHairTick(int n){
        this.getEntityData().set(HAIR_TICK, n);
    }
    public int getHairTick(){
        return this.getEntityData().get(HAIR_TICK);
    }
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
    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_WOOL_ID) & 15);
    }
    //1.20.1 -value()
    private DyeColor getOffspringColor(Animal animal, Animal animal1) {
        DyeColor dyecolor = ((BigWoolGemsbokEntity)animal).getColor();
        DyeColor dyecolor1 = ((BigWoolGemsbokEntity)animal1).getColor();
        CraftingContainer craftingcontainer = makeContainer(dyecolor, dyecolor1);
        return this.level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainer, this.level()).map(recipeHolder -> {
            return recipeHolder
                    //1.20.4↑//
                    //.value()
                    .assemble(craftingcontainer, this.level().registryAccess());
        }).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> {
            return this.level().random.nextBoolean() ? dyecolor : dyecolor1;
        });
    }
    public void setColor(DyeColor dyeColor) {
        byte b0 = this.entityData.get(DATA_WOOL_ID);
        this.entityData.set(DATA_WOOL_ID, (byte)(b0 & 240 | dyeColor.getId() & 15));
    }
    public boolean isStopHair() {
        return this.getEntityData().get(IS_STOP_HAIR);
    }
    public boolean isHairLess() {
        return this.getEntityData().get(HAIR_COUNT) > 0;
    }
    public boolean isHairMedium() {
        return this.getEntityData().get(HAIR_COUNT) > 1;
    }
    public boolean isHairMore() {
        return this.getEntityData().get(HAIR_COUNT) > 2;
    }
    public byte getHairCount() {
        return this.getEntityData().get(HAIR_COUNT);
    }
    public boolean isHairCanMore() {
        return this.getEntityData().get(IS_HAIR_CAN_MORE);
    }
    public void setStopHair(boolean bl) {
        this.getEntityData().set(IS_STOP_HAIR, bl);
    }
    public void setHairCount(byte by) {
        this.getEntityData().set(HAIR_COUNT, by);
    }
    public void setHairCanMore(boolean bl) {
        this.getEntityData().set(IS_HAIR_CAN_MORE, bl);
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
        if (Objects.equals(animation, "attack")){
            return 1;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.attackAnimationState);
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
        compoundTag.putInt("HairTick", this.getHairTick());
        compoundTag.putBoolean("IsStopHair", this.isStopHair());
        compoundTag.putBoolean("IsHairCanMore", this.isHairCanMore());
        compoundTag.putByte("HairCount", this.getHairCount());
        compoundTag.putByte("Color", (byte)this.getColor().getId());
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
        this.setHairTick(compoundTag.getInt("HairTick"));
        if (compoundTag.contains("IsStopHair")) {
            this.setStopHair(compoundTag.getBoolean("IsStopHair"));
        }
        if (compoundTag.contains("IsHairCanMore")) {
            this.setHairCanMore(compoundTag.getBoolean("IsHairCanMore"));
        }
        if (compoundTag.contains("HairCount")) {
            this.setHairCount(compoundTag.getByte("HairCount"));
        }
        this.setInSittingPose(this.orderedToSit);
        if (compoundTag.contains("Color")) {
            this.setColor(DyeColor.byId(compoundTag.getByte("Color")));
        }
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
        this.getEntityData().define(HAIR_TICK, 0);
        this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
        this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
        this.getEntityData().define(IS_STOP_HAIR, false);
        this.getEntityData().define(IS_HAIR_CAN_MORE, false);
        this.getEntityData().define(HAIR_COUNT, (byte)2);
        this.getEntityData().define(DATA_WOOL_ID, (byte)0);
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
                        this.attackAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.attackAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isStopHair() && !((this.isHairMedium() && !this.isHairCanMore()) || (this.isHairMore() && this.isHairCanMore()))) {
            if (!this.level().isClientSide()) {
                this.setHairTick(Math.min(3000, this.getHairTick() + 1));
            }
            if (this.getHairTick() >= 3000) {
                if (!this.level().isClientSide()) {
                    this.setHairCount((byte) (this.getHairCount() + 1));
                    this.setHairTick(0);
                }
                for (int i = 0; i < 6; ++i) {
                    double d = this.random.nextGaussian() * 0.02;
                    double d2 = this.random.nextGaussian() * 0.02;
                    double d3 = this.random.nextGaussian() * 0.02;
                    this.level().playSound(null, this, SoundEvents.WOOL_PLACE, this.getSoundSource(), 1.0f, 1.0f);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.WHITE_WOOL.defaultBlockState()), this.getRandomX(0.5), this.getRandomY() + 0.5, this.getRandomZ(0.5), d, d2, d3);
                }
            }
        }
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
        //清除冻结
        if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
        }
        if (!this.isInSittingPose()) {
            this.sitAnimationState.stop();
        }
        if (this.isInSittingPose() && sitTick <= 0){
            this.sitTick = 40;
        }
        if (!this.isInSittingPose() && sitTick > 0){
            this.stopSitAnimationState.start(this.tickCount);
            this.sitTick = 0;
            this.sitAnimationState.stop();
            this.toSitAnimationState.stop();
        }

        if (this.sitTick == 40){
            this.toSitAnimationState.start(this.tickCount);
        }
        if (this.sitTick == 30){
            this.toSitAnimationState.stop();
            this.sitAnimationState.start(this.tickCount);
        }
        if (this.sitTick > 5){
            this.sitTick -= 1;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        //剪毛
        if (item instanceof ShearsItem ||
                itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:shears"))) ||
                itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/shears")))) {
            return InteractionResult.PASS;
        }
        //染色
        if (item instanceof DyeItem dyeItem) {
            if (this.isAlive() && this.getColor() != dyeItem.getDyeColor()) {
                this.level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!this.level().isClientSide) {
                    this.setColor(dyeItem.getDyeColor());
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

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
                //涂抹骨粉
                if (item == Items.BONE_MEAL) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    this.level().playSound(null, this, SoundEvents.BONE_MEAL_USE, player.getSoundSource(), 1.0f, 1.0f);
                    this.setStopHair(true);
                    return InteractionResult.SUCCESS;
                }
                //擦除
                if ((item instanceof BrushItem) || item == Items.SPONGE) {
                    this.level().playSound(null, this, SoundEvents.BRUSH_GENERIC, player.getSoundSource(), 1.0f, 1.0f);
                    this.setStopHair(false);
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
    public void shear(SoundSource soundSource) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0f, 1.0f);
        this.setHairCount((byte)(this.getHairCount()-1));
        int n = Main.randomReach(this.getRandom(), 1, 3);
        for (int i = 0; i < n; ++i) {
            ItemEntity itemEntity = this.spawnAtLocation(ITEM_BY_DYE.get(this.getColor()), 1);
            if (itemEntity == null) continue;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }
    }
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);
        if (!world.isClientSide) {
            this.setHairCount((byte)(this.getHairCount()-1));
            int n = Main.randomReach(this.getRandom(), 1, 3);
            List<ItemStack> items = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                items.add(new ItemStack(ITEM_BY_DYE.get(this.getColor())));
            }
            return items;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && this.isHairLess() && !this.isBaby();
    }
    @Override
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        return readyForShearing();
    }

    @Override
    protected void playChestEquipsSound() {
        this.playSound(JerotesSoundEvents.USE_CHEST, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
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
            this.setAnimTick(10);
            this.setAttackTick(20);
            this.setAnimationState("attack");
        }
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.BIG_WOOL_GEMSBOK_ATTACK, 1.0f, 1.0f);
            }
        }
        return bl;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        int freezeAmount = 2;
        if (this.isHairLess())
            freezeAmount = 1;
        if (AttackFind.isColdDamage(damageSource))
            amount = amount / freezeAmount;
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
    public void handleStartJump(int n) {
        this.allowStandSliding = true;
        this.standIfPossible();
        this.playJumpSound();
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        int hairCount = 0;
        if (this.isHairMore()) {
            hairCount = 3;
        }
        else if (this.isHairMedium()) {
            hairCount = 2;
        }
        else if (this.isHairLess()) {
            hairCount = 1;
        }
        return (float) ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (1 - (hairCount * 0.2)));
    }

    @Override
    public double getCustomJump() {
        int hairCount = 0;
        if (this.isHairMore()) {
            hairCount = 3;
        }
        else if (this.isHairMedium()) {
            hairCount = 2;
        }
        else if (this.isHairLess()) {
            hairCount = 1;
        }
        return this.getAttributeValue(Attributes.JUMP_STRENGTH) * (1 - (hairCount * 0.2));
    }

    @Override
    protected void executeRidersJump(float f, Vec3 vec3) {
        double d = this.getCustomJump() * (double)f * (double)this.getBlockJumpFactor();
        double d2 = d + (double)this.getJumpBoostPower();
        Vec3 vec32 = this.getDeltaMovement();
        this.setDeltaMovement(vec32.x, d2, vec32.z);
        this.setIsJumping(true);
        this.hasImpulse = true;
        if (vec3.z > 0.0) {
            float f2 = Mth.sin(this.getYRot() * 0.017453292f);
            float f3 = Mth.cos(this.getYRot() * 0.017453292f);
            this.setDeltaMovement(this.getDeltaMovement().add(-0.4f * f2 * f, 0.0, 0.4f * f3 * f));
        }
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
        int type = 1;
        //苦寒之地
        if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BITTER_COLD)) {
            type = 2;
        }
        //明路草原
        else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)) {
            type = 3;
        }
        //和谐仙境
        else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
            type = 4;
        }
        //林地荒野
        else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
            type = 5;
        }
        //寒冷
        else if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)) {
            type = 6;
        }
        //炎热
        else if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)) {
            type = 7;
        }
        this.setColor(BigWoolGemsbokEntity.getRandomGemsbokColor(serverLevelAccessor.getRandom(), type));
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }
    public static DyeColor getRandomGemsbokColor(RandomSource randomSource, int type) {
        int i = randomSource.nextInt(100);
        if (type != 1) {
            //苦寒之地
            if (type == 2) {
                if (i < 15) {
                    return DyeColor.LIGHT_GRAY;
                } else if (i < 35) {
                    return DyeColor.BLUE;
                } else if (i < 50) {
                    return DyeColor.LIGHT_BLUE;
                }
                return DyeColor.WHITE;
            }
            //明路草原
            if (type == 3) {
                if (i < 15) {
                    return DyeColor.YELLOW;
                } else if (i < 35) {
                    return DyeColor.GREEN;
                } else if (i < 50) {
                    return DyeColor.WHITE;
                }
                return DyeColor.LIME;
            }
            //和谐仙境
            if (type == 4) {
                if (i < 15) {
                    return DyeColor.RED;
                } else if (i < 35) {
                    return DyeColor.GREEN;
                } else if (i < 50) {
                    return DyeColor.LIME;
                }
                return DyeColor.PINK;
            }
            //林地荒野
            if (type == 5) {
                if (i < 5) {
                    return DyeColor.ORANGE;
                } else if (i < 15) {
                    return DyeColor.BLACK;
                } else if (i < 30) {
                    return DyeColor.GRAY;
                }
                return DyeColor.BROWN;
            }
            //寒冷
            if (type == 6) {
                if (i < 5) {
                    return DyeColor.WHITE;
                } else if (i < 15) {
                    return DyeColor.LIGHT_BLUE;
                } else if (i < 30) {
                    return DyeColor.LIGHT_GRAY;
                }
                return DyeColor.CYAN;
            }
            //炎热
            if (type == 7) {
                if (i < 5) {
                    return DyeColor.ORANGE;
                } else if (i < 15) {
                    return DyeColor.RED;
                } else if (i < 30) {
                    return DyeColor.YELLOW;
                }
                return DyeColor.BROWN;
            }
        }
        if (i < 5) {
            return DyeColor.BLACK;
        }
        else if (i < 10) {
            return DyeColor.GRAY;
        }
        else if (i < 15) {
            return DyeColor.LIGHT_GRAY;
        }
        else if (i < 18) {
            return DyeColor.BROWN;
        }
        return randomSource.nextInt(500) == 0 ? DyeColor.PURPLE : DyeColor.WHITE;
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
            this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }
        super.die(damageSource);
    }


    protected void dropFromLootTable(DamageSource damageSource, boolean bl) {
        {
            super.dropFromLootTable(damageSource, bl);
        }
        {
            ResourceLocation resourcelocation = this.getDefaultLootTables();
            LootTable loottable = this.level().getServer().getLootData().getLootTable(resourcelocation);
            LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
            if (bl && this.lastHurtByPlayer != null) {
                lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
            }

            LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
            loottable.getRandomItems(lootparams, this.getLootTableSeed(), this::spawnAtLocation);
        }
    }

    public ResourceLocation getDefaultLootTables() {
        if (!this.isHairLess()) {
            return this.getType().getDefaultLootTable();
        }
        else {
            ResourceLocation resourcelocation;
            switch (this.getColor()) {
                case WHITE:
                    resourcelocation = BuiltInLootTables.SHEEP_WHITE;
                    break;
                case ORANGE:
                    resourcelocation = BuiltInLootTables.SHEEP_ORANGE;
                    break;
                case MAGENTA:
                    resourcelocation = BuiltInLootTables.SHEEP_MAGENTA;
                    break;
                case LIGHT_BLUE:
                    resourcelocation = BuiltInLootTables.SHEEP_LIGHT_BLUE;
                    break;
                case YELLOW:
                    resourcelocation = BuiltInLootTables.SHEEP_YELLOW;
                    break;
                case LIME:
                    resourcelocation = BuiltInLootTables.SHEEP_LIME;
                    break;
                case PINK:
                    resourcelocation = BuiltInLootTables.SHEEP_PINK;
                    break;
                case GRAY:
                    resourcelocation = BuiltInLootTables.SHEEP_GRAY;
                    break;
                case LIGHT_GRAY:
                    resourcelocation = BuiltInLootTables.SHEEP_LIGHT_GRAY;
                    break;
                case CYAN:
                    resourcelocation = BuiltInLootTables.SHEEP_CYAN;
                    break;
                case PURPLE:
                    resourcelocation = BuiltInLootTables.SHEEP_PURPLE;
                    break;
                case BLUE:
                    resourcelocation = BuiltInLootTables.SHEEP_BLUE;
                    break;
                case BROWN:
                    resourcelocation = BuiltInLootTables.SHEEP_BROWN;
                    break;
                case GREEN:
                    resourcelocation = BuiltInLootTables.SHEEP_GREEN;
                    break;
                case RED:
                    resourcelocation = BuiltInLootTables.SHEEP_RED;
                    break;
                case BLACK:
                    resourcelocation = BuiltInLootTables.SHEEP_BLACK;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }
            return resourcelocation;
        }
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

