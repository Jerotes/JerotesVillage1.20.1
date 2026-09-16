package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class GlowSparrowEntity extends ShoulderRidingEntity implements FlyingAnimal, JerotesEntity, FactionEntity, ChangePoseAbout, HasHomePosEntity {
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState danceAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/seed_and_fruit_foods"))
	);
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(GlowSparrowEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(GlowSparrowEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(GlowSparrowEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(GlowSparrowEntity.class, EntityDataSerializers.BOOLEAN);

	public GlowSparrowEntity(EntityType<? extends GlowSparrowEntity> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new FlyingMoveControl(this, 20, false);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0f);
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.FLYING_SPEED, 2.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
		builder = builder.add(Attributes.MAX_HEALTH, 6.0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		return builder;
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(1, new JerotesAnimalChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, true));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(4, new GlowSparrowWanderGoal(this, 1.0));
		this.goalSelector.addGoal(5, new LandOnOwnersShoulderGoal(this));
		this.goalSelector.addGoal(6, new FollowMobGoal(this, 1.0, 3.0f, 7.0f));
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		return GlowSparrowEntity.getAmbient((Level)this.level(), ((Level)this.level()).random);
	}
	public static SoundEvent getAmbient(Level level, RandomSource randomSource) {
		return JerotesVillageSoundEvents.GLOW_SPARROW_AMBIENT;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Override
	protected void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
		this.resetFallDistance();
	}
	@Nullable
	@Override
	public GlowSparrowEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		GlowSparrowEntity glowSparrow = JerotesVillageEntityType.GLOW_SPARROW.get().create(serverLevel);
		if (glowSparrow != null && (uUID = this.getOwnerUUID()) != null) {
			glowSparrow.setOwnerUUID(uUID);
			glowSparrow.setTame(true);
		}
		return glowSparrow;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.GLOW_SPARROW_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.GLOW_SPARROW_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.GLOW_SPARROW_WALK, 0.15f, 1.0f);
	}
	@Override
	protected boolean isFlapping() {
		return this.flyDist > this.nextFlap;
	}
	@Override
	protected void onFlap() {
		this.playSound(JerotesVillageSoundEvents.GLOW_SPARROW_FLY, 0.15f, 1.0f);
		this.nextFlap = this.flyDist + this.flapSpeed / 2.0f;
	}
	@Override
	public boolean isPushable() {
		return true;
	}
	@Override
	protected void doPush(Entity entity) {
		if (entity instanceof Player player && this.isOwnedBy(player)) {
			return;
		}
		super.doPush(entity);
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanFloat(true);
		flyingPathNavigation.setCanPassDoors(true);
		return flyingPathNavigation;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 0.5f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public void setRecordPlayingNearby(BlockPos blockPos, boolean bl) {
		this.jukebox = blockPos;
		this.dance = bl;
	}
	public boolean isDance() {
		return this.dance;
	}
	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	private int danceTick = 0;
	private int sitTick = 0;
	private double sitFlySpeed = 2.0f;
	private boolean dance;
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	private float flapping = 1.0f;
	private float nextFlap = 1.0f;
	@Nullable
	private BlockPos jukebox;
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
		compoundTag.putBoolean("Dance", this.dance);
		compoundTag.putDouble("SitFlySpeed", this.sitFlySpeed);
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("DanceTick", this.danceTick);
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
		this.dance = compoundTag.getBoolean("Dance");
		this.sitFlySpeed = compoundTag.getDouble("SitFlySpeed");
		this.sitTick = compoundTag.getInt("SitTick");
		this.danceTick = compoundTag.getInt("DanceTick");
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
		//跳舞
		if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 12) || !((Level)this.level()).getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
			this.dance = false;
			this.jukebox = null;
		}
		if (this.isDance()) {
			if (this.danceTick <= 0) {
				this.danceTick = 10;
				this.danceAnimationState.start(this.tickCount);
				this.sitAnimationState.stop();
				this.stopSitAnimationState.stop();
				this.toSitAnimationState.stop();
			}
		}
		else {
			this.danceAnimationState.stop();
			this.danceTick = 0;
		}
		if (this.danceTick > 0) {
			this.danceTick -= 1;
		}

		if (this.isInSittingPose() && !this.isFlying() && sitTick <= 0 && !this.isDance()){
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

//		if (this.ride == 1) {
//			if (this.random.nextInt(20) == 1 && this.deathTime == 0) {
//				if (!this.level().isClientSide) {
//					if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
//						BlockState blockState = JerotesVillageBlocks.GLOW_SHINE.get().defaultBlockState();
//						BlockPos blockPos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
//						if ((this.level().getBlockState(blockPos).isAir() || this.level().getBlockState(blockPos).getBlock() == JerotesVillageBlocks.GLOW_SHINE.get()) && blockState.canSurvive(this.level(), blockPos)) {
//							this.level().setBlockAndUpdate(blockPos, blockState);
//							this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
//						}
//					}
//				}
//			}
//		}
//		else {
			if (this.random.nextInt(50) == 1 && this.deathTime == 0) {
				if (!this.level().isClientSide) {
					if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
						BlockState blockState = JerotesVillageBlocks.GLOW_SHINE.get().defaultBlockState();
						BlockPos blockPos = this.getOnPos().above();
						if ((this.level().getBlockState(blockPos).isAir() || this.level().getBlockState(blockPos).getBlock() == JerotesVillageBlocks.GLOW_SHINE.get()) && blockState.canSurvive(this.level(), blockPos)) {
							this.level().setBlockAndUpdate(blockPos, blockState);
							this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
						}
					}
				}
			}
//		}
		this.calculateFlapping();
	}

	private void calculateFlapping() {
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed += (float)(this.onGround() || this.isPassenger() ? -1 : 4) * 0.3f;
		this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0f, 1.0f);
		if (!this.onGround() && this.flapping < 1.0f) {
			this.flapping = 1.0f;
		}
		this.flapping *= 0.9f;
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
		}
		this.flap += this.flapping * 2.0f;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
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
						this.heal(1f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
			}
			//坐下
			if ((interactionResult = super.mobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
			if (!this.isFlying()) {
				if (!this.isVehicle() && player == this.getOwner()) {
					int pose = this.getChangeType() + 1;
					if (pose > 4) {
						pose = 1;
					}
					this.setChangeType(pose, player);
				}
			}
			return InteractionResult.SUCCESS;
		}
		if (!this.isFood(itemStack))
			return super.mobInteract(player, interactionHand);

		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (!this.isSilent()) {
			((Level)this.level()).playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GLOW_SPARROW_EAT, this.getSoundSource(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
		}
		if (!this.level().isClientSide()) {
			if (this.random.nextInt(3) == 1) {
				this.tame(player);
				this.setChangeType(1, player);
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				((Level)this.level()).broadcastEntityEvent(this, (byte)7);
				return InteractionResult.SUCCESS;
			}
			else {
				((Level)this.level()).broadcastEntityEvent(this, (byte)6);
			}
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}

	static class GlowSparrowWanderGoal extends WaterAvoidingRandomFlyingGoal {
		public GlowSparrowWanderGoal(PathfinderMob pathfinderMob, double d) {
			super(pathfinderMob, d);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && !(this.mob instanceof GlowSparrowEntity glowSparrow && glowSparrow.isDance());
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && !(this.mob instanceof GlowSparrowEntity glowSparrow && glowSparrow.isDance());
		}

		@Nullable
		@Override
		protected Vec3 getPosition() {
			Vec3 vec3 = null;
			if (this.mob.isInWater()) {
				vec3 = LandRandomPos.getPos(this.mob, 15, 15);
			}
			if (this.mob.getRandom().nextFloat() >= this.probability) {
				vec3 = this.getTreePos();
			}
			return vec3 == null ? super.getPosition() : vec3;
		}

		@Nullable
		private Vec3 getTreePos() {
			BlockPos blockPos = this.mob.blockPosition();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
			Iterable<BlockPos> iterable = BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0), Mth.floor(this.mob.getY() - 6.0), Mth.floor(this.mob.getZ() - 3.0), Mth.floor(this.mob.getX() + 3.0), Mth.floor(this.mob.getY() + 6.0), Mth.floor(this.mob.getZ() + 3.0));
			for (BlockPos blockPos2 : iterable) {
				BlockState blockState;
				boolean bl;
				if (blockPos.equals(blockPos2) || !(bl = (blockState = this.mob.level().getBlockState(mutableBlockPos2.setWithOffset((Vec3i)blockPos2, Direction.DOWN))).getBlock() instanceof LeavesBlock || blockState.is(BlockTags.LOGS)) || !this.mob.level().isEmptyBlock(blockPos2) || !this.mob.level().isEmptyBlock(mutableBlockPos.setWithOffset((Vec3i)blockPos2, Direction.UP))) continue;
				return Vec3.atBottomCenterOf(blockPos2);
			}
			return null;
		}
	}

}