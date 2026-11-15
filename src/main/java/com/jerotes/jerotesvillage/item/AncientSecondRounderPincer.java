package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBasePincer;

public class AncientSecondRounderPincer extends ItemToolBasePincer {

	@Override
	public int getEnchantmentValue() {
		return 14;
	}

	public AncientSecondRounderPincer() {
		super(new Properties().durability(250), 5.5f,1f);
	}
}
