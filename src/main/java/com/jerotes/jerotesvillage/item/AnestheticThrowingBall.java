package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemAnesthetized;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.AnestheticThrowingBallEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AnestheticThrowingBall extends ItemToolBaseThrowingBall implements ItemAnesthetized {
	public AnestheticThrowingBall() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON), 1, 2, 4);
	}

	@Override
	public BaseThrowingBallEntity throwingBall(Level level, LivingEntity livingEntity) {
		return new AnestheticThrowingBallEntity(level, livingEntity);
	}

	@Override
	public int getAnesthetized() {
		return 60;
	}
	@Override
	public boolean onlyPlayer() {
		return false;
	}
	@Override
	public boolean onlyThrowing() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable("item.jerotes.anesthetized_shoot", this.getAnesthetized()).withStyle(ChatFormatting.YELLOW));
	}
}
