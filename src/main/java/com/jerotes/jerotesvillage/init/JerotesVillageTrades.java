package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.init.JerotesItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JerotesVillageTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(Items.ENDER_PEARL), 30, 2, 0.05f));

			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.GUNPOWDER, 2), 30, 2, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.DEAD_BUSH, 2), 30, 5, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.GLOWSTONE_DUST, 4), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),
					new ItemStack(Items.EXPERIENCE_BOTTLE), 30, 10, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(Items.GOLDEN_APPLE), 30, 10, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(Items.BLAZE_ROD), 30, 15, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.NETHER_WART), 30, 15, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 64),
					new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 30, 20, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(Items.DRAGON_BREATH), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_OBSERVER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.ROTTEN_FLESH, 32),

					new ItemStack(Items.EMERALD), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.BONE, 16),

					new ItemStack(Items.EMERALD), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.STRING, 16),

					new ItemStack(Items.EMERALD), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.SLIME_BALL, 5),

					new ItemStack(Items.EMERALD), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.GUNPOWDER, 12),

					new ItemStack(Items.EMERALD), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.SPIDER_EYE, 8),

					new ItemStack(Items.EMERALD), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.PHANTOM_MEMBRANE, 6),

					new ItemStack(Items.EMERALD), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.ENDER_PEARL, 3),

					new ItemStack(Items.EMERALD), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.LEATHER, 6),

					new ItemStack(Items.EMERALD), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.FEATHER, 24),

					new ItemStack(Items.EMERALD), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_SHOPKEEPER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BONE_MEAL, 3), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SUGAR_CANE, 4), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.INK_SAC, 3), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.VINE, 4), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.RABBIT_HIDE, 16), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.MINECART, 2), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),

					new ItemStack(Items.MAGMA_BLOCK), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 6),

					new ItemStack(Items.SPONGE), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 32),

					new ItemStack(Items.SHULKER_BOX), 30, 20, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.EGG, 16), 30, 2, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_CHEF.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BREAD, 6), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COOKED_BEEF, 5), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COOKED_PORKCHOP, 5), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COOKED_CHICKEN, 8), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PUMPKIN_PIE, 4), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COOKIE, 18), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.CAKE), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.APPLE, 4), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.BUCKET),

					new ItemStack(Items.MILK_BUCKET), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.BOWL),

					new ItemStack(Items.MUSHROOM_STEW), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_HUNTER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(Items.BOW), 30, 2, 0.05f));

			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.ARROW, 16), 30, 2, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),
					new ItemStack(Items.CROSSBOW), 30, 5, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.PORKCHOP, 5), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.FEATHER, 16), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.BEEF, 5), 30, 10, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.LEATHER), 30, 15, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.CHICKEN, 8), 30, 15, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.MUTTON, 10), 30, 20, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.WHITE_WOOL, 3), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_PHYSICIAN.get()) {
			//1级 奶桶
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(Items.MILK_BUCKET), 30, 2, 0.05f));
			//1级 治疗药片
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.THERAPEUTIC_TABLET.get(), 3), 30, 2, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),
					new ItemStack(Items.GLISTERING_MELON_SLICE), 30, 5, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(JerotesItems.HEAL_JAVELIN.get()), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.VIOLENT_TABLET.get(), 3), 30, 10, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),
					new ItemStack(JerotesItems.RESILIENT_BANDAGE.get(), 3), 30, 10, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),
					new ItemStack(JerotesVillageItems.VILLAGER_METAL_SYRINGE.get(), 1), 30, 15, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.DEXTEROUS_TABLET.get(), 1), 30, 15, 0.05f));
		}
		//应征官
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 1),
					JerotesVillageTabs.CarvedBanner(), 30, 2, 0.05f));

			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,2),
					PotionUtils.setPotion(new ItemStack(Items.POTION), JerotesVillagePotions.CARVED_VILLAGE_ARROW.get()), 30, 2, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(JerotesVillageItems.CARVED_FLAG.get()), 30, 5, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),
					new ItemStack(JerotesVillageItems.CARVED_VILLAGER_SPECTRAL_ARROW.get(), 8), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 10),
					new ItemStack(JerotesVillageItems.CARVED_CONTACTER.get()), 30, 10, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.CARVED_VILLAGER_ITEM.get()), 30, 10, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.CARVED_AMETHYST_FLUTE.get()), 30, 15, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.CARVED_DOG_WHISTLE.get()), 30, 15, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesVillageItems.CARVED_IRON_GOLEM_CORE.get()), 30, 20, 0.05f));

			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 20),
					new ItemStack(JerotesVillageItems.CARVED_LEADER_UPGRADE_SMITHING_TEMPLATE.get()), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_BARTENDER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BREAD), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),

					new ItemStack(Items.MUSIC_DISC_CAT), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BEETROOT_SOUP), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.MUSHROOM_STEW), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.RABBIT_STEW), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.GLASS_BOTTLE, 8), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.MILK_BUCKET), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.GHAST_TEAR), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.POPPY, 12), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 25),

					new ItemStack(Items.SNIFFER_EGG), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_LAMPLIGHTER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.LANTERN), 30, 2, 0.05f));

			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.TORCH, 32), 30, 2, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),
					new ItemStack(Items.SOUL_LANTERN), 30, 5, 0.05f));

			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.CAMPFIRE), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 1),
					new ItemStack(Items.CHAINMAIL_HELMET), 30, 5, 0.05f));

			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 1),
					new ItemStack(Items.CHAINMAIL_BOOTS), 30, 5, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),
					new ItemStack(Items.CHAINMAIL_LEGGINGS), 30, 5, 0.05f));

			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),
					new ItemStack(Items.CHAINMAIL_CHESTPLATE), 30, 5, 0.05f));
		}
		//养马人
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_HORSEMAN.get()) {
			//1级 皮革马铠
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 6),
					new ItemStack(Items.LEATHER_HORSE_ARMOR), 30, 2, 0.05f));
			//1级 鞍
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(Items.SADDLE), 30, 2, 0.05f));
			//2级 铁马铠
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(Items.IRON_HORSE_ARMOR), 30, 5, 0.05f));
			//2级 马鞭
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(JerotesVillageItems.VILLAGER_METAL_HORSEWHIP.get()), 30, 5, 0.05f));
			//3级 金马铠
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(Items.GOLDEN_HORSE_ARMOR), 30, 10, 0.05f));
			//3级 金胡萝卜
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.GOLDEN_CARROT), 30, 10, 0.05f));
			//4级 拴绳
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(Items.LEAD, 2), 30, 15, 0.05f));
			//4级 干草块
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(Items.HAY_BLOCK, 2), 30, 15, 0.05f));
			//5级 金苹果
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(Items.GOLDEN_APPLE), 30, 20, 0.05f));
			//5级 钻石马铠
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
					new ItemStack(Items.DIAMOND_HORSE_ARMOR), 30, 20, 0.05f));
		}
		//车夫
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_COACHMAN.get()) {
			//1级 兽铠基底
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),
					new ItemStack(JerotesItems.BEAST_ARMOR.get(), 4), 30, 2, 0.05f));
			//1级 皮革战兽铠
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 6),
					new ItemStack(JerotesItems.LEATHER_WAR_BEAST_ARMOR.get()), 30, 2, 0.05f));
			//2级 战兽铠基底
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(JerotesItems.WAR_BEAST_ARMOR_BASE.get()), 30, 5, 0.05f));
			//2级 锁链战兽铠
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 8),
					new ItemStack(JerotesItems.CHAINMAIL_WAR_BEAST_ARMOR.get()), 30, 5, 0.05f));
			//3级 铁战兽铠
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(JerotesItems.IRON_WAR_BEAST_ARMOR.get()), 30, 5, 0.05f));
			//3级 金战兽铠
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),
					new ItemStack(JerotesItems.GOLDEN_WAR_BEAST_ARMOR.get()), 30, 10, 0.05f));
			//4级 村民金属战兽铠
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
					new ItemStack(JerotesVillageItems.VILLAGER_METAL_WAR_BEAST_ARMOR.get()), 30, 15, 0.05f));
			//4级 铜刻上马台
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(JerotesVillageItems.CARVED_HORSE_SLAB.get(), 1), 30, 15, 0.05f));;
			//5级 钻石战兽铠
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
					new ItemStack(JerotesItems.DIAMOND_WAR_BEAST_ARMOR.get()), 30, 20, 0.05f));
			//5级 锁链巨兽铠
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 8),
					new ItemStack(JerotesItems.CHAINMAIL_GIANT_BEAST_ARMOR.get()), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_BUILDER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.DIRT, 64), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.OAK_LOG, 4), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COBBLESTONE, 20), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SAND, 8), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.RED_SAND, 4), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.CLAY, 5), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.ICE, 4), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.DEEPSLATE, 20), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BLACKSTONE, 12), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.NETHER_BRICKS, 2), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_BREEDER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.WHEAT_SEEDS, 4), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BEETROOT_SEEDS, 4), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PUMPKIN_SEEDS, 4), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.MELON_SEEDS, 4), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.CARROT, 4), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.POTATO, 4), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SWEET_BERRIES, 4), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.OAK_SAPLING, 4), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BAMBOO, 4), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COCOA_BEANS, 3), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_PISCATOR.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.FISHING_ROD), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COD, 6), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SALMON, 6), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.TROPICAL_FISH, 4), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PUFFERFISH, 2), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(Items.SALMON_BUCKET), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(Items.COD_BUCKET), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(Items.TROPICAL_FISH_BUCKET), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),

					new ItemStack(Items.AXOLOTL_BUCKET), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COOKED_COD, 9), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_MAGICIAN.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BROWN_MUSHROOM), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.RED_MUSHROOM), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.LAPIS_LAZULI), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),

					new ItemStack(Items.SKELETON_SKULL), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(Items.CRYING_OBSIDIAN), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.CAULDRON), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.BUCKET),

					new ItemStack(Items.POWDER_SNOW_BUCKET), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),

					new ItemStack(Items.TOTEM_OF_UNDYING), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SPECTRAL_ARROW), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 32),

					new ItemStack(Items.END_CRYSTAL), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_CAPTAIN.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PRISMARINE_CRYSTALS, 2), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.SEA_PICKLE), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.GLOW_INK_SAC), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SCUTE, 4), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 5),

					new ItemStack(Items.PUFFERFISH_BUCKET), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 5),

					new ItemStack(Items.NAUTILUS_SHELL), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PRISMARINE_SHARD, 3), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 8),

					new ItemStack(Items.TRIDENT), 30, 15, 0.05f));
			//5级 海豚铜饰
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 32),
					new ItemStack(JerotesVillageItems.DOLPHIN_COPPER_DECORATION.get()), 30, 20, 0.05f));
			//5级 村民邀请券
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
					new ItemStack(JerotesVillageItems.VILLAGER_INVITATION.get()), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_MESSENGER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.OAK_SIGN), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.WRITABLE_BOOK), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_JAILER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),

					new ItemStack(JerotesItems.ANESTHETIC_JAVELIN.get()), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.IRON_BARS, 12), 30, 2, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_HAWKER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.FLINT, 8), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(JerotesVillageItems.ARMOR_STAND_ARM_SET.get(), 1), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.GLOW_ITEM_FRAME), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 16),

					new ItemStack(JerotesVillageItems.CARVED_LEAD.get(), 2), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 20),

					new ItemStack(Items.NAME_TAG), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 5),

					new ItemStack(JerotesVillageItems.UNICYCLE_ITEM.get()), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 4),

					new ItemStack(Items.GOAT_HORN), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 8),

					new ItemStack(JerotesVillageItems.SPEAR_MACHINE_ITEM.get()), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.TADPOLE_BUCKET), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.LAVA_BUCKET), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_VILLAGEHEAD.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
					new ItemStack(Items.BELL), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),
					new ItemStack(JerotesVillageItems.VILLAGER_BLOCK.get()), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
					new ItemStack(JerotesVillageItems.CERTIFICATE_OF_HONOR_FOR_HERO_OF_THE_VILLAGE.get()), 30, 5, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_FORGER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get(), 2), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.CHARCOAL, 8), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.IRON_INGOT, 4), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COAL, 10), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.COPPER_INGOT, 4), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.QUARTZ, 12), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.REDSTONE, 2), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.LAPIS_LAZULI), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.GOLD_INGOT), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,4),

					new ItemStack(Items.DIAMOND), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_DYE_MERCHANT.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.WHITE_DYE, 3), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BLACK_DYE, 3), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.RED_DYE, 3), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BLUE_DYE, 3), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.YELLOW_DYE, 3), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.GREEN_DYE, 3), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.PURPLE_DYE, 3), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.ORANGE_DYE, 3), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.GRAY_DYE, 3), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BROWN_DYE, 3), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(JerotesVillageItems.ILLAGER_SIGNAL_LIGHT.get()), 30, 2, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 2),

					new ItemStack(Items.FIRE_CHARGE), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT),

					new ItemStack(Items.BLACKSTONE, 4), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT),

					new ItemStack(Items.NETHER_BRICK, 4), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT),

					new ItemStack(Items.SPECTRAL_ARROW, 6), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 2),

					new ItemStack(Items.QUARTZ, 12), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 2),

					new ItemStack(Items.CRYING_OBSIDIAN), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 6),

					new ItemStack(Items.ENDER_PEARL, 4), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT),

					new ItemStack(Items.SOUL_SAND), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT),

					new ItemStack(Items.BLAZE_ROD), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 32),

					new ItemStack(Items.NETHERITE_SCRAP), 30, 20, 0.05f));
		}
		if (event.getType() == JerotesVillageVillagerProfessions.VILLAGER_BEEKEEPER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.GLASS_BOTTLE, 16), new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.HONEY_BOTTLE, 16), 30, 2, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.HONEYCOMB, 4), 30, 2, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.HONEYCOMB_BLOCK), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.CAMPFIRE), 30, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.BEEHIVE), 30, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.HONEY_BLOCK), 30, 10, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.SUNFLOWER, 2), 30, 15, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(Items.SPORE_BLOSSOM), 30, 15, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.DANDELION, 4), 30, 20, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD),

					new ItemStack(Items.FLOWER_POT), 30, 20, 0.05f));
		}
	}
}
