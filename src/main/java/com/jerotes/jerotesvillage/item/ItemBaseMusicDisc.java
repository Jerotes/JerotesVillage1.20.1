package com.jerotes.jerotesvillage.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

public class ItemBaseMusicDisc extends RecordItem {
	public ItemBaseMusicDisc(int n, SoundEvent soundEvent, Item.Properties properties, int n2) {
		super(n, soundEvent, properties, n2);
	}

	@Override
	public Component getName(ItemStack itemStack) {
		return Component.translatable("item.minecraft.music_disc_5");
	}
}
