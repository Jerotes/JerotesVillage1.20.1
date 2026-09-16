package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDesc extends Item {
	private final int chat;
	public ItemDesc(Properties properties, int chat) {
		super(properties);
		this.chat = chat;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		if (chat == 1) {
			list.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
		}
		else if (chat > 1) {
			for (int n = 0; n < chat; ++n) {
				list.add(Component.translatable(this.getDescriptionId() + ".desc_" + n).withStyle(ChatFormatting.GRAY));
			}
		}
	}
}
