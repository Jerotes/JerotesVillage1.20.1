package com.jerotes.jerotesvillage.effect;

import com.jerotes.jerotes.effect.BaseMobEffectAllTick;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class GlowSparrowBlessingEffect extends BaseMobEffectAllTick {
	public GlowSparrowBlessingEffect() {
		super(MobEffectCategory.BENEFICIAL, 0xcec0ff);
	}

	@Override
	public String getDescriptionId() {
		return "effect.jerotesvillage.glow_sparrow_blessing";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		if (livingEntity.isAlive() && livingEntity.tickCount % 10 == 0) {
			if (livingEntity.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				BlockState blockState = JerotesVillageBlocks.GLOW_SHINE.get().defaultBlockState();
				BlockPos blockPos = livingEntity.getOnPos().above().above().above();
				if ((livingEntity.level().getBlockState(blockPos).isAir() || livingEntity.level().getBlockState(blockPos).getBlock() == JerotesVillageBlocks.GLOW_SHINE.get()) && blockState.canSurvive(livingEntity.level(), blockPos)) {
					livingEntity.level().setBlockAndUpdate(blockPos, blockState);
					livingEntity.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(livingEntity, blockState));
				}
			}
		}
	}
}
