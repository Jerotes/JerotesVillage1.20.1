package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Greyscale extends Item {
	public static final String FISH_SIZE = "FishSize";

	public Greyscale() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.GREYSCALE));
	}

	public int getFishSize(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		return compoundtag.getInt(FISH_SIZE);
	}

	public static void setFishSize(ItemStack itemStack, int n) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		compoundtag.putInt(FISH_SIZE,  n);
		itemStack.setTag(compoundtag);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName(itemStack).withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName(ItemStack itemStack) {
		return Component.translatable(this.getDescriptionId() + ".desc", this.getFishSize(itemStack));
	}
}
