package com.jerotes.jerotesvillage.entity.Monster.Hag;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.control.QoaikuMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.PurpleSandHagEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.jerotes.jerotesvillage.goal.HagAttackGoal;
import com.jerotes.jerotesvillage.goal.QoaikuGroundOpenDoorGoal;
import com.jerotes.jerotesvillage.goal.QoaikuOpenDoorGoal;
import com.jerotes.jerotesvillage.goal.SpirveOrHagAttackTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class BaseHagEntity extends Raider implements WizardEntity, SpellUseEntity, PurpleSandSisterhoodEntity, InventoryEntity, UseShieldEntity, NeutralMob, InventoryCarrier, Npc, JerotesEntity, FactionEntity, SkinEntity, Enemy, Merchant {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState spell1AnimationState = new AnimationState();
	public AnimationState spell2AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	public AnimationState shieldUseMainhandAnimationState = new AnimationState();
	public AnimationState shieldUseOffhandAnimationState = new AnimationState();

	private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_MELEE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MELEE_TICK = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CONJUR_SPIRVE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> COVENS = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> INVISIBILITY = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> INVISIBILITY_TICK = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);

	private static final EntityDataAccessor<Boolean> COVEN = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	//
	private static final EntityDataAccessor<Boolean> IS_FEMALE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SKIN_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EYE_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EYE_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_1 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_2 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_3 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JACKET_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JACKET_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PANTS_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PANTS_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GLOVES_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GLOVES_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_1 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_2 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_3 = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOES_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOES_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAT_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAT_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERCOAT_TYPE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERCOAT_COLOR = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	//
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(BaseHagEntity.class, EntityDataSerializers.INT);
	public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
	private LazyOptional<?> itemHandler = null;

	protected BaseHagEntity(EntityType<? extends BaseHagEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0.6f);
		this.xpReward = 50;
		setPersistenceRequired();
		this.applyOpenDoorsAbility();
		this.setCanPickUpLoot(false);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.itemHandler = LazyOptional.of(this::createCombinedHandler);
	}
	private IItemHandler createCombinedHandler() {
		IItemHandlerModifiable handsHandler = new EntityHandsInvWrapper(this);
		IItemHandlerModifiable armorHandler = new EntityArmorInvWrapper(this);
		IItemHandlerModifiable customHandler = new InvWrapper(this.inventory);
		return new CombinedInvWrapper(
				armorHandler,
				handsHandler,
				customHandler
		);
	}
	@Override
	public ItemStack equipItemIfPossible(ItemStack itemStack) {
		EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
		boolean bl = this.canReplaceCurrentItem(itemStack, itemStack2);
		if (equipmentSlot.isArmor() && !bl) {
			equipmentSlot = EquipmentSlot.MAINHAND;
			itemStack2 = this.getItemBySlot(equipmentSlot);
			bl = itemStack2.isEmpty();
		}
		if (bl && this.canHoldItem(itemStack)) {
			double d = this.getEquipmentDropChance(equipmentSlot);
			if (this.canAddToInventory(itemStack2)) {
				this.addToInventory(itemStack2);
			}
			else {
				this.spawnAtLocation(itemStack2);
			}

			if (equipmentSlot.isArmor() && itemStack.getCount() > 1) {
				ItemStack itemStack3 = itemStack.copyWithCount(1);
				this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack3);
				return itemStack3;
			}
			this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack);
			return itemStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(this, newItem, oldItem);
	}

	@Override
	public String getFirstFactionTypeName() {
		return "purple_sand_sisterhood";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("radier");
		return list;
	}

	@VisibleForDebug
	@Override
	public SimpleContainer getInventory() {
		return this.inventory;
	}

	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null &&
				this.isAlive() ? itemHandler.cast() : super.getCapability(capability, facing);
	}
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}
	protected ItemStack addToInventory(ItemStack itemStack) {
		return this.inventory.addItem(itemStack);
	}

	protected boolean canAddToInventory(ItemStack itemStack) {
		return this.inventory.canAddItem(itemStack);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.MAX_HEALTH, 121);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		super.dropCustomDeathLoot(damageSource, n, bl);
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new QoaikuOpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new QoaikuGroundOpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesTradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new JerotesAttackAvoidEntityGoal<LivingEntity>(this, LivingEntity.class, 4.0f, 1.0, 1.0) {
			@Override
			public boolean canUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(1, new HagAttackGoal(this, 1.2, 10, 10.0f) {
			@Override
			public boolean canUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		if (!(this instanceof PurpleSandHagEntity)) {
			this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
			this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		}
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.3, false) {
			@Override
			public boolean canUse() {
				if (!BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (!BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(3, new JerotesMeleeAttackGoal(this, 1.0, false) {
			@Override
			public boolean canUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (BaseHagEntity.this.isMelee()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Witch.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, SpirveEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, BaseHagEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, BaseHagEntity.class));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new SpirveOrHagAttackTargetGoal<Player>(this, Player.class, true) {
			@Override
			public boolean canUse() {
				if (BaseHagEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new SpirveOrHagAttackTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new SpirveOrHagAttackTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(3, new SpirveOrHagAttackTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<BaseHagEntity>(this, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.HAG_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource) && this.isMelee()) {
			return SoundEvents.SHIELD_BLOCK;
		}
		return JerotesVillageSoundEvents.HAG_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.HAG_DEATH;
	}
	@Override
	public SoundEvent getCelebrateSound() {
		return JerotesVillageSoundEvents.HAG_LAUGH_1;
	}
	@Override
	public void applyRaidBuffs(int p_37844_, boolean p_37845_) {
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0f, entityDimensions.height + 0.0625f * f, 0.0f);
	}
	protected float ridingOffset(Entity entity) {
		return -0.7f;
	}
	public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.45D;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.65f;
	}
	@Override
	public boolean canBeLeader() {
		return false;
	}

	public void setMelee(boolean bl){
		this.getEntityData().set(IS_MELEE, bl);
	}
	public boolean isMelee(){
		return this.getEntityData().get(IS_MELEE);
	}
	public int spellLevel = 3;
	public int covenSpellLevel = 4;
	public int shieldCoolDown;
	public int shieldCanUse = 1;
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	@Override
	public int getSpellLevel() {
		if (this.covenSpellLevel > spellLevel && this.isCoven()) {
			return covenSpellLevel;
		}
		return this.spellLevel;
	}
	public void setMeleeTick(int n){
		this.getEntityData().set(MELEE_TICK, n);
	}
	public int getMeleeTick(){
		return this.getEntityData().get(MELEE_TICK);
	}
	public void setConjurSpirve(int n){
		this.getEntityData().set(CONJUR_SPIRVE, n);
	}
	public int getConjurSpirve(){
		return this.getEntityData().get(CONJUR_SPIRVE);
	}
	public boolean isCovens() {
		return this.getEntityData().get(COVENS);
	}
	public void setCovens(boolean bl) {
		this.getEntityData().set(COVENS, bl);
	}
	public void setInvisibility(boolean bl){
		this.getEntityData().set(INVISIBILITY, bl);
	}
	public boolean isInvisibility(){
		return this.getEntityData().get(INVISIBILITY);
	}
	public void setInvisibilityTick(int n){
		this.getEntityData().set(INVISIBILITY_TICK, n);
	}
	public int getInvisibilityTick(){
		return this.getEntityData().get(INVISIBILITY_TICK);
	}
	public boolean isCoven() {
		return this.getEntityData().get(COVEN);
	}
	public void setCoven(boolean bl) {
		this.getEntityData().set(COVEN, bl);
	}
	//性别
	@Override
	public boolean IsFemale() {
		return this.getEntityData().get(IS_FEMALE);
	}
	public void setFemale(boolean bl) {
		this.getEntityData().set(IS_FEMALE, bl);
	}
	//皮肤
	@Override
	public int SkinType() {
		return this.getEntityData().get(SKIN_TYPE);
	}
	public void setSkinType(int n) {
		this.getEntityData().set(SKIN_TYPE, n);
	}
	@Override
	public int SkinColor() {
		return this.getEntityData().get(SKIN_COLOR);
	}
	public void setSkinColor(int n) {
		this.getEntityData().set(SKIN_COLOR, n);
	}
	//眼睛
	@Override
	public int EyeType() {
		return this.getEntityData().get(EYE_TYPE);
	}
	public void setEyeType(int n) {
		this.getEntityData().set(EYE_TYPE, n);
	}
	@Override
	public int EyeColor() {
		return this.getEntityData().get(EYE_COLOR);
	}
	public void setEyeColor(int n) {
		this.getEntityData().set(EYE_COLOR, n);
	}
	//头发
	@Override
	public int HairType() {
		return this.getEntityData().get(HAIR_TYPE);
	}
	public void setHairType(int n) {
		this.getEntityData().set(HAIR_TYPE, n);
	}
	@Override
	public int HairColor() {
		return this.getEntityData().get(HAIR_COLOR);
	}
	public void setHairColor(int n) {
		this.getEntityData().set(HAIR_COLOR, n);
	}
	//附件-1
	@Override
	public int AddType_1() {
		return this.getEntityData().get(ADD_TYPE_1);
	}
	public void setAddType_1(int n) {
		this.getEntityData().set(ADD_TYPE_1, n);
	}
	//附件-2
	@Override
	public int AddType_2() {
		return this.getEntityData().get(ADD_TYPE_2);
	}
	public void setAddType_2(int n) {
		this.getEntityData().set(ADD_TYPE_2, n);
	}
	//附件-3
	@Override
	public int AddType_3() {
		return this.getEntityData().get(ADD_TYPE_3);
	}
	public void setAddType_3(int n) {
		this.getEntityData().set(ADD_TYPE_3, n);
	}
	//上衣
	@Override
	public int JacketType() {
		return this.getEntityData().get(JACKET_TYPE);
	}
	public void setJacketType(int n) {
		this.getEntityData().set(JACKET_TYPE, n);
	}
	@Override
	public int JacketColor() {
		return this.getEntityData().get(JACKET_COLOR);
	}
	public void setJacketColor(int n) {
		this.getEntityData().set(JACKET_COLOR, n);
	}
	//裤子
	@Override
	public int PantsType() {
		return this.getEntityData().get(PANTS_TYPE);
	}
	public void setPantsType(int n) {
		this.getEntityData().set(PANTS_TYPE, n);
	}
	@Override
	public int PantsColor() {
		return this.getEntityData().get(PANTS_COLOR);
	}
	public void setPantsColor(int n) {
		this.getEntityData().set(PANTS_COLOR, n);
	}
	//手套
	@Override
	public int GlovesType() {
		return this.getEntityData().get(GLOVES_TYPE);
	}
	public void setGlovesType(int n) {
		this.getEntityData().set(GLOVES_TYPE, n);
	}
	@Override
	public int GlovesColor() {
		return this.getEntityData().get(GLOVES_COLOR);
	}
	public void setGlovesColor(int n) {
		this.getEntityData().set(GLOVES_COLOR, n);
	}
	//饰品-1
	@Override
	public int BaubleType_1() {
		return this.getEntityData().get(BAUBLE_TYPE_1);
	}
	public void setBaubleType_1(int n) {
		this.getEntityData().set(BAUBLE_TYPE_1, n);
	}
	//饰品-2
	@Override
	public int BaubleType_2() {
		return this.getEntityData().get(BAUBLE_TYPE_2);
	}
	public void setBaubleType_2(int n) {
		this.getEntityData().set(BAUBLE_TYPE_2, n);
	}
	//饰品-3
	@Override
	public int BaubleType_3() {
		return this.getEntityData().get(BAUBLE_TYPE_3);
	}
	public void setBaubleType_3(int n) {
		this.getEntityData().set(BAUBLE_TYPE_3, n);
	}
	//鞋子
	@Override
	public int ShoesType() {
		return this.getEntityData().get(SHOES_TYPE);
	}
	public void setShoesType(int n) {
		this.getEntityData().set(SHOES_TYPE, n);
	}
	@Override
	public int ShoesColor() {
		return this.getEntityData().get(SHOES_COLOR);
	}
	public void setShoesColor(int n) {
		this.getEntityData().set(SHOES_COLOR, n);
	}
	//帽子
	@Override
	public int HatType() {
		return this.getEntityData().get(HAT_TYPE);
	}
	public void setHatType(int n) {
		this.getEntityData().set(HAT_TYPE, n);
	}
	@Override
	public int HatColor() {
		return this.getEntityData().get(HAT_COLOR);
	}
	public void setHatColor(int n) {
		this.getEntityData().set(HAT_COLOR, n);
	}
	//外套
	@Override
	public int OvercoatType() {
		return this.getEntityData().get(OVERCOAT_TYPE);
	}
	public void setOvercoatType(int n) {
		this.getEntityData().set(OVERCOAT_TYPE, n);
	}
	@Override
	public int OvercoatColor() {
		return this.getEntityData().get(OVERCOAT_COLOR);
	}
	public void setOvercoatColor(int n) {
		this.getEntityData().set(OVERCOAT_COLOR, n);
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
		if (Objects.equals(animation, "attack1")) {
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "spell1")){
			return 3;
		}
		else if (Objects.equals(animation, "spell2")){
			return 4;
		}
		else if (Objects.equals(animation, "dead")){
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
		list.add(this.spell1AnimationState);
		list.add(this.spell2AnimationState);
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
	@Override
	public SimpleContainer mobInventory() {
		return inventory;
	}
	@Override
	public int inventoryCount() {
		return 8;
	}
	@Override
	public boolean isCanChangeInventory() {
		return this.getEntityData().get(CAN_CHANGE_INVENTORY);
	}
	@Override
	public void setCanChangeInventory(boolean bl) {
		this.getEntityData().set(CAN_CHANGE_INVENTORY, bl);
	}
	@Override
	public boolean isCanChangeMeleeOrRange() {
		return this.getEntityData().get(CAN_CHANGE_MELEE_OR_RANGE);
	}
	@Override
	public void setCanChangeMeleeOrRange(boolean bl) {
		this.getEntityData().set(CAN_CHANGE_MELEE_OR_RANGE, bl);
	}
	@Override
	public boolean canUseCrossbow() {
		return false;
	}
	@Override
	public boolean canUseThrow() {
		return false;
	}
	@Override
	public boolean canUseRangeJavelin() {
		return false;
	}
	@Override
	public boolean canUseBow() {
		return false;
	}
	@Override
	public boolean NonCombatEmptyWeapon() {
		return this.isNoCombatEmptyWeapon();
	}
	@Override
	public boolean NonCombatEmptyShield() {
		return this.isNoCombatEmptyShield();
	}
	@Override
	public int changeInventoryCooldownTick() {
		return this.getChangeInventoryCooldownTick();
	}
	public boolean isNoCombatEmptyWeapon() {
		return this.getEntityData().get(NO_COMBAT_EMPTY_WEAPON);
	}
	public void setNoCombatEmptyWeapon(boolean bl) {
		this.getEntityData().set(NO_COMBAT_EMPTY_WEAPON, bl);
	}
	public boolean isNoCombatEmptyShield() {
		return this.getEntityData().get(NO_COMBAT_EMPTY_SHIELD);
	}
	public void setNoCombatEmptyShield(boolean bl) {
		this.getEntityData().set(NO_COMBAT_EMPTY_SHIELD, bl);
	}
	public int getChangeInventoryCooldownTick() {
		return this.getEntityData().get(CHANGE_INVENTORY_COOLDOWN_TICK);
	}
	public void setChangeInventoryCooldownTick(int n) {
		this.getEntityData().set(CHANGE_INVENTORY_COOLDOWN_TICK, n);
	}
	@Override
	public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
		if (magicType2 == MagicType.MAIN && magicType == MagicType.SHOOT) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAnimationState("spell1");
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAnimTick(15);
				this.setAnimationState("spell2");
			}
		}
	}
	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		return new ArrayList<>();
	}
	@Override
	public List<SpellTypeInterface> SelfAddSpellList() {
		return new ArrayList<>();
	}
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public int getCombatStyle() {
		return this.getEntityData().get(COMBAT_STYLE);
	}
	public void setCombatStyle(int n) {
		this.getEntityData().set(COMBAT_STYLE, n);
	}
	public boolean isUseSelfNotStringSpellList() {
		return this.getEntityData().get(USE_SELF_NOT_SPELL_LIST);
	}
	public void setUseSelfNotStringSpellList(boolean bl) {
		this.getEntityData().set(USE_SELF_NOT_SPELL_LIST, bl);
	}
	public void setShieldLevel(int n){
		this.getEntityData().set(SHIELD_LEVEL, n);
	}
	public int getShieldLevel(){
		return this.getEntityData().get(SHIELD_LEVEL);
	}
	public boolean isMagicUseStyle() {
		return this.getSpellTick() > 0;
	}
	@Override
	public boolean isSpellHumanoid() {
		return true;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("ShieldLevel", this.getShieldLevel());
		compoundTag.putInt("CombatStyle", this.getCombatStyle());
		compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
		compoundTag.putBoolean("IsNoCombatEmptyWeapon", this.isNoCombatEmptyWeapon());
		compoundTag.putBoolean("IsNoCombatEmptyShield", this.isNoCombatEmptyShield());
		compoundTag.putInt("ChangeInventoryCooldownTick", this.getChangeInventoryCooldownTick());
		compoundTag.putBoolean("IsCanChangeInventory", this.isCanChangeInventory());
		compoundTag.putBoolean("IsCanChangeMeleeOrRange", this.isCanChangeMeleeOrRange());
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putBoolean("IsMelee", this.isMelee());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("CovenSpellLevel", this.covenSpellLevel);
		compoundTag.putBoolean("Covens", this.isCovens());
		compoundTag.putInt("MeleeTick", this.getMeleeTick());
		compoundTag.putInt("ConjurSpirve", this.getConjurSpirve());
		compoundTag.putBoolean("Invisibility", this.isInvisibility());
		compoundTag.putDouble("InvisibilityTick", this.getInvisibilityTick());
		compoundTag.putBoolean("IsCoven", this.isCoven());
		MerchantOffers merchantOffers = this.getOffers();
		if (!merchantOffers.isEmpty()) {
			compoundTag.put("Offers", merchantOffers.createTag());
		}
		this.writeInventoryToTag(compoundTag);
		if (this.wanderTarget != null) {
			compoundTag.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
		}

		compoundTag.putBoolean("IsFemale", this.IsFemale());
		compoundTag.putInt("SkinType", this.SkinType());
		compoundTag.putInt("SkinColor", this.SkinColor());
		compoundTag.putInt("EyeType", this.EyeType());
		compoundTag.putInt("EyeColor", this.EyeColor());
		compoundTag.putInt("HairType", this.HairType());
		compoundTag.putInt("HairColor", this.HairColor());
		compoundTag.putInt("AddType_1", this.AddType_1());
		compoundTag.putInt("AddType_2", this.AddType_2());
		compoundTag.putInt("AddType_3", this.AddType_3());
		compoundTag.putInt("JacketType", this.JacketType());
		compoundTag.putInt("JacketColor", this.JacketColor());
		compoundTag.putInt("PantsType", this.PantsType());
		compoundTag.putInt("PantsColor", this.PantsColor());
		compoundTag.putInt("GlovesType", this.GlovesType());
		compoundTag.putInt("GlovesColor", this.GlovesColor());
		compoundTag.putInt("BaubleType_1", this.BaubleType_1());
		compoundTag.putInt("BaubleType_2", this.BaubleType_2());
		compoundTag.putInt("BaubleType_3", this.BaubleType_3());
		compoundTag.putInt("ShoesType", this.ShoesType());
		compoundTag.putInt("ShoesColor", this.ShoesColor());
		compoundTag.putInt("HatType", this.HatType());
		compoundTag.putInt("HatColor", this.HatColor());
		compoundTag.putInt("OvercoatType", this.OvercoatType());
		compoundTag.putInt("OvercoatColor", this.OvercoatColor());
		if (compoundTag.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compoundTag.getCompound("Offers"));
		}
		this.readInventoryFromTag(compoundTag);
		if (compoundTag.contains("WanderTarget")) {
			this.wanderTarget = NbtUtils.readBlockPos(compoundTag.getCompound("WanderTarget"));
		}

		this.addPersistentAngerSaveData(compoundTag);
		this.writeInventoryToTag(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setCombatStyle(compoundTag.getInt("CombatStyle"));
		this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
		this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
		this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
		this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
		this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
		this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.setMelee(compoundTag.getBoolean("IsMelee"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.covenSpellLevel = compoundTag.getInt("CovenSpellLevel");
		this.setCovens(compoundTag.getBoolean("Covens"));
		this.setMeleeTick(compoundTag.getInt("MeleeTick"));
		this.setConjurSpirve(compoundTag.getInt("ConjurSpirve"));
		this.setInvisibility(compoundTag.getBoolean("Invisibility"));
		this.setInvisibilityTick(compoundTag.getInt("InvisibilityTick"));
		this.setCoven(compoundTag.getBoolean("IsCoven"));

		if (compoundTag.contains("IsFemale")) {
			this.setFemale(compoundTag.getBoolean("IsFemale"));
		}
		if (compoundTag.contains("SkinType")) {
			this.setSkinType(compoundTag.getInt("SkinType"));
		}
		if (compoundTag.contains("SkinColor")) {
			this.setSkinColor(compoundTag.getInt("SkinColor"));
		}
		if (compoundTag.contains("EyeType")) {
			this.setEyeType(compoundTag.getInt("EyeType"));
		}
		if (compoundTag.contains("EyeColor")) {
			this.setEyeColor(compoundTag.getInt("EyeColor"));
		}
		if (compoundTag.contains("HairType")) {
			this.setHairType(compoundTag.getInt("HairType"));
		}
		if (compoundTag.contains("HairColor")) {
			this.setHairColor(compoundTag.getInt("HairColor"));
		}
		if (compoundTag.contains("AddType_1")) {
			this.setAddType_1(compoundTag.getInt("AddType_1"));
		}
		if (compoundTag.contains("AddType_2")) {
			this.setAddType_2(compoundTag.getInt("AddType_2"));
		}
		if (compoundTag.contains("AddType_3")) {
			this.setAddType_3(compoundTag.getInt("AddType_3"));
		}
		if (compoundTag.contains("JacketType")) {
			this.setJacketType(compoundTag.getInt("JacketType"));
		}
		if (compoundTag.contains("JacketColor")) {
			this.setJacketColor(compoundTag.getInt("JacketColor"));
		}
		if (compoundTag.contains("PantsType")) {
			this.setPantsType(compoundTag.getInt("PantsType"));
		}
		if (compoundTag.contains("PantsColor")) {
			this.setPantsColor(compoundTag.getInt("PantsColor"));
		}
		if (compoundTag.contains("GlovesType")) {
			this.setGlovesType(compoundTag.getInt("GlovesType"));
		}
		if (compoundTag.contains("GlovesColor")) {
			this.setGlovesColor(compoundTag.getInt("GlovesColor"));
		}
		if (compoundTag.contains("BaubleType_1")) {
			this.setBaubleType_1(compoundTag.getInt("BaubleType_1"));
		}
		if (compoundTag.contains("BaubleType_2")) {
			this.setBaubleType_2(compoundTag.getInt("BaubleType_2"));
		}
		if (compoundTag.contains("BaubleType_3")) {
			this.setBaubleType_3(compoundTag.getInt("BaubleType_3"));
		}
		if (compoundTag.contains("ShoesType")) {
			this.setShoesType(compoundTag.getInt("ShoesType"));
		}
		if (compoundTag.contains("ShoesColor")) {
			this.setShoesColor(compoundTag.getInt("ShoesColor"));
		}
		if (compoundTag.contains("HatType")) {
			this.setHatType(compoundTag.getInt("HatType"));
		}
		if (compoundTag.contains("HatColor")) {
			this.setHatColor(compoundTag.getInt("HatColor"));
		}
		if (compoundTag.contains("OvercoatType")) {
			this.setOvercoatType(compoundTag.getInt("OvercoatType"));
		}
		if (compoundTag.contains("OvercoatColor")) {
			this.setOvercoatColor(compoundTag.getInt("OvercoatColor"));
		}

		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.readInventoryFromTag(compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
		this.getEntityData().define(SHIELD_LEVEL, 1);
		this.getEntityData().define(COMBAT_STYLE, 1);
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(IS_MELEE, false);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
		this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
		this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);

		this.getEntityData().define(MELEE_TICK, 0);
		this.getEntityData().define(CONJUR_SPIRVE, 0);
		this.getEntityData().define(COVENS, false);
		this.getEntityData().define(INVISIBILITY, false);
		this.getEntityData().define(INVISIBILITY_TICK, 0);
		this.getEntityData().define(COVEN, false);

		this.getEntityData().define(IS_FEMALE, true);
		this.getEntityData().define(SKIN_TYPE, 1);
		this.getEntityData().define(ADD_TYPE_2, 0);
		this.getEntityData().define(ADD_TYPE_3, 0);
		this.getEntityData().define(JACKET_TYPE, 1);
		this.getEntityData().define(JACKET_COLOR, 1);
		this.getEntityData().define(PANTS_TYPE, 1);
		this.getEntityData().define(PANTS_COLOR, 1);
		this.getEntityData().define(GLOVES_TYPE, 0);
		this.getEntityData().define(GLOVES_COLOR, 1);
		this.getEntityData().define(BAUBLE_TYPE_1, 0);
		this.getEntityData().define(BAUBLE_TYPE_2, 0);
		this.getEntityData().define(BAUBLE_TYPE_3, 0);
		this.getEntityData().define(SHOES_TYPE, 1);
		this.getEntityData().define(SHOES_COLOR, 1);
		this.getEntityData().define(HAT_COLOR, 1);
		this.getEntityData().define(OVERCOAT_COLOR, 1);
		if (this.getType() == JerotesVillageEntityType.COVEN_HAG_ONE.get()) {
			this.getEntityData().define(SKIN_COLOR, 7);
			this.getEntityData().define(EYE_TYPE, 3);
			this.getEntityData().define(EYE_COLOR, 1);
			this.getEntityData().define(HAIR_TYPE, 17);
			this.getEntityData().define(HAIR_COLOR, 3);
			this.getEntityData().define(ADD_TYPE_1, 0);
			this.getEntityData().define(HAT_TYPE, 9);
			this.getEntityData().define(OVERCOAT_TYPE, 1);
		}
		else if (this.getType() == JerotesVillageEntityType.COVEN_HAG_TWO.get()) {
			this.getEntityData().define(SKIN_COLOR, 1);
			this.getEntityData().define(EYE_TYPE, 1);
			this.getEntityData().define(EYE_COLOR, 1);
			this.getEntityData().define(HAIR_TYPE, 18);
			this.getEntityData().define(HAIR_COLOR, 2);
			this.getEntityData().define(ADD_TYPE_1, 0);
			this.getEntityData().define(HAT_TYPE, 10);
			this.getEntityData().define(OVERCOAT_TYPE, 2);
		}
		else if (this.getType() == JerotesVillageEntityType.PURPLE_SAND_HAG.get()) {
			this.getEntityData().define(SKIN_COLOR, 2);
			this.getEntityData().define(EYE_TYPE, 1);
			this.getEntityData().define(EYE_COLOR, 1);
			this.getEntityData().define(HAIR_TYPE, 19);
			this.getEntityData().define(HAIR_COLOR, 1);
			this.getEntityData().define(ADD_TYPE_1, 1);
			this.getEntityData().define(HAT_TYPE, 11);
			this.getEntityData().define(OVERCOAT_TYPE, 3);
		}
		else {
			this.getEntityData().define(SKIN_COLOR, 1);
			this.getEntityData().define(EYE_TYPE, 1);
			this.getEntityData().define(EYE_COLOR, 1);
			this.getEntityData().define(HAIR_TYPE, 1);
			this.getEntityData().define(HAIR_COLOR, 1);
			this.getEntityData().define(ADD_TYPE_1, 0);
			this.getEntityData().define(HAT_TYPE, 0);
			this.getEntityData().define(OVERCOAT_TYPE, 4);
		}
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (COVEN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (IS_FEMALE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
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
						this.spell1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell1AnimationState);
						break;
					case 4:
						this.spell2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell2AnimationState);
						break;
					case 5:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (!this.isAggressive() && this.getTarget() == null && MainConfig.MobSayQuestionMark) {
			if (!this.level().isClientSide() && player instanceof ServerPlayer serverPlayer && interactionHand == InteractionHand.MAIN_HAND) {
				serverPlayer.sendSystemMessage(Component.translatable("talk.jerotesvillage.normal", this.getDisplayName()).withStyle(ChatFormatting.WHITE));
			}
		}
		if (this.getTarget() == null && this.isAlive() && !this.isTrading() && !(this instanceof PurpleSandHagEntity) && !(this instanceof CovenHagEntity) && !player.isShiftKeyDown()) {
			if (interactionHand == InteractionHand.MAIN_HAND) {
				player.awardStat(Stats.TALKED_TO_VILLAGER);
			}
			if (this.getOffers().isEmpty()) {
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			if (this.offers != null && !this.level().isClientSide && !this.offers.isEmpty()) {
				this.setTradingPlayer(player);
				this.openTradingScreen(player, this.getDisplayName(), 1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		if (this.random.nextInt(5 * 60 * 20) == 1) {
			for (MerchantOffer merchantOffer : this.getOffers()) {
				merchantOffer.resetUses();
			}
		}
		this.updateSwingTime();
		this.updateNoActionTime();
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		//
		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//近战攻击
		if (!this.isMelee() && this.getMeleeTick() <= 0 && this.isAlive() && this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget()) && this.getAttackBoundingBox().intersects(this.getTarget().getBoundingBox()) && this.getSensing().hasLineOfSight(this.getTarget())) {
			this.swing(InteractionHand.MAIN_HAND);
			this.doHurtTarget(this.getTarget());
		}
		if (!this.level().isClientSide()) {
			this.setMeleeTick(Math.max(0, this.getMeleeTick() - 1));
			this.setConjurSpirve(Math.max(0, this.getConjurSpirve() - 1));
			this.setInvisibilityTick(Math.max(0, this.getInvisibilityTick() - 1));
		}
		//鬼婆集会判定
		{
			List<BaseHagEntity> list = this.level().getEntitiesOfClass(BaseHagEntity.class, this.getBoundingBox().inflate(10.0, 10.0, 10.0));
			list.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
			if (!this.level().isClientSide) {
				boolean coven = list.size() >= 3;
				this.setCoven(coven);
				this.setCovens(coven);
			}
			if (this.isCoven() && !this.isInvisible()) {
				if (this.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.WITCH,
							this.getX(), this.getY(1.2f), this.getZ(),
							0, this.getRandom().nextFloat() / 5 - 0.1f, 0.15 + this.getRandom().nextFloat() / 5, this.getRandom().nextFloat() / 5 - 0.1f,  0.0125);
				}
			}
		}
		if (this.isInvisibility() && this.getTarget() != null) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5, 0));
			}
		}
		//摧毁骑乘物
		Main.destroyRides(this);
		//替换自己的物品
		changeInventory(this);
		//使用盾牌和双手武器
		if (this.isMelee()) {
			useBlockingItem(this);
			if (this.isAggressive() && this.shieldCanUse() && this.isUsingItem() && this.getUseItem().getItem() instanceof ShieldItem) {
				if (this.getOffhandItem().getItem() instanceof ShieldItem) {
					this.shieldUseOffhandAnimationState.start(this.tickCount);
				}
				else {
					this.shieldUseOffhandAnimationState.stop();
					if (this.getMainHandItem().getItem() instanceof ShieldItem) {
						this.shieldUseMainhandAnimationState.start(this.tickCount);
					}
					else {
						this.shieldUseMainhandAnimationState.stop();
					}
				}
			}
			else {
				this.shieldUseOffhandAnimationState.stop();
				this.shieldUseMainhandAnimationState.stop();
			}
		}
		//
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new QoaikuMoveControl(this);
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
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 1.1;
		}
	}

	@Override
	protected void blockUsingShield(LivingEntity entityIn) {
		super.blockUsingShield(entityIn);
		if (entityIn instanceof BreakShieldEntity breakShield) this.disableShieldBreak(breakShield.getShieldBreakStrength());
		if (entityIn.getMainHandItem().canDisableShield(this.useItem, this, entityIn) || entityIn.canDisableShield()) this.disableShield();
		this.disableShieldTry();
	}

	@Override
	public boolean isDamageSourceBlocks(DamageSource damageSource) {
		Vec3 object;
		Entity entity = damageSource.getDirectEntity();
		boolean bl = entity instanceof AbstractArrow && ((AbstractArrow) (AbstractArrow) entity).getPierceLevel() > 0;
		if (!damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && !bl && (object = damageSource.getSourcePosition()) != null) {
			Vec3 vec3 = this.calculateViewVector(0.0f, this.getYHeadRot());
			Vec3 vec32 = object.vectorTo(this.position());
			vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
			return vec32.dot(vec3) < 0.0;
		}
		return false;
	}

	@Override
	protected void hurtCurrentlyUsedShield(float f) {
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_SHIELD_CAN_BREAK)) {
			if (this.useItem.canPerformAction(ToolActions.SHIELD_BLOCK)) {
				if (f >= 3.0f) {
					int n = 1 + Mth.floor(f);
					InteractionHand interactionHand = this.getUsedItemHand();
					this.useItem.hurtAndBreak(n, this, player -> player.broadcastBreakEvent(interactionHand));
					if (this.useItem.isEmpty()) {
						if (interactionHand == InteractionHand.MAIN_HAND) {
							this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
						} else {
							this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
						}
						this.useItem = ItemStack.EMPTY;
						this.playSound(SoundEvents.SHIELD_BREAK, 0.8f, 0.8f + this.level().random.nextFloat() * 0.4f);
					}
				}
			}
		}
	}

	@Override
	public void disableShield() {
		if (this.getRandom().nextFloat() <= (1 - (this.getShieldLevel() - 1) * 0.2f)) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}
	@Override
	public void disableShieldTry() {
		if (this.getRandom().nextFloat() <= (0.15 - (this.getShieldLevel() - 1) * 0.05f)) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}
	@Override
	public void disableShieldBreak(int tick) {
		if (tick == 0) {
			return;
		}
		if (this.shieldCoolDown < tick) {
			this.shieldCoolDown = tick;
		}
		this.shieldCanUse = 0;
		if (this.getUseItem().getItem() instanceof ShieldItem) {
			this.stopUsingItem();
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setMeleeTick(20);
			this.setInvisibility(false);
			if (!(this.isMelee() && !this.getMainHandItem().isEmpty())) {
				this.setAnimTick(5);
			}
			else {
				this.setAnimTick(10);
			}
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 15) {
				this.setAnimationState("attack1");
			}
			else {
				this.setAnimationState("attack2");
			}
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0f, 1.0f);
			}
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			this.stopTrading();
		}
		return bl;
	}

	@Override
	protected void hurtArmor(DamageSource damageSource, float damage) {
		if (JerotesGameRules.JEROTES_ARMOR_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_ARMOR_CAN_BREAK)) {
			if (damage >= 0.0F) {
				damage = damage / 4.0F;
				if (damage < 1.0F) {
					damage = 1.0F;
				}
				ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
				ItemStack chest = this.getItemBySlot(EquipmentSlot.CHEST);
				ItemStack legs = this.getItemBySlot(EquipmentSlot.LEGS);
				ItemStack feet = this.getItemBySlot(EquipmentSlot.FEET);
				if ((!damageSource.is(DamageTypes.ON_FIRE) || !head.getItem().isFireResistant() && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR))) {
					if (head.getItem() instanceof ArmorItem) {
						head.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.HEAD));
					}
					if (chest.getItem() instanceof ArmorItem) {
						chest.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.CHEST));
					}
					if (legs.getItem() instanceof ArmorItem) {
						legs.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.LEGS));
					}
					if (feet.getItem() instanceof ArmorItem) {
						feet.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.FEET));
					}
				}
			}
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			RandomSource randomSource = serverLevelAccessor.getRandom();
			this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);

			this.setFemale(true);
			this.setSkinType(this.MaxSkinType());
			if (this.getType() == JerotesVillageEntityType.COVEN_HAG_ONE.get()) {
				this.setSkinColor(7);
				this.setEyeType(3);
				this.setEyeColor(1);
				this.setHairType(17);
				this.setHairColor(3);
				this.setAddType_1(0);
				this.setAddType_2(0);
				this.setAddType_3(0);
				this.setJacketColor(1);
				this.setPantsColor(1);
				this.setShoesColor(1);
				this.setHatType(9);
				this.setHatColor(1);
				this.setOvercoatType(1);
				this.setOvercoatColor(1);
			}
			else if (this.getType() == JerotesVillageEntityType.COVEN_HAG_TWO.get()) {
				this.setSkinColor(1);
				this.setEyeType(1);
				this.setEyeColor(1);
				this.setHairType(18);
				this.setHairColor(2);
				this.setAddType_1(0);
				this.setAddType_2(0);
				this.setAddType_3(0);
				this.setJacketColor(1);
				this.setPantsColor(1);
				this.setShoesColor(1);
				this.setHatType(10);
				this.setHatColor(1);
				this.setOvercoatType(2);
				this.setOvercoatColor(1);
			}
			else if (this.getType() == JerotesVillageEntityType.PURPLE_SAND_HAG.get()) {
				this.setSkinColor(2);
				this.setEyeType(1);
				this.setEyeColor(1);
				this.setHairType(19);
				this.setHairColor(1);
				this.setAddType_1(1);
				this.setAddType_2(0);
				this.setAddType_3(0);
				this.setJacketColor(1);
				this.setPantsColor(1);
				this.setShoesColor(1);
				this.setHatType(11);
				this.setHatColor(1);
				this.setOvercoatType(3);
				this.setOvercoatColor(1);
			}
			else {
				this.setSkinColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxSkinColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.03f) {
					this.setEyeType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxEyeType()));
				}
				else {
					this.setEyeType(1);
				}
				this.setEyeColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxEyeColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setAddType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						this.setAddType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
						if (this.AddType_1() == this.AddType_2()) {
							this.setAddType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							this.setAddType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
							if (this.AddType_2() == this.AddType_3()) {
								this.setAddType_3(0);
							}
						}
					}
				}
				this.setHairType(Main.randomReach(serverLevelAccessor.getRandom(), 0, this.MaxHairType()));
				this.setHairColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairColor()));
				this.setJacketType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxJacketType()));
				this.setJacketColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxJacketColor()));
				this.setPantsType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxPantsType()));
				this.setPantsColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxPantsColor()));
				this.setGlovesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxGlovesType()));
				this.setGlovesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxGlovesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setBaubleType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						this.setBaubleType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
						if (this.BaubleType_1() == this.BaubleType_2()) {
							this.setBaubleType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							this.setBaubleType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
							if (this.BaubleType_2() == this.BaubleType_3()) {
								this.setBaubleType_3(0);
							}
						}
					}
				}
				this.setShoesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxShoesType()));
				this.setShoesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxShoesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setHatType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairType()));
				}
				this.setHatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairColor()));
				this.setOvercoatType(4);
				this.setOvercoatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxOvercoatColor()));
			}

			float weaponRandom = this.random.nextFloat();
			float offhandRandom = this.random.nextFloat();
			this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
			this.setItemSlot(EquipmentSlot.OFFHAND, this.createSpawnOffhand(offhandRandom, weaponRandom));
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		return spawnGroupData;
	}
	@Override
	protected void onOffspringSpawnedFromEgg(Player player, Mob child) {
		super.onOffspringSpawnedFromEgg(player, child);
		if (child instanceof BaseHagEntity baseHagEntity && baseHagEntity.level() instanceof ServerLevel serverLevelAccessor) {
			baseHagEntity.setFemale(true);
			baseHagEntity.setSkinType(baseHagEntity.MaxSkinType());
			if (baseHagEntity.getType() == JerotesVillageEntityType.COVEN_HAG_ONE.get()) {
				baseHagEntity.setSkinColor(7);
				baseHagEntity.setEyeType(3);
				baseHagEntity.setEyeColor(1);
				baseHagEntity.setHairType(17);
				baseHagEntity.setHairColor(3);
				baseHagEntity.setAddType_1(0);
				baseHagEntity.setAddType_2(0);
				baseHagEntity.setAddType_3(0);
				baseHagEntity.setJacketColor(1);
				baseHagEntity.setPantsColor(1);
				baseHagEntity.setShoesColor(1);
				baseHagEntity.setHatType(9);
				baseHagEntity.setHatColor(1);
				baseHagEntity.setOvercoatType(1);
				baseHagEntity.setOvercoatColor(1);
			}
			else if (baseHagEntity.getType() == JerotesVillageEntityType.COVEN_HAG_TWO.get()) {
				baseHagEntity.setSkinColor(1);
				baseHagEntity.setEyeType(1);
				baseHagEntity.setEyeColor(1);
				baseHagEntity.setHairType(18);
				baseHagEntity.setHairColor(2);
				baseHagEntity.setAddType_1(0);
				baseHagEntity.setAddType_2(0);
				baseHagEntity.setAddType_3(0);
				baseHagEntity.setJacketColor(1);
				baseHagEntity.setPantsColor(1);
				baseHagEntity.setShoesColor(1);
				baseHagEntity.setHatType(10);
				baseHagEntity.setHatColor(1);
				baseHagEntity.setOvercoatType(2);
				baseHagEntity.setOvercoatColor(1);
			}
			else if (baseHagEntity.getType() == JerotesVillageEntityType.PURPLE_SAND_HAG.get()) {
				baseHagEntity.setSkinColor(2);
				baseHagEntity.setEyeType(1);
				baseHagEntity.setEyeColor(1);
				baseHagEntity.setHairType(19);
				baseHagEntity.setHairColor(1);
				baseHagEntity.setAddType_1(1);
				baseHagEntity.setAddType_2(0);
				baseHagEntity.setAddType_3(0);
				baseHagEntity.setJacketColor(1);
				baseHagEntity.setPantsColor(1);
				baseHagEntity.setShoesColor(1);
				baseHagEntity.setHatType(11);
				baseHagEntity.setHatColor(1);
				baseHagEntity.setOvercoatType(3);
				baseHagEntity.setOvercoatColor(1);
			}
			else {
				baseHagEntity.setSkinColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxSkinColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.03f) {
					baseHagEntity.setEyeType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxEyeType()));
				}
				else {
					baseHagEntity.setEyeType(1);
				}
				baseHagEntity.setEyeColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxEyeColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					baseHagEntity.setAddType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxAddType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						baseHagEntity.setAddType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxAddType()));
						if (baseHagEntity.AddType_1() == baseHagEntity.AddType_2()) {
							baseHagEntity.setAddType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							baseHagEntity.setAddType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxAddType()));
							if (baseHagEntity.AddType_2() == baseHagEntity.AddType_3()) {
								baseHagEntity.setAddType_3(0);
							}
						}
					}
				}
				baseHagEntity.setHairType(Main.randomReach(serverLevelAccessor.getRandom(), 0, baseHagEntity.MaxHairType()));
				baseHagEntity.setHairColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxHairColor()));
				baseHagEntity.setJacketType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxJacketType()));
				baseHagEntity.setJacketColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxJacketColor()));
				baseHagEntity.setPantsType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxPantsType()));
				baseHagEntity.setPantsColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxPantsColor()));
				baseHagEntity.setGlovesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxGlovesType()));
				baseHagEntity.setGlovesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxGlovesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					baseHagEntity.setBaubleType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxBaubleType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						baseHagEntity.setBaubleType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxBaubleType()));
						if (baseHagEntity.BaubleType_1() == baseHagEntity.BaubleType_2()) {
							baseHagEntity.setBaubleType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							baseHagEntity.setBaubleType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxBaubleType()));
							if (baseHagEntity.BaubleType_2() == baseHagEntity.BaubleType_3()) {
								baseHagEntity.setBaubleType_3(0);
							}
						}
					}
				}
				baseHagEntity.setShoesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxShoesType()));
				baseHagEntity.setShoesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxShoesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					baseHagEntity.setHatType(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxHairType()));
				}
				baseHagEntity.setHatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxHairColor()));
				baseHagEntity.setOvercoatType(4);
				baseHagEntity.setOvercoatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, baseHagEntity.MaxOvercoatColor()));
			}
		}
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(Items.AIR);
	}

	public ItemStack createSpawnOffhand(float offhandRandom, float weaponRandom) {
		return new ItemStack(Items.AIR);
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

	//商人

	@Nullable
	private Player tradingPlayer;
	@Nullable
	protected MerchantOffers offers;
	@Nullable
	private BlockPos wanderTarget;

	@Override
	public void setTradingPlayer(@Nullable Player player) {
		this.tradingPlayer = player;
	}

	@Nullable
	@Override
	public Player getTradingPlayer() {
		return this.tradingPlayer;
	}

	public boolean isTrading() {
		return this.tradingPlayer != null;
	}

	@Override
	public MerchantOffers getOffers() {
		if (this.offers == null) {
			this.offers = new MerchantOffers();
			this.updateTrades();
		}
		return this.offers;
	}

	@Override
	public void overrideOffers(@Nullable MerchantOffers merchantOffers) {
	}

	@Override
	public void overrideXp(int n) {
	}

	@Override
	public void notifyTrade(MerchantOffer merchantOffer) {
		merchantOffer.increaseUses();
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.rewardTradeXp(merchantOffer);
	}

	protected void rewardTradeXp(MerchantOffer merchantOffer) {
		if (merchantOffer.shouldRewardExp()) {
			int n = 3 + this.random.nextInt(4);
			this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), n));
		}
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	@Override
	public void notifyTradeUpdated(ItemStack itemStack) {
		if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
			this.ambientSoundTime = -this.getAmbientSoundInterval();
			this.playSound(this.getTradeUpdatedSound(!itemStack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
		}
	}

	@Override
	public int getVillagerXp() {
		return 0;
	}

	@Override
	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.PLAYER_LEVELUP;
	}

	protected SoundEvent getTradeUpdatedSound(boolean bl) {
		return bl ? SoundEvents.PLAYER_LEVELUP : JerotesVillageSoundEvents.MEROR_TOOL_USE;
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel serverLevel) {
		this.stopTrading();
		return super.changeDimension(serverLevel);
	}

	protected void stopTrading() {
		this.setTradingPlayer(null);
	}

	@Override
	public void die(DamageSource damageSource) {
		super.die(damageSource);
		this.stopTrading();
	}

	@Override
	public SlotAccess getSlot(int n) {
		int n2 = n - 300;
		if (n2 >= 0 && n2 < this.getInventory().getContainerSize()) {
			return SlotAccess.forContainer(this.getInventory(), n2);
		}
		return super.getSlot(n);
	}

	protected void updateTrades() {
		VillagerTrades.ItemListing[] arritemListing = HAG.get(1);
		if (arritemListing == null) {
			return;
		}
		MerchantOffers merchantOffers = this.getOffers();
		this.addOffersFromItemListings(merchantOffers, arritemListing, 8);
	}

	public static final Int2ObjectMap<VillagerTrades.ItemListing[]> HAG =
			new Int2ObjectOpenHashMap(ImmutableMap.of(
					//卖出
					1, new VillagerTrades.ItemListing[]{
							//腐肉-骨头
							new BasicItemListing(new ItemStack(Items.ROTTEN_FLESH, 1),
									new ItemStack(Items.BONE, 1), 30, 5, 0.05f),
							//骨头-腐肉
							new BasicItemListing(new ItemStack(Items.BONE, 1),
									new ItemStack(Items.ROTTEN_FLESH, 1), 30, 5, 0.05f),
							//牛奶-水
							new BasicItemListing(new ItemStack(Items.MILK_BUCKET, 1),
									new ItemStack(Items.WATER_BUCKET, 1), 30, 5, 0.05f),
							//沙子-紫沙
							new BasicItemListing(new ItemStack(Items.SAND, 1),
									new ItemStack(JerotesVillageItems.PURPLE_SAND.get(), 1), 30, 5, 0.05f),
							//蜘蛛眼-鬼婆之眼
							new BasicItemListing(new ItemStack(Items.SPIDER_EYE, 32),
									new ItemStack(JerotesVillageItems.HAG_EYE.get(), 1), 30, 5, 0.05f),
							//粉红仙人掌-女巫集会地图
							new BasicItemListing(new ItemStack(JerotesVillageItems.PINK_CACTUS.get(), 32),
									new ItemStack(JerotesVillageItems.WITCH_COVEN_MAP.get(), 1), 30, 5, 0.05f),
							//紫沙守宫尾巴-紫水晶碎片
							new BasicItemListing(new ItemStack(JerotesVillageItems.PURPLE_SAND_GECKO_TAIL.get(), 1),
									new ItemStack(Items.AMETHYST_SHARD, 1), 30, 5, 0.05f),
							//蜂蜜块-蜂巢
							new BasicItemListing(new ItemStack(Items.HONEY_BLOCK, 1),
									new ItemStack(Items.BEE_NEST, 1), 30, 5, 0.05f),
							//黑色染料-白色染料
							new BasicItemListing(new ItemStack(Items.BLACK_DYE, 1),
									new ItemStack(Items.WHITE_DYE, 1), 30, 5, 0.05f),
							//白色染料-黑色染料
							new BasicItemListing(new ItemStack(Items.WHITE_DYE, 1),
									new ItemStack(Items.BLACK_DYE, 1), 30, 5, 0.05f),
							//水瓶-碎大药水瓶
							new BasicItemListing(new ItemStack(Items.HONEY_BOTTLE, 1),
									new ItemStack(JerotesVillageItems.SPIRVE_BROKEN_BOOTLE.get(), 1), 30, 5, 0.05f),
							//鳕鱼-鳕鱼桶
							new BasicItemListing(new ItemStack(Items.COD, 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(Items.COD_BUCKET, 1), 30, 5, 0.05f),
							//鲑鱼-鲑鱼桶
							new BasicItemListing(new ItemStack(Items.SALMON, 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(Items.SALMON_BUCKET, 1), 30, 5, 0.05f),
							//热带鱼-热带鱼桶
							new BasicItemListing(new ItemStack(Items.TROPICAL_FISH, 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(Items.TROPICAL_FISH_BUCKET, 1), 30, 5, 0.05f),
							//河豚-河豚桶
							new BasicItemListing(new ItemStack(Items.PUFFERFISH, 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(Items.PUFFERFISH_BUCKET, 1), 30, 5, 0.05f),
							//明草鲤-明草鲤桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.BRIGHT_GRASS_CARP.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.BRIGHT_GRASS_CARP_BUCKET.get(), 1), 30, 5, 0.05f),
							//花鲈鱼-花鲈鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.CHROMASNAP_BASS.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.CHROMASNAP_BASS_BUCKET.get(), 1), 30, 5, 0.05f),
							//泊鳅-泊鳅桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.LAKE_LOACH.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.LAKE_LOACH_BUCKET.get(), 1), 30, 5, 0.05f),
							//畅鱼-畅鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.FLOWMINNOW.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.FLOWMINNOW_BUCKET.get(), 1), 30, 5, 0.05f),
							//苦鳕-苦鳕桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.BITTER_COD.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.BITTER_COD_BUCKET.get(), 1), 30, 5, 0.05f),
							//灰鳞鱼-灰鳞鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.GREYSCALE.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.GREYSCALE_BUCKET.get(), 1), 30, 5, 0.05f),
							//宝游鱼-宝游鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.JEWELROAMER.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.JEWELROAMER_BUCKET.get(), 1), 30, 5, 0.05f),
							//星闪鱼-星闪鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.STARBRITE.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.STARBRITE_BUCKET.get(), 1), 30, 5, 0.05f),
							//渊鱼-渊鱼桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.ABYSSALER.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.ABYSSALER_BUCKET.get(), 1), 30, 5, 0.05f),
							//日光蟹-日光蟹桶
							new BasicItemListing(new ItemStack(JerotesVillageItems.SUN_CRAB.get(), 1),
									new ItemStack(Items.WATER_BUCKET, 1),
									new ItemStack(JerotesVillageItems.SUN_CRAB_BUCKET.get(), 1), 30, 5, 0.05f),
							//皮革头盔-灵奴束缚面罩
							new BasicItemListing(new ItemStack(Items.LEATHER_HELMET, 1),
									new ItemStack(JerotesVillageItems.SPIRVE_HELMET.get(), 1), 30, 5, 0.05f),
							//皮革胸甲-灵奴束缚手铐
							new BasicItemListing(new ItemStack(Items.LEATHER_CHESTPLATE, 1),
									new ItemStack(JerotesVillageItems.SPIRVE_CHESTPLATE.get(), 1), 30, 5, 0.05f),
							//皮革护腿-灵奴束缚裤子
							new BasicItemListing(new ItemStack(Items.LEATHER_LEGGINGS, 1),
									new ItemStack(JerotesVillageItems.SPIRVE_LEGGINGS.get(), 1), 30, 5, 0.05f),
							//皮革靴子-灵奴束缚脚铐
							new BasicItemListing(new ItemStack(Items.LEATHER_BOOTS, 1),
									new ItemStack(JerotesVillageItems.SPIRVE_BOOTS.get(), 1), 30, 5, 0.05f),
					}
			));

	protected void addOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] arritemListing, int n) {
		ArrayList arrayList = Lists.newArrayList((Object[])arritemListing);
		int n2 = 0;
		while (n2 < n && !arrayList.isEmpty()) {
			MerchantOffer merchantOffer = ((VillagerTrades.ItemListing)arrayList.remove(this.random.nextInt(arrayList.size()))).getOffer(this, this.random);
			if (merchantOffer == null) continue;
			merchantOffers.add(merchantOffer);
			++n2;
		}
	}

	@Override
	public Vec3 getRopeHoldPosition(float f) {
		float f2 = Mth.lerp(f, this.yBodyRotO, this.yBodyRot) * 0.017453292f;
		Vec3 vec3 = new Vec3(0.0, this.getBoundingBox().getYsize() - 1.0, 0.2);
		return this.getPosition(f).add(vec3.yRot(-f2));
	}

	@Override
	public boolean isClientSide() {
		return this.level().isClientSide;
	}
}
