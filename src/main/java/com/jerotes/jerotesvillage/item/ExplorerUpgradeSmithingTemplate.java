package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ExplorerUpgradeSmithingTemplate extends ItemBaseUpgradeSmithingTemplate {
	public ExplorerUpgradeSmithingTemplate() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(CommonComponents.space());
		list.add(Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.applies_to"))).withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.BLUE));
		list.add(Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.ingredients"))).withStyle(ChatFormatting.GRAY));;
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.BLUE));
		list.add(CommonComponents.space());
		list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
		list.add(CommonComponents.space());
		list.add(Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.applies_to"))).withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_4().withStyle(ChatFormatting.BLUE));
		list.add(Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.ingredients"))).withStyle(ChatFormatting.GRAY));;
		list.add(this.getDisplayName_5().withStyle(ChatFormatting.BLUE));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
	public MutableComponent getDisplayName_3() {
		return Component.translatable(this.getDescriptionId() + ".desc_3");
	}
	public MutableComponent getDisplayName_4() {
		return Component.translatable(this.getDescriptionId() + ".desc_4");
	}
	public MutableComponent getDisplayName_5() {
		return Component.translatable(this.getDescriptionId() + ".desc_5");
	}
}
