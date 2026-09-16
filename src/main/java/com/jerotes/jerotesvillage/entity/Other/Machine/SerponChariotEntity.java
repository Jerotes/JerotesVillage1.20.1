package com.jerotes.jerotesvillage.entity.Other.Machine;

import com.jerotes.jerotes.entity.Interface.CanBeSerponFactionEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesFlyingRandomStrollGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.FollowSerponCombatExecuterGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Predicate;

public class SerponChariotEntity extends PathfinderMob implements JerotesEntity, Saddleable, CanBeSerponFactionEntity, FactionEntity {
	private static final EntityDataAccessor<Boolean> NOT_SERPON = SynchedEntityData.defineId(SerponChariotEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SerponChariotEntity.class, EntityDataSerializers.INT);

	public SerponChariotEntity(EntityType<? extends SerponChariotEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(2.1f);
		xpReward = 0;
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
	}
	public boolean isJerotesFlyingMob() {
		return true;
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.FLYING_SPEED, 1.0);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.7);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(SerponChariotEntity.this, 1.2, true));
		this.goalSelector.addGoal(2, new FollowSerponCombatExecuterGoal(this, 1.1f));
		this.goalSelector.addGoal(2, new SerponRandomStrollGoal());
		this.targetSelector.addGoal(1, new HelpSerponGoal(LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
	}

	class SerponRandomStrollGoal extends JerotesFlyingRandomStrollGoal {
		public SerponRandomStrollGoal() {
			super(SerponChariotEntity.this, 0.8, 20);
		}

		@Override
		public boolean canUse() {
			if (((SerponChariotEntity)this.mob).isNotSerpon() || this.mob.getControllingPassenger() == null) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			if (this.mob.getControllingPassenger() != null && !OtherEntityFactionFind.isSerpon(this.mob.getControllingPassenger().getType())) {
				return false;
			}
			if (!this.forceTrigger) {
				if (this.mob.getNoActionTime() >= 100) {
					return false;
				}
				if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
					return false;
				}
			}

			Vec3 vec3 = this.getPosition();
			if (vec3 == null) {
				return false;
			} else {
				this.wantedX = vec3.x;
				this.wantedY = vec3.y;
				this.wantedZ = vec3.z;
				this.forceTrigger = false;
				return true;
			}
		}

		public boolean canContinueToUse() {
			if (((SerponChariotEntity)this.mob).isNotSerpon() || this.mob.getControllingPassenger() == null) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			if (this.mob.getControllingPassenger() != null && !OtherEntityFactionFind.isSerpon(this.mob.getControllingPassenger().getType())) {
				return false;
			}
			return !this.mob.getNavigation().isDone();
		}
	}

	class HelpSerponGoal extends NearestAttackableTargetGoal<LivingEntity> {
		private LivingEntity attack;

		public HelpSerponGoal(Class<LivingEntity> class_, boolean bl, boolean bl2, Predicate<LivingEntity> predicate) {
			super(SerponChariotEntity.this, class_, 10, bl, bl2, predicate);
		}

		@Override
		public boolean canUse() {
			if (!(this.mob instanceof SerponChariotEntity serponChariot)) {
				return false;
			}
			if (!serponChariot.isNotSerpon() && serponChariot.getControllingPassenger() != null) {
				if (serponChariot.getControllingPassenger() instanceof Mob mob && OtherEntityFactionFind.isSerpon(mob.getType()) && mob.getTarget() != null) {
					attack = mob.getTarget();
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return this.canUse();
		}

		@Override
		public void start() {
			this.setTarget(attack);
			super.start();
		}
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return JerotesVillageSoundEvents.SERPON_CHARIOT_DEATH;
	}
	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SERPON_CHARIOT_DEATH;
	}
	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(JerotesVillageSoundEvents.SERPON_CHARIOT_WALK, 0.15f, 1);
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
		return isNotSerpon();
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
		return -1.6F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected float getSinglePassengerXOffset() {
		return 0.0F;
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		float f2 = this.getSinglePassengerXOffset();
		float f3 = 0f;
		if (this.getPassengers().size() > 1) {
			int i = this.getPassengers().indexOf(entity);
			if (i == 0) {
				f2 = 0F;
				f3 = 0F;
			}
			if (i == 1) {
				f2 = -0.6F;
				f3 = 0.45F;
			}
			if (i == 2) {
				f2 = -0.6F;
				f3 = -0.45F;
			}
		}

		return new Vector3f(f3, entityDimensions.height, f2);
	}
	//
	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle() && this.getFirstPassenger() instanceof Player) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if (!this.onGround() && !Main.isInFluid(this)) {
					this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));
				}
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
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}
	protected int getMaxPassengers() {
		return 3;
	}
	public boolean hasEnoughSpaceFor(Entity entity) {
		return Main.mobWidth(entity) < Main.mobWidth(this);
	}

	@Override
	public boolean isNotSerpon() {
		return this.getEntityData().get(NOT_SERPON);
	}
	public void setNotSerpon(boolean bl) {
		this.getEntityData().set(NOT_SERPON, bl);
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
		compoundTag.putBoolean("IsNotSerpon", this.isNotSerpon());
		compoundTag.putInt("AttackTick", this.getAttackTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("IsNotSerpon")) {
			this.setNotSerpon(compoundTag.getBoolean("IsNotSerpon"));
		}
		this.setAttackTick(compoundTag.getInt("AttackTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(NOT_SERPON, false);
		this.getEntityData().define(ATTACK_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (NOT_SERPON.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		this.setNoGravity(true);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(1.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		this.clearFire();
		LivingEntity passenger = this.getPassengers().isEmpty() ? this : (LivingEntity) this.getPassengers().get(0);
		if (this.getAttackTick() <= 0) {
			if (!this.level().isClientSide()) {
				if (ItemToolBaseSpearBase.getKnownMovement(this).length() > 0.1f || ItemToolBaseSpearBase.getKnownMovement(this).length() < -0.1f) {
					if (passenger != null && (passenger instanceof Player || passenger instanceof Mob mob && mob.getTarget() != null)) {
						List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2f, 0.2f, 2f));
						list.removeIf(livingEntity -> this.getPassengers().contains(livingEntity));
						for (LivingEntity hurt : list) {
							if (hurt == null) continue;
							if (hurt == this) continue;
							if (AttackFind.FindCanNotAttack(passenger, hurt)) continue;
							if (!this.isNotSerpon() && hurt instanceof Mob serpon && OtherEntityFactionFind.isFactionSerponCombatTeam(serpon) && serpon.getTarget() != this && this.getTarget() != serpon && serpon.getTarget() != passenger)
								continue;
							if (OtherEntityFactionFind.isFactionSerponCombatTeam(hurt) && passenger.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()))
								continue;
							AttackFind.attackBegin(passenger, hurt);
							boolean bl2 = AttackFind.attackAfter(passenger, hurt, 0.0f, 0.0f, true, (float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
							if (bl2) {
								hurt.knockback(0.40, Mth.sin(this.getYRot() * 0.0175f), -Mth.cos(this.getYRot() * 0.0175f));
							}
						}
						if (!this.level().isClientSide()) {
							this.setAttackTick(10);
						}
					}
				}
			}
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}

		if (!this.onGround() && this.getTarget() == null && passenger == this) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0,  -0.1f, 0.0));
		}
		if (passenger instanceof Player player) {
			if (!(this.xOld == this.getX() && this.zOld == this.getZ())) {
				double d = Math.abs(this.getX() - this.xOld);
				double d2 = Math.abs(this.getZ() - this.zOld);
				if (d >= 0.01 || d2 >= 0.01) {
					if (this.getDeltaMovement().y < 1 && this.getDeltaMovement().y > -1) {
						float headXRot = player.getXRot();
						this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot / 4 * -0.0015f, 0.0));
					}
				}
			}
		}

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
					if (bl && AttackFind.canRideAbout(this, null, this.getControllingPassenger(), entity) && !entity.isPassenger() && this.hasEnoughSpaceFor(entity) && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses"))))) {
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

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (this.isNotSerpon() && AttackFind.canRideAbout(this, null, this.getControllingPassenger(), player)) {
			if (!this.level().isClientSide) {
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
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean causeFallDamage(float f, float f2, DamageSource damageSource) {
		return false;
	}
	private SoundEvent getFallDamageSound(int p_21313_) {
		return p_21313_ > 4 ? this.getFallSounds().big() : this.getFallSounds().small();
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
			this.remove(RemovalReason.DISCARDED);
		}
	}
}