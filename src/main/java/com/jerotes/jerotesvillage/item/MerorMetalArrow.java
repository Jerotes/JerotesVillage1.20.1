package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorMetalArrowEntity;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MerorMetalArrow extends ItemToolBaseArrow {
	public MerorMetalArrow() {
		super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
		return new MerorMetalArrowEntity(level, livingEntity, itemStack.copyWithCount(1));
	}

	@Override
	public float getBaseDamage() {
		return 4.0f;
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
