package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Interface.HornableEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.item.ItemGiantBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.goal.WildernessBullChargeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
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

public class WildernessBullEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, HornableEntity, ArmorEntity, Saddleable, ContainerListener, HasCustomInventoryScreen {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("b81f4268-09f0-47af-b0e5-d748973f233f");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("1aec6506-0a26-4386-bb94-589f4231f7f6");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("32ae4294-4e55-4464-81d3-0845929a9e92");
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_ADD_TICK = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_ATTACK_TICK = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_ATTACK_DAMAGE_TICK = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> HURT_BY_GOAT = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Float> GOAT_GIVE_SELF_DAMAGE = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(WildernessBullEntity.class, EntityDataSerializers.INT);
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState hoeAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public WildernessBullEntity(EntityType<? extends WildernessBullEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.createInventory();
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
		if (this.level().isClientSide) {
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

	@Override
	public boolean isHorn() {
		return !this.isBaby();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.8, true));
		this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.8));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WildernessBullChargeGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<WildernessBullEntity>(this, false));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 65);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		return builder;
	}


	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(75.0);
			this.setHealth(75.0f);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0);
			this.getAttribute(Attributes.ARMOR).setBaseValue(5.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(65);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0);
			this.getAttribute(Attributes.ARMOR).setBaseValue(3.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.WILDERNESS_BULL_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.WILDERNESS_BULL_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.WILDERNESS_BULL_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.WILDERNESS_BULL_WALK, 0.15f, 1.0f);
		if (!this.level().isClientSide()) {
			this.setRushAddTick(20);
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
			this.setRushAttackTick(45);
			this.setAnimTick(12);
			this.setAnimationState("attack");
		}
		if (this.getRushAttackDamageTick() > 60) {
			AttackFind.attackBegin(this, entity);
			boolean bl = AttackFind.attackAfter(this, entity, (float) (1f + (this.getRushAttackDamageTick() / 240f * 4f)), 1.0f, false, 0f);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.WILDERNESS_BULL_ATTACK, 1.0f, 1.0f);
				}
				if (entity instanceof LivingEntity livingEntity) {
					livingEntity.knockback(getRushAttackDamageTick() / 120f, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
				}
			}
			if (!this.level().isClientSide()) {
				this.setRushAttackDamageTick(0);
			}
			return bl;
		}
		else {
			boolean bl = super.doHurtTarget(entity);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.WILDERNESS_BULL_ATTACK, 1.0f, 1.0f);
				}
			}
			if (!this.level().isClientSide()) {
				this.setRushAttackDamageTick(0);
			}
			return bl;
		}
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	@Nullable
	@Override
	public WildernessBullEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		WildernessBullEntity bull = JerotesVillageEntityType.WILDERNESS_BULL.get().create(serverLevel);
		if (bull != null && (uUID = this.getOwnerUUID()) != null) {
			bull.setOwnerUUID(uUID);
			bull.setTame(true);
		}
		return bull;
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -42000 : 0);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.95f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
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
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//

	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		if (!this.level().isClientSide()) {
			this.setRushAttackTick(Math.max(0, this.getRushAttackTick() - 1));
			this.setRushAddTick(Math.max(0, this.getRushAddTick() - 1));
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
		if ((this.getTarget() != null || this.isVehicle()) && (this.getRushAddTick() > 0) && this.getRushAttackDamageTick() < 240) {
			if (!this.level().isClientSide()) {
				this.setRushAttackDamageTick(this.getRushAttackDamageTick() + 1);
			}
		}
		else if (this.getRushAttackDamageTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setRushAttackDamageTick(this.getRushAttackDamageTick() - 1);
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

		if (!this.level().isClientSide) {
			if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				if (this.getOwner() != null && this.isTame() && this.getOwner().getVehicle() != null && this.getOwner().getVehicle() == this) {
					if (this.getOwner().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem || this.getOwner().getOffhandItem().getItem() instanceof HoeItem) {
						AABB aABB = this.getBoundingBox().inflate(0.2);
						for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
							BlockState blockState = this.level().getBlockState(blockPos);
							if (!(blockState == Blocks.GRASS_BLOCK.defaultBlockState()
									|| blockState == Blocks.DIRT.defaultBlockState()
									|| blockState == Blocks.DIRT_PATH.defaultBlockState()
									|| blockState == Blocks.COARSE_DIRT.defaultBlockState()
									|| blockState == Blocks.ROOTED_DIRT.defaultBlockState()
									|| blockState == Blocks.MYCELIUM.defaultBlockState()
									|| blockState == Blocks.PODZOL.defaultBlockState()
									|| blockState == JerotesVillageBlocks.WILDERNESS_DIRT.get().defaultBlockState()
									|| blockState == JerotesVillageBlocks.SNOW_FROZEN_SOIL.get().defaultBlockState()))
								continue;
							if (this.level().getBlockState(blockPos.above()).getBlock() instanceof AirBlock || this.level().getBlockState(blockPos.above()).getBlock() instanceof BushBlock) {
								this.level().setBlockAndUpdate(blockPos, Blocks.FARMLAND.defaultBlockState());
								if (this.level().getBlockState(blockPos.above()).is(BlockTags.FLOWERS) || this.level().getBlockState(blockPos.above()).is(BlockTags.SWORD_EFFICIENT)); {
									float block = blockState.getDestroySpeed(this.level(), blockPos.above());
									if (block >= 5f || block < 0f) continue;
									this.level().destroyBlock(blockPos.above(), true, this);
								}
								if (this.random.nextInt(5) == 1) {
									this.getOwner().getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1, this.getOwner(), livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
								}
								if (!this.isSilent() && (double)this.random.nextFloat() < 0.25) {
									this.level().playSound(this, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
								}
							}

						}
					}
				}
			}
		}
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
		if (damageSource.is(DamageTypes.MOB_ATTACK_NO_AGGRO) &&
				!this.isHurtByGoat() &&
				!this.isAggressive() &&
				!this.isOrderedToSit() &&
				damageSource.getEntity() instanceof LivingEntity livingEntity &&
				!AttackFind.FindCanNotAttack(this, livingEntity)) {
			if (!this.level().isClientSide()) {
				this.setNotAttackTarget(livingEntity);
				this.setHurtByGoat(true);
				this.setGoatGiveSelfDamage(amount);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void doPush(Entity entity) {
		if (this.getOwner() != null && this.isTame() && this.getOwner().getVehicle() != null) {
			if (this.getOwner().getVehicle() == this && this.getRushAttackTick() <= 0 && !(this.getOwner().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) && !(this.getOwner().getOffhandItem().getItem() instanceof HoeItem)) {
				if (this.getRushAttackDamageTick() > 60) {
					if (entity != this.getOwner() && entity instanceof LivingEntity livingEntity && (this.wantsToAttack(livingEntity, this.getOwner())) && !AttackFind.FindCanNotAttack(this, livingEntity) && !AttackFind.FindCanNotAttack(this.getOwner(), livingEntity)) {
						float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
						((LivingEntity) entity).knockback((((double) f1 * 0.5F) + this.getRushAttackTick() / 120F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
						this.doHurtTarget(entity);
					}
				}
			}
		}
		super.doPush(entity);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (itemStack.is(Items.BUCKET) && !this.isBaby()) {
			player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
			player.setItemInHand(interactionHand, itemstack1);
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
				//装配
				if (this.isOwnedBy(player)) {
					//铠甲
					if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
						this.equipArmor(player, itemStack);
						return InteractionResult.sidedSuccess(this.level().isClientSide);
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
		if (!this.isFood(itemStack) || this.isAngry())
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(48) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity;
		if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}

	public void setRushAttackTick(int n){
		this.getEntityData().set(RUSH_ATTACK_TICK, n);
	}
	public int getRushAttackTick(){
		return this.getEntityData().get(RUSH_ATTACK_TICK);
	}
	public void setRushAddTick(int n){
		this.getEntityData().set(RUSH_ADD_TICK, n);
	}
	public int getRushAddTick(){
		return this.getEntityData().get(RUSH_ADD_TICK);
	}
	public void setRushAttackDamageTick(int n){
		this.getEntityData().set(RUSH_ATTACK_DAMAGE_TICK, n);
	}
	public int getRushAttackDamageTick(){
		return this.getEntityData().get(RUSH_ATTACK_DAMAGE_TICK);
	}
	public void setHurtByGoat(boolean bl){
		this.getEntityData().set(HURT_BY_GOAT, bl);
	}
	public boolean isHurtByGoat(){
		return this.getEntityData().get(HURT_BY_GOAT);
	}
	public void setGoatGiveSelfDamage(float f){
		this.getEntityData().set(GOAT_GIVE_SELF_DAMAGE, f);
	}
	public float getGoatGiveSelfDamage(){
		return this.getEntityData().get(GOAT_GIVE_SELF_DAMAGE);
	}
	private int sitTick = 0;
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
	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	public boolean isArmor(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemBaseGiantBeastArmor;
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
			this.playSound(JerotesVillageSoundEvents.WILDERNESS_BULL_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setRushAttackTick(45);
			this.setAnimTick(12);
			this.setAnimationState("attack");
			this.setRushAttackDamageTick(0);
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
		if (this.getRushAttackDamageTick() > 60) {
			boolean bl =  AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
			AttackFind.attackAfter(this, livingEntity, (float) (1f + (this.getRushAttackDamageTick() / 240f * 4f)), 1f, false, 0f);
			if (bl) {
				livingEntity.knockback(getRushAttackDamageTick() / 120f, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
			}
		}
		else {
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
	@Nullable
	private LivingEntity notAttackTarget;
	@Nullable
	private UUID notAttackTargetUUID;
	@Nullable
	public LivingEntity getNotAttackTarget() {
		Entity entity;
		if (this.notAttackTarget == null && this.notAttackTargetUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.notAttackTargetUUID)) instanceof LivingEntity) {
			this.notAttackTarget = (LivingEntity)entity;
		}
		return this.notAttackTarget;
	}
	public void setNotAttackTarget(@Nullable LivingEntity livingEntity) {
		this.notAttackTarget = livingEntity;
		this.notAttackTargetUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	//
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.notAttackTargetUUID != null) {
			compoundTag.putUUID("NotAttackTarget", this.notAttackTargetUUID);
		}
		compoundTag.putBoolean("IsHurtByGoat", this.isHurtByGoat());
		compoundTag.putFloat("GoatGiveSelfDamage", this.getGoatGiveSelfDamage());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("RushAttackTick", this.getRushAttackTick());
		compoundTag.putInt("RushAddTick", this.getRushAddTick());
		compoundTag.putDouble("RushAttackDamageTick", this.getRushAttackDamageTick());
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
		if (compoundTag.hasUUID("NotAttackTarget")) {
			this.notAttackTargetUUID = compoundTag.getUUID("NotAttackTarget");
		}
		this.setHurtByGoat(compoundTag.getBoolean("IsHurtByGoat"));
		this.setGoatGiveSelfDamage(compoundTag.getFloat("GoatGiveSelfDamage"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setRushAttackTick(compoundTag.getInt("RushAttackTick"));
		this.setRushAddTick(compoundTag.getInt("RushAddTick"));
		this.setRushAttackDamageTick(compoundTag.getInt("RushAttackDamageTick"));
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
		this.getEntityData().define(RUSH_ADD_TICK, 0);
		this.getEntityData().define(RUSH_ATTACK_TICK, 0);
		this.getEntityData().define(RUSH_ATTACK_DAMAGE_TICK, 0);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(HURT_BY_GOAT, false);
		this.getEntityData().define(GOAT_GIVE_SELF_DAMAGE, 0f);
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
		super.onSyncedDataUpdated(entityDataAccessor);
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
				return WildernessBullEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					WildernessBullEntity.this.inventory.setItem(p_149503_, p_149528_);
					WildernessBullEntity.this.updateContainerEquipment();
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
			if (!player.getAbilities().instabuild) {
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

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.6f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		float addSpeed = (float) (this.getRushAttackDamageTick() * 0.0005f);
		if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				if (this.getOwner() != null && entity == this.getOwner() && (this.getOwner().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem || this.getOwner().getOffhandItem().getItem() instanceof HoeItem)) {
					this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) + addSpeed) / 3);
					this.hoeAnimationState.startIfStopped(this.tickCount);
				}
				else {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) + addSpeed);
					this.hoeAnimationState.stop();
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
		else {
			this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) + addSpeed * 5);
			this.hoeAnimationState.stop();
		}
		super.travel(dir);
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




