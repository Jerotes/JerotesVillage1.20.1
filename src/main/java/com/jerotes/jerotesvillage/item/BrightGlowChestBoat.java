package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BrightGlowChestBoat extends ItemBaseChestBoat {
	public BrightGlowChestBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BRIGHT_GLOW);
	}
}
