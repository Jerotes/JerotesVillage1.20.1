package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.ThrowingStubbornStoneEntity;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class ThrowingStubbornStone extends ItemToolBaseThrowingBall {
	public ThrowingStubbornStone() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON), 1, 2, 4);
	}

	@Override
	public BaseThrowingBallEntity throwingBall(Level level, LivingEntity livingEntity) {
		return new ThrowingStubbornStoneEntity(level, livingEntity);
	}
}
