package com.jerotes.jerotesvillage.entity.Other.Boat;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ABaseBoatEntity extends Boat {
	private static final EntityDataAccessor<Integer> MOD_BOAT_TYPE = SynchedEntityData.defineId(ABaseBoatEntity.class, EntityDataSerializers.INT);

	public ABaseBoatEntity(EntityType<ABaseBoatEntity> entityType, Level level) {
		super(entityType, level);
	}

	public ABaseBoatEntity(Level level, double positionX, double positionY, double positionZ) {
		super(JerotesVillageEntityType.ABASE_BOAT.get(), level);
		this.setPos(positionX, positionY, positionZ);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = positionX;
		this.yo = positionY;
		this.zo = positionZ;
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("ModType", this.getModBoatType().getName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("ModType", 8))
			this.setModBoatType(ABaseBoatType.byName(compound.getString("ModType")));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MOD_BOAT_TYPE, ABaseBoatType.BITTER_COLD.ordinal());
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	public void setModBoatType(ABaseBoatType type) {
		this.entityData.set(MOD_BOAT_TYPE, type.ordinal());
	}

	public ABaseBoatType getModBoatType() {
		return ABaseBoatType.byId(this.entityData.get(MOD_BOAT_TYPE));
	}

	@Override
	public Item getDropItem() {
		switch(this.getModBoatType()) {
			case BITTER_COLD:
				return JerotesVillageItems.BITTER_COLD_BOAT.get();
			case WONDERLAND:
				return JerotesVillageItems.WONDERLAND_BOAT.get();
			case BLACK_YELLOW:
				return JerotesVillageItems.BLACK_YELLOW_BOAT.get();
			case BRIGHT_GLOW:
				return JerotesVillageItems.BRIGHT_GLOW_BOAT.get();
			default:
				return JerotesVillageItems.BITTER_COLD_BOAT.get();
		}
	}
}