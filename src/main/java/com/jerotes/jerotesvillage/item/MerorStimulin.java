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

public class MerorStimulin extends Item {
	public MerorStimulin() {
		super(new Properties().stacksTo(16).rarity(Rarity.EPIC).food(JerotesVillageFoods.MEROR_STIMULIN));
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
		Player player;
		super.finishUsingItem(itemStack, level, livingEntity);
		if (itemStack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		if (livingEntity instanceof Player) {
			player = (Player)livingEntity;
			if (!player.getAbilities().instabuild) {
				ItemStack itemStack2 = new ItemStack(Items.GLASS_BOTTLE);
				if (!player.getInventory().add(itemStack2)) {
					player.drop(itemStack2, false);
				}
			}
		}
		return itemStack;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
}
