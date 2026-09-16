package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item;

public class VirtualCaveFishingNetBlockItem extends BlockItem implements Equipable {

	public VirtualCaveFishingNetBlockItem() {
		super(JerotesVillageBlocks.VIRTUAL_CAVE_FISHING_NET_BLOCK.get(), new Item.Properties());
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}

}
