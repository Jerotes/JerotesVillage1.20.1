package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.Other.OminousBombEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class OminousBomb extends Item implements JerotesItemThrowUse {
	public OminousBomb() {
		super(new Properties().stacksTo(16).rarity(Rarity.COMMON));
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.ITEM_THROW, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
		player.getCooldowns().addCooldown(this, 20);
		if (!level.isClientSide) {
			Projectile throwitem = useJerotesThrowShoot(player, interactionHand);
			throwitem.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, useJerotesThrowShootSpeed(player, interactionHand), 1.0f);
			level.addFreshEntity(throwitem);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}

	@Override
	public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof OminousBomb ominousBomb) {
			if (!livingEntity.level().isClientSide()) {
				OminousBombEntity throwItem = new OminousBombEntity(livingEntity.level(), livingEntity);
				throwItem.setItem(livingEntity.getItemInHand(interactionHand));
				return throwItem;
			}
		}
		return null;
	}
	@Override
	public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof OminousBomb ominousBomb) {
			if (!livingEntity.level().isClientSide()) {
				return 1.6f;
			}
		}
		return 1.6f;
	}

	@Override
	public boolean isJerotesThrow() {
		return true;
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
