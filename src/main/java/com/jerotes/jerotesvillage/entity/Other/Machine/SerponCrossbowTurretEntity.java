package com.jerotes.jerotesvillage.entity.Other.Machine;

import com.jerotes.jerotes.entity.Interface.CanBeSerponFactionEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.SerponCrossbowTurretArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerponCrossbowTurretEntity extends PathfinderMob implements JerotesEntity, Saddleable, CanBeSerponFactionEntity, FactionEntity {
	private static final EntityDataAccessor<Boolean> NOT_SERPON = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Float> LOAD_TICK = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> PIERCING_LEVEL = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> QUICK_CHARGE_LEVEL = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MULTISHOT_LEVEL = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SerponCrossbowTurretEntity.class, EntityDataSerializers.INT);
	public AnimationState fireAnimationState = new AnimationState();
	public AnimationState chargeAnimationState = new AnimationState();

	public SerponCrossbowTurretEntity(EntityType<? extends SerponCrossbowTurretEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0f);
		xpReward = 0;
		setPersistenceRequired();
	}

	@Override
	public String getFirstFactionTypeName() {
		if (this.isNotSerpon()) {
			return "";
		}
		return "serpon_combat_team";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 50);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.7);
		return builder;
	}

	@Override
	protected void registerGoals() {
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return JerotesVillageSoundEvents.SERPON_CROSSBOW_TURRET_DEATH;
	}
	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SERPON_CROSSBOW_TURRET_DEATH;
	}
	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
		return new Vec3(0.0, 0.0, 1.0);
	}
	@Override
	public boolean isOnFire() {
		return false;
	}
	@Override
	public boolean isSaddleable() {
		return this.isAlive();
	}
	@Override
	public void equipSaddle(@Nullable SoundSource p_21748_) {
	}
	@Override
	public boolean isSaddled() {
		return this.isNotSerpon();
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
		return -1.7F + this.getXRot() / 80f;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yHeadRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected float getSinglePassengerXOffset() {
		float f = this.getXRot() < 0 ? -this.getXRot() : this.getXRot();
		return -1.2F + f / 120f;
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		float f2 = this.getSinglePassengerXOffset();
		float f3 = 0f;
		return new Vector3f(f3, entityDimensions.height, f2);
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		if (this.onGround()) {
			super.setDeltaMovement(new Vec3(0, 0, 0));
		}
		else {
			super.setDeltaMovement(new Vec3(0, vec3.y, 0));
		}
	}
	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle() && this.getFirstPassenger() instanceof LivingEntity livingEntity) {
			this.setYHeadRot(livingEntity.getYHeadRot());
			this.setXRot(livingEntity.getXRot());
			this.setRot(livingEntity.getYRot(), livingEntity.getXRot());
			this.yHeadRot = livingEntity.yHeadRot;
			this.yHeadRotO = livingEntity.yHeadRotO;
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float forward = passenger.zza;
				float strafe = 0;
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
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public int getMaxHeadYRot() {
		return 360;
	}
	@Override
	public int getMaxHeadXRot() {
		return 360;
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
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
	protected BodyRotationControl createBodyControl() {
		return new CrossbowTurretBodyRotationControl(this);
	}
	static class CrossbowTurretBodyRotationControl extends BodyRotationControl {
		public CrossbowTurretBodyRotationControl(Mob mob) {
			super(mob);
		}

		@Override
		public void clientTick() {
		}
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public int soundTick;
	@Override
	public boolean isNotSerpon() {
		return this.getEntityData().get(NOT_SERPON);
	}
	public void setNotSerpon(boolean bl) {
		this.getEntityData().set(NOT_SERPON, bl);
	}
	public void setLoadTick(float f){
		this.getEntityData().set(LOAD_TICK, f);
	}
	public float getLoadTick(){
		return this.getEntityData().get(LOAD_TICK);
	}
	public void setPiercingLevel(int n){
		this.getEntityData().set(PIERCING_LEVEL, n);
	}
	public int getPiercingLevel(){
		return this.getEntityData().get(PIERCING_LEVEL);
	}
	public void setQuickChargeLevel(int n){
		this.getEntityData().set(QUICK_CHARGE_LEVEL, n);
	}
	public int getQuickChargeLevel(){
		return this.getEntityData().get(QUICK_CHARGE_LEVEL);
	}
	public void setMultishotLevel(int n){
		this.getEntityData().set(MULTISHOT_LEVEL, n);
	}
	public int getMultishotLevel(){
		return this.getEntityData().get(MULTISHOT_LEVEL);
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
		if (Objects.equals(animation, "fire")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.fireAnimationState);
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
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putFloat("Faces", this.faces);
		compoundTag.putBoolean("IsNotSerpon", this.isNotSerpon());
		compoundTag.putFloat("LoadTick", this.getLoadTick());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("PiercingLevel", this.getPiercingLevel());
		compoundTag.putInt("QuickChargeLevel", this.getQuickChargeLevel());
		compoundTag.putInt("MultishotLevel", this.getMultishotLevel());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.faces = compoundTag.getFloat("Faces");
		if (compoundTag.contains("IsNotSerpon")) {
			this.setNotSerpon(compoundTag.getBoolean("IsNotSerpon"));
		}
		this.setLoadTick(compoundTag.getFloat("LoadTick"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setPiercingLevel(compoundTag.getInt("PiercingLevel"));
		this.setQuickChargeLevel(compoundTag.getInt("QuickChargeLevel"));
		this.setMultishotLevel(compoundTag.getInt("MultishotLevel"));
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(NOT_SERPON, true);
		this.getEntityData().define(LOAD_TICK, 0f);
		this.getEntityData().define(QUICK_CHARGE_LEVEL, 0);
		this.getEntityData().define(PIERCING_LEVEL, 0);
		this.getEntityData().define(MULTISHOT_LEVEL, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (NOT_SERPON.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.fireAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fireAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide()) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(1.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator())) || this.getControllingPassenger() == null) {
			this.setTarget(null);
		}
		//
		this.chargeAnimationState.startIfStopped(this.tickCount);
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
		this.clearFire();
		LivingEntity passenger = this.getPassengers().isEmpty() ? this : (LivingEntity) this.getPassengers().get(0);
		boolean use = this.getLoadTick() >= 0 && (
				(passenger instanceof Player player && (player.swinging))
						||
						(passenger instanceof Mob mob && mob.getTarget() != null)
		);
		//
		if (!this.level().isClientSide()) {
			//降低
			if (this.getLoadTick() > 0 && !use && !(this.getLoadTick() > 30 && this.getVehicle() != null)) {
				this.setLoadTick(Math.max(0, this.getLoadTick() - 0.25f));
			}
			float f = Math.min(5, this.getQuickChargeLevel() * 0.5f + 1);
			//蓄力
			if (use) {
				this.setLoadTick(Math.min(50, this.getLoadTick() + f));
			}
			//冷却
			if (this.getLoadTick() < 0) {
				this.setLoadTick(Math.min(0, this.getLoadTick() + f));
			}
		}
		//音效
		if (!this.isSilent()) {
			if (this.getLoadTick() >= 10 && this.getLoadTick() < 15 && soundTick <= 0) {
				soundTick = 5;
				if (this.getQuickChargeLevel() <= 0) {
					this.playSound(SoundEvents.CROSSBOW_LOADING_START, 3.0f, 1.0f);
				}
				else {
					this.playSound(SoundEvents.CROSSBOW_QUICK_CHARGE_1, 3.0f, 1.0f);
				}
			}
			if (this.getLoadTick() >= 17 && this.getLoadTick() < 22 && soundTick <= 0) {
				soundTick = 5;
				if (this.getQuickChargeLevel() <= 0) {
					this.playSound(SoundEvents.CROSSBOW_LOADING_MIDDLE, 3.0f, 1.0f);
				}
				else {
					this.playSound(SoundEvents.CROSSBOW_QUICK_CHARGE_2, 3.0f, 1.0f);
				}
			}
			if (this.getLoadTick() >= 25 && this.getLoadTick() < 30 && soundTick <= 0) {
				soundTick = 5;
				if (this.getQuickChargeLevel() <= 0) {
					this.playSound(SoundEvents.CROSSBOW_LOADING_END, 3.0f, 1.0f);
				}
				else {
					this.playSound(SoundEvents.CROSSBOW_QUICK_CHARGE_3, 3.0f, 1.0f);
				}
			}
		}
		if (soundTick > 0) {
			soundTick --;
		}
		//发射
		if (!this.level().isClientSide()
				&& ((passenger instanceof Player player && player.swinging)
				|| (passenger instanceof Mob mob && mob.getTarget() != null))
				&& this.getLoadTick() >= 40) {
			shoot(passenger);
			float f = Math.min(-5, this.getQuickChargeLevel() * 5f - 30);
			this.setLoadTick(f);
		}
		//
		if (!this.isNotSerpon()) {
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0, 0.2), EntitySelector.pushableBy(this));
			if (!list.isEmpty() && !this.isNotSerpon()) {
				boolean bl = !this.level().isClientSide && !(passenger instanceof Player);
				for (LivingEntity entity : list) {
					if (entity == null) continue;
					if (entity.hasPassenger(this)) continue;
					if (entity.isVehicle()) continue;
					if (entity.isPassenger()) continue;
					if (!OtherEntityFactionFind.isSerpon(entity.getType())) continue;
					if (bl && AttackFind.canRideAbout(this, null, this.getControllingPassenger(), entity) && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses"))))) {
						if (!this.level().isClientSide) {
							entity.startRiding(this);
						}
						continue;
					}
					this.push(entity);
				}
			}
		}
	}

	public void shoot(LivingEntity passenger) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(5);
			this.setAnimationState("fire");
		}
		if (passenger instanceof Player) {
			Vec3 lookVec = passenger.getLookAngle();
			if (this.getMultishotLevel() > 0) {
				for (int i = 0; i < 3; ++i) {
					SerponCrossbowTurretArrowEntity arrow = new SerponCrossbowTurretArrowEntity(this.level(), passenger, new ItemStack(Items.ARROW));
					arrow.setCritArrow(true);
					arrow.setPierceLevel((byte) this.getPiercingLevel());
					arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
					arrow.setKnockback(5);
					float angleOffset = -5 + i * 5;
					if (angleOffset != 0) {
						Vec3 rotatedVec = lookVec.yRot(angleOffset * Mth.DEG_TO_RAD);
						arrow.shoot(rotatedVec.x, rotatedVec.y, rotatedVec.z, 4.5f, 0);
					} else {
						arrow.shoot(lookVec.x, lookVec.y, lookVec.z, 4.5f, 0);
					}
					this.level().addFreshEntity(arrow);
				}
			} else {
				SerponCrossbowTurretArrowEntity arrow = new SerponCrossbowTurretArrowEntity(this.level(), passenger, new ItemStack(Items.ARROW));
				arrow.setCritArrow(true);
				arrow.setPierceLevel((byte) this.getPiercingLevel());
				arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
				arrow.setKnockback(5);
				arrow.shoot(lookVec.x, lookVec.y, lookVec.z, 4.5f, 0);
				this.level().addFreshEntity(arrow);
			}
			if (!this.isSilent()) {
				this.playSound(SoundEvents.CROSSBOW_SHOOT, 5.0f, 1.0f);
			}
		}
		else {
			if (this.getMultishotLevel() > 0) {
				for (int i = 0; i < 3; ++i) {
					SerponCrossbowTurretArrowEntity arrow = new SerponCrossbowTurretArrowEntity(this.level(), passenger, new ItemStack(Items.ARROW));
					arrow.setCritArrow(true);
					arrow.setPierceLevel((byte) this.getPiercingLevel());
					arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
					arrow.setKnockback(5);
					arrow.shootFromRotation(this, this.getXRot(), (this.getYHeadRot() - 5 + i * 5), 0.0F, 4.5f, 0F);
					this.level().addFreshEntity(arrow);
				}
			} else {
				SerponCrossbowTurretArrowEntity arrow = new SerponCrossbowTurretArrowEntity(this.level(), passenger, new ItemStack(Items.ARROW));
				arrow.setCritArrow(true);
				arrow.setPierceLevel((byte) this.getPiercingLevel());
				arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
				arrow.setKnockback(5);
				arrow.shootFromRotation(this, this.getXRot(), this.getYHeadRot(), 0.0F, 4.5f, 0F);
				this.level().addFreshEntity(arrow);
			}
			if (!this.isSilent()) {
				this.playSound(SoundEvents.CROSSBOW_SHOOT, 5.0f, 1.0f);
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getItem() == Items.ENCHANTED_BOOK && itemStack.getItem() instanceof EnchantedBookItem enchantedBookItem) {
			boolean bl = false;
			ListTag listTag = EnchantedBookItem.getEnchantments(itemStack);
			ResourceLocation quick_charge = EnchantmentHelper.getEnchantmentId(Enchantments.QUICK_CHARGE);
			ResourceLocation piercing = EnchantmentHelper.getEnchantmentId(Enchantments.PIERCING);
			ResourceLocation multishot = EnchantmentHelper.getEnchantmentId(Enchantments.MULTISHOT);
			for(int i = 0; i < listTag.size(); ++i) {
				CompoundTag compoundTag = listTag.getCompound(i);
				ResourceLocation resourceLocation = EnchantmentHelper.getEnchantmentId(compoundTag);
				if (resourceLocation != null && resourceLocation.equals(quick_charge)) {
					if (EnchantmentHelper.getEnchantmentLevel(compoundTag) > this.getQuickChargeLevel()) {
						this.setQuickChargeLevel(EnchantmentHelper.getEnchantmentLevel(compoundTag));
						bl = true;
					}
				}
				if (resourceLocation != null && resourceLocation.equals(piercing)) {
					if (EnchantmentHelper.getEnchantmentLevel(compoundTag) > this.getPiercingLevel()) {
						this.setPiercingLevel(EnchantmentHelper.getEnchantmentLevel(compoundTag));
						bl = true;
					}
				}
				if (resourceLocation != null && resourceLocation.equals(multishot)) {
					if (EnchantmentHelper.getEnchantmentLevel(compoundTag) > this.getMultishotLevel()) {
						this.setMultishotLevel(EnchantmentHelper.getEnchantmentLevel(compoundTag));
						bl = true;
					}
				}
			}
			if (bl) {
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		if (this.isNotSerpon() && AttackFind.canRideAbout(this, null, this.getControllingPassenger(), player)) {
			if (!this.level().isClientSide()) {
				player.startRiding(this);
			}
		}
		if (itemStack.is(ItemTags.create(new ResourceLocation("forge:ingots/serpon_alloy")))) {
			this.heal(this.getMaxHealth() / 8);
			float f2 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			this.playSound(JerotesSoundEvents.REPAIR_MOB, 1.0f, f2);
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		return false;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isFireDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource))
			return true;
		if (damageSource.getEntity() == this.getControllingPassenger() && this.getControllingPassenger() != this && this.getControllingPassenger() != null)
			return true;
		if (this.getControllingPassenger() != null && this.getControllingPassenger().isInvulnerableTo(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.getDirectEntity() instanceof SerponCrossbowTurretArrowEntity) {
			amount /= 10;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float f, float f2, DamageSource damageSource) {
		return false;
	}
	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == this.getControllingPassenger()) {
			return true;
		}
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

	public float faces;
	@Override
	public void stopRiding() {
		super.stopRiding();
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	@javax.annotation.Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData, @javax.annotation.Nullable CompoundTag compoundTag) {
		float faces = serverLevelAccessor.getRandom().nextFloat();
		this.faces = faces;
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}