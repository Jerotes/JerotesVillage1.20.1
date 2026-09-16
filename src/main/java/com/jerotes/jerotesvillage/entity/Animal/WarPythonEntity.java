package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.CanBeSerponFactionEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesLeapAtTargetAndLookGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.goal.FollowSerponCombatExecuterGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class WarPythonEntity extends Animal implements NeutralMob, JerotesEntity, FactionEntity, FireAbsorptionEntity, CanBeSerponFactionEntity {
	private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(WarPythonEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	public WarPythonEntity(EntityType<? extends WarPythonEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.1f);
		this.setNoGravity(true);
		this.moveControl = new FlyingMoveControl(this, 10, true);

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

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new JerotesLeapAtTargetAndLookGoal(this, (float) 0.5));
		this.goalSelector.addGoal(2, new FollowSerponCombatExecuterGoal(this, 1.1f));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, WarPythonEntity.class).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<WarPythonEntity>(this, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.FLYING_SPEED, 1.0);
		builder = builder.add(Attributes.MAX_HEALTH, 45);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.WAR_PYTHON_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.WAR_PYTHON_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.WAR_PYTHON_DEATH;
	}
	@Override
	public void playBlockFallSound() {
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
	public boolean isNotSerpon() {
		return false;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(Items.FIRE_CHARGE);
	}
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.WAR_PYTHON.get().create(serverLevel);
	}
	@Override
	public double FireAbsorptionPercentage() {
		return 20;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		this.setNoGravity(true);
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		if (this.level().isClientSide) {
			this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			final Entity child = getChild();
			if (child == null) {
				LivingEntity partParent = this;
				final int segments = 16;
				for (int i = 0; i < segments; i++) {
					BasePartLivingEntity part;
					if (!(this instanceof RoyalWarPythonEntity)) {
						part = new BasePartLivingEntity(JerotesVillageEntityType.WAR_PYTHON_PART.get(), partParent, 0.4F, 180, 0);
					}
					else {
						part = new BasePartLivingEntity(JerotesVillageEntityType.ROYAL_WAR_PYTHON_PART.get(), partParent, 0.48F, 180, 0);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						PlayerTeam teams = (PlayerTeam) this.getTeam();
						if (teams != null) {
							serverLevel.getScoreboard().addPlayerToTeam(part.getStringUUID(), teams);
						}
					}
					part.setParent(partParent);
					part.setBodyIndex(i);
					if (partParent == this) {
						this.setChildId(part.getUUID());
					}
					part.setInitialPartPos(this);
					partParent = part;
					this.level().addFreshEntity(part);
				}
			}
		}
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.getChildId() != null) {
			compoundTag.putUUID("ChildUUID", this.getChildId());
		}
		this.addPersistentAngerSaveData(compoundTag);
	}
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID("ChildUUID")) {
			this.setChildId(compoundTag.getUUID("ChildUUID"));
		}
		this.readPersistentAngerSaveData((Level)this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(CHILD_UUID, Optional.empty());
	}
	@Nullable
	public UUID getChildId() {
		return this.entityData.get(CHILD_UUID).orElse(null);
	}
	public void setChildId(@Nullable UUID uniqueId) {
		this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
	}
	public Entity getChild() {
		UUID id = getChildId();
		if (id != null && !this.level().isClientSide) {
			return ((ServerLevel) level()).getEntity(id);
		}
		return null;
	}


	@Override
	public boolean doHurtTarget(Entity entity){
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.WAR_PYTHON_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	protected void doPush(Entity entity) {
		if (!(entity instanceof BasePartLivingEntity)) {
			super.doPush(entity);
		}
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
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isFireDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
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

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}
}

