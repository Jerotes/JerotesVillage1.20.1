package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedFlag extends Item {
	public CarvedFlag() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer().isShiftKeyDown()) {
			if (context.getPlayer().getCooldowns().isOnCooldown(context.getItemInHand().getItem())) {
				context.getPlayer().getCooldowns().removeCooldown(context.getItemInHand().getItem());
			} else {
				context.getPlayer().getCooldowns().addCooldown(context.getItemInHand().getItem(), 6000);
			}
			context.getPlayer().level().playSound(context.getPlayer(), context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), SoundEvents.METAL_BREAK, SoundSource.NEUTRAL, 1.5f, 0.4f / (context.getPlayer().level().getRandom().nextFloat() * 0.4f + 0.8f));
			context.getPlayer().swing(context.getPlayer().getUsedItemHand());
			context.getPlayer().stopUsingItem();
			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}


	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity instanceof CarvedEntity carvedEntity) {
			player.swing(InteractionHand.MAIN_HAND);
			if (carvedEntity.trusts(player.getUUID())) {
				if (player.level() instanceof ServerLevel) {
					player.sendSystemMessage(Component.translatable("message.jerotesvillage.carved_trust_yes", livingEntity.getDisplayName(), player.getDisplayName()).withStyle(ChatFormatting.GOLD));
				}
			} else {
				if (player.level() instanceof ServerLevel) {
					player.sendSystemMessage(Component.translatable("message.jerotesvillage.carved_trust_no", livingEntity.getDisplayName(), player.getDisplayName()).withStyle(ChatFormatting.GOLD));
				}
			}
			return InteractionResult.sidedSuccess(player.level().isClientSide());
		}
		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
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

}
