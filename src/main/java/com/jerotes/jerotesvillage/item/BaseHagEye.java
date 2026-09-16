package com.jerotes.jerotesvillage.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaseHagEye extends Item implements Equipable {
	public BaseHagEye(Properties properties) {
		super(properties);
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}
}
