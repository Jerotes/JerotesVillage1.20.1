package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesPotions;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.recipes.brewing.AloeVeraPulpBrewingRecipe;
import com.jerotes.jerotesvillage.recipes.brewing.JerotesVillageBrewingRecipe;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class JerotesVillagePotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, JerotesVillage.MODID);

    public static final RegistryObject<Potion> CARVED_VILLAGE_ARROW = REGISTRY.register("carved_villager_arrow", () -> new Potion(new MobEffectInstance(JerotesVillageMobEffects.CARVED_VILLAGER_ARROW.get(), 40, 1)));
    public static final RegistryObject<Potion> BIG_WITCH_HEAL = REGISTRY.register("big_witch_heal", () -> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 4), new MobEffectInstance(MobEffects.REGENERATION, 600, 1)));
    public static final RegistryObject<Potion> BIG_WITCH_HURT = REGISTRY.register("big_witch_hurt", () -> new Potion(new MobEffectInstance(MobEffects.POISON, 600, 1), new MobEffectInstance(MobEffects.BLINDNESS, 600, 0), new MobEffectInstance(MobEffects.UNLUCK, 600, 2), new MobEffectInstance(MobEffects.HARM, 1, 1)));
    //鬼婆系列
    public static final RegistryObject<Potion> LOVES_SHAME = REGISTRY.register("loves_shame", () -> new Potion(
            new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 12 * 20, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 12 * 20, 1),
            new MobEffectInstance(MobEffects.POISON, 12 * 20, 0),
            new MobEffectInstance(MobEffects.HEAL, 1, 1),
            new MobEffectInstance(JerotesMobEffects.PRURITUS.get(), 12 * 20, 1)
    ));
    public static final RegistryObject<Potion> WITCH_ROT = REGISTRY.register("witch_rot", () -> new Potion(
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30 * 20, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * 20, 0),
            new MobEffectInstance(MobEffects.UNLUCK, 60 * 20, 0),
            new MobEffectInstance(JerotesMobEffects.MAGIC_ABSORPTION.get(), 30 * 20, 0),
            new MobEffectInstance(JerotesVillageMobEffects.FRAGRANCE.get(), 12 * 20, 1)
    ));
    public static final RegistryObject<Potion> UNEASY_RABBIT = REGISTRY.register("uneasy_rabbit", () -> new Potion(
            new MobEffectInstance(MobEffects.SLOW_FALLING, 90 * 20, 0),
            new MobEffectInstance(MobEffects.JUMP, 90 * 20, 1),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 90 * 20, 1),
            new MobEffectInstance(MobEffects.WEAKNESS, 90 * 20, 1)
    ));
    public static final RegistryObject<Potion> WITHER_SLEEP = REGISTRY.register("wither_sleep", () -> new Potion(
            new MobEffectInstance(MobEffects.WITHER, 120 * 20, 1),
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120 * 20, 0),
            new MobEffectInstance(MobEffects.HARM, 1, 2)
    ));
    public static final RegistryObject<Potion> VOMIT_INDUCER = REGISTRY.register("vomit_inducer", () -> new Potion(
            new MobEffectInstance(MobEffects.CONFUSION, 60 * 20, 0),
            new MobEffectInstance(MobEffects.HUNGER, 60 * 20, 0),
            new MobEffectInstance(MobEffects.WATER_BREATHING, 60 * 20, 0)
    ));
    public static final RegistryObject<Potion> LICH_CURSE = REGISTRY.register("lich_curse", () -> new Potion(
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120 * 20, 0),
            new MobEffectInstance(MobEffects.HEAL, 1, 2)
    ));
    public static final RegistryObject<Potion> LAST_STAND = REGISTRY.register("last_stand", () -> new Potion(
            new MobEffectInstance(JerotesVillageMobEffects.LAST_DITCH.get(), 120 * 20, 0)
    ));
    public static final RegistryObject<Potion> TROLL_TONGUE = REGISTRY.register("troll_tongue", () -> new Potion(
            new MobEffectInstance(MobEffects.REGENERATION, 120 * 20, 3),
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120 * 20, 3),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120 * 20, 3)
    ));
    public static final RegistryObject<Potion> HAUNTING_IMP = REGISTRY.register("haunting_imp", () -> new Potion(
            new MobEffectInstance(MobEffects.INVISIBILITY, 120 * 20, 0),
            new MobEffectInstance(MobEffects.NIGHT_VISION, 120 * 20, 0),
            new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), 120 * 20, 0)
    ));
    public static final RegistryObject<Potion> PURPLE_SANDS_SECRET = REGISTRY.register("purple_sands_secret", () -> new Potion(
            new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 1200 * 20, 0),
            new MobEffectInstance(MobEffects.HARM, 1, 4),
            new MobEffectInstance(MobEffects.HEAL, 1, 4)
    ));
    public static final RegistryObject<Potion> SERPENT_SCALE = REGISTRY.register("serpent_scale", () -> new Potion(
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 240 * 20, 1),
            new MobEffectInstance(MobEffects.LEVITATION, 24 * 20, 0),
            new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 24 * 20, 2)
    ));
    public static final RegistryObject<Potion> DESPICABLE_MEROR = REGISTRY.register("despicable_meror", () -> new Potion(
            new MobEffectInstance(JerotesVillageMobEffects.MEROR_STIMULIN.get(), 24 * 20, 1),
            new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 24 * 20, 0)
    ));
    public static final RegistryObject<Potion> SEA_MONSTER_WORSHIP = REGISTRY.register("sea_monster_worship", () -> new Potion(
            new MobEffectInstance(JerotesVillageMobEffects.MALIGNASAUR_ASPHYXIA.get(), 24 * 20, 0),
            new MobEffectInstance(JerotesVillageMobEffects.OCEAN_DRAGON_DETERRENCE.get(), 24 * 20, 0)
    ));
    public static final RegistryObject<Potion> DARK_HEART = REGISTRY.register("dark_heart", () -> new Potion(
            new MobEffectInstance(JerotesMobEffects.ABACK.get(), 240 * 20, 1),
            new MobEffectInstance(MobEffects.DARKNESS, 240 * 20, 0)
    ));
    public static final RegistryObject<Potion> BINDING_PACT = REGISTRY.register("binding_pact", () -> new Potion(
            new MobEffectInstance(JerotesVillageMobEffects.FAIRNESS.get(), 5 * 20, 0)
    ));
    //
    public static final RegistryObject<Potion> WILDERNESS_GIANT_POISON = REGISTRY.register("wilderness_giant_poison", () -> new Potion(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 240, 0)));
    public static final RegistryObject<Potion> LONG_WILDERNESS_GIANT_POISON = REGISTRY.register("long_wilderness_giant_poison", () -> new Potion(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 600, 0)));
    public static final RegistryObject<Potion> STRONG_WILDERNESS_GIANT_POISON = REGISTRY.register("strong_wilderness_giant_poison", () -> new Potion(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 160, 1)));


    public static void init() {
        //波涛粉
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BULGE_POWDER.get()), createPotion(Potions.WATER_BREATHING)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BULGE_POWDER.get()), createSplashPotion(Potions.WATER_BREATHING)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BULGE_POWDER.get()), createLingeringPotion(Potions.WATER_BREATHING)));
        //闪烁的无味瓜片
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.GLISTERING_FLAVORLESS_MELON_SLICE.get()), createPotion(Potions.REGENERATION)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.GLISTERING_FLAVORLESS_MELON_SLICE.get()), createSplashPotion(Potions.REGENERATION)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.GLISTERING_FLAVORLESS_MELON_SLICE.get()), createLingeringPotion(Potions.REGENERATION)));
        //芒草
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.AWN_HERB.get()), createPotion(Potions.HEALING)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.AWN_HERB.get()), createSplashPotion(Potions.HEALING)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.AWN_HERB.get()), createLingeringPotion(Potions.HEALING)));
        //锈蚀步甲颚
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.RUST_BEETLE_MANDIBLE.get()), createPotion(JerotesPotions.CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.RUST_BEETLE_MANDIBLE.get()), createSplashPotion(JerotesPotions.CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.RUST_BEETLE_MANDIBLE.get()), createLingeringPotion(JerotesPotions.CORROSIVE)));
        //锈蚀步甲颚-延长
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.REDSTONE), createPotion(JerotesPotions.STRONG_CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.REDSTONE), createSplashPotion(JerotesPotions.STRONG_CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.REDSTONE), createLingeringPotion(JerotesPotions.STRONG_CORROSIVE)));
        //锈蚀步甲颚-增强
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(JerotesPotions.LONG_CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.GLOWSTONE_DUST), createSplashPotion(JerotesPotions.LONG_CORROSIVE)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(JerotesPotions.CORROSIVE)), Ingredient.of(Items.GLOWSTONE_DUST), createLingeringPotion(JerotesPotions.LONG_CORROSIVE)));
        //褐毒蛞蝓
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get()), createPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get()), createSplashPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(Potions.AWKWARD)), Ingredient.of(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get()), createLingeringPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)));
        //褐毒蛞蝓-延长
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.REDSTONE), createPotion(JerotesVillagePotions.STRONG_WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.REDSTONE), createSplashPotion(JerotesVillagePotions.STRONG_WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.REDSTONE), createLingeringPotion(JerotesVillagePotions.STRONG_WILDERNESS_GIANT_POISON)));
        //褐毒蛞蝓-增强
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(JerotesVillagePotions.LONG_WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createSplashPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.GLOWSTONE_DUST), createSplashPotion(JerotesVillagePotions.LONG_WILDERNESS_GIANT_POISON)));
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createLingeringPotion(JerotesVillagePotions.WILDERNESS_GIANT_POISON)), Ingredient.of(Items.GLOWSTONE_DUST), createLingeringPotion(JerotesVillagePotions.LONG_WILDERNESS_GIANT_POISON)));

        //芦荟精华-榨取
        BrewingRecipeRegistry.addRecipe(new JerotesVillageBrewingRecipe(Ingredient.of(createPotion(Potions.WATER)), Ingredient.of(JerotesVillageItems.SECOND_ROUND_ALOE_LEAF.get()), new ItemStack(JerotesVillageItems.ALOE_VERA_PULP.get())));
        //芦荟精华-增强
        BrewingRecipeRegistry.addRecipe(new AloeVeraPulpBrewingRecipe());
    }

    public static ItemStack createPotion(RegistryObject<Potion> potion) {
        return createPotion(potion.get());
    }
    public static ItemStack createSplashPotion(RegistryObject<Potion> potion) {
        return createSplashPotion(potion.get());
    }
    public static ItemStack createLingeringPotion(RegistryObject<Potion> potion) {
        return createLingeringPotion(potion.get());
    }
    public static ItemStack createPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }
    public static ItemStack createSplashPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion);
    }
    public static ItemStack createLingeringPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
    }
}
