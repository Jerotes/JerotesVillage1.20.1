package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.UseBowEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedBowAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SecondRounderSkeletonEntity extends AbstractSkeleton implements JerotesEntity, WickedRidiculeCallsEntity, UseBowEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.INT);

	public SecondRounderSkeletonEntity(EntityType<? extends SecondRounderSkeletonEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}
	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(newItem, oldItem, true, true, false, false, false, false, true, true, true, true);
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.is(Items.BONE_MEAL)) {
			return false;
		}
		if (itemStack.is(Items.GLOW_INK_SAC)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<SecondRounderSkeletonEntity>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(SecondRounderSkeletonEntity.this, 1.2, true));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isBow(handItem)) {
			int n = this.getBowLevel();
			if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).WickedRidiculeNight) {
				n = Math.max(18, this.getBowLevel());
			}
			useBowShoot(this, livingEntity, f, n, 20);
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SKELETON_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}
	@Override
	protected @NotNull SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		Creeper creeper;
		super.dropCustomDeathLoot(damageSource, n, bl);
		Entity entity = damageSource.getEntity();
		if (entity instanceof Creeper && (creeper = (Creeper)entity).canDropMobsSkull()) {
			creeper.increaseDroppedSkulls();
			this.spawnAtLocation(JerotesVillageItems.SECOND_ROUNDER_SKELETON_SKULL.get());
		}
	}
	@Override
	public void reassessWeaponGoal() {
		if (this.level() != null && !this.level().isClientSide) {
			this.goalSelector.removeGoal(new MeleeAttackGoal(this, 1.2D, false));
			this.goalSelector.removeGoal(new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F));
		}
	}
	@Override
	public void addInWickedRidiculeNight(boolean bl) {
		if (!this.level().isClientSide()) {
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DEEP_BLESSING.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get(), 5, 0), this);

			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 0), this);
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		if (this.getSpawnType() != MobSpawnType.CONVERSION) {
			if (newSpawn == 0) {
				float f = 0.3f;
				if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).WickedRidiculeNight) {
					f = 0.8f;
				}
				if (this.random.nextFloat() <= f) {
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				}
				if (this.random.nextFloat() <= f) {
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				}
				if (this.random.nextFloat() <= f) {
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				}
				if (this.random.nextFloat() <= f) {
					this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
				}
				float random = this.random.nextFloat();
				if (random <= 0.6) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
				} else if (random <= 0.85 && random > 0.6) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				} else {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				}
			}
			else {
				super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			}
		}
		else {
			super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
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
	public void aiStep() {
		super.aiStep();
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
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
	public int newSpawn;

	public void setBowLevel(int n){
		this.getEntityData().set(BOW_LEVEL, n);
	}
	public int getBowLevel(){
		return this.getEntityData().get(BOW_LEVEL);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("BowLevel", this.getBowLevel());
		compoundTag.putInt("MerorLocation", this.getMerorLocation());
		compoundTag.putInt("NewSpawn", this.newSpawn);
		compoundTag.putBoolean("IsSkinA", this.isSkinA());
		compoundTag.putBoolean("IsSkinB", this.isSkinB());
		compoundTag.putBoolean("IsSkinC", this.isSkinC());
		compoundTag.putBoolean("IsGiant", this.isGiant());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBowLevel(compoundTag.getInt("BowLevel"));
		this.setMerorLocation(compoundTag.getInt("MerorLocation"));
		this.newSpawn = compoundTag.getInt("NewSpawn");
		if (compoundTag.contains("IsSkinA")) {
			this.setSkinA(compoundTag.getBoolean("IsSkinA"));
		}
		if (compoundTag.contains("IsSkinB")) {
			this.setSkinB(compoundTag.getBoolean("IsSkinB"));
		}
		if (compoundTag.contains("IsSkinC")) {
			this.setSkinC(compoundTag.getBoolean("IsSkinC"));
		}
		if (compoundTag.contains("IsGiant")) {
			this.setGiant(compoundTag.getBoolean("IsGiant"));
		}
	}

	@Override
	protected AABB makeBoundingBox() {
		if (this.isGiant()) {
			return AABB.ofSize(new Vec3(this.getX(), this.getY() + 1.7, this.getZ()), 1.05f, 3.4f, 1.05f);
		}
		return super.makeBoundingBox();
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isGiant()) {
			return 3.05f;
		}
		return 1.9f;
	}

	private static final EntityDataAccessor<Boolean> DATA_SKIN_A_ID = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SKIN_B_ID = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SKIN_C_ID = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> GIANT = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> MEROR_LOCATION = SynchedEntityData.defineId(SecondRounderSkeletonEntity.class, EntityDataSerializers.INT);

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(BOW_LEVEL, 15);
		this.getEntityData().define(MEROR_LOCATION, 1);
		this.getEntityData().define(DATA_SKIN_A_ID, false);
		this.getEntityData().define(DATA_SKIN_B_ID, false);
		this.getEntityData().define(DATA_SKIN_C_ID, false);
		this.getEntityData().define(GIANT, false);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_SKIN_A_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (DATA_SKIN_B_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (DATA_SKIN_C_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (GIANT.equals(entityDataAccessor)) {
			this.refreshDimensions();
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

	public int getMerorLocation() {
		return this.getEntityData().get(MEROR_LOCATION);
	}
	public void setMerorLocation(int n) {
		this.getEntityData().set(MEROR_LOCATION, n);
	}
	public boolean isSkinA() {
		return this.getEntityData().get(DATA_SKIN_A_ID);
	}
	public boolean isSkinB() {
		return this.getEntityData().get(DATA_SKIN_B_ID);
	}
	public boolean isSkinC() {
		return this.getEntityData().get(DATA_SKIN_C_ID);
	}
	public boolean isGiant() {
		return this.getEntityData().get(GIANT);
	}
	public void setSkinA(boolean bl) {
		this.getEntityData().set(DATA_SKIN_A_ID, bl);
	}
	public void setSkinB(boolean bl) {
		this.getEntityData().set(DATA_SKIN_B_ID, bl);
	}
	public void setSkinC(boolean bl) {
		this.getEntityData().set(DATA_SKIN_C_ID, bl);
	}
	public void setGiant(boolean bl) {
		this.getEntityData().set(GIANT, bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60);
			this.setHealth(60.0f);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(9);
			setMaxUpStep(1.1f);
		}
		else{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
			setMaxUpStep(0.6f);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
			if (newSpawn == 0) {
				RandomSource randomSource = serverLevelAccessor.getRandom();
				this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
				RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
				if (randomSourceSkin.nextFloat() < 0.5f) {
					this.setSkinA(true);
				}
				if (randomSourceSkin.nextFloat() < 0.5f) {
					this.setSkinB(true);
				}
				if (randomSourceSkin.nextFloat() < 0.5f) {
					this.setSkinC(true);
				}
				this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
				newSpawn = 1;
			}
		}
		else {
			this.setCanPickUpLoot(false);
		}
		return spawnGroupData;
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
	@Override
	public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
		return ProjectileUtil.getMobArrow(this, itemStack, f);
	}
	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
	}
}