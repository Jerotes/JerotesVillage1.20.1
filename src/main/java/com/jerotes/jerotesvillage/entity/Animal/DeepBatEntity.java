package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DeepBatEntity extends Bat implements JerotesEntity {
	public final AnimationState flyAnimationState = new AnimationState();
	public final AnimationState restAnimationState = new AnimationState();
	public DeepBatEntity(EntityType<? extends DeepBatEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 8.0);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		return builder;
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}

	public void tick() {
		super.tick();
		this.setupAnimationStates();
	}

	private void setupAnimationStates() {
		if (this.isResting()) {
			this.flyAnimationState.stop();
			this.restAnimationState.startIfStopped(this.tickCount);
		} else {
			this.restAnimationState.stop();
			this.flyAnimationState.startIfStopped(this.tickCount);
		}

	}

	@Override
	public void aiStep() {
		super.aiStep();
	}
}
