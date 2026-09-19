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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class HeroSoup extends BowlFoodItem {
	public HeroSoup() {
		super(new Properties().stacksTo(16).rarity(Rarity.COMMON).food(JerotesVillageFoods.HERO_SOUP));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
		Player player;
		super.finishUsingItem(itemStack, level, livingEntity);
		if (!level.isClientSide) {
			livingEntity.removeEffect(JerotesMobEffects.ABACK.get());
			livingEntity.removeEffect(MobEffects.WEAKNESS);
			livingEntity.removeEffect(JerotesMobEffects.ANESTHETIZED.get());
		}
		if (level.isClientSide) {
			Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(JerotesVillageItems.ABUNDANT_COURAGE.get()));
		}
		if (itemStack.isEmpty()) {
			return new ItemStack(Items.BOWL);
		}
		if (livingEntity instanceof Player) {
			player = (Player)livingEntity;
			if (!player.getAbilities().instabuild) {
				ItemStack itemStack2 = new ItemStack(Items.BOWL);
				if (!player.getInventory().add(itemStack2)) {
					player.drop(itemStack2, false);
				}
			}
		}
		return itemStack;
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
