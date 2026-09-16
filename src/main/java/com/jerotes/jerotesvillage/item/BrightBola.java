package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.BrightBolaEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrightBola extends Item implements JerotesItemThrowUse {
	public BrightBola() {
		super(new Properties().stacksTo(16).rarity(Rarity.COMMON));
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public boolean isJerotesThrow() {
		return true;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player)) {
			return;
		}
		Player player2 = (Player)livingEntity;
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		if (!level.isClientSide) {
			BrightBolaEntity thrownJavelin = new BrightBolaEntity(level, player2, itemStack);
				thrownJavelin.shootFromRotation(player2, player2.getXRot(), player2.getYRot(), 0.0f, 1.5f, 1.0f);
				if (player2.getAbilities().instabuild) {
					thrownJavelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				level.addFreshEntity(thrownJavelin);
				level.playSound(null, thrownJavelin, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.0f);
				if (!player2.getAbilities().instabuild) {
					itemStack.shrink(1);
				}

		}
		player2.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof BrightBola brightBola) {
			if (!livingEntity.level().isClientSide()) {
				BrightBolaEntity throwItem = new BrightBolaEntity(livingEntity.level(), livingEntity, livingEntity.getItemInHand(interactionHand));
				return throwItem;
			}
		}
		return null;
	}
	@Override
	public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof BrightBola brightBola) {
			if (!livingEntity.level().isClientSide()) {
				return 1.5f;
			}
		}
		return 1.5f;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.SPEAR;
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


