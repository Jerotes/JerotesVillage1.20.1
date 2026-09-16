package com.jerotes.jerotesvillage.block.ResurrectSediment;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class ResurrecterEmptyCoffin extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_15;

    public ResurrecterEmptyCoffin() {
        super(Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.METAL).strength(6f, 9f).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public int getLevel(BlockState blockState) {
        return blockState.getValue(LEVEL);
    }

    static int getLevels(BlockState blockState) {
        return blockState.getValue(LEVEL);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        entity.getItemInHand(hand);
        if (!(entity.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING)) {
            return super.use(blockState, world, pos, entity, hand, hit);
        }
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (entity.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING) {
            ItemStack hands = entity.getItemInHand(hand);
            if (!entity.getAbilities().instabuild) {
                hands.shrink(1);
            }
            resurrect(world, blockState, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public void resurrect(Level level, BlockState blockState, BlockPos blockPos) {
        level.setBlock(blockPos, JerotesVillageBlocks.RESURRECTER_COFFIN.get().defaultBlockState().setValue(LEVEL, this.getLevel(blockState)), 2);
        level.playSound(null, blockPos, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 5, 1);
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 60; ++i) {
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, blockPos.getX() + 0.5 + serverLevel.random.nextFloat(), blockPos.getY() + serverLevel.random.nextFloat(), blockPos.getZ() + serverLevel.random.nextFloat(), 0, 0, 0, 0, 0);
            }
        }
    }

    public static void resurrects(Level level, BlockState blockState, BlockPos blockPos) {
        level.setBlock(blockPos, JerotesVillageBlocks.RESURRECTER_COFFIN.get().defaultBlockState().setValue(LEVEL, getLevels(blockState)), 2);
        level.playSound(null, blockPos, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 5, 1);
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 60; ++i) {
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, blockPos.getX() + 0.5 + serverLevel.random.nextFloat(), blockPos.getY() + serverLevel.random.nextFloat(), blockPos.getZ() + serverLevel.random.nextFloat(), 0, 0, 0, 0, 0);
            }
        }
    }
}