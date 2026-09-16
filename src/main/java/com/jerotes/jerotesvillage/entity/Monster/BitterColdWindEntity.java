package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class BitterColdWindEntity extends Monster implements JerotesEntity {
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BitterColdWindEntity.class, EntityDataSerializers.BYTE);
	public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> livingEntity.hasEffect(JerotesVillageMobEffects.BITTER_COLD.get());

	public BitterColdWindEntity(EntityType<? extends BitterColdWindEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.1f);
		this.xpReward = 5;
		this.setCanPickUpLoot(true);
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
		if (oldItem.is(Items.BLUE_ICE)) {
			return false;
		}
		if (newItem.is(Items.BLUE_ICE)) {
			return true;
		}
		return InventoryEntity.canReplaceCurrentItem(newItem, oldItem, true, false, false, false, false, false, false, false, false, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, LivingEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8, 20));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.targetSelector.addGoal(1, new HelpCorrosiverGoal(LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, true, PREY_SELECTOR));
	}

	class HelpCorrosiverGoal extends NearestAttackableTargetGoal<LivingEntity> {
		@Nullable
		private LivingEntity attack;

		public HelpCorrosiverGoal(Class<LivingEntity> class_, boolean bl, @Nullable boolean bl2, Predicate<LivingEntity> predicate) {
			super(BitterColdWindEntity.this, class_, 10, bl, bl2, predicate);
		}

		@Override
		public boolean canUse() {
			if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			List<CorrosiverEntity> list = BitterColdWindEntity.this.level().getEntitiesOfClass(CorrosiverEntity.class, BitterColdWindEntity.this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			double d = Double.MAX_VALUE;
			for (CorrosiverEntity other : list) {
				if (other == null) continue;
				if (this.mob == other) continue;
				if ((BitterColdWindEntity.this.distanceToSqr(other)) > d) continue;
				if (BitterColdWindEntity.this.getTarget() != null) continue;
				if ((other.getTeam() != null || BitterColdWindEntity.this.getTeam() != null) && !BitterColdWindEntity.this.isAlliedTo(other)) continue;
				if (other.getTarget() != null && BitterColdWindEntity.this.canAttack(other.getTarget()) && BitterColdWindEntity.this.canAttackType(other.getTarget().getType())){
					if (BitterColdWindEntity.this.getTarget() == null || !(BitterColdWindEntity.this.getTarget() instanceof LivingEntity)) {
						if (!(other.getTarget().isAlliedTo(BitterColdWindEntity.this)) && !(other.getTarget().isAlliedTo(other))){
							attack = other.getTarget();
							return true;
						}
					}
				}
			}
			return false;
		}

		@Override
		public void start() {
			this.setTarget(attack);
			super.start();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.38);
		builder = builder.add(Attributes.MAX_HEALTH, 26);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(d * 3, d2 * 3, d3 * 3);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(d * 3, d2 * 3, d3 * 3);
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BITTER_COLD_WIND_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BITTER_COLD_WIND_AMBIENT;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	protected float getSoundVolume() {
		return 1f;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.8f;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}
	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, by);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_FLAGS_ID, (byte) 0);
	}


	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isWaterDamage(damageSource)
				|| AttackFind.isColdDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.ON_FIRE))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isFireDamage(damageSource))
			return super.hurt(damageSource, amount * 4);
		if (damageSource.is(DamageTypes.IN_WALL))
			return super.hurt(damageSource, amount * 2);
		return super.hurt(damageSource, amount);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.3, 1.0));
		}
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
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte)4);
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.BITTER_COLD_WIND_AMBIENT, 1.0f, 1.0f);
			}
			if (this.getMainHandItem().getItem() == Items.BLUE_ICE) {
				if (entity.getTicksFrozen() < 220) {
					entity.setTicksFrozen(220);
				}
			}
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (livingEntity instanceof CorrosiverEntity && ((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam())) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		float weaponRandom = this.random.nextFloat();
		this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
		this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.95) {
			return new ItemStack(Items.IRON_SWORD);
		}
		else if (weaponRandom > 0.90){
			return new ItemStack(Items.IRON_AXE);
		}
		else if (weaponRandom > 0.85){
			return new ItemStack(Items.WOODEN_SWORD);
		}
		else if (weaponRandom > 0.75){
			return new ItemStack(Items.WOODEN_AXE);
		}
		else if (weaponRandom > 0.7){
			return new ItemStack(JerotesItems.SIMPLE_JAVELIN.get());
		}
		else if (weaponRandom > 0.68){
			return new ItemStack(JerotesItems.IRON_JAVELIN.get());
		}
		else if (weaponRandom > 0.55){
			return new ItemStack(Items.BLUE_ICE);
		}
		return new ItemStack(Items.AIR);
	}
}

