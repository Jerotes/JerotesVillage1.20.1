package com.jerotes.jerotesvillage.world.density;

import com.jerotes.jerotesvillage.world.region.RegionBiomeMap;
import com.jerotes.jerotesvillage.world.region.RegionConfig;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record OceanFluidBiasDensityFunction() implements DensityFunction {

	@Override
	public double compute(FunctionContext ctx) {
		RegionBiomeMap.Result r = RegionBiomeMap.decide(ctx.blockX(), ctx.blockZ());
		return r.isOcean() ? RegionConfig.OCEAN_FLOOD_BIAS : 0.0;
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