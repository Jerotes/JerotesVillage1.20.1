package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class StubbornStoneCane extends Item implements Vanishable {
	public StubbornStoneCane() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(32));
	}

	public void addEffects(LivingEntity livingEntity) {
		if (!livingEntity.level().isClientSide) {
			livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.THROWING_STUBBORN_STONE.get(), 5, 1, false, false));
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(interactionHand));
		if (!player.isSilent()) {
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.ITEM_THROW, player.getSoundSource(), 1.0f, 1.0f);
		}
		if (!player.level().isClientSide) {
			player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.THROWING_STUBBORN_STONE.get(), 140, 2, false, false));
		}
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 120);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.consume(itemStack);

	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.THROWING_STUBBORN_STONE.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
