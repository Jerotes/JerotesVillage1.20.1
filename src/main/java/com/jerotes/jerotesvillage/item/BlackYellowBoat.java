package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class BlackYellowBoat extends ItemBaseBoat {
	public BlackYellowBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.BLACK_YELLOW);
	}
}