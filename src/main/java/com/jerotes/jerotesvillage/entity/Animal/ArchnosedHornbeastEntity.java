package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotesvillage.goal.BrightLandBeastFloatGoal;
import com.jerotes.jerotesvillage.goal.BrightLandBeastMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.navigation.BrightLandBeastPathNavigation;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class ArchnosedHornbeastEntity extends BaseTamableAnimalEntity implements NeutralMob, StopLook, PlayerRideableJumping, BreakShieldEntity, Saddleable, ControlVehicleEntity, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState attack6AnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState quakeStartAnimationState = new AnimationState();
	public AnimationState quakeAnimationState = new AnimationState();
	public AnimationState quakeUseAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("2ed991e3-f1bd-439a-b3fb-0b2791549b50");
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_RUSH = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> QUAKE_TICK = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> QUAKE_USE_TICK = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_ANIM = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(ArchnosedHornbeastEntity.class, EntityDataSerializers.BYTE);
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

	public ArchnosedHornbeastEntity(EntityType<? extends ArchnosedHornbeastEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.6f);
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		this.moveControl = new GiantMoveControl(this);
		this.lookControl = new GiantLookControl(this, 1);
		this.createInventory();
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
		builder = builder.add(Attributes.MAX_HEALTH, 400);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 28);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3.0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new BrightLandBeastFloatGoal(this));
		if (!(this instanceof BrightLandBeastEntity)) {
			this.goalSelector.addGoal(1, new BrightLandBeastMeleeAttackGoal(ArchnosedHornbeastEntity.this, 1.2, true));
		}
		else {
			this.goalSelector.addGoal(1, new BrightLandBeastMeleeAttackGoal(ArchnosedHornbeastEntity.this,  1.15, true));
		}
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<ArchnosedHornbeastEntity>(this, false));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		if (walkSound == 1) {
			if (this.isAggressive()) {
				this.playRushSound();
			}
			else {
				this.playWalkSound();
			}
			this.trampUse();
			walkSound = 0;
		}
		else {
			walkSound = 1;
		}
	}
	public void playRushSound() {
		this.playSound(JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_RUSH, 1.0f, 1.0f);
	}
	public void playWalkSound() {
		this.playSound(JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_WALK, 1.0f, 1.0f);
	}
	public void trampUse() {
		if (this.getTarget() == null && !this.isVehicle()) {
			return;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5, 2.5));
		list.removeIf(livingEntity -> livingEntity == this || livingEntity.getY() >= this.getEyeY() || AttackFind.FindCanNotAttack(this, livingEntity));
		if (list.isEmpty()) return;
		for (LivingEntity shock : list) {
			if (shock == null) continue;
			if ((this.distanceToSqr(shock)) > Double.MAX_VALUE) continue;
			if (shock.getY() >= this.getEyeY()) continue;
			if (AttackFind.FindCanNotAttack(this, shock)) continue;
			if (this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), shock)) continue;
			if (shock instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != this && this.getTarget() != archnosedHornbeast) continue;
			if (!shock.onGround()) continue;
			if (!(Main.mobSizeSmall(shock) || Main.mobSizeMedium(shock))) continue;
			if (shock.getHealth() <= this.getHealth()/10f) {
				shock.hurt(this.damageSources().noAggroMobAttack(this), 4f);
			}
			else {
				AttackFind.attackBegin(this, shock);
				AttackFind.attackAfter(this, shock, 0, 0.2f, true, 4f);
			}
		}
	}
	@Override
	public int getMaxAirSupply() {
		return 6000;
	}
	@Nullable
	@Override
	public ArchnosedHornbeastEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		ArchnosedHornbeastEntity archnosedHornbeast = JerotesVillageEntityType.ARCHNOSED_HORNBEAST.get().create(serverLevel);
		if (archnosedHornbeast != null && (uUID = this.getOwnerUUID()) != null) {
			archnosedHornbeast.setOwnerUUID(uUID);
			archnosedHornbeast.setTame(true);
		}
		return archnosedHornbeast;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float scale = 1f;
		if (this instanceof BrightLandBeastEntity) {
			scale = 1.25f;
		}
		float height = 5.8f * scale;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return this.isTame() && this.isBaby() && super.canBeLeashed(player);
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7;
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -288000 : 0);
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		if (this instanceof BrightLandBeastEntity) {
			return new BrightLandBeastPathNavigation(this, level);
		}
		return super.createNavigation(level);
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
				if (this.isEffectiveAi() && this.isInWater()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					float forward = passenger.zza;
					float strafe = passenger.xxa/2;
					this.moveRelative(this.getSpeed(), new Vec3(strafe, 0, forward));
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.6));
					if (this.getTarget() == null) {
						this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
					}
				} else {
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
		if (this.isEffectiveAi() && (this.isInFluidType() && this instanceof BrightLandBeastEntity)) {
			this.moveRelative(this.getSpeed(), dir);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.6));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(dir);
		}
		if (this.getQuakeUseTick() > 10) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
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
		float scale = 1f;
		if (this instanceof BrightLandBeastEntity) {
			scale = 1.25f;
		}
		if (!this.isSaddled()) {
			return -0.1F * scale;
		}
		return 0.2F * scale;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected float getSinglePassengerXOffset() {
		return 0.0F;
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		float standScale = 0;
		if (this.getQuakeUseTick() <= 0 && this.getAttackUse() == 4 && this.getAttackTick() > 0) {
			standScale = 0.6f;
			if (this.getAttackTick() > 20) {
				//35-20
				standScale = 1 - ((this.getAttackTick() - 20) / 15f);
			}
			else if (this.getAttackTick() > 15){
				standScale = 1;
			}
			else if (this.getAttackTick() <= 15 && this.getAttackTick() > 12) {
				//15-12
				standScale = ((this.getAttackTick() - 12) / 3f);
			}
			else {
				standScale = 0;
			}
			standScale *= 0.6f;
		}
		else if (this.getQuakeUseTick() > 0) {
			standScale = 0;
			if (this.getQuakeUseTick() > 95) {
				//110-95
				standScale = 1 - ((this.getQuakeUseTick() - 95) / 15f);
			}
			else if (this.getQuakeUseTick() > 14){
				standScale = 1;
			}
			else if (this.getQuakeUseTick() <= 14 && this.getQuakeUseTick() > 12) {
				//20-0
				standScale = ((this.getQuakeUseTick() - 12) / 2f);
			}
			else {
				standScale = 0;
			}
		}
		float scale = 1f;
		if (this instanceof BrightLandBeastEntity) {
			scale = 1.25f;
		}
		if (!this.isSaddled()) {
			float f2;
			float f3;
			float f4 = 0f;
			f2 = 2f;
			f3 = 0f;
			f4 += 2.35f * standScale;
			f2 -= 2.25f * standScale;
			return new Vector3f(f3 * scale, entityDimensions.height + f4 * scale, f2 * scale);
		}

		float f2 = this.getSinglePassengerXOffset();
		float f3 = 0f;
		float f4 = 0f;
		int i = this.getPassengers().indexOf(entity);
		if (i == 0) {
			f2 = 2f;
			f3 = 0f;
			f4 += 2.35f * standScale;
			f2 -= 2.25f * standScale;
		}
		if (i == 1) {
			f2 = 1.5f;
			f3 = -1.25f;
			f4 += 1.875f * standScale;
			f2 -= 2.25f * standScale;
		}
		if (i == 2) {
			f2 = 1.5f;
			f3 = 1.25f;
			f4 += 1.875f * standScale;
			f2 -= 2.25f * standScale;
		}
		if (i == 3) {
			f2 = 0f;
			f3 = -1.25f;
			f4 += 2.0f * standScale;
			f2 -= 1.8f * standScale;
		}
		if (i == 4) {
			f2 = 0f;
			f3 = 1.25f;
			f4 += 2.0f * standScale;
			f2 -= 1.8f * standScale;
		}

		if (i == 5) {
			f2 = -1.25f;
			f3 = -1.25f;
			f4 = -0.1f;
			f4 += 0.8f * standScale;
			f2 -= 1.6f * standScale;
		}
		if (i == 6) {
			f2 = -1.25f;
			f3 = 1.25f;
			f4 = -0.1f;
			f4 += 0.8f * standScale;
			f2 -= 1.6f * standScale;
		}
		if (i == 7) {
			f2 = -2.5f;
			f3 = -1.25f;
			f4 = -0.1f;
			f4 += 0.3f * standScale;
			f2 -= 1.2f * standScale;
		}
		if (i == 8) {
			f2 = -2.5f;
			f3 = 1.25f;
			f4 = -0.1f;
			f4 += 0.1f * standScale;
			f2 -= 1.4f * standScale;
		}

		if (i == 9) {
			f2 = -1.75f;
			f4 = -0.1f;
			f4 += 0f * standScale;
			f2 -= 1.8f * standScale;
		}
		return new Vector3f(f3 * scale, entityDimensions.height + f4 * scale, f2 * scale);
	}
	//
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public int getMaxHeadYRot() {
		return 15;
	}
	@Override
	public int getMaxHeadXRot() {
		return 15;
	}
	@Override
	public int getHeadRotSpeed() {
		return 5;
	}
	@Override
	public void lookAt(Entity entity, float f, float f2) {
		if (!this.stopLookTime()) {
			super.lookAt(entity,f,f2);
		}
	}
	@Override
	public void setXRot(float f) {
		if (!this.stopLookTime()) {
			super.setXRot(f);
		}
	}
	@Override
	public void setYRot(float f) {
		if (!this.stopLookTime()) {
			super.setYRot(f);
		}
	}
	@Override
	public void setYBodyRot(float f) {
		if (!this.stopLookTime()) {
			super.setYBodyRot(f);
		}
	}
	public AABB getAttackBoundingBox() {
		float scale = 1f;
		if (this instanceof BrightLandBeastEntity) {
			scale = 1.25f;
		}
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
		return aabb1.inflate(2d * scale, 2d * scale, 2d * scale);
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
		int scale = 1;
		if (this instanceof BrightLandBeastEntity) {
			scale = 12;
		}
		return 100 * scale;
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
		if (!this.isSaddled()) {
			return 1;
		}
		return 10;
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
		return 4.0f;
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
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(25);
			if (attackRandom > 20) {
				this.setAttackTick(25);
				this.setAttackUse(1);
				this.setAnimTick(25);
				this.setAnimationState("attack1");
			} else if (attackRandom > 15) {
				this.setAttackTick(25);
				this.setAttackUse(1);
				this.setAnimTick(25);
				this.setAnimationState("attack2");
			} else if (attackRandom > 10) {
				this.setAttackTick(25);
				this.setAttackUse(2);
				this.setAnimTick(25);
				this.setAnimationState("attack3");
			} else if (attackRandom > 5) {
				this.setAttackTick(25);
				this.setAttackUse(2);
				this.setAnimTick(30);
				this.setAnimationState("attack4");
			} else {
				this.setAttackTick(25);
				this.setAttackUse(3);
				this.setAnimTick(30);
				this.setAnimationState("attack5");
			}
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		if (!this.level().isClientSide()) {
			this.setAttackTick(35);
			this.setAttackUse(4);
			this.setAnimTick(35);
			this.setAnimationState("attack6");
		}
	}
	@Override
	public boolean canPressMainJerotes() {
		int attack = this instanceof BrightLandBeastEntity ? -(int)(OtherMainConfig.BrightLandBeastMeleeAttackCooldown * 20) : -10;
		return this.getQuakeUseTick() <= 0 && this.getAttackTick() <= attack;
	}
	@Override
	public boolean canPressAddJerotes() {
		int attack = this instanceof BrightLandBeastEntity ? -(int)(OtherMainConfig.BrightLandBeastMeleeAttackCooldown * 20) : -10;
		return this.getQuakeUseTick() <= 0 && this.getAttackTick() <= attack;
	}
	@Override
	public boolean hasChest() {
		return this.getEntityData().get(DATA_ID_CHEST);
	}
	public void setChest(boolean p_30505_) {
		this.getEntityData().set(DATA_ID_CHEST, p_30505_);
	}
	public boolean isRush() {
		return this.getEntityData().get(IS_RUSH);
	}
	public void setRush(boolean bl) {
		this.getEntityData().set(IS_RUSH, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Rush Movement Speed bonus", 0.05, AttributeModifier.Operation.ADDITION));
		}
	}
	public int getRushAnim() {
		return this.getEntityData().get(RUSH_ANIM);
	}
	public void setRushAnim(int n) {
		this.getEntityData().set(RUSH_ANIM, n);
	}
	public int getInventorySize() {
		return 36 + 15;
	}
	public int getInventoryColumns() {
		return 10;
	}

	public double trampleTick;
	public int sitTick = 0;
	private int walkSound;
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
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (!(this instanceof BrightLandBeastEntity)) {
			if (bl) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(440.0);
				this.setHealth(440.0f);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0);
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400.0);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(28.0);
			}
		}
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else if (Objects.equals(animation, "attack4")){
			return 4;
		}
		else if (Objects.equals(animation, "attack5")){
			return 5;
		}
		else if (Objects.equals(animation, "attack6")){
			return 6;
		}
		else if (Objects.equals(animation, "quake")){
			return 7;
		}
		else if (Objects.equals(animation, "quakeStart")){
			return 8;
		}
		else if (Objects.equals(animation, "quakeUse")){
			return 9;
		}
		else if (Objects.equals(animation, "dead")){
			return 10;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
		list.add(this.attack4AnimationState);
		list.add(this.attack5AnimationState);
		list.add(this.attack6AnimationState);
		list.add(this.quakeAnimationState);
		list.add(this.quakeStartAnimationState);
		list.add(this.quakeUseAnimationState);
		list.add(this.deadAnimationState);
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
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setQuakeTick(int n){
		this.getEntityData().set(QUAKE_TICK, n);
	}
	public int getQuakeTick(){
		return this.getEntityData().get(QUAKE_TICK);
	}
	public void setQuakeUseTick(int n){
		this.getEntityData().set(QUAKE_USE_TICK, n);
	}
	public int getQuakeUseTick(){
		return this.getEntityData().get(QUAKE_USE_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("QuakeUseTick", this.getQuakeUseTick());
		compoundTag.putInt("QuakeTick", this.getQuakeTick());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putDouble("TrampleTick", this.trampleTick);
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putBoolean("ChestedHorse", this.hasChest());
		compoundTag.putBoolean("IsRush", this.isRush());
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
		compoundTag.putInt("RushAnim", this.getRushAnim());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setQuakeUseTick(compoundTag.getInt("QuakeUseTick"));
		this.setQuakeTick(compoundTag.getInt("QuakeTick"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.trampleTick = compoundTag.getDouble("TrampleTick");
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
		this.setRush(compoundTag.getBoolean("IsRush"));
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
		this.setRushAnim(compoundTag.getInt("RushAnim"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.updateContainerEquipment();
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(QUAKE_TICK, 600);
		this.getEntityData().define(QUAKE_USE_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(DATA_ID_CHEST, false);
		this.getEntityData().define(IS_RUSH, false);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(RUSH_ANIM, 0);
		this.getEntityData().define(ATTACK_USE, 0);
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
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.attack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack4AnimationState);
						break;
					case 5:
						this.attack5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack5AnimationState);
						break;
					case 6:
						this.attack6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack6AnimationState);
						break;
					case 7:
						this.quakeAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.quakeAnimationState);
						break;
					case 8:
						this.quakeStartAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.quakeStartAnimationState);
						break;
					case 9:
						this.quakeUseAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.quakeUseAnimationState);
						break;
					case 10:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}

		//注视
		if (this.getTarget() != null && !this.stopLookTime() && this.getAttackTick() <= 0) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
		}
		if (this.getTarget() != null && this.distanceTo(this.getTarget()) > this.getBbWidth() * 5) {
			if (!this.level().isClientSide()) {
				this.setRush(true);
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setRush(false);
			}
		}
		if (this.isAggressive() && this.isRush()) {
			if (!this.level().isClientSide()) {
				this.setRushAnim(Math.min(this.getRushAnim() + 1, 40));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setRushAnim(Math.max(this.getRushAnim() - 1, 0));
			}
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

		//技能-地震
		if (!this.level().isClientSide()) {
			this.setQuakeTick(Math.max(0, this.getQuakeTick() - 1));
			this.setQuakeUseTick(Math.max(0, this.getQuakeUseTick() - 1));
		}
		if (this.getQuakeUseTick() <= 90 && this.getQuakeUseTick() > 20 && !quakeAnimationState.isStarted()) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(80);
				this.setAnimationState("quake");
			}
		}
		if (this.getQuakeUseTick() == 20) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("quakeUse");
			}
		}
		if (this.getQuakeUseTick() == 10) {
			this.earthQuake();
			if (!this.level().isClientSide()) {
				this.setAttackTick(0);
			}
		}
		//近战攻击
		if (this.getAttackTick() == 10 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 15) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
			}
		}
		if (this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		int attack = this instanceof BrightLandBeastEntity ? -(int)(OtherMainConfig.BrightLandBeastMeleeAttackCooldown * 20) : -20;
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(attack, this.getAttackTick() - 1));
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		float breakLevel = this instanceof BrightLandBeastEntity ? 20f : 5f;
		double blockDestroyTickUse = this instanceof BrightLandBeastEntity ? OtherMainConfig.BrightLandBeastBreakBlockCooldown * 20 : 60;
		if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			boolean blockDestroy = Main.BlockDestroy(this, breakLevel);
			if (blockDestroy) {
				this.blockDestroyTick = blockDestroyTickUse;
			}
			if (!this.level().isClientSide) {
				if (!canBlockDestroy && this.onGround()) {
					this.jumpFromGround();
				}
			}
		}
	}

	@Override
	public void onPlayerJump(int i) {
	}
	@Override
	public int getJumpCooldown() {
		return getQuakeTick();
	}
	@Override
	public boolean canJump() {
		return getQuakeTick() <= 0 && this.getQuakeUseTick() <= 0 && this.getAttackTick() <= -20;
	}
	@Override
	public void handleStartJump(int i) {
		if (i > 80) {
			//触发地震
			if (getQuakeTick() <= 0 && this.getQuakeUseTick() <= 0 && this.getAttackTick() <= -20) {
				if (!this.level().isClientSide()) {
					this.setQuakeUseTick(110);
					this.setQuakeTick(640);
					this.setAnimTick(20);
					this.setAnimationState("quakeStart");
					this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3), this);
				}
			}
		}
	}
	@Override
	public void handleStopJump() {
	}

	public boolean earthQuake() {
		float damage = 20 + this.getHealth() / 20;
		OtherSpellFind.EarthQuake(this, (int) damage, 14);
		if (!this.isSilent() && this.isAlive()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_QUAKE, this.getSoundSource(), 10.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
		}
		this.setQuakeTick((int) (OtherMainConfig.BrightLandBeastQuakeCooldown * 20));
		return true;
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
						this.heal(12f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
				if (!this.hasChest() && itemStack.is(Items.CHEST)) {
					this.equipChest(player, itemStack);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
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
		if (!this.isFood(itemStack) || this.isAngry() || this instanceof BrightLandBeastEntity)
			return super.mobInteract(player, interactionHand);
		if (!this.hasEffect(JerotesMobEffects.ANESTHETIZED_HOLD.get()))
			return InteractionResult.PASS;
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(64) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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
	public boolean stopLookTime() {
		return this.getQuakeUseTick() <= 20 && this.getQuakeUseTick() > 0 || this.getAttackTick() > 0 && !this.isVehicle();
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		int attack = this instanceof BrightLandBeastEntity ? -(int)(OtherMainConfig.BrightLandBeastMeleeAttackCooldown * 20) : -10;
		if (this.getQuakeUseTick() <= 0 && this.getAttackTick() <= attack) {
			if (!this.level().isClientSide()) {
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 25) {
					this.setAttackTick(25);
					this.setAttackUse(1);
					this.setAnimTick(25);
					this.setAnimationState("attack1");
				} else if (attackRandom > 20) {
					this.setAttackTick(25);
					this.setAttackUse(1);
					this.setAnimTick(25);
					this.setAnimationState("attack2");
				} else if (attackRandom > 15) {
					this.setAttackTick(25);
					this.setAttackUse(2);
					this.setAnimTick(25);
					this.setAnimationState("attack3");
				} else if (attackRandom > 10) {
					this.setAttackTick(25);
					this.setAttackUse(2);
					this.setAnimTick(30);
					this.setAnimationState("attack4");
				} else if (attackRandom > 5) {
					this.setAttackTick(25);
					this.setAttackUse(3);
					this.setAnimTick(30);
					this.setAnimationState("attack5");
				} else {
					this.setAttackTick(35);
					this.setAttackUse(4);
					this.setAnimTick(35);
					this.setAnimationState("attack6");
				}
			}
			return true;
		}
		return false;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.ARCHNOSED_HORNBEAST_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		if (this.getAttackUse() == 1) {
			knockbackMulti = 1.25f;
		}
		if (this.getAttackUse() == 2) {
			damageMulti = 1.25f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.5f;
			knockbackMulti = 1.25f;
		}
		if (this.getAttackUse() == 4) {
			damageMulti = 1.5f;
			knockbackMulti = 1.05f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(1.25f));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && !this.getAttackBoundingBox().intersects(hurt.getBoundingBox()) && this.getAttackUse() != 2 && this.getAttackUse() != 4) continue;
			if (hurt instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != this && this.getTarget() != archnosedHornbeast)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl2) {
				//击飞
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.5;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-((this.getX() - hurt.getX()) * d3 / 2) * (0.7 + knockbackMulti / 3),
							-((this.getY() - hurt.getY()) * d3 / 2) * (0.7 + knockbackMulti / 3),
							-((this.getZ() - hurt.getZ()) * d3 / 2) * (0.7 + knockbackMulti / 3)
					));
				}
				if (this.getAttackUse() == 2 && this.getAttackUse() == 4) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add((7 - this.distanceTo(hurt)) / 10, (7 - this.distanceTo(hurt)) / 10, (7 - this.distanceTo(hurt)) / 10));
					}
				}
			}
		}
		//震动
		if (this.getAttackUse() == 2 || this.getAttackUse() == 4) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		//下落方块
		if (this.level() instanceof ServerLevel serverLevel) {
			AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 5, 0, Main.mobWidth(this)/2 + 5);
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				if (!Main.canSee(blockPos.getCenter(), this) && !(this.getAttackUse() == 2 || this.getAttackUse() == 4)) continue;
				if (!Main.canSeeAngle(this, blockPos.getCenter(), 40) && !(this.getAttackUse() == 2 || this.getAttackUse() == 4 || this.getAttackUse() == 3)) continue;
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		ParticleOptions particleOptions = ParticleTypes.CAMPFIRE_COSY_SMOKE;
		if (this.getAttackUse() == 4) {
			//范围粒子
			if (this.level() instanceof ServerLevel serverLevel) {
				double d = -Mth.sin(this.getYRot() * 0.017453292f);
				double d2 = Mth.cos(this.getYRot() * 0.017453292f);
				for (int i = 0; i < 160; i++) {
					double angle = (Math.PI * 2 * i) / 160;
					double radius = 8;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					// 生成烟雾粒子
					serverLevel.sendParticles(particleOptions, this.getX() + offsetX + d, this.getY() + 0.2f, this.getZ() + offsetZ + d2, 0, offsetX * 0.02, -0.04, offsetZ * 0.02, 1.0);
				}
				for (int i = 0; i < 100; i++) {
					double angle = (Math.PI * 2 * i) / 100;
					double radius = 5;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					// 生成烟雾粒子
					serverLevel.sendParticles(particleOptions, this.getX() + offsetX + d, this.getY() + 0.2f, this.getZ() + offsetZ + d2, 0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
				}
			}
		}
		return true;
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - (this instanceof BrightLandBeastEntity ? 40 : 20);
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
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
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
				return ArchnosedHornbeastEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					ArchnosedHornbeastEntity.this.inventory.setItem(p_149503_, p_149528_);
					ArchnosedHornbeastEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}


	public SlotAccess getSlot(int p_149479_) {
		return p_149479_ == 499 ? new SlotAccess() {
			public ItemStack get() {
				return ArchnosedHornbeastEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
			}
			public boolean set(ItemStack p_149485_) {
				if (p_149485_.isEmpty()) {
					if (ArchnosedHornbeastEntity.this.hasChest()) {
						ArchnosedHornbeastEntity.this.setChest(false);
						ArchnosedHornbeastEntity.this.createInventory();
					}
					return true;
				} else if (p_149485_.is(Items.CHEST)) {
					if (!ArchnosedHornbeastEntity.this.hasChest()) {
						ArchnosedHornbeastEntity.this.setChest(true);
						ArchnosedHornbeastEntity.this.createInventory();
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
		if (this.getQuakeTick() > 0) {
			return 0;
		}
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.6f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}

	@Override
	public void tickDeath() {
		super.tickDeath();
		if (deathTime > 0) {
			if (!this.level().isClientSide()) {
				this.setQuakeUseTick(0);
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