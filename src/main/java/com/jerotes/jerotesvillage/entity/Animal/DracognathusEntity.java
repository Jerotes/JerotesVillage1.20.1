package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.Level;
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

public class DracognathusEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, Saddleable, PlayerRideableJumping, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState breathAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_COOLDOWN = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_CORROSIVER = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(DracognathusEntity.class, EntityDataSerializers.BYTE);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/meat_foods"))
	);
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("c25acf1e-68d3-4686-8e65-dc7747268176");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("cdf94f4a-8a09-4af7-901a-d599a0f5be10");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("cd7ff0e5-6acc-46d3-b00c-179abdc3148b");
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public DracognathusEntity(EntityType<? extends DracognathusEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.1f);
		this.createInventory();
	}
	public int getInventorySize() {
		return 2;
	}
	@Override
	public boolean isWarBeastArmor() {
		return true;
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
		builder = builder.add(Attributes.MAX_HEALTH, 38);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		if (!(this instanceof CorrosiverEntity)) {
			this.goalSelector.addGoal(0, new FloatGoal(this));
			this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(DracognathusEntity.this, 1.3, true));
			this.goalSelector.addGoal(1, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
			this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8));
			this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
			this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
		}
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<DracognathusEntity>(this, false));
	}

	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (!(this instanceof CorrosiverEntity)) {
			if (bl) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(48);
				this.setHealth(48);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(38);
				this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
			}
		}
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.55f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.DRACOGNATHUS_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 500;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.DRACOGNATHUS_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.DRACOGNATHUS_DEATH;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this instanceof CorrosiverEntity)
			return super.doHurtTarget(entity);

		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > 0) {
			return false;
		}
		boolean bl = super.doHurtTarget(entity);
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(10);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 10) {
				this.setAnimationState("attack1");
			}
			else if (attackRandom < 20) {
				this.setAnimationState("attack2");
			}
			else if (attackRandom < 25) {
				this.setAnimationState("attack3");
			}
			else {
				this.setAnimationState("attack4");
			}
		}
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.DRACOGNATHUS_ATTACK, 1.0f, 1.0f);
			}
			if (entity instanceof LivingEntity livingEntity && this.isCorrosiver()) {
				if (livingEntity.getTicksFrozen() < 280) {
					livingEntity.setTicksFrozen(280);
				}
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 170, 0), this);
					livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0), this);
					livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0), this);
				}
			}
		}
		return bl;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	@Nullable
	@Override
	public DracognathusEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		DracognathusEntity dracognathusEntity = JerotesVillageEntityType.DRACOGNATHUS.get().create(serverLevel);
		if (dracognathusEntity != null && (uUID = this.getOwnerUUID()) != null) {
			dracognathusEntity.setOwnerUUID(uUID);
			dracognathusEntity.setTame(true);
		}
		return dracognathusEntity;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (this.isCorrosiver() && mobEffectInstance.getEffect() == JerotesMobEffects.CORROSIVE.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
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
		return -0.6F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	//
	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
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
			this.setAttackTick(Math.max(this instanceof CorrosiverEntity ? -(int)(OtherMainConfig.CorrosiverMeleeAttackCooldown * 20) : 0, this.getAttackTick() - 1));
		}
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
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
		if (!this.level().isClientSide()) {
			this.setSpellCooldown(Math.max(0, this.getSpellCooldown() - 1));
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isColdDamage(damageSource) && this.isCorrosiver())
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this instanceof CorrosiverEntity)
			return super.hurt(damageSource, amount);
		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
		return super.hurt(damageSource, amount);
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 12;
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
		//蚀龙兽化
		if (item == JerotesVillageItems.CORROSIVER_CLAW.get() && player.getAbilities().instabuild) {
			this.setCorrosiver(true);
			this.level().playSound(null, this, JerotesSoundEvents.BREATH, this.getSoundSource(), 1.0f, 1.0f);
			if (!player.getAbilities().instabuild) {
				itemStack.hurtAndBreak(30, player, players -> players.broadcastBreakEvent(interactionHand));
			}
			return InteractionResult.SUCCESS;
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
					//蚀龙兽化
					if (item == JerotesVillageItems.CORROSIVER_CLAW.get() && !this.isCorrosiver()) {
						this.setCorrosiver(true);
						this.level().playSound(null, this, JerotesSoundEvents.BREATH, this.getSoundSource(), 1.0f, 1.0f);
						if (!player.getAbilities().instabuild) {
							itemStack.hurtAndBreak(30, player, players -> players.broadcastBreakEvent(interactionHand));
						}
						return InteractionResult.SUCCESS;
					}
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
		if (this.random.nextInt(24) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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
			this.playSound(JerotesVillageSoundEvents.DRACOGNATHUS_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 10) {
				this.setAnimationState("attack1");
			}
			else if (attackRandom < 20) {
				this.setAnimationState("attack2");
			}
			else if (attackRandom < 25) {
				this.setAnimationState("attack3");
			}
			else {
				this.setAnimationState("attack4");
			}
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		AttackFind.individualAttackJerotes(this, 40);
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.DRACOGNATHUS_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(10);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 10) {
				this.setAnimationState("attack1");
			}
			else if (attackRandom < 20) {
				this.setAnimationState("attack2");
			}
			else if (attackRandom < 25) {
				this.setAnimationState("attack3");
			}
			else {
				this.setAnimationState("attack4");
			}
		}
	}
	@Override
	public void individualAttackJerotes(LivingEntity livingEntity) {
		AttackFind.attackBegin(this, livingEntity);
		boolean bl = AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
		if (bl) {
			if (this.isCorrosiver()) {
				if (livingEntity.getTicksFrozen() < 280) {
					livingEntity.setTicksFrozen(280);
				}
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 170, 0), this);
					livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0), this);
					livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0), this);
				}
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

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(SPELL_COOLDOWN, 0);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(IS_CORROSIVER, this instanceof CorrosiverEntity);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
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
		return itemStack.getItem() instanceof HorseArmorItem;
	}
	public boolean isCorrosiver() {
		return this.getEntityData().get(IS_CORROSIVER);
	}
	public void setCorrosiver(boolean bl) {
		this.getEntityData().set(IS_CORROSIVER, bl);
	}
	public void setSpellCooldown(int n){
		this.getEntityData().set(SPELL_COOLDOWN, n);
	}
	public int getSpellCooldown(){
		return this.getEntityData().get(SPELL_COOLDOWN);
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
		else if (Objects.equals(animation, "breath")){
			return 5;
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
		list.add(this.breathAnimationState);
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
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		compoundTag.putInt("SpellCooldown", this.getSpellCooldown());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putBoolean("Corrosiver", this.isCorrosiver());
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.setSpellCooldown(compoundTag.getInt("SpellCooldown"));
		this.sitTick = compoundTag.getInt("SitTick");
		this.setCorrosiver(compoundTag.getBoolean("Corrosiver"));
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.updateContainerEquipment();
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (!(this instanceof CorrosiverEntity)) {
			if (ANIM_STATE.equals(entityDataAccessor)) {
				if (this.level().isClientSide()) {
					switch (this.entityData.get(ANIM_STATE)) {
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
							this.breathAnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.breathAnimationState);
							break;
					}
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
				return DracognathusEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					DracognathusEntity.this.inventory.setItem(p_149503_, p_149528_);
					DracognathusEntity.this.updateContainerEquipment();
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
				if (this.isControlledByLocalInstance() && this.isInWater() && this instanceof CorrosiverEntity) {
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
		if (this.isEffectiveAi() && this.isInWater() && this instanceof CorrosiverEntity) {
			this.moveRelative(this.getSpeed(), dir);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(dir);
		}
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
	public void onPlayerJump(int n) {
		if (!this.isSaddled()) {
			return;
		}
		if (n < 0) {
			n = 0;
		}
		this.playerJumpPendingScale = n >= 90 ? 1.0f : 0.4f + 0.4f * (float)n / 90.0f;
	}
	@Override
	public boolean canJump() {
		return this.isSaddled();
	}
	@Override
	public void handleStartJump(int i) {
		if (i > 80 && i <= 100 && this.isCorrosiver() && this.getSpellCooldown() <= 0) {
			if (!(this instanceof CorrosiverEntity corrosiverEntity)) {
				//法术列表-蚀龙兽吐息
				OtherSpellList.CorrosiverBreath(3, this, this).spellUse(i > 90 ? 1 : 0);
				if (!this.level().isClientSide()) {
					this.setSpellCooldown(80);
					this.setAnimTick(20);
					this.setAnimationState("breath");
				}
			}
			else {
				if (!this.level().isClientSide()) {
					corrosiverEntity.setSpellTick(25);
					corrosiverEntity.setBreathUseTick(20);
				}
			}
		}
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
		if (f < 0.8f || !this.isCorrosiver() || this.getSpellCooldown() > 0) {
			double d = 1f * (double) f * (double) this.getBlockJumpFactor();
			double d2 = d + (double) this.getJumpBoostPower();
			Vec3 vec32 = this.getDeltaMovement();
			this.setDeltaMovement(vec32.x, d2, vec32.z);
			this.setIsJumping(true);
			this.hasImpulse = true;
			if (vec3.z > 0.0) {
				float f2 = Mth.sin(this.getYRot() * 0.017453292f);
				float f3 = Mth.cos(this.getYRot() * 0.017453292f);
				this.setDeltaMovement(this.getDeltaMovement().add(-0.4f * f2 * f, 0.0, 0.4f * f3 * f));
			}
		}
	}
	@Override
	public void handleStopJump() {
	}
}
