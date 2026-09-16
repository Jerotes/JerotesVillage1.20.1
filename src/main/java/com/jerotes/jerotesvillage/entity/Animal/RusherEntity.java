package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.JerotesHorseEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;

public class RusherEntity extends AbstractHorse implements PlayerRideableJumping, ControlVehicleEntity, ChangePoseAbout, HasHomePosEntity, Saddleable, OwnableEntity, NeutralMob, JerotesEntity, FactionEntity, TameMobEntity {
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState eatAnimationState = new AnimationState();
	public AnimationState longJumpAnimationState = new AnimationState();
	public AnimationState jumpAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BYTE);
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("8FDDCE28-5F25-34C8-138A-16BE55D2F35E");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("7AC4FB79-3D9D-E76B-DB11-4B2BF9BED0B8");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("02F2E1AA-1544-2EAE-0D99-8DFCB42DF328");
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_NORMAL_CONTROL = SynchedEntityData.defineId(RusherEntity.class, EntityDataSerializers.BOOLEAN);

	public RusherEntity(EntityType<? extends RusherEntity> type, Level world) {
		super(type, world);
		this.reassessTameGoals();
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.38);
		builder = builder.add(Attributes.MAX_HEALTH, 35);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.JUMP_STRENGTH, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.4));
		this.goalSelector.addGoal(2, new RunAroundLikeCrazyGoal(this, 1.2));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				if (RusherEntity.this.getControllingPassenger() instanceof Player player && !RusherEntity.this.isNormalSteeringControlJerotes() && RusherEntity.this.canBeControlJerotes(player)) {
					return false;
				}
				return super.canUse();
			}    @Override
			public boolean canContinueToUse() {
				if (RusherEntity.this.getControllingPassenger() instanceof Player player && !RusherEntity.this.isNormalSteeringControlJerotes() && RusherEntity.this.canBeControlJerotes(player)) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		if (this.canPerformRearing()) {
			this.goalSelector.addGoal(9, new RandomStandGoal(this));
		}
		this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<RusherEntity>(this, false));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.RUSHER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.RUSHER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.RUSHER_DEATH;
	}
	@Override
	protected SoundEvent getAngrySound() {
		return JerotesVillageSoundEvents.RUSHER_ANGRY;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.RUSHER_WALK, 0.15f, 1.0f);
	}
	@Nullable
	@Override
	protected SoundEvent getEatingSound() {
		return JerotesVillageSoundEvents.RUSHER_EAT;
	}
	@Override
	protected void playJumpSound() {
		this.playSound(JerotesVillageSoundEvents.RUSHER_JUMP, 0.4f, 1.0f);
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
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
		return -0.55F;
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
	public RusherEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		RusherEntity rusher = JerotesVillageEntityType.RUSHER.get().create(serverLevel);
		if (rusher != null && (uUID = this.getOwnerUUID()) != null) {
			rusher.setOwnerUUID(uUID);
			rusher.setTame(true);
		}
		return rusher;
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
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 2.1f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean canJump() {
		return this.isSaddled();
	}
	@Override
	public boolean canWearArmor() {
		return true;
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
		}
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
		boolean flag = !(this.getControllingPassenger() instanceof Mob) &&
				!(this.getControllingPassenger() instanceof Player && !this.isNormalSteeringControlJerotes());
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
			this.playSound(JerotesVillageSoundEvents.RUSHER_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(9);
			this.setAnimationState("attack");
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.RUSHER_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(9);
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
	@Override
	public void pressLeftJerotes(Player player) {
		float targetYRot = this.getYRot() - 12.0f;
		targetYRot = targetYRot % 360.0f;
		if (targetYRot < 0) targetYRot += 360.0f;
		this.yRotO = targetYRot;
		this.setYRot(targetYRot);
		this.setYHeadRot(targetYRot);
		this.setYBodyRot(targetYRot);
		this.hasImpulse = true;
	}
	@Override
	public void pressRightJerotes(Player player) {
		float targetYRot = this.getYRot() + 12.0f;
		targetYRot = targetYRot % 360.0f;
		if (targetYRot < 0) targetYRot += 360.0f;
		this.yRotO = targetYRot;
		this.setYRot(targetYRot);
		this.setYHeadRot(targetYRot);
		this.setYBodyRot(targetYRot);
		this.hasImpulse = true;
	}
	public boolean canPressLeftJerotes() {return !this.isNormalSteeringControlJerotes();}
	public boolean canPressRightJerotes() {return !this.isNormalSteeringControlJerotes();}
	public boolean canChangeSteeringControl(Player player) {
		return true;
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
	@Override
	public boolean isArmor(ItemStack itemStack) {
		return itemStack.getItem() instanceof HorseArmorItem;
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setOrderedToSit(boolean bl) {
		this.orderedToSit = bl;
	}
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	private void setArmor(ItemStack itemStack) {
		this.setItemSlot(EquipmentSlot.CHEST, itemStack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
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
	public boolean isNormalSteeringControlJerotes() {
		return this.getEntityData().get(IS_NORMAL_CONTROL);
	}
	public void setNormalSteeringControlJerotes(boolean bl) {
		this.getEntityData().set(IS_NORMAL_CONTROL, bl);
		if (this.getControllingPassenger() instanceof Player player) {
			if (bl) {
				player.displayClientMessage(Component.translatable("message.jerotes.change_control_steering_type_0"), true);
			}
			else {
				player.displayClientMessage(Component.translatable("message.jerotes.change_control_steering_type_1"), true);
			}
		}
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
		compoundTag.putInt("SitTick", this.sitTick);
		if (this.getOwnerUUID() != null) {
			compoundTag.putUUID("Owner", this.getOwnerUUID());
		}
		compoundTag.putBoolean("Sitting", this.orderedToSit);
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		compoundTag.putBoolean("IsNormalSteeringControlJerotes", this.isNormalSteeringControlJerotes());
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
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.setNormalSteeringControlJerotes(compoundTag.getBoolean("IsNormalSteeringControlJerotes"));
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
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
		this.getEntityData().define(IS_NORMAL_CONTROL, false);
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
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}
		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
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
		if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
			return InteractionResult.sidedSuccess(((Level)this.level()).isClientSide);
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
	protected void updateContainerEquipment() {
		if (this.level().isClientSide()) {
			return;
		}
		super.updateContainerEquipment();
		this.setArmorEquipment(this.inventory.getItem(1));
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public void setArmorEquipment(ItemStack itemStack) {
		this.setArmor(itemStack);
		if (!this.level().isClientSide) {
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
	public void containerChanged(Container container) {
		ItemStack itemStack = this.getArmor();
		super.containerChanged(container);
		ItemStack itemStack2 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemStack2) && itemStack != itemStack2) {
			this.playSound(JerotesSoundEvents.USE_BEAST_ARMOR, 0.5f, 1.0f);
		}
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
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.RUSHER_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.isSaddled() && damageSource.is(DamageTypeTags.IS_FALL)){
			amount = amount / 4;
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
	public void handleStartJump(int n) {
		this.allowStandSliding = true;
		this.standIfPossible();
		this.playJumpSound();
		if (n < 70) {
			this.level().broadcastEntityEvent(this, (byte)101);
		}
		else {
			this.level().broadcastEntityEvent(this, (byte)102);
		}
	}

	@Override
	public void handleStopJump() {
	}

	@Override
	protected float getRiddenSpeed(Player player) {
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 1.5f;
	}

	@Override
	public double getCustomJump() {
		return this.getAttributeValue(Attributes.JUMP_STRENGTH);
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
			this.setDeltaMovement(this.getDeltaMovement().add(-0.7f * f2 * f, 0.0, 0.7f * f3 * f));
		}
	}
	protected void tickRidden(Player p_278233_, Vec3 p_275693_) {
		if (this.isNormalSteeringControlJerotes()) {
			super.tickRidden(p_278233_, p_275693_);
		}
		else {
			Vec2 vec2 = this.getRiddenRotation(p_278233_);
//			this.setRot(vec2.y, vec2.x);
//			this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
			if (this.isControlledByLocalInstance()) {
				if (p_275693_.z <= 0.0D) {
					this.gallopSoundCounter = 0;
				}
				if (this.onGround()) {
					this.setIsJumping(false);
					if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
						this.executeRidersJump(this.playerJumpPendingScale, p_275693_);
					}
					this.playerJumpPendingScale = 0.0F;
				}
			}
		}
	}
	protected Vec3 getRiddenInput(Player player, Vec3 p_275506_) {
		if (this.isNormalSteeringControlJerotes()) {
			return super.getRiddenInput(player, p_275506_);
		}
		if (this.onGround() && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
			return Vec3.ZERO;
		} else {
			float f1 = player.zza;
			if (f1 <= 0.0F) {
				f1 *= 0.25F;
			}
			return new Vec3(0, 0.0D, (double)f1);
		}
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 7) {
			this.spawnTamingParticles(true);
		} else if (by == 6) {
			this.spawnTamingParticles(false);
		}
		else if (by == 101) {
			this.jumpAnimationState.start(this.tickCount);
		}
		else if (by == 102) {
			this.longJumpAnimationState.start(this.tickCount);
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