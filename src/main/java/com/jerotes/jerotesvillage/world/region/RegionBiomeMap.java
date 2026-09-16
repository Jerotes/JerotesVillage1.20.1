package com.jerotes.jerotesvillage.world.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RegionBiomeMap {

	// 【关键修改 1】改成 public static final，让密度函数能直接访问大陆网格对象
	public static final VoronoiRegion CONTINENT =
			new VoronoiRegion(RegionConfig.CONTINENT_CELL_SIZE, RegionConfig.CONTINENT_JITTER, 1001L);
	private static final VoronoiRegion LAND =
			new VoronoiRegion(RegionConfig.LAND_CELL_SIZE, RegionConfig.LAND_JITTER, 2002L);
	private static final VoronoiRegion CLUSTER =
			new VoronoiRegion(RegionConfig.CLUSTER_CELL_SIZE, RegionConfig.CLUSTER_JITTER, 3003L);
	private static final VoronoiRegion PATCH =
			new VoronoiRegion(RegionConfig.PATCH_CELL_SIZE, RegionConfig.PATCH_JITTER, 5005L);

	public record WeightedBiome(String id, int weight) {}

	private static final WeightedBiome[] LAND_BIOMES = {
			new WeightedBiome("jerotesvillage:bitter_cold", 10),
			new WeightedBiome("jerotesvillage:purple_desert", 8),
			new WeightedBiome("jerotesvillage:bright_grassland", 15),
			new WeightedBiome("jerotesvillage:drought_fire", 6),
			new WeightedBiome("jerotesvillage:harmonious_wonderland", 10),
			new WeightedBiome("jerotesvillage:damaged_ruins", 6),
			new WeightedBiome("jerotesvillage:wilderness_woodland", 15),
			new WeightedBiome("jerotesvillage:sand_beach", 8),
	};

	private static final Set<String> FORBIDDEN_NEAR_OCEAN = Set.of(
			"jerotesvillage:drought_fire",
			"jerotesvillage:purple_desert"
	);

	private static final String SAND_BEACH = "jerotesvillage:sand_beach";

	public record Result(
			String biomeId,
			VoronoiRegion.Sample continentSample,
			boolean isOcean,
			VoronoiRegion.Sample landSample,
			String neighborBiomeId
	) {}

	private static final ThreadLocal<long[]> LAST_KEY = ThreadLocal.withInitial(() -> new long[]{Long.MIN_VALUE});
	private static final ThreadLocal<Result> LAST_RESULT = new ThreadLocal<>();

	public static Result decide(int blockX, int blockZ) {
		long key = (((long) blockX) << 32) ^ (blockZ & 0xFFFFFFFFL);
		if (LAST_KEY.get()[0] == key && LAST_RESULT.get() != null) {
			return LAST_RESULT.get();
		}
		Result r = compute(blockX, blockZ);
		LAST_KEY.get()[0] = key;
		LAST_RESULT.set(r);
		return r;
	}

	private static Result compute(double x, double z) {
		VoronoiRegion.Sample c = CONTINENT.sample(x, z);
		boolean selfOcean = isOceanCell(c.cx(), c.cz());

		if (selfOcean) {
			return new Result("jerotesvillage:endless_ocean", c, true, null, null);
		}

		VoronoiRegion.Sample l = LAND.sample(x, z);
		String biome = biomeForLandCell(l.cx(), l.cz());
		String neighborBiome = biomeForLandCell(l.cx2(), l.cz2());

		return new Result(biome, c, false, l, neighborBiome);
	}

	private static String biomeForLandCell(int cx, int cz) {
		double[] seedPoint = LAND.seedPointOf(cx, cz);
		VoronoiRegion.Sample cAtCell = CONTINENT.sample(seedPoint[0], seedPoint[1]);
		boolean cellNearOcean = isOceanCell(cAtCell.cx2(), cAtCell.cz2())
				&& (cAtCell.f2() - cAtCell.f1()) < RegionConfig.COAST_WIDTH * 2.5;

		VoronoiRegion.Sample clusterSample = CLUSTER.sample(seedPoint[0], seedPoint[1]);
		long clusterSeed = CLUSTER.cellSeed(clusterSample.cx(), clusterSample.cz(), 8888L);
		String dominant = pickLandBiome(clusterSeed, false);

		VoronoiRegion.Sample patchSample = PATCH.sample(seedPoint[0], seedPoint[1]);
		double roll = VoronoiRegion.unitFloat(PATCH.cellSeed(patchSample.cx(), patchSample.cz(), 6543L));
		long escapeSeed = PATCH.cellSeed(patchSample.cx(), patchSample.cz(), 7654L);

		boolean dominantAllowedHere = !(cellNearOcean && FORBIDDEN_NEAR_OCEAN.contains(dominant));

		if (dominantAllowedHere && roll < RegionConfig.CLUSTER_DOMINANCE) {
			return dominant;
		}
		return pickLandBiome(escapeSeed, cellNearOcean);
	}

	// 【关键修改 2】公开给外部使用
	public static boolean isOceanCell(int cx, int cz) {
		long h = CONTINENT.cellSeed(cx, cz, 555L);
		return VoronoiRegion.unitFloat(h) < RegionConfig.OCEAN_BIAS;
	}

	private static String pickLandBiome(long seed, boolean nearOcean) {
		List<WeightedBiome> pool = new ArrayList<>();
		for (WeightedBiome w : LAND_BIOMES) {
			if (nearOcean && FORBIDDEN_NEAR_OCEAN.contains(w.id())) {
				continue;
			}
			int weight = w.weight();
			if (nearOcean && w.id().equals(SAND_BEACH)) {
				weight *= 4;
			}
			pool.add(new WeightedBiome(w.id(), weight));
		}
		return pickWeighted(pool, seed);
	}

	private static String pickWeighted(List<WeightedBiome> list, long seed) {
		int total = 0;
		for (WeightedBiome w : list) total += w.weight();
		double r = VoronoiRegion.unitFloat(seed) * total;
		double acc = 0;
		for (WeightedBiome w : list) {
			acc += w.weight();
			if (r <= acc) return w.id();
		}
		return list.get(list.size() - 1).id();
	}

	private RegionBiomeMap() {}
}