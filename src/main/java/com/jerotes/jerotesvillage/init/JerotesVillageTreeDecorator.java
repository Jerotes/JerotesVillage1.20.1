package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.world.treedecorators.PlaceOnGroundDecorator;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageTreeDecorator {
    public static final DeferredRegister<TreeDecoratorType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, JerotesVillage.MODID);

    public static final RegistryObject<TreeDecoratorType<?>> PLACE_ON_GROUND = REGISTRY.register("place_on_ground",
            () -> new TreeDecoratorType<>(PlaceOnGroundDecorator.CODEC));

    private static <T extends TreeDecorator> TreeDecoratorType<T> register(String name, Codec<T> codec) {
        return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE,
                new ResourceLocation(JerotesVillage.MODID, name), new TreeDecoratorType<>(codec));
    }
    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
