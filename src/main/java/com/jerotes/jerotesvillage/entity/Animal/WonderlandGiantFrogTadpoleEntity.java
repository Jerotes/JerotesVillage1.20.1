package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.collect.ImmutableList;
import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotes.goal.JerotesChangeFollowOwnerGoal;
import com.jerotes.jerotes.goal.JerotesChangeSitWhenOrderedToGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Optional;
import java.util.UUID;

public class WonderlandGiantFrogTadpoleEntity extends AbstractFish implements JerotesEntity, TameMobEntity, OwnableEntity, ChangePoseAbout, HasHomePosEntity {
	private static final EntityDataAccessor<Integer> FROG_TYPE = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(WonderlandGiantFrogTadpoleEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	protected static final ImmutableList<SensorType<? extends Sensor<? super WonderlandGiantFrogTadpoleEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.FROG_TEMPTATIONS);

	public WonderlandGiantFrogTadpoleEntity(EntityType<? extends AbstractFish> entityType, Level level) {
		super(entityType, level);
		this.reassessTameGoals();
		setPersistenceRequired();
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.8);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(1, new TryFindWaterGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<GemstoneMalignasaurEntity>(this, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<Player>(this, Player.class, 8.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		//this.goalSelector.addGoal(3, new JerotesAnimalChangeTemptGoal(this, 1.0, Ingredient.of(new ItemStack(JerotesVillageItems.PINK_SLIME_BALL.get())), false));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 40));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.TADPOLE_HURT;
	}
	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.TADPOLE_DEATH;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.TADPOLE_FLOP;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WaterBoundPathNavigation(this, level);
	}
	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}

	@VisibleForTesting
	public static int ticksToBeFrog = Math.abs(-24000);
	public static float HITBOX_WIDTH = 1.0f;
	public static float HITBOX_HEIGHT = 0.8f;
	private int age;
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
	public void setOwnerUUID(@javax.annotation.Nullable UUID uuid) {
		this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uuid));
	}
	@javax.annotation.Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setTame(boolean bl) {
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7);
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2);
		}
		this.reassessTameGoals();
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		if (this.isTame()) {
			LivingEntity livingentity = this.getOwner();
			if (livingentity != null) {
				return livingentity.getTeam();
			}
		}
		return super.getTeam();
	}
	public void setOrderedToSit(boolean bl) {
		this.orderedToSit = bl;
	}
	@Override
	public void setInSittingPose(boolean bl) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
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
	public boolean isWander() {
		return this.getEntityData().get(IS_WANDER);
	}
	public void setWander(boolean bl) {
		this.getEntityData().set(IS_WANDER, bl);
	}
	@javax.annotation.Nullable
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
	public int getFrogType(){
		return this.entityData.get(FROG_TYPE);
	}
	public void setFrogType(int id){
		this.entityData.set(FROG_TYPE, id);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("FrogType", this.getFrogType());
		compoundTag.putBoolean("IsWander", this.isWander());
		compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
		compoundTag.putInt("Age", this.age);
		if (this.getOwnerUUID() != null) {
			compoundTag.putUUID("Owner", this.getOwnerUUID());
		}

		compoundTag.putBoolean("Sitting", this.orderedToSit);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFrogType(compoundTag.getInt("FrogType"));
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
		UUID uuid;
		if (compoundTag.hasUUID("Owner")) {
			uuid = compoundTag.getUUID("Owner");
		} else {
			String string = compoundTag.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
		}
		if (uuid != null) {
			try {
				this.setOwnerUUID(uuid);
				this.setTame(true);
			} catch (Throwable throwable) {
				this.setTame(false);
			}
		}
		this.orderedToSit = compoundTag.getBoolean("Sitting");
		this.setInSittingPose(this.orderedToSit);
		this.setAge(compoundTag.getInt("Age"));
	}
	@Override
	public boolean canChangeDimensions() {
		return super.canChangeDimensions() && this.getChangeType() != 3;
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FROG_TYPE, 1);
		this.getEntityData().define(IS_WANDER, false);
		this.getEntityData().define(DATA_OWNER_ID, -1);
		this.getEntityData().define(HOME_POS, BlockPos.ZERO);
		this.getEntityData().define(GOING_HOME, false);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}
		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		if (!this.level().isClientSide) {
			this.setAge(this.age + 1);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (!this.level().isClientSide) {
			this.setAge(this.age + 1);
		}
	}


	public InteractionResult superMobInteract(Player p_218703_, InteractionHand p_218704_) {
		ItemStack itemstack = p_218703_.getItemInHand(p_218704_);
		if (this.isFood(itemstack)) {
			this.feed(p_218703_, itemstack);
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return (InteractionResult)Bucketable.bucketMobPickup(p_218703_, p_218704_, this).orElse(super.mobInteract(p_218703_, p_218704_));
		}
	}
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (this.level().isClientSide) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame();
			boolean bl2 = this.isTame() && !player.isShiftKeyDown() && this.isBaby();
			if (!bl2) {
				return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
			}
		}

		if (this.isTame()) {
			InteractionResult interactionResult;
			//常规
			if (!player.isShiftKeyDown()) {
				//喂食
				if (this.isFood(itemStack)) {
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(5f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					this.feed(player, itemStack);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
					//return super.mobInteract(player, interactionHand);
				}
			}
			//坐下
			if ((interactionResult = superMobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
			if (!this.isVehicle() && player == this.getOwner()) {
				int pose = this.getChangeType() + 1;
				if (pose > 4) {
					pose = 1;
				}
				this.setChangeType(pose, player);
			}
			return InteractionResult.SUCCESS;
		}
		if (!this.isFood(itemStack))
			return superMobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(4) == 1) {
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

	protected void spawnTamingParticles(boolean bl) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!bl) {
			particleoptions = ParticleTypes.SMOKE;
		}
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	protected void spawnHealParticles() {
		ParticleOptions particleoptions = ParticleTypes.HAPPY_VILLAGER;
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	public boolean fromBucket() {
		return true;
	}

	@Override
	public void setFromBucket(boolean bl) {
	}

	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		Bucketable.saveDefaultDataToBucketTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}

	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.WONDERLAND_GIANT_FROG_TADPOLE_BUCKET.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_TADPOLE;
	}

	private boolean isFood(ItemStack itemStack) {
		return Ingredient.of(JerotesVillageItems.PINK_SLIME_BALL.get()).test(itemStack);
	}

	private void feed(Player player, ItemStack itemStack) {
		this.usePlayerItem(player, itemStack);
		this.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(this.getTicksLeftUntilAdult()));
		this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
	}

	private void usePlayerItem(Player player, ItemStack itemStack) {
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
	}

	private int getAge() {
		return this.age;
	}

	private void ageUp(int n) {
		this.setAge(this.age + n * 20);
	}

	private void setAge(int n) {
		this.age = n;
		if (this.age >= ticksToBeFrog) {
			this.ageUp();
		}
	}

	private void ageUp() {
		Object object = this.level();
		if (object instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel)object;
			object = JerotesVillageEntityType.WONDERLAND_GIANT_FROG.get().create(this.level());
			if (object != null) {
				((Entity)object).moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
				((WonderlandGiantFrogEntity)object).finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(((Entity)object).blockPosition()), MobSpawnType.CONVERSION, null, null);
				((Mob)object).setNoAi(this.isNoAi());
				if (this.hasCustomName()) {
					((Entity)object).setCustomName(this.getCustomName());
					((Entity)object).setCustomNameVisible(this.isCustomNameVisible());
				}
				((Mob)object).setPersistenceRequired();
				if (this.isTame()) {
					((WonderlandGiantFrogEntity)object).setOwnerUUID(this.getOwnerUUID());
					((WonderlandGiantFrogEntity)object).setTame(true);
					if (this.getOwner() instanceof Player player) {
						((WonderlandGiantFrogEntity)object).tame(player);
					}
					((WonderlandGiantFrogEntity)object).setOrderedToSit(true);
				}
				((WonderlandGiantFrogEntity)object).setFrogType(this.getFrogType());
				this.playSound(SoundEvents.TADPOLE_GROW_UP, 0.15f, 1.0f);
				serverLevel.addFreshEntityWithPassengers((Entity)object);
				this.discard();
			}
		}
	}

	private int getTicksLeftUntilAdult() {
		return Math.max(0, ticksToBeFrog - this.age);
	}

	@Override
	public boolean shouldDropExperience() {
		return false;
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 7) {
			this.spawnTamingParticles(true);
		} else if (by == 6) {
			this.spawnTamingParticles(false);
		} else if (by == 11) {
			this.spawnHealParticles();
		} else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.isTame()) {
			LivingEntity livingentity = this.getOwner();
			if (entity == livingentity) {
				return true;
			}
			if (livingentity != null) {
				return livingentity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		return !this.isOwnedBy(livingEntity) && super.canAttack(livingEntity);
	}

	@Override
	public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
		return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}

		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource) && this.getFrogType() == 4)
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

	@javax.annotation.Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData, @javax.annotation.Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void die(DamageSource damageSource) {
		Component deathMessage = this.getCombatTracker().getDeathMessage();
		super.die(damageSource);
		if (this.dead)
			if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
				this.getOwner().sendSystemMessage(deathMessage);
			}
	}
}