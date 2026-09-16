package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class WonderlandChestBoat extends ItemBaseChestBoat {
	public WonderlandChestBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.WONDERLAND);
	}
}