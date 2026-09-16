package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.goal.AxCrazyMeleeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class PurpleSandRabbitEntity extends Rabbit implements JerotesEntity {
	private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("63a4828e-d752-4312-a301-24328d691b2f");
	private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("fab8fcac-8a49-4365-b08a-e887a5ada48b");
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("d1ba2817-e17c-4734-b218-d8c7b708adec");
	private static final EntityDataAccessor<Boolean> FIRST_HEAL = SynchedEntityData.defineId(PurpleSandRabbitEntity.class, EntityDataSerializers.BOOLEAN);
	public PurpleSandRabbitEntity(EntityType<? extends PurpleSandRabbitEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 10.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		return builder;
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
		if (this.getVariant() == Variant.EVIL) {
			return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
		}
		return super.isWithinMeleeAttackRange(livingEntity);
	}
	@Override
	protected AABB makeBoundingBox() {
		if (this.getVariant() == Variant.EVIL) {
			return AABB.ofSize(new Vec3(this.getX(), this.getY() + 0.75, this.getZ()), 1.2, 1.5, 1.2);
		}
		return super.makeBoundingBox();
	}

	public void setVariant(Variant variant) {
		super.setVariant(variant);
		Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (this.getVariant() == Variant.EVIL) {
			this.goalSelector.addGoal(3, new AxCrazyMeleeAttackGoal(this, 1.4, true));
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "attack damage", 7, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_UUID, "max health", 20, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "movement speed", 0.4, AttributeModifier.Operation.ADDITION));
			if (this.isFirstHeal()) {
				this.setHealth(this.getMaxHealth());
				this.entityData.set(FIRST_HEAL, false);
			}
		}
		this.setBoundingBox(this.makeBoundingBox());
	}
	public boolean isFirstHeal() {
		return this.entityData.get(FIRST_HEAL);
	}
	public void setFirstHeal(boolean bl) {
		this.entityData.set(FIRST_HEAL, bl);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putBoolean("IsFirstHeal", this.isFirstHeal());
		super.addAdditionalSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setFirstHeal(compoundTag.getBoolean("IsFirstHeal"));
		super.readAdditionalSaveData(compoundTag);
		this.setBoundingBox(this.makeBoundingBox());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FIRST_HEAL, true);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getVariant() == Variant.EVIL && this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360F, 360F);
			this.lookAt(this.getTarget(), 360F, 360F);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (this.getVariant() == Variant.EVIL) {
			if (this.level().isClientSide) {
				for (int i = 0; i < 3; ++i) {
					this.level().addParticle(JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN.get(), this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0.0, 0.0, 0.0);
				}
			}
		}
	}

	public void startJumping() {
		super.startJumping();
		if (this.getVariant() == Variant.EVIL) {
			if (this.getVariant() == Variant.EVIL && this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360F, 360F);
				this.lookAt(this.getTarget(), 360F, 360F);
				this.RushAttack();
			}
		}
	}
	public boolean RushAttack() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.25f;
		float f8 = f4 *= f7 / f6 * 2;
		this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getVariant() == Variant.EVIL) {
			this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			AttackFind.attackBegin(this, entity);
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
			for (int i = 0; i < 6; ++i) {
				AttackFind.attackAfterCustomDamage(this, entity, damageSource, 0.5f, 0f, false, 0);
			}
			return AttackFind.attackAfterCustomDamage(this, entity, damageSource, 0.5f, 0f, false, 0);
		} else {
			AttackFind.attackBegin(this, entity);
			return AttackFind.attackAfter(this, entity, 1, 1.0f, false, 0);
		}
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 12;
	}

	@Nullable
	@Override
	public PurpleSandRabbitEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.PURPLE_SAND_RABBIT.get().create(serverLevel);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.getVariant() == Variant.EVIL) {
			return super.hurt(damageSource, amount/3);
		}
		return super.hurt(damageSource, amount);
	}
}
