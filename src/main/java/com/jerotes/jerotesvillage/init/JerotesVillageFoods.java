package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.init.JerotesMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class JerotesVillageFoods {
    public static final FoodProperties RAMBLER_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build();
    public static final FoodProperties COOKED_RAMBLER_MEAT = new FoodProperties.Builder().nutrition(10).saturationMod(0.8f).meat().build();
    public static final FoodProperties MEGANOSE_SNOW_LIZARD_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build();
    public static final FoodProperties BITTER_COLD_FAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build();
    public static final FoodProperties COOKED_MEGANOSE_SNOW_LIZARD_MEAT = new FoodProperties.Builder().nutrition(10).saturationMod(0.8f).meat().build();
    public static final FoodProperties ABYSSALER = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties COOKED_ABYSSALER = new FoodProperties.Builder().nutrition(12).saturationMod(0.8f).build();
    public static final FoodProperties SOLISH_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build();
    public static final FoodProperties COOKED_SOLISH_MEAT = new FoodProperties.Builder().nutrition(10).saturationMod(0.8f).build();
    public static final FoodProperties SECOND_ROUND_NIGRUM = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.1f).build();
    public static final FoodProperties COOKED_SECOND_ROUND_NIGRUM = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
    public static final FoodProperties SECOND_ROUND_NIGRUM_SOUP = new FoodProperties.Builder().nutrition(8).saturationMod(2.4f).build();
    public static final FoodProperties BRIGHT_MELON = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build();
    public static final FoodProperties GOLDEN_BRIGHT_MELON = new FoodProperties.Builder().nutrition(8).saturationMod(1.2f).alwaysEat().effect(new MobEffectInstance(JerotesMobEffects.MAGIC_ABSORPTION.get(), 2400, 0), 1.0f).effect(new MobEffectInstance(MobEffects.SATURATION, 10, 0), 1.0f).build();
    public static final FoodProperties SECOND_ROUND_ALOE_LEAF = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0f).effect(new MobEffectInstance(MobEffects.HEAL, 1, 0), 0.1f).build();
    public static final FoodProperties ALOE_VERA_PULP = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0f).effect(new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).build();
    public static final FoodProperties HERO_SOUP = new FoodProperties.Builder().nutrition(8).saturationMod(2.4f).alwaysEat().effect(new MobEffectInstance(JerotesVillageMobEffects.ABUNDANT_COURAGE.get(), 18000, 0), 1.0f).build();
    public static final FoodProperties WONDERLAND_FRUIT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build();
    public static final FoodProperties PINK_SLIME_BALL = new FoodProperties.Builder().nutrition(1).saturationMod(0.3f).effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1.0f).effect(new MobEffectInstance(MobEffects.LEVITATION, 160, 0), 1.0f).alwaysEat().build();
    public static final FoodProperties GLUTINOUS_CRUST_MEAT_PIE = new FoodProperties.Builder().nutrition(12).saturationMod(1.2f).effect(new MobEffectInstance(MobEffects.SATURATION, 5, 0), 1.0f).effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, 0), 1.0f).alwaysEat().build();
    public static final FoodProperties SUN_CRAB = new FoodProperties.Builder().nutrition(3).saturationMod(0.4f).build();
    public static final FoodProperties COOKED_SUN_CRAB = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build();
    public static final FoodProperties SLIDER_MUCUS = new FoodProperties.Builder().nutrition(1).saturationMod(0.3f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 3600, 0), 1.0f).effect(new MobEffectInstance(MobEffects.GLOWING, 160, 0), 1.0f).alwaysEat().build();
    public static final FoodProperties PURPLE_SAND_GECKO_TAIL = new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).effect(new MobEffectInstance(MobEffects.UNLUCK, 72000, 2), 1.0f).meat().alwaysEat().build();
    public static final FoodProperties PURPLE_SANDWICH = new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).build();
    public static final FoodProperties BROWN_VENOMOUS_SLUG = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).effect(new MobEffectInstance(MobEffects.POISON, 1200, 0), 1.0f).build();
    public static final FoodProperties COOKED_BROWN_VENOMOUS_SLUG = new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build();
    public static final FoodProperties MEROR_STIMULIN = new FoodProperties.Builder().nutrition(0).saturationMod(0f).alwaysEat().effect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_STIMULIN.get(), 12000, 0, false, true), 1.0f).build();
    public static final FoodProperties IMMORTAL_BREW = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).alwaysEat().build();
    public static final FoodProperties FLAVORLESS_MELON_SLICE = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f).build();
    public static final FoodProperties GLISTERING_FLAVORLESS_MELON_SLICE = new FoodProperties.Builder().nutrition(6).saturationMod(0.4f).build();
    public static final FoodProperties CLOUD_FUR_VENISON = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).meat().build();
    public static final FoodProperties COOKED_CLOUD_FUR_VENISON = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).meat().build();

    public static final FoodProperties BRIGHT_GRASS_CARP = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_BRIGHT_GRASS_CARP = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build();
    public static final FoodProperties CHROMASNAP_BASS = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_CHROMASNAP_BASS = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build();
    public static final FoodProperties LAKE_LOACH = new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_LAKE_LOACH = new FoodProperties.Builder().nutrition(8).saturationMod(0.8f).build();
    public static final FoodProperties FLOWMINNOW = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_FLOWMINNOW = new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build();
    public static final FoodProperties BITTER_COD = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_BITTER_COD = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build();
    public static final FoodProperties GREYSCALE = new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_GREYSCALE = new FoodProperties.Builder().nutrition(8).saturationMod(0.8f).build();
    public static final FoodProperties JEWELROAMER = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_JEWELROAMER = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties STARBRITE = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_STARBRITE = new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build();
}
