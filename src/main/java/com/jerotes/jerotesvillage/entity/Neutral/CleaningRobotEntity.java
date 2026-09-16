package com.jerotes.jerotesvillage.entity.Neutral;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.CleaningRobotGoHomeGoal;
import com.jerotes.jerotesvillage.goal.CleaningRobotLookAtPlayerGoal;
import com.jerotes.jerotesvillage.goal.CleaningRobotRandomStrollGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class CleaningRobotEntity extends PathfinderMob implements JerotesEntity, InventoryEntity, InventoryCarrier, Container {
	private static final EntityDataAccessor<Boolean> IS_ON = SynchedEntityData.defineId(CleaningRobotEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_CAN_PICKUP = SynchedEntityData.defineId(CleaningRobotEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_ITEM = SynchedEntityData.defineId(CleaningRobotEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(CleaningRobotEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(CleaningRobotEntity.class, EntityDataSerializers.BOOLEAN);
	public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
	private LazyOptional<?> itemHandler = null;

	public CleaningRobotEntity(EntityType<? extends CleaningRobotEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0.6f);
		xpReward = 0;
		setPersistenceRequired();
		this.itemHandler = LazyOptional.of(this::createCombinedHandler);
	}
	private IItemHandler createCombinedHandler() {
		IItemHandlerModifiable handsHandler = new EntityHandsInvWrapper(this);
		IItemHandlerModifiable armorHandler = new EntityArmorInvWrapper(this);
		IItemHandlerModifiable customHandler = new InvWrapper(this.inventory);
		return new CombinedInvWrapper(
				armorHandler,
				handsHandler,
				customHandler
		);
	}
	@VisibleForDebug
	@Override
	public SimpleContainer getInventory() {
		return this.inventory;
	}

	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null &&
				this.isAlive() ? itemHandler.cast() : super.getCapability(capability, facing);
	}
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}

	protected ItemStack addToInventory(ItemStack itemStack) {
		return this.inventory.addItem(itemStack);
	}

	protected boolean canAddToInventory(ItemStack itemStack) {
		return this.inventory.canAddItem(itemStack);
	}
	@Override
	public ItemStack equipItemIfPossible(ItemStack itemStack) {
		EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
		boolean bl = this.canReplaceCurrentItem(itemStack, itemStack2);
		if (equipmentSlot.isArmor() && !bl) {
			equipmentSlot = EquipmentSlot.MAINHAND;
			itemStack2 = this.getItemBySlot(equipmentSlot);
			bl = itemStack2.isEmpty();
		}
		if (bl && this.canHoldItem(itemStack)) {
			double d = this.getEquipmentDropChance(equipmentSlot);
			if (this.canAddToInventory(itemStack2)) {
				this.addToInventory(itemStack2);
			}
			else {
				this.spawnAtLocation(itemStack2);
			}

			if (equipmentSlot.isArmor() && itemStack.getCount() > 1) {
				ItemStack itemStack3 = itemStack.copyWithCount(1);
				this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack3);
				return itemStack3;
			}
			this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack);
			return itemStack;
		}
		return ItemStack.EMPTY;
	}
	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		InventoryCarrier.pickUpItem(this, this, itemEntity);
	}
	@Override
	protected boolean canReplaceCurrentItem(ItemStack itemStack, ItemStack itemStack2) {
		return false;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		super.dropCustomDeathLoot(damageSource, n, bl);
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new CleaningRobotGoHomeGoal(this, 2.0f));
		this.goalSelector.addGoal(1, new CleaningRobotMoveToItemEntityGoal());
		this.goalSelector.addGoal(2, new CleaningRobotLookAtPlayerGoal(this, LivingEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new CleaningRobotRandomStrollGoal(this, 1));
	}

	@Override
	public int getContainerSize() {
		return inventoryCount();
	}
	@Override
	public boolean isEmpty() {
		return this.mobInventory().isEmpty();
	}
	@Override
	public ItemStack getItem(int n) {
		return this.getInventory().getItem(n);
	}
	@Override
	public ItemStack removeItem(int n, int n2) {
		return this.getInventory().removeItem(n, n2);
	}
	@Override
	public ItemStack removeItemNoUpdate(int n) {
		return this.getInventory().removeItemNoUpdate(n);
	}
	@Override
	public void setItem(int n, ItemStack itemStack) {
		this.getInventory().setItem(n, itemStack);
	}
	@Override
	public void setChanged() {
		this.getInventory().setChanged();
	}
	@Override
	public boolean stillValid(Player player) {
		return this.getInventory().stillValid(player);
	}
	@Override
	public void clearContent() {
		this.getInventory().clearContent();
	}

	public class CleaningRobotMoveToItemEntityGoal extends Goal {
		private int cooldown;
		CleaningRobotMoveToItemEntityGoal() {
		}
		@Override
		public boolean canUse() {
			if (this.cooldown > CleaningRobotEntity.this.tickCount) {
				return false;
			}
			List<ItemEntity> list = CleaningRobotEntity.this.level().getEntitiesOfClass(ItemEntity.class, CleaningRobotEntity.this.getBoundingBox()
					.inflate(32.0, 32.0, 32.0));
			list.removeIf(itemEntity -> !itemEntity.onGround() || !CleaningRobotEntity.this.getInventory().canAddItem(itemEntity.getItem()));
			return !list.isEmpty() && CleaningRobotEntity.this.isOn() && CleaningRobotEntity.this.isCanPickup() && CleaningRobotEntity.this.canPickUpLoot();
		}

		@Override
		public void start() {
			List<ItemEntity> list = CleaningRobotEntity.this.level().getEntitiesOfClass(ItemEntity.class, CleaningRobotEntity.this.getBoundingBox()
					.inflate(32.0, 32.0, 32.0));
			list.removeIf(itemEntity -> !itemEntity.onGround() || !CleaningRobotEntity.this.getInventory().canAddItem(itemEntity.getItem()));
			if (!list.isEmpty()) {
				CleaningRobotEntity.this.getNavigation().moveTo(list.get(0), 2f);
			}
			this.cooldown = 0;
		}

		@Override
		public void stop() {
			this.cooldown = CleaningRobotEntity.this.tickCount + CleaningRobotEntity.this.random.nextInt(100);
		}

		@Override
		public void tick() {
			List<ItemEntity> list = CleaningRobotEntity.this.level().getEntitiesOfClass(ItemEntity.class, CleaningRobotEntity.this.getBoundingBox()
					.inflate(32.0, 32.0, 32.0));
			list.removeIf(itemEntity -> !itemEntity.onGround() || !CleaningRobotEntity.this.getInventory().canAddItem(itemEntity.getItem()));
			if (CleaningRobotEntity.this.getNavigation().isDone()) {
				CleaningRobotEntity.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
			}
		}
	}
	

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(1.0f);
		}
		//清洁
		if (!this.level().isClientSide()) {
			if (this.isOn()) {
				if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
					boolean bl = false;
					AABB aABB = this.getBoundingBox().inflate(0.2);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
						BlockState blockState = this.level().getBlockState(blockPos);
						Block block = blockState.getBlock();
						if (!(block instanceof SnowLayerBlock
								|| block instanceof TallGrassBlock
								|| block == Blocks.TALL_GRASS
								|| block == Blocks.LARGE_FERN
								|| block == JerotesVillageBlocks.TALL_BRIGHT_GRASS.get()
								|| block == JerotesVillageBlocks.BRIGHT_GRASS.get()
								|| block instanceof WebBlock
								|| block == Blocks.MOSS_CARPET
								|| block == Blocks.SCULK_VEIN)) continue;
						bl = this.level().destroyBlock(blockPos, true, this) || bl;
					}
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
						Optional<BlockState> optional = this.evaluateNewBlockState(this.level(), blockPos = blockPos, this, this.level().getBlockState(blockPos));
						if (optional.isEmpty()) {
							continue;
						}
						this.level().setBlock(blockPos, optional.get(), 11);
						this.level().gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(this, optional.get()));
					}
				}
			}
		}
	}

	private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos blockPos, @Nullable LivingEntity player, BlockState blockState) {
		Optional<BlockState> optional2 = WeatheringCopper.getPrevious(blockState);
		if (optional2.isPresent()) {
			if (!this.isSilent()) {
				level.playSound(player, blockPos, JerotesVillageSoundEvents.CLEANING_ROBOT_WALK, SoundSource.BLOCKS, 1.0f, 1.0f);
			}
			return optional2;
		}
		return Optional.empty();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		if (this.isOn()) {
			this.playSound(JerotesVillageSoundEvents.CLEANING_ROBOT_WALK, 0.35f, 1);
		}
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return JerotesVillageSoundEvents.CLEANING_ROBOT_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.CLEANING_ROBOT_DEATH;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isLightningDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| damageSource.is(JerotesDamageTypes.PSYCHIC))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_ON, false);
		this.getEntityData().define(IS_CAN_PICKUP, false);
		this.getEntityData().define(IS_ITEM, false);
		this.getEntityData().define(HOME_POS, BlockPos.ZERO);
		this.getEntityData().define(GOING_HOME, false);
	}
	public boolean isCanPickup() {
		return this.getEntityData().get(IS_CAN_PICKUP);
	}
	public void setCanPickup(boolean bl) {
		this.getEntityData().set(IS_CAN_PICKUP, bl);
		this.setCanPickUpLoot(bl);
	}

	public boolean isOn() {
		return this.getEntityData().get(IS_ON);
	}
	public void setOn(boolean bl) {
		this.getEntityData().set(IS_ON, bl);
		float f = (float)this.getY();
		Vec3 vec3 = this.getDeltaMovement();
		if (!bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.CLEANING_ROBOT_CLOSE, 1f, 1f);
			}
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level) this.level()).addParticle(ParticleTypes.SCRAPE, this.getX() + (double) f2, f + 1.4f, this.getZ() + (double) f3, vec3.x, vec3.y, vec3.z);
			}
		}
		else {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.CLEANING_ROBOT_OPEN, 1f, 1f);
			}
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level) this.level()).addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double) f2, f + 1.4f, this.getZ() + (double) f3, vec3.x, vec3.y, vec3.z);
			}
		}
	}
	public boolean isIsItem() {
		return this.getEntityData().get(IS_ITEM);
	}
	public void setIsItem(boolean bl) {
		this.getEntityData().set(IS_ITEM, bl);
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

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsOn", this.isOn());
		compoundTag.putBoolean("IsIsItem", isIsItem());
		compoundTag.putBoolean("IsCanPickup", isCanPickup());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
		this.writeInventoryToTag(compoundTag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setOn(compoundTag.getBoolean("IsOn"));
		this.setIsItem(compoundTag.getBoolean("IsIsItem"));
		this.setCanPickup(compoundTag.getBoolean("IsCanPickup"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
		this.readInventoryFromTag(compoundTag);
	}
	@Override
	public void restoreFrom(Entity entity) {
		super.restoreFrom(entity);
		if (entity instanceof CleaningRobotEntity cleaningRobotEntity) {
			if (!this.level().isClientSide()) {
				cleaningRobotEntity.setOn(false);
			}
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		RandomSource randomSource = serverLevelAccessor.getRandom();
		this.setIsItem(false);
		float f = (float)this.getY();
		Vec3 vec3 = this.getDeltaMovement();
		if (randomSource.nextFloat() < 0.75f) {
			this.setOn(false);
			this.level().broadcastEntityEvent(this, (byte) 11);
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.CLEANING_ROBOT_CLOSE, 1f, 1f);
			}
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.SCRAPE, this.getX() + (double)f2, f + 1.4f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
		}
		else {
			this.setOn(true);
			this.level().broadcastEntityEvent(this, (byte) 12);
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.CLEANING_ROBOT_OPEN, 1f, 1f);
			}
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double)f2, f + 1.4f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
		}
		return spawnGroupData;
	}

	public void saveToCatchTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	public void loadFromCatchTag(CompoundTag compoundTag) {
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}
	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (!sourceentity.isShiftKeyDown()) {
			if (sourceentity instanceof ServerPlayer serverPlayer) {
				Main.openMobInventoryGui(serverPlayer, CleaningRobotEntity.this, false, true, false, false, false, false, false, false);
			}
			return InteractionResult.SUCCESS;
		}
		else {
			if (this.isIsItem() || itemstack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/pincers")))) {
				ItemStack itemStack = new ItemStack(JerotesVillageItems.CLEANING_ROBOT_ITEM.get());
				this.saveToCatchTag(itemStack);
				ItemEntity item_robot = new ItemEntity(this.level(), this.getX(), this.getY()+0.5, this.getZ(), itemStack);
				item_robot.setPickUpDelay(10);
				item_robot.setUnlimitedLifetime();
				this.level().addFreshEntity(item_robot);
				this.discard();
				return InteractionResult.SUCCESS;
			}
		}
		return super.mobInteract(sourceentity, hand);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		return builder;
	}

	@Override
	public SimpleContainer mobInventory() {
		return inventory;
	}
	@Override
	public int inventoryCount() {
		return 16;
	}
	@Override
	public boolean isCanChangeInventory() {
		return this.isCanPickup();
	}
	@Override
	public void setCanChangeInventory(boolean bl) {
		this.setCanPickup(bl);
	}
	@Override
	public boolean isCanChangeMeleeOrRange() {
		return this.isOn();
	}
	@Override
	public void setCanChangeMeleeOrRange(boolean bl) {
		this.setOn(bl);
	}

	public Component componentInventoryIYes() {
		return Component.translatable("message.jerotesvillage.mob_inventory_pickup_yes");
	}
	public Component componentInventoryINo() {
		return Component.translatable("message.jerotesvillage.mob_inventory_pickup_no");
	}
	public Component componentInventoryIIYes() {
		return Component.translatable("message.jerotesvillage.mob_inventory_on_yes");
	}
	public Component componentInventoryIINo() {
		return Component.translatable("message.jerotesvillage.mob_inventory_on_no");
	}
}

