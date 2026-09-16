package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class HagEye extends BaseHagEye {
	public HagEye() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(120));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.SPIDER_EYE) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 24000, 0), player);
			}
			player.hurt(player.damageSources().generic(), Main.rollDice(3, 10, player.getRandom()));
			itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
			return InteractionResultHolder.consume(itemStack);
		}
//
//		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(32.0, 32.0, 32.0));
//		for (LivingEntity entityiterator : list) {
//			if (entityiterator != player && !entityiterator.level().isClientSide()) {
//				if (!entityiterator.level().isClientSide) {
//					entityiterator.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
//				}
//			}
//		}
//

		player.startUsingItem(interactionHand);
		if (!level.isClientSide) {
			player.removeEffect(MobEffects.BLINDNESS);
			player.removeEffect(MobEffects.DARKNESS);
		}
		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.HAG_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
		if (!player.getAbilities().instabuild) {
			itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
			player.getCooldowns().addCooldown(this, 120);
		}

		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (!livingEntity.level().isClientSide) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 0, false, false), livingEntity);
		}
		super.onUseTick(level, livingEntity, itemStack, count);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}


}
