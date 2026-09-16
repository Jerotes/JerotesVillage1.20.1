package com.jerotes.jerotesvillage.entity.Neutral;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorBountyHunterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MerorLocatorEntity extends PathfinderMob implements JerotesEntity {
	private static final EntityDataAccessor<Boolean> CALL = SynchedEntityData.defineId(MerorLocatorEntity.class, EntityDataSerializers.BOOLEAN);
	public MerorLocatorEntity(EntityType<? extends MerorLocatorEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0f);
		xpReward = 0;
		setPersistenceRequired();
		this.setJumping(false);
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		if (entity instanceof MerorBountyHunterEntity && this.isCall()) {
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 60; ++i) {
				float f2 = (this.random.nextFloat() * 4.0f - 1.0f) * this.getBbWidth() * 1f;
				float f3 = (this.random.nextFloat() * 4.0f - 1.0f) * this.getBbWidth() * 1f;
				this.level().addParticle(JerotesVillageParticleTypes.MEROR.get(), this.getX() + (double)f2, (this.getY() + getEyeY()) + (double)f2, this.getZ() + (double)f3, vec3.x, vec3.y - 0.1, vec3.z);
			}
			this.discard();
		}
		super.doPush(entity);
	}
	@Override
	public boolean isPushable() {
		return super.isPushable() && this.isCall();
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getTarget() == null || !this.getTarget().hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
			double d = Double.MAX_VALUE;
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == this) continue;
				if ((this.distanceToSqr(find)) > d) continue;
				if (this.getTarget() != null) continue;
				if (!find.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) continue;
				this.setTarget(find);
			}
		}


		if (this.callTick < 300){
			this.callTick += 1;
		}
		if (!this.isCall() && this.random.nextInt(8) == 1) {
			this.level().addParticle(JerotesVillageParticleTypes.MEROR_LOCATOR.get(), this.getRandomX(0.2), this.getRandomY(), this.getRandomZ(0.2), 0.0, 0.05, 0.0);
		}
		if (!this.isCall() && this.callTick >= 300) {
			if (this.level() instanceof ServerLevel serverLevel) {
				PlayerTeam teams = (PlayerTeam) this.getTeam();
				BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(this, 32);
				MerorBountyHunterEntity hunter1 = null;
				if (blockPos != null) {
					hunter1 = JerotesVillageEntityType.MEROR_BOUNTY_HUNTER.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				}
				if (hunter1 != null) {
					if (!this.level().isClientSide) {
						this.setCall(true);
					}
					this.bomb = 1;
					if (this.getTarget() != null) {
						hunter1.setTarget(this.getTarget());
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MEROR_TELEPORT, this.getSoundSource(), 5.0f, 1.0F);
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(hunter1.getStringUUID(), teams);
					}
					int randomCall = this.random.nextInt( 1, 4);
					for (int i = 0; i < randomCall; ++i) {
						BlockPos blockPosBy = Main.findSpawnPositionNearFillOnBlock(this, 4);
						MerorBountyHunterEntity hunters = null;
						if (blockPosBy != null) {
							hunters = JerotesVillageEntityType.MEROR_BOUNTY_HUNTER.get().spawn(serverLevel, blockPosBy, MobSpawnType.EVENT);
						}
						if (hunters != null) {
							if (this.getTarget() != null) {
								hunters.setTarget(this.getTarget());
							}
							if (teams != null) {
								serverLevel.getScoreboard().addPlayerToTeam(hunters.getStringUUID(), teams);
							}
						}
					}
				}
			}
		}


		if (this.dieTick < 6000){
			this.dieTick += 1;
		}
		if (this.dieTick == 6000) {
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 60; ++i) {
				float f2 = (this.random.nextFloat() * 4.0f - 1.0f) * this.getBbWidth() * 1f;
				float f3 = (this.random.nextFloat() * 4.0f - 1.0f) * this.getBbWidth() * 1f;
				this.level().addParticle(JerotesVillageParticleTypes.MEROR.get(), this.getX() + (double)f2, (this.getY() + getEyeY()) + (double)f2, this.getZ() + (double)f3, vec3.x, vec3.y - 0.1, vec3.z);
			}
			this.discard();
		}

		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.2, 1.0));
			this.fallTick += 1;
			if (!this.isSilent() && this.fallTick >= 20) {
				this.playSound(JerotesVillageSoundEvents.MEROR_LOCATOR_FLY, 1.0f, 1.0f);
				this.fallTick = 0;
			}
		}
		super.aiStep();
	}

	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return JerotesVillageSoundEvents.MEROR_TOUGHNESS;
		}
		return JerotesVillageSoundEvents.MEROR_LOCATOR_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEROR_LOCATOR_DEATH;
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
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| AttackFind.isLightningDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public @NotNull InteractionResult mobInteract(Player sourceentity, @NotNull InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (itemstack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/pincers")))){
			sourceentity.swing(hand);
			itemstack.hurtAndBreak(1, sourceentity, livingEntitys -> sourceentity.broadcastBreakEvent(hand));
			this.bomb = 1;
			this.hurt(this.damageSources().playerAttack(sourceentity), 300);
		}
		return super.mobInteract(sourceentity, hand);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(CALL, false);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (CALL.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	public boolean isCall() {
		return this.getEntityData().get(CALL);
	}

	public void setCall(boolean bl) {
		this.getEntityData().set(CALL, bl);
	}

	private int dieTick;
	private int callTick;
	private int fallTick = 0;
	private int bomb = 0;

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("DieTick", this.dieTick);
		compoundTag.putInt("CallTick", this.callTick);
		compoundTag.putBoolean("IsCall", this.isCall());
		compoundTag.putInt("FallTick", this.fallTick);
		compoundTag.putInt("Bomb", this.bomb);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.dieTick = compoundTag.getInt("DieTick");
		this.callTick = compoundTag.getInt("CallTick");
		this.setCall(compoundTag.getBoolean("IsCall"));
		this.fallTick = compoundTag.getInt("FallTick");
		this.bomb = compoundTag.getInt("Bomb");
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.8);
		return builder;
	}

	@Override
	public void tickDeath () {
		++this.deathTime;
		boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			if (bomb == 0) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0f, bl, Level.ExplosionInteraction.NONE);
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}

