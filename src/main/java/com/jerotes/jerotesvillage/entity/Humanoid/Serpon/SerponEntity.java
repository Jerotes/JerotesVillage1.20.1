package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class SerponEntity extends PathfinderMob implements SpellUseEntity, LoreUndyingEntity, UseDaggerEntity, ShiftKeyDownEntity, WizardEntity, UseThrowEntity, InventoryEntity, CrossbowAttackMob, UseCrossbowEntity, UseBowEntity, NeutralMob, RangedAttackMob, InventoryCarrier, Npc, UseShieldEntity, JerotesEntity, FactionEntity, UseThrownJavelinEntity {
	private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("b3c91c6b-a156-42e2-8bfe-083189792db3");
	private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("5217434d-3249-4efd-94fb-569ad5706a3a");
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("61edd5dc-697a-445a-8be5-dd5ddcce8ffa");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("bf29282a-b559-4716-a590-1824ec1346bd");
	private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> CAN_THROW_SERPON_JAVELIN = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> PREPARE = SynchedEntityData.defineId(SerponEntity.class, EntityDataSerializers.BOOLEAN);
	public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
	private LazyOptional<?> itemHandler = null;

	public SerponEntity(EntityType<? extends SerponEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
		this.setMaxUpStep(5.1f);
		this.applyOpenDoorsAbility();
		this.setCanPickUpLoot(true);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
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
	public boolean isJerotesFlyingMob() {
		return true;
	}

	@Override
	public boolean isFriendFaction(Entity entity) {
		return this.isFriendFactionBase(entity) ||
				entity instanceof LivingEntity livingEntity2 && (livingEntity2.getLastHurtMob() != this && this.getLastHurtByMob() != livingEntity2 && this.getTarget() != livingEntity2 && !(livingEntity2 instanceof Mob mob && mob.getTarget() == this)) && livingEntity2.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get());
	}
	@Override
	public String getFirstFactionTypeName() {
		return "serpon_combat_team";
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
	public boolean wantsToPickUp(ItemStack itemStack) {
		if ((itemStack.canEquip(EquipmentSlot.LEGS,this) || itemStack.canEquip(EquipmentSlot.FEET,this)) && !OtherMainConfig.SerponCanWearAllArmor) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(this, newItem, oldItem);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
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
		this.goalSelector.addGoal(0, new SerponOpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new QoaikuGroundOpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		if (!(this instanceof SerponCombatExecuterEntity) && !(this instanceof SerponFireApostleEntity)) {
			this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
			this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
			this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
			this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
			this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
			this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
			this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
			this.goalSelector.addGoal(1, new SerponRangedAttackGoal<SerponEntity>(this, 0.5, 20, 15.0f));
			this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
			this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));
			this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<SerponEntity>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()) {
				@Override
				public boolean canUse() {
					if (SerponEntity.this.getMainHandItem().is(JerotesVillageItems.SERPON_JAVELIN.get()) && !SerponEntity.this.isCanThrowSerponJavelin()) {
						return false;
					}
					return super.canUse();
				}

				@Override
				public boolean canContinueToUse() {
					if (SerponEntity.this.getMainHandItem().is(JerotesVillageItems.SERPON_JAVELIN.get()) && !SerponEntity.this.isCanThrowSerponJavelin()) {
						return false;
					}
					return super.canContinueToUse();
				}
			});
			this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
			this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<SerponEntity>(this, 1.0, 15.0f));
			this.goalSelector.addGoal(3, new FollowSerponCombatExecuterGoal(this, 1.1f));
		}
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(SerponEntity.this, 1.2, true));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f) {
			@Override
			public boolean canUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f) {
			@Override
			public boolean canUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, SerponEntity.class, 6.0f) {
			@Override
			public boolean canUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 6.0f) {
			@Override
			public boolean canUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(4, new JerotesFlyingRandomStrollGoal(this, 0.8, 10));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canUse();
			}
			@Override
			public boolean canContinueToUse() {
				if (SerponEntity.this instanceof SerponCombatExecuterEntity serponCombatExecuter && !serponCombatExecuter.isTrueCome())
					return false;
				return super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, SerponEntity.class).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HighNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new SerponHelpPlayerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<SerponEntity>(this, true));
	}

	static class SerponRangedAttackGoal<T extends Mob & RangedAttackMob> extends JerotesRangedBowAttackGoal {
		private final SerponEntity entity;

		public SerponRangedAttackGoal(T t, double d, int n, float f) {
			super(t, d, n, f);
			this.entity = (SerponEntity)t;
		}

		@Override
		public boolean canUse() {
			if (this.entity.getTarget() != null && this.entity.distanceToSqr(this.entity.getTarget()) <= 5 && this.entity.getMainHandItem().is(JerotesVillageItems.SERPON_BLOWPIPE.get())) {
				return false;
			}
			return super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			if (this.entity.getTarget() != null && this.entity.distanceToSqr(this.entity.getTarget()) <= 5 && this.entity.getMainHandItem().is(JerotesVillageItems.SERPON_BLOWPIPE.get())) {
				return false;
			}
			return super.canContinueToUse();
		}
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
	public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
		return ProjectileUtil.getMobArrow(this, itemStack, f);
	}
	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem instanceof CrossbowItem || projectileWeaponItem instanceof BowItem || super.canFireProjectileWeapon(projectileWeaponItem);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SERPON_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		List<SerponCombatExecuterEntity> bossList = this.level().getEntitiesOfClass(SerponCombatExecuterEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		bossList.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		if (!bossList.isEmpty()) {
			return 600;
		}
		return super.getAmbientSoundInterval();
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		return JerotesVillageSoundEvents.SERPON_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SERPON_DEATH;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean isOnFire() {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return this.isBaby() ? 1.0f : 1.875f;
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
	public boolean isNoGravity() {
		if (this.fly > 50) {
			return true;
		}
		return super.isNoGravity();
	}

	public int fly = 500;
	public int shieldCoolDown;
	public int shieldCanUse = 1;
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	@Override
	public boolean isBaby() {
		return this.getEntityData().get(DATA_BABY_ID);
	}
	@Override
	public void setBaby(boolean bl) {
		this.getEntityData().set(DATA_BABY_ID, bl);
	}
	public boolean isPrepare() {
		return this.getEntityData().get(PREPARE);
	}
	public void setPrepare(boolean bl) {
		setPrepare(bl, false);
	}
	public void setPrepare(boolean bl, boolean bl2) {
		this.getEntityData().set(PREPARE, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).
					addTransientModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_UUID, "Prepare max health", 1.0, AttributeModifier.Operation.MULTIPLY_BASE));
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).
					addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Prepare attack damage", 0.6, AttributeModifier.Operation.MULTIPLY_BASE));
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).
					addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Prepare knockback resistance", Math.min(0.3f, (1 - this.getAttributeBaseValue(Attributes.KNOCKBACK_RESISTANCE))), AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).
					addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Prepare movement speed", 0.2, AttributeModifier.Operation.MULTIPLY_BASE));
		}
		if (bl2) {
			this.setHealth(this.getMaxHealth());
		}
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
	public boolean isCanThrowSerponJavelin() {
		return this.getEntityData().get(CAN_THROW_SERPON_JAVELIN);
	}
	public void setCanThrowSerponJavelin(boolean bl) {
		this.getEntityData().set(CAN_THROW_SERPON_JAVELIN, bl);
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
	public boolean canUseLeggings() {
		return OtherMainConfig.SerponCanWearAllArmor;
	}
	@Override
	public boolean canUseBoots() {
		return OtherMainConfig.SerponCanWearAllArmor;
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
	public boolean isMagicUseStyle() {
		return this.getSpellTick() > 0;
	}
	@Override
	public boolean isSpellHumanoid() {
		return true;
	}
	public void setCustomNameUseNameTag(@Nullable Component component, Entity self, Entity source, InteractionHand interactionHand) {
        String string;
        if (component != null) {
            string = ChatFormatting.stripFormatting(component.getString());
            if ("Prepare".equals(string) && self instanceof SerponEntity serponEntity) {
				serponEntity.setPrepare(true, true);
            }
        }
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AllTickCount", this.allTickCount);
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
		compoundTag.putBoolean("CanThrowSerponJavelin", this.isCanThrowSerponJavelin());
		compoundTag.putInt("Fly", this.fly);
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putBoolean("IsBaby", this.isBaby());
		compoundTag.putBoolean("IsPrepare", this.isPrepare());
		this.addPersistentAngerSaveData(compoundTag);
		this.writeInventoryToTag(compoundTag);

		compoundTag.putFloat("PrepareHealth", this.getHealth());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.allTickCount = compoundTag.getInt("AllTickCount");
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setBowLevel(compoundTag.getInt("BowLevel"));
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setCombatStyle(compoundTag.getInt("CombatStyle"));
		this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
		this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
		this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
		this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
		this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
		this.setCanThrowSerponJavelin(compoundTag.getBoolean("CanThrowSerponJavelin"));
		this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
		this.fly = compoundTag.getInt("Fly");
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.setBaby(compoundTag.getBoolean("IsBaby"));
		this.setPrepare(compoundTag.getBoolean("IsPrepare"));
		this.readPersistentAngerSaveData((Level)this.level(), compoundTag);
		this.readInventoryFromTag(compoundTag);

		this.setHealth(compoundTag.getFloat("PrepareHealth"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(BOW_LEVEL, 15);
		this.getEntityData().define(SHIELD_LEVEL, 1);
		this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
		this.getEntityData().define(COMBAT_STYLE, 1);
		this.getEntityData().define(IS_CHARGING_CROSSBOW, false);
		this.getEntityData().define(CAN_THROW_SERPON_JAVELIN, false);
		this.getEntityData().define(DATA_BABY_ID, false);
		this.getEntityData().define(PREPARE, false);
		this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
		this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
		this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_BABY_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
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
		return super.mobInteract(player, interactionHand);
	}

	public boolean hasBoss = false;
	public int allTickCount = 0;

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.updateNoActionTime();
		if (this.allTickCount < 10)
			allTickCount ++;
		if (this.allTickCount == 2) {
			String string = ChatFormatting.stripFormatting(this.getName().getString());
			if ("Prepare".equals(string)) {
				this.setPrepare(true);
				this.setHealth(this.getMaxHealth());
			}
		}
		if (this.isPrepare()) {
			if (this.tickCount % 3 == 0) {
				for (int i = 0; i < 4; i++) {
					double angle = (Math.PI * 2 * i) / 4;
					double radius = 0.45;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					this.level().addParticle(ParticleTypes.FLAME, this.getX() + offsetX, this.getY(0.65f), this.getZ() + offsetZ, 0, 0.12, 0);
				}
			}
		}
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		//
		if (this.tickCount % 200 == 0) {
			List<SerponCombatExecuterEntity> bossList = this.level().getEntitiesOfClass(SerponCombatExecuterEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			bossList.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
			hasBoss = !bossList.isEmpty();
		}
		if (!this.level().isClientSide()) {
			if (OtherMainConfig.SerponWeaknessInNonHotBiomes) {
				if (!this.level().getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS) && !this.isPrepare() && !this.isInLava() && !hasBoss) {
					this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0));
				}
			}
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		if (this.fly > 0 && !hasBoss){
			this.fly -= 1;
		}
		else{
			this.fly = 500;
		}
		//空陆切换
		boolean land = this.onGround() || this.fly <= 50 && !hasBoss;
		if (!land && this.isLandNavigatorType) {
			this.moveControl = new FlyingMoveControl(this, 10, true);
			this.navigation = new FlyingPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (land && !this.isLandNavigatorType) {
			this.moveControl = new MoveControl(this);
			this.navigation = new GroundPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//起飞
		if (this.getNavigation().getPath() != null && !this.getNavigation().isDone() && (this.fly > 50 || hasBoss)) {
			BlockState blockState = this.level().getBlockState(this.getNavigation().getPath().getTarget());
			if ((blockState.isAir() || this.getNavigation().getPath().getTarget().getY() > this.getY() + 1 || this.getTarget() != null && !this.getTarget().onGround() || this.getTarget() != null && this.getTarget().getY() > this.getY() + 1) && this.onGround() || this.horizontalCollision) {
				this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5f, 0));
			}
		}
		//
		this.clearFire();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}
		//摧毁骑乘物
		Main.destroyRides(this);
		//使用盾牌和双手武器
		if (!(this instanceof SerponCombatExecuterEntity)) {
			useBlockingItem(this);
		}
		else {
			if (this.shieldCanUse() && this.notBowCrossbow(this, InteractionHand.MAIN_HAND) && this.getOffhandItem().getItem() instanceof ShieldItem && (!this.isUsingItem() || this.getUseItem().getItem() instanceof ShieldItem) && this.attackAnim <= 0.0F) {
				if (!this.isUsingItem()) {
					this.startUsingItem(InteractionHand.OFF_HAND);
				}
			} else if (this.shieldCanUse() && this.notBowCrossbow(this, InteractionHand.OFF_HAND) && this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldItem && (!this.isUsingItem() || this.getUseItem().getItem() instanceof ShieldItem) && this.attackAnim <= 0.0F) {
				if (!this.isUsingItem()) {
					this.startUsingItem(InteractionHand.MAIN_HAND);
				}
			}
		}
		//替换自己的物品
		changeInventory(this);
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
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte)4);
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent() && !(entity instanceof SerponCombatExecuterEntity)) {
				this.playSound(JerotesVillageSoundEvents.SERPON_ATTACK, 1.0f, 1.0f);
			}
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isFireDamage(damageSource)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.is(DamageTypes.EXPLOSION))
			return super.hurt(damageSource, amount/4);
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		this.resetFallDistance();
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

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_PORTAL.get(), this.getX(), this.getY(0.5), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			float weaponRandom = this.random.nextFloat();
			float offhandRandom = this.random.nextFloat();
			this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
			this.setItemSlot(EquipmentSlot.OFFHAND, this.createSpawnOffhand(offhandRandom));
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		SpawnGroupData spawnGroupData1 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		return spawnGroupData1;
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(Items.AIR);
	}

	public ItemStack createSpawnOffhand(float offhandRandom) {
		return new ItemStack(Items.AIR);
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

