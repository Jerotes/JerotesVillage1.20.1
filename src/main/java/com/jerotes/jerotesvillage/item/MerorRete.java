package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorReteEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MerorRete extends Item implements ItemSpecialEffect, JerotesItemThrowUse {
	public MerorRete() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(12800));
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player)) {
			return;
		}
		Player player = (Player)livingEntity;
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		if (!level.isClientSide) {
			itemStack.hurtAndBreak(1, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			Projectile thrownItem = useJerotesThrowShoot(player, player.getUsedItemHand());
			if (thrownItem instanceof MerorReteEntity merorReteEntity) {
				merorReteEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, useJerotesThrowShootSpeed(player, player.getUsedItemHand()), 1.0f);
				if (player.getAbilities().instabuild) {
					merorReteEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				level.addFreshEntity(merorReteEntity);
				level.playSound(null, merorReteEntity, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof MerorRete merorRete) {
			if (!livingEntity.level().isClientSide()) {
				MerorReteEntity throwItem = new MerorReteEntity(livingEntity.level(), livingEntity, livingEntity.getItemInHand(interactionHand));
				return throwItem;
			}
		}
		return null;
	}
	@Override
	public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof MerorRete merorRete) {
			if (!livingEntity.level().isClientSide()) {
				return 2.5f;
			}
		}
		return 2.5f;
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public boolean isJerotesThrow() {
		return true;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.SPEAR;
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


