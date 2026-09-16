package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class ScalyBearEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, Saddleable, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState danceAnimationState = new AnimationState();
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("e853c6e2-1f42-4935-abba-1c493e6c210a");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("fd71c48c-d096-4768-b927-b801148e4019");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("a0cdafe8-4617-46b0-b30d-27162dbdb421");
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> IS_SNOW = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_SCALE = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ScalyBearEntity.class, EntityDataSerializers.INT);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public ScalyBearEntity(EntityType<? extends ScalyBearEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		this.createInventory();
		this.fixupDimensions();
	}
	@Override
	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	@Override
	public boolean isWarBeastArmor() {
		return false;
	}
	@Override
	public boolean isGiantBeastArmor() {
		return true;
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
		this.setArmorEquipment(this.inventory.getItem(1));
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public void containerChanged(Container p_30548_) {
		ItemStack itemStack = this.getArmor();
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
		}
		ItemStack itemStack2 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemStack2) && itemStack != itemStack2) {
			this.playSound(JerotesSoundEvents.USE_BEAST_ARMOR, 0.5f, 1.0f);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 11);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.6, true));
		this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.3));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new ScalyBearHurtByTargetGoal());
		this.targetSelector.addGoal(2, new ScalyBearAttackPlayersGoal());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<ScalyBearEntity>(this, false));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	class ScalyBearHurtByTargetGoal extends HurtByTargetGoal {
		public ScalyBearHurtByTargetGoal() {
			super(ScalyBearEntity.this, new Class[0]);
		}
		@Override
		public void start() {
			super.start();
			if (ScalyBearEntity.this.isBaby()) {
				this.alertOthers();
				this.stop();
			}
		}
		@Override
		protected void alertOther(Mob mob, LivingEntity livingEntity) {
			if (mob instanceof ScalyBearEntity && !mob.isBaby()) {
				super.alertOther(mob, livingEntity);
			}
		}
	}

	class ScalyBearAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {

		public ScalyBearAttackPlayersGoal() {
			super(ScalyBearEntity.this, Player.class, 20, true, true, null);
		}

		@Override
		public boolean canUse() {
			if (ScalyBearEntity.this.isBaby()) {
				return false;
			}
			if (super.canUse()) {
				List<ScalyBearEntity> list = ScalyBearEntity.this.level().getEntitiesOfClass(ScalyBearEntity.class, ScalyBearEntity.this.getBoundingBox().inflate(8.0, 4.0, 8.0));
				for (ScalyBearEntity bear : list) {
					if (!bear.isBaby() || bear.isTame()) continue;
					return true;
				}
			}
			return false;
		}

		@Override
		protected double getFollowDistance() {
			return super.getFollowDistance() * 0.5;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.isBaby()) {
			return JerotesVillageSoundEvents.SCALY_BEAR_BABY_AMBIENT;
		}
		return JerotesVillageSoundEvents.SCALY_BEAR_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SCALY_BEAR_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SCALY_BEAR_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.SCALY_BEAR_WALK, 0.15f, 1.0f);
	}
	@Override
	public int getAmbientSoundInterval() {
		return 600;
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
		return -0.35F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//
	@Override
	public boolean isFood(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item.isEdible();
	}
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		ScalyBearEntity scalyBear = JerotesVillageEntityType.SCALY_BEAR.get().create(serverLevel);
		if (scalyBear != null && (uUID = this.getOwnerUUID()) != null) {
			scalyBear.setOwnerUUID(uUID);
			scalyBear.setTame(true);
			if (ageableMob instanceof ScalyBearEntity scalyBear1) {
				if (this.random.nextBoolean()) {
					scalyBear.setSnow(this.isSnow());
				} else {
					scalyBear.setSnow(scalyBear1.isSnow());
				}
			}
			else {
				scalyBear.setSnow(this.isSnow());
			}
		}
		else if (scalyBear != null) {
			if (ageableMob instanceof ScalyBearEntity scalyBear1) {
				if (this.random.nextBoolean()) {
					scalyBear.setSnow(this.isSnow());
				} else {
					scalyBear.setSnow(scalyBear1.isSnow());
				}
			}
			else {
				scalyBear.setSnow(this.isSnow());
			}
		}
		return scalyBear;
	}
	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.isTame() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(JerotesVillageItems.SCALY_BEAR_HAIR.get(), 2);
			this.spawnAtLocation(JerotesVillageItems.SCALY_BEAR_SCALE.get(), 2);
		}
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -42000 : 0);
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
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
	@Override
	public void setRecordPlayingNearby(BlockPos blockPos, boolean bl) {
		this.jukebox = blockPos;
		this.dance = bl;
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		float scale = 1f;
		if (this.isSnow())
			scale = 1.075f;
		return super.getDimensions(pose).scale(scale);
	}
	public boolean isDance() {
		return this.dance;
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
		return aabb1.inflate(0.15d, 0.15d, 0.15d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
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
		if (!this.isDance()) {
			if (!this.level().isClientSide()) {
				this.setAttackTick(20);
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 20) {
					if (this.isVehicle()) {
						this.setAnimTick(9);
						this.setAnimationState("attack2");
					} else {
						this.setAnimTick(10);
						this.setAnimationState("attack1");
					}
				} else {
					if (attackRandom > 10) {
						this.setAnimTick(9);
						this.setAnimationState("attack4");
					} else {
						this.setAnimTick(9);
						this.setAnimationState("attack3");
					}
				}
			}
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SCALY_BEAR_ATTACK, 1.0f, 1.0f);
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.isDance()) {
			if (!this.level().isClientSide()) {
				this.setAttackTick(20);
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 20) {
					if (this.isVehicle()) {
						this.setAnimTick(9);
						this.setAnimationState("attack2");
					} else {
						this.setAnimTick(10);
						this.setAnimationState("attack1");
					}
				} else {
					if (attackRandom > 10) {
						this.setAnimTick(9);
						this.setAnimationState("attack4");
					} else {
						this.setAnimTick(9);
						this.setAnimationState("attack3");
					}
				}
			}
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SCALY_BEAR_ATTACK, 1.0f, 1.0f);
		}
	}
	@Override
	public void individualAttackJerotes(LivingEntity livingEntity) {
		AttackFind.attackBegin(this, livingEntity);
	 	boolean bl = AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
		if (bl) {
			if (this.warningSoundTick <= 0) {
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(0.5, 0.2, 0.5));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, livingEntity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (hurt instanceof ScalyBearEntity scalyBear && scalyBear.getTarget() != this && this.getTarget() != scalyBear)
						continue;
					AttackFind.attackBegin(this, hurt);
					AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
				}
				//横扫效果
				Main.sweepAttack(this);
				this.warningSoundTick = 20;
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

	private int danceTick = 0;
	private boolean dance;
	@Nullable
	private BlockPos jukebox;
	private int sitTick = 0;
	private int warningSoundTick;
	private int scaleTick = 36000;
	private double honeyLike = 12;
	private double meatLike = 32;
	private double fishLike = 32;
	private double otherLike = 64;
	public boolean isNoScale() {
		return this.getEntityData().get(NO_SCALE);
	}
	public void setNoScale(boolean bl) {
		this.getEntityData().set(NO_SCALE, bl);
		if (bl) {
			this.getAttribute(Attributes.ARMOR).setBaseValue(5);
		}
		else {
			this.getAttribute(Attributes.ARMOR).setBaseValue(15);
		}
	}
	public boolean isSnow() {
		return this.getEntityData().get(IS_SNOW);
	}
	public void setSnow(boolean bl) {
		this.getEntityData().set(IS_SNOW, bl);
		this.refreshDimensions();
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(75.0);
			this.setHealth(75.0f);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(14.0);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60.0);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(11.0);
		}
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
	private void setArmor(ItemStack itemStack) {
		this.setItemSlot(EquipmentSlot.CHEST, itemStack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public boolean isArmor(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemBaseGiantBeastArmor;
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
		compoundTag.putBoolean("Dance", this.dance);
		compoundTag.putInt("DanceTick", this.danceTick);
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("WarningSoundTick", this.warningSoundTick);
		compoundTag.putInt("ScaleTick", this.scaleTick);
		compoundTag.putDouble("HoneyLike", this.honeyLike);
		compoundTag.putDouble("MeatLike", this.meatLike);
		compoundTag.putDouble("FishLike", this.fishLike);
		compoundTag.putDouble("OtherLike", this.otherLike);
		compoundTag.putBoolean("IsNoScale", this.isNoScale());
		compoundTag.putBoolean("IsSnow", this.isSnow());
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
		this.dance = compoundTag.getBoolean("Dance");
		this.danceTick = compoundTag.getInt("DanceTick");
		this.sitTick = compoundTag.getInt("SitTick");
		this.warningSoundTick = compoundTag.getInt("WarningSoundTick");
		this.scaleTick = compoundTag.getInt("ScaleTick");
		this.honeyLike = compoundTag.getDouble("HoneyLike");
		this.meatLike = compoundTag.getDouble("MeatLike");
		this.fishLike = compoundTag.getDouble("FishLike");
		this.otherLike = compoundTag.getDouble("OtherLike");
		this.setNoScale(compoundTag.getBoolean("IsNoScale"));
		this.setSnow(compoundTag.getBoolean("IsSnow"));
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
		this.getEntityData().define(NO_SCALE, false);
		this.getEntityData().define(IS_SNOW, false);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
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
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
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
			//常规
			if (!player.isShiftKeyDown()) {
				//喂食
				if (this.isFood(itemStack)) {
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(4f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
				//装配
				if (this.isOwnedBy(player)) {
					if (this.isOwnedBy(player)) {
						//铠甲
						if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
							this.equipArmor(player, itemStack);
							return InteractionResult.sidedSuccess(this.level().isClientSide);
						}
						//刷毛
						if ((item instanceof BrushItem)) {
							if (!this.isNoScale()) {
								this.setNoScale(true);
								this.scaleTick = 0;
								this.level().playSound(null, this, SoundEvents.BRUSH_GENERIC, player.getSoundSource(), 1.0f, 1.0f);
								this.level().playSound(null, this, SoundEvents.AMETHYST_BLOCK_BREAK, this.getSoundSource(), 1.0f, 1.0f);
								ItemStack scale = new ItemStack(JerotesVillageItems.SCALY_BEAR_SCALE.get(), 1);
								ItemStack hair = new ItemStack(JerotesVillageItems.SCALY_BEAR_HAIR.get(), 1);
								if (!player.getInventory().add(scale)) {
									player.drop(scale, false);
								}
								if (!player.getInventory().add(hair)) {
									player.drop(hair, false);
								}
								return InteractionResult.SUCCESS;
							}
						}
					}
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
		if (!this.isFood(itemStack))
			return super.mobInteract(player, interactionHand);
		if (this.isAngry() || !this.isBaby())
			return InteractionResult.PASS;
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		//喂食
		int FoodUse = 1;
		//蜂蜜判定
		if (itemStack.getItem() == Items.HONEY_BOTTLE || itemStack.getItem() == Items.HONEY_BLOCK || itemStack.getItem() == Items.HONEYCOMB || itemStack.getItem() == Items.HONEYCOMB_BLOCK) {
			FoodUse = (int) this.honeyLike;
			this.honeyLike += 1;
		}
		//鱼类判定
		else if (itemStack.is(ItemTags.FISHES)){
			FoodUse = (int) this.fishLike;
			this.fishLike += 1;
		}
		//肉类判定
		else if (itemStack.getItem().getFoodProperties().isMeat()){
			FoodUse = (int) this.meatLike;
			this.meatLike += 1;
		}
		else {
			FoodUse = (int) this.otherLike;
			this.otherLike += 1;
		}
		if (this.random.nextInt(FoodUse) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			setChangeType(1, player);
			((Level)this.level()).broadcastEntityEvent(this, (byte)7);
			return InteractionResult.SUCCESS;
		}
		((Level)this.level()).broadcastEntityEvent(this, (byte)6);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isNoScale() && this.scaleTick < 36000) {
			this.scaleTick += 1;
			if (this.scaleTick >= 36000) {
				if (this.getHealth() >= this.getMaxHealth()/3) {
					this.setNoScale(false);
				}
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
		if (this.getHealth() < this.getMaxHealth()/3) {
			this.setNoScale(true);
		}

		//跳舞
		if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 12) || !((Level)this.level()).getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
			this.dance = false;
			this.jukebox = null;
		}
		if (this.isDance()) {
			if (this.danceTick <= 0) {
				this.danceTick = 20;
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

		if (honeyLike > 12) {
			honeyLike -= 0.0025;
		}
		if (honeyLike > 128) {
			honeyLike = 128;
		}
		if (meatLike > 32) {
			honeyLike -= 0.0025;
		}
		if (meatLike > 128) {
			meatLike = 128;
		}
		if (fishLike > 32) {
			honeyLike -= 0.0025;
		}
		if (fishLike > 128) {
			fishLike = 128;
		}
		if (otherLike > 64) {
			honeyLike -= 0.0025;
		}
		if (otherLike > 128) {
			otherLike = 128;
		}

		if (this.warningSoundTick > 0) {
			this.warningSoundTick -= 1;
		}
		if (this.isInSittingPose()) {
			this.idleAnimationState.stop();
		}
		else {
			this.sitAnimationState.stop();
		}
		if (this.isInSittingPose() && sitTick <= 0 && !this.isDance()){
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
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (!this.isDance()) {
			if (!this.level().isClientSide()) {
				this.setAttackTick(20);
				int attackRandom = this.getRandom().nextInt(30);
				if (attackRandom > 20) {
					if (this.isVehicle()) {
						this.setAnimTick(9);
						this.setAnimationState("attack2");
					} else {
						this.setAnimTick(10);
						this.setAnimationState("attack1");
					}
				} else {
					if (attackRandom > 10) {
						this.setAnimTick(9);
						this.setAnimationState("attack4");
					} else {
						this.setAnimTick(9);
						this.setAnimationState("attack3");
					}
				}
			}
		}

		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (this.warningSoundTick <= 0) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.SCALY_BEAR_ATTACK, 1.0f, this.getVoicePitch());
				}
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(0.5, 0.2, 0.5));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (hurt instanceof ScalyBearEntity scalyBear && scalyBear.getTarget() != this && this.getTarget() != scalyBear)
						continue;
					AttackFind.attackBegin(this, hurt);
					AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
				}
				//横扫效果
				Main.sweepAttack(this);
				this.warningSoundTick = 20;
			}
		}
		return bl;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (!this.isNoScale()) {
			if (damageSource.is(DamageTypes.CACTUS)
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
		int scale = 1;
		if (!this.isNoScale()) {
			scale = 2;
			if (damageSource.is(DamageTypeTags.IS_FALL))
				scale = 4;
			if (damageSource.is(DamageTypes.EXPLOSION))
				scale = 4;
		}
		amount = amount / scale;
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / 2;

		return super.hurt(damageSource, amount);
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
				return ScalyBearEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					ScalyBearEntity.this.inventory.setItem(p_149503_, p_149528_);
					ScalyBearEntity.this.updateContainerEquipment();
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

	public boolean canWearArmor() {
		return true;
	}
	public void setArmorEquipment(ItemStack itemStack) {
		this.setArmor(itemStack);
		if (!(this.level()).isClientSide) {
			int n;
			double n2;
			double n3;
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR_TOUGHNESS)).removeModifier(ARMOR_TOUGHNESS_MODIFIER_UUID);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n = armor.getProtection()) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Giant Beast Armor bonus", n, AttributeModifier.Operation.ADDITION));
			}
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n2 = armor.getToughness()) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.ARMOR_TOUGHNESS)).addTransientModifier(new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_UUID, "Giant Beast Armor toughness bonus", n2, AttributeModifier.Operation.ADDITION));
			}
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n3 = armor.getKnockbackResistance() * 0.1) != 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Giant Beast Armor knockback resistance bonus", n3, AttributeModifier.Operation.ADDITION));
			}
		}
	}
	@Override
	public void equipSaddle(@Nullable SoundSource soundSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
	}
	public void equipArmor(Player player, ItemStack itemStack) {
		if (this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack.copyWithCount(1));
			if (player != null && !player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (spawnGroupData == null) {
			spawnGroupData = new AgeableMobGroupData(1.0f);
		}
		if (mobSpawnType == MobSpawnType.CHUNK_GENERATION) {
			if (serverLevelAccessor.getRandom().nextFloat() > 0.55f) {
				this.setBaby(true);
			}
		}
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			//苦寒之地
			if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)) {
				this.setSnow(true);
			}
			else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)) {
				this.setSnow(false);
			}
			else {
				this.setSnow(serverLevelAccessor.getRandom().nextFloat() > 0.9f);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
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
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

}

