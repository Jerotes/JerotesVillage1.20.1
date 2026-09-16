package com.jerotes.jerotesvillage.entity.Humanoid;

import com.google.common.collect.Lists;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.goal.HelpVillagerGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.CarvedFlag;
import com.jerotes.jerotesvillage.item.CarvedHorn;
import com.jerotes.jerotesvillage.item.CarvedHorseSlab;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
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
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class CarvedVillagerEntity extends AbstractVillager implements SpellUseEntity, SkinEntity, FactionEntity, UseDaggerEntity, ShiftKeyDownEntity, WizardEntity, UseThrowEntity, UseThrownJavelinEntity, InventoryEntity, CrossbowAttackMob, NeutralMob, RangedAttackMob, InventoryCarrier, UseCrossbowEntity, UseBowEntity, UseShieldEntity, JerotesEntity, CarvedEntity {
	private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SKIN = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPECIAL_SKIN = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_FEMALE = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> ILLAGER = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PIGLIN = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ENDERMAN = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(CarvedVillagerEntity.class, EntityDataSerializers.INT);
	public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
	private LazyOptional<?> itemHandler = null;
	public AnimationState armWideScaleAnimationState = new AnimationState();
	public AnimationState armSlimScaleAnimationState = new AnimationState();

	public CarvedVillagerEntity(EntityType<? extends CarvedVillagerEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.applyOpenDoorsAbility();
		this.setCanPickUpLoot(true);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.fixupDimensions();
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
	public boolean isOffHandItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof CarvedHorn;
	}


	@Override
	public String getFirstFactionTypeName() {
		return "copper_carved_company";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("villager");
		return list;
	}
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		if (OtherMainConfig.SpawnEggCarvedVillagerHasArmor) {
			this.setItemSlot(equipmentSlot, itemStack);
		}
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

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 24);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3.5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
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
		this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<>(this, 1.0, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(CarvedVillagerEntity.this, 1.2, true));
		this.goalSelector.addGoal(3, new MoveBackToVillageGoal(this, 0.6, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, AbstractVillager.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, IronGolem.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, CarvedAllayEntity.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, CarvedHoundEntity.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedLlamaEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, CarvedEntity.class));
		this.targetSelector.addGoal(1, new JerotesHelpCarvedGoal(this));
		this.targetSelector.addGoal(1, new HelpVillagerGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		if (OtherMainConfig.CarvedObligation) {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> (mob instanceof Enemy || OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId())) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
		}
		else {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Raider>(this, Raider.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Zombie>(this, Zombie.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
		}
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, livingEntity -> livingEntity instanceof Player player && RelationshipEvent.LessCopperCarvedCompanyRelationship(player, -500) && !this.trusts(player.getUUID())));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<CarvedVillagerEntity>(this, true));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isRangeJavelin(handItem)) {
			useTridentShoot(this, livingEntity);
		}
		if (InventoryEntity.isBow(handItem)) {
			useBowShoot(this, livingEntity, f, getBowLevel(), 20);
		}
		if (InventoryEntity.isThrow(handItem)) {
			useThrowShoot(this, livingEntity);
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
			}
		}
		if (InventoryEntity.isCrossbow(handItem)) {
			useCrossbowShoot(this, 3.15F);
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
	}


	@Override
	public ItemStack getProjectile(ItemStack itemStack) {
		if (itemStack.getItem() instanceof ProjectileWeaponItem projectileWeaponItem) {
			Predicate<ItemStack> predicate = projectileWeaponItem.getSupportedHeldProjectiles();
			ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			if (predicate.test(this.getMainHandItem())) {
				itemStack2 = this.getMainHandItem();
			}
			else if (predicate.test(this.getOffhandItem())) {
				itemStack2 = this.getOffhandItem();
			}
			else {
				for (int n = 0; n < this.inventoryCount(); ++n) {
					ItemStack finds = this.mobInventory().getItem(n);
					if (predicate.test(finds)) {
						itemStack2 = finds;
						break;
					}
				}
			}
			return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
		return ProjectileUtil.getMobArrow(this, itemStack, f);
	}

	@Override
	public boolean isChargingCrossbow() {
		return this.entityData.get(IS_CHARGING_CROSSBOW);
	}

	@Override
	public void setChargingCrossbow(boolean bl) {
		this.entityData.set(IS_CHARGING_CROSSBOW, bl);
	}

	@Override
	public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectile, float f) {
		this.shootCrossbowProjectile(this, livingEntity, projectile, f, itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.getShootingPower(itemStack) : 3.15f);
	}
	@Override
	public void shootCrossbowProjectile(LivingEntity livingEntity, LivingEntity livingEntity2, Projectile projectile, float f, float f2) {
		double d0 = livingEntity2.getX() - livingEntity.getX();
		double d1 = livingEntity2.getZ() - livingEntity.getZ();
		double d2 = Math.sqrt(d0 * d0 + d1 * d1);
		double d3 = livingEntity2.getY(0.3333333333333333) - projectile.getY() + d2 * 0.1;
		Vector3f vector3f = this.getProjectileShotVector(livingEntity, new Vec3(d0, d3, d1), f);
		projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f2, Math.max(0, 20 - getBowLevel()) / 5f);
		livingEntity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (livingEntity.getRandom().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void onCrossbowAttackPerformed() {
		this.noActionTime = 0;
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.tickCount <= 20) {
			return null;
		}
		if (this.isSleeping()) {
			return null;
		}
		if (isSkin()) {
			return null;
		}
		if (this.isEndermanRace()) {
			return this.isAggressive() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
		}
		if (this.isIllagerRace()) {
			return SoundEvents.PILLAGER_AMBIENT;
		}
		if (this.isPiglinRace()) {
			return SoundEvents.PIGLIN_AMBIENT;
		}
		return SoundEvents.VILLAGER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		if (isSkin()) {
			return damageSource.type().effects().sound();
		}
		if (this.isEndermanRace()) {
			return SoundEvents.ENDERMAN_HURT;
		}
		if (this.isIllagerRace()) {
			return SoundEvents.PILLAGER_HURT;
		}
		if (this.isPiglinRace()) {
			return SoundEvents.PIGLIN_HURT;
		}
		return SoundEvents.VILLAGER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		if (isSkin()) {
			return SoundEvents.PLAYER_DEATH;
		}
		if (this.isEndermanRace()) {
			return SoundEvents.ENDERMAN_DEATH;
		}
		if (this.isIllagerRace()) {
			return SoundEvents.PILLAGER_DEATH;
		}
		if (this.isPiglinRace()) {
			return SoundEvents.PIGLIN_DEATH;
		}
		return SoundEvents.VILLAGER_DEATH;
	}
	@Override
	protected void updateTrades() {
	}
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.CARVED_VILLAGER.get().create(serverLevel);
	}
	@Override
	protected void rewardTradeXp(MerchantOffer merchantOffer) {
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	protected Component getTypeName() {
		if (this.isEndermanRace())
			return Component.translatable("entity.jerotesvillage.carved_villager.enderman");
		if (this.isPiglinRace())
			return Component.translatable("entity.jerotesvillage.carved_villager.piglin");
		if (this.isIllagerRace())
			return Component.translatable("entity.jerotesvillage.carved_villager.illager");
		return Component.translatable(this.getType().getDescriptionId());
	}
	public boolean isEmerald(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.EMERALD)).test(stack);
	}
	@Override
	public void setCustomName(Component component) {
		String string = ChatFormatting.stripFormatting(component.getString());
		super.setCustomName(component);
		if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameWide.contains(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameSlim.contains(string)) {
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Steve".equals(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Alex".equals(string)) {
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Ari".equals(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Kai".equals(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Noor".equals(string)) {
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Sunny".equals(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Zuri".equals(string)) {
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Efe".equals(string)) {
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Makena".equals(string)) {
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if ("Enderman".equals(string)) {
			this.setSkin(false);
			this.setEndermanRace(true);
			this.setIllagerRace(false);
			this.setPiglinRace(false);
		}
		else if ("Piglin".equals(string)) {
			this.setSkin(false);
			this.setEndermanRace(false);
			this.setIllagerRace(false);
			this.setPiglinRace(true);
		}
		else if ("Illager".equals(string)) {
			this.setSkin(false);
			this.setEndermanRace(false);
			this.setIllagerRace(true);
			this.setPiglinRace(false);
		}
		else if ("Villager".equals(string)) {
			this.setSkin(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else {
			this.setSkin(false);
		}
		this.reapplyPosition();
		this.refreshDimensions();
	}
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		//0.6f, 2.9f
		if (this.isEndermanRace() && !this.isSkin()) {
			return super.getDimensions(pose).scale(1f,2.9f/1.8f);
		}
		//0.6f, 1.95f
		if (this.isPiglinRace() && !this.isSkin()) {
			return super.getDimensions(pose).scale(1f,1.95f/1.8f);
		}
		//0.6f, 1.8f
		return super.getDimensions(pose);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isEndermanRace() && !this.isSkin()) {
			return this.isBaby() ? 1.275f : 2.55f;
		}
		if (this.isPiglinRace() && !this.isSkin()) {
			float f = 1.79f;
			return this.isBaby() ? f - 0.82f : f;
		}
		return this.isBaby() ? 0.93f : 1.62f;
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		if (this.isEndermanRace() && !this.isSkin()) {
			return new Vector3f(0.0f, entityDimensions.height - 0.09375f * f, 0.0f);
		}
		return new Vector3f(0.0f, entityDimensions.height + 0.0625f * f, 0.0f);
	}
	@Override
	public boolean isSensitiveToWater() {
		return this.isEndermanRace() && !this.isSkin() && this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && this.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && this.getItemBySlot(EquipmentSlot.FEET).isEmpty();
	}
	protected float ridingOffset(Entity entity) {
		return -0.7f;
	}
	public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.45D;
	}

	public int shieldCoolDown;
	public int shieldCanUse = 1;
	public int hornCoolDown;
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	public boolean isSkin() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (this.getCustomName() != null) {
			if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameWide.contains(string)) {
				return true;
			}
			if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameSlim.contains(string)) {
				return true;
			}
		}
		return this.getEntityData().get(SKIN);
	}
	public void setSkin(boolean bl) {
		this.getEntityData().set(SKIN, bl);
	}
	public int getSpecialSkin() {
		return this.getEntityData().get(SPECIAL_SKIN);
	}
	public void setSpecialSkin(int n) {
		this.getEntityData().set(SPECIAL_SKIN, n);
	}
	public boolean isIllagerRace() {
		return this.getEntityData().get(ILLAGER);
	}
	public boolean isPiglinRace() {
		return this.getEntityData().get(PIGLIN);
	}
	public boolean isEndermanRace() {
		return this.getEntityData().get(ENDERMAN);
	}
	public void setIllagerRace(boolean bl) {
		this.getEntityData().set(ILLAGER, bl);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public void setPiglinRace(boolean bl) {
		this.getEntityData().set(PIGLIN, bl);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public void setEndermanRace(boolean bl) {
		this.getEntityData().set(ENDERMAN, bl);
		if (bl) {
			if (this.getAttributeBaseValue(Attributes.MAX_HEALTH) < 40) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
			}
		}
		else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24);
		}
		this.reapplyPosition();
		this.refreshDimensions();
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
		if (!this.level().isClientSide()) {
			this.setSpellTick(10);
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
	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public void setBowLevel(int n){
		this.getEntityData().set(BOW_LEVEL, n);
	}
	public int getBowLevel(){
		return this.getEntityData().get(BOW_LEVEL);
	}
	public void setShieldLevel(int n){
		this.getEntityData().set(SHIELD_LEVEL, n);
	}
	public int getShieldLevel(){
		return this.getEntityData().get(SHIELD_LEVEL);
	}
	@Override
	public boolean IsFemale(){
		return this.getEntityData().get(IS_FEMALE);
	}
	public void setFemale(boolean bl){
		this.getEntityData().set(IS_FEMALE, bl);
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
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listTag = new ListTag();
		for (UUID uUID : list) {
			if (uUID == null) continue;
			listTag.add(NbtUtils.createUUID(uUID));
		}
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("BowLevel", this.getBowLevel());
		compoundTag.putInt("ShieldLevel", this.getShieldLevel());
		compoundTag.putInt("CombatStyle", this.getCombatStyle());
		compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
		compoundTag.putBoolean("IsNoCombatEmptyWeapon", this.isNoCombatEmptyWeapon());
		compoundTag.putBoolean("IsNoCombatEmptyShield", this.isNoCombatEmptyShield());
		compoundTag.putInt("ChangeInventoryCooldownTick", this.getChangeInventoryCooldownTick());
		compoundTag.putBoolean("IsCanChangeInventory", this.isCanChangeInventory());
		compoundTag.putBoolean("IsCanChangeMeleeOrRange", this.isCanChangeMeleeOrRange());
		compoundTag.putBoolean("IsFemale", this.IsFemale());
		compoundTag.putBoolean("IsSkin", this.isSkin());
		compoundTag.putBoolean("IsIllager", this.isIllagerRace());
		compoundTag.putBoolean("IsPiglin", this.isPiglinRace());
		compoundTag.putBoolean("IsEnderman", this.isEndermanRace());
		compoundTag.put("Trusted", listTag);
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putInt("HornCoolDown", this.hornCoolDown);
		compoundTag.putInt("SpecialSkin", this.getSpecialSkin());
		this.addPersistentAngerSaveData(compoundTag);
		this.writeInventoryToTag(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		ListTag listTag = compoundTag.getList("Trusted", 11);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setBowLevel(compoundTag.getInt("BowLevel"));
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setCombatStyle(compoundTag.getInt("CombatStyle"));
		this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.hornCoolDown = compoundTag.getInt("HornCoolDown");
		for (Tag tag : listTag) {
			this.addTrustedUUID(NbtUtils.loadUUID(tag));
		}
		this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
		this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
		this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
		this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
		this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
		this.setSkin(compoundTag.getBoolean("IsSkin"));
		this.setFemale(compoundTag.getBoolean("IsFemale"));
		this.setIllagerRace(compoundTag.getBoolean("IsIllager"));
		this.setPiglinRace(compoundTag.getBoolean("IsPiglin"));
		if (compoundTag.getBoolean("IsEnderman")) {
			this.setEndermanRace(compoundTag.getBoolean("IsEnderman"));
		}
		//
		if (compoundTag.getInt("SpecialSkin") == 1) {
			this.setCustomName(Component.literal("Sentnes"));
			this.setSpecialSkin(1);
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if (compoundTag.getInt("SpecialSkin") == 2) {
			this.setCustomName(Component.literal("Maid"));
			this.setSpecialSkin(2);
			this.setSkin(true);
			this.setFemale(true);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		else if (compoundTag.getInt("SpecialSkin") == 3) {
			this.setCustomName(Component.literal("Jerotes"));
			this.setSpecialSkin(3);
			this.setSkin(true);
			this.setFemale(false);
			this.setEndermanRace(false);
			this.setPiglinRace(false);
			this.setIllagerRace(false);
		}
		//
		this.readPersistentAngerSaveData((Level)this.level(), compoundTag);
		this.readInventoryFromTag(compoundTag);
		this.setBoundingBox(this.makeBoundingBox());
		if (compoundTag.contains("Health", 99)) {
			this.setHealth(compoundTag.getFloat("Health"));
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(BOW_LEVEL, 15);
		this.getEntityData().define(SHIELD_LEVEL, 1);
		this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
		this.getEntityData().define(COMBAT_STYLE, 1);
		this.getEntityData().define(IS_FEMALE, false);
		this.getEntityData().define(SKIN, false);
		this.getEntityData().define(SPECIAL_SKIN, 0);
		this.getEntityData().define(ILLAGER, false);
		this.getEntityData().define(PIGLIN, false);
		this.getEntityData().define(ENDERMAN, false);
		this.getEntityData().define(DATA_TRUSTED_ID_0, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_1, Optional.empty());
		this.getEntityData().define(DATA_TYPE_ID, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(IS_CHARGING_CROSSBOW, false);
		this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
		this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
		this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (SKIN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (SPECIAL_SKIN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ILLAGER.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (PIGLIN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ENDERMAN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	public List<UUID> getTrustedUUIDs() {
		ArrayList arrayList = Lists.newArrayList();
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse(null));
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse(null));
		return arrayList;
	}
	void addTrustedUUID(@Nullable UUID uUID) {
		if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
			this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(uUID));
		} else {
			this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(uUID));
		}
	}
	@Override
	protected void onOffspringSpawnedFromEgg(Player player, Mob mob) {
		this.addTrustedUUID(player.getUUID());
	}
	@Override
	public boolean trusts(UUID uUID) {
		return this.getTrustedUUIDs().contains(uUID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (itemstack.getItem() instanceof CarvedFlag) {
			float f1 = this.getVoicePitch();
			if (!this.isSilent()) {
				if (isSkin()) {
					this.playSound(SoundEvents.PLAYER_ATTACK_NODAMAGE, 1.0f, f1);
				}
				else if (isEndermanRace()) {
					this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, f1);
				}
				else if (this.isIllagerRace()) {
					this.playSound(SoundEvents.PILLAGER_CELEBRATE, 1.0f, f1);
				}
				else if (this.isPiglinRace()) {
					this.playSound(SoundEvents.PIGLIN_CELEBRATE, 1.0f, f1);
				}
				else {
					this.playSound(SoundEvents.VILLAGER_YES, 1.0f, f1);
				}
			}
			return InteractionResult.PASS;
		}
		if (!this.isAggressive() && this.isEmerald(itemstack)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.JUMP, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.LUCK, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 12000, 2));
				this.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 12000, 4));
				this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 660, 1));
				this.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 3));
			}
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				if (isSkin()) {
					this.playSound(SoundEvents.PLAYER_ATTACK_NODAMAGE, 1.0f, f1);
				}
				else if (isEndermanRace()) {
					this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, f1);
				}
				else if (this.isIllagerRace()) {
					this.playSound(SoundEvents.PILLAGER_CELEBRATE, 1.0f, f1);
				}
				else if (this.isPiglinRace()) {
					this.playSound(SoundEvents.PIGLIN_CELEBRATE, 1.0f, f1);
				}
				else {
					this.playSound(SoundEvents.VILLAGER_YES, 1.0f, f1);
				}
			}
			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			this.setCanPickUpLoot(false);
			this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 2.0f;
			this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
			this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0f;
			if (!(this.trusts(player.getUUID()))) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
			}
			this.addTrustedUUID(player.getUUID());
			return InteractionResult.SUCCESS;
		}
		if (this.trusts(player.getUUID()) && !(this.isEmerald(itemstack) || itemstack.getItem() instanceof CarvedFlag || itemstack.getItem() instanceof CarvedHorseSlab)) {
			if (!player.isSecondaryUseActive()) {
				if (player instanceof ServerPlayer serverPlayer) {
					Main.openMobInventoryGui(serverPlayer, CarvedVillagerEntity.this, false, true, true, true, true, true, true, true);
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide());
			}
		}
		return super.mobInteract(player, interactionHand);
	}

	public boolean trustsBy(Player player) {float f = (float)this.getY();
		Vec3 vec3 = this.getDeltaMovement();
		for (int i = 0; i < 10; ++i) {
			float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
			float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
			((Level)this.level()).addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
		}
		this.setCanPickUpLoot(false);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 2.0f;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 2.0f;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 2.0f;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 2.0f;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0f;
		this.addTrustedUUID(player.getUUID());
		return true;
	}

//	@Override
//	public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
//		ItemStack itemstack = player.getItemInHand(interactionHand);
//		if (this.isEmerald(itemstack) || itemstack.getItem() instanceof CarvedFlag || itemstack.getItem() instanceof CarvedHorseSlab || player.isShiftKeyDown()) {
//			return InteractionResult.FAIL;
//		}
//		if (player.isSpectator()) {
//			return InteractionResult.SUCCESS;
//		}
//		else{
//			if(this.trusts(player.getUUID()) && !(player.getMainHandItem().getItem() instanceof CarvedFlag) && !(player.getMainHandItem().getItem() instanceof CarvedHorseSlab)) {
//				EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemstack);
//				if (itemstack.getItem() instanceof BannerItem) {
//					equipmentSlot = EquipmentSlot.HEAD;
//				}
//				if (itemstack.getItem() instanceof CarvedHorn) {
//					equipmentSlot = EquipmentSlot.OFFHAND;
//				}
//				if (itemstack.isEmpty()) {
//					EquipmentSlot equipmentSlot2;
//					EquipmentSlot equipmentSlot3 = this.getClickedSlot(vec3);
//                    equipmentSlot2 = this.isDisabled(equipmentSlot3) ? equipmentSlot : equipmentSlot3;
//                    if (this.hasItemInSlot(equipmentSlot2) && this.swapItem(player, equipmentSlot2, itemstack, interactionHand)) {
//						return InteractionResult.SUCCESS;
//					}
//				}
//				else {
//					if (this.swapItem(player, equipmentSlot, itemstack, interactionHand)) {
//						return InteractionResult.SUCCESS;
//					}
//				}
//			}
//		}
//		return InteractionResult.PASS;
//	}

//	private boolean isDisabled(EquipmentSlot equipmentSlot) {
//		return false;
//	}

//	private EquipmentSlot getClickedSlot(Vec3 vec3) {
//		EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
//		boolean bl = this.isBaby();
//		double d = bl ? vec3.y * 2.0 : vec3.y;
//		EquipmentSlot equipmentSlot2 = EquipmentSlot.FEET;
//		if (d >= 0.1) {
//			double d2 = bl ? 0.8 : 0.45;
//			if (d < 0.1 + d2 && this.hasItemInSlot(equipmentSlot2)) {
//				return EquipmentSlot.FEET;
//			}
//		}
//		double d3 = bl ? 0.3 : 0.0;
//		if (d >= 0.9 + d3) {
//			double d4 = bl ? 1.0 : 0.7;
//			if (d < 0.9 + d4 && this.hasItemInSlot(EquipmentSlot.CHEST)) {
//				return EquipmentSlot.CHEST;
//			}
//		}
//		if (d >= 0.4) {
//			double d5 = bl ? 1.0 : 0.8;
//			if (d < 0.4 + d5 && this.hasItemInSlot(EquipmentSlot.LEGS)) {
//				return EquipmentSlot.LEGS;
//			}
//		}
//		if (d >= 1.6 && this.hasItemInSlot(EquipmentSlot.HEAD)) {
//			return EquipmentSlot.HEAD;
//		}
//		if (this.hasItemInSlot(EquipmentSlot.MAINHAND)) return equipmentSlot;
//		if (!this.hasItemInSlot(EquipmentSlot.OFFHAND)) return equipmentSlot;
//		return EquipmentSlot.OFFHAND;
//	}

//	private boolean swapItem(Player player, EquipmentSlot equipmentSlot, ItemStack itemStack, InteractionHand interactionHand) {
//		//旗帜
//		if (this.getItemBySlot(equipmentSlot).getItem() instanceof BannerItem) {
//			equipmentSlot = EquipmentSlot.HEAD;
//		}
//		//铜刻号角
//		if (this.getItemBySlot(equipmentSlot).getItem() instanceof CarvedHorn && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
//			equipmentSlot = EquipmentSlot.OFFHAND;
//		}
//		//相同物品副手
//		if (itemStack.getItem() == Items.TOTEM_OF_UNDYING && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
//			equipmentSlot = EquipmentSlot.OFFHAND;
//		}
//		//特殊 虚洞渔网 镇山旗矛 优先手部
//		if ((itemStack.getItem() instanceof VirtualCaveFishingNet ||
//				itemStack.getItem() instanceof VirtualCaveFishingNetBlockItem ||
//				itemStack.getItem() instanceof MountainRealmFlagSpear) && !this.getItemBySlot(equipmentSlot).isEmpty()) {
//			equipmentSlot = EquipmentSlot.MAINHAND;
//		}
//		//穿戴相同物品则为主手
//		if (this.getItemBySlot(equipmentSlot) == itemStack) {
//			equipmentSlot = EquipmentSlot.MAINHAND;
//		}
//		//常规
//		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
//		if (player.getAbilities().instabuild && itemStack2.isEmpty() && !itemStack.isEmpty()) {
//			this.setItemSlot(equipmentSlot, itemStack.copyWithCount(1));
//			return true;
//		}
//		if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
//			if (!itemStack2.isEmpty()) {
//				return false;
//			}
//			this.setItemSlot(equipmentSlot, itemStack.split(1));
//			return true;
//		}
//		this.setItemSlot(equipmentSlot, itemStack);
//		player.setItemInHand(interactionHand, itemStack2);
//		return true;
//	}

	@Override
	public void tick() {
		super.tick();
		if (!this.IsFemale()) {
			this.armSlimScaleAnimationState.stop();
			this.armWideScaleAnimationState.startIfStopped(this.tickCount);
		} else {
			this.armWideScaleAnimationState.stop();
			this.armSlimScaleAnimationState.startIfStopped(this.tickCount);
		}

		if (this.isEndermanRace() && !this.isSkin()) {
			this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
		}
		else {
			this.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
		}

		if (this.isEndermanRace() && !this.isSkin()) {
			if (this.getTarget() != null) {
				if (this.getTarget().distanceToSqr(this) < 16.0 && this.random.nextFloat() > 0.95f) {
					this.teleport();
				}
				else {
                    if (this.getTarget().distanceToSqr(this) > 256.0 && this.random.nextFloat() > 0.95f) {
                        this.teleportTowards(this.getTarget());
                    }
                }
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		this.updateSwingTime();
		this.updateNoActionTime();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		//
		if (this.isEndermanRace() && !this.isSkin()) {
			if (this.level().isClientSide) {
				for (int i = 0; i < 2; ++i) {
					this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
				}
			}
		}
		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}

		if (this.hornCoolDown > 0){
			this.hornCoolDown -= 1;
		}

		//骑乘生物速度
		if (this.isAlive() && this.getVehicle() != null && this.getVehicle() instanceof LivingEntity livingVehicle && livingVehicle.isAlive()) {
			if (!this.level().isClientSide) {
				livingVehicle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, false, false));
			}
		}
		//

		//摧毁骑乘物
		Main.destroyRides(this);
		//使用盾牌和双手武器
		useBlockingItem(this);
		//替换自己的物品
		changeInventory(this);

		//号角
		if (this.isAggressive() && this.getTarget() != null && this.getTarget().isAlive() && this.getOffhandItem().getItem() instanceof CarvedHorn) {
			if (this.random.nextInt(40) == 1 && this.hornCoolDown <= 0) {
				PlayerTeam teams = (PlayerTeam) this.getTeam();
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CARVED_HORN_USE, SoundSource.NEUTRAL, 20.0f, 1.0f / (this.level().getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
				double d = Double.MAX_VALUE;
				for (LivingEntity livingEntity : list) {
					if (livingEntity == null) continue;
					if ((livingEntity.distanceToSqr(livingEntity)) > d) continue;


					//有队伍
					if (teams != null) {
						if (!this.isAlliedTo(livingEntity)) continue;
					}
					//常规
					else {
						if (!(OtherEntityFactionFind.isCarved(livingEntity.getType())) && !(livingEntity instanceof Player player1 && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player1, 500))) continue;
					}
					if (livingEntity == this.getTarget()) continue;
					if (livingEntity instanceof Mob mob && this == mob.getTarget()) continue;


					if (!this.level().isClientSide) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 0));
						livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0));
						livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0));
						livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 0));
					}
				}
				this.hornCoolDown = 120;
			}
		}
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 1.1;
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
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
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		if (!this.level().isClientSide) {
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 660, 0));
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		if (this.isEndermanRace() && !this.isSkin() && damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
			boolean bl = damageSource.getDirectEntity() instanceof ThrownPotion;
			boolean bl2 = bl && this.hurtWithCleanWater(damageSource, (ThrownPotion)damageSource.getDirectEntity(), f);
			for (int i = 0; i < 64; ++i) {
				if (!this.teleport()) continue;
				return true;
			}
			return bl2;
		}
		if (this.isEndermanRace() && !this.isSkin() && !this.level().isClientSide() && !(damageSource.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
			this.teleport();
		}
		boolean bl = super.hurt(damageSource, f);
		if (bl) {
			if (damageSource.getEntity() instanceof Player player && !this.trusts(player.getUUID()) && this.getTarget() == null) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -2);
			}
		}
		return bl;
	}

	private boolean hurtWithCleanWater(DamageSource damageSource, ThrownPotion thrownPotion, float f) {
		boolean bl;
		ItemStack itemStack = thrownPotion.getItem();
		Potion potion = PotionUtils.getPotion(itemStack);
		List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
		boolean bl2 = bl = potion == Potions.WATER && list.isEmpty();
		if (bl) {
			return super.hurt(damageSource, f);
		}
		return false;
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
		if (this.trusts(entity.getUUID())) {
			return true;
		}
		if (entity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (this.trusts(livingEntity.getUUID())) {
			return false;
		}
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		if (OtherMainConfig.CarvedCanNotAttackList.contains(livingEntity.getEncodeId())) {
			return false;
		}
		if (livingEntity instanceof DefectorEntity defector && defector.getTarget() != this && !defector.isMustEnemy()) {
			return false;
		}
		if (livingEntity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	protected boolean teleport() {
		if (this.level().isClientSide() || !this.isAlive()) {
			return false;
		}
		double d = this.getX() + (this.random.nextDouble() - 0.5) * 32.0;
		double d2 = this.getY() + (double)(this.random.nextInt(32) - 16);
		double d3 = this.getZ() + (this.random.nextDouble() - 0.5) * 32.0;
		return this.teleport(d, d2, d3);
	}

	boolean teleportTowards(Entity entity) {
		Vec3 vec3 = new Vec3(this.getX() - entity.getX(), this.getY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
		vec3 = vec3.normalize();
		double d = 8.0;
		double d2 = this.getX() + (this.random.nextDouble() - 0.5) * 4.0 - vec3.x * 8.0;
		double d3 = this.getY() + (double)(this.random.nextInt(8) - 4) - vec3.y * 8.0;
		double d4 = this.getZ() + (this.random.nextDouble() - 0.5) * 4.0 - vec3.z * 8.0;
		return this.teleport(d2, d3, d4);
	}

	private boolean teleport(double d, double d2, double d3) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(d, d2, d3);
		while (mutableBlockPos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(mutableBlockPos).blocksMotion()) {
			mutableBlockPos.move(Direction.DOWN);
		}
		BlockState blockState = this.level().getBlockState(mutableBlockPos);
		boolean bl = blockState.blocksMotion();
		boolean bl2 = blockState.getFluidState().is(FluidTags.WATER);
		if (!bl || bl2) {
			return false;
		}
		Vec3 vec3 = this.position();
		boolean bl3 = this.randomTeleport(d, d2, d3, true);
		if (bl3) {
			this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
			if (!this.isSilent()) {
				this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
				this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
			}
		}
		return bl3;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
			if (randomSourceSkin.nextFloat() < 0.05f) {
				this.setEndermanRace(false);
				this.setIllagerRace(true);
				this.setPiglinRace(false);
			}
			else if (randomSourceSkin.nextFloat() < 0.075f) {
				this.setEndermanRace(false);
				this.setIllagerRace(false);
				this.setPiglinRace(true);
			}
			else if (randomSourceSkin.nextFloat() < 0.08f) {
				this.setEndermanRace(true);
				this.setIllagerRace(false);
				this.setPiglinRace(false);
			}
			else {
				this.setEndermanRace(false);
				this.setIllagerRace(false);
				this.setPiglinRace(false);
			}
			this.setHealth(this.getMaxHealth());
			float weaponRandom = this.random.nextFloat();
			this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.6) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_JAVELIN.get());
		}
		if (weaponRandom > 0.5) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_AXE.get());
		}
		if (weaponRandom > 0.1) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_SWORD.get());
		}
		return new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get());
	}

	@Override
	public void die(DamageSource damageSource) {
		if (damageSource.getEntity() instanceof Player player) {
			RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -10);
		}
		if (!this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && !this.getTrustedUUIDs().isEmpty()) {
			List<UUID> list = this.getTrustedUUIDs();
			for (UUID uUID : list) {
				if (uUID == null) continue;
				if (!(this.level() instanceof ServerLevel)) continue;
				if (this.level() instanceof ServerLevel serverLevel && serverLevel.getEntity(uUID) instanceof Player player) {
					player.sendSystemMessage(this.getCombatTracker().getDeathMessage());
				}
			}
		}
		super.die(damageSource);
	}


	public SoundEvent getNotifyTradeSound() {
		if (isSkin()) {
			return SoundEvents.PLAYER_ATTACK_NODAMAGE;
		}
		else if (isEndermanRace()) {
			return SoundEvents.ENDERMAN_TELEPORT;
		}
		else if (this.isIllagerRace()) {
			return SoundEvents.PILLAGER_CELEBRATE;
		}
		else if (this.isPiglinRace()) {
			return SoundEvents.PIGLIN_CELEBRATE;
		}
		else {
			return SoundEvents.VILLAGER_YES;
		}
	}

	protected SoundEvent getTradeUpdatedSound(boolean p_35323_) {
		if (isSkin()) {
			return p_35323_ ? SoundEvents.PLAYER_ATTACK_NODAMAGE : SoundEvents.PLAYER_ATTACK_CRIT;
		}
		else if (isEndermanRace()) {
			return p_35323_ ? SoundEvents.ENDERMAN_TELEPORT : SoundEvents.ENDERMAN_SCREAM;
		}
		else if (this.isIllagerRace()) {
			return p_35323_ ? SoundEvents.PILLAGER_CELEBRATE : SoundEvents.PILLAGER_HURT;
		}
		else if (this.isPiglinRace()) {
			return p_35323_ ? SoundEvents.PIGLIN_CELEBRATE : SoundEvents.PIGLIN_ANGRY;
		}
		else {
			return p_35323_ ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
		}
	}

	public void playCelebrateSound() {
		if (isSkin()) {
			this.playSound(SoundEvents.PLAYER_ATTACK_NODAMAGE, this.getSoundVolume(), this.getVoicePitch());
		}
		else if (isEndermanRace()) {
			this.playSound(SoundEvents.ENDERMAN_TELEPORT, this.getSoundVolume(), this.getVoicePitch());
		}
		else if (this.isIllagerRace()) {
			this.playSound(SoundEvents.PILLAGER_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
		}
		else if (this.isPiglinRace()) {
			this.playSound(SoundEvents.PIGLIN_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
		}
		else {
			this.playSound(SoundEvents.VILLAGER_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
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
}
