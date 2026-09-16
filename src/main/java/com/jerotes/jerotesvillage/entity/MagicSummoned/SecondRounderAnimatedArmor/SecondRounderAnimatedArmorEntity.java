package com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.AnimatedArmorGoHomeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.AnimatedIronArmor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class SecondRounderAnimatedArmorEntity extends MagicSummonedEntity implements SpellUseEntity, UseDaggerEntity, ShiftKeyDownEntity, WizardEntity, UseThrowEntity, UseThrownJavelinEntity, CrossbowAttackMob, RangedAttackMob, UseCrossbowEntity, UseBowEntity, UseShieldEntity {
	private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(SecondRounderAnimatedArmorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Predicate<Entity> RIDABLE_MINECARTS = entity -> entity instanceof AbstractMinecart && ((AbstractMinecart)entity).getMinecartType() == AbstractMinecart.Type.RIDEABLE;

	public SecondRounderAnimatedArmorEntity(EntityType<? extends SecondRounderAnimatedArmorEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0.6f);
		this.setCanPickUpLoot(false);
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(newItem, oldItem, true, true, true, true, true, true, false, false, false, false);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 54);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, 8.0f));
		this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, 8.0f));
		this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, 8.0f));
		this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, 8.0f));
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<SecondRounderAnimatedArmorEntity>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<SecondRounderAnimatedArmorEntity>(this, 1.0, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, 8));
		this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(SecondRounderAnimatedArmorEntity.this, 1.25, true));
		this.goalSelector.addGoal(2, new AnimatedArmorGoHomeGoal(this, 1.0f));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
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
		return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		Item armor = this.getItemBySlot(EquipmentSlot.HEAD).getItem();
		if (armor instanceof ArmorItem && !(armor instanceof AnimatedIronArmor)) {
			return ((ArmorItem) armor).getEquipSound();
		}
		return JerotesVillageSoundEvents.SECOND_ROUNDER_ANIMATED_ARMOR_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		Item armor = this.getItemBySlot(EquipmentSlot.CHEST).getItem();
		if (armor instanceof ArmorItem && !(armor instanceof AnimatedIronArmor)) {
			return ((ArmorItem) armor).getEquipSound();
		}
		return JerotesVillageSoundEvents.SECOND_ROUNDER_ANIMATED_ARMOR_DEATH;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.8f;
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
	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}
	@Override
	public boolean isPushable() {
		if (this.isInSittingPose() && !this.isWander()) {
			return false;
		}
		return super.isPushable();
	}
	@Override
	protected void doPush(Entity entity) {
		if (!this.isInSittingPose() && !this.isWander()) {
			super.doPush(entity);
		}
	}
	@Override
	protected void pushEntities() {
		if (!this.isInSittingPose() && !this.isWander()) {
			super.pushEntities();
		}
		else {
			List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), RIDABLE_MINECARTS);
			for (Entity entity : list) {
				if (!(this.distanceToSqr(entity) <= 0.2)) continue;
				entity.push(this);
			}
		}
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

	public int shieldCoolDown;
	public int shieldCanUse = 1;
	public int stopPoseTick;
	public float stopXRot;
	public float stopYRot;
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	@Override
	public void setTame(boolean bl) {
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
			this.setCanPickUpLoot(false);
			this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 2.0f;
			this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 2.0f;
			this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
			this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0f;
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
		}
		this.reassessTameGoals();
	}
	@Override
	public void setOrderedToSit(boolean bl) {
		this.orderedToSit = bl;
		if (bl) {
			if (!this.level().isClientSide()) {
				this.setHomePos(this.blockPosition());
			}
			this.stopXRot = this.getXRot();
			this.stopYRot = this.getYRot();
			this.setXRot(this.stopXRot);
			this.setYBodyRot(this.stopYRot);
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.TARGET.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
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
	public boolean isStop() {
		return this.getDeltaMovement().length() <= 0.2 &&
				this.stopPoseTick <= 0 &&
				!this.isTame() &&
				!this.isAggressive() &&
				this.getOwner() == null &&
				this.entityData.get(HOME_POS).closerToCenterThan(this.position(), 1.0);
	}
	@Override
	public boolean isInSittingPose() {
		return super.isInSittingPose() || this.isStop();
	}
	@Override
	public boolean isOrderedToSit() {
		return super.isOrderedToSit() || this.isStop();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("BowLevel", this.getBowLevel());
		compoundTag.putInt("ShieldLevel", this.getShieldLevel());
		compoundTag.putInt("CombatStyle", this.getCombatStyle());
		compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putInt("StopPoseTick", this.stopPoseTick);
		compoundTag.putFloat("stopXRot", this.stopXRot);
		compoundTag.putFloat("stopYRot", this.stopYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBowLevel(compoundTag.getInt("BowLevel"));
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setCombatStyle(compoundTag.getInt("CombatStyle"));
		this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.stopPoseTick = compoundTag.getInt("StopPoseTick");
		this.stopXRot = compoundTag.getFloat("stopXRot");
		this.stopYRot = compoundTag.getFloat("stopYRot");
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
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		if (!this.level().isClientSide()) {
			if (this.isTame()) {
				InteractionResult interactionResult;
				if (!player.isShiftKeyDown() && player == this.getOwner()) {
					if (player instanceof ServerPlayer serverPlayer) {
						Main.openMobInventoryGui(serverPlayer, SecondRounderAnimatedArmorEntity.this, false, true, true, true, false, false, false, false);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide());
				}
			}
		}
		return super.mobInteract(player, interactionHand);
	}
	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.isInSittingPose() || this.isWander()) {
			this.updateSwingTime();
			this.updateNoActionTime();
		}
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		//
		if (this.isStop()) {
			if (this.tickCount % 20 == 0) {
				if (!this.level().isClientSide()) {
					this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
					this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
					this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
					this.getNavigation().stop();
					this.setXRot(this.stopXRot);
					this.setYRot(this.stopYRot);
					this.moveTo(this.getHomePos().getX() + 0.5f, this.getHomePos().getY(), this.getHomePos().getZ() + 0.5f, this.stopXRot, this.stopYRot);
					this.setGoingHome(false);
				}
			}
		}
		//传送
		if (!this.isStop() && !this.isTame() && this.getTarget() == null && this.onGround() && !this.level().getBlockState(getHomePos().below()).isAir() && !this.isAggressive() && this.stopPoseTick <= 0 && this.getHomePos().closerToCenterThan(this.getOnPos().getCenter(), 3)) {
			if (!this.level().isClientSide()) {
				this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
				this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
				this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
				this.getNavigation().stop();
				this.setXRot(this.stopXRot);
				this.setYRot(this.stopYRot);
				this.moveTo(this.getHomePos().getX() + 0.5f, this.getHomePos().getY(), this.getHomePos().getZ() + 0.5f, this.stopXRot, this.stopYRot);
				this.setGoingHome(false);
			}
		}
		if (this.stopPoseTick > 0) {
			this.stopPoseTick -= 1;
		}
		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}
		if (this.getTarget() != null) {
			this.stopPoseTick = 300;
		}
		//使用盾牌和双手武器
		useBlockingItem(this);
		if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && this.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && this.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
			this.hurt(this.damageSources().generic(), 5f);
			if (!this.level().isClientSide() && !this.isRemoved() && this.getHealth() < 100) {
				this.level().broadcastEntityEvent(this, (byte)60);
				this.remove(RemovalReason.KILLED);
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
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.level().broadcastEntityEvent(this, (byte) 4);
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
		if (damageSource.is(DamageTypes.IN_WALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isPsychicDamage(damageSource)
				|| AttackFind.isWaterDamage(damageSource)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			this.stopPoseTick = 300;
		}
		return bl;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.BLINDNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.HOLD_MOB.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.PARALYZED.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			RandomSource randomSource = serverLevelAccessor.getRandom();
			this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			float random = this.random.nextFloat();
			float random1 = this.random.nextFloat();
			if (random1 <= 0.25) {
				if (random <= 0.25) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				}
				if (random > 0.25 && random <= 0.75) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				}
				if (random > 0.75 && random <= 0.85) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
				}
				if (random > 0.85 && random <= 0.90) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				}
				if (random > 0.90 && random <= 0.98) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
				}
				if (random > 0.98) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.MEROR_METAL_SWORD.get()));
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.MEROR_METAL_HELMET.get()));
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.MEROR_METAL_CHESTPLATE.get()));
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.MEROR_METAL_LEGGINGS.get()));
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.MEROR_METAL_BOOTS.get()));
				}
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ANIMATED_IRON_HELMET.get()));
				this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ANIMATED_IRON_CHESTPLATE.get()));
				this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ANIMATED_IRON_LEGGINGS.get()));
				this.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ANIMATED_IRON_BOOTS.get()));
				this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.4f;
				this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 0.4f;
				this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 0.4f;
				this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 0.4f;
			}
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		this.stopXRot = this.getXRot();
		this.stopYRot = this.getYRot();
		this.setXRot(this.stopXRot);
		this.setYBodyRot(this.stopYRot);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void die(DamageSource damageSource) {
		net.minecraft.network.chat.Component deathMessage = this.getCombatTracker().getDeathMessage();
		super.die(damageSource);
		if (this.dead)
			if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
				this.getOwner().sendSystemMessage(deathMessage);
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

