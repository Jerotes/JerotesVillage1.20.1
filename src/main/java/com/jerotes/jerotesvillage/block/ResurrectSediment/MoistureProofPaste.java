package com.jerotes.jerotesvillage.block.ResurrectSediment;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockSetTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nullable;
import java.util.List;

public class MoistureProofPaste extends ABaseBlock.ABaseButtonBlock {
    private static final Direction[] ALL_DIRECTIONS = Direction.values();

    public MoistureProofPaste() {
        super(JerotesVillageBlockSetTypes.SEDIMENT, 1,Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.MUD).strength(2f, 2f));
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return true;
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState2.is(blockState.getBlock())) {
            return;
        }
        this.tryAbsorbWater(level, blockPos);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.tryAbsorbWater(level, blockPos);
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
    }

    protected void tryAbsorbWater(Level level, BlockPos blockPos) {
        if (this.removeWaterBreadthFirstSearch(level, blockPos)) {
            level.playSound(null, blockPos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    private boolean removeWaterBreadthFirstSearch(Level level, BlockPos blockPos3) {
        return BlockPos.breadthFirstTraversal(blockPos3, 12, 128, (blockPos, consumer) -> {
            for (Direction direction : ALL_DIRECTIONS) {
                consumer.accept(blockPos.relative(direction));
            }
        }, blockPos2 -> {
            BucketPickup bucketPickup;
            if (blockPos2.equals(blockPos3)) {
                return true;
            }
            BlockState blockState = level.getBlockState(blockPos2);
            FluidState fluidState = level.getFluidState(blockPos2);
            if (!fluidState.is(FluidTags.WATER)) {
                return false;
            }
            Block block = blockState.getBlock();
            //1.20.4↑//
            //boolean pick = block instanceof BucketPickup && !((BucketPickup)(block)).pickupBlock(null, level, blockPos2, blockState).isEmpty();
            //1.20.1//
            boolean pick = block instanceof BucketPickup && !((BucketPickup)(block)).pickupBlock(level, blockPos2, blockState).isEmpty();
            if (pick) {
                return true;
            }
            if (blockState.getBlock() instanceof LiquidBlock) {
                level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
            }
            else if (blockState.is(Blocks.KELP) || blockState.is(Blocks.KELP_PLANT) || blockState.is(Blocks.SEAGRASS) || blockState.is(Blocks.TALL_SEAGRASS)) {
                bucketPickup = blockState.hasBlockEntity() ? (BucketPickup) level.getBlockEntity(blockPos2) : null;
                MoistureProofPaste.dropResources(blockState, level, blockPos2, (BlockEntity)((Object)bucketPickup));
                level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
            }
            else {
                return false;
            }
            return true;
        }) > 1;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }
}