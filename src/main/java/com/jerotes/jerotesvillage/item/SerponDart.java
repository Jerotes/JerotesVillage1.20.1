package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SerponDart extends ItemDesc {
	public SerponDart() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), 1);
	}

	public float getBaseDamage() {
		return 4.0f;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.jerotes.arrow", new Object[]{this.getBaseDamage()}).withStyle(ChatFormatting.YELLOW));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}
