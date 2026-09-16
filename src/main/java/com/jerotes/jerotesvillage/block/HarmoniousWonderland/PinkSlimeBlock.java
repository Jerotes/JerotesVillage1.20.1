package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nullable;
import java.util.List;

public class PinkSlimeBlock extends SlimeBlock {
	public PinkSlimeBlock() {
		super(Properties.of().mapColor(MapColor.COLOR_PINK).friction(0.8f).sound(SoundType.SLIME_BLOCK).noOcclusion());
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		if (level.isClientSide) {
			return;
		}
		if (level.hasNeighborSignal(pos) && level instanceof ServerLevel serverLevel) {
			serverLevel.setBlock(pos, JerotesVillageBlocks.PINK_SLIME_BLOCK_ON.get().defaultBlockState(), 2);
		}
		super.neighborChanged(blockstate, level, pos, neighborBlock, fromPos, moving);
	}
	public boolean isSlimeBlock(BlockState state) {
		return true;
	}
	public boolean isStickyBlock(BlockState state) {
		return true;
	}
	public boolean canStickTo(BlockState state, BlockState other) {
		return !(state.isStickyBlock() && other.isStickyBlock());
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}


}
