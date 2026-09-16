package com.jerotes.jerotesvillage.init;

import com.google.common.collect.Sets;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class JerotesVillageBuiltInLootTable {
	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
	public static final ResourceLocation EMPTY = new ResourceLocation("empty");
	public static final ResourceLocation BARTENDER_GIFT = register("gameplay/hero_of_the_village/bartender_gift");
	public static final ResourceLocation BEEKEEPER_GIFT = register("gameplay/hero_of_the_village/beekeeper_gift");
	public static final ResourceLocation BREEDER_GIFT = register("gameplay/hero_of_the_village/breeder_gift");
	public static final ResourceLocation BUILDER_GIFT = register("gameplay/hero_of_the_village/builder_gift");
	public static final ResourceLocation CAPTAIN_GIFT = register("gameplay/hero_of_the_village/captain_gift");
	public static final ResourceLocation CHEF_GIFT = register("gameplay/hero_of_the_village/chef_gift");
	public static final ResourceLocation COACHMAN_GIFT = register("gameplay/hero_of_the_village/coachman_gift");
	public static final ResourceLocation CONSCRIPTION_GIFT = register("gameplay/hero_of_the_village/conscription_gift");
	public static final ResourceLocation FORGER_GIFT = register("gameplay/hero_of_the_village/forger_gift");
	public static final ResourceLocation HAWKER_GIFT = register("gameplay/hero_of_the_village/hawker_gift");
	public static final ResourceLocation HORSEMAN_GIFT = register("gameplay/hero_of_the_village/horseman_gift");
	public static final ResourceLocation HUNTER_GIFT = register("gameplay/hero_of_the_village/hunter_gift");
	public static final ResourceLocation ILLAGER_GIFT = register("gameplay/hero_of_the_village/illager_gift");
	public static final ResourceLocation JAILER_GIFT = register("gameplay/hero_of_the_village/jailer_gift");
	public static final ResourceLocation LAMPLIGHTER_GIFT = register("gameplay/hero_of_the_village/lamplighter_gift");
	public static final ResourceLocation MAGICIAN_GIFT = register("gameplay/hero_of_the_village/magician_gift");
	public static final ResourceLocation DYE_MERCHANT_GIFT = register("gameplay/hero_of_the_village/dye_merchant_gift");
	public static final ResourceLocation MESSENGER_GIFT = register("gameplay/hero_of_the_village/messenger_gift");
	public static final ResourceLocation OBSERVER_GIFT = register("gameplay/hero_of_the_village/observer_gift");
	public static final ResourceLocation PHYSICIAN_GIFT = register("gameplay/hero_of_the_village/physician_gift");
	public static final ResourceLocation PIGLIN_GIFT = register("gameplay/hero_of_the_village/piglin_gift");
	public static final ResourceLocation PISCATOR_GIFT = register("gameplay/hero_of_the_village/piscator_gift");
	public static final ResourceLocation SHOPKEEPER_GIFT = register("gameplay/hero_of_the_village/shopkeeper_gift");
	public static final ResourceLocation VILLAGEHEAD_GIFT = register("gameplay/hero_of_the_village/villagehead_gift");
	public static final ResourceLocation WITCH_GIFT = register("gameplay/hero_of_the_village/witch_gift");
	public static final ResourceLocation FISHING = register("gameplay/fishing");
	public static final ResourceLocation FISHING_JUNK = register("gameplay/fishing/junk");
	public static final ResourceLocation FISHING_TREASURE = register("gameplay/fishing/treasure");
	public static final ResourceLocation FISHING_FISH = register("gameplay/fishing/fish");
	public static final ResourceLocation LI_CAT_MORNING_GIFT = register("gameplay/li_cat_morning_gift");

	private static ResourceLocation register(String string) {
		return register(new ResourceLocation(JerotesVillage.MODID, string));
	}

	private static ResourceLocation register(ResourceLocation p_78770_) {
		if (LOCATIONS.add(p_78770_)) {
			return p_78770_;
		} else {
			throw new IllegalArgumentException(p_78770_ + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> all() {
		return IMMUTABLE_LOCATIONS;
	}
}