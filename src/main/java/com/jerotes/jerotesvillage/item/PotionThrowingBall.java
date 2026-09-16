package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.PotionThrowingBallEntity;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PotionThrowingBall extends ItemToolBaseThrowingBall {
	public PotionThrowingBall() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON), 1, 2, 4);
	}

	@Override
	public BaseThrowingBallEntity throwingBall(Level level, LivingEntity livingEntity) {
		return new PotionThrowingBallEntity(level, livingEntity);
	}
	public ItemStack getDefaultInstance() {
		return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1(itemStack).withStyle(ChatFormatting.GRAY));
		//1.20.4↑//
		//PotionUtils.addPotionTooltip(itemStack, list, 0.1f, level == null ? 20.0f : level.tickRateManager().tickrate());
		//1.20.1//
		PotionUtils.addPotionTooltip(itemStack, list, 0.1f);
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
	public MutableComponent getDisplayName_1(ItemStack itemStack) {
		return Component.translatable(PotionUtils.getPotion(itemStack).getName("item.minecraft.potion.effect."));
	}
}
