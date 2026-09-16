package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;

public class WonderlandBoat extends ItemBaseBoat {
	public WonderlandBoat() {
		super(new Properties().stacksTo(1), ABaseBoatType.WONDERLAND);
	}
}