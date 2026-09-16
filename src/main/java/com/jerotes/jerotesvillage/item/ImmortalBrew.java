package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ImmortalBrew extends BowlFoodItem {
	public ImmortalBrew() {
		super(new Properties().stacksTo(16).rarity(Rarity.UNCOMMON).food(JerotesVillageFoods.IMMORTAL_BREW));
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.DRINK;
	}

	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Player player1) {
			clearAllCooldowns(player1);
		}
		return super.finishUsingItem(itemStack, level, livingEntity);
	}

	public void clearAllCooldowns(Player player) {
		ItemCooldowns cooldowns = player.getCooldowns();
		cooldowns.removeCooldown(player.getMainHandItem().getItem());
		cooldowns.removeCooldown(player.getOffhandItem().getItem());

//		for (ItemStack stack : player.getInventory().items) {
//			if (!stack.isEmpty()) {
//				cooldowns.removeCooldown(stack.getItem());
//			}
//		}
//		ItemStack offhand = player.getInventory().offhand.get(0);
//		if (!offhand.isEmpty()) {
//			cooldowns.removeCooldown(offhand.getItem());
//		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
