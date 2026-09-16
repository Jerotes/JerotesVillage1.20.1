package com.jerotes.jerotesvillage.world.density;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraftforge.common.util.Lazy;
import net.minecraft.world.level.levelgen.DensityFunction;

public final class JerotesDensityFunctions {

	// 注意：这里存的是 Codec，不是 MapCodec
	public static final DeferredRegister<Codec<? extends DensityFunction>> REGISTRY =
			DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, "jerotesvillage");

	public static final RegistryObject<Codec<RegionBiasDensityFunction>> REGION_BIAS =
			REGISTRY.register("region_bias", () -> Codec.unit(RegionBiasDensityFunction::new));

	// codec() 每次调用都要返回同一个 KeyDispatchDataCodec 实例，所以缓存一份
	public static final Lazy<KeyDispatchDataCodec<RegionBiasDensityFunction>> REGION_BIAS_KEY_CODEC =
			Lazy.of(() -> KeyDispatchDataCodec.of(REGION_BIAS.get()));

	// 新增：海洋含水层偏置，注入到 fluid_level_floodedness 里
	public static final RegistryObject<Codec<OceanFluidBiasDensityFunction>> OCEAN_FLUID_BIAS =
			REGISTRY.register("ocean_fluid_bias", () -> Codec.unit(OceanFluidBiasDensityFunction::new));

	public static final Lazy<KeyDispatchDataCodec<OceanFluidBiasDensityFunction>> OCEAN_FLUID_BIAS_KEY_CODEC =
			Lazy.of(() -> KeyDispatchDataCodec.of(OCEAN_FLUID_BIAS.get()));
}