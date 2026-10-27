package com.jerotes.jerotesvillage.world.density;

import com.jerotes.jerotesvillage.world.region.RegionBiomeMap;
import com.jerotes.jerotesvillage.world.region.RegionConfig;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;

public record OceanFluidBiasDensityFunction() implements DensityFunction {

	@Override
	public double compute(FunctionContext ctx) {
		RegionBiomeMap.Result r = RegionBiomeMap.decide(ctx.blockX(), ctx.blockZ());
		if (!r.isOcean()) {
			return 0.0;
		}
		// 原来这里是 0 / +2.0 的硬切，切在大陆 Voronoi 单元死边上；而地形那边的海岸淡出
		// 有 COAST_WIDTH=220 的过渡带。两条边界不重合 -> 水线横切地形，出现被切断的岸线。
		// 用与 RegionBiasDensityFunction 完全相同的海岸淡出，让注水边界跟着地形走。
		double distToBorder = Math.abs(r.continentSample().f2() - r.continentSample().f1());
		double t = Mth.clamp(distToBorder / RegionConfig.COAST_WIDTH, 0.0, 1.0);
		double smoothT = Math.pow(t, RegionConfig.COAST_EASE_POWER);
		return RegionConfig.OCEAN_FLOOD_BIAS * smoothT;
	}

	@Override public double minValue() { return 0.0; }
	@Override public double maxValue() { return RegionConfig.OCEAN_FLOOD_BIAS; }
	@Override public DensityFunction mapAll(Visitor visitor) { return visitor.apply(this); }

	@Override
	public void fillArray(double[] array, ContextProvider contextProvider) {
		contextProvider.fillAllDirectly(array, this);
	}

	@Override
	public KeyDispatchDataCodec<? extends DensityFunction> codec() {
		return JerotesDensityFunctions.OCEAN_FLUID_BIAS_KEY_CODEC.get();
	}
}
