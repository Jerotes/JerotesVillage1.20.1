package com.jerotes.jerotesvillage.entity.Neutral.MerorMachine;

import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ZsieinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.*;

public class MerorMachineEntity extends AbstractGolem implements NeutralMob, ChangePoseAbout, HasHomePosEntity, OwnableEntity, TameMobEntity, JerotesEntity, FactionEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	protected static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> POSE_TICK = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> POSE_USE_TICK = SynchedEntityData.defineId(MerorMachineEntity.class, EntityDataSerializers.INT);
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState pose1AnimationState = new AnimationState();
	public AnimationState pose2AnimationState = new AnimationState();
	public AnimationState pose3AnimationState = new AnimationState();

	public MerorMachineEntity(EntityType<? extends MerorMachineEntity> entityType, Level level) {
		super(entityType, level);
		this.reassessTameGoals();
		setPersistenceRequired();
	}

	@Override
	public String getFirstFactionTypeName() {
		return "meror_machine";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("ancient_meror_civilization");
		return list;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<ZsieinEntity>(this, ZsieinEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<MerorMachineEntity>(this, true));
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && (this.isInFluidType())) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(vec3);
		}
		if (this.specialAction() && !specialActionCanMove() || this.getPoseUseTick() > 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	@Override
	public boolean isOnFire() {
		return false;
	}

	public boolean specialAction() {
		return false;
	}
	public boolean specialActionCanMove() {
		return false;
	}
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
	public void setOwnerUUID(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_OWNER_UUID_ID, Optional.ofNullable(uuid));
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.getEntityData().get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setTame(boolean bl) {
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
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

	public void setPoseTick(int n){
		this.getEntityData().set(POSE_TICK, n);
	}
	public int getPoseTick(){
		return this.getEntityData().get(POSE_TICK);
	}
	public void setPoseUseTick(int n){
		this.getEntityData().set(POSE_USE_TICK, n);
	}
	public int getPoseUseTick(){
		return this.getEntityData().get(POSE_USE_TICK);
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
		if (Objects.equals(animation, "pose1")){
			return 101;
		}
		else if (Objects.equals(animation, "pose2")){
			return 102;
		}
		else if (Objects.equals(animation, "pose3")){
			return 103;
		}
		return 0;
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.pose1AnimationState);
		list.add(this.pose2AnimationState);
		list.add(this.pose3AnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != pose1AnimationState && state != pose2AnimationState && state != pose3AnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			if (state != pose1AnimationState && state != pose2AnimationState && state != pose3AnimationState) {
				state.stop();
			}
		}
	}

	//
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
		compoundTag.putInt("PoseTick", this.getPoseTick());
		compoundTag.putInt("PoseUseTick", this.getPoseUseTick());
		this.addPersistentAngerSaveData(compoundTag);
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
		}
		else {
			String string = compoundTag.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
		}
		if (uuid != null) {
			try {
				this.setOwnerUUID(uuid);
				this.setTame(true);
			}
			catch (Throwable throwable) {
				this.setTame(false);
			}
		}
		this.orderedToSit = compoundTag.getBoolean("Sitting");
		this.setInSittingPose(this.orderedToSit);
		this.setPoseTick(compoundTag.getInt("PoseTick"));
		this.setPoseUseTick(compoundTag.getInt("PoseUseTick"));
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
		this.getEntityData().define(POSE_TICK, 0);
		this.getEntityData().define(POSE_USE_TICK, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 101:
						this.pose1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pose1AnimationState);
						break;
					case 102:
						this.pose2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pose2AnimationState);
						break;
					case 103:
						this.pose3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pose3AnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		Item item = itemStack.getItem();
		if (this.level().isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(player)) ? InteractionResult.sidedSuccess(this.level().isClientSide()) : InteractionResult.PASS;
		}
		else {
			if (this.isTame()) {
				if (this.isOwnedBy(player)) {
					if (!player.isShiftKeyDown()) {
						if (itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/meror_metal"))) || itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/refine_meror_metal")))) {
							this.repair(player, itemStack);
							return super.mobInteract(player, interactionHand);
						}
						if (player instanceof ServerPlayer serverPlayer && this instanceof MerorOathguardMechEntity && player == this.getOwner()) {
							Main.openMobInventoryGui(serverPlayer, this, false, true, true, true, true, true, true, true);
						}
					}
					if (!this.isVehicle() && player == this.getOwner()) {
						int pose = this.getChangeType() + 1;
						if (pose > 4) {
							pose = 1;
						}
						this.setChangeType(pose, player);
					}
					return super.mobInteract(player, interactionHand);
				}
			}
			else {
				if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")))) {
					if (!this.isTame()) {
						this.tame(player);
						this.jumping = false;
						this.navigation.stop();
						this.setTarget(null);
						setChangeType(1, player);
						this.level().broadcastEntityEvent(this, (byte) 7);
						return InteractionResult.SUCCESS;
					}
					else {
						this.level().broadcastEntityEvent(this, (byte) 6);
					}
					this.setPersistenceRequired();
					return InteractionResult.SUCCESS;
				}
				if (itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/meror_metal"))) || itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/refine_meror_metal")))) {
					this.repair(player, itemStack);
					return InteractionResult.SUCCESS;
				}
				else {
					retval = super.mobInteract(player, interactionHand);
					if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
						this.setPersistenceRequired();
				}
			}
		}
		return retval;
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

	public boolean repair(Player player, ItemStack itemStack) {
		this.heal(this.getMaxHealth() / 8);
		float f2 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
		if (this instanceof OldSecondRounderGolemEntity) {
			this.playSound(JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_REPAIR, 1.0f, f2);
		}
		else {
			this.playSound(JerotesSoundEvents.REPAIR_MOB, 1.0f, f2);
		}
		if (!this.level().isClientSide()) {
			this.removeAllEffects();
		}
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return true;
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
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;

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
		if (!(this instanceof MerorChampionEntity)) {
			//清除动画
			if (!this.level().isClientSide()) {
				this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
			}
			if (this.getAnimTick() == 0) {
				if (!this.level().isClientSide()) {
					this.setAnimationState(0);
				}
			}
		}
		this.clearFire();
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//闲置
		if (this.specialAction() || this.isAggressive() || this.isOrderedToSit()) {
			if (!this.level().isClientSide()) {
				this.setPoseTick(1020);
			}
		}
		if (this.getPoseUseTick() > 0) {
			this.getNavigation().stop();
		}
		if (this.onGround() && !this.isAggressive() && this.getRandom().nextInt(10 * 20) == 1 && this.getPoseTick() <= 0) {
			if (!this.level().isClientSide()) {
				int poseRandom = this.getRandom().nextInt(30);
				if (poseRandom > 20) {
					this.setAnimationState("pose1");
				}
				else if (poseRandom > 10) {
					this.setAnimationState("pose2");
				}
				else {
					this.setAnimationState("pose3");
				}
				this.setPoseTick(960 + this.getRandom().nextInt(10 * 20));
				this.setPoseUseTick(60);
			}
		}
		if (!this.level().isClientSide()) {
			this.setPoseTick(Math.max(0, this.getPoseTick() - 1));
			this.setPoseUseTick(Math.max(0, this.getPoseUseTick() - 1));
		}
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
			this.navigation = new AmphibiousPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			if (this instanceof MerorMonstrosityEntity || this instanceof MerorChampionEntity) {
				this.moveControl = new GiantMoveControl(this);
			}
			else {
				this.moveControl = new MoveControl(this);
			}
			this.navigation = new GroundPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		attackAnimation();
		return super.doHurtTarget(entity);
	}
	public void attackAnimation() {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isFireDamage(damageSource)
				|| AttackFind.isLightningDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource)
				|| damageSource.is(DamageTypes.FALLING_BLOCK)
				|| damageSource.is(DamageTypes.FALLING_ANVIL)
				|| damageSource.is(DamageTypes.FALLING_STALACTITE)
				|| damageSource.is(JerotesDamageTypes.POISON))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!(this instanceof OldSecondRounderGolemEntity) && !EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			amount = (float) (amount / OtherMainConfig.MerorMachineMerorToughness);
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
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.BLEEDING.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.DARKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
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
		if (this.isTame()) {
			LivingEntity livingentity = this.getOwner();
			if (entity == livingentity) {
				return true;
			}
			if (livingentity != null) {
				return livingentity.isAlliedTo(entity);
			}
		}
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return !this.isOwnedBy(livingEntity) && super.canAttack(livingEntity);
	}

	@Override
	public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
		return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(Items.AIR);
	}

	public ItemStack createSpawnOffhand(float offhandRandom) {
		return new ItemStack(Items.AIR);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			float weaponRandom = this.random.nextFloat();
			float offhandRandom = this.random.nextFloat();
			this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
			this.setItemSlot(EquipmentSlot.OFFHAND, this.createSpawnOffhand(offhandRandom));
		}
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

	private UUID persistentAngerTarget;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}


	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}
}

