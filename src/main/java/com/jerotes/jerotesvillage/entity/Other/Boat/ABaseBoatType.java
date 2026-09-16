package com.jerotes.jerotesvillage.entity.Other.Boat;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.world.level.block.Block;

public enum ABaseBoatType {
	BITTER_COLD(JerotesVillageBlocks.BITTER_COLD_PLANKS.get(), "bitter_cold"),
	WONDERLAND(JerotesVillageBlocks.BLACK_YELLOW_PLANKS.get(), "wonderland"),
	BLACK_YELLOW(JerotesVillageBlocks.BLACK_YELLOW_PLANKS.get(), "black_yellow"),
	BRIGHT_GLOW(JerotesVillageBlocks.BRIGHT_GLOW_PLANKS.get(), "bright_glow");

	private final Block planks;
	private final String name;

	ABaseBoatType(Block planks, String name) {
		this.planks = planks;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Block getPlanks() {
		return this.planks;
	}

	public String toString() {
		return this.name;
	}

	public static ABaseBoatType byId(int id) {
		ABaseBoatType[] boatEntityType = values();
		if (id < 0 || id >= boatEntityType.length) {
			id = 0;
		}

		return boatEntityType[id];
	}

	public static ABaseBoatType byName(String name) {
		ABaseBoatType[] boatEntityType = values();

		for(int i = 0; i < boatEntityType.length; ++i) {
			if (boatEntityType[i].getName().equals(name)) {
				return boatEntityType[i];
			}
		}

		return boatEntityType[0];
	}
}