package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class RustleFoxEntity extends Fox implements JerotesEntity, TameMobEntity, OwnableEntity, ChangePoseAbout, HasHomePosEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState sleepAnimationState = new AnimationState();
	public AnimationState jumpAnimationState = new AnimationState();
	public AnimationState wantAttackAnimationState = new AnimationState();
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FOX_TYPE = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.INT);
	static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_309166_) -> {
		return !p_309166_.hasPickUpDelay() && p_309166_.isAlive();
	};
	public static final Predicate<LivingEntity> PREY_SELECTOR = entity ->{
		return entity instanceof Chicken || entity instanceof Rabbit || entity instanceof RuinsMouseEntity;
	};
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID_OTHER = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(RustleFoxEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	public RustleFoxEntity(EntityType<? extends Fox> entityType, Level level) {
		super(entityType, level);
		this.reassessTameGoals();
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32.0);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FoxFloatGoal());
		this.goalSelector.addGoal(0, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
		this.goalSelector.addGoal(1, new FaceplantGoal());
		this.goalSelector.addGoal(2, new FoxPanicGoal(2.2));
		this.goalSelector.addGoal(3, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 1.6, 1.4, (wolf) -> {return !((Wolf)wolf).isTame() && !this.isDefending() && this.getTarget() != wolf;}));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PolarBear.class, 8.0F, 1.6, 1.4, (bear) -> {return !this.isDefending() && this.getTarget() != bear;}));
		this.goalSelector.addGoal(5, new StalkPreyGoal());
		this.goalSelector.addGoal(6, new FoxPounceGoal());
		this.goalSelector.addGoal(6, new SeekShelterGoal(1.25));
		this.goalSelector.addGoal(7, new FoxMeleeAttackGoal(1.2, true));
		this.goalSelector.addGoal(7, new SleepGoal());
		this.goalSelector.addGoal(8, new FoxFollowParentGoal(this, 1.25) {
			public boolean canUse() {
				if (RustleFoxEntity.this.isWander()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (RustleFoxEntity.this.isWander()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.goalSelector.addGoal(9, new FoxStrollThroughVillageGoal(32, 200));
		this.goalSelector.addGoal(10, new FoxEatBerriesGoal(1.2, 12, 1));
		this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(11, new FoxSearchForItemsGoal());
		this.goalSelector.addGoal(12, new FoxLookAtPlayerGoal(this, Player.class, 24.0F));
		this.goalSelector.addGoal(13, new PerchAndSearchGoal());

		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
		this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		if (randomSource.nextFloat() < 0.2f) {
			float f = randomSource.nextFloat();
			ItemStack itemStack =
					f < 0.05f ? new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get()) :
							(f < 0.2f ? new ItemStack(JerotesVillageItems.IRISHTAIL_CHICKEN_EGG.get()) :
									(f < 0.4f ? (randomSource.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE)) :
											(f < 0.6f ? new ItemStack(Items.WHEAT) :
													(f < 0.8f ? new ItemStack(Items.LEATHER) : new ItemStack(Items.FEATHER)))));
			this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
		}
	}


	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}

	private boolean orderedToSit;
	float crouchAmount;
	float crouchAmountO;
	public int getFoxType() {
		return this.getEntityData().get(FOX_TYPE);
	}
	public void setFoxType(int n) {
		this.getEntityData().set(FOX_TYPE, n);
	}
	@Override
	public boolean isTame() {
		return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
	}
	@Override
	public boolean isInSittingPose() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0 || this.isSitting();
	}
	public boolean isOwnedBy(LivingEntity livingEntity) {
		return livingEntity == this.getOwner();
	}
	@Override
	public boolean isOrderedToSit() {
		return this.orderedToSit;
	}
	public void setOwnerUUID(@Nullable UUID uuid) {
		this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uuid));
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setTame(boolean bl) {
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3);
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
	private int crouchTick;
	private int sitTick = 0;
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
	//
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsWander", this.isWander());
		compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		if (this.getOwnerUUID() != null) {
			compoundTag.putUUID("Owner", this.getOwnerUUID());
		}

		compoundTag.putBoolean("Sitting", this.orderedToSit);
		compoundTag.putInt("FoxType", this.getFoxType());
		compoundTag.putInt("crouchTick", this.crouchTick);
		compoundTag.putInt("SitTick", this.sitTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
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
		this.setFoxType(compoundTag.getInt("FoxType"));
		this.crouchTick = compoundTag.getInt("CrouchTick");
		this.sitTick = compoundTag.getInt("SitTick");
		this.setInSittingPose(this.orderedToSit);
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
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_FLAGS_ID_OTHER, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
		this.getEntityData().define(FOX_TYPE, 1);
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

	@Nullable
	@Override
	public RustleFoxEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		RustleFoxEntity fox = JerotesVillageEntityType.RUSTLE_FOX.get().create(serverLevel);
		if (fox != null && (uUID = this.getOwnerUUID()) != null) {
			fox.setOwnerUUID(uUID);
			fox.setTame(true);
			if (ageableMob instanceof RustleFoxEntity fox2) {
				if (this.random.nextBoolean()) {
					fox.setFoxType(this.getFoxType());
				} else {
					fox.setFoxType(fox2.getFoxType());
				}
			}
			else {
				fox.setFoxType(this.getFoxType());
			}
		}
		else if (fox != null) {
			if (ageableMob instanceof RustleFoxEntity fox2) {
				if (this.random.nextBoolean()) {
					fox.setFoxType(this.getFoxType());
				} else {
					fox.setFoxType(fox2.getFoxType());
				}
			}
			else {
				fox.setFoxType(this.getFoxType());
			}
		}
		return fox;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}
		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
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
		if (this.crouchTick > 0){
			this.crouchTick -= 1;
		}

		if (this.isSleeping()){
			this.sleepAnimationState.start(this.tickCount);
		}
		else {
			this.sleepAnimationState.stop();
		}
		if (this.isPouncing() || this.isFaceplanted()) {
			this.jumpAnimationState.start(this.tickCount);
		}
		else {
			this.jumpAnimationState.stop();
			if (this.isCrouching()) {
				if (this.crouchTick <= 0 && this.deathTime == 0) {
					this.wantAttackAnimationState.start(this.tickCount);
					this.crouchTick = 40;
				}
			}
			else {
				this.wantAttackAnimationState.stop();
			}
		}

		if (this.isTame() && this.isSitting()) {
			this.setSitting(false);
		}
	}

	public void tick() {
		super.tick();
		this.crouchAmountO = this.crouchAmount;
		if (this.isCrouching()) {
			this.crouchAmount += 0.2F;
			if (this.crouchAmount > 3.0F) {
				this.crouchAmount = 3.0F;
			}
		} else {
			this.crouchAmount = 0.0F;
		}
	}
	public boolean isFullyCrouched() {
		return this.crouchAmount == 3.0F;
	}
	public float getCrouchAmount(float p_28625_) {
		return Mth.lerp(p_28625_, this.crouchAmountO, this.crouchAmount);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
		return super.doHurtTarget(entity);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}

		int freezeAmount = 2;
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
					return super.mobInteract(player, interactionHand);
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			//坐下
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
		if (!this.isFood(itemStack))
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(6) == 1 && !ForgeEventFactory.onAnimalTame(this, player)) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			setChangeType(1, player);
			((Level)this.level()).broadcastEntityEvent(this, (byte)7);
		}
		else {
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
	public void die(DamageSource damageSource) {
		Component deathMessage = this.getCombatTracker().getDeathMessage();
		super.die(damageSource);
		if (this.dead)
			if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
				this.getOwner().sendSystemMessage(deathMessage);
		}
	}

	boolean canMove() {
		return !this.isSleeping() && !this.isSitting() && !this.isFaceplanted();
	}
	void clearStates() {
		this.setIsInterested(false);
		this.setIsCrouching(false);
		this.setSitting(false);
		this.setSleeping(false);
		this.setDefending(false);
		this.setFaceplanted(false);
	}
	void setSleeping(boolean p_28627_) {
		this.setFlag(32, p_28627_);
	}
	void setDefending(boolean p_28623_) {
		this.setFlag(128, p_28623_);
	}
	void setFaceplanted(boolean p_28619_) {
		this.setFlag(64, p_28619_);
	}
	private void setFlag(int p_28533_, boolean p_28534_) {
		if (p_28534_) {
			this.entityData.set(DATA_FLAGS_ID_OTHER, (byte)((Byte)this.entityData.get(DATA_FLAGS_ID_OTHER) | p_28533_));
		} else {
			this.entityData.set(DATA_FLAGS_ID_OTHER, (byte)((Byte)this.entityData.get(DATA_FLAGS_ID_OTHER) & ~p_28533_));
		}
	}
	boolean isDefending() {
		return this.getFlag(128);
	}
	private boolean getFlag(int p_28609_) {
		return ((Byte)this.entityData.get(DATA_FLAGS_ID_OTHER) & p_28609_) != 0;
	}

	class FoxFloatGoal extends FloatGoal {
		public FoxFloatGoal() {
			super(RustleFoxEntity.this);
		}

		public void start() {
			super.start();
			RustleFoxEntity.this.clearStates();
		}

		public boolean canUse() {
			return RustleFoxEntity.this.isInWater() && RustleFoxEntity.this.getFluidHeight(FluidTags.WATER) > 0.25 || RustleFoxEntity.this.isInLava();
		}
	}

	class FaceplantGoal extends Goal {
		int countdown;

		public FaceplantGoal() {
			this.setFlags(EnumSet.of(Flag.LOOK, Flag.JUMP, Flag.MOVE));
		}

		public boolean canUse() {
			return RustleFoxEntity.this.isFaceplanted();
		}

		public boolean canContinueToUse() {
			return this.canUse() && this.countdown > 0;
		}

		public void start() {
			this.countdown = this.adjustedTickDelay(40);
		}

		public void stop() {
			RustleFoxEntity.this.setFaceplanted(false);
		}

		public void tick() {
			--this.countdown;
		}
	}

	class FoxPanicGoal extends PanicGoal {
		public FoxPanicGoal(double p_28734_) {
			super(RustleFoxEntity.this, p_28734_);
		}
		public boolean shouldPanic() {
			return !RustleFoxEntity.this.isDefending() && super.shouldPanic() && !RustleFoxEntity.this.isTame();
		}
	}


	class StalkPreyGoal extends Goal {
		public StalkPreyGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		public boolean canUse() {
			if (RustleFoxEntity.this.isSleeping()) {
				return false;
			} else {
				LivingEntity livingentity = RustleFoxEntity.this.getTarget();
				return livingentity != null && livingentity.isAlive() && (RustleFoxEntity.PREY_SELECTOR.test(livingentity) || (livingentity instanceof Mob mob && mob.getTarget() != RustleFoxEntity.this)) && RustleFoxEntity.this.distanceToSqr(livingentity) > 36.0 && !RustleFoxEntity.this.isCrouching() && !RustleFoxEntity.this.isInterested() && !RustleFoxEntity.this.jumping;
			}
		}

		public void start() {
			RustleFoxEntity.this.setSitting(false);
			RustleFoxEntity.this.setFaceplanted(false);
		}

		public void stop() {
			LivingEntity livingentity = RustleFoxEntity.this.getTarget();
			if (livingentity != null && Fox.isPathClear(RustleFoxEntity.this, livingentity)) {
				RustleFoxEntity.this.setIsInterested(true);
				RustleFoxEntity.this.setIsCrouching(true);
				RustleFoxEntity.this.getNavigation().stop();
				RustleFoxEntity.this.getLookControl().setLookAt(livingentity, (float)RustleFoxEntity.this.getMaxHeadYRot(), (float)RustleFoxEntity.this.getMaxHeadXRot());
			} else {
				RustleFoxEntity.this.setIsInterested(false);
				RustleFoxEntity.this.setIsCrouching(false);
			}

		}

		public void tick() {
			LivingEntity livingentity = RustleFoxEntity.this.getTarget();
			if (livingentity != null) {
				RustleFoxEntity.this.getLookControl().setLookAt(livingentity, (float)RustleFoxEntity.this.getMaxHeadYRot(), (float)RustleFoxEntity.this.getMaxHeadXRot());
				if (RustleFoxEntity.this.distanceToSqr(livingentity) <= 36.0) {
					RustleFoxEntity.this.setIsInterested(true);
					RustleFoxEntity.this.setIsCrouching(true);
					RustleFoxEntity.this.getNavigation().stop();
				} else {
					RustleFoxEntity.this.getNavigation().moveTo(livingentity, 1.5);
				}
			}

		}
	}

	public class FoxPounceGoal extends JumpGoal {
		public FoxPounceGoal() {
		}

		public boolean canUse() {
			if (!RustleFoxEntity.this.isFullyCrouched()) {
				return false;
			} else {
				LivingEntity livingentity = RustleFoxEntity.this.getTarget();
				if (livingentity != null && livingentity.isAlive()) {
					if (livingentity.getMotionDirection() != livingentity.getDirection()) {
						return false;
					} else {
						boolean flag = Fox.isPathClear(RustleFoxEntity.this, livingentity);
						if (!flag) {
							RustleFoxEntity.this.getNavigation().createPath(livingentity, 0);
							RustleFoxEntity.this.setIsCrouching(false);
							RustleFoxEntity.this.setIsInterested(false);
						}

						return flag;
					}
				} else {
					return false;
				}
			}
		}

		public boolean canContinueToUse() {
			LivingEntity livingentity = RustleFoxEntity.this.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				double d0 = RustleFoxEntity.this.getDeltaMovement().y;
				return (!(d0 * d0 < 0.05000000074505806) || !(Math.abs(RustleFoxEntity.this.getXRot()) < 15.0F) || !RustleFoxEntity.this.onGround()) && !RustleFoxEntity.this.isFaceplanted();
			} else {
				return false;
			}
		}

		public boolean isInterruptable() {
			return false;
		}

		public void start() {
			RustleFoxEntity.this.setJumping(true);
			RustleFoxEntity.this.setIsPouncing(true);
			RustleFoxEntity.this.setIsInterested(false);
			LivingEntity livingentity = RustleFoxEntity.this.getTarget();
			if (livingentity != null) {
				RustleFoxEntity.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
				Vec3 vec3 = (new Vec3(livingentity.getX() - RustleFoxEntity.this.getX(), livingentity.getY() - RustleFoxEntity.this.getY(), livingentity.getZ() - RustleFoxEntity.this.getZ())).normalize();
				RustleFoxEntity.this.setDeltaMovement(RustleFoxEntity.this.getDeltaMovement().add(vec3.x * 0.8, 0.9, vec3.z * 0.8));
			}

			RustleFoxEntity.this.getNavigation().stop();
		}

		public void stop() {
			RustleFoxEntity.this.setIsCrouching(false);
			RustleFoxEntity.this.crouchAmount = 0.0F;
			RustleFoxEntity.this.crouchAmountO = 0.0F;
			RustleFoxEntity.this.setIsInterested(false);
			RustleFoxEntity.this.setIsPouncing(false);
		}

		public void tick() {
			LivingEntity livingentity = RustleFoxEntity.this.getTarget();
			if (livingentity != null) {
				RustleFoxEntity.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
			}

			if (!RustleFoxEntity.this.isFaceplanted()) {
				Vec3 vec3 = RustleFoxEntity.this.getDeltaMovement();
				if (vec3.y * vec3.y < 0.029999999329447746 && RustleFoxEntity.this.getXRot() != 0.0F) {
					RustleFoxEntity.this.setXRot(Mth.rotLerp(0.2F, RustleFoxEntity.this.getXRot(), 0.0F));
				} else {
					double d0 = vec3.horizontalDistance();
					double d1 = Math.signum(-vec3.y) * Math.acos(d0 / vec3.length()) * 57.2957763671875;
					RustleFoxEntity.this.setXRot((float)d1);
				}
			}

			if (livingentity != null && RustleFoxEntity.this.distanceTo(livingentity) <= 2.0F) {
				RustleFoxEntity.this.doHurtTarget(livingentity);
			} else if (RustleFoxEntity.this.getXRot() > 0.0F && RustleFoxEntity.this.onGround() && (float)RustleFoxEntity.this.getDeltaMovement().y != 0.0F && RustleFoxEntity.this.level().getBlockState(RustleFoxEntity.this.blockPosition()).is(Blocks.SNOW)) {
				RustleFoxEntity.this.setXRot(60.0F);
				RustleFoxEntity.this.setTarget((LivingEntity)null);
				RustleFoxEntity.this.setFaceplanted(true);
			}

		}
	}

	class SeekShelterGoal extends FleeSunGoal {
		private int interval = reducedTickDelay(100);

		public SeekShelterGoal(double p_28777_) {
			super(RustleFoxEntity.this, p_28777_);
		}

		public boolean canUse() {
			if (!RustleFoxEntity.this.isSleeping() && this.mob.getTarget() == null) {
				if (RustleFoxEntity.this.level().isThundering() && RustleFoxEntity.this.level().canSeeSky(this.mob.blockPosition())) {
					return this.setWantedPos();
				} else if (this.interval > 0) {
					--this.interval;
					return false;
				} else {
					this.interval = 100;
					BlockPos blockpos = this.mob.blockPosition();
					return RustleFoxEntity.this.level().isDay() && RustleFoxEntity.this.level().canSeeSky(blockpos) && !((ServerLevel)RustleFoxEntity.this.level()).isVillage(blockpos) && this.setWantedPos();
				}
			} else {
				return false;
			}
		}

		public void start() {
			RustleFoxEntity.this.clearStates();
			super.start();
		}
	}

	class FoxMeleeAttackGoal extends JerotesMeleeAttackGoal {
		public FoxMeleeAttackGoal(double p_28720_, boolean p_28721_) {
			super(RustleFoxEntity.this, p_28720_, p_28721_);
		}

		protected void checkAndPerformAttack(LivingEntity p_28724_) {
			if (this.canPerformAttack(p_28724_)) {
				this.resetAttackCooldown();
				this.mob.doHurtTarget(p_28724_);
				RustleFoxEntity.this.playSound(SoundEvents.FOX_BITE, 1.0F, 1.0F);
			}

		}

		public void start() {
			RustleFoxEntity.this.setIsInterested(false);
			super.start();
		}

		public boolean canUse() {
			return !RustleFoxEntity.this.isSitting()
					&& !RustleFoxEntity.this.isSleeping()
					&& !RustleFoxEntity.this.isCrouching()
					&& !RustleFoxEntity.this.isFaceplanted()
					&& super.canUse();
		}
	}

	class SleepGoal extends FoxBehaviorGoal {
		private static final int WAIT_TIME_BEFORE_SLEEP = reducedTickDelay(140);
		private int countdown;

		public SleepGoal() {
			super();
			this.countdown = RustleFoxEntity.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
		}

		public boolean canUse() {
			if (RustleFoxEntity.this.isTame()) {
				return false;
			}
			if (RustleFoxEntity.this.xxa == 0.0F && RustleFoxEntity.this.yya == 0.0F && RustleFoxEntity.this.zza == 0.0F) {
				return this.canSleep() || RustleFoxEntity.this.isSleeping();
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			if (RustleFoxEntity.this.isTame()) {
				return false;
			}
			return this.canSleep();
		}

		private boolean canSleep() {
			if (RustleFoxEntity.this.isTame()) {
				return false;
			}
			if (this.countdown > 0) {
				--this.countdown;
				return false;
			} else {
				return RustleFoxEntity.this.level().isDay() && this.hasShelter() && !this.alertable() && !RustleFoxEntity.this.isInPowderSnow;
			}
		}

		public void stop() {
			this.countdown = RustleFoxEntity.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
			RustleFoxEntity.this.clearStates();
		}

		public void start() {
			RustleFoxEntity.this.setSitting(false);
			RustleFoxEntity.this.setIsCrouching(false);
			RustleFoxEntity.this.setIsInterested(false);
			RustleFoxEntity.this.setJumping(false);
			RustleFoxEntity.this.setSleeping(true);
			RustleFoxEntity.this.getNavigation().stop();
			RustleFoxEntity.this.getMoveControl().setWantedPosition(RustleFoxEntity.this.getX(), RustleFoxEntity.this.getY(), RustleFoxEntity.this.getZ(), 0.0);
		}
	}

	class FoxFollowParentGoal extends JerotesFollowParentGoal {
		private final RustleFoxEntity fox;

		public FoxFollowParentGoal(RustleFoxEntity p_28696_, double p_28697_) {
			super(p_28696_, p_28697_);
			this.fox = p_28696_;
		}

		public boolean canUse() {
			return !this.fox.isDefending() && super.canUse();
		}

		public boolean canContinueToUse() {
			return !this.fox.isDefending() && super.canContinueToUse();
		}

		public void start() {
			this.fox.clearStates();
			super.start();
		}
	}

	class FoxStrollThroughVillageGoal extends StrollThroughVillageGoal {
		public FoxStrollThroughVillageGoal(int p_28754_, int p_28755_) {
			super(RustleFoxEntity.this, p_28755_);
		}

		public void start() {
			RustleFoxEntity.this.clearStates();
			super.start();
		}

		public boolean canUse() {
			return super.canUse() && this.canFoxMove();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && this.canFoxMove();
		}

		private boolean canFoxMove() {
			return !RustleFoxEntity.this.isSleeping() && !RustleFoxEntity.this.isSitting() && !RustleFoxEntity.this.isDefending() && RustleFoxEntity.this.getTarget() == null;
		}
	}

	public class FoxEatBerriesGoal extends MoveToBlockGoal {
		private static final int WAIT_TICKS = 40;
		protected int ticksWaited;

		public FoxEatBerriesGoal(double p_28675_, int p_28676_, int p_28677_) {
			super(RustleFoxEntity.this, p_28675_, p_28676_, p_28677_);
		}

		public double acceptedDistance() {
			return 2.0;
		}

		public boolean shouldRecalculatePath() {
			return this.tryTicks % 100 == 0;
		}

		protected boolean isValidTarget(LevelReader p_28680_, BlockPos p_28681_) {
			BlockState blockstate = p_28680_.getBlockState(p_28681_);
			return blockstate.is(Blocks.SWEET_BERRY_BUSH) && (Integer)blockstate.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(blockstate);
		}

		public void tick() {
			if (this.isReachedTarget()) {
				if (this.ticksWaited >= 40) {
					this.onReachedTarget();
				} else {
					++this.ticksWaited;
				}
			} else if (!this.isReachedTarget() && RustleFoxEntity.this.random.nextFloat() < 0.05F) {
				RustleFoxEntity.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
			}

			super.tick();
		}

		protected void onReachedTarget() {
			if (ForgeEventFactory.getMobGriefingEvent(RustleFoxEntity.this.level(), RustleFoxEntity.this)) {
				BlockState blockstate = RustleFoxEntity.this.level().getBlockState(this.blockPos);
				if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
					this.pickSweetBerries(blockstate);
				} else if (CaveVines.hasGlowBerries(blockstate)) {
					this.pickGlowBerry(blockstate);
				}
			}

		}

		private void pickGlowBerry(BlockState p_148927_) {
			CaveVines.use(RustleFoxEntity.this, p_148927_, RustleFoxEntity.this.level(), this.blockPos);
		}

		private void pickSweetBerries(BlockState p_148929_) {
			int i = (Integer)p_148929_.getValue(SweetBerryBushBlock.AGE);
			p_148929_.setValue(SweetBerryBushBlock.AGE, 1);
			int j = 1 + RustleFoxEntity.this.level().random.nextInt(2) + (i == 3 ? 1 : 0);
			ItemStack itemstack = RustleFoxEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (itemstack.isEmpty()) {
				RustleFoxEntity.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
				--j;
			}

			if (j > 0) {
				Block.popResource(RustleFoxEntity.this.level(), this.blockPos, new ItemStack(Items.SWEET_BERRIES, j));
			}

			RustleFoxEntity.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
			RustleFoxEntity.this.level().setBlock(this.blockPos, (BlockState)p_148929_.setValue(SweetBerryBushBlock.AGE, 1), 2);
			RustleFoxEntity.this.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(RustleFoxEntity.this));
		}

		public boolean canUse() {
			return !RustleFoxEntity.this.isSleeping() && super.canUse();
		}

		public void start() {
			this.ticksWaited = 0;
			RustleFoxEntity.this.setSitting(false);
			super.start();
		}
	}

	class FoxSearchForItemsGoal extends Goal {
		public FoxSearchForItemsGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			if (!RustleFoxEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				return false;
			} else if (RustleFoxEntity.this.getTarget() == null && RustleFoxEntity.this.getLastHurtByMob() == null) {
				if (!RustleFoxEntity.this.canMove()) {
					return false;
				} else if (RustleFoxEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
					return false;
				} else {
					List<ItemEntity> list = RustleFoxEntity.this.level().getEntitiesOfClass(ItemEntity.class, RustleFoxEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), RustleFoxEntity.ALLOWED_ITEMS);
					return !list.isEmpty() && RustleFoxEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
				}
			} else {
				return false;
			}
		}

		public void tick() {
			List<ItemEntity> list = RustleFoxEntity.this.level().getEntitiesOfClass(ItemEntity.class, RustleFoxEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), RustleFoxEntity.ALLOWED_ITEMS);
			ItemStack itemstack = RustleFoxEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (itemstack.isEmpty() && !list.isEmpty()) {
				RustleFoxEntity.this.getNavigation().moveTo((Entity)list.get(0), 1.2000000476837158);
			}

		}

		public void start() {
			List<ItemEntity> list = RustleFoxEntity.this.level().getEntitiesOfClass(ItemEntity.class, RustleFoxEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), RustleFoxEntity.ALLOWED_ITEMS);
			if (!list.isEmpty()) {
				RustleFoxEntity.this.getNavigation().moveTo((Entity)list.get(0), 1.2000000476837158);
			}

		}
	}

	class FoxLookAtPlayerGoal extends LookAtPlayerGoal {
		public FoxLookAtPlayerGoal(Mob p_28707_, Class<? extends LivingEntity> p_28708_, float p_28709_) {
			super(p_28707_, p_28708_, p_28709_);
		}

		public boolean canUse() {
			return super.canUse() && !RustleFoxEntity.this.isFaceplanted() && !RustleFoxEntity.this.isInterested();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && !RustleFoxEntity.this.isFaceplanted() && !RustleFoxEntity.this.isInterested();
		}
	}

	class PerchAndSearchGoal extends FoxBehaviorGoal {
		private double relX;
		private double relZ;
		private int lookTime;
		private int looksRemaining;

		public PerchAndSearchGoal() {
			super();
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		public boolean canUse() {
			if (RustleFoxEntity.this.isTame()) {
				return false;
			}
			return RustleFoxEntity.this.getLastHurtByMob() == null && RustleFoxEntity.this.getRandom().nextFloat() < 0.02F && !RustleFoxEntity.this.isSleeping() && RustleFoxEntity.this.getTarget() == null && RustleFoxEntity.this.getNavigation().isDone() && !this.alertable() && !RustleFoxEntity.this.isPouncing() && !RustleFoxEntity.this.isCrouching();
		}

		public boolean canContinueToUse() {
			if (RustleFoxEntity.this.isTame()) {
				return false;
			}
			return this.looksRemaining > 0;
		}

		public void start() {
			this.resetLook();
			this.looksRemaining = 2 + RustleFoxEntity.this.getRandom().nextInt(3);
			RustleFoxEntity.this.setSitting(true);
			RustleFoxEntity.this.getNavigation().stop();
		}

		public void stop() {
			RustleFoxEntity.this.setSitting(false);
		}

		public void tick() {
			--this.lookTime;
			if (this.lookTime <= 0) {
				--this.looksRemaining;
				this.resetLook();
			}

			RustleFoxEntity.this.getLookControl().setLookAt(RustleFoxEntity.this.getX() + this.relX, RustleFoxEntity.this.getEyeY(), RustleFoxEntity.this.getZ() + this.relZ, (float)RustleFoxEntity.this.getMaxHeadYRot(), (float)RustleFoxEntity.this.getMaxHeadXRot());
		}

		private void resetLook() {
			double d0 = 6.283185307179586 * RustleFoxEntity.this.getRandom().nextDouble();
			this.relX = Math.cos(d0);
			this.relZ = Math.sin(d0);
			this.lookTime = this.adjustedTickDelay(80 + RustleFoxEntity.this.getRandom().nextInt(20));
		}
	}

	abstract class FoxBehaviorGoal extends Goal {
		private final TargetingConditions alertableTargeting;

		FoxBehaviorGoal() {
			TargetingConditions var10001 = TargetingConditions.forCombat().range(12.0).ignoreLineOfSight();
			Fox var10004 = RustleFoxEntity.this;
			Objects.requireNonNull(var10004);
			this.alertableTargeting = var10001.selector(var10004.new FoxAlertableEntitiesSelector());
		}

		protected boolean hasShelter() {
			BlockPos blockpos = BlockPos.containing(RustleFoxEntity.this.getX(), RustleFoxEntity.this.getBoundingBox().maxY, RustleFoxEntity.this.getZ());
			return !RustleFoxEntity.this.level().canSeeSky(blockpos) && RustleFoxEntity.this.getWalkTargetValue(blockpos) >= 0.0F;
		}

		protected boolean alertable() {
			return !RustleFoxEntity.this.level().getNearbyEntities(LivingEntity.class, this.alertableTargeting, RustleFoxEntity.this, RustleFoxEntity.this.getBoundingBox().inflate(12.0, 6.0, 12.0)).isEmpty();
		}
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.DAMAGED_RUINS)) {
				this.setFoxType(2);
			} else if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)) {
				this.setFoxType(3);
			} else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
				this.setFoxType(1);
			}
			else {
				float f = serverLevelAccessor.getRandom().nextFloat();
				if (f < 0.75f) {
					this.setFoxType(1);
				}
				else if (f < 0.9f) {
					this.setFoxType(2);
				}
				else {
					this.setFoxType(3);
				}
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}