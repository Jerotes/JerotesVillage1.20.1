package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BitterColdBoat extends ItemBaseBoat {
	public BitterColdBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BITTER_COLD);
	}
}