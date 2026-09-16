package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.world.teleporter.SecondRoundWorldPortalShape;
import com.jerotes.jerotesvillage.world.teleporter.SecondRoundWorldTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
import java.util.Optional;

public class SecondRoundWorldPortalBlock extends NetherPortalBlock {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    public SecondRoundWorldPortalBlock() {
        super(Properties.of()
                .noCollission()
                .randomTicks()
                .pushReaction(PushReaction.BLOCK)
                .strength(-1.0F)
                .sound(SoundType.GLASS)
                .lightLevel(s -> 15)
                .noLootTable());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HATCH);
    }

    public int getPortalType(BlockState blockState) {
        return blockState.getValue(HATCH);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState,
                                  LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis portalAxis = blockState.getValue(AXIS);
        boolean isHorizontalAndDifferent = portalAxis != axis && axis.isHorizontal();

        if (!isHorizontalAndDifferent && !neighborState.is(this) &&
                !(new SecondRoundWorldPortalShape(levelAccessor, pos, portalAxis)).isComplete()) {
            return Blocks.AIR.defaultBlockState();
        }

        int newHatch = computeHatch(levelAccessor, pos);
        if (blockState.getValue(HATCH) != newHatch) {
            blockState = blockState.setValue(HATCH, newHatch);
        }
        return super.updateShape(blockState, direction, neighborState, levelAccessor, pos, neighborPos);
    }

    private int computeHatch(LevelAccessor level, BlockPos pos) {
        boolean hasFrame = level.getBlockState(pos.above()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get()
                || level.getBlockState(pos.below()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get()
                || level.getBlockState(pos.east()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get()
                || level.getBlockState(pos.west()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get()
                || level.getBlockState(pos.north()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get()
                || level.getBlockState(pos.south()).getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get();
        return hasFrame ? 1 : 0;
    }

    public static boolean portalSpawn(Level world, BlockPos pos) {
        Optional<SecondRoundWorldPortalShape> optional = SecondRoundWorldPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
        if (optional.isPresent()) {
            optional.get().createPortalBlocks();
            return true;
        }
        return false;
    }
    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(110) == 1) {
            world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f);
        }
        double d0 = (double) pos.getX() + random.nextDouble();
        double d1 = (double) pos.getY() + random.nextDouble();
        double d2 = (double) pos.getZ() + random.nextDouble();
        double d3 = ((double) random.nextFloat() - 0.5) * 0.1;
        double d4 = ((double) random.nextFloat() - 0.5) * 0.1;
        double d5 = ((double) random.nextFloat() - 0.5) * 0.1;
        int i = random.nextInt(2) * 2 - 1;
        if (!world.getBlockState(pos.west()).is(this) && !world.getBlockState(pos.east()).is(this)) {
            d0 = (double) pos.getX() + 0.5 + 0.25 * (double) i;
            d3 = (double) (random.nextFloat() * 0.002f * (float) i);
        } else {
            d2 = (double) pos.getZ() + 0.5 + 0.25 * (double) i;
            d5 = (double) (random.nextFloat() * 0.002f * (float) i);
        }
        world.addParticle(JerotesVillageParticleTypes.SECOND_ROUND_WORLD_PORTAL_PARTICLE.get(), d0, d1, d2, d3, d4, d5);
    }

    // 实体传送逻辑完全保留
    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.canChangeDimensions() && !entity.level().isClientSide()) {
            if (entity instanceof Player player &&
                    Main.getJerotesPersistentData(player).getDouble("jerotesvillage_second_rounder_portal") < 15) {
                return;
            }
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            } else if (entity.level().dimension() == Level.OVERWORLD) {
                entity.setPortalCooldown();
                teleportToDimension0(entity, pos, JerotesVillageDimensions.SECOND_ROUND_WORLD);
            } else if (entity.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
                entity.setPortalCooldown();
                teleportToDimension1(entity, pos, Level.OVERWORLD);
            }
        }
    }

    private void teleportToDimension0(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
        entity.changeDimension(Objects.requireNonNull(Objects.requireNonNull(entity.getServer()).getLevel(destinationType)),
                new SecondRoundWorldTeleporter(entity.getServer().getLevel(destinationType), pos));
    }

    private void teleportToDimension1(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
        entity.changeDimension(Objects.requireNonNull(Objects.requireNonNull(entity.getServer()).getLevel(destinationType)),
                new SecondRoundWorldTeleporter(entity.getServer().getLevel(destinationType), pos));
    }
}