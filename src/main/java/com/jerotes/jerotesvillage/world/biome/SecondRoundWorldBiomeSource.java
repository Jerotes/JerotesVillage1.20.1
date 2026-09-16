package com.jerotes.jerotesvillage.world.biome;

import com.jerotes.jerotesvillage.world.region.CaveBiomeMap;
import com.jerotes.jerotesvillage.world.region.RegionBiomeMap;
import com.jerotes.jerotesvillage.world.region.RegionConfig;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecondRoundWorldBiomeSource extends BiomeSource {
    private static final MapCodec<Holder<Biome>> ENTRY_CODEC = Biome.CODEC.fieldOf("biome");
    public static final MapCodec<Climate.ParameterList<Holder<Biome>>> DIRECT_CODEC =
            Climate.ParameterList.codec(ENTRY_CODEC).fieldOf("biomes");

    private static final MapCodec<Holder<MultiNoiseBiomeSourceParameterList>> PRESET_CODEC =
            MultiNoiseBiomeSourceParameterList.CODEC.fieldOf("preset");

    public static final Codec<SecondRoundWorldBiomeSource> CODEC =
            Codec.mapEither(DIRECT_CODEC, PRESET_CODEC)
                    .xmap(SecondRoundWorldBiomeSource::new, source -> source.parameters)
                    .codec();

    private final Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters;

    // 缓存缩放后的参数列表以提高性能
    private final Lazy<Climate.ParameterList<Holder<Biome>>> scaledParameterList;

    // 用于缓存的参数
    private int lastX = Integer.MIN_VALUE;
    private int lastZ = Integer.MIN_VALUE;
    @Nullable
    private Climate.TargetPoint cachedTargetPoint;

    public SecondRoundWorldBiomeSource(Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters) {
        super();
        this.parameters = parameters;
        this.scaledParameterList = Lazy.of(() -> {
            Climate.ParameterList<Holder<Biome>> original = parameters();
            return createScaledParameterList(original, 0.85f);
        });
    }

    private static Stream<Holder<Biome>> getBiomesFromParameters(Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters) {
        return parameters.map(
                paramList -> paramList.values().stream().map(Pair::getSecond),
                preset -> preset.value().parameters().values().stream().map(Pair::getSecond)
        );
    }

    public static SecondRoundWorldBiomeSource createFromList(Climate.ParameterList<Holder<Biome>> parameterList) {
        return new SecondRoundWorldBiomeSource(Either.left(parameterList));
    }

    public static SecondRoundWorldBiomeSource createFromPreset(Holder<MultiNoiseBiomeSourceParameterList> preset) {
        return new SecondRoundWorldBiomeSource(Either.right(preset));
    }

    private Climate.ParameterList<Holder<Biome>> parameters() {
        return this.parameters.map(
                Function.identity(),
                preset -> preset.value().parameters()
        );
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.parameters().values().stream().map(Pair::getSecond);
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    public boolean stable(ResourceKey<MultiNoiseBiomeSourceParameterList> key) {
        Optional<Holder<MultiNoiseBiomeSourceParameterList>> optional = this.parameters.right();
        return optional.isPresent() && optional.get().is(key);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        int blockX = QuartPos.toBlock(x);
        int blockY = QuartPos.toBlock(y);
        int blockZ = QuartPos.toBlock(z);

        if (blockY < RegionConfig.CAVE_Y_THRESHOLD) {
            String caveBiome = CaveBiomeMap.decide(blockX, blockZ);
            return lookupBiome(caveBiome);
        }

        RegionBiomeMap.Result r = RegionBiomeMap.decide(blockX, blockZ);
        return lookupBiome(r.biomeId());
    }

    // 类字段：懒加载的 id -> Holder<Biome> 查找表
    private final Lazy<Map<ResourceLocation, Holder<Biome>>> biomeLookup = Lazy.of(() ->
            this.parameters().values().stream()
                    .map(Pair::getSecond)
                    .collect(Collectors.toMap(
                            h -> h.unwrapKey().orElseThrow(() ->
                                    new IllegalStateException("群系没有注册 ResourceKey: " + h)).location(),
                            h -> h,
                            (a, b) -> a // 理论上不会重复，重复时随便留一个即可
                    ))
    );

    private Holder<Biome> lookupBiome(String id) {
        ResourceLocation rl = new ResourceLocation(id); // 1.20.1 用构造函数，不是 .parse()
        Holder<Biome> h = biomeLookup.get().get(rl);
        if (h == null) {
            throw new IllegalStateException("未知群系: " + id
                    + "（检查该群系是否在 datapack 里被列进了 biomes 参数表）");
        }
        return h;
    }

    private Climate.TargetPoint scaleTargetPointMildly(Climate.TargetPoint original, float scaleFactor) {
        if (scaleFactor >= 1.0f) {
            return original;
        }

        float temperature = Climate.unquantizeCoord(original.temperature());
        float humidity = Climate.unquantizeCoord(original.humidity());
        float continentalness = Climate.unquantizeCoord(original.continentalness());
        float erosion = Climate.unquantizeCoord(original.erosion());
        float depth = Climate.unquantizeCoord(original.depth());
        float weirdness = Climate.unquantizeCoord(original.weirdness());

        // 使用更温和的缩放：不是直接乘以因子，而是向中心收缩一部分
        // 这样能保持一些多样性
        temperature = mildScale(temperature, scaleFactor);
        humidity = mildScale(humidity, scaleFactor);
        continentalness = mildScale(continentalness, scaleFactor);
        erosion = mildScale(erosion, scaleFactor);
        weirdness = mildScale(weirdness, scaleFactor);

        return Climate.target(temperature, humidity, continentalness, erosion, depth, weirdness);
    }
    private float mildScale(float value, float scale) {
        float scaled = value * scale;
        return Mth.clamp(scaled, -1.0f, 1.0f);
    }
    private Climate.ParameterList<Holder<Biome>> createScaledParameterList(
            Climate.ParameterList<Holder<Biome>> original, float scaleFactor) {

        List<Pair<Climate.ParameterPoint, Holder<Biome>>> scaledValues =
                original.values().stream()
                        .map(pair -> Pair.of(scaleParameterPoint(pair.getFirst(), scaleFactor), pair.getSecond()))
                        .toList();

        return new Climate.ParameterList<>(scaledValues);
    }

    private Climate.ParameterPoint scaleParameterPoint(Climate.ParameterPoint original, float scaleFactor) {
        // 扩大参数范围而不是缩小
        Climate.Parameter temperature = expandParameter(original.temperature(), scaleFactor);
        Climate.Parameter humidity = expandParameter(original.humidity(), scaleFactor);
        Climate.Parameter continentalness = expandParameter(original.continentalness(), scaleFactor);
        Climate.Parameter erosion = expandParameter(original.erosion(), scaleFactor);
        Climate.Parameter depth = original.depth();
        Climate.Parameter weirdness = expandParameter(original.weirdness(), scaleFactor);

        return new Climate.ParameterPoint(
                temperature, humidity, continentalness, erosion, depth, weirdness, original.offset()
        );
    }

    private Climate.Parameter expandParameter(Climate.Parameter param, float expandFactor) {
        if (expandFactor <= 1.0f) {
            return param;
        }

        long center = (param.min() + param.max()) / 2;
        long range = (long)((param.max() - param.min()) * expandFactor);
        long newMin = Math.max(Climate.quantizeCoord(-1.0f), center - range / 2);
        long newMax = Math.min(Climate.quantizeCoord(1.0f), center + range / 2);

        return new Climate.Parameter(newMin, newMax);
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos, Climate.Sampler sampler) {
        int x = QuartPos.fromBlock(pos.getX());
        int y = QuartPos.fromBlock(pos.getY());
        int z = QuartPos.fromBlock(pos.getZ());

        Climate.TargetPoint targetPoint = sampler.sample(x, y, z);
        Climate.TargetPoint scaledTarget = scaleTargetPointMildly(targetPoint, 0.85f);

        float continentalness = Climate.unquantizeCoord(scaledTarget.continentalness());
        float erosion = Climate.unquantizeCoord(scaledTarget.erosion());
        float temperature = Climate.unquantizeCoord(scaledTarget.temperature());
        float humidity = Climate.unquantizeCoord(scaledTarget.humidity());
        float weirdness = Climate.unquantizeCoord(scaledTarget.weirdness());

        double peaksValleys = (double) NoiseRouterData.peaksAndValleys(weirdness);
        OverworldBiomeBuilder overworldBiomeBuilder = new OverworldBiomeBuilder();

        info.add("Second Round World Biome Source (Mild Scaling)");
        info.add(String.format("Temperature: %.3f", temperature));
        info.add(String.format("Humidity: %.3f", humidity));
        info.add(String.format("Continentalness: %.3f", continentalness));
        info.add(String.format("Erosion: %.3f", erosion));
        info.add(String.format("Weirdness: %.3f", weirdness));
        info.add("Scaling applied: 0.85 (mild)");
    }
}