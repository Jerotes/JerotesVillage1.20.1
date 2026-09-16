package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageFoods;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class HeroSoup extends BowlFoodItem {
	public HeroSoup() {
		super(new Properties().stacksTo(16).rarity(Rarity.COMMON).food(JerotesVillageFoods.HERO_SOUP));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level level, LivingEntity entity) {
		if (!level.isClientSide) {
			entity.removeEffect(JerotesMobEffects.ABACK.get());
			entity.removeEffect(MobEffects.WEAKNESS);
			entity.removeEffect(JerotesMobEffects.ANESTHETIZED.get());
		}
		if (level.isClientSide) {
			Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(JerotesVillageItems.ABUNDANT_COURAGE.get()));
		}
		return super.finishUsingItem(itemstack, level, entity);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
