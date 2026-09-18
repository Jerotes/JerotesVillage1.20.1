package com.jerotes.jerotesvillage.world.features;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DroughtFireLavaFractureFeature extends Feature<NoneFeatureConfiguration> {
	public static final DeferredRegister<Feature<?>> REGISTRY =
			DeferredRegister.create(ForgeRegistries.FEATURES, JerotesVillage.MODID);
	public static final RegistryObject<Feature<?>> DROUGHT_FIRE_LAVA_FRACTURE =
			REGISTRY.register("drought_fire_lava_fracture",
					() -> new DroughtFireLavaFractureFeature(NoneFeatureConfiguration.CODEC));

	public DroughtFireLavaFractureFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ());
		BlockPos surface = new BlockPos(origin.getX(), surfaceY, origin.getZ());
		if (!level.getBlockState(surface).isAir()) {
			return false;
		}
		BlockState ground = level.getBlockState(surface.below());
		if (ground.isAir() || !ground.getFluidState().isEmpty()
				|| !ground.isFaceSturdy(level, surface.below(), Direction.UP)) {
			return false;
		}

		int length = 3 + random.nextInt(4);
		boolean alongX = random.nextBoolean();
		int step = random.nextBoolean() ? 1 : -1;

		boolean placed = false;
		for (int i = 0; i < length; ++i) {
			int x = surface.getX() + (alongX ? i * step : 0);
			int z = surface.getZ() + (alongX ? 0 : i * step);
			int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
			if (Math.abs(y - surfaceY) > 1) {
				break;
			}
			BlockPos crackPos = new BlockPos(x, y, z);
			BlockPos crackGroundPos = crackPos.below();
			BlockState crackGround = level.getBlockState(crackGroundPos);
			if (crackGround.isAir() || !crackGround.getFluidState().isEmpty()) {
				break;
			}
			this.setBlock(level, crackGroundPos, Blocks.LAVA.defaultBlockState());
			if (random.nextFloat() < 0.6F) {
				BlockPos sideA = alongX ? crackGroundPos.offset(0, 0, 1) : crackGroundPos.offset(1, 0, 0);
				if (!level.getBlockState(sideA).isAir()) {
					this.setBlock(level, sideA, Blocks.MAGMA_BLOCK.defaultBlockState());
				}
			}
			if (random.nextFloat() < 0.6F) {
				BlockPos sideB = alongX ? crackGroundPos.offset(0, 0, -1) : crackGroundPos.offset(-1, 0, 0);
				if (!level.getBlockState(sideB).isAir()) {
					this.setBlock(level, sideB, Blocks.MAGMA_BLOCK.defaultBlockState());
				}
			}
			placed = true;
		}
		return placed;
	}
}
