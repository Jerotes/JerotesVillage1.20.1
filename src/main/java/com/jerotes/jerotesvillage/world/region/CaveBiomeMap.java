package com.jerotes.jerotesvillage.world.region;

public final class CaveBiomeMap {

	private static final VoronoiRegion CAVE =
			new VoronoiRegion(RegionConfig.CAVE_CELL_SIZE, RegionConfig.CAVE_JITTER, 3003L);

	private static final String[] CAVE_BIOMES = {
			"jerotesvillage:unknown_deep",
			"jerotesvillage:virtual_cave"
	};

	public static String decide(int blockX, int blockZ) {
		VoronoiRegion.Sample s = CAVE.sample(blockX, blockZ);
		long seed = CAVE.cellSeed(s.cx(), s.cz(), 909L);
		return CAVE_BIOMES[(int) (VoronoiRegion.unitFloat(seed) * CAVE_BIOMES.length) % CAVE_BIOMES.length];
	}

	private CaveBiomeMap() {}
}