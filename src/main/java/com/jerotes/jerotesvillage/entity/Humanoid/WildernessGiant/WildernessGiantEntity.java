package com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant;

import com.jerotes.jerotes.goal.JerotesOpenTwoDoorGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

public class WildernessGiantEntity extends SecondRounderEntity {
	public WildernessGiantEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 10;
		setMaxUpStep(1.1f);
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wilderness_giant_tribes";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new JerotesOpenTwoDoorGoal(this, true));
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return this.isBaby() ? 1.63f : 3.05f;
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
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0f, entityDimensions.height + 0.0625f * f, 0.0f);
	}
	protected float ridingOffset(Entity entity) {
		return -1.0f;
	}
	public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.65D;
	}


	@Override
	public void aiStep() {
		this.updateSwingTime();
		this.updateNoActionTime();
		if (!this.isGiant()) {
			this.setGiant(true);
		}
		super.aiStep();
	}
	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 1.1;
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get()) {
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
}
