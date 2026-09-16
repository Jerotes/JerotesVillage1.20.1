package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.annotations.VisibleForTesting;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotesvillage.control.VirtualCaveCockroachFlyMoveControl;
import com.jerotes.jerotesvillage.goal.BrightMouseTemptGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotesvillage.goal.VirtualCaveCockroachLeapAtTargetAndLookGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class VirtualCaveCockroachEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, CatchEntity, BreakShieldEntity, Saddleable, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FLY_TICK = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState climbAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> CLIMB = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> FROM_CATCH = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(VirtualCaveCockroachEntity.class, EntityDataSerializers.INT);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public VirtualCaveCockroachEntity(EntityType<? extends VirtualCaveCockroachEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0.5f);
		this.createInventory();
		this.fixupDimensions();
	}
	public boolean isJerotesFlyingMob() {
		return canFlySize();
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
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
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
		builder = builder.add(Attributes.FLYING_SPEED, 2.275);
		builder = builder.add(Attributes.MAX_HEALTH, 25);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.325);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(2, new VirtualCaveCockroachLeapAtTargetAndLookGoal(this, 0.4F));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new BrightMouseTemptGoal(this, 0.9, false){
			public boolean canUse() {
				if (!((VirtualCaveCockroachEntity.this.getOwner() != null) && VirtualCaveCockroachEntity.this.getChangeType() != 1)) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (!((VirtualCaveCockroachEntity.this.getOwner() != null) && VirtualCaveCockroachEntity.this.getChangeType() != 1)) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<VirtualCaveCockroachEntity>(this, false));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.getSizeLike() > 2)
			return JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_AMBIENT_GIANT;
		return JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_AMBIENT_SMALL;
	}
	@Override
	public int getAmbientSoundInterval() {
		if (this.getSizeLike() > 3)
			return 400;
		return super.getAmbientSoundInterval();
	}
	@Override
	protected float getSoundVolume() {
		if (this.getSizeLike() > 2)
			return super.getSoundVolume();
		return 0.1f;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_DEATH;
	}
	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}
	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}
	@Override
	public boolean canMate(Animal animal) {
		if (animal instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity) {
			int otherSize = virtualCaveCockroachEntity.getSizeLike() - this.getSizeLike();
			if (otherSize > 1 || otherSize < -1) {
				return false;
			}
		}
		if (animal instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity && this.isTame() != virtualCaveCockroachEntity.isTame()) {
			return false;
		}
		return super.canMate(animal);
	}
	@Nullable
	@Override
	public VirtualCaveCockroachEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		VirtualCaveCockroachEntity virtualCaveCockroachEntity = JerotesVillageEntityType.VIRTUAL_CAVE_COCKROACH.get().create(serverLevel);
		if (virtualCaveCockroachEntity != null) {
			//体型
			float sizeFind = this.getRandom().nextFloat();
			float sizeOtherFind = this.getRandom().nextFloat();
			if (sizeFind > 2/3f) {
				//随机一边体型
				if (sizeOtherFind > 0.5f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
					virtualCaveCockroachEntity.setSize(virtualCaveCockroachEntity2.getSize(), true);
				}
				else {
					virtualCaveCockroachEntity.setSize(this.getSize(), true);
				}
			}
			else if (sizeFind > 1/3f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
				//体型融合
				virtualCaveCockroachEntity.setSize((int) ((this.getSize() + virtualCaveCockroachEntity2.getSize())/2f), true);
			}
			else {
				//大于其中一边
				//小于其中一边
				if (sizeOtherFind > 0.75f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
					virtualCaveCockroachEntity.setSize(virtualCaveCockroachEntity2.getSize() + Main.randomReach(this.getRandom(), 1, 2), true);
				}
				else if (sizeOtherFind > 0.5f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
					virtualCaveCockroachEntity.setSize(virtualCaveCockroachEntity2.getSize() - Main.randomReach(this.getRandom(), 1, 2), true);
					}
				else if (sizeOtherFind > 0.25f) {
					virtualCaveCockroachEntity.setSize(this.getSize() - Main.randomReach(this.getRandom(), 1, 2), true);
				}
				else {
					virtualCaveCockroachEntity.setSize(this.getSize() + Main.randomReach(this.getRandom(), 1, 2), true);
				}
			}

			//颜色
			float colorFind = this.getRandom().nextFloat();
			float colorOtherFind = this.getRandom().nextFloat();
			if (colorFind > 2/3f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
				int color = createColor(this.getColor(), virtualCaveCockroachEntity2.getColor(), true);
				virtualCaveCockroachEntity.setColor(color);
			}
			else if (colorFind > 1/3f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
				//颜色融合
				int color = createColor(this.getColor(), virtualCaveCockroachEntity2.getColor(), false);
				virtualCaveCockroachEntity.setColor(color);
			}
			else {
				//随机一边颜色
				if (colorOtherFind > 0.5f && ageableMob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity2) {
					virtualCaveCockroachEntity.setColor(virtualCaveCockroachEntity2.getColor());
				}
				else {
					virtualCaveCockroachEntity.setColor(this.getColor());
				}
			}

			if ((uUID = this.getOwnerUUID()) != null) {
				virtualCaveCockroachEntity.setOwnerUUID(uUID);
				virtualCaveCockroachEntity.setTame(true);
			}
		}
		return virtualCaveCockroachEntity;
	}
	public int createColor(int n, int n2, boolean bl) {
		if (!bl) {
			int f0 = (n >> 16 & 255);
			int f1 = (n >> 8 & 255);
			int f2 = (n & 255);
			int f02 = (n2 >> 16 & 255);
			int f12 = (n2 >> 8 & 255);
			int f22 = (n2 & 255);
			int f03 = (f0 + f02) / 2;
			int f13 = (f1 + f12) / 2;
			int f23 = (f2 + f22) / 2;
			return 65536 * f03 + 256 * f13 + f23;
		}
		else {
			int f0 = (n >> 16 & 255);
			int f1 = (n >> 8 & 255);
			int f2 = (n & 255);
			int f02 = (n2 >> 16 & 255);
			int f12 = (n2 >> 8 & 255);
			int f22 = (n2 & 255);
			int f03 = Math.min((f0 + f02) / 2, 255);
			int f13 = Math.min((f1 + f12) / 2, 255);
			int f23 = Math.min((f2 + f22) / 2, 255);
			//大于其中一边
			if (this.getRandom().nextFloat() > 1/3f) {
				f03 = Math.min((f0 + f02) / 2 + Main.randomReach(this.getRandom(), -10, 10), 255);
			}
			if (this.getRandom().nextFloat() > 1/3f) {
				f13 = Math.min((f1 + f12) / 2 + Main.randomReach(this.getRandom(), -10, 10), 255);
			}
			else {
				f23 = Math.min((f2 + f22) / 2 + Main.randomReach(this.getRandom(), -10, 10), 255);
			}
			return 65536 * f03 + 256 * f13 + f23;
		}
	}
	@Override
	public void spawnChildFromBreeding(ServerLevel serverLevel, Animal animal) {
		for (int i = 0; i < this.random.nextInt(1 , 4); ++i) {
			AgeableMob ageableMob = this.getBreedOffspring(serverLevel, animal);
			if (ageableMob == null) {
				return;
			}
			ageableMob.setBaby(true);
			ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
			this.finalizeSpawnChildFromBreeding(serverLevel, animal, ageableMob);
			serverLevel.addFreshEntityWithPassengers(ageableMob);
		}
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = this.getSize() * 0.06f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isPushedByFluid() {
		if (this.getSizeLike() > 3 && !this.isBaby())
			return false;
		return super.isPushable();
	}
	@Override
	public boolean isPushable() {
		if (this.getSizeLike() > 3 && !this.isBaby())
			return false;
		return super.isPushable();
	}
	@Override
	public boolean canBeLeashed(Player player) {
		if (this.getSizeLike() > 3 && !this.isBaby())
			return false;
		return super.canBeLeashed(player);
	}
	@Override
	public boolean canDisableShield() {
		return this.getSizeLike() > 3;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		if (this.getSizeLike() > 2) {
			return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7;
		}
		return super.canSpawnSprintParticle();
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item.isEdible();
	}
	@Override
	protected void onFlap() {
		this.playSound(JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_FLY, 0.15f, 1.0f);
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -10000 * this.getSizeLike() : 0);
	}
	public boolean isFlying() {
		if (!this.canFlySize() || this.isBaby())
			return false;
		if (Main.isInFluid(this) && this.getTarget() == null || this.onGround() && this.getTarget() == null)
			return false;
		if (this.isClimbing() && this.getTarget() == null)
			return false;
		if (this.isInSittingPose())
			return false;
		return this.getFlyTick() > 300 || this.getControllingPassenger() instanceof Player || this.getTarget() != null;
	}
	@Override
	public @NotNull PathNavigation getNavigation() {
		Entity entity = this.getControlledVehicle();
		if (entity instanceof Mob) {
			return super.getNavigation();
		} else {
			if (!this.canGiantNavigationSize()) {
				if (this.canClimbSize()) {
					return super.getNavigation();
				}
				return new GroundPathNavigation(this, this.level());
			}
			return new GroundPathNavigation(this, this.level());
		}
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
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
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if (!this.onGround() && !Main.isInFluid(this)) {
					this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));
				}
				float forward = passenger.zza;
				float strafe = passenger.xxa/2;
				super.travel(new Vec3(strafe, 0, forward));
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
		super.travel(dir);
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
		return -0.4f;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected float getSinglePassengerXOffset() {
		return 0.0F;
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		if (!this.isSaddled()) {
			return new Vector3f(0.0F, entityDimensions.height, 1.0F);
		}
		float f2 = this.getSinglePassengerXOffset();
		float f3 = 0f;
		float f4 = 0.055F * this.getSize();
		int i = this.getPassengers().indexOf(entity);
		if (i == 0) {
			f2 = 0.04f;
			f3 = 0f;
		}
		if (i == 1) {
			f2 = 0.035f;
			f3 = 0f;
		}
		return new Vector3f(f3 * this.getSize(), f4, f2 * this.getSize());
	}
	//
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
		double d = this.getSizeLike() * 0.02d;
		return aabb1.inflate(d, d, d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	protected void playChestEquipsSound() {
		this.playSound(JerotesSoundEvents.USE_CHEST, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
	}
	@Override
	public int getShieldBreakStrength() {
		if (this.getSizeLike() > 3)
			return 100;
		return 0;
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
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}
	protected int getMaxPassengers() {
		if (this.getSizeLike() > 3) {
			if (!this.isSaddled()) {
				return 1;
			}
			return 2;
		}
		return 1;
	}
	public boolean OwnerCanOrderAttack() {
		return this.getSizeLike() > 3 || this.getSizeLike() > 2 && !this.isBaby();
	}
	@Override
	public boolean hasChest() {
		return this.getEntityData().get(DATA_ID_CHEST);
	}
	public void setChest(boolean p_30505_) {
		this.getEntityData().set(DATA_ID_CHEST, p_30505_);
	}
	public int getInventorySize() {
		return 27;
	}
	public int getInventoryColumns() {
		return 5;
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
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(6);
			this.setAnimationState("attack");
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_ATTACK, 1.0f, 1.0f);
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(6);
			this.setAnimationState("attack");
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_ATTACK, 1.0f, 1.0f);
		}
	}
	@Override
	public void individualAttackJerotes(LivingEntity livingEntity) {
		AttackFind.attackBegin(this, livingEntity);
		boolean bl = AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
		if (bl) {
			if (this.getSizeLike() > 3) {
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2, 1.2, 2));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, livingEntity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (hurt instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity && virtualCaveCockroachEntity.getTarget() != this)
						continue;
					AttackFind.attackBegin(this, hurt);
					AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
				}
				//横扫效果
				Main.sweepAttack(this);
			}
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

	public int sitTick = 0;
	public double blockDestroyTick;
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
	public boolean isClimbing() {
		return (this.entityData.get(CLIMB) & 1) != 0;
	}
	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(CLIMB);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(CLIMB, by);
	}
	public void setColor(int n) {
		this.getEntityData().set(COLOR, n);
	}
	public int getColor() {
		return this.getEntityData().get(COLOR);
	}
	public void setFlyTick(int n) {
		this.getEntityData().set(FLY_TICK, n);
	}
	public int getFlyTick() {
		return this.getEntityData().get(FLY_TICK);
	}
	@Override
	public ItemStack getCatchItemStack() {
		return new ItemStack(JerotesVillageItems.CAPTURED_VIRTUAL_CAVE_COCKROACH.get());
	}
	@Override
	public SoundEvent getPickupSound() {
		return JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_AMBIENT_SMALL;
	}
	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromCatch();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromCatch() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}
	@Override
	public boolean fromCatch() {
		return this.entityData.get(FROM_CATCH);
	}
	@Override
	public void setFromCatch(boolean bl) {
		this.entityData.set(FROM_CATCH, bl);
	}
	@Override
	public void setTame(boolean bl) {
		int f2 = this.getSize();
		this.entityData.set(ID_SIZE, f2);

		float health = Math.max((int)(f2 * f2 * 0.25), 1.0f);
		float healthTame = health * 1.25f;
		float attack = f2 * 0.5f;
		float attackTame = attack * 1.25f;
		this.healthGet = health;
		this.healthTameGet = healthTame;
		this.attackGet = attack;
		this.attackTameGet = attackTame;

		boolean tame = this.isTame();
		super.setTame(bl);
		if (bl) {
			if (!tame) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.healthTameGet);
				this.setHealth((float) this.healthTameGet);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.attackTameGet);
			}
		} else {
			if (tame) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.healthGet);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.attackGet);
			}
		}
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(0.1f * (float)this.getSize());
	}
	//体型
	@VisibleForTesting
	public void setSize(int f, boolean bl) {
		int f2 = (int) Mth.clamp(f, 1, OtherMainConfig.VirtualCaveCockroachMaxSize);
		this.entityData.set(ID_SIZE, f2);

		float health = Math.max((int)(f2 * f2 * 0.25), 1.0f);
		float healthTame = health * 1.25f;
		float attack = f2 * 0.5f;
		float attackTame = attack * 1.25f;
		this.healthGet = health;
		this.healthTameGet = healthTame;
		this.attackGet = attack;
		this.attackTameGet = attackTame;

		if (this.isTame()) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healthTameGet);
			if (bl) {
				this.setHealth((float) healthTameGet);
			}
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackTameGet);
		}
		else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healthGet);
			if (bl) {
				this.setHealth((float) healthGet);
			}
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackGet);
		}
		if (f2 <= 20) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25 + (f2 * 0.0075f));
		}
		else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25 + (20 * 0.0075f) + ((f2 - 20) * 0.0015f));
		}
		if (f2 <= 20) {
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0.25 + (f2 * 0.0525f));
		}
		else {
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0.25 + (20 * 0.0525f) + ((f2 - 20) * 0.0105f));
		}
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Math.min(((int)f2 * 0.025f), 1));
		this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(Math.min(((int)f2 * 0.1f), 5));
		this.getAttribute(Attributes.ARMOR).setBaseValue(Math.min(((int)f2 * 0.3f), 16));

		if (f2 < 10) {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 8.0F);
			this.moveControl = new VirtualCaveCockroachFlyMoveControl(this, 10);
		}
		else if (f2 < 20) {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
			this.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 8.0F);
			this.moveControl = new VirtualCaveCockroachFlyMoveControl(this, 10);
		}
		else if (f2 < 40) {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
			this.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 8.0F);
			this.moveControl = new VirtualCaveCockroachFlyMoveControl(this, 10);
		}
		else {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
			this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
			this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
			this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
			this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
			this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
			this.moveControl = new GiantMoveControl(this);
		}
		this.reapplyPosition();
		this.refreshDimensions();
		this.setMaxUpStep(0.5f + this.getSize() * 0.05f);
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}
	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
	}
	public int getSize() {
		return this.entityData.get(ID_SIZE);
	}
	//体型类型
	public int getSizeLike() {
		float size = this.getSize();
		if (size < 10) {
			return 1;
		}
		else if (size < 20) {
			return 2;
		}
		else if (size < 40) {
			return 3;
		}
		else {
			return 4;
		}
	}
	//能否爬墙
	public boolean canClimbSize() {
		return this.getSizeLike() < 4;
	}
	//能否驯服
	public boolean canTameSize() {
		return this.getSizeLike() > 1;
	}
	//能否飞行
	public boolean canFlySize() {
		return this.getSizeLike() < 4;
	}
	//能否骑乘
	public boolean canRideSize() {
		return this.getSizeLike() > 1;
	}
	//能否装箱
	public boolean canChestSize() {
		return this.getSizeLike() > 3;
	}
	//能否破坏方块
	public boolean canBreakBlockSize() {
		return this.getSizeLike() > 3;
	}
	//能否巨型寻路
	public boolean canGiantNavigationSize() {
		return this.getSizeLike() > 3;
	}
	//
	public double attackGet = 10 * 0.5f;
	public double attackTameGet = 10 * 0.5f * 1.25f;
	public double healthGet = Math.max((int)(10 * 10 * 0.25), 1);
	public double healthTameGet = Math.max((int)(10 * 10 * 0.25), 1) * 1.25f;
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
		compoundTag.putBoolean("FromCatch", this.fromCatch());
		compoundTag.putInt("Color", this.getColor());
		compoundTag.putInt("Size", this.getSize());
		compoundTag.putInt("FlyTick", this.getSizeLike());
		compoundTag.putDouble("AttackGet", this.attackGet);
		compoundTag.putDouble("AttackTameGet", this.attackTameGet);
		compoundTag.putDouble("HealthGet", this.healthGet);
		compoundTag.putDouble("HealthTameGet", this.healthTameGet);
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putBoolean("ChestedHorse", this.hasChest());
		if (this.hasChest()) {
			ListTag $$1 = new ListTag();
			for(int $$2 = 2; $$2 < this.inventory.getContainerSize(); ++$$2) {
				ItemStack $$3 = this.inventory.getItem($$2);
				if (!$$3.isEmpty()) {
					CompoundTag $$4 = new CompoundTag();
					$$4.putByte("Slot", (byte)$$2);
					$$3.save($$4);
					$$1.add($$4);
				}
			}
			compoundTag.put("Items", $$1);
		}
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		this.setSize(compoundTag.getInt("Size"), false);
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setFromCatch(compoundTag.getBoolean("FromCatch"));
		this.setColor(compoundTag.getInt("Color"));
		this.setFlyTick(compoundTag.getInt("FlyTick"));
		this.attackGet = compoundTag.getDouble("AttackGet");
		this.attackTameGet = compoundTag.getDouble("AttackTameGet");
		this.healthGet = compoundTag.getDouble("HealthGet");
		this.healthTameGet = compoundTag.getDouble("HealthTameGet");
		this.sitTick = compoundTag.getInt("SitTick");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.setChest(compoundTag.getBoolean("ChestedHorse"));
		this.createInventory();
		if (this.hasChest()) {
			ListTag $$1 = compoundTag.getList("Items", 10);
			for(int $$2 = 0; $$2 < $$1.size(); ++$$2) {
				CompoundTag $$3 = $$1.getCompound($$2);
				int $$4 = $$3.getByte("Slot") & 255;
				if ($$4 >= 2 && $$4 < this.inventory.getContainerSize()) {
					this.inventory.setItem($$4, ItemStack.of($$3));
				}
			}
		}
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.updateContainerEquipment();
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(COLOR, 1);
		this.getEntityData().define(FLY_TICK, 400);
		this.getEntityData().define(FROM_CATCH, false);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(CLIMB, (byte)0);
		this.getEntityData().define(DATA_ID_CHEST, false);
		this.getEntityData().define(ID_SIZE, 10);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ID_SIZE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
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
	public void saveToCatchTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void loadFromCatchTag(CompoundTag compoundTag) {
		ItemStack itemStack;
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
		this.updateContainerEquipment();
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.canClimbSize()) {
			if (!this.level().isClientSide) {
				this.setClimbing(this.horizontalCollision);
			}
		}
		else if (this.isClimbing()) {
			if (!this.level().isClientSide) {
				this.setClimbing(false);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
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
		if (this.isClimbing() && this.canClimbSize()) {
			this.climbAnimationState.start(this.tickCount);
		}
		else {
			this.climbAnimationState.stop();
		}

		if (this.getFlyTick() > 0) {
			this.setFlyTick(this.getFlyTick()-1);
		}
		else {
			this.setFlyTick(400);
		}

		if (this.canFlySize() && this.getControllingPassenger() instanceof Player player) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
			if (!(this.xOld == this.getX() && this.zOld == this.getZ())) {
				double d = Math.abs(this.getX() - this.xOld);
				double d2 = Math.abs(this.getZ() - this.zOld);
				if (d >= 0.01 || d2 >= 0.01) {
					if (this.getDeltaMovement().y < 0.6f && this.getDeltaMovement().y > -0.6f) {
						float headXRot = player.getXRot();
						this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot / 2 * -0.0025f, 0.0));
					}
				}
				else {
					if (this.getDeltaMovement().y > -0.5f) {
						this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05f, 0.0));
					}
				}
			}
			else {
				if (this.getDeltaMovement().y > -0.5f) {
					this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05f, 0.0));
				}
			}
		}

		//视角摇晃
		if (this.walkAnimation.isMoving() && this.onGround() && this.getHealth() > this.getMaxHealth() / 4 && !this.isBaby() && this.canGiantNavigationSize()) {
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
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}

		if (this.canBreakBlockSize()) {
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = 60;
				}
				if (!this.level().isClientSide) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (!this.canTameSize()) {
			return CatchEntity.catchMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
		}
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
						this.heal(this.getSizeLike() * 4);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
				if (!this.hasChest() && itemStack.is(Items.CHEST) && this.canChestSize()) {
					this.equipChest(player, itemStack);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				//骑乘
				if (!this.isFood(itemStack) && this.canRideSize() && this.isSaddled() && AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), player) && !player.isSecondaryUseActive() && !isOrderedToSit()) {
					if (!this.level().isClientSide) {
						player.startRiding(this);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				if (this.canRideSize() || this.canChestSize() || this.isSaddled() || this.hasChest()) {
					this.openCustomInventoryScreen(player);
				}
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
		if (!this.isFood(itemStack) || this.isAngry() || !this.canTameSize())
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(24 * this.getSizeLike()) == 1 && !ForgeEventFactory.onAnimalTame(this, player)) {
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

	private void equipChest(Player player, ItemStack itemStack) {
		this.setChest(true);
		this.playChestEquipsSound();
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		this.createInventory();
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
			this.setAnimTick(6);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.VIRTUAL_CAVE_COCKROACH_ATTACK, 1.0f, 1.0f);
			}
			if (this.getSizeLike() > 3) {
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2, 1.2, 2));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (hurt instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity && virtualCaveCockroachEntity.getTarget() != this)
						continue;
					AttackFind.attackBegin(this, hurt);
					AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
				}
				//横扫效果
				Main.sweepAttack(this);
			}
		}
		return bl;
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		if (this.getSizeLike() > 3) {
			return super.calculateFallDamage(f, f2) - 40;
		}
		if (this.getSizeLike() > 2) {
			return super.calculateFallDamage(f, f2) - 30;
		}
		return super.calculateFallDamage(f, f2) - 20;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.getSizeLike() > 2) {
			if (damageSource.is(DamageTypes.CACTUS)
					|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
				return true;
		}
		if (damageSource.is(DamageTypeTags.IS_FALL) && this.canFlySize())
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
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
			boolean flag = false;
			int i = this.calculateFallDamage(f, f2);
			if (i > 0) {
				this.playSound(this.getFallDamageSound(i), 1.0F, 1.0F);
				this.playBlockFallSound();
				this.hurt(damageSource, (float)i);
				return true;
			} else {
				return flag;
			}
		//1.20.4↑//
//		}
	}
	private SoundEvent getFallDamageSound(int p_21313_) {
		return p_21313_ > 4 ? this.getFallSounds().big() : this.getFallSounds().small();
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (this.getSizeLike() > 3) {
			if (livingEntity instanceof Player player) {
				player.disableShield(true);
			}
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (this.getSizeLike() > 3) {
			if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
				return false;
			}
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame() && this.getSizeLike() > 2;
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
		if (this.hasChest()) {
			if (!this.level().isClientSide) {
				this.spawnAtLocation(Blocks.CHEST);
			}
			this.setChest(false);
		}
	}
	private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
		return new SlotAccess() {
			public ItemStack get() {
				return VirtualCaveCockroachEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					VirtualCaveCockroachEntity.this.inventory.setItem(p_149503_, p_149528_);
					VirtualCaveCockroachEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}


	public SlotAccess getSlot(int p_149479_) {
		return p_149479_ == 499 ? new SlotAccess() {
			public ItemStack get() {
				return VirtualCaveCockroachEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
			}
			public boolean set(ItemStack p_149485_) {
				if (p_149485_.isEmpty()) {
					if (VirtualCaveCockroachEntity.this.hasChest()) {
						VirtualCaveCockroachEntity.this.setChest(false);
						VirtualCaveCockroachEntity.this.createInventory();
					}
					return true;
				} else if (p_149485_.is(Items.CHEST)) {
					if (!VirtualCaveCockroachEntity.this.hasChest()) {
						VirtualCaveCockroachEntity.this.setChest(true);
						VirtualCaveCockroachEntity.this.createInventory();
					}
					return true;
				} else {
					return false;
				}
			}
		} : this.getSlots(p_149479_);
	}
	public SlotAccess getSlots(int p_149514_) {
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			this.setSize(randomSource.nextInt(4,6), true);
			this.setColor(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		super.tickDeath();
		if (this.deathTime == 5) {
			for (int i = 0; i < this.getSize() * 5; ++i) {
				double d = this.random.nextGaussian() * 0.02;
				double d2 = this.random.nextGaussian() * 0.02;
				double d3 = this.random.nextGaussian() * 0.02;
				this.level().addAlwaysVisibleParticle(ParticleTypes.END_ROD, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, d2, d3);
			}
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
}




