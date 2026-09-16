package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.item.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MerorMetalGiantBeastArmor extends ItemGiantBeastArmor {
	public MerorMetalGiantBeastArmor() {
			super(21, 9, 1.5, 0xb55667, JerotesVillage.MODID, "meror_metal", new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
