package com.jerotes.jerotesvillage.block.DroughtFire;

import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.world.teleporter.PiglinSecondRoundWorldPortalShape;
import com.jerotes.jerotesvillage.world.teleporter.PiglinSecondRoundWorldTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
import java.util.Optional;

public class PiglinSecondRoundWorldPortalBlock extends NetherPortalBlock {

    public PiglinSecondRoundWorldPortalBlock() {
        super(Properties.of().noCollission().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 15).noLootTable());
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        super.randomTick(blockState, level, pos, randomSource);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
    }

    public static boolean portalSpawn(Level world, BlockPos pos) {
        Optional<PiglinSecondRoundWorldPortalShape> optional = PiglinSecondRoundWorldPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
        if (optional.isPresent()) {
            optional.get().createPortalBlocks();
            return true;
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis portalAxis = blockState.getValue(AXIS);
        boolean isHorizontalAndDifferent = portalAxis != axis && axis.isHorizontal();
        if (!isHorizontalAndDifferent && !neighborState.is(this) && !(new PiglinSecondRoundWorldPortalShape(levelAccessor, pos, portalAxis)).isComplete()) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, neighborState, levelAccessor, pos, neighborPos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            world.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }
        for(int i = 0; i < 4; ++i) {
            double d0 = (double)pos.getX() + random.nextDouble();
            double d1 = (double)pos.getY() + random.nextDouble();
            double d2 = (double)pos.getZ() + random.nextDouble();
            double d3 = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)random.nextFloat() - 0.5D) * 0.5D;
            int j = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).is(this) && !world.getBlockState(pos.east()).is(this)) {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(random.nextFloat() * 2.0F * (float)j);
            } else {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(random.nextFloat() * 2.0F * (float)j);
            }
            world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.canChangeDimensions() && !entity.level().isClientSide()) {
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            } else if (entity.level().dimension() == Level.NETHER) {
                entity.setPortalCooldown();
                teleportToDimension(entity, pos, JerotesVillageDimensions.SECOND_ROUND_WORLD);
            } else if (entity.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
                entity.setPortalCooldown();
                teleportToDimension(entity, pos, Level.NETHER);
            }
        }
    }

    private void teleportToDimension(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
        entity.changeDimension(Objects.requireNonNull(Objects.requireNonNull(entity.getServer()).getLevel(destinationType)), new PiglinSecondRoundWorldTeleporter(entity.getServer().getLevel(destinationType), pos));
    }
}