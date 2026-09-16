package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class IllagerSignalLight extends Item {
	public IllagerSignalLight() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (player.hasEffect(MobEffects.BAD_OMEN) && Objects.requireNonNull(player.getEffect(MobEffects.BAD_OMEN)).getAmplifier() >= 4) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		if (player.hasEffect(MobEffects.BAD_OMEN)) {
			if (!player.level().isClientSide()) {
				player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 16000, Objects.requireNonNull(player.getEffect(MobEffects.BAD_OMEN)).getAmplifier() + 1, false, true));
			}
		}
		else {
			if (!player.level().isClientSide()) {
				player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 16000, 0, false, true));
			}
		}
		if (level.isClientSide()) {
			Minecraft.getInstance().gameRenderer.displayItemActivation(JerotesVillageItems.ILLAGER_SIGNAL_LIGHT_USE.get().getDefaultInstance());
		}
		player.getCooldowns().addCooldown(this, 20);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
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
