package com.jerotes.jerotesvillage.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemRareFire extends Item {
	public ItemRareFire() {
		super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}
}
