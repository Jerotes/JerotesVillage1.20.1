package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CollectingArrowsBall extends Item implements Vanishable {
	public CollectingArrowsBall() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(512));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.PINK_SLIME_BALL.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}
	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.TOOT_HORN;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);

		List<AbstractArrow> list = level.getEntitiesOfClass(AbstractArrow.class, player.getBoundingBox().inflate(64, 64, 64));
		for (AbstractArrow arrow : list) {
			if (arrow == null) continue;
			if ((arrow.distanceTo(player)) > 512) continue;
			if (arrow.getOwner() != player) continue;
			arrow.playerTouch(player);
			if (!player.getAbilities().instabuild) {
				itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
			}
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.TELEPORT, SoundSource.NEUTRAL, 1.0f, 1.0f);
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 120);
		}
		return InteractionResultHolder.consume(itemStack);
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
