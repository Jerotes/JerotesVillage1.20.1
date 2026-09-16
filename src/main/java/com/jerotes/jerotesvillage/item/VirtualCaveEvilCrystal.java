package com.jerotes.jerotesvillage.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class VirtualCaveEvilCrystal extends Item {
	public VirtualCaveEvilCrystal() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
