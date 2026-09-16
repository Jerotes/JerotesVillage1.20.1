package com.jerotes.jerotesvillage.entity.Humanoid.Meror;

import com.jerotes.jerotes.entity.Interface.LoreUndyingEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Interface.MerorTransportEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class MerorEntity extends SecondRounderEntity implements MerorTransportEntity, LoreUndyingEntity {
	private static final EntityDataAccessor<Boolean> SHOULD_TELEPORT = SynchedEntityData.defineId(MerorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> MEROR_LOCATION = SynchedEntityData.defineId(MerorEntity.class, EntityDataSerializers.INT);

	public MerorEntity(EntityType type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	@Override
	public void MobInventoryAddTooltip(List<Component> tooltip, LivingEntity livingEntity) {
		tooltip.add(Component.translatable("message.jerotesvillage.meror_location").append(Component.translatable("message.jerotesvillage.meror_location_" + this.getMerorLocation())).withStyle(ChatFormatting.AQUA));
	}

	@Override
	public String getFirstFactionTypeName() {
		return "meror_civilization";
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new WantedGoal(LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
	}
	class WantedGoal extends NearestAttackableTargetGoal<LivingEntity> {
		@Nullable
		private LivingEntity attack;

		public WantedGoal(Class<LivingEntity> class_, boolean bl, @Nullable boolean bl2, Predicate<LivingEntity> predicate) {
			super(MerorEntity.this, class_, 10, bl, bl2, predicate);
		}

		@Override
		public boolean canUse() {
			if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			if (this.mob.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator())) {
				return false;
			}
			List<LivingEntity> list = MerorEntity.this.level().getEntitiesOfClass(LivingEntity.class, MerorEntity.this.getBoundingBox().inflate(32.0, 32.0, 32.0));
			double d = Double.MAX_VALUE;
			for (LivingEntity wanted : list) {
				if (wanted == null) continue;
				if (wanted == MerorEntity.this) continue;
				if ((MerorEntity.this.distanceToSqr(wanted)) > d) continue;
				if (MerorEntity.this.getTarget() != null) continue;
				if (!(wanted.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()))) continue;
				if (!(MerorEntity.this.canAttack(wanted) && MerorEntity.this.canAttackType(wanted.getType()))) continue;
				if (wanted.isSpectator()) continue;
				if (wanted instanceof Player player && player.isCreative()) continue;
				if (MerorEntity.this.getTarget() == null || wanted.isAlive()) {
					if (!(wanted.isAlliedTo(MerorEntity.this))){
						attack = wanted;
						return true;
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

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return this.isBaby() ? 0.9f : 1.8f;
	}

	public int runAwayTick;
	public int getMerorLocation() {
		return this.getEntityData().get(MEROR_LOCATION);
	}
	public void setMerorLocation(int n) {
		this.getEntityData().set(MEROR_LOCATION, n);
	}
	public boolean isShouldTeleport() {
		return this.getEntityData().get(SHOULD_TELEPORT);
	}
	public void setShouldTeleport(boolean bl) {
		this.getEntityData().set(SHOULD_TELEPORT, bl);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("MerorLocation", this.getMerorLocation());
		compoundTag.putBoolean("IsShouldTeleport", this.isShouldTeleport());
		compoundTag.putInt("RunAwayTick", this.runAwayTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setMerorLocation(compoundTag.getInt("MerorLocation"));
		this.setShouldTeleport(compoundTag.getBoolean("RunAwayTick"));
		this.runAwayTick = compoundTag.getInt("RunAwayTick");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MEROR_LOCATION, 1);
		this.getEntityData().define(SHOULD_TELEPORT, this instanceof MerorMerchantEntity || this instanceof MerorBountyHunterEntity || this instanceof MerorAdventurerEntity);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isEyeInFluid(FluidTags.WATER) && !this.hasEffect(MobEffects.WATER_BREATHING)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
			}
		}
		if ((this.isOnFire() || this.getLastDamageSource() != null && this.getLastDamageSource().is(DamageTypeTags.IS_FIRE)) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			if (!this.level().isClientSide()) {
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
			}
		}

		if (this.isShouldTeleport()) {
			if (this.runAwayTick > -48000 && !this.hasCustomName()) {
				this.runAwayTick -= 1;
			} else if (this.runAwayTick <= -40 && this.getTarget() == null) {
				this.runAwayTick += 40;
			}
			if (this.getHealth() < this.getMaxHealth() / 4) {
				this.runAwayTick -= 120;
			}
			if (this.runAwayTick <= -45600) {
				this.transportSoon(this);
			}
			if (this.runAwayTick <= -48000) {
				this.transport(this);
			}
		}
		else  {
			this.runAwayTick = 0;
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isWaterDamage(damageSource) && !this.hasEffect(MobEffects.WATER_BREATHING)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
			}
		}
		if (AttackFind.isFireDamage(damageSource) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
			}
		}
		return super.hurt(damageSource, amount);
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
		if (!this.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()) && !livingEntity.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
			if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
					EntityFactionFind.isFaction(this, livingEntity)
			) {
				return false;
			}
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			if (newSpawn == 0) {
				this.setMerorLocation(Main.randomReach(serverLevelAccessor.getRandom(), 1, 8));
			}
		}
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		return spawnGroupData;
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.transport(this);
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}
