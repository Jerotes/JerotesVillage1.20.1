package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SerponCredential extends Item {

	public SerponCredential() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (player.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) &&
				player.getEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()).getAmplifier() <= 0 && !player.getCooldowns().isOnCooldown(itemStack.getItem())) {
			level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.SERPON_AMBIENT, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.SERPON_CREDENTIAL.get(), 60, 1));
			}
			player.getCooldowns().addCooldown(itemStack.getItem(), 480);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		return super.use(level, player, interactionHand);
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (!entity.level().isClientSide) {
			if (entity instanceof LivingEntity livingEntity && !(livingEntity.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) && livingEntity.getEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()).getAmplifier() > 0)) {
				livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.SERPON_CREDENTIAL.get(), 365, 0, false, false));
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		for (int n = 0; n < 3; ++n) {
			list.add(Component.translatable(this.getDescriptionId() + ".desc_" + n).withStyle(ChatFormatting.GRAY));
		}
	}
}
