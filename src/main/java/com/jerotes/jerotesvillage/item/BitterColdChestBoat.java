package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BitterColdChestBoat extends ItemBaseChestBoat {
	public BitterColdChestBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BITTER_COLD);
	}
}