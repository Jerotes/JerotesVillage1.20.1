package com.jerotes.jerotesvillage.block.WildernessWoodland;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WildernessDirt extends GrassBlock {
	public WildernessDirt() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f));
	}
}
