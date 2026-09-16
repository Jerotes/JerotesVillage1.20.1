package com.jerotes.jerotesvillage.world.density;

import com.jerotes.jerotesvillage.world.region.RegionBiomeMap;
import com.jerotes.jerotesvillage.world.region.RegionConfig;
import com.jerotes.jerotesvillage.world.region.ValueNoise2D;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;

public record RegionBiasDensityFunction() implements DensityFunction {

	private static final ValueNoise2D TERRAIN_NOISE = new ValueNoise2D(4004L);

	@Override
	public double compute(FunctionContext ctx) {
		int x = ctx.blockX();
		int z = ctx.blockZ();
		RegionBiomeMap.Result r = RegionBiomeMap.decide(x, z);

		double bias;
		if (r.isOcean()) {
			double undulation = TERRAIN_NOISE.fbm(x, z, 150, 3) * 0.08;
			bias = RegionConfig.OCEAN_DENSITY_BIAS + undulation;
			bias = Math.max(bias, RegionConfig.OCEAN_DENSITY_FLOOR);
		} else {
			double ownBias = biasForBiome(r.biomeId(), x, z);

			// 陆地群系间的边界混合：只有当邻格群系确实不一样时才需要插值
			if (r.neighborBiomeId() != null && !r.neighborBiomeId().equals(r.biomeId())) {
				double landDist = r.landSample().f2() - r.landSample().f1();
				double lt = Mth.clamp(landDist / RegionConfig.LAND_BORDER_BLEND, 0.0, 1.0);
				if (lt < 1.0) {
					double neighborBias = biasForBiome(r.neighborBiomeId(), x, z);
					double smooth = lt * lt * (3 - 2 * lt); // smoothstep，过渡更自然
					ownBias = Mth.lerp(smooth, neighborBias, ownBias);
				}
			}
			bias = ownBias;
		}
		double distToBorder = Math.abs(r.continentSample().f2() - r.continentSample().f1());
		double t = Mth.clamp(distToBorder / RegionConfig.COAST_WIDTH, 0.0, 1.0);
		double smoothT = Math.pow(t, RegionConfig.COAST_EASE_POWER); // ease-in，靠近海岸格外温和
		double blended = bias * smoothT;
		double taper = r.isOcean() ? oceanYTaper(ctx.blockY(), t) : yTaper(ctx.blockY());
		return blended * taper;
	}

	private static double biasForBiome(String biomeId, int x, int z) {
		if (biomeId.equals("jerotesvillage:harmonious_wonderland")) {
			double h = TERRAIN_NOISE.fbm(x, z, 220, 4);
			return h > 0
					? RegionConfig.WONDERLAND_MOUNTAIN_BIAS * h
					: RegionConfig.WONDERLAND_BASIN_BIAS * -h;
		} else if (biomeId.equals("jerotesvillage:drought_fire")) {
			double h = TERRAIN_NOISE.fbm(x, z, 300, 2) * 0.3 + 0.7;
			return RegionConfig.DROUGHT_FIRE_PLATEAU_BIAS * h;
		} else {
			double h = TERRAIN_NOISE.fbm(x, z, 250, 2) * 0.25 + 0.75;
			return RegionConfig.LAND_DENSITY_BIAS * h;
		}
	}

	private static double yTaper(int y) {
		if (y < RegionConfig.TAPER_HARD_FLOOR) {
			return 0.0;
		}
		int sea = RegionConfig.SEA_LEVEL;
		double half = RegionConfig.TAPER_CORE_HALF_WIDTH;
		if (y >= sea - half && y <= sea + half) {
			return 1.0;
		}
		if (y < sea - half) {
			double d = (sea - half) - y;
			return Mth.clamp(1.0 - d / RegionConfig.TAPER_FADE_DOWN, 0.0, 1.0);
		}
		double d = y - (sea + half);
		return Mth.clamp(1.0 - d / RegionConfig.TAPER_FADE_UP, 0.0, 1.0);
	}

	private static double oceanYTaper(int y, double horizontalT) {
		if (y < RegionConfig.TAPER_HARD_FLOOR) {
			return 0.0;
		}
		int sea = RegionConfig.SEA_LEVEL;
		int strongBottom = sea - RegionConfig.OCEAN_STRONG_BELOW_SEA;

		// 留一个最小底线（0.15），避免贴着海岸的地方完全不生效又冒出新的怪问题
		double reach = Math.max(horizontalT, 0.15);
		int coreTop = (int) (sea + RegionConfig.TAPER_CORE_HALF_WIDTH * reach);
		double fadeUp = Math.max(RegionConfig.TAPER_FADE_UP * reach, 5.0);

		if (y >= strongBottom && y <= coreTop) {
			return 1.0;
		}
		if (y < strongBottom) {
			double d = strongBottom - y;
			return Mth.clamp(1.0 - d / RegionConfig.OCEAN_FADE_DOWN, 0.0, 1.0);
		}
		double d = y - coreTop;
		return Mth.clamp(1.0 - d / fadeUp, 0.0, 1.0);
	}

	@Override public double minValue() { return -1.2; }
	@Override public double maxValue() { return 1.2; }
	@Override public DensityFunction mapAll(Visitor visitor) { return visitor.apply(this); }

	@Override
	public void fillArray(double[] array, ContextProvider contextProvider) {
		contextProvider.fillAllDirectly(array, this);
	}

	@Override
	public KeyDispatchDataCodec<? extends DensityFunction> codec() {
		return JerotesDensityFunctions.REGION_BIAS_KEY_CODEC.get();
	}
}