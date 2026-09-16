package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class WonderlinFakeEars extends Item implements Equipable, DyeableLeatherItem {
	public WonderlinFakeEars() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public int getColor(ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getTagElement(TAG_DISPLAY);
		if (compoundTag != null && compoundTag.contains(TAG_COLOR, 99)) {
			return compoundTag.getInt(TAG_COLOR);
		}
		return -1;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}


