package com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball;

import com.jerotes.jerotes.entity.Interface.ConcentrationSpellEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.goal.JerotesChangeOwnerHurtByTargetGoal;
import com.jerotes.jerotes.goal.JerotesChangeOwnerHurtTargetGoal;
import com.jerotes.jerotes.goal.JerotesLeapAtTargetAndLookGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.network.JerotesPlayerData;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.UUID;

public class FlyingFireballEntity extends MagicSummonedEntity implements JerotesEntity, FireAbsorptionEntity, ConcentrationSpellEntity {
	private static final EntityDataAccessor<Float> EXPLODE_LEVEL = SynchedEntityData.defineId(FlyingFireballEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> HAS_EXPLODE = SynchedEntityData.defineId(FlyingFireballEntity.class, EntityDataSerializers.BOOLEAN);

	private int fireTick = 0;

	public FlyingFireballEntity(EntityType<? extends FlyingFireballEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(0.6f);
		this.xpReward = 10;
		this.moveControl = new FlyingMoveControl(this, 10, true);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
	}

	public float getExplodeLevel() {
		return this.getEntityData().get(EXPLODE_LEVEL);
	}
	public void setExplodeLevel(float level) {
		this.getEntityData().set(EXPLODE_LEVEL, level);
	}
	public boolean isHasExplode() {
		return this.getEntityData().get(HAS_EXPLODE);
	}
	public void setHasExplode(boolean hasExplode) {
		this.getEntityData().set(HAS_EXPLODE, hasExplode);
	}


	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putFloat("ExplodeLevel", this.getExplodeLevel());
		compoundTag.putBoolean("HasExplode", this.isHasExplode());
		compoundTag.putInt("FireTick", this.fireTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setExplodeLevel(compoundTag.getFloat("ExplodeLevel"));
		this.setHasExplode(compoundTag.getBoolean("HasExplode"));
		this.fireTick = compoundTag.getInt("FireTick");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(EXPLODE_LEVEL, 0f);
		this.getEntityData().define(HAS_EXPLODE, false);
	}

	public boolean isJerotesFlyingMob() {
		return true;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.FLYING_SPEED, 1.25);
		builder = builder.add(Attributes.MAX_HEALTH, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new JerotesLeapAtTargetAndLookGoal(this, (float) 0.5));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
	}
	protected void targetGoals() {
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this) {
			public boolean canUse() {
				if (FlyingFireballEntity.this.tickCount < 10 && FlyingFireballEntity.this.isHasExplode()) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if (FlyingFireballEntity.this.tickCount < 10 && FlyingFireballEntity.this.isHasExplode()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this) {
			public boolean canUse() {
				if (FlyingFireballEntity.this.tickCount < 60 && FlyingFireballEntity.this.isHasExplode()) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if (FlyingFireballEntity.this.tickCount < 60 && FlyingFireballEntity.this.isHasExplode()) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.FIRE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.FIRE_EXTINGUISH;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.FIRE_EXTINGUISH;
	}

	@Override
	protected float getSoundVolume() {
		return 1.2f;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean isSensitiveToWater() {
		return true;
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
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	public double FireAbsorptionPercentage() {
		return 60;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		if (this.level().isClientSide) {
			this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			this.level().addParticle(ParticleTypes.FLAME, this.getX(0), this.getY(0.5), this.getZ(0), 0.0, 0.04, 0.0);
		}
		if (!(this.getOwner() != null && this.distanceTo(this.getOwner()) <= 8)) {
			if (!this.level().isClientSide) {
				if (!this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
					return;
				}
				BlockState blockState = Blocks.FIRE.defaultBlockState();
				if (fireTick == 0) {
					for (int i = 0; i < 2; ++i) {
						int n = Mth.floor(this.getX() + (double) ((float) (i % 2 * 2 - 1) * 0.25f));
						int n2 = Mth.floor(this.getY());
						int n3 = Mth.floor(this.getZ() + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25f));
						BlockPos blockPos = new BlockPos(n, n2, n3);
						if (!this.level().getBlockState(blockPos).isAir() || !blockState.canSurvive(this.level(), blockPos))
							continue;
						this.level().setBlockAndUpdate(blockPos, blockState);
						this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
						fireTick = 60;
					}
				}
			}
		}
		if (this.fireTick > 0) {
			this.fireTick -= 1;
		}

		if (!this.level().isClientSide() && this.isHasExplode() && this.getTarget() != null && this.distanceToSqr(this.getTarget()) <= 6) {
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FIRE, this, this.getOwner());
			if (this.level() instanceof ServerLevel) {
				for (int i = 0; i < 6; ++i) {
					ParticlesUse.sendBallParticles(this, ParticleTypes.FLAME, true, 1.0f, getExplodeLevel() * 0.5f);
				}
			}
			this.level().explode(this, damageSource, null, this.getX(), this.getY(), this.getZ(), getExplodeLevel(), true, Level.ExplosionInteraction.NONE);
			this.setHasExplode(false);
		}
		if (!this.level().isClientSide() && this.isHasExplode() && this.getTarget() != null) {
			this.RushAttack();
		}
	}

	public boolean RushAttack() {
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.35f;
		float f8 = f4 *= f7 / f6;
		this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6, f8, f5 *= f7 / f6));
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.FIRE_AMBIENT, 1.0f, 1.0f);
			}
			entity.setSecondsOnFire(14);
		}
		return super.doHurtTarget(entity);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL) || AttackFind.isFireDamage(damageSource)) {
			return true;
		}
		return super.isInvulnerableTo(damageSource);
	}


	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.endConcentration();
			if (this.getLastDamageSource() != null && !this.getLastDamageSource().is(JerotesDamageTypes.MAGIC_EFFECT)) {
				this.level().broadcastEntityEvent(this, (byte) 60);
				this.remove(RemovalReason.KILLED);
			}
			else {
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public void remove(RemovalReason removalReason) {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_FLYING_FIREBALL.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		super.remove(removalReason);
	}

	@Override
	public boolean endConcentration() {
		if (this.getOwner() != null) {
			SpellType.setConcentrationSpellTypeJerotes(this.getOwner(), "");
			LivingEntity var2 = this.getOwner();
			if (var2 instanceof Player) {
				Player player = (Player) var2;
				player.getCapability(JerotesPlayerData.CAPABILITY, (Direction) null).ifPresent((capability) -> {
					capability.setConcentrationSpellTarget("");
					capability.syncPlayerVariables(player);
				});
			}
			SpellType.setConcentrationSpellTargetJerotes(this.getOwner(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
			SpellType.endConcentrationMessage(this.getOwner(), OtherSpellType.JEROTESVILLAGE_CONJURE_FLYING_FIREBALL.magicSpellGet(1, (LivingEntity) null, (Entity) null).getSpellName());
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			ParticlesUse.summonParticle(serverLevel, this, this.getX(), this.getY(), this.getZ(),
					0xa32800, 0xeac900);
		}
		this.discard();
		return true;
	}
}