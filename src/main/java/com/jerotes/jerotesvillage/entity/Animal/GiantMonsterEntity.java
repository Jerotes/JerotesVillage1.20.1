package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Interface.HornableEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import com.jerotes.jerotesvillage.goal.GiantMonsterWrestleAttackGoal;
import com.jerotes.jerotesvillage.goal.GiantMonsterWrestleTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.BitterColdBell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
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
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
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
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class GiantMonsterEntity extends BaseTamableAnimalEntity implements CanBeIllagerFactionEntity, ControlVehicleEntity, HornableEntity, NeutralMob, ArmorEntity, Saddleable, ContainerListener, HasCustomInventoryScreen, Shearable, IForgeShearable {
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_TICK = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HORN_TICK = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WRESTLE_COOLDOWN = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> NO_HAIR = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_HORN = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_CHAIN = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_BELL = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_BITTER_COLD_BELL = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_ILLAGER_FACTION = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("BDBEC7F5-C1AE-B628-0905-EBFCCB94B1E3");
	private static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("6E83DF5F-4A12-6F5F-7DC8-CFC26367E728");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("115D4DA3-C3D2-8031-E678-918AADADF94C");
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.BYTE);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemStack(Blocks.ICE), new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.FROSTED_ICE), new ItemStack(Blocks.BLUE_ICE), new ItemStack(JerotesVillageItems.ICE_ROCK.get()));
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(GiantMonsterEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public SimpleContainer inventory() {
		return inventory;
	}

	public GiantMonsterEntity(EntityType<? extends GiantMonsterEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
		this.createInventory();
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
		builder = builder.add(Attributes.MAX_HEALTH, 130);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.9);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.5, true));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new GiantMonsterWrestleAttackGoal(GiantMonsterEntity.this));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesIllagerFactionEntityHelpIllagerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new GiantMonsterWrestleTargetGoal<>(this, GiantMonsterEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<GiantMonsterEntity>(this, false));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.GIANT_MONSTER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.GIANT_MONSTER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.GIANT_MONSTER_DEATH;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		if (this.isBells()) {
			bellTick += 1;
			if (bellTick >= 10) {
				bellTick = 0;
			}
		}
		if (this.isBells() && this.bellTick == 0) {
			this.bellUse(blockPos);
		}
		this.playSound(JerotesVillageSoundEvents.GIANT_MONSTER_WALK, 0.15f, 1.0f);
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
		return -0.4F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
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
	public void bellUse(BlockPos blockPos) {
		this.playSound(SoundEvents.BELL_BLOCK, 8.0f, 1.0f);
		this.level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
		if (this.isBell() && this.level() instanceof ServerLevel serverLevel) {
			//村民警示
			if (serverLevel.isRaided(blockPos)) {
				List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32, 32, 32));
				for (LivingEntity villager : list) {
					if (villager == null) continue;
					if (!villager.isAlive() || villager.isRemoved() || !blockPos.closerToCenterThan(villager.position(), 32.0)) continue;
					villager.getBrain().setMemory(MemoryModuleType.HEARD_BELL_TIME, serverLevel.getGameTime());
				}
			}
			//灾厄村民现身
			List<LivingEntity> listIllager = serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(48, 48, 48));
			listIllager.removeIf(livingEntityDeter -> (!livingEntityDeter.isAlive() || livingEntityDeter.isRemoved() || !blockPos.closerToCenterThan(livingEntityDeter.position(), 32.0) || !livingEntityDeter.getType().is(EntityTypeTags.RAIDERS)));
			for (LivingEntity livingEntityDeter : listIllager) {
				if (livingEntityDeter == null) continue;
				if (!livingEntityDeter.isAlive() || livingEntityDeter.isRemoved() || !blockPos.closerToCenterThan(livingEntityDeter.position(), 32.0) || !livingEntityDeter.getType().is(EntityTypeTags.RAIDERS)) continue;
				if (!livingEntityDeter.level().isClientSide) {
					livingEntityDeter.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
				}
			}
			if (!listIllager.isEmpty()) {
				serverLevel.playSound(null, blockPos, SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 1.0f, 1.0f);
			}
			int n = (int)listIllager.stream().filter(livingEntity -> blockPos.closerToCenterThan(livingEntity.position(), 48.0)).count();
			int n2 = Mth.clamp((n - 21) / -2, 3, 15);
			for (int i = 0; i < n2; ++i) {
				MutableInt mutableInt = new MutableInt(16700985);
				int n3 = mutableInt.addAndGet(5);
				double d4 = (double) FastColor.ARGB32.red(n3) / 255.0;
				double d5 = (double)FastColor.ARGB32.green(n3) / 255.0;
				double d6 = (double)FastColor.ARGB32.blue(n3) / 255.0;
				serverLevel.sendParticles(ParticleTypes.GLOW, this.getRandomX(1.2), this.getRandomY(), this.getRandomZ(1.2), 0, d4, d5, d6, 0.0);
			}
		}
		if (this.isBitterColdBell() && this.level() instanceof ServerLevel) {
			//驱逐寒霜雪怪
			BitterColdBell.YetiAway(this);
		}
		return;
	}
	@Nullable
	@Override
	public GiantMonsterEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		GiantMonsterEntity giantMonster = JerotesVillageEntityType.GIANT_MONSTER.get().create(serverLevel);
		if (giantMonster != null && (uUID = this.getOwnerUUID()) != null) {
			giantMonster.setOwnerUUID(uUID);
			giantMonster.setTame(true);
		}
		return giantMonster;
	}
	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(JerotesVillageItems.GIANT_MONSTER_HORN.get(), 2);
		}
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -48000 : 0);
	}
	@Override
	public boolean isHorn() {
		return !this.isBaby() && !this.isNoHorn();
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
			if (itemStack.getItem() instanceof ItemBeastArmor armor && (n = armor.getBeastArmorProtection()) != 0) {
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
		boolean bl = !(this.getControllingPassenger() instanceof Mob) || this.getControllingPassenger().getType().is(EntityTypeTags.RAIDERS);
		boolean bl2 = !(this.getVehicle() instanceof Boat);
		boolean controlStopTarget = isTrueManuallyControlCombatJerotes();
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, bl);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, bl && bl2);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, bl);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, bl && !controlStopTarget);
	}
	@Override
	public void pressMainJerotes(Player player) {
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAnimationState("attack1");
			} else if (attackRandom > 10) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAnimationState("attack2");
			} else {
				this.setAttackTick(25);
				this.setAnimTick(25);
				this.setAnimationState("attack3");
			}
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAnimationState("attack1");
			} else if (attackRandom > 10) {
				this.setAttackTick(20);
				this.setAnimTick(20);
				this.setAnimationState("attack2");
			} else {
				this.setAttackTick(25);
				this.setAnimTick(25);
				this.setAnimationState("attack3");
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

	private int sitTick = 0;
	private int bellTick = 0;
	public boolean isNoHair() {
		return this.getEntityData().get(NO_HAIR);
	}
	public boolean isNoHorn() {
		return this.getEntityData().get(NO_HORN);
	}
	public boolean isChain() {
		return this.getEntityData().get(IS_CHAIN);
	}
	public boolean isBells() {
		return this.getEntityData().get(IS_BELL) || this.getEntityData().get(IS_BITTER_COLD_BELL);
	}
	public boolean isBell() {
		return this.getEntityData().get(IS_BELL);
	}
	public boolean isBitterColdBell() {
		return this.getEntityData().get(IS_BITTER_COLD_BELL);
	}
	@Override
	public boolean isIllagerFaction() {
		return this.getEntityData().get(IS_ILLAGER_FACTION);
	}
	public void setNoHair(boolean bl) {
		this.getEntityData().set(NO_HAIR, bl);
	}
	public void setNoHorn(boolean bl) {
		this.getEntityData().set(NO_HORN, bl);
	}
	public void setChain(boolean bl) {
		this.getEntityData().set(IS_CHAIN, bl);
	}
	public void setBell(boolean bl) {
		this.getEntityData().set(IS_BELL, bl);
	}
	public void setBitterColdBell(boolean bl) {
		this.getEntityData().set(IS_BITTER_COLD_BELL, bl);
	}
	@Override
	public void setIllagerFaction(boolean bl) {
		this.getEntityData().set(IS_ILLAGER_FACTION, bl);
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.setIllagerFaction(false);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(145.0);
			this.setHealth(145.0f);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.32);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(130.0);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
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
	public void setHairTick(int n){
		this.getEntityData().set(HAIR_TICK, n);
	}
	public int getHairTick(){
		return this.getEntityData().get(HAIR_TICK);
	}
	public void setHornTick(int n){
		this.getEntityData().set(HORN_TICK, n);
	}
	public int getHornTick(){
		return this.getEntityData().get(HORN_TICK);
	}
	public void setWrestleCooldown(int n){
		this.getEntityData().set(WRESTLE_COOLDOWN, n);
	}
	public int getWrestleCooldown(){
		return this.getEntityData().get(WRESTLE_COOLDOWN);
	}
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

	public boolean isArmor(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemBaseGiantBeastArmor;
	}
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
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("BellTick", this.bellTick);
		compoundTag.putInt("HairTick", this.getHairTick());
		compoundTag.putInt("HornTick", this.getHornTick());
		compoundTag.putInt("WrestleCooldown", this.getWrestleCooldown());
		compoundTag.putBoolean("IsNoHair", this.isNoHair());
		compoundTag.putBoolean("IsNoHorn", this.isNoHorn());
		compoundTag.putBoolean("IsChain", this.isChain());
		compoundTag.putBoolean("IsBell", this.isBell());
		compoundTag.putBoolean("IsBitterColdBell", this.isBitterColdBell());
		compoundTag.putBoolean("IsIllagerFaction", this.isIllagerFaction());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		if (this.notAttackTargetUUID != null) {
			compoundTag.putUUID("NotAttackTarget", this.notAttackTargetUUID);
		}
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
		this.setHornTick(compoundTag.getInt("HornTick"));
		this.setWrestleCooldown(compoundTag.getInt("WrestleCooldown"));
		if (compoundTag.contains("IsNoHair")) {
			this.setNoHair(compoundTag.getBoolean("IsNoHair"));
		}
		if (compoundTag.contains("IsNoHorn")) {
			this.setNoHorn(compoundTag.getBoolean("IsNoHorn"));
		}
		if (compoundTag.contains("IsChain")) {
			this.setChain(compoundTag.getBoolean("IsChain"));
		}
		if (compoundTag.contains("IsBell")) {
			this.setBell(compoundTag.getBoolean("IsBell"));
		}
		if (compoundTag.contains("IsBitterColdBell")) {
			this.setBitterColdBell(compoundTag.getBoolean("IsBitterColdBell"));
		}
		if (compoundTag.contains("IsIllagerFaction")) {
			this.setIllagerFaction(compoundTag.getBoolean("IsIllagerFaction"));
		}
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		if (compoundTag.hasUUID("NotAttackTarget")) {
			this.notAttackTargetUUID = compoundTag.getUUID("NotAttackTarget");
		}
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
		this.getEntityData().define(HORN_TICK, 0);
		this.getEntityData().define(WRESTLE_COOLDOWN, 0);
		this.getEntityData().define(NO_HAIR, false);
		this.getEntityData().define(NO_HORN, false);
		this.getEntityData().define(IS_CHAIN, false);
		this.getEntityData().define(IS_BELL, false);
		this.getEntityData().define(IS_BITTER_COLD_BELL, false);
		this.getEntityData().define(IS_ILLAGER_FACTION, false);
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
		if (readyForShearing() && (item instanceof ShearsItem ||
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
				//装配
				if (this.isOwnedBy(player)) {
					//拆卸安装
					if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/pincers"))) ||
							itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:shears")))) {
						if (this.isBell()) {
							this.setBell(false);
							this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0f, 1.0f);
							this.level().playSound(null, this, SoundEvents.ANVIL_BREAK, this.getSoundSource(), 1.0f, 1.0f);
							ItemStack getItems = new ItemStack(Items.BELL, 1);
							if (!player.getInventory().add(getItems)) {
								player.drop(getItems, false);
							}
							return InteractionResult.SUCCESS;
						}
						else if (this.isBitterColdBell()) {
							this.setBitterColdBell(false);
							this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0f, 1.0f);
							this.level().playSound(null, this, SoundEvents.ANVIL_BREAK, this.getSoundSource(), 1.0f, 1.0f);
							ItemStack getItems = new ItemStack(JerotesVillageItems.BITTER_COLD_BELL.get(), 1);
							if (!player.getInventory().add(getItems)) {
								player.drop(getItems, false);
							}
							return InteractionResult.SUCCESS;
						}
						else if (this.isChain()) {
							this.setChain(false);
							this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0f, 1.0f);
							this.level().playSound(null, this, SoundEvents.CHAIN_BREAK, this.getSoundSource(), 1.0f, 1.0f);
							ItemStack getItems = new ItemStack(Items.CHAIN, 1);
							if (!player.getInventory().add(getItems)) {
								player.drop(getItems, false);
							}
							return InteractionResult.SUCCESS;
						}
						else if (!this.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
							this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0f, 1.0f);
							ItemStack getItems = this.getItemBySlot(EquipmentSlot.CHEST);
							if (!player.getInventory().add(getItems)) {
								player.drop(getItems, false);
							}
							this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.AIR));
							return InteractionResult.SUCCESS;
						}
					}
					//铠甲
					if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
						this.equipArmor(player, itemStack);
						return InteractionResult.sidedSuccess(this.level().isClientSide);
					}
					//铁链
					if (item == Items.CHAIN && !this.isChain()) {
						if (this.isOwnedBy(player)) {
							this.setChain(true);
							this.level().playSound(null, this, SoundEvents.CHAIN_PLACE, this.getSoundSource(), 1.0f, 1.0f);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							return InteractionResult.SUCCESS;
						}
					}
					//钟
					if (item == Items.BELL && !this.isBells() && this.isChain()) {
						if (this.isOwnedBy(player)) {
							this.setBell(true);
							this.setBitterColdBell(false);
							this.level().playSound(null, this, SoundEvents.ANVIL_PLACE, this.getSoundSource(), 1.0f, 1.0f);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							return InteractionResult.SUCCESS;
						}
					}
					//苦寒摇铃
					if (item == JerotesVillageItems.BITTER_COLD_BELL.get() && !this.isBells() && this.isChain()) {
						if (this.isOwnedBy(player)) {
							this.setBell(false);
							this.setBitterColdBell(true);
							this.level().playSound(null, this, SoundEvents.ANVIL_PLACE, this.getSoundSource(), 1.0f, 1.0f);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							return InteractionResult.SUCCESS;
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
		if (this.random.nextInt(64) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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
	private boolean swapItem(Player player, EquipmentSlot equipmentSlot, ItemStack itemStack, InteractionHand interactionHand) {
		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
		if (player.getAbilities().instabuild && itemStack2.isEmpty() && !itemStack.isEmpty()) {
			this.setItemSlot(equipmentSlot, itemStack.copyWithCount(1));
			return true;
		}
		if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
			if (!itemStack2.isEmpty()) {
				return false;
			}
			this.setItemSlot(equipmentSlot, itemStack.split(1));
			return true;
		}
		this.setItemSlot(equipmentSlot, itemStack);
		player.setItemInHand(interactionHand, itemStack2);
		return true;
	}
	@Override
	public void shear(SoundSource soundSource) {
		this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0f, 1.0f);
		if (!this.level().isClientSide()) {
			this.setNoHair(true);
			this.setHairTick(0);
		}
		int n = Main.randomReach(this.getRandom(), 1, 3);
		for (int i = 0; i < n; ++i) {
			ItemEntity itemEntity = this.spawnAtLocation(JerotesVillageItems.GIANT_MONSTER_HAIR.get());
			if (itemEntity == null) continue;
			itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
		}
	}
	@Override
	public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
		this.gameEvent(GameEvent.SHEAR, player);
		if (player != null && player != this.getOwner() && this.getTarget() == null && (player.distanceTo(this) < 4)) {
			this.doHurtTarget(player);
		}
		if (!world.isClientSide) {
			if (!this.level().isClientSide()) {
				this.setNoHair(true);
				this.setHairTick(0);
			}
			int n = Main.randomReach(this.getRandom(), 1, 3);
			List<ItemStack> items = new ArrayList<>();
			for (int j = 0; j < n; ++j) {
				items.add(new ItemStack(JerotesVillageItems.GIANT_MONSTER_HAIR.get()));
			}
			return items;
		}
		return java.util.Collections.emptyList();
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isNoHair() && !this.isBaby() && !(this.isChain() || this.isBells() || this.isBitterColdBell()) && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	@Override
	public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
		return readyForShearing();
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
		if (this.isNoHorn()) {
			if (!this.level().isClientSide()) {
				this.setHornTick(Math.min(24000, this.getHairTick() + 1));
			}
			if (this.getHornTick() >= 24000) {
				if (!this.level().isClientSide()) {
					this.setNoHorn(false);
					this.setHornTick(0);
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
		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 10 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 15) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
			}
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
		//灾厄阵营
		//队伍
		if (this.isIllagerFaction() && this.tickCount % 10 == 1 && this.getPassengers().isEmpty()) {
			List<AbstractIllager> listRaider = this.level().getEntitiesOfClass(AbstractIllager.class, this.getBoundingBox().inflate(2.0, 2.0, 2.0));
			listRaider.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !(entity.getMobType() == MobType.ILLAGER && this.getTeam() == null && entity.getTeam() == null || (entity.isAlliedTo(this))));
			if (this.getPassengers().isEmpty()) {
				for (LivingEntity raider : listRaider) {
					if (raider == null) continue;
					if (raider.hasPassenger(this)) continue;
					if (raider.isVehicle()) continue;
					if (raider.isPassenger()) continue;
					if (!AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), raider)) continue;
					if (!(Main.mobSizeSmall(raider) || Main.mobSizeMedium(raider))) continue;
					raider.startRiding(this);
				}
			}
		}
		//敌人
		if ((this.getWrestleCooldown() < 2000 || !this.isHorn()) && this.getNotAttackTarget() != null) {
			if (!this.level().isClientSide()) {
				this.setNotAttackTarget(null);
			}

			if (this.getNotAttackTarget() instanceof GiantMonsterEntity giantMonster) {
				if (!this.level().isClientSide()) {
					giantMonster.setNotAttackTarget(null);
				}
			}
		}
		if (this.getTarget() != null && this.getNotAttackTarget() != null) {
			if (!this.level().isClientSide()) {
				this.setNotAttackTarget(null);
			}
			if (this.getNotAttackTarget() instanceof GiantMonsterEntity giantMonster) {
				if (!this.level().isClientSide()) {
					giantMonster.setNotAttackTarget(null);
				}
			}
		}
		if (this.getWrestleCooldown() > 0){
			this.setWrestleCooldown(this.getWrestleCooldown() - 1);
		}
		else if (this.getNotAttackTarget() != null) {
			if (!this.level().isClientSide()) {
				this.setNotAttackTarget(null);
			}
			if (this.getNotAttackTarget() instanceof GiantMonsterEntity giantMonster) {
				if (!this.level().isClientSide()) {
					giantMonster.setNotAttackTarget(null);
				}
			}
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
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAttackTick(15);
				this.setAnimTick(15);
				this.setAnimationState("attack1");
			} else if (attackRandom > 10) {
				this.setAttackTick(15);
				this.setAnimTick(15);
				this.setAnimationState("attack2");
			} else {
				this.setAttackTick(20);
				this.setAnimTick(15);
				this.setAnimationState("attack3");
			}
		}
		//钟
		if (this.isBells()) {
			bellUse(this.getOnPos());
		}
		return true;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GIANT_MONSTER_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}

		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(1.25f));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			if (hurt instanceof GiantMonsterEntity giantMonster && giantMonster.getTarget() != this && this.getTarget() != giantMonster)
				continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl2) {
				if (this.isBitterColdBell() && hurt instanceof FrostYetiEntity frostYetiEntity) {
					if (!frostYetiEntity.level().isClientSide()) {
						frostYetiEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80));
					}
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
	}

	public boolean wrestleTarget(LivingEntity entity) {
		float damage = 0;
		DamageSource damageSources = this.level().damageSources().noAggroMobAttack(this);
		AttackFind.attackBegin(this, entity);
		boolean bl = AttackFind.attackAfterCustomDamage(this, entity, damageSources, 0f, 1.0f, true, damage);
		if (bl && this.getRandom().nextFloat() > 0.9f && !this.isNoHorn()) {
			if (!this.level().isClientSide()) {
				this.setNoHorn(true);
				this.setHornTick(0);
			}
			int n = Main.randomReach(this.getRandom(), 0, 2);
			for (int i = 0; i < n; ++i) {
				ItemEntity itemEntity = this.spawnAtLocation(JerotesVillageItems.GIANT_MONSTER_HORN.get());
				if (itemEntity == null) continue;
				itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
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
		if (this.isBitterColdBell()) {
			freezeAmount = 5;
		}
		if (this.isNoHair()) {
			freezeAmount = 1;
		}
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
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
				return GiantMonsterEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					GiantMonsterEntity.this.inventory.setItem(p_149503_, p_149528_);
					GiantMonsterEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}

	public SlotAccess getSlot(int n) {
		int i = n - getAddNumber();
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

		int j = n - 500 + 2;
		return j >= 2 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(n);
	}

	public boolean hasInventoryChanged(Container p_149512_) {
		return this.inventory != p_149512_;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		if (this.isChain()) {
			this.spawnAtLocation(Items.CHAIN);
		}
		if (this.isBell()) {
			this.spawnAtLocation(Items.BELL);
		}
		if (this.isBitterColdBell()) {
			this.spawnAtLocation(JerotesVillageItems.BITTER_COLD_BELL.get());
		}
	}

	public ResourceLocation getDefaultLootTable() {
		if (!this.isNoHorn()) {
			return this.getType().getDefaultLootTable();
		}
		return new ResourceLocation(JerotesVillage.MODID, "entities/giant_monster_no_horn");
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

	public boolean canAttack(LivingEntity livingEntity) {
		if (this.isIllagerFaction() && livingEntity instanceof AbstractIllager && ((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam())) {
			return false;
		}
		return super.canAttack(livingEntity);
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
