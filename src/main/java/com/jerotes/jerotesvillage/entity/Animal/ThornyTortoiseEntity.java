package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.ThornyTortoiseBreedGoal;
import com.jerotes.jerotesvillage.goal.ThornyTortoiseMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class ThornyTortoiseEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, Saddleable, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState hideAnimationState = new AnimationState();
	public AnimationState unHideAnimationState = new AnimationState();
	public AnimationState inHideAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/sea_grass_foods"))
	);
	private static final Ingredient FOOD_ITEMSS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);
	private static final EntityDataAccessor<Boolean> IS_WITHIN = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ThornyTortoiseEntity.class, EntityDataSerializers.INT);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public ThornyTortoiseEntity(EntityType<? extends ThornyTortoiseEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		this.createInventory();
		this.fixupDimensions();
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

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new ThornyTortoiseMeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(1, new JerotesLeapAtTargetAndLookGoal(this, 0.4f));
		this.goalSelector.addGoal(2, new ThornyTortoiseBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMSS, false));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new BreathAirGoal(this));
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<ThornyTortoiseEntity>(this, false));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		return builder;
	}

	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(75.0);
			this.setHealth(75.0f);
			this.getAttribute(Attributes.ARMOR).setBaseValue(24.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60);
			this.getAttribute(Attributes.ARMOR).setBaseValue(20.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		if (this.isInWater() && this.onGround() && !this.isBaby()) {
			return JerotesVillageSoundEvents.THORNY_TORTOISE_AMBIENT;
		}
		return super.getAmbientSound();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.THORNY_TORTOISE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.THORNY_TORTOISE_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.THORNY_TORTOISE_WALK, 0.15f, 1.0f);
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
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
				this.playSound(JerotesVillageSoundEvents.THORNY_TORTOISE_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}
	@Override
	public float getScale() {
		return this.isBaby() ? 0.25f : 1.0f;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack) || FOOD_ITEMSS.test(itemStack);
	}

	@Nullable
	@Override
	public ThornyTortoiseEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		ThornyTortoiseEntity thornyTortoise = JerotesVillageEntityType.THORNY_TORTOISE.get().create(serverLevel);
		if (thornyTortoise != null && (uUID = this.getOwnerUUID()) != null) {
			thornyTortoise.setOwnerUUID(uUID);
			thornyTortoise.setTame(true);
		}
		return thornyTortoise;
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(Items.SCUTE, 3);
		}
	}

	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -72000 : 0);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.8f;
		if (this.isWithin())
			height = 0.75f;
		if (this.isBaby()) {
			return height / 4;
		}
		return height;
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		float size = this.isWithin() ? 0.75f : 1f;
		return super.getDimensions(pose).scale(1f, size);
	}

	@Override
	public boolean isPushable() {
		return !this.isVehicle() && !this.isWithin();
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isWithin() && this.isAlive() || super.canBeCollidedWith();
	}
	//
	protected void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
		Vec3 vec3 = this.getPassengerRidingPosition(entity);
		moveFunction.accept(entity, vec3.x, vec3.y + getMyRidingOffset(this), vec3.z);
	}
	public float getMyRidingOffset(Entity entity) {
		return this.ridingOffset(entity) * this.getScale();
	}
	protected float ridingOffset(Entity entity) {
		return -0.5F;
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
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;

	public void aiStep() {
		super.aiStep();
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
			this.navigation = new AmphibiousPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			this.moveControl = new MoveControl(this);
			this.navigation = new GroundPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//
		if (!this.level().isClientSide()) {
			this.setWithin((this.getHealth() < (this.getMaxHealth()/8) && !this.isOnFire()) || this.isInSittingPose());
		}
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		if (this.isWithin()){
			if (this.random.nextInt(600) == 1 && this.deathTime == 0) {
				this.heal(3.0f);
			}
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4, false, false), this);
				this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 2, false, false), this);
			}
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}

		if (this.isWithin() && sitTick <= 0){
			this.sitTick = 40;
		}
		if (!this.isWithin() && sitTick > 0){
			this.unHideAnimationState.start(this.tickCount);
			this.sitTick = 0;
			this.inHideAnimationState.stop();
			this.hideAnimationState.stop();
		}

		if (this.sitTick == 40){
			this.hideAnimationState.start(this.tickCount);
		}
		if (this.sitTick == 30){
			this.hideAnimationState.stop();
			this.inHideAnimationState.start(this.tickCount);
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
						if (this.getDeltaMovement().y < 0.6f && this.getDeltaMovement().y > -0.6f) {
							float headXRot = player.getXRot();
							this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot / 2 * -0.0035f, 0.0));
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.isWithin()) {
			if (damageSource.is(DamageTypes.CACTUS)
					|| damageSource.is(DamageTypeTags.IS_FALL)
					|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
				return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.isWithin()) {
			if (amount <= 40 && damageSource.is(DamageTypes.ARROW))
				return false;
			if (!AttackFind.isFireDamage(damageSource))
				amount = amount/4;
			Entity entity = damageSource.getEntity();
			if (entity instanceof LivingEntity livingEntity && !AttackFind.FindCanNotAttack(this, livingEntity) && this.distanceTo(livingEntity) < 8) {
				float damages = Math.min(40, amount/3);
				damages = Math.max(2, damages);
				if (AttackFind.isMeleeDamage(damageSource)) {
					DamageSource damageSource2 = this.level().damageSources().mobAttack(this);
					entity.hurt(damageSource2, damages);
				}
			}
			boolean bl = super.hurt(damageSource, amount / 4);
			if (bl) {
				if (this.getHealth() < this.getMaxHealth()/3) {
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
		else {
			boolean bl = super.hurt(damageSource, amount);
			if (bl) {
				if (this.getHealth() < this.getMaxHealth()/3) {
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
	}@Override
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
			this.playSound(JerotesVillageSoundEvents.THORNY_TORTOISE_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.THORNY_TORTOISE_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(10);
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

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity;
		if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}

	private int sitTick = 0;
	public boolean isWithin() {
		return this.getEntityData().get(IS_WITHIN);
	}
	public void setWithin(boolean bl) {
		this.getEntityData().set(IS_WITHIN, bl);
		refreshDimensions();
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
		compoundTag.putBoolean("Within", this.isWithin());
		compoundTag.putInt("SitTick", this.sitTick);
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setWithin(compoundTag.getBoolean("Within"));
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
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.updateContainerEquipment();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(IS_WITHIN, false);
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
		if (IS_WITHIN.equals(entityDataAccessor)) {
			refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
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
	}
	private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
		return new SlotAccess() {
			public ItemStack get() {
				return ThornyTortoiseEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					ThornyTortoiseEntity.this.inventory.setItem(p_149503_, p_149528_);
					ThornyTortoiseEntity.this.updateContainerEquipment();
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

		if (this.isWithin()) {
			player.stopRiding();
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
					this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
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
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(dir);
		}
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




