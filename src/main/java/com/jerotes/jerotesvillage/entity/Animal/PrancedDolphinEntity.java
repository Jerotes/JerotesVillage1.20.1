package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.goal.PrancedDolphinJumpGoal;
import com.jerotes.jerotesvillage.goal.WaterSitWhenOrderedToGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.navigation.CanGroundWaterBoundPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class PrancedDolphinEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, Saddleable, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	private static final EntityDataAccessor<BlockPos> TREASURE_POS = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> GOT_FISH = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.INT);
	public static final Predicate<ItemEntity> ALLOWED_ITEMS = (item) -> {
		return !item.hasPickUpDelay() && item.isAlive() && item.isInWater();
	};
	static final TargetingConditions SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/fish_foods"))
	);
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("f357ce9b-19a0-44f0-b43e-0f7b1f3b763b");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("c0a9c25d-6fc4-488c-b659-221917ee2edc");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("0e96085a-b936-487d-98b1-3a0ecd567778");
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(PrancedDolphinEntity.class, EntityDataSerializers.INT);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public PrancedDolphinEntity(EntityType<? extends PrancedDolphinEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 1.0f);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.createInventory();
		this.setCanPickUpLoot(true);
    }
	@Override
	public boolean isWarBeastArmor() {
		return true;
	}
	@Override
	public boolean isGiantBeastArmor() {
		return false;
	}

	public int getInventorySize() {
		return 2;
	}
	protected void createInventory() {
		SimpleContainer simplecontainer = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());
		if (simplecontainer != null) {
			simplecontainer.removeListener(this);
			int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = simplecontainer.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.inventory.addListener(this);
		this.updateContainerEquipment();
		this.itemHandler = LazyOptional.of(() -> {
			return new InvWrapper(this.inventory);
		});
	}
	protected void updateContainerEquipment() {
		if (this.level().isClientSide()) {
			return;
		}
		if (!this.level().isClientSide) {
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
		}
		this.setArmorEquipment(this.inventory.getItem(1));
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public void containerChanged(Container p_30548_) {
		ItemStack itemStack = this.getArmor();
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
		}
		ItemStack itemStack2 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemStack2) && itemStack != itemStack2) {
			this.playSound(JerotesSoundEvents.USE_BEAST_ARMOR, 0.5f, 1.0f);
		}
	}
	public SoundEvent getSaddleSoundEvent() {
		return JerotesSoundEvents.USE_SADDLE;
	}
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return capability == ForgeCapabilities.ITEM_HANDLER && this.itemHandler != null && this.isAlive() ? this.itemHandler.cast() : super.getCapability(capability, facing);
	}
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 35);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new WaterSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(1, new JerotesTameBreathAirGoal(this));
		this.goalSelector.addGoal(1, new JerotesTameTryFindWaterGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<GemstoneMalignasaurEntity>(this, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(2, new DolphinSwimToTreasureGoal(this));
		this.goalSelector.addGoal(3, new DolphinSwimWithPlayerGoal(this, 4.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 10));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new PrancedDolphinJumpGoal(this, 10));
		this.goalSelector.addGoal(8, new PlayWithItemsGoal());
		this.goalSelector.addGoal(8, new FollowBoatGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<PrancedDolphinEntity>(this, false));
	}


	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return this.isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.DOLPHIN_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.DOLPHIN_DEATH;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	protected SoundEvent getSwimSplashSound() {
		return SoundEvents.DOLPHIN_SPLASH;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.DOLPHIN_SWIM;
	}
	protected boolean closeToNextPos() {
		BlockPos $$0 = this.getNavigation().getTargetPos();
		return $$0 != null ? $$0.closerToCenterThan(this.position(), 12.0) : false;
	}
	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}
	@Override
	public int getAmbientSoundInterval() {
		return 120;
	}
	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Nullable
	@Override
	public PrancedDolphinEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		PrancedDolphinEntity prancedDolphinEntity = JerotesVillageEntityType.PRANCED_DOLPHIN.get().create(serverLevel);
		if (prancedDolphinEntity != null && (uUID = this.getOwnerUUID()) != null) {
			prancedDolphinEntity.setOwnerUUID(uUID);
			prancedDolphinEntity.setTame(true);
		}
		return prancedDolphinEntity;
	}
	public int getMaxAirSupply() {
		return 4800;
	}
	@Override
	protected int increaseAirSupply(int p_28389_) {
		return this.getMaxAirSupply();
	}
	@Override
	public int getMaxHeadXRot() {
		return 1;
	}
	@Override
	public int getMaxHeadYRot() {
		return 1;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 0.6f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isPushable() {
		return !this.isVehicle();
	}
	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity;
		if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}
	protected void handleAirSupply(int n) {
		if (this.isAlive() && !this.isInWaterOrBubble()) {
			this.setAirSupply(n - 1);
			if (this.getAirSupply() == -20) {
				this.setAirSupply(0);
			}
		} else {
			this.setAirSupply(300);
		}
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new CanGroundWaterBoundPathNavigation(this, level);
	}
	@Override
	public boolean canTakeItem(ItemStack itemStack) {
		EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
		if (!this.getItemBySlot(equipmentSlot).isEmpty()) {
			return false;
		} else {
			return equipmentSlot == EquipmentSlot.MAINHAND && super.canTakeItem(itemStack);
		}
	}
	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
			ItemStack itemStack = itemEntity.getItem();
			if (this.canHoldItem(itemStack)) {
				this.onItemPickup(itemEntity);
				this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
				this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
				this.take(itemEntity, itemStack.getCount());
				itemEntity.discard();
			}
		}
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
		return -0.9F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//
	@Override
	public boolean isPushedByFluid() {
		return false;
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
			this.playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("attack");
		}
		if (Main.isInFluid(this)) {
			this.RushAttack();
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.isSilent()) {
			this.playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("attack");
		}
		if (Main.isInFluid(this)) {
			this.RushAttack();
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

	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(45.0);
			this.setHealth(45.0f);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
		}
	}
	public void setTreasurePos(BlockPos p_28385_) {
		this.entityData.set(TREASURE_POS, p_28385_);
	}
	public BlockPos getTreasurePos() {
		return (BlockPos)this.entityData.get(TREASURE_POS);
	}
	public boolean gotFish() {
		return (Boolean)this.entityData.get(GOT_FISH);
	}
	public void setGotFish(boolean p_28394_) {
		this.entityData.set(GOT_FISH, p_28394_);
	}
	public int getMoistnessLevel() {
		return (Integer)this.entityData.get(MOISTNESS_LEVEL);
	}
	public void setMoisntessLevel(int p_28344_) {
		this.entityData.set(MOISTNESS_LEVEL, p_28344_);
	}
	private int sitTick = 0;
	protected void setFlag(int n, boolean n2) {
		byte b0 = (Byte)this.entityData.get(DATA_ID_FLAGS);
		if (n2) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | n));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~n));
		}
	}
	protected boolean getFlag(int n) {
		return ((Byte)this.entityData.get(DATA_ID_FLAGS) & n) != 0;
	}
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	private void setArmor(ItemStack itemStack) {
		this.setItemSlot(EquipmentSlot.CHEST, itemStack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	public boolean isArmor(ItemStack itemStack) {
		return itemStack.getItem() instanceof HorseArmorItem;
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
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SitTick", this.sitTick);
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putInt("TreasurePosX", this.getTreasurePos().getX());
		compoundTag.putInt("TreasurePosY", this.getTreasurePos().getY());
		compoundTag.putInt("TreasurePosZ", this.getTreasurePos().getZ());
		compoundTag.putBoolean("GotFish", this.gotFish());
		compoundTag.putInt("Moistness", this.getMoistnessLevel());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		int x = compoundTag.getInt("TreasurePosX");
		int y = compoundTag.getInt("TreasurePosY");
		int z = compoundTag.getInt("TreasurePosZ");
		this.setTreasurePos(new BlockPos(x, y, z));
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.sitTick = compoundTag.getInt("SitTick");
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.setGotFish(compoundTag.getBoolean("GotFish"));
		this.setMoisntessLevel(compoundTag.getInt("Moistness"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.updateContainerEquipment();
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(TREASURE_POS, BlockPos.ZERO);
		this.getEntityData().define(GOT_FISH, false);
		this.getEntityData().define(MOISTNESS_LEVEL, 2400);
		this.getEntityData().define(ATTACK_TICK, 0);
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

	public void baseTick() {
		int $$0 = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply($$0);
	}
	public void tick() {
		super.tick();
		if (this.isNoAi()) {
			this.setAirSupply(this.getMaxAirSupply());
		} else {
			if (this.isInWaterRainOrBubble()) {
				this.setMoisntessLevel(2400);
			} else {
				this.setMoisntessLevel(this.getMoistnessLevel() - 1);
				if (this.onGround() && !this.isTame()) {
					this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));
					this.setOnGround(false);
					this.hasImpulse = true;
				}
			}

			if (this.level().isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03) {
				Vec3 $$0 = this.getViewVector(0.0F);
				float $$1 = Mth.cos(this.getYRot() * 0.017453292F) * 0.3F;
				float $$2 = Mth.sin(this.getYRot() * 0.017453292F) * 0.3F;
				float $$3 = 1.2F - this.random.nextFloat() * 0.7F;

				for(int $$4 = 0; $$4 < 2; ++$$4) {
					this.level().addParticle(ParticleTypes.DOLPHIN, this.getX() - $$0.x * (double)$$3 + (double)$$1, this.getY() - $$0.y, this.getZ() - $$0.z * (double)$$3 + (double)$$2, 0.0, 0.0, 0.0);
					this.level().addParticle(ParticleTypes.DOLPHIN, this.getX() - $$0.x * (double)$$3 - (double)$$1, this.getY() - $$0.y, this.getZ() - $$0.z * (double)$$3 - (double)$$2, 0.0, 0.0, 0.0);
				}
			}

		}
	}

	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
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
		if (this.isInSittingPose()) {
			this.idleAnimationState.stop();
		}
		else {
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
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
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

		if (this.getControllingPassenger() instanceof Player player) {
			if (!this.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && this.isInWater() && this.horizontalCollision) {
				for (int n = 0; n < 18; ++n) {
					if (this.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getRandomX(0.5), this.getY() + 0.5f, this.getRandomZ(0.5),
								0, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.2, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.5);
					}
				}
				this.setDeltaMovement(this.getDeltaMovement().add(0,0.25f,0));
			}
			if (!(this.xOld == this.getX() && this.zOld == this.getZ())) {
				if (this.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
					double d = Math.abs(this.getX() - this.xOld);
					double d2 = Math.abs(this.getZ() - this.zOld);
					if (d >= 0.003 || d2 >= 0.003) {
						if (this.getDeltaMovement().y < 1.0f && this.getDeltaMovement().y > -1.0f) {
							float headXRot = player.getXRot();
							this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot / 2 * -0.0035f, 0.0));
						}
					}
				}
			}
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (this.level().isClientSide) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isAngry();
			boolean bl2 = this.isTame() && !player.isShiftKeyDown() && this.isBaby();
			if (!bl2) {
				return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
			}
		}
		if (this.isTame()) {
			InteractionResult interactionResult;
			if (!player.isShiftKeyDown()) {
				if (this.isFood(itemStack)) {
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(5f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
				}
				if (!itemStack.isEmpty() && itemStack.is(ItemTags.FISHES)) {
					if (!this.level().isClientSide) {
						this.playSound(SoundEvents.DOLPHIN_EAT, 1.0F, 1.0F);
					}

					this.setGotFish(true);
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				//装配
				if (this.isOwnedBy(player)) {
					//铠甲
					if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
						this.equipArmor(player, itemStack);
						return InteractionResult.sidedSuccess(this.level().isClientSide);
					}
				}
				//骑乘
				if (!this.isFood(itemStack) && this.isSaddled() && AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), player) && !player.isSecondaryUseActive() && !isOrderedToSit()) {
					if (!this.level().isClientSide) {
						player.startRiding(this);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				this.openCustomInventoryScreen(player);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			if ((interactionResult = super.mobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
			if (!this.isVehicle() && player == this.getOwner()) {
				int pose = this.getChangeType() + 1;
				if (pose > 4) {
					pose = 1;
				}
				this.setChangeType(pose, player);
			}
			return InteractionResult.SUCCESS;
		}
		if (!this.isFood(itemStack) || this.isAngry()) {
			if (!itemStack.isEmpty() && itemStack.is(ItemTags.FISHES)) {
				if (!this.level().isClientSide) {
					this.playSound(SoundEvents.DOLPHIN_EAT, 1.0F, 1.0F);
				}
				this.setGotFish(true);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			return super.mobInteract(player, interactionHand);
		}
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(16) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			setChangeType(1, player);
			((Level)this.level()).broadcastEntityEvent(this, (byte)7);
		} else {
			((Level)this.level()).broadcastEntityEvent(this, (byte)6);
		}
		return InteractionResult.SUCCESS;
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
			this.setAnimTick(20);
			this.setAnimationState("attack");
		}
		if (Main.isInFluid(this)) {
			this.RushAttack();
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
			}
		}
		return bl;
	}

	public boolean RushAttack() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.5f;
		float f8 = f4 *= f7 / f6 * 2;
		this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
		return true;
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 10;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 38) {
			this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	private void addParticlesAroundSelf(ParticleOptions p_28338_) {
		for(int $$1 = 0; $$1 < 7; ++$$1) {
			double $$2 = this.random.nextGaussian() * 0.01;
			double $$3 = this.random.nextGaussian() * 0.01;
			double $$4 = this.random.nextGaussian() * 0.01;
			this.level().addParticle(p_28338_, this.getRandomX(1.0), this.getRandomY() + 0.2, this.getRandomZ(1.0), $$2, $$3, $$4);
		}
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}
		}
	}
	private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
		return new SlotAccess() {
			public ItemStack get() {
				return PrancedDolphinEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					PrancedDolphinEntity.this.inventory.setItem(p_149503_, p_149528_);
					PrancedDolphinEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}

	public SlotAccess getSlot(int p_149514_) {
		int i = p_149514_ - getAddNumber();
		if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
			if (i == 0) {
				return this.createEquipmentSlotAccess(i, (p_149516_) -> {
					return p_149516_.isEmpty() || p_149516_.getItem() instanceof SaddleItem;
				});
			}

			if (i == 1) {
				if (!this.canWearArmor()) {
					return SlotAccess.NULL;
				}

				return this.createEquipmentSlotAccess(i, (p_149516_) -> {
					return p_149516_.isEmpty() || this.isArmor(p_149516_);
				});
			}
		}

		int j = p_149514_ - 500 + 2;
		return j >= 2 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(p_149514_);
	}

	public boolean hasInventoryChanged(Container p_149512_) {
		return this.inventory != p_149512_;
	}

	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
	}
	public boolean canWearArmor() {
		return true;
	}
	public void setArmorEquipment(ItemStack itemStack) {
		this.setArmor(itemStack);
		if (!(this.level()).isClientSide) {
			int n;
			double n2;
			double n3;
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR_TOUGHNESS)).removeModifier(ARMOR_TOUGHNESS_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
			if (itemStack.getItem() instanceof HorseArmorItem armor && (n = armor.getProtection()) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "War Beast Armor bonus", n, AttributeModifier.Operation.ADDITION));
			}
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n2 = armor.getToughness()) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.ARMOR_TOUGHNESS)).addTransientModifier(new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_UUID, "War Beast Armor toughness bonus", n2, AttributeModifier.Operation.ADDITION));
			}
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n3 = armor.getKnockbackResistance() * 0.1) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "War Beast Armor knockback resistance bonus", n3, AttributeModifier.Operation.ADDITION));
			}
		}
	}
	@Override
	public void equipSaddle(@Nullable SoundSource soundSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
	}
	public void equipArmor(Player player, ItemStack itemStack) {
		if (this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack.copyWithCount(1));
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
	}
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
			if (player.containerMenu != player.inventoryMenu) {
				player.closeContainer();
			}

			if (player instanceof ServerPlayer serverPlayer) {
				Main.openSuchInventoryGui(serverPlayer, this);
			}
		}
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return super.getDismountLocationForPassenger(livingEntity);
		}
		int[][] arrn = DismountHelper.offsetsForDirection(direction);
		BlockPos blockPos = this.blockPosition();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Pose pose : livingEntity.getDismountPoses()) {
			AABB aABB = livingEntity.getLocalBoundsForPose(pose);
			for (int[] arrn2 : arrn) {
				mutableBlockPos.set(blockPos.getX() + arrn2[0], blockPos.getY(), blockPos.getZ() + arrn2[1]);
				double d = this.level().getBlockFloorHeight(mutableBlockPos);
				if (!DismountHelper.isBlockFloorValid(d)) continue;
				Vec3 vec3 = Vec3.upFromBottomCenterOf(mutableBlockPos, d);
				if (!DismountHelper.canDismountTo(this.level(), livingEntity, aABB.move(vec3))) continue;
				livingEntity.setPose(pose);
				return vec3;
			}
		}
		return super.getDismountLocationForPassenger(livingEntity);
	}

	@Override
	protected void tickRidden(Player player, Vec3 vec3) {
		super.tickRidden(player, vec3);
		this.setRot(player.getYRot(), player.getXRot() * 0.5f);
		this.yBodyRot = this.yHeadRot = this.getYRot();
		this.yRotO = this.yHeadRot;

		if (!this.level().isClientSide) {
			player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20, 0), this);
			player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0), this);
		}
	}

	@Override
	protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
		return new Vec3(0.0, 0.0, 1.0);
	}

	@Override
	protected float getRiddenSpeed(Player player) {
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.6f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				if (this.isControlledByLocalInstance() && this.isInWater()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					float forward = passenger.zza;
					float strafe = passenger.xxa/2;
					this.moveRelative(this.getSpeed(), new Vec3(strafe, 0, forward));
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.75));
					this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
				}
				else {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED)/2);
					float forward = passenger.zza;
					float strafe = passenger.xxa/2;
					super.travel(new Vec3(strafe, 0, forward));
				}
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			return;
		}
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), dir);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(dir);
			this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED)/2);
		}
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28332_, DifficultyInstance p_28333_, MobSpawnType p_28334_, @Nullable SpawnGroupData p_28335_, @Nullable CompoundTag p_28336_) {
		this.setAirSupply(this.getMaxAirSupply());
		this.setXRot(0.0F);
		return super.finalizeSpawn(p_28332_, p_28333_, p_28334_, p_28335_, p_28336_);
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

	private static class DolphinSwimToTreasureGoal extends Goal {
		private final PrancedDolphinEntity dolphin;
		private boolean stuck;

		DolphinSwimToTreasureGoal(PrancedDolphinEntity p_28402_) {
			this.dolphin = p_28402_;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		public boolean isInterruptable() {
			return false;
		}

		public boolean canUse() {
			if (this.dolphin.isTame()) {
				return false;
			}
			return this.dolphin.gotFish() && this.dolphin.getAirSupply() >= 100;
		}

		public boolean canContinueToUse() {
			if (this.dolphin.isTame()) {
				return false;
			}
			BlockPos $$0 = this.dolphin.getTreasurePos();
			return !BlockPos.containing((double)$$0.getX(), this.dolphin.getY(), (double)$$0.getZ()).closerToCenterThan(this.dolphin.position(), 4.0) && !this.stuck && this.dolphin.getAirSupply() >= 100;
		}

		public void start() {
			if (this.dolphin.level() instanceof ServerLevel) {
				ServerLevel $$0 = (ServerLevel)this.dolphin.level();
				this.stuck = false;
				this.dolphin.getNavigation().stop();
				BlockPos $$1 = this.dolphin.blockPosition();
				BlockPos $$2 = $$0.findNearestMapStructure(StructureTags.DOLPHIN_LOCATED, $$1, 50, false);
				if ($$2 != null) {
					this.dolphin.setTreasurePos($$2);
					$$0.broadcastEntityEvent(this.dolphin, (byte)38);
				} else {
					this.stuck = true;
				}
			}
		}

		public void stop() {
			BlockPos $$0 = this.dolphin.getTreasurePos();
			if (BlockPos.containing((double)$$0.getX(), this.dolphin.getY(), (double)$$0.getZ()).closerToCenterThan(this.dolphin.position(), 4.0) || this.stuck) {
				this.dolphin.setGotFish(false);
			}

		}

		public void tick() {
			Level $$0 = this.dolphin.level();
			if (this.dolphin.closeToNextPos() || this.dolphin.getNavigation().isDone()) {
				Vec3 $$1 = Vec3.atCenterOf(this.dolphin.getTreasurePos());
				Vec3 $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 16, 1, $$1, 0.39269909262657166);
				if ($$2 == null) {
					$$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 4, $$1, 1.5707963705062866);
				}

				if ($$2 != null) {
					BlockPos $$3 = BlockPos.containing($$2);
					if (!$$0.getFluidState($$3).is(FluidTags.WATER) || !$$0.getBlockState($$3).isPathfindable($$0, $$3, PathComputationType.WATER)) {
						$$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 5, $$1, 1.5707963705062866);
					}
				}

				if ($$2 == null) {
					this.stuck = true;
					return;
				}

				this.dolphin.getLookControl().setLookAt($$2.x, $$2.y, $$2.z, (float)(this.dolphin.getMaxHeadYRot() + 20), (float)this.dolphin.getMaxHeadXRot());
				this.dolphin.getNavigation().moveTo($$2.x, $$2.y, $$2.z, 1.3);
				if ($$0.random.nextInt(this.adjustedTickDelay(80)) == 0) {
					$$0.broadcastEntityEvent(this.dolphin, (byte)38);
				}
			}

		}
	}

	static class DolphinSwimWithPlayerGoal extends Goal {
		private final PrancedDolphinEntity dolphin;
		private final double speedModifier;
		@Nullable
		private Player player;

		DolphinSwimWithPlayerGoal(PrancedDolphinEntity p_28413_, double p_28414_) {
			this.dolphin = p_28413_;
			this.speedModifier = p_28414_;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		public boolean canUse() {
			if (this.dolphin.isTame()) {
				return false;
			}
			this.player = this.dolphin.level().getNearestPlayer(PrancedDolphinEntity.SWIM_WITH_PLAYER_TARGETING, this.dolphin);
			if (this.player == null) {
				return false;
			} else {
				return this.player.isSwimming() && this.dolphin.getTarget() != this.player;
			}
		}

		public boolean canContinueToUse() {
			if (this.dolphin.isTame()) {
				return false;
			}
			return this.player != null && this.player.isSwimming() && this.dolphin.distanceToSqr(this.player) < 256.0;
		}

		public void start() {
			this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.dolphin);
		}

		public void stop() {
			this.player = null;
			this.dolphin.getNavigation().stop();
		}

		public void tick() {
			this.dolphin.getLookControl().setLookAt(this.player, (float)(this.dolphin.getMaxHeadYRot() + 20), (float)this.dolphin.getMaxHeadXRot());
			if (this.dolphin.distanceToSqr(this.player) < 6.25) {
				this.dolphin.getNavigation().stop();
			} else {
				this.dolphin.getNavigation().moveTo(this.player, this.speedModifier);
			}

			if (this.player.isSwimming() && this.player.level().random.nextInt(6) == 0) {
				this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.dolphin);
			}

		}
	}

	private class PlayWithItemsGoal extends Goal {
		private int cooldown;

		PlayWithItemsGoal() {
		}

		public boolean canUse() {
			if (PrancedDolphinEntity.this.isOrderedToSit()) {
				return false;
			}
			if (this.cooldown > PrancedDolphinEntity.this.tickCount) {
				return false;
			} else {
				List<ItemEntity> $$0 = PrancedDolphinEntity.this.level().getEntitiesOfClass(ItemEntity.class, PrancedDolphinEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), PrancedDolphinEntity.ALLOWED_ITEMS);
				return !$$0.isEmpty() || !PrancedDolphinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
			}
		}
		public boolean canContinueToUse() {
			if (PrancedDolphinEntity.this.isOrderedToSit()) {
				return false;
			}
			return super.canContinueToUse();
		}

		public void start() {
			List<ItemEntity> $$0 = PrancedDolphinEntity.this.level().getEntitiesOfClass(ItemEntity.class, PrancedDolphinEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), PrancedDolphinEntity.ALLOWED_ITEMS);
			if (!$$0.isEmpty()) {
				PrancedDolphinEntity.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158);
				PrancedDolphinEntity.this.playSound(SoundEvents.DOLPHIN_PLAY, 1.0F, 1.0F);
			}

			this.cooldown = 0;
		}

		public void stop() {
			ItemStack $$0 = PrancedDolphinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!$$0.isEmpty()) {
				this.drop($$0);
				PrancedDolphinEntity.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				this.cooldown = PrancedDolphinEntity.this.tickCount + PrancedDolphinEntity.this.random.nextInt(100);
			}

		}

		public void tick() {
			List<ItemEntity> $$0 = PrancedDolphinEntity.this.level().getEntitiesOfClass(ItemEntity.class, PrancedDolphinEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), PrancedDolphinEntity.ALLOWED_ITEMS);
			ItemStack $$1 = PrancedDolphinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!$$1.isEmpty()) {
				this.drop($$1);
				PrancedDolphinEntity.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			} else if (!$$0.isEmpty()) {
				PrancedDolphinEntity.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158);
			}

		}

		private void drop(ItemStack p_28429_) {
			if (!p_28429_.isEmpty()) {
				double $$1 = PrancedDolphinEntity.this.getEyeY() - 0.30000001192092896;
				ItemEntity $$2 = new ItemEntity(PrancedDolphinEntity.this.level(), PrancedDolphinEntity.this.getX(), $$1, PrancedDolphinEntity.this.getZ(), p_28429_);
				$$2.setPickUpDelay(40);
				//1.20.4↑//
				//$$2.setThrower(PrancedDolphinEntity.this);
				//1.20.1//
				$$2.setThrower(PrancedDolphinEntity.this.getUUID());
				float $$3 = 0.3F;
				float $$4 = PrancedDolphinEntity.this.random.nextFloat() * 6.2831855F;
				float $$5 = 0.02F * PrancedDolphinEntity.this.random.nextFloat();
				$$2.setDeltaMovement((double)(0.3F * -Mth.sin(PrancedDolphinEntity.this.getYRot() * 0.017453292F) * Mth.cos(PrancedDolphinEntity.this.getXRot() * 0.017453292F) + Mth.cos($$4) * $$5), (double)(0.3F * Mth.sin(PrancedDolphinEntity.this.getXRot() * 0.017453292F) * 1.5F), (double)(0.3F * Mth.cos(PrancedDolphinEntity.this.getYRot() * 0.017453292F) * Mth.cos(PrancedDolphinEntity.this.getXRot() * 0.017453292F) + Mth.sin($$4) * $$5));
				PrancedDolphinEntity.this.level().addFreshEntity($$2);
			}
		}
	}
}




