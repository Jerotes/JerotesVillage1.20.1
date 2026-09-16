package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WonderlandRamblerEntity extends RamblerEntity {

	public WonderlandRamblerEntity(EntityType entityType, Level level) {
		super(entityType, level);
	}
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Nullable
	@Override
	public WonderlandRamblerEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.WONDERLAND_RAMBLER.get().create(serverLevel);
	}
}




