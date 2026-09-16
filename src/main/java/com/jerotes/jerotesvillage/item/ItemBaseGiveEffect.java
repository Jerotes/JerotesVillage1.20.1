package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemBaseGiveEffect extends Item {
	public final MobEffect mobEffect;
	public final int time;
	public final int level;
	public final int chat;

	public ItemBaseGiveEffect(Properties properties, MobEffect mobEffect, int time, int level, int chat) {
		super(properties);
		this.mobEffect = mobEffect;
		this.time = time;
		this.level = level;
		this.chat = chat;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (!entity.level().isClientSide()) {
			if (entity instanceof LivingEntity livingEntity) {
				livingEntity.addEffect(new MobEffectInstance(mobEffect, time, level, false, false));
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
