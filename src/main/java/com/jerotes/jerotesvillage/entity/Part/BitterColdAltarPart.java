package com.jerotes.jerotesvillage.entity.Part;

import com.jerotes.jerotes.entity.Part.BasePartEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BitterColdAltarPart extends BasePartEntity {
	private BitterColdAltarEntity bitterColdAltar;

	public BitterColdAltarPart(EntityType<? extends BitterColdAltarPart> t, Level world) {
		super(t, world);
	}

	public BitterColdAltarPart(EntityType<? extends BitterColdAltarPart> type, BitterColdAltarEntity bitterColdAltar, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(type, bitterColdAltar, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.bitterColdAltar = bitterColdAltar;
	}

	public BitterColdAltarPart(BitterColdAltarEntity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(JerotesVillageEntityType.BITTER_COLD_ALTAR_PART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.bitterColdAltar = parent;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof BitterColdAltarEntity) {
			return false;
		}
		return this.isAlive();
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}

	@Override
	public void collideWithNearbyEntities() {
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(new Vec3(0, vec3.y, 0));
	}
}