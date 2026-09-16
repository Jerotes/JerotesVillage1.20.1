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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class SedimentLordEmptyCoffin extends Block {

    public SedimentLordEmptyCoffin() {
        super(Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.METAL).strength(6f, 9f).requiresCorrectToolForDrops());
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        entity.getItemInHand(hand);
        if (!(entity.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING)) {
            return super.use(blockstate, world, pos, entity, hand, hit);
        }
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (entity.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING) {
            ItemStack hands = entity.getItemInHand(hand);
            if (!entity.getAbilities().instabuild) {
                hands.shrink(1);
            }
            resurrect(world, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public static void resurrect(Level level, BlockPos blockPos) {
        if (level.random.nextFloat() > 0.8f) {
            level.setBlock(blockPos, JerotesVillageBlocks.SEDIMENT_LORD_COFFIN.get().defaultBlockState(), 2);
            if (!level.isClientSide()) {
                level.playSound(null, blockPos, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 5, 1);
            }
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 60; ++i) {
                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, blockPos.getX() + serverLevel.random.nextFloat(), blockPos.getY() + 0.5 + serverLevel.random.nextFloat(), blockPos.getZ() + serverLevel.random.nextFloat(), 0, 0, 0, 0, 0);
                }
            }
        }
        else {
            if (!level.isClientSide()) {
                level.playSound(null, blockPos, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 5, 1);
            }
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 60; ++i) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, blockPos.getX() + serverLevel.random.nextFloat(), blockPos.getY() + 0.5 + serverLevel.random.nextFloat(), blockPos.getZ() + serverLevel.random.nextFloat(), 0, 0, 0, 0, 0);
                }
            }
        }
    }
}