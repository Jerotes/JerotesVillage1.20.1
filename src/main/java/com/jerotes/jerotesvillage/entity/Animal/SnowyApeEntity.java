package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotes.goal.JerotesAnimalChangeFollowParentGoal;
import com.jerotes.jerotes.goal.JerotesAnimalChangeTemptGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.MountainRealmFlagSpear;
import com.jerotes.jerotesvillage.item.VirtualCaveFishingNet;
import com.jerotes.jerotesvillage.item.VirtualCaveFishingNetBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SnowyApeEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, UseThrowEntity, RangedAttackMob, UseThrownJavelinEntity, Saddleable, UseShieldEntity, PlayerRideableJumping {
	public static final EntityDataAccessor<Boolean> THROW = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROWING_TICK = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(SnowyApeEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemStack(Items.POWDER_SNOW_BUCKET));
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState throwAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public AnimationState shieldUseMainhandAnimationState = new AnimationState();
	public AnimationState shieldUseOffhandAnimationState = new AnimationState();

	public SnowyApeEntity(EntityType<? extends SnowyApeEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		this.applyOpenDoorsAbility();
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0f);
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 65);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new JerotesOpenTwoDoorGoal(this, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, 8));
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.75, true));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(3, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new JerotesAnimalChangeTemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new JerotesAnimalChangeFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Animal.class, 8.0f));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpMobOwnerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<SnowyApeEntity>(this, false));
	}
	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(13);
			this.setAnimationState("throw");
		}
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isRangeJavelin(handItem)) {
			useTridentShoot(this, livingEntity);
		}
		if (InventoryEntity.isThrow(handItem)) {
			useThrowShoot(this, livingEntity);
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
			}
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SNOWY_APE_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		return JerotesVillageSoundEvents.SNOWY_APE_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SNOWY_APE_DEATH;
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
		return -0.7F;
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
	@Nullable
	@Override
	public SnowyApeEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		SnowyApeEntity snowyApe = JerotesVillageEntityType.SNOWY_APE.get().create(serverLevel);
		if (snowyApe != null && (uUID = this.getOwnerUUID()) != null) {
			snowyApe.setOwnerUUID(uUID);
			snowyApe.setTame(true);
		}
		return snowyApe;
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -48000 : 0);
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 12;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 2.35f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isSaddled() {
		return this.isAlive() && !this.isBaby();
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
		return aabb1.inflate(0.75d, 0.75d, 0.75d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
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
			this.playSound(JerotesVillageSoundEvents.SNOWY_APE_ATTACK, 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(0);
			int attackRandom = this.getRandom().nextInt(25);
			if (attackRandom < 10) {
				this.setAnimTick(12);
				this.setAnimationState("attack3");
			} else {
				this.setAnimTick(12);
				this.setAnimationState("attack1");
			}
		}
	}
	@Override
	public void pressAddJerotes(Player player) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(15);
			this.setAttackTick(15);
			this.setAnimationState("attack2");
		}
	}
	@Override
	public void individualAttackJerotes(LivingEntity livingEntity) {
		AttackFind.attackBegin(this, livingEntity);
		boolean bl = AttackFind.attackAfter(this, livingEntity, 1f, 1f, false, 0);
		if (bl) {
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
	}
	@Override
	public boolean canPressMainJerotes() {
		return this.getAttackTick() <= -20;
	}
	@Override
	public boolean canPressAddJerotes() {
		return this.getAttackTick() <= -20;
	}

	private int sitTick = 0;
	public int shieldCoolDown;
	public int shieldCanUse = 1;
	@Nullable
	private LivingEntity thrower;
	@Nullable
	private UUID throwerUUID;
	public void setThrower(@Nullable LivingEntity livingEntity) {
		this.thrower = livingEntity;
		this.throwerUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	@Nullable
	public LivingEntity getThrower() {
		Entity entity;
		if (this.thrower == null && this.throwerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.throwerUUID)) instanceof LivingEntity) {
			this.thrower = (LivingEntity)entity;
		}
		return this.thrower;
	}
	public boolean isThrow() {
		return this.entityData.get(THROW);
	}
	public void setThrow(boolean bl) {
		this.entityData.set(THROW, bl);
	}
	public void setShieldLevel(int n){
		this.getEntityData().set(SHIELD_LEVEL, n);
	}
	public int getShieldLevel(){
		return this.getEntityData().get(SHIELD_LEVEL);
	}
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80.0);
			this.setHealth(80.0f);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(65.0);
			this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24);
		}
	}
	public void setThrowingTick(int n){
		this.getEntityData().set(THROWING_TICK, n);
	}
	public int getThrowingTick(){
		return this.getEntityData().get(THROWING_TICK);
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
		if (Objects.equals(animation, "attack2")){
			return 2;
		}
		if (Objects.equals(animation, "attack3")){
			return 3;
		}
		if (Objects.equals(animation, "throw")){
			return 4;
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
		list.add(this.throwAnimationState);
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
		compoundTag.putInt("SitTick", this.sitTick);
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putInt("ThrowingTick", this.getThrowingTick());
		compoundTag.putBoolean("IsThrow", this.isThrow());
		if (compoundTag.hasUUID("Thrower")) {
			this.throwerUUID = compoundTag.getUUID("Thrower");
		}
		compoundTag.putInt("ShieldLevel", this.getShieldLevel());
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.sitTick = compoundTag.getInt("SitTick");
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.setThrow(compoundTag.getBoolean("IsThrow"));
		this.setThrowingTick(compoundTag.getInt("ThrowingTick"));
		if (this.throwerUUID != null) {
			compoundTag.putUUID("Thrower", this.throwerUUID);
		}
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(THROW, false);
		this.getEntityData().define(THROWING_TICK, 0);
		this.getEntityData().define(SHIELD_LEVEL, 1);
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
					case 4:
						this.throwAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.throwAnimationState);
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
				//骑乘
				if (!this.isFood(itemStack) && !player.onGround() && !player.isInWater() && this.isSaddled() && AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), player) && !player.isSecondaryUseActive() && !isOrderedToSit()) {
					if (!this.level().isClientSide) {
						player.startRiding(this);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
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
			if (player.getItemInHand(interactionHand).isEmpty()) {
				player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
			}
		}
		if (this.random.nextInt(4) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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

	@Override
	public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if(this.getOwner() == player && !player.isSpectator() && this.isTame() && !player.isShiftKeyDown() && (player.onGround() || player.isInWater())) {
			EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemstack);
			if (itemstack.getItem() instanceof BookItem && this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
				ItemStack glasses = new ItemStack(JerotesVillageItems.MYOPIA_GLASSES.get());
				if (this.getRandom().nextFloat() > 0.66f) {
					glasses = new ItemStack(JerotesVillageItems.PRESBYOPIC_GLASSES.get());
				}
				else if (this.getRandom().nextFloat() > 0.33f) {
					glasses = new ItemStack(JerotesVillageItems.SUNGLASSES.get());
				}
				this.setItemSlot(EquipmentSlot.HEAD, glasses);
				equipmentSlot = EquipmentSlot.OFFHAND;
			}
			if (itemstack.isEmpty()) {
				EquipmentSlot equipmentSlot2;
				EquipmentSlot equipmentSlot3 = this.getClickedSlot(vec3);
				EquipmentSlot equipmentSlot4 = equipmentSlot2 = this.isDisabled(equipmentSlot3) ? equipmentSlot : equipmentSlot3;
				if (this.hasItemInSlot(equipmentSlot2) && this.swapItem(player, equipmentSlot2, itemstack, interactionHand)) {
					return InteractionResult.SUCCESS;
				}
			}
			else {
				if (this.swapItem(player, equipmentSlot, itemstack, interactionHand)) {
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.interactAt(player, vec3, interactionHand);
	}

	private boolean isDisabled(EquipmentSlot equipmentSlot) {
		return equipmentSlot.getType() != EquipmentSlot.Type.HAND && equipmentSlot != EquipmentSlot.HEAD;
	}

	private EquipmentSlot getClickedSlot(Vec3 vec3) {
		EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
		boolean bl = this.isBaby();
		double d = bl ? vec3.y * 2.0 : vec3.y;
		if (d >= 1.6 && this.hasItemInSlot(EquipmentSlot.HEAD)) {
			return EquipmentSlot.HEAD;
		}
		if (this.hasItemInSlot(EquipmentSlot.MAINHAND)) return equipmentSlot;
		if (!this.hasItemInSlot(EquipmentSlot.OFFHAND)) return equipmentSlot;
		return EquipmentSlot.OFFHAND;
	}

	private boolean swapItem(Player player, EquipmentSlot equipmentSlot, ItemStack itemStack, InteractionHand interactionHand) {
		//旗帜
		if (this.getItemBySlot(equipmentSlot).getItem() instanceof BannerItem) {
			equipmentSlot = EquipmentSlot.HEAD;
		}
		//相同物品副手
		if (itemStack.getItem() == Items.TOTEM_OF_UNDYING && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
			equipmentSlot = EquipmentSlot.OFFHAND;
		}
		//非副手非头部为主手
		if (equipmentSlot != EquipmentSlot.OFFHAND && equipmentSlot != EquipmentSlot.HEAD) {
			equipmentSlot = EquipmentSlot.MAINHAND;
		}
		//特殊 虚洞渔网 镇山旗矛 优先手部
		if ((itemStack.getItem() instanceof VirtualCaveFishingNet ||
				itemStack.getItem() instanceof VirtualCaveFishingNetBlockItem ||
				itemStack.getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfJavelin ||
				itemStack.getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfSpear) && !this.getItemBySlot(equipmentSlot).isEmpty()) {
			equipmentSlot = EquipmentSlot.MAINHAND;
		}
		//穿戴相同物品则为主手
		if (this.getItemBySlot(equipmentSlot) == itemStack) {
			equipmentSlot = EquipmentSlot.MAINHAND;
		}
		//常规
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
		if (this.getAttackTick() == 5 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackTick() > 10) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360f, 360f);
			}
		}
		if (this.getAttackTick() > 0) {
			this.getNavigation().stop();
		}
		//投掷
		if (this.isThrow()) {
			if (!this.level().isClientSide()) {
				this.setThrowingTick(15);
				this.setAnimTick(15);
				this.setAnimationState("attack2");
				this.setThrow(false);
			}
		}
		if (this.getThrowingTick() <= 0 && this.getThrower() != null) {
			if (!this.level().isClientSide()) {
				this.setThrower(null);
			}
		}
		if (this.getThrowingTick() == 8) {
			if (this.getControllingPassenger() != null) {
				LivingEntity livingEntity = this.getControllingPassenger();
				livingEntity.stopRiding();
				livingEntity.moveTo(this.getPassengerRidingPosition(livingEntity));
				this.setThrower(livingEntity);
			}
		}
		if (this.getThrowingTick() == 5) {
			this.playThrowSound();
			if (this.getThrower() != null && this.distanceTo(this.getThrower()) < 5) {
				this.RushAttack(this.getThrower());
			}
		}
		if (!this.level().isClientSide()) {
			this.setThrowingTick(Math.max(0, this.getThrowingTick() - 1));
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

		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}
		//使用盾牌和双手武器
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

	public boolean RushAttack(LivingEntity livingEntity) {
		float f = livingEntity.getYRot();
		float f2 = livingEntity.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 1.5f;
		livingEntity.setOnGround(false);
		livingEntity.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 3, f5 *= f7 / f6 * 2);
		return true;
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
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(0);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 10) {
				this.setAnimTick(12);
				this.setAnimationState("attack3");
			}
			else if (attackRandom < 15) {
				this.setAnimTick(15);
				this.setAttackTick(15);
				this.setAnimationState("attack2");
				return true;
			} else {
				this.setAnimTick(12);
				this.setAnimationState("attack1");
			}
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SNOWY_APE_ATTACK, 1.0f, 1.0f);
			}
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}
	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SNOWY_APE_ATTACK, 1.0f, 1.0f);
		}
		float reach = 0.5f;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, 1f, 1f, false, 0f);
		}
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		if (this.isSaddled() && this.getFirstPassenger() instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}

	@Override
	public void equipSaddle(@Nullable SoundSource soundSource) {
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
	public void handleStartJump(int n) {
		this.playJumpSound();
		if (n >= 90 && n <= 100) {
			if (!this.level().isClientSide()) {
				this.setThrow(true);
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
		if (f < 0.9f) {
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
	protected void playJumpSound() {
		this.playSound(JerotesVillageSoundEvents.SNOWY_APE_JUMP, 0.4f, 1.0f);
	}
	protected void playThrowSound() {
		this.playSound(JerotesVillageSoundEvents.SNOWY_APE_THROW, 1.0f, 1.0f);
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
