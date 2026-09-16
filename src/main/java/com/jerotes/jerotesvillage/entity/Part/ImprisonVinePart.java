package com.jerotes.jerotesvillage.entity.Part;

import com.jerotes.jerotes.entity.Part.BasePartEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.ImprisonVineEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ImprisonVinePart extends BasePartEntity {
	private ImprisonVineEntity ImprisonVine;

	public ImprisonVinePart(EntityType<? extends ImprisonVinePart> t, Level world) {
		super(t, world);
	}

	public ImprisonVinePart(EntityType<? extends ImprisonVinePart> type, ImprisonVineEntity ImprisonVine, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(type, ImprisonVine, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.ImprisonVine = ImprisonVine;
	}

	public ImprisonVinePart(ImprisonVineEntity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(JerotesVillageEntityType.IMPRISON_VINE_PART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.ImprisonVine = parent;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof ImprisonVineEntity) {
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