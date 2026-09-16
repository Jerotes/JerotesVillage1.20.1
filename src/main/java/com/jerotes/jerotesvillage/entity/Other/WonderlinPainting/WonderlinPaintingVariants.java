package com.jerotes.jerotesvillage.entity.Other.WonderlinPainting;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WonderlinPaintingVariants {
    public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, JerotesVillage.MODID);

    public static final RegistryObject<PaintingVariant> WONDERLIN = REGISTRY.register("wonderlin", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> BOSS = REGISTRY.register("boss", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> FLOWER = REGISTRY.register("flower", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> SUN = REGISTRY.register("sun", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> GOD = REGISTRY.register("god", () -> new PaintingVariant(32, 32));
}

