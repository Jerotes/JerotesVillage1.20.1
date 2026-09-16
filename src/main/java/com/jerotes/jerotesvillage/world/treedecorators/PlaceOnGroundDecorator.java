package com.jerotes.jerotesvillage.world.treedecorators;

import com.jerotes.jerotesvillage.init.JerotesVillageTreeDecorator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PlaceOnGroundDecorator extends TreeDecorator {
    public static final Codec<PlaceOnGroundDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("tries").orElse(128).forGetter(placeOnGroundDecorator -> placeOnGroundDecorator.tries),
            Codec.INT.fieldOf("radius").orElse(2).forGetter(placeOnGroundDecorator -> placeOnGroundDecorator.radius),
            Codec.INT.fieldOf("height").orElse(1).forGetter(placeOnGroundDecorator -> placeOnGroundDecorator.height),
            BlockStateProvider.CODEC.fieldOf("block_state_provider").forGetter(placeOnGroundDecorator -> placeOnGroundDecorator.blockStateProvider)
    ).apply(instance, PlaceOnGroundDecorator::new));

    private final int tries;
    private final int radius;
    private final int height;
    private final BlockStateProvider blockStateProvider;

    public PlaceOnGroundDecorator(int tries, int radius, int height, BlockStateProvider blockStateProvider) {
        this.tries = tries;
        this.radius = radius;
        this.height = height;
        this.blockStateProvider = blockStateProvider;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return JerotesVillageTreeDecorator.PLACE_ON_GROUND.get();
    }

    @Override
    public void place(Context context) {
        List<BlockPos> logs = context.logs();
        if (logs.isEmpty()) {
            return;
        }

        // 找到最低的树干位置
        int minY = Integer.MAX_VALUE;
        for (BlockPos pos : logs) {
            minY = Math.min(minY, pos.getY());
        }

        List<BlockPos> lowestLogs = new ArrayList<>();
        for (BlockPos pos : logs) {
            if (pos.getY() == minY) {
                lowestLogs.add(pos);
            }
        }

        if (lowestLogs.isEmpty()) {
            return;
        }

        BlockPos firstPos = lowestLogs.get(0);
        int minX = firstPos.getX();
        int maxX = firstPos.getX();
        int minZ = firstPos.getZ();
        int maxZ = firstPos.getZ();

        for (BlockPos pos : lowestLogs) {
            minX = Math.min(minX, pos.getX());
            maxX = Math.max(maxX, pos.getX());
            minZ = Math.min(minZ, pos.getZ());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        RandomSource random = context.random();
        BoundingBox boundingBox = new BoundingBox(
                minX - radius, minY, minZ - radius,
                maxX + radius, minY + height, maxZ + radius
        );

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < this.tries; ++i) {
            mutablePos.set(
                    random.nextIntBetweenInclusive(boundingBox.minX(), boundingBox.maxX()),
                    random.nextIntBetweenInclusive(boundingBox.minY(), boundingBox.maxY()),
                    random.nextIntBetweenInclusive(boundingBox.minZ(), boundingBox.maxZ())
            );
            attemptToPlaceBlockAbove(this, context, mutablePos);
        }
    }


    private static void attemptToPlaceBlockAbove(PlaceOnGroundDecorator placeOnGroundDecorator, TreeDecorator.Context context, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        if (context.level().isStateAtPosition(blockPos2, blockState -> blockState.isAir() || blockState.is(Blocks.VINE)) && context.level() instanceof BlockGetter blockGetter &&
                checkBlock(context, blockPos, blockState -> blockState.isSolidRender(blockGetter, blockPos)) &&
                context.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY() <= blockPos2.getY()) {
            context.setBlock(blockPos2, placeOnGroundDecorator.blockStateProvider.getState(context.random(), blockPos2));
        }
    }
    public static boolean checkBlock(TreeDecorator.Context context, BlockPos blockPos, Predicate<BlockState> predicate) {
        return context.level().isStateAtPosition(blockPos, predicate);
    }

}

