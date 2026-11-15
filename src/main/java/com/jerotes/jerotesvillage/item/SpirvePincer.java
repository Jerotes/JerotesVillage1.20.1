package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBasePincer;

public class SpirvePincer extends ItemToolBasePincer {

	@Override
	public int getEnchantmentValue() {
		return 14;
	}

	public SpirvePincer() {
		super(new Properties().durability(250), 5.0f,1f);
	}
}
