package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BrightGlowBoat extends ItemBaseBoat {
	public BrightGlowBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BRIGHT_GLOW);
	}
}
