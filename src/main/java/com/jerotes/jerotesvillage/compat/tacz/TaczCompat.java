package com.jerotes.jerotesvillage.compat.tacz;

import com.google.common.base.Suppliers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import java.util.*;
import java.util.function.Supplier;

public class TaczCompat {
    private static final Supplier<Boolean> TACZ_LOADED = Suppliers.memoize(() ->
            ModList.get().isLoaded("tacz")
    );

    private static final Supplier<Optional<Class<?>>> ENTITY_KINETIC_BULLET = Suppliers.memoize(() -> {
        if (!TACZ_LOADED.get()) return Optional.empty();
        try {
            return Optional.of(Class.forName("com.tac.entity.EntityKineticBullet"));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    });

    private static final Supplier<Optional<Class<?>>> MODERN_KINETIC_GUN_ITEM = Suppliers.memoize(() -> {
        if (!TACZ_LOADED.get()) return Optional.empty();
        try {
            return Optional.of(Class.forName("com.tac.item.ModernKineticGunItem"));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    });

    public static boolean isLoaded() {
        return TACZ_LOADED.get();
    }

    public static Optional<Object> castToEntityKineticBullet(Entity entity) {
        return ENTITY_KINETIC_BULLET.get()
                .filter(clazz -> clazz.isInstance(entity))
                .map(clazz -> clazz.cast(entity));
    }

    public static Optional<Object> castToModernKineticGunItem(Item item) {
        return MODERN_KINETIC_GUN_ITEM.get()
                .filter(clazz -> clazz.isInstance(item))
                .map(clazz -> clazz.cast(item));
    }
}