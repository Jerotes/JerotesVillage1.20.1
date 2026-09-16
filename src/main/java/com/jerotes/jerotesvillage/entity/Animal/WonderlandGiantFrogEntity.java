package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.WonderlandGiantFrogBreedGoal;
import com.jerotes.jerotesvillage.goal.WonderlandGiantFrogLayEggGoal;
import com.jerotes.jerotesvillage.goal.WonderlandGiantFrogMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class WonderlandGiantFrogEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, ArmorEntity, PlayerRideableJumping, Saddleable, ContainerListener, HasCustomInventoryScreen {
	public AnimationState jumpAnimationState = new AnimationState();
	public AnimationState croakAnimationState = new AnimationState();
	public AnimationState tongueAnimationState = new AnimationState();
	public AnimationState swimIdleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState eatAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_12, false);
	private static final EntityDataAccessor<Integer> FROG_TYPE = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PLAYER_JUMP_COOLDOWN = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> LAYING_EGG = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FAKE_BOSS = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FALL_ATTACK = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> FALL_TICK = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FIRST_HEAL = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(WonderlandGiantFrogEntity.class, EntityDataSerializers.INT);
	public int layEggCounter;
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	@Override
	public boolean isWearingArmor() {
		return false;
	}
	@Override
	public boolean isWarBeastArmor() {
		return false;
	}
	@Override
	public boolean isGiantBeastArmor() {
		return false;
	}

	public WonderlandGiantFrogEntity(EntityType<? extends WonderlandGiantFrogEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(1.1f);
		this.lookControl = new FrogLookControl(this);
		this.setPathfindingMalus(BlockPathTypes.WATER, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 16.0f);
		this.createInventory();
		this.fixupDimensions();
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		if (this.isFakeBoss() && JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR))
			this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		if (this.isFakeBoss() && JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR))
			this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (this.isFakeBoss() && JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR))
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
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
	}
	public void containerChanged(Container p_30548_) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
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
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new WonderlandGiantFrogMeleeAttackGoal(WonderlandGiantFrogEntity.this, 1.2, true));
		this.goalSelector.addGoal(1, new JerotesLeapAtTargetAndLookGoal(this, 0.6F) {
			public boolean canUse() {
				return !WonderlandGiantFrogEntity.this.isFakeBoss() && super.canUse();
			}
			public boolean canContinueToUse() {
				return !WonderlandGiantFrogEntity.this.isFakeBoss() && super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(1, new WonderlandGiantFrogLayEggGoal(this, 1.0));
		this.goalSelector.addGoal(2, new WonderlandGiantFrogBreedGoal(this, 1.0));
		this.goalSelector.addGoal(2, new JerotesAnimalChangeTemptGoal(this, 1.1, Ingredient.of(new ItemStack(JerotesVillageItems.PINK_SLIME_BALL.get())), false));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new BreathAirGoal(this));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<Slime>(this, Slime.class, false, livingEntity -> true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<WonderlandGiantFrogEntity>(this, false));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("croak");
		}
		return SoundEvents.FROG_AMBIENT;
	}
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.FROG_HURT;
	}
	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.FROG_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(SoundEvents.FROG_STEP, 0.15f, 1.0f);
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 25;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return Ingredient.of(JerotesVillageItems.PINK_SLIME_BALL.get()).test(itemStack);
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
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
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
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), dir);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		} else {
			super.travel(dir);
		}
	}
	@Override
	public boolean isPushable() {
		if (this.isFakeBoss()) {
			return false;
		}
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
		if (this.isFakeBoss())
			return -0.6f;
		return -0.6f;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//
	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.6f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}
	@Override
	public int getMaxHeadYRot() {
		return 5;
	}
	@Override
	public int getHeadRotSpeed() {
		return 35;
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
		if (this.isFakeBoss())
			return aabb1.inflate(4.8d, 4.8d, 4.8d);
		return aabb1.inflate(1.2d, 1.2d, 1.2d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		float scale = 1f;
		if (this.isFakeBoss())
			scale = 4f;
		else if (this.getFrogType() == 2)
			scale = 0.75f;
		else if (this.getFrogType() == 3)
			scale = 0.85f;
		else if (this.getFrogType() == 4)
			scale = 0.8f;
		return super.getDimensions(pose).scale(scale);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float scale = 1f;
		if (this.isFakeBoss())
			scale = 4f;
		else if (this.getFrogType() == 2)
			scale = 0.75f;
		else if (this.getFrogType() == 3)
			scale = 0.85f;
		else if (this.getFrogType() == 4)
			scale = 0.8f;
		return 1.25f * scale;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		WonderlandGiantFrogEntity frog = JerotesVillageEntityType.WONDERLAND_GIANT_FROG.get().create(serverLevel);
		if (frog != null && (uUID = this.getOwnerUUID()) != null) {
			frog.setOwnerUUID(uUID);
			frog.setTame(true);
		}
		return frog;
	}
	@Override
	public boolean canFallInLove() {
		return super.canFallInLove() && !this.hasEgg();
	}
	@Override
	public boolean canBeCollidedWith() {
		if (this.isFakeBoss()) {
			return this.isAlive();
		}
		else {
			return super.canBeCollidedWith();
		}
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		if (this.isFakeBoss()) {
			super.knockback(0, 0, 0);
		}
		super.knockback(d, d2, d3);
	}
	@Override
	public void push(double d, double d2, double d3) {
		if (this.isFakeBoss()) {
			super.push(0, 0, 0);
		}
		super.push(d, d2, d3);
	}
	@Override
	public boolean canBeLeashed(Player player) {
		if (this.isFakeBoss()) {
			return false;
		}
		return super.canBeLeashed(player);
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
		if (!this.isFakeBoss()) {
			AttackFind.individualAttackJerotes(this, 40, 0);
			if (!this.isSilent()) {
				this.playSound(SoundEvents.FROG_TONGUE, 1.0f, 1.0f);
			}
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAttackTick(20);
				this.setAnimationState("attack");
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAnimationState("attack");
			}
			if (!this.isSilent()) {
				this.playSound(SoundEvents.FROG_TONGUE, 5.0f, 1.0f);
			}
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(16, 16, 16));
			for (LivingEntity hurt : list) {
				if (hurt == null) continue;
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				if (!this.hasLineOfSight(hurt)) continue;
				if (!Main.canSee(hurt, this)) continue;
				if (hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != this)
					continue;
				if (hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != this)
					continue;
				float damageMulti = 1;
				if (hurt.getType().is(EntityTypeTags.FROG_FOOD)) {
					damageMulti = 4;
				}
				AttackFind.attackBegin(this, hurt);
				boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, 0f, false, 0f);
				if (bl2) {
					//设为吞咽
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.teleportTo(this.getX(), this.getY(0.1f), this.getZ());
					}
				}
			}
			//横扫效果
			Main.sweepAttack(this);
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		if (!this.isFakeBoss()) {
			rightAttack(this, 40);
			if (!this.level().isClientSide()) {
				this.setAnimTick(24);
				this.setAttackTick(60);
				this.setAnimationState("tongue");
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAnimationState("attack");
			}
			if (!this.isSilent()) {
				this.playSound(SoundEvents.FROG_TONGUE, 5.0f, 1.0f);
			}
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(16, 16, 16));
			for (LivingEntity hurt : list) {
				if (hurt == null) continue;
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				if (!this.hasLineOfSight(hurt)) continue;
				if (!Main.canSee(hurt, this)) continue;
				if (hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != this)
					continue;
				if (hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != this)
					continue;
				float damageMulti = 1;
				if (hurt.getType().is(EntityTypeTags.FROG_FOOD)) {
					damageMulti = 4;
				}
				AttackFind.attackBegin(this, hurt);
				boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, 0f, false, 0f);
				if (bl2) {
					//设为吞咽
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.teleportTo(this.getX(), this.getY(0.1f), this.getZ());
					}
				}
			}
			//横扫效果
			Main.sweepAttack(this);
		}
	}
	public void rightAttack(Mob mob, double angleThreshold) {
		LivingEntity tureHurt = null;
		List<LivingEntity> list = mob.level().getEntitiesOfClass(LivingEntity.class, mob instanceof JerotesEntity jerotes && jerotes.getAttackBoundingBox() != null ? jerotes.getAttackBoundingBox().inflate(5) : mob.getBoundingBox().inflate(5));
		if (list.isEmpty())
			return;
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(mob, hurt)) continue;
			if (!mob.hasLineOfSight(hurt)) continue;
			if (!Main.canSeeAngle(mob, hurt.getEyePosition(), angleThreshold)) continue;
			if (tureHurt == null || tureHurt.distanceTo(mob) > hurt.distanceTo(mob)) {
				tureHurt = hurt;
			}
		}
		if (tureHurt == null)
			return;
		if (mob instanceof ControlVehicleEntity controlVehicleEntity) {
			controlVehicleEntity.individualAttackJerotes(tureHurt, 1);
			controlVehicleEntity.individualAttackJerotes(tureHurt);
		} else {
			mob.doHurtTarget(tureHurt);
		}
	}
	@Override
	public void individualAttackJerotes(LivingEntity livingEntity, int type) {
		if (type == 1) {
			AttackFind.attackBegin(this, livingEntity);
			AttackFind.attackAfter(this, livingEntity, 4f, 0.2f, false, 0);
		}
		else {
			AttackFind.attackBegin(this, livingEntity);
			AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
		}
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
	public double blockDestroyTick;
	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}
	public void setHasEgg(boolean bl) {
		this.entityData.set(HAS_EGG, bl);
	}
	public void setLayingEgg(boolean bl) {
		this.layEggCounter = 1;
		this.entityData.set(LAYING_EGG, bl);
	}
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
	public boolean isArmor(ItemStack itemStack) {
		return false;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	public void setBaby(boolean bl) {
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35.0);
			this.setHealth(35.0f);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
		}
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	public void setJumpTick(int n){
		this.getEntityData().set(JUMP_TICK, n);
	}
	public int getJumpTick(){
		return this.getEntityData().get(JUMP_TICK);
	}
	public void setPlayerJumpCooldown(int n){
		this.getEntityData().set(PLAYER_JUMP_COOLDOWN, n);
	}
	public int getPlayerJumpCooldown(){
		return this.getEntityData().get(PLAYER_JUMP_COOLDOWN);
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
		if (Objects.equals(animation, "jump")){
			return 1;
		}
		else if (Objects.equals(animation, "croak")){
			return 2;
		}
		else if (Objects.equals(animation, "tongue")){
			return 3;
		}
		else if (Objects.equals(animation, "attack")){
			return 4;
		}
		else if (Objects.equals(animation, "eat")){
			return 5;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.jumpAnimationState);
		list.add(this.croakAnimationState);
		list.add(this.tongueAnimationState);
		list.add(this.attackAnimationState);
		list.add(this.eatAnimationState);
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
	public boolean isFirstHeal() {
		return this.entityData.get(FIRST_HEAL);
	}
	public void setFirstHeal(boolean bl) {
		this.entityData.set(FIRST_HEAL, bl);
	}
	public boolean isFakeBoss() {
		return this.entityData.get(FAKE_BOSS);
	}
	public void setFakeBoss(boolean bl) {
		this.entityData.set(FAKE_BOSS, bl);
		if (this.isTame()) {
			if (bl) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(440.0);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(128);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20.0);
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35.0);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22);
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0);
			}
		}
		else {
			if (bl) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(420.0);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(128);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20.0);
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22);
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0);
			}
		}
		if (this.isFirstHeal()) {
			this.setHealth(this.getMaxHealth());
			this.entityData.set(FIRST_HEAL, false);
		}
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public boolean isFallAttack() {
		return this.entityData.get(FALL_ATTACK);
	}
	public void setFallAttack(boolean bl) {
		this.entityData.set(FALL_ATTACK, bl);
	}
	public int getFallTick() {
		return this.entityData.get(FALL_TICK);
	}
	public void setFallTick(int n) {
		this.entityData.set(FALL_TICK, n);
	}
	public int getFrogType(){
		return this.entityData.get(FROG_TYPE);
	}
	public void setFrogType(int id){
		this.entityData.set(FROG_TYPE, id);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putBoolean("IsFirstHeal", this.isFirstHeal());
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("FrogType", this.getFrogType());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("JumpTick", this.getJumpTick());
		compoundTag.putInt("PlayerJumpCooldown", this.getPlayerJumpCooldown());
		compoundTag.putBoolean("HasEgg", this.hasEgg());
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putBoolean("IsFakeBoss", this.isFakeBoss());
		compoundTag.putInt("FallTick", this.getFallTick());
		compoundTag.putBoolean("IsFallAttack", this.isFallAttack());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setFirstHeal(compoundTag.getBoolean("IsFirstHeal"));
		this.setFakeBoss(compoundTag.getBoolean("IsFakeBoss"));
		ItemStack itemStack;
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.sitTick = compoundTag.getInt("SitTick");
		super.readAdditionalSaveData(compoundTag);
		this.setFrogType(compoundTag.getInt("FrogType"));
		this.setJumpTick(compoundTag.getInt("JumpTick"));
		this.setPlayerJumpCooldown(compoundTag.getInt("PlayerJumpCooldown"));
		this.setHasEgg(compoundTag.getBoolean("HasEgg"));
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.setFallTick(compoundTag.getInt("FallTick"));
		this.setFallAttack(compoundTag.getBoolean("IsFallAttack"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.updateContainerEquipment();
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FROG_TYPE, 1);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(HAS_EGG, false);
		this.getEntityData().define(LAYING_EGG, false);
		this.getEntityData().define(FAKE_BOSS, false);
		this.getEntityData().define(FIRST_HEAL, true);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(JUMP_TICK, 0);
		this.getEntityData().define(PLAYER_JUMP_COOLDOWN, 0);
		this.getEntityData().define(FALL_TICK, 0);
		this.getEntityData().define(FALL_ATTACK, false);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (FAKE_BOSS.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.jumpAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.jumpAnimationState);
						break;
					case 2:
						this.croakAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.croakAnimationState);
						break;
					case 3:
						this.tongueAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tongueAnimationState);
						break;
					case 4:
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
					case 5:
						this.eatAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eatAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		if (this.level().isClientSide()) {
			this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
		}
		super.tick();
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (!this.isFakeBoss()) {
			if (entity instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.FROG_FOOD)) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(24);
					this.setAnimationState("tongue");
				}
				AttackFind.attackBegin(this, entity);
				boolean bl = AttackFind.attackAfter(this, entity, 4, 0.2f, false, 0f);
				if (bl) {
					if (!this.isSilent()) {
						this.playSound(SoundEvents.FROG_TONGUE, 1.0f, 1.0f);
					}
				}
				return bl;
			} else {
				if (!this.level().isClientSide()) {
					this.setAnimTick(10);
					this.setAnimationState("attack");
				}
				boolean bl = super.doHurtTarget(entity);
				if (bl) {
					if (!this.isSilent()) {
						this.playSound(SoundEvents.FROG_TONGUE, 1.0f, 1.0f);
					}
				}
				return bl;
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAnimationState("attack");
			}
			float damageMulti = 1;
			if (entity instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.FROG_FOOD)) {
				damageMulti = 4;
			}
			AttackFind.attackBegin(this, entity);
			boolean bl = AttackFind.attackAfter(this, entity, damageMulti, 0.2f, false, 0f);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(SoundEvents.FROG_TONGUE, 5.0f, 1.0f);
				}
				//设为吞咽
				if (entity instanceof LivingEntity livingEntity && (Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity) || Main.mobSizeLarge(livingEntity)) && !EntityAndItemFind.isNoSpecialKnockback(livingEntity.getType())) {
					livingEntity.teleportTo(this.getX(), this.getY(0.1f), this.getZ());
				}
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(8, 8, 8));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (!Main.canSee(hurt, this)) continue;
					if (hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != this)
						continue;
					if (hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != this)
						continue;
					AttackFind.attackBegin(this, hurt);
					boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti * 0.5f, 0f, false, 0f);
					if (bl2) {
						//设为吞咽
						if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
							hurt.teleportTo(this.getX(), this.getY(0.1f), this.getZ());
						}
					}
				}
				//横扫效果
				Main.sweepAttack(this);
			}
			return bl;
		}
	}

	@Override
	public boolean killedEntity(ServerLevel serverLevel, LivingEntity livingEntity) {
		boolean bl = super.killedEntity(serverLevel, livingEntity);
		if (!EntityAndItemFind.isLoreUndying(livingEntity)) {
			if (this.isFakeBoss()) {
				for (int i = 0; i < 64; ++i) {
					double d = this.getRandom().nextGaussian() * 0.02;
					double d2 = this.getRandom().nextGaussian() * 0.02;
					double d3 = this.getRandom().nextGaussian() * 0.02;
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d, d2, d3, 0);
				}
				if (!this.isSilent()) {
					this.level().playSound(null, this, SoundEvents.FROG_EAT, SoundSource.NEUTRAL, 2.0f, 1.0f);
				}
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.75));
				if (list.contains(livingEntity)) {
					this.heal(livingEntity.getMaxHealth() / 10f);
				}
				livingEntity.remove(RemovalReason.KILLED);
				if (this.getTarget() == null) {
					if (!this.level().isClientSide()) {
						this.setAnimTick(10);
						this.setAnimationState("eat");
					}
				}
			} else {
				if (livingEntity.getType().is(EntityTypeTags.FROG_FOOD)) {
					if (!this.isSilent()) {
						this.level().playSound(null, this, SoundEvents.FROG_EAT, SoundSource.NEUTRAL, 2.0f, 1.0f);
					}
					livingEntity.remove(RemovalReason.KILLED);
					if (this.getTarget() == null) {
						if (!this.level().isClientSide()) {
							this.setAnimTick(10);
							this.setAnimationState("eat");
						}
					}
				}
			}
		}
		//岩浆怪
		if (livingEntity.getType() == EntityType.MAGMA_CUBE && livingEntity instanceof MagmaCube magmaCube && magmaCube.getScale() <= 1)  {
			ItemStack itemStack;
			if (this.getRandom().nextFloat() > 0.95f) {
				float f = this.getRandom().nextFloat();
				itemStack = new ItemStack(Items.OCHRE_FROGLIGHT);
				if (f > 2 / 3f) {
					itemStack = new ItemStack(Items.VERDANT_FROGLIGHT);
				} else if (f < 1 / 3f) {
					itemStack = new ItemStack(Items.PEARLESCENT_FROGLIGHT);
				}
			}
			else {
				itemStack = new ItemStack(JerotesVillageItems.SPRING_FROGLIGHT.get());
				if (this.getFrogType() == 2) {
					itemStack = new ItemStack(JerotesVillageItems.SUMMER_FROGLIGHT.get());
				} else if (this.getFrogType() == 3) {
					itemStack = new ItemStack(JerotesVillageItems.AUTUMN_FROGLIGHT.get());
				} else if (this.getFrogType() == 4) {
					itemStack = new ItemStack(JerotesVillageItems.WINTER_FROGLIGHT.get());
				}
			}
			livingEntity.spawnAtLocation(itemStack,0);
		}
		return bl;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (itemStack.is(JerotesVillageItems.PINK_SLIME_BLOCK.get())) {
			player.playSound(SoundEvents.FROG_EAT, 1.0f, 1.0f);
			ItemStack itemStack2;
			itemStack2 = new ItemStack(JerotesVillageItems.SPRING_FROGLIGHT.get());
			if (this.getFrogType() == 2) {
				itemStack2 = new ItemStack(JerotesVillageItems.SUMMER_FROGLIGHT.get());
			} else if (this.getFrogType() == 3) {
				itemStack2 = new ItemStack(JerotesVillageItems.AUTUMN_FROGLIGHT.get());
			} else if (this.getFrogType() == 4) {
				itemStack2 = new ItemStack(JerotesVillageItems.WINTER_FROGLIGHT.get());
			}
			if (!player.getInventory().add(itemStack2)) {
				player.drop(itemStack2, false);
			}
			this.heal(3.0f);
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.HEART, this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
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
					return super.mobInteract(player, interactionHand);
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
		if (!this.isFood(itemStack) || this.isAngry())
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.isFakeBoss()) {
			if (this.random.nextInt(2400) == 1 && !ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.navigation.stop();
				this.setTarget(null);
				setChangeType(1, player);
				((Level) this.level()).broadcastEntityEvent(this, (byte) 7);
			} else {
				((Level) this.level()).broadcastEntityEvent(this, (byte) 6);
			}
		}
		else {
			if (this.random.nextInt(24) == 1 && !ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.navigation.stop();
				this.setTarget(null);
				setChangeType(1, player);
				((Level) this.level()).broadcastEntityEvent(this, (byte) 7);
			} else {
				((Level) this.level()).broadcastEntityEvent(this, (byte) 6);
			}
		}
		return InteractionResult.SUCCESS;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FrogPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = false;

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		//boss
		if (this.isFakeBoss()) {
			if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR)) {
				this.bossEvent.update();
			}
			if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
				this.heal(5.0f);
			}
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = 60;
				}
				if (!this.level().isClientSide()) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
			//视角摇晃
			if (this.walkAnimation.isMoving() && this.onGround() && this.getHealth() > this.getMaxHealth() / 4 && !this.isBaby()) {
				float f = (float) Math.cos(this.walkAnimation.position() * 0.8F - 1.5F);
				if (Math.abs(f) < 0.2) {
					List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
					for (LivingEntity shake : listShake) {
						if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
							continue;
						if (!shake.level().isClientSide()) {
							shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
						}
					}
				}
			}
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.75));
			list.removeIf(living -> living == this);
			//摔击
			if (this.getJumpTick() <= 0 && !this.isVehicle() && this.isAlive()) {
				if (this.onGround() && this.getTarget() != null && this.getRandom().nextInt(2 * 20) == 1 && this.getFallTick() >= 240) {
					if (!this.level().isClientSide()) {
						this.setJumpTick(20);
						this.setAnimTick(24);
						this.setAnimationState("jump");
						this.setOnGround(false);
						this.setFallTick(0);
						this.setFallAttack(true);
					}
				}
			}
			if (isFallAttack() && this.onGround() && this.isAlive() && this.getFallTick() >= 10) {
				if (!this.level().isClientSide()) {
					this.setFallAttack(false);
				}
				float damage = 20 + this.getHealth() / 20;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					damage = 40 + this.getHealth() / 15;
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					damage = 60 + this.getHealth() / 10;
				}
				OtherSpellFind.EarthQuake(this, (int) damage, 12);
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 100.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
				}
			}

			if (!list.isEmpty()) {
				if (this.tickCount % 10 == 0) {
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != this && this.getTarget() != wonderlandGiantFrogEntity)
							continue;
						if (hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != this && this.getTarget() != wonderlandGiantFrogTadpoleEntity)
							continue;
						if (!hurt.level().isClientSide()) {
							if (hurt.isShiftKeyDown()) {
								float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
								double x = this.getX() + 7 * Mth.cos((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
								double y = this.getY() + 2.25f;
								double z = this.getZ() + 7 * Mth.sin((float) (this.getYRot() * (Math.PI / 180.0F) + angleYaw));
								hurt.teleportTo(x, y, z);
								hurt.setDeltaMovement(0, 0, 0);
							}
							else if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
								hurt.teleportTo(this.getX(), this.getY(0.1f), this.getZ());
								if (!hurt.level().isClientSide()) {
									hurt.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 20, 0, false, false), this);
									hurt.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2, 0, false, false), this);
								}
							}
						}
					}
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != this && this.getTarget() != wonderlandGiantFrogEntity)
							continue;
						if (hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != this && this.getTarget() != wonderlandGiantFrogTadpoleEntity)
							continue;
						if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
							AttackFind.attackBegin(this, hurt);
							DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.CRAMMING, this);
							boolean bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, 0.5f, 0f, false, 0f);
							if (!bl) {
								DamageSource damageSources2 = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MELEE, this);
								boolean bl2 = AttackFind.attackAfterCustomDamage(this, hurt, damageSources2, 0.5f, 0f, false, 0f);
								if (!bl2) {
									DamageSource damageSources3 = AttackFind.findDamageType(this, JerotesDamageTypes.CORROSION, this);
									AttackFind.attackAfterCustomDamage(this, hurt, damageSources3, 0.5f, 0f, false, 0f);
								} else {
									if (!hurt.level().isClientSide()) {
										hurt.addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 5, 0), this);
										hurt.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 2, false, false), this);
										hurt.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 30, 0, false, false), this);
									}
								}
							} else {
								if (!hurt.level().isClientSide()) {
									hurt.addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 5, 0), this);
									hurt.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 2, false, false), this);
									hurt.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 30, 0, false, false), this);
								}
							}
						}
					}
				}
			}
		}
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
			this.setFallTick(Math.min(240, this.getFallTick() + 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}

		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
			this.navigation = new FrogPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			if (this.isFakeBoss()) {
				this.moveControl = new GiantMoveControl(this);
			}
			else {
				this.moveControl = new MoveControl(this);
			}
			this.navigation = new FrogPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//
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
		//跳跃
		if (!this.level().isClientSide()) {
			this.setJumpTick(Math.max(0, this.getJumpTick() - 1));
			this.setPlayerJumpCooldown(Math.max(0, this.getPlayerJumpCooldown() - 1));
		}
		if (this.getJumpTick() <= 0 && !this.isVehicle()) {
			if (this.onGround() && this.horizontalCollision) {
				if (!this.level().isClientSide()) {
					this.setJumpTick(20);
					this.setAnimTick(24);
					this.setAnimationState("jump");
				}
			}
		}
		if (this.getJumpTick() == 15 && !this.isVehicle()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.1, 0.8, 0.1));
			this.playJumpSound();
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
						if (this.getDeltaMovement().y < 0.7f && this.getDeltaMovement().y > -0.7f) {
							float headXRot = player.getXRot();
							this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot/2 * -0.0035f, 0.0));
						}
					}
				}
			}
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
				return WonderlandGiantFrogEntity.this.inventory.getItem(p_149503_);
			}
			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					WonderlandGiantFrogEntity.this.inventory.setItem(p_149503_, p_149528_);
					WonderlandGiantFrogEntity.this.updateContainerEquipment();
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
		return false;
	}
	@Override
	public void equipSaddle(@Nullable SoundSource soundSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
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
		if (this.isControlledByLocalInstance()) {
			if (this.onGround()) {
				this.setIsJumping(false);
				if (this.playerJumpPendingScale > 0.0f && !this.isJumping()) {
					this.executeRidersJump(this.playerJumpPendingScale, vec3);
				}
				this.playerJumpPendingScale = 0.0f;
			}
		}
	}

	@Override
	protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
		if (this.onGround() && this.playerJumpPendingScale == 0.0f) {
			return Vec3.ZERO;
		}
		float f = player.xxa * 0.5f;
		float f2 = player.zza;
		if (f2 <= 0.0f) {
			f2 *= 0.25f;
		}
		return new Vec3(f, 0.0, f2);
	}
	@Override
	protected float getRiddenSpeed(Player player) {
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	}
	@Override
	public void onPlayerJump(int n) {
		if (!this.isSaddled()) {
			return;
		}
		if (n < 0) {
			n = 0;
		}
		if (!this.level().isClientSide()) {
			this.setPlayerJumpCooldown(40);
		}
		this.playerJumpPendingScale = n >= 90 ? 1.0f : 0.4f + 0.4f * (float)n / 90.0f;
	}
	@Override
	public boolean canJump() {
		return this.isSaddled() && this.getPlayerJumpCooldown() <= 0 && this.onGround();
	}
	@Override
	public void handleStartJump(int i) {
		if (!this.level().isClientSide()) {
			this.setPlayerJumpCooldown(40);
		}
		if (i >= 30 && i <= 100) {
			this.playJumpSound();
			if (!this.level().isClientSide()) {
				this.setJumpTick(20);
				this.setAnimTick(24);
				this.setAnimationState("jump");
			}
		}
	}
	protected void playJumpSound() {
		this.playSound(SoundEvents.FROG_LONG_JUMP, 2.0f, 1.0f);
	}
	@Override
	public void handleStopJump() {
	}
	protected boolean isJumping;
	protected float playerJumpPendingScale;
	public boolean isJumping() {
		return this.isJumping;
	}
	public void setIsJumping(boolean bl) {
		this.isJumping = bl;
	}
	protected void executeRidersJump(float f, Vec3 vec3) {
		double d = 0.45f * (double) f * (double) this.getBlockJumpFactor();
		double d2 = d * 2 + (double) this.getJumpBoostPower();
		Vec3 vec32 = this.getDeltaMovement();
		this.setDeltaMovement(vec32.x * (1 + f * 20), d2, vec32.z * (1 + f * 20));
		this.setIsJumping(true);
		this.hasImpulse = true;
		if (vec3.z > 0.0) {
			float f2 = Mth.sin(this.getYRot() * 0.017453292f);
			float f3 = Mth.cos(this.getYRot() * 0.017453292f);
			this.setDeltaMovement(-0.4f * f2 * f + vec32.x * (1 + f * 20), d2, 0.4f * f3 * f + vec32.z * (1 + f * 20));
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}

		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource) && this.getFrogType() == 4)
			amount = amount / freezeAmount;

		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.75));
		if (damageSource.getEntity() instanceof LivingEntity livingEntity && (damageSource.getDirectEntity() == livingEntity || damageSource.getDirectEntity() == null) && list.contains(livingEntity))  {
			amount /= 10;
		}
		else if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity && list.contains(livingEntity))  {
			amount /= 10;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float f, float f2, DamageSource damageSource) {
		//1.20.4↑//
		//LivingFallEvent event = ForgeEventFactory.onLivingFall(this, f, f2);
//		if (event.isCanceled()) {
//			return false;
//		} else {
//			f = event.getDistance();
//			f2 = event.getDamageMultiplier();
			if (f > 1.0F) {
				this.playSound(SoundEvents.FROG_AMBIENT, 0.4F, 1.0F);
			}

			int i = this.calculateFallDamage(f, f2);
			if (i <= 0) {
				return false;
			}
			else {
				this.hurt(damageSource, (float)i);
				this.playBlockFallSound();
				return true;
			}
		//1.20.4↑//
//		}
	}

	class FrogLookControl extends LookControl {
		FrogLookControl(Mob mob) {
			super(mob);
		}

	}
	static class FrogPathNavigation extends GroundPathNavigation {
		FrogPathNavigation(WonderlandGiantFrogEntity frog, Level level) {
			super(frog, level);
		}

		@Override
		public boolean canCutCorner(BlockPathTypes blockPathTypes) {
			return blockPathTypes != BlockPathTypes.WATER_BORDER && super.canCutCorner(blockPathTypes);
		}

		@Override
		protected PathFinder createPathFinder(int n) {
			this.nodeEvaluator = new FrogNodeEvaluator(true);
			this.nodeEvaluator.setCanPassDoors(true);
			return new PathFinder(this.nodeEvaluator, n);
		}
	}
	static class FrogNodeEvaluator extends AmphibiousNodeEvaluator {
		private final BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

		public FrogNodeEvaluator(boolean bl) {
			super(bl);
		}

		@Override
		public Node getStart() {
			if (!this.mob.isInWater()) {
				return super.getStart();
			}
			return this.getStartNode(new BlockPos(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY), Mth.floor(this.mob.getBoundingBox().minZ)));
		}

		@Override
		public BlockPathTypes getBlockPathType(BlockGetter blockGetter, int n, int n2, int n3) {
			this.belowPos.set(n, n2 - 1, n3);
			BlockState blockState = blockGetter.getBlockState(this.belowPos);
			if (blockState.is(BlockTags.FROG_PREFER_JUMP_TO)) {
				return BlockPathTypes.OPEN;
			}
			return super.getBlockPathType(blockGetter, n, n2, n3);
		}
	}

	@Nullable
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


	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType == MobSpawnType.SPAWN_EGG || mobSpawnType == MobSpawnType.COMMAND) {
			if (serverLevelAccessor.getRandom().nextFloat() > 0.8f) {
				//冬
				if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.HAS_OCEAN_RUIN_COLD)) {
					this.setFrogType(4);
				}
				//夏
				else if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)) {
					this.setFrogType(2);
				}
				//春
				else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.IS_MOUNTAIN)) {
					this.setFrogType(1);
				}
				//秋
				else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)
						|| serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
					this.setFrogType(3);
				}
			} else {
				this.setFrogType(Main.randomReach(serverLevelAccessor.getRandom(), 1, 4));
			}
		}
		else if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			//冬
			if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.HAS_OCEAN_RUIN_COLD)) {
				this.setFrogType(4);
			}
			//夏
			else if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)) {
				this.setFrogType(2);
			}
			//春
			else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.IS_MOUNTAIN)) {
				this.setFrogType(1);
			}
			//秋
			else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)
					|| serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
				this.setFrogType(3);
			}
		}
		else {
			this.setFrogType(Main.randomReach(serverLevelAccessor.getRandom(), 1, 4));
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}