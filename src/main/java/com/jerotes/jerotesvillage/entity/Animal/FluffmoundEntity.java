package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Interface.HornableEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.goal.FluffmoundFollowCaravanGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;
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

public class FluffmoundEntity extends BaseTamableAnimalEntity implements HornableEntity, ControlVehicleEntity, ArmorEntity, NeutralMob, Saddleable, ContainerListener, HasCustomInventoryScreen, Shearable, IForgeShearable {
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_TICK = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> NO_HAIR = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.BYTE);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/flower_foods"))
	);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(FluffmoundEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState hairlessAnimationState = new AnimationState();
	public AnimationState hurtAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public FluffmoundEntity(EntityType<? extends FluffmoundEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.createInventory();
	}

	public int getInventorySize() {
		return 27;
	}
	public int getInventoryColumns() {
		return 5;
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
		ItemStack itemStack = this.getArmor();
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 170);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.3){
			public boolean canUse() {
				if (FluffmoundEntity.this.isTame()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (FluffmoundEntity.this.isTame()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.goalSelector.addGoal(2, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new FluffmoundFollowCaravanGoal(this, 2.0999999046325684));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this)
		{
			public boolean canUse() {
				if (!FluffmoundEntity.this.isTame()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (!FluffmoundEntity.this.isTame()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<FluffmoundEntity>(this, false));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.FLUFFMOUND_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.FLUFFMOUND_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.FLUFFMOUND_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.FLUFFMOUND_WALK, 0.15f, 1.0f);
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
		return -0.8F;
	}
	protected int getMaxPassengers() {
		if (!this.isSaddled()) {
			return 1;
		}
		return 2;
	}
	@Override
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		int i = this.getPassengers().indexOf(entity);
		if (i == 0) {
			float f2 = 1.15f;
			float f3 = 0f;
			return new Vector3f(f3, entityDimensions.height, f2);
		}
		if (i == 1) {
			float f2 = -1.25f;
			float f3 = 0f;
			return new Vector3f(f3, entityDimensions.height, f2);
		}
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Nullable
	@Override
	public FluffmoundEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		FluffmoundEntity fluffmound = JerotesVillageEntityType.FLUFFMOUND.get().create(serverLevel);
		if (fluffmound != null && (uUID = this.getOwnerUUID()) != null) {
			fluffmound.setOwnerUUID(uUID);
			fluffmound.setTame(true);
		}
		return fluffmound;
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -48000 : 0);
	}
	@Override
	public boolean isHorn() {
		return !this.isBaby();
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
	}
	public boolean canWearArmor() {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 2.2f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
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
			this.playSound(JerotesVillageSoundEvents.FLUFFMOUND_ATTACK, 1.0f, 1.0f);
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
			this.playSound(JerotesVillageSoundEvents.FLUFFMOUND_ATTACK, 1.0f, 1.0f);
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
		return this.getAttackTick() <= -10;
	}
	@Override
	public boolean canPressAddJerotes() {
		return this.getAttackTick() <= -10;
	}

	private int sitTick = 0;
	private int bellTick = 0;
	public boolean isNoHair() {
		return this.getEntityData().get(NO_HAIR);
	}
	public void setNoHair(boolean bl) {
		this.getEntityData().set(NO_HAIR, bl);
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(190);
			this.setHealth(190.0f);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(170);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
		}
		this.leaveCaravan();
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
	public void setHairTick(int n){
		this.getEntityData().set(HAIR_TICK, n);
	}
	public int getHairTick(){
		return this.getEntityData().get(HAIR_TICK);
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
		else if (Objects.equals(animation, "hurt")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		list.add(this.hurtAnimationState);
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
	@Nullable
	private FluffmoundEntity caravanHead;
	@Nullable
	private FluffmoundEntity caravanTail;
	public void leaveCaravan() {
		if (this.caravanHead != null) {
			this.caravanHead.caravanTail = null;
		}

		this.caravanHead = null;
	}
	public void joinCaravan(FluffmoundEntity p_30767_) {
		this.caravanHead = p_30767_;
		this.caravanHead.caravanTail = this;
	}
	public boolean hasCaravanTail() {
		return this.caravanTail != null;
	}
	public boolean inCaravan() {
		return this.caravanHead != null;
	}
	@Nullable
	public FluffmoundEntity getCaravanHead() {
		return this.caravanHead;
	}
	protected double followLeashSpeed() {
		return 2.0;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("BellTick", this.bellTick);
		compoundTag.putInt("HairTick", this.getHairTick());
		compoundTag.putBoolean("IsNoHair", this.isNoHair());
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
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.sitTick = compoundTag.getInt("SitTick");
		this.bellTick = compoundTag.getInt("BellTick");
		this.setHairTick(compoundTag.getInt("HairTick"));
		if (compoundTag.contains("IsNoHair")) {
			this.setNoHair(compoundTag.getBoolean("IsNoHair"));
		}
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
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.updateContainerEquipment();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(HAIR_TICK, 0);
		this.getEntityData().define(NO_HAIR, false);
		this.getEntityData().define(DATA_ID_CHEST, false);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
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
					case 2:
						this.hurtAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hurtAnimationState);
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

		//剪毛
		if (readyForShearing() && (
				item instanceof ShearsItem ||
						itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:shears"))) ||
						itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/shears"))))) {
			return InteractionResult.PASS;
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
					return super.mobInteract(player, interactionHand);
				}
				if (!this.hasChest() && itemStack.is(Items.CHEST)) {
					this.equipChest(player, itemStack);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				//骑乘
				if (!this.isFood(itemStack) && this.isSaddled() && AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), player) && this.getPassengers().size() < this.getMaxPassengers() && !player.isSecondaryUseActive() && !isOrderedToSit()) {
					if (!this.level().isClientSide()) {
						player.startRiding(this);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				//鞍
//				if (item instanceof SaddleItem && !this.isSaddled()) {
//					if (this.isOwnedBy(player)) {
//						return itemStack.interactLivingEntity(player, this, interactionHand);
//					}
//				}
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
		if (this.random.nextInt(6) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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
	private void equipChest(Player player, ItemStack itemStack) {
		this.setChest(true);
		this.playChestEquipsSound();
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		this.createInventory();
	}
	@Override
	public void shear(SoundSource soundSource) {
		this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0f, 1.0f);
		if (!this.level().isClientSide()) {
			this.setNoHair(true);
			this.setHairTick(0);
		}
		int n = Main.randomReach(this.getRandom(), 2, 5);
		for (int i = 0; i < n; ++i) {
			ItemEntity itemEntity = this.spawnAtLocation(Items.WHITE_WOOL);
			if (itemEntity == null) continue;
			itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
		}
		int n2 = Main.randomReach(this.getRandom(), 2, 5);
		for (int i = 0; i < n2; ++i) {
			ItemEntity itemEntity = this.spawnAtLocation(JerotesVillageItems.BITTER_COLD_FAT.get());
			if (itemEntity == null) continue;
			itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
		}
	}
	@Override
	public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
		this.gameEvent(GameEvent.SHEAR, player);
		if (!world.isClientSide) {
			if (!this.level().isClientSide()) {
				this.setNoHair(true);
				this.setHairTick(0);
			}
			int n = Main.randomReach(this.getRandom(), 2, 5);
			List<ItemStack> items = new ArrayList<>();
			for (int j = 0; j < n; ++j) {
				items.add(new ItemStack(Items.WHITE_WOOL));
			}
			int n2 = Main.randomReach(this.getRandom(), 2, 5);
			for (int i = 0; i < n2; ++i) {
				items.add(new ItemStack(JerotesVillageItems.BITTER_COLD_FAT.get()));
			}
			return items;
		}
		return java.util.Collections.emptyList();
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isNoHair() && !this.isBaby() && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	@Override
	public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
		return readyForShearing();
	}
	protected void playChestEquipsSound() {
		this.playSound(JerotesSoundEvents.USE_CHEST, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
	}
	@Override
	public boolean hasChest() {
		return this.getEntityData().get(DATA_ID_CHEST);
	}
	public void setChest(boolean p_30505_) {
		this.getEntityData().set(DATA_ID_CHEST, p_30505_);
	}
	@Override
	public void tick() {
		super.tick();
		if (this.isNoHair()) {
			if (!this.level().isClientSide()) {
				this.setHairTick(Math.min(24000, this.getHairTick() + 1));
			}
			if (this.getHairTick() >= 24000) {
				if (!this.level().isClientSide()) {
					this.setNoHair(false);
					this.setHairTick(0);
				}
				for (int i = 0; i < 20; ++i) {
					double d = this.random.nextGaussian() * 0.02;
					double d2 = this.random.nextGaussian() * 0.02;
					double d3 = this.random.nextGaussian() * 0.02;
					this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, d2, d3);
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
		//攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
		}
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//保暖
		if (this.tickCount % 10 == 0) {
			List<LivingEntity> listFreeze = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(6, 6, 6));
			listFreeze.removeIf(livingEntity -> (!livingEntity.isAlive() || livingEntity.isRemoved() || this.getTarget() == livingEntity));
			for (LivingEntity livingEntity : listFreeze) {
				if (livingEntity == null) continue;
				if (livingEntity.getTicksFrozen() > 0) {
					livingEntity.setTicksFrozen(Math.max(0, livingEntity.getTicksFrozen() - 100));
				}
			}
		}

		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
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
		if (this.isNoHair()) {
			this.hairlessAnimationState.startIfStopped(this.tickCount);
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
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(9);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.FLUFFMOUND_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		int freezeAmount = 2;
		if (this.isNoHair()) {
			freezeAmount = 1;
		}
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
		if (AttackFind.isMeleeDamage(damageSource) && !this.hasEffect(MobEffects.DAMAGE_RESISTANCE) && !this.isVehicle() && !this.isAggressive()) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("hurt");
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1, false, false), this);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false), this);
			}
		}
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
				return FluffmoundEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					FluffmoundEntity.this.inventory.setItem(p_149503_, p_149528_);
					FluffmoundEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}

	public SlotAccess getSlot(int p_149479_) {
		return p_149479_ == 499 ? new SlotAccess() {
			public ItemStack get() {
				return FluffmoundEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
			}
			public boolean set(ItemStack p_149485_) {
				if (p_149485_.isEmpty()) {
					if (FluffmoundEntity.this.hasChest()) {
						FluffmoundEntity.this.setChest(false);
						FluffmoundEntity.this.createInventory();
					}
					return true;
				} else if (p_149485_.is(Items.CHEST)) {
					if (!FluffmoundEntity.this.hasChest()) {
						FluffmoundEntity.this.setChest(true);
						FluffmoundEntity.this.createInventory();
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
