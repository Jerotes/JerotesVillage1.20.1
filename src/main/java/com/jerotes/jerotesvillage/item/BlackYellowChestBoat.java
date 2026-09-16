package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BlackYellowChestBoat extends ItemBaseChestBoat {
	public BlackYellowChestBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BLACK_YELLOW);
	}
}